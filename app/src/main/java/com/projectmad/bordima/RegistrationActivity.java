package com.projectmad.bordima;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText nameEdt,emailEdt,pwdEdt,cnfPwdEdt;
    private Button signupBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameEdt = findViewById(R.id.idEdtName);
        emailEdt = findViewById(R.id.idEdtEmail);
        pwdEdt = findViewById(R.id.idEdtPassword);
        cnfPwdEdt = findViewById(R.id.idEdtCnfPwd);
        signupBtn = findViewById(R.id.idBtnSignup);
        loadingPB = findViewById(R.id.idPBLoading);
        loginTV = findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String name = nameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdEdt.getText().toString();

                //validations
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegistrationActivity.this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
                }else if(!pwd.equals(cnfPwd)){
                    Toast.makeText(RegistrationActivity.this, "please check both passwords", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText(RegistrationActivity.this, "Empty values not allowed", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Register Successful..!Please login with your Email and password", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "Fail to register User..", Toast.LENGTH_SHORT).show();
                            } 
                        }
                    });
                }

            }
        });

    }
}
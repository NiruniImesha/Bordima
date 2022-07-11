package com.projectmad.bordima;

<<<<<<< Updated upstream
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
>>>>>>> Stashed changes
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
<<<<<<< Updated upstream
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEdt,pwdEdt;
    private Button loginBtn;
    private ProgressBar loadingPB;
    private TextView signupTV;
    private FirebaseAuth mAuth;
=======
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projectmad.bordima.Model.Users;

public class LoginActivity extends AppCompatActivity {

    private EditText InputPhoneNumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;

    private String parentDbName = "Users";

>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
<<<<<<< Updated upstream
        emailEdt = findViewById(R.id.idEdtEmail);
        pwdEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        loadingPB = findViewById(R.id.idPBLoading);
        signupTV = findViewById(R.id.idTVSignup);
        mAuth = FirebaseAuth.getInstance();

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String email = emailEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(LoginActivity.this,AddPromotionActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Fail to Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
=======

        LoginButton = findViewById(R.id.loginBtnLogin);
        InputPhoneNumber = findViewById(R.id.inputPhoneNoLogin);
        InputPassword = findViewById(R.id.inputPasswordLogin);
        AdminLink = (TextView) findViewById(R.id.adminPanelLink);
        NotAdminLink = (TextView) findViewById(R.id.notAdminPanelLink);
        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });
        //when user click im admin
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";

            }
        });
        //when user click again im admin
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            }
        });
    }

    private void loginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait..We are checking credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone, password);

        }
    }

    private void AllowAccessToAccount(String phone, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){
                        if(usersData.getPassword().equals(password)){

                            if(parentDbName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Welcome Admin..You Logged in Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,PromotionsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(parentDbName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,BottomNavigationBar.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid Password..Please Try again", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }
                else{
                    Toast.makeText(LoginActivity.this, "Account with this "+phone+" Number not Exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
>>>>>>> Stashed changes
    }
}
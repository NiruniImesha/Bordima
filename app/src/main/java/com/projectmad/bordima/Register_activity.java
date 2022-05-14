package com.projectmad.bordima;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.EditText;

import java.util.HashMap;

public class Register_activity extends AppCompatActivity {

    //init ui views
    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }

            private void CreateAccount() {
                String name = InputName.getText().toString();
                String phone = InputPhoneNumber.getText().toString();
                String password = InputPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Register_activity.this, "Please enter your name...", LENGTH_SHORT).show();
                } else if (!Patterns.PHONE.matcher(phone).matches() || TextUtils.isEmpty(phone)) {
                    Toast.makeText(Register_activity.this, "Please enter your phone no...", LENGTH_SHORT).show();
                } else if (password.length()<=4 || TextUtils.isEmpty(password)) {
                    Toast.makeText(Register_activity.this, "Please enter your password (Size should be minimum 4 characters)...", LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidatephoneNumber(name, phone, password);
                }
            }

            private void ValidatephoneNumber(String name, String phone, String password) {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!(snapshot.child("Users").child(phone).exists()))
                        {
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("phone", phone);
                            userdataMap.put("password", password);
                            userdataMap.put("name", name);

                            RootRef.child("Users").child(phone).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(Register_activity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();

                                                Intent intent = new Intent(Register_activity.this, Login_activity.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                loadingBar.dismiss();
                                                Toast.makeText(Register_activity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(Register_activity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Toast.makeText(Register_activity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Register_activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
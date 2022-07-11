package com.projectmad.bordima;

<<<<<<< Updated upstream
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
=======
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    private Button loginButton,signupButton;
    private TextView adminLoginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
=======

        loginButton = (Button) findViewById(R.id.loginBtn);
        signupButton = (Button) findViewById(R.id.signupBtn);
        loadingBar = new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
>>>>>>> Stashed changes
    }
} 
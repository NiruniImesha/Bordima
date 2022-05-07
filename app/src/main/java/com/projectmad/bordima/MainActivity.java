package com.projectmad.bordima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bordingHouseBtn, foodBtn, laundryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bordingHouseBtn = (Button) findViewById(R.id.btnBordingHouse);
        foodBtn = (Button) findViewById(R.id.btnFood);
        laundryBtn = (Button) findViewById(R.id.btnLaundry);

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddServiceActivity.class);
                intent.putExtra("category","Food");
                startActivity(intent);
            }
        });

        laundryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddServiceActivity.class);
                intent.putExtra("category","Laundry");
                startActivity(intent);
            }
        });
    }
}
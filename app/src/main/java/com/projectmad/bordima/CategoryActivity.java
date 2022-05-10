package com.projectmad.bordima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    private Button bordingHouseBtn, foodBtn, laundryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bordingHouseBtn = (Button) findViewById(R.id.btnBordingHouse);
        foodBtn = (Button) findViewById(R.id.btnFood);
        laundryBtn = (Button) findViewById(R.id.btnLaundry);

        bordingHouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CategoryActivity.this,AddBordingActivity.class);
                intent.putExtra("category","Boarding");
                startActivity(intent);
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddServiceActivity.class);
                intent.putExtra("category","Food");
                startActivity(intent);
            }
        });

        laundryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddServiceActivity.class);
                intent.putExtra("category","Laundry");
                startActivity(intent);
            }
        });
    }
}
package com.projectmad.bordima;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bordingHouseBtn, foodBtn, laundryBtn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //variables
    Animation topAnim, bottomAnim;
    ImageView imageView;
    TextView textView, textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        imageView.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);
        textView2.setAnimation(bottomAnim);

        int SPLASH_SCREEN = 5000;
        new Handler().postDelayed(() -> {
           Intent intent = new Intent(MainActivity.this,Login.class);
           Pair[] pairs = new Pair[1];
           pairs[0] = new Pair<View,String>(imageView,"logo_image");

           ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
           startActivity(intent,options.toBundle());
        }, SPLASH_SCREEN);

    }




}
package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    private Button logout_btn, review_btn,feedback;

    private ReviewManager manager;
    private ReviewInfo reviewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activeReviewInfo();

        logout_btn = (Button) findViewById(R.id.logout_btn);
        review_btn = (Button) findViewById(R.id.rating);
        feedback = (Button) findViewById(R.id.feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WriteReviewActivity.class);
                startActivity(intent);
            }
        });

        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReviewFlow();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void activeReviewInfo()
    {
        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = manager.requestReviewFlow();

        managerInfoTask.addOnSuccessListener((task)->
        {
            if (managerInfoTask.isSuccessful())
            {
                reviewInfo = managerInfoTask.getResult();
            }
            else
            {
                Toast.makeText(this, "Review failed to start", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void startReviewFlow()
    {
        if (reviewInfo != null)
        {
            Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
            flow.addOnSuccessListener(task->
            {
                Toast.makeText(this, "Rating is completed", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
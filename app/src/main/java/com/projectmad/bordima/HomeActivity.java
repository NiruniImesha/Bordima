package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projectmad.bordima.Model.Feedback;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    //Feedback list
    FirebaseDatabase firebaseDatabase;
    DatabaseReference RootRef;
    RecyclerView recyclerView;
    FeedbackAdapter feedbackAdapter;
    ArrayList<Feedback> FeedbackArrayList;
    //Feedback list

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

        recyclerView = findViewById(R.id.FeedbackList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        RootRef = firebaseDatabase.getReference("Feedbacks");
        FeedbackArrayList = new ArrayList<Feedback>();
        feedbackAdapter = new FeedbackAdapter(HomeActivity.this,FeedbackArrayList);
        recyclerView.setAdapter(feedbackAdapter);

        RootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Feedback feedback = snapshot.getValue(Feedback.class);
                FeedbackArrayList.add(feedback);
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WriteReviewActivity.class);
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
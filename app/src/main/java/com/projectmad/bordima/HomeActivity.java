package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
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

    private Button logout_btn,feedback, Service;

    //service ui views
    private TextView serviceName, serviceDes, serviceLoc, serviceCont, packageName, packagePrice;
    private ImageView serviceImg, packageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

            //logout_btn = (Button) findViewById(R.id.logout);
            feedback = (Button) findViewById(R.id.leavefeedback);
            //Service = (Button) findViewById(R.id.allservices);

            //text views (service)
            serviceName = (TextView) findViewById(R.id.service_name);
            serviceDes = (TextView) findViewById(R.id.service_description);
            serviceLoc = (TextView) findViewById(R.id.service_location);
            serviceCont = (TextView) findViewById(R.id.service_contact);
            packageName = (TextView) findViewById(R.id.package_name);
            packagePrice = (TextView) findViewById(R.id.package_price);

            //image views(service)
            serviceImg = (ImageView) findViewById(R.id.service_image);
            packageImg = (ImageView) findViewById(R.id.package_image);

            recyclerView = findViewById(R.id.feedbackrv);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            firebaseDatabase = FirebaseDatabase.getInstance();
            RootRef = firebaseDatabase.getReference("Feedbacks");
            FeedbackArrayList = new ArrayList<Feedback>();
            feedbackAdapter = new FeedbackAdapter(HomeActivity.this,FeedbackArrayList);
            recyclerView.setAdapter(feedbackAdapter);

            //get data as snapshots from the database
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

            /*logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });*/

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, WriteReviewActivity.class);
                    startActivity(intent);
                }
            });

            /*Service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, ServiceActivity.class);
                    startActivity(intent);
                }
            });*/
        }

}
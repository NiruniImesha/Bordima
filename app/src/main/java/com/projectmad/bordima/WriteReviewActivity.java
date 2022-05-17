package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class WriteReviewActivity extends AppCompatActivity {

    private String review,saveCurrentDate, saveCurrentTime, FeedbackRandomKey;
    private float rating;
    private DatabaseReference FeedbackRef;

    //ui views
    private RatingBar Inputrating;
    private EditText Inputreview;
    private Button submitBtn;

    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write_review);

        FeedbackRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

        //init ui views
        Inputrating = findViewById(R.id.ratingBar);
        Inputreview = findViewById(R.id.reviewEt);
        submitBtn = findViewById(R.id.submitBtn);

        loadingBar = new ProgressDialog(this);

        Inputrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        //input data
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateRatingsReviews();
            }

        });


    }

    private void ValidateRatingsReviews() {

        review = Inputreview.getText().toString();
        rating = Inputrating.getRating();

        if (TextUtils.isEmpty(review))
        {
            Toast.makeText(this, "Please leave feedback...", Toast.LENGTH_SHORT).show();
        }
        else if (rating == 0.0)
        {
            Toast.makeText(this, "Please leave star rating...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreFeedbackInformation();
        }

    }

    private void StoreFeedbackInformation() {

        loadingBar.setTitle("Leaving new Feedback");
        loadingBar.setMessage("Dear customer, please wait while we are leaving the new feedback.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        FeedbackRandomKey = saveCurrentDate + saveCurrentTime;
        
        SaveFeedbackInfoToDatabase();
    }

    private void SaveFeedbackInfoToDatabase() {

        HashMap<String, Object> FeedbackMap = new HashMap<>();
        FeedbackMap.put("FID", FeedbackRandomKey);
        FeedbackMap.put("data", saveCurrentDate);
        FeedbackMap.put("time", saveCurrentTime);
        FeedbackMap.put("rating", rating);
        FeedbackMap.put("review", review);


        FeedbackRef.child(FeedbackRandomKey).updateChildren(FeedbackMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(WriteReviewActivity.this, HomeActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(WriteReviewActivity.this, "Feedback is saved successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(WriteReviewActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}
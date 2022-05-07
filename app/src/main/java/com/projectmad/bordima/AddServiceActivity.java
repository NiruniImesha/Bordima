package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddServiceActivity extends AppCompatActivity {

    private String categoryName;
    private Button nextbtn;
    private EditText InputName, InputCategory,InputDescription, InputLocation, InputContactNo;
    private ImageView InputCoverphoto;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        categoryName = getIntent().getExtras().get("category").toString();

        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        nextbtn =(Button) findViewById(R.id.ServiceNext);
        InputName = (EditText) findViewById(R.id.ServiceName);
        InputContactNo = (EditText) findViewById(R.id.ServiceContactNo);
        //InputCoverphoto = (ImageView) findViewById(R.id.ServiceCoverPhoto);
        InputDescription = (EditText) findViewById(R.id.ServiceDdescription);
        InputLocation = (EditText) findViewById(R.id.ServiceLocation);
        loadingbar = new ProgressDialog(this);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddServiceActivity.this,AddPackageActivity.class);
                startActivity(intent);
            }
        });

    }
}
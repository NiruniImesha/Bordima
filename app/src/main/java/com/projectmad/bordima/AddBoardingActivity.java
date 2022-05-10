package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddBoardingActivity extends AppCompatActivity {

    private String CategoryName, Title, BoardingLocation, BoardingPrice, BoardingContact, Bedroom, Bathroom, Kitchen, BoardingDescription, saveCurrentDate, saveCurrentTime;
    private Button PostBtn;
    private ImageView InputBoardingImage;
    private EditText InputTitle, InputBoardingLocation, InputBoardingContact, InputBoardingPrice, InputBedroom, InputBathroom, InputKitchen, InputBoardingDesccription;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String BoardingRandomKey, downloadImageUrl;
    private StorageReference BoardingImagesRef;
    private DatabaseReference BoardingRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        CategoryName = getIntent().getExtras().get("category").toString();
        BoardingImagesRef = FirebaseStorage.getInstance().getReference().child("Service Images");
        BoardingRef = FirebaseDatabase.getInstance().getReference().child("Service");

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();

        PostBtn =(Button) findViewById(R.id.BoardingPost);
        InputTitle = (EditText) findViewById(R.id.BoardingName);
        InputBoardingLocation = (EditText) findViewById(R.id.BoardingLocation);
        InputBoardingContact = (EditText) findViewById(R.id.BoardingContactNo);
        InputBoardingDesccription = (EditText) findViewById(R.id.BoardingDescription);
        InputBedroom = (EditText) findViewById(R.id.Bedroom);
        InputBathroom = (EditText) findViewById(R.id.Bathroom);
        InputKitchen = (EditText) findViewById(R.id.Kitchen);
        InputBoardingPrice = (EditText) findViewById(R.id.BoardingPrice);
        InputBoardingImage= (ImageView) findViewById(R.id.BoardingImageBtn);
        loadingBar = new ProgressDialog(this);

        InputBoardingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputBoardingImage.setImageURI(ImageUri);
        }
    }


    private void ValidateProductData()
    {
        Title = InputTitle.getText().toString();
        BoardingLocation = InputBoardingLocation.getText().toString();
        BoardingDescription = InputBoardingDesccription.getText().toString();
        BoardingPrice = InputBoardingPrice.getText().toString();
        BoardingContact = InputBoardingContact.getText().toString();
        Bedroom = InputBedroom.getText().toString();
        Bathroom = InputBathroom.getText().toString();
        Kitchen = InputKitchen.getText().toString();

        if (TextUtils.isEmpty(Title))
        {
            Toast.makeText(this, "Please enter title...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Bedroom))
        {
            Toast.makeText(this, "Please enter the count of bedrooms...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Bathroom))
        {
            Toast.makeText(this, "Please enter the count of bathrooms...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Kitchen))
        {
            Toast.makeText(this, "Please enter the count of kitchens...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BoardingDescription))
        {
            Toast.makeText(this, "Please write a small description...", Toast.LENGTH_SHORT).show();
        }
        else if (ImageUri == null)
        {
            Toast.makeText(this, "Cover photo is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BoardingLocation))
        {
            Toast.makeText(this, "Please enter location...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BoardingPrice))
        {
            Toast.makeText(this, "Please enter price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BoardingContact))
        {
            Toast.makeText(this, "Please enter contact number...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreServiceInformation();
        }
    }



    private void StoreServiceInformation()
    {
        loadingBar.setTitle("Add New Boarding Place");
        loadingBar.setMessage("please wait while we are adding the new boarding place.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        BoardingRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = BoardingImagesRef.child(ImageUri.getLastPathSegment() + BoardingRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);



        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddBoardingActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddBoardingActivity.this, "Boarding house cover photo uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddBoardingActivity.this, "got the Boarding place image Url Successfully", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> boardingMap = new HashMap<>();
        boardingMap.put("bid", BoardingRandomKey);
        boardingMap.put("date", saveCurrentDate);
        boardingMap.put("time", saveCurrentTime);
        boardingMap.put("title", Title);
        boardingMap.put("bedroom", Bedroom);
        boardingMap.put("bimage", downloadImageUrl);
        boardingMap.put("bathroom", Bathroom);
        boardingMap.put("kitchen", Kitchen);
        boardingMap.put("description", BoardingDescription);
        boardingMap.put("location", BoardingLocation);
        boardingMap.put("price", BoardingPrice);
        boardingMap.put("contact", BoardingContact);

        BoardingRef.child(BoardingRandomKey).updateChildren(boardingMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AddBoardingActivity.this, CategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddBoardingActivity.this, "Boarding place is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddBoardingActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

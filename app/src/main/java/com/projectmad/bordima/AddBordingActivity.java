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

public class AddBordingActivity extends AppCompatActivity {

    private String BordingTitle,BedRooms,BathRooms,Kitchens, BordingDescription, BordingContactNo, BordingPrice,BordingLocation, saveCurrentDate, saveCurrentTime;
    private Button PostBtn;
    private ImageView InputBordingImage;
    private EditText InputBordingTitle, InputBordingDescription, InputBordingContactNo, InputBordingLocation, InputBordingPrice, InputBedRooms, InputBathRooms, InputKitchens;
    private static final int GalleryPick = 1;
    private Uri ImageURI;
    private String BordingRandomKey, downloadImageUrl;
    private StorageReference BordingImagesRef;
    private DatabaseReference BordingRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bording);

        BordingImagesRef = FirebaseStorage.getInstance().getReference().child("Bording Images");
        BordingRef = FirebaseDatabase.getInstance().getReference().child("Bording");//Create DB

        PostBtn =(Button) findViewById(R.id.PostBtn);
        InputBordingTitle = (EditText) findViewById(R.id.BordingTitle);
        InputBordingContactNo = (EditText) findViewById(R.id.BordingContactNo);
        InputBordingImage= (ImageView) findViewById(R.id.BordingImageBtn);
        InputBordingDescription = (EditText) findViewById(R.id.BordingDescription);
        InputBordingLocation = (EditText) findViewById(R.id.BordingLocation);
        InputBordingPrice = (EditText) findViewById(R.id.BordingPrice);
        InputBedRooms = (EditText) findViewById(R.id.BedRooms);
        InputBathRooms = (EditText) findViewById(R.id.BathRooms);
        InputKitchens = (EditText) findViewById(R.id.Kitchens);
        loadingBar = new ProgressDialog(this);

        InputBordingImage.setOnClickListener(view -> OpenGallery());


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
            ImageURI = data.getData();
            InputBordingImage.setImageURI(ImageURI);
        }
    }


    private void ValidateProductData()
    {
        BordingDescription = InputBordingDescription.getText().toString();
        BordingTitle = InputBordingTitle.getText().toString();
        BordingContactNo = InputBordingContactNo.getText().toString();
        BordingLocation = InputBordingLocation.getText().toString();
        BordingPrice = InputBordingPrice.getText().toString();
        BedRooms = InputBedRooms.getText().toString();
        BathRooms = InputBathRooms.getText().toString();
        Kitchens = InputKitchens.getText().toString();


        if (ImageURI == null)
        {
            Toast.makeText(this, "Service image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BordingDescription))
        {
            Toast.makeText(this, "Please write Boarding description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BordingTitle))
        {
            Toast.makeText(this, "Please enter Boarding name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(BordingLocation))
        {
            Toast.makeText(this, "Please enter Boarding Place location...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreBordingInformation();
        }
    }



    private void StoreBordingInformation()
    {
        loadingBar.setTitle("Add New Boarding Place");
        loadingBar.setMessage("Please wait while we are adding the new Boarding Ad.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        BordingRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = BordingImagesRef.child(ImageURI.getLastPathSegment() + BordingRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageURI);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddBordingActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddBordingActivity.this, "Boarding image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AddBordingActivity.this, "Got the Boarding image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", BordingRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("bname", BordingTitle);
        productMap.put("bimage", downloadImageUrl);
        productMap.put("bdescription", BordingDescription);
        productMap.put("location", BordingLocation);
        productMap.put("contactno", BordingContactNo);
        productMap.put("bprice", BordingPrice);
        productMap.put("bedrooms", BedRooms);
        productMap.put("bathrooms", BathRooms);
        productMap.put("kitchens", Kitchens);

        BordingRef.child(BordingRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AddBordingActivity.this, MainActivity.class);//future addpackagesA  change to retrive avtivity
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddBordingActivity.this, "Boarding Ad is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddBordingActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

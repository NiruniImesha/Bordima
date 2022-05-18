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

public class AddServiceActivity extends AppCompatActivity {

    private String CategoryName, Description, ContactNo, ServiceName,Location,ServicePrice, saveCurrentDate, saveCurrentTime;
    private Button nextbtn;
    private ImageView InputServiceImage;
    private EditText InputServiceName, InputServiceDescription, InputContactNo, InputLocation, InputPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String ServiceRandomKey, downloadImageUrl;
    private StorageReference ServiceImagesRef;
    private DatabaseReference ServiceRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        CategoryName = getIntent().getExtras().get("category").toString();
        ServiceImagesRef = FirebaseStorage.getInstance().getReference().child("Service Images");
        ServiceRef = FirebaseDatabase.getInstance().getReference().child("Service");

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();

        nextbtn =(Button) findViewById(R.id.ServiceNext);
        InputServiceName = (EditText) findViewById(R.id.ServiceName);
        InputContactNo = (EditText) findViewById(R.id.ServiceContactNo);
        InputServiceImage= (ImageView) findViewById(R.id.ServiceImageBtn);
        InputServiceDescription = (EditText) findViewById(R.id.Servicedescription);
        InputLocation = (EditText) findViewById(R.id.ServiceLocation);
        InputPrice = (EditText) findViewById(R.id.Serviceprice);
        loadingBar = new ProgressDialog(this);

        InputServiceImage.setOnClickListener(view -> OpenGallery());


        nextbtn.setOnClickListener(view -> ValidateProductData());
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
            InputServiceImage.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData()
    {
        Description = InputServiceDescription.getText().toString();
        ServiceName = InputServiceName.getText().toString();
        ContactNo = InputContactNo.getText().toString();
        Location = InputLocation.getText().toString();
        ServicePrice = InputPrice.getText().toString();

        if (TextUtils.isEmpty(ServiceName))
        {
            Toast.makeText(this, "Please enter service name", Toast.LENGTH_SHORT).show();
        }
        else if (ImageUri == null)
        {
            Toast.makeText(this, "Service image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write service description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Location))
        {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ContactNo))
        {
            Toast.makeText(this, "Please enter contact number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ServicePrice))
        {
            Toast.makeText(this, "Please enter average price for the service", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreServiceInformation();
        }
    }
    private void StoreServiceInformation()
    {
        loadingBar.setTitle("Add New Service");
        loadingBar.setMessage("please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ServiceRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = ServiceImagesRef.child(ImageUri.getLastPathSegment() + ServiceRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);



        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddServiceActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddServiceActivity.this, "Service cover photo uploaded Successfully", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AddServiceActivity.this, "got the service image Url Successfully", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> serviceMap = new HashMap<>();
        serviceMap.put("sid", ServiceRandomKey);
        serviceMap.put("date", saveCurrentDate);
        serviceMap.put("time", saveCurrentTime);
        serviceMap.put("sname", ServiceName);
        serviceMap.put("category", CategoryName);
        serviceMap.put("simage", downloadImageUrl);
        serviceMap.put("sdescription", Description);
        serviceMap.put("location", Location);
        serviceMap.put("contactno", ContactNo);
        serviceMap.put("sprice", ServicePrice);

        ServiceRef.child(ServiceRandomKey).updateChildren(serviceMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AddServiceActivity.this, ServiceActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddServiceActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddServiceActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

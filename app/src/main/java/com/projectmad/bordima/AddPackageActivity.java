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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import android.os.Bundle;

public class AddPackageActivity extends AppCompatActivity {

    private String Price, Packagename,saveCurrentDate,saveCurrentTime;
    private Button PublishBtn;
    private ImageView InputPackageImage;
    private EditText InputPackageName,InputPackagePrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String packageRandomKey, downloadImageUrl;
    private StorageReference PackageImagesRef;
    private DatabaseReference PackageRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);

        PackageImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        PackageRef = FirebaseDatabase.getInstance().getReference().child("Products");

        PublishBtn = (Button) findViewById(R.id.ServicePublishBtn);
        InputPackageImage = (ImageView) findViewById(R.id.PackageImageBtn);
        InputPackageName = (EditText) findViewById(R.id.ServicePackageName);
        InputPackagePrice = (EditText) findViewById(R.id.ServicePrice);
        loadingBar = new ProgressDialog(this);


        InputPackageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        PublishBtn.setOnClickListener(new View.OnClickListener() {
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
            InputPackageImage.setImageURI(ImageUri);
        }
    }


    private void ValidateProductData()
    {
        Price = InputPackagePrice.getText().toString();
        Packagename = InputPackageName.getText().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write package Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Packagename))
        {
            Toast.makeText(this, "Please write package name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StorePackageInformation();
        }
    }



    private void StorePackageInformation()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
         saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        packageRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = PackageImagesRef.child(ImageUri.getLastPathSegment() + packageRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddPackageActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddPackageActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AddPackageActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

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
        productMap.put("pid", packageRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("image", downloadImageUrl);
        productMap.put("price", Price);
        productMap.put("pname", Packagename);

        PackageRef.child(packageRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AddPackageActivity.this, MainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddPackageActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddPackageActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

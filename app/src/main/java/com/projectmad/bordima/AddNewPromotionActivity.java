package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddNewPromotionActivity extends AppCompatActivity {

    private Button SubmitPromotionButton;
    private EditText InputPromotionName,InputPromotionLink;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_promotion);

        SubmitPromotionButton = findViewById(R.id.BtnPromotionSubmit);
        InputPromotionName = findViewById(R.id.inputPromotionName);
        InputPromotionLink = findViewById(R.id.inputPromotionLink);
        loadingBar = new ProgressDialog(this);

        SubmitPromotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPromotion();
            }
        });


    }

    private void createPromotion() {

        String name = InputPromotionName.getText().toString();
        String imgLink = InputPromotionLink.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter Promotion Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(imgLink)){
            Toast.makeText(this, "Please Enter Promotion Link", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Submit Promotion");
            loadingBar.setMessage("Please wait..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePromotionDetails(name,imgLink);
        }
    }

    private void ValidatePromotionDetails(String name, String imgLink) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Promotion").child(name).exists())){

                    HashMap<String ,Object> userdataMap = new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("imgLink",imgLink);

                    RootRef.child("Promotion").child(name).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddNewPromotionActivity.this, "Promotion added Successfully", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AddNewPromotionActivity.this,PromotionsActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        Toast.makeText(AddNewPromotionActivity.this, "Error..Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(AddNewPromotionActivity.this, "Promotion "+name+" is Already Exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AddNewPromotionActivity.this, "Try again with another promotion name", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddNewPromotionActivity.this,AddNewPromotionActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
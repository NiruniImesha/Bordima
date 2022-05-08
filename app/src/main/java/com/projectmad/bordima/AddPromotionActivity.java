package com.projectmad.bordima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPromotionActivity extends AppCompatActivity {

    private TextInputEditText promoIdEdt,promoNameEdt,promoImgLinkEdt,promoLinkEdt;
    private Button addPromoBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String promoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);

        promoIdEdt = findViewById(R.id.idEdtPromoId);
        promoNameEdt = findViewById(R.id.idEdtPromoName);
        promoImgLinkEdt = findViewById(R.id.idEdtPromoImgLink);
        promoLinkEdt = findViewById(R.id.idEdtPromoLink);
        addPromoBtn = findViewById(R.id.idBtnAddPromo);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Promotions");

        addPromoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String promoId = promoIdEdt.getText().toString();
                String promoName = promoNameEdt.getText().toString();
                String promoImgLink = promoImgLinkEdt.getText().toString();
                String promoLink = promoLinkEdt.getText().toString();
                PromotionRVModal promotionRVModal = new PromotionRVModal(promoId,promoName,promoImgLink,promoLink);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(promoId).setValue(promotionRVModal);
                        Toast.makeText(AddPromotionActivity.this, "Promotion Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPromotionActivity.this,PromotionsActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPromotionActivity.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
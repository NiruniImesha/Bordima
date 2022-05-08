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

import java.util.HashMap;
import java.util.Map;

public class EditPromotionActivity extends AppCompatActivity {

    private TextInputEditText promoIdEdt,promoNameEdt,promoImgLinkEdt,promoLinkEdt;
    private Button updatePromoBtn,deletePromoBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String promoId;
    private PromotionRVModal promotionRVModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion);
        firebaseDatabase = FirebaseDatabase.getInstance();
        promoIdEdt = findViewById(R.id.idEdtPromoId);
        promoNameEdt = findViewById(R.id.idEdtPromoName);
        promoImgLinkEdt = findViewById(R.id.idEdtPromoImgLink);
        promoLinkEdt = findViewById(R.id.idEdtPromoLink);
        updatePromoBtn = findViewById(R.id.idBtnUpdatePromo);
        deletePromoBtn = findViewById(R.id.idBtnDeletePromo);
        loadingPB = findViewById(R.id.idPBLoading);
        promotionRVModal =getIntent().getParcelableExtra("promotion");
        if(promotionRVModal!=null){
            promoNameEdt.setText(promotionRVModal.getPromotionName());
            promoImgLinkEdt.setText(promotionRVModal.getPromotionName());
            promoLinkEdt.setText(promotionRVModal.getPromotionName());
            promoId= promotionRVModal.getPromotionId();
        }


        databaseReference = firebaseDatabase.getReference("Promotions").child(promoId);
        updatePromoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String promoName = promoNameEdt.getText().toString();
                String promoImgLink = promoImgLinkEdt.getText().toString();
                String promoLink = promoLinkEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("promotionId",promoId);
                map.put("promotionName",promoName);
                map.put("promotionImgLink",promoImgLink);
                map.put("promotionLink",promoLink);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPromotionActivity.this, "Promotion Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPromotionActivity.this,PromotionsActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPromotionActivity.this, "Fail to Update Promotion..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deletePromoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePromotion();
            }
        });
    }
    private void deletePromotion(){
        databaseReference.removeValue();
        Toast.makeText(this, "Promotion Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPromotionActivity.this,PromotionsActivity.class));
    }
}
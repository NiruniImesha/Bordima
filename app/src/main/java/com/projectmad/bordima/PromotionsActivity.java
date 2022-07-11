package com.projectmad.bordima;

<<<<<<< Updated upstream
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PromotionsActivity extends AppCompatActivity {

    private RecyclerView promotionRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PromotionRVModal> promotionRVModalArrayList;
    private RelativeLayout promotionRL;
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class PromotionsActivity extends AppCompatActivity {

    //widgets
    RecyclerView recyclerView;

    //Firebase:
    private DatabaseReference myRef;

    //Variables:
    private ArrayList<Promotions> promotionsList;
    private RecyclerAdapter recyclerAdapter;
    private Context mContext;

    private Button LogoutBtn;
    private Button NewPromotionButton;


>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
<<<<<<< Updated upstream
    }
=======

        //logout admin
        LogoutBtn = (Button) findViewById(R.id.BtnLogoutAdmin);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Paper.book().destroy();

                Intent intent = new Intent(PromotionsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });  //end of logout

        //New Promotion
        NewPromotionButton = (Button) findViewById(R.id.BtnNewPromotion);

        NewPromotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PromotionsActivity.this,AddNewPromotionActivity.class);
                startActivity(intent);
            }
        });

        //end of new promotion

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //ArrayList
        promotionsList = new ArrayList<>();

        //clear Arraylist
        clearAll();

        //Get Data Method
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {
        Query query = myRef.child("Promotion");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Promotions promotions = new Promotions();

                    promotions.setImageUrl(snapshot.child("imgLink").getValue().toString());
                    promotions.setName(snapshot.child("name").getValue().toString());

                    promotionsList.add(promotions);
                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), promotionsList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearAll(){
        if(promotionsList !=null){
            promotionsList.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        promotionsList = new ArrayList<>();
    }

>>>>>>> Stashed changes
}
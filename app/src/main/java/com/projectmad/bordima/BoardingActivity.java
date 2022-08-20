package com.projectmad.bordima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

public class BoardingActivity extends AppCompatActivity {

    // widgets
    RecyclerView recyclerView;

    //firebase
    private DatabaseReference boardingRef;

    //Variable
    private ArrayList<Boarding> boardingArrayList;
    private BoardingAdapter boardingAdapter;
    private Context context;
    private Button AddButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boarding_display);

        recyclerView = findViewById(R.id.boarding_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        boardingRef = FirebaseDatabase.getInstance().getReference();

        //ArrayList
        boardingArrayList = new ArrayList<>();

        //Add button
        AddButton = (Button) findViewById(R.id.add);

        //clear ArrayList
        clearAll();

        //get data method
        GetDataFromFirebase();

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardingActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetDataFromFirebase() {

        Query query = boardingRef.child("Bording");

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Boarding boarding = new Boarding();

                    boarding.setBimage(snapshot.child("bimage").getValue().toString());
                    boarding.setBname(snapshot.child("bname").getValue().toString());
                    boarding.setLocation(snapshot.child("location").getValue().toString());
                    boarding.setBprice(snapshot.child("bprice").getValue().toString());

                    boardingArrayList.add(boarding);
                }

                boardingAdapter = new BoardingAdapter(getApplicationContext(),boardingArrayList);
                recyclerView.setAdapter(boardingAdapter);
                boardingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    @SuppressLint("NotifyDataSetChanged")
    private void clearAll(){
        if(boardingArrayList != null){
            boardingArrayList.clear();
            if (boardingAdapter !=null){
                boardingAdapter.notifyDataSetChanged();
            }
        }
        boardingArrayList = new ArrayList<>();
    }
}
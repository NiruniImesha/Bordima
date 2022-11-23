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
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.projectmad.bordima.Model.Service;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    // widgets
    RecyclerView recyclerView;

    //firebase
    private DatabaseReference serviceRef;

    //Variable
    private ArrayList<Service> serviceArrayList;
    private ServiceAdapter serviceAdapter;
    private Context context;
    private Button AddButton;
    private ImageButton arrowbtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_display);

        recyclerView = findViewById(R.id.service_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        serviceRef = FirebaseDatabase.getInstance().getReference();

        //ArrayList
        serviceArrayList = new ArrayList<>();

        //Add button
        AddButton = (Button) findViewById(R.id.add);
        arrowbtn = (ImageButton) findViewById(R.id.arrow);

        //clear ArrayList
        clearAll();

        //get data method
        GetDataFromFirebase();

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this,HomeActivity.class);
                intent.putExtra("category","Boarding");
                startActivity(intent);
            }
        });

        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetDataFromFirebase() {
        Query query = serviceRef.child("Service");

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Service service = new Service();

                    service.setSimage(snapshot.child("simage").getValue().toString());
                    service.setSname(snapshot.child("sname").getValue().toString());
                    service.setLocation(snapshot.child("location").getValue().toString());
                    service.setPrice(snapshot.child("sprice").getValue().toString());

                    serviceArrayList.add(service);
                }

                serviceAdapter = new ServiceAdapter(getApplicationContext(),serviceArrayList);
                recyclerView.setAdapter(serviceAdapter);
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void clearAll() {
        if (serviceArrayList != null) {
            serviceArrayList.clear();
            if (serviceAdapter != null) {
                serviceAdapter.notifyDataSetChanged();
            }
        }
        serviceArrayList = new ArrayList<>();
    }
}
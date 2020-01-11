package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalAndBloodBank extends AppCompatActivity {


    //varriable declaration
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // attaching listener to activities
        setContentView(R.layout.activity_hospital_and_blood_bank);

        Button settingsButton = (Button) findViewById(R.id.feed_button_settings);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalAndBloodBank.this, Settings.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.feed_button_profile);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalAndBloodBank.this, Profile.class);
                startActivity(intent);
            }
        });

        Button notificationButton = (Button) findViewById(R.id.feed_button_notification);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalAndBloodBank.this, Notifications.class);
                startActivity(intent);
            }
        });

        Button newsfeedButton = (Button) findViewById(R.id.feed_button_feed);

        newsfeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalAndBloodBank.this, NewsFeed.class);
                startActivity(intent);
            }
        });

        //fetching info from firebase and initiating  RecyclerView

        FirebaseDatabase.getInstance().getReference("bloodBank/bloodBanks/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names.clear();
                locations.clear();
                numbers.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Hnb hnb = child.getValue(Hnb.class);
                    names.add(hnb.name);
                    locations.add(hnb.location);
                    numbers.add(hnb.number);
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //initiating recycler view
    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewhobb);
        RecyclerViewAdapterHospitalAndBloodBank adapter = new RecyclerViewAdapterHospitalAndBloodBank(this, names, locations, numbers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

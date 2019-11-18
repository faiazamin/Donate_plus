package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PeopleWhoResponded extends AppCompatActivity {


    private ArrayList<String> peopleWhoResponded = new ArrayList<>();
    private ArrayList<String> phoneNumber = new ArrayList<String>();
    private ArrayList<String> uid = new ArrayList<String>();
    private ArrayList<String> isEnlisted = new ArrayList<String>();
    private ArrayList<String> isCalled = new ArrayList<String>();
    private ArrayList<String> postIds = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_who_responded);

        String postId = getIntent().getStringExtra("postId");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                peopleWhoResponded.clear();
                phoneNumber.clear();
                uid.clear();
                isCalled.clear();
                isEnlisted.clear();
                postIds.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    NotiBlock notiBlock = child.getValue(NotiBlock.class);
                    peopleWhoResponded.add(notiBlock.name);
                    phoneNumber.add(notiBlock.cellNo);
                    uid.add(notiBlock.uid);
                    isCalled.add(notiBlock.isCalled);
                    isEnlisted.add(notiBlock.isEnlisted);
                    postIds.add(notiBlock.postId);
                }
                Collections.reverse(peopleWhoResponded);
                Collections.reverse(phoneNumber);
                Collections.reverse(uid);
                Collections.reverse(postIds);
                Collections.reverse(isCalled);
                Collections.reverse(isEnlisted);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        FirebaseDatabase.getInstance().getReference("post/response/" + postId).addValueEventListener(valueEventListener);

    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewpeoplewhoresponded);
        RecyclerViewAdapterPeopleWhoResponded adapter = new RecyclerViewAdapterPeopleWhoResponded(this, peopleWhoResponded, phoneNumber, uid, isCalled, isEnlisted, postIds);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}



package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class EnlistedPeople extends AppCompatActivity {

    public String postId;
    public ArrayList<String> name = new ArrayList<>();
    public ArrayList<String> uid = new ArrayList<>();
    public ArrayList<String> number = new ArrayList<>();
    public ArrayList<String> postIds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlisted_people);
        postId = getIntent().getStringExtra("postId");

        FirebaseDatabase.getInstance().getReference("post/list/" + postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.clear();
                uid.clear();
                number.clear();
                postIds.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    NotiBlock notiBlock = child.getValue(NotiBlock.class);
                    name.add(notiBlock.name);
                    uid.add(notiBlock.uid);
                    number.add(notiBlock.cellNo);
                    postIds.add(notiBlock.postId);
                }
                Collections.reverse(name);
                Collections.reverse(uid);
                Collections.reverse(number);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewenlistedpeople);
        RecyclerViewAdapterEnlistedPeople adapter = new RecyclerViewAdapterEnlistedPeople(this, name, uid, number, postIds);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

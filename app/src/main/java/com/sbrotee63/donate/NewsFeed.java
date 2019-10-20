package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity {

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        Button feedRequestButton = (Button) findViewById(R.id.feed_button_request);
        feedRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsFeed.this, PostNewEvent.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.feed_button_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsFeed.this, Profile.class);
                startActivity(intent);
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bloodGroups.clear();
                locations.clear();
                for( DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    Post post = postSnapShot.getValue(Post.class);
                    bloodGroups.add(post.bloodGroup);
                    locations.add(post.location);
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("newTag", "loadPost:onCancelled", databaseError.toException());

            }
        };
        database.getReference("post/posts").addValueEventListener(postListener);
    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, bloodGroups, locations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

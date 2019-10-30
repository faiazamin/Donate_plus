package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NewsFeed extends AppCompatActivity {

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> postId = new ArrayList<>();

    static public User user;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onStart() {
        super.onStart();

        /**/
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private ArrayList<String> statuses = new ArrayList<>();

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


            Button settingsButton = (Button) findViewById(R.id.feed_button_settings);


        settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsFeed.this, Settings.class);
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

            Button notificationButton = (Button) findViewById(R.id.feed_button_notification);

        notificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsFeed.this, Notifications.class);
                    startActivity(intent);
                }
            });

            Button bloodBankButton = (Button) findViewById(R.id.feed_button_hospitals);

        bloodBankButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsFeed.this, HospitalAndBloodBank.class);
                    startActivity(intent);
                }
            });



            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bloodGroups.clear();
                    locations.clear();
                    statuses.clear();
                    for( DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                        Post post = postSnapShot.getValue(Post.class);
                        bloodGroups.add(post.bloodGroup);
                        locations.add(post.location);
                        postId.add(post.postId);
                        if(post.response.equals("0")){
                            statuses.add("Urgent");
                        }
                        else{
                            statuses.add("");
                        }
                    }
                    Collections.reverse(bloodGroups);
                    Collections.reverse(postId);
                    Collections.reverse(locations);
                    Collections.reverse(statuses);
                    initRecyclerView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("newTag", "loadPost:onCancelled", databaseError.toException());
                }
            };
        database.getReference("post/posts").addValueEventListener(postListener);
        bloodGroups.add("O+");
        locations.add("Sylhet");
        statuses.add("Urgent");

            initRecyclerView();
        }

        private void initRecyclerView()
        {
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, bloodGroups, locations, statuses, postId);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }
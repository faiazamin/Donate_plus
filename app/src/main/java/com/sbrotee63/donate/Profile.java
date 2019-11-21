package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        String uid = getIntent().getStringExtra("uid");
        if(uid == null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        else{
            findViewById(R.id.profile_button_seeposts).setVisibility(View.INVISIBLE);
            findViewById(R.id.profile_button_seeresponses).setVisibility(View.INVISIBLE);
        }
        //Toast.makeText(Profile.this, uid, Toast.LENGTH_SHORT).show();

        FirebaseDatabase.getInstance().getReference("post/responseCount/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(Long.class).toString();
                if(temp == null) temp = "0";
                ((TextInputEditText)findViewById(R.id.profile_totalresponses)).setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button settingsButton = (Button) findViewById(R.id.feed_button_settings);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Settings.class);
                startActivity(intent);
            }
        });

        Button newsfeedButton = (Button) findViewById(R.id.feed_button_feed);

        newsfeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, NewsFeed.class);
                startActivity(intent);
            }
        });

        Button notificationButton = (Button) findViewById(R.id.feed_button_notification);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Notifications.class);
                startActivity(intent);
            }
        });

        Button bloodBankButton = (Button) findViewById(R.id.feed_button_hospitals);

        bloodBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, HospitalAndBloodBank.class);
                startActivity(intent);
            }
        });



        ValueEventListener postListener = new ValueEventListener() {
            @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    setup(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("newTag", "Error", databaseError.toException());
                }
            };
        FirebaseDatabase.getInstance().getReference("user/info/" + uid).addValueEventListener(postListener);

        findViewById(R.id.feed_button_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, NewsFeed.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.profile_button_seeposts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MyPosts.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.profile_button_seeresponses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MyResponses.class);
                startActivity(intent);
            }
        });
    }

    void setup(User user){
        ((TextInputEditText) findViewById(R.id.profile_name)).setText(user.name);
        ((TextInputEditText) findViewById(R.id.profile_email)).setText(user.email);
        Toast.makeText(Profile.this,user.email,Toast.LENGTH_SHORT).show();
        ((TextInputEditText) findViewById(R.id.profile_bloodgroup)).setText(user.bloodGroup);
        ((TextInputEditText) findViewById(R.id.profile_dateofbirth)).setText(user.dateOfBirth);
        ((TextInputEditText) findViewById(R.id.profile_address)).setText(user.address);
        ((TextInputEditText) findViewById(R.id.profile_cellno)).setText(user.cellNo);
        ((TextInputEditText) findViewById(R.id.profile_lastblooddonation)).setText(user.lastBloodDonation);
    }


}

package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

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
        database.getReference("user/info/" + currentUser.getUid()).addValueEventListener(postListener);

        findViewById(R.id.profile_button_seeposts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MyPosts.class);
                startActivity(intent);
            }
        });
    }

    void setup(User user){
        ((Button) findViewById(R.id.profile_name)).setText(user.name);
        ((Button) findViewById(R.id.profile_email)).setText(user.email);
        ((Button) findViewById(R.id.profile_bloodgroup)).setText(user.bloodGroup);
        ((Button) findViewById(R.id.profile_dateofbirth)).setText(user.dateOfBirth);
        ((Button) findViewById(R.id.profile_address)).setText(user.address);
        ((Button) findViewById(R.id.profile_cellno)).setText(user.cellNo);
        ((Button) findViewById(R.id.profile_lastblooddonation)).setText(user.lastBloodDonation);
    }
}

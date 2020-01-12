package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        Button newsfeedButton = (Button) findViewById(R.id.feed_button_feed);

        newsfeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, NewsFeed.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.feed_button_profile);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Profile.class);
                startActivity(intent);
            }
        });

        Button notificationButton = (Button) findViewById(R.id.feed_button_notification);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Notifications.class);
                startActivity(intent);
            }
        });

        Button bloodBankButton = (Button) findViewById(R.id.feed_button_hospitals);

        bloodBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, HospitalAndBloodBank.class);
                startActivity(intent);
            }
        });

        Button ChangeProfileButton = (Button) findViewById(R.id.setting_button_change_profile);

        ChangeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ChangeProfile.class);
                startActivity(intent);
            }
        });

        Button logoutButton = (Button) findViewById(R.id.setting_button_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Settings.this, Welcome.class);
                startActivity(intent);
                Toast.makeText(Settings.this, "Logged out", Toast.LENGTH_LONG).show();
            }
        });

    }
}

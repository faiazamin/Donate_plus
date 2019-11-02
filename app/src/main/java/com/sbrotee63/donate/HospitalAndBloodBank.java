package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HospitalAndBloodBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}

package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity {

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> statuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        findViewById(R.id.feed_button_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsFeed.this, PostNewEvent.class);
                startActivity(intent);
            }
        });

        bloodGroups.add("A+");
        locations.add("Dhaka");
        statuses.add("Urgent");

        bloodGroups.add("A+");
        locations.add("Dhaka");
        statuses.add("Urgent");

        bloodGroups.add("A+");
        locations.add("Dhaka");
        statuses.add("Urgent");

        bloodGroups.add("A+");
        locations.add("Dhaka");
        statuses.add("Urgent");

        bloodGroups.add("A+");
        locations.add("Dhaka");
        statuses.add("Urgent");

        bloodGroups.add("O+");
        locations.add("Sylhet");
        statuses.add("Urgent");

        initRecyclerView();
    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, bloodGroups, locations, statuses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

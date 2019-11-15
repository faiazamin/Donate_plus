package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyPosts extends AppCompatActivity {

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> postId = new ArrayList<>();
    private ArrayList<String> statuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);



        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodGroups.clear();
                locations.clear();
                statuses.clear();
                postId.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Post post = child.getValue(Post.class);
                    bloodGroups.add(post.bloodGroup);
                    locations.add(post.location);
                    statuses.add("");
                    postId.add(post.postId);
                }
                Collections.reverse(bloodGroups);
                Collections.reverse(locations);
                Collections.reverse(statuses);
                Collections.reverse(postId);
                initRecyclerView();
                Toast.makeText(MyPosts.this, "GOT", Toast.LENGTH_SHORT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("newTag", "loadPost:onCancelled", databaseError.toException());
            }
        };
        FirebaseDatabase.getInstance().getReference("post/userPosts/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(valueEventListener);

    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewmyposts);
        RecyclerViewAdapterMyPosts adapter = new RecyclerViewAdapterMyPosts(this, bloodGroups, locations, statuses, postId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

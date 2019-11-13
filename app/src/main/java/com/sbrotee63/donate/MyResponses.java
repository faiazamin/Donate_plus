package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyResponses extends AppCompatActivity {

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> postId = new ArrayList<>();

    static public User user;


    @Override
    protected void onStart() {
        super.onStart();
    }
    private ArrayList<String> statuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_responses);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bloodGroups.clear();
                locations.clear();
                statuses.clear();
                postId.clear();
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
        FirebaseDatabase.getInstance().getReference("post/userResponse/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(postListener);
    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewmyresponses);
        RecyclerViewAdapterMyResponses adapter = new RecyclerViewAdapterMyResponses(this, bloodGroups, locations, statuses, postId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}



/*package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyResponses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_responses);

        String uid = "currently no";    // TODtake input from intent;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Post post = child.getValue(Post.class);
                    // TODO: PRINT IT ON THE PAGE
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


        };
        FirebaseDatabase.getInstance().getReference("post/userResposne/" + uid).addValueEventListener(valueEventListener);

    }
}
*/


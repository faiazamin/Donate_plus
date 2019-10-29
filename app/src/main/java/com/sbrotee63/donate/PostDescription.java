package com.sbrotee63.donate;

import androidx.annotation.NonNull;
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

public class PostDescription extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);
        Intent intent = getIntent();
        Button acceptButton = (Button) findViewById(R.id.postdesc_button_accept);
        final String postId = intent.getStringExtra("postId");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("post/response/" + postId).push().setValue(user.getUid());
            }
        });;


        Log.d("newTag", postId + "HERE I AM BRO");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                ((Button)findViewById(R.id.postdes_bloodgroup)).setText(post.bloodGroup);
                ((Button)findViewById(R.id.postdes_name)).setText(post.name);
                ((Button)findViewById(R.id.postdes_location)).setText(post.location);
                ((Button)findViewById(R.id.postdes_dateofbirth)).setText(post.dateOfBirth);
                ((Button)findViewById(R.id.postdes_cellno)).setText(post.cellno);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        database.getReference("post/posts/" + postId).addListenerForSingleValueEvent(valueEventListener);

    }
}

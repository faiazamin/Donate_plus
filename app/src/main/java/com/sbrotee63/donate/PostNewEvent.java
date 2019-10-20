package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PostNewEvent extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_event);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        ((Button)findViewById(R.id.post_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post( ((EditText)findViewById(R.id.post_name)).getText().toString(),
                        ((EditText)findViewById(R.id.post_bloodgroup)).getText().toString(),
                        ((EditText)findViewById(R.id.post_location)).getText().toString(),
                        ((EditText)findViewById(R.id.post_dateofbirth)).getText().toString(),
                        ((EditText)findViewById(R.id.post_cellno)).getText().toString(),
                        currentUser.getUid());
                if(post.isEmpty()){
                    Toast.makeText(PostNewEvent.this, "Please fill all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                database.getReference("post/posts").push().setValue(post);
                Toast.makeText(PostNewEvent.this, "Request posted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PostNewEvent.this, NewsFeed.class);
                startActivity(intent);
            }
        });

    }
}

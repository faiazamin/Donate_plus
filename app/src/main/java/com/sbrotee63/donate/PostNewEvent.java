package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostNewEvent extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Post post;


    DatePickerDialog datePickerDialog;
    DatePicker datePicker;

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
                post = new Post( ((EditText)findViewById(R.id.post_name)).getText().toString(),
                        ((EditText)findViewById(R.id.post_bloodgroup)).getText().toString(),
                        ((EditText)findViewById(R.id.post_location)).getText().toString(),
                        ((EditText)findViewById(R.id.post_dateofrequirement)).getText().toString(),
                        ((EditText)findViewById(R.id.post_cellno)).getText().toString(),
                        currentUser.getUid());
                if(post.isEmpty()){
                    Toast.makeText(PostNewEvent.this, "Please fill all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Integer[] currentPost = {0};
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentPost[0] = dataSnapshot.getValue(Integer.class);
                        database.getReference("post/currentPost").setValue(currentPost[0] + 1);
                        lastWork(currentPost[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                database.getReference("post/currentPost").addListenerForSingleValueEvent(valueEventListener);
            }
        });

        findViewById(R.id.post_datepicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePicker(PostNewEvent.this);
                int currentYear = datePicker.getYear();
                int currentMonth = (datePicker.getMonth())+1;
                int currentDay = datePicker.getDayOfMonth();

                datePickerDialog = new DatePickerDialog(PostNewEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((EditText)findViewById(R.id.post_dateofrequirement)).setText(dayOfMonth+"-"+month+"-"+year);
                    }
                },currentYear, currentMonth, currentDay);
                datePickerDialog.show();

            }
        });


    }

    public  void lastWork(Integer cur){
        post.postId = Integer.toString(cur);
        database.getReference("post/posts/" + post.postId).setValue(post);
        database.getReference("notification/" + post.bloodGroup).push().setValue("0+"  + post.postId);
        Toast.makeText(PostNewEvent.this, "Request posted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PostNewEvent.this, NewsFeed.class);
        startActivity(intent);
    }


}

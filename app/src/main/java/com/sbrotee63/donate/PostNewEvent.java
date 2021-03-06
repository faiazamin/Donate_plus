package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostNewEvent extends AppCompatActivity {



    Post post;


    DatePickerDialog datePickerDialog;
    DatePicker datePicker;


    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_event);

        location = getIntent().getStringExtra("location");

        Spinner mySpinner = (Spinner)findViewById(R.id.post_bloodgroup);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(PostNewEvent.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bloodGroups));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);



        if(location != null){
            com.google.android.material.textfield.TextInputEditText editText = findViewById(R.id.post_location);
            editText.setText(location);
        }



        ((Button)findViewById(R.id.post_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post = new Post( ((EditText)findViewById(R.id.post_name)).getText().toString(),
                        ((Spinner)findViewById(R.id.post_bloodgroup)).getSelectedItem().toString(),
                        ((EditText)findViewById(R.id.post_location)).getText().toString(),
                        ((EditText)findViewById(R.id.post_dateofrequirement)).getText().toString(),
                        ((EditText)findViewById(R.id.post_cellno)).getText().toString(),
                       FirebaseAuth.getInstance().getCurrentUser().getUid());
                if(post.isEmpty()){
                    Toast.makeText(PostNewEvent.this, "Please fill all the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Integer[] currentPost = {0};
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentPost[0] = dataSnapshot.getValue(Integer.class);
                        FirebaseDatabase.getInstance().getReference("post/currentPost").setValue(currentPost[0] + 1);
                        lastWork(currentPost[0], post);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                FirebaseDatabase.getInstance().getReference("post/currentPost").addListenerForSingleValueEvent(valueEventListener);
            }
        });

        findViewById(R.id.post_button_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostNewEvent.this, ShowPostLocation.class);
                intent.putExtra("location", "Hello");
                intent.putExtra("flag", "pick");
                startActivity(intent);
            }
        });

        findViewById(R.id.post_datepicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePicker(PostNewEvent.this);
                int currentYear = datePicker.getYear();
                int currentMonth = (datePicker.getMonth());
                int currentDay = datePicker.getDayOfMonth();

                datePickerDialog = new DatePickerDialog(PostNewEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((EditText)findViewById(R.id.post_dateofrequirement)).setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },currentYear, currentMonth, currentDay);
                datePickerDialog.show();

            }
        });



    }

    public  void lastWork(Integer cur, Post post){
        post.postId = Integer.toString(cur);
        FirebaseDatabase.getInstance().getReference("post/posts/" + post.postId).setValue(post);
        FirebaseDatabase.getInstance().getReference("post/userPosts/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/"+ post.postId).setValue(post);
        FirebaseDatabase.getInstance().getReference("notification/" + post.bloodGroup).push().setValue("0+"  + post.postId);
        Toast.makeText(PostNewEvent.this, "Request posted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PostNewEvent.this, NewsFeed.class);
        startActivity(intent);
    }


}

package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDescription extends AppCompatActivity {

    Post post;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);
        Intent intent = getIntent();
        Button acceptButton = (Button) findViewById(R.id.postdesc_button_accept);
        final String postId = intent.getStringExtra("postId");

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseDatabase.getInstance().getReference("post/response/" + postId).push().setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                //FirebaseDatabase.getInstance().getReference("post/response/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(postId);
                //Toast.makeText(PostDescription.this, "Response noted", Toast.LENGTH_SHORT).show();
                if( !((Button) findViewById(R.id.postdesc_button_accept)).getText().equals("Accept") ){
                    Intent intent = new Intent(PostDescription.this, PeopleWhoResponded.class);
                    intent.putExtra("postId", post.postId);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(PostDescription.this, "Accepted", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("post/response/" + postId + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new NotiBlock(user.name, user.cellNo, FirebaseAuth.getInstance().getCurrentUser().getUid(), postId));
                FirebaseDatabase.getInstance().getReference("post/userResponse/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(post);
            }
        });


        findViewById(R.id.postdesc_button_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDescription.this, ShowPostLocation.class);
                intent.putExtra("location", ((com.google.android.material.textfield.TextInputEditText)findViewById(R.id.postdes_location)).getText().toString());
                intent.putExtra("flag", "post");
                startActivity(intent);
            }
        });

        findViewById(R.id.postdes_location).setFocusable(false);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                if(post == null){
                    Toast.makeText(PostDescription.this, "Sorry. Post has been deleted.", Toast.LENGTH_SHORT).show();
                    return;
                }
                put(post);
                if(post.seekerId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    ((Button)findViewById(R.id.postdesc_button_accept)).setText("Responses");
                }
                ((TextInputEditText)findViewById(R.id.postdes_bloodgroup)).setText(post.bloodGroup);
                ((TextInputEditText)findViewById(R.id.postdes_name)).setText(post.name);
                ((TextInputEditText)findViewById(R.id.postdes_location)).setText(post.location);
                ((TextInputEditText)findViewById(R.id.postdes_dateofbirth)).setText(post.dateOfBirth);
                ((TextInputEditText)findViewById(R.id.postdes_cellno)).setText(post.cellno);
                //Toast.makeText(PostDescription.this, post.seekerId + " " + FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                if(!post.seekerId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    ((MaterialButton)findViewById(R.id.postdesc_button_notify)).setVisibility(View.INVISIBLE);
                    ((MaterialButton)findViewById(R.id.postdesc_button_delete)).setVisibility(View.INVISIBLE);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        FirebaseDatabase.getInstance().getReference("post/posts/" + postId).addListenerForSingleValueEvent(valueEventListener);

        View.OnClickListener onClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(PostDescription.this)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want to delete this?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("post/posts/" + post.postId).setValue(null);
                        FirebaseDatabase.getInstance().getReference("post/userPosts/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/"+ post.postId).setValue(post);
                        onBackPressed();
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        };
        ((MaterialButton)findViewById(R.id.postdesc_button_delete)).setOnClickListener(onClickListener1);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("notification/" + post.bloodGroup).push().setValue("0+"  + post.postId);
            }
        };
        ((MaterialButton)findViewById(R.id.postdesc_button_notify)).setOnClickListener(onClickListener);



        ValueEventListener valueEventListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        FirebaseDatabase.getInstance().getReference("user/info/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(valueEventListener1);

    }
    void put(Post post){
        this.post = post;
    }
}

package com.sbrotee63.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.welcome_button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, SignUp.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.welcome_button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, LogIn.class);
                startActivity(intent);
            }
        });
    }

    public void onStart()
    {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        Log.d("newTag", currentUser.getEmail().toString());
    }
}

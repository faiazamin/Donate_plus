package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText emailF = (EditText) findViewById(R.id.login_text_email);
        final EditText passwordF = (EditText) findViewById(R.id.login_password_password);

        findViewById(R.id.login_button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(emailF.getText().toString().trim(), passwordF.getText().toString().trim());

            }
        });
    }

    private void logIn(String email, String password)
    {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                        Toast.makeText(LogIn.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LogIn.this, "Log In Successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogIn.this, NewsFeed.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(LogIn.this, "Log In Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(LogIn.this, NewsFeed.class);
            startActivity(intent);
        }
    }
}

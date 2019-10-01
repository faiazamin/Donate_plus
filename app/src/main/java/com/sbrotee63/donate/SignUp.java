package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUp extends AppCompatActivity {

    String name;
    String email;
    String password;
    String confirmPassword;
    String bloodGroup;
    String dateOfBirth;
    String address;
    String cellNumber;
    String lastBloodDonation;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText nameF = (EditText) findViewById(R.id.signup_text_name);
        final EditText emailF = (EditText) findViewById(R.id.signup_text_email);
        final EditText passwordF = (EditText) findViewById(R.id.signup_password_password);
        EditText confirmPasswordF = (EditText) findViewById(R.id.signup_password_confirmpassword);
        EditText bloodGroupF = (EditText) findViewById(R.id.signup_text_bloodgroup);
        EditText dateOfBirthF = (EditText) findViewById(R.id.signup_text_dateofbirth);
        EditText addressF = (EditText) findViewById(R.id.signup_text_address);
        EditText cellNumberF = (EditText) findViewById(R.id.singup_text_cellnumber);
        EditText lastBloodDonationF = (EditText) findViewById(R.id.signup_text_lastblooddonation);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signup_button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(emailF.getText().toString().trim(), passwordF.getText().toString().trim());
            }
        });

        /* please */

    }

    private void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener <AuthResult>()
        {
            public void onComplete(@NonNull Task <AuthResult> task)
            {
                if(task.isSuccessful()){
                    Log.d("DONATE+", "createUserWithEmail: successful");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(SignUp.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.w("DONATE+", "createUserWithEmail: failure", task.getException());
                    Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
     */

}

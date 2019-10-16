package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    EditText nameF = (EditText) findViewById(R.id.signup_text_name);
    EditText emailF = (EditText) findViewById(R.id.signup_text_email);
    EditText passwordF = (EditText) findViewById(R.id.signup_password_password);
    EditText confirmPasswordF = (EditText) findViewById(R.id.signup_password_confirmpassword);
    EditText bloodGroupF = (EditText) findViewById(R.id.signup_text_bloodgroup);
    EditText dateOfBirthF = (EditText) findViewById(R.id.signup_text_dateofbirth);
    EditText addressF = (EditText) findViewById(R.id.signup_text_address);
    EditText cellNumberF = (EditText) findViewById(R.id.singup_text_cellnumber);
    EditText lastBloodDonationF = (EditText) findViewById(R.id.signup_text_lastblooddonation);

    private FirebaseAuth mAuth;

    private Button signUpButton = (Button) findViewById(R.id.signup_button_signup);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameF = (EditText) findViewById(R.id.signup_text_name);
        emailF = (EditText) findViewById(R.id.signup_text_email);
        passwordF = (EditText) findViewById(R.id.signup_password_password);
        confirmPasswordF = (EditText) findViewById(R.id.signup_password_confirmpassword);
        bloodGroupF = (EditText) findViewById(R.id.signup_text_bloodgroup);
        dateOfBirthF = (EditText) findViewById(R.id.signup_text_dateofbirth);
        addressF = (EditText) findViewById(R.id.signup_text_address);
        cellNumberF = (EditText) findViewById(R.id.singup_text_cellnumber);
        lastBloodDonationF = (EditText) findViewById(R.id.signup_text_lastblooddonation);

        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(emailF.getText().toString().trim(), passwordF.getText().toString().trim());
            }
        });
    }

    private void signUp(String email, String password){
        if(!allFilledAndValidated()) return;
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener <AuthResult>()
        {
            public void onComplete(@NonNull Task <AuthResult> task)
            {
                if(task.isSuccessful()){
                    Log.d("DONATE+", "createUserWithEmail: successful");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(SignUp.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);

                }
                else{
                    Log.w("DONATE+", "createUserWithEmail: failure", task.getException());
                    Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean allFilledAndValidated(){
        name = nameF.getText().toString().trim();
        email = emailF.getText().toString().trim();
        password = emailF.getText().toString().trim();
        confirmPassword = confirmPasswordF.getText().toString().trim();
        bloodGroup = bloodGroupF.getText().toString().trim();
        dateOfBirth = dateOfBirthF.getText().toString().trim();
        address = addressF.getText().toString().trim();
        cellNumber = cellNumberF.getText().toString().trim();
        lastBloodDonation = lastBloodDonationF.getText().toString().trim();
        if(name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("") || bloodGroup.equals("") || dateOfBirth.equals("") || address.equals("") || cellNumber.equals("") || lastBloodDonation.equals("")){
            Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT);
            return false;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
            return false;
        }
        switch (bloodGroup){}
        return true;
    }

    /*
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
     */

}

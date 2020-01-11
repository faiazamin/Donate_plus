package com.sbrotee63.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    //declare variables
    String name;
    String email;
    String password;
    String confirmPassword;
    String bloodGroup;
    String dateOfBirth;
    String address;
    String cellNumber;
    String lastBloodDonation;


    //variables for datepicker dialogue
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        findViewById(R.id.signup_button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            // click this to sign up
            public void onClick(View view) {
                signUp();
            }
        });

        // code for dte picker dialogue
        findViewById(R.id.signup_button_dateofbirth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePicker(SignUp.this);
                int currentYear = datePicker.getYear();
                int currentMonth = datePicker.getMonth()+1;
                int currentDay = datePicker.getDayOfMonth();

                datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((EditText)findViewById(R.id.signup_text_dateofbirth)).setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },currentYear, currentMonth, currentDay);
                datePickerDialog.show();

            }
        });



        findViewById(R.id.signup_button_datepicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePicker(SignUp.this);
                int currentYear = datePicker.getYear();
                int currentMonth = datePicker.getMonth()+1;
                int currentDay = datePicker.getDayOfMonth();

                datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((com.google.android.material.textfield.TextInputEditText)findViewById(R.id.signup_text_lastblooddonation)).setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },currentYear, currentMonth, currentDay);
                datePickerDialog.show();

            }
        });
        //code for drop down list
        Spinner mySpinner = (Spinner)findViewById(R.id.signup_text_bloodgroup);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bloodGroups));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

    }

    private void signUp(){
        // checks if all fields are filled
        if(!allFieldChecked()) return;
        String email = ((EditText)findViewById(R.id.signup_text_email)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.signup_password_password)).getText().toString().trim();
       FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener <AuthResult>()
        {
            public void onComplete(@NonNull Task <AuthResult> task)
            {
                if(task.isSuccessful()){
                    // send verification message to email
                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Email has been sent to this address. Please verify your email", Toast.LENGTH_LONG).show();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();
                                User userObj = new User(name, ((EditText)findViewById(R.id.signup_text_email)).getText().toString().trim(), bloodGroup, dateOfBirth, address, cellNumber, lastBloodDonation, FirebaseAuth.getInstance().getCurrentUser().getUid());
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                database.getReference("user/info/" + uid).setValue( userObj );
                                Intent intent = new Intent(SignUp.this, LogIn.class);
                                startActivity(intent);
                                clear();
                            }
                            else{
                                Toast.makeText(SignUp.this, "Error : Please try again", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
                else{
                    Log.w("DONATE+", "createUserWithEmail: failure", task.getException());
                    Toast.makeText(SignUp.this, "Sign up failed. Please check all information.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //checks if any fiels is empty
    private Boolean allFieldChecked(){
        name = ((EditText)findViewById(R.id.signup_text_name)).getText().toString().trim();
        email = ((EditText)findViewById(R.id.signup_text_email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.signup_password_password)).getText().toString().trim();
        confirmPassword = ((EditText)findViewById(R.id.signup_password_confirmpassword)).getText().toString().trim();
        bloodGroup = ((Spinner)findViewById(R.id.signup_text_bloodgroup)).getSelectedItem().toString().trim();
        dateOfBirth = ((EditText)findViewById(R.id.signup_text_dateofbirth)).getText().toString().trim();
        address = ((EditText)findViewById(R.id.signup_text_address)).getText().toString().trim();
        cellNumber = ((EditText)findViewById(R.id.singup_text_cellnumber)).getText().toString().trim();
        lastBloodDonation = ((EditText)findViewById(R.id.signup_text_lastblooddonation)).getText().toString().trim();

        if(name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("") || bloodGroup.equals("") || dateOfBirth.equals("") || address.equals("") || cellNumber.equals("") || lastBloodDonation.equals("")){
            Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!confirmPassword.equals(password)){
            Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(SignUp.this, NewsFeed.class);
            startActivity(intent);
        }
    }

    void clear(){

        //clear all fields
        ((EditText)findViewById(R.id.signup_text_name)).setText("");
        ((EditText)findViewById(R.id.signup_text_email)).setText("");
        ((EditText)findViewById(R.id.signup_password_password)).setText("");
        ((EditText)findViewById(R.id.signup_password_confirmpassword)).setText("");
        /*((Spinner)findViewById(R.id.signup_text_bloodgroup)).setText("");*/
        ((EditText)findViewById(R.id.signup_text_dateofbirth)).setText("");
        ((EditText)findViewById(R.id.signup_text_address)).setText("");
        ((EditText)findViewById(R.id.singup_text_cellnumber)).setText("");
        ((EditText)findViewById(R.id.signup_text_lastblooddonation)).setText("");
    }

}
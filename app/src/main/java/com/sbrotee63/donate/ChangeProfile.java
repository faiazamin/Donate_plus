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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeProfile extends AppCompatActivity {


    //varriables
    String name;
    String email;
    String password;
    String confirmPassword;
    String currentPassword;
    String bloodGroup;
    String dateOfBirth;
    String address;
    String cellNumber;
    String lastBloodDonation;


    EditText editPass;
    EditText editConPass;
    EditText editCurPass;


    DatePickerDialog datePickerDialog;
    DatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                setup(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("user/info/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(postListener);
        ;




        //varriable initiation and manipulation

        editPass =      (EditText) findViewById(R.id.changeprofile_password_password);
        password = editPass.getText().toString();

        editConPass =       (EditText) findViewById(R.id.changeprofile_password_confirmpassword);
        confirmPassword = editConPass.getText().toString();

        editCurPass =       (EditText) findViewById(R.id.changeprofile_password_currentpassword);
        currentPassword = editCurPass.getText().toString();

        findViewById(R.id.changeprofile_text_email).setFocusable(false);


        //by clicking this button, user updates his profile
        findViewById(R.id.changeprofile_button_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(allFieldChecked()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    User newUser = new User(name, email, bloodGroup, dateOfBirth, address, cellNumber, lastBloodDonation);
                    FirebaseDatabase.getInstance().getReference("user/info/" + user.getUid()).setValue(newUser);

                    if(currentPassword != null){
                        Log.w("newtag223","Change password.");
                        setPassword();
                    }

                    Intent intent = new Intent(ChangeProfile.this, Profile.class);
                    startActivity(intent);

                }
                else{
                    return;
                }



            }
        });;
        //code for datpicker dialogue
        findViewById(R.id.changeprofile_button_dateofbirth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePicker(ChangeProfile.this);
                int currentYear = datePicker.getYear();
                int currentMonth = datePicker.getMonth()+1;
                int currentDay = datePicker.getDayOfMonth();

                datePickerDialog = new DatePickerDialog(ChangeProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((EditText)findViewById(R.id.post_dateofrequirement)).setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },currentYear, currentMonth, currentDay);
                datePickerDialog.show();

            }
        });

        //code for datpicker dialogue

        findViewById(R.id.changeprofile_button_lastblooddonation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker = new DatePicker(ChangeProfile.this);
                int currentYear = datePicker.getYear();
                int currentMonth = datePicker.getMonth()+1;
                int currentDay = datePicker.getDayOfMonth();

                datePickerDialog = new DatePickerDialog(ChangeProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ((TextInputEditText)findViewById(R.id.changeprofile_text_lastblooddonation)).setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },currentYear, currentMonth, currentDay);
                datePickerDialog.show();

            }
        });


        //drop down list for blood group selection
        Spinner mySpinner = (Spinner)findViewById(R.id.changeprofile_text_bloodgroup);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ChangeProfile.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bloodGroups));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);





    }
    // function for setting up user in formaton to their respctive field
    void setup(User user){
        ((TextInputEditText) findViewById(R.id.changeprofile_text_name)).setText(user.name);
        ((TextInputEditText) findViewById(R.id.changeprofile_text_email)).setText(user.email);
        ((Spinner)findViewById(R.id.changeprofile_text_bloodgroup)).getSelectedItem().toString().trim();
        ((TextInputEditText) findViewById(R.id.changeprofile_text_dateofbirth)).setText(user.dateOfBirth);
        ((TextInputEditText) findViewById(R.id.changeprofile_text_address)).setText(user.address);
        ((TextInputEditText) findViewById(R.id.changeprofile_text_cellnumber)).setText(user.cellNo);
        ((TextInputEditText) findViewById(R.id.changeprofile_text_lastblooddonation)).setText(user.lastBloodDonation);
    }

     // checks is al fields are present
    private Boolean allFieldChecked(){
        name = ((EditText)findViewById(R.id.changeprofile_text_name)).getText().toString().trim();
        bloodGroup = ((Spinner)findViewById(R.id.changeprofile_text_bloodgroup)).getSelectedItem().toString().trim();
        dateOfBirth = ((EditText)findViewById(R.id.changeprofile_text_dateofbirth)).getText().toString().trim();
        address = ((EditText)findViewById(R.id.changeprofile_text_address)).getText().toString().trim();
        cellNumber = ((EditText)findViewById(R.id.changeprofile_text_cellnumber)).getText().toString().trim();
        lastBloodDonation = ((EditText)findViewById(R.id.changeprofile_text_lastblooddonation)).getText().toString().trim();

        if(name.equals("") || bloodGroup.equals("") || dateOfBirth.equals("") || address.equals("") || cellNumber.equals("") || lastBloodDonation.equals("")){
            Toast.makeText(ChangeProfile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!confirmPassword.equals(password)){
            Toast.makeText(ChangeProfile.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            Toast.makeText(ChangeProfile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //code for settig up new passport
    private void setPassword(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, currentPassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if(!confirmPassword.equals(password)){
                                Toast.makeText(ChangeProfile.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                                editConPass.setError("Passwords did not match");
                            }
                            else{
                                user.updatePassword(password)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.w("newTag223", "User password updated.");
                                                }
                                            }
                                        });

                                Intent intent = new Intent(ChangeProfile.this, Profile.class);
                                startActivity(intent);

                            }

                        }
                        else{
                            Toast.makeText(ChangeProfile.this, "Current password didn't match. Try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }



}

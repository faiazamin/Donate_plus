package com.sbrotee63.donate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInfo {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser user;

    FirebaseInfo(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        while(mAuth == null || database == null);
        user = mAuth.getCurrentUser();
    }

    public FirebaseUser getUser(){
        return user;
    }

    public Boolean isActiveUser(){
        return user != null;
    }

    public Boolean isActive(){
        return mAuth != null || database != null;
    }

    public FirebaseDatabase getDatabase(){
        return database;
    }

    public FirebaseAuth getmAuth(){
        return mAuth;
    }

    public void signOut(){
        mAuth.signOut();
    }

}

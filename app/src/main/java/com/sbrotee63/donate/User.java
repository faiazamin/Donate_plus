package com.sbrotee63.donate;

public class User {
    public String name;
    public String email;
    public String bloodGroup;
    public String dateOfBirth;
    public String address;
    public String cellNo;
    public String lastBloodDonation;
    public String userId;

    User(){

    }

    User(String name, String email, String bloodGroup, String dateOfBirth, String address, String cellNo, String lastBloodDonation, String userId){
        this.name = name;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.cellNo = cellNo;
        this.lastBloodDonation = lastBloodDonation;
        this.userId = userId;
    }

    User(String name, String email, String bloodGroup, String dateOfBirth, String address, String cellNo, String lastBloodDonation){
        this.name = name;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.cellNo = cellNo;
        this.lastBloodDonation = lastBloodDonation;
    }
}

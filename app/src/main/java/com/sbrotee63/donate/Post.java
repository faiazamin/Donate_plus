package com.sbrotee63.donate;

public class Post {

    public String name;
    public String bloodGroup;
    public String location;
    public String dateOfBirth;
    public String cellno;
    public String seekerId;
    Boolean visibility;

    Post(){

    }

    Post(String name, String bloodGroup, String location, String dateOfBirth, String cellno, String uid){
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
        this.cellno = cellno;
        seekerId = uid;
        visibility = true;
    }

    public Boolean isEmpty(){
        return name.equals("") || bloodGroup.equals("") || location.equals("") || dateOfBirth.equals("") || cellno.equals("");
    }
}

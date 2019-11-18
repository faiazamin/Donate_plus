package com.sbrotee63.donate;

public class NotiBlock {
    public String name;
    public String cellNo;
    public String uid;
    public String isEnlisted = "false";
    public String isCalled = "false";
    public String postId;
    public String note = "";

    NotiBlock(){

    }

    NotiBlock(String name, String cellNo, String uid, String postId){
        this.name = name;
        this.cellNo = cellNo;
        this.uid = uid;
        this.postId = postId;
    }

}

package com.example.shula.mygrownupsapp.models;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class FamilyMember{
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String memberId;
    public FamilyMember(){

    }

    public FamilyMember(String id, String name, String phoneNumber, String email, String password, String memberId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }



    public void clone(Map<String, Object> data){
        this.id = (String)data.get("id");
        this.name = (String)data.get("name");
        this.email = (String)data.get("email");
        this.password = (String)data.get("password");
        this.memberId = (String)data.get("memberId");
        this.phoneNumber = (String)data.get("number");


    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> fm = new HashMap();
        fm.put("id", this.id);
        fm.put("name",this.name);
        fm.put("number",this.phoneNumber);
        fm.put("email",this.email);
        fm.put("memberId",this.memberId);
        fm.put("password",this.password);
        return fm;
    }

}

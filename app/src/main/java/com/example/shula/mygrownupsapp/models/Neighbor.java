package com.example.shula.mygrownupsapp.models;

import java.util.HashMap;
import java.util.Map;

public class Neighbor {
    private String name;
    private String phoneNumber;
    private String memberId;


    public Neighbor(){
    }

    public Neighbor(String name, String phoneNumber, String memberId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.memberId = memberId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void clone(Map<String, Object> data){
        this.name = (String)data.get("name");
        this.phoneNumber = (String)data.get("number");
        this.memberId = (String)data.get("memberId");


    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> r = new HashMap();
        r.put("name",this.name);
        r.put("number",this.phoneNumber);
        r.put("memberId", this.memberId);
        return r;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}

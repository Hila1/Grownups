package com.example.shula.mygrownupsapp.models;

import com.example.shula.mygrownupsapp.ui.RegisterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Member {
    private String id;
    private String name;
    private Address address;
    private String number;
    private boolean videoCamera;

    public Member(String id, String name, Address address, String number, boolean  videoCamera) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.number = number;
        this.videoCamera = videoCamera;
    }

    public Member() {
     }

    public void clone(Map<String, Object> data){
        this.id = (String)data.get("id");
        this.name = (String)data.get("name");
        this.address=new Address((String)data.get("streetName"),(String)data.get("apartment"),(String)data.get("building"), (String)data.get("memberId"));
        this.number = (String)data.get("number");
        this.videoCamera = (boolean) data.get("videoCamera");
    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> m = new HashMap();
        Address address = this.address;
        HashMap<String, Object> addressMap = address.toMap();
        m.put("id", this.id);
        m.put("name",this.name);
        m.put("number",this.number);
        m.put("videoCamera",this.videoCamera);
        m.put("Address",addressMap);
        return m;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean getVideoCamera() {
        return videoCamera;
    }

    public void setVideoCamera(boolean videoCamera) {
        this.videoCamera = videoCamera;
    }
}

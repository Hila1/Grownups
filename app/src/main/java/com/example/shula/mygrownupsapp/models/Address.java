package com.example.shula.mygrownupsapp.models;

import java.util.HashMap;
import java.util.Map;

public class Address {
    private String memberId;
    private String streetName;
    private String building;
    private String apartment;

    public Address() {
    }

    public Address(String memberId, String streetName, String building, String apartment) {
        this.memberId = memberId;
        this.streetName = streetName;
        this.building = building;
        this.apartment = apartment;
    }
    public void clone(Map<String, Object> data){
        this.memberId = (String)data.get("memberId");
        this.streetName = (String)data.get("streetName");
        this.building = (String)data.get("building");
        this.apartment = (String) data.get("apartment");
    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> a = new HashMap();
        a.put("memberId", this.memberId);
        a.put("streetName",this.streetName);
        a.put("building",this.building);
        a.put("apartment",this.apartment);
        return a;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}

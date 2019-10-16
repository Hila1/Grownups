package com.example.shula.mygrownupsapp.models;

import java.util.HashMap;
import java.util.Map;

public class HelpCenter {
    private int code;
    private String name;
    private  String number;
    private String memberId;
    private  HelpCenter(){

    }

    public void clone(Map<String, Object> data){
        this.code = (int)data.get("code");
        this.name = (String)data.get("name");
        this.number = (String)data.get("number");
        this.memberId = (String) data.get("memberId");
    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> h = new HashMap();
        h.put("code",this.code);
        h.put("name",this.name);
        h.put("number",this.number);
        h.put("memberId",this.memberId);
        return  h;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void getPhoneCall(String name,String address){

    }


}

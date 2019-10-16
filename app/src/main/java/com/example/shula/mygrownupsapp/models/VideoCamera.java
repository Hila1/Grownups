package com.example.shula.mygrownupsapp.models;

import java.util.HashMap;
import java.util.Map;

public class VideoCamera {
    private String connectionLink;
    private String memberId;
    public VideoCamera(){

    }
    public VideoCamera(String memberId){
        //get from database
    }

    public VideoCamera(String connectionLink, String memberId) {
        this.connectionLink = connectionLink;
        this.memberId = memberId;
    }

    public void clone(Map<String, Object> data){

        this.memberId = (String) data.get("memberId");
        this.connectionLink = (String) data.get("connectionLink");

    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> v = new HashMap();
        v.put("id", this.memberId);
        v.put("connectionLink", this.connectionLink);
        return v;

    }



    public String getConnectionLink() {
        return connectionLink;
    }

    public void setConnectionLink(String connectionLink) {
        this.connectionLink = connectionLink;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void connectCamera(){
            //need a connection link;
    }
}

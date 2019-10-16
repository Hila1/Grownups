package com.example.shula.mygrownupsapp.models;

import java.lang.reflect.Member;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Warning  {
    private String warningId;
    private String date;
    private String level;
    private boolean helpCenter;
    private boolean neighbor;
    private boolean videoCamera;
    private String memberId;

    //blank constructor
    public Warning(){

    }

    public Warning(String warningId, String date, String level, String memberId) {
        this.warningId = warningId;
        this.date = date;
        this.level = level;
        this.memberId = memberId;
        this.helpCenter = false;
        this.videoCamera = false;
        this.neighbor = false;

    }

    public Warning(String warningId, String date, String level, boolean helpCenter, boolean neighbor, boolean videoCamera, String memberId) {
        this.warningId = warningId;
        this.date = date;
        this.level = level;
        this.helpCenter = helpCenter;
        this.neighbor = neighbor;
        this.videoCamera = videoCamera;
        this.memberId = memberId;
    }

    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLevel() {
        return  level+"";
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isHelpCenter() {
        return helpCenter;
    }

    public void setHelpCenter(boolean helpCenter) {
        this.helpCenter = helpCenter;
    }

    public boolean isNeighbor() {
        return neighbor;
    }

    public void setNeighbor(boolean neighbor) {
        this.neighbor = neighbor;
    }

    public boolean isVideoCamera() {
        return videoCamera;
    }

    public void setVideoCamera(boolean videoCamera) {
        this.videoCamera = videoCamera;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getChosenHelp()
    {
        String chosenHelp="";
        if(this.helpCenter)
            chosenHelp+= "Help Center ";
        if(this.neighbor)
            chosenHelp+= "Neighbor ";
        if(this.videoCamera)
            chosenHelp+= "Video Camera";
        if(!this.videoCamera && !this.helpCenter && !this.neighbor)
            chosenHelp="family mmembeer did not chose help";
        return chosenHelp;
    }
    public void clone(Map<String, Object> data){
        this.date = (String)data.get("date");
        this.helpCenter = (boolean)data.get("helpCenter");
        this.level = (String) data.get("level");
        this.neighbor = (boolean)data.get("neighbor");
        this.videoCamera  = (boolean)data.get("videoCamera");
        this.warningId = (String) data.get("warningId");
        this.memberId = (String) data.get("memberId");
    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> w = new HashMap();
        w.put("id", this.warningId);
        w.put("level",this.level);
        w.put("date", this.date);
        w.put("videoCamera", this.videoCamera);
        w.put("neighbor", this.neighbor);
        w.put("helpCenter", this.helpCenter);
        w.put("memberId", this.memberId);
        return w;

    }
}

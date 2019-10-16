package com.example.shula.mygrownupsapp.models;

public class Notifications {
    private String uid;
    private String text;
    private String topic;

    public Notifications(String uid, String text, String topic) {
        this.uid = uid;
        this.text = text;
        this.topic = topic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

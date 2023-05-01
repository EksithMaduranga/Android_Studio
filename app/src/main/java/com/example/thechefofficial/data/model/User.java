package com.example.thechefofficial.data.model;

import android.net.Uri;

public class User {
    private String uid;
    private String name;
    private  String mail;
    private int tp;
    private String pass;
    private String url;

    public User() {

    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public User(String uid, String name, String mail, int tp, String pass, String url) {
        this.uid = uid;
        this.name = name;
        this.mail = mail;
        this.tp = tp;
        this.pass = pass;
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

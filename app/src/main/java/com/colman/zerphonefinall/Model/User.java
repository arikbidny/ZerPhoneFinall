package com.colman.zerphonefinall.Model;

/**
 * Created by Sagi on 7/26/2016.
 */
public class User{
    private String email;
    private String password;
    private String lastUpdate;



    public User(String mail,String date) {
        this.email = mail;
        this.lastUpdate = date;
    }

    public User(String mail,String password, String lastUpdate){
        this.email = mail;
        this.password = password;
        this.lastUpdate = lastUpdate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {

        return password;
    }

    public String getEmail() {
        return email;
    }


    public String getlastUpdate() {
        return lastUpdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setlastUpdate (String date) {
        this.lastUpdate = date;
    }
}
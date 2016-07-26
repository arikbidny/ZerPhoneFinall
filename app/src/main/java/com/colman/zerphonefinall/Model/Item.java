package com.colman.zerphonefinall.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Sagi on 5/28/2016.
 */
public class Item {


    private String title;
    private String image;
    private String price;
    private String details;
    private String category;
    private String lastUpadte;
    @JsonIgnore
    private  String key;


    public Item() {}//empty constructor for firebase

    public Item(String name,String drawableId,String price,String dt,String cat,String date) {
        this.title = name;
        this.image = drawableId;
        this.price = price;
        this.details = dt;
        this.category = cat;
        this.lastUpadte = date;
    }

    public Item(String name,String drawableId,String price,String dt,String cat,String key, String date) {
        this.title = name;
        this.image = drawableId;
        this.price = price;
        this.details = dt;
        this.category = cat;
        this.key = key;
        this.lastUpadte = date;
    }

    public String getKey(){return this.key;}

    public String getTitle (){
        return this.title;
    }

    public String getImage() {
        return this.image;
    }

    public String getPrice(){
        return this.price;
    }

    public String getdetails(){
        return this.details;
    }

    public String getCategory() {
        return this.category;
    }

    public void setKey(String key) {this.key=key;}

    public void setTitle(String name) {
        this.title=name;
    }

    public void setImage(String image){
        this.image=image;
    }

    public void setPrice (String price){
        this.price=price;
    }

    public void setDetails(String details){
        this.details =details;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLastUpadte(String date) {this.lastUpadte = date;}

    public String getLastUpadte() {return this.lastUpadte;}
}

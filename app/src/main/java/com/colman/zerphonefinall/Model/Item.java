package com.colman.zerphonefinall.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Sagi on 5/28/2016.
 */
public class Item {


    private String title;
    private int image;
    private String price;
    private String details;
    private String category;
    @JsonIgnore
    private  String key;


    public Item() {}//empty constructor for firebase

    public Item(String name,int drawableId,String price,String dt,String cat) {
        this.title = name;
        this.image = drawableId;
        this.price = price;
        this.details = dt;
        this.category = cat;
    }

    public Item(String name,int drawableId,String price,String dt,String cat,String key) {
        this.title = name;
        this.image = drawableId;
        this.price = price;
        this.details = dt;
        this.category = cat;
        this.key = key;
    }

    public String getKey(){return this.key;}

    public String getTitle (){
        return this.title;
    }

    public int getImage() {
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

    public void setImage(int image){
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
}

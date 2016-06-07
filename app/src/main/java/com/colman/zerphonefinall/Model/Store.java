package com.colman.zerphonefinall.Model;

/**
 * Created by arikbi on 06/06/2016.
 */
public class Store {
    private String city;
    private String neighborhood;
    private String description;
    private String image;
    private String address;

    public Store(String city,
                 String neighborhood,
                 String description,
                 String image,
                 String address) {
        this.city = city;
        this.neighborhood = neighborhood;
        this.description = description;
        this.image = image;
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    public String getNeighborhood() {
        return neighborhood;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public String getAddress() {
        return address;
    }
}

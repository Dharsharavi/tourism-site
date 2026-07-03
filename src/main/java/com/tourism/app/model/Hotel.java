package com.tourism.app.model;

public class Hotel {

    private int id;
    private String name;
    private String location;
    private int starRating;
    private double pricePerNight;
    private String contact;

    public Hotel() {
    }

    public Hotel(int id, String name, String location, int starRating,
                 double pricePerNight, String contact) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.starRating = starRating;
        this.pricePerNight = pricePerNight;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

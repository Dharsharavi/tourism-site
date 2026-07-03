package com.tourism.app.model;

public class Attraction {

    private int id;
    private String name;
    private String location;
    private String category;
    private String description;
    private String imageUrl;

    public Attraction() {
    }

    public Attraction(int id, String name, String location, String category,
                       String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

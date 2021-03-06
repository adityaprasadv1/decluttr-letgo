package com.example.myshoppingstore;

public class CreateForm {
    private String name;
    private String image;
    private String description;
    private double price;
    private double condition;
    private String id;
    public CreateForm() {
    }

    public CreateForm(String name, String image, String description, double price, double condition, String id) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCondition() {
        return condition;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

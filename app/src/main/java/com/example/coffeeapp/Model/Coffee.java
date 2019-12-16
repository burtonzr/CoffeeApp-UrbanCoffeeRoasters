package com.example.coffeeapp.Model;

public class Coffee {
    private String drinkName;
    private String description;
    private int ID;
    private int image;
    private double price;

    public Coffee(String drinkName, int ID, int image, double price, String description) {
        this.drinkName = drinkName;
        this.ID = ID;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public Double getPrice() {
        return price;
    }

    public int getID() {
        return ID;
    }

    public int getImage() {
        return image;
    }
}

package com.example.coffeeapp.Model;

public class Order {
    private String CoffeeID;
    private String CoffeeName;
    private String CoffeePrice;
    private String MilkType;
    private String Size;
    private String Quantity;
    private String UserID;
    private String Status;
    private int OrderID;

    public Order(String CoffeeID, String CoffeeName, String CoffeePrice, String MilkType, String Size, String Quantity, String UserID, String Status, int OrderID) {
        this.CoffeeID       = CoffeeID;
        this.CoffeeName     = CoffeeName;
        this.CoffeePrice    = CoffeePrice;
        this.MilkType       = MilkType;
        this.Size           = Size;
        this.Quantity       = Quantity;
        this.UserID         = UserID;
        this.Status         = Status;
        this.OrderID        = OrderID;
    }

    public String getCoffeeID() {
        return CoffeeID;
    }

    public void setCoffeeID(String CoffeeID) {
        this.CoffeeID = CoffeeID;
    }

    public String getCoffeeName() {
        return CoffeeName;
    }

    public void setCoffeeName(String CoffeeName) {
        this.CoffeeName = CoffeeName;
    }

    public String getCoffeePrice() {
        return CoffeePrice;
    }

    public String getMilkType() {
        return MilkType;
    }

    public String getSize() {
        return Size;
    }

    public String getStatus() {
        return Status;
    }

    public void setCoffeePrice() {
        this.CoffeePrice = CoffeePrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getOrderID() {
        return OrderID;
    }
}

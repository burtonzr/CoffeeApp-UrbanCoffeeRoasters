package com.example.coffeeapp.Model;

public class OrderSubmit {
    private String PickUpTime;
    private String TotalAmount;
    private String CardNumber;
    private String Status;

    public OrderSubmit(String PickUpTime, String TotalAmount, String CardNumber, String Status) {
        this.PickUpTime  = PickUpTime;
        this.TotalAmount = TotalAmount;
        this.CardNumber  = CardNumber;
        this.Status      = Status;
    }

    public String getPickUpTime() {
        return PickUpTime;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public String getStatus() {
        return Status;
    }
}

package com.example.uts.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryResponseData {

    private String message;

    @SerializedName("data")
    private Delivery delivery;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}

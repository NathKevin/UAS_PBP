package com.example.uts.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryResponse {

    private String message;

    @SerializedName("data")
    private List<Delivery> deliveryList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }
}

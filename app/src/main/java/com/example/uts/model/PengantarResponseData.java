package com.example.uts.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PengantarResponseData {

    private String message;

    @SerializedName("data")
    private Pengantar pengantar;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pengantar getPengantar() {
        return pengantar;
    }

    public void setDelivery(Pengantar pengantar) {
        this.pengantar = pengantar;
    }
}

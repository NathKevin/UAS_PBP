package com.example.uts.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PengantarResponse {

    private String message;

    @SerializedName("data")
    private List<Pengantar> pengantarList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pengantar> getPengantarList() {
        return pengantarList;
    }

    public void setDeliveryList(List<Pengantar> pengantarList) {
        this.pengantarList = pengantarList;
    }
}

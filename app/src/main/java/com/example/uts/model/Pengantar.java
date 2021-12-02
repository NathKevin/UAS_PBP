package com.example.uts.model;

import com.google.gson.annotations.SerializedName;

public class Pengantar {

    @SerializedName("id")
    private int id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("noTelp")
    private String noTelp;

    public Pengantar(String nama, String noTelp) {
        this.nama = nama;
        this.noTelp = noTelp;
    }

    public Pengantar() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

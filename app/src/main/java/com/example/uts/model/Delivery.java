package com.example.uts.model;

public class Delivery {
    private String namaPenerima;
    private String tipe;
    private String fragile;
    private String addPickup;
    private String addTujuan;
    private int idUser;

    public Delivery(String namaPenerima, String tipe, String fragile, String addPickup, String addTujuan, int idUser) {
        this.namaPenerima = namaPenerima;
        this.tipe = tipe;
        this.fragile = fragile;
        this.addPickup = addPickup;
        this.addTujuan = addTujuan;
        this.idUser = idUser;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String isFragile() {
        return fragile;
    }

    public void setFragile(String fragile) {
        this.fragile = fragile;
    }

    public String getAddPickup() {
        return addPickup;
    }

    public void setAddPickup(String addPickup) {
        this.addPickup = addPickup;
    }

    public String getAddTujuan() {
        return addTujuan;
    }

    public void setAddTujuan(String addTujuan) {
        this.addTujuan = addTujuan;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}

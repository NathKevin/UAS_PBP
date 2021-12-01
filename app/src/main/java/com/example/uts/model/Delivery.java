package com.example.uts.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "delivery")

public class Delivery extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    public int idDelivery;

    @ColumnInfo(name = "namaPenerima")
    @SerializedName("recipientName")
    private String namaPenerima;

    @ColumnInfo(name = "tipe")
    @SerializedName("type")
    private String tipe;

    @ColumnInfo(name = "fragile")
    @SerializedName("fragile")
    private String fragile;

    @ColumnInfo(name = "addPickup")
    @SerializedName("pickupAddress")
    private String addPickup;

    @ColumnInfo(name = "addTujuan")
    @SerializedName("destinationAddress")
    private String addTujuan;

    @ColumnInfo(name = "idUser")
    @SerializedName("user_id")
    private int idUser;

    @SerializedName("pengantar_id")
    private int idPengantar;

    public Delivery() {

    }

    public Delivery(String namaPenerima, String tipe, String fragile, String addPickup, String addTujuan, int idUser, int idPengantar) {
        this.namaPenerima = namaPenerima;
        this.tipe = tipe;
        this.fragile = fragile;
        this.addPickup = addPickup;
        this.addTujuan = addTujuan;
        this.idUser = idUser;
        this.idPengantar = idPengantar;
    }

    @Bindable
    public String getNamaPenerima() {
        return namaPenerima;
    }
    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
        notifyPropertyChanged(BR.namaPenerima);
    }

    @Bindable
    public String getTipe() {
        return tipe;
    }
    public void setTipe(String tipe) {
        this.tipe = tipe;
        notifyPropertyChanged(BR.tipe);
    }

    @Bindable
    public String getFragile() {
        return fragile;
    }
    public void setFragile(String fragile) {
        this.fragile = fragile;
        notifyPropertyChanged(BR.fragile);
    }

    @Bindable
    public String getAddPickup() {
        return addPickup;
    }
    public void setAddPickup(String addPickup) {
        this.addPickup = addPickup;
        notifyPropertyChanged(BR.addPickup);
    }

    @Bindable
    public String getAddTujuan() {
        return addTujuan;
    }
    public void setAddTujuan(String addTujuan) {
        this.addTujuan = addTujuan;
        notifyPropertyChanged(BR.addTujuan);
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPengantar() {
        return idPengantar;
    }
    public void setIdPengantar(int idPengantar) {
        this.idPengantar = idPengantar;
    }

    public int getIdDelivery() {
        return idDelivery;
    }
    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }
}

package com.example.uts.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "delivery",
        foreignKeys = @ForeignKey(entity = User.class,
                    parentColumns = "id",
                    childColumns = "idDelivery",
                    onDelete = CASCADE))

public class Delivery extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    public int idDelivery;

    @ColumnInfo(name = "namaPenerima")
    private String namaPenerima;

    @ColumnInfo(name = "tipe")
    private String tipe;

    @ColumnInfo(name = "fragile")
    private String fragile;

    @ColumnInfo(name = "addPickup")
    private String addPickup;

    @ColumnInfo(name = "addTujuan")
    private String addTujuan;

    @ColumnInfo(name = "idUser")
    private int idUser;

    public Delivery() {

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

    @Bindable
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
        notifyPropertyChanged(BR.idUser);
    }
}

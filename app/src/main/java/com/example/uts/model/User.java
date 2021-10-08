package com.example.uts.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.uts.BR;

@Entity(tableName = "user")
public class User extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "noTelp")
    private String noTelp;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "pass")
    private String password;

    private String confirmPassword;

    public User() {
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {

        this.nama = nama;
        notifyPropertyChanged(BR.nama);
    }

    @Bindable
    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {

        this.noTelp = noTelp;
        notifyPropertyChanged(BR.noTelp);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}

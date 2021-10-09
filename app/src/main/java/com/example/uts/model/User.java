package com.example.uts.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.uts.BR;

import java.util.List;

@Entity(tableName = "user")
public class User extends BaseObservable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nama")
    public String nama;

    @ColumnInfo(name = "noTelp")
    public String noTelp;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "pass")
    public String password;

    @ColumnInfo(name = "uang")
    public int uang;


    public String confirmPassword;

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

    @Bindable
    public int getUang() {
        return uang;
    }

    public void setUang(int uang) {

        this.uang = uang;
        notifyPropertyChanged(BR.uang);
    }


    //  Bagian ini merupakan implementasi dari Parcelable agar kita dapat mengirim data dalam bentuk kelas.
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeString(noTelp);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeInt(uang);
    }

    protected User(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        noTelp = in.readString();
        email = in.readString();
        password = in.readString();
        uang = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}

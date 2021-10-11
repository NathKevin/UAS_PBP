package com.example.uts.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.uts.model.Delivery;
import com.example.uts.model.User;

@Dao
public interface DeliveryDao {
    @Query("SELECT * FROM delivery WHERE idUser = :id")
    Delivery searchUser(int id);

    @Insert
    void insertUser(Delivery delivery);

    @Update
    void updateUser(Delivery delivery);

    @Delete
    void deleteUser(Delivery delivery);
}

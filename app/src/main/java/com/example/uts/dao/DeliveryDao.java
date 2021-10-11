package com.example.uts.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.uts.model.Delivery;
import com.example.uts.model.User;

import java.util.List;

@Dao
public interface DeliveryDao {
    @Query("SELECT * FROM delivery WHERE idUser = :id")
    List<Delivery> getAll(int id);

    @Insert
    void insertDelivery(Delivery delivery);

    @Update
    void updateDelivery(Delivery delivery);

    @Delete
    void deleteDelivery(Delivery delivery);
}

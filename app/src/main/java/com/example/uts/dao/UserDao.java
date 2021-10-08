package com.example.uts.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.uts.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE email = :email AND pass = :password")
    User login(String email, String password);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}

package com.example.uts.database;

import android.os.Build;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//import com.example.uts.dao.DeliveryDao;
import com.example.uts.dao.DeliveryDao;
import com.example.uts.dao.UserDao;
import com.example.uts.model.Delivery;
import com.example.uts.model.User;

@Database(entities = {User.class, Delivery.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DeliveryDao deliveryDao();
}

package com.example.uts.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseDelivery {
    private Context context;
    private static DatabaseDelivery databaseDelivery;

    private AppDatabase database;

    public DatabaseDelivery(Context context){
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "delivery").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseDelivery getInstance(Context context){
        if(databaseDelivery == null){
            databaseDelivery = new DatabaseDelivery(context);
        }
        return databaseDelivery;
    }

    public AppDatabase getDatabase(){ return database; }
}

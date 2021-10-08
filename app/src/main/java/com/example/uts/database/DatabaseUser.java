package com.example.uts.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseUser {
    private Context context;
    private static DatabaseUser databaseUser;

    private AppDatabase database;

    public DatabaseUser(Context context){
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "user").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseUser getInstance(Context context){
        if(databaseUser == null){
            databaseUser = new DatabaseUser(context);
        }
        return databaseUser;
    }

    public AppDatabase getDatabase(){ return database; }
}

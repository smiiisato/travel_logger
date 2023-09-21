package com.example.travel_logger;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataEntity.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase{
    public abstract DataDao dataDao();
}

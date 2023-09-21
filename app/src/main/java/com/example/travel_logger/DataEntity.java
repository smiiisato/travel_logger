package com.example.travel_logger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;


@Entity
public class DataEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    // define data entity
    //feeling
    //weather
    //diary
    @ColumnInfo(name = "diary")
    public String diary;


}

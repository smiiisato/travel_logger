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
    @ColumnInfo(name = "feeling")
    public int feeling;

    //weather
    @ColumnInfo(name = "weather")
    public int weather;

    //diary
    @ColumnInfo(name = "diary")
    public String diary;

    //picture uri
    @ColumnInfo(name = "pictureUri")
    public String pictureUri;


}

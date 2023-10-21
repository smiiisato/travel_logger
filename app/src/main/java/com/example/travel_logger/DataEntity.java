package com.example.travel_logger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;


@Entity(tableName = "dataEntity")
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

    public int getFeeling(){
        return feeling;
    }

    public int getWeather(){
        return weather;
    }

    public String getDiary(){
        return diary;
    }

    public String getPictureUri(){
        return pictureUri;
    }

}

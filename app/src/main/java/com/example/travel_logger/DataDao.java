package com.example.travel_logger;

import static java.lang.Math.max;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.travel_logger.DataEntity;

import java.util.List;

@Dao
public interface DataDao {
    //add operations
    @Insert
    void insertData(DataEntity dataEntity);

    @Delete
    void delete(DataEntity dataEntity);

    @Query("SELECT * FROM dataEntity")
    List<DataEntity> getAll();

    // get info by id
    @Query("SELECT * FROM dataEntity WHERE id = :id")
    DataEntity getDataById(int id);

    @Query("SELECT max(id) FROM dataEntity")
    int getLatestId();
}

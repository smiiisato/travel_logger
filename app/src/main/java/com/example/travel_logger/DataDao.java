package com.example.travel_logger;

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

    @Query("SELECT * FROM dataentity")
    List<DataEntity> getAll();
}

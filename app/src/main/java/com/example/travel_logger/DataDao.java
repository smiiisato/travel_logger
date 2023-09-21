package com.example.travel_logger;

import androidx.room.Insert;
import com.example.travel_logger.DataEntity;

public interface DataDao {
    //add operations
    @Insert
    void insertUser(DataEntity dataEntity);
}

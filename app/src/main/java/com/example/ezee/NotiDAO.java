package com.example.ezee;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotiDAO {

    @Query("SELECT * FROM NotiClass LIMIT 50")
    List<NotiClass> getallNoti();

    @Insert
    void insert(NotiClass notifications);

    @Delete
    void delete(NotiClass notifications);
}

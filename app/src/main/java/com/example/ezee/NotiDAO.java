package com.example.ezee;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotiDAO {

    @Query("SELECT * FROM NotiClass LIMIT 3")
    List<NotiClass> getallNoti();

    @Insert
    void insert(NotiClass notifications);

    @Query("DELETE FROM NotiClass WHERE uid=:uid")
    void deleteByUID(int uid);
}

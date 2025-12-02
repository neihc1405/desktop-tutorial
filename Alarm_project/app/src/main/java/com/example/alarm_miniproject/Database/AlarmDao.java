package com.example.alarm_miniproject.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM AlarmData")
    List<AlarmData> getAll();

    @Query("SELECT * FROM AlarmData WHERE id IN(:ids)")
    List<AlarmData> loadAllByIds(int[] ids);

    @Query("SELECT * FROM AlarmData WHERE id LIKE(:ids)")
    List<AlarmData> FindByName(int[] ids);

    @Update
    void update(AlarmData noteData);

    @Insert
    void insertAll(AlarmData... noteData);

    @Delete
    void delete(AlarmData noteData);
}

package com.example.alarm_miniproject.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities={AlarmData.class}, version = 2,exportSchema = false)
@TypeConverters(Conveters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlarmDao getAlarmDao();
}

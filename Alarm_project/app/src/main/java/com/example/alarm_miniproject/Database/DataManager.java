package com.example.alarm_miniproject.Database;

import android.content.Context;

import androidx.room.Room;

public class DataManager {
    public AppDatabase mAppDAtabase;
    public static DataManager mInstance;

    public DataManager(Context context) {
        mAppDAtabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppnoteDatabase").fallbackToDestructiveMigration().allowMainThreadQueries().build();

    }

    public AlarmDao getAlarmDao() {
        return mAppDAtabase.getAlarmDao();
    }

    public static DataManager getInstance(Context context) {
        synchronized (DataManager.class) {
            if (mInstance == null) {
                mInstance = new DataManager(context);
            }
        }
        return mInstance;
    }
}

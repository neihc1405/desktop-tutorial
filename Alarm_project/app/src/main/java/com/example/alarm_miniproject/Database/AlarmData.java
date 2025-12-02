package com.example.alarm_miniproject.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlarmData {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "Time")
    public String time;

    @ColumnInfo(name = "tinhtrang")
    public String tinhtrang;

    @ColumnInfo(name = "Battat")
    public Boolean battat = true;

    public AlarmData(long id, String time, String tinhtrang, Boolean battat) {
        this.id = id;
        this.time = time;
        this.tinhtrang = tinhtrang;
        this.battat = battat;
    }

    public AlarmData(String time, String tinhtrang, Boolean battat) {

        this.time = time;
        this.tinhtrang = tinhtrang;
        this.battat = battat;
    }

    public AlarmData() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public Boolean getBattat() {
        return battat;
    }

    public void setBattat(Boolean battat) {
        this.battat = battat;
    }
}

package com.igorganapolsky.vibratingwatchapp.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Timer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private long milliseconds;

    @ColumnInfo(name = "buzz_mode")
    private int buzzMode;

    @ColumnInfo(name = "repeat")
    private int repeat;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "state")
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getBuzzMode() {
        return buzzMode;
    }

    public void setBuzzMode(int buzzMode) {
        this.buzzMode = buzzMode;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package com.igorganapolsky.vibratingwatchapp.domain.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TimerEntity implements Serializable {

    public static final String TIMER_ID = "timer_id";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time_total")
    private long millisecondsTotal;

    @ColumnInfo(name = "time_left")
    private long millisecondsLeft;

    @ColumnInfo(name = "buzz_mode")
    private int buzzMode;

    @ColumnInfo(name = "repeat")
    private int repeat;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMillisecondsTotal() {
        return millisecondsTotal;
    }

    public void setMillisecondsTotal(long millisecondsTotal) {
        this.millisecondsTotal = millisecondsTotal;
    }

    public long getMillisecondsLeft() {
        return millisecondsLeft;
    }

    public void setMillisecondsLeft(long millisecondsLeft) {
        this.millisecondsLeft = millisecondsLeft;
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
}

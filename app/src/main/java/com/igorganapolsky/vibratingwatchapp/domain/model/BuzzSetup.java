package com.igorganapolsky.vibratingwatchapp.domain.model;

import java.util.Objects;

public class BuzzSetup {

    public enum Type {SHORT, LONG}

    private final Type buzzType;
    private final int buzzCount;
    private final int buzzTime;
    private boolean isSelected;

    public BuzzSetup(Type buzzType, int buzzCount, int buzzTime) {
        this.buzzType = buzzType;
        this.buzzCount = buzzCount;
        this.buzzTime = buzzTime;
        this.isSelected = false;
    }

    public Type getBuzzType() {
        return buzzType;
    }

    public int getBuzzCount() {
        return buzzCount;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getBuzzTime() {
        return buzzTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuzzSetup buzzSetup = (BuzzSetup) o;
        return buzzCount == buzzSetup.buzzCount &&
            buzzTime == buzzSetup.buzzTime &&
            buzzType == buzzSetup.buzzType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buzzType, buzzCount, buzzTime);
    }
}

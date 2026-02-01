package com.example.model;

public class Slot {
    private boolean isMine;
    private boolean isRevealed;
    private int nearMineCount;

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getNearMineCount() {
        return nearMineCount;
    }

    public void setNearMineCount(int nearMineCount) {
        this.nearMineCount = nearMineCount;
    }
}

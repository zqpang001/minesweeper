package com.example.service;

public interface MineEngineService {
    void placeMines(int mineCount);
    void calMineCount();
    void displayAdjMineCount(int r, int c);
    boolean checkWin();
    boolean checkMine(int r, int c);
    void revealGrid (int r, int c);
    void displayBoard (boolean initialDisplay);
    boolean isValidGrid(String input);
}

package com.example.model;

public class Game {
    private int gridSize;
    private Slot[][] grid;
    // To determine if player has won the game.
    private int slotLeftCount;

    public Game(int gridSize, int mineCount) {
        this.gridSize = gridSize;
        this.grid = new Slot[gridSize][gridSize];
        this.slotLeftCount = gridSize * gridSize - mineCount;
        init();
    }

    private void init() {
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                grid[r][c] = new Slot();
            }
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }


    public Slot[][] getGrid() {
        return grid;
    }

    public void setGrid(Slot[][] grid) {
        this.grid = grid;
    }

    public int getSlotLeftCount() {
        return slotLeftCount;
    }

    public void setSlotLeftCount(int slotLeftCount) {
        this.slotLeftCount = slotLeftCount;
    }

    public void minusSlotLeftCount() {
        this.slotLeftCount--;
    }
}

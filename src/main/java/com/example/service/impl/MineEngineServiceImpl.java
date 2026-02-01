package com.example.service.impl;

import com.example.model.Game;
import com.example.model.Slot;
import com.example.service.MineEngineService;

import java.util.Random;

/**
 - Assumptions
 1. User will input coordinates in a row, column format (e.g., A5 or C10).
 2. Maximum of 35 grid.
 2. Number of mines is fixed at the start and does not change throughout the game.
 3. Uncovering (recursive reveal) only triggers when the mine count is 0.
 4. Game ends immediately after all uncovered mine are revealed (gridsize * gridsize - mines).
**/
public class MineEngineServiceImpl implements MineEngineService {
    private Game game;

    public MineEngineServiceImpl(Game game) {
        this.game = game;
    }

    // randomly plant the mine
    public void placeMines(int mineCount) {
        Random rand = new Random();
        int gridSize = game.getGridSize();
        while (0 < mineCount) {
            int r = rand.nextInt(gridSize);
            int c = rand.nextInt(gridSize);
            boolean isMine = game.getGrid()[r][c].isMine();
            if (!isMine) {
                game.getGrid()[r][c].setMine(true);
                mineCount--;
            }
        }
    }

    // Calculate all the mine count for the slot
    public void calMineCount() {
        int gridSize = game.getGridSize();
        Slot[][] grid = game.getGrid();
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                if (grid[r][c].isMine()) {
                    continue;
                }

                int nearMineCount = 0;

                // Calculate 8 surrounding slots mine count for this slot
                for (int xr = -1; xr <= 1; xr++) {
                    for (int xc = -1; xc <= 1; xc++) {
                        if (r + xr < 0 || r + xr > gridSize-1 || c + xc < 0 || c + xc > gridSize-1) {
                            continue;
                        }

                        if (grid[r+xr][c+xc].isMine()) {
                            nearMineCount++;
                        }
                    }
                }
                grid[r][c].setNearMineCount(nearMineCount);
            }
        }
    }

    public void displayAdjMineCount(int r, int c) {
        System.out.printf("This square contains %d adjacent mines",game.getGrid()[r][c].getNearMineCount());
        System.out.println();
    }

    public boolean checkWin() {
        return game.getSlotLeftCount() == 0;
    }

    public boolean checkMine(int r, int c) {
        if (game.getGrid()[r][c].isMine()) {
            return true;
        }
        revealGrid(r,c);
        return false;
    }

    public void revealGrid (int r, int c) {
        int gridSize = game.getGridSize();
        Slot[][] grid = game.getGrid();
        // 1. Bounds check and "already processed" check
        if (r < 0 || r >= gridSize || c < 0 || c >= gridSize ||
                grid[r][c].isRevealed() || grid[r][c].isMine()) {
            return;
        }

        // 2. Reveal the current slot. Calculate the number of slot left.
        grid[r][c].setRevealed(true);
        game.minusSlotLeftCount();

        // 3. End the loop if current slot encountered mine.
        if (grid[r][c].getNearMineCount() > 0) {
            return;
        }

        // 4. If it's a "0" slot, recursively reveal all 8 adjacent slot
        for (int xr = -1; xr <= 1; xr++) {
            for (int xc = -1; xc <= 1; xc++) {
                // Skip the current center square
                if (xr == 0 && xc == 0) {
                    continue;
                }
                revealGrid(r + xr, c + xc);
            }
        }
    }

    public void displayBoard (boolean initialDisplay) {
        int gridSize = game.getGridSize();
        Slot[][] grid = game.getGrid();
        int alphabetCount = 0;
        for (int r = 0; r < gridSize; r++) {
            if (r==0) {
                System.out.print("  ");
                for (int h = 1; h <= gridSize; h++) {
                    System.out.print(h + " ");
                }
                System.out.println();
            }


            for (int c = -1; c < gridSize; c++) {
                if (c == -1) {
                    char letter = (char) ('A' + alphabetCount);
                    System.out.print(letter + " ");
                    alphabetCount++;
                    continue;
                }
                if (grid[r][c].isRevealed() && !initialDisplay) {
                    System.out.print(grid[r][c].getNearMineCount() + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    public boolean isValidGrid(String input) {
        if (input == null || !input.matches("[A-Z][0-9]+")) {
            return false;
        }
        int alphaRow = input.charAt(0) - 'A';
        int column = Integer.parseInt(input.substring(1)) - 1;
        if (alphaRow > game.getGridSize() - 1  || column > game.getGridSize() -1 ) {
            return false;
        }
        return true;
    }
}

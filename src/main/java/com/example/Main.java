package com.example;

import com.example.model.Game;
import com.example.service.impl.MineEngineServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            int gridSize = 0;
            while (true) {
                System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid) max 35: ");
                if (scanner.hasNextInt()) {
                    gridSize = scanner.nextInt();
                    if (gridSize >= 3 && gridSize <= 35) break;
                } else {
                    scanner.next();
                }
                System.out.println("Invalid! Try again.");
            }

            int mineCount;
            int maxMineCount = gridSize*gridSize;
            while (true) {
                System.out.print("Enter mine count:  ");
                if (scanner.hasNextInt()) {
                    mineCount = scanner.nextInt();
                    if (mineCount < maxMineCount) break;
                } else {
                    scanner.next();
                }
                System.out.println("Invalid! Mine count, renter again");
            }
            Game game = new Game(gridSize,mineCount);

            MineEngineServiceImpl mineEngine = new MineEngineServiceImpl(game);
            mineEngine.placeMines(mineCount);
            mineEngine.calMineCount();
            scanner.nextLine();
            System.out.println("Here is your minefield:");
            mineEngine.displayBoard(true);
            while (true) {
                System.out.print("Select a square to reveal (e.g. A1):");
                String selectedGrid = scanner.nextLine().toUpperCase();

                if (mineEngine.isValidGrid(selectedGrid)) {
                    int alphaRow = selectedGrid.charAt(0) - 'A';
                    int column = Integer.parseInt(selectedGrid.substring(1)) - 1;
                    if (mineEngine.checkMine(alphaRow, column)) {
                        System.out.println("Game over! You detonated a mine!");
                        break;

                    };
                    mineEngine.displayAdjMineCount(alphaRow, column);
                    mineEngine.displayBoard(false);
                    if (mineEngine.checkWin()) {
                        System.out.println("Congratulation you have won the game!");
                        break;
                    };
                } else {
                    System.out.println("Invalid input! Please enter between grid range");
                }
            }
            if (replayGamePrompt(scanner)) {
                break;
            }
        }

    }

    public static boolean replayGamePrompt(Scanner scanner) {
        System.out.print("Press any key to continue (or Q to quit): ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("Q")) {
            System.out.println("Game exited.");
            return true;
        }
        return false;
    }
}
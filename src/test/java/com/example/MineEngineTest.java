package com.example;

import com.example.model.Game;
import com.example.model.Slot;
import com.example.service.impl.MineEngineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MineEngineTest {
    private Game game;
    private MineEngineServiceImpl mineEngine;

    @BeforeEach
    void setUp() {
        // Use a small 3x3 grid for predictable unit testing
        game = new Game(3, 0);
        mineEngine = new MineEngineServiceImpl(game);
    }

    @Test
    @DisplayName("Should correctly validate grid coordinates like A1, B2")
    void testIsValidGrid() {
        assertTrue(mineEngine.isValidGrid("A1"), "A1 should be valid");
        assertTrue(mineEngine.isValidGrid("C3"), "C3 should be valid");
        assertFalse(mineEngine.isValidGrid("D4"), "D4 is out of bounds for 3x3");
        assertFalse(mineEngine.isValidGrid("1A"), "Wrong format should be invalid");
        assertFalse(mineEngine.isValidGrid(null), "Null should be invalid");
    }

    @Test
    @DisplayName("Should place the exact number of mines requested")
    void testPlaceMinesCount() {
        int minesToPlace = 3;
        mineEngine.placeMines(minesToPlace);

        int count = 0;
        for (Slot[] row : game.getGrid()) {
            for (Slot slot : row) {
                if (slot.isMine()) count++;
            }
        }
        assertEquals(minesToPlace, count, "The engine should place exactly 3 mines");
    }

    @Test
    @DisplayName("Should calculate adjacent mine counts correctly")
    void testCalMineCount() {
        // Manually place a mine at A1
        game.getGrid()[0][0].setMine(true);

        mineEngine.calMineCount();

        // The slot at A2 and B2 should now have a nearMineCount of 1
        assertEquals(1, game.getGrid()[0][1].getNearMineCount());
        assertEquals(1, game.getGrid()[1][1].getNearMineCount());
        assertEquals(1, game.getGrid()[1][0].getNearMineCount());
        // The slot at (2,2) is far away and should be 0
        assertEquals(0, game.getGrid()[2][2].getNearMineCount());

    }

    @Test
    @DisplayName("Should place the exact number of mines requested")
    void testRevealSlots() {
        //1. Manually set A1 as mine.
        game.getGrid()[0][0].setMine(true);
        mineEngine.calMineCount(); // Update the numbers so neighbors know there is a mine

        //2. Select A2 to reveal
        mineEngine.revealGrid(0,1);
        //3. Check all the adjacent slot if its revealed
        assertTrue(game.getGrid()[0][1].isRevealed(),"A2 should reveal");
        assertFalse(game.getGrid()[1][1].isRevealed(),"B2 is adj to mine but should not be revealed");
        assertFalse(game.getGrid()[2][0].isRevealed(), "C1 is not adj to the mine");
    }

    @Test
    @DisplayName("Should place the exact number of mines requested")
    void testRevealZeroSlot() {
        //1. Manually set A1 as mine.
        game.getGrid()[0][1].setMine(true);
        mineEngine.calMineCount(); // Update the numbers so neighbors know there is a mine

        //2. Select C3 to reveal
        mineEngine.revealGrid(2,2);
        //3. Check all the adjacent slot if its revealed
        assertFalse(game.getGrid()[0][0].isRevealed(),"A1 Should not reveal");
        assertFalse(game.getGrid()[0][2].isRevealed(),"A2 Should not reveal");
        assertTrue(game.getGrid()[1][1].isRevealed(),"B2 Should reveal");
        assertTrue(game.getGrid()[2][0].isRevealed(),"C1 Should reveal");
        assertTrue(game.getGrid()[2][2].isRevealed(), "C3 should reveal as its selected");
    }

    @Test
    @DisplayName("Should detect a win when all non-mine slots are revealed")
    void testCheckWin() {
        // A 3x3 grid with 0 mines has 9 slots to reveal
        for (int i = 0; i < 9; i++) {
            game.minusSlotLeftCount();
        }

        // check if able to detect a win condition
        assertTrue(mineEngine.checkWin(), "Game should be won when 0 slots are left");
    }

    @Test
    @DisplayName("Should return true when a mine is hit")
    void testCheckMineHit() {
        game.getGrid()[0][0].setMine(true);
        assertTrue(mineEngine.checkMine(0, 0), "checkMine should return true if the slot is a mine");
    }
}

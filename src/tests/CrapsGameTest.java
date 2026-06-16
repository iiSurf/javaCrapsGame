/*
 * Craps Game Tests
 */

package tests;

import model.CrapsGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the CrapsGame model.
 * This class contains unit tests to verify the correct behavior of the Craps game logic,
 * including betting, bank account management, dice rolling, and win/loss scenarios.
 *
 * @author Nicholas Humeniuk Sandberg
 * @version November/December 2025
 */
public class CrapsGameTest {

    /** The CrapsGame instance used for testing. */
    private CrapsGame myGame;

    /**
     * Sets up the test fixture before each test method.
     * Creates a new CrapsGame instance for testing.
     */
    @BeforeEach
    public void setUp() {
        myGame = new CrapsGame();
    }

    /**
     * Tests that the bank account is correctly set to the specified amount.
     */
    @Test
    public void testSetBankAccount() {
        myGame.setBankAccount(100);
        assertEquals(100, myGame.getBankAccount(), "Bank account should be 100");
    }

    /**
     * Tests that placing a bet correctly deducts from the bank account
     * and sets the current bet amount.
     */
    @Test
    public void testPlaceBet() {
        myGame.setBankAccount(100);
        myGame.placeBet(20);

        assertEquals(80, myGame.getBankAccount(), "Bank should be 80 after betting 20");
        assertEquals(20, myGame.getCurrentBet(), "Current bet should be 20");
    }

    /**
     * Tests that placing a bet larger than the bank balance throws an IllegalArgumentException.
     */
    @Test
    public void testBetLargerThanBalance() {
        myGame.setBankAccount(50);
        assertThrows(IllegalArgumentException.class, () -> {
            myGame.placeBet(100);
        }, "Betting more than bank balance should throw IllegalArgumentException");
    }

    /**
     * Tests that placing a negative bet throws an IllegalArgumentException.
     */
    @Test
    public void testPlaceBetNegative() {
        myGame.setBankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> {
            myGame.placeBet(-20);
        }, "Negative bet should throw IllegalArgumentException");
    }

    /**
     * Tests that winning a round correctly doubles the bet and adds it to the bank account,
     * then resets the current bet to zero.
     */
    @Test
    public void testWin() {
        myGame.setBankAccount(100);
        myGame.placeBet(20);
        myGame.creditWin();
        assertEquals(120, myGame.getBankAccount(), "Bank should be at $120 after winning");
        assertEquals(0, myGame.getCurrentBet(), "Current bet should be 0 after win");
    }

    /**
     * Tests that losing a round keeps the bank account at the reduced amount
     * (after the bet was deducted) and resets the current bet to zero.
     */
    @Test
    public void testLoss() {
        myGame.setBankAccount(100);
        myGame.placeBet(20);
        myGame.resetBet();
        assertEquals(80, myGame.getBankAccount(), "Bank should be $80 after losing bet");
        assertEquals(0, myGame.getCurrentBet(), "Current bet should be reset to zero");
    }

    /**
     * Tests that rolling a natural 7 on the first roll results in an immediate win.
     * Uses setDiceForTestingOnly to simulate rolling 3 and 4 (total 7).
     */
    @Test
    public void testNaturalWinWithSeven() {
        myGame.setBankAccount(100);
        myGame.placeBet(20);

        myGame.setDiceForTestingOnly(3, 4);
        myGame.roll();

        assertEquals(120, myGame.getBankAccount(), "Player wins with 7 on first roll");
        assertEquals(0, myGame.getCurrentBet(), "Bet should reset after win");
    }

    /**
     * Tests that rolling craps (2) on the first roll results in an immediate loss.
     * Uses setDiceForTestingOnly to simulate rolling 1 and 1 (total 2).
     */
    @Test
    public void testCrapsLoseWithTwo() {
        myGame.setBankAccount(100);
        myGame.placeBet(20);

        myGame.setDiceForTestingOnly(1, 1);
        myGame.roll();

        assertEquals(80, myGame.getBankAccount(), "Player loses with 2 on first roll");
        assertEquals(0, myGame.getCurrentBet(), "Bet should reset after loss");
    }

    /**
     * Tests that rolling a natural 11 on the first roll results in an immediate win.
     * Uses setDiceForTesting to simulate rolling 5 and 6 (total 11).
     * Includes debug output to trace the game state throughout the test.
     */
    @Test
    public void testNaturalWinWithEleven() {
        myGame.setBankAccount(100);
        System.out.println("line 1. Bank before bet: " + myGame.getBankAccount());

        myGame.placeBet(20);
        System.out.println("line 2. Bank after bet: " + myGame.getBankAccount());
        System.out.println("line 3. Current bet: " + myGame.getCurrentBet());

        myGame.setDiceForTesting(5, 6);
        System.out.println("line 4 Die1: " + myGame.getDie1() + ", Die2: " + myGame.getDie2());
        System.out.println("line 4 continued total roll: "
                + (myGame.getDie1() + myGame.getDie2()));

        myGame.roll();
        System.out.println("line 5. Bank after roll: " + myGame.getBankAccount());
        System.out.println("line 6. Current bet after roll: " + myGame.getCurrentBet());
        System.out.println("line 7. Point: " + myGame.getPoint());

        assertEquals(120, myGame.getBankAccount(), "Player wins with 11 on first roll");
        assertEquals(0, myGame.getCurrentBet(), "Bet should reset after win");
    }
}

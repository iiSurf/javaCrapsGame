/*
 * Craps Game Logic
 */

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 * Represents the game logic for the Craps game.
 * Manages dice rolls, betting, scoring, and game state.
 * Supports property change listeners for UI updates.
 *
 * @author Nicholas Humeniuk Sandberg
 * @version November/December 2025
 */
public class CrapsGame {

    /** Supports property change notifications for observers. */
    private final PropertyChangeSupport myPcs;

    /** Random number generator for dice rolls. */
    private final Random myRandom;

    /** The value of the first die. */
    private int myDie1;

    /** The value of the second die. */
    private int myDie2;

    /** The point value established in the current game. */
    private int myPoint;

    /** The total number of wins by the player. */
    private int myPlayerWins;

    /** The total number of wins by the house. */
    private int myHouseWins;

    /** Whether a game is currently in progress. */
    private boolean myGameActive;

    /** Whether the player won the most recent game. */
    private boolean myGameWon;

    /** The player's current bank account balance. */
    private int myBankAccount;

    /** The amount currently bet by the player. */
    private int myCurrentBet;

    /**
     * Constructs a new CrapsGame instance with default values.
     * Initializes dice, point, wins, bank account, and bet to zero.
     * Sets the game as inactive and not won.
     */
    public CrapsGame() {
        myPcs = new PropertyChangeSupport(this);
        myRandom = new Random();

        myDie1 = 0;
        myDie2 = 0;
        myPoint = 0;
        myPlayerWins = 0;
        myHouseWins = 0;
        myGameActive = false;
        myGameWon = false;
        myBankAccount = 0;
        myCurrentBet = 0;
    }

    /**
     * Returns the current bank account balance.
     *
     * @return the player's bank account balance
     */
    public int getBankAccount() {
        return myBankAccount;
    }

    /**
     * Returns the current bet amount.
     *
     * @return the current bet placed by the player
     */
    public int getCurrentBet() {
        return myCurrentBet;
    }

    /**
     * Returns the number of wins by the player.
     *
     * @return the total player wins
     */
    public int getPlayerWins() {
        return myPlayerWins;
    }

    /**
     * Returns the number of wins by the house.
     *
     * @return the total house wins
     */
    public int getHouseWins() {
        return myHouseWins;
    }

    /**
     * Indicates whether a game round is currently active.
     *
     * @return true if the game is active, false otherwise
     */
    public boolean isGameActive() {
        return myGameActive;
    }

    /**
     * Resets the entire game session to its initial state.
     * Clears wins, bank account, bet, point, and resets game status.
     * Fires property change events to notify listeners of the reset.
     */
    public void resetSession() {
        myPlayerWins = 0;
        myHouseWins = 0;
        myBankAccount = 0;
        myCurrentBet = 0;
        myPoint = 0;
        myGameActive = false;
        myGameWon = false;

        myPcs.firePropertyChange("playerWins", null, 0);
        myPcs.firePropertyChange("houseWins", null, 0);
        myPcs.firePropertyChange("bankAccount", null, 0);
        myPcs.firePropertyChange("currentBet", null, 0);
    }

    /**
     * Checks if the player can continue playing.
     * The player can continue if the bank account balance is greater than zero.
     *
     * @return true if the player has funds to continue, false otherwise
     */
    public boolean canContinuePlaying() {
        return myBankAccount > 0;
    }

    /**
     * Places a bet for the current round.
     * Validates that the bet is non-negative and does not exceed the bank account balance.
     * Updates the bank account and current bet values, and fires property change events.
     *
     * @param theAmount the bet amount to place
     * @throws IllegalArgumentException if the bet is less than zero or greater than the bank account
     */
    public void placeBet(final int theAmount) {
        if (theAmount < 0) {
            throw new IllegalArgumentException("Your bet can not be less than zero");
        }

        if (theAmount > myBankAccount) {
            throw new IllegalArgumentException("Your bet can not be greater than your bank account");
        }

        final int oldBankAccount = myBankAccount;
        final int oldBet = myCurrentBet;

        myBankAccount = myBankAccount - theAmount;
        myCurrentBet = theAmount;

        myPcs.firePropertyChange("bankAccount", oldBankAccount, myBankAccount);
        myPcs.firePropertyChange("currentBet", oldBet, myCurrentBet);
    }

    /**
     * Sets the player's bank account to a specified amount.
     * Fires a property change event to notify listeners of the update.
     *
     * @param theAmount the new bank account balance
     */
    public void setBankAccount(final int theAmount) {
        final int oldBankAccount = myBankAccount;
        myBankAccount = theAmount;
        myPcs.firePropertyChange("bankAccount", oldBankAccount, myBankAccount);
    }

    /**
     * Credits the player's bank account with winnings after a successful round.
     * The winnings are calculated as double the current bet. The bet is then reset to zero.
     * Property change events are fired to update listeners about the bank account and bet changes.
     */
    public void creditWin() {

        final int gameWinnings = myCurrentBet * 2;

        final int oldBankAccount = myBankAccount;
        final int oldBet = myCurrentBet;

        myBankAccount = myBankAccount + gameWinnings;

        myCurrentBet = 0;

        myPcs.firePropertyChange("bankAccount", oldBankAccount, myBankAccount);
        myPcs.firePropertyChange("currentBet", oldBet, myCurrentBet);
    }

    /**
     * Sets specific dice values for testing purposes only.
     * This bypasses random dice rolls and allows controlled outcomes.
     *
     * @param theDie1 the value to assign to die 1
     * @param theDie2 the value to assign to die 2
     */
    // TODO - FOR TESTING PURPOSES ONLY - FOR SPECIFIC DIE VALUES INSTEAD OF RANDOM
    public void setDiceForTestingOnly(final int theDie1, final int theDie2) {
        myDie1 = theDie1;
        myDie2 = theDie2;
    }

    /**
     * Returns the current value of die 1.
     *
     * @return the value of die 1
     */
    public int getDie1() {
        return myDie1;
    }

    /**
     * Returns the current value of die 2.
     *
     * @return the value of die 2
     */
    public int getDie2() {
        return myDie2;
    }

    /**
     * Returns the current point value.
     *
     * @return the point value, or 0 if no point is set
     */
    public int getPoint() {
        return myPoint;
    }

    // TODO - END OF SPECIFIC TESTS

    /**
     * Simulates a dice roll using a weighted random distribution.
     * Produces values between 1 and 6 with specific probabilities.
     *
     * @return the result of the dice roll
     */
    private int diceRoll() {
        int myDiceRoll = myRandom.nextInt(10);
        int result;
        if (myDiceRoll < 3) {
            result = 1;
        } else if (myDiceRoll < 5) {
            result = 2;
        } else if (myDiceRoll < 7) {
            result = 3;
        } else if (myDiceRoll < 8) {
            result = 4;
        } else if (myDiceRoll < 9) {
            result = 5;
        } else {
            result = 6;}
        return result;
    }

    /**
     * Entry point for testing the CrapsGame class.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    /**
     * Adds a property change listener to observe changes in game state.
     *
     * @param theListener the listener to add
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * Removes a property change listener.
     *
     * @param theListener the listener to remove
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    /**
     * Ends the current game round and updates the game state.
     * Increments player or house wins depending on the outcome.
     * Resets point and bet values, and fires property change events.
     *
     * @param thePlayerWon true if the player won, false if the house won
     */
    private void endGame(final boolean thePlayerWon) {
        int oldPlayerWins = myPlayerWins;
        int oldHouseWins = myHouseWins;

        myGameActive = false;
        myGameWon = thePlayerWon;
        myPoint = 0;

        if (thePlayerWon) {
            myPlayerWins++;
            creditWin();
            myPcs.firePropertyChange("playerWins", oldPlayerWins, myPlayerWins);
        } else {
            myHouseWins++;
            resetBet();
            myPcs.firePropertyChange("houseWins", oldHouseWins, myHouseWins);
        }

        myPcs.firePropertyChange("gameActive", true, false);
        myPcs.firePropertyChange("gameWon", !thePlayerWon, thePlayerWon);
    }

    /**
     * Resets the current bet to zero.
     * Fires a property change event to notify listeners of the update.
     */
    public void resetBet() {
        final int oldBet = myCurrentBet;
        myCurrentBet = 0;
        myPcs.firePropertyChange("currentBet", oldBet, myCurrentBet);
    }

    /**
     * Rolls the dice and updates the game state based on Craps rules.
     * Handles first roll outcomes, point setting, and subsequent rolls.
     * Fires property change events for dice values and point changes.
     */
    public void roll() {
        if (!myGameActive) {
            myGameActive = true;
        }

        final int oldDie1 = myDie1;
        final int oldDie2 = myDie2;
        final int oldPoint = myPoint;

        if (!myUseSetDice) {
            myDie1 = diceRoll();
            myDie2 = diceRoll();
        }
        myUseSetDice = false;

        final int totalRoll = myDie1 + myDie2;

        myPcs.firePropertyChange("die1", oldDie1, myDie1);
        myPcs.firePropertyChange("die2", oldDie2, myDie2);

        if (myPoint == 0) {
            if (totalRoll == 2 || totalRoll == 3 || totalRoll == 12) {
                endGame(false);
            } else if (totalRoll == 7 || totalRoll == 11) {
                endGame(true);
            } else {
                myPoint = totalRoll;
                myPcs.firePropertyChange("point", oldPoint, myPoint);
            }
        }
                else {
            if (totalRoll == myPoint) {
                endGame(true);
            }
            else if (totalRoll == 7) {
                endGame(false);
            }
        }
    }

    private boolean myUseSetDice = false;

    /**
     * Sets specific dice values for testing and marks them to be used
     * in the next roll instead of random values.
     *
     * @param theDie1 the value to assign to die 1
     * @param theDie2 the value to assign to die 2
     */
    public void setDiceForTesting(final int theDie1, final int theDie2) {
        myDie1 = theDie1;
        myDie2 = theDie2;
        myUseSetDice = true;
    }
}
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class CrapsGame {

    private final PropertyChangeSupport myPcs;

    private final Random myRandom;

    private int myDie1;

    private int myDie2;

    private int myPoint;

    private int myPlayerWins;

    private int myHouseWins;

    private boolean myGameActive;

    private boolean myGameWon;

    public CrapsGame() {
        myPcs = new PropertyChangeSupport(this);
        myRandom = new Random();

        myDie1 = 0;
        myDie2 = 0;
        myPoint = 0;
    }

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

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    private void endGame(final boolean thePlayerWon) {
        int oldPlayerWins = myPlayerWins;
        int oldHouseWins = myHouseWins;

        myGameActive = false;
        myGameWon = thePlayerWon;

        if (thePlayerWon) {
            myPlayerWins++;
            myPcs.firePropertyChange("playerWins", oldPlayerWins, myPlayerWins);
        } else {
            myPlayerWins++;
            myPcs.firePropertyChange("houseWins", oldHouseWins, myHouseWins);
        }

        myPcs.firePropertyChange("gameActive", true, false);
        myPcs.firePropertyChange("gameWon", !thePlayerWon, thePlayerWon);
    }

    // TODO - check logic later on when testing
    public void roll() {
        if (!myGameActive) {
            myGameActive = true;
        }

        final int myBothDiceSum = myDie1 + myDie2;
        final int oldDie1 = myDie1;
        final int oldDie2 = myDie2;
        final int oldPoint = myPoint;
        final int oldBothDieTotal = myBothDiceSum;

        myDie1 = diceRoll();
        myDie2 = diceRoll();
        final int totalRoll = myBothDiceSum;

        myPcs.firePropertyChange("die1", oldDie1, myDie1);
        myPcs.firePropertyChange("die2", oldDie2, oldDie2);
        myPcs.firePropertyChange("point", oldPoint, oldBothDieTotal);

        if (myPoint == 0) {

            if (oldBothDieTotal == 2 || oldBothDieTotal == 3 || oldBothDieTotal == 12) {
                endGame(false);
            } else if (oldBothDieTotal == 7 || oldBothDieTotal == 11) {
                endGame(true);
            } else {
                myPoint = oldBothDieTotal;
                myPcs.firePropertyChange("point", oldPoint, myPoint);
            }
        }
        else {
            if (oldBothDieTotal == myPoint) {
                endGame(true);
            }
            else if (oldBothDieTotal == 7) {
                endGame(false);
            }
        }
    }
}

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
}

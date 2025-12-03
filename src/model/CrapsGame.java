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



    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}

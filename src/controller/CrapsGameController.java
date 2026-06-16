/*
 * Craps Game Controller
 */

package controller;

import java.awt.EventQueue;
import model.CrapsGame;
import view.CrapsGameView;

/**
 * Controller for the Craps Game application.
 * This class contains the main method that initializes the MVC components
 * and launches the game GUI using the Event Dispatch Thread.
 *
 * @author Nicholas Humeniuk Sandberg
 * @version November/December 2025
 */
public class CrapsGameController {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CrapsGameController() {
    }

    /**
     * Main method to start the Craps Game application.
     * Creates the model and view, then displays the game window.
     *
     * @param theArgs command line arguments (not used)
     */
    public static void main(final String[] theArgs) {
        // PLAY GAME HERE, for some reason I initially tried playing it from the view.
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final CrapsGame game = new CrapsGame();
                new CrapsGameView(game);
            }
        });
    }
}

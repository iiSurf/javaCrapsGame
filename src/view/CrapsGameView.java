/*
 * Craps Game GUI View
 */

package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import model.CrapsGame;

/**
 * A graphical user interface (GUI) view for the Craps game.
 *
 * This class manages user interactions such as starting a game,
 * placing bets, rolling dice, and displaying results. It listens
 * to property changes from the CrapsGame model and updates
 * the interface accordingly.
 * Features include menu options for starting, resetting, and exiting,
 * a help menu with About and Rules dialogs, interactive buttons for
 * betting and rolling, and labels showing dice values, totals, points,
 * bank balance, bets, and win counts.
 *
 * @author Nicholas Humeniuk Sandberg
 * @version November/December 2025
 */
public class CrapsGameView extends JFrame implements PropertyChangeListener {

    /** The game model. */
    private final CrapsGame myGame;

    private JMenuItem myStartMenuItem;
    private JMenuItem myResetMenuItem;

    private JButton myRollButton;
    private JButton myPlayAgainButton;
    private JButton myPlaceBetButton;

    private JLabel myDie1Label;
    private JLabel myDie2Label;
    private JLabel myTotalLabel;
    private JLabel myPointLabel;
    private JLabel myBankLabel;
    private JLabel myBetLabel;
    private JLabel myPlayerWinsLabel;
    private JLabel myHouseWinsLabel;
    private JLabel myStatusLabel;

    private JTextField myBetField;

    private boolean myGameStarted = false;
    private boolean myBetPlaced = false;

    /**
     * Constructs the Craps view.
     * Arial
     * @param theGame the game model
     */
    public CrapsGameView(final CrapsGame theGame) {
        super("This is a Craps Game, you have money to play?");
        myGame = theGame;
        myGame.addPropertyChangeListener(this);

        setupComponents();
        setupMenu();
        setupLayout();
        setupListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes all GUI components.
     */
    private void setupComponents() {
        myRollButton = new JButton("Roll the Dice");
        myRollButton.setMnemonic(KeyEvent.VK_R);
        myRollButton.setEnabled(false);

        myPlayAgainButton = new JButton("Play Again");
        myPlayAgainButton.setMnemonic(KeyEvent.VK_P);
        myPlayAgainButton.setEnabled(false);

        myPlaceBetButton = new JButton("Place Bet");
        myPlaceBetButton.setMnemonic(KeyEvent.VK_B);
        myPlaceBetButton.setEnabled(false);

        myDie1Label = new JLabel("Die 1: --", SwingConstants.CENTER);
        myDie2Label = new JLabel("Die 2: --", SwingConstants.CENTER);
        myTotalLabel = new JLabel("Total: --", SwingConstants.CENTER);
        myPointLabel = new JLabel("Point: --", SwingConstants.CENTER);

        myBankLabel = new JLabel("Bank Account: $0.00", SwingConstants.CENTER);
        myBetLabel = new JLabel("Current Bet: $0.00", SwingConstants.CENTER);

        myPlayerWinsLabel = new JLabel("Player Wins: 0", SwingConstants.CENTER);
        myHouseWinsLabel = new JLabel("House Wins: 0", SwingConstants.CENTER);

        myStatusLabel = new JLabel(
                "Hi there! Please start a new game.", SwingConstants.CENTER);
        myStatusLabel.setFont(new Font("Futura", Font.BOLD, 14));

        // I love futura font
        final Font diceFont = new Font("Futura", Font.BOLD, 16);
        myDie1Label.setFont(diceFont);
        myDie2Label.setFont(diceFont);
        myTotalLabel.setFont(diceFont);
        myPointLabel.setFont(diceFont);

        myBetField = new JTextField(10);
        myBetField.setEnabled(false);
    }

    /**
     * Sets up the menu bar with Game and Help menus.
     */
    private void setupMenu() {
        final JMenuBar menuBar = new JMenuBar();

        menuBar.add(buildGameMenu());
        menuBar.add(buildHelpMenu());

        setJMenuBar(menuBar);
    }

    /**
     * Builds the Game menu with Start, Reset, and Exit options.
     *
     * @return the Game menu
     */
    private JMenu buildGameMenu() {
        final JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        myStartMenuItem = new JMenuItem("Start");
        myStartMenuItem.setMnemonic(KeyEvent.VK_S);
        myStartMenuItem.setAccelerator(KeyStroke.getKeyStroke
                (KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        myStartMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                startGame();
            }
        });

        myResetMenuItem = new JMenuItem("Reset Game");
        myResetMenuItem.setMnemonic(KeyEvent.VK_R);
        myResetMenuItem.setAccelerator(KeyStroke.getKeyStroke
                (KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        myResetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                resetSession();
            }
        });

        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                exitGame();
            }
        });

        gameMenu.add(myStartMenuItem);
        gameMenu.add(myResetMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        return gameMenu;
    }

    /**
     * Builds the Help menu with About and Rules options.
     *
     * @return the Help menu
     */
    private JMenu buildHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic(KeyEvent.VK_A);
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                showAbout();
            }
        });

        final JMenuItem rulesItem = new JMenuItem("Rules");
        rulesItem.setMnemonic(KeyEvent.VK_U);
        rulesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
        rulesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                showRules();
            }
        });

        helpMenu.add(aboutItem);
        helpMenu.add(rulesItem);

        return helpMenu;
    }

    /**
     * Sets up the layout of all components.
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Top panel - Bank and Bets
        final JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Your Bank 0_o"));
        topPanel.add(myBankLabel);

        final JPanel betInputPanel = new JPanel();
        betInputPanel.add(new JLabel("Enter Bet Amount: $"));
        betInputPanel.add(myBetField);
        betInputPanel.add(myPlaceBetButton);
        topPanel.add(betInputPanel);

        topPanel.add(myBetLabel);

        // Center panel - Game stuff
        final JPanel centerPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("The Game :D"));
        centerPanel.add(myDie1Label);
        centerPanel.add(myDie2Label);
        centerPanel.add(myTotalLabel);
        centerPanel.add(myPointLabel);
        centerPanel.add(myPlayerWinsLabel);
        centerPanel.add(myHouseWinsLabel);

        // Bottom panel - Buttons and Status
        final JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(myRollButton);
        buttonPanel.add(myPlayAgainButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(myStatusLabel, BorderLayout.SOUTH);

        // combine
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up action listeners for buttons.
     */
    private void setupListeners() {
        myPlaceBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                placeBet();
            }
        });

        myRollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                rollDice();
            }
        });

        myPlayAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                playAgain();
            }
        });
    }

    /**
     * Starts a new game session by prompting for initial bank account.
     */
    private void startGame() {
        final String input = JOptionPane.showInputDialog(this,
                "Enter starting bank account amount:",
                "Start Game",
                JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                final int amount = Integer.parseInt(input.trim());
                if (amount < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Bank account must be a positive integer!",
                            "Not a valid Input",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                myGame.setBankAccount(amount);
                myGameStarted = true;
                myBetField.setEnabled(true);
                myPlaceBetButton.setEnabled(true);
                myStartMenuItem.setEnabled(false);
                myStatusLabel.setText("Game started! Place your bet and lets play!.");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Please enter an integer number!",
                        "Not real Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Places a bet for the current round.
     */
    private void placeBet() {
        final String betText = myBetField.getText().trim();

        if (betText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a bet amount!",
                    "Add money please :)",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            final int betAmount = Integer.parseInt(betText);

            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Bet must be greater than zero!",
                        "What was that? Not a real Bet",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (betAmount > myGame.getBankAccount()) {
                JOptionPane.showMessageDialog(this,
                        "Bet cannot be more than what you have in your bank!\nBank Account: $" + myGame.getBankAccount(),
                        "Invalid Bet",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Place the bet
            myGame.placeBet(betAmount);
            myBetPlaced = true;

            // Update UI
            myBetField.setEnabled(false);
            myPlaceBetButton.setEnabled(false);
            myRollButton.setEnabled(true);
            myStatusLabel.setText("Bet placed! Roll the dice to start.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number!",
                    "Not a real Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "That is not a bet D:",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rolls the dice and updates the game state.
     */
    private void rollDice() {
        CrapsGameSounds.playSoundAsync("roll.wav");
        myGame.roll();
        myRollButton.setEnabled(myGame.isGameActive());
    }

    private void playAgain() {
        // Check if player can continue
        if (!myGame.canContinuePlaying()) {
            JOptionPane.showMessageDialog(this,
                    "Oh no! Game Over! Your bank account is empty.\nPlease reset and play again.",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            myPlayAgainButton.setEnabled(false);
            myResetMenuItem.setEnabled(true);
            return;
        }

        // Reset for new round
        myBetPlaced = false;
        myBetField.setText("");
        myBetField.setEnabled(true);
        myPlaceBetButton.setEnabled(true);
        myPlayAgainButton.setEnabled(false);
        myRollButton.setEnabled(false);

        // Clear dice gui
        myDie1Label.setText("Die 1: ");
        myDie2Label.setText("Die 2: ");
        myTotalLabel.setText("Total: ");
        myPointLabel.setText("Point: ");

        myStatusLabel.setText("Place your bet for the next round.");
    }

    /**
     * Resets the entire game session.
     */
    private void resetSession() {
        final int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset the entire game?\nOnly if you really want to.",
                "Reset Session",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            myGame.resetSession();

            // Reset UI
            myGameStarted = false;
            myBetPlaced = false;

            myBetField.setText("");
            myBetField.setEnabled(false);
            myPlaceBetButton.setEnabled(false);
            myRollButton.setEnabled(false);
            myPlayAgainButton.setEnabled(false);

            myStartMenuItem.setEnabled(true);

            // Clear all displays
            myDie1Label.setText("Die 1: Let's");
            myDie2Label.setText("Die 2: Play");
            myTotalLabel.setText("Total: a Game");
            myPointLabel.setText("Point: of Craps!");
            myBankLabel.setText("Bank Account: $0.00");
            myBetLabel.setText("Current Bet: $0.00");
            myPlayerWinsLabel.setText("Player Wins: 0");
            myHouseWinsLabel.setText("House Wins: 0");

            myStatusLabel.setText("Game has reset. Start a new game to play!");
        }
    }

    /**
     * Exits the application after confirmation.
     */
    private void exitGame() {
        final int result = JOptionPane.showConfirmDialog(this,
                "Are you really, really, really sure you want to leave?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Displays information about the application.
     */
    private void showAbout() {
        final String aboutMessage = "Craps Game\n" +
                "Version 0.1.1\n\n" +
                "Java Version: 25.0.1" + "\n\n" +
                "Made by: https://www.linkedin.com/in/nick-hsd\n\n" +
                "A craps casino game.";

        JOptionPane.showMessageDialog(this,
                aboutMessage,
                "About Craps Game",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the rules of the game.
     */
    private void showRules() {
        final String rules = "CRAPS RULES:\n\n" +
                "FIRST ROLL:\n" +
                "• Roll 7 or 11: You win!\n" +
                "• Roll 2, 3, or 12: You lose! House wins!\n" +
                "• If you roll a 4, 5, 6, 8, 9, or 10: Becomes your point.\n\n" +
                "AFTER POINT IS SET:\n" +
                "• Roll your point again: You Win! House Loses!\n" +
                "• Roll a 7: You lose. House wins!\n" +
                "• Any other number: Keep rolling\n\n" +
                "BETTING:\n" +
                "• Place a bet before each round\n" +
                "• If you win, your bet is doubled and added to your bank\n" +
                "• If you lose, your bet is lost\n" +
                "• Game ends when your bank account reaches $0. Which happens a lot :)";

        JOptionPane.showMessageDialog(this,
                rules,
                "Game Rules",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Responds to property changes from the model.
     *
     * @param theEvent the property change event
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String propertyName = theEvent.getPropertyName();

        if ("die1".equals(propertyName)) {
            myDie1Label.setText("Die 1: " + theEvent.getNewValue());
            updateTotal();
        } else if ("die2".equals(propertyName)) {
            myDie2Label.setText("Die 2: " + theEvent.getNewValue());
            updateTotal();
        } else if ("point".equals(propertyName)) {
            final int point = (Integer) theEvent.getNewValue();
            if (point == 0) {
                myPointLabel.setText("Point: --");
            } else {
                myPointLabel.setText("Point: " + point);
                myStatusLabel.setText("Point is " + point + ". Roll again!");
            }
        } else if ("bankAccount".equals(propertyName)) {
            myBankLabel.setText("Bank Account: $" + theEvent.getNewValue());
        } else if ("currentBet".equals(propertyName)) {
            myBetLabel.setText("Current Bet: $" + theEvent.getNewValue());
        } else if ("playerWins".equals(propertyName)) {
            myPlayerWinsLabel.setText("Player Wins: " + theEvent.getNewValue());
        } else if ("houseWins".equals(propertyName)) {
            myHouseWinsLabel.setText("House Wins: " + theEvent.getNewValue());
        } else if ("gameActive".equals(propertyName)) {
            final boolean isActive = (Boolean) theEvent.getNewValue();
            myRollButton.setEnabled(isActive);

            if (!isActive) {
                // Game has ended
                myPlayAgainButton.setEnabled(true);
            }
        } else if ("gameWon".equals(propertyName)) {
            final boolean playerWon = (Boolean) theEvent.getNewValue();
            if (playerWon) {
                CrapsGameSounds.playSoundAsync("win.wav");
                myStatusLabel.setText("YOU WIN!");
                myStatusLabel.setForeground(Color.GREEN.darker());
            } else {
                CrapsGameSounds.playSoundAsync("lose.wav");
                myStatusLabel.setText("House wins. More than free to try again!");
                myStatusLabel.setForeground(Color.RED.darker());
            }

            // Reset status color after a moment
            javax.swing.Timer timer = new javax.swing.Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myStatusLabel.setForeground(Color.BLACK);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Updates the total label based on current die values.
     */
    private void updateTotal() {
        final int die1 = myGame.getDie1();
        final int die2 = myGame.getDie2();

        if (die1 > 0 && die2 > 0) {
            myTotalLabel.setText("Total: " + (die1 + die2));
        }
    }
}

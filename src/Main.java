package src;

import java.awt.*;
import javax.swing.*;

public class Main {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    
    // Game Data
    private String selectedGround;
    private String playerName; // 1. Added variable to save name

    // Screens
    private final SplashScreen splashScreen; // First Screen (Image)
    private final NameInput nameInput;       // Second Screen (Input)
    
    private final InitialScreenPanel initialScreen;
    private final GroundsScreenPanel groundsScreen;
    private final ASStageSelectionPanel asStageSelection;
    private final DMStageSelectionPanel dmStageSelection;
    private final Instructions howToPlayScreen;
    private final GameplayScreen gameplayScreen;

    public Main() {
        frame = new JFrame("Isko Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Initialize Panels ---
        splashScreen = new SplashScreen();
        nameInput = new NameInput(this); // 2. Initialize NameInput
        
        initialScreen = new InitialScreenPanel(this);
        groundsScreen = new GroundsScreenPanel(this);
        asStageSelection = new ASStageSelectionPanel(this);
        dmStageSelection = new DMStageSelectionPanel(this);
        howToPlayScreen = new Instructions(this);
        gameplayScreen = new GameplayScreen(this);

        // --- Add to CardLayout ---
        mainPanel.add(splashScreen, "Splash");     // 1st
        mainPanel.add(nameInput, "NameInput");     // 2nd
        mainPanel.add(initialScreen, "Initial");   // 3rd
        
        mainPanel.add(groundsScreen, "Grounds");
        mainPanel.add(asStageSelection, "ASStages");
        mainPanel.add(dmStageSelection, "DMStages");
        mainPanel.add(howToPlayScreen, "HowToPlay");
        mainPanel.add(gameplayScreen, "Gameplay");

        frame.setContentPane(mainPanel);
        frame.setSize(1440, 1024);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // --- Start the Sequence ---
        runStartupSequence();
    }

    public Main(ASStageSelectionPanel asStageSelection, CardLayout cardLayout, DMStageSelectionPanel dmStageSelection, JFrame frame, GameplayScreen gameplayScreen, GroundsScreenPanel groundsScreen, Instructions howToPlayScreen, InitialScreenPanel initialScreen, JPanel mainPanel, NameInput nameInput, SplashScreen splashScreen) {
        this.asStageSelection = asStageSelection;
        this.cardLayout = cardLayout;
        this.dmStageSelection = dmStageSelection;
        this.frame = frame;
        this.gameplayScreen = gameplayScreen;
        this.groundsScreen = groundsScreen;
        this.howToPlayScreen = howToPlayScreen;
        this.initialScreen = initialScreen;
        this.mainPanel = mainPanel;
        this.nameInput = nameInput;
        this.splashScreen = splashScreen;
    }

    private void runStartupSequence() {
        // Show the Splash Screen immediately
        showScreen("Splash");

        // Wait 3 Seconds (3000ms), then switch to Name Input
        Timer timer = new Timer(3000, e -> {
            showScreen("NameInput");
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void showScreen(String name) {
        if (name.equals("HowToPlay")) {
            howToPlayScreen.reset();
        }
        // Focus the text field when NameInput appears
        if (name.equals("NameInput")) {
            nameInput.reset();
        }
        cardLayout.show(mainPanel, name);
    }

    // --- Data Methods ---
    public void setPlayerName(String name) {
        this.playerName = name;
        System.out.println("Player Name Saved: " + this.playerName);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setSelectedGround(String ground) {
        this.selectedGround = ground;
    }

    public String getSelectedGround() {
        return selectedGround;
    }

    public void startGameplay(String ground, int stage){
        gameplayScreen.startStage(ground, stage);
        showScreen("Gameplay");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
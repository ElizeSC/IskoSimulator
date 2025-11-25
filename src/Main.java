package src;

import java.awt.*;
import javax.swing.*;

public class Main {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    
    // Game Data
    private String selectedGround;
    private String playerName;
    
    // Music Manager
    private final MusicManager musicManager;

    // Screens
    private final SplashScreen splashScreen;
    private final NameInput nameInput;
    private final InitialScreenPanel initialScreen;
    private final GroundsScreenPanel groundsScreen;
    private final ASStageSelectionPanel asStageSelection;
    private final DMStageSelectionPanel dmStageSelection;
    private final Instructions howToPlayScreen;
    private final GameplayScreen gameplayScreen;

    public Main() {
        frame = new JFrame("Isko Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize Music Manager
        musicManager = new MusicManager();
        
        // Add window listener to cleanup music when closing
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                musicManager.dispose();
            }
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Initialize Panels ---
        splashScreen = new SplashScreen();
        nameInput = new NameInput(this);
        initialScreen = new InitialScreenPanel(this);
        groundsScreen = new GroundsScreenPanel(this);
        asStageSelection = new ASStageSelectionPanel(this);
        dmStageSelection = new DMStageSelectionPanel(this);
        howToPlayScreen = new Instructions(this);
        gameplayScreen = new GameplayScreen(this);

        // --- Add to CardLayout ---
        mainPanel.add(splashScreen, "Splash");
        mainPanel.add(nameInput, "NameInput");
        mainPanel.add(initialScreen, "Initial");
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
        this.musicManager = new MusicManager();
    }

    private void runStartupSequence() {
        // Show the Splash Screen immediately
        showScreen("Splash");
        
        // Start intro music (plays during Splash + Name Entry)
        musicManager.playMusic("assets/music/susunod-na-habang-buhay.wav");

        // Wait 3 Seconds (3000ms), then switch to Name Input
        Timer timer = new Timer(3000, e -> {
            showScreen("NameInput");
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void showScreen(String name) {
        // Handle screen-specific logic
        if (name.equals("HowToPlay")) {
            howToPlayScreen.reset();
        }
        if (name.equals("NameInput")) {
            nameInput.reset();
        }
        
        // Switch to game music when entering the main game
        if (name.equals("Initial")) {
            musicManager.playMusic("assets/music/multo.wav");
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
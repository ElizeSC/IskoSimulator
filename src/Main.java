package src;

import java.awt.*;
import javax.swing.*;

public class Main {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    
    // Game Data
    private String selectedGround;
    private GameState gameState;
    private String playerName = "Player";
    
    // Music Manager
    private final MusicManager musicManager;

    // Screens
    private final SplashScreen splashScreen;
    private final NameInput nameInput;
    private final InitialScreenPanel initialScreen;
    private final GroundsScreenPanel groundsScreen;
    private ASStageSelectionPanel asStageSelection;
    private DMStageSelectionPanel dmStageSelection;
    private final Instructions howToPlayScreen;
    private final GameplayScreen gameplayScreen;
    private LeaderboardManager leaderboardManager;
    private LeaderboardPanel leaderboardPanel;
    
    // [Updated] Game Over and Stage Complete Panels
    private final GameOverPanel gameOverScreen;
    private final StageCompletePanel stageCompleteScreen; 

    public Main() {
        // 1. Create Frame First
        frame = new JFrame("Isko Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Initialize CardLayout and Main Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 3. Initialize Managers
        musicManager = new MusicManager();
        leaderboardManager = new LeaderboardManager();
        gameState = new GameState("Player");

        // 4. Add window listener
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                musicManager.dispose();
            }
        });

        // 5. Initialize Screens
        splashScreen = new SplashScreen();
        nameInput = new NameInput(this);
        initialScreen = new InitialScreenPanel(this);
        groundsScreen = new GroundsScreenPanel(this);
        howToPlayScreen = new Instructions(this);
        gameplayScreen = new GameplayScreen(this);
        asStageSelection = new ASStageSelectionPanel(this, gameState);
        dmStageSelection = new DMStageSelectionPanel(this, gameState);
        leaderboardPanel = new LeaderboardPanel(this, leaderboardManager);
        
        // Initialize Custom Panels
        stageCompleteScreen = new StageCompletePanel(this); 
        gameOverScreen = new GameOverPanel(this);

        // 6. Add screens to CardLayout
        mainPanel.add(splashScreen, "Splash");
        mainPanel.add(nameInput, "NameInput");
        mainPanel.add(initialScreen, "Initial");
        mainPanel.add(groundsScreen, "Grounds");
        mainPanel.add(asStageSelection, "ASStages");
        mainPanel.add(dmStageSelection, "DMStages");
        mainPanel.add(howToPlayScreen, "HowToPlay");
        mainPanel.add(gameplayScreen, "Gameplay");
        mainPanel.add(leaderboardPanel, "Leaderboard");
        mainPanel.add(stageCompleteScreen, "StageComplete");
        mainPanel.add(gameOverScreen, "GameOver");

        // 7. Finalize frame
        frame.setContentPane(mainPanel);
        frame.setSize(1440, 1024);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 8. Run Startup Sequence
        runStartupSequence();
    }

    public void showStageComplete(GameState currentState) {
        stageCompleteScreen.updateStats(currentState);
        cardLayout.show(mainPanel, "StageComplete");
    }

    // [Updated] This ensures your custom panel gets the score before showing
    public void showGameOver(GameState currentState) {
        gameOverScreen.setMessage("You ran out of sunflowers!"); // Reset message to default loss text
        gameOverScreen.updateScore(currentState.getTotalScore());
        cardLayout.show(mainPanel, "GameOver");
    }

    public void showLeaderboard() {
        leaderboardPanel.refresh();
        showScreen("Leaderboard");
    }

    private void runStartupSequence() {
        showScreen("Splash");
        musicManager.playMusic("assets/music/multo.wav");

        Timer timer = new Timer(3500, e -> {
            showScreen("NameInput");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateStageSelectionPanels() {
        mainPanel.remove(asStageSelection);
        mainPanel.remove(dmStageSelection);
    
        asStageSelection = new ASStageSelectionPanel(this, gameState);
        dmStageSelection = new DMStageSelectionPanel(this, gameState);
    
        mainPanel.add(asStageSelection, "ASStages");
        mainPanel.add(dmStageSelection, "DMStages");
    
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showScreen(String name) {
        if (name.equals("ASStages") || name.equals("DMStages")) {
            updateStageSelectionPanels();
        } 
        if (name.equals("HowToPlay")) {
            howToPlayScreen.reset();
        }
        if (name.equals("NameInput")) {
            nameInput.reset();
        }
        if (name.equals("Initial")) {
            musicManager.playMusic("assets/music/game-music.wav");
        }
        
        cardLayout.show(mainPanel, name);
    }

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
        gameplayScreen.startStage(ground, stage, gameState);
        showScreen("Gameplay");
    }

    public GameState getGameState() {
        return gameState;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public LeaderboardManager getLeaderboardManager() {
        return leaderboardManager;
    }
}
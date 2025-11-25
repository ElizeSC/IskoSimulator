package src;

import java.awt.*;
import javax.swing.*;

public class Main {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private String selectedGround;

    private GameState gameState;

    private final InitialScreenPanel initialScreen;
    private final GroundsScreenPanel groundsScreen;
    private ASStageSelectionPanel asStageSelection;
    private DMStageSelectionPanel dmStageSelection;
    private final Instructions howToPlayScreen;
    private final GameplayScreen gameplayScreen;

    public Main() {
        frame = new JFrame("Isko Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gameState = new GameState("Player");

        // Initialize panels
        initialScreen = new InitialScreenPanel(this);
        groundsScreen = new GroundsScreenPanel(this);
        howToPlayScreen = new Instructions(this);
        gameplayScreen = new GameplayScreen(this);

        // Initialize stage selection panels directly
        asStageSelection = new ASStageSelectionPanel(this, gameState);
        //dmStageSelection = new DMStageSelectionPanel(this, gameState);

        // Add to CardLayout
        mainPanel.add(initialScreen, "Initial");
        mainPanel.add(groundsScreen, "Grounds");
        mainPanel.add(asStageSelection, "ASStages");
        //mainPanel.add(dmStageSelection, "DMStages");
        mainPanel.add(howToPlayScreen, "HowToPlay");
        mainPanel.add(gameplayScreen, "Gameplay");

        frame.setContentPane(mainPanel);
        frame.setSize(1440, 1024);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Refresh stage panels to show unlocked stages
    private void updateStageSelectionPanels() {
        // Remove old panels
        mainPanel.remove(asStageSelection);
        //mainPanel.remove(dmStageSelection);
        
        // Create new panels with updated game state
        asStageSelection = new ASStageSelectionPanel(this, gameState);
        //dmStageSelection = new DMStageSelectionPanel(this, gameState);

        // Add new panels
        mainPanel.add(asStageSelection, "ASStages");
        //mainPanel.add(dmStageSelection, "DMStages");
        
        // Revalidate to apply changes
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
        cardLayout.show(mainPanel, name);
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
}
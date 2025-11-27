package src;

import java.awt.*;
import javax.swing.*;

public class StageCompletePanel extends JPanel {
    private Main mainApp;
    private JLabel stageLabel;
    private JLabel scoreLabel;
    private JLabel bonusLabel;
    private JLabel totalScoreLabel;
    private JLabel sunflowersLabel;
    private JLabel powerupsLabel;
    private JButton continueBtn;
    private JButton nextStageBtn;

    private String currentGround;
    private int currentStage;

    public StageCompletePanel(Main mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/AS-1-1.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout());

        // Main container with rounded background
        RoundedPanel container = new RoundedPanel(30);
        container.setBackground(new Color(0, 0, 0, 180));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Title
        JLabel title = new JLabel("STAGE COMPLETE!", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 48));
        title.setForeground(new Color(255, 215, 0)); // Gold color
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stage info
        stageLabel = new JLabel("Stage: AS-1");
        stageLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        stageLabel.setForeground(Color.WHITE);
        stageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score breakdown
        JPanel scoresPanel = new JPanel();
        scoresPanel.setOpaque(false);
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("Stage Score: 0");
        scoreLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bonusLabel = new JLabel("Bonus: 0");
        bonusLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        bonusLabel.setForeground(new Color(144, 238, 144)); // Light green
        bonusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sunflowersLabel = new JLabel("Sunflowers: 3 x 100 = 300");
        sunflowersLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        sunflowersLabel.setForeground(Color.LIGHT_GRAY);
        sunflowersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        powerupsLabel = new JLabel("Power-ups Saved: 3 x 50 = 150");
        powerupsLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        powerupsLabel.setForeground(Color.LIGHT_GRAY);
        powerupsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Total score
        totalScoreLabel = new JLabel("Total Score: 0");
        totalScoreLabel.setFont(new Font("Poppins", Font.BOLD, 32));
        totalScoreLabel.setForeground(new Color(255, 215, 0)); // Gold
        totalScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setOpaque(false);

        continueBtn = createStyledButton("Main Menu");
        continueBtn.addActionListener(e -> mainApp.showScreen("Initial"));

        nextStageBtn = createStyledButton("Continue");
        // Action will be set dynamically in updateStats()

        buttonsPanel.add(continueBtn);
        buttonsPanel.add(nextStageBtn);

        // Add components to container
        container.add(title);
        container.add(Box.createVerticalStrut(20));
        container.add(stageLabel);
        container.add(Box.createVerticalStrut(30));
        container.add(scoreLabel);
        container.add(Box.createVerticalStrut(10));
        container.add(bonusLabel);
        container.add(Box.createVerticalStrut(5));
        container.add(sunflowersLabel);
        container.add(Box.createVerticalStrut(5));
        container.add(powerupsLabel);
        container.add(Box.createVerticalStrut(30));
        container.add(totalScoreLabel);
        container.add(Box.createVerticalStrut(40));
        container.add(buttonsPanel);

        bg.add(container);
        add(bg, BorderLayout.CENTER);
    }

    // Replace the updateStats method in StageCompletePanel.java:

public void updateStats(GameState gameState) {
    String ground = gameState.getCurrentGround();
    int stage = gameState.getCurrentStage();
    
    this.currentGround = ground;
    this.currentStage = stage;

    stageLabel.setText("Stage: " + ground + "-" + stage);
    
    int stageScore = gameState.getCurrentStageScore();
    int bonus = gameState.calculateStageBonus();
    int sunflowers = gameState.getSunflowers();
    
    int powerupsSaved = 0;
    if (gameState.isLatteAvailable()) powerupsSaved++;
    if (gameState.isMacchiatoAvailable()) powerupsSaved++;
    if (gameState.isAmericanoAvailable()) powerupsSaved++;
    
    // Calculate stage score before bonus
    int scoreBeforeBonus = stageScore - bonus;
    
    scoreLabel.setText("Stage Score: " + scoreBeforeBonus);
    bonusLabel.setText("Bonus: " + bonus);
    sunflowersLabel.setText("Sunflowers: " + sunflowers + " × 100 = " + (sunflowers * 100));
    powerupsLabel.setText("Power-ups Saved: " + powerupsSaved + " × 50 = " + (powerupsSaved * 50));
    totalScoreLabel.setText("Total Score: " + gameState.getTotalScore());
    
    // Debug output - VERY DETAILED
    System.out.println("=== STAGE COMPLETE SCREEN ===");
    System.out.println("Current Ground: " + ground);
    System.out.println("Current Stage: " + stage);
    System.out.println("---AS Stages---");
    System.out.println("  AS-1 Complete: " + (ground.equals("AS") && stage >= 1));
    System.out.println("  AS-2 Complete: " + (ground.equals("AS") && stage >= 2));
    System.out.println("  AS-3 Complete: " + (ground.equals("AS") && stage >= 3));
    System.out.println("---DM Stages---");
    System.out.println("  DM-1 Complete: " + (ground.equals("DM") && stage >= 1));
    System.out.println("  DM-2 Complete: " + (ground.equals("DM") && stage >= 2));
    System.out.println("  DM-3 Complete: " + (ground.equals("DM") && stage >= 3));
    System.out.println("---Ground Completion---");
    System.out.println("AS Complete: " + gameState.isGroundComplete("AS"));
    System.out.println("DM Complete: " + gameState.isGroundComplete("DM"));
    System.out.println("---GAME STATUS---");
    System.out.println("Game Complete: " + gameState.isGameComplete());
    
    // Update button actions based on game state
    updateContinueButton(gameState);
    
    // Check if game is complete - USE LONGER DELAY
    if (gameState.isGameComplete()) {
        System.out.println("✅✅✅ ALL 6 STAGES COMPLETE! ✅✅✅");
        System.out.println("Showing winning screen in 3 seconds...");
        
        // Disable buttons to prevent double-clicking
        nextStageBtn.setEnabled(false);
        continueBtn.setEnabled(false);
        
        Timer showWinningTimer = new Timer(3000, evt -> {
            System.out.println("Transitioning to Winning Screen NOW!");
            mainApp.showWinningScreen(gameState);
        });
        showWinningTimer.setRepeats(false);
        showWinningTimer.start();
    } else {
        System.out.println("❌ Game not complete yet. Continue playing.");
        // Make sure buttons are enabled
        nextStageBtn.setEnabled(true);
        continueBtn.setEnabled(true);
    }
}

    private void updateContinueButton(GameState gameState) {
        // Remove old action listeners
        for (var listener : nextStageBtn.getActionListeners()) {
            nextStageBtn.removeActionListener(listener);
        }
        
        // Set new action based on current state
        if (currentStage == 3) {
            // Just completed the 3rd stage of a ground
            boolean currentGroundComplete = gameState.isGroundComplete(currentGround);
            
            if (currentGroundComplete) {
                // All 3 stages of this ground are done - go to Grounds selection
                nextStageBtn.setText("Choose Next Ground");
                nextStageBtn.addActionListener(e -> {
                    System.out.println("All " + currentGround + " stages complete. Going to Grounds selection.");
                    mainApp.showScreen("Grounds");
                });
            } else {
                // Shouldn't happen, but go to stage selection as fallback
                nextStageBtn.setText("Continue");
                nextStageBtn.addActionListener(e -> {
                    if (currentGround.equals("AS")) {
                        mainApp.showScreen("ASStages");
                    } else {
                        mainApp.showScreen("DMStages");
                    }
                });
            }
        } else {
            // Not on stage 3 yet - continue to next stage in same ground
            nextStageBtn.setText("Next Stage");
            nextStageBtn.addActionListener(e -> {
                if (currentGround.equals("AS")) {
                    mainApp.showScreen("ASStages");
                } else if (currentGround.equals("DM")) {
                    mainApp.showScreen("DMStages");
                }
            });
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }

    private class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            super();
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
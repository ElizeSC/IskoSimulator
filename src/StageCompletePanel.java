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

        JButton continueBtn = createStyledButton("Main Menu");
        continueBtn.addActionListener(e -> mainApp.showScreen("Initial"));

        JButton nextStageBtn = createStyledButton("Stage Selection");
        nextStageBtn.addActionListener(e -> {
            // [Updated] Go to ASStages map for next level selection
            mainApp.showScreen("ASStages");
        });

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

    public void updateStats(GameState gameState) {
        String ground = gameState.getCurrentGround();
        int stage = gameState.getCurrentStage();
        
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
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 50));
        
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
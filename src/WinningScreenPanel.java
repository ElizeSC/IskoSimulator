package src;

import java.awt.*;
import javax.swing.*;

public class WinningScreenPanel extends JPanel {
    private Main mainApp;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    public WinningScreenPanel(Main mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        // Background - using the same backdrop as gameplay
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/AS-1-1.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout());

        // Sunflower decorations at bottom
        ImageIcon sunflowerIcon = new ImageIcon("assets/objects/sunflower.png");
        JPanel sunflowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        sunflowerPanel.setOpaque(false);
        
        // Add multiple sunflowers
        for (int i = 0; i < 15; i++) {
            JLabel sunflower = new JLabel(sunflowerIcon);
            sunflowerPanel.add(sunflower);
        }

        // Main container with rounded background
        RoundedPanel container = new RoundedPanel(30);
        container.setBackground(new Color(255, 248, 220, 230)); // Cornsilk with transparency
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 4), // Dark goldenrod border
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        // Title - "congratulations!" style
        JLabel title = new JLabel("congratulations!", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 64));
        title.setForeground(new Color(220, 20, 60)); // Crimson red with yellow outline effect
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("You completed all stages!", SwingConstants.CENTER);
        subtitle.setFont(new Font("Poppins", Font.PLAIN, 28));
        subtitle.setForeground(new Color(0, 0, 0));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score display
        scoreLabel = new JLabel("Score: 12345");
        scoreLabel.setFont(new Font("Poppins", Font.BOLD, 40));
        scoreLabel.setForeground(new Color(0, 0, 0));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // High score indicator
        highScoreLabel = new JLabel("New High Score!");
        highScoreLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        highScoreLabel.setForeground(new Color(255, 140, 0)); // Dark orange
        highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreLabel.setVisible(false); // Hidden by default

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setOpaque(false);

        JButton playAgainBtn = createStyledButton("Play again");
        playAgainBtn.addActionListener(e -> {
            mainApp.resetGame();
            mainApp.showScreen("Grounds");
        });

        JButton mainMenuBtn = createStyledButton("Main Menu");
        mainMenuBtn.addActionListener(e -> {
            mainApp.resetGame();
            mainApp.showScreen("Initial");
        });

        buttonsPanel.add(playAgainBtn);
        buttonsPanel.add(mainMenuBtn);

        // Add components to container
        container.add(Box.createVerticalStrut(20));
        container.add(title);
        container.add(Box.createVerticalStrut(10));
        container.add(subtitle);
        container.add(Box.createVerticalStrut(30));
        container.add(scoreLabel);
        container.add(Box.createVerticalStrut(10));
        container.add(highScoreLabel);
        container.add(Box.createVerticalStrut(40));
        container.add(buttonsPanel);
        container.add(Box.createVerticalStrut(20));

        // Layer the components
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContainer.add(Box.createVerticalGlue());
        mainContainer.add(container);
        mainContainer.add(Box.createVerticalStrut(20));
        
        bg.add(mainContainer);

        // Add sunflowers at the bottom
        add(bg, BorderLayout.CENTER);
        add(sunflowerPanel, BorderLayout.SOUTH);
    }

    public void updateScore(int finalScore, boolean isHighScore) {
        scoreLabel.setText("Score: " + finalScore);
        highScoreLabel.setVisible(isHighScore);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 22));
        button.setForeground(new Color(0, 0, 0));
        button.setBackground(new Color(255, 223, 186)); // Peach
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 2),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        button.setPreferredSize(new Dimension(200, 50));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 239, 213)); // Papaya whip
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 223, 186));
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
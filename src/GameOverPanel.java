package src;

import java.awt.*;
import javax.swing.*;

public class GameOverPanel extends JPanel {
    private Main mainApp;
    private JLabel scoreLabel;
    private JLabel messageLabel;

    public GameOverPanel(Main mainApp) {
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
        JLabel title = new JLabel("GAME OVER", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 56));
        title.setForeground(new Color(220, 20, 60)); // Crimson red
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        messageLabel = new JLabel("You ran out of sunflowers!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score display
        scoreLabel = new JLabel("Final Score: 0");
        scoreLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        scoreLabel.setForeground(new Color(255, 215, 0)); // Gold
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setOpaque(false);

        JButton retryBtn = createStyledButton("Main Menu");
        retryBtn.addActionListener(e -> {
            mainApp.showScreen("Initial");
        });

        JButton exitBtn = createStyledButton("Exit Game");
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonsPanel.add(retryBtn);
        buttonsPanel.add(exitBtn);

        // Add components to container
        container.add(title);
        container.add(Box.createVerticalStrut(20));
        container.add(messageLabel);
        container.add(Box.createVerticalStrut(30));
        container.add(scoreLabel);
        container.add(Box.createVerticalStrut(40));
        container.add(buttonsPanel);

        bg.add(container);
        add(bg, BorderLayout.CENTER);
    }

    public void updateScore(int finalScore) {
        scoreLabel.setText("Final Score: " + finalScore);
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
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
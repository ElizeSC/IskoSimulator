package src;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class LeaderboardPanel extends JPanel {
    private Main mainApp;
    private LeaderboardManager leaderboardManager;
    private JPanel entriesPanel;
    
    public LeaderboardPanel(Main mainApp, LeaderboardManager leaderboardManager) {
        this.mainApp = mainApp;
        this.leaderboardManager = leaderboardManager;
        
        setLayout(new BorderLayout());
        
        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/leaderboard-bg.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new BorderLayout());
        
        // Main container
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        
        // Title
        JLabel title = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(title);
        container.add(Box.createVerticalStrut(40));
        
        // Entries panel with semi-transparent background
        JPanel entriesContainer = new JPanel();
        entriesContainer.setOpaque(false);
        entriesContainer.setLayout(new BorderLayout());
        
        // Create rounded panel for entries
        RoundedPanel entriesBackground = new RoundedPanel(20);
        entriesBackground.setBackground(new Color(0, 0, 0, 150));
        entriesBackground.setLayout(new BorderLayout());
        entriesBackground.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        entriesPanel = new JPanel();
        entriesPanel.setOpaque(false);
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));
        
        entriesBackground.add(entriesPanel, BorderLayout.CENTER);
        entriesContainer.add(entriesBackground, BorderLayout.CENTER);
        
        container.add(entriesContainer);
        container.add(Box.createVerticalStrut(40));
        
        // Back button
        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Poppins", Font.BOLD, 20));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainApp.showScreen("Initial"));
        container.add(backButton);
        
        bg.add(container, BorderLayout.CENTER);
        add(bg);
    }
    
    /**
     * Refresh leaderboard display
     */
    public void refresh() {
        entriesPanel.removeAll();
        
        List<LeaderboardEntry> entries = leaderboardManager.getTopEntries(10);
        
        if (entries.isEmpty()) {
            JLabel noEntries = new JLabel("No scores yet! Be the first to play!");
            noEntries.setFont(new Font("Poppins", Font.ITALIC, 18));
            noEntries.setForeground(Color.LIGHT_GRAY);
            noEntries.setAlignmentX(Component.CENTER_ALIGNMENT);
            entriesPanel.add(noEntries);
        } else {
            for (int i = 0; i < entries.size(); i++) {
                LeaderboardEntry entry = entries.get(i);
                JPanel entryPanel = createEntryPanel(i + 1, entry);
                entriesPanel.add(entryPanel);
                if (i < entries.size() - 1) {
                    entriesPanel.add(Box.createVerticalStrut(15));
                }
            }
        }
        
        entriesPanel.revalidate();
        entriesPanel.repaint();
    }
    
    private JPanel createEntryPanel(int rank, LeaderboardEntry entry) {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(900, 50));
        
        // Rank
        JLabel rankLabel = new JLabel("#" + rank);
        rankLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        rankLabel.setForeground(getRankColor(rank));
        rankLabel.setPreferredSize(new Dimension(60, 40));
        
        // Name
        JLabel nameLabel = new JLabel(entry.getPlayerName());
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        nameLabel.setForeground(Color.WHITE);
        
        // Score
        JLabel scoreLabel = new JLabel(entry.getScore() + " pts");
        scoreLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreLabel.setPreferredSize(new Dimension(150, 40));
        
        panel.add(rankLabel, BorderLayout.WEST);
        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(scoreLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private Color getRankColor(int rank) {
        switch (rank) {
            case 1: return new Color(255, 215, 0); // Gold
            case 2: return new Color(192, 192, 192); // Silver
            case 3: return new Color(205, 127, 50); // Bronze
            default: return Color.WHITE;
        }
    }
    
    // Rounded panel class
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
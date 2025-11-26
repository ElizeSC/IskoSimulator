package src;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class LeaderboardPanel extends JPanel {
    private Main mainApp;
    private LeaderboardManager leaderboardManager;
    private JPanel entriesPanel;

    // Font file paths
    //private static final String POPPINS_BOLD = "assets/fonts/Poppins-Bold.ttf";
    private static final String POPPINS_ITALIC = "assets/fonts/Poppins-Italic.ttf";
    private static final String CANDC_RED_ALERT = "assets/fonts/C&C Red Alert [LAN].ttf";

    public LeaderboardPanel(Main mainApp, LeaderboardManager leaderboardManager) {
        this.mainApp = mainApp;
        this.leaderboardManager = leaderboardManager;

        setLayout(new BorderLayout());

        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/leaderboard-bg.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout());

        // Blackboard overlay
        ImageIcon blackboardIcon = new ImageIcon("assets/objects/blackboard-2.png");
        int boardWidth = 900;
        int boardHeight = 600;
        Image scaledBlackboard = blackboardIcon.getImage()
                .getScaledInstance(boardWidth, boardHeight, Image.SCALE_SMOOTH);

        JLayeredPane layeredBoard = new JLayeredPane();
        layeredBoard.setPreferredSize(new Dimension(boardWidth, boardHeight));

        JLabel boardLabel = new JLabel(new ImageIcon(scaledBlackboard));
        boardLabel.setBounds(0, 0, boardWidth, boardHeight);
        layeredBoard.add(boardLabel, Integer.valueOf(0));

        // Content container
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBounds(50, 50, boardWidth - 100, boardHeight - 100);

        // Title
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(loadFont(CANDC_RED_ALERT, 40f));
        title.setForeground(Color.WHITE);

        // Header row (Rank | Name | Score)
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(800, 40));

        JLabel rankHeader = new JLabel("Rank");
        rankHeader.setFont(loadFont(CANDC_RED_ALERT, 20f));
        rankHeader.setForeground(Color.WHITE);
        rankHeader.setPreferredSize(new Dimension(80, 30));

        JLabel nameHeader = new JLabel("Name");
        nameHeader.setFont(loadFont(CANDC_RED_ALERT, 20f));
        nameHeader.setForeground(Color.WHITE);

        JLabel scoreHeader = new JLabel("Score", SwingConstants.RIGHT);
        scoreHeader.setFont(loadFont(CANDC_RED_ALERT, 20f));
        scoreHeader.setForeground(Color.WHITE);
        scoreHeader.setPreferredSize(new Dimension(150, 30));

        headerPanel.add(rankHeader, BorderLayout.WEST);
        headerPanel.add(nameHeader, BorderLayout.CENTER);
        headerPanel.add(scoreHeader, BorderLayout.EAST);

        // Entries panel
        entriesPanel = new JPanel();
        entriesPanel.setOpaque(false);
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(entriesPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Assemble content panel
        JPanel topSection = new JPanel();
        topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        // --- Add vertical strut to push everything down ---
        topSection.add(Box.createVerticalStrut(70)); // adjust this value to move content lower
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topSection.add(title);
        topSection.add(Box.createVerticalStrut(30));
        topSection.add(headerPanel);
        topSection.add(Box.createVerticalStrut(10));

        contentPanel.add(topSection, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        layeredBoard.add(contentPanel, Integer.valueOf(1));

        // Back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createVerticalStrut(20));

        ImageIcon backIcon = new ImageIcon("assets/text-and-buttons/back.png");
        JButton backButton = UIHelpers.createImageButtonPlain(backIcon, () -> mainApp.showScreen("Initial"));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(backButton);

        // Main container
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        layeredBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContainer.add(layeredBoard);
        mainContainer.add(bottomPanel);

        bg.add(mainContainer);
        add(bg);
    }

    /** Refresh leaderboard display */
    public void refresh() {
        entriesPanel.removeAll();

        List<LeaderboardEntry> entries = leaderboardManager.getTopEntries(10);

        if (entries.isEmpty()) {
            JLabel noEntries = new JLabel("No scores yet! Be the first to play!");
            noEntries.setFont(loadFont(POPPINS_ITALIC, 18f));
            noEntries.setForeground(Color.LIGHT_GRAY);
            noEntries.setAlignmentX(Component.CENTER_ALIGNMENT);
            entriesPanel.add(noEntries);
        } else {
            for (int i = 0; i < entries.size(); i++) {
                LeaderboardEntry entry = entries.get(i);
                JPanel entryPanel = createEntryPanel(i + 1, entry);
                entriesPanel.add(entryPanel);
                if (i < entries.size() - 1) {
                    entriesPanel.add(Box.createVerticalStrut(8));
                }
            }
        }

        entriesPanel.revalidate();
        entriesPanel.repaint();
    }

    private JPanel createEntryPanel(int rank, LeaderboardEntry entry) {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(700, 40));

        // Rank
        JLabel rankLabel = new JLabel(String.valueOf(rank));
        rankLabel.setFont(loadFont(CANDC_RED_ALERT, 22f));
        rankLabel.setForeground(Color.WHITE);
        rankLabel.setPreferredSize(new Dimension(80, 35));
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Name
        JLabel nameLabel = new JLabel(entry.getPlayerName());
        nameLabel.setFont(loadFont(CANDC_RED_ALERT, 20f));
        nameLabel.setForeground(Color.WHITE);

        // Score
        JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore()));
        scoreLabel.setFont(loadFont(CANDC_RED_ALERT, 20f));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreLabel.setPreferredSize(new Dimension(150, 35));

        panel.add(rankLabel, BorderLayout.WEST);
        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(scoreLabel, BorderLayout.EAST);

        return panel;
    }

    /** Helper method to load font from file */
    private Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(path));
            font = font.deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size); // fallback
        }
    }
}

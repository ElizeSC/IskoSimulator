package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class InitialScreenPanel extends JPanel {

    public InitialScreenPanel(Main mainApp) {
        // Load images
        ImageIcon splashScreen = new ImageIcon("assets/backdrops/home-screen-bg.png");
        ImageIcon heroIcon = new ImageIcon("assets/images-and-hero-sceens/Hero Screen.png");
        ImageIcon playIcon = new ImageIcon("assets/text-and-buttons/start-default.png");
        ImageIcon playHoverIcon = new ImageIcon("assets/text-and-buttons/start-hover.png");
        ImageIcon playPressedIcon = new ImageIcon("assets/text-and-buttons/start-pressed.png");
        ImageIcon leaderboardIcon = new ImageIcon("assets/text-and-buttons/leaderboard.png");
        ImageIcon leaderboardHoverIcon = new ImageIcon("assets/text-and-buttons/leaderboard-hover.png");
        ImageIcon leaderboardPressedIcon = new ImageIcon("assets/text-and-buttons/leaderboard-pressed.png");

        setLayout(new BorderLayout());

        // Background
        JLabel bg = new JLabel(splashScreen);
        bg.setLayout(null);

        // Hero - positioned at top
        JLabel hero = new JLabel(heroIcon);
        hero.setBounds(141, 1, heroIcon.getIconWidth(), heroIcon.getIconHeight());
        bg.add(hero);

        // Buttons
        JButton playBtn = createImageButton(playIcon, playHoverIcon, playPressedIcon,
                () -> mainApp.showScreen("Grounds"));

        JButton leaderboardBtn = createImageButton(leaderboardIcon, leaderboardHoverIcon,
                leaderboardPressedIcon, () -> System.out.println("Leaderboard clicked"));

        // Button panel - centered horizontally, positioned lower
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(playBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(leaderboardBtn);

        int widest = Math.max(playIcon.getIconWidth(), leaderboardIcon.getIconWidth());
        int totalHeight = playIcon.getIconHeight() + leaderboardIcon.getIconHeight() + 20;
        int panelX = (splashScreen.getIconWidth() - widest) / 2;
        int panelY = 450;
        buttonPanel.setBounds(panelX, panelY, widest, totalHeight);
        bg.add(buttonPanel);

        add(bg, BorderLayout.CENTER);
    }
}
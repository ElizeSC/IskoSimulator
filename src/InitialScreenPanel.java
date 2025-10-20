package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class InitialScreenPanel extends JPanel {

    private final ImageIcon splashScreen, heroIcon, playIcon, playHoverIcon, playPressedIcon,
            leaderboardIcon, leaderboardHoverIcon, leaderboardPressedIcon;

    public InitialScreenPanel(Main mainApp) {
        // --- Load images ---
        splashScreen = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/backdrops/home-screen-bg.png");
        heroIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/images-and-hero-sceens/Hero Screen.png");

        playIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/start-default.png");
        playHoverIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/start-hover.png");
        playPressedIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/start-pressed.png");

        leaderboardIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/leaderboard.png");
        leaderboardHoverIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/leaderboard-hover.png");
        leaderboardPressedIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/leaderboard-pressed.png");

        // --- Layout ---
        setLayout(null);
        setPreferredSize(new Dimension(splashScreen.getIconWidth(), splashScreen.getIconHeight()));

        // Background
        JLabel bg = new JLabel(splashScreen);
        bg.setBounds(0, 0, splashScreen.getIconWidth(), splashScreen.getIconHeight());
        add(bg);

        // Hero
        JLabel hero = new JLabel(heroIcon);
        hero.setBounds(141, 1, heroIcon.getIconWidth(), heroIcon.getIconHeight());
        add(hero);

        // Play button
        JButton playBtn = createImageButton(playIcon, playHoverIcon, playPressedIcon, () -> {
            mainApp.showScreen("Grounds"); // switch to Grounds screen
        });

        // Leaderboard button
        JButton leaderboardBtn = createImageButton(leaderboardIcon, leaderboardHoverIcon, leaderboardPressedIcon, () -> {
            System.out.println("Leaderboard clicked");
        });

        // Button panel
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

        add(buttonPanel);
        setComponentZOrder(bg, getComponentCount() - 1); // make sure bg is at back
    }

    /*// Helper to create styled image button
    public JButton createImageButton(ImageIcon icon, ImageIcon hover, ImageIcon pressed, Runnable action) {
        JButton button = new JButton(icon);
        button.setRolloverIcon(resizeIcon(hover, icon.getIconWidth(), icon.getIconHeight()));
        button.setPressedIcon(pressed);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }

    public JButton createImageButtonPlain(ImageIcon icon, Runnable action) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }*/

    private ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}

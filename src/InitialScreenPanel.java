package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class InitialScreenPanel extends JPanel {

    private Image background;

    public InitialScreenPanel(Main mainApp) {
        // Load images
        background = new ImageIcon("assets/backdrops/home-screen-bg.png").getImage();

        ImageIcon heroIcon = new ImageIcon("assets/images-and-hero-sceens/Hero Screen.png");
        ImageIcon playIcon = new ImageIcon("assets/text-and-buttons/start-default.png");
        ImageIcon playHoverIcon = new ImageIcon("assets/text-and-buttons/start-hover.png");
        ImageIcon playPressedIcon = new ImageIcon("assets/text-and-buttons/start-pressed.png");
        ImageIcon leaderboardIcon = new ImageIcon("assets/text-and-buttons/leaderboard.png");
        ImageIcon leaderboardHoverIcon = new ImageIcon("assets/text-and-buttons/leaderboard-hover.png");
        ImageIcon leaderboardPressedIcon = new ImageIcon("assets/text-and-buttons/leaderboard-pressed.png");
        ImageIcon htpIcon = new ImageIcon("assets/text-and-buttons/htp-default.png");
        ImageIcon htpHoverIcon = new ImageIcon("assets/text-and-buttons/htp-hover.png");
        ImageIcon htpPressedIcon = new ImageIcon("assets/text-and-buttons/htp-pressed.png");

        setLayout(new BorderLayout());

        // Hero at top
        JLabel hero = new JLabel(heroIcon);
        hero.setHorizontalAlignment(SwingConstants.CENTER);
        add(hero, BorderLayout.NORTH);

        // Buttons
        JButton playBtn = createImageButton(playIcon, playHoverIcon, playPressedIcon,
                () -> mainApp.showScreen("Grounds"));
        JButton leaderboardBtn = createImageButton(leaderboardIcon, leaderboardHoverIcon,
                leaderboardPressedIcon, () -> System.out.println("Leaderboard clicked"));
        JButton htpBtn = createImageButton(htpIcon, htpHoverIcon, htpPressedIcon,
                () -> mainApp.showScreen("HowToPlay"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        htpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(playBtn);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(leaderboardBtn);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(htpBtn);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}

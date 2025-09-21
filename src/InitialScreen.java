package src;

import java.awt.*;
import javax.swing.*;

public class InitialScreen {

    private final JFrame frame;
    private final JLayeredPane layeredPane;
    private final ImageIcon splashScreen, heroIcon;

    public InitialScreen() {
        splashScreen = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/backdrops/home-screen-bg.png");
        heroIcon = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/images-and-hero-sceens/Hero Screen.png");

        frame = new JFrame("Initial Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create layered pane sized to background
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(splashScreen.getIconWidth(), splashScreen.getIconHeight()));

        // Background
        JLabel bg = new JLabel(splashScreen);
        bg.setBounds(0, 0, splashScreen.getIconWidth(), splashScreen.getIconHeight());
        layeredPane.add(bg, Integer.valueOf(0)); // background layer

        // Hero (placed at fixed spot for now)
        JLabel hero = new JLabel(heroIcon);
        hero.setBounds(200, 1, heroIcon.getIconWidth(), heroIcon.getIconHeight());
        layeredPane.add(hero, Integer.valueOf(1)); // on top of background

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InitialScreen::new);
    }
}

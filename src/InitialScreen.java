package src;

import java.awt.*;
import javax.swing.*;

public class InitialScreen {

    private final JFrame frame;
    private final JLayeredPane layeredPane;
    private final ImageIcon splashScreen, heroIcon, playIcon, playHoverIcon, playPressedIcon;

    //helper function to create buttons

    private JButton createImageButton(ImageIcon icon, ImageIcon hover, ImageIcon pressed, int x, int y, Runnable action){
        JButton button = new JButton(icon);

        if (hover != null){
            hover = resizeIcon(hover, icon.getIconWidth(), icon.getIconHeight());
            button.setRolloverIcon(hover);
        } if (pressed != null){
            button.setPressedIcon(pressed);
        } 

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.addActionListener(e -> action.run());
        
        return button;
    }

    // helper function to resize image when hovered

    private ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }



    public InitialScreen() {
        splashScreen = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/backdrops/home-screen-bg.png");
        heroIcon = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/images-and-hero-sceens/Hero Screen.png");
        playIcon = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/text-and-buttons/start-default.png");
        playHoverIcon = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/text-and-buttons/start-hover.png");
        playPressedIcon = new ImageIcon("C:/Users/Armeliz/Desktop/Isko_Simulator/IskoSimulator/assets/text-and-buttons/start-pressed.png");


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
        hero.setBounds(141, 1, heroIcon.getIconWidth(), heroIcon.getIconHeight());
        layeredPane.add(hero, Integer.valueOf(1)); // on top of background

        //add playbutton
        JButton playBtn = createImageButton(playIcon, playHoverIcon, playPressedIcon, 619, 400, () -> {
        System.out.println("Play clicked!");
        });
        layeredPane.add(playBtn, Integer.valueOf(2));



        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InitialScreen::new);
    }
}

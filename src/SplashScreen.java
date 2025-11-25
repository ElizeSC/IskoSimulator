package src;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JPanel {
    private Image logoImage;

    public SplashScreen() {
        
        logoImage = new ImageIcon("assets/backdrops/splash-screen.png").getImage();
        
        setLayout(new BorderLayout());
        setBackground(Color.BLACK); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logoImage, 0, 0, getWidth(), getHeight(), this);
    }
}
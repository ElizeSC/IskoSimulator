package src;
import javax.swing.*;

public class InitialScreen {

    private final JFrame frame;
    private final ImageIcon splashScreen;

    public InitialScreen(){
        splashScreen = new ImageIcon("assets/backdrops/home-screen.png");
        int width = splashScreen.getIconWidth();
        int height = splashScreen.getIconHeight();

        frame = new JFrame("Initial Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);

        JLabel image = new JLabel(splashScreen);
        frame.add(image);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new InitialScreen();
    }
}

package src;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private String selectedGround;

    public Main() {
        frame = new JFrame("Isko Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        InitialScreenPanel initialScreen = new InitialScreenPanel(this);
        GroundsScreenPanel groundsScreen = new GroundsScreenPanel(this);
        ASStageSelectionPanel asStageSelection = new ASStageSelectionPanel(this);
        DMStageSelectionPanel dmStageSelection = new DMStageSelectionPanel(this);
        Instructions howToPlayScreen = new Instructions(this);

        // Add to CardLayout
        mainPanel.add(initialScreen, "Initial");
        mainPanel.add(groundsScreen, "Grounds");
        mainPanel.add(asStageSelection, "ASStages");
        mainPanel.add(dmStageSelection, "DMStages");
        mainPanel.add(howToPlayScreen, "HowToPlay");


        frame.setContentPane(mainPanel);
        frame.setSize(1440, 1024);
        frame.setResizable(false); // Prevent resizing

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void setSelectedGround(String ground) {
        this.selectedGround = ground;
    }

    public String getSelectedGround() {
        return selectedGround;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
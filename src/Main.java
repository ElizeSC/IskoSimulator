package src;

import java.awt.*;
import javax.swing.*;

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
        StageSelectionPanel stageSelection = new StageSelectionPanel(this);
        

        // Add them to CardLayout
        mainPanel.add(initialScreen, "Initial");
        mainPanel.add(groundsScreen, "Grounds");
        mainPanel.add(stageSelection, "Stage");

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to switch screens
    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void setSelectedGround(String ground){
        this.selectedGround = ground;
    }

    public String getSelectedGround(){
        return selectedGround;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

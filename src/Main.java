package src;

import javax.swing.*;
import java.awt.*;

public class Main {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private String selectedGround;

    // Declare panels as fields
    private final InitialScreenPanel initialScreen;
    private final GroundsScreenPanel groundsScreen;
    private final ASStageSelectionPanel asStageSelection;
    private final DMStageSelectionPanel dmStageSelection;
    private final Instructions howToPlayScreen;

    public Main() {
        frame = new JFrame("Isko Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        initialScreen = new InitialScreenPanel(this);
        groundsScreen = new GroundsScreenPanel(this);
        asStageSelection = new ASStageSelectionPanel(this);
        dmStageSelection = new DMStageSelectionPanel(this);
        howToPlayScreen = new Instructions(this);

        // Add to CardLayout
        mainPanel.add(initialScreen, "Initial");
        mainPanel.add(groundsScreen, "Grounds");
        mainPanel.add(asStageSelection, "ASStages");
        mainPanel.add(dmStageSelection, "DMStages");
        mainPanel.add(howToPlayScreen, "HowToPlay");

        frame.setContentPane(mainPanel);
        frame.setSize(1440, 1024);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showScreen(String name) {
        if (name.equals("HowToPlay")) {
            howToPlayScreen.reset(); // Now this works!
        }
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

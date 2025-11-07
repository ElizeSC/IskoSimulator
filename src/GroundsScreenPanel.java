package src;

import java.awt.*;
import javax.swing.*;
import  java.awt.event.*;
import static src.UIHelpers.*;

public class GroundsScreenPanel extends JPanel {

    public GroundsScreenPanel(Main mainApp) {
        setLayout(new BorderLayout());

        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/grounds-bg.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout());

        // Load images
        ImageIcon asImage = new ImageIcon("assets/text-and-buttons/as-image.png");
        ImageIcon asLabel = new ImageIcon("assets/text-and-buttons/as-label.png");
        ImageIcon asDesc = new ImageIcon("assets/text-and-buttons/as-description.png");
        ImageIcon dmImage = new ImageIcon("assets/text-and-buttons/dm-image.png");
        ImageIcon dmLabel = new ImageIcon("assets/text-and-buttons/dm-label.png");
        ImageIcon dmDesc = new ImageIcon("assets/text-and-buttons/dm-description.png");

        // Create choice panels using helper
        JPanel asChoice = createChoicePanel(asImage, asLabel, asDesc, true, () -> {
            mainApp.setSelectedGround("AS");
            mainApp.showScreen("ASStages");
        });

        JPanel dmChoice = createChoicePanel(dmImage, dmLabel, dmDesc, true, () -> {
            mainApp.setSelectedGround("DM");
            mainApp.showScreen("DMStages");
        });

        // Combine choices
        JPanel choicesPanel = new JPanel(new GridLayout(1, 2, 100, 0));
        choicesPanel.setOpaque(false);
        choicesPanel.add(asChoice);
        choicesPanel.add(dmChoice);

        bg.add(choicesPanel);
        add(bg, BorderLayout.CENTER);
    }
}
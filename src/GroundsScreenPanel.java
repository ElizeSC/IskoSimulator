package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class GroundsScreenPanel extends JPanel {

    ImageIcon bgIcon;
    ImageIcon as_label, as_description, dm_label, dm_description;
    ImageIcon as_image, dm_image;

    public GroundsScreenPanel(Main mainApp) {
        // --- Background setup ---
        bgIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/backdrops/grounds-bg.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout()); // background will hold everything centered
        setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);

        // --- Load images ---
        as_label = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/as-label.png");
        as_description = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/as-description.png");
        dm_label = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/dm-label.png");
        dm_description = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/dm-description.png");
        as_image = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/as-image.png");
        dm_image = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/dm-image.png");


        JPanel as_choice = new JPanel();
        as_choice.setOpaque(false);
        as_choice.setLayout(new BoxLayout(as_choice, BoxLayout.Y_AXIS));
        as_choice.setAlignmentY(Component.CENTER_ALIGNMENT);
        as_choice.setAlignmentX(Component.CENTER_ALIGNMENT);

       JButton as_button = createImageButtonPlain(as_image, () -> {
            mainApp.setSelectedGround("AS");
            mainApp.showScreen("Stage");
        });
        as_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel asLabel = new JLabel(as_label, SwingConstants.CENTER);
        asLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel asDesc = new JLabel(as_description, SwingConstants.CENTER);
        asDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        as_choice.add(as_button);
        as_choice.add(Box.createVerticalStrut(10));
        as_choice.add(asLabel);
        as_choice.add(Box.createVerticalStrut(10));
        as_choice.add(asDesc);


        JPanel dm_choice = new JPanel();
        dm_choice.setOpaque(false);
        dm_choice.setLayout(new BoxLayout(dm_choice, BoxLayout.Y_AXIS));
        dm_choice.setAlignmentY(Component.CENTER_ALIGNMENT);
        dm_choice.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton dm_button = createImageButtonPlain(dm_image, () -> {
            mainApp.setSelectedGround("DM");
            mainApp.showScreen("Stage");
        });
        dm_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dmLabel = new JLabel(dm_label, SwingConstants.CENTER);
        dmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dmDesc = new JLabel(dm_description, SwingConstants.CENTER);
        dmDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        dm_choice.add(dm_button);
        dm_choice.add(Box.createVerticalStrut(10));
        dm_choice.add(dmLabel);
        dm_choice.add(Box.createVerticalStrut(10));
        dm_choice.add(dmDesc);

        // --- Combine both choices side by side ---
        JPanel choicesPanel = new JPanel(new GridLayout(1, 2, 100, 0)); // 1 row, 2 columns, 100px gap
        choicesPanel.setOpaque(false);
        choicesPanel.add(as_choice);
        choicesPanel.add(dm_choice);

        // --- Center the choices panel ---
        bg.add(choicesPanel, new GridBagConstraints());
    }
}

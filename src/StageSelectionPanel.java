package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class StageSelectionPanel extends JPanel {

    ImageIcon bgIcon;
    ImageIcon as1_image, as1_label, as1_description;
    ImageIcon locked_image, locked_label, locked_description;

    public StageSelectionPanel(Main mainApp) {

        // --- Background setup ---
        bgIcon = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/backdrops/stages-bg.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout());
        setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);

        // --- Load images ---
        as1_image = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/as1-image.png");
        as1_label = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/as1-label.png");
        as1_description = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/Easy.png");

        locked_image = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/locked-img.png");
        locked_label = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/Locked.png");
        locked_description = new ImageIcon("C:/Users/Armeliz/Desktop/IskoSimulator/assets/text-and-buttons/locked-description.png");

        // --- ACTIVE STAGE (AS-1) ---
        JPanel stage1 = new JPanel();
        stage1.setOpaque(false);
        stage1.setLayout(new BoxLayout(stage1, BoxLayout.Y_AXIS));
        stage1.setAlignmentY(Component.CENTER_ALIGNMENT);
        stage1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton stage1_button = createImageButtonPlain(as1_image, () -> {
            System.out.println("AS-1 selected");
            // TODO: proceed to question screen
        });
        stage1_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stage1Label = new JLabel(as1_label, SwingConstants.CENTER);
        stage1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel stage1Desc = new JLabel(as1_description, SwingConstants.CENTER);
        stage1Desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        stage1.add(stage1_button);
        stage1.add(Box.createVerticalStrut(10));
        stage1.add(stage1Label);
        stage1.add(Box.createVerticalStrut(10));
        stage1.add(stage1Desc);

        // --- LOCKED STAGE 2 ---
        JPanel stage2 = new JPanel();
        stage2.setOpaque(false);
        stage2.setLayout(new BoxLayout(stage2, BoxLayout.Y_AXIS));
        stage2.setAlignmentY(Component.CENTER_ALIGNMENT);
        stage2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton stage2_button = new JButton(locked_image);
        stage2_button.setBorderPainted(false);
        stage2_button.setContentAreaFilled(false);
        stage2_button.setEnabled(false); // locked
        stage2_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stage2Label = new JLabel(locked_label, SwingConstants.CENTER);
        stage2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel stage2Desc = new JLabel(locked_description, SwingConstants.CENTER);
        stage2Desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        stage2.add(stage2_button);
        stage2.add(Box.createVerticalStrut(10));
        stage2.add(stage2Label);
        stage2.add(Box.createVerticalStrut(10));
        stage2.add(stage2Desc);

        // --- LOCKED STAGE 3 ---
        JPanel stage3 = new JPanel();
        stage3.setOpaque(false);
        stage3.setLayout(new BoxLayout(stage3, BoxLayout.Y_AXIS));
        stage3.setAlignmentY(Component.CENTER_ALIGNMENT);
        stage3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton stage3_button = new JButton(locked_image);
        stage3_button.setBorderPainted(false);
        stage3_button.setContentAreaFilled(false);
        stage3_button.setEnabled(false);
        stage3_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stage3Label = new JLabel(locked_label, SwingConstants.CENTER);
        stage3Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel stage3Desc = new JLabel(locked_description, SwingConstants.CENTER);
        stage3Desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        stage3.add(stage3_button);
        stage3.add(Box.createVerticalStrut(10));
        stage3.add(stage3Label);
        stage3.add(Box.createVerticalStrut(10));
        stage3.add(stage3Desc);

        // --- Combine all three in one centered row ---
        JPanel choicesPanel = new JPanel(new GridLayout(1, 3, 80, 0)); // 80px gap between stages
        choicesPanel.setOpaque(false);
        choicesPanel.add(stage1);
        choicesPanel.add(stage2);
        choicesPanel.add(stage3);

        // --- Add title above the choices ---
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("CHOOSE YOUR STAGE", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ✅ add padding *inside* the container
        container.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100)); 
        // top, left, bottom, right padding (adjust values to taste)

        container.add(title);
        container.add(Box.createVerticalStrut(40));
        container.add(choicesPanel);

        // --- Center everything on the background ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30); // margin from background edges
        bg.add(container, gbc);

    }
}

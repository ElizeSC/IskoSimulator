package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class ASStageSelectionPanel extends JPanel {

    public ASStageSelectionPanel(Main mainApp) {
        setLayout(new BorderLayout());

        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/stages-bg.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new GridBagLayout());

        // Main container
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("CHOOSE YOUR STAGE", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(title);
        container.add(Box.createVerticalStrut(40));

        // Load images
        ImageIcon as1Image = new ImageIcon("assets/text-and-buttons/as1-image.png");
        ImageIcon as1Label = new ImageIcon("assets/text-and-buttons/as1-label.png");
        ImageIcon as1Desc = new ImageIcon("assets/text-and-buttons/Easy.png");
        ImageIcon lockedImage = new ImageIcon("assets/text-and-buttons/locked-img.png");
        ImageIcon lockedLabel = new ImageIcon("assets/text-and-buttons/Locked.png");
        ImageIcon lockedDesc = new ImageIcon("assets/text-and-buttons/locked-description.png");

        // Create stage panels using helper
        JPanel stage1 = createChoicePanel(as1Image, as1Label, as1Desc, true,
                () -> System.out.println("AS-1 selected"));

        JPanel stage2 = createChoicePanel(lockedImage, lockedLabel, lockedDesc, false, null);
        JPanel stage3 = createChoicePanel(lockedImage, lockedLabel, lockedDesc, false, null);

        // Combine stages with Box layout for perfect centering
        JPanel stagesPanel = new JPanel();
        stagesPanel.setOpaque(false);
        stagesPanel.setLayout(new BoxLayout(stagesPanel, BoxLayout.X_AXIS));
        stagesPanel.add(Box.createHorizontalGlue());
        stagesPanel.add(stage1);
        stagesPanel.add(Box.createHorizontalStrut(80));
        stagesPanel.add(stage2);
        stagesPanel.add(Box.createHorizontalStrut(80));
        stagesPanel.add(stage3);
        stagesPanel.add(Box.createHorizontalGlue());

        container.add(stagesPanel);
        bg.add(container);
        add(bg, BorderLayout.CENTER);
    }
}
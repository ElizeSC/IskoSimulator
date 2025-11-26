package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class ASStageSelectionPanel extends JPanel {

    private Main mainApp;
    private GameState gameState;

    public ASStageSelectionPanel(Main mainApp, GameState gameState) {
        this.mainApp = mainApp;
        this.gameState = gameState;

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
        ImageIcon as1ImageOriginal = new ImageIcon("assets/text-and-buttons/as1-image.png");
        Image as1ImageScaled = as1ImageOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon as1Image = new ImageIcon(as1ImageScaled);
        ImageIcon as1Label = new ImageIcon("assets/text-and-buttons/as1-label.png");
        ImageIcon as1Desc = new ImageIcon("assets/text-and-buttons/Easy.png");

        ImageIcon as2ImageOriginal = new ImageIcon("assets/text-and-buttons/as2-image.png");
        Image as2ImageScaled = as2ImageOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon as2Image = new ImageIcon(as2ImageScaled);
        ImageIcon as2Label = new ImageIcon("assets/text-and-buttons/as2-label.png");
        ImageIcon as2Desc = new ImageIcon("assets/text-and-buttons/average.png");

        ImageIcon as3ImageOriginal = new ImageIcon("assets/text-and-buttons/as3-image.png");
        Image as3ImageScaled = as3ImageOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon as3Image = new ImageIcon(as3ImageScaled);
        ImageIcon as3Label = new ImageIcon("assets/text-and-buttons/as3-label.png");
        ImageIcon as3Desc = new ImageIcon("assets/text-and-buttons/difficult.png");

        ImageIcon lockedImage = new ImageIcon("assets/text-and-buttons/locked-img.png");
        ImageIcon lockedLabel = new ImageIcon("assets/text-and-buttons/Locked.png");
        ImageIcon lockedDesc = new ImageIcon("assets/text-and-buttons/locked-description.png");

        boolean stage2Unlocked = gameState.isStageUnlocked("AS", 2);
        boolean stage3Unlocked = gameState.isStageUnlocked("AS", 3);

        System.out.println("AS Stage 2 unlocked: " + stage2Unlocked);
        System.out.println("AS Stage 3 unlocked: " + stage3Unlocked);

        // Create stage panels using helper
        JPanel stage1 = createChoicePanel(as1Image, as1Label, as1Desc, true,
                () -> mainApp.startGameplay("AS", 1));

        JPanel stage2 = createChoicePanel(
            stage2Unlocked ? as2Image : lockedImage,
            stage2Unlocked ? as2Label : lockedLabel,
            stage2Unlocked ? as2Desc : lockedDesc,
            stage2Unlocked,
            stage2Unlocked ? () -> mainApp.startGameplay("AS", 2) : null
        );

        JPanel stage3 = createChoicePanel(
            stage3Unlocked ? as3Image : lockedImage,
            stage3Unlocked ? as3Label : lockedLabel,
            stage3Unlocked ? as3Desc : lockedDesc,
            stage3Unlocked,
            stage3Unlocked ? () -> mainApp.startGameplay("AS", 3) : null
        );

        // Combine Stages
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
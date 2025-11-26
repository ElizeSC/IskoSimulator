package src;

import java.awt.*;
import javax.swing.*;
import static src.UIHelpers.*;

public class DMStageSelectionPanel extends JPanel {

    private Main mainApp;
    private GameState gameState;

    public DMStageSelectionPanel(Main mainApp, GameState gameState) {
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

        // Load images for DM stages
        ImageIcon dm1ImageOriginal = new ImageIcon("assets/text-and-buttons/dm1-image.png");
        Image dm1ImageScaled = dm1ImageOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon dm1Image = new ImageIcon(dm1ImageScaled);
        ImageIcon dm1Label = new ImageIcon("assets/text-and-buttons/dm1-label.png");
        ImageIcon dm1Desc = new ImageIcon("assets/text-and-buttons/Easy.png");

        ImageIcon dm2ImageOriginal = new ImageIcon("assets/text-and-buttons/dm2-image.png");
        Image dm2ImageScaled = dm2ImageOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon dm2Image = new ImageIcon(dm2ImageScaled);
        ImageIcon dm2Label = new ImageIcon("assets/text-and-buttons/dm2-label.png");
        ImageIcon dm2Desc = new ImageIcon("assets/text-and-buttons/average.png");

        ImageIcon dm3ImageOriginal = new ImageIcon("assets/text-and-buttons/dm3-image.png");
        Image dm3ImageScaled = dm3ImageOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon dm3Image = new ImageIcon(dm3ImageScaled);
        ImageIcon dm3Label = new ImageIcon("assets/text-and-buttons/dm3-label.png");
        ImageIcon dm3Desc = new ImageIcon("assets/text-and-buttons/difficult.png");

        ImageIcon lockedImage = new ImageIcon("assets/text-and-buttons/locked-img.png");
        ImageIcon lockedLabel = new ImageIcon("assets/text-and-buttons/Locked.png");
        ImageIcon lockedDesc = new ImageIcon("assets/text-and-buttons/locked-description.png");

        // Check unlock status from GameState
        boolean stage1Unlocked = gameState.isStageUnlocked("DM", 1);
        boolean stage2Unlocked = gameState.isStageUnlocked("DM", 2);
        boolean stage3Unlocked = gameState.isStageUnlocked("DM", 3);

        boolean stage1Clickable = gameState.isStageClickable("DM", 1);
        boolean stage2Clickable = gameState.isStageClickable("DM", 2);
        boolean stage3Clickable = gameState.isStageClickable("DM", 3);

        System.out.println("=== DM STAGE SELECTION SCREEN ===");
        System.out.println("DM Stage 1 unlocked: " + stage1Unlocked); 
        System.out.println("DM Stage 2 unlocked: " + stage2Unlocked);
        System.out.println("DM Stage 3 unlocked: " + stage3Unlocked);

        // Create stage panels with proper unlock/clickable logic
        JPanel stage1 = createChoicePanel(
            stage1Unlocked ? dm1Image : lockedImage,  
            stage1Unlocked ? dm1Label : lockedLabel,   
            stage1Unlocked ? dm1Desc : lockedDesc,   
            stage1Clickable,                        
            stage1Clickable ? () -> mainApp.startGameplay("DM", 1) : null 
        );

        JPanel stage2 = createChoicePanel(
            stage2Unlocked ? dm2Image : lockedImage,
            stage2Unlocked ? dm2Label : lockedLabel,
            stage2Unlocked ? dm2Desc : lockedDesc,
            stage2Clickable,
            stage2Clickable ? () -> mainApp.startGameplay("DM", 2) : null
        );

        JPanel stage3 = createChoicePanel(
            stage3Unlocked ? dm3Image : lockedImage,
            stage3Unlocked ? dm3Label : lockedLabel,
            stage3Unlocked ? dm3Desc : lockedDesc,
            stage3Clickable,
            stage3Clickable ? () -> mainApp.startGameplay("DM", 3) : null
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
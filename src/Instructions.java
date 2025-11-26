package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Instructions extends JPanel {

    private int currentPage = 1;
    private final int TOTAL_PAGES = 3;
    private JLabel textLabel;
    private Main mainApp;

    private ImageIcon[] instructionTexts;

    public Instructions(Main mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        // Load all instruction text images
        instructionTexts = new ImageIcon[TOTAL_PAGES];
        instructionTexts[0] = new ImageIcon("assets/text-and-buttons/instructions_1.png");
        instructionTexts[1] = new ImageIcon("assets/text-and-buttons/instructions_2.png");
        instructionTexts[2] = new ImageIcon("assets/text-and-buttons/instructions_3.png");

        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/AS-1-1.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new BorderLayout());

        // Load images
        ImageIcon blackboardIcon = new ImageIcon("assets/objects/blackboard-2.png");
        ImageIcon grassIcon = new ImageIcon("assets/objects/grass.png");

        // Scale blackboard
        int boardWidth = 1000;
        int boardHeight = 500;
        Image scaledBlackboard = blackboardIcon.getImage()
                .getScaledInstance(boardWidth, boardHeight, Image.SCALE_SMOOTH);

        // Create layered pane for blackboard + text
        JLayeredPane layeredBoard = new JLayeredPane();
        layeredBoard.setPreferredSize(new Dimension(boardWidth, boardHeight));

        // Blackboard layer
        JLabel boardLabel = new JLabel(new ImageIcon(scaledBlackboard));
        boardLabel.setBounds(0, 0, boardWidth, boardHeight);
        layeredBoard.add(boardLabel, Integer.valueOf(0));

        // Text layer (will be updated dynamically)
        textLabel = new JLabel();
        updateTextImage();
        layeredBoard.add(textLabel, Integer.valueOf(1));

        // Center the blackboard
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(layeredBoard);

        // Grass at bottom
        JLabel grass = new JLabel(grassIcon);
        grass.setHorizontalAlignment(SwingConstants.CENTER);

        // Add to background
        bg.add(centerPanel, BorderLayout.CENTER);
        bg.add(grass, BorderLayout.SOUTH);
        add(bg, BorderLayout.CENTER);


        // Also support keyboard input
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                nextPage();
            }
        });

        // Request focus when mouse enters
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                requestFocusInWindow();
            }
        });
    }

    private void updateTextImage() {
        // Scale current text
        int textWidth = 800;
        int textHeight = 250;
        Image scaledText = instructionTexts[currentPage - 1].getImage()
                .getScaledInstance(textWidth, textHeight, Image.SCALE_SMOOTH);

        textLabel.setIcon(new ImageIcon(scaledText));

        // Center text on blackboard
        int boardWidth = 1000;
        int boardHeight = 500;
        int textX = (boardWidth - textWidth) / 2;
        int textY = (boardHeight - textHeight) / 2;
        textLabel.setBounds(textX, textY, textWidth, textHeight);
    }

    private void nextPage() {
        if (currentPage < TOTAL_PAGES) {
            // Go to next instruction page
            currentPage++;
            updateTextImage();
            repaint();
        } else {
            // Last page - go to the Ground selection screen
            System.out.println("Going to the Ground selection screen");
            reset();
            mainApp.showScreen("Grounds");
        }
    }

    // Call this when the panel becomes visible to reset to page 1
    public void reset() {
        currentPage = 1;
        updateTextImage();
        requestFocusInWindow();
    }
}
/* todo:
    fix this idfk what the fuck is going on na
 */


package src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static src.UIHelpers.*;

public class Instructions extends JPanel {
    ImageIcon bg;
    ImageIcon grass, blackboard;
    ImageIcon cont;
    ImageIcon text;

    public Instructions(Main mainApp) {
        // --- Load images ---
        bg = new ImageIcon("assets/backdrops/AS-1-1.png");
        grass = new ImageIcon("assets/objects/grass.png");
        blackboard = new ImageIcon("assets/objects/blackboard.png");
        text = new ImageIcon("assets/text-and-buttons/instructions_1.png");

        // --- Main layout ---
        setLayout(new GridBagLayout());

        JLabel bgLabel = new JLabel(bg);
        bgLabel.setLayout(new GridBagLayout()); // allow centering of content

        // --- Center panel ---
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // --- Scale blackboard to fit 1440x842 nicely ---
        int boardWidth = 1000;
        int boardHeight = 500;
        Image scaledBlackboard = blackboard.getImage().getScaledInstance(boardWidth, boardHeight, Image.SCALE_SMOOTH);
        ImageIcon blackboardScaledIcon = new ImageIcon(scaledBlackboard);

        // --- Scale text to fit proportionally ---
        int textWidth = 800;
        int textHeight = 250;
        Image scaledText = text.getImage().getScaledInstance(textWidth, textHeight, Image.SCALE_SMOOTH);
        ImageIcon textScaledIcon = new ImageIcon(scaledText);

        // --- Layered blackboard + text setup ---
        JLayeredPane layeredBoard = new JLayeredPane();
        layeredBoard.setPreferredSize(new Dimension(boardWidth, boardHeight));

        JLabel boardLabel = new JLabel(blackboardScaledIcon);
        boardLabel.setBounds(0, 0, boardWidth, boardHeight);

        JLabel textLabel = new JLabel(textScaledIcon);
        // Adjust position so text sits centered and slightly higher (like written on it)
        textLabel.setBounds((boardWidth - textWidth) / 2, 100, textWidth, textHeight);

        layeredBoard.add(boardLabel, Integer.valueOf(0)); // blackboard at back
        layeredBoard.add(textLabel, Integer.valueOf(1));  // text on top

        // --- Center vertically ---
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(layeredBoard);
        centerPanel.add(Box.createVerticalGlue());

        // --- Add to background and main panel ---
        bgLabel.add(centerPanel, new GridBagConstraints());
        add(bgLabel);

        // --- Keyboard input setup ---
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
            }
        });
    }
}

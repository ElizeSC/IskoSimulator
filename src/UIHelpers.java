package src;

import javax.swing.*;
import java.awt.*;

public class UIHelpers {

    public static JButton createImageButtonPlain(ImageIcon icon, Runnable action) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }

    public static JButton createImageButton(ImageIcon icon, ImageIcon hover, ImageIcon pressed, Runnable action) {
        JButton button = new JButton(icon);
        if (hover != null) button.setRolloverIcon(hover);
        if (pressed != null) button.setPressedIcon(pressed);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());


        Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        return button;
    }

    // Reusable method for creating choice panels (button + label + description)
    public static JPanel createChoicePanel(ImageIcon image, ImageIcon label,
                                           ImageIcon description, boolean enabled,
                                           Runnable onClick) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton button = enabled ? createImageButtonPlain(image, onClick) : new JButton(image);
        if (!enabled) {
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setEnabled(false);
        }
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelComp = new JLabel(label);
        labelComp.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descComp = new JLabel(description);
        descComp.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        panel.add(labelComp);
        panel.add(Box.createVerticalStrut(10));
        panel.add(descComp);

        return panel;
    }
}
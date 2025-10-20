package src;

import javax.swing.*;

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
        return button;
    }
}

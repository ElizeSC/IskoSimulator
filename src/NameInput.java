package src;

import java.awt.*;
import javax.swing.*;

public class NameInput extends JPanel {
    private Main mainApp;
    private JTextField nameField;
    private Image background;

    public NameInput(Main mainApp) {
        this.mainApp = mainApp;
        
        // Load background
        background = new ImageIcon("assets/backdrops/name-input.png").getImage();
        
        setLayout(new GridBagLayout());

        // Style Text Field
        nameField = new JTextField(15); 
        nameField.setFont(new Font("Poppins", Font.BOLD, 32));
        nameField.setOpaque(false); 
        nameField.setForeground(Color.WHITE); 
        nameField.setCaretColor(Color.WHITE); 
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE)); 

        // Submit Action
        nameField.addActionListener(e -> handleNameSubmit());

        // Position
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(300, 0, 0, 0); // Move down 300px

        add(nameField, gbc);
    }

    private void handleNameSubmit() {
        String name = nameField.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter your name!", 
                "Name Required", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        mainApp.setPlayerName(name);
        nameField.setText("");
        mainApp.showScreen("Initial");
    }

    public void reset() {
        nameField.setText("");
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
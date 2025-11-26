// ========== GameplayScreen.java ==========
package src;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GameplayScreen extends JPanel {

    private Main mainApp;
    private GameState gameState;
    private QuestionBank questionBank;
    private List<Question> currentQuestions;
    private int currentQuestionIndex;

    // UI Components
    private JLabel questionLabel;
    private JButton[] answerButtons;
    private JLabel[] sunflowerLabels;
    private JButton latteButton, macchiatoButton, americanoButton;
    private JLabel scoreLabel;

    // Power-up state
    private boolean macchiatoActive = false;

    public GameplayScreen(Main mainApp) {
        this.mainApp = mainApp;
        this.questionBank = new QuestionBank();

        setLayout(new BorderLayout());

        // Background
        ImageIcon bgIcon = new ImageIcon("assets/backdrops/AS-1-1.png");
        JLabel bg = new JLabel(bgIcon);
        bg.setLayout(new BorderLayout());

        // === TOP: Lives and Score ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Lives (upper left)
        JPanel livesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        livesPanel.setOpaque(false);
        livesPanel.setPreferredSize(new Dimension(500, 80));
        sunflowerLabels = new JLabel[3];
        
        ImageIcon sunflowerIcon = new ImageIcon("assets/objects/sunflower.png");
        
        for (int i = 0; i < 3; i++) {
            sunflowerLabels[i] = new JLabel(sunflowerIcon);
            livesPanel.add(sunflowerLabels[i]);
        }
        topPanel.add(livesPanel, BorderLayout.WEST);

        // Score (center)
        RoundedPanel scorePanel = new RoundedPanel(20);
        scorePanel.setBackground(new Color(0, 0, 0, 120));
        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        scoreLabel.setForeground(Color.WHITE);
        
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        
        // Wrapper to center the score panel
        JPanel scorePanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanelWrapper.setOpaque(false);
        scorePanelWrapper.add(scorePanel);
        
        topPanel.add(scorePanelWrapper, BorderLayout.CENTER);

        // Power-ups (upper right)
        JPanel powerupsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        powerupsPanel.setOpaque(false);

        ImageIcon latteIcon = new ImageIcon("assets/objects/latte.png");
        ImageIcon macchiatoIcon = new ImageIcon("assets/objects/machiatto.png");
        ImageIcon americanoIcon = new ImageIcon("assets/objects/americano.png");

        latteButton = createPowerupButton(latteIcon);
        latteButton.addActionListener(e -> useLatte());

        macchiatoButton = createPowerupButton(macchiatoIcon);
        macchiatoButton.addActionListener(e -> useMacchiato());

        americanoButton = createPowerupButton(americanoIcon);
        americanoButton.addActionListener(e -> useAmericano());

        powerupsPanel.add(latteButton);
        powerupsPanel.add(macchiatoButton);
        powerupsPanel.add(americanoButton);

        topPanel.add(powerupsPanel, BorderLayout.EAST);

        // === CENTER: Question + Answers ===
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 50, 100));

       // Question panel with rounded transparent background
        RoundedPanel questionPanel = new RoundedPanel(20);
        questionPanel.setBackground(new Color(0, 0, 0, 120));
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        questionPanel.setMaximumSize(new Dimension(900, 120));
        
        questionLabel = new JLabel("<html><center>Loading question...</center></html>");
        questionLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        
        centerPanel.add(questionPanel);
        centerPanel.add(Box.createVerticalStrut(30));

        // Answer buttons (2x2 grid)
        JPanel answersGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        answersGrid.setOpaque(false);
        answersGrid.setMaximumSize(new Dimension(900, 250));

        answerButtons = new JButton[4];
        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            final int index = i;
            answerButtons[i] = new JButton(labels[i] + ". Answer");
            answerButtons[i].setFont(new Font("Poppins", Font.PLAIN, 16));
            answerButtons[i].addActionListener(e -> handleAnswer(index));
            answersGrid.add(answerButtons[i]);
        }
        centerPanel.add(answersGrid);

        // === BOTTOM: Grass ===
        ImageIcon grassIcon = new ImageIcon("assets/objects/grass.png");
        JLabel grass = new JLabel(grassIcon);

        bg.add(topPanel, BorderLayout.NORTH);
        bg.add(centerPanel, BorderLayout.CENTER);
        bg.add(grass, BorderLayout.SOUTH);

        add(bg, BorderLayout.CENTER);
    }

    private JButton createPowerupButton(ImageIcon icon) {
        JButton btn = new JButton(icon);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        return btn;
    }

    public void startStage(String ground, int stage, GameState existingGameState) {
        this.gameState = existingGameState; 
        gameState.setCurrentStage(ground, stage);

        currentQuestionIndex = 0;  // must reset to 0
        macchiatoActive = false; 
        currentQuestions = questionBank.getQuestionsForStage(ground, stage);

        System.out.println("=== STARTING STAGE ===");
        System.out.println("Ground: " + ground + ", Stage: " + stage);
        System.out.println("Questions loaded: " + currentQuestions.size());
        System.out.println("Current question index RESET to: " + currentQuestionIndex);
        System.out.println("Lives: " + gameState.getSunflowers());
        System.out.println("Power-ups available: L=" + gameState.isLatteAvailable() 
                        + " M=" + gameState.isMacchiatoAvailable() 
                        + " A=" + gameState.isAmericanoAvailable());
    
        if (!currentQuestions.isEmpty()) {
            System.out.println("First question: " + currentQuestions.get(0).getQuestionText().substring(0, Math.min(50, currentQuestions.get(0).getQuestionText().length())) + "...");
        }

        refreshDisplay();
        loadQuestion();
    }

    private void loadQuestion() {
        System.out.println("loadQuestion() called - Index: " + currentQuestionIndex + " / Total: " + currentQuestions.size());
    
        if (currentQuestionIndex >= currentQuestions.size()) {
            System.out.println("No more questions - completing stage");
            completeStage();
            return;
        }

        Question q = currentQuestions.get(currentQuestionIndex);
    
        String preview = q.getQuestionText();
        if (preview.length() > 50) {
            preview = preview.substring(0, 50) + "...";
        }
        System.out.println("Loading Q" + (currentQuestionIndex + 1) + ": " + preview);

        questionLabel.setText("<html><center>" + q.getQuestionText() + "</center></html>");

        String[] options = q.getOptions();
        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText("<html>" + labels[i] + ". " + options[i] + "</html>");
            answerButtons[i].setEnabled(true);
            answerButtons[i].setBackground(null);
        }
    }

    private void handleAnswer(int selectedIndex) {
        Question q = currentQuestions.get(currentQuestionIndex);
        boolean correct = q.isCorrect(selectedIndex);

        if (correct) {
            answerButtons[selectedIndex].setBackground(Color.GREEN);
            int points = gameState.getBasePointsForStage();
            gameState.addScore(points);
            refreshDisplay();

            Timer timer = new Timer(1000, e -> {
                currentQuestionIndex++;
                loadQuestion();
            });
            timer.setRepeats(false);
            timer.start();

        } else {
            answerButtons[selectedIndex].setBackground(Color.RED);

            if (macchiatoActive) {
                macchiatoActive = false;
                answerButtons[selectedIndex].setEnabled(false);
                JOptionPane.showMessageDialog(this, "Second chance! Try again.");
            } else {
                gameState.loseLife();
                refreshDisplay();

                if (gameState.isGameOver()) {
                    gameOver();
                    return;
                }

                Timer timer = new Timer(1000, e -> {
                    currentQuestionIndex++;
                    loadQuestion();
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void useLatte() {
        if (!gameState.useLatte()) return;

        Question q = currentQuestions.get(currentQuestionIndex);
        int correct = q.getCorrectAnswer();

        int removed = 0;
        for (int i = 0; i < 4 && removed < 2; i++) {
            if (i != correct) {
                answerButtons[i].setEnabled(false);
                answerButtons[i].setBackground(Color.GRAY);
                removed++;
            }
        }

        refreshDisplay();
    }

    private void useMacchiato() {
        if (!gameState.useMacchiato()) return;
        macchiatoActive = true;
        JOptionPane.showMessageDialog(this, "Next wrong answer won't cost a life!");
        refreshDisplay();
    }

    private void useAmericano() {
        if (!gameState.useAmericano()) return;
        refreshDisplay();
        currentQuestionIndex++;
        loadQuestion();
    }

    private void refreshDisplay() {
        int lives = gameState.getSunflowers();
        for (int i = 0; i < 3; i++) {
            sunflowerLabels[i].setVisible(i < lives);
        }

        scoreLabel.setText("Score: " + gameState.getTotalScore());

        latteButton.setEnabled(gameState.isLatteAvailable());
        macchiatoButton.setEnabled(gameState.isMacchiatoAvailable());
        americanoButton.setEnabled(gameState.isAmericanoAvailable());
    }

    private void completeStage() {
        System.out.println("=== COMPLETING STAGE ===");
        System.out.println("Current ground: " + gameState.getCurrentGround());
        System.out.println("Current stage: " + gameState.getCurrentStage());
    
        gameState.completeStage();
    
        System.out.println("Stage completed. Checking unlock status:");
        System.out.println("AS-1 complete: " + gameState.isStageUnlocked("AS", 1));
        System.out.println("AS-2 unlocked: " + gameState.isStageUnlocked("AS", 2));
        System.out.println("AS-3 unlocked: " + gameState.isStageUnlocked("AS", 3));
    
        JOptionPane.showMessageDialog(this,
            "Stage Complete!\nTotal Score: " + gameState.getTotalScore());

        LeaderboardManager lbManager = mainApp.getLeaderboardManager();
        if (lbManager.isHighScore(gameState.getTotalScore())) {
            boolean madeIt = lbManager.addScore(gameState.getPlayerName(), gameState.getTotalScore());
            if (madeIt) {
                JOptionPane.showMessageDialog(this,
                    "NEW HIGH SCORE!\nYou made it to the leaderboard!\nRank #" + 
                    lbManager.getRank(gameState.getTotalScore()));
            }
        }
        
        mainApp.showScreen("Initial");
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this,
                "Game Over!\nTotal Score: " + gameState.getTotalScore());
        mainApp.showScreen("Initial");
    }

      private class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            super();
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            
            g2.dispose();
            super.paintComponent(g);
        }

        
    }
}
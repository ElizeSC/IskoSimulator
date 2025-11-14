/*
    todo: fix interface

 */
package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

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

        // Lives (left)
        JPanel livesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        livesPanel.setOpaque(false);
        sunflowerLabels = new JLabel[3];
        ImageIcon sunflowerIcon = new ImageIcon("assets/objects/sunflower.png");
        for (int i = 0; i < 3; i++) {
            sunflowerLabels[i] = new JLabel(sunflowerIcon);
            livesPanel.add(sunflowerLabels[i]);
        }
        topPanel.add(livesPanel, BorderLayout.WEST);

        // Score (right)
        scoreLabel = new JLabel("Score: 0   ");
        scoreLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        topPanel.add(scoreLabel, BorderLayout.EAST);

        // === CENTER: Question + Answers ===
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 50, 100));

        // Question text
        questionLabel = new JLabel("<html><center>Loading question...</center></html>");
        questionLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createVerticalStrut(40));

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

        // === BOTTOM: Power-ups + Grass ===
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Power-ups
        JPanel powerupsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        powerupsPanel.setOpaque(false);

        latteButton = new JButton("Latte\n50/50 (-20pts)");
        latteButton.addActionListener(e -> useLatte());

        macchiatoButton = new JButton("Macchiato\n2nd Chance (-30pts)");
        macchiatoButton.addActionListener(e -> useMacchiato());

        americanoButton = new JButton("Americano\nSkip (-50pts)");
        americanoButton.addActionListener(e -> useAmericano());

        powerupsPanel.add(latteButton);
        powerupsPanel.add(macchiatoButton);
        powerupsPanel.add(americanoButton);

        // Grass
        ImageIcon grassIcon = new ImageIcon("assets/objects/grass.png");
        JLabel grass = new JLabel(grassIcon);

        bottomPanel.add(powerupsPanel, BorderLayout.NORTH);
        bottomPanel.add(grass, BorderLayout.SOUTH);

        // Add everything to background
        bg.add(topPanel, BorderLayout.NORTH);
        bg.add(centerPanel, BorderLayout.CENTER);
        bg.add(bottomPanel, BorderLayout.SOUTH);
        add(bg);
    }

    public void startStage(String ground, int stage) {
        // Initialize game state
        gameState = new GameState("Player");
        gameState.setCurrentStage(ground, stage);

        // Load questions
        currentQuestions = questionBank.getQuestionsForStage(ground, stage);
        currentQuestionIndex = 0;

        System.out.println("Starting stage with " + currentQuestions.size() + " questions");

        // Load first question
        refreshDisplay();
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= currentQuestions.size()) {
            completeStage();
            return;
        }

        Question q = currentQuestions.get(currentQuestionIndex);

        // Update question
        questionLabel.setText("<html><center>" + q.getQuestionText() + "</center></html>");

        // Update answers
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
            // Correct!
            answerButtons[selectedIndex].setBackground(Color.GREEN);
            int points = gameState.getBasePointsForStage();
            gameState.addScore(points);
            refreshDisplay();

            // Next question after 1 second
            Timer timer = new Timer(1000, e -> {
                currentQuestionIndex++;
                loadQuestion();
            });
            timer.setRepeats(false);
            timer.start();

        } else {
            // Wrong
            answerButtons[selectedIndex].setBackground(Color.RED);

            if (macchiatoActive) {
                // Second chance
                macchiatoActive = false;
                answerButtons[selectedIndex].setEnabled(false);
                JOptionPane.showMessageDialog(this, "Second chance! Try again.");
            } else {
                // Lose life
                gameState.loseLife();
                refreshDisplay();

                if (gameState.isGameOver()) {
                    gameOver();
                    return;
                }

                // Next question after 1 second
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

        // Disable 2 wrong answers
        int removed = 0;
        for (int i = 0; i < 4 && removed < 2; i++) {
            if (i != correct) {
                answerButtons[i].setEnabled(false);
                answerButtons[i].setBackground(Color.GRAY);
                removed++;
            }
        }

        latteButton.setEnabled(false);
        refreshDisplay();
    }

    private void useMacchiato() {
        if (!gameState.useMacchiato()) return;

        macchiatoActive = true;
        macchiatoButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Next wrong answer won't cost a life!");
        refreshDisplay();
    }

    private void useAmericano() {
        if (!gameState.useAmericano()) return;

        americanoButton.setEnabled(false);
        currentQuestionIndex++;
        loadQuestion();
    }

    private void refreshDisplay() {
        // Update lives
        int lives = gameState.getSunflowers();
        for (int i = 0; i < 3; i++) {
            sunflowerLabels[i].setVisible(i < lives);
        }

        // Update score
        scoreLabel.setText("Score: " + gameState.getCurrentStageScore() + "   ");

        // Update power-ups
        latteButton.setEnabled(gameState.isLatteAvailable());
        macchiatoButton.setEnabled(gameState.isMacchiatoAvailable());
        americanoButton.setEnabled(gameState.isAmericanoAvailable());
    }

    private void completeStage() {
        gameState.completeStage();
        JOptionPane.showMessageDialog(this,
                "Stage Complete!\nTotal Score: " + gameState.getTotalScore());
        mainApp.showScreen("Initial");
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this,
                "Game Over!\nScore: " + gameState.getCurrentStageScore());
        mainApp.showScreen("Initial");
    }
}
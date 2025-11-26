// ========== GameState.java ==========
package src;

public class GameState {
    // Player info
    private String playerName;
    private int totalScore;

    // Current game session
    private int sunflowers; // Lives (max 3)
    private int currentStageScore;

    // Power-ups available (1 of each per stage)
    private boolean latteAvailable;      // 50/50
    private boolean macchiatoAvailable;  // Second chance
    private boolean americanoAvailable;  // Skip question

    // Stage progression
    private boolean as1Complete, as2Complete, as3Complete;
    private boolean dm1Complete, dm2Complete, dm3Complete;

    // Current session
    private String currentGround; // "AS" or "DM"
    private int currentStage; // 1, 2, or 3

    public GameState(String playerName) {
        this.playerName = playerName;
        this.totalScore = 0;
        resetStageState();
    }

    // Reset state for new stage
    public void resetStageState() {
        this.sunflowers = 3;
        this.currentStageScore = 0;
        this.latteAvailable = true;
        this.macchiatoAvailable = true;
        this.americanoAvailable = true;
    }

    // Life system
    public void loseLife() {
        if (sunflowers > 0) sunflowers--;
    }

    public boolean isGameOver() {
        return sunflowers == 0;
    }

    public int getSunflowers() { return sunflowers; }

    // Power-ups
    public boolean useLatte() {
        if (latteAvailable) {
            latteAvailable = false;
            currentStageScore -= 20;
            totalScore -= 20;
            return true;
        }
        return false;
    }

    public boolean useMacchiato() {
        if (macchiatoAvailable) {
            macchiatoAvailable = false;
            currentStageScore -= 30;
            totalScore -= 30;
            return true;
        }
        return false;
    }

    public boolean useAmericano() {
        if (americanoAvailable) {
            americanoAvailable = false;
            currentStageScore -= 50;
            totalScore -= 50;
            return true;
        }
        return false;
    }

    public boolean isLatteAvailable() { return latteAvailable; }
    public boolean isMacchiatoAvailable() { return macchiatoAvailable; }
    public boolean isAmericanoAvailable() { return americanoAvailable; }

    // Scoring
    public void addScore(int points) {
        currentStageScore += points;
        totalScore += points;  // Add to both stage and total
        System.out.println("Score added: +" + points + " | Stage: " + currentStageScore + " | Total: " + totalScore);
    }

    public int calculateStageBonus() {
        int bonus = 0;

        // Completion bonus based on stage
        if (currentStage == 1) bonus += 200;
        else if (currentStage == 2) bonus += 300;
        else if (currentStage == 3) bonus += 500;

        // Sunflower preservation (+100 per remaining)
        bonus += sunflowers * 100;

        // Coffee conservation (+50 per unused)
        if (latteAvailable) bonus += 50;
        if (macchiatoAvailable) bonus += 50;
        if (americanoAvailable) bonus += 50;

        return bonus;
    }

    public void completeStage() {
        int bonus = calculateStageBonus();
        currentStageScore += bonus;
        totalScore += bonus;
        
        System.out.println("=== STAGE COMPLETED ===");
        System.out.println("Stage bonus: " + bonus);
        System.out.println("Stage score: " + currentStageScore);
        System.out.println("New total score: " + totalScore);

        // Mark stage as complete
         if (currentGround.equals("AS")) {
            if (currentStage == 1) as1Complete = true;
            else if (currentStage == 2) as2Complete = true;
            else if (currentStage == 3) as3Complete = true;
        } else {
            if (currentStage == 1) dm1Complete = true;
            else if (currentStage == 2) dm2Complete = true;
            else if (currentStage == 3) dm3Complete = true;
        }
    }

    public int getBasePointsForStage() {
        if (currentStage == 1) return 100;
        else if (currentStage == 2) return 150;
        else return 200;
    }

    // Stage progression
    public void setCurrentStage(String ground, int stage) {
        this.currentGround = ground;
        this.currentStage = stage;
        resetStageState();
        System.out.println("Starting " + ground + "-" + stage + " | Current Total Score: " + totalScore);
    }

     public boolean isStageUnlocked(String ground, int stage) {
        if (stage == 1) return true; // Stage 1 always unlocked

        if (ground.equals("AS")) {
            if (stage == 2) return as1Complete;
            if (stage == 3) return as2Complete;
        } else {
            if (stage == 2) return dm1Complete;
            if (stage == 3) return dm2Complete;
        }
        return false;
    }


    // Getters
    public String getPlayerName() { return playerName; }
    public int getTotalScore() { return totalScore; }
    public int getCurrentStageScore() { return currentStageScore; }
    public String getCurrentGround() { return currentGround; }
    public int getCurrentStage() { return currentStage; }
}
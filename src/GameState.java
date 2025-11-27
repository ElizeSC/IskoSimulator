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
        this.sunflowers = 50;
        this.currentStageScore = 0;
        this.latteAvailable = true;
        this.macchiatoAvailable = true;
        this.americanoAvailable = true;
    }

    
public boolean isGroundComplete(String ground) {
    if (ground.equals("AS")) {
        return as1Complete && as2Complete && as3Complete;
    } else if (ground.equals("DM")) {
        return dm1Complete && dm2Complete && dm3Complete;
    }
    return false;
}

public boolean isASComplete() {
    return as1Complete && as2Complete && as3Complete;
}

public boolean isDMComplete() {
    return dm1Complete && dm2Complete && dm3Complete;
}

    public void setPlayerName(String name) {
    this.playerName = name;
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

    // Replace the completeStage method in GameState.java:

public void completeStage() {
    int bonus = calculateStageBonus();
    currentStageScore += bonus;
    totalScore += bonus;
    
    System.out.println("=== STAGE COMPLETED (GameState) ===");
    System.out.println("Stage bonus: " + bonus);
    System.out.println("Stage score: " + currentStageScore);
    System.out.println("New total score: " + totalScore);
    System.out.println("Marking stage as complete: " + currentGround + "-" + currentStage);

    // Mark stage as complete
    if (currentGround.equals("AS")) {
        if (currentStage == 1) {
            as1Complete = true;
            System.out.println("✓ AS-1 marked complete");
        } else if (currentStage == 2) {
            as2Complete = true;
            System.out.println("✓ AS-2 marked complete");
        } else if (currentStage == 3) {
            as3Complete = true;
            System.out.println("✓ AS-3 marked complete");
        }
    } else if (currentGround.equals("DM")) {
        if (currentStage == 1) {
            dm1Complete = true;
            System.out.println("✓ DM-1 marked complete");
        } else if (currentStage == 2) {
            dm2Complete = true;
            System.out.println("✓ DM-2 marked complete");
        } else if (currentStage == 3) {
            dm3Complete = true;
            System.out.println("✓ DM-3 marked complete");
        }
    }
    
    // Print all completion statuses
    System.out.println("--- All Stage Statuses ---");
    System.out.println("AS-1: " + as1Complete);
    System.out.println("AS-2: " + as2Complete);
    System.out.println("AS-3: " + as3Complete);
    System.out.println("DM-1: " + dm1Complete);
    System.out.println("DM-2: " + dm2Complete);
    System.out.println("DM-3: " + dm3Complete);
    System.out.println("Game Complete? " + isGameComplete());
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
        if (ground.equals("AS")) {
            if (stage == 1) return true;
            if (stage == 2) return as1Complete;
            if (stage == 3) return as2Complete; 
        } else {
            if (stage == 1) return true;
            if (stage == 2) return dm1Complete;
            if (stage == 3) return dm2Complete;
        }
        return false;
    }

    public boolean isGameComplete() {
    return as1Complete && as2Complete && as3Complete && 
           dm1Complete && dm2Complete && dm3Complete;
}

    public void resetProgress() {
        as1Complete = false;
        as2Complete = false;
        as3Complete = false;
        dm1Complete = false;
        dm2Complete = false;
        dm3Complete = false;
        totalScore = 0;
        resetStageState();
    }

    public boolean isStageClickable(String ground, int stage) {
        if (ground.equals("AS")) {
            if (stage == 1) return !as1Complete; // Clickable only if not complete
            if (stage == 2) return as1Complete && !as2Complete;
            if (stage == 3) return as2Complete && !as3Complete;
        } else {
            if (stage == 1) return !dm1Complete;
            if (stage == 2) return dm1Complete && !dm2Complete;
            if (stage == 3) return dm2Complete && !dm3Complete;
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
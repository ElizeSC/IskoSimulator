// ========== LeaderboardManager.java ==========
package src;

import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.dat";
    private static final int MAX_ENTRIES = 10; // Top 10
    
    private List<LeaderboardEntry> entries;
    
    public LeaderboardManager() {
        entries = new ArrayList<>();
        loadLeaderboard();
    }
    
    /**
     * Add a new score to the leaderboard
     * @return true if score made it to top 10, false otherwise
     */
    public boolean addScore(String playerName, int score) {
        LeaderboardEntry newEntry = new LeaderboardEntry(playerName, score);
        entries.add(newEntry);
        Collections.sort(entries);
        
        // Keep only top 10
        boolean madeIt = false;
        if (entries.size() > MAX_ENTRIES) {
            // Check if the new entry is in top 10
            madeIt = entries.indexOf(newEntry) < MAX_ENTRIES;
            entries = new ArrayList<>(entries.subList(0, MAX_ENTRIES));
        } else {
            madeIt = true;
        }
        
        saveLeaderboard();
        return madeIt;
    }
    
    /**
     * Get top N entries
     */
    public List<LeaderboardEntry> getTopEntries(int count) {
        return new ArrayList<>(entries.subList(0, Math.min(count, entries.size())));
    }
    
    /**
     * Get all entries
     */
    public List<LeaderboardEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }
    
    /**
     * Check if a score qualifies for the leaderboard
     */
    public boolean isHighScore(int score) {
        if (entries.size() < MAX_ENTRIES) {
            return true;
        }
        return score > entries.get(entries.size() - 1).getScore();
    }
    
    /**
     * Get the rank a score would have (1-based)
     * @return rank (1 = highest), or -1 if doesn't qualify
     */
    public int getRank(int score) {
        int rank = 1;
        for (LeaderboardEntry entry : entries) {
            if (score > entry.getScore()) {
                return rank;
            }
            rank++;
        }
        
        if (entries.size() < MAX_ENTRIES) {
            return rank;
        }
        return -1; // Doesn't qualify
    }
    
    /**
     * Save leaderboard to file
     */
    private void saveLeaderboard() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LEADERBOARD_FILE))) {
            oos.writeObject(entries);
            System.out.println("Leaderboard saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }


    
    
    /**
     * Load leaderboard from file
     */
    @SuppressWarnings("unchecked")
    private void loadLeaderboard() {
        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) {
            System.out.println("No existing leaderboard found, starting fresh");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            entries = (List<LeaderboardEntry>) ois.readObject();
            System.out.println("Leaderboard loaded: " + entries.size() + " entries");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading leaderboard: " + e.getMessage());
            entries = new ArrayList<>();
        }
    }
    
    /**
     * Clear the leaderboard (for testing)
     */
    public void clearLeaderboard() {
        entries.clear();
        saveLeaderboard();
    }
}
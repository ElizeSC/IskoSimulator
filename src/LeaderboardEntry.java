// ========== LeaderboardEntry.java ==========
package src;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeaderboardEntry implements Serializable, Comparable<LeaderboardEntry> {
    private static final long serialVersionUID = 1L;
    
    private String playerName;
    private int score;
    private String timestamp;
    
    public LeaderboardEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    
    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public String getTimestamp() { return timestamp; }
    
    @Override
    public int compareTo(LeaderboardEntry other) {
        return Integer.compare(other.score, this.score); // Descending order
    }
    
    @Override
    public String toString() {
        return playerName + " - " + score + " points (" + timestamp + ")";
    }
}


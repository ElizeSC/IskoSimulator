// ========== QuestionBank.java ==========
package src;

import java.io.*;
import java.util.*;

public class QuestionBank {
    private List<Question> asQuestions; // Programming questions
    private List<Question> dmQuestions; // Theoretical questions

    public QuestionBank() {
        asQuestions = new ArrayList<>();
        dmQuestions = new ArrayList<>();
        loadQuestionsFromCSV();
    }

    private void loadQuestionsFromCSV() {
        String filepath = "assets/questions.csv";
        System.out.println("Attempting to load questions from: " + filepath);

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            StringBuilder currentLine = new StringBuilder();
            String line;
            int lineNum = 0;
            boolean inQuotes = false;

            while ((line = br.readLine()) != null) {
                lineNum++;

                // Check if we're inside a quoted field
                for (char c : line.toCharArray()) {
                    if (c == '"') inQuotes = !inQuotes;
                }

                // Append line to current record
                if (currentLine.length() > 0) {
                    currentLine.append(" "); // Replace newline with space
                }
                currentLine.append(line);

                // If we're still in quotes, continue reading
                if (inQuotes) continue;

                // Process complete line
                String completeLine = currentLine.toString();
                currentLine = new StringBuilder();

                // Skip header
                if (completeLine.startsWith("grounds,")) {
                    System.out.println("Header found");
                    continue;
                }

                // Skip empty lines
                if (completeLine.trim().isEmpty()) continue;

                // Parse CSV line
                String[] parts = parseCSVLine(completeLine);

                if (parts.length < 9) {
                    continue; // Skip silently
                }

                String ground = parts[0].trim().toUpperCase();
                String difficulty = parts[1].trim();
                String category = parts[2].trim();
                String questionText = parts[3].trim().replace("|", "\n");
                String[] options = {
                        parts[4].trim(),
                        parts[5].trim(),
                        parts[6].trim(),
                        parts[7].trim()
                };

                int correctAnswer = letterToIndex(parts[8].trim());

                Question q = new Question(questionText, options, correctAnswer, difficulty, category);

                if (ground.equals("AS")) {
                    asQuestions.add(q);
                } else if (ground.equals("DM")) {
                    dmQuestions.add(q);
                }
            }

            System.out.println("✅ Loaded " + asQuestions.size() + " AS questions");
            System.out.println("✅ Loaded " + dmQuestions.size() + " DM questions");

        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found at: " + filepath);
            loadSampleQuestions();
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            loadSampleQuestions();
        }
    }

    // Helper method to parse CSV line handling quoted fields
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }

    private int letterToIndex(String letter) {
        switch (letter.toUpperCase()) {
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return 0;
        }
    }

    private void loadSampleQuestions() {
        // Sample questions for testing
        asQuestions.add(new Question(
                "What is the output of: System.out.println(5 + 3);",
                new String[]{"53", "8", "Error", "5 + 3"},
                1, "Easy", "Java"
        ));

        dmQuestions.add(new Question(
                "Which paradigm emphasizes immutability?",
                new String[]{"Procedural", "Object-Oriented", "Functional", "Logic"},
                2, "Easy", "Paradigms"
        ));
    }

    public List<Question> getQuestionsForStage(String ground, int stage) {
        List<Question> sourceBank = ground.equals("AS") ? asQuestions : dmQuestions;
        List<Question> filtered = new ArrayList<>();

        String difficulty;
        if (stage == 1) difficulty = "Easy";
        else if (stage == 2) difficulty = "Average";
        else difficulty = "Difficult";

        for (Question q : sourceBank) {
            if (q.getDifficulty().equals(difficulty)) {
                filtered.add(q);
            }
        }

        // Shuffle with random seed and return limited set (20 questions per stage)
        Collections.shuffle(filtered, new Random());
        return filtered.subList(0, Math.min(20, filtered.size()));
    }
}
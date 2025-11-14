// ========== Question.java ==========
package src;

public class Question {
    private final String questionText;
    private final String[] options; // A, B, C, D
    private final int correctAnswer; // 0=A, 1=B, 2=C, 3=D
    private final String difficulty; // "Easy", "Moderate", "Difficult"
    private final String category; // "C", "Java", "Python", "JavaScript", or paradigm lesson

    public Question(String questionText, String[] options, int correctAnswer,
                    String difficulty, String category) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.category = category;
    }

    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getDifficulty() { return difficulty; }
    public String getCategory() { return category; }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}
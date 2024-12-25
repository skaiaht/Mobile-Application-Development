// Question.java
package com.example.lab5;

public class Question {
    private String questionText;
    private String[] options;
    private String correctAnswer;
    private int imageResourceId;
    private QuestionType type;

    public enum QuestionType {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE,
        FREE_ANSWER
    }

    public Question(String questionText, String[] options, String correctAnswer, int imageResourceId, QuestionType type) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.imageResourceId = imageResourceId;
        this.type = type;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public int getImageResourceId() { return imageResourceId; }
    public QuestionType getType() { return type; }
}

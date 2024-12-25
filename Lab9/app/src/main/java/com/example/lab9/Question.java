// Question.java
package com.example.lab9;

import java.io.Serializable;

public class Question implements Serializable {
    private final String questionText;
    private final String[] options;
    private final String correctAnswer;
    private final int imageResourceId;
    private final QuestionType type;

    public Question(String questionText, String[] options, String correctAnswer, int imageResourceId, QuestionType type) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.imageResourceId = imageResourceId;
        this.type = type;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public QuestionType getType() {
        return type;
    }

    public enum QuestionType {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE,
        FREE_ANSWER
    }
}
// QuizViewModel.java
package com.example.lab12;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {
    private final MutableLiveData<Integer> currentQuestionIndex = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> correctAnswers = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> incorrectAnswers = new MutableLiveData<>(0);
    private final Question[] questions;

    public QuizViewModel() {
        questions = initializeQuestions();
    }

    public LiveData<Integer> getCurrentQuestionIndex() { return currentQuestionIndex; }
    public LiveData<Integer> getCorrectAnswers() { return correctAnswers; }
    public LiveData<Integer> getIncorrectAnswers() { return incorrectAnswers; }

    public Question getCurrentQuestion() {
        return questions[currentQuestionIndex.getValue() != null ? currentQuestionIndex.getValue() : 0];
    }

    public void setCurrentQuestionIndex(int index) {
        currentQuestionIndex.setValue(index);
    }

    public void incrementCorrectAnswers() {
        correctAnswers.setValue(correctAnswers.getValue() != null ? correctAnswers.getValue() + 1 : 1);
    }

    public void incrementIncorrectAnswers() {
        incorrectAnswers.setValue(incorrectAnswers.getValue() != null ? incorrectAnswers.getValue() + 1 : 1);
    }

    public int getTotalQuestions() {
        return questions.length;
    }

    private Question[] initializeQuestions() {
        return new Question[]{
                new Question(
                        "What is the recommended ski length for beginners?",
                        new String[]{"Chin height", "Eye level", "Above head", "Shoulder height"},
                        "Chin height",
                        0,
                        Question.QuestionType.SINGLE_CHOICE
                ),
                new Question(
                        "Which of these are types of skiing?",
                        new String[]{"Alpine", "Nordic", "Slavic", "Freestyle"},
                        "Alpine,Nordic,Freestyle",
                        0,
                        Question.QuestionType.MULTIPLE_CHOICE
                ),
                new Question(
                        "What is mountain slope?",
                        new String[]{
                                "The steepness of a ski run measured in degrees or percentage",
                                "The length of the ski run",
                                "The width of the ski run",
                                "The elevation of the mountain peak"
                        },
                        "The steepness of a ski run measured in degrees or percentage",
                        R.drawable.placeholder,
                        Question.QuestionType.SINGLE_CHOICE
                ),
                new Question(
                        "How deep fresh snow is being called by freeriders?",
                        null,
                        "powder",
                        0,
                        Question.QuestionType.FREE_ANSWER
                )
        };
    }
}

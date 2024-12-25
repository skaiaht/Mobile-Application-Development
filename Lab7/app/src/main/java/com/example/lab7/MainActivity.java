package com.example.lab7;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements QuestionFragment.QuestionAnswerListener {
    private Question[] questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private StatisticsFragment statisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeQuestions();

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
            correctAnswers = savedInstanceState.getInt("correctAnswers", 0);
            incorrectAnswers = savedInstanceState.getInt("incorrectAnswers", 0);
        }

        if (isLandscape()) {
            // In landscape, show both fragments
            statisticsFragment = new StatisticsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.questionContainer, createQuestionFragment())
                    .replace(R.id.statisticsContainer, statisticsFragment)
                    .commit();

            // Delay statistics update to ensure fragment is ready
            getSupportFragmentManager().executePendingTransactions();
            updateStatistics();
        } else {
            // In portrait, show only question fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.questionContainer, createQuestionFragment())
                    .commit();
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private QuestionFragment createQuestionFragment() {
        QuestionFragment fragment = QuestionFragment.newInstance(questions[currentQuestionIndex]);
        fragment.setAnswerListener(this);
        return fragment;
    }

    @Override
    public void onAnswerSubmitted(boolean isCorrect) {
        if (isCorrect) correctAnswers++;
        else incorrectAnswers++;

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            // Show next question
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.questionContainer, createQuestionFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            // Quiz completed
            if (isLandscape()) {
                updateStatistics();
            } else {
                // In portrait mode, show statistics fragment
                statisticsFragment = new StatisticsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionContainer, statisticsFragment)
                        .addToBackStack(null)  // Add to back stack so we can go back
                        .commit();
                // Ensure fragment is ready before updating statistics
                getSupportFragmentManager().executePendingTransactions();
                updateStatistics();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            currentQuestionIndex--;
            // Reset if we're going back from statistics
            if (currentQuestionIndex >= questions.length) {
                currentQuestionIndex = questions.length - 1;
            }
            // Clear statistics fragment reference in portrait mode
            if (!isLandscape()) {
                statisticsFragment = null;
            }
        } else {
            super.onBackPressed();
        }
    }

    private void updateStatistics() {
        if (statisticsFragment != null) {
            statisticsFragment.updateAnswers(correctAnswers, incorrectAnswers, questions.length);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        outState.putInt("correctAnswers", correctAnswers);
        outState.putInt("incorrectAnswers", incorrectAnswers);
    }

    private void initializeQuestions() {
        questions = new Question[]{
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

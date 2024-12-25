package com.example.lab6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView questionText;
    private ImageView questionImage;
    private RadioGroup radioGroup;
    private LinearLayout checkboxContainer;
    private EditText freeAnswerInput;
    private Button submitButton;
    private TextView scoreText;
    private LinearLayout quizContainer;
    private LinearLayout completionContainer;
    private Button restartButton;

    private Question[] questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private boolean isQuizCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeQuestions();

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
            correctAnswers = savedInstanceState.getInt("correctAnswers", 0);
            incorrectAnswers = savedInstanceState.getInt("incorrectAnswers", 0);
            isQuizCompleted = savedInstanceState.getBoolean("isQuizCompleted", false);
        }

        if (isQuizCompleted) {
            showCompletion();
        } else {
            displayQuestion(currentQuestionIndex);
        }
        updateScore();

        submitButton.setOnClickListener(v -> checkAnswer());
        restartButton.setOnClickListener(v -> restartQuiz());
    }

    private void initializeViews() {
        questionText = findViewById(R.id.questionText);
        questionImage = findViewById(R.id.questionImage);
        radioGroup = findViewById(R.id.radioGroup);
        checkboxContainer = findViewById(R.id.checkboxContainer);
        freeAnswerInput = findViewById(R.id.freeAnswerInput);
        submitButton = findViewById(R.id.submitButton);
        scoreText = findViewById(R.id.scoreText);
        quizContainer = findViewById(R.id.quizContainer);
        completionContainer = findViewById(R.id.completionContainer);
        restartButton = findViewById(R.id.restartButton);
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

    private void displayQuestion(int index) {
        Question currentQuestion = questions[index];
        questionText.setText(currentQuestion.getQuestionText());

        radioGroup.setVisibility(View.GONE);
        checkboxContainer.setVisibility(View.GONE);
        freeAnswerInput.setVisibility(View.GONE);
        questionImage.setVisibility(currentQuestion.getImageResourceId() != 0 ? View.VISIBLE : View.GONE);

        if (currentQuestion.getImageResourceId() != 0) {
            questionImage.setImageResource(currentQuestion.getImageResourceId());
        }

        switch (currentQuestion.getType()) {
            case SINGLE_CHOICE:
                setupSingleChoice(currentQuestion);
                break;
            case MULTIPLE_CHOICE:
                setupMultipleChoice(currentQuestion);
                break;
            case FREE_ANSWER:
                setupFreeAnswer();
                break;
        }
    }

    private void setupSingleChoice(Question question) {
        radioGroup.setVisibility(View.VISIBLE);
        radioGroup.removeAllViews();

        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }
    }

    private void setupMultipleChoice(Question question) {
        checkboxContainer.setVisibility(View.VISIBLE);
        checkboxContainer.removeAllViews();

        for (String option : question.getOptions()) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(option);
            checkboxContainer.addView(checkBox);
        }
    }

    private void setupFreeAnswer() {
        freeAnswerInput.setVisibility(View.VISIBLE);
        freeAnswerInput.setText("");
    }

    private void checkAnswer() {
        Question currentQuestion = questions[currentQuestionIndex];
        boolean isCorrect = false;

        switch (currentQuestion.getType()) {
            case SINGLE_CHOICE:
                RadioButton selectedRadio = findViewById(radioGroup.getCheckedRadioButtonId());
                if (selectedRadio != null) {
                    isCorrect = selectedRadio.getText().toString().equals(currentQuestion.getCorrectAnswer());
                }
                break;

            case MULTIPLE_CHOICE:
                StringBuilder selectedOptions = new StringBuilder();
                for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) checkboxContainer.getChildAt(i);
                    if (checkBox.isChecked()) {
                        if (selectedOptions.length() > 0) selectedOptions.append(",");
                        selectedOptions.append(checkBox.getText().toString());
                    }
                }
                isCorrect = selectedOptions.toString().equals(currentQuestion.getCorrectAnswer());
                break;

            case FREE_ANSWER:
                String userAnswer = freeAnswerInput.getText().toString().toLowerCase().trim();
                isCorrect = userAnswer.equals(currentQuestion.getCorrectAnswer().toLowerCase());
                break;
        }

        if (isCorrect) correctAnswers++;
        else incorrectAnswers++;
        updateScore();

        currentQuestionIndex++;
        if (currentQuestionIndex >= questions.length) {
            showCompletion();
        } else {
            displayQuestion(currentQuestionIndex);
        }
    }

    private void showCompletion() {
        isQuizCompleted = true;
        quizContainer.setVisibility(View.GONE);
        completionContainer.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.GONE);
        restartButton.setVisibility(View.VISIBLE);

        // Launch statistics activity
        Intent intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("incorrectAnswers", incorrectAnswers);
        intent.putExtra("totalQuestions", questions.length);
        startActivity(intent);
    }


    private void restartQuiz() {
        isQuizCompleted = false;
        currentQuestionIndex = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        quizContainer.setVisibility(View.VISIBLE);
        completionContainer.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.GONE);
        displayQuestion(currentQuestionIndex);
        updateScore();
    }

    @SuppressLint("DefaultLocale")
    private void updateScore() {
        scoreText.setText(String.format("Correct: %d | Incorrect: %d", correctAnswers, incorrectAnswers));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        outState.putInt("correctAnswers", correctAnswers);
        outState.putInt("incorrectAnswers", incorrectAnswers);
        outState.putBoolean("isQuizCompleted", isQuizCompleted);
    }
}

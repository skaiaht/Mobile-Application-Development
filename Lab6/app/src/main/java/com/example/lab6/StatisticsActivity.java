package com.example.lab6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {
    private TextView statisticsText;
    private Button shareButton;
    private Button learnMoreButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statisticsText = findViewById(R.id.statisticsText);
        shareButton = findViewById(R.id.shareButton);
        learnMoreButton = findViewById(R.id.learnMoreButton);
        backButton = findViewById(R.id.backButton);

        // Get statistics from intent
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int incorrectAnswers = getIntent().getIntExtra("incorrectAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        updateStatistics(correctAnswers, incorrectAnswers, totalQuestions);

        shareButton.setOnClickListener(v -> shareStatistics(correctAnswers, incorrectAnswers));
        learnMoreButton.setOnClickListener(v -> openLearnMore());
        backButton.setOnClickListener(v -> finish());
    }

    private void updateStatistics(int correctAnswers, int incorrectAnswers, int totalQuestions) {
        int totalAttempts = correctAnswers + incorrectAnswers;
        @SuppressLint("DefaultLocale") String statistics = String.format(
                "Quiz Statistics:\n\n" +
                        "Total Questions: %d\n" +
                        "Correct Answers: %d\n" +
                        "Incorrect Answers: %d\n" +
                        "Success Rate: %.1f%%",
                totalQuestions,
                correctAnswers,
                incorrectAnswers,
                totalAttempts > 0 ? (correctAnswers * 100.0f) / totalAttempts : 0
        );
        statisticsText.setText(statistics);
    }

    private void shareStatistics(int correctAnswers, int incorrectAnswers) {
        int totalAttempts = correctAnswers + incorrectAnswers;
        @SuppressLint("DefaultLocale") String shareText = String.format(
                "I just completed the Skiing Quiz!\n\n" +
                        "My Results:\n" +
                        "Correct Answers: %d\n" +
                        "Incorrect Answers: %d\n" +
                        "Success Rate: %.1f%%",
                correctAnswers,
                incorrectAnswers,
                totalAttempts > 0 ? (correctAnswers * 100.0f) / totalAttempts : 0
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share Quiz Results"));
    }

    private void openLearnMore() {
        String url = "https://alltracksacademy.com/blog/beginner-skiing-tips/"; // Replace with your URL
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}


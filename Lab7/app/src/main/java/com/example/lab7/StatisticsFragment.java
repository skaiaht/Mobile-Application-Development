package com.example.lab7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class StatisticsFragment extends Fragment {
    private TextView statisticsText;
    private Button shareButton;
    private Button learnMoreButton;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int totalQuestions = 0;
    private boolean pendingUpdate = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        statisticsText = view.findViewById(R.id.statisticsText);
        shareButton = view.findViewById(R.id.shareButton);
        learnMoreButton = view.findViewById(R.id.learnMoreButton);

        shareButton.setOnClickListener(v -> shareStatistics());
        learnMoreButton.setOnClickListener(v -> openLearnMore());

        // Update statistics immediately if we have pending data
        if (pendingUpdate) {
            updateStatistics();
            pendingUpdate = false;
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Ensure statistics are shown when fragment becomes visible
        updateStatistics();
    }

    public void updateAnswers(int correct, int incorrect, int total) {
        this.correctAnswers = correct;
        this.incorrectAnswers = incorrect;
        this.totalQuestions = total;

        if (isAdded() && statisticsText != null) {
            updateStatistics();
        } else {
            pendingUpdate = true;
        }
    }

    private void updateStatistics() {
        if (!isAdded() || statisticsText == null) return;

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

    private void shareStatistics() {
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
        String url = "https://alltracksacademy.com/blog/beginner-skiing-tips/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}

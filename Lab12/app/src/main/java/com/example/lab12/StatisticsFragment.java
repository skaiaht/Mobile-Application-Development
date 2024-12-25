package com.example.lab12;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.lab12.databinding.FragmentStatisticsBinding;

public class StatisticsFragment extends Fragment {
    private FragmentStatisticsBinding binding;
    private StatisticsViewModel viewModel;
    private boolean pendingUpdate = false;
    private int pendingCorrect, pendingIncorrect, pendingTotal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);

        binding.setFragment(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        if (pendingUpdate) {
            viewModel.updateStatistics(pendingCorrect, pendingIncorrect, pendingTotal);
            pendingUpdate = false;
        }

        return binding.getRoot();
    }

    public void updateAnswers(int correct, int incorrect, int total) {
        if (viewModel != null) {
            viewModel.updateStatistics(correct, incorrect, total);
        } else {
            pendingUpdate = true;
            pendingCorrect = correct;
            pendingIncorrect = incorrect;
            pendingTotal = total;
        }
    }

    public void shareStatistics() {
        Integer correct = viewModel.getCorrectAnswers().getValue();
        Integer incorrect = viewModel.getIncorrectAnswers().getValue();
        Float rate = viewModel.getSuccessRate().getValue();

        if (correct == null || incorrect == null || rate == null) return;

        @SuppressLint("DefaultLocale") String shareText = String.format(
                "I just completed the Skiing Quiz!\n\n" +
                        "My Results:\n" +
                        "Correct Answers: %d\n" +
                        "Incorrect Answers: %d\n" +
                        "Success Rate: %.1f%%",
                correct, incorrect, rate
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share Quiz Results"));
    }

    public void openLearnMore() {
        String url = "https://alltracksacademy.com/blog/beginner-skiing-tips/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

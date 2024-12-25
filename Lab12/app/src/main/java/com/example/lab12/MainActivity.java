package com.example.lab12;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.lab12.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements QuestionFragment.QuestionAnswerListener {
    private ActivityMainBinding binding;
    private QuizViewModel viewModel;
    private StatisticsFragment statisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        if (isLandscape()) {
            statisticsFragment = new StatisticsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.questionContainer, createQuestionFragment())
                    .replace(R.id.statisticsContainer, statisticsFragment)
                    .commit();

            getSupportFragmentManager().executePendingTransactions();
            updateStatistics();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.questionContainer, createQuestionFragment())
                    .commit();
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private QuestionFragment createQuestionFragment() {
        QuestionFragment fragment = QuestionFragment.newInstance(viewModel.getCurrentQuestion());
        fragment.setAnswerListener(this);
        return fragment;
    }

    @Override
    public void onAnswerSubmitted(boolean isCorrect) {
        if (isCorrect) viewModel.incrementCorrectAnswers();
        else viewModel.incrementIncorrectAnswers();

        int nextIndex = viewModel.getCurrentQuestionIndex().getValue() + 1;
        if (nextIndex < viewModel.getTotalQuestions()) {
            viewModel.setCurrentQuestionIndex(nextIndex);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.questionContainer, createQuestionFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            if (isLandscape()) {
                updateStatistics();
            } else {
                statisticsFragment = new StatisticsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionContainer, statisticsFragment)
                        .addToBackStack(null)
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
                updateStatistics();
            }
        }
    }

    private void updateStatistics() {
        if (statisticsFragment != null) {
            statisticsFragment.updateAnswers(
                    viewModel.getCorrectAnswers().getValue(),
                    viewModel.getIncorrectAnswers().getValue(),
                    viewModel.getTotalQuestions()
            );
        }
    }
}

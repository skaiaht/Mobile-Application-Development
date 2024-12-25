package com.example.lab12;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticsViewModel extends ViewModel {
    private final MutableLiveData<Integer> correctAnswers = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> incorrectAnswers = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> totalQuestions = new MutableLiveData<>(0);
    private final MutableLiveData<Float> successRate = new MutableLiveData<>(0f);

    public MutableLiveData<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public MutableLiveData<Integer> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public MutableLiveData<Integer> getTotalQuestions() {
        return totalQuestions;
    }

    public MutableLiveData<Float> getSuccessRate() {
        return successRate;
    }

    public void updateStatistics(int correct, int incorrect, int total) {
        correctAnswers.setValue(correct);
        incorrectAnswers.setValue(incorrect);
        totalQuestions.setValue(total);

        int totalAttempts = correct + incorrect;
        float rate = totalAttempts > 0 ? (correct * 100.0f) / totalAttempts : 0;
        successRate.setValue(rate);
    }
}

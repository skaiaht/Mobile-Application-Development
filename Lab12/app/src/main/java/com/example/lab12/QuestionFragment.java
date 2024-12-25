package com.example.lab12;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.lab12.databinding.FragmentQuestionBinding;

public class QuestionFragment extends Fragment {
    private FragmentQuestionBinding binding;
    private Question question;
    private QuestionAnswerListener answerListener;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("question", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("question");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        binding.setQuestion(question);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        setupQuestionViews();
        return binding.getRoot();
    }

    private void setupQuestionViews() {
        switch (question.getType()) {
            case SINGLE_CHOICE:
                setupSingleChoice();
                break;
            case MULTIPLE_CHOICE:
                setupMultipleChoice();
                break;
        }
    }

    private void setupSingleChoice() {
        binding.radioGroup.removeAllViews();
        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(option);
            binding.radioGroup.addView(radioButton);
        }
    }

    private void setupMultipleChoice() {
        binding.checkboxContainer.removeAllViews();
        for (String option : question.getOptions()) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(option);
            binding.checkboxContainer.addView(checkBox);
        }
    }

    public void checkAnswer() {
        boolean isCorrect = false;

        switch (question.getType()) {
            case SINGLE_CHOICE:
                RadioButton selectedRadio = requireView().findViewById(binding.radioGroup.getCheckedRadioButtonId());
                if (selectedRadio != null) {
                    isCorrect = selectedRadio.getText().toString().equals(question.getCorrectAnswer());
                }
                break;

            case MULTIPLE_CHOICE:
                StringBuilder selectedOptions = new StringBuilder();
                for (int i = 0; i < binding.checkboxContainer.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) binding.checkboxContainer.getChildAt(i);
                    if (checkBox.isChecked()) {
                        if (selectedOptions.length() > 0) selectedOptions.append(",");
                        selectedOptions.append(checkBox.getText().toString());
                    }
                }
                isCorrect = selectedOptions.toString().equals(question.getCorrectAnswer());
                break;

            case FREE_ANSWER:
                String userAnswer = binding.freeAnswerInput.getText().toString().toLowerCase().trim();
                isCorrect = userAnswer.equals(question.getCorrectAnswer().toLowerCase());
                break;
        }

        if (answerListener != null) {
            answerListener.onAnswerSubmitted(isCorrect);
        }
    }

    public void setAnswerListener(QuestionAnswerListener listener) {
        this.answerListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface QuestionAnswerListener {
        void onAnswerSubmitted(boolean isCorrect);
    }
}

package com.example.lab8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {
    private TextView questionText;
    private ImageView questionImage;
    private RadioGroup radioGroup;
    private LinearLayout checkboxContainer;
    private EditText freeAnswerInput;
    private Button submitButton;

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
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        questionText = view.findViewById(R.id.questionText);
        questionImage = view.findViewById(R.id.questionImage);
        radioGroup = view.findViewById(R.id.radioGroup);
        checkboxContainer = view.findViewById(R.id.checkboxContainer);
        freeAnswerInput = view.findViewById(R.id.freeAnswerInput);
        submitButton = view.findViewById(R.id.submitButton);

        setupQuestion();

        submitButton.setOnClickListener(v -> checkAnswer());

        return view;
    }

    private void setupQuestion() {
        questionText.setText(question.getQuestionText());

        radioGroup.setVisibility(View.GONE);
        checkboxContainer.setVisibility(View.GONE);
        freeAnswerInput.setVisibility(View.GONE);

        if (question.getImageResourceId() != 0) {
            questionImage.setVisibility(View.VISIBLE);
            questionImage.setImageResource(question.getImageResourceId());
        } else {
            questionImage.setVisibility(View.GONE);
        }

        switch (question.getType()) {
            case SINGLE_CHOICE:
                setupSingleChoice();
                break;
            case MULTIPLE_CHOICE:
                setupMultipleChoice();
                break;
            case FREE_ANSWER:
                setupFreeAnswer();
                break;
        }
    }

    private void setupSingleChoice() {
        radioGroup.setVisibility(View.VISIBLE);
        radioGroup.removeAllViews();

        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }
    }

    private void setupMultipleChoice() {
        checkboxContainer.setVisibility(View.VISIBLE);
        checkboxContainer.removeAllViews();

        for (String option : question.getOptions()) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(option);
            checkboxContainer.addView(checkBox);
        }
    }

    private void setupFreeAnswer() {
        freeAnswerInput.setVisibility(View.VISIBLE);
        freeAnswerInput.setText("");
    }

    private void checkAnswer() {
        boolean isCorrect = false;

        switch (question.getType()) {
            case SINGLE_CHOICE:
                RadioButton selectedRadio = requireView().findViewById(radioGroup.getCheckedRadioButtonId());
                if (selectedRadio != null) {
                    isCorrect = selectedRadio.getText().toString().equals(question.getCorrectAnswer());
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
                isCorrect = selectedOptions.toString().equals(question.getCorrectAnswer());
                break;

            case FREE_ANSWER:
                String userAnswer = freeAnswerInput.getText().toString().toLowerCase().trim();
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

    public interface QuestionAnswerListener {
        void onAnswerSubmitted(boolean isCorrect);
    }
}

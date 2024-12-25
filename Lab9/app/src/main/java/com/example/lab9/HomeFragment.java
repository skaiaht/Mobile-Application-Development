package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button startQuizButton = view.findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            startActivity(intent);
        });

        return view;
    }
}

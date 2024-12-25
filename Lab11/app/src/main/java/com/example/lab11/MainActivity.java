// MainActivity.java
package com.example.lab11;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.lab11.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GameViewModel viewModel;
    private LetterTileView[][] tileViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        createGrid();
        setupGame();
        setupObservers();
        Toast.makeText(this, "DEBUG: " + viewModel.getCurrentWord(), Toast.LENGTH_SHORT).show();
    }

    private void createGrid() {
        tileViews = new LetterTileView[6][5];
        int tileSize = getResources().getDisplayMetrics().widthPixels / 7;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                LetterTileView tile = new LetterTileView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = tileSize;
                params.height = tileSize;
                params.setMargins(4, 4, 4, 4);
                tile.setLayoutParams(params);
                binding.wordleGrid.addView(tile);
                tileViews[i][j] = tile;
            }
        }
    }

    private void setupGame() {
        binding.submitButton.setOnClickListener(v -> {
            String guess = binding.guessInput.getText().toString().toUpperCase();
            if (guess.length() != 5) {
                Toast.makeText(this, "Слово должно быть из 5 букв", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.makeAttempt(guess);
            binding.guessInput.setText("");
        });

        binding.finishButton.setOnClickListener(v -> {
            viewModel.endGame();
            showResult();
        });

        binding.restartButton.setOnClickListener(v -> {
            viewModel.startNewGame();
            updateGameState(false);
        });
    }

    private void setupObservers() {
        viewModel.getGrid().observe(this, grid -> {
            if (grid != null) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        tileViews[i][j].setLetter(grid[i][j]);
                    }
                }
            }
        });

        viewModel.getStates().observe(this, states -> {
            if (states != null) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        tileViews[i][j].setState(states[i][j]);
                    }
                }
            }
        });

        viewModel.getGameFinished().observe(this, isFinished -> {
            if (isFinished) {
                showResult();
                updateGameState(true);
            }
        });

        viewModel.getInputEnabled().observe(this, enabled -> {
            binding.guessInput.setEnabled(enabled);
            binding.submitButton.setEnabled(enabled);
        });
    }

    private void updateGameState(boolean gameEnded) {
        binding.finishButton.setVisibility(gameEnded ? View.GONE : View.VISIBLE);
        binding.restartButton.setVisibility(gameEnded ? View.VISIBLE : View.GONE);
        binding.guessInput.setEnabled(!gameEnded);
        binding.submitButton.setEnabled(!gameEnded);
    }

    private void showResult() {
        String message = "Правильное слово: " + viewModel.getCurrentWord();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

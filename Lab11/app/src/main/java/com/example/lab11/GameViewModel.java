// GameViewModel.java
package com.example.lab11;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Arrays;

public class GameViewModel extends ViewModel {
    private final String[] WORDS = {"КНИГА", "МЕСТО", "ВРЕМЯ", "ЖИЗНЬ", "СЛОВО", "ЧАСТЬ", "ЗЕМЛЯ"};
    private String currentWord;
    private final MutableLiveData<String[][]> grid;
    private final MutableLiveData<Integer[][]> states;
    private final MutableLiveData<Integer> currentRow;
    private final MutableLiveData<Boolean> gameFinished;
    private final MutableLiveData<Boolean> inputEnabled;

    public GameViewModel() {
        // Initialize all LiveData objects with initial values
        String[][] initialGrid = new String[6][5];
        Integer[][] initialStates = new Integer[6][5];
        for (int i = 0; i < 6; i++) {
            Arrays.fill(initialGrid[i], "");
            Arrays.fill(initialStates[i], 0);
        }

        grid = new MutableLiveData<>(initialGrid);
        states = new MutableLiveData<>(initialStates);
        currentRow = new MutableLiveData<>(0);
        gameFinished = new MutableLiveData<>(false);
        inputEnabled = new MutableLiveData<>(true);

        // Start new game after initialization
        startNewGame();
    }

    public void startNewGame() {
        currentWord = WORDS[(int)(Math.random() * WORDS.length)];

        String[][] newGrid = new String[6][5];
        Integer[][] newStates = new Integer[6][5];
        for (int i = 0; i < 6; i++) {
            Arrays.fill(newGrid[i], "");
            Arrays.fill(newStates[i], 0);
        }

        grid.setValue(newGrid);
        states.setValue(newStates);
        currentRow.setValue(0);
        gameFinished.setValue(false);
        inputEnabled.setValue(true);
    }

    public void makeAttempt(String guess) {
        if (!inputEnabled.getValue() || guess.length() != 5 || currentRow.getValue() >= 6) return;

        String[][] currentGrid = grid.getValue();
        Integer[][] currentStates = states.getValue();
        int row = currentRow.getValue();

        // Fill the current row
        for (int i = 0; i < 5; i++) {
            currentGrid[row][i] = String.valueOf(guess.charAt(i));
        }

        // Check letters
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == currentWord.charAt(i)) {
                currentStates[row][i] = 2; // Correct
            } else if (currentWord.indexOf(guess.charAt(i)) != -1) {
                currentStates[row][i] = 3; // Wrong position
            } else {
                currentStates[row][i] = 4; // Incorrect
            }
        }

        grid.setValue(currentGrid);
        states.setValue(currentStates);
        currentRow.setValue(row + 1);

        if (guess.equals(currentWord) || row == 5) {
            endGame();
        }
    }

    public void endGame() {
        gameFinished.setValue(true);
        inputEnabled.setValue(false);
    }

    public LiveData<String[][]> getGrid() { return grid; }
    public LiveData<Integer[][]> getStates() { return states; }
    public LiveData<Integer> getCurrentRow() { return currentRow; }
    public LiveData<Boolean> getGameFinished() { return gameFinished; }
    public LiveData<Boolean> getInputEnabled() { return inputEnabled; }
    public String getCurrentWord() { return currentWord; }
}
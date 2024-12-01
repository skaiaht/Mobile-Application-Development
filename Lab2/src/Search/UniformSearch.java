package src.Search;

import java.util.Arrays;

public class UniformSearch implements ISearch {
    @Override
    public boolean search(int[] array, int element) {
        return switch (array.length) {
            case 0 -> false;
            case 1 -> array[0] == element;
            default -> {
                for (int value : array) {
                    if (value == element) {
                        yield true;
                    }
                }
                yield false;
            }
        };
    }
}
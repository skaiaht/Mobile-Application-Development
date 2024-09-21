package src.Search;

import java.util.Arrays;

public class BinarySearch implements ISearch {
    @Override
    public boolean search(int[] array, int element) {
        if (!Check.isSorted(array)) {
            Arrays.sort(array);
        }

        return switch (array.length) {
            case 0 -> false;
            case 1 -> array[0] == element;
            default -> Arrays.binarySearch(array, element) >= 0;
        };
    }
}
package src.Search;

import java.util.stream.IntStream;

public class Check {
    public static boolean isSorted(int[] array) {
        return IntStream.range(0, array.length - 1).noneMatch(i -> array[i] > array[i + 1]);
    }
}
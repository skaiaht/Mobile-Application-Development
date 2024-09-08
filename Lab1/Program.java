import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Program {
    public static int binarySearchRecursiveInternal(int[] array, int target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        if (array[mid] == target) {
            return mid;
        } else if (array[mid] > target) {
            return binarySearchRecursiveInternal(array, target, low, mid - 1);
        } else {
            return binarySearchRecursiveInternal(array, target, mid + 1, high);
        }
    }

    public static int binarySearchRecursive(int[] array, int target) {
        return binarySearchRecursiveInternal(array, target, 0, array.length - 1);
    }

    public static int binarySearchIterative(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    public static List<String> splitString(String str, char delimiter) {
        List<String> result = new ArrayList<>();
        int start = 0; // To track the start of each substring

        for (int i = 0; i < str.length(); i++) {
            // When we find the delimiter, extract the substring
            if (str.charAt(i) == delimiter) {
                result.add(str.substring(start, i));
                start = i + 1; // Move the start to the character after the delimiter
            }
        }

        // Add the last part of the string after the last delimiter
        result.add(str.substring(start));

        return result;
    }

    public static void main(String[] args) {

        int[] largeArray = new int[1_000_000];
        Random random = new Random();

        // Generate 1 million random numbers
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = random.nextInt(1_000_000);
        }

        Arrays.sort(largeArray);

        int target = 92;
        int index = binarySearchRecursive(largeArray, target);

        if (index != -1) {
            System.out.println("[rec] found at index: " + index);
        } else {
            System.out.println("[rec] not found");
        }

        target = 72;
        index = binarySearchRecursive(largeArray, target);

        if (index != -1) {
            System.out.println("[rec] found at index: " + index);
        } else {
            System.out.println("[rec] not found");
        }

        target = 349;
        index = binarySearchIterative(largeArray, target);

        if (index != -1) {
            System.out.println("[iter] found at index: " + index);
        } else {
            System.out.println("[iter] found");
        }

        target = 379;
        index = binarySearchIterative(largeArray, target);

        if (index != -1) {
            System.out.println("[iter] found at index: " + index);
        } else {
            System.out.println("[iter] not found");
        }

        String input = "Test sentence to split with custom method";
        char delimiter = ' ';

        // Call the custom split method
        List<String> splitResult = splitString(input, delimiter);

        // Print the result
        for (String part : splitResult) {
            System.out.println(part);
        }
    }
}
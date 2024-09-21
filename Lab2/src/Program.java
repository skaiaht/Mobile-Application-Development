package src;

import src.Search.BinarySearch;
import src.Search.ISearch;
import src.Search.UniformSearch;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Program {

    private static int[] array;
    private static ISearch iSearch;

    public static void main(String[] args) {
        createArray();
        chooseSearchingAlgorithm();
        goSearch();
    }

    private static void createArray() {
        array = new int[100];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1001);
        }
        message("Array: " + Arrays.toString(array));
    }

    private static void chooseSearchingAlgorithm() {
        Scanner scanner = new Scanner(System.in);
        message("Search algorithm: 1 - Uniform search, 2 - Binary search: ");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            message("Invalid input");
            return;
        }

        switch (choice) {
            case 1:
                message("Uniform search");
                iSearch = new UniformSearch();
                break;
            case 2:
                message("Binary search");
                iSearch = new BinarySearch();
                break;
            default:
                message("Doesn't exist");
        }
    }

    private static void goSearch() {
        if (iSearch == null) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        message("Enter number: ");
        int element;
        try {
            element = scanner.nextInt();
        } catch (Exception any) {
            message("Invalid input");
            return;
        }

        boolean found = iSearch.search(array, element);
        if (found) {
            message("Number (" + element + ") found");
        } else {
            message("Number (" + element + ") not found");
        }
    }

    private static void message(String msg) {
        System.out.println(msg);
    }
}
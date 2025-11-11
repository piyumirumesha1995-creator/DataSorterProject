package datasorter;

import java.util.*;

public class DataSorter {
    private static Scanner sc = new Scanner(System.in);
    private static int[] data = new int[0];

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Data Sorter: Sorting Algorithm Comparison Tool ---");
            System.out.println("1. Enter numbers manually");
            System.out.println("2. Generate random numbers");
            System.out.println("3. Perform Bubble Sort");
            System.out.println("4. Perform Merge Sort");
            System.out.println("5. Perform Quick Sort");
            System.out.println("6. Compare all algorithms");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> enterNumbers();
                case 2 -> generateRandomNumbers();
                case 3 -> performSort("Bubble");
                case 4 -> performSort("Merge");
                case 5 -> performSort("Quick");
                case 6 -> compareAll();
                case 7 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ----------------- INPUT METHODS -----------------

    private static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static void enterNumbers() {
        System.out.print("How many numbers do you want to enter? ");
        int n = getIntInput();
        data = new int[n];
        System.out.println("Enter " + n + " numbers:");
        for (int i = 0; i < n; i++) {
            data[i] = getIntInput();
        }
        System.out.println("Numbers saved successfully!");
    }

    private static void generateRandomNumbers() {
        System.out.print("How many random numbers to generate? ");
        int n = getIntInput();
        Random rand = new Random();
        data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = rand.nextInt(100); // 0–99 range
        }
        System.out.println("Random numbers generated: " + Arrays.toString(data));
    }

    // ----------------- SORTING METHODS -----------------

    private static void performSort(String type) {
        if (data.length == 0) {
            System.out.println("No data available. Please enter or generate numbers first.");
            return;
        }

        int[] copy = Arrays.copyOf(data, data.length);
        long startTime = System.nanoTime();
        int steps = 0;

        switch (type) {
            case "Bubble" -> steps = bubbleSort(copy);
            case "Merge" -> steps = mergeSort(copy, 0, copy.length - 1);
            case "Quick" -> steps = quickSort(copy, 0, copy.length - 1);
        }

        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1e6;

        System.out.println(type + " Sort Result: " + Arrays.toString(copy));
        System.out.printf("%s Sort took %.6f ms and %d steps.%n", type, timeMs, steps);
    }

    private static void compareAll() {
        if (data.length == 0) {
            System.out.println("No data available. Please enter or generate numbers first.");
            return;
        }

        String[] algorithms = {"Bubble", "Merge", "Quick"};
        System.out.println("\n--- Performance Comparison ---");
        System.out.printf("%-10s %-15s %-10s%n", "Algorithm", "Time (ms)", "Steps");
        System.out.println("----------------------------------------");

        for (String algo : algorithms) {
            int[] copy = Arrays.copyOf(data, data.length);
            long start = System.nanoTime();
            int steps = switch (algo) {
                case "Bubble" -> bubbleSort(copy);
                case "Merge" -> mergeSort(copy, 0, copy.length - 1);
                case "Quick" -> quickSort(copy, 0, copy.length - 1);
                default -> 0;
            };
            long end = System.nanoTime();
            double timeMs = (end - start) / 1e6;
            System.out.printf("%-10s %-15.6f %-10d%n", algo, timeMs, steps);
        }
    }

    // ----------------- ALGORITHM IMPLEMENTATIONS -----------------

    private static int bubbleSort(int[] arr) {
        int steps = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                steps++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return steps;
    }

    private static int mergeSort(int[] arr, int left, int right) {
        int steps = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            steps += mergeSort(arr, left, mid);
            steps += mergeSort(arr, mid + 1, right);
            steps += merge(arr, left, mid, right);
        }
        return steps;
    }

    private static int merge(int[] arr, int left, int mid, int right) {
        int steps = 0;
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            steps++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) {
            arr[k++] = L[i++];
            steps++;
        }

        while (j < n2) {
            arr[k++] = R[j++];
            steps++;
        }

        return steps;
    }

    private static int quickSort(int[] arr, int low, int high) {
        int steps = 0;
        if (low < high) {
            int[] result = partition(arr, low, high);
            int pi = result[0];
            steps += result[1];
            steps += quickSort(arr, low, pi - 1);
            steps += quickSort(arr, pi + 1, high);
        }
        return steps;
    }

    private static int[] partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        int steps = 0;

        for (int j = low; j < high; j++) {
            steps++;
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return new int[]{i + 1, steps};
    }
}

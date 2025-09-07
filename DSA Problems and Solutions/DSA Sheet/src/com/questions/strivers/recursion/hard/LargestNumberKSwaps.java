package com.questions.strivers.recursion.hard;

public class LargestNumberKSwaps {

    // Global variable to store the maximum result
    static String maxNumber;

    // Recursive function to find the maximum number
    public static void findMaximum(char[] number, int k, int index) {
        // Base condition
        if (k == 0 || index == number.length) {
            return;
        }

        char maxDigit = number[index];

        // Find the maximum digit in the remaining (right) part
        for (int i = index + 1; i < number.length; i++) {
            if (number[i] > maxDigit) {
                maxDigit = number[i];
            }
        }

        // If a better digit exists ahead, we need to explore swaps
        if (maxDigit != number[index]) {
            k--; // use one swap
        }

        // Explore all possible swaps with maxDigit
        for (int i = number.length - 1; i > index; i--) {
            if (number[i] == maxDigit) {
                // Swap
                swap(number, index, i);

                // Update maxNumber if new number is greater
                String current = new String(number);
                if (current.compareTo(maxNumber) > 0) {
                    maxNumber = current;
                }

                // Recurse for next index
                findMaximum(number, k, index + 1);

                // Backtrack (undo swap)
                swap(number, index, i);
            }
        }

        // If no swap is done, move to next digit
        if (maxDigit == number[index]) {
            findMaximum(number, k, index + 1);
        }
    }

    // Utility function to swap characters in char array
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        String number = "129814999";
        int k = 4;

        maxNumber = number; // initialize with the original number

        findMaximum(number.toCharArray(), k, 0);

        System.out.println("Largest number after " + k + " swaps: " + maxNumber);
    }
}

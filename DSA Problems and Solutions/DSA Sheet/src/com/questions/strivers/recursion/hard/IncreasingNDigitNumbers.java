package com.questions.strivers.recursion.hard;

import java.util.*;

public class IncreasingNDigitNumbers {

    // Recursive function to generate increasing N-digit numbers
    public static void generateNumbers(int n, int start, String current, List<String> result) {
        // Base case: if current length == n, we found a valid number
        if (current.length() == n) {
            result.add(current);
            return;
        }

        // Try all digits from 'start' to 9
        for (int digit = start; digit <= 9; digit++) {
            // Choose digit and recurse
            generateNumbers(n, digit + 1, current + digit, result);
        }
    }

    public static void main(String[] args) {
        int n = 3;  // Example: 3-digit increasing numbers
        List<String> result = new ArrayList<>();

        generateNumbers(n, 1, "", result);

        // Print results
        for (String num : result) {
            System.out.println(num);
        }
    }
}

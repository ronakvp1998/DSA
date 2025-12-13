package com.questions.strivers.recursionbacktracking.hard;

import java.util.*;

public class IncreasingNDigitNumbers {

    /**
     * Recursive function to generate all N-digit numbers
     * where digits are strictly increasing (from left to right).
     *
     * Example for n=3: 123, 124, 125 ... 789
     *
     * @param n       Length of the number (total digits required)
     * @param start   The digit to start from in the current recursive call
     * @param current The current number being formed (in string form)
     * @param result  List to store all valid N-digit numbers
     */
    private static void generateNumbers(int n, int start, String current, List<String> result) {
        // Base case: If current number length == n,
        // we found a valid increasing number → add to result
        if (current.length() == n) {
            result.add(current);
            return;
        }

        // Recursive case: Try all possible digits starting from 'start' up to 9
        for (int digit = start; digit <= 9; digit++) {
            // Choose the current digit and recurse with the next possible digit (digit+1)
            generateNumbers(n, digit + 1, current + digit, result);
        }
    }

    public static void main(String[] args) {
        int n = 3;  // Example: generate 3-digit increasing numbers
        List<String> result = new ArrayList<>();

        // Start recursion with digit '1' (since numbers don't start with 0)
        generateNumbers(n, 1, "", result);

        // Print all generated increasing N-digit numbers
        for (String num : result) {
            System.out.println(num);
        }
    }
}

/*
⏱️ Time Complexity
At each recursive call, we choose from a shrinking set of digits.
The total number of results is C(9, n) (combinations of 9 digits taken n at a time).
Each valid number requires O(n) time to build (string concatenation).
Time Complexity = O(C(9, n) * n)

💾 Space Complexity
Recursive stack depth = O(n) (since each call adds 1 digit).
Result storage = O(C(9, n) * n) (all numbers stored as strings).
Space Complexity = O(n + C(9, n) * n)
 */
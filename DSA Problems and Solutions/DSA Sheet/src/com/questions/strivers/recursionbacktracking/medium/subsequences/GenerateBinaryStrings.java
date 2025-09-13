package com.questions.strivers.recursionbacktracking.medium.subsequences;

public class GenerateBinaryStrings {

    /*
     * Problem: Generate all binary strings of length n.
     *
     * Example:
     * n = 3
     * Output:
     * 000
     * 001
     * 010
     * 011
     * 100
     * 101
     * 110
     * 111
     *
     * Approach:
     * - We use recursion to build strings digit by digit.
     * - At each index, we can either put '0' or '1'.
     * - Continue until the string reaches length n, then print it.
     */

    // Recursive function to generate all binary strings
    public static void generateBinaryStrings(int n, StringBuilder current) {
        // Base case: if the current string has length n, print it
        if (current.length() == n) {
            System.out.println(current.toString());
            return;
        }

        // Choice 1: Add '0'
        current.append('0');
        generateBinaryStrings(n, current);
        current.deleteCharAt(current.length() - 1); // backtrack

        // Choice 2: Add '1'
        current.append('1');
        generateBinaryStrings(n, current);
        current.deleteCharAt(current.length() - 1); // backtrack
    }

    public static void main(String[] args) {
        int n = 3; // Example: length of binary strings
        generateBinaryStrings(n, new StringBuilder());
    }
}
/*
Time Complexity:

There are 2^n possible binary strings.

For each string, building takes O(n) in worst case (though here printing dominates).

Total = O(n * 2^n)

Space Complexity:

Recursion depth = O(n).

StringBuilder stores at most n characters.

Total = O(n)
 */
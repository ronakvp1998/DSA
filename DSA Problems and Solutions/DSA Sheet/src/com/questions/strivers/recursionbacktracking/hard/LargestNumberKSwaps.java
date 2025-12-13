package com.questions.strivers.recursionbacktracking.hard;

/**
 * Problem Statement:
 * ------------------
 * Given a numeric string "number" and an integer k,
 * the task is to find the largest number possible by performing at most k swaps
 * of its digits.
 *
 * Example:
 * Input:  number = "129814999", k = 4
 * Output: "999984211"
 *
 * Explanation:
 * By performing at most 4 swaps optimally, we can rearrange digits to form
 * the maximum possible number.
 *
 * ------------------------------------------------------------
 * Code Logic:
 * 1. Start from index = 0, at each position find the maximum digit in the suffix.
 * 2. If the current digit is already the maximum, move to the next index.
 * 3. If a larger digit exists later:
 *    - Swap current digit with the rightmost occurrence of that maximum digit.
 *    - Update the global maximum string if the new number is greater.
 *    - Recurse for the next index with one less swap available (k-1).
 *    - Backtrack (undo the swap) to explore other possibilities.
 * 4. Continue until no swaps remain or we reach the end of the number.
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - Worst case: O(n! / (n-k)!)  (Exponential, since multiple swaps explored).
 * - At each index, multiple swaps may be tried and recursion depth can go up to n.
 * - In practice, pruning (choosing only max digits) reduces calls significantly.
 *
 * Space Complexity:
 * - O(n) recursion depth (stack space).
 * - No significant extra space apart from recursion and global string.
 */

public class LargestNumberKSwaps {

    // Global variable to store the maximum result found so far
    static String maxNumber;

    /**
     * Recursive function to find the maximum number possible
     * by performing at most 'k' swaps on the given number.
     *
     * @param number char array representing the number
     * @param k      number of swaps remaining
     * @param index  current index we are processing
     */
    private static void findMaximum(char[] number, int k, int index) {
        // Base condition: if no swaps left OR reached end of number, stop recursion
        if (k == 0 || index == number.length) {
            return;
        }

        // Step 1: Find the maximum digit in the suffix (from index to end)
        char maxDigit = number[index];
        for (int i = index + 1; i < number.length; i++) {
            if (number[i] > maxDigit) {
                maxDigit = number[i];
            }
        }

        // Step 2: If a larger digit exists later, then we must use a swap
        if (maxDigit != number[index]) {
            k--; // consume one swap
        }

        // Step 3: Explore all possible swaps with the maximum digit found
        for (int i = number.length - 1; i > index; i--) {
            if (number[i] == maxDigit) {
                // Swap current digit with the chosen maximum digit
                swap(number, index, i);

                // Update maxNumber if this new configuration is greater
                String current = new String(number);
                if (current.compareTo(maxNumber) > 0) {
                    maxNumber = current;
                }

                // Recurse for next index with updated array and remaining swaps
                findMaximum(number, k, index + 1);

                // Backtrack: undo the swap before exploring other possibilities
                swap(number, index, i);
            }
        }

        // Step 4: If no swap was needed (already max digit at position),
        // move to next digit without reducing k
        if (maxDigit == number[index]) {
            findMaximum(number, k, index + 1);
        }
    }

    // Utility function to swap two characters in a char array
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        String number = "129814999"; // input number
        int k = 4; // maximum allowed swaps

        maxNumber = number; // initialize global max with original number

        // Start recursive search
        findMaximum(number.toCharArray(), k, 0);

        // Print final result
        System.out.println("Largest number after " + k + " swaps: " + maxNumber);
    }
}

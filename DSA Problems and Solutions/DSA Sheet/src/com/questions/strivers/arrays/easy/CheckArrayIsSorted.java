package com.questions.strivers.arrays.easy;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: CHECK IF AN ARRAY IS SORTED
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * *
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array of size n, write a program to check if the given array is
 * sorted in (ascending / increasing / non-decreasing) order or not.
 * If the array is sorted then return True, Else return False.
 * * * Constraints:
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * * Input/Output Formats:
 * Input: An array of N integers.
 * Output: A boolean value (`true` if sorted, `false` otherwise).
 * * * Example 1:
 * Input: arr = [1, 2, 3, 4, 5]
 * Output: true
 * Explanation: Each element is greater than or equal to the previous one.
 * * * Example 2:
 * Input: arr = [5, 4, 6, 7, 8]
 * Output: false
 * Explanation: 4 is smaller than 5, which violates the non-decreasing rule.
 * * * Example 3:
 * Input: arr = [1, 1, 2, 2, 3]
 * Output: true
 * Explanation: The array is in non-decreasing order (duplicates are allowed).
 * * * Conceptual Visualization (The Staircase Logic):
 * Think of the array as a staircase. If you are climbing a non-decreasing
 * staircase, every step you take must either be at the same level or higher
 * than the previous step.
 * Step 0:  _
 * Step 1:   _
 * Step 2:    _
 * Step 3:    _ (allowed, same height)
 * Step 4:     _
 * If you ever have to step *down* (arr[i] < arr[i-1]), the staircase is broken,
 * and the array is NOT sorted.
 * ============================================================================
 */
public class CheckArrayIsSorted {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Single Pass Iteration)
     * ========================================================================
     * Approach and Steps:
     * 1. Handle base cases: If the array is null or has 0 or 1 elements, it
     * is inherently sorted. Return true.
     * 2. Traverse the array starting from the second element (index 1).
     * 3. At each step `i`, compare the current element `arr[i]` with the
     * previous element `arr[i-1]`.
     * 4. If `arr[i] < arr[i-1]`, it violates the sorted property. Return false.
     * 5. If the loop completes without finding any violations, return true.
     * * * Detailed Intuition:
     * To verify if an entire sequence is sorted, we only need to verify that
     * every adjacent pair is sorted. By checking pairs `(arr[0], arr[1])`,
     * `(arr[1], arr[2])`, etc., we ensure the transitive property holds. If
     * A <= B and B <= C, then A <= C. Thus, a single linear scan is sufficient.
     * * * Complexity Analysis:
     * - Time (O): O(N). We traverse the array exactly once, performing a single
     * comparison at each step.
     * - Space (O): O(1) auxiliary heap space. Only standard loop variables are used.
     * ========================================================================
     */
    public static boolean isSortedOptimal(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }

        for (int i = 1; i < arr.length; i++) {
            // The "step down" violation check
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }

        return true;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Use an outer loop `i` to select every element in the array one by one.
     * 2. Use an inner loop `j` (starting from `i + 1`) to compare the selected
     * element with *every* subsequent element.
     * 3. If any subsequent element `arr[j]` is strictly smaller than the current
     * element `arr[i]`, the array is not sorted. Return false.
     * 4. If all nested comparisons pass, return true.
     * * * Detailed Intuition:
     * A beginner might interpret "is sorted" as "every element to the right must
     * be greater than or equal to the current element". Translating this literal
     * definition into code leads to nested loops. While correct, it does massive
     * amounts of redundant work by not trusting the transitive property.
     * * * Complexity Analysis:
     * - Time (O): O(N^2). We compare every element against every other subsequent
     * element, resulting in N*(N-1)/2 comparisons.
     * - Space (O): O(1) auxiliary space.
     * ========================================================================
     */
    public static boolean isSortedBruteForce(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive)
     * ========================================================================
     * Approach and Steps:
     * 1. Define a helper function that takes the array and an `index`.
     * 2. Base Case: If `index` reaches the last element (length - 1), return true.
     * 3. Check: If the element at `index` is strictly greater than the element
     * at `index + 1`, return false.
     * 4. Recursive Step: Return the result of calling the function on `index + 1`.
     * * * Detailed Intuition:
     * This applies functional programming principles. An array is sorted if the
     * first two elements are sorted AND the rest of the array is sorted. We
     * recursively chop off the head of the problem until we hit the base case.
     * * * Complexity Analysis:
     * - Time (O): O(N). We visit each element once via recursive calls.
     * - Space (O): O(N) auxiliary stack space. The recursion goes N levels deep.
     * This approach is susceptible to StackOverflowError for large arrays.
     * ========================================================================
     */
    public static boolean isSortedAlternative(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }
        return isSortedRecursiveHelper(arr, 0);
    }

    private static boolean isSortedRecursiveHelper(int[] arr, int index) {
        // Base case: Reached the end of the array without violations
        if (index == arr.length - 1) {
            return true;
        }

        // Violation check
        if (arr[index] > arr[index + 1]) {
            return false;
        }

        // Recursive leap of faith
        return isSortedRecursiveHelper(arr, index + 1);
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * cases, edge cases, duplicates, and strictly descending arrays.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== CHECK IF ARRAY IS SORTED TESTING SUITE ===\n");

        int[][] testCases = {
                {1, 2, 3, 4, 5},            // Strictly increasing
                {1, 1, 2, 2, 3},            // Non-decreasing (with duplicates)
                {5, 4, 6, 7, 8},            // Single violation at the start
                {1, 2, 3, 5, 4},            // Single violation at the end
                {5, 4, 3, 2, 1},            // Strictly decreasing
                {42},                       // Single element (edge case)
                {},                         // Empty array (edge case)
                {7, 7, 7, 7, 7},            // All identical elements
                {-50, -10, 0, 4, 100}       // Negative numbers
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Test Brute Force
            long start1 = System.nanoTime();
            boolean res1 = isSortedBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // Test Alternative (Recursive)
            long start2 = System.nanoTime();
            boolean res2 = isSortedAlternative(tc);
            long end2 = System.nanoTime();
            System.out.println("Alternative Output: " + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Test Optimal (Linear Scan)
            long start3 = System.nanoTime();
            boolean res3 = isSortedOptimal(tc);
            long end3 = System.nanoTime();
            System.out.println("Optimal Output:     " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = (res1 == res2) && (res2 == res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(50) + "\n");
        }
    }
}
package strivers.arrays.easy;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: FIND THE LARGEST ELEMENT IN AN ARRAY
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * *
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array of integers `arr`, find and return the largest element present in the array.
 * * * Input/Output Formats:
 * Input: An array of N integers.
 * Output: A single integer representing the maximum value in the array.
 * * * Constraints (Standard DSA expectations):
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 *
 * * * Example 1:
 * Input: arr = [1, 8, 7, 56, 90]
 * Output: 90
 * Explanation: The largest element in the array is 90.
 *
 * * * Example 2:
 * Input: arr = [1, 2, 0, 3, 2, 4, 5]
 * Output: 5
 * Explanation: The largest element in the array is 5.
 *
 * * * Conceptual Visualization:
 * Imagine standing in front of a line of people and you want to find the tallest person.
 * You look at the first person and say, "You are the tallest I've seen so far."
 * Then you move to the next person. If they are taller than your current record,
 * you update your record. You continue this until you reach the end of the line.
 * ============================================================================
 */
public class LargestElementInArray {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Linear Scan)
     * ========================================================================
     * Approach and Steps:
     * 1. Check for edge cases (e.g., empty or null array).
     * 2. Initialize a variable `maxElement` with the first element of the array `arr[0]`.
     * 3. Traverse the array starting from the second element (index 1) to the end.
     * 4. In each iteration, compare the current element with `maxElement`.
     * 5. If the current element is strictly greater than `maxElement`, update `maxElement`.
     * 6. After the loop completes, return `maxElement`.
     * * * Detailed Intuition:
     * This approach mimics the natural human process of finding a maximum. By keeping a
     * running maximum, we ensure that we only need to look at each element exactly once.
     * There is no need to sort or alter the original array, preserving its state and
     * minimizing both time and memory overhead.
     * * * Complexity Analysis:
     * - Time (O): O(N) where N is the length of the array. We perform a single pass.
     * - Space (O): O(1) auxiliary heap space. Only a single variable is used.
     * ========================================================================
     */
    public static int findLargestOptimal(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int maxElement = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxElement) {
                maxElement = arr[i];
            }
        }

        return maxElement;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage (Sorting)
     * ========================================================================
     * Approach and Steps:
     * 1. Check for edge cases (e.g., empty or null array).
     * 2. Use a built-in sorting algorithm to sort the array in ascending order.
     * 3. Once sorted, the largest element will naturally be positioned at the
     * very end of the array.
     * 4. Return the element at `arr.length - 1`.
     * * * Detailed Intuition:
     * Often, beginners associate finding extremes (min/max) with order. If an array
     * is completely ordered, finding the largest is trivial (just grab the last one).
     * However, sorting does significantly more work than necessary, as it orders *every* * element, not just the maximum.
     * * * Complexity Analysis:
     * - Time (O): O(N log N). Sorting the array dominates the time complexity.
     * - Space (O): O(1) to O(N) depending on the sorting algorithm used under the hood
     * (Java's Arrays.sort uses Dual-Pivot Quicksort for primitives, taking O(log N) stack space).
     * ========================================================================
     */
    public static int findLargestBruteForce(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        // Clone to avoid modifying the original array for testing purposes
        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        return sortedArr[sortedArr.length - 1];
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive Divide and Conquer)
     * ========================================================================
     * Approach and Steps:
     * 1. Define a helper recursive function that takes the array and an index.
     * 2. Base Case: If the index reaches the last element, return that element.
     * 3. Recursive Step: Find the maximum of the rest of the array by recursively
     * calling the function with `index + 1`.
     * 4. Return the maximum between the current element `arr[index]` and the
     * result of the recursive call.
     * * * Detailed Intuition:
     * Recursion breaks the problem down: The maximum of the array is the maximum
     * between the first element and the maximum of the *rest* of the array. While
     * elegant, it is highly impractical for large arrays in Java due to StackOverflow
     * risks, but it demonstrates functional programming thinking.
     * * * Complexity Analysis:
     * - Time (O): O(N). The function is called N times, doing O(1) work per call.
     * - Space (O): O(N) auxiliary stack space. The recursion goes N levels deep before
     * returning, which can easily trigger a StackOverflowError for large N.
     * ========================================================================
     */
    public static int findLargestAlternative(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        return findLargestRecursiveHelper(arr, 0);
    }

    private static int findLargestRecursiveHelper(int[] arr, int index) {
        // Base case: only one element left to consider
        if (index == arr.length - 1) {
            return arr[index];
        }

        // Find max in the remaining array
        int maxOfRest = findLargestRecursiveHelper(arr, index + 1);

        // Return the larger of the current element or the max of the rest
        return Math.max(arr[index], maxOfRest);
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * cases, negative numbers, duplicates, and single-element arrays.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== LARGEST ELEMENT IN ARRAY TESTING SUITE ===\n");

        int[][] testCases = {
                {1, 8, 7, 56, 90},          // Standard Case 1
                {1, 2, 0, 3, 2, 4, 5},      // Standard Case 2
                {-10, -5, -3, -99, -1},     // All negative numbers
                {42},                       // Single element
                {7, 7, 7, 7, 7},            // All duplicates
                {100, 20, 30, 40, 50},      // Largest element at the beginning
                {0, 0, 0, 0}                // All zeroes
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Test Brute Force (Sorting)
            long start1 = System.nanoTime();
            int res1 = findLargestBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // Test Alternative (Recursive)
            long start2 = System.nanoTime();
            int res2 = findLargestAlternative(tc);
            long end2 = System.nanoTime();
            System.out.println("Alternative Output: " + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Test Optimal (Linear Scan)
            long start3 = System.nanoTime();
            int res3 = findLargestOptimal(tc);
            long end3 = System.nanoTime();
            System.out.println("Optimal Output:     " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = (res1 == res2) && (res2 == res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(50) + "\n");
        }
    }
}

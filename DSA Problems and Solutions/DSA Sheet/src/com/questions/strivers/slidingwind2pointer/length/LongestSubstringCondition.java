package com.questions.strivers.slidingwind2pointer.length;

/**
 * ============================================================================
 * DSA MASTERCLASS: Longest Subarray (Substring) with Sum <= K
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an array of non-negative integers `arr[]` and an integer `k`, find the
 * length of the longest contiguous subarray (or substring, if treating elements
 * as a sequence) where the sum of its elements is less than or equal to `k`.
 *
 * Constraints:
 * - 1 <= arr.length <= 10^5
 * - 0 <= arr[i] <= 10^4  (Elements must be non-negative for the sliding window to work optimally)
 * - 1 <= k <= 10^9
 *
 * Input Format:
 * - `arr`: An array of non-negative integers.
 * - `k`: An integer representing the maximum allowed sum.
 *
 * Output Format:
 * - An integer representing the length of the maximum valid subarray. Returns 0
 *   if no valid subarray is found.
 *
 * Examples:
 * ---------
 * Example 1:
 * Input: arr = {2, 5, 1, 7, 10}, k = 14
 * Output: 3
 * Explanation:
 * Subarrays and sums:
 *   {2, 5, 1} -> Sum: 8 (Length 3, <= 14) ✅
 *   {5, 1, 7} -> Sum: 13 (Length 3, <= 14) ✅
 *   {1, 7, 10} -> Sum: 18 (Length 3, > 14) ❌
 * The longest valid subarray is of length 3.
 *
 * Example 2:
 * Input: arr = {1, 2, 1, 0, 1, 1, 0}, k = 4
 * Output: 5
 * Explanation:
 * The subarray {1, 2, 1, 0} has sum 4 (length 4).
 * The subarray {2, 1, 0, 1} has sum 4 (length 4).
 * The subarray {1, 0, 1, 1, 0} has sum 3 (length 5). <= Longest
 *
 * Example 3 (Edge Case):
 * Input: arr = {10, 20, 30}, k = 5
 * Output: 0
 * Explanation: No single element is <= 5.
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ============================================================================
 * Evaluation of provided code:
 * The provided code includes an excellent progression from Brute Force (O(N^2))
 * to Standard Sliding Window (O(2N)) to an Optimized Sliding Window (strictly O(N)).
 * I have structured these exact approaches into the requested phases, fixing minor
 * bugs (like returning `Integer.MIN_VALUE` instead of `0` when no valid window is found).
 */

import java.util.Arrays;

public class LongestSubstringCondition {

    /**
     * PHASE 1: Optimal Approach - Optimized Sliding Window
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * In the standard sliding window (Phase 3), we shrink the window using a `while`
     * loop until the sum becomes valid again. However, since we only care about the
     * MAXIMUM length, there is no point in shrinking the window size to be smaller
     * than our currently recorded `maxLen`.
     *
     * Instead of a `while` loop, we use an `if` condition. If the sum exceeds `k`,
     * we slide the ENTIRE window forward by moving both `l` and `r` pointers simultaneously.
     * This keeps the window size strictly equal to the `maxLen` found so far. The window
     * will only grow (by skipping the `l++` step) when a valid sum is found for a larger size.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Both the `r` and `l` pointers only traverse the array once. Because there is
     *   no inner `while` loop, this runs in strictly N iterations.
     * - Space Complexity: O(1) Auxiliary Space
     *   Only primitive variables are used for state tracking.
     */
    public static int longestSubstringConditionOptimal(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int maxLen = 0; // Changed from Integer.MIN_VALUE to 0 to handle cases where all elements > k
        int l = 0, r = 0, n = arr.length, sum = 0;

        while (r < n) {
            sum = sum + arr[r];

            // If invalid, shift the left pointer to maintain the max window size found so far.
            // We use 'if' instead of 'while' because we don't need to shrink the window
            // smaller than our current best maxLen.
            if (sum > k) {
                sum = sum - arr[l];
                l++;
            }

            // Only update maxLen if the current window is valid
            if (sum <= k) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
            r++;
        }
        return maxLen;
    }

    /**
     * PHASE 2: Brute Force Approach - Generate All Subarrays
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * The simplest way to solve this is to generate every possible contiguous subarray,
     * calculate its sum, and check if it satisfies the condition `<= k`. We use two
     * nested loops: the outer loop `i` defines the starting point, and the inner
     * loop `j` expands the subarray to the right, accumulating the sum.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2)
     *   For every starting index `i` from 0 to N-1, we iterate up to N times.
     *   This leads to roughly N*(N+1)/2 iterations.
     * - Space Complexity: O(1) Auxiliary Space
     *   No extra memory structures are allocated.
     */
    public static int longestSubstringConditionBruteForce(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int maxLen = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum = sum + arr[j];
                if (sum <= k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                } else {
                    // Optimization: Since numbers are non-negative, if sum exceeds k,
                    // adding more numbers will only increase the sum further. Break early.
                    break;
                }
            }
        }
        return maxLen;
    }

    /**
     * PHASE 3: Alternative Approach - Standard Sliding Window / Two Pointers
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * We use a sliding window with two pointers (`l` and `r`). We expand the window
     * to the right by adding `arr[r]` to our sum. If the sum exceeds `k`, the window
     * becomes invalid. We then shrink the window from the left using a `while` loop,
     * subtracting `arr[l]` and incrementing `l`, until the sum is valid (`<= k`) again.
     * Once valid, we calculate the length and update `maxLen`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2N) ≈ O(N)
     *   In the worst case, the right pointer `r` traverses the array once, and the left
     *   pointer `l` also traverses the array once. So, elements are visited at most twice.
     * - Space Complexity: O(1) Auxiliary Space
     *   We only maintain `l`, `r`, `sum`, and `maxLen`.
     */
    public static int longestSubstringConditionStandard(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int maxLen = 0;
        int l = 0, r = 0, n = arr.length, sum = 0;

        while (r < n) {
            sum = sum + arr[r];

            // Shrink the window until the sum becomes valid again
            while (sum > k && l <= r) {
                sum = sum - arr[l];
                l++;
            }

            if (sum <= k) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
            r = r + 1;
        }
        return maxLen;
    }

    /**
     * 4. TESTING SUITE
     * ----------------------------------------------------------------------------
     * Validating all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        int[][] testCases = {
                {2, 5, 1, 7, 10},        // Standard Case 1
                {1, 2, 1, 0, 1, 1, 0},   // Standard Case 2 (with zeroes)
                {10, 20, 30},            // Edge Case: All elements > k
                {1, 1, 1, 1, 1},         // Edge Case: Entire array is valid
                {5},                     // Edge Case: Single element valid
                {10}                     // Edge Case: Single element invalid
        };

        int[] kValues = {14, 4, 5, 10, 5, 5};
        int[] expectedResults = {3, 5, 0, 5, 1, 0};

        System.out.println("======================================================");
        System.out.println("TESTING SUITE: Longest Subarray with Sum <= K");
        System.out.println("======================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            int k = kValues[i];
            int expected = expectedResults[i];

            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(arr) + " | k = " + k);

            int bruteResult = longestSubstringConditionBruteForce(arr, k);
            int standardResult = longestSubstringConditionStandard(arr, k);
            int optimalResult = longestSubstringConditionOptimal(arr, k);

            boolean passed = (bruteResult == expected) &&
                    (standardResult == expected) &&
                    (optimalResult == expected);

            System.out.println("Brute Force Output: " + bruteResult);
            System.out.println("Standard Window   : " + standardResult);
            System.out.println("Optimal Window    : " + optimalResult);
            System.out.println("Expected          : " + expected);
            System.out.println("Status            : " + (passed ? "✅ PASS" : "❌ FAIL"));
            System.out.println("------------------------------------------------------");
        }
    }
}
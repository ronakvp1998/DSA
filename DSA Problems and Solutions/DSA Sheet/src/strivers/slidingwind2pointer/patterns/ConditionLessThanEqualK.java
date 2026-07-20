/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array of non-negative integers `arr` and an integer `k`, find the 
 * maximum length of a contiguous subarray such that the sum of its elements 
 * is strictly less than or equal to `k`.
 *
 * Constraints:
 * - 1 <= arr.length <= 10^5
 * - 0 <= arr[i] <= 10^4  (Note: Sliding window approaches below assume non-negative 
 *                         integers. If negative numbers exist, a different 
 *                         approach like Prefix Sums with Hash Maps or Binary 
 *                         Search is required).
 * - 0 <= k <= 10^9
 *
 * Input/Output Formats:
 * - Input: An integer array `arr` and an integer `k`.
 * - Output: An integer representing the max length of a valid subarray.
 *
 * Examples:
 * Example 1:
 * Input: arr = [2, 5, 1, 7, 10], k = 14
 * Output: 3
 * Explanation: The subarrays [2, 5, 1] (sum 8) and [5, 1, 7] (sum 13) both have 
 * length 3 and sum <= 14. Maximum length is 3.
 *
 * Example 2:
 * Input: arr = [1, 2, 3, 4, 5], k = 5
 * Output: 2
 * Explanation: The subarray [2, 3] has a sum of 5 <= 5 and length 2.
 *
 * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * - Phase 1: Optimal Approach - Highly Optimized Sliding Window O(N).
 * - Phase 2: Brute Force Approach - Nested Loops O(N^2).
 * - Phase 3: Alternative Approach - Classic Sliding Window O(2N).
 * ============================================================================
 */
package strivers.slidingwind2pointer.patterns;

import java.util.Arrays;

public class ConditionLessThanEqualK {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Highly Optimized Sliding Window)
     * ========================================================================
     * Detailed Intuition:
     * This is a brilliant optimization over the classic sliding window (Phase 3).
     * Instead of using a `while` loop to shrink the window until it's valid, 
     * we only shrink it by EXACTLY ONE element (using an `if` condition) if 
     * the sum exceeds `k`. 
     *
     * Why does this work? Because we only care about the *maximum* length. 
     * If we find a valid window of size `L`, we never need to check windows 
     * of size `< L` again. When an invalid element is added, shifting both 
     * `l` and `r` by 1 maintains the maximum window size found so far. The 
     * window only grows when the sum is valid.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). The right pointer `r` traverses the array exactly 
     *   N times. The left pointer `l` increments at most once per iteration of `r`. 
     *   Therefore, the maximum operations are strictly bounded by N.
     * - Space Complexity: O(1) auxiliary stack space. Only primitive variables 
     *   are used. No heap space is allocated.
     */
    public static int optimalSlidingWindow(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int l = 0, r = 0, sum = 0, maxLen = 0, n = arr.length;

        while (r < n) {
            sum = sum + arr[r];

            // Replaced 'while' with 'if'. We just shift the window rightward.
            if (sum > k) {
                sum = sum - arr[l];
                l = l + 1;
            }

            // Check maxLen only when the sum is valid
            if (sum <= k) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
            r = r + 1;
        }
        return maxLen;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * Detailed Intuition:
     * The simplest way to solve this is to generate all possible subarrays, 
     * calculate their sums, and keep track of the maximum length among those 
     * whose sum is <= k. We use an outer loop to fix the starting point and 
     * an inner loop to expand the subarray. We break early if the sum exceeds 
     * `k` (since all numbers are non-negative, the sum will only increase).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). For each element, we potentially iterate through 
     *   the rest of the array. The early break optimizes it slightly but worst-case 
     *   is still quadratic.
     * - Space Complexity: O(1) auxiliary stack space.
     */
    public static int bruteForce(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int maxLen = 0, n = arr.length;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum = sum + arr[j];
                if (sum <= k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                } else {
                    // Since numbers are non-negative, adding more will only increase sum
                    break;
                }
            }
        }
        return maxLen;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Classic Sliding Window)
     * ========================================================================
     * Detailed Intuition:
     * This is the standard 2-pointer/sliding window approach. We expand our 
     * window by moving `r` to the right. If the current window's sum exceeds `k`, 
     * we are forced to shrink the window from the left by moving `l` forward 
     * until the sum becomes <= `k` again. Once valid, we update the max length.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2N). The right pointer `r` moves N times. The left 
     *   pointer `l` also moves a maximum of N times across the entire traversal. 
     *   Thus, the inner `while` loop runs at most N times in total.
     * - Space Complexity: O(1) auxiliary stack space.
     */
    public static int classicSlidingWindow(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int l = 0, r = 0, sum = 0, maxLen = 0, n = arr.length;

        while (r < n) {
            sum = sum + arr[r];

            // Shrink the window until it becomes valid
            while (sum > k && l <= r) {
                sum = sum - arr[l];
                l = l + 1;
            }

            if (sum <= k) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
            r = r + 1;
        }
        return maxLen;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Tests all implementations against standard arrays, large target cases, 
     * and edge cases where no valid subarray exists.
     */
    public static void main(String[] args) {
        int[][] testArrays = {
                {2, 5, 1, 7, 10},      // Standard case
                {1, 2, 3, 4, 5},       // Ascending order
                {10, 20, 30},          // No valid subarray (if k is small)
                {1, 1, 1, 1, 1},       // All same elements
                {5, 0, 0, 5, 1}        // Includes zeroes (sum doesn't change, len increases)
        };

        int[] kValues = {14, 5, 5, 3, 6};

        System.out.println("Running Subarray Sum <= K test suite...\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] currentArr = testArrays[i];
            int currentK = kValues[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("Array: ");
            Arrays.stream(currentArr).forEach(num -> System.out.print(num + " "));
            System.out.println("\nk = " + currentK);

            int brute = bruteForce(currentArr, currentK);
            int classicWin = classicSlidingWindow(currentArr, currentK);
            int optimalWin = optimalSlidingWindow(currentArr, currentK);

            System.out.println("Phase 1 (Optimal)       : " + optimalWin);
            System.out.println("Phase 2 (Brute Force)   : " + brute);
            System.out.println("Phase 3 (Classic Window): " + classicWin);

            if (brute == classicWin && classicWin == optimalWin) {
                System.out.println("Status: PASS\n");
            } else {
                System.out.println("Status: FAIL - Mismatch detected\n");
            }
        }
    }
}
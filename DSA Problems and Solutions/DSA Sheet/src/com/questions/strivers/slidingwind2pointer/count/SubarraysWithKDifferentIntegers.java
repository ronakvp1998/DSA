package com.questions.strivers.slidingwind2pointer.count;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION: Subarrays with K Different Integers
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * PROBLEM: 992. Subarrays with K Different Integers (Hard)
 *
 * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an integer array `nums` and an integer `k`, return the number of
 * good subarrays of `nums`. A good array is an array where the number of
 * different integers in that array is exactly `k`.
 * A subarray is a contiguous part of an array.
 *
 * Constraints:
 * - 1 <= nums.length <= 2 * 10^4
 * - 1 <= nums[i], k <= nums.length
 *
 * Example 1:
 * Input: nums = [1,2,1,2,3], k = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers:
 * [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
 *
 * Example 2:
 * Input: nums = [1,2,1,3,4], k = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers:
 * [1,2,1,3], [2,1,3], [1,3,4].
 *
 * --- 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP) ---
 * Phase 1: Optimal Approach - Sliding Window with Algebraic Reduction (AtMost)
 * Phase 2: Brute Force Approach - Nested Loops with HashSet
 * Phase 3: Alternative Approach - Single Pass Sliding Window with 3 Pointers
 * ============================================================================
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class SubarraysWithKDifferentIntegers {

    /**
     * ============================================================================
     * Phase 1: Optimal Approach - The "Best" Stage
     * ============================================================================
     * Detailed Intuition:
     * Finding EXACTLY 'K' distinct elements in a sliding window is extremely difficult
     * because as you shrink the window from the left, you don't inherently know
     * how many valid subarrays you are leaving behind.
     *
     * However, finding AT MOST 'K' distinct elements is a classic, straightforward
     * sliding window problem. If we have a window [left, right] that contains at most
     * K distinct elements, the number of valid subarrays ending at 'right' is simply
     * (right - left + 1).
     *
     * Therefore, we can use a powerful algebraic reduction:
     * Exactly(K) = AtMost(K) - AtMost(K - 1)
     *
     * We calculate the number of subarrays with at most K distinct integers,
     * and subtract the number of subarrays with at most K-1 distinct integers.
     * The difference gives us the subarrays with exactly K distinct integers.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of `nums`. Both `atMost`
     *   calls process each element at most twice (once by right pointer, once by left).
     * - Space Complexity: O(N) auxiliary space. We use an array `freq` of size N + 1
     *   (since 1 <= nums[i] <= nums.length) instead of a HashMap for optimal speed.
     * ============================================================================
     */
    public int subarraysWithKDistinctOptimal(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private int atMost(int[] nums, int k) {
        if (k == 0) return 0;

        // Using an array for frequency counting is significantly faster than HashMap
        // since the constraints state 1 <= nums[i] <= nums.length
        int[] freq = new int[nums.length + 1];
        int left = 0,right=0;
        int count = 0;
        int distinct = 0;

        while (right < nums.length) {
            // If it's a new element, increment distinct counter
            if (freq[nums[right]] == 0) {
                distinct++;
            }
            freq[nums[right]]++;

            // If we exceed k distinct elements, shrink from the left
            while (distinct > k) {
                freq[nums[left]]--;
                if (freq[nums[left]] == 0) {
                    distinct--;
                }
                left++;
            }

            // The number of valid subarrays ending at 'right' is the window size
            count += (right - left + 1);
            right++;
        }

        return count;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - The "Think It" Stage
     * ============================================================================
     * Detailed Intuition:
     * Generate every possible subarray using two nested loops. The outer loop
     * fixes the starting point `i`, and the inner loop expands the ending point `j`.
     * We maintain a HashSet to count the distinct integers in the current subarray
     * `nums[i...j]`. If the set size hits `k`, we increment our result. If it
     * exceeds `k`, we can break early from the inner loop since adding more
     * elements will never decrease the distinct count.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) in the worst case, as we potentially check
     *   all combinations of subarrays. Will cause Time Limit Exceeded (TLE) on LeetCode.
     * - Space Complexity: O(K) heap space for the HashSet.
     * ============================================================================
     */
    public int subarraysWithKDistinctBruteForce(int[] nums, int k) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            Set<Integer> distinctElements = new HashSet<>();
            for (int j = i; j < n; j++) {
                distinctElements.add(nums[j]);

                if (distinctElements.size() == k) {
                    count++;
                } else if (distinctElements.size() > k) {
                    break; // Optimization: distinct count can only grow
                }
            }
        }
        return count;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - Single Pass Sliding Window
     * ============================================================================
     * Detailed Intuition:
     * Instead of doing two separate `atMost` passes, we can do it in a single pass
     * by maintaining THREE pointers: `right`, `left1`, and `left2`.
     * `left1` tracks the start of the window for AtMost(K).
     * `left2` tracks the start of the window for AtMost(K-1).
     * The number of exactly K distinct subarrays ending at `right` is simply
     * `left2 - left1`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) single pass.
     * - Space Complexity: O(N) for frequency maps.
     * ============================================================================
     */
    public int subarraysWithKDistinctAlternative(int[] nums, int k) {
        int[] freq1 = new int[nums.length + 1];
        int[] freq2 = new int[nums.length + 1];
        int left1 = 0, left2 = 0, dist1 = 0, dist2 = 0;
        int totalGoodSubarrays = 0;

        for (int right = 0; right < nums.length; right++) {
            int val = nums[right];

            if (freq1[val] == 0) dist1++;
            freq1[val]++;

            if (freq2[val] == 0) dist2++;
            freq2[val]++;

            // Maintain window for AtMost(K)
            while (dist1 > k) {
                freq1[nums[left1]]--;
                if (freq1[nums[left1]] == 0) dist1--;
                left1++;
            }

            // Maintain window for AtMost(K-1)
            while (dist2 > k - 1) {
                freq2[nums[left2]]--;
                if (freq2[nums[left2]] == 0) dist2--;
                left2++;
            }

            // The difference between the left pointers is exactly the number
            // of valid subarrays ending at 'right'
            totalGoodSubarrays += (left2 - left1);
        }

        return totalGoodSubarrays;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thorough testing of all approaches against standard and edge cases.
     * Uses Java 8 Stream API to format output elegantly.
     * ============================================================================
     */
    public static void main(String[] args) {
        SubarraysWithKDifferentIntegers solution = new SubarraysWithKDifferentIntegers();

        // Define Test Cases
        int[][] testCases = {
                {1, 2, 1, 2, 3},       // Standard Case 1
                {1, 2, 1, 3, 4},       // Standard Case 2
                {1, 1, 1, 1, 1},       // Edge Case: All same elements, k = 1
                {1, 2, 3, 4, 5},       // Edge Case: All distinct, k = 5
                {2, 1, 1, 1, 2}        // Edge Case: Duplicates mixed, k = 2
        };
        int[] kValues = {2, 3, 1, 5, 2};

        System.out.println("🤖 Running Test Suite: Subarrays with K Different Integers\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            int k = kValues[i];

            // Format array string using Java 8 Streams for pretty printing
            String arrayStr = Arrays.toString(nums);

            int expected = solution.subarraysWithKDistinctBruteForce(nums, k);
            int optimalResult = solution.subarraysWithKDistinctOptimal(nums, k);
            int altResult = solution.subarraysWithKDistinctAlternative(nums, k);

            boolean passed = (optimalResult == expected) && (altResult == expected);

            System.out.printf("Test Case %d: %s | K = %d\n", i + 1, arrayStr, k);
            System.out.printf("   Expected (Brute Force): %d\n", expected);
            System.out.printf("   Optimal Approach:       %d\n", optimalResult);
            System.out.printf("   Alternative Approach:   %d\n", altResult);
            System.out.printf("   Status: %s\n", passed ? "✅ PASSED" : "❌ FAILED");
            System.out.println("--------------------------------------------------");
        }
    }
}
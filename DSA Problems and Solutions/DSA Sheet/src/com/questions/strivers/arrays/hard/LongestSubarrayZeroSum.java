package com.questions.strivers.arrays.hard;

/**
 * ============================================================================
 * 🎯 MASTERCLASS: Longest Subarray with Zero Sum
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 * * Formal Problem Statement:
 * Given an array containing both positive and negative integers, we have to find
 * the length of the longest subarray with the sum of all elements equal to zero.
 *
 * Constraints:
 * - 1 <= N <= 10^5 (Typical constraint for O(N) expectations)
 * - -1000 <= array[i] <= 1000
 * - Array can contain zeroes, positive, and negative numbers.
 *
 * Example 1:
 * Input Format: N = 6, array[] = {9, -3, 3, -1, 6, -5}
 * Result: 5
 * Explanation: The following subarrays sum to zero:
 * {-3, 3} , {-1, 6, -5}, {-3, 3, -1, 6, -5}
 * Since we require the length of the longest subarray, our answer is 5.
 *
 * Example 2:
 * Input Format: N = 8, array[] = {6, -2, 2, -8, 1, 7, 4, -10}
 * Result: 8
 * Subarrays with sum 0 : {-2, 2}, {-8, 1, 7}, {-2, 2, -8, 1, 7}, {6, -2, 2, -8, 1, 7, 4, -10}
 * Length of longest subarray = 8
 *
 * Example 3:
 * Input Format: N = 3, array[] = {1, 0, -5}
 * Result: 1
 * Subarray : {0}
 * Length of longest subarray = 1
 *
 * Example 4:
 * Input Format: N = 5, array[] = {1, 3, -5, 6, -2}
 * Result: 0
 * Subarray: There is no subarray that sums to zero
 * * * Conceptual Visualization (Prefix Sum Logic):
 * Array:       [  9,  -3,   3,  -1,   6,  -5 ]
 * Prefix Sums: [  9,   6,   9,   8,  14,   9 ]
 * Indices:     [  0,   1,   2,   3,   4,   5 ]
 * * Notice that the prefix sum '9' occurs at index 0, index 2, and index 5.
 * - Between index 0 and 2 (exclusive of 0): {-3, 3} -> sum is 0, length = 2 - 0 = 2.
 * - Between index 0 and 5 (exclusive of 0): {-3, 3, -1, 6, -5} -> sum is 0, length = 5 - 0 = 5.
 * The distance between the first occurrence of a prefix sum and its current occurrence
 * gives the length of the subarray with sum 0.
 *
 * ============================================================================
 * ### 2.2 Progressive Implementation Roadmap
 * * Phase 1: Best and recommended approach - Prefix Sum with HashMap
 * * Phase 2: Brute Force approach - The "Think it" stage (Nested loops O(N^3))
 * * Phase 3: Alternative Approaches - Optimized Brute Force (O(N^2)) & Sliding Window discussion
 * ============================================================================
 */
import java.util.HashMap;

public class LongestSubarrayZeroSum {

    /**
     * ============================================================================
     * Phase 1: Best and recommended approach - Prefix Sum & Hashing
     * ============================================================================
     * Detailed Intuition:
     * As seen in the conceptual visualization, if a prefix sum at index `i` is
     * equal to a prefix sum at index `j` (where i < j), it mathematically guarantees
     * that the sum of elements between `i+1` and `j` is exactly 0.
     * * We use a HashMap to store the FIRST occurrence of every prefix sum.
     * - If the prefix sum is 0, the subarray from the beginning to the current index sums to 0.
     * - If the prefix sum has been seen before, we calculate the distance between
     * the current index and the previously stored index, updating our max length.
     * - We ONLY store the first occurrence to maximize the length of the subarray.
     *
     * Complexity Analysis:
     * Time (O): O(N) on average, O(N) in worst case (using standard Hash Map operations). We traverse the array exactly once.
     * Space (O): O(N) heap space. In the worst case (all prefix sums are unique),
     * we will store N key-value pairs in the HashMap. O(1) auxiliary stack space.
     */
    public static int phase1OptimalHashing(int[] arr) {
        // Stores {prefixSum, first_occurrence_index}
        HashMap<Integer, Integer> map = new HashMap<>();

        int maxLength = 0;
        int currentSum = 0;

        for (int i = 0; i < arr.length; i++) {
            currentSum += arr[i];

            // Case 1: The subarray from the 0th index to the current index has a sum of 0
            if (currentSum == 0) {
                maxLength = i + 1;
            }
            else {
                // Case 2: We have seen this sum before
                if (map.containsKey(currentSum)) {
                    int previousIndex = map.get(currentSum);
                    maxLength = Math.max(maxLength, i - previousIndex);
                }
                // Case 3: First time seeing this sum, record its index
                else {
                    map.put(currentSum, i);
                }
            }
        }

        return maxLength;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most naive way to solve this is to generate every single possible subarray.
     * For every starting point `i`, we pick an endpoint `j`, and then we run a third
     * loop from `i` to `j` to sum the elements. If the sum is 0, we record its length.
     *
     * Complexity Analysis:
     * Time (O): O(N^3) - Three nested loops: picking start, picking end, summing.
     * Space (O): O(1) heap space, O(1) auxiliary stack space.
     */
    public static int phase2BruteForce(int[] arr) {
        int maxLength = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = 0;
                // Summing the current subarray from i to j
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                }

                if (sum == 0) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }

        return maxLength;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approaches - Optimized Brute Force (O(N^2))
     * ============================================================================
     * Detailed Intuition:
     * In the O(N^3) approach, we recalculate the sum from scratch for every `j`.
     * Instead, we can maintain a running sum as we expand `j`. The sum of the
     * subarray from `i` to `j` is simply the sum from `i` to `j-1` plus `arr[j]`.
     * This eliminates the third inner loop.
     * * (Note on other alternatives: Sliding Window / Two Pointers DOES NOT work here
     * because the array contains both positive and negative numbers. Expanding the
     * window doesn't monotonically increase the sum, and shrinking doesn't monotonically
     * decrease it, making the two-pointer greedy logic fail).
     *
     * Complexity Analysis:
     * Time (O): O(N^2) - Two nested loops.
     * Space (O): O(1) heap space, O(1) auxiliary stack space.
     */
    public static int phase3AlternativeOptimizedBrute(int[] arr) {
        int maxLength = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            // Expand the subarray starting at i
            for (int j = i; j < n; j++) {
                currentSum += arr[j];

                if (currentSum == 0) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }

        return maxLength;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Running Test Suite for Longest Subarray with Zero Sum...");
        System.out.println("-----------------------------------------------------------\n");

        // Format: {input_array, expected_result}
        int[][][] testCases = {
                {{9, -3, 3, -1, 6, -5}, {5}},                    // Example 1
                {{6, -2, 2, -8, 1, 7, 4, -10}, {8}},             // Example 2
                {{1, 0, -5}, {1}},                               // Example 3 (Contains 0)
                {{1, 3, -5, 6, -2}, {0}},                        // Example 4 (No zero sum)
                {{0, 0, 0, 0}, {4}},                             // Edge case: All zeros
                {{-1, 1, -1, 1}, {4}},                           // Alternating to zero
                {{15, -2, 2, -8, 1, 7, 10, 23}, {5}},            // Zero sum in the middle
                {{}, {0}}                                        // Edge case: Empty array
        };

        boolean allPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i][0];
            int expected = testCases[i][1][0];

            int res1 = phase1OptimalHashing(arr);
            int res2 = phase2BruteForce(arr);
            int res3 = phase3AlternativeOptimizedBrute(arr);

            boolean pass = (res1 == expected) && (res2 == expected) && (res3 == expected);

            System.out.printf("Test %d: Input Array Size: %d\n", i + 1, arr.length);
            System.out.printf("  Expected              : %d\n", expected);
            System.out.printf("  Optimal (Hashing)     : %d\n", res1);
            System.out.printf("  Brute Force (O(N^3))  : %d\n", res2);
            System.out.printf("  Opt. Brute (O(N^2))   : %d\n", res3);
            System.out.printf("  Status: %s\n\n", pass ? "✅ PASS" : "❌ FAIL");

            if (!pass) {
                allPassed = false;
            }
        }

        System.out.println("-----------------------------------------------------------");
        System.out.println(allPassed ? "🎉 ALL TESTS PASSED!" : "⚠️ SOME TESTS FAILED.");
    }
}
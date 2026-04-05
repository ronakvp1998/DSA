package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: PREFIX SUM (RANGE SUM QUERY - IMMUTABLE)
 * ============================================================================
 * * 303. Range Sum Query - Immutable
 * Solved | Easy | Topics | Companies
 * * Hint:
 * Given an integer array nums, handle multiple queries of the following type:
 * Calculate the sum of the elements of nums between indices left and right
 * inclusive where left <= right.
 * * Implement the NumArray class (adapted here as functional methods):
 * - Initializes the object with the integer array nums.
 * - Returns the sum of the elements of nums between indices left and right inclusive.
 * * Example 1:
 * Input:
 * nums = [-2, 0, 3, -5, 2, -1]
 * queries = [[0, 2], [2, 5], [0, 5]]
 * Output: [1, -1, -3]
 * Explanation:
 * sumRange(0, 2) -> (-2) + 0 + 3 = 1
 * sumRange(2, 5) -> 3 + (-5) + 2 + (-1) = -1
 * sumRange(0, 5) -> (-2) + 0 + 3 + (-5) + 2 + (-1) = -3
 * * Constraints:
 * - 1 <= nums.length <= 10^4
 * - -10^5 <= nums[i] <= 10^5
 * - 0 <= left <= right < nums.length
 * - At most 10^4 calls will be made to sumRange.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Prefix Sum Array)
 * ============================================================================
 * The core idea of Prefix Sum is to precompute cumulative sums so that any
 * contiguous subarray sum can be found in O(1) time using simple subtraction.
 * * To avoid OutOfBounds errors when querying from index 0, we make the Prefix
 * array 1-indexed (size N + 1), where prefix[0] = 0.
 * * nums:      [ -2,   0,   3,  -5,   2,  -1 ]
 * index:        0    1    2    3    4    5
 * * prefix:  [  0,  -2,  -2,   1,  -4,  -2,  -3 ]
 * index:      0    1    2    3    4    5    6
 * * Formula: sumRange(left, right) = prefix[right + 1] - prefix[left]
 * * Example: sumRange(2, 5)
 * = prefix[5 + 1] - prefix[2]
 * = prefix[6] - prefix[2]
 * = (-3) - (-2)
 * = -1
 * ============================================================================
 */

import java.util.Arrays;

public class PrefixSumMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * For every query, we iterate through the array from the 'left' index to
     * the 'right' index, accumulating the sum on the fly.
     * * Detailed Intuition:
     * This is the most direct translation of the problem statement. However, if
     * we have Q queries and the array size is N, we are repeatedly summing the
     * exact same overlapping elements over and over again.
     * * Complexity Analysis:
     * - Time Complexity: O(N) per query.
     * If there are Q queries, total time is O(Q * N). For LeetCode constraints
     * (10^4 queries * 10^4 elements), this is 10^8 operations, which is too slow.
     * - Space Complexity: O(1).
     * We only use a single primitive integer for the running sum. Heap space is
     * O(1), and auxiliary stack space is O(1).
     */
    public int rangeSumBruteForce(int[] nums, int left, int right) {
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += nums[i];
        }
        return sum;
    }

    /**
     * ========================================================================
     * PHASE 2: PREFIX SUM OPTIMAL APPROACH (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * We divide the solution into two parts: Preprocessing (Building) and Querying.
     * Step 1: Create a prefix array of size N + 1. Iterate through `nums` once,
     * adding the current element to the previous cumulative sum.
     * Step 2: To answer a query for range [left, right], simply subtract
     * prefix[left] from prefix[right + 1].
     * * Detailed Intuition:
     * (Total sum from 0 to right) MINUS (Total sum from 0 to left-1) exactly
     * isolates the sum of the subarray [left...right]. By shifting our prefix
     * array by 1 index (prefix[0] = 0), we gracefully handle cases where left = 0
     * without needing messy "if (left == 0)" conditional checks. We trade a tiny
     * bit of upfront space/time to make all future queries instantaneous.
     * * Complexity Analysis:
     * - Time Complexity: O(N) for Preprocessing, O(1) per Query.
     * For Q queries, total time drops dramatically from O(Q * N) down to O(N + Q).
     * - Space Complexity: O(N).
     * We allocate an array of size N + 1 in Heap Space. Auxiliary stack space
     * is O(1) since we are iterating, not recurring.
     */

    // Step 1: Preprocessing Method
    public int[] buildPrefixArray(int[] nums) {
        if (nums == null || nums.length == 0) return new int[1];

        int[] prefix = new int[nums.length + 1];
        prefix[0] = 0; // Explicitly setting base state (default in Java, but good practice)

        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        return prefix;
    }

    // Step 2: O(1) Query Method
    public int queryPrefixSum(int[] prefix, int left, int right) {
        // prefix[right + 1] holds the sum of nums[0...right]
        // prefix[left] holds the sum of nums[0...left-1]
        return prefix[right + 1] - prefix[left];
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        PrefixSumMasterclass solution = new PrefixSumMasterclass();

        // Define Test Arrays
        int[] standardArray = {-2, 0, 3, -5, 2, -1};
        int[] allPositives = {1, 2, 3, 4, 5};
        int[] singleElement = {42};
        int[] zeroArray = {0, 0, 0, 0};

        // Define Queries [left, right]
        int[][] queries1 = {{0, 2}, {2, 5}, {0, 5}}; // For standard array
        int[][] queries2 = {{1, 3}, {0, 4}};         // For positives
        int[][] queries3 = {{0, 0}};                 // For single element
        int[][] queries4 = {{1, 2}, {0, 3}};         // For zero array

        System.out.println("==================================================");
        System.out.println("Executing Prefix Sum Testing Suite");
        System.out.println("==================================================\n");

        executeTest(solution, "Test Case 1 (Standard)", standardArray, queries1);
        executeTest(solution, "Test Case 2 (All Positives)", allPositives, queries2);
        executeTest(solution, "Test Case 3 (Single Element)", singleElement, queries3);
        executeTest(solution, "Test Case 4 (Zero Values)", zeroArray, queries4);
    }

    // Helper method to keep main clean
    private static void executeTest(PrefixSumMasterclass solution, String testName, int[] nums, int[][] queries) {
        System.out.println(testName + ": nums = " + Arrays.toString(nums));

        // Pre-compute prefix array once
        int[] prefix = solution.buildPrefixArray(nums);
        System.out.println("  [Generated Prefix Array] : " + Arrays.toString(prefix));

        for (int[] query : queries) {
            int left = query[0];
            int right = query[1];

            // Brute Force Execution
            long start1 = System.nanoTime();
            int res1 = solution.rangeSumBruteForce(nums, left, right);
            long end1 = System.nanoTime();

            // Prefix Sum Execution
            long start2 = System.nanoTime();
            int res2 = solution.queryPrefixSum(prefix, left, right);
            long end2 = System.nanoTime();

            System.out.println("  Query [" + left + ", " + right + "]");
            System.out.println("    -> Brute Force Output: " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("    -> Prefix Sum  Output: " + res2 + " | Time: " + (end2 - start2) + " ns");

            if (res1 != res2) {
                System.out.println("    -> VERIFICATION FAILED!");
            }
        }
        System.out.println("--------------------------------------------------");
    }
}
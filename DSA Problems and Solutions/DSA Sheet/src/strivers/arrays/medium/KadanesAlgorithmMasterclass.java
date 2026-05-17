package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: MAXIMUM SUBARRAY (KADANE'S ALGORITHM EXCLUSIVE)
 * ============================================================================
 * * 53. Maximum Subarray
 * Solved | Medium | Topics | Companies
 * * Hint:
 * Given an integer array nums, find the subarray with the largest sum,
 * and return its sum.
 * * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
 * * Example 2:
 * Input: nums = [1]
 * Output: 1
 * Explanation: The subarray [1] has the largest sum 1.
 * * Example 3:
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 * * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Kadane's Algorithm Logic)
 * ============================================================================
 * * Note: Per user request, this class focuses EXCLUSIVELY on Kadane's Algorithm.
 * * Kadane's algorithm is a space-optimized Dynamic Programming approach.
 * The core DP transition is:
 * dp[i] = max(nums[i], dp[i-1] + nums[i])
 * * Because we only ever need the immediately preceding state (dp[i-1]), we can
 * discard the O(N) DP array and just use a single variable (`currentMax`).
 * * Visualizing the state transitions for nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]:
 * * i | nums[i] | currentMax = max(nums[i], currentMax + nums[i]) | globalMax
 * --|---------|-------------------------------------------------|----------
 * 0 |   -2    |  -2 (Init)                                      |  -2 (Init)
 * 1 |    1    |  max(1, -2 + 1) = 1  (Start fresh!)             |   1
 * 2 |   -3    |  max(-3, 1 - 3) = -2 (Keep going)               |   1
 * 3 |    4    |  max(4, -2 + 4) = 4  (Start fresh!)             |   4
 * 4 |   -1    |  max(-1, 4 - 1) = 3  (Keep going)               |   4
 * 5 |    2    |  max(2, 3 + 2)  = 5  (Keep going)               |   5
 * 6 |    1    |  max(1, 5 + 1)  = 6  (Keep going)               |   6
 * 7 |   -5    |  max(-5, 6 - 5) = 1  (Keep going)               |   6
 * 8 |    4    |  max(4, 1 + 4)  = 5  (Keep going)               |   6 -> RESULT
 * * ============================================================================
 */

import java.util.Arrays;

public class KadanesAlgorithmMasterclass {

    /**
     * ========================================================================
     * KADANE'S ALGORITHM (Optimal Dynamic Programming with Space Optimization)
     * ========================================================================
     * * Approach:
     * We iterate through the array maintaining two variables:
     * 1. `currentMax`: The maximum contiguous sum ending exactly at the current index.
     * 2. `globalMax`: The maximum sum seen across all evaluated sub-arrays so far.
     * * At every step, we make a local decision: do we extend the existing
     * subarray by adding `nums[i]` to `currentMax`, or is the existing sum so
     * detrimental (negative) that we are better off starting a brand new
     * sequence directly at `nums[i]`?
     * * Detailed Intuition:
     * If `currentMax` falls below zero, it acts as a net negative drag on
     * whatever comes next. By using `Math.max(nums[i], currentMax + nums[i])`,
     * the algorithm automatically "resets" the contiguous subarray the moment
     * the past accumulated sequence becomes worse than just taking the current
     * number by itself.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We process every element in the array exactly once in a single pass.
     * - Space Complexity: O(1)
     * No auxiliary data structures are used. We only maintain two integer
     * variables. Heap space is O(1) and auxiliary stack space is O(1).
     * * @param nums The integer array to evaluate.
     * @return The maximum contiguous subarray sum.
     */
    public int maxSubArray(int[] nums) {
        // Base case: If array is empty, technically sum is 0, but based on
        // LeetCode constraints (length >= 1), this won't hit.
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Initialize both trackers to the first element.
        // We do NOT initialize to 0, because if the array contains entirely
        // negative numbers, 0 would incorrectly be the maximum sum returned.
        int currentMax = nums[0];
        int globalMax = nums[0];

        // Start evaluating from the second element (index 1)
        for (int i = 1; i < nums.length; i++) {

            // The core Kadane's decision point: Start fresh, or extend?
            currentMax = Math.max(nums[i], currentMax + nums[i]);

            // Update the absolute best sum found so far
            globalMax = Math.max(globalMax, currentMax);
        }

        return globalMax;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        KadanesAlgorithmMasterclass solution = new KadanesAlgorithmMasterclass();

        // Comprehensive Test Cases
        int[][] testCases = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4}, // Standard mix of pos/neg (Expected: 6)
                {1},                             // Single element (Expected: 1)
                {5, 4, -1, 7, 8},                // Mostly positive (Expected: 23)
                {-8, -3, -6, -2, -5},            // Edge Case: All negative (Expected: -2)
                {0, 0, 0, 0},                    // Edge Case: All zeroes (Expected: 0)
                {-10, 2, 3, -2, 0, 5, -15}       // Mixed with zero reset (Expected: 8)
        };

        System.out.println("==================================================");
        System.out.println("Executing Kadane's Algorithm Testing Suite");
        System.out.println("==================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums));

            long start = System.nanoTime();
            int result = solution.maxSubArray(nums);
            long end = System.nanoTime();

            System.out.println("  [Output] : " + result);
            System.out.println("  [Time]   : " + (end - start) + " ns");
            System.out.println("--------------------------------------------------");
        }
    }
}
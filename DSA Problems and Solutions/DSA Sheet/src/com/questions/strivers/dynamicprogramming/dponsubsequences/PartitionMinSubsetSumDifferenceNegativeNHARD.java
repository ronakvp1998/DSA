package com.questions.strivers.dynamicprogramming.dponsubsequences;

/**
 * ==================================================================================================
 * PROBLEM: 2035. Partition Array Into Two Arrays to Minimize Sum Difference (LeetCode Hard) (negative n)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an integer array nums of 2 * n integers. You need to partition nums into two
 * arrays of length n to minimize the absolute difference of the sums of the arrays.
 * Return the minimum possible absolute difference.
 *
 * CONSTRAINTS:
 * 1 <= n <= 15
 * nums.length == 2 * n  (Max 30 elements)
 * -10^7 <= nums[i] <= 10^7 (Large, negative capable values)
 *
 * ==================================================================================================
 * CONCEPTUAL VISUALIZATION & THE "DP TRAP"
 * ==================================================================================================
 * 1. The Standard DP Approach (Why it fails):
 * Usually, we use a 2D or 3D DP array: dp[index][elementsPicked][currentSum].
 * However, look at the constraints:
 * - elementsPicked = up to 15
 * - currentSum = 15 * 10^7 = 150,000,000.
 * A DP array of size [30][15][150,000,000] is physically impossible to store in memory (MLE).
 *
 * 2. The Senior Solution: "Meet in the Middle"
 * Since N is small (max 30), we can split the array directly in half (15 elements each).
 * Left Half  -> Generate all subset sums.
 * Right Half -> Generate all subset sums.
 * If we pick 'k' elements from the left, we MUST pick exactly 'n-k' elements from the right.
 * We sort the right half, and use Binary Search to find the optimal match.
 *
 * RECURSION TREE (For generating subset sums of a half):
 *
 *                    f(idx: 0, count: 0, sum: 0)
 *                  /                           \
 *      PICK (count+1, sum+arr[0])      NOT PICK (count, sum)
 *          /            \                  /             \
 *       f(...)        f(...)            f(...)         f(...)
 *
 * ==================================================================================================
 */

import java.util.*;

public class PartitionMinSubsetSumDifferenceNegativeNHARD {

    // Global variables for testing purposes
    private static int bruteForceMinDiff = Integer.MAX_VALUE;

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION
     * ==============================================================================================
     * Intuition: Explore every single combination of picking 'n' elements out of '2n'.
     * At each step, we either include nums[i] in subset 1, or we don't.
     * * Complexity:
     * - Time: O(2^(2N)) - Roughly 2^30 operations. Will result in Time Limit Exceeded (TLE).
     * - Space: O(2N) - Auxiliary stack space for recursion.
     */
    public static void phase1BruteForce(int[] nums, int idx, int count, int currentSum, int totalSum) {
        int n = nums.length / 2;

        // Base case: we have picked exactly n elements
        if (count == n) {
            int otherSum = totalSum - currentSum;
            bruteForceMinDiff = Math.min(bruteForceMinDiff, Math.abs(currentSum - otherSum));
            return;
        }

        // Base case: out of bounds
        if (idx == nums.length) return;

        // Path 1: Pick the current element
        phase1BruteForce(nums, idx + 1, count + 1, currentSum + nums[idx], totalSum);

        // Path 2: Not Pick
        phase1BruteForce(nums, idx + 1, count, currentSum, totalSum);
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The Restricted DP)
     * ==============================================================================================
     * Intuition: Because 'currentSum' can be negative and huge, an array is impossible.
     * We must use a HashMap with a string key "idx_count_sum".
     * * Complexity:
     * - Time: O(N * N * SumRange) -> Still results in TLE/MLE because the state space is too vast.
     * - Space: O(Unique States) -> Will exceed memory limits.
     * * NOTE: This is implemented conceptually to show the transition, but is NOT the optimal path.
     */
    public static int phase2Memoization(int[] nums, int idx, int count, int currentSum, int totalSum, Map<String, Integer> memo) {
        int n = nums.length / 2;

        if (count == n) return Math.abs(currentSum - (totalSum - currentSum));
        if (idx == nums.length) return Integer.MAX_VALUE;

        String key = idx + "_" + count + "_" + currentSum;
        if (memo.containsKey(key)) return memo.get(key);

        int pick = phase2Memoization(nums, idx + 1, count + 1, currentSum + nums[idx], totalSum, memo);
        int notPick = phase2Memoization(nums, idx + 1, count, currentSum, totalSum, memo);

        int res = Math.min(pick, notPick);
        memo.put(key, res);
        return res;
    }

    /**
     * ==============================================================================================
     * PHASE 3 & 4: TABULATION & SPACE OPTIMIZATION (SKIPPED)
     * ==============================================================================================
     * Technical Analysis:
     * In a senior interview, you MUST verbally explain why you stop DP here.
     * Tabulation requires initializing a continuous block of memory for the state space.
     * A matrix of size [30][15][300,000,000] requires hundreds of Gigabytes of RAM.
     * Therefore, bottom-up DP is fundamentally impossible for this problem's constraints.
     */

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE / OPTIMAL -> "MEET IN THE MIDDLE" + BINARY SEARCH
     * ==============================================================================================
     * Intuition:
     * 1. Split the array into Left (0 to n-1) and Right (n to 2n-1).
     * 2. Generate all subset sums for both halves. Store them based on the NUMBER of elements picked.
     * 3. Sort the Right half arrays to enable Binary Search.
     * 4. For every subset sum 'A' in the Left half (which has 'k' elements), we need exactly
     * 'n-k' elements from the Right half. Let's call that sum 'B'.
     * 5. We want (A + B) to be as close to totalSum / 2 as possible. Use Binary Search to find B.
     * * Complexity:
     * - Time: O(2^N * log(2^N)) -> Where N is 15. 2^15 is 32,768. Very fast!
     * - Space: O(2^N) -> To store the subset sums. Easily fits in memory.
     */
    @SuppressWarnings("unchecked")
    public static int phase5OptimalMeetInTheMiddle(int[] nums) {
        int n = nums.length / 2;
        int totalSum = 0;
        for (int num : nums) totalSum += num;

        // Array of Lists to store subset sums based on count of elements picked
        List<Integer>[] left = new ArrayList[n + 1];
        List<Integer>[] right = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            left[i] = new ArrayList<>();
            right[i] = new ArrayList<>();
        }

        // Generate subsets for both halves
        generateSubsets(nums, 0, n, 0, 0, left);
        generateSubsets(nums, n, 2 * n, 0, 0, right);

        // Sort the right half to prepare for Binary Search
        for (int i = 0; i <= n; i++) {
            Collections.sort(right[i]);
        }

        int minDiff = Integer.MAX_VALUE;
        int target = totalSum / 2; // Ideal sum for one partition

        // Iterate through all possible number of elements 'k' picked from the left
        for (int k = 0; k <= n; k++) {
            List<Integer> leftVals = left[k];
            List<Integer> rightVals = right[n - k]; // Must pick remaining from right

            for (int a : leftVals) {
                int expected = target - a; // The ideal value we want from the right

                // Binary search for closest to expected
                int idx = Collections.binarySearch(rightVals, expected);

                if (idx >= 0) {
                    // Exact match found! Diff might be 0 or 1 depending on odd/even totalSum
                    return Math.abs(totalSum - 2 * (a + expected));
                } else {
                    // If not found, binarySearch returns (-(insertion point) - 1)
                    idx = -(idx + 1);

                    // Check element at insertion point (greater than expected)
                    if (idx < rightVals.size()) {
                        int b = rightVals.get(idx);
                        minDiff = Math.min(minDiff, Math.abs(totalSum - 2 * (a + b)));
                    }
                    // Check element right before insertion point (less than expected)
                    if (idx > 0) {
                        int b = rightVals.get(idx - 1);
                        minDiff = Math.min(minDiff, Math.abs(totalSum - 2 * (a + b)));
                    }
                }
            }
        }
        return minDiff;
    }

    // Helper method to generate all subsets for a given half
    private static void generateSubsets(int[] nums, int start, int end, int count, int currentSum, List<Integer>[] res) {
        if (start == end) {
            res[count].add(currentSum);
            return;
        }
        // Pick
        generateSubsets(nums, start + 1, end, count + 1, currentSum + nums[start], res);
        // Not Pick
        generateSubsets(nums, start + 1, end, count, currentSum, res);
    }

    /**
     * ==============================================================================================
     * TESTING SUITE
     * ==============================================================================================
     */
    public static void main(String[] args) {
        int[] test1 = {3, 9, 7, 3};
        int[] test2 = {-36, 36};
        int[] test3 = {2, -1, 0, 4, -2, -9};

        System.out.println("=== Phase 5: Optimal Meet in the Middle ===");
        System.out.println("Test 1 (Expected 2):  " + phase5OptimalMeetInTheMiddle(test1));
        System.out.println("Test 2 (Expected 72): " + phase5OptimalMeetInTheMiddle(test2));
        System.out.println("Test 3 (Expected 0):  " + phase5OptimalMeetInTheMiddle(test3));

        System.out.println("\n=== Phase 1: Brute Force (For conceptual validation on small inputs) ===");
        bruteForceMinDiff = Integer.MAX_VALUE;
        int total1 = Arrays.stream(test1).sum();
        phase1BruteForce(test1, 0, 0, 0, total1);
        System.out.println("Test 1 Brute Force: " + bruteForceMinDiff);
    }
}
package com.questions.strivers.dynamicprogramming.dponlis;

/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: NUMBER OF LONGEST INCREASING SUBSEQUENCE (LeetCode 673)
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * Given an integer array 'nums', return the number of longest increasing subsequences.
 * Notice that the sequence has to be strictly increasing.
 * * EXAMPLES:
 * Example 1:
 * Input: nums = [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].
 * * Example 2:
 * Input: nums = [2,2,2,2,2]
 * Output: 5
 * Explanation: The length of the LIS is 1, and there are 5 increasing subsequences of length 1.
 * * CONSTRAINTS:
 * - 1 <= nums.length <= 2000
 * - -10^6 <= nums[i] <= 10^6
 * - The answer is guaranteed to fit inside a 32-bit integer.
 * * ==================================================================================================
 * 💡 THE CORE INTUITION (The "Aha!" Moment)
 * ==================================================================================================
 * To find the *number* of Longest Increasing Subsequences (LIS), we must track TWO states for
 * every element in the array:
 * 1. `len[i]`: The length of the LIS ending precisely at index `i`.
 * 2. `count[i]`: The total number of valid LIS combinations that end precisely at index `i`.
 * * When checking a previous element `nums[j]` (where j < i) that can strictly increase to `nums[i]`:
 * - If `len[j] + 1 > len[i]`: We discovered a completely NEW longest length. We update `len[i]`
 * to this new length, and the number of ways to make it is exactly the number of ways we
 * made the sequence at `j` (`count[i] = count[j]`).
 * - If `len[j] + 1 == len[i]`: We found ANOTHER way to make the CURRENT longest length!
 * We add the combinations from `j` to our current count (`count[i] += count[j]`).
 * *
 * * ==================================================================================================
 * 🌳 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * RECURSION TREE (Tracking paths ending at index i. e.g., nums = [1, 3, 5, 4, 7])
 * Let's trace paths ending at index 4 (Value: 7):
 * * f(idx=4, val=7)
 * /                 \
 * (From idx=2, val=5)   (From idx=3, val=4)
 * /                     \
 * f(1, val=3)               f(1, val=3)
 * /                         \
 * f(0, val=1)                   f(0, val=1)
 * * Both branches yield a valid path of length 4: [1,3,5,7] and [1,3,4,7].
 * Total count ending at 7 = 1 + 1 = 2.
 * * --------------------------------------------------------------------------------------------------
 * 📦 DP TABLE STATE TRANSITIONS (Tabulation)
 * nums = [1, 3, 5, 4, 7]
 * * Index (i) | Value | len[i] (Max LIS Length) | count[i] (Ways to make that length)
 * -----------------------------------------------------------------------------------
 * 0         |   1   |           1             |           1
 * 1         |   3   |           2             |           1 (comes from idx 0)
 * 2         |   5   |           3             |           1 (comes from idx 1)
 * 3         |   4   |           3             |           1 (comes from idx 1)
 * 4         |   7   |           4             |           2 (comes from idx 2 and idx 3!)
 * * Global Max Length = 4. Sum of counts where len[i] == 4 is 2.
 * ==================================================================================================
 */

import java.util.Arrays;

public class NumberOfLISMasterclass {

    public static void main(String[] args) {
        NumberOfLISMasterclass solver = new NumberOfLISMasterclass();

        int[] nums1 = {1, 3, 5, 4, 7};
        int[] nums2 = {2, 2, 2, 2, 2};

        System.out.println("Input Array 1: " + Arrays.toString(nums1));
        System.out.println("---------------------------------------------------------");
        System.out.println("1. Brute Force Recursion : " + solver.phase1_bruteForce(nums1));
        System.out.println("2. Top-Down Memoization  : " + solver.phase2_memoization(nums1));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(nums1));

        System.out.println("\nInput Array 2: " + Arrays.toString(nums2));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(nums2));
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Intuition:
     * We define a recursive function `solve(i)` that returns an integer array containing two values:
     * index 0: The maximum LIS length ending exactly at index `i`.
     * index 1: The count of LIS of that maximum length ending exactly at index `i`.
     * To find the final answer, we calculate this for every index, find the global max length,
     * and sum up the counts of all indices that achieved that global max length.
     * * Complexity Analysis:
     * - Time Complexity: O(2^N). Without caching, we explore every increasing subsequence repeatedly.
     * - Space Complexity: O(N) auxiliary stack space for the recursion tree depth.
     */
    public int phase1_bruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxLen = 0;
        int totalCount = 0;

        for (int i = 0; i < nums.length; i++) {
            int[] res = solveRecursive(i, nums);
            if (res[0] > maxLen) {
                maxLen = res[0];
                totalCount = res[1]; // Found a new max length, reset count
            } else if (res[0] == maxLen) {
                totalCount += res[1]; // Found same max length, accumulate count
            }
        }

        return totalCount;
    }

    // Returns: int[] { maxLengthEndingAtI, countOfMaxLength }
    private int[] solveRecursive(int i, int[] nums) {
        int maxLen = 1;
        int count = 1;

        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                int[] res = solveRecursive(j, nums);
                if (res[0] + 1 > maxLen) {
                    maxLen = res[0] + 1;
                    count = res[1];
                } else if (res[0] + 1 == maxLen) {
                    count += res[1];
                }
            }
        }

        return new int[]{maxLen, count};
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Intuition:
     * The brute force approach calculates `solveRecursive(j)` redundantly multiple times.
     * We can cache the `int[] {length, count}` result for each index in a `memo` array.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). We compute the result for each of the N indices exactly once.
     * Computing it takes O(N) time to iterate through previous indices.
     * - Space Complexity: O(N) for the memoization table (heap) + O(N) auxiliary stack space.
     */
    public int phase2_memoization(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        // memo[i] will store {maxLength, count} for index i
        int[][] memo = new int[n][2];
        for (int i = 0; i < n; i++) {
            memo[i][0] = -1; // -1 denotes uncomputed
        }

        int maxLen = 0;
        int totalCount = 0;

        for (int i = 0; i < n; i++) {
            int[] res = solveMemo(i, nums, memo);
            if (res[0] > maxLen) {
                maxLen = res[0];
                totalCount = res[1];
            } else if (res[0] == maxLen) {
                totalCount += res[1];
            }
        }

        return totalCount;
    }

    private int[] solveMemo(int i, int[] nums, int[][] memo) {
        if (memo[i][0] != -1) {
            return memo[i];
        }

        int maxLen = 1;
        int count = 1;

        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                int[] res = solveMemo(j, nums, memo);
                if (res[0] + 1 > maxLen) {
                    maxLen = res[0] + 1;
                    count = res[1];
                } else if (res[0] + 1 == maxLen) {
                    count += res[1];
                }
            }
        }

        memo[i][0] = maxLen;
        memo[i][1] = count;
        return memo[i];
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage) - ✨ INDUSTRY STANDARD
     * ==============================================================================================
     * Intuition:
     * We map the top-down approach into iterative 1D arrays: `len[]` and `count[]`.
     * This avoids recursion stack overhead entirely and gives us an elegant, robust solution.
     * We iterate left to right, building the optimal subproblems as we go.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). Two nested loops iterate over the array elements.
     * - Space Complexity: O(N). Two 1D arrays of size N allocated on the heap.
     */
    public int phase3_tabulation(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] len = new int[n];
        int[] count = new int[n];

        Arrays.fill(len, 1);   // By default, LIS is 1 (the element itself)
        Arrays.fill(count, 1); // By default, there is 1 way to make a sequence of length 1

        int globalMaxLen = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {

                    // Discovered a strictly longer subsequence
                    if (len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1;
                        count[i] = count[j]; // Inherit the count
                    }
                    // Discovered another way to make the current max subsequence
                    else if (len[j] + 1 == len[i]) {
                        count[i] += count[j]; // Accumulate the counts
                    }
                }
            }
            globalMaxLen = Math.max(globalMaxLen, len[i]);
        }

        // Final pass to sum up counts of all subsequences matching globalMaxLen
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (len[i] == globalMaxLen) {
                result += count[i];
            }
        }

        return result;
    }

    /**
     * ==============================================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" Stage)
     * ==============================================================================================
     * Intuition:
     * Unlike typical Grid DP (where row `i` only depends on row `i-1`), LIS variants require
     * the current element to inspect ALL previous elements `0` to `i-1`.
     * Because of this lookback requirement, compressing the state beyond O(N) 1D arrays is
     * not feasible. Phase 3 represents the optimal spatial footprint for the standard DP approach.
     */
    public int phase4_spaceOptimization(int[] nums) {
        return phase3_tabulation(nums);
    }

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE APPROACHES (Fenwick Tree / Segment Tree)
     * ==============================================================================================
     * While the O(N^2) Tabulation passes the constraints for N <= 2000, what if N <= 10^5?
     * * We can achieve O(N log N) time complexity using a Segment Tree or Binary Indexed Tree (BIT).
     * 1. Coordinate Compression: Map the values of `nums` to continuous ranks to fit them
     * nicely into a Segment Tree index.
     * 2. Segment Tree: The segment tree nodes will store `{max_length, total_count}` for a given
     * range of values.
     * 3. Process: As we iterate through `nums`, we query the Segment Tree for the maximum length
     * and its count for all values strictly less than `nums[i]`. We then update the tree with
     * `nums[i]`'s new length and count.
     * * Trade-off: Much faster for massive inputs, but extremely heavy to implement in an
     * interview compared to the elegant O(N^2) DP, which is usually exactly what interviewers expect.
     */
}
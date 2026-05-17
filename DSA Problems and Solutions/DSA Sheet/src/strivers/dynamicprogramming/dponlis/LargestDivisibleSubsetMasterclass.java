package com.questions.strivers.dynamicprogramming.dponlis;
/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: LARGEST DIVISIBLE SUBSET (LeetCode 368)
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * Given a set of distinct positive integers 'nums', return the largest subset 'answer' such that
 * every pair (answer[i], answer[j]) of elements in this subset satisfies:
 * - answer[i] % answer[j] == 0, or
 * - answer[j] % answer[i] == 0
 * If there are multiple solutions, return any of them.
 * * EXAMPLES:
 * Input: nums = [1,2,3]       => Output: [1,2] or [1,3]
 * Input: nums = [1,2,4,8]     => Output: [1,2,4,8]
 * * CONSTRAINTS:
 * - 1 <= nums.length <= 1000
 * - 1 <= nums[i] <= 2 * 10^9
 * - All integers in nums are unique.
 * * ==================================================================================================
 * 💡 THE CORE INTUITION (The "Aha!" Moment)
 * ==================================================================================================
 * If we sort the array in ascending order, we unlock the property of Transitivity.
 * Suppose our sorted array is [a, b, c].
 * If (b % a == 0) and (c % b == 0), it is mathematically guaranteed that (c % a == 0).
 * * Therefore, if we maintain a sorted subset, when considering adding a new element 'c',
 * we ONLY need to check if 'c' is divisible by the LARGEST element currently in our subset ('b').
 * This transforms the problem into a variation of the Longest Increasing Subsequence (LIS)!
 * * ==================================================================================================
 * 🌳 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * RECURSION TREE (For nums = [1, 2, 4] sorted)
 * State: f(currentIndex, previousIndex)
 *
 * *                             f(idx=0, prev=-1)
 *                            /                   \
 *                  (Pick 1) /                     \ (Skip 1)
 *                          /                       \
 *               f(idx=1, prev=0)                 f(idx=1, prev=-1)
 *                /             \                  /              \
 *          (Pick 2)            (Skip 2)       (Pick 2)           (Skip 2)
 *              /                    \              /                  \
 *      f(2, prev=1)           f(2, prev=0)   f(2, prev=1)         f(2, prev=-1)
 *      | (Pick 4)             | (Pick 4)     | (Pick 4)           | (Pick 4)
 *      Returns [1,2,4]        Returns [1,4]  Returns [2,4]        Returns [4]
 * *
 * * --------------------------------------------------------------------------------------------------
 * 📦 DP TABLE STATE TRANSITIONS (Tabulation & Path Reconstruction)
 * nums = [1, 2, 4, 8]
 * * Index (i) | Value | Divisible By (Valid Parents) | dp[i] (Length) | hash[i] (Parent Index)
 * -----------------------------------------------------------------------------------------
 * 0      |   1   | None                         |       1        |       0
 * 1      |   2   | nums[0]=1                    |   dp[0]+1 = 2  |       0
 * 2      |   4   | nums[0]=1, nums[1]=2         |   dp[1]+1 = 3  |       1
 * 3      |   8   | nums[0]=1, nums[1]=2, ...    |   dp[2]+1 = 4  |       2
 * * By tracing the `hash` array backward from the max `dp` value, we build the final answer!
 * *
 * ==================================================================================================
 */

import java.util.*;

public class LargestDivisibleSubsetMasterclass {

    public static void main(String[] args) {
        LargestDivisibleSubsetMasterclass solver = new LargestDivisibleSubsetMasterclass();

        int[] nums = {1, 16, 7, 8, 4};
        System.out.println("Input Array: " + Arrays.toString(nums));
        System.out.println("---------------------------------------------------------");

        System.out.println("1. Brute Force Recursion : " + solver.phase1_bruteForce(nums));
        System.out.println("2. Top-Down Memoization  : " + solver.phase2_memoization(nums));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(nums));
        System.out.println("4. Space Optimization    : " + solver.phase4_spaceOptimization(nums));
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Intuition:
     * We sort the array first. At every element, we have two choices:
     * 1. Skip it.
     * 2. Pick it (only if it's divisible by the previous element we picked).
     * We return the actual List of integers to build the subsets recursively.
     * * Complexity Analysis:
     * - Time Complexity: O(2^N). For every element, we branch into 2 paths.
     * - Space Complexity: O(N) auxiliary stack space + O(N) heap space to construct lists.
     */
    public List<Integer> phase1_bruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return new ArrayList<>();

        // Step 1: Sort the array to unlock transitivity
        Arrays.sort(nums);
        return solveRecursive(0, -1, nums);
    }

    private List<Integer> solveRecursive(int idx, int prevIdx, int[] nums) {
        // Base case: Reached the end of the array
        if (idx == nums.length) {
            return new ArrayList<>();
        }

        // Choice 1: Do not pick the current element
        List<Integer> skip = solveRecursive(idx + 1, prevIdx, nums);

        // Choice 2: Pick the current element (if valid)
        List<Integer> pick = new ArrayList<>();
        if (prevIdx == -1 || nums[idx] % nums[prevIdx] == 0) {
            pick.add(nums[idx]);
            // Recurse with the current index becoming the new 'prevIdx'
            pick.addAll(solveRecursive(idx + 1, idx, nums));
        }

        // Return the list that resulted in a larger subset
        return pick.size() > skip.size() ? pick : skip;
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Intuition:
     * The recursion recalculates the exact same (idx, prevIdx) states multiple times.
     * We can cache the resulting List<Integer> for each state.
     * Note: Since prevIdx starts at -1, we shift it by +1 in our memo array to avoid negative indices.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). There are N * N unique states.
     * - Space Complexity: O(N^2) for the DP table (heap) + O(N) stack space.
     * *Warning: Storing full ArrayLists inside the DP table consumes massive heap memory!
     */
    public List<Integer> phase2_memoization(int[] nums) {
        if (nums == null || nums.length == 0) return new ArrayList<>();
        Arrays.sort(nums);

        int n = nums.length;
        // dp[idx][prevIdx + 1]
        List<Integer>[][] dp = new ArrayList[n][n + 1];

        return solveMemo(0, -1, nums, dp);
    }

    private List<Integer> solveMemo(int idx, int prevIdx, int[] nums, List<Integer>[][] dp) {
        if (idx == nums.length) return new ArrayList<>();

        // Check Cache (prevIdx shifted by 1)
        if (dp[idx][prevIdx + 1] != null) {
            return dp[idx][prevIdx + 1];
        }

        List<Integer> skip = solveMemo(idx + 1, prevIdx, nums, dp);

        List<Integer> pick = new ArrayList<>();
        if (prevIdx == -1 || nums[idx] % nums[prevIdx] == 0) {
            pick.add(nums[idx]);
            pick.addAll(solveMemo(idx + 1, idx, nums, dp));
        }

        List<Integer> result = pick.size() > skip.size() ? pick : skip;

        // Cache the best list for this state
        return dp[idx][prevIdx + 1] = result;
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage) - ✨ INDUSTRY STANDARD
     * ==============================================================================================
     * Intuition:
     * Storing lists in a 2D matrix (Phase 2) is a massive waste of memory.
     * Instead, we use a 1D DP array where `dp[i]` stores ONLY the LENGTH of the longest subset ending at `i`.
     * To physically rebuild the subset at the end, we use a secondary 1D array `hash[i]` that acts
     * as a pointer to the previous index.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). Nested loops to compare every element with all previous elements.
     * - Space Complexity: O(N). Two 1D arrays of size N on the heap. No recursion stack!
     */
    public List<Integer> phase3_tabulation(int[] nums) {
        int n = nums.length;
        if (n == 0) return new ArrayList<>();
        Arrays.sort(nums);

        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Every element is a valid subset of length 1 by itself

        int[] hash = new int[n]; // Tracks the parent indices to rebuild the path
        for (int i = 0; i < n; i++) hash[i] = i;

        int maxLength = 1;
        int lastIndex = 0;

        // Build the DP table
        for (int i = 1; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                // If divisible AND it forms a strictly longer sequence
                if (nums[i] % nums[prev] == 0 && dp[i] < dp[prev] + 1) {
                    dp[i] = dp[prev] + 1;
                    hash[i] = prev; // Log the parent
                }
            }
            // Track where the absolute longest subset ends
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                lastIndex = i;
            }
        }

        // Reconstruct the path using the hash pointers
        List<Integer> result = new ArrayList<>();
        result.add(nums[lastIndex]);

        while (hash[lastIndex] != lastIndex) { // While parent is not itself
            lastIndex = hash[lastIndex];
            result.add(nums[lastIndex]);
        }

        // List is built backward, so reverse it
        Collections.reverse(result);
        return result;
    }

    /**
     * ==============================================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" Stage)
     * ==============================================================================================
     * Intuition:
     * In standard Grid DP or Subset Sum problems, we can reduce O(N^2) space to O(N) because
     * Row 'i' only ever looks at Row 'i-1'.
     * * HOWEVER, in Longest Increasing Subsequence (LIS) variations like this, element 'i' must
     * potentially look back at EVERY single previous element from index 0 to i-1.
     * * Therefore, the Tabulation approach above (using 1D arrays) IS ALREADY the ultimate space
     * optimization for this problem. We cannot compress it down to O(1) variables.
     * * The code below simply calls the optimal Phase 3 implementation.
     */
    public List<Integer> phase4_spaceOptimization(int[] nums) {
        // Space Optimization for LIS variations strictly plateaus at O(N) using 1D arrays.
        // Returning Phase 3 as it represents the theoretical limit of optimization.
        return phase3_tabulation(nums);
    }

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE APPROACHES
     * ==============================================================================================
     * 1. Directed Acyclic Graph (DAG) - Longest Path Algorithm:
     * We can treat every number as a node in a graph. Draw a directed edge from node A to node B
     * if B % A == 0. The problem then becomes finding the Longest Path in a DAG.
     * While theoretically elegant, the topological sorting and path tracking require O(N^2) time
     * and O(N^2) space (to store the adjacency list), making DP (Phase 3) strictly superior
     * due to its O(N) space footprint.
     */
}
package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: Partition Set Into 2 Subsets With Min Absolute Sum Diff (DP-16)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an array of n integers, partition the array into two subsets such that the absolute
 * difference between their sums is minimized.
 *
 * EXAMPLES:
 * Input: nums = [1, 2, 3, 4]
 * Output: 0
 * Explanation: Two subsets can be [1,4] (sum=5) and [2,3] (sum=5). Diff = 0.
 *
 * Input: nums = [8, 6, 5]
 * Output: 3
 * Explanation: Two subsets can be [8] (sum=8) and [6, 5] (sum=11). Diff = |8 - 11| = 3.
 * ==================================================================================================
 * Let's trace finding if a subset sum of 3 is possible for arr = [1, 2, 3].
 * We start from the last index (idx = 2).
 *
 *                                  f(idx=2, target=3) -> arr[2] = 3
 *                               /                                  \
 *                (Exclude 3)  /                                      \ (Include 3)
 *                           /                                          \
 *                  f(1, 3) -> arr[1]=2                                f(1, 0)
 *                  /                 \                                   |
 *        (Exclude 2)               (Include 2)                     🎯 TARGET 0 MET!
 *          /                             \                         (Returns TRUE)
 *     f(0, 3)                          f(0, 1)
 *     arr[0]=1                         arr[0]=1
 *     Target 3 != 1                    Target 1 == 1
 *     (Returns FALSE)                  (Returns TRUE)
 *
 * Index \ Target (S1), 0,   1,  2,  3,  4,  5,  6
 * 0 (val: 1),          T,   T,  F,  F,  F,  F,  F
 * 1 (val: 2),          T,   T,  T,  T,  F,  F,  F
 * 2 (val: 3),          T,   T,  T,  T,  T,  T,  T
 *
 * We look at the last row to see all possible Target values.
 * Because $S1 = 3$ is True, $S2 = 6 - 3 = 3$. The minimum difference is $|3 - 3| = 0$!
 *
  * *********************************************************************************************
 *
 * ### 🧠 The Mathematical Insight (The "Aha!" Moment)
 *
 * The absolute core of this problem relies on a simple mathematical truth.
 * If you divide an array into two subsets ($S_1$ and $S_2$), their individual sums combined will always equal the total sum of the entire array.
 *
 * * $S_1 + S_2 = \text{TotalSum}$
 * * Therefore, $S_2 = \text{TotalSum} - S_1$
 *
 * The problem asks us to minimize the absolute difference: $|S_1 - S_2|$.
 * If we substitute $S_2$ using our formula above, the difference becomes:
 * **$|S_1 - (\text{TotalSum} - S_1)| \rightarrow |\text{TotalSum} - 2 \cdot S_1|$**
 *
 * **The Conclusion:** We don't actually need to find both subsets.
 * We just need to figure out **all possible values** that a single subset ($S_1$) can form.
 * Once we know what sums $S_1$ can make, we just plug them into that simple formula and find the smallest result!
 *
 * ### 🗺️ The Approach Strategy: DP on Subsequences
 * To find all possible sums for $S_1$, we use the exact same logic as the classic **Subset Sum** Dynamic Programming problem.
 * We ask the question: *"Using the elements in our array,
 * is it possible to create a subset sum of exactly $X$?"* We do this for every target $X$ from $0$ up to $\text{TotalSum}$.
 * We build this iteratively (Bottom-Up). For every element, we decide:
 *
 * 1. **Exclude it:** The subset sum remains the same.
 * 2. **Include it:** The subset sum increases by the element's value.
 *
 * ### 👣 Step-by-Step Execution (Space-Optimized DP)
 * Here is the exact algorithmic flow you should walk an interviewer through:
 *
 * **Step 1: Find the Total Sum**
 * * Iterate through the array and add up all the elements to get the `totSum`.
 * * This defines the maximum possible boundary for our subset sum.
 *
 * **Step 2: Initialize the DP Array**
 * * Create a boolean 1D array called `prev` of size `totSum + 1`.
 * * This array will track which subset sums are mathematically possible.
 * `prev[X] == true` means we can form a subset that sums exactly to $X$.
 * * **Base Case:** `prev[0] = true` (You can always make a sum of $0$ by picking no elements).
 * * **Base Case:** If the first element is $e$, set `prev[e] = true`.
 *
 * **Step 3: Fill the DP Array (Iterative Choices)**
 * * Loop through the remaining elements in the array one by one.
 * * For each element, create a new boolean array `curr` (also size `totSum + 1`).
 * * Check every target sum from $1$ to `totSum`:
 * * *Path 1 (Exclude):* Look at `prev[target]`. If it was possible without this element, it's still possible.
 * * *Path 2 (Include):* Look at `prev[target - currentElement]`. If we could make the remainder previously, we can make the target now.
 * * Mark `curr[target] = true` if either path works.
 *
 * * Make `prev = curr` and move to the next element.
 *
 * **Step 4: Find the Minimum Difference**
 * * After the loops finish, your `prev` array contains the final truth. It tells you exactly which subset sums ($S_1$) are possible using the whole array.
 * * Initialize `minDiff = Integer.MAX_VALUE`.
 * * Loop through the `prev` array from $i = 0$ to `totSum / 2` (we only need to check half, because $S_2$ will naturally cover the other half).
 * * If `prev[i]` is `true` (meaning $S_1 = i$ is a valid subset sum):
 * * Calculate the difference: `Math.abs(totSum - 2 * i)`
 * * Update `minDiff = Math.min(minDiff, difference)`
 *
 * * Return `minDiff`.
 *
 * **Complexity:** * **Time:** $O(N \cdot \text{TotalSum})$
 * * **Space:** $O(\text{TotalSum})$ (Using the 1D space optimization).
 *
 */
public class PartitionMinSubsetSumDifferencePostiveN {

    // 🔍 MAIN METHOD FOR TESTING
    public static void main(String[] args) {
        PartitionMinSubsetSumDifferencePostiveN solver = new PartitionMinSubsetSumDifferencePostiveN();

        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {8, 6, 5};

        System.out.println("Array: [1, 2, 3, 4]");
        System.out.println("1. Memoization      : " + solver.minSubsetSumDifference(arr1, arr1.length));
        System.out.println("2. Tabulation       : " + solver.minSubsetSumDifferenceTabulation(arr1, arr1.length));
        System.out.println("3. Space Optimized  : " + solver.minSubsetSumDifferenceSpaceOptimization(arr1, arr1.length));

        System.out.println("\nArray: [8, 6, 5]");
        System.out.println("1. Space Optimized  : " + solver.minSubsetSumDifferenceSpaceOptimization(arr2, arr2.length));
    }

    /**
     * ======================================================================
     * APPROACH 1: MEMOIZATION (Top-Down)
     * ======================================================================
     * Time Complexity: O(N * totSum) - Each state is computed exactly once.
     * Space Complexity: O(N * totSum) for DP table + O(N) for recursion stack.
     */
    public boolean subsetSumUtil(int ind, int target, int[] arr, int[][] dp) {
        // Base case 1: If the target sum is exactly 0, a valid subset exists (empty subset or exact match)
        if (target == 0) {
            dp[ind][target] = 1; // 1 represents true
            return true;
        }

        // Base case 2: If we are at the first element, we can only form the target if they are equal
        if (ind == 0) {
            boolean isPossible = (arr[0] == target);
            dp[ind][target] = isPossible ? 1 : 0;
            return isPossible;
        }

        // Memoization Check: Return previously computed result to avoid recomputation
        if (dp[ind][target] != -1) {
            return dp[ind][target] == 1;
        }

        // Choice 1: Exclude the current element. Target remains the same.
        boolean notTaken = subsetSumUtil(ind - 1, target, arr, dp);

        // Choice 2: Include the current element. Only valid if it does not exceed the remaining target.
        boolean taken = false;
        if (arr[ind] <= target) {
            taken = subsetSumUtil(ind - 1, target - arr[ind], arr, dp);
        }

        // Store the result in dp array and return
        boolean result = notTaken || taken;
        dp[ind][target] = result ? 1 : 0;
        return result;
    }

    public int minSubsetSumDifference(int[] arr, int n) {
        int totSum = 0;
        for (int val : arr) {
            totSum += val;
        }

        // Initialize dp table with -1 (uncalculated). 1 = true, 0 = false.
        int[][] dp = new int[n][totSum + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Populate the DP table by checking every possible sum from 0 to totSum
        for (int i = 0; i <= totSum; i++) {
            subsetSumUtil(n - 1, i, arr, dp);
        }

        int mini = Integer.MAX_VALUE;

        // Check the last row of the DP table to find all valid subset sums (S1)
        for (int s1 = 0; s1 <= totSum; s1++) {
            if (dp[n - 1][s1] == 1) { // If sum S1 is possible
                int s2 = totSum - s1; // The rest of the array forms S2
                int diff = Math.abs(s1 - s2);
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }

    /**
     * ======================================================================
     * APPROACH 2: TABULATION (Bottom-Up DP)
     * ======================================================================
     * Time Complexity: O(N * totSum) - Nested loops iterate through all states.
     * Space Complexity: O(N * totSum) - 2D DP array. No recursion stack overhead!
     */
    public int minSubsetSumDifferenceTabulation(int[] arr, int n) {
        int totSum = 0;
        for (int i = 0; i < n; i++) {
            totSum += arr[i];
        }

        // dp[i][t] = true if a subset of arr[0..i] has a sum exactly equal to 't'
        boolean[][] dp = new boolean[n][totSum + 1];

        // Base case 1: Target 0 is always possible (pick no elements)
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // Base case 2: The first element can form a sum equal to its own value
        if (arr[0] <= totSum) {
            dp[0][arr[0]] = true;
        }

        // Fill the DP table using a bottom-up approach
        for (int ind = 1; ind < n; ind++) {
            for (int target = 1; target <= totSum; target++) {

                // Exclude the current element (copy answer from the row above)
                boolean notTaken = dp[ind - 1][target];

                // Include the current element if it fits
                boolean taken = false;
                if (arr[ind] <= target) {
                    taken = dp[ind - 1][target - arr[ind]];
                }

                // Current state is possible if either choice works
                dp[ind][target] = notTaken || taken;
            }
        }

        int mini = Integer.MAX_VALUE;
        // Search the last row for valid S1 sums
        for (int i = 0; i <= totSum; i++) {
            if (dp[n - 1][i]) {
                int diff = Math.abs(i - (totSum - i));
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }

    /**
     * ======================================================================
     * APPROACH 3: SPACE OPTIMIZATION (Best DP Solution)
     * ======================================================================
     * Time Complexity: O(N * totSum)
     * Space Complexity: O(totSum) - We only store the "previous" row, reducing
     * memory usage drastically from a 2D matrix to a single 1D array.
     */
    public int minSubsetSumDifferenceSpaceOptimization(int[] arr, int n) {
        int totSum = 0;
        for (int i = 0; i < n; i++) {
            totSum += arr[i];
        }

        // 'prev' represents the row we just calculated (i - 1)
        boolean[] prev = new boolean[totSum + 1];

        // Base cases for the first row (index 0)
        prev[0] = true;
        if (arr[0] <= totSum) {
            prev[arr[0]] = true;
        }

        // Process the rest of the array elements
        for (int ind = 1; ind < n; ind++) {
            // 'cur' represents the current row we are calculating
            boolean[] cur = new boolean[totSum + 1];
            cur[0] = true; // Target 0 is always achievable

            for (int target = 1; target <= totSum; target++) {

                boolean notTaken = prev[target];

                boolean taken = false;
                if (arr[ind] <= target) {
                    taken = prev[target - arr[ind]];
                }

                cur[target] = notTaken || taken;
            }

            // Move to the next row: current row becomes the previous row
            prev = cur;
        }

        int mini = Integer.MAX_VALUE;
        // Search the final 'prev' array for valid S1 sums
        for (int i = 0; i <= totSum; i++) {
            if (prev[i]) {
                int diff = Math.abs(i - (totSum - i));
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }
}
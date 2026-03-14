package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: SUBSET SUM EQUAL TO TARGET (Medium)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an array of positive integers 'ARR' and an integer 'K', find if there exists a subset
 * in 'ARR' with a sum equal to 'K'. Return true if it exists, otherwise return false.
 *
 * A subset/subsequence is a contiguous or non-contiguous part of an array where elements appear
 * in the same order as the original array.
 *
 * EXAMPLE BEING USED:
 * Input: arr = [2, 3, 1], target = 4
 * Output: true
 * Explanation: The subset {3, 1} sums exactly to 4.
 *
 * ==================================================================================================
 * APPROACH EXPLANATION (0/1 Knapsack Variation)
 * ==================================================================================================
 * For every element in the array, we have exactly two choices:
 * 1. EXCLUDE: Do not pick the element. The target remains the same.
 * 2. INCLUDE: Pick the element (if its value is <= target). The target decreases by its value.
 * If ANY combination of these choices reduces the target to exactly 0, a valid subset exists.
 * * We evolve our solution through 4 stages:
 * 1. Recursion: Try all paths. (Exponential Time)
 * 2. Memoization: Cache repeated subproblems. (O(N*K) Time, O(N*K) Space + Stack)
 * 3. Tabulation: Build bottom-up iteratively. (O(N*K) Time, O(N*K) Space)
 * 4. Space Optimization: Keep only the previous row. (O(N*K) Time, O(K) Space)
 *
 * ==================================================================================================
 * 🌳 RECURSION TREE VISUALIZATION (arr = [2,3,1], target = 4)
 * ==================================================================================================
 * We start at the last index (2) and work backwards.
 *
 *                             f(idx=2, target=4)
 *                          /                    \
 *            (Exclude 1)  /                      \ (Include 1)
 *                        /                        \
 *                f(1, 4)                             f(1, 3)
 *               /     \                            /        \
 *      (Exclude 3)    (Include 3)             (Exclude 3)    (Include 3)
 *      /               \                      /               \
 *      f(0, 4)           f(0, 1)              f(0, 3)            f(0, 0)
 *      arr[0]=2          arr[0]=2             arr[0]=2           🎯 TARGET 0!
 *      Target 4!=2       Target 1!=2          Target 3!=2        (Returns TRUE)
 *      (Returns F)       (Returns F)          (Returns F)
 *
 *
 *
 * ==================================================================================================
 * 📦 FINAL DP ARRAY VISUALIZATION (Tabulation/Memoization)
 * ==================================================================================================
 * Target ranges from 0 to 4. (T = True, F = False)
 * * Index \ Target |  0  |  1  |  2  |  3  |  4  |
 * 0 (val: 2)       |  T  |  F  |  T  |  F  |  F  |  <- Row 0: Only target 2 is achievable.
 * 1 (val: 3)       |  T  |  F  |  T  |  T  |  F  |  <- Row 1: Combines above with val 3.
 * 2 (val: 1)       |  T  |  T  |  T  |  T  |  T  |  <- Row 2: Combines above with val 1. (Answer at dp[2][4] is T)
 * *
 * ==================================================================================================
 */
public class SubsetSumK {

    public static void main(String[] args) {
        // Using the exact example from our documentation
        int[] arr = {2, 3, 1};
        int k = 4;
        int n = arr.length;

        System.out.println("Array: " + Arrays.toString(arr) + " | Target: " + k);
        System.out.println("--------------------------------------------------");

        // 1️⃣ Test Recursion
        System.out.println("1. Recursion       : " + subsetSumRecursive(n - 1, k, arr));

        // 2️⃣ Test Memoization
        // We use an int[][] instead of boolean[][] because boolean arrays default to false.
        // We need a way to represent an "uncomputed" state, so we use -1.
        // -1 = Uncomputed, 1 = True, 0 = False.
        int[][] dp = new int[n][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + subsetSumMemo(n - 1, k, arr, dp));

        // 3️⃣ Test Tabulation
        System.out.println("3. Tabulation      : " + subsetSumTabulation(n, k, arr));

        // 4️⃣ Test Space Optimized
        System.out.println("4. Space Optimized : " + subsetSumSpaceOptimized(n, k, arr));
    }

    /**
     * APPROACH 1: PLAIN RECURSION (Brute Force)
     * Time Complexity: O(2^N) - Every element has 2 choices (Pick/Not Pick).
     * Space Complexity: O(N) - Maximum depth of the recursion tree.
     */
    private static boolean subsetSumRecursive(int index, int target, int[] arr) {
        // BASE CASE 1: If target reaches 0, we found a valid subset!
        if (target == 0) return true;

        // BASE CASE 2: If we are at the very first element (index 0),
        // the only way to succeed is if this element equals the remaining target.
        if (index == 0) return arr[0] == target;

        // CHOICE 1: Do not pick the current element.
        // Target remains the same, move to the previous index.
        boolean notPick = subsetSumRecursive(index - 1, target, arr);

        // CHOICE 2: Pick the current element.
        // We can only pick it if its value doesn't exceed our remaining target.
        boolean pick = false;
        if (arr[index] <= target) {
            pick = subsetSumRecursive(index - 1, target - arr[index], arr);
        }

        // Return true if either choosing or not choosing leads to a valid subset
        return pick || notPick;
    }

    /**
     * APPROACH 2: MEMOIZATION (Top-Down DP)
     * Time Complexity: O(N * K) - We compute each (index, target) state exactly once.
     * Space Complexity: O(N * K) [DP array] + O(N) [Recursion Stack].
     *
     * The reason we use an int[][] array initialized to -1 instead of a primitive boolean[][] array comes down to the "Three-State Problem" in memoization.
     * The "Three-State" Problem in Memoization
     * In Top-Down DP, our cache isn't just tracking true or false. We actually need a way to represent three distinctly different states:
     * Uncalculated: We haven't visited this subproblem yet.
     * Calculated & True: We visited it, and a valid subset does exist.
     * Calculated & False: We visited it, and a valid subset does not exist.
     */
    private static boolean subsetSumMemo(int index, int target, int[] arr, int[][] dp) {
        // Base cases (Same as recursion)
        if (target == 0) return true;
        if (index == 0) return arr[0] == target;

        // MEMOIZATION CHECK: If we've calculated this exact state before, return it instantly.
        if (dp[index][target] != -1) {
            return dp[index][target] == 1;
        }

        // Branch 1: Exclude
        boolean notPick = subsetSumMemo(index - 1, target, arr, dp);

        // Branch 2: Include
        boolean pick = false;
        if (arr[index] <= target) {
            pick = subsetSumMemo(index - 1, target - arr[index], arr, dp);
        }

        // CACHE THE RESULT: Map the boolean result to our integer DP table (1 = true, 0 = false)
        boolean result = pick || notPick;
        dp[index][target] = result ? 1 : 0;

        return result;
    }

    /**
     * APPROACH 3: TABULATION (Bottom-Up DP)
     * Time Complexity: O(N * K) - Nested loops over array length and target.
     * Space Complexity: O(N * K) - 2D DP array. Recursion stack overhead is eliminated.
     *
     * arr = [2, 3, 1], n = 3, k = 7.
     * default values of dp array for tabulation 
     * Index (i) \ Target (t),  0,  1,  2,  3,  4,  5,  6,  7
     * 0 (arr[0] = 2),          T,  F,  T,  F,  F,  F,  F,  F
     * 1 (arr[1] = 3),          T,  F,  F,  F,  F,  F,  F,  F
     * 2 (arr[2] = 1),          T,  F,  F,  F,  F,  F,  F,  F
     */
    private static boolean subsetSumTabulation(int n, int k, int[] arr) {
        // dp[i][t] is true if there is a subset from index 0..i that sums exactly to 't'
        boolean[][] dp = new boolean[n][k + 1];

        // BASE CASE 1: A target of 0 can always be achieved with an empty subset.
        // So, the entire 0th column is marked as true.
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // BASE CASE 2: The first element (index 0) can only achieve a target equal to its own value.
        // We must ensure arr[0] <= k to avoid an ArrayIndexOutOfBoundsException.
        if (arr[0] <= k) {
            dp[0][arr[0]] = true;
        }

        // ITERATE through the rest of the array (rows) and targets (columns)
        for (int i = 1; i < n; i++) {
            for (int target = 1; target <= k; target++) {

                // Path 1: Value if we don't pick the item (just copy from the row directly above)
                boolean notPick = dp[i - 1][target];

                // Path 2: Value if we do pick the item
                boolean pick = false;
                if (arr[i] <= target) {
                    pick = dp[i - 1][target - arr[i]];
                }

                // Current cell is true if either path is successful
                dp[i][target] = pick || notPick;
            }
        }

        // The final answer resides in the bottom-right corner of our table
        return dp[n - 1][k];
    }

    /**
     * APPROACH 4: SPACE OPTIMIZED DP (Best Solution)
     * Time Complexity: O(N * K)
     * Space Complexity: O(K) - We only store two 1D arrays of size K, dramatically reducing memory.
     * * Why this works: In tabulation, row `i` ONLY ever looks at row `i - 1`.
     * Older rows are useless. We can just keep the "previous" row in memory.
     */
    private static boolean subsetSumSpaceOptimized(int n, int k, int[] arr) {
        // 'prev' represents the row we just finished calculating (i - 1)
        boolean[] prev = new boolean[k + 1];

        // Base case setup for the first row (index 0)
        prev[0] = true;
        if (arr[0] <= k) {
            prev[arr[0]] = true;
        }

        // Process the rest of the elements
        for (int i = 1; i < n; i++) {
            // 'curr' represents the row we are currently calculating
            boolean[] curr = new boolean[k + 1];

            // Base case: target 0 is always achievable
            curr[0] = true;

            for (int target = 1; target <= k; target++) {

                boolean notPick = prev[target];

                boolean pick = false;
                if (arr[i] <= target) {
                    pick = prev[target - arr[i]];
                }

                curr[target] = pick || notPick;
            }

            // CRITICAL STEP: The row we just calculated becomes the 'previous' row for the next cycle
            prev = curr;
        }

        // The final answer is in the last cell of our 'prev' array
        return prev[k];
    }
}
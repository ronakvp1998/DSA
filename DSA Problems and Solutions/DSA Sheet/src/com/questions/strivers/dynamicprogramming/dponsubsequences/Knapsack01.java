package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ============================================================================
 * MASTERCLASS: 0/1 Knapsack Problem
 * ============================================================================
 *
 * ### 1. Problem Documentation
 *
 * **Problem Statement:**
 * You are given 'N' items, each with a specific weight 'wt[i]' and a value 'val[i]'.
 * You have a knapsack with a maximum weight capacity 'W'. Determine the maximum
 * total value you can achieve by selecting a subset of items such that the sum
 * of their weights does not exceed 'W'. Each item can be picked at most once.
 *
 * **Input Format:**
 * - int[] wt: Array representing weights.
 * - int[] val: Array representing values.
 * - int W: Maximum weight capacity.
 * - int n: Total number of items.
 *
 * **Constraints:**
 * - 1 <= N <= 1000
 * - 1 <= W <= 1000
 * - 1 <= wt[i], val[i] <= 1000
 *
 * ----------------------------------------------------------------------------
 * **Conceptual Visualization**
 *
 * **Recursion Tree (Decision Tree for N=3 items, Capacity W=5):**
 * Let f(index, remaining_W) return the max value.
 * Example for item at index '2':
 *
 *                                          f(2, 5)
 *                                      [wt[2]=3, val[2]=4]
 *                              /                             \
 *                      NOT PICK (W=5)                     PICK (W=5-3=2)
 *                        /                                   \
 *                     f(1, 5)                             4 + f(1, 2)
 *                   /       \                           /           \
 *              NOT PICK      PICK                    NOT PICK        PICK
 *              /              \                      /                \
 *          f(0, 5)      val[1] + f(0, 5-wt[1])    f(0, 2)      val[1] + f(0, 2-wt[1])
 *
 *
 *    Weights (wt): [3, 2, 5], Values (val): [30, 40, 60], Max Capacity (W): 6
 *  * Index (i) \ Capacity (w) ->  0   1   2   3    4    5    6
 *  * ---------------------------------------------------------
 *  * 0 (Item 0: w=3, v=30)     |  0   0   0   30   30   30   30
 *  * 1 (Item 1: w=2, v=40)     |  0   0  40   40   40   70   70
 *  * 2 (Item 2: w=5, v=60)     |  0   0  40   40   40   70   70
 * ============================================================================
 */
public class Knapsack01{

    public static void main(String[] args) {
        System.out.println("=== 0/1 Knapsack Testing Suite ===\n");

        int[] wt = {3, 2, 5};
        int[] val = {30, 40, 60};
        int W = 6;
        int n = wt.length;

        System.out.println("Input Weights : " + Arrays.toString(wt));
        System.out.println("Input Values  : " + Arrays.toString(val));
        System.out.println("Max Capacity  : " + W);
        System.out.println("----------------------------------");

        // 1. Brute Force
        System.out.println("Phase 1 (Brute Force)      : " + phase1BruteForce(n - 1, W, wt, val));

        // 2. Memoization
        int[][] dp = new int[n][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("Phase 2 (Memoization)      : " + phase2Memoization(n - 1, W, wt, val, dp));

        // 3. Tabulation
        System.out.println("Phase 3 (Tabulation)       : " + phase3Tabulation(wt, val, W, n));

        // 4. Space Optimization
        System.out.println("Phase 4 (Space Optimized)  : " + phase4SpaceOptimized(wt, val, W, n));
    }

    /**
     * ==========================================================================
     * Phase 1: Recursive Brute Force (The "Think it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * We start from the last index (n-1) and evaluate two choices for every item:
     * 1. Not Pick: The knapsack capacity remains the same.
     * 2. Pick: If the item's weight is <= current capacity, we add its value and
     * subtract its weight from the remaining capacity.
     * We return the maximum value between picking and not picking.
     *
     * **Complexity Analysis:**
     * - Time Complexity: O(2^N) - We branch into two paths for every item.
     * - Space Complexity: O(N) - Auxiliary stack space for recursion depth.
     */
    public static int phase1BruteForce(int index, int W, int[] wt, int[] val) {
        // Base Case: If we are at the first item
        // Optimization: If the bag is full, we can't add any more value. Stop here.
        if (W == 0) return 0;
        if (index == 0) {
            // We can only pick it if its weight is <= our remaining capacity
            if (wt[0] <= W) return val[0];
            return 0;
        }

        // Choice 1: Do not pick the current item
        int notPick = phase1BruteForce(index - 1, W, wt, val);

        // Choice 2: Pick the current item (if capacity allows)
        int pick = 0;
        if (wt[index] <= W) {
            pick = val[index] + phase1BruteForce(index - 1, W - wt[index], wt, val);
        }

        return Math.max(notPick, pick);
    }

    /**
     * ==========================================================================
     * Phase 2: Top-Down Memoization (The "Refine it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * The brute force approach computes identical (index, W) states multiple times.
     * We introduce an int[][] dp array initialized to -1 to cache results. If a state
     * has been computed before (dp[index][W] != -1), we return it instantly in O(1) time.
     *
     * **Complexity Analysis:**
     * - Time Complexity: O(N * W) - We solve each subproblem exactly once.
     * - Space Complexity: O(N * W) for the DP Heap Matrix + O(N) Auxiliary Stack.
     */
    public static int phase2Memoization(int index, int W, int[] wt, int[] val, int[][] dp) {
        // Optimization: If the bag is full, we can't add any more value. Stop here.
        if (W == 0) return 0;
        if (index == 0) {
            if (wt[0] <= W) return val[0];
            return 0;
        }

        if (dp[index][W] != -1) return dp[index][W];

        int notPick = phase2Memoization(index - 1, W, wt, val, dp);

        int pick = 0;
        if (wt[index] <= W) {
            pick = val[index] + phase2Memoization(index - 1, W - wt[index], wt, val, dp);
        }

        return dp[index][W] = Math.max(notPick, pick);
    }

    /**
     * ==========================================================================
     * Phase 3: Bottom-Up Tabulation (The "Build it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * We convert the recursion into iterative loops to eliminate the O(N) stack space.
     * The base case for index 0 dictates that for any capacity >= wt[0], the max
     * value is val[0].
     *
     * **DP Array State Before Loops (Base Cases Initialization):**
     * Example: wt[0] = 3, val[0] = 30. Max Capacity W = 6.
     * w = {3,2,5} v = {30,40,60}
     * Index (i) \ Capacity (w) ->  0   1   2   3    4    5    6
     * ---------------------------------------------------------
     * 0 (Item 0: w=3, v=30)     |  0   0   0   30   30   30   30  <- Pre-filled!
     * 1 (Item 1: w=2, v=40)     |  0   0   0   0    0    0    0
     * 2 (Item 2: w=5, v=60)     |  0   0   0   0    0    0    0
     *
     * Notice how capacities 0, 1, and 2 remain 0 because the item is too heavy,
     * but capacities 3 through 6 are successfully populated with 30.
     *
     * * FINAL POPULATED DP ARRAY:
     *  * Index (i) \ Capacity (w) ->  0   1   2   3    4    5    6
     *  * ---------------------------------------------------------
     *  * 0 (Item 0: w=3, v=30)     |  0   0   0   30   30   30   30
     *  * 1 (Item 1: w=2, v=40)     |  0   0  40   40   40   70   70
     *  * 2 (Item 2: w=5, v=60)     |  0   0  40   40   40   70   70
     * **Complexity Analysis:**
     * - Time Complexity: O(N * W) - Two nested loops.
     * - Space Complexity: O(N * W) - DP matrix entirely in Heap space. No stack overhead.
     */
    public static int phase3Tabulation(int[] wt, int[] val, int W, int n) {
        int[][] dp = new int[n][W + 1];

        // 1. Initialize Base Case for Index 0
        // For the first item, if the capacity is greater than or equal to its weight,
        // we can pick it and get its value.
        for (int w = wt[0]; w <= W; w++) {
            dp[0][w] = val[0];
        }

        // 2. Iterate DP Table
        for (int index = 1; index < n; index++) {
            for (int w = 0; w <= W; w++) {

                int notPick = dp[index - 1][w];

                int pick = 0;
                if (wt[index] <= w) {
                    pick = val[index] + dp[index - 1][w - wt[index]];
                }

                dp[index][w] = Math.max(notPick, pick);
            }
        }

        return dp[n - 1][W];
    }

    /**
     * ==========================================================================
     * Phase 4: Space Optimization (The "Perfect it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * If we look at Phase 3, row 'index' ONLY ever relies on row 'index - 1'.
     * We don't need a 2D matrix. We can use a single 1D array `prev`.
     *
     * *CRITICAL TRICK:* We MUST traverse the capacity loop BACKWARDS (from W down to 0).
     * If we traverse forwards, we might overwrite a value in `prev` that we need later
     * in the same row's calculation, effectively allowing us to pick the same item
     * multiple times (which is the Unbounded Knapsack, not 0/1).
     *
     * **Complexity Analysis:**
     * - Time Complexity: O(N * W)
     * - Space Complexity: O(W) - Reduced from a massive 2D matrix to a single 1D array!
     */
    public static int phase4SpaceOptimized(int[] wt, int[] val, int W, int n) {
        int[] prev = new int[W + 1];

        // 1. Base Case Initialization
        for (int w = wt[0]; w <= W; w++) {
            prev[w] = val[0];
        }

        // 2. Iterate Array
        for (int index = 1; index < n; index++) {
            // Traverse backwards to preserve previous row's state for the current item
            for (int w = W; w >= 0; w--) {

                int notPick = prev[w];

                int pick = 0;
                if (wt[index] <= w) {
                    pick = val[index] + prev[w - wt[index]];
                }

                // Overwrite the array in place
                prev[w] = Math.max(notPick, pick);
            }
        }

        return prev[W];
    }

    /**
     * ==========================================================================
     * Phase 5: Alternative Approaches
     * ==========================================================================
     * 1. Greedy (Fractional Knapsack):
     * - Fails for 0/1 Knapsack. Sorting by value/weight ratio might leave unused
     * capacity that could have been better filled by a different combination.
     * Only works if we can break items into fractions.
     *
     * 2. Branch and Bound:
     * - Used when W is massively large (e.g., 10^9) but N is small to medium.
     * DP fails due to MLE (Memory Limit Exceeded), but Branch and Bound uses
     * a priority queue to explore the state space tree, pruning suboptimal
     * paths to reach the max value faster than brute force O(2^N).
     */
}
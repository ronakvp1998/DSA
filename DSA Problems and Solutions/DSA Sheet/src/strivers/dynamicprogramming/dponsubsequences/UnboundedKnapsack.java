package com.questions.strivers.dynamicprogramming.dponsubsequences;
/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: UNBOUNDED KNAPSACK
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * A thief wants to rob a store. He is carrying a bag of capacity W.
 * The store has ‘n’ items of INFINITE supply.
 * - Each item 'i' has a weight 'wt[i]' and a value 'val[i]'.
 * - He can either include an item in his knapsack or exclude it.
 * - He can take a single item ANY number of times.
 * * Find the maximum value of items that the thief can steal without exceeding capacity W.
 * * * EXAMPLES:
 * Example 1:
 * Input: N = 3, W = 10, wt = [2, 4, 6], val = [5, 11, 13]
 * Output: 27
 * Explanation:
 * - Take item 1 (wt=4, val=11) -> Rem W=6
 * - Take item 1 (wt=4, val=11) -> Rem W=2
 * - Take item 0 (wt=2, val=5)  -> Rem W=0
 * Total Value = 11 + 11 + 5 = 27.
 * * Example 2 (Edge Case):
 * Input: N = 2, W = 3, wt = [4, 5], val = [10, 20]
 * Output: 0
 * Explanation: No item can fit in the knapsack of capacity 3.
 * * * CONSTRAINTS:
 * 1 <= N <= 1000
 * 1 <= W <= 1000
 * 1 <= wt[i], val[i] <= 10^5
 * * ==================================================================================================
 * 💡 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * The core difference between 0/1 Knapsack and Unbounded Knapsack is infinite supply.
 * When we choose to "Pick" an item, we DO NOT move to the previous index (i-1). We stay at the SAME
 * index (i) because we might want to pick it again. We only move to (i-1) when we "Not Pick" an item.
 * * 🌳 RECURSION TREE
 * Example: W = 10, wt = [2, 4], val = [5, 11]
 * State: f(index, remainingWeight)
 *                          * f(1, 10)  <-- Considering item 1 (wt=4, val=11)
 *                          /        \
 *              (Pick 1)   /          \   (Not Pick 1)
 *                        /            \
 *              f(1, 6) + 11         f(0, 10) <-- Moved to item 0
 *               /         \             /     \
 *          (Pick 1)     (Not Pick)     (Pick 0)   (Not Pick)
 *              /               \           /           \
 *          f(1, 2) + 11       f(0, 6)    f(0, 8) + 5     f(-1, 10) [0]
 *              / \               ...         ...
 *          ...   ...
 * ==================================================================================================
 *
 */

public class UnboundedKnapsack {

    public static void main(String[] args) {
        UnboundedKnapsack solver = new UnboundedKnapsack();

        int[] wt1 = {2, 4, 6};
        int[] val1 = {5, 11, 13};
        int W1 = 10;
        int N1 = 3;

        int[] wt2 = {4, 5};
        int[] val2 = {10, 20};
        int W2 = 3; // Edge case: Capacity too small
        int N2 = 2;

        System.out.println("--- Test Case 1: W = 10, wt = [2,4,6], val = [5,11,13] ---");
        System.out.println("Phase 1 (Brute Force) : " + solver.phase1_bruteForce(W1, wt1, val1, N1));
        System.out.println("Phase 2 (Memoization) : " + solver.phase2_memoization(W1, wt1, val1, N1));
        System.out.println("Phase 3 (Tabulation)  : " + solver.phase3_tabulation(W1, wt1, val1, N1));
        System.out.println("Phase 4 (Space Opt.)  : " + solver.phase4_spaceOptimization(W1, wt1, val1, N1));

        System.out.println("\n--- Test Case 2 (Edge): W = 3, wt = [4,5], val = [10,20] ---");
        System.out.println("Phase 4 (Space Opt.)  : " + solver.phase4_spaceOptimization(W2, wt2, val2, N2));

        System.out.println("\n--- Test Case 3 (Zero): W = 0, wt = [1], val = [10] ---");
        System.out.println("Phase 4 (Space Opt.)  : " + solver.phase4_spaceOptimization(0, new int[]{1}, new int[]{10}, 1));
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Detailed Intuition:
     * Starting from the last item (N-1), we decide whether to pick it or not.
     * - Not Pick: Move to the previous item (idx - 1), capacity remains W.
     * - Pick: Only possible if wt[idx] <= W. Add val[idx] to total. Crucially, pass 'idx'
     * (not idx - 1) into the recursive call, allowing the item to be picked repeatedly.
     * Return the maximum of both choices.
     * * Complexity Analysis:
     * - Time Complexity: O(2^(W / min_wt)) - At each step we have 2 choices. The maximum depth of
     * the tree depends on how many times we can pick the lightest item.
     * - Space Complexity: O(W / min_wt) - Auxiliary stack space for recursion depth. No heap space.
     */
    public int phase1_bruteForce(int W, int[] wt, int[] val, int n) {
        if (n == 0 || W == 0) return 0;
        return solveRecursive(n - 1, W, wt, val);
    }

    private int solveRecursive(int idx, int w, int[] wt, int[] val) {
        // Base Case: If we are at the 0th item, we take it as many times as it fits.
        if (idx == 0) {
            return (w / wt[0]) * val[0];
        }

        int notPick = solveRecursive(idx - 1, w, wt, val);

        int pick = 0;
        if (wt[idx] <= w) {
            // Notice: We pass 'idx', not 'idx - 1', keeping infinite supply intact.
            pick = val[idx] + solveRecursive(idx, w - wt[idx], wt, val);
        }

        return Math.max(pick, notPick);
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Detailed Intuition:
     * The brute force approach calculates identical states (idx, remaining_weight) repeatedly.
     * We initialize a 2D `dp` cache. If `dp[idx][w]` is already computed, we return it instantly.
     * * Complexity Analysis:
     * - Time Complexity: O(N * W). Each unique (index, weight) state is computed exactly once.
     * - Space Complexity: O(N * W) for the 2D DP array (Heap space) + O(W) auxiliary stack space
     * for recursion depth.
     */
    public int phase2_memoization(int W, int[] wt, int[] val, int n) {
        if (n == 0 || W == 0) return 0;

        Integer[][] dp = new Integer[n][W + 1];
        return solveMemo(n - 1, W, wt, val, dp);
    }

    private int solveMemo(int idx, int w, int[] wt, int[] val, Integer[][] dp) {
        if (idx == 0) {
            return (w / wt[0]) * val[0];
        }

        if (dp[idx][w] != null) return dp[idx][w];

        int notPick = solveMemo(idx - 1, w, wt, val, dp);

        int pick = 0;
        if (wt[idx] <= w) {
            pick = val[idx] + solveMemo(idx, w - wt[idx], wt, val, dp);
        }

        return dp[idx][w] = Math.max(pick, notPick);
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage)
     * ==============================================================================================
     * Detailed Intuition:
     * We drop the recursion stack by building our 2D array iteratively.
     * We initialize the base case for the 0th row, then compute subsequent rows based on
     * previously solved subproblems.
     * * ----------------------------------------------------------------------------------------------
     * EXACT INITIAL STATE (Right after base case initialization, before nested loops)
     * Example: N = 3, W = 10, wt = [2, 4, 6], val = [5, 11, 13]
     * * Index \ Weight (w) | 0 | 1 | 2 | 3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 |
     * -----------------------------------------------------------------------
     * 0 (wt=2, val=5)    | 0 | 0 | 5 | 5 | 10 | 10 | 15 | 15 | 20 | 20 | 25 | <-- Initialized
     * 1 (wt=4, val=11)   | 0 | 0 | 0 | 0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 | <-- Default zeros
     * 2 (wt=6, val=13)   | 0 | 0 | 0 | 0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 | <-- Default zeros
     * ----------------------------------------------------------------------------------------------
     * EXACT FINAL STATE (After loops finish)
     * * Index \ Weight (w) | 0 | 1 | 2 | 3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 |
     * -----------------------------------------------------------------------
     * 0 (wt=2, val=5)    | 0 | 0 | 5 | 5 | 10 | 10 | 15 | 15 | 20 | 20 | 25 |
     * 1 (wt=4, val=11)   | 0 | 0 | 5 | 5 | 11 | 11 | 16 | 16 | 22 | 22 | 27 |
     * 2 (wt=6, val=13)   | 0 | 0 | 5 | 5 | 11 | 11 | 16 | 16 | 22 | 22 | 27 | <-- Max Value is 27
     * ----------------------------------------------------------------------------------------------
     * * Complexity Analysis:
     * - Time Complexity: O(N * W). Nested loops over items and weights.
     * - Space Complexity: O(N * W). Full 2D array allocated on the heap. No stack space.
     */
    public int phase3_tabulation(int W, int[] wt, int[] val, int n) {
        if (n == 0 || W == 0) return 0;

        int[][] dp = new int[n][W + 1];

        // Base Case Initialization: For 0th item, take it as many times as it fits.
        for (int w = 0; w <= W; w++) {
            dp[0][w] = (w / wt[0]) * val[0];
        }

        // Build the table Bottom-Up
        for (int idx = 1; idx < n; idx++) {
            for (int w = 0; w <= W; w++) {

                int notPick = dp[idx - 1][w];

                int pick = 0;
                if (wt[idx] <= w) {
                    // Look at CURRENT row 'idx' for unbounded knapsack
                    pick = val[idx] + dp[idx][w - wt[idx]];
                }

                dp[idx][w] = Math.max(pick, notPick);
            }
        }

        return dp[n - 1][W];
    }

    /**
     * ==============================================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" Stage) - ✨ INDUSTRY STANDARD
     * ==============================================================================================
     * Detailed Intuition:
     * Notice the transition: `dp[idx][w] = max(dp[idx-1][w], val + dp[idx][w - wt[idx]])`.
     * To compute the current state, we need:
     * 1. The cell directly above it (`dp[idx-1][w]`).
     * 2. The cell in the SAME row to the left (`dp[idx][w - wt[idx]]`).
     * * We can compress the 2D array into a 1D array of size `W + 1`.
     * Unlike 0/1 Knapsack (which loops right-to-left to preserve old data), Unbounded Knapsack
     * loops LEFT-TO-RIGHT. This ensures that when we evaluate `w`, the value at `w - wt[idx]`
     * has ALREADY been updated with the current item, simulating the infinite supply!
     * * Complexity Analysis:
     * - Time Complexity: O(N * W). Nested loops over N and W.
     * - Space Complexity: O(W). A single 1D array on the heap. Ultimate space efficiency!
     */
    public int phase4_spaceOptimization(int W, int[] wt, int[] val, int n) {
        if (n == 0 || W == 0) return 0;

        int[] dp = new int[W + 1];

        // Base case: Initialize 1D array for 0th item
        for (int w = 0; w <= W; w++) {
            dp[w] = (w / wt[0]) * val[0];
        }

        // Iterate through remaining items
        for (int idx = 1; idx < n; idx++) {
            // Unbounded knapsack: Loop LEFT to RIGHT
            for (int w = 0; w <= W; w++) {
                if (wt[idx] <= w) {
                    dp[w] = Math.max(dp[w], val[idx] + dp[w - wt[idx]]);
                }
            }
        }

        return dp[W];
    }

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE APPROACHES
     * ==============================================================================================
     * Transformation to 0/1 Knapsack:
     * Technically, an unbounded knapsack problem can be transformed into a standard 0/1 Knapsack
     * problem by creating multiple copies of each item. Since max weight is W, we can create at most
     * W/wt[i] copies of item i, each with weight wt[i] and value val[i].
     * Then we run the standard 0/1 Knapsack algorithm.
     * * While mathematically sound, this transformation increases the number of items N drastically
     * and leads to horrific Time and Space complexities compared to the native Unbounded DP solution.
     * Phase 4 (1D Array looping Left-to-Right) remains the absolute gold standard approach.
     */
}
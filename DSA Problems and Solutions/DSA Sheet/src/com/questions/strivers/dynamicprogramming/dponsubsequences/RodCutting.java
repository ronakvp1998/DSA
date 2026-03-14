package com.questions.strivers.dynamicprogramming.dponsubsequences;
/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: ROD CUTTING PROBLEM (Unbounded Knapsack Variation)
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * Given a rod of length 'N' inches and an array of prices, 'price[]', that contains prices of all
 * pieces of size smaller than N. Determine the maximum value obtainable by cutting up the rod and
 * selling the pieces. You can make as many cuts as you want.
 * * This is a classic Unbounded Knapsack problem because we have an infinite supply of each rod piece
 * length (we can cut a length of 2 multiple times).
 * * EXAMPLES:
 * Example 1:
 * Input: N = 5, price[] = [2, 5, 7, 8, 10]
 * Output: 12
 * Explanation:
 * - Length 1 costs 2. Length 2 costs 5.
 * - Best Strategy: Cut into pieces of length 2, 2, 1.
 * - Value: 5 + 5 + 2 = 12.
 * * Example 2:
 * Input: N = 8, price[] = [1, 5, 8, 9, 10, 17, 17, 20]
 * Output: 22
 * Explanation:
 * - Best Strategy: Cut into pieces of length 2 and 6.
 * - Value: price[1] + price[5] = 5 + 17 = 22.
 * * CONSTRAINTS:
 * 1 <= N <= 1000
 * 1 <= price[i] <= 10^5
 * * ==================================================================================================
 * 💡 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * 🌳 RECURSION TREE
 * Example: N = 3, price = [2, 5, 7] (Lengths are 1, 2, 3 respectively)
 * State: f(index, remainingLength)
 * *                              f(2, 3)  <-- Considering piece of length 3 (idx=2, price=7)
 *                            /             \
 *              (Pick L=3)   /               \  (Not Pick L=3)
 *                          /                 \
 *              f(2, 0) + 7               f(1, 3) <-- Considering piece of length 2 (idx=1, price=5)
 *              [Returns 7]               /     \
 *          (Pick L=2) (Not Pick L=2)
 *              /           \
 *          f(1, 1) + 5    f(0, 3) <-- Considering piece of length 1 (idx=0, price=2)
 *          /   \           /   \
 *      ... [7] ...      ... [6] ...
 *
 * Index (Piece Considered) \ Rod Length,   0,  1,  2,  3
 * "0 (Length=1, Price=2)",                 0,  2,  4,  6
 * "1 (Length=2, Price=5)",                 0,  2,  5,  7
 * "2 (Length=3, Price=7)",                 0,  2,  5,  7
 * ==================================================================================================
 */

public class RodCutting {

    /**
     * ==============================================================================================
     * 4. TESTING SUITE
     * ==============================================================================================
     */
    public static void main(String[] args) {
        RodCutting solver = new RodCutting();

        int[] price1 = {2, 5, 7, 8, 10};
        int N1 = 5;

        int[] price2 = {1, 5, 8, 9, 10, 17, 17, 20};
        int N2 = 8;

        System.out.println("--- Test Case 1: N = 5, price = [2, 5, 7, 8, 10] ---");
        System.out.println("Phase 1 (Brute Force) : " + solver.phase1_bruteForce(price1, N1));
        System.out.println("Phase 2 (Memoization) : " + solver.phase2_memoization(price1, N1));
        System.out.println("Phase 3 (Tabulation)  : " + solver.phase3_tabulation(price1, N1));
        System.out.println("Phase 4 (Space Opt.)  : " + solver.phase4_spaceOptimization(price1, N1));

        System.out.println("\n--- Test Case 2: N = 8, price = [1, 5, 8, 9, 10, 17, 17, 20] ---");
        System.out.println("Phase 4 (Space Opt.)  : " + solver.phase4_spaceOptimization(price2, N2));

        System.out.println("\n--- Test Case 3 (Edge): N = 0, price = [] ---");
        System.out.println("Phase 4 (Space Opt.)  : " + solver.phase4_spaceOptimization(new int[]{}, 0));
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Detailed Intuition:
     * We start from the largest possible piece length (N) and decide whether to cut it or not.
     * - Not Pick: We don't cut this length. We move to the next smaller piece length (idx - 1).
     * - Pick: We cut this length. We add its price and keep the index the SAME (because we can
     * cut the same length again - Unbounded Knapsack property).
     * Return the maximum of both choices.
     * * Complexity Analysis:
     * - Time Complexity: O(2^N) - In the worst case, we branch exponentially at every step.
     * - Space Complexity: O(N) - Auxiliary stack space for recursion depth. No heap space.
     */
    public int phase1_bruteForce(int[] price, int n) {
        if (n == 0 || price == null || price.length == 0) return 0;
        return solveRecursive(price.length - 1, n, price);
    }

    private int solveRecursive(int idx, int remainingLen, int[] price) {
        if (idx == 0) {
            // Base Case: If we only have pieces of length 1, we just take as many as fit.
            return remainingLen * price[0];
        }

        int notPick = solveRecursive(idx - 1, remainingLen, price);

        int pick = Integer.MIN_VALUE;
        int rodLength = idx + 1; // rod length is 1-based, index is 0-based
        if (rodLength <= remainingLen) {
            pick = price[idx] + solveRecursive(idx, remainingLen - rodLength, price);
        }

        return Math.max(pick, notPick);
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Detailed Intuition:
     * The brute force approach calculates the same `(idx, remainingLen)` states redundantly.
     * We introduce a 2D cache `dp[][]` where `dp[idx][remainingLen]` stores the max revenue for
     * that state.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) - We compute each unique state exactly once.
     * - Space Complexity: O(N^2) for the DP table (Heap) + O(N) Auxiliary Stack space.
     */
    public int phase2_memoization(int[] price, int n) {
        if (n == 0 || price == null || price.length == 0) return 0;

        Integer[][] dp = new Integer[n][n + 1];
        return solveMemo(n - 1, n, price, dp);
    }

    private int solveMemo(int idx, int remainingLen, int[] price, Integer[][] dp) {
        if (idx == 0) {
            return remainingLen * price[0];
        }

        if (dp[idx][remainingLen] != null) return dp[idx][remainingLen];

        int notPick = solveMemo(idx - 1, remainingLen, price, dp);

        int pick = Integer.MIN_VALUE;
        int rodLength = idx + 1;
        if (rodLength <= remainingLen) {
            pick = price[idx] + solveMemo(idx, remainingLen - rodLength, price, dp);
        }

        return dp[idx][remainingLen] = Math.max(pick, notPick);
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage)
     * ==============================================================================================
     * Detailed Intuition:
     * We eliminate the recursion stack by building our 2D array iteratively.
     * * ----------------------------------------------------------------------------------------------
     * EXACT INITIAL STATE (Right after base case initialization, before nested loops)
     * Example: N = 5, price = [2, 5, 7, 8, 10]
     * * Index \ Length | 0 | 1 | 2 | 3 |  4 |  5 |
     * --------------------------------------------
     * 0 (len=1, p=2) | 0 | 2 | 4 | 6 |  8 | 10 | <-- Base Case: Length 1 gives (len * price[0])
     * 1 (len=2, p=5) | 0 | 0 | 0 | 0 |  0 |  0 |
     * 2 (len=3, p=7) | 0 | 0 | 0 | 0 |  0 |  0 |
     * 3 (len=4, p=8) | 0 | 0 | 0 | 0 |  0 |  0 |
     * 4 (len=5, p=10)| 0 | 0 | 0 | 0 |  0 |  0 |
     * * ----------------------------------------------------------------------------------------------
     * EXACT FINAL STATE OF 2D DP ARRAY (After loops finish)
     * * Index \ Length | 0 | 1 | 2 | 3 |  4 |  5 |
     * --------------------------------------------
     * 0 (len=1, p=2) | 0 | 2 | 4 | 6 |  8 | 10 |
     * 1 (len=2, p=5) | 0 | 2 | 5 | 7 | 10 | 12 | <-- max(NotPick(4), Pick(5 + dp[1][0])) = 5
     * 2 (len=3, p=7) | 0 | 2 | 5 | 7 | 10 | 12 |
     * 3 (len=4, p=8) | 0 | 2 | 5 | 7 | 10 | 12 |
     * 4 (len=5, p=10)| 0 | 2 | 5 | 7 | 10 | 12 | <-- Final Answer: 12
     * ----------------------------------------------------------------------------------------------
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) - Nested loops over all indices and lengths.
     * - Space Complexity: O(N^2) - 2D Array on the heap. No stack overhead.
     */
    public int phase3_tabulation(int[] price, int n) {
        if (n == 0 || price == null || price.length == 0) return 0;

        int[][] dp = new int[n][n + 1];

        // Base Case Initialization
        for (int len = 0; len <= n; len++) {
            dp[0][len] = len * price[0];
        }

        // Build the table Bottom-Up
        for (int idx = 1; idx < n; idx++) {
            for (int len = 0; len <= n; len++) {
                int notPick = dp[idx - 1][len];

                int pick = Integer.MIN_VALUE;
                int rodLength = idx + 1;
                if (rodLength <= len) {
                    pick = price[idx] + dp[idx][len - rodLength];
                }

                dp[idx][len] = Math.max(pick, notPick);
            }
        }

        return dp[n - 1][n];
    }

    /**
     * ==============================================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" Stage) - ✨ INDUSTRY STANDARD
     * ==============================================================================================
     * Detailed Intuition:
     * Look closely at the Tabulation transition: `dp[idx][len] = max(dp[idx-1][len], price + dp[idx][len - rodLength])`.
     * To compute the current cell, we ONLY need the cell directly above it (`idx - 1`), and the cell
     * in the SAME row to its left (`len - rodLength`).
     * * We can collapse the 2D array into a 1D array of size `N + 1`.
     * We iterate LEFT-TO-RIGHT. This ensures that when we evaluate length `L`, the value at `L - rodLength`
     * has ALREADY been updated with the current piece, allowing us to pick the same piece multiple times.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) - We still process every combination.
     * - Space Complexity: O(N) - A single 1D array on the heap. Maximum efficiency achieved!
     */
    public int phase4_spaceOptimization(int[] price, int n) {
        if (n == 0 || price == null || price.length == 0) return 0;

        int[] dp = new int[n + 1];

        // Base case: Initialize 1D array for 0th index (length 1 pieces)
        for (int len = 0; len <= n; len++) {
            dp[len] = len * price[0];
        }

        // Iterate through remaining pieces
        for (int idx = 1; idx < n; idx++) {
            int rodLength = idx + 1;
            // Unbounded knapsack: Loop LEFT to RIGHT
            for (int len = rodLength; len <= n; len++) {
                dp[len] = Math.max(dp[len], price[idx] + dp[len - rodLength]);
            }
        }

        return dp[n];
    }

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE APPROACHES
     * ==============================================================================================
     * Unbounded Knapsack Reduction:
     * The Rod Cutting Problem is fundamentally the exact same as the Unbounded Knapsack Problem.
     * - 'W' (Knapsack Capacity) = 'N' (Rod Length)
     * - 'wt[]' (Item Weights) = [1, 2, 3, ..., N] (Piece Lengths)
     * - 'val[]' (Item Values) = price[]
     * Thus, Phase 4 is structurally identical to the optimal Unbounded Knapsack solution. There are
     * no faster asymptotic approaches (like Greedy or Binary Search) because the problem requires
     * evaluating overlapping subproblems to find the optimal substructure.
     */
}
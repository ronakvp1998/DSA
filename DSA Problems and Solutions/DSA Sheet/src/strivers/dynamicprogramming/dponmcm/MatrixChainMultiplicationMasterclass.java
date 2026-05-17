package com.questions.strivers.dynamicprogramming.dponmcm;

/**
 * ==================================================================================================
 * 🏆 MASTERCLASS: MATRIX CHAIN MULTIPLICATION (DP-48)
 * ==================================================================================================
 * * 📝 PROBLEM STATEMENT:
 * Given a sequence of matrices, find the most efficient way to multiply these matrices together.
 * The problem is not actually to perform the multiplications, but merely to decide the sequence
 * of the matrix multiplications.
 * * We are given an array `arr` of size $n+1$, representing $n$ matrices.
 * The dimensions of the $i$-th matrix are $arr[i-1] \times arr[i]$.
 * * EXAMPLES:
 * Example 1:
 * Input: arr = [40, 20, 30, 10, 30]
 * Output: 26000
 * Explanation: There are 4 matrices of dimensions 40x20, 20x30, 30x10, and 10x30.
 * Best parenthesization is (A1 x (A2 x A3)) x A4. Cost = 26000.
 * * Example 2:
 * Input: arr = [10, 20, 30, 40, 30]
 * Output: 30000
 * Explanation: Optimal parenthesization minimizes the multiplication cost to 30000.
 * * CONSTRAINTS:
 * - 2 <= arr.length <= 100
 * - 1 <= arr[i] <= 500
 * * ==================================================================================================
 * 💡 THE CORE INTUITION (The "Aha!" Moment)
 * ==================================================================================================
 * Matrix multiplication is associative: A(BC) = (AB)C, but the cost of multiplication differs wildly
 * depending on where we place the parentheses.
 * * Multiplying a matrix of size $P \times Q$ with a matrix of size $Q \times R$ takes
 * exactly $P \cdot Q \cdot R$ scalar multiplications.
 * * We want to find the minimum cost to multiply matrices from index `i` to `j`.
 * To do this, we can try partitioning the sequence of matrices at every possible index `k`
 * (where $i \le k < j$).
 * * The total cost for a partition at `k` is:
 * 1. The minimum cost to multiply matrices from `i` to `k`.
 * 2. The minimum cost to multiply matrices from `k+1` to `j`.
 * 3. The cost to multiply the two resulting matrices together: $arr[i-1] \cdot arr[k] \cdot arr[j]$.
 * * Recurrence Relation:
 * $$ dp[i][j] = \min_{i \le k < j} \{ dp[i][k] + dp[k+1][j] + (arr[i-1] \cdot arr[k] \cdot arr[j]) \} $$
 * * ==================================================================================================
 * 🌳 CONCEPTUAL VISUALIZATION
 * ==================================================================================================
 * RECURSION TREE (For 4 matrices: A1, A2, A3, A4. Array indices 1 to 4)
 *
 *                                State: f(i, j)
 * *                                     f(1, 4)
 *                         /                  |                     \
 *                  k=1  /                   k=2                     \ k=3
 *                     /                      |                       \
 *              f(1,1)+f(2,4)          f(1,2)+f(3,4)            f(1,3)+f(4,4)
 *                  /  \                   / \                      /  \
 *              k=2/    \k=3           k=1/   \k=3              k=1/    \k=2
 *      f(2,2)+f(3,4) ...     f(1,1)+f(2,2) ...       f(1,1)+f(2,3) ...
 * *
 * * --------------------------------------------------------------------------------------------------
 * 📦 DP TABLE STATE TRANSITIONS (Tabulation)
 * The table is filled diagonally because to compute a chain of length $L$, you need the answers
 * for chains of length $< L$.
 * * For arr = [10, 20, 30, 40]: (Matrices A1: 10x20, A2: 20x30, A3: 30x40)
 * * dp[i][j] table:
 * j=1      j=2      j=3
 * i=1   0       6000     18000
 * i=2   -       0        24000
 * i=3   -       -        0
 * *
 * ==================================================================================================
 */

import java.util.Arrays;

public class MatrixChainMultiplicationMasterclass {

    public static void main(String[] args) {
        MatrixChainMultiplicationMasterclass solver = new MatrixChainMultiplicationMasterclass();

        int[] arr1 = {40, 20, 30, 10, 30};
        int[] arr2 = {10, 20, 30, 40, 30};

        System.out.println("Input Array 1: " + Arrays.toString(arr1));
        System.out.println("---------------------------------------------------------");
        System.out.println("1. Brute Force Recursion : " + solver.phase1_bruteForce(arr1));
        System.out.println("2. Top-Down Memoization  : " + solver.phase2_memoization(arr1));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(arr1));
        System.out.println("4. Space Optimization    : " + solver.phase4_spaceOptimization(arr1));

        System.out.println("\nInput Array 2: " + Arrays.toString(arr2));
        System.out.println("3. Bottom-Up Tabulation  : " + solver.phase3_tabulation(arr2));
    }

    /**
     * ==============================================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" Stage)
     * ==============================================================================================
     * Intuition:
     * To find the minimum cost to multiply matrices from index `i` to `j`, we place parentheses
     * at every possible position `k` between `i` and `j-1`. We calculate the cost for each partition
     * and return the minimum.
     * * Base Case: If `i == j`, we only have one matrix. Cost of multiplying one matrix is 0.
     * * Complexity Analysis:
     * - Time Complexity: $O(2^N)$ roughly. Specifically, the number of ways to parenthesize a chain
     * of length $N$ is the $(N-1)$-th Catalan number, which grows exponentially.
     * - Space Complexity: $O(N)$ auxiliary stack space for the depth of the recursion tree.
     */
    public int phase1_bruteForce(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int n = arr.length;
        // Matrices are conceptually 1-indexed up to n-1
        return solveRecursive(arr, 1, n - 1);
    }

    private int solveRecursive(int[] arr, int i, int j) {
        if (i == j) return 0; // Base case: 1 matrix

        int minCost = Integer.MAX_VALUE;

        // Partition at every index k from i to j-1
        for (int k = i; k < j; k++) {
            int cost = solveRecursive(arr, i, k)
                    + solveRecursive(arr, k + 1, j)
                    + (arr[i - 1] * arr[k] * arr[j]);

            minCost = Math.min(minCost, cost);
        }

        return minCost;
    }

    /**
     * ==============================================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" Stage)
     * ==============================================================================================
     * Intuition:
     * The brute force recursion repeatedly evaluates the same subproblems (e.g., `solve(1, 2)`).
     * We can cache the result of `solve(i, j)` in a 2D matrix `dp[i][j]`. If the value has already
     * been computed, we return it in $O(1)$ time.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^3)$. There are $O(N^2)$ states (pairs of i, j). For each state,
     * we run a loop of size up to $N$ to find `k`. $N^2 \cdot N = N^3$.
     * - Space Complexity: $O(N^2)$ for the DP matrix (heap) + $O(N)$ auxiliary stack space.
     */
    public int phase2_memoization(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int n = arr.length;
        int[][] dp = new int[n][n];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return solveMemo(arr, 1, n - 1, dp);
    }

    private int solveMemo(int[] arr, int i, int j, int[][] dp) {
        if (i == j) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        int minCost = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {
            int cost = solveMemo(arr, i, k, dp)
                    + solveMemo(arr, k + 1, j, dp)
                    + (arr[i - 1] * arr[k] * arr[j]);

            minCost = Math.min(minCost, cost);
        }

        return dp[i][j] = minCost;
    }

    /**
     * ==============================================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" Stage) - ✨ INDUSTRY STANDARD
     * ==============================================================================================
     * Intuition:
     * To drop the recursion stack overhead, we build the DP table iteratively.
     * In MCM, we cannot simply fill row by row. To compute a chain of length $L$, we need the
     * sub-chains of lengths $< L$. Therefore, we must loop based on the `length` of the chain,
     * starting from length 2 up to $N-1$.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^3)$. Three nested loops: `length`, `i` (start point), and `k` (partition).
     * - Space Complexity: $O(N^2)$. The 2D DP table.
     */
    public int phase3_tabulation(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int n = arr.length;
        int[][] dp = new int[n][n];

        // Base cases: dp[i][i] is initialized to 0 by default in Java.

        // Loop over chain lengths, from 2 to n-1
        for (int length = 2; length < n; length++) {
            // Loop over start index of the chain
            for (int i = 1; i < n - length + 1; i++) {
                // Determine the end index of the chain
                int j = i + length - 1;

                dp[i][j] = Integer.MAX_VALUE;

                // Try every partition k
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + (arr[i - 1] * arr[k] * arr[j]);
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[1][n - 1];
    }

    /**
     * ==============================================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" Stage)
     * ==============================================================================================
     * Intuition:
     * In many string/grid DP problems, we can optimize space to $O(N)$ by only storing the previous row.
     * However, the Matrix Chain Multiplication (Partition DP) pattern is fundamentally different.
     * To compute `dp[i][j]`, we need `dp[i][k]` (values in the same row) AND `dp[k+1][j]` (values in
     * the same column) for ALL $k$ between $i$ and $j$.
     * * Because the state relies on the entire triangular section of the 2D matrix, we CANNOT easily
     * reduce the space complexity below $O(N^2)$ without severely complicating the algorithm to an
     * impractical degree for interviews.
     * * Phase 3's $O(N^2)$ space remains the optimal and expected solution in coding interviews.
     */
    public int phase4_spaceOptimization(int[] arr) {
        return phase3_tabulation(arr);
    }

    /**
     * ==============================================================================================
     * PHASE 5: ALTERNATIVE APPROACHES
     * ==============================================================================================
     * 1. Hu-Shing Algorithm:
     * - There exists a highly complex algorithm introduced by T. C. Hu and M. T. Shing in 1982
     * that solves the Matrix Chain Multiplication problem in $O(N \log N)$ time.
     * - It achieves this by transforming the problem into finding a minimum weight triangulation
     * of a convex polygon.
     * - While theoretically fascinating, it is extremely convoluted to implement and is never
     * expected in a software engineering interview. The $O(N^3)$ dynamic programming approach
     * is the gold standard.
     */
}
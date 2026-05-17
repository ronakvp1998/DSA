package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: ASSIGN COOKIES (LeetCode 455)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Assume you are an awesome parent and want to give your children some cookies.
 * But, you should give each child at most one cookie.
 * * Each child 'i' has a greed factor g[i], which is the minimum size of a cookie that
 * the child will be content with; and each cookie 'j' has a size s[j].
 * If s[j] >= g[i], we can assign the cookie 'j' to the child 'i', and the child 'i' will be content.
 * * Your goal is to maximize the number of your content children and output the maximum number.
 *
 * EXAMPLE 1:
 * Input: g = [1,2,3], s = [1,1]
 * Output: 1
 * Explanation: Only the child with greed factor 1 can be satisfied by a cookie of size 1.
 *
 * EXAMPLE 2:
 * Input: g = [1,2], s = [1,2,3]
 * Output: 2
 * Explanation: We have cookies of size 1, 2, 3. We can satisfy child 1 (greed 1) with cookie 1,
 * and child 2 (greed 2) with cookie 2.
 *
 * KEY INSIGHT (GREEDY STRATEGY):
 * To maximize the number of content children, we should:
 * 1. Give the smallest sufficient cookie to the child with the smallest greed factor.
 * 2. This saves larger cookies for children with higher greed factors.
 * *
 * ==================================================================================================
 */
public class AssignCookies {

    public static void main(String[] args) {
        int[] g = {1, 2, 3}; // Greed factors
        int[] s = {1, 1};    // Cookie sizes

        // CRITICAL STEP: Both arrays MUST be sorted for Greedy (and DP) to work efficiently
        Arrays.sort(g);
        Arrays.sort(s);

        System.out.println("Children (Sorted): " + Arrays.toString(g));
        System.out.println("Cookies  (Sorted): " + Arrays.toString(s));
        System.out.println("--------------------------------------------------");

        // 1. Greedy Approach (The Optimal Solution)
        System.out.println("1. Greedy (Optimal) : " + solveGreedy(g, s));

        // 2. Recursive Approach (Educational only)
        System.out.println("2. Recursion        : " + solveRecursive(g, s, 0, 0));

        // 3. Memoization Approach (Educational only)
        int[][] dp = new int[g.length][s.length];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("3. Memoization      : " + solveMemoization(g, s, 0, 0, dp));

        // 4. Tabulation Approach (Educational only)
        System.out.println("4. Tabulation       : " + solveTabulation(g, s));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: GREEDY (OPTIMAL SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. Sort both greed factors (g) and cookie sizes (s).
     * 2. Use two pointers: 'i' for children, 'j' for cookies.
     * 3. Iterate through cookies:
     * - If cookie 'j' satisfies child 'i' (s[j] >= g[i]):
     * Content the child! Move to the next child (i++) and next cookie (j++).
     * - If not (cookie too small):
     * Discard this cookie and try the next larger one (j++).
     * * WHY THIS WORKS:
     * Assigning a cookie larger than necessary to a small-greed child is wasteful.
     * We always try to match the "cheapest" possible resource to the current need.
     *
     * COMPLEXITY:
     * - Time: O(N log N + M log M) due to sorting. The linear scan is O(N + M).
     * - Space: O(1) (excluding sort space).
     */
    private static int solveGreedy(int[] g, int[] s) {
        int i = 0; // Child pointer
        int j = 0; // Cookie pointer

        while (i < g.length && j < s.length) {
            // Check if current cookie 'j' can satisfy current child 'i'
            if (s[j] >= g[i]) {
                // Success: Assign cookie, move to next child
                i++;
            }
            // Regardless of success, move to next cookie.
            // If valid: we used it. If invalid: it's too small for anyone else (since sorted), so discard.
            j++;
        }

        // 'i' represents the count of children satisfied
        return i;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: RECURSION (BRUTE FORCE / EDUCATIONAL)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try to find the optimal matching using recursion.
     * At each step (child i, cookie j):
     * - If s[j] >= g[i]: We can either Assign OR Skip this cookie (standard Knapsack style).
     * - If s[j] < g[i]: We MUST skip this cookie.
     * * NOTE: This is overkill O(2^M) logic for a problem solvable by O(M).
     */
    private static int solveRecursive(int[] g, int[] s, int i, int j) {
        // Base case: No more children or no more cookies
        if (i == g.length || j == s.length) return 0;

        if (s[j] >= g[i]) {
            // Option A: Assign cookie 'j' to child 'i'
            int assign = 1 + solveRecursive(g, s, i + 1, j + 1);

            // Option B: Skip cookie 'j' (maybe save it for someone else? - redundant in sorted case)
            int skip = solveRecursive(g, s, i, j + 1);

            return Math.max(assign, skip);
        } else {
            // Current cookie too small, skip it
            return solveRecursive(g, s, i, j + 1);
        }
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Caches the result of (childIndex, cookieIndex) to avoid re-computation.
     * * COMPLEXITY:
     * - Time: O(N * M) - Check every child against every cookie.
     * - Space: O(N * M) - DP Table.
     */
    private static int solveMemoization(int[] g, int[] s, int i, int j, int[][] dp) {
        if (i == g.length || j == s.length) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (s[j] >= g[i]) {
            int assign = 1 + solveMemoization(g, s, i + 1, j + 1, dp);
            int skip = solveMemoization(g, s, i, j + 1, dp);
            return dp[i][j] = Math.max(assign, skip);
        } else {
            return dp[i][j] = solveMemoization(g, s, i, j + 1, dp);
        }
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Build the solution from the end of the arrays towards the beginning.
     * dp[i][j] represents max satisfied children considering children i...end and cookies j...end.
     * * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M)
     */
    private static int solveTabulation(int[] g, int[] s) {
        int n = g.length;
        int m = s.length;

        int[][] dp = new int[n + 1][m + 1];

        // Fill from bottom-right to top-left
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (s[j] >= g[i]) {
                    // Max of (Assign, Skip)
                    dp[i][j] = Math.max(1 + dp[i + 1][j + 1], dp[i][j + 1]);
                } else {
                    // Skip only
                    dp[i][j] = dp[i][j + 1];
                }
            }
        }

        return dp[0][0];
    }

    // Space Optimization
    private static int spaceOptimized(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0; // pointer for child
        int j = 0; // pointer for cookies
        int count = 0;

        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                count++;
                i++;
                j++;
            } else {
                j++;
            }
        }

        return count;
    }

}
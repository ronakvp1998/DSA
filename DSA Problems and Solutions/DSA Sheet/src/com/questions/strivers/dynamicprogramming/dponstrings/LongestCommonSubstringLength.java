package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: LONGEST COMMON SUBSTRING (Striver DP-27)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given two strings 's1' and 's2', find the length of the longest common substring.
 * A substring is a contiguous sequence of characters within the string.
 *
 * EXAMPLE:
 * Input: s1 = "abcjklp", s2 = "acjpk"
 * Output: 3 (The substring is "cjp")
 *
 * KEY DIFFERENCE FROM SUBSEQUENCE:
 * - Subsequence: Gaps are allowed (acjp is valid from abcjklp).
 * - Substring: No gaps allowed (must be consecutive).
 *
 * MEMOIZATION LOGIC (Optimization):
 * Directly memoizing the 3-parameter recursion (i, j, count) is inefficient (3D state).
 * Instead, we compute: "What is the longest common suffix ending at indices i and j?"
 * - If chars match: 1 + solve(i-1, j-1)
 * - If chars mismatch: 0
 * We then iterate over all (i, j) to find the maximum value.
 * ==================================================================================================
 */
public class LongestCommonSubstringLength {

    public static void main(String[] args) {
        String s1 = "abcjklp";
        String s2 = "acjpk";

        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach (Educational)
        System.out.println("1. Recursion       : " + lcsRecursive(s1, s2, s1.length() - 1, s2.length() - 1, 0));

        // 2. Memoization Approach (Optimized Recursion)
        System.out.println("2. Memoization     : " + lcsMemoization(s1, s2));

        // 3. Tabulation Approach (Standard Solution)
        System.out.println("3. Tabulation      : " + lcsTabulation(s1, s2));

        // 4. Space Optimized Approach (Best Solution)
        System.out.println("4. Space Optimized : " + lcsSpaceOptimized(s1, s2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * Time: O(3^(N+M)) -> Very slow due to 3 branches.
     * Space: O(N+M) -> Stack depth.
     */
    private static int lcsRecursive(String s1, String s2, int i, int j, int count) {
        if (i < 0 || j < 0) {
            return count;
        }

        int extendCount = count;
        if (s1.charAt(i) == s2.charAt(j)) {
            extendCount = lcsRecursive(s1, s2, i - 1, j - 1, count + 1);
        }

        int moveI = lcsRecursive(s1, s2, i - 1, j, 0);
        int moveJ = lcsRecursive(s1, s2, i, j - 1, 0);

        return Math.max(extendCount, Math.max(moveI, moveJ));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We calculate the length of common substring ending at EVERY index (i, j).
     * State: memo[i][j] = length of common suffix ending at s1[i] and s2[j].
     *
     * COMPLEXITY:
     * - Time: O(N * M) -> We visit every state once.
     * - Space: O(N * M) -> Memoization table + Recursion stack.
     */
    private static int lcsMemoization(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] memo = new int[n][m];

        // Initialize memo table with -1
        for (int[] row : memo) Arrays.fill(row, -1);

        int maxLen = 0;

        // CRITICAL STEP: Unlike standard LCS, we must call the recursive function
        // for EVERY (i, j) pair to find where the longest substring ends.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxLen = Math.max(maxLen, solveMemo(i, j, s1, s2, memo));
            }
        }
        return maxLen;
    }

    // Helper function returns length of substring ENDING at i, j
    private static int solveMemo(int i, int j, String s1, String s2, int[][] memo) {
        // Base Case
        if (i < 0 || j < 0) return 0;

        // Return cached value
        if (memo[i][j] != -1) return memo[i][j];

        int val = 0;
        if (s1.charAt(i) == s2.charAt(j)) {
            // Match: 1 + previous diagonal result
            val = 1 + solveMemo(i - 1, j - 1, s1, s2, memo);
        } else {
            // Mismatch: Streak is broken, length is 0
            val = 0;
        }

        return memo[i][j] = val;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP) - STANDARD
     * ----------------------------------------------------------------------
     * Time: O(N * M)
     * Space: O(N * M)
     */
    private static int lcsTabulation(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        int ans = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    ans = Math.max(ans, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return ans;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * Time: O(N * M)
     * Space: O(M)
     */
    private static int lcsSpaceOptimized(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[] prev = new int[m + 1];
        int ans = 0;

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                    ans = Math.max(ans, curr[j]);
                } else {
                    curr[j] = 0;
                }
            }
            prev = curr;
        }
        return ans;
    }
}
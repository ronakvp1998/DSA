package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: DELETE OPERATION FOR TWO STRINGS (LeetCode 583)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given two strings 'word1' and 'word2', return the minimum number of steps required
 * to make 'word1' and 'word2' the same.
 * In one step, you can delete exactly one character in either string.
 *
 * EXAMPLE 1:
 * Input: word1 = "sea", word2 = "eat"
 * Output: 2
 * Explanation:
 * 1. Delete 's' from "sea" -> "ea"
 * 2. Delete 't' from "eat" -> "ea"
 * Total deletions = 2.
 *
 * KEY INSIGHT (REDUCTION TO LCS):
 * To make two strings identical with the *minimum* deletions, we want to keep the
 * *maximum* number of characters that are common to both strings and appear in the same relative order.
 *
 * This "maximum set of common characters" is simply the **Longest Common Subsequence (LCS)**.
 *
 * FORMULA:
 * Min Deletions = (Characters to remove from word1) + (Characters to remove from word2)
 * Min Deletions = (word1.length - LCS) + (word2.length - LCS)
 * Min Deletions = word1.length + word2.length - 2 * LCS
 *
 * ==================================================================================================
 */
public class DeleteOperationTwoStrings {

    public static void main(String[] args) {
        String word1 = "leetcode";
        String word2 = "etco";

        System.out.println("Word 1: " + word1);
        System.out.println("Word 2: " + word2);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + minDistanceRecursive(word1, word2));

        // 2. Memoization Approach
        System.out.println("2. Memoization     : " + minDistanceMemoization(word1, word2));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + minDistanceTabulation(word1, word2));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + minDistanceSpaceOptimized(word1, word2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE VIA LCS)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Calculate LCS length recursively, then apply the formula.
     *
     * COMPLEXITY:
     * - Time: O(2^(N+M))
     * - Space: O(N+M) Stack
     */
    private static int minDistanceRecursive(String s1, String s2) {
        int lcsLength = lcsRecHelper(s1, s2, s1.length() - 1, s2.length() - 1);
        return s1.length() + s2.length() - 2 * lcsLength;
    }

    private static int lcsRecHelper(String s1, String s2, int i, int j) {
        if (i < 0 || j < 0) return 0;

        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsRecHelper(s1, s2, i - 1, j - 1);
        }
        return Math.max(lcsRecHelper(s1, s2, i - 1, j), lcsRecHelper(s1, s2, i, j - 1));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache LCS results to avoid re-computation.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M) + Stack
     */
    private static int minDistanceMemoization(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, -1);

        int lcsLength = lcsMemoHelper(s1, s2, n - 1, m - 1, dp);
        return n + m - 2 * lcsLength;
    }

    private static int lcsMemoHelper(String s1, String s2, int i, int j, int[][] dp) {
        if (i < 0 || j < 0) return 0;
        if (dp[i][j] != -1) return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] = 1 + lcsMemoHelper(s1, s2, i - 1, j - 1, dp);
        }
        return dp[i][j] = Math.max(
                lcsMemoHelper(s1, s2, i - 1, j, dp),
                lcsMemoHelper(s1, s2, i, j - 1, dp)
        );
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Compute LCS using standard 2D table iteration.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M)
     */
    private static int minDistanceTabulation(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        // Fill DP Table for LCS
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int lcsLength = dp[n][m];
        return n + m - 2 * lcsLength;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Use two 1D arrays ('prev' and 'curr') to compute LCS length.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(M)
     */
    private static int minDistanceSpaceOptimized(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[] prev = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            prev = curr;
        }

        int lcsLength = prev[m];
        return n + m - 2 * lcsLength;
    }
}
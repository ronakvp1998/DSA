package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: PRINT LONGEST COMMON SUBSTRING (All 4 Approaches)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given two strings 's1' and 's2', find and print the Longest Common Substring.
 * A substring must be CONTIGUOUS (no gaps allowed).
 *
 * EXAMPLE:
 * Input: s1 = "abcjklp", s2 = "acjpk"
 * Output: "cj" (or "kp")
 * Explanation: "cjp" is a Subsequence, not a Substring.
 *
 * STRATEGY FOR PRINTING:
 * Unlike "Subsequence" problems (where the answer is at dp[n][m]), a "Substring" can end ANYWHERE.
 * To print it, we must track:
 * 1. maxLen: The length of the longest substring found so far.
 * 2. endIndex: The ending index in 's1' where this maxLen occurs.
 * 3. Final Step: Extract s1.substring(endIndex - maxLen, endIndex).
 * ==================================================================================================
 */
public class LongestCommonSubstringPrint {

    // Global variables to track the result for Recursion & Memoization
    // Since recursion returns length of suffix ending at (i,j), we need globals to track the overall max.
    static int globalMaxLen = 0;
    static int globalEndIndex = -1;

    public static void main(String[] args) {
        String s1 = "abcjklp";
        String s2 = "acjpk";

        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        resetGlobals();
        solveRecursiveWrapper(s1, s2);
        System.out.println("1. Recursion       : " + getResultString(s1));

        // 2. Memoization Approach
        resetGlobals();
        solveMemoizationWrapper(s1, s2);
        System.out.println("2. Memoization     : " + getResultString(s1));

        // 3. Tabulation Approach (Standard)
        System.out.println("3. Tabulation      : " + solveTabulation(s1, s2));

        // 4. Space Optimized Approach (Best)
        System.out.println("4. Space Optimized : " + solveSpaceOptimized(s1, s2));
    }

    // Helper to reset static variables
    private static void resetGlobals() {
        globalMaxLen = 0;
        globalEndIndex = -1;
    }

    // Helper to extract the substring
    private static String getResultString(String s1) {
        if (globalMaxLen == 0) return "";
        // globalEndIndex is 1-based (from DP logic).
        // Java substring is [start, end).
        return s1.substring(globalEndIndex - globalMaxLen, globalEndIndex);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To find the longest substring, we must check the common suffix length
     * ending at EVERY pair of indices (i, j).
     *
     * Complexity: O(N * M * min(N,M)) -> Extremely Slow
     */
    private static void solveRecursiveWrapper(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        // Iterate over all possible ending positions to find the global max
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int len = recursiveHelper(i, j, s1, s2);

                if (len > globalMaxLen) {
                    globalMaxLen = len;
                    globalEndIndex = i;
                }
            }
        }
    }

    // Returns length of common substring strictly ending at i and j
    private static int recursiveHelper(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return 0;

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + recursiveHelper(i - 1, j - 1, s1, s2);
        }
        return 0; // Mismatch breaks the consecutive chain
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but memoize results for state (i, j).
     *
     * Complexity: O(N * M)
     */
    private static void solveMemoizationWrapper(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] memo = new int[n + 1][m + 1];

        for (int[] row : memo) Arrays.fill(row, -1);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int len = memoHelper(i, j, s1, s2, memo);

                if (len > globalMaxLen) {
                    globalMaxLen = len;
                    globalEndIndex = i;
                }
            }
        }
    }

    private static int memoHelper(int i, int j, String s1, String s2, int[][] memo) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != -1) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + memoHelper(i - 1, j - 1, s1, s2, memo);
        }
        return memo[i][j] = 0;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP) - RECOMMENDED
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][j] = length of common substring ending at s1[i-1] and s2[j-1].
     * We capture 'endIndex' whenever we update the maximum length.
     *
     * Complexity: O(N * M)
     */
    private static String solveTabulation(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        int maxLen = 0;
        int endIndex = -1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];

                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        endIndex = i;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        if (maxLen == 0) return "";
        return s1.substring(endIndex - maxLen, endIndex);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (1D ARRAY)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need 'prev' row to compute 'curr'.
     * We capture 'endIndex' from the outer loop 'i' when a new max is found.
     *
     * Complexity: O(N * M) Time, O(M) Space
     */
    private static String solveSpaceOptimized(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] prev = new int[m + 1];
        int maxLen = 0;
        int endIndex = -1;

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];

                    if (curr[j] > maxLen) {
                        maxLen = curr[j];
                        endIndex = i;
                    }
                } else {
                    curr[j] = 0;
                }
            }
            prev = curr;
        }

        if (maxLen == 0) return "";
        return s1.substring(endIndex - maxLen, endIndex);
    }
}
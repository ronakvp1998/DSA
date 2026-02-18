package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ==================================================================================================
 * PROBLEM: PRINT LONGEST COMMON SUBSEQUENCE (Striver DP-26)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given two strings 's1' and 's2', find and print the Longest Common Subsequence (LCS).
 * A subsequence is a sequence that can be derived from another sequence by deleting some or no
 * elements without changing the order of the remaining elements.
 *
 * EXAMPLE 1:
 * Input: s1 = "abcde", s2 = "bdgek"
 * Output: "bde"
 *
 * EXAMPLE 2:
 * Input: s1 = "ferret", s2 = "herbert"
 * Output: "eret"
 *
 * APPROACH STRATEGY:
 * 1. The most efficient way to PRINT the LCS is using **Tabulation** (Bottom-Up DP).
 * 2. We first fill a 2D table where dp[i][j] stores the *length* of LCS for s1[0...i] and s2[0...j].
 * 3. Once the table is filled, we start from the bottom-right corner and **Backtrack**.
 * - If characters match: It's part of LCS. Add to string, move Diagonal.
 * - If mismatch: Move to the cell (Up or Left) that has the greater value.
 * ==================================================================================================
 */
public class LongestCommonSubsequencePrint {

    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "bdgek";

        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("--------------------------------------------------");

        // 1. Tabulation with Backtracking (Best for Printing)
        System.out.println("1. Tabulation (Print) : " + printLCS_Tabulation(s1, s2));

        // 2. Space Optimized (Length Only)
        System.out.println("2. Space Opt (Len)    : " + lcsLength_SpaceOptimized(s1, s2));

        // Note: Recursive/Memoization returning Strings is generally discouraged in interviews
        // due to high time complexity of String concatenation O(N^3).
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: TABULATION + BACKTRACKING (BEST FOR PRINTING)
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. Build the standard LCS DP table.
     * 2. Start pointers i = n, j = m.
     * 3. Loop until i > 0 and j > 0:
     * - If s1[i-1] == s2[j-1]: The char is part of LCS. Append to result. Decr i and j.
     * - Else if dp[i-1][j] > dp[i][j-1]: The max value came from Up. Decr i.
     * - Else: The max value came from Left. Decr j.
     *
     *
     *
     * COMPLEXITY:
     * - Time: O(N * M) to build table + O(N + M) to backtrack.
     * - Space: O(N * M) for the DP table.
     */
    private static String printLCS_Tabulation(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        // Step 1: Fill DP Table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Check if characters match (adjust for 0-based index)
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Backtrack to reconstruct String
        StringBuilder sb = new StringBuilder();
        int i = n, j = m;

        while (i > 0 && j > 0) {
            // Characters Match -> Part of LCS
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                sb.append(s1.charAt(i - 1));
                i--;
                j--;
            }
            // Mismatch -> Move in direction of larger value
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // Move Up
            } else {
                j--; // Move Left
            }
        }

        // Since we backtracked from end, reverse the string
        return sb.reverse().toString();
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: SPACE OPTIMIZED (LENGTH ONLY)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We use two 1D arrays (prev and curr) to save space.
     * NOTE: This approach CANNOT easily print the LCS string because we lose
     * the path information required for backtracking. To print using O(N) space,
     * complex algorithms like Hirschberg's Algorithm are required (rare in interviews).
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(M)
     */
    private static int lcsLength_SpaceOptimized(String s1, String s2) {
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
            prev = curr; // Move curr to prev
        }

        return prev[m];
    }

    /**
     * ----------------------------------------------------------------------
     * NOTE ON RECURSIVE / MEMOIZATION FOR PRINTING
     * ----------------------------------------------------------------------
     * Why is it not recommended?
     *
     * private static String lcsRecursive(String s1, String s2, int i, int j) { ... }
     *
     * 1. String Immutability: In Java, Strings are immutable. Every time you do
     * `return str1 + char`, a new String object is created and copied.
     * 2. Complexity Explosion: The time complexity degrades significantly due to
     * O(N) copy operations at every step of the recursion.
     * 3. Stack Overflow: Deep recursion for large strings will cause stack overflow.
     *
     * The Tabulation + Backtracking approach is consistently O(N*M) and is
     * the standard expected answer.
     */
}
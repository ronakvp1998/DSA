package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: LONGEST COMMON SUBSEQUENCE LENGTH (LeetCode 1143 / Striver DP-26)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given two strings 'text1' and 'text2', return the length of their longest common subsequence.
 * If there is no common subsequence, return 0.
 *
 * A subsequence of a string is a new string generated from the original string with some characters
 * (can be none) deleted without changing the relative order of the remaining characters.
 *
 * EXAMPLE 1:
 * Input: text1 = "abcde", text2 = "ace"
 * Output: 3
 * Explanation: The longest common subsequence is "ace" and its length is 3.
 *
 * EXAMPLE 2:
 * Input: text1 = "abc", text2 = "abc"
 * Output: 3
 * Explanation: The longest common subsequence is "abc" and its length is 3.
 *
 * EXAMPLE 3:
 * Input: text1 = "abc", text2 = "def"
 * Output: 0
 * Explanation: There is no such common subsequence, so the result is 0.
 *
 * KEY INSIGHT:
 * Compare characters at the current indices (i, j):
 * 1. MATCH: If text1[i] == text2[j], this character is part of the LCS.
 * Add 1 to the result and move both pointers back (i-1, j-1).
 * 2. NO MATCH: We have two choices:
 * a) Skip the character in text1 (move i-1).
 * b) Skip the character in text2 (move j-1).
 * We take the Maximum of these two choices.
 * ==================================================================================================
 */
public class LongestCommonSubsequenceLength {

    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "ace";

        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("--------------------------------------------------");

        // 1. Recursive
        System.out.println("1. Recursion       : " + lcsRecursive(s1, s2, s1.length() - 1, s2.length() - 1));

        // 2. Memoization
        int m = s1.length();
        int n = s2.length();
        int[][] dpMemo = new int[m][n];
        for (int[] row : dpMemo) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + lcsMemoization(s1, s2, m - 1, n - 1, dpMemo));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + lcsTabulation(s1, s2));

        // 4. Space Optimization
        System.out.println("4. Space Optimized : " + lcsSpaceOptimized(s1, s2));

        // Bonus: Actually Printing the String
        System.out.println("--------------------------------------------------");
        System.out.println("Actual LCS String  : " + printLCS(s1, s2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Iterate from the end of strings. Compare characters.
     * Match -> 1 + recurse(both - 1).
     * No Match -> Max(recurse(i-1), recurse(j-1)).
     *
     * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Exponential (Two branches at every mismatch).
     * - Space: O(M+N) -> Recursion stack.
     */
    private static int lcsRecursive(String s1, String s2, int index1, int index2) {
        // Base Case: If either string is exhausted (index < 0), length is 0.
        if (index1 < 0 || index2 < 0) return 0;

        // Match Logic
        if (s1.charAt(index1) == s2.charAt(index2)) {
            return 1 + lcsRecursive(s1, s2, index1 - 1, index2 - 1);
        }

        // No Match Logic
        return Math.max(
                lcsRecursive(s1, s2, index1 - 1, index2), // Skip s1 char
                lcsRecursive(s1, s2, index1, index2 - 1)  // Skip s2 char
        );
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but store result in dp[index1][index2].
     *
     * COMPLEXITY:
     * - Time: O(M * N) -> Total unique states.
     * - Space: O(M * N) + Stack.
     */
    private static int lcsMemoization(String s1, String s2, int i, int j, int[][] dp) {
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        // Match
        if (s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] = 1 + lcsMemoization(s1, s2, i - 1, j - 1, dp);
        }

        // No Match
        return dp[i][j] = Math.max(
                lcsMemoization(s1, s2, i - 1, j, dp),
                lcsMemoization(s1, s2, i, j - 1, dp)
        );
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Use a 2D table `dp[m+1][n+1]`.
     * Shifting Indices:
     * dp[i][j] stores LCS length for s1[0...i-1] and s2[0...j-1].
     * This allows row 0 and col 0 to act as "Empty String" base cases.
     *
     * * WHY SHIFT INDICES?
     *      * 1. Recursive Base Case: When i < 0 or j < 0, return 0.
     *      * 2. Array Limitation: dp[-1][-1] is invalid in Java.
     *      * 3. Solution: We treat dp index '0' as the "Base Case" (Empty String).
     *      * Therefore, dp index '1' corresponds to String index '0'.
     *      * dp index 'i' corresponds to String index 'i-1'.
     *
     * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(M * N)
     */
    private static int lcsTabulation(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base case is implicitly 0 for row 0 and col 0 (Java default).

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Check if characters match
                // NOTE: Use i-1 and j-1 to access string characters (0-indexed)
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1]; // Move diagonal
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // Max of Top or Left
                }
            }
        }
        return dp[m][n];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the previous row (`prev`) to calculate the current row (`curr`).
     *
     * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Only two 1D arrays.
     */
    private static int lcsSpaceOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Move current row to prev for the next iteration
            prev = curr.clone();
        }

        return prev[n];
    }

    /**
     * ----------------------------------------------------------------------
     * BONUS: PRINT THE LCS STRING
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. Fill the DP Table (Tabulation).
     * 2. Start from bottom-right (dp[m][n]).
     * 3. Backtrack to reconstruct the path:
     * - If chars match: It was part of LCS. Add to string, move diagonal up-left.
     * - If chars don't match: Move in the direction of the larger value (Up or Left).
     */
    private static String printLCS(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Fill DP Table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Backtrack
        int i = m, j = n;
        StringBuilder sb = new StringBuilder();

        while (i > 0 && j > 0) {
            // If characters match, it is part of LCS
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                sb.append(s1.charAt(i - 1));
                i--;
                j--;
            }
            // If not, move to the cell with the larger value
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // Move Up
            } else {
                j--; // Move Left
            }
        }

        return sb.reverse().toString();
    }
}
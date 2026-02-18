package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: LONGEST PALINDROMIC SUBSEQUENCE (LeetCode 516)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given a string s, find the longest palindromic subsequence's length in s.
 * A subsequence is a sequence that can be derived from another sequence by deleting some or no
 * elements without changing the order of the remaining elements.
 *
 * EXAMPLE 1:
 * Input: s = "bbbab"
 * Output: 4
 * Explanation: One possible longest palindromic subsequence is "bbbb".
 *
 * EXAMPLE 2:
 * Input: s = "cbbd"
 * Output: 2
 * Explanation: One possible longest palindromic subsequence is "bb".
 *
 * ALTERNATIVE APPROACH (LCS):
 * This problem is equivalent to finding the Longest Common Subsequence (LCS) between:
 * 1. The original string 's'.
 * 2. The reversed string 'reverse(s)'.
 * However, the approach below (Interval DP) is more direct and specific to palindromes.
 * ==================================================================================================
 */
public class LongestPalindromicSubsequence {

    public static void main(String[] args) {
        String str = "bbbab";
        int n = str.length();

        System.out.println("String: " + str);
        System.out.println("--------------------------------------------------");

        // 1. Recursion
        System.out.println("1. Recursion       : " + lpsRecursive(str, 0, n - 1));

        // 2. Memoization
        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + lpsMemoization(str, 0, n - 1, dp));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + lpsTabulation(str));

        // 4. Space Optimization
        System.out.println("4. Space Optimized : " + lpsSpaceOptimized(str));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Use two pointers, 'left' and 'right'.
     * 1. Match: If s.charAt(left) == s.charAt(right), they contribute '2' to the length.
     * Then move both pointers inward (left+1, right-1).
     * 2. Mismatch: We can't include both. Try skipping 'left' OR skipping 'right'.
     * Take the maximum of the two results.
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Exponential.
     * - Space: O(N) -> Recursion stack.
     */
    private static int lpsRecursive(String s, int left, int right) {
        // Base Case 1: Pointers crossed (Invalid)
        if (left > right) return 0;

        // Base Case 2: Pointers at same char (Center of odd length palindrome)
        if (left == right) return 1;

        // Case A: Characters match
        if (s.charAt(left) == s.charAt(right)) {
            return 2 + lpsRecursive(s, left + 1, right - 1);
        }

        // Case B: Characters do not match
        return Math.max(
                lpsRecursive(s, left + 1, right), // Skip left
                lpsRecursive(s, left, right - 1)  // Skip right
        );
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same logic as recursion, but store the result for state [left][right].
     *
     * COMPLEXITY:
     * - Time: O(N^2) -> Unique states are (left, right).
     * - Space: O(N^2) -> DP Table.
     */
    private static int lpsMemoization(String s, int left, int right, int[][] dp) {
        if (left > right) return 0;
        if (left == right) return 1;

        if (dp[left][right] != -1) return dp[left][right];

        if (s.charAt(left) == s.charAt(right)) {
            dp[left][right] = 2 + lpsMemoization(s, left + 1, right - 1, dp);
        } else {
            dp[left][right] = Math.max(
                    lpsMemoization(s, left + 1, right, dp),
                    lpsMemoization(s, left, right - 1, dp)
            );
        }
        return dp[left][right];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][j] represents the LPS length of substring s[i...j].
     * We need to compute values for smaller substrings before larger ones.
     * Iteration:
     * - 'i' (start index) goes from n-1 down to 0.
     * - 'j' (end index) goes from i to n-1.
     *
     *
     *
     * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N^2)
     */
    private static int lpsTabulation(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // Fill table
        for (int i = n - 1; i >= 0; i--) {
            // Base Case: Single character is always a palindrome of length 1
            dp[i][i] = 1;

            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    // Match: 2 + inner part (down-left in DP table)
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    // Mismatch: Max of (skip i) or (skip j)
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        // The answer is the entire string s[0...n-1]
        return dp[0][n - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Looking at Tabulation:
     * - dp[i][j] depends on dp[i+1][...] (Next Row / Previous Iteration).
     * - We can reduce the 2D array to two 1D arrays: 'curr' (row i) and 'prev' (row i+1).
     *
     * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N) -> Only two rows required.
     */
    private static int lpsSpaceOptimized(String s) {
        int n = s.length();
        int[] prev = new int[n];
        int[] curr = new int[n];

        // Iterate backwards like Tabulation
        for (int i = n - 1; i >= 0; i--) {
            curr[i] = 1; // Base case: substring s[i...i] has length 1

            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    // Match: 2 + value from previous row, previous column
                    curr[j] = 2 + prev[j - 1];
                } else {
                    // Mismatch: Max of (prev row, same col) or (curr row, prev col)
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Move current row to previous for next iteration
            prev = curr.clone();
        }

        return prev[n - 1];
    }
}
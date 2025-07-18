package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

public class LongestPalindromicSubsequence {

    // Function to find the LPS recursively
    public static int longestPalindromicSubsequence(String str) {
        return lpsHelper(str, 0, str.length() - 1);
    }

    /**
     * Helper function to find the length of the longest palindromic subsequence
     * between indices left and right in the string.
     */
    private static int lpsHelper(String str, int left, int right) {
        // 🔁 Base Case 1: Invalid range (left crossed right)
        if (left > right) {
            return 0;
        }

        // 🔁 Base Case 2: Single character is always a palindrome of length 1
        if (left == right) {
            return 1;
        }

        // ✅ Case 1: Characters match → include both and solve for inner substring
        if (str.charAt(left) == str.charAt(right)) {
            return 2 + lpsHelper(str, left + 1, right - 1);
        }

        // ❌ Case 2: Characters don’t match → try both options and take max
        int skipLeft = lpsHelper(str, left + 1, right);     // Skip the left character
        int skipRight = lpsHelper(str, left, right - 1);    // Skip the right character

        return Math.max(skipLeft, skipRight);
    }

    // Time Complexity: O(2^n)
    // Space Complexity: O(n) → Recursion stack space

    public static int lpsMemoization(String s) {
        int n = s.length();
        // dp[i][j] will store the LPS of substring s[i..j]
        int[][] dp = new int[n][n];

        // Initialize all values with -1 (uncomputed)
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return lpsHelper(s, 0, n - 1, dp);
    }

    private static int lpsHelper(String s, int i, int j, int[][] dp) {
        if (i > j) return 0;
        if (i == j) return 1;

        // If already computed
        if (dp[i][j] != -1) return dp[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            // Characters match → extend the palindrome
            dp[i][j] = 2 + lpsHelper(s, i + 1, j - 1, dp);
        } else {
            // Characters don’t match → skip one from either end
            dp[i][j] = Math.max(lpsHelper(s, i + 1, j, dp), lpsHelper(s, i, j - 1, dp));
        }

        return dp[i][j];
    }

// Time Complexity: O(n^2) → Every i-j pair is computed once
// Space Complexity: O(n^2) + O(n) → DP + Recursion stack

    public static int lpsTabulation(String s) {
        int n = s.length();

        // dp[i][j] = length of LPS from index i to j
        int[][] dp = new int[n][n];

        // All substrings of length 1 are palindromes of length 1
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // Build up the table
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    if (len == 2)
                        dp[i][j] = 2;
                    else
                        dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        // Final answer is LPS from index 0 to n-1
        return dp[0][n - 1];
    }

// Time Complexity: O(n^2)
// Space Complexity: O(n^2)
public static int lpsSpaceOptimized(String s) {
    int n = s.length();
    int[] prev = new int[n];
    int[] curr = new int[n];

    for (int i = n - 1; i >= 0; i--) {
        curr[i] = 1; // single char is always palindrome

        for (int j = i + 1; j < n; j++) {
            if (s.charAt(i) == s.charAt(j)) {
                curr[j] = 2 + prev[j - 1];
            } else {
                curr[j] = Math.max(prev[j], curr[j - 1]);
            }
        }

        // Move current row to previous for next iteration
        prev = curr.clone();
    }

    return prev[n - 1];
}

// Time Complexity: O(n^2)
// Space Complexity: O(n)

    public static void main(String[] args) {
        String str = "bbbab";
        int result = longestPalindromicSubsequence(str);
        System.out.println("Length of Longest Palindromic Subsequence: " + result);
        System.out.println("Memoization: " + lpsMemoization(str));
        System.out.println("Tabulation: " + lpsTabulation(str));
        System.out.println("Space Optimized: " + lpsSpaceOptimized(str));
    }
}

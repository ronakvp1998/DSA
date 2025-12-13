package com.questions.strivers.dynamicprogramming.dponstrings;

//Problem Statement: Introduction to DP on Strings - Longest Common Subsequence
//In the coming articles, we will discuss problems related to ‘Dynamic Programming on Strings’.
//We will discuss the problem of ‘Longest Common Subsequence’ in this article. Before proceeding further,
//let us understand what is the “Longest Common Subsequence”, or rather what is a “subsequence”?
//A subsequence of a string is a list of characters of the string where some characters are deleted
//( or not deleted at all) and they should be in the same order in the subsequence as in the original string.
//For eg:
//Strings like “cab”,” bc” will not be called as a subsequence of “abc” as the characters are not coming in the same order.
//Note: For a string of length n, the number of subsequences will be 2n.
//Now we will look at “Longest Common Subsequence”. The longest Common Subsequence is defined for two strings.
//It is the common subsequence that has the greatest length.

public class LongestCommonSubsequence {

    // 1. Recursive Approach
    // Time Complexity: O(2^(m + n)) -> Exponential due to overlapping subproblems
    // Space Complexity: O(m + n) -> Stack space due to recursion depth
    private static int lcsRecursive(String str1, String str2, int index1, int index2) {
        // Base Case: If we have reached the beginning of either string, there is no LCS
        if (index1 < 0 || index2 < 0) return 0;

        // If characters at current indices match, include it in LCS and move diagonally
        if (str1.charAt(index1) == str2.charAt(index2)) {
            // Match found: increase LCS length by 1, and move to previous characters
            return 1 + lcsRecursive(str1, str2, index1 - 1, index2 - 1);
        }

        // Else, explore both options: exclude from str1 or exclude from str2
        int excludeFromStr1 = lcsRecursive(str1, str2, index1 - 1, index2);   // Move in str1
        int excludeFromStr2 = lcsRecursive(str1, str2, index1, index2 - 1);   // Move in str2

        // Return the maximum of both options
        return Math.max(excludeFromStr1, excludeFromStr2);
    }

    // 2. Memoization Approach (Top-Down DP)
    // Time Complexity: O(m * n) -> Each state (i, j) is computed only once
    // Space Complexity: O(m * n) for dp[][] + O(m + n) recursion stack
    private static int lcsMemoization(String s1, String s2) {
        int m = s1.length(); // Length of first string
        int n = s2.length(); // Length of second string

        // Create dp array initialized with -1
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                dp[i][j] = -1; // Mark uncomputed states

        // Start recursion from the last indices
        return memo(s1, s2, m - 1, n - 1, dp);
    }

    private static int memo(String s1, String s2, int i, int j, int[][] dp) {
        // Base Case: If either string is exhausted, return 0
        if (i < 0 || j < 0) return 0;

        // If already computed, return cached value
        if (dp[i][j] != -1) return dp[i][j];

        // If characters match, move diagonally and add 1
        if (s1.charAt(i) == s2.charAt(j))
            dp[i][j] = 1 + memo(s1, s2, i - 1, j - 1, dp);
        else
            // Else, exclude character from one string at a time
            dp[i][j] = Math.max(
                    memo(s1, s2, i - 1, j, dp),
                    memo(s1, s2, i, j - 1, dp)
            );

        return dp[i][j]; // Return computed result
    }

    // 3. Tabulation (Bottom-Up DP)
    // Time Complexity: O(m * n) -> Each cell of dp table is filled once
    // Space Complexity: O(m * n) for the 2D dp array
    private static int lcsTabulation(String s1, String s2) {
        int m = s1.length(); // Length of first string
        int n = s2.length(); // Length of second string

        // dp[i][j] = length of LCS for first i characters of s1 and first j characters of s2
        int[][] dp = new int[m + 1][n + 1]; // Extra row & column for base case

        // Build the dp table bottom-up
        for (int i = 1; i <= m; i++) { // Loop through s1
            for (int j = 1; j <= n; j++) { // Loop through s2
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // If characters match, take diagonal + 1
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // Else take max of top or left cell
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n]; // Final answer in bottom-right cell
    }

    // 4. Space Optimized Approach
    // Time Complexity: O(m * n)
    // Space Complexity: O(2 * n) = O(n) -> Only two rows required at a time
    private static int lcsSpaceOptimized(String s1, String s2) {
        int m = s1.length(); // Length of s1
        int n = s2.length(); // Length of s2

        // We only keep previous and current rows
        int[] prev = new int[n + 1]; // Stores results from previous row
        int[] curr = new int[n + 1]; // Stores results for current row

        for (int i = 1; i <= m; i++) { // Loop over s1
            for (int j = 1; j <= n; j++) { // Loop over s2
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Characters match -> 1 + diagonal value
                    curr[j] = 1 + prev[j - 1];
                } else {
                    // Take max from top or left
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }

            // After each row, copy curr to prev for next iteration
            prev = curr.clone();
        }

        return prev[n]; // Final LCS length
    }

    // Main function to test all approaches
    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";

        System.out.println("Recursive LCS: " + lcsRecursive(text1, text2, text1.length() - 1, text2.length() - 1));
        System.out.println("Memoization LCS: " + lcsMemoization(text1, text2));
        System.out.println("Tabulation LCS: " + lcsTabulation(text1, text2));
        System.out.println("Space Optimized LCS: " + lcsSpaceOptimized(text1, text2));
    }
}

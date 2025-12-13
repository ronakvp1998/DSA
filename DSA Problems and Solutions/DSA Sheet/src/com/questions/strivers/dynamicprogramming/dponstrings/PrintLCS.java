package com.questions.strivers.dynamicprogramming.dponstrings;

//https://takeuforward.org/data-structure/print-longest-common-subsequence-dp-26/
//Problem Statement:
//Print Longest Common Subsequence, we will learn to print the actual string of the longest common subsequence.

public class PrintLCS {

    // 1️⃣ Recursive Approach (Inefficient for large inputs - TLE)

    /**
     * A plain recursive approach to find the LCS string.
     * It explores all combinations and builds the LCS by checking matches.
     */
    private static String lcsRecursive(String s1, String s2, int i, int j) {
        // Base Case: If either string is exhausted, return empty string
        if (i < 0 || j < 0) return "";

        // If characters match, include this character in the LCS and move diagonally
        if (s1.charAt(i) == s2.charAt(j)) {
            return lcsRecursive(s1, s2, i - 1, j - 1) + s1.charAt(i);
        } else {
            // If characters don't match, explore both options (left and up)
            // and return the one with longer LCS
            String opt1 = lcsRecursive(s1, s2, i - 1, j);
            String opt2 = lcsRecursive(s1, s2, i, j - 1);
            return (opt1.length() > opt2.length()) ? opt1 : opt2;
        }
    }
    // ⏱️ Time: O(2^(n+m)), 📦 Space: O(n + m) for recursion stack

    // 2️⃣ Memoization Approach (Top-Down DP)
    static String[][] memo;

    /**
     * A top-down dynamic programming approach using memoization
     * to avoid recomputation of overlapping subproblems.
     */
    private static String lcsMemoization(String s1, String s2, int i, int j) {
        // Base Case: If any index is out of bounds
        if (i < 0 || j < 0) return "";

        // If result is already computed, return it
        if (memo[i][j] != null) return memo[i][j];

        // If characters match, add to LCS and move diagonally
        if (s1.charAt(i) == s2.charAt(j)) {
            memo[i][j] = lcsMemoization(s1, s2, i - 1, j - 1) + s1.charAt(i);
        } else {
            // Else choose the longer LCS from left or top
            String opt1 = lcsMemoization(s1, s2, i - 1, j);
            String opt2 = lcsMemoization(s1, s2, i, j - 1);
            memo[i][j] = (opt1.length() > opt2.length()) ? opt1 : opt2;
        }
        return memo[i][j];
    }
    // ⏱️ Time: O(n*m * min(n,m)) due to string concatenation
    // 📦 Space: O(n*m) for memo + O(n + m) recursion stack

    // 3️⃣ Tabulation Approach (Bottom-Up DP)
    /**
     * Builds the LCS string using a bottom-up DP table,
     * then reconstructs the LCS by tracing back from the bottom-right corner.
     */
    private static String lcsTabulation(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1]; // dp[i][j] = LCS length for s1[0..i-1] & s2[0..j-1]

        // Step 1: Fill the dp table using nested loops
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Characters match -> move diagonally and add 1
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // Take the maximum from top or left
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Backtrack to build the actual LCS string
        int i = n, j = m;
        StringBuilder lcs = new StringBuilder();

        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                // Character is part of LCS
                lcs.append(s1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move up
                i--;
            } else {
                // Move left
                j--;
            }
        }

        // Since we built the string in reverse, reverse it before returning
        return lcs.reverse().toString();
    }
    // ⏱️ Time: O(n*m), 📦 Space: O(n*m)

    // 4️⃣ Space Optimized Version (Only returns LCS length, not the string)

    /**
     * Optimized space version using two 1D arrays.
     * Only gives LCS length, not the string.
     */
    private static int lcsLengthSpaceOptimized(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[] prev = new int[m + 1]; // Previous row
        int[] curr = new int[m + 1]; // Current row

        // Fill the dp rows iteratively
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Move current row to previous row for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[m]; // Final LCS length
    }
    // ⏱️ Time: O(n*m), 📦 Space: O(2*m)

    // 🔍 Main function to test all approaches
    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "ace";

        // 1️⃣ Test Recursive
        System.out.println("🔁 Recursive LCS: " + lcsRecursive(s1, s2, s1.length() - 1, s2.length() - 1));

        // 2️⃣ Test Memoization
        memo = new String[s1.length()][s2.length()];
        System.out.println("🧠 Memoized LCS: " + lcsMemoization(s1, s2, s1.length() - 1, s2.length() - 1));

        // 3️⃣ Test Tabulation
        System.out.println("📋 Tabulation LCS: " + lcsTabulation(s1, s2));

        // 4️⃣ Test Space Optimized for length
        System.out.println("💾 LCS Length (Space Optimized): " + lcsLengthSpaceOptimized(s1, s2));
    }
}



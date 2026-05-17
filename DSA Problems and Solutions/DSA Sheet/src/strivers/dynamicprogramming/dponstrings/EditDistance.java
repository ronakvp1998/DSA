package com.questions.strivers.dynamicprogramming.dponstrings;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: EDIT DISTANCE (LeetCode 72)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given two strings 'word1' and 'word2', return the minimum number of operations required
 * to convert 'word1' to 'word2'.
 *
 * Allowed Operations:
 * 1. Insert a character
 * 2. Delete a character
 * 3. Replace a character
 *
 * EXAMPLE 1:
 * Input: word1 = "horse", word2 = "ros"
 * Output: 3
 * Explanation:
 * horse -> rorse (replace 'h' with 'r')
 * rorse -> rose (remove 'r')
 * rose -> ros (remove 'e')
 *
 * KEY INSIGHT:
 * We compare characters at indices `i` (word1) and `j` (word2).
 * 1. **Match:** If `word1[i] == word2[j]`, no operation needed. Move both pointers (`i-1`, `j-1`).
 * 2. **Mismatch:** We take the minimum of 3 possibilities + 1 cost:
 * - **Insert:** Imagine inserting a char in word1 matching word2[j].
 * Pointer `i` stays same (char still needs processing), `j` moves back (`j-1`).
 * - **Delete:** Delete char at word1[i].
 * Pointer `i` moves back (`i-1`), `j` stays same (still need to match it).
 * - **Replace:** Turn word1[i] into word2[j].
 * Both pointers move back (`i-1`, `j-1`).
 *
 * BASE CASES:
 * - If word1 is empty (i < 0): Insert all remaining characters of word2 (return j + 1).
 * - If word2 is empty (j < 0): Delete all remaining characters of word1 (return i + 1).
 * ==================================================================================================
 */
public class EditDistance {

    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";

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
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Explore all 3 paths (Insert, Delete, Replace) at every mismatch.
     *
     * COMPLEXITY:
     * - Time: O(3^(N+M)) -> Exponential.
     * - Space: O(N+M) -> Stack depth.
     */
    private static int minDistanceRecursive(String s1, String s2) {
        return helperRecursive(s1, s2, s1.length() - 1, s2.length() - 1);
    }

    private static int helperRecursive(String s1, String s2, int i, int j) {
        // Base Case 1: s1 exhausted, insert all remaining chars of s2
        if (i < 0) return j + 1;

        // Base Case 2: s2 exhausted, delete all remaining chars of s1
        if (j < 0) return i + 1;

        // Match
        if (s1.charAt(i) == s2.charAt(j)) {
            return helperRecursive(s1, s2, i - 1, j - 1);
        }

        // Mismatch: 1 + Min(Insert, Delete, Replace)
        int insert = helperRecursive(s1, s2, i, j - 1);    // Insert into s1 matches s2[j]
        int delete = helperRecursive(s1, s2, i - 1, j);    // Delete s1[i]
        int replace = helperRecursive(s1, s2, i - 1, j - 1); // Replace s1[i] with s2[j]

        return 1 + Math.min(insert, Math.min(delete, replace));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results for state (i, j).
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

        return helperMemo(s1, s2, n - 1, m - 1, dp);
    }

    private static int helperMemo(String s1, String s2, int i, int j, int[][] dp) {
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;

        if (dp[i][j] != -1) return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] = helperMemo(s1, s2, i - 1, j - 1, dp);
        }

        int insert = helperMemo(s1, s2, i, j - 1, dp);
        int delete = helperMemo(s1, s2, i - 1, j, dp);
        int replace = helperMemo(s1, s2, i - 1, j - 1, dp);

        return dp[i][j] = 1 + Math.min(insert, Math.min(delete, replace));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Shift indices by 1 to handle base cases (empty strings) at row 0 / col 0.
     * dp[i][j] = Min ops to convert s1[0...i-1] to s2[0...j-1].
     *
     *
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M)
     */
    private static int minDistanceTabulation(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        // Base Cases (Row 0 and Col 0)
        for (int i = 0; i <= n; i++) dp[i][0] = i; // Deleting all chars from s1
        for (int j = 0; j <= m; j++) dp[0][j] = j; // Inserting all chars into s1

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No Op
                } else {
                    int insert = dp[i][j - 1];
                    int delete = dp[i - 1][j];
                    int replace = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(insert, Math.min(delete, replace));
                }
            }
        }
        return dp[n][m];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the previous row to compute the current row.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(M)
     */
    private static int minDistanceSpaceOptimized(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[] prev = new int[m + 1];

        // Base Case for Row 0 (Empty s1 -> Insert all s2 chars)
        for (int j = 0; j <= m; j++) prev[j] = j;

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            curr[0] = i; // Base Case for Col 0 (s1 has i chars, s2 empty -> Delete all i)

            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    int insert = curr[j - 1]; // Left
                    int delete = prev[j];     // Up
                    int replace = prev[j - 1]; // Diagonal

                    curr[j] = 1 + Math.min(insert, Math.min(delete, replace));
                }
            }
            prev = curr;
        }
        return prev[m];
    }
}
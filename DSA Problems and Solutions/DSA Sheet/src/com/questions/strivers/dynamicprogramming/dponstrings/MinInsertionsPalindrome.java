package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: 1312. Minimum Insertion Steps to Make a String Palindrome
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given a string s. In one step you can insert any character at any index of
 * the string. Return the minimum number of steps to make s a palindrome.
 * * * CONSTRAINTS:
 * - 1 <= s.length <= 500
 * - s consists of lowercase English letters.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION
 * ============================================================================
 * * 1. State Definition (Interval DP):
 * We use two pointers, `i` at the start and `j` at the end of the string.
 * - State: f(i, j) represents the minimum insertions to make substring s[i...j]
 * a palindrome.
 *
 * * * 2. Recursion Tree (Partial for s = "mbadm"):
 *
 *                        f(0, 4) ["mbadm"]
 *                      /                  \
 *                  (m != m? False. They match!)
 *                              |
 *                       f(1, 3) ["bad"]
 *                      /               \
 *          (b != d? True. Insert 'd' left OR 'b' right)
 *              /                   \
 *      1 + f(1, 2) ["ba"]       1 + f(2, 3) ["ad"]
 *
 * * * 3. DP Table Transitions:
 * If s[i] == s[j]:
 * dp[i][j] = dp[i+1][j-1]                // Shrink window, no cost
 * If s[i] != s[j]:
 * dp[i][j] = 1 + min(dp[i+1][j],         // Insert char matching s[j] at left
 * dp[i][j-1])         // Insert char matching s[i] at right
 * * ============================================================================
 */



public class MinInsertionsPalindrome {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * Intuition:
     * We look at the outer boundaries of our current string window.
     * If the characters match, they already contribute to a palindrome; we
     * move both pointers inward. If they don't match, we are forced to insert
     * a character. We can either insert a character to match the left pointer
     * or the right pointer. We explore both, take the minimum, and add 1 cost.
     * * Complexity Analysis:
     * - Time Complexity: $O(2^N)$ - In the worst case (no matching pairs), we
     * branch into two paths at every step. Will TLE on LeetCode.
     * - Space Complexity: $O(N)$ - Auxiliary stack space for recursion depth.
     */
    public int minInsertionsBruteForce(String s) {
        return solveRecursive(s, 0, s.length() - 1);
    }

    private int solveRecursive(String s, int i, int j) {
        // Base case: If the window size is 1 or 0, it's already a palindrome
        if (i >= j) {
            return 0;
        }

        if (s.charAt(i) == s.charAt(j)) {
            // Characters match, shrink the window inward without any cost
            return solveRecursive(s, i + 1, j - 1);
        } else {
            // No match. Try inserting on the left (matching right char)
            // OR inserting on the right (matching left char)
            int insertLeft = solveRecursive(s, i, j - 1);
            int insertRight = solveRecursive(s, i + 1, j);
            return 1 + Math.min(insertLeft, insertRight);
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * Intuition:
     * The recursive tree evaluates the same string intervals `s[i...j]`
     * multiple times. We cache the results in a 2D array `memo[i][j]` where
     * `i` and `j` represent the start and end indices of the window.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^2)$ - We evaluate each combination of (i, j) once.
     * - Space Complexity: $O(N^2)$ heap space for the memo table + $O(N)$ stack space.
     */
    public int minInsertionsMemo(String s) {
        int n = s.length();
        Integer[][] memo = new Integer[n][n];
        return solveMemo(s, 0, n - 1, memo);
    }

    private int solveMemo(String s, int i, int j, Integer[][] memo) {
        if (i >= j) return 0;

        if (memo[i][j] != null) return memo[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            return memo[i][j] = solveMemo(s, i + 1, j - 1, memo);
        } else {
            int insertLeft = solveMemo(s, i, j - 1, memo);
            int insertRight = solveMemo(s, i + 1, j, memo);
            return memo[i][j] = 1 + Math.min(insertLeft, insertRight);
        }
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * Intuition:
     * To build bottom-up, we need to ensure that smaller windows are computed
     * before larger ones. We loop `i` backwards from the end of the string,
     * and `j` forwards from `i + 1` to the end. This guarantees `dp[i+1][j-1]`
     * is ready when we need it.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^2)$ - Two nested loops iterating over the string.
     * - Space Complexity: $O(N^2)$ - 2D DP array. Stack space is eliminated.
     */
    public int minInsertionsTabulation(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // i goes backwards to ensure i+1 is already computed
        for (int i = n - 1; i >= 0; i--) {
            // j goes forwards to ensure j-1 is already computed
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * Intuition:
     * Notice that `dp[i][j]` only relies on the current row `dp[i]` (for `j-1`)
     * and the previous row `dp[i+1]` (for `j` and `j-1`). We can reduce the
     * $O(N^2)$ 2D matrix into two $O(N)$ 1D arrays representing the previous
     * row and the current row.
     * * Complexity Analysis:
     * - Time Complexity: $O(N^2)$
     * - Space Complexity: $O(N)$ - Only two 1D arrays of size N.
     */
    public int minInsertionsSpaceOptimized(String s) {
        int n = s.length();
        int[] prev = new int[n]; // Represents dp[i+1] row
        int[] curr = new int[n]; // Represents dp[i] row

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(prev[j], curr[j - 1]);
                }
            }
            // Move current state to previous for the next iteration of i
            prev = curr.clone();
        }
        return prev[n - 1];
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (LPS via LCS)
     * ========================================================================
     * Intuition:
     * The characters that DO NOT need to be inserted are the ones that already
     * form a palindrome. Therefore, the minimum insertions equals the string
     * length minus the Longest Palindromic Subsequence (LPS).
     * LPS(s) is simply the Longest Common Subsequence (LCS) of `s` and `reverse(s)`.
     * * Formula: Minimum Insertions = length - LCS(s, reverse(s))
     * * Complexity Analysis:
     * - Time Complexity: $O(N^2)$ - Computing LCS.
     * - Space Complexity: $O(N)$ - Space-optimized LCS table.
     */
    public int minInsertionsLCS(String s) {
        String reversedStr = new StringBuilder(s).reverse().toString();
        int lcsLength = longestCommonSubsequence(s, reversedStr);
        return s.length() - lcsLength;
    }

    private int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            int[] temp = prev; prev = curr; curr = temp; // Swap arrays
        }
        return prev[m];
    }
}
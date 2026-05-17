package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * SHORTEST COMMON SUPERSEQUENCE LENGTH (LCS FORMULA APPROACH)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings str1 and str2, return the length of the shortest string
 * that has both str1 and str2 as subsequences.
 * A string s is a subsequence of string t if deleting some number of characters
 * from t (possibly 0) results in the string s.
 * * CONSTRAINTS:
 * - 1 <= str1.length, str2.length <= 1000
 * - str1 and str2 consist of lowercase English letters.
 * * EXAMPLES:
 * Example 1:
 * Input: str1 = "abac", str2 = "cab" | Output: 5
 * Explanation: The SCS is "cabac" (length 5).
 * str1 "abac" is a subsequence. str2 "cab" is a subsequence.
 * * Example 2:
 * Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa" | Output: 8
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "LCS" FORMULA
 * ============================================================================
 * The core mathematical trick:
 * If we simply concatenated str1 and str2, the length would be (m + n).
 * However, this guarantees duplicate characters for any shared subsequences.
 * To make the supersequence as SHORT as possible, we must overlap the common
 * characters exactly once. The maximum number of characters we can overlap
 * is precisely the Longest Common Subsequence (LCS).
 * * Formula:
 * Length of SCS = str1.length + str2.length - length(LCS)
 * * Example: str1 = "abac" (len 4), str2 = "cab" (len 3)
 * LCS("abac", "cab") = "ab" (Length 2)
 * SCS Length = 4 + 3 - 2 = 5.
 * * EXACT FINAL DP ARRAY STATE FOR LCS (str1 = "abac", str2 = "cab"):
 * (Rows = str1 prefixes, Cols = str2 prefixes)
 * ""   c   a   b
 * "" [ 0,  0,  0,  0 ]
 * a  [ 0,  0,  1,  1 ]
 * b  [ 0,  0,  1,  2 ]
 * a  [ 0,  0,  1,  2 ]
 * c  [ 0,  1,  1,  2 ]  <- dp[4][3] is LCS length (2)
 * * Applying Formula: 4 + 3 - 2 = 5.
 * ============================================================================
 */
public class ShortestCommonSupersequenceLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We use the standard recursive LCS algorithm to find the length of the
     * Longest Common Subsequence between str1 and str2.
     * Finally, we apply the formula: m + n - lcs.
     * * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Branching factor of 2 at every mismatch.
     * - Space: O(M+N) -> Auxiliary stack space for recursion depth.
     */
    public int scsLengthRecursive(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int lcs = lcsRecursive(m, n, str1, str2);
        return m + n - lcs;
    }

    private int lcsRecursive(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return 0;

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + lcsRecursive(i - 1, j - 1, s1, s2);
        }

        return Math.max(lcsRecursive(i - 1, j, s1, s2), lcsRecursive(i, j - 1, s1, s2));
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursive approach recalculates the exact same states (i, j) repeatedly.
     * We introduce an Integer[][] memo table to cache and reuse overlapping
     * LCS subproblem results.
     * * COMPLEXITY:
     * - Time: O(M * N) -> Every (i, j) combination is calculated exactly once.
     * - Space: O(M * N) for the Heap (memo array) + O(M + N) for the Call Stack.
     */
    public int scsLengthMemo(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];

        int lcs = lcsMemo(m, n, str1, str2, memo);
        return m + n - lcs;
    }

    private int lcsMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + lcsMemo(i - 1, j - 1, s1, s2, memo);
        }

        return memo[i][j] = Math.max(lcsMemo(i - 1, j, s1, s2, memo), lcsMemo(i, j - 1, s1, s2, memo));
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * Convert the recursion stack into iterative loops using a standard 2D DP
     * table for LCS. Then apply the formula.
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (str1="abac", str2="cab", m=4, n=3):
     * 0   1   2   3
     * 0 [  0,  0,  0,  0 ] <- Base case row (str1 empty)
     * 1 [  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0 ]
     * 3 [  0,  0,  0,  0 ]
     * 4 [  0,  0,  0,  0 ]
     * (Note: Java primitive int arrays default to 0, handling the base cases)
     * * COMPLEXITY:
     * - Time: O(M * N) -> Strictly nested loops evaluating all character pairs.
     * - Space: O(M * N) -> Heap memory for the DP table. Call Stack is eliminated.
     */
    public int scsLengthTabulation(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Standard LCS Tabulation
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int lcs = dp[m][n];
        return m + n - lcs;
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * In the Tabulation matrix, computing row `i` only ever requires values from
     * row `i-1` (the previous row) and the current row `i`. We do not need all `M`
     * rows in memory. We can reduce the O(M * N) space to O(N) by using two 1D
     * arrays: `prev` (representing row i-1) and `curr` (representing row i).
     * * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Only two 1D arrays of size N+1.
     */
    public int scsLengthSpaceOptimized(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Move current state to previous for the next iteration
            prev = curr.clone();
        }

        int lcs = prev[n];
        return m + n - lcs;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        ShortestCommonSupersequenceLength solver = new ShortestCommonSupersequenceLength();

        System.out.println("=== Test Case 1: Standard Example ===");
        String s1 = "abac", s2 = "cab";
        System.out.println("Recursion  : " + solver.scsLengthRecursive(s1, s2));
        System.out.println("Memoization: " + solver.scsLengthMemo(s1, s2));
        System.out.println("Tabulation : " + solver.scsLengthTabulation(s1, s2));
        System.out.println("Space Opt  : " + solver.scsLengthSpaceOptimized(s1, s2));
        System.out.println("Expected   : 5 (LCS is 'ab' length 2. 4 + 3 - 2 = 5)\n");

        System.out.println("=== Test Case 2: Identical Strings ===");
        String s3 = "aaaaaaaa", s4 = "aaaaaaaa";
        System.out.println("Space Opt  : " + solver.scsLengthSpaceOptimized(s3, s4));
        System.out.println("Expected   : 8\n");

        System.out.println("=== Test Case 3: Completely Disjoint Strings ===");
        String s5 = "abc", s6 = "def";
        System.out.println("Space Opt  : " + solver.scsLengthSpaceOptimized(s5, s6));
        System.out.println("Expected   : 6 (LCS is 0. 3 + 3 - 0 = 6)\n");

        System.out.println("=== Test Case 4: One String is Subsequence of Another ===");
        String s7 = "abcde", s8 = "ace";
        System.out.println("Space Opt  : " + solver.scsLengthSpaceOptimized(s7, s8));
        System.out.println("Expected   : 5 (LCS is 'ace' length 3. 5 + 3 - 3 = 5)\n");
    }
}
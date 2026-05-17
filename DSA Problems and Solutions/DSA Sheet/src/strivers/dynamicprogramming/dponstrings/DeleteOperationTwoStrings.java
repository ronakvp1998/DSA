package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * 583. DELETE OPERATION FOR TWO STRINGS (LCS APPROACH)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings word1 and word2, return the minimum number of steps
 * required to make word1 and word2 the same.
 * In one step, you can delete exactly one character in either string.
 * * CONSTRAINTS:
 * - 1 <= word1.length, word2.length <= 500
 * - word1 and word2 consist of only lowercase English letters.
 * * EXAMPLES:
 * Example 1:
 * Input: word1 = "sea", word2 = "eat" | Output: 2
 * Explanation: You need one step to make "sea" to "ea" and another step to
 * make "eat" to "ea".
 * * Example 2:
 * Input: word1 = "leetcode", word2 = "etco" | Output: 4
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "LCS" FORMULA
 * ============================================================================
 * The core mathematical trick:
 * To make two strings identical with the MINIMUM number of deletions, we must
 * preserve the MAXIMUM number of common characters. The characters we preserve
 * exactly form the Longest Common Subsequence (LCS).
 * * Characters to delete from word1 = word1.length - LCS
 * Characters to delete from word2 = word2.length - LCS
 * Total Deletions = word1.length + word2.length - 2 * LCS
 * * Example: word1 = "sea", word2 = "eat"
 * LCS("sea", "eat") = 2 ("ea")
 * Total Deletions = 3 + 3 - (2 * 2) = 6 - 4 = 2.
 * * RECURSION TREE FOR LCS (Partial for "sea", "eat"):
 * f(3, 3) ["sea", "eat"]
 * ('a' != 't') -> Branch
 * /                          \
 * f(2, 3) ["se", "eat"]               f(3, 2) ["sea", "ea"]
 * ('e' != 't' -> Branch...)           ('a' == 'a' -> Match!) -> 1 + f(2, 1)
 * * EXACT FINAL DP ARRAY STATE FOR LCS (word1 = "sea", word2 = "eat"):
 * (Rows = word1 prefixes, Cols = word2 prefixes)
 * ""   e   a   t
 * "" [ 0,  0,  0,  0 ]
 * s  [ 0,  0,  0,  0 ]
 * e  [ 0,  1,  1,  1 ]
 * a  [ 0,  1,  2,  2 ]  <- dp[3][3] is the LCS length (2)
 * * Formula applied: 3 + 3 - (2 * 2) = 2 deletions.
 * ============================================================================
 */
public class DeleteOperationTwoStrings {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We use the standard recursive LCS algorithm to find the length of the
     * Longest Common Subsequence between word1 and word2.
     * Finally, we apply the mathematical formula to find the minimum deletions.
     * * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Branching factor of 2 at every mismatch.
     * - Space: O(M+N) -> Auxiliary stack space for recursion depth.
     */
    public int minDistanceRecursive(String word1, String word2) {
        int lcs = lcsRecursive(word1.length(), word2.length(), word1, word2);
        return word1.length() + word2.length() - (2 * lcs);
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
     * The recursive approach recalculates the exact same suffix states repeatedly.
     * We introduce an Integer[][] memo table to cache and reuse the overlapping
     * LCS subproblem results.
     * * COMPLEXITY:
     * - Time: O(M * N) -> Every (i, j) combination is calculated exactly once.
     * - Space: O(M * N) for the Heap (memo array) + O(M + N) for the Call Stack.
     */
    public int minDistanceMemo(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];

        int lcs = lcsMemo(m, n, word1, word2, memo);
        return m + n - (2 * lcs);
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
     * table for LCS.
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (word1 = "sea", word2 = "eat"):
     * 0   1   2   3
     * 0 [  0,  0,  0,  0 ] <- Base case row (word1 empty)
     * 1 [  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0 ]
     * 3 [  0,  0,  0,  0 ]
     * (Note: Java primitive int arrays default to 0, handling the base case perfectly)
     * * COMPLEXITY:
     * - Time: O(M * N) -> Strictly nested loops evaluating all character pairs.
     * - Space: O(M * N) -> Heap memory for the DP table. Call Stack space is eliminated.
     */
    public int minDistanceTabulation(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Standard LCS Tabulation
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int lcs = dp[m][n];
        return m + n - (2 * lcs);
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * In the Tabulation matrix, computing row `i` only ever requires values from
     * row `i-1` (the previous row) and the current row `i`. We do not need all `M`
     * rows in memory. We can reduce the O(M * N) space down to O(N) by using
     * two 1D arrays: `prev` (representing row i-1) and `curr` (representing row i).
     * * COMPLEXITY:
     * - Time: O(M * N) -> Still nested loops evaluating all characters.
     * - Space: O(N) -> Only two 1D arrays of size N+1.
     */
    public int minDistanceSpaceOptimized(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Move current state to previous for the next iteration
            prev = curr.clone();
        }

        int lcs = prev[n];
        return m + n - (2 * lcs);
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        DeleteOperationTwoStrings solver = new DeleteOperationTwoStrings();

        System.out.println("=== Test Case 1: Standard Example ===");
        String w1 = "sea", w2 = "eat";
        System.out.println("Recursion  : " + solver.minDistanceRecursive(w1, w2));
        System.out.println("Memoization: " + solver.minDistanceMemo(w1, w2));
        System.out.println("Tabulation : " + solver.minDistanceTabulation(w1, w2));
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w1, w2));
        System.out.println("Expected   : 2 (LCS is 'ea', len 2. 3 + 3 - 4 = 2)\n");

        System.out.println("=== Test Case 2: Longer Example ===");
        String w3 = "leetcode", w4 = "etco";
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w3, w4));
        System.out.println("Expected   : 4\n");

        System.out.println("=== Test Case 3: Completely Different Strings ===");
        String w5 = "abc", w6 = "xyz";
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w5, w6));
        System.out.println("Expected   : 6 (Delete everything: 3 + 3 - 0 = 6)\n");

        System.out.println("=== Test Case 4: Already Identical ===");
        String w7 = "same", w8 = "same";
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w7, w8));
        System.out.println("Expected   : 0\n");
    }
}
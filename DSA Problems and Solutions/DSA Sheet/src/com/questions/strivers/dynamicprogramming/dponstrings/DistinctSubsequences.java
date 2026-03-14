package com.questions.strivers.dynamicprogramming.dponstrings;
/**
 * ============================================================================
 * 115. Distinct Subsequences
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings s and t, return the number of distinct subsequences of s
 * which equals t.
 * * A string's subsequence is a new string formed from the original string by
 * deleting some (can be none) of the characters without disturbing the
 * remaining characters' relative positions. (i.e., "ACE" is a subsequence of
 * "ABCDE" while "AEC" is not).
 * * * CONSTRAINTS:
 * - 1 <= s.length, t.length <= 1000
 * - s and t consist of English letters.
 * - The test cases are generated so that the answer fits on a 32-bit signed int.
 * * * EXAMPLES:
 * Example 1:
 * Input: s = "rabbbit", t = "rabbit" | Output: 3
 * Explanation:
 * As shown below, there are 3 ways you can generate "rabbit" from s.
 * rabbbit -> rabb_it
 * rabbbit -> rab_bit
 * rabbbit -> ra_bbit
 * * Example 2:
 * Input: s = "babgbag", t = "bag" | Output: 5
 * Explanation:
 * babgbag -> ba_g___
 * babgbag -> ba____g
 * babgbag -> b____ag
 * babgbag -> __b_bag
 * babgbag -> ____bag
 * * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s = "bab", t = "ba")
 * ============================================================================
 * Let f(i, j) represent the number of ways to form t[0..j] using s[0..i].
 * Indices represent the length of the string prefixes being compared.
 * * f(3, 2) [s="bab", t="ba"]
 * ('b' != 'a') -> Skip char in s
 * |
 * f(2, 2) [s="ba", t="ba"]
 * ('a' == 'a') -> Choice: Use it + Skip it
 * /                       \
 * (Use 'a') /                         \ (Skip 'a')
 * f(1, 1) [s="b", t="b"]              f(1, 2) [s="b", t="ba"]
 * ('b' == 'b') -> Use + Skip          ('b' != 'a') -> Skip
 * /                \                       |
 * f(0,0)["",""]   f(0,1)["","b"]          f(0, 2)["", "ba"]
 * (Base: 1)       (Base: 0)               (Base: 0)
 * * Total ways = 1
 * * * COMPLETE FINAL DP ARRAY STATE (s = "bab", t = "ba"):
 * (Rows = s prefixes: "", "b", "a", "b")
 * (Cols = t prefixes: "", "b", "a")
 * *    ""   b   a
 * "" [  1,  0,  0 ]
 * b  [  1,  1,  0 ]
 * a  [  1,  1,  1 ]
 * b  [  1,  2,  1 ] <- Final Answer at bottom right: 1
 */

public class DistinctSubsequences {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We compare the strings starting from the end (using prefix lengths).
     * 1. If the current characters match (s[i-1] == t[j-1]), we have two choices:
     * - USE the character: Both indices reduce -> solve(i-1, j-1)
     * - SKIP the character (look for another match in s) -> solve(i-1, j)
     * We add the results of both choices.
     * 2. If the characters DO NOT match, we only have one choice:
     * - SKIP the character in 's' -> solve(i-1, j)
     * 3. Base Cases:
     * - If t becomes empty (j == 0): We found a valid subsequence! Return 1.
     * - If s becomes empty (i == 0) and t is not: Impossible. Return 0.
     * * COMPLEXITY:
     * - Time: O(2^M) where M is the length of string s. In the worst case
     * (all characters match), we branch twice at every step.
     * - Space: O(M) Auxiliary Stack Space for the recursion depth.
     */
    public int numDistinctRecursive(String s, String t) {
        return solveRecursive(s.length(), t.length(), s, t);
    }

    private int solveRecursive(int i, int j, String s, String t) {
        if (j == 0) return 1; // All characters of t matched
        if (i == 0) return 0; // s ran out before t could match

        if (s.charAt(i - 1) == t.charAt(j - 1)) {
            return solveRecursive(i - 1, j - 1, s, t) + solveRecursive(i - 1, j, s, t);
        } else {
            return solveRecursive(i - 1, j, s, t);
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursion tree explores the same prefix pairs multiple times. We
     * cache the results in a 2D array `dp[i][j]`. If `dp[i][j]` is already
     * computed, we return it in O(1) time.
     * * COMPLEXITY:
     * - Time: O(M * N) -> We compute each unique state exactly once.
     * - Space: O(M * N) Heap Space for `dp` array + O(M) Stack Space.
     */
    public int numDistinctMemo(String s, String t) {
        int m = s.length();
        int n = t.length();
        Integer[][] dp = new Integer[m + 1][n + 1];
        return solveMemo(m, n, s, t, dp);
    }

    private int solveMemo(int i, int j, String s, String t, Integer[][] dp) {
        if (j == 0) return 1;
        if (i == 0) return 0;

        if (dp[i][j] != null) return dp[i][j];

        if (s.charAt(i - 1) == t.charAt(j - 1)) {
            dp[i][j] = solveMemo(i - 1, j - 1, s, t, dp) + solveMemo(i - 1, j, s, t, dp);
        } else {
            dp[i][j] = solveMemo(i - 1, j, s, t, dp);
        }
        return dp[i][j];
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * Convert memoization to an iterative approach. We build the DP table
     * from the smallest prefixes (length 0) up to the full strings.
     * * EXACT DEFAULT STATE OF DP ARRAY (s="bab", t="ba"):
     * After base case initialization (j=0 gets 1, i=0 gets 0), before nested loops:
     * ""        b   a
     * "" [  1,  0,  0 ]
     * b  [  1,  0,  0 ]
     * a  [  1,  0,  0 ]
     * b  [  1,  0,  0 ]
     * * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(M * N) Heap Space for the 2D DP table. No stack overhead.
     */
    public int numDistinctTabulation(String s, String t) {
        int m = s.length();
        int n = t.length();

        // Use double to avoid overflow during intermediate large test cases on LeetCode
        // before they cast to unsigned, though constraints say answer fits in 32-bit int.
        int[][] dp = new int[m + 1][n + 1];

        // Base Case: empty 't' can be matched by any 's' prefix exactly 1 way
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }
        // dp[0][j] is inherently 0 for j > 0 in Java

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * In the recurrence `dp[i][j] = dp[i-1][j-1] + dp[i-1][j]`, the current
     * row `i` only relies on the previous row `i-1`.
     * * *ULTIMATE OPTIMIZATION*: We can use a SINGLE 1D array of size N+1.
     * If we traverse the inner loop strictly from RIGHT TO LEFT (j = n down to 1),
     * `dp[j-1]` and `dp[j]` still hold the values from the *previous* row
     * because we haven't overwritten them yet!
     * * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) Heap Space -> A single 1D array instead of an M*N matrix.
     */
    public int numDistinctSpaceOptimized(String s, String t) {
        int m = s.length();
        int n = t.length();

        int[] dp = new int[n + 1];
        dp[0] = 1; // Base case: 1 way to form an empty string t

        for (int i = 1; i <= m; i++) {
            // CRITICAL: Traverse right to left to prevent overwriting required data
            for (int j = n; j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] = dp[j] + dp[j - 1];
                }
                // If they don't match, dp[j] = dp[j] (value carries over from previous row)
            }
        }

        return dp[n];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        DistinctSubsequences solver = new DistinctSubsequences();

        System.out.println("=== Test Case 1 ===");
        String s1 = "rabbbit", t1 = "rabbit";
        System.out.println("Recursion  : " + solver.numDistinctRecursive(s1, t1));
        System.out.println("Memoization: " + solver.numDistinctMemo(s1, t1));
        System.out.println("Tabulation : " + solver.numDistinctTabulation(s1, t1));
        System.out.println("Space Opt  : " + solver.numDistinctSpaceOptimized(s1, t1));
        System.out.println("Expected   : 3\n");

        System.out.println("=== Test Case 2 ===");
        String s2 = "babgbag", t2 = "bag";
        System.out.println("Space Opt  : " + solver.numDistinctSpaceOptimized(s2, t2));
        System.out.println("Expected   : 5\n");

        System.out.println("=== Edge Case: Impossible Match ===");
        String s3 = "abc", t3 = "def";
        System.out.println("Space Opt  : " + solver.numDistinctSpaceOptimized(s3, t3));
        System.out.println("Expected   : 0\n");

        System.out.println("=== Edge Case: Empty Target String ===");
        String s4 = "hello", t4 = "";
        System.out.println("Space Opt  : " + solver.numDistinctSpaceOptimized(s4, t4));
        System.out.println("Expected   : 1");
    }
}
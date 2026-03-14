package com.questions.strivers.dynamicprogramming.dponstrings;
/**
 * ============================================================================
 * PRINT LONGEST COMMON SUBSEQUENCE (Striver DP-26)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings 's1' and 's2', find and return the Longest Common
 * Subsequence (LCS) string. If multiple valid LCS strings exist, returning
 * any one of them is acceptable.
 * * A subsequence is a sequence that can be derived from another sequence by
 * deleting some or no elements without changing the order of the remaining
 * elements.
 * * * CONSTRAINTS:
 * - 1 <= s1.length, s2.length <= 1000
 * - s1 and s2 consist of lowercase English characters.
 * * * EXAMPLES:
 * Example 1:
 * Input: s1 = "abcde", s2 = "bdgek" | Output: "bde"
 * * Example 2:
 * Input: s1 = "ferret", s2 = "herbert" | Output: "eret"
 * * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s1 = "abc", s2 = "ac")
 * ============================================================================
 * Let f(i, j) return the LCS String for s1[0..i-1] and s2[0..j-1].
 *                          * f(3, 2) ["abc", "ac"]
 *                          ('c' == 'c') -> Match! Keep 'c' and append to the result of f(2, 1)
 *                                          |
 *                           f(2, 1) ["ab", "a"] + "c"
 *                          ('b' != 'a') -> Mismatch! Take the longest string from skipping either char
 *                      /                                   \
 *                   (Skip 'b' in s1)                    (Skip 'a' in s2)
 *                  f(1, 1) ["a", "a"]                  f(2, 0) ["ab", ""] -> Returns "" (Base)
 *                  ('a' == 'a') -> Match!
 *                          |
 *               f(0, 0) ["", ""] + "a" -> Returns "" (Base) + "a" = "a"
 *               
 * * Total Result: "a" + "c" = "ac"
 * * * COMPLETE FINAL DP ARRAY STATE (s1 = "abc", s2 = "ac") for Length Tabulation:
 * (Rows = s1 prefixes: "", "a", "b", "c")
 * (Cols = s2 prefixes: "", "a", "c")
 * * ""   a   c
 * "" [ 0,  0,  0 ]
 * a  [ 0,  1,  1 ]
 * b  [ 0,  1,  1 ]
 * c  [ 0,  1,  2 ]  <- Max length is 2. Backtrack from here to build "ac".
 */

public class LongestCommonSubsequencePrint {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We directly return the actual string from our recursive calls.
     * 1. If the last characters match, that character is part of the LCS. We
     * solve for the remaining prefixes and append the matched character.
     * 2. If they do not match, the LCS string could come from dropping the
     * last character of s1 OR the last character of s2. We compute both
     * and return the longer string.
     * * COMPLEXITY:
     * - Time: O(2^(M+N) * max(M,N)) -> Exponential state branching. The extra
     * multiplier is for string concatenation at each step.
     * - Space: O(M+N) Auxiliary Stack Space + massive Heap Space for String generation.
     */
    public String printLCSRecursive(String s1, String s2) {
        return solveRecursive(s1.length(), s2.length(), s1, s2);
    }

    private String solveRecursive(int i, int j, String s1, String s2) {
        // Base case: If either string is empty, the common subsequence is empty
        if (i == 0 || j == 0) return "";

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            // Match: append current char to the end of the recursion result
            return solveRecursive(i - 1, j - 1, s1, s2) + s1.charAt(i - 1);
        } else {
            // Mismatch: find max length string from both branches
            String leftStr = solveRecursive(i - 1, j, s1, s2);
            String rightStr = solveRecursive(i, j - 1, s1, s2);
            return leftStr.length() > rightStr.length() ? leftStr : rightStr;
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursive tree calculates the same prefix pairs multiple times.
     * We introduce a `String[][] memo` array to cache the resulting strings
     * for states (i, j) so we compute each exact state only once.
     * * COMPLEXITY:
     * - Time: O(M * N * max(M,N)) -> M*N unique states. String concatenation
     * takes O(max(M,N)) time per state.
     * - Space: O(M * N) Heap Space for the `memo` matrix (storing full strings)
     * + O(M+N) Auxiliary Stack Space.
     */
    public String printLCSMemo(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        String[][] memo = new String[m + 1][n + 1];
        return solveMemo(m, n, s1, s2, memo);
    }

    private String solveMemo(int i, int j, String s1, String s2, String[][] memo) {
        if (i == 0 || j == 0) return "";

        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = solveMemo(i - 1, j - 1, s1, s2, memo) + s1.charAt(i - 1);
        } else {
            String leftStr = solveMemo(i - 1, j, s1, s2, memo);
            String rightStr = solveMemo(i, j - 1, s1, s2, memo);
            return memo[i][j] = leftStr.length() > rightStr.length() ? leftStr : rightStr;
        }
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION + BACKTRACKING (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * Storing actual strings in a 2D matrix is highly memory inefficient.
     * The optimal industry approach is a two-step process:
     * 1. Tabulate the **lengths** of the LCS using a standard integer `dp` array.
     * 2. **Backtrack** from the bottom-right corner (`dp[m][n]`) to construct
     * the actual string, tracing the path of maximum values.
     * * EXACT DEFAULT STATE OF DP ARRAY (s1 = "abc", s2 = "ac"):
     * After Java default initialization (all 0s), before loops:
     *      ""   a   c
     * "" [ 0,  0,  0 ]
     * a  [ 0,  0,  0 ]
     * b  [ 0,  0,  0 ]
     * c  [ 0,  0,  0 ]
     * * EXACT FINAL STATE OF DP ARRAY (s1 = "abc", s2 = "ac"):
     *      ""   a   c
     * "" [ 0,  0,  0 ]
     * a  [ 0,  1,  1 ]
     * b  [ 0,  1,  1 ]
     * c  [ 0,  1,  2 ]
     * * COMPLEXITY:
     * - Time: O(M * N) for tabulation + O(M + N) for backtracking.
     * - Space: O(M * N) Heap Space for the `dp` integer matrix.
     */
    public String printLCSTabulation(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Step 1: Tabulate the LCS Lengths
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Backtrack to find the string
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            // Case 1: Characters match. It is part of the LCS.
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.append(s1.charAt(i - 1));
                i--;
                j--;
            }
            // Case 2: Max value came from the top cell
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            }
            // Case 3: Max value came from the left cell
            else {
                j--;
            }
        }

        // Since we traversed from the end to the beginning, reverse the string
        return lcs.reverse().toString();
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage) - LIMITATIONS
     * ========================================================================
     * INTUITION & CRITICAL CAVEAT:
     * Normally, we can space-optimize LCS to O(N) by keeping only the `prev`
     * and `curr` rows of the DP table.
     * HOWEVER, to **print** the sequence using backtracking, we absolutely
     * need the full historical context (the entire 2D matrix) to trace our
     * path backwards.
     * * Therefore, standard O(N) space optimization CANNOT be used to reconstruct
     * the path via simple backtracking. (See Phase 5 for advanced technique).
     * * Below is an implementation that caches Strings in a 1D array. While
     * this avoids an M*N matrix, it is still memory intensive due to String
     * object overhead, but conceptually demonstrates state collapse.
     * * COMPLEXITY:
     * - Time: O(M * N * max(M,N))
     * - Space: O(N * max(M,N)) Heap Space -> 1D array holding Strings.
     */
    public String printLCSSpaceOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        String[] prev = new String[n + 1];
        String[] curr = new String[n + 1];

        // Initialize base case
        for (int j = 0; j <= n; j++) prev[j] = "";

        for (int i = 1; i <= m; i++) {
            curr[0] = "";
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + s1.charAt(i - 1);
                } else {
                    String topStr = prev[j];
                    String leftStr = curr[j - 1];
                    curr[j] = topStr.length() > leftStr.length() ? topStr : leftStr;
                }
            }
            // Swap arrays
            String[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Hirschberg's Algorithm)
     * ========================================================================
     * INTUITION:
     * For competitive programming and rigorous interviews, it is worth knowing
     * that you CAN print the LCS in O(M*N) Time and strictly O(min(M,N)) Space.
     * * This is achieved using **Hirschberg's Algorithm**, which combines the
     * O(N) space-optimized DP with a Divide and Conquer approach. It calculates
     * the LCS length from the front and back, finds the optimal splitting point,
     * and recursively solves the smaller halves.
     * * Note: Implementation of Hirschberg's is highly advanced and usually
     * overkill for standard 45-minute interviews, but referencing it earns
     * massive bonus points for systems constrained by tight memory limits.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestCommonSubsequencePrint solver = new LongestCommonSubsequencePrint();

        System.out.println("=== Test Case 1: Standard ===");
        String s1 = "abcde", s2 = "bdgek";
        System.out.println("Recursion   : " + solver.printLCSRecursive(s1, s2));
        System.out.println("Memoization : " + solver.printLCSMemo(s1, s2));
        System.out.println("Tabulation  : " + solver.printLCSTabulation(s1, s2));
        System.out.println("Space Opt   : " + solver.printLCSSpaceOptimized(s1, s2));
        System.out.println("Expected    : bde\n");

        System.out.println("=== Test Case 2: Striver's Example ===");
        String s3 = "ferret", s4 = "herbert";
        System.out.println("Tabulation  : " + solver.printLCSTabulation(s3, s4));
        // Expected can be "erret" or "eret" depending on specific match choice,
        // but length must be 4. Both are valid subsequences.
        System.out.println("Expected    : eret (or valid length 4 sequence)\n");

        System.out.println("=== Test Case 3: No Common Subsequence ===");
        String s5 = "abc", s6 = "xyz";
        System.out.println("Tabulation  : " + solver.printLCSTabulation(s5, s6));
        System.out.println("Expected    : \n"); // Empty string

        System.out.println("=== Edge Case: Single Character Match ===");
        String s7 = "a", s8 = "a";
        System.out.println("Tabulation  : " + solver.printLCSTabulation(s7, s8));
        System.out.println("Expected    : a");
    }
}
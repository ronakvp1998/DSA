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
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE & BACKTRACKING
 * ============================================================================
 * Let f(i, j) return the LCS String of s1[0..i-1] and s2[0..j-1]
 * * Returning strings recursively creates a massive recursion tree:
 * f(5, 5) ["abcde", "bdgek"]
 * ('e' != 'k') -> Branch into two possibilities
 * /                              \
 * f(4, 5) ["abcd", "bdgek"]              f(5, 4) ["abcde", "bdge"]
 * ...                                    ('e' == 'e') -> Match!
 * return f(4, 3) + 'e'
 * * EXACT FINAL DP ARRAY STATE (s1 = "abcde", s2 = "bdgek"):
 * (Rows = s1 prefixes i, Cols = s2 prefixes j)
 * ""   b   d   g   e   k  (s2)
 * "" [ 0,  0,  0,  0,  0,  0 ]
 * a  [ 0,  0,  0,  0,  0,  0 ]
 * b  [ 0,  1,  1,  1,  1,  1 ]
 * c  [ 0,  1,  1,  1,  1,  1 ]
 * d  [ 0,  1,  2,  2,  2,  2 ]
 * e  [ 0,  1,  2,  2,  3,  3 ]  <- dp[5][5] is LCS length (3)
 * * HOW TO BACKTRACK:
 * Start at dp[5][5] (value 3). 'e' != 'k'. Compare Up (2) and Left (3).
 * Move Left to dp[5][4]. 'e' == 'e'!
 * -> Add 'e' to answer. Move diagonally to dp[4][3] (value 2).
 * Continue this until you hit a 0 row or column. Reverse the final answer.
 * ============================================================================
 */
public class LongestCommonSubsequencePrint {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We directly build and return the strings. If characters match, we include
     * the character and recursively solve for the remaining substrings.
     * If they don't match, we branch (skip s1 char OR skip s2 char) and return
     * the LONGER of the two resulting strings.
     * * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Branching factor of 2 at every mismatch.
     * - Space: O(M+N) auxiliary stack space + massive Heap space for Strings.
     * Will definitely cause Memory Limit Exceeded (MLE) or TLE.
     */
    public String printLCSRecursive(String s1, String s2) {
        return solveRecursive(s1.length(), s2.length(), s1, s2);
    }

    private String solveRecursive(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return "";

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return solveRecursive(i - 1, j - 1, s1, s2) + s1.charAt(i - 1);
        }

        String skipS1 = solveRecursive(i - 1, j, s1, s2);
        String skipS2 = solveRecursive(i, j - 1, s1, s2);

        return skipS1.length() > skipS2.length() ? skipS1 : skipS2;
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION + BACKTRACKING (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * Returning Strings recursively is too expensive. We shift our strategy:
     * 1. Use an Integer[][] table to memoize the *lengths* of the LCS in O(M*N).
     * 2. Once the table is populated, write a linear O(M+N) while-loop to trace
     * the path backwards and build the string.
     * * COMPLEXITY:
     * - Time: O(M * N) to fill memo + O(M + N) to backtrack. Total: O(M * N).
     * - Space: O(M * N) for the Heap (memo array) + O(M + N) for the Call Stack.
     */
    public String printLCSMemo(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];

        // 1. Fill the memo table with LCS lengths
        solveMemo(m, n, s1, s2, memo);

        // 2. Backtrack to build the string
        return backtrackLCS(s1, s2, memo);
    }

    private int solveMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + solveMemo(i - 1, j - 1, s1, s2, memo);
        }

        int skipS1 = solveMemo(i - 1, j, s1, s2, memo);
        int skipS2 = solveMemo(i, j - 1, s1, s2, memo);
        return memo[i][j] = Math.max(skipS1, skipS2);
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We build the standard iterative 2D DP table.
     * After building it, we start at dp[M][N].
     * - If s1[i-1] == s2[j-1], it's part of our answer! Append, and move ↖️.
     * - If they don't match, we move in the direction of the larger DP value (⬆️ or ⬅️).
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (s1="abcde", s2="bdgek"):
     * 0   1   2   3   4   5
     * 0 [  0,  0,  0,  0,  0,  0 ] <- Base case row (s1 empty)
     * 1 [  0,  0,  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0,  0,  0 ]
     * ...
     * * COMPLEXITY:
     * - Time: O(M * N) -> Nested loops + O(M + N) backtracking.
     * - Space: O(M * N) -> Heap memory for the DP table. Auxiliary Stack is eliminated!
     */
    public String printLCSTabulation(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        Integer[][] dp = new Integer[m + 1][n + 1];

        // Base cases initialized to 0
        for (int i = 0; i <= m; i++) dp[i][0] = 0;
        for (int j = 0; j <= n; j++) dp[0][j] = 0;

        // Fill table with LCS values
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }


        return backtrackLCS(s1, s2, dp);
    }

    /**
     * Universal Backtracking Helper for Top-Down and Bottom-Up
     * Uses safe null-coalescing to prevent NullPointerExceptions in Top-Down grids.
     */
    private String backtrackLCS(String s1, String s2, Integer[][] table) {
        int i = s1.length();
        int j = s2.length();
        StringBuilder lcsString = new StringBuilder();

        while (i > 0 && j > 0) {
            // If characters match, add to our result and move diagonally
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcsString.append(s1.charAt(i - 1));
                i--;
                j--;
            } else {
                // Safely handle nulls (vital if called from Memoization)
                int up = (table[i - 1][j] != null) ? table[i - 1][j] : 0;
                int left = (table[i][j - 1] != null) ? table[i][j - 1] : 0;

                // Move in the direction of the maximum value
                if (up >= left) {
                    i--; // Move Up
                } else {
                    j--; // Move Left
                }
            }
        }

        // Since we trace from the end of the strings to the start, the string
        // is built in reverse. We must reverse it back to get the correct order.
        return lcsString.reverse().toString();
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * CRITICAL LIMITATION: Space optimization (O(N) space) using two 1D arrays
     * is perfect for finding the *LENGTH* of the LCS.
     * However, to *PRINT* the LCS, we must backtrack through the ENTIRE history
     * of decisions made in the 2D grid. We cannot do this if we throw away the
     * previous rows!
     * Pure O(N) space printing requires Hirschberg's Algorithm (Divide & Conquer),
     * which is generally beyond standard interview scope.
     * This method demonstrates finding the LENGTH ONLY to complete the DP phases.
     * * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Two 1D arrays.
     */
    public int lengthLCSSpaceOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            prev = curr.clone();
        }
        return prev[n];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestCommonSubsequencePrint solver = new LongestCommonSubsequencePrint();

        System.out.println("=== Test Case 1: Standard Example ===");
        String s1 = "abcde", s2 = "bdgek";
        System.out.println("Recursion String  : " + solver.printLCSRecursive(s1, s2));
        System.out.println("Memoization String: " + solver.printLCSMemo(s1, s2));
        System.out.println("Tabulation String : " + solver.printLCSTabulation(s1, s2));
        System.out.println("Space Opt Length  : " + solver.lengthLCSSpaceOptimized(s1, s2));
        System.out.println("Expected          : bde\n");

        System.out.println("=== Test Case 2: Animals ===");
        String s3 = "ferret", s4 = "herbert";
        System.out.println("Tabulation String : " + solver.printLCSTabulation(s3, s4));
        System.out.println("Expected          : eret\n");

        System.out.println("=== Test Case 3: Completely Disjoint ===");
        String s5 = "abc", s6 = "xyz";
        System.out.println("Tabulation String : " + solver.printLCSTabulation(s5, s6));
        System.out.println("Expected          : (empty string)\n");

        System.out.println("=== Test Case 4: Identical Strings ===");
        String s7 = "hello", s8 = "hello";
        System.out.println("Tabulation String : " + solver.printLCSTabulation(s7, s8));
        System.out.println("Expected          : hello\n");
    }
}
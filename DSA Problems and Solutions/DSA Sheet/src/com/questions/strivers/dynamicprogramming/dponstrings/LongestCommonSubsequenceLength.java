package com.questions.strivers.dynamicprogramming.dponstrings;
/**
 * ============================================================================
 * 1143. Longest Common Subsequence (Striver DP-26)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings 'text1' and 'text2', return the length of their longest
 * common subsequence. If there is no common subsequence, return 0.
 * * A subsequence of a string is a new string generated from the original string
 * with some characters (can be none) deleted without changing the relative
 * order of the remaining characters.
 * * * CONSTRAINTS:
 * - 1 <= text1.length, text2.length <= 1000
 * - text1 and text2 consist of only lowercase English characters.
 * * * EXAMPLES:
 * Example 1:
 * Input: text1 = "abcde", text2 = "ace" | Output: 3
 * Explanation: The longest common subsequence is "ace" and its length is 3.
 * * Example 2:
 * Input: text1 = "abc", text2 = "abc" | Output: 3
 * Explanation: The longest common subsequence is "abc" and its length is 3.
 * * Example 3:
 * Input: text1 = "abc", text2 = "def" | Output: 0
 * Explanation: There is no such common subsequence, so the result is 0.
 * * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (text1 = "abc", text2 = "ac")
 * ============================================================================
 * Let f(i, j) represent the LCS of text1[0..i-1] and text2[0..j-1].
 * Indices represent the length of the string prefixes being compared.
 * *                     f(3, 2) ["abc", "ac"]
 *                      ('c' == 'c') -> Match!
 *                          |
 *                  1 + f(2, 1) ["ab", "a"]
 *                  ('b' != 'a') -> Mismatch!
 *                  /                      \
 *          (Skip 'b' in text1)              (Skip 'a' in text2)
 *          f(1, 1) ["a", "a"]               f(2, 0) ["ab", ""]
 *          ('a' == 'a') Match!              (Base: text2 empty) -> 0
 *              |
 *          1 + f(0, 0) ["", ""]
 *          (Base: both empty) -> 0
 *
 * * Total LCS = 1 + (1 + 0) = 2.
 * * * COMPLETE FINAL DP ARRAY STATE (text1 = "abc", text2 = "ac"):
 * (Rows = text1 prefixes: "", "a", "b", "c")
 * (Cols = text2 prefixes: "", "a", "c")
 * *   ""   a   c
 * "" [ 0,  0,  0 ]
 * a  [ 0,  1,  1 ]
 * b  [ 0,  1,  1 ]
 * c  [ 0,  1,  2 ] <- Final Answer at bottom right: 2
 */

public class LongestCommonSubsequenceLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We compare the strings starting from the end (using lengths of prefixes).
     * 1. If the current characters match (text1[i-1] == text2[j-1]), they must be
     * part of the LCS. We add 1 to the result and shrink both strings.
     * 2. If they DO NOT match, the longest common subsequence could either come
     * from skipping the current character in text1 OR skipping the current
     * character in text2. We explore both paths and take the maximum.
     * 3. Base Case: If either string becomes empty (length 0), the common
     * subsequence length is 0.
     * * * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Exponential. In the worst case (no matching chars),
     * we branch twice at every step.
     * - Space: O(M+N) -> Auxiliary Stack Space for the maximum recursion depth.
     */
    public int longestCommonSubsequenceRecursive(String text1, String text2) {
        return solveRecursive(text1.length(), text2.length(), text1, text2);
    }

    private int solveRecursive(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return 0; // Base case

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + solveRecursive(i - 1, j - 1, s1, s2);
        } else {
            int skipS1 = solveRecursive(i - 1, j, s1, s2);
            int skipS2 = solveRecursive(i, j - 1, s1, s2);
            return Math.max(skipS1, skipS2);
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursion tree explores the same states multiple times (e.g., f(1,1)
     * might be reached through different paths of mismatches). We introduce a
     * 2D `memo` array where `memo[i][j]` stores the LCS for prefixes of length
     * `i` and `j`. We calculate each state only once.
     * * * COMPLEXITY:
     * - Time: O(M * N) -> There are M * N unique states, each computed in O(1).
     * - Space: O(M * N) Heap Space for the `memo` matrix + O(M+N) Stack Space.
     */
    public int longestCommonSubsequenceMemo(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];
        return solveMemo(m, n, text1, text2, memo);
    }

    private int solveMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;

        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + solveMemo(i - 1, j - 1, s1, s2, memo);
        } else {
            return memo[i][j] = Math.max(
                    solveMemo(i - 1, j, s1, s2, memo),
                    solveMemo(i, j - 1, s1, s2, memo)
            );
        }
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We convert the top-down approach into an iterative bottom-up approach.
     * We shift our index definitions slightly: `dp[i][j]` will represent the
     * LCS of `text1` up to index `i-1` and `text2` up to index `j-1`. This
     * allows index 0 to gracefully handle the "empty string" base cases natively.
     * * * EXACT DEFAULT STATE OF DP ARRAY (text1 = "abc", text2 = "ac"):
     * Right after Java default initialization (all 0s), before loops:
     *      ""   a   c
     * "" [ 0,  0,  0 ]
     * a  [ 0,  0,  0 ]
     * b  [ 0,  0,  0 ]
     * c  [ 0,  0,  0 ]
     * * * COMPLEXITY:
     * - Time: O(M * N) -> Two nested loops.
     * - Space: O(M * N) Heap Space for the 2D DP table. Zero stack overhead.
     */
    public int longestCommonSubsequenceTabulation(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // Java initializes arrays to 0 by default, covering our base cases
        // where i == 0 or j == 0.
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1]; // Diagonal up-left
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // Top or Left
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
     * Notice the recurrence relation in Phase 3:
     * `dp[i][j]` relies ONLY on:
     * - `dp[i-1][j-1]` (Previous row, previous column)
     * - `dp[i-1][j]`   (Previous row, same column)
     * - `dp[i][j-1]`   (Current row, previous column)
     * We only ever need the *current* row and the *previous* row. We can discard
     * older rows, reducing the 2D matrix to two 1D arrays: `prev` and `curr`.
     * * * COMPLEXITY:
     * - Time: O(M * N) -> Iteration count remains the same.
     * - Space: O(N) Heap Space -> Two 1D arrays of size N+1 instead of an M*N matrix.
     */
    public int longestCommonSubsequenceSpaceOptimized(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Swap arrays: current row becomes the previous row for the next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        // After the final swap, 'prev' points to the last computed row
        return prev[n];
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Print the LCS String)
     * ========================================================================
     * INTUITION:
     * While finding the *length* of the LCS only requires O(N) space, finding
     * the *actual string* requires the full O(M*N) DP table to backtrack.
     * We start at the bottom-right corner `dp[M][N]`.
     * 1. If characters match, include it in the result and move diagonally up-left.
     * 2. If they don't match, move to the cell (top or left) that gave the maximum value.
     * * * COMPLEXITY:
     * - Time: O(M * N) for tabulation + O(M + N) for backtracking.
     * - Space: O(M * N) for DP table + O(M + N) for the StringBuilder.
     */
    public String printLongestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        // 1. Build the Tabulation Table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 2. Backtrack to find the string
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Character is part of LCS, append and move diagonal
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Max came from the top
                i--;
            } else {
                // Max came from the left
                j--;
            }
        }

        // The string was built backwards, so reverse it
        return lcs.reverse().toString();
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestCommonSubsequenceLength solver = new LongestCommonSubsequenceLength();

        System.out.println("=== Test Case 1: Standard ===");
        String s1 = "abcde", t1 = "ace";
        System.out.println("Recursion  : " + solver.longestCommonSubsequenceRecursive(s1, t1));
        System.out.println("Memoization: " + solver.longestCommonSubsequenceMemo(s1, t1));
        System.out.println("Tabulation : " + solver.longestCommonSubsequenceTabulation(s1, t1));
        System.out.println("Space Opt  : " + solver.longestCommonSubsequenceSpaceOptimized(s1, t1));
        System.out.println("Actual LCS : " + solver.printLongestCommonSubsequence(s1, t1));
        System.out.println("Expected   : 3\n");

        System.out.println("=== Test Case 2: Identical Strings ===");
        String s2 = "abc", t2 = "abc";
        System.out.println("Space Opt  : " + solver.longestCommonSubsequenceSpaceOptimized(s2, t2));
        System.out.println("Actual LCS : " + solver.printLongestCommonSubsequence(s2, t2));
        System.out.println("Expected   : 3\n");

        System.out.println("=== Test Case 3: No Common Subsequence ===");
        String s3 = "abc", t3 = "def";
        System.out.println("Space Opt  : " + solver.longestCommonSubsequenceSpaceOptimized(s3, t3));
        System.out.println("Expected   : 0\n");

        System.out.println("=== Edge Case: Single Character Match ===");
        String s4 = "a", t4 = "a";
        System.out.println("Space Opt  : " + solver.longestCommonSubsequenceSpaceOptimized(s4, t4));
        System.out.println("Expected   : 1");
    }
}
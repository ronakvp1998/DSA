package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * LONGEST COMMON SUBSTRING (Striver DP-27)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings 's1' and 's2', find the length of the longest common substring.
 * A substring is a contiguous sequence of characters within the string.
 * * CONSTRAINTS:
 * - 1 <= s1.length, s2.length <= 1000
 * - s1 and s2 consist of lowercase English characters.
 * * EXAMPLES:
 * Example 1:
 * Input: s1 = "abcjklp", s2 = "acjkp" | Output: 3
 * Explanation: The longest common substring is "cjk".
 * * Example 2:
 * Input: s1 = "wasdijkl", s2 = "wsdijk" | Output: 5
 * Explanation: The longest common substring is "sdijk".
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "STREAK" LOGIC
 * ============================================================================
 * Unlike Longest Common Subsequence (LCS), a Substring must be contiguous.
 * Think of it as a "matching streak."
 * - If characters match: Streak continues (1 + previous diagonal).
 * - If characters mismatch: Streak breaks (Reset to 0).
 * * * EXACT FINAL DP ARRAY STATE (s1 = "abc", s2 = "abce"):
 * (Rows = s1 prefixes, Cols = s2 prefixes)
 * ""   a   b   c   e
 * "" [ 0,  0,  0,  0,  0 ]
 * a  [ 0,  1,  0,  0,  0 ]
 * b  [ 0,  0,  2,  0,  0 ]
 * c  [ 0,  0,  0,  3,  0 ] <- Max value in the entire table is the answer (3).
 * ============================================================================
 */
public class LongestCommonSubstringLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We use a recursive function that returns the length of the common suffix
     * ending EXACTLY at i and j. To find the global maximum, we must call this
     * function for every possible pair of (i, j).
     * * COMPLEXITY:
     * - Time: O(3^(N+M)) or O(N*M * 2^min(N,M)) depending on implementation.
     * - Space: O(N+M) auxiliary stack space.
     */
    public int lcsSubstringRecursive(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int maxLen = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                maxLen = Math.max(maxLen, solveRecursive(i, j, s1, s2));
            }
        }
        return maxLen;
    }

    private int solveRecursive(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return 0;
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + solveRecursive(i - 1, j - 1, s1, s2);
        }
        return 0; // Streak broken
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * We cache the results of `solveRecursive(i, j)`. Since `memo[i][j]` only
     * represents the substring ending at that specific index, we iterate
     * through all indices to find the maximum streak.
     * * COMPLEXITY:
     * - Time: O(N * M) -> Each cell is computed once.
     * - Space: O(N * M) Heap space + O(min(N,M)) Stack space.
     */
    public int lcsSubstringMemo(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        Integer[][] memo = new Integer[n + 1][m + 1];
        int maxLen = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                maxLen = Math.max(maxLen, solveMemo(i, j, s1, s2, memo));
            }
        }
        return maxLen;
    }

    private int solveMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + solveMemo(i - 1, j - 1, s1, s2, memo);
        }
        return memo[i][j] = 0;
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We fill a 2D table where `dp[i][j]` is the length of the common substring
     * ending at s1[i-1] and s2[j-1]. If characters don't match, we set it to 0.
     * * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (s1="abc", s2="ab", n=3, m=2):
     * 0   1   2
     * 0 [ 0,  0,  0 ] <- Base case row (empty s1)
     * 1 [ 0,  0,  0 ]
     * 2 [ 0,  0,  0 ]
     * 3 [ 0,  0,  0 ]
     * * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M) Heap memory.
     */
    public int lcsSubstringTabulation(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        int maxLen = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    maxLen = Math.max(maxLen, dp[i][j]);
                } else {
                    dp[i][j] = 0; // Key difference from LCS: No Math.max here!
                }
            }
        }
        return maxLen;
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * To compute `dp[i][j]`, we only ever look at `dp[i-1][j-1]`.
     * We can use a single 1D array, but we must iterate `j` backwards to avoid
     * overwriting the value from the "previous diagonal" before we use it.
     * * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(M) -> Single 1D array.
     */
    public int lcsSubstringSpaceOptimized(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[] dp = new int[m + 1];
        int maxLen = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 1; j--) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[j] = 1 + dp[j - 1];
                    maxLen = Math.max(maxLen, dp[j]);
                } else {
                    dp[j] = 0; // Must explicitly reset for substring streak
                }
            }
        }
        return maxLen;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestCommonSubstringLength solver = new LongestCommonSubstringLength();

        String s1 = "abcjklp", s2 = "acjkp";
        System.out.println("Test 1 Result: " + solver.lcsSubstringSpaceOptimized(s1, s2)); // Expected: 3

        String s3 = "wasdijkl", s4 = "wsdijk";
        System.out.println("Test 2 Result: " + solver.lcsSubstringSpaceOptimized(s3, s4)); // Expected: 5
    }
}
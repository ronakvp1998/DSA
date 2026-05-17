package com.questions.strivers.dynamicprogramming.dponstrings;


/**
 * ============================================================================
 * 1312. MINIMUM INSERTION STEPS TO MAKE A STRING PALINDROME
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given a string s. In one step you can insert any character at any index of the string.
 * Return the minimum number of steps to make s a palindrome.
 * A Palindrome String is one that reads the same backward as well as forward.
 * * CONSTRAINTS:
 * - 1 <= s.length <= 500
 * - s consists of lowercase English letters.
 * * EXAMPLES:
 * Example 1:
 * Input: s = "zzazz" | Output: 0
 * Explanation: The string "zzazz" is already palindrome we do not need any insertions.
 * * Example 2:
 * Input: s = "mbadm" | Output: 2
 * Explanation: String can be "mbdadbm" or "mdbabdm".
 * * Example 3:
 * Input: s = "leetcode" | Output: 5
 * Explanation: Inserting 5 characters the string becomes "leetcodocteel".
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "LPS" FORMULA
 * ============================================================================
 * The core mathematical trick:
 * Every string already contains a palindromic subsequence hidden inside it.
 * The characters that are NOT part of this longest palindromic subsequence
 * are the ones breaking the palindrome.
 * Therefore, to make the whole string a palindrome, we just need to insert a
 * matching pair for every "broken" character.
 * * Formula:
 * Minimum Insertions = Total Length of String - Longest Palindromic Subsequence (LPS)
 * And we know: LPS(s) = LCS(s, reverse(s))
 * * Example: s = "mbadm" (Length = 5)
 * reverse(s) = "mdabm"
 * LPS("mbadm") = 3 (The subsequence is "mam" or "mbm" or "mdm")
 * Minimum Insertions = 5 - 3 = 2.
 * * EXACT FINAL DP ARRAY STATE (s1 = "mbadm", s2 = "mdabm"):
 * (Rows = s1 prefixes, Cols = s2 prefixes)
 * ""   m   d   a   b   m
 * "" [ 0,  0,  0,  0,  0,  0 ]
 * m  [ 0,  1,  1,  1,  1,  1 ]
 * b  [ 0,  1,  1,  1,  2,  2 ]
 * a  [ 0,  1,  1,  2,  2,  2 ]
 * d  [ 0,  1,  2,  2,  2,  2 ]
 * m  [ 0,  1,  2,  2,  2,  3 ]  <- dp[5][5] is the LPS length (3)
 * Insertions = Length (5) - LPS (3) = 2.
 * ============================================================================
 */
public class MinInsertionsToPalindrome {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We reverse the string `s` to create `s2`. We then use the standard recursive
     * LCS algorithm to find the Longest Palindromic Subsequence length.
     * Finally, we return `s.length() - lcsLength`.
     * * COMPLEXITY:
     * - Time: O(2^(2N)) -> Branching factor of 2 at every mismatch for strings of length N.
     * - Space: O(N) -> Auxiliary stack space for recursion depth up to 2N.
     */
    public int minInsertionsRecursive(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        int lps = lcsRecursive(n, n, s, reverseS);
        return n - lps;
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
     * subproblem results.
     * * COMPLEXITY:
     * - Time: O(N^2) -> Every (i, j) combination is calculated exactly once.
     * - Space: O(N^2) for the Heap (memo array) + O(N) for the Call Stack.
     */
    public int minInsertionsMemo(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        Integer[][] memo = new Integer[n + 1][n + 1];

        int lps = lcsMemo(n, n, s, reverseS, memo);
        return n - lps;
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
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (s = "mbadm", n = 5):
     * 0   1   2   3   4   5
     * 0 [  0,  0,  0,  0,  0,  0 ] <- Base case row (s1 empty)
     * 1 [  0,  0,  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0,  0,  0 ]
     * 3 [  0,  0,  0,  0,  0,  0 ]
     * 4 [  0,  0,  0,  0,  0,  0 ]
     * 5 [  0,  0,  0,  0,  0,  0 ]
     * (Note: Java primitive int arrays default to 0, handling the base case perfectly)
     * * COMPLEXITY:
     * - Time: O(N^2) -> Strictly nested loops.
     * - Space: O(N^2) -> Heap memory for the DP table. Call Stack space is eliminated.
     */
    public int minInsertionsTabulation(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == reverseS.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int lps = dp[n][n];
        return n - lps;
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * In the Tabulation matrix, computing row `i` only ever requires values from
     * row `i-1` (the previous row) and the current row `i`. We do not need all `N`
     * rows in memory. We can reduce the O(N^2) space to O(N) by using two 1D arrays:
     * `prev` (representing row i-1) and `curr` (representing row i).
     * * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N) -> Only two 1D arrays of size N+1.
     */
    public int minInsertionsSpaceOptimized(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == reverseS.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Move current state to previous for the next iteration
            prev = curr.clone();
        }

        int lps = prev[n];
        return n - lps;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        MinInsertionsToPalindrome solver = new MinInsertionsToPalindrome();

        System.out.println("=== Test Case 1: Already Palindrome ===");
        String s1 = "zzazz";
        System.out.println("Recursion  : " + solver.minInsertionsRecursive(s1));
        System.out.println("Memoization: " + solver.minInsertionsMemo(s1));
        System.out.println("Tabulation : " + solver.minInsertionsTabulation(s1));
        System.out.println("Space Opt  : " + solver.minInsertionsSpaceOptimized(s1));
        System.out.println("Expected   : 0\n");

        System.out.println("=== Test Case 2: Standard Example ===");
        String s2 = "mbadm";
        System.out.println("Space Opt  : " + solver.minInsertionsSpaceOptimized(s2));
        System.out.println("Expected   : 2 (LPS is 3, 5 - 3 = 2)\n");

        System.out.println("=== Test Case 3: Longer String ===");
        String s3 = "leetcode";
        System.out.println("Space Opt  : " + solver.minInsertionsSpaceOptimized(s3));
        System.out.println("Expected   : 5\n");

        System.out.println("=== Test Case 4: No Common Subsequence (except length 1) ===");
        String s4 = "abcd";
        System.out.println("Space Opt  : " + solver.minInsertionsSpaceOptimized(s4));
        System.out.println("Expected   : 3\n");
    }
}
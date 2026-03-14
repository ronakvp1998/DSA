package com.questions.strivers.dynamicprogramming.dponstrings;
/**
 * ============================================================================
 * 516. Longest Palindromic Subsequence
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given a string s, find the longest palindromic subsequence's length in s.
 * A subsequence is a sequence that can be derived from another sequence by
 * deleting some or no elements without changing the order of the remaining
 * elements.
 * * CONSTRAINTS:
 * - 1 <= s.length <= 1000
 * - s consists only of lowercase English letters.
 * * EXAMPLES:
 * Example 1:
 * Input: s = "bbbab" | Output: 4
 * Explanation: One possible longest palindromic subsequence is "bbbb".
 * * Example 2:
 * Input: s = "cbbd" | Output: 2
 * Explanation: One possible longest palindromic subsequence is "bb".
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s = "cbbd")
 * ============================================================================
 * Let f(i, j) represent the length of the longest palindromic subsequence
 * in the substring s[i...j].
 * * f(0, 3) ["cbbd"]
 * ('c' != 'd') -> Mismatch! Take max of ignoring left or ignoring right.
 * /                                   \
 * (Ignore 'c')                        (Ignore 'd')
 * f(1, 3) ["bbd"]                     f(0, 2) ["cbb"]
 * ('b' != 'd')                        ('c' != 'b')
 * /            \                      /            \
 * f(2, 3)["bd"]  f(1, 2)["bb"]        f(1, 2)["bb"]  f(0, 1)["cb"]
 * (b!=d)         ('b'=='b')           ('b'=='b')     (c!=b)
 * -> 1           -> 2 + f(2,1)=0      -> 2           -> 1
 * -> 2                 -> 2
 * * Global Max = 2.
 * * * COMPLETE FINAL DP ARRAY STATE (s = "cbbd"):
 * (Rows = start index 'i', Cols = end index 'j')
 * * c  b  b  d
 * c [ 1, 1, 2, 2 ]  <- Final Answer at top right: 2
 * b [ 0, 1, 2, 2 ]
 * b [ 0, 0, 1, 1 ]
 * d [ 0, 0, 0, 1 ]
 */

public class LongestPalindromicSubsequence {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * This is a classic Interval DP problem. We use two pointers, `i` at the
     * start and `j` at the end of the string.
     * 1. If `s[i] == s[j]`, these two characters form the outer shell of a
     * palindrome. We add 2 to our length and shrink the interval `(i+1, j-1)`.
     * 2. If `s[i] != s[j]`, the outer characters don't match. The longest
     * palindrome must exist by either dropping the left character `(i+1, j)`
     * or dropping the right character `(i, j-1)`. We take the maximum.
     * 3. Base cases: If `i == j`, it's a single character (palindrome of len 1).
     * If `i > j`, the interval is empty (len 0).
     * * COMPLEXITY:
     * - Time: O(2^N) -> In the worst case (no matching characters), every state
     * branches into two possibilities.
     * - Space: O(N) -> Auxiliary Stack Space for recursion depth up to N.
     */
    public int longestPalindromeSubseqRecursive(String s) {
        return solveRecursive(0, s.length() - 1, s);
    }

    private int solveRecursive(int i, int j, String s) {
        if (i > j) return 0;
        if (i == j) return 1;

        if (s.charAt(i) == s.charAt(j)) {
            return 2 + solveRecursive(i + 1, j - 1, s);
        } else {
            return Math.max(solveRecursive(i + 1, j, s), solveRecursive(i, j - 1, s));
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * Our recursion tree shows overlapping subproblems (e.g., `f(1, 2)` is
     * calculated multiple times). We cache the answers for interval `(i, j)`
     * in a 2D `memo` array to avoid recalculating known states.
     * * COMPLEXITY:
     * - Time: O(N^2) -> There are N^2 possible intervals `(i, j)`, computed once.
     * - Space: O(N^2) Heap Space for `memo` matrix + O(N) Stack Space.
     */
    public int longestPalindromeSubseqMemo(String s) {
        int n = s.length();
        Integer[][] memo = new Integer[n][n];
        return solveMemo(0, n - 1, s, memo);
    }

    private int solveMemo(int i, int j, String s, Integer[][] memo) {
        if (i > j) return 0;
        if (i == j) return 1;

        if (memo[i][j] != null) return memo[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            return memo[i][j] = 2 + solveMemo(i + 1, j - 1, s, memo);
        } else {
            return memo[i][j] = Math.max(
                    solveMemo(i + 1, j, s, memo),
                    solveMemo(i, j - 1, s, memo)
            );
        }
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We convert our recursive memoization to iterative tabulation.
     * Since `dp[i][j]` depends on `dp[i+1][j-1]` (next row) and `dp[i+1][j]`
     * (next row), we MUST process the `i` loops strictly backwards (from N-1 to 0).
     * The `j` loop goes forwards from `i+1` to N-1.
     * * EXACT DEFAULT STATE OF DP ARRAY (s = "cbbd", n = 4):
     * After base case initialization (dp[i][i] = 1), before nested loops:
     * c  b  b  d
     * c [ 1, 0, 0, 0 ]
     * b [ 0, 1, 0, 0 ]
     * b [ 0, 0, 1, 0 ]
     * d [ 0, 0, 0, 1 ]
     * * COMPLEXITY:
     * - Time: O(N^2) -> Double nested loop iterating over half the matrix.
     * - Space: O(N^2) Heap Space for the 2D DP matrix. Zero Stack Space.
     */
    public int longestPalindromeSubseqTabulation(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // Base case: every single character is a palindrome of length 1
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // i goes strictly backwards so dp[i+1] is already computed
        for (int i = n - 1; i >= 0; i--) {
            // j starts right after i
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1]; // Full string interval
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * Look closely at the recurrence:
     * `dp[i][j]` requires `dp[i+1][j-1]` and `dp[i+1][j]` (from the PREVIOUSLY
     * computed row `i+1`), and `dp[i][j-1]` (from the CURRENT row `i`).
     * We can discard all older rows and use two 1D arrays: `prev` (holding row i+1)
     * and `curr` (holding row i).
     * * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N) Heap Space -> Two 1D arrays of size N.
     */
    public int longestPalindromeSubseqSpaceOptimized(String s) {
        int n = s.length();
        int[] prev = new int[n];
        int[] curr = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            curr[i] = 1; // Base case: single character interval (i == j)

            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    curr[j] = 2 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            // Swap arrays: current row becomes the next iteration's "i+1"
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        // Since we swapped at the end of the last loop (i=0), 'prev' holds the final answer
        return prev[n - 1];
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (LCS Reduction - The "Striver" Way)
     * ========================================================================
     * INTUITION:
     * There is an incredibly elegant alternative way to solve this.
     * The Longest Palindromic Subsequence of a string 's' is EXACTLY EQUAL TO
     * the Longest Common Subsequence (LCS) of 's' and its reverse!
     * Since we have already mastered the LCS space-optimized template in DP-26,
     * we can reuse that exact code block with zero modifications to the core logic.
     * * COMPLEXITY:
     * - Time: O(N^2) -> Standard LCS time complexity.
     * - Space: O(N) -> Single 1D array for LCS space optimization.
     */
    public int longestPalindromeSubseqLCSVariant(String s) {
        String revS = new StringBuilder(s).reverse().toString();
        int n = s.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        // Standard Longest Common Subsequence logic between 's' and 'revS'
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == revS.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestPalindromicSubsequence solver = new LongestPalindromicSubsequence();

        System.out.println("=== Test Case 1: Standard ===");
        String s1 = "bbbab";
        System.out.println("Recursion  : " + solver.longestPalindromeSubseqRecursive(s1));
        System.out.println("Memoization: " + solver.longestPalindromeSubseqMemo(s1));
        System.out.println("Tabulation : " + solver.longestPalindromeSubseqTabulation(s1));
        System.out.println("Space Opt  : " + solver.longestPalindromeSubseqSpaceOptimized(s1));
        System.out.println("LCS Variant: " + solver.longestPalindromeSubseqLCSVariant(s1));
        System.out.println("Expected   : 4 (bbbb)\n");

        System.out.println("=== Test Case 2: Even Length ===");
        String s2 = "cbbd";
        System.out.println("Space Opt  : " + solver.longestPalindromeSubseqSpaceOptimized(s2));
        System.out.println("LCS Variant: " + solver.longestPalindromeSubseqLCSVariant(s2));
        System.out.println("Expected   : 2 (bb)\n");

        System.out.println("=== Edge Case: Single Character ===");
        String s3 = "a";
        System.out.println("Space Opt  : " + solver.longestPalindromeSubseqSpaceOptimized(s3));
        System.out.println("Expected   : 1\n");

        System.out.println("=== Edge Case: Already Palindrome ===");
        String s4 = "racecar";
        System.out.println("Space Opt  : " + solver.longestPalindromeSubseqSpaceOptimized(s4));
        System.out.println("Expected   : 7");
    }
}
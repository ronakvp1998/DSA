package strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * 516. LONGEST PALINDROMIC SUBSEQUENCE (LCS APPROACH)
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
 * CONCEPTUAL VISUALIZATION: THE "LCS" BRIDGE (s = "cbbd")
 * ============================================================================
 * The core trick: The Longest Palindromic Subsequence of a string 'S' is exactly
 * equal to the Longest Common Subsequence (LCS) of 'S' and 'reverse(S)'.
 * * Let s1 = "cbbd"
 * Let s2 = "dbbc" (reverse of s1)
 * * RECURSION TREE (Partial for s1="cbb", s2="dbb"):
 * Let f(i, j) = LCS of s1[0..i-1] and s2[0..j-1]
 * * f(3, 3) ["cbb", "dbb"]
 * ('b' != 'b' -> Wait, s1[2]='b', s2[2]='b' Match!)
 * |
 * 1 + f(2, 2) ["cb", "db"]
 * ('b' != 'b' -> s1[1]='b', s2[1]='b' Match!)
 * |
 * 1 + f(1, 1) ["c", "d"]
 * ('c' != 'd' -> Mismatch!)
 * /                         \
 * f(0, 1) ["", "d"]                 f(1, 0) ["c", ""]
 * (Base: 0)                         (Base: 0)
 * * * EXACT FINAL DP ARRAY STATE (s1 = "cbbd", s2 = "dbbc"):
 * (Rows = s1 prefixes, Cols = s2 prefixes)
 * ""   d   b   b   c
 * "" [ 0,   0,  0,  0,  0 ]
 * c  [ 0,   0,  0,  0,  1 ]
 * b  [ 0,   0,  1,  1,  1 ]
 * b  [ 0,   0,  1,  2,  2 ]
 * d  [ 0,   1,  1,  2,  2 ]  <- dp[4][4] is the max length (2)
 * ============================================================================
 */
public class LongestPalindromicSubsequenceLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We reverse the string `s` to create `s2`. We then use the standard recursive
     * LCS algorithm:
     * 1. If characters match, add 1 and move both pointers diagonally.
     * 2. If they mismatch, branch into two paths (skip s1 char OR skip s2 char)
     * and take the maximum of the two.
     * * COMPLEXITY:
     * - Time: O(2^(2N)) -> Branching factor of 2 at every mismatch for strings of length N.
     * - Space: O(N) -> Auxiliary stack space for recursion depth up to 2N.
     */
    public int lpsRecursive(String s) {
        String reverseS = new StringBuilder(s).reverse().toString();
        return lcsRecursive(s.length(), s.length(), s, reverseS);
    }

    private int lcsRecursive(int i, int j, String s1, String s2) {
        // Base case: If either string is exhausted
        if (i == 0 || j == 0) {
            return 0;
        }

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + lcsRecursive(i - 1, j - 1, s1, s2);
        }

        int skipS1 = lcsRecursive(i - 1, j, s1, s2);
        int skipS2 = lcsRecursive(i, j - 1, s1, s2);

        return Math.max(skipS1, skipS2);
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
    public int lpsMemo(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        Integer[][] memo = new Integer[n + 1][n + 1];

        return lcsMemo(n, n, s, reverseS, memo);
    }

    private int lcsMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;

        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + lcsMemo(i - 1, j - 1, s1, s2, memo);
        }

        int skipS1 = lcsMemo(i - 1, j, s1, s2, memo);
        int skipS2 = lcsMemo(i, j - 1, s1, s2, memo);

        return memo[i][j] = Math.max(skipS1, skipS2);
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * Convert the recursion stack into iterative loops using a standard 2D DP
     * table for LCS.
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (s = "cbbd", n = 4):
     * 0   1   2   3   4
     * 0 [  0,  0,  0,  0,  0 ] <- Base case row (s1 empty)
     * 1 [  0,  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0,  0 ]
     * 3 [  0,  0,  0,  0,  0 ]
     * 4 [  0,  0,  0,  0,  0 ]
     * (Note: Java primitive int arrays default to 0, handling the base case perfectly)
     * * COMPLEXITY:
     * - Time: O(N^2) -> Strictly nested loops.
     * - Space: O(N^2) -> Heap memory for the DP table. Call Stack space is eliminated.
     */
    public int lpsTabulation(String s) {
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
        return dp[n][n];
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
    public int lpsSpaceOptimized(String s) {
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
        return prev[n];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestPalindromicSubsequenceLength solver = new LongestPalindromicSubsequenceLength();

        System.out.println("=== Test Case 1: Standard Example ===");
        String s1 = "bbbab";
        System.out.println("Recursion  : " + solver.lpsRecursive(s1));
        System.out.println("Memoization: " + solver.lpsMemo(s1));
        System.out.println("Tabulation : " + solver.lpsTabulation(s1));
        System.out.println("Space Opt  : " + solver.lpsSpaceOptimized(s1));
        System.out.println("Expected   : 4 (bbbb)\n");

        System.out.println("=== Test Case 2: Even Length Example ===");
        String s2 = "cbbd";
        System.out.println("Space Opt  : " + solver.lpsSpaceOptimized(s2));
        System.out.println("Expected   : 2 (bb)\n");

        System.out.println("=== Test Case 3: Fully Palindromic ===");
        String s3 = "racecar";
        System.out.println("Space Opt  : " + solver.lpsSpaceOptimized(s3));
        System.out.println("Expected   : 7\n");

        System.out.println("=== Test Case 4: Single Character ===");
        String s4 = "a";
        System.out.println("Space Opt  : " + solver.lpsSpaceOptimized(s4));
        System.out.println("Expected   : 1\n");
    }
}
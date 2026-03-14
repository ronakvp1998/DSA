package com.questions.strivers.dynamicprogramming.dponstrings;
/**
 * ============================================================================
 * 583. Delete Operation for Two Strings
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings 'word1' and 'word2', return the minimum number of steps
 * required to make 'word1' and 'word2' the same.
 * * In one step, you can delete exactly one character in either string.
 * * * CONSTRAINTS:
 * - 1 <= word1.length, word2.length <= 500
 * - word1 and word2 consist of only lowercase English letters.
 * * * EXAMPLES:
 * Example 1:
 * Input: word1 = "sea", word2 = "eat" | Output: 2
 * Explanation: You need one step to make "sea" to "ea" and another step to
 * make "eat" to "ea".
 * * Example 2:
 * Input: word1 = "leetcode", word2 = "etco" | Output: 4
 * Explanation:
 * 1. Delete 'l', 'e', 'd', 'e' from "leetcode" -> "etco"
 * 2. "etco" remains as is. Total 4 deletions.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE & DP ARRAY (word1="sea", word2="eat")
 * ============================================================================
 * Let f(i, j) represent the minimum deletions to match prefix word1[0..i] and word2[0..j].
 * Indices represent the length of the string prefixes being compared.
 * * f(3, 3) ["sea", "eat"]
 * ('a' != 't')
 * /              \
 * (delete 'a') /                \ (delete 't')
 * f(2, 3) ["se", "eat"]       f(3, 2) ["sea", "ea"]
 * ('e' != 't')                 ('a' == 'a') -> No deletion!
 * /          \                      |
 * /            \                     |
 * f(1,3)["s","eat"]  f(2,2)["se","ea"]        f(2, 1) ["se", "e"]
 * ('e' == 'e') -> No deletion!
 * |
 * |
 * f(1, 0) ["s", ""]
 * | (Base case: string 2 is empty)
 * Requires 1 deletion (delete 's')
 * * * COMPLETE FINAL DP ARRAY STATE:
 * (Rows = word1 prefixes: "", "s", "e", "a")
 * (Cols = word2 prefixes: "", "e", "a", "t")
 * * ""   e   a   t
 * "" [  0,  1,  2,  3 ]
 * s [  1,  2,  3,  4 ]
 * e [  2,  1,  2,  3 ]
 * a [  3,  2,  1,  2 ]  <- Final Answer at bottom right: 2
 */

public class DeleteOperationTwoStrings {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We compare the strings from right to left (using lengths of prefixes).
     * 1. If the last characters match, they are part of the final identical string.
     * No deletions needed for them. We move diagonally: `solve(i-1, j-1)`.
     * 2. If they don't match, we have two choices:
     * - Delete the character from word1 (costs 1 step) -> `1 + solve(i-1, j)`
     * - Delete the character from word2 (costs 1 step) -> `1 + solve(i, j-1)`
     * We take the minimum of these two choices.
     * 3. Base cases: If one string becomes empty (length 0), we must delete all
     * remaining characters in the other string.
     * * COMPLEXITY:
     * - Time: O(2^(M+N)) where M and N are lengths of the strings. In the worst case
     * (no matching characters), every state branches into two.
     * - Space: O(M+N) Auxiliary Stack Space for the recursion depth.
     */
    public int minDistanceRecursive(String word1, String word2) {
        return solveRecursive(word1.length(), word2.length(), word1, word2);
    }

    private int solveRecursive(int i, int j, String w1, String w2) {
        // Base cases: If one string is empty, return the length of the other
        if (i == 0) return j;
        if (j == 0) return i;

        // Characters match
        if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
            return solveRecursive(i - 1, j - 1, w1, w2);
        }

        // Characters don't match -> Min of deleting from w1 or deleting from w2
        int deleteW1 = 1 + solveRecursive(i - 1, j, w1, w2);
        int deleteW2 = 1 + solveRecursive(i, j - 1, w1, w2);

        return Math.min(deleteW1, deleteW2);
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursive tree reveals overlapping subproblems (e.g., `f(2,2)` might be
     * calculated multiple times). We create a 2D array `memo[i][j]` to cache the
     * minimum deletions required for prefixes of length `i` and `j`. If we see a
     * state we've already solved, we return the cached value immediately.
     * * COMPLEXITY:
     * - Time: O(M * N) -> We compute each unique state (prefix pair) exactly once.
     * - Space: O(M * N) Heap Space for the `memo` matrix + O(M+N) Auxiliary Stack Space.
     */
    public int minDistanceMemo(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];
        return solveMemo(m, n, word1, word2, memo);
    }

    private int solveMemo(int i, int j, String w1, String w2, Integer[][] memo) {
        if (i == 0) return j;
        if (j == 0) return i;

        if (memo[i][j] != null) return memo[i][j];

        if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
            return memo[i][j] = solveMemo(i - 1, j - 1, w1, w2, memo);
        }

        int deleteW1 = 1 + solveMemo(i - 1, j, w1, w2, memo);
        int deleteW2 = 1 + solveMemo(i, j - 1, w1, w2, memo);

        return memo[i][j] = Math.min(deleteW1, deleteW2);
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We translate the memoized approach into an iterative one, building answers
     * from the smallest subproblems (empty strings) up to the full lengths. This
     * eliminates the risk of StackOverflow errors and reduces overhead.
     * * EXACT DEFAULT STATE OF DP ARRAY (After base case init, before loops):
     * word1="sea" (len 3), word2="eat" (len 3)
     * ""   e   a   t
     * "" [  0,  1,  2,  3 ]  <- To match "", need j deletions
     * s [  1,  0,  0,  0 ]
     * e [  2,  0,  0,  0 ]
     * a [  3,  0,  0,  0 ]
     * ^
     * To match "", need i deletions
     * * COMPLEXITY:
     * - Time: O(M * N) -> Two nested loops iterating over prefix lengths.
     * - Space: O(M * N) Heap Space for the 2D `dp` array. No stack space used.
     */
    public int minDistanceTabulation(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base case initialization
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        // Main Tabulation Loops
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No deletion needed
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
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
     * In the tabulation approach, calculating `dp[i][j]` only requires values from
     * the current row `i` (specifically `dp[i][j-1]`) and the previous row `i-1`
     * (specifically `dp[i-1][j]` and `dp[i-1][j-1]`). We can discard older rows.
     * We maintain two 1D arrays: `prev` (representing row i-1) and `curr`
     * (representing row i).
     * * COMPLEXITY:
     * - Time: O(M * N) -> Still doing the same number of comparative operations.
     * - Space: O(N) Heap Space -> Two 1D arrays of size N+1 instead of an M*N matrix.
     */
    public int minDistanceSpaceOptimized(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        // Initialize base case for the very first 'prev' row (i=0)
        for (int j = 0; j <= n; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= m; i++) {
            // Base case for the current row's 0th column
            curr[0] = i;

            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(prev[j], curr[j - 1]);
                }
            }
            // Swap arrays to move to the next row
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        // After the final iteration, 'prev' holds the last computed row
        return prev[n];
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Longest Common Subsequence)
     * ========================================================================
     * INTUITION:
     * Instead of calculating the minimum deletions directly, we can find the
     * Longest Common Subsequence (LCS) of the two strings. The characters that
     * are NOT part of the LCS are exactly the ones we must delete.
     * Formula: Total Deletions = (word1.length - LCS) + (word2.length - LCS)
     * * COMPLEXITY:
     * - Time: O(M * N) -> Standard LCS tabulation time.
     * - Space: O(N) -> LCS can also be space-optimized to use a 1D array.
     */
    public int minDistanceLCSVariant(String word1, String word2) {
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
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        int lcsLength = prev[n];
        return (m - lcsLength) + (n - lcsLength);
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        DeleteOperationTwoStrings solver = new DeleteOperationTwoStrings();

        System.out.println("=== Test Case 1 ===");
        String w1_1 = "sea", w2_1 = "eat";
        System.out.println("Recursion  : " + solver.minDistanceRecursive(w1_1, w2_1));
        System.out.println("Memoization: " + solver.minDistanceMemo(w1_1, w2_1));
        System.out.println("Tabulation : " + solver.minDistanceTabulation(w1_1, w2_1));
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w1_1, w2_1));
        System.out.println("LCS Variant: " + solver.minDistanceLCSVariant(w1_1, w2_1));
        System.out.println("Expected   : 2\n");

        System.out.println("=== Test Case 2 ===");
        String w1_2 = "leetcode", w2_2 = "etco";
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w1_2, w2_2));
        System.out.println("Expected   : 4\n");

        System.out.println("=== Edge Case: Identical Strings ===");
        String w1_3 = "hello", w2_3 = "hello";
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w1_3, w2_3));
        System.out.println("Expected   : 0\n");

        System.out.println("=== Edge Case: One Empty String ===");
        String w1_4 = "abc", w2_4 = "";
        System.out.println("Space Opt  : " + solver.minDistanceSpaceOptimized(w1_4, w2_4));
        System.out.println("Expected   : 3");
    }
}
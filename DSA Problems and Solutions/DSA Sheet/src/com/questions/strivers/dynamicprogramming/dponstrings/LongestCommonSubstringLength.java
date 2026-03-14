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
 * Input: s1 = "abcjklp", s2 = "acjkp"
 * Output: 3
 * Explanation: The longest common substring is "cjk" with length 3.
 * (Note: Adjusted from the prompt's 'cjp' which is a subsequence, to a true substring).
 * * Example 2:
 * Input: s1 = "wasdijkl", s2 = "wsdijk"
 * Output: 5
 * Explanation: The longest common substring is "sdijk".
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s1 = "ab", s2 = "ac")
 * ============================================================================
 * Let f(i, j, count) represent the LCS matching state for s1[0..i-1] and s2[0..j-1],
 * where 'count' is the length of the current contiguous match.
 * *                                f(2, 2, 0) ["ab", "ac"]
 *                                  ('b' != 'c') -> Mismatch! Chain broken. Count resets to 0. We explore skipping:
 *                          /                                   \
 *                      (Skip 'b')                            (Skip 'c')
 *                      f(1, 2, 0) ["a", "ac"]                f(2, 1, 0) ["ab", "a"]
 *                      ('a' != 'c') -> Mismatch             ('b' != 'a') -> Mismatch
 *                          /            \                         /            \
 *                      f(0, 2, 0)   f(1, 1, 0) ["a", "a"]     f(1, 1, 0)     f(2, 0, 0)
 *                      (Base: 0)     ('a' == 'a') Match!      (Handled)      (Base: 0)
 *                          |
 *              Count increments to 1.
 *              f(0, 0, 1) -> Returns 1
 *
 * * Global Max Length Found = 1 ("a").
 * * * COMPLETE FINAL DP ARRAY STATE (s1 = "ab", s2 = "ac"):
 * (Rows = s1 prefixes: "", "a", "b")
 * (Cols = s2 prefixes: "", "a", "c")
 * * ""   a   c
 * ""  [ 0,  0,  0 ]
 * a   [ 0,  1,  0 ]  <- Max value in the entire grid is 1
 * b   [ 0,  0,  0 ]
 */

public class LongestCommonSubstringLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * For substrings, the characters must be strictly contiguous.
     * We track the current matching length with a `count` parameter.
     * 1. If characters match, we increment `count` and move diagonally.
     * 2. If they DO NOT match, the contiguous chain is broken! We must reset
     * `count` to 0 and start looking for new substrings by moving left or up.
     * 3. The answer for a state is the maximum of the matched path count vs
     * the reset paths.
     * * COMPLEXITY:
     * - Time: O(3^(M+N)) -> At each mismatch, we branch into 3 recursive calls
     * (conceptually, though optimized here to 2 skips + 1 match).
     * Highly exponential.
     * - Space: O(M+N) -> Auxiliary stack space.
     */
    public int lcsSubstringRecursive(String s1, String s2) {
        return solveRecursive(s1.length(), s2.length(), 0, s1, s2);
    }

    private int solveRecursive(int i, int j, int count, String s1, String s2) {
        if (i == 0 || j == 0) return count;

        int matchCount = count;
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            matchCount = solveRecursive(i - 1, j - 1, count + 1, s1, s2);
        }

        // If chain breaks, or we want to explore starting a NEW substring
        int skipS1 = solveRecursive(i - 1, j, 0, s1, s2);
        int skipS2 = solveRecursive(i, j - 1, 0, s1, s2);

        return Math.max(matchCount, Math.max(skipS1, skipS2));
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * Memoizing the 3D state `(i, j, count)` takes too much memory.
     * Instead, we shift our paradigm: Let `memo[i][j]` represent the length
     * of the longest common suffix ending exactly at `s1[i-1]` and `s2[j-1]`.
     * We then update a global `maxLen` variable whenever we find a valid suffix.
     * To ensure we explore all possible substring endings, our recursion
     * must explicitly fan out to `i-1, j` and `i, j-1`.
     * * COMPLEXITY:
     * - Time: O(M * N) -> Every pair (i, j) is computed exactly once.
     * - Space: O(M * N) Heap space for memo matrix + O(M + N) Stack space.
     */
    private int maxLenMemo = 0;

    public int lcsSubstringMemo(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];
        maxLenMemo = 0;

        solveMemo(m, n, s1, s2, memo);
        return maxLenMemo;
    }

    private int solveMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;

        if (memo[i][j] != null) return memo[i][j];

        // Ensure we traverse the entire grid to populate the memo table
        solveMemo(i - 1, j, s1, s2, memo);
        solveMemo(i, j - 1, s1, s2, memo);

        int currentSuffixLen = 0;
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            currentSuffixLen = 1 + solveMemo(i - 1, j - 1, s1, s2, memo);
        }

        maxLenMemo = Math.max(maxLenMemo, currentSuffixLen);
        return memo[i][j] = currentSuffixLen;
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We build a 2D array where `dp[i][j]` is the longest common substring
     * ending at `s1[i-1]` and `s2[j-1]`.
     * - If characters match: `dp[i][j] = 1 + dp[i-1][j-1]`
     * - If mismatch: `dp[i][j] = 0` (Because continuity is broken!)
     * We track the maximum value seen anywhere in the DP table.
     * * EXACT DEFAULT STATE OF DP ARRAY (s1 = "ab", s2 = "ac"):
     *      ""   a   c
     * "" [ 0,  0,  0 ]
     * a  [ 0,  0,  0 ]
     * b  [ 0,  0,  0 ]
     * * COMPLEXITY:
     * - Time: O(M * N) -> Nested loops over the string lengths.
     * - Space: O(M * N) -> For the full DP table. No stack space used.
     */
    public int lcsSubstringTabulation(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        int maxLen = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    maxLen = Math.max(maxLen, dp[i][j]);
                } else {
                    dp[i][j] = 0; // Continuity broken
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
     * To compute `dp[i][j]`, we ONLY need `dp[i-1][j-1]` from the previous row.
     * We do not need the rest of the matrix. We can optimize this to a single
     * 1D array by iterating right-to-left.
     * When evaluating column `j` (right-to-left), `dp[j-1]` still holds the
     * value from the *previous* row (`i-1`), exactly what we need!
     * * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Single 1D array instead of an M*N matrix.
     */
    public int lcsSubstringSpaceOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] dp = new int[n + 1];
        int maxLen = 0;

        for (int i = 1; i <= m; i++) {
            // Traverse backwards so dp[j-1] is not overwritten before we use it
            for (int j = n; j >= 1; j--) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[j] = 1 + dp[j - 1];
                    maxLen = Math.max(maxLen, dp[j]);
                } else {
                    dp[j] = 0; // Reset to 0 since match broken
                }
            }
        }

        return maxLen;
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Binary Search + Rolling Hash)
     * ========================================================================
     * INTUITION:
     * While DP provides an elegant O(M*N) solution, competitive programmers
     * dealing with strings > 10^5 length use Binary Search combined with
     * Rabin-Karp (Rolling Hash).
     * 1. Binary Search on the length of the substring (1 to min(M, N)).
     * 2. For a guessed length 'L', compute all rolling hashes of substrings of
     * length 'L' in s1 and store them in a HashSet.
     * 3. Compute hashes for s2 substrings of length 'L'. If a match is found
     * in the set, search upper half; else search lower half.
     * * COMPLEXITY:
     * - Time: O((M + N) * log(min(M, N))) -> Much faster for massive strings.
     * - Space: O(M) -> HashSet to store hashes.
     * (Implementation omitted to keep focus strictly on DP concepts).
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestCommonSubstringLength solver = new LongestCommonSubstringLength();

        System.out.println("=== Test Case 1: Standard ===");
        String s1 = "abcjklp", s2 = "acjkp";
        System.out.println("Recursion  : " + solver.lcsSubstringRecursive(s1, s2));
        System.out.println("Memoization: " + solver.lcsSubstringMemo(s1, s2));
        System.out.println("Tabulation : " + solver.lcsSubstringTabulation(s1, s2));
        System.out.println("Space Opt  : " + solver.lcsSubstringSpaceOptimized(s1, s2));
        System.out.println("Expected   : 3 (cjk)\n");

        System.out.println("=== Test Case 2: Identical Strings ===");
        String s3 = "hello", s4 = "hello";
        System.out.println("Space Opt  : " + solver.lcsSubstringSpaceOptimized(s3, s4));
        System.out.println("Expected   : 5\n");

        System.out.println("=== Test Case 3: No Common Characters ===");
        String s5 = "abc", s6 = "xyz";
        System.out.println("Space Opt  : " + solver.lcsSubstringSpaceOptimized(s5, s6));
        System.out.println("Expected   : 0\n");

        System.out.println("=== Edge Case: Full String Mismatch then Match ===");
        String s7 = "wasdijkl", s8 = "wsdijk";
        System.out.println("Space Opt  : " + solver.lcsSubstringSpaceOptimized(s7, s8));
        System.out.println("Expected   : 5 (sdijk)");
    }
}
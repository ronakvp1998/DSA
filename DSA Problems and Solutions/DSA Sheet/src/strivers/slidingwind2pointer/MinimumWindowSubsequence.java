package strivers.slidingwind2pointer;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION: Minimum Window Subsequence
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * PROBLEM: 727. Minimum Window Subsequence (Hard)
 *
 * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given strings s1 and s2, return the minimum contiguous substring part of s1,
 * so that s2 is a subsequence of the part.
 *
 * If there is no such window in s1 that covers all characters in s2, return
 * the empty string "". If there are multiple such minimum-length windows,
 * return the one with the left-most starting index.
 *
 * Constraints:
 * - 1 <= s1.length <= 2 * 10^4
 * - 1 <= s2.length <= 100
 * - s1 and s2 consist of lowercase English letters.
 *
 * Example 1:
 * Input: s1 = "abcdebdde", s2 = "bde"
 * Output: "bcde"
 * Explanation:
 * "bcde" is the answer because it occurs before "bdde" which has the same length.
 * "deb" is not a smaller window because the elements of s2 in the window must occur in order.
 *
 * Example 2:
 * Input: s1 = "jmeqksfrsdcmsiw", s2 = "k"
 * Output: "k"
 *
 * --- CONCEPTUAL VISUALIZATION ---
 * The state `f(i, j)` represents the MAXIMUM STARTING INDEX in `s1[0...i]` such that
 * `s2[0...j]` is a valid subsequence.
 * If no such starting index exists, it returns -1.
 *
 * Recursion Tree (Partial for s1="abc", s2="bc"):
 *                         f("abc", "bc") -> finds 'c' matches 'c'
 *                               |
 *                         f("ab", "b") -> finds 'b' matches 'b'
 *                               |
 *                         f("a", "") -> base case: returns length of "a" = 1 (start index)
 *
 * --- 2.1. PROGRESSIVE IMPLEMENTATION ROADMAP (DP Problems) ---
 * Phase 1: Brute Force Recursion - Top-down matching of suffixes.
 * Phase 2: Top-Down Memoization - Caching the (s1Index, s2Index) state.
 * Phase 3: Bottom-Up Tabulation - 2D Grid mapping prefix lengths.
 * Phase 4: Space Optimization - 1D arrays (Rolling Rows).
 * Phase 5: Alternative Approach - Two Pointers (Expand & Contract) - Most optimal in practice.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class MinimumWindowSubsequence {


    /**
     * ============================================================================
     * Phase 5: Alternative Approach - Expand & Contract (Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * This is the most optimal approach in real-world practice.
     * 1. Expand: Move a pointer `p1` forward in `s1` to greedily match all characters of `s2`.
     * 2. Contract: Once `s2` is completely matched, we know a valid window ends at `p1-1`.
     *    To MINIMIZE this window, we trace backwards from `p1-1` in `s1`, matching `s2`
     *    in reverse to find the tightest possible starting point.
     * 3. Resume: Update the global minimum, and reset `p1` to immediately after the
     *    tightest starting point we just found to search for the next possible window.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N) worst case, but practically O(M) for most strings as
     *   the backward scan only triggers upon a full match.
     * - Space Complexity: O(1) auxiliary space, purely pointer manipulation.
     * ============================================================================
     */
    public String minWindowTwoPointers(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int minLen = Integer.MAX_VALUE;
        int minStart = -1;

        int p1 = 0, p2 = 0;

        while (p1 < m) {
            // Forward pass: check if characters match
            if (s1.charAt(p1) == s2.charAt(p2)) {
                p2++;

                // Full match found
                if (p2 == n) {
                    int end = p1;
                    p2--; // Point to the last character of s2

                    // Backward pass: find the tightest start
                    while (p2 >= 0) {
                        if (s1.charAt(p1) == s2.charAt(p2)) {
                            p2--;
                        }
                        p1--; // p1 ends up 1 step BEFORE the actual start
                    }

                    p1++; // Move to the actual start index of the window

                    int currentLen = end - p1 + 1;
                    if (currentLen < minLen) {
                        minLen = currentLen;
                        minStart = p1;
                    }

                    // Reset p2 for the next potential window
                    p2 = 0;
                    // Note: p1 will be incremented by the outer while loop
                    // which means the next search starts exactly at p1 + 1
                }
            }
            p1++;
        }

        return minStart == -1 ? "" : s1.substring(minStart, minStart + minLen);
    }

    /**
     * ============================================================================
     * Phase 1: Brute Force Recursion - The "Think It" Stage
     * ============================================================================
     * Detailed Intuition:
     * We want to find the largest starting index for every valid window.
     * Let `f(i, j)` be a function that searches backwards from indices `i` (in s1)
     * and `j` (in s2).
     * - If s1[i] == s2[j], the character is part of our subsequence. We recursively
     *   look for s2[j-1] in s1[i-1].
     * - If s1[i] != s2[j], we simply skip s1[i] and look for s2[j] in s1[i-1].
     * - Base Case: When `j` < 0, we have successfully matched all of `s2`. The
     *   current `i` + 1 is the starting index of this valid window.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^M) where M is the length of s1. The branching
     *   factor without memoization results in exponential redundant checks.
     * - Space Complexity: O(M) auxiliary stack space for the recursion depth.
     * ============================================================================
     */
    public String minWindowBruteForce(String s1, String s2) {
        int minLen = Integer.MAX_VALUE;
        int minStart = -1;

        // Iterate through s1. Whenever we see the last char of s2, try to form a window
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(s2.length() - 1)) {
                int start = recurse(s1, s2, i, s2.length() - 1);
                if (start != -1) {
                    int len = i - start + 1;
                    // Strict less-than ensures we keep the left-most window on ties
                    if (len < minLen) {
                        minLen = len;
                        minStart = start;
                    }
                }
            }
        }
        return minStart == -1 ? "" : s1.substring(minStart, minStart + minLen);
    }

    private int recurse(String s1, String s2, int i, int j) {
        if (j < 0) return i + 1; // Successfully matched all chars of s2
        if (i < 0) return -1;    // s1 exhausted, s2 not matched

        if (s1.charAt(i) == s2.charAt(j)) {
            return recurse(s1, s2, i - 1, j - 1);
        } else {
            return recurse(s1, s2, i - 1, j);
        }
    }

    /**
     * ============================================================================
     * Phase 2: Top-Down Memoization - The "Refine It" Stage
     * ============================================================================
     * Detailed Intuition:
     * The brute force approach calculates `recurse(i, j)` multiple times. We can
     * cache the result of `(i, j)` in a 2D integer array `memo`.
     * A value of -1 means invalid, so we initialize the memo table with -2 to
     * represent "uncalculated".
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N) where M is s1.length() and N is s2.length().
     *   Each state (i, j) is calculated strictly once.
     * - Space Complexity: O(M * N) heap space for the `memo` array + O(M) auxiliary
     *   stack space for recursion depth.
     * ============================================================================
     */
    public String minWindowMemoization(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] memo = new int[m][n];
        for (int[] row : memo) Arrays.fill(row, -2);

        int minLen = Integer.MAX_VALUE;
        int minStart = -1;

        for (int i = 0; i < m; i++) {
            if (s1.charAt(i) == s2.charAt(n - 1)) {
                int start = memoizedRecurse(s1, s2, i, n - 1, memo);
                if (start != -1) {
                    int len = i - start + 1;
                    if (len < minLen) {
                        minLen = len;
                        minStart = start;
                    }
                }
            }
        }
        return minStart == -1 ? "" : s1.substring(minStart, minStart + minLen);
    }

    private int memoizedRecurse(String s1, String s2, int i, int j, int[][] memo) {
        if (j < 0) return i + 1;
        if (i < 0) return -1;
        if (memo[i][j] != -2) return memo[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            memo[i][j] = memoizedRecurse(s1, s2, i - 1, j - 1, memo);
        } else {
            memo[i][j] = memoizedRecurse(s1, s2, i - 1, j, memo);
        }
        return memo[i][j];
    }

    /**
     * ============================================================================
     * Phase 3: Bottom-Up Tabulation - The "Build It" Stage
     * ============================================================================
     * EXACT DEFAULT STATE OF DP ARRAY (s1="abcde", s2="bde"):
     * Let dp[i][j] be the starting index in s1 for prefix s1[0..i-1] and s2[0..j-1].
     *       ""   b   d   e
     *  ""    0  -1  -1  -1
     *  a     1  -1  -1  -1
     *  b     2  -1  -1  -1
     *  c     3  -1  -1  -1
     *  d     4  -1  -1  -1
     *  e     5  -1  -1  -1
     *
     * EXACT FINAL STATE OF DP ARRAY:
     *       ""   b   d   e
     *  ""    0  -1  -1  -1
     *  a     1  -1  -1  -1
     *  b     2   1  -1  -1  (s1='b' matches s2='b', start is dp[1][0] = 1)
     *  c     3   1  -1  -1  (s1='c' != 'b', carries over dp[2][1] = 1)
     *  d     4   1   1  -1  (s1='d' matches s2='d', start is dp[3][1] = 1)
     *  e     5   1   1   1  (s1='e' matches s2='e', start is dp[4][2] = 1) -> Window: len(5-1)
     *
     * Detailed Intuition:
     * We convert the memoized logic into an iterative tabulation.
     * We pad the table by 1 to handle empty prefixes cleanly.
     * `dp[i][j]` tracks the maximum starting index of a valid window.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N) to fill the grid.
     * - Space Complexity: O(M * N) heap space for the 2D DP table. No recursion stack.
     * ============================================================================
     */
    public String minWindowTabulation(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Initialize table
        for (int i = 0; i <= m; i++) Arrays.fill(dp[i], -1);
        for (int i = 0; i <= m; i++) dp[i][0] = i;

        // Build table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Find min window
        int minLen = Integer.MAX_VALUE;
        int minStart = -1;
        for (int i = 1; i <= m; i++) {
            if (dp[i][n] != -1) {
                int len = i - dp[i][n];
                if (len < minLen) {
                    minLen = len;
                    minStart = dp[i][n];
                }
            }
        }
        return minStart == -1 ? "" : s1.substring(minStart, minStart + minLen);
    }

    /**
     * ============================================================================
     * Phase 4: Space Optimization - The "Perfect It" Stage
     * ============================================================================
     * Detailed Intuition:
     * In the 2D DP tabulation, the transition for `dp[i][j]` relies EXCLUSIVELY on
     * `dp[i-1][j-1]` and `dp[i-1][j]`. This means we only ever need the values from
     * the PREVIOUS row to compute the CURRENT row. We can discard the rest of the matrix.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N).
     * - Space Complexity: O(N) auxiliary heap space, using only two arrays of size N.
     * ============================================================================
     */
    public String minWindowSpaceOptimized(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        Arrays.fill(prev, -1);
        prev[0] = 0; // Empty s1 matches empty s2 at index 0

        int minLen = Integer.MAX_VALUE;
        int minStart = -1;

        for (int i = 1; i <= m; i++) {
            Arrays.fill(curr, -1);
            curr[0] = i; // Empty s2 always matches current s1 index

            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = prev[j];
                }
            }

            if (curr[n] != -1) {
                int len = i - curr[n];
                if (len < minLen) {
                    minLen = len;
                    minStart = curr[n];
                }
            }

            // Swap arrays to roll forward
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return minStart == -1 ? "" : s1.substring(minStart, minStart + minLen);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thorough testing of all approaches against standard and edge cases.
     * Uses Java 8 Stream API to format output elegantly.
     * ============================================================================
     */
    public static void main(String[] args) {
        MinimumWindowSubsequence solution = new MinimumWindowSubsequence();

        // Define Test Cases [s1, s2, expectedOutput]
        String[][] testCases = {
                {"abcdebdde", "bde", "bcde"},               // Standard Case
                {"jmeqksfrsdcmsiw", "k", "k"},              // Single char target
                {"abc", "def", ""},                         // Impossible target
                {"bbbbbbbb", "b", "b"},                     // Duplicates in source
                {"ababa", "ba", "ba"},                      // Overlapping matches
                {"ababababa", "ab", "ab"}                   // Multiple same-length matches (must pick left-most)
        };

        System.out.println("🤖 Running Test Suite: Minimum Window Subsequence\n");

        IntStream.range(0, testCases.length).forEach(i -> {
            String s1 = testCases[i][0];
            String s2 = testCases[i][1];
            String expected = testCases[i][2];

            String brute = solution.minWindowBruteForce(s1, s2);
            String memo = solution.minWindowMemoization(s1, s2);
            String tab = solution.minWindowTabulation(s1, s2);
            String opt = solution.minWindowSpaceOptimized(s1, s2);
            String twoPointers = solution.minWindowTwoPointers(s1, s2);

            boolean passed = brute.equals(expected) && memo.equals(expected) &&
                    tab.equals(expected) && opt.equals(expected) &&
                    twoPointers.equals(expected);

            System.out.printf("Test Case %d: s1 = \"%s\" | s2 = \"%s\"\n", (i + 1), s1, s2);
            System.out.printf("   Expected:          \"%s\"\n", expected);
            System.out.printf("   Space Optimized:   \"%s\"\n", opt);
            System.out.printf("   Two Pointers:      \"%s\"\n", twoPointers);
            System.out.printf("   Status: %s\n", passed ? "✅ PASSED" : "❌ FAILED");
            System.out.println("--------------------------------------------------");
        });
    }
}
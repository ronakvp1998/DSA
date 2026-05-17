package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: 44. Wildcard Matching
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given an input string (s) and a pattern (p), implement wildcard pattern
 * matching with support for '?' and '*' where:
 * - '?' Matches any single character.
 * - '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 * * CONSTRAINTS:
 * - 0 <= s.length, p.length <= 2000
 * - s contains only lowercase English letters.
 * - p contains only lowercase English letters, '?' or '*'.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION
 * ============================================================================
 * * 1. State Definition:
 * We evaluate from the end of both strings using pointers `i` (for `s`) and
 * `j` (for `p`).
 * - f(i, j) = Does s[0...i] match p[0...j]?
 * * * 2. Recursion Tree (Partial for s = "adceb", p = "*a*b"):
 * f(4, 3) [s="adceb", p="*a*b"]
 * /
 * (s[4]=='b' == p[3]=='b') -> Match!
 * /
 * f(3, 2) [s="adce", p="*a*"]
 * /
 * (p[2]=='*') -> Branch into two universes:
 * /                                  \
 * [Empty Sequence]                        [Consume 1 Char]
 * f(3, 1) [s="adce", p="*a"]             f(2, 2) [s="adc", p="*a*"]
 * (Ignore '*')                           (Keep '*', consume 'e')
 * * * 3. DP Table Transitions (1-based indexing):
 * If s[i-1] == p[j-1] OR p[j-1] == '?':
 * dp[i][j] = dp[i-1][j-1]
 * If p[j-1] == '*':
 * dp[i][j] = dp[i][j-1] OR dp[i-1][j]
 * * ============================================================================
 */

import java.util.Arrays;

public class WildcardMatching {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * Intuition:
     * We compare the strings from right to left. If characters match or the
     * pattern has a '?', we simply shrink both. The magic happens when we hit
     * a '*'. A '*' can either match NOTHING (so we shrink the pattern pointer
     * and leave the string pointer) OR it can match ONE OR MORE characters
     * (so we shrink the string pointer but KEEP the pattern pointer at '*').
     * * Complexity Analysis:
     * - Time Complexity: $O(2^{\min(M, N)})$ worst case due to exponential
     * branching whenever a '*' is encountered. Will TLE.
     * - Space Complexity: $O(M + N)$ for the recursion stack depth.
     */
    public boolean isMatchBruteForce(String s, String p) {
        return solveRecursive(s, p, s.length() - 1, p.length() - 1);
    }

    private boolean solveRecursive(String s, String p, int i, int j) {
        // Base Case 1: Both strings exhausted. Perfect match.
        if (i < 0 && j < 0) return true;

        // Base Case 2: Pattern exhausted, but string still has characters.
        if (i >= 0 && j < 0) return false;

        // Base Case 3: String exhausted, but pattern still has characters.
        // This is ONLY a match if all remaining pattern characters are '*'.
        if (i < 0 && j >= 0) {
            for (int k = 0; k <= j; k++) {
                if (p.charAt(k) != '*') return false;
            }
            return true;
        }

        // Match Logic
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            return solveRecursive(s, p, i - 1, j - 1);
        }

        // Wildcard '*' Logic
        if (p.charAt(j) == '*') {
            // Option 1: '*' matches empty string (j - 1)
            // Option 2: '*' matches the current character (i - 1)
            return solveRecursive(s, p, i, j - 1) || solveRecursive(s, p, i - 1, j);
        }

        return false;
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * Intuition:
     * The recursive tree frequently visits the same (i, j) coordinate. We cache
     * the boolean results. Since boolean arrays default to false in Java, we
     * use an Integer array to represent unvisited (-1), false (0), and true (1).
     * * Complexity Analysis:
     * - Time Complexity: $O(M \times N)$ - Each state is evaluated once.
     * - Space Complexity: $O(M \times N)$ heap space + $O(M + N)$ stack space.
     */
    public boolean isMatchMemoization(String s, String p) {
        int m = s.length();
        int n = p.length();
        int[][] memo = new int[m][n];
        for (int[] row : memo) Arrays.fill(row, -1);

        return solveMemo(s, p, m - 1, n - 1, memo) == 1;
    }

    private int solveMemo(String s, String p, int i, int j, int[][] memo) {
        if (i < 0 && j < 0) return 1;
        if (i >= 0 && j < 0) return 0;
        if (i < 0 && j >= 0) {
            for (int k = 0; k <= j; k++) if (p.charAt(k) != '*') return 0;
            return 1;
        }

        if (memo[i][j] != -1) return memo[i][j];

        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            return memo[i][j] = solveMemo(s, p, i - 1, j - 1, memo);
        }

        if (p.charAt(j) == '*') {
            boolean emptySeq = solveMemo(s, p, i, j - 1, memo) == 1;
            boolean consumeChar = solveMemo(s, p, i - 1, j, memo) == 1;
            return memo[i][j] = (emptySeq || consumeChar) ? 1 : 0;
        }

        return memo[i][j] = 0;
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * Intuition:
     * We convert to an iterative DP table, shifting to 1-based indexing so that
     * index 0 represents an empty string. Handling the base case for an empty
     * string against a pattern of pure '*'s is critical here.
     * * Complexity Analysis:
     * - Time Complexity: $O(M \times N)$
     * - Space Complexity: $O(M \times N)$ for the DP matrix.
     */
    public boolean isMatchTabulation(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Base case: empty string and empty pattern match
        dp[0][0] = true;

        // Base case: empty string matches pattern if pattern is all '*'
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * Intuition:
     * Every calculation in row `i` only requires values from the current row `i`
     * and the previous row `i-1`. We can compress the $O(M \times N)$ matrix
     * into two 1D arrays of size $N+1$.
     * * Complexity Analysis:
     * - Time Complexity: $O(M \times N)$
     * - Space Complexity: $O(N)$ where N is the length of pattern `p`.
     */
    public boolean isMatchSpaceOptimized(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[] prev = new boolean[n + 1];
        boolean[] curr = new boolean[n + 1];

        prev[0] = true;
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') prev[j] = prev[j - 1];
        }

        for (int i = 1; i <= m; i++) {
            // Crucial: Reset curr[0] for the new row. An empty pattern
            // CANNOT match a non-empty string.
            curr[0] = false;

            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    curr[j] = prev[j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    curr[j] = curr[j - 1] || prev[j];
                } else {
                    curr[j] = false;
                }
            }
            prev = curr.clone();
        }
        return prev[n];
    }

    /**
     * ========================================================================
     * PHASE 5: SENIOR INSIGHT - TWO POINTERS / GREEDY (The "Show-Off" stage)
     * ========================================================================
     * Intuition:
     * We don't actually need full DP for this! When we hit a '*', we can greedily
     * assume it matches nothing. If we encounter a mismatch later, we BACKTRACK
     * to the last '*' we saw, assume it matched one character, and try again.
     * We only need to keep track of the index of the last '*' and the index in `s`
     * where that '*' started matching.
     * * Complexity Analysis:
     * - Time Complexity: $O(M)$ average time, $O(M \times N)$ worst case
     * (e.g., s = "aaaaaaaaab", p = "*a*a*a*a*a*a*a*a*c").
     * - Space Complexity: $O(1)$ constant space!
     */
    public boolean isMatchGreedy(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int i = 0, j = 0;
        int matchIdx = -1, starIdx = -1;

        while (i < sLen) {
            // Advance both pointers if chars match or '?' is found
            if (j < pLen && (p.charAt(j) == '?' || s.charAt(i) == p.charAt(j))) {
                i++;
                j++;
            }
            // Found a '*', track its index and the current string index
            else if (j < pLen && p.charAt(j) == '*') {
                starIdx = j;
                matchIdx = i;
                j++; // Greedily assume '*' matches zero characters first
            }
            // Mismatch occurred, but we previously saw a '*'
            else if (starIdx != -1) {
                // Backtrack: Force the last '*' to match one more character
                j = starIdx + 1;
                matchIdx++;
                i = matchIdx;
            }
            // Mismatch with no preceding '*'
            else {
                return false;
            }
        }

        // Check if any remaining characters in pattern are just trailing '*'
        while (j < pLen && p.charAt(j) == '*') {
            j++;
        }

        return j == pLen;
    }
}
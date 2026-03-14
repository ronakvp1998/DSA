package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: 1092. Shortest Common Supersequence
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings str1 and str2, return the shortest string that has both
 * str1 and str2 as subsequences. If there are multiple valid strings, return
 * any of them.
 * * CONSTRAINTS:
 * - 1 <= str1.length, str2.length <= 1000
 * - str1 and str2 consist of lowercase English letters.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION
 * ============================================================================
 * * 1. State Definition (For Top-Down String Building):
 * - f(i, j) returns the literal Shortest Common Supersequence String for
 * substrings str1[i...] and str2[j...].
 * * * 2. Recursion Tree (Partial for str1="ab", str2="ac"):
 *
 * f(0, 0) ["ab", "ac"]
 * /                    \
 * (a == a) -> "a" + f(1, 1) ["b", "c"]
 * /                 \
 * (b != c) -> min length of:
 * 1. "b" + f(2, 1) ["", "c"]  OR  2. "c" + f(1, 2) ["b", ""]
 * * * 3. Core Transition Logic:
 * If str1[i] == str2[j]: Take the char, advance both pointers.
 * If str1[i] != str2[j]: Branch into two paths (take str1[i] vs take str2[j]),
 * and return the branch that yields the SHORTER string.
 * * ============================================================================
 */

public class ShortestCommonSupersequence {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * Intuition:
     * We compare characters at our current pointers. If they match, we append
     * it to our supersequence and move both pointers. If they don't match, we
     * must branch: we try taking the character from str1 (and advance i) OR
     * taking the character from str2 (and advance j). We use StringBuilder to
     * construct the strings and return the shorter resulting supersequence.
     * * Complexity Analysis:
     * - Time: O(2^(M+N)) - In the worst case of completely distinct strings,
     * we branch twice at every single character.
     * - Space: O(M+N) for the recursion stack, plus massive auxiliary space
     * for generating new String/StringBuilder objects at every frame.
     */
    public String scsBruteForce(String str1, String str2) {
        return solveRecursive(str1, str2, 0, 0);
    }

    private String solveRecursive(String str1, String str2, int i, int j) {
        // Base Cases: If one string is exhausted, the remaining supersequence
        // MUST be the rest of the other string.
        if (i == str1.length()) return str2.substring(j);
        if (j == str2.length()) return str1.substring(i);

        if (str1.charAt(i) == str2.charAt(j)) {
            // Match: Append the shared character and advance both
            return new StringBuilder()
                    .append(str1.charAt(i))
                    .append(solveRecursive(str1, str2, i + 1, j + 1))
                    .toString();
        } else {
            // No Match: Try both paths
            String opt1 = new StringBuilder()
                    .append(str1.charAt(i))
                    .append(solveRecursive(str1, str2, i + 1, j))
                    .toString();

            String opt2 = new StringBuilder()
                    .append(str2.charAt(j))
                    .append(solveRecursive(str1, str2, i, j + 1))
                    .toString();

            // Return the shorter constructed string
            return opt1.length() < opt2.length() ? opt1 : opt2;
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * Intuition:
     * The brute force tree evaluates the same index pairs (i, j) multiple times.
     * We can cache the literal resulting String for each (i, j) state.
     * * INTERVIEWER NOTE (The Memory Limit Trap):
     * While logically flawless, caching strings in a 1000x1000 matrix means
     * storing up to 1,000,000 strings, some of length 2000. This will cause a
     * Memory Limit Exceeded (MLE) error in Java. This proves exactly why Phase 3
     * (caching lengths, then backtracking) is the required production standard.
     * * Complexity Analysis:
     * - Time: O(M * N) total states evaluated. However, string concatenation
     * takes O(M+N) time per state, making effective time O(M * N * (M+N)).
     * - Space: O(M * N) array size, but heap memory is massive due to String
     * storage -> O(M * N * (M+N)) memory footprint.
     */
    public String scsMemoization(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        String[][] memo = new String[m][n];
        return solveMemo(str1, str2, 0, 0, memo);
    }

    private String solveMemo(String str1, String str2, int i, int j, String[][] memo) {
        if (i == str1.length()) return str2.substring(j);
        if (j == str2.length()) return str1.substring(i);

        if (memo[i][j] != null) {
            return memo[i][j];
        }

        if (str1.charAt(i) == str2.charAt(j)) {
            memo[i][j] = new StringBuilder()
                    .append(str1.charAt(i))
                    .append(solveMemo(str1, str2, i + 1, j + 1, memo))
                    .toString();
        } else {
            String opt1 = new StringBuilder()
                    .append(str1.charAt(i))
                    .append(solveMemo(str1, str2, i + 1, j, memo))
                    .toString();

            String opt2 = new StringBuilder()
                    .append(str2.charAt(j))
                    .append(solveMemo(str1, str2, i, j + 1, memo))
                    .toString();

            memo[i][j] = opt1.length() < opt2.length() ? opt1 : opt2;
        }

        return memo[i][j];
    }

    /**
     * ========================================================================
     * PHASE 3 & 4: TABULATION & RECONSTRUCTION (The "Production" stage)
     * ========================================================================
     * Intuition:
     * To solve the memory crisis of Phase 2, we stop caching strings. Instead,
     * we cache the LENGTH of the Longest Common Subsequence (LCS) using an
     * integer DP table. Once the integer table is fully built, it acts as a map.
     * We start at dp[m][n] and backtrack our way to dp[0][0], building the
     * final string exactly once.
     * * Complexity Analysis:
     * - Time: O(M * N) to build the DP table + O(M + N) to backtrack.
     * - Space: O(M * N) strictly for the integer primitive matrix. No string bloat.
     */
    public String scsTabulation(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        // Step 1: Build the DP Table for Longest Common Subsequence (LCS)
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Backtrack to construct the SCS using StringBuilder
        StringBuilder scs = new StringBuilder();
        int i = m;
        int j = n;

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                // Match: It's part of the LCS. Add ONCE, move diagonally up-left.
                scs.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // No Match: Value above is greater. Add str1 char, move up.
                scs.append(str1.charAt(i - 1));
                i--;
            } else {
                // No Match: Value left is greater. Add str2 char, move left.
                scs.append(str2.charAt(j - 1));
                j--;
            }
        }

        // Step 3: Exhaust remaining characters
        while (i > 0) {
            scs.append(str1.charAt(i - 1));
            i--;
        }
        while (j > 0) {
            scs.append(str2.charAt(j - 1));
            j--;
        }

        // We built from the end of the strings to the beginning, so reverse it
        return scs.reverse().toString();
    }
}
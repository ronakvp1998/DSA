package com.questions.strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * 1092. SHORTEST COMMON SUPERSEQUENCE (LCS APPROACH)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings str1 and str2, return the shortest string that has both
 * str1 and str2 as subsequences. If there are multiple valid strings, return any.
 * A string s is a subsequence of string t if deleting some number of characters
 * from t (possibly 0) results in the string s.
 * * CONSTRAINTS:
 * - 1 <= str1.length, str2.length <= 1000
 * - str1 and str2 consist of lowercase English letters.
 * * EXAMPLES:
 * Example 1:
 * Input: str1 = "abac", str2 = "cab" | Output: "cabac"
 * Explanation:
 * str1 = "abac" is a subsequence of "cabac" (delete first 'c').
 * str2 = "cab" is a subsequence of "cabac" (delete last "ac").
 * * Example 2:
 * Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa" | Output: "aaaaaaaa"
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "LCS" FORMULA & BACKTRACKING
 * ============================================================================
 * The core mathematical trick:
 * To make the supersequence as SHORT as possible, we must avoid duplicating
 * the characters that both strings share in the exact same order.
 * The shared characters we preserve exactly form the Longest Common Subsequence (LCS).
 * * Formula for Length:
 * Length of SCS = str1.length + str2.length - length(LCS)
 * * Example: str1 = "abac", str2 = "cab"
 * LCS("abac", "cab") = "ab" (Length 2)
 * SCS Length = 4 + 3 - 2 = 5.
 * * To PRINT the SCS:
 * We build the LCS DP table, then backtrack from the bottom-right (m, n):
 * 1. If str1[i-1] == str2[j-1]: It's part of the LCS! Include it ONCE. Move ↖️.
 * 2. If str1[i-1] != str2[j-1]: Look at the DP table. Move towards the LARGER value.
 * - If we move ⬆️ (up), we are leaving a char from str1 behind. Include it!
 * - If we move ⬅️ (left), we are leaving a char from str2 behind. Include it!
 * 3. If we hit the top or left edge, just include the remaining characters
 * of the other string.
 * * EXACT FINAL DP ARRAY STATE FOR LCS (str1 = "abac", str2 = "cab"):
 * (Rows = str1 prefixes i, Cols = str2 prefixes j)
 * ""   c   a   b
 * "" [ 0,  0,  0,  0 ]
 * a  [ 0,  0,  1,  1 ]
 * b  [ 0,  0,  1,  2 ]
 * a  [ 0,  0,  1,  2 ]
 * c  [ 0,  1,  1,  2 ]  <- dp[4][3] is LCS length (2)
 * * Backtracking Trace:
 * dp[4][3] (c!=b, up >= left) -> add 'c', move up to dp[3][3]
 * dp[3][3] (a!=b, up < left)  -> add 'b', move left to dp[3][2]
 * dp[3][2] (a==a)             -> add 'a', move diagonal to dp[2][1]
 * dp[2][1] (b!=c, up >= left) -> add 'b', move up to dp[1][1]
 * dp[1][1] (a!=c, up < left)  -> add 'c', move left to dp[1][0]
 * dp[1][0] (j=0)              -> add remaining str1 ('a')
 * Result built backwards: "cabac".
 * ============================================================================
 */
public class ShortestCommonSupersequencePrint {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We build the supersequence directly. If characters match, we take it once.
     * If they don't match, we branch: either take str1's character or str2's
     * character, and return the SHORTER resulting string.
     * * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Branching factor of 2 at every mismatch.
     * - Space: O(M+N) stack space + massive Heap space for Strings. Will MLE/TLE.
     */
    public String scsRecursive(String str1, String str2) {
        return solveRecursive(str1.length(), str2.length(), str1, str2);
    }

    private String solveRecursive(int i, int j, String s1, String s2) {
        if (i == 0) return s2.substring(0, j); // If s1 is empty, append rest of s2
        if (j == 0) return s1.substring(0, i); // If s2 is empty, append rest of s1

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return solveRecursive(i - 1, j - 1, s1, s2) + s1.charAt(i - 1);
        }

        String skipS1 = solveRecursive(i - 1, j, s1, s2) + s1.charAt(i - 1);
        String skipS2 = solveRecursive(i, j - 1, s1, s2) + s2.charAt(j - 1);

        return skipS1.length() < skipS2.length() ? skipS1 : skipS2;
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION + BACKTRACK (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * Returning Strings recursively causes Memory Limit Exceeded (MLE).
     * Instead, we use Memoization to compute the *lengths* of the LCS in O(M*N).
     * Once the memo table is filled, we use our backtracking logic to trace
     * the path and construct the actual shortest supersequence.
     * * COMPLEXITY:
     * - Time: O(M * N) to fill memo + O(M + N) to backtrack. Total: O(M * N).
     * - Space: O(M * N) for the Heap (memo array) + O(M + N) Call Stack.
     */
    public String scsMemo(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];

        // 1. Fill the memo table with LCS lengths
        lcsMemo(m, n, str1, str2, memo);

        // 2. Backtrack to build the string
        return backtrackSCS(str1, str2, memo);
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
     * We convert the recursion into a standard iterative 2D DP table for LCS.
     * After building the table, we start at dp[M][N] and work backwards to (0,0).
     * If chars match, we append once. If not, we append the character from the
     * string whose DP path was chosen, effectively preserving the remaining string.
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (str1="abac", str2="cab", m=4, n=3):
     * 0   1   2   3
     * 0 [  0,  0,  0,  0 ] <- Base case row (str1 empty)
     * 1 [  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0 ]
     * 3 [  0,  0,  0,  0 ]
     * 4 [  0,  0,  0,  0 ]
     * * COMPLEXITY:
     * - Time: O(M * N) -> Nested loops + O(M + N) backtracking.
     * - Space: O(M * N) -> Heap memory for the DP table. Auxiliary Stack is eliminated!
     */
    public String scsTabulation(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        Integer[][] dp = new Integer[m + 1][n + 1];

        // Base cases initialized to 0
        for (int i = 0; i <= m; i++) dp[i][0] = 0;
        for (int j = 0; j <= n; j++) dp[0][j] = 0;

        // Fill table with LCS values
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return backtrackSCS(str1, str2, dp);
    }

    /**
     * Universal Backtracking Helper for Top-Down and Bottom-Up
     */
    private String backtrackSCS(String s1, String s2, Integer[][] table) {
        int i = s1.length();
        int j = s2.length();
        StringBuilder scsString = new StringBuilder();

        while (i > 0 && j > 0) {
            // Match found: Part of LCS, add it ONCE, move diagonally
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                scsString.append(s1.charAt(i - 1));
                i--;
                j--;
            } else {
                // Safely handle nulls (in case this is called from Memoization)
                int up = (table[i - 1][j] != null) ? table[i - 1][j] : 0;
                int left = (table[i][j - 1] != null) ? table[i][j - 1] : 0;

                // Move in the direction of the maximum value.
                // We must append the character we are "leaving behind"
                if (up >= left) {
                    scsString.append(s1.charAt(i - 1));
                    i--;
                } else {
                    scsString.append(s2.charAt(j - 1));
                    j--;
                }
            }
        }

        // Edge cases: If we hit the boundary of one string, we must append
        // ALL remaining characters of the other string.
        while (i > 0) {
            scsString.append(s1.charAt(i - 1));
            i--;
        }
        while (j > 0) {
            scsString.append(s2.charAt(j - 1));
            j--;
        }

        // Since we trace from the end, the string is built backwards.
        return scsString.reverse().toString();
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * CRITICAL LIMITATION: Space optimization (O(N) space) using a 1D array is
     * perfect for finding the *LENGTH* of the Shortest Common Supersequence using
     * our formula: (M + N - LCS).
     * However, to *PRINT* the SCS, we must backtrack through the entire history
     * of decisions made in the 2D grid. Pure O(N) space printing requires
     * Hirschberg's Algorithm (divide and conquer), which is beyond standard interviews.
     * This method demonstrates finding the LENGTH ONLY.
     * * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Two 1D arrays.
     */
    public int lengthSCSSpaceOptimized(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            prev = curr.clone();
        }

        int lcsLength = prev[n];
        return m + n - lcsLength;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        ShortestCommonSupersequencePrint solver = new ShortestCommonSupersequencePrint();

        System.out.println("=== Test Case 1: Standard Example ===");
        String s1 = "abac", s2 = "cab";
        System.out.println("Recursion String  : " + solver.scsRecursive(s1, s2));
        System.out.println("Memoization String: " + solver.scsMemo(s1, s2));
        System.out.println("Tabulation String : " + solver.scsTabulation(s1, s2));
        System.out.println("Space Opt Length  : " + solver.lengthSCSSpaceOptimized(s1, s2));
        System.out.println("Expected String   : cabac (Length 5)\n");

        System.out.println("=== Test Case 2: Identical Strings ===");
        String s3 = "aaaaaaaa", s4 = "aaaaaaaa";
        System.out.println("Tabulation String : " + solver.scsTabulation(s3, s4));
        System.out.println("Expected String   : aaaaaaaa\n");

        System.out.println("=== Test Case 3: Completely Disjoint Strings ===");
        String s5 = "abc", s6 = "def";
        System.out.println("Tabulation String : " + solver.scsTabulation(s5, s6));
        System.out.println("Expected String   : abcdef (or any valid combination)\n");

        System.out.println("=== Test Case 4: One String is Empty ===");
        String s7 = "", s8 = "hello";
        System.out.println("Tabulation String : " + solver.scsTabulation(s7, s8));
        System.out.println("Expected String   : hello\n");
    }
}
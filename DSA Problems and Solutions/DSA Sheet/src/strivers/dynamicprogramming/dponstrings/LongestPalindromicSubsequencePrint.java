package strivers.dynamicprogramming.dponstrings;

/**
 * ============================================================================
 * PRINT LONGEST PALINDROMIC SUBSEQUENCE (LCS APPROACH)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given a string s, find and print the longest palindromic subsequence's string.
 * A subsequence is a sequence that can be derived from another sequence by
 * deleting some or no elements without changing the order of the remaining elements.
 * * CONSTRAINTS:
 * - 1 <= s.length <= 1000
 * - s consists only of lowercase English letters.
 * * EXAMPLES:
 * Example 1:
 * Input: s = "bbbab"
 * Output: "bbbb"
 * * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: THE "LCS" BRIDGE (s = "cbbd")
 * ============================================================================
 * The elegant trick: To find the LPS of a string 'S', we simply find the
 * Longest Common Subsequence (LCS) between 'S' and 'reverse(S)'.
 * * Let s1 = "cbbd"
 * Let s2 = "dbbc" (reverse of s1)
 * * RECURSION TREE (Returning Strings):
 * Let f(i, j) = LCS String of s1[0..i-1] and s2[0..j-1]
 * * f(4, 4) ["cbbd", "dbbc"]
 * ('d' != 'c') -> Mismatch! Branch and take longest.
 * /                         \
 * f(3, 4) ["cbb", "dbbc"]              f(4, 3) ["cbbd", "dbb"]
 * (Ends match 'b' == 'c'? No)          (Ends match 'd' == 'b'? No)
 * ...                                  ...
 * Eventually we hit matching characters, e.g., 'b' == 'b', and append them.
 * * * EXACT FINAL DP ARRAY STATE (s1 = "cbbd", s2 = "dbbc"):
 * (Rows = s1 prefixes, Cols = s2 prefixes)
 * ""   d   b   b   c
 * "" [ 0,   0,  0,  0,  0 ]
 * c  [ 0,   0,  0,  0,  1 ]
 * b  [ 0,   0,  1,  1,  1 ]
 * b  [ 0,   0,  1,  2,  2 ]
 * d  [ 0,   1,  1,  2,  2 ]  <- Max length is 2. Backtrack from here!
 * ============================================================================
 */
public class LongestPalindromicSubsequencePrint {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * We reverse the string `s` to create `s2`. We then use a recursive LCS
     * algorithm that directly returns Strings.
     * 1. If s1[i-1] == s2[j-1], we append the character to the result of the
     * remaining substrings.
     * 2. If they don't match, we branch (skip s1 char OR skip s2 char) and
     * return the longer of the two resulting strings.
     * * COMPLEXITY:
     * - Time: O(2^(2N)) -> Branching factor of 2 for strings of length N.
     * - Space: O(N) auxiliary stack space + massive Heap space for String creation.
     */
    public String printLPSRecursive(String s) {
        String reverseS = new StringBuilder(s).reverse().toString();
        return lcsStringRecursive(s.length(), s.length(), s, reverseS);
    }

    private String lcsStringRecursive(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return "";

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return lcsStringRecursive(i - 1, j - 1, s1, s2) + s1.charAt(i - 1);
        }

        String skipS1 = lcsStringRecursive(i - 1, j, s1, s2);
        String skipS2 = lcsStringRecursive(i, j - 1, s1, s2);

        return skipS1.length() > skipS2.length() ? skipS1 : skipS2;
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * Returning Strings recursively causes Memory Limit Exceeded (MLE).
     * Instead, we use Memoization to find the *length* of the LCS in O(N^2) time.
     * Once the memo table is filled, we write a backtracking loop to trace the
     * path and construct the actual palindrome string.
     * * COMPLEXITY:
     * - Time: O(N^2) to fill the memo table + O(N) to backtrack. Total: O(N^2).
     * - Space: O(N^2) for the Heap (memo array) + O(N) Call Stack.
     */
    public String printLPSMemo(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        Integer[][] memo = new Integer[n + 1][n + 1];

        // 1. Fill the memo table with lengths
        lcsMemo(n, n, s, reverseS, memo);

        // 2. Backtrack to build the string
        return backtrackLCS(s, reverseS, memo);
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
     * We convert the recursion into a standard iterative 2D DP table.
     * After building the table, we start at dp[N][N] and work backwards to (0,0).
     * If the characters matched, they belong in our Palindrome, so we append
     * them and move diagonally up-left. Otherwise, we move in the direction
     * of the larger DP value.
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (s1="cbbd", s2="dbbc", n=4):
     * 0   1   2   3   4
     * 0 [  0,  0,  0,  0,  0 ] <- Base case row (s1 empty)
     * 1 [  0,  0,  0,  0,  0 ]
     * 2 [  0,  0,  0,  0,  0 ]
     * 3 [  0,  0,  0,  0,  0 ]
     * 4 [  0,  0,  0,  0,  0 ]
     * * COMPLEXITY:
     * - Time: O(N^2) -> Nested loops + O(N) backtracking.
     * - Space: O(N^2) -> Heap memory for the DP table. Auxiliary Stack is eliminated!
     */
    public String printLPSTabulation(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        Integer[][] dp = new Integer[n + 1][n + 1];

        // Base cases initialized to 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
            dp[0][i] = 0;
        }

        // Fill table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == reverseS.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        //
        return backtrackLCS(s, reverseS, dp);
    }

    /**
     * Universal Backtracking Helper for Top-Down and Bottom-Up
     */
    private String backtrackLCS(String s1, String s2, Integer[][] table) {
        int i = s1.length();
        int j = s2.length();
        StringBuilder lcsString = new StringBuilder();

        while (i > 0 && j > 0) {
            // If characters match, add to our result and move diagonally
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcsString.append(s1.charAt(i - 1));
                i--;
                j--;
            } else {
                // Safely handle nulls (in case this is called from Memoization)
                int up = (table[i - 1][j] != null) ? table[i - 1][j] : 0;
                int left = (table[i][j - 1] != null) ? table[i][j - 1] : 0;

                // Move in the direction of the maximum value
                if (up >= left) {
                    i--;
                } else {
                    j--;
                }
            }
        }
        // Since we trace from the end of the strings to the start, the string
        // is built in reverse. We must reverse it back to get the correct order.
        return lcsString.reverse().toString();
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * In Tabulation, `dp[i][j]` only relies on the current row `i` and the
     * previous row `i-1`. We can optimize the DP array from O(N^2) to O(N) using
     * two 1D arrays: `prev` and `curr`.
     * * CRITICAL LIMITATION: Space optimization is perfect for finding the *length*
     * of the LPS. However, to *print* the LPS, we must backtrack through the
     * entire history of decisions. Therefore, pure O(N) space printing requires
     * Hirschberg's Algorithm (divide and conquer), which is beyond standard interviews.
     * This method demonstrates finding the length only.
     * * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N) -> Two 1D arrays.
     */
    public int lengthLPSSpaceOptimized(String s) {
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
        LongestPalindromicSubsequencePrint solver = new LongestPalindromicSubsequencePrint();

        System.out.println("=== Test Case 1: Standard Example ===");
        String s1 = "bbbab";
        System.out.println("Recursion String  : " + solver.printLPSRecursive(s1));
        System.out.println("Memoization String: " + solver.printLPSMemo(s1));
        System.out.println("Tabulation String : " + solver.printLPSTabulation(s1));
        System.out.println("Space Opt Length  : " + solver.lengthLPSSpaceOptimized(s1));
        System.out.println("Expected          : bbbb\n");

        System.out.println("=== Test Case 2: Even Length Example ===");
        String s2 = "cbbd";
        System.out.println("Tabulation String : " + solver.printLPSTabulation(s2));
        System.out.println("Expected          : bb\n");

        System.out.println("=== Test Case 3: Fully Palindromic ===");
        String s3 = "racecar";
        System.out.println("Tabulation String : " + solver.printLPSTabulation(s3));
        System.out.println("Expected          : racecar\n");

        System.out.println("=== Test Case 4: Edge Case ===");
        String s4 = "a";
        System.out.println("Tabulation String : " + solver.printLPSTabulation(s4));
        System.out.println("Expected          : a\n");
    }
}
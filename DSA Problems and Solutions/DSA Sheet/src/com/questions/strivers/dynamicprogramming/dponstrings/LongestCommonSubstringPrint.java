package com.questions.strivers.dynamicprogramming.dponstrings;
/**
 * ============================================================================
 * PRINT LONGEST COMMON SUBSTRING (All 4 Approaches)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given two strings 's1' and 's2', find and print the Longest Common Substring.
 * A substring must be CONTIGUOUS (no gaps allowed).
 * * CONSTRAINTS:
 * - 1 <= s1.length, s2.length <= 1000
 * - s1 and s2 consist of lowercase English characters.
 * * EXAMPLES:
 * Example 1:
 * Input: s1 = "abcjklp", s2 = "acjpk"
 * Output: "cj"
 * Explanation: The longest contiguous sequence present in both is "cj".
 * ("cjp" is a subsequence, not a substring).
 * * Example 2:
 * Input: s1 = "wasdijkl", s2 = "wsdijk"
 * Output: "sdijk"
 * Explanation: The substring "sdijk" of length 5 is common to both.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s1 = "ab", s2 = "ac")
 * ============================================================================
 * Let f(i, j) represent the length of the longest common suffix strictly
 * ending at s1[i-1] and s2[j-1].
 * * f(2, 2) ["ab", "ac"]
 * ('b' != 'c') -> Continuity broken. Returns 0.
 * * f(1, 2) ["a", "ac"]
 * ('a' != 'c') -> Returns 0.
 * * f(2, 1) ["ab", "a"]
 * ('b' != 'a') -> Returns 0.
 * * f(1, 1) ["a", "a"]
 * ('a' == 'a') -> Match! Returns 1 + f(0, 0)
 * |
 * f(0, 0) ["", ""] -> Returns 0 (Base case)
 * * Global Max Tracking: We track the maximum value returned by any f(i, j)
 * and the corresponding end index 'i' in s1. Max = 1, endIndex = 1.
 * Result string = s1.substring(1 - 1, 1) = "a".
 * * * COMPLETE FINAL DP ARRAY STATE (s1 = "ab", s2 = "ac"):
 * (Rows = s1 prefixes: "", "a", "b")
 * (Cols = s2 prefixes: "", "a", "c")
 * * ""   a   c
 * ""  [ 0,  0,  0 ]
 * a   [ 0,  1,  0 ]  <- Max value is 1, occurring at row i=1.
 * b   [ 0,  0,  0 ]
 */

public class LongestCommonSubstringPrint {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * To find the longest contiguous substring, we calculate the longest common
     * suffix ending at EVERY possible pair of indices (i, j).
     * We iterate through all (i, j) pairs, and for each pair, recursively check
     * how far back the match goes. We maintain a global `maxLen` and the `endIndex`
     * in string `s1` where this max length was found.
     * Finally, we extract the string using `s1.substring(endIndex - maxLen, endIndex)`.
     * * COMPLEXITY:
     * - Time: O(M * N * min(M, N)) -> We loop M*N times. For each, recursion
     * goes back up to min(M, N) steps.
     * - Space: O(min(M, N)) -> Auxiliary Stack Space for the recursion depth.
     * O(1) Heap Space.
     */
    public String printLcsSubstringRecursive(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int maxLen = 0;
        int endIndex = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int len = suffixLengthRecursive(i, j, s1, s2);
                if (len > maxLen) {
                    maxLen = len;
                    endIndex = i;
                }
            }
        }

        if (maxLen == 0) return "";
        return s1.substring(endIndex - maxLen, endIndex);
    }

    private int suffixLengthRecursive(int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) return 0;

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + suffixLengthRecursive(i - 1, j - 1, s1, s2);
        }
        return 0; // Continuity broken
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursive calls repeatedly calculate the common suffix for the same
     * (i, j) pairs. We can store these lengths in a `memo[i][j]` table.
     * If `memo[i][j]` is not null, we return it immediately.
     * * COMPLEXITY:
     * - Time: O(M * N) -> Every pair (i, j) is calculated at most once.
     * - Space: O(M * N) Heap Space for `memo` matrix + O(min(M, N)) Stack Space.
     */
    public String printLcsSubstringMemo(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];

        int maxLen = 0;
        int endIndex = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int len = suffixLengthMemo(i, j, s1, s2, memo);
                if (len > maxLen) {
                    maxLen = len;
                    endIndex = i;
                }
            }
        }

        if (maxLen == 0) return "";
        return s1.substring(endIndex - maxLen, endIndex);
    }

    private int suffixLengthMemo(int i, int j, String s1, String s2, Integer[][] memo) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return memo[i][j] = 1 + suffixLengthMemo(i - 1, j - 1, s1, s2, memo);
        }
        return memo[i][j] = 0;
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We invert the memoization into a 2D `dp` array where `dp[i][j]` explicitly
     * stores the length of the matching suffix ending at `s1[i-1]` and `s2[j-1]`.
     * We populate it iteratively from top-left to bottom-right, keeping track of
     * the maximum value and its row index.
     * * EXACT DEFAULT STATE OF DP ARRAY (s1 = "ab", s2 = "ac"):
     * After base case initialization (all 0s naturally in Java), before loops:
     * ""   a   c
     * "" [ 0,  0,  0 ]
     * a  [ 0,  0,  0 ]
     * b  [ 0,  0,  0 ]
     * * COMPLEXITY:
     * - Time: O(M * N) -> Two nested loops evaluating states in O(1) each.
     * - Space: O(M * N) Heap Space for the DP table. No Stack Space overhead.
     */
    public String printLcsSubstringTabulation(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        int maxLen = 0;
        int endIndex = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];

                    // Track maximum contiguous length and its end position
                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        endIndex = i;
                    }
                } else {
                    dp[i][j] = 0; // Continuity broken
                }
            }
        }

        if (maxLen == 0) return "";
        return s1.substring(endIndex - maxLen, endIndex);
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * Notice that `dp[i][j]` only ever depends on `dp[i-1][j-1]` (the cell
     * diagonally top-left from the previous row).
     * We can completely discard older rows and use a single 1D array of size N+1.
     * By traversing the inner loop backwards (right-to-left), `dp[j-1]` still
     * contains the value from the *previous* row before we overwrite it,
     * allowing perfect space optimization.
     * * COMPLEXITY:
     * - Time: O(M * N) -> Iteration count remains unchanged.
     * - Space: O(N) Heap Space -> Reduced from an M*N matrix to a single array.
     */
    public String printLcsSubstringSpaceOptimized(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[] dp = new int[n + 1];
        int maxLen = 0;
        int endIndex = 0;

        for (int i = 1; i <= m; i++) {
            // CRITICAL: Traverse j backwards so dp[j-1] holds data from row i-1
            for (int j = n; j >= 1; j--) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[j] = 1 + dp[j - 1];

                    if (dp[j] > maxLen) {
                        maxLen = dp[j];
                        endIndex = i;
                    }
                } else {
                    dp[j] = 0; // Must explicitly reset to 0 because we reuse the array
                }
            }
        }

        if (maxLen == 0) return "";
        return s1.substring(endIndex - maxLen, endIndex);
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACHES (Suffix Array / Rolling Hash)
     * ========================================================================
     * INTUITION:
     * While DP provides an O(M*N) solution, modern competitive programming
     * environments with massive strings (M, N > 10^5) will Time Limit Exceed (TLE).
     * Two advanced alternatives exist:
     * 1. Binary Search + Rolling Hash (Rabin-Karp): Binary search the length
     * of the answer. For a chosen length 'L', hash all substrings of size 'L'
     * in s1 into a HashSet, then check s2 substrings against the set. O(N log N).
     * 2. Generalized Suffix Tree / Suffix Array: Concatenate s1 and s2 with
     * a unique delimiter (e.g., s1 + "#" + s2). Build a Suffix Array + LCP
     * (Longest Common Prefix) array in O(N log N). The max LCP between two
     * suffixes from different original strings is the answer.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestCommonSubstringPrint solver = new LongestCommonSubstringPrint();

        System.out.println("=== Test Case 1: Standard ===");
        String s1 = "abcjklp", s2 = "acjpk";
        System.out.println("Recursion  : " + solver.printLcsSubstringRecursive(s1, s2));
        System.out.println("Memoization: " + solver.printLcsSubstringMemo(s1, s2));
        System.out.println("Tabulation : " + solver.printLcsSubstringTabulation(s1, s2));
        System.out.println("Space Opt  : " + solver.printLcsSubstringSpaceOptimized(s1, s2));
        System.out.println("Expected   : cj\n");

        System.out.println("=== Test Case 2: Complete Match ===");
        String s3 = "hello", s4 = "hello";
        System.out.println("Space Opt  : " + solver.printLcsSubstringSpaceOptimized(s3, s4));
        System.out.println("Expected   : hello\n");

        System.out.println("=== Test Case 3: No Common Substring ===");
        String s5 = "abc", s6 = "xyz";
        System.out.println("Space Opt  : " + solver.printLcsSubstringSpaceOptimized(s5, s6));
        System.out.println("Expected   : \"\"\n");

        System.out.println("=== Test Case 4: Multiple Substrings (Returns first found) ===");
        String s7 = "wasdijkl", s8 = "wsdijk";
        System.out.println("Space Opt  : " + solver.printLcsSubstringSpaceOptimized(s7, s8));
        System.out.println("Expected   : sdijk");
    }
}
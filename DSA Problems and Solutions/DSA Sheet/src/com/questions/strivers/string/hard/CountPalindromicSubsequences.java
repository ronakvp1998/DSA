package com.questions.strivers.string.hard;

/**
 * ============================================================================
 * Count Palindromic Subsequences
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * **Problem Statement:**
 * Given a string s, find the number of palindromic subsequences (need not
 * necessarily be distinct) which could be formed from the string s.
 * Since the answer can be very large, return it modulo 10^9 + 7.
 * * Note: A subsequence is a sequence that can be derived from another sequence
 * by deleting some or no elements without changing the order of the remaining elements.
 * * **Example 1:**
 * Input: s = "aab"
 * Output: 4
 * Explanation: The palindromic subsequences are "a", "a", "b", "aa".
 * * **Example 2:**
 * Input: s = "bccb"
 * Output: 9
 * Explanation: "b", "c", "c", "b", "cc", "bb", "bcb", "bcb", "bccb".
 * * **Constraints:**
 * - 1 <= s.length <= 1000
 * - s consists of lowercase English letters.
 * * ---
 * * **Conceptual Visualization:**
 * Overlapping Subproblems: To find the number of palindromic subsequences in
 * s[i...j], we look at the characters s[i] and s[j].
 * Case 1: If s[i] != s[j], we sum the subsequences in s[i+1...j] and s[i...j-1],
 * but subtract s[i+1...j-1] because it was counted twice.
 * Case 2: If s[i] == s[j], we add the subsequences in s[i+1...j] and s[i...j-1],
 * and add 1 (for the new palindrome formed by s[i] and s[j] alone).
 * * Recursion Tree (for s = "aab", tracking indices (i, j)):
 * f(0, 2) ["aab"] -> s[0]!=s[2]
 * /            |             \
 * f(1, 2)["ab"]   f(0, 1)["aa"]   -f(1, 1)["a"]
 * /    |   \        /    |    \          |
 * f(2,2) f(1,1) -f(2,1) f(1,1) f(0,0) +1       1
 * * Final DP array filled for Example 1 ("aab"):
 * (Rows = i (start index), Cols = j (end index))
 * 0(a)  1(a)  2(b)
 * ------------------
 * 0 |  1     3     4
 * 1 |  0     1     2
 * 2 |  0     0     1
 * * (Total valid palindromic subsequences = dp[0][2] = 4)
 * ============================================================================
 */
public class CountPalindromicSubsequences {

    private static final int MOD = 1000000007;

    /**
     * ### Phase 1: Brute Force Recursion - The "Think it" stage.
     * * **Detailed Intuition:**
     * The core idea is based on the Inclusion-Exclusion principle. We recursively
     * check the bounds i and j. If the characters match, we can form new palindromes
     * by wrapping the inner palindromes with s[i] and s[j], plus the pair itself (+1).
     * If they don't match, we take the sum of both sub-segments and subtract the
     * intersection to avoid double-counting.
     * * **Complexity Analysis:**
     * - **Time (O):** Exponential, roughly O(2.41^N). The recurrence relation is
     * T(N) = 2*T(N-1) + T(N-2), leading to massive overlapping subproblems.
     * - **Space (O):** O(N) auxiliary stack space for the recursion depth.
     * O(1) heap space.
     */
    public int phase1BruteForce(String s) {
        return (int) solveBrute(s, 0, s.length() - 1);
    }

    private long solveBrute(String s, int i, int j) {
        if (i > j) return 0;
        if (i == j) return 1;

        if (s.charAt(i) == s.charAt(j)) {
            long res = solveBrute(s, i + 1, j) + solveBrute(s, i, j - 1) + 1;
            return (res % MOD + MOD) % MOD;
        } else {
            long res = solveBrute(s, i + 1, j) + solveBrute(s, i, j - 1) - solveBrute(s, i + 1, j - 1);
            return (res % MOD + MOD) % MOD; // Add MOD before modulo to handle negative values
        }
    }

    /**
     * ### Phase 2: Top-Down Memoization - The "Refine it" stage.
     * * **Detailed Intuition:**
     * The recursion tree reveals that we are recalculating the same substrings
     * (like f(1,1) in the visual) multiple times. We can cache the results of
     * f(i, j) in a 2D Long array to fetch them in O(1) time if seen again.
     * * **Complexity Analysis:**
     * - **Time (O):** O(N^2). There are N^2 possible states (i, j pairs), and
     * each state is computed exactly once.
     * - **Space (O):** O(N^2) heap space for the `memo` table + O(N) auxiliary
     * stack space for recursion overhead.
     */
    public int phase2Memoization(String s) {
        int n = s.length();
        Long[][] memo = new Long[n][n];
        return (int) solveMemo(s, 0, n - 1, memo);
    }

    private long solveMemo(String s, int i, int j, Long[][] memo) {
        if (i > j) return 0;
        if (i == j) return 1;
        if (memo[i][j] != null) return memo[i][j];

        long ans;
        if (s.charAt(i) == s.charAt(j)) {
            ans = solveMemo(s, i + 1, j, memo) + solveMemo(s, i, j - 1, memo) + 1;
        } else {
            ans = solveMemo(s, i + 1, j, memo) + solveMemo(s, i, j - 1, memo) - solveMemo(s, i + 1, j - 1, memo);
        }

        // Ensure strictly positive modulo
        memo[i][j] = (ans % MOD + MOD) % MOD;
        return memo[i][j];
    }

    /**
     * ### Phase 3: Bottom-Up Tabulation - The "Build it" stage.
     * * EXACT DEFAULT STATE OF THE DP ARRAY (for "aab", N=3) BEFORE NESTED LOOPS:
     * 0(a)  1(a)  2(b)
     * ------------------
     * 0 |  1     0     0
     * 1 |  0     1     0
     * 2 |  0     0     1
     * * * **Detailed Intuition:**
     * We convert the memoization into an iterative table. To ensure that the
     * subproblems `dp[i+1][j]`, `dp[i][j-1]`, and `dp[i+1][j-1]` are already computed
     * before calculating `dp[i][j]`, we must iterate `i` downwards from N-1 to 0,
     * and `j` upwards from i+1 to N-1.
     * * **Complexity Analysis:**
     * - **Time (O):** O(N^2). Two nested loops fill the table.
     * - **Space (O):** O(N^2) heap space for the DP array. O(1) auxiliary stack space.
     */
    public int phase3Tabulation(String s) {
        int n = s.length();
        long[][] dp = new long[n][n];

        // Base cases: single characters are length-1 palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // i goes right-to-left, j goes left-to-right starting from i+1
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = (dp[i + 1][j] + dp[i][j - 1] + 1) % MOD;
                } else {
                    dp[i][j] = (dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1]) % MOD;
                }
                dp[i][j] = (dp[i][j] + MOD) % MOD; // Handle negative modulo
            }
        }
        return (int) dp[0][n - 1];
    }

    /**
     * ### Phase 4: Space Optimization - The "Perfect it" stage.
     * * **Detailed Intuition:**
     * Looking at the state transition in Tabulation: `dp[i][j]` only depends on the
     * current row `dp[i]` (specifically `dp[i][j-1]`) and the next row `dp[i+1]`
     * (specifically `dp[i+1][j]` and `dp[i+1][j-1]`).
     * We don't need the entire NxN matrix! We can just keep track of two 1D arrays:
     * `prev` (representing the i+1 row) and `curr` (representing the i row).
     * * **Complexity Analysis:**
     * - **Time (O):** O(N^2). The nested loop structure remains exactly the same.
     * - **Space (O):** O(N) heap space for the two 1D arrays of size N.
     * O(1) auxiliary stack space. This is optimal!
     */
    public int phase4SpaceOptimization(String s) {
        int n = s.length();
        long[] prev = new long[n]; // Represents dp[i+1]
        long[] curr = new long[n]; // Represents dp[i]

        for (int i = n - 1; i >= 0; i--) {
            curr[i] = 1; // Base case equivalent to dp[i][i] = 1
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    curr[j] = (prev[j] + curr[j - 1] + 1) % MOD;
                } else {
                    curr[j] = (prev[j] + curr[j - 1] - prev[j - 1]) % MOD;
                }
                curr[j] = (curr[j] + MOD) % MOD;
            }
            // Move curr to prev for the next iteration (i decrements)
            System.arraycopy(curr, i, prev, i, n - i);
        }
        return (int) curr[n - 1];
    }

    /**
     * ### Phase 5: Alternative Approach (Bitmask / Generate All)
     * * **Detailed Intuition:**
     * For extremely small strings (N <= 20), we can use Bit Manipulation to generate
     * all 2^N possible subsequences. If the i-th bit is set, include the i-th character.
     * Check if each generated subsequence is a palindrome. This acts as a robust
     * validation tool for testing the DP logic.
     * * **Complexity Analysis:**
     * - **Time (O):** O(N * 2^N). 2^N subsequences, O(N) to check each.
     * - **Space (O):** O(N) heap space to build the substring. O(1) stack.
     */
    public int phase5Bitmask(String s) {
        int n = s.length();
        if (n > 20) return -1; // Prevent TLE/Overflow for large strings

        int count = 0;
        int totalSubsequences = 1 << n; // 2^n

        // Start from 1 to skip the empty subsequence
        for (int mask = 1; mask < totalSubsequences; mask++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sb.append(s.charAt(i));
                }
            }
            if (isPalindrome(sb.toString())) {
                count = (count + 1) % MOD;
            }
        }
        return count;
    }

    private boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--)) return false;
        }
        return true;
    }

    /**
     * ### 4. Testing Suite
     */
    public static void main(String[] args) {
        CountPalindromicSubsequences solver = new CountPalindromicSubsequences();

        String[] testCases = {
                "aab",          // Example 1
                "bccb",         // Example 2
                "abcd",         // All unique characters
                "aaaa",         // All identical charactersl
                "a"             // Edge case: single character
        };

        int[] expectedOutputs = {4, 9, 4, 15, 1};

        System.out.println("Running Tests for Count Palindromic Subsequences\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            int expected = expectedOutputs[i];

            int brute = solver.phase1BruteForce(s);
            int memo = solver.phase2Memoization(s);
            int tab = solver.phase3Tabulation(s);
            int spaceOpt = solver.phase4SpaceOptimization(s);
            int bitmask = solver.phase5Bitmask(s);

            boolean passed = (brute == expected) &&
                    (memo == expected) &&
                    (tab == expected) &&
                    (spaceOpt == expected) &&
                    (bitmask == expected);

            System.out.printf("Test Case %d: s = \"%s\"%n", i + 1, s);
            System.out.printf("Expected: %d | Brute: %d | Memo: %d | Tab: %d | SpaceOpt: %d | Bitmask: %d%n",
                    expected, brute, memo, tab, spaceOpt, bitmask);
            System.out.println("Status: " + (passed ? "PASS" : "FAIL") + "\n");
        }
    }
}
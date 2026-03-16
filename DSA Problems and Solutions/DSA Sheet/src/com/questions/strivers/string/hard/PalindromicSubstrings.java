package com.questions.strivers.string.hard;

/**
 * ============================================================================
 * 647. Palindromic Substrings
 * ============================================================================
 * Given a string s, return the number of palindromic substrings in it.
 * * A string is a palindrome when it reads the same backward as forward.
 * A substring is a contiguous sequence of characters within the string.
 * * Example 1:
 * Input: s = "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 * * Example 2:
 * Input: s = "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 * * Constraints:
 * - 1 <= s.length <= 1000
 * - s consists of lowercase English letters.
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Dynamic Programming)
 * ============================================================================
 * Recursion Tree mapping the overlapping subproblems for s = "aaa":
 * To check if substring(0, 2) "aaa" is a palindrome, we check if s[0] == s[2]
 * AND if the inner substring(1, 1) "a" is a palindrome.
 * * isPal(0, 2) -> "aaa" (s[0]=='a', s[2]=='a' -> TRUE)
 * |
 * isPal(1, 1) -> "a" (Base case length 1 -> TRUE)
 * * Because "aaa" requires the answer to "a", we have overlapping subproblems.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class PalindromicSubstrings {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most literal translation of the problem is to generate every possible
     * contiguous substring, and for each one, check if it reads the same
     * backwards and forwards. If it does, we increment a counter.
     * * Complexity Analysis:
     * - Time Complexity: O(N^3). There are roughly N^2 / 2 substrings. For each
     * substring, the `isPalindrome` check takes up to O(N) time.
     * - Space Complexity: O(1) Auxiliary Heap Space, as we only use pointers.
     * ========================================================================
     */
    public static int countSubstringsBruteForce(String s) {
        if (s == null || s.length() == 0) return 0;

        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * ========================================================================
     * Phase 2: Top-Down Memoization - The "Refine it" stage.
     * ========================================================================
     * Detailed Intuition:
     * To avoid checking the same inner substrings multiple times, we can cache
     * the results of `isPalindrome(i, j)`. We use a Boolean matrix where
     * `memo[i][j]` stores whether the substring from index `i` to `j` is a
     * palindrome.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). We evaluate the palindromic state of each
     * substring bounds (i, j) exactly once.
     * - Space Complexity: O(N^2) Heap Space for the memoization table, plus
     * O(N) Auxiliary Stack Space for the recursive call stack depth.
     * ========================================================================
     */
    public static int countSubstringsMemoization(String s) {
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        Boolean[][] memo = new Boolean[n][n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalMemo(s, i, j, memo)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isPalMemo(String s, int i, int j, Boolean[][] memo) {
        if (i >= j) return true; // Base case: length 1 or 0 is a palindrome
        if (memo[i][j] != null) return memo[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = isPalMemo(s, i + 1, j - 1, memo);
        } else {
            memo[i][j] = false;
        }
        return memo[i][j];
    }

    /**
     * ========================================================================
     * Phase 3: Bottom-Up Tabulation - The "Build it" stage.
     * ========================================================================
     * Default state of DP array (boolean[][] dp) before loops for "aaa":
     * (All primitive booleans default to false in Java)
     * a a a
     * a F F F
     * a F F F
     * a F F F
     * * Final state of DP array for "aaa":
     * (Diagonal is length 1. '-' means unused half of matrix where i > j)
     * a a a
     * a T T T  (Row 0: 'a', 'aa', 'aaa' are all palindromes)
     * a - T T  (Row 1: 'a', 'aa' are palindromes)
     * a - - T  (Row 2: 'a' is a palindrome)
     * Total True count = 6
     * * Detailed Intuition:
     * We convert the recursion into an iterative table. `dp[i][j]` is true if
     * the substring from `i` to `j` is a palindrome. We populate the table
     * starting from substrings of length 1, then length 2, up to length N.
     * We increment our total count every time we assign `true` to a cell.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). We iterate through the N*N matrix once.
     * - Space Complexity: O(N^2) Heap Space to store the DP table.
     * ========================================================================
     */
    public static int countSubstringsTabulation(String s) {
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int count = 0;

        // Base case 1: Substrings of length 1
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            count++;
        }

        // Base case 2: Substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                count++;
            }
        }

        // General case: Substrings of length 3 or more
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1; // End index

                // A string is a palindrome if outer chars match AND inner string is a palindrome
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * ========================================================================
     * Phase 4: Space Optimization (Expand Around Center) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Just like in the "Longest Palindromic Substring" problem, we notice the
     * state of a palindrome `dp[i][j]` depends exclusively on `dp[i+1][j-1]`.
     * We don't need to keep an entire N*N grid in memory. We can just pick a
     * "center" and expand outwards as long as the left and right characters match.
     * There are 2N - 1 possible centers (N single-character centers for odd
     * length palindromes, and N - 1 two-character centers for even length).
     * Every successful outward expansion represents a new valid palindrome!
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). Expanding around a center takes O(N) worst case,
     * and we do this 2N - 1 times.
     * - Space Complexity: O(1) Auxiliary Space. Completely eliminates the DP table.
     * ========================================================================
     */
    public static int countSubstringsOptimal(String s) {
        if (s == null || s.length() == 0) return 0;

        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            // Count odd length palindromes centered at i
            count += countExpansions(s, i, i);
            // Count even length palindromes centered between i and i+1
            count += countExpansions(s, i, i + 1);
        }

        return count;
    }

    private static int countExpansions(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++; // Found a valid palindrome
            left--;  // Expand outward
            right++; // Expand outward
        }
        return count;
    }

    /**
     * ========================================================================
     * Phase 5: Alternative Approach (Manacher's Algorithm)
     * ========================================================================
     * Detailed Intuition:
     * Manacher's Algorithm transforms the string to handle even and odd lengths
     * uniformly (e.g., "aaa" -> "^#a#a#a#$"). It calculates an array `p` where
     * `p[i]` is the "radius" of the longest palindrome centered at `i`.
     * A beautiful property of Manacher's is that the radius `p[i]` in the
     * transformed string directly equals the *number of palindromes* centered
     * at that specific location in the original string!
     * We just sum up `p[i] / 2` for all centers.
     * * Complexity Analysis:
     * - Time Complexity: O(N). The aggressive mirroring skips redundant checks,
     * keeping the right boundary expansion strictly linear.
     * - Space Complexity: O(N) Auxiliary Heap Space to store the transformed
     * string and the radii array.
     * ========================================================================
     */
    public static int countSubstringsManacher(String s) {
        if (s == null || s.length() == 0) return 0;

        // Transform string
        StringBuilder t = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++) {
            t.append("#").append(s.charAt(i));
        }
        t.append("#$");

        int n = t.length();
        int[] p = new int[n];
        int center = 0, rightBoundary = 0;
        int totalPalindromes = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                p[i] = Math.min(rightBoundary - i, p[mirror]);
            }

            // Expand
            while (t.charAt(i + 1 + p[i]) == t.charAt(i - 1 - p[i])) {
                p[i]++;
            }

            // Adjust center
            if (i + p[i] > rightBoundary) {
                center = i;
                rightBoundary = i + p[i];
            }

            // The radius in the transformed string divided by 2 (integer division)
            // gives the exact number of palindromes centered here in the original string.
            // However, because of the '#' characters, we can actually just add
            // p[i] / 2 directly. If we look at the pure radius without the center itself:
            // Math.ceil(p[i]/2.0) logic applies, but in Java, integer division of
            // the expanded radius effectively counts the valid characters.
            // Specifically: (p[i] + 1) / 2 counts the palindromes.
            totalPalindromes += (p[i] + 1) / 2;
        }

        return totalPalindromes;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "abc",        // Example 1: No palindromes longer than 1
                "aaa",        // Example 2: Highly overlapping palindromes
                "a",          // Edge Case: Single character
                "racecar",    // Standard Case: Entire string is a palindrome
                "ababa",      // Alternating characters
                "aaaaa"       // All identical characters
        };

        System.out.println("Running test cases for 647. Palindromic Substrings...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            int res1 = countSubstringsBruteForce(input);
            int res2 = countSubstringsMemoization(input);
            int res3 = countSubstringsTabulation(input);
            int res4 = countSubstringsOptimal(input);
            int res5 = countSubstringsManacher(input);

            System.out.println("Phase 1 (Brute Force) : " + res1);
            System.out.println("Phase 2 (Memoization) : " + res2);
            System.out.println("Phase 3 (Tabulation)  : " + res3);
            System.out.println("Phase 4 (Space Opt)   : " + res4);
            System.out.println("Phase 5 (Manacher's)  : " + res5);

            // Validation step
            if (res1 == res2 && res2 == res3 && res3 == res4 && res4 == res5) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
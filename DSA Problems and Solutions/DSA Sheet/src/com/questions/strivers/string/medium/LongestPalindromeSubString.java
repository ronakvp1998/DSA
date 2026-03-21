package com.questions.strivers.string.medium;
/**
 * ============================================================================
 * 5. Longest Palindromic Substring
 * ============================================================================
 * Given a string s, return the longest palindromic substring in s.
 * * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 * * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 * * Constraints:
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters.
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Dynamic Programming)
 * ============================================================================
 * Recursion Tree mapping the overlapping subproblems:
 * To check if substring(0, 4) "babad" is a palindrome, we check if s[0] == s[4]
 * AND if the inner substring(1, 3) "aba" is a palindrome.
 * * isPal(0, 4) -> "babad" (s[0]=='b', s[4]=='d' -> FALSE)
 * /                     \
 * (Not evaluated because s[0]!=s[4])   ...
 * * But for substring(0, 2) "bab":
 * isPal(0, 2) -> "bab" (s[0]=='b', s[2]=='b' -> TRUE + check inner)
 * |
 * isPal(1, 1) -> "a" (Base case length 1 -> TRUE)
 * * This reveals overlapping subproblems: to know if a long string is a palindrome,
 * we must know if its inner strings are palindromes.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class LongestPalindromeSubString {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to generate all possible
     * substrings of `s`, check if each one is a palindrome, and keep track of
     * the longest valid one.
     * * Complexity Analysis:
     * - Time Complexity: O(N^3). There are O(N^2) total substrings. For each
     * substring, checking if it is a palindrome takes O(N) time.
     * - Space Complexity: O(1) Auxiliary Space, ignoring the space required
     * to return the final substring.
     * ========================================================================
     */
    public static String longestPalindromeBruteForce(String s) {
        if (s == null || s.length() < 2) return s;

        String longest = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (isPalindrome(s, i, j) && (j - i + 1) > longest.length()) {
                    longest = s.substring(i, j + 1);
                }
            }
        }
        return longest;
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
     * Since the Brute Force approach re-evaluates the same inner substrings
     * repeatedly, we can cache the results. We use a Boolean[][] memoization
     * table where memo[i][j] stores whether the substring from i to j is a
     * palindrome.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). We evaluate each substring bounds (i, j)
     * exactly once.
     * - Space Complexity: O(N^2) Heap Space for the memoization table, plus
     * O(N) Auxiliary Stack Space for the recursion depth.
     * ========================================================================
     */
    public static String longestPalindromeMemoization(String s) {
        if (s == null || s.length() < 2) return s;

        int n = s.length();
        // Boolean object wrapper used so null represents "uncalculated"
        Boolean[][] memo = new Boolean[n][n];

        int start = 0, maxLength = 1;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalMemo(s, i, j, memo)) {
                    if (j - i + 1 > maxLength) {
                        maxLength = j - i + 1;
                        start = i;
                    }
                }
            }
        }
        return s.substring(start, start + maxLength);
    }

    private static boolean isPalMemo(String s, int i, int j, Boolean[][] memo) {
        if (i >= j) return true; // Base case: length 1 or 0
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
     * Default state of DP array (boolean[][] dp) before loops:
     * (All primitive booleans default to false in Java)
     * b a b a d
     * b F F F F F
     * a F F F F F
     * b F F F F F
     * a F F F F F
     * d F F F F F
     * * Final state of DP array for "babad":
     * (Diagonal is length 1. '-' means unused half of matrix where i > j)
     * b a b a d
     * b T F T F F  (dp[0][2] is True -> "bab")
     * a - T F T F  (dp[1][3] is True -> "aba")
     * b - - T F F
     * a - - - T F
     * d - - - - T
     * * Detailed Intuition:
     * We can convert the recursive memoization into an iterative table.
     * dp[i][j] will be true if the substring from i to j is a palindrome.
     * A string of length > 2 is a palindrome if:
     * 1. Its boundary characters match (s[i] == s[j])
     * 2. The rest of the string inside is a palindrome (dp[i+1][j-1] == true)
     * We must fill the table starting from smaller lengths up to larger lengths.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). We iterate through the N*N matrix exactly once.
     * - Space Complexity: O(N^2) Heap Space to store the DP table.
     * ========================================================================
     */
    public static String longestPalindromeTabulation(String s) {
        if (s == null || s.length() < 2) return s;

        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int start = 0;
        int maxLength = 1;

        // Base case 1: All substrings of length 1 are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Base case 2: Check substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // General case: Substrings of length 3 or more
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1; // End index of the current substring

                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (len > maxLength) {
                        start = i;
                        maxLength = len;
                    }
                }
            }
        }

        return s.substring(start, start + maxLength);
    }

    /**
     * ========================================================================
     * Phase 4: Space Optimization (Expand Around Center) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Looking at the DP table transition `dp[i+1][j-1]`, we see that the state
     * of a string only depends on the state of its immediate inner string.
     * Instead of allocating an O(N^2) table, we can simply pick a "center"
     * and expand outwards as long as the characters match.
     * A palindrome can be centered on a single character (e.g., "aba" centered
     * on 'b') or between two characters (e.g., "abba" centered between 'b's).
     * Therefore, for a string of length N, there are 2N - 1 possible centers.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). Expanding around a center takes O(N) time in
     * the worst case, and we do this 2N - 1 times.
     * - Space Complexity: O(1) Auxiliary Space. We only maintain a few integer
     * pointers (start, end), completely eliminating the DP table.
     * ========================================================================
     */
    public static String longestPalindromeSpaceOptimized(String s) {
        if (s == null || s.length() < 2) return s;

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // Expand around single character center (odd length)
            int len1 = expandAroundCenter(s, i, i);
            // Expand around two character center (even length)
            int len2 = expandAroundCenter(s, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > end - start) {
                // Calculate new boundaries. Math logic handles both odd/even centers.
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // The length is (right - left - 1) because the loop breaks when bounds
        // fail or chars mismatch, meaning left and right have gone one step too far.
        return right - left - 1;
    }

    /**
     * ========================================================================
     * Phase 5: Alternative Approach (Manacher's Algorithm)
     * ========================================================================
     * Detailed Intuition:
     * Manacher's Algorithm discovers the longest palindromic substring in
     * strictly linear time. It does this by aggressively mirroring palindromic
     * radii found on the left side of the center to the right side, skipping
     * redundant expansions.
     * It transforms the string (e.g., "aba" -> "#a#b#a#") to gracefully handle
     * both even and odd length palindromes uniformly.
     * * Complexity Analysis:
     * - Time Complexity: O(N). The inner `while` loop only increments the right
     * boundary `r`, which can be incremented at most 2N times total across
     * the entire algorithm execution.
     * - Space Complexity: O(N) Auxiliary Space to store the transformed string
     * and the palindrome radii array.
     * ========================================================================
     */
    public static String longestPalindromeManacher(String s) {
        if (s == null || s.length() == 0) return "";

        // Transform S to T to handle even length palindromes
        // e.g., "aba" -> "^#a#b#a#$"
        StringBuilder t = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++) {
            t.append("#").append(s.charAt(i));
        }
        t.append("#$");

        int n = t.length();
        int[] p = new int[n]; // Array to store palindrome radii
        int center = 0, rightBoundary = 0;

        int maxLen = 0;
        int centerIndex = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i; // Center - (i - Center)

            // If i is within the right boundary, mirror the radius
            if (i < rightBoundary) {
                p[i] = Math.min(rightBoundary - i, p[mirror]);
            }

            // Expand around i
            while (t.charAt(i + 1 + p[i]) == t.charAt(i - 1 - p[i])) {
                p[i]++;
            }

            // If palindrome centered at i expands past the right boundary,
            // adjust center based on expanded palindrome.
            if (i + p[i] > rightBoundary) {
                center = i;
                rightBoundary = i + p[i];
            }

            // Track max length
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        // Extract substring from original string
        int start = (centerIndex - 1 - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "babad",      // Example 1: Odd length max palindrome
                "cbbd",       // Example 2: Even length max palindrome
                "a",          // Edge Case: Single character
                "ac",         // Edge Case: No palindromes > length 1
                "racecar",    // Standard Case: Entire string is a palindrome
                "aaaa",       // Edge Case: All identical characters
                "abacabacabb" // Complex overlapping palindromes
        };

        System.out.println("Running test cases for 5. Longest Palindromic Substring...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            String res1 = longestPalindromeBruteForce(input);
            String res2 = longestPalindromeMemoization(input);
            String res3 = longestPalindromeTabulation(input);
            String res4 = longestPalindromeSpaceOptimized(input);
            String res5 = longestPalindromeManacher(input);

            System.out.println("Phase 1 (Brute Force) : \"" + res1 + "\"");
            System.out.println("Phase 2 (Memoization) : \"" + res2 + "\"");
            System.out.println("Phase 3 (Tabulation)  : \"" + res3 + "\"");
            System.out.println("Phase 4 (Space Opt)   : \"" + res4 + "\"");
            System.out.println("Phase 5 (Manacher's)  : \"" + res5 + "\"");

            // Validation step
            // Note: Multiple valid answers exist (e.g., "babad" -> "bab" or "aba").
            // We validate length instead of direct string equality.
            int expectedLen = res4.length();
            if (res1.length() == expectedLen && res2.length() == expectedLen &&
                    res3.length() == expectedLen && res5.length() == expectedLen) {
                System.out.println("-> ALL PASS ✓ (Lengths Match)\n");
            } else {
                System.out.println("-> FAIL ✗ (Lengths Differ)\n");
            }
        }
    }
}
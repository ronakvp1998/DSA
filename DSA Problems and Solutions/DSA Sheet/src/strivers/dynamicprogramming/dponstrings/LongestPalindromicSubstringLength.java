package strivers.dynamicprogramming.dponstrings;


/**
 * ============================================================================
 * LONGEST PALINDROMIC SUBSTRING (LENGTH)
 * ============================================================================
 * * PROBLEM STATEMENT:
 * Given a string s, return the length of the longest palindromic substring in s.
 * A substring is a contiguous non-empty sequence of characters within a string.
 * A palindrome is a string that reads the same forward and backward.
 * (Note: This is a variation of LeetCode #5, focused purely on returning the length).
 * * * CONSTRAINTS:
 * - 1 <= s.length <= 1000
 * - s consist of only digits and English letters.
 * * * EXAMPLES:
 * Example 1:
 * Input: s = "babad"
 * Output: 3
 * Explanation: "bab" is a palindrome of length 3. ("aba" is also a valid answer).
 * * Example 2:
 * Input: s = "cbbd"
 * Output: 2
 * Explanation: "bb" is a palindrome of length 2.
 * * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (s = "babad")
 * ============================================================================
 * Let isPal(i, j) represent whether the substring from index i to j is a palindrome.
 * To find the maximum length, we conceptually evaluate isPal(i, j) for all pairs.
 * * Evaluating isPal(0, 4) ["babad"]:
 * ('b' != 'd') -> Mismatch! Return FALSE.
 * * Evaluating isPal(0, 2) ["bab"]:
 * ('b' == 'b') -> Match! Check inner substring -> isPal(1, 1)
 * |
 * isPal(1, 1) ["a"]
 * (i == j) -> Base Case: TRUE
 * -> "bab" is a palindrome. Length = 3.
 * * * EXACT FINAL DP ARRAY STATE (s = "babad"):
 * (Rows = start index i, Cols = end index j)
 * 0   1   2   3   4  (j)
 * 0 [ T,  F,  T,  F,  F ]   <- dp[0][2] is True ("bab", length 3)
 * 1 [ 0,  T,  F,  T,  F ]   <- dp[1][3] is True ("aba", length 3)
 * 2 [ 0,  0,  T,  F,  F ]
 * 3 [ 0,  0,  0,  T,  F ]
 * 4 [ 0,  0,  0,  0,  T ]
 * (i)
 * ============================================================================
 */
public class LongestPalindromicSubstringLength {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * The simplest way to find the longest palindromic substring is to generate
     * all possible substrings, check if each one is a palindrome, and keep
     * track of the maximum length found.
     * * COMPLEXITY:
     * - Time: O(N^3) -> O(N^2) to generate all substrings, and O(N) to check
     * if each one is a palindrome. Will cause Time Limit Exceeded (TLE) on LeetCode.
     * - Space: O(1) -> No extra space utilized beyond variables.
     */
    public int longestPalindromeBruteForce(String s) {
        int n = s.length();
        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * In the brute force approach, we repeatedly check the same inner substrings.
     * For example, to check if "ababa" is a palindrome, we check if "bab" is a
     * palindrome. But we might have already checked "bab" separately!
     * We can memoize the boolean result of isPal(i, j).
     * * State transition:
     * isPal(i, j) = (s[i] == s[j]) AND isPal(i+1, j-1)
     * * COMPLEXITY:
     * - Time: O(N^2) -> Each substring state (i, j) is evaluated exactly once.
     * - Space: O(N^2) Heap space for the Boolean matrix + O(N) Call Stack space.
     */
    public int longestPalindromeMemo(String s) {
        int n = s.length();
        if (n == 0) return 0;

        // Using Boolean object to allow 'null' for uncalculated states
        Boolean[][] memo = new Boolean[n][n];
        int maxLength = 1;

        // Drive the check for all possible substrings
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (solveMemo(s, i, j, memo)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    private boolean solveMemo(String s, int i, int j, Boolean[][] memo) {
        // Base cases
        if (i >= j) return true; // Single char or empty inner string is a palindrome

        // Return cached result if available
        if (memo[i][j] != null) return memo[i][j];

        // Recurrence relation
        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = solveMemo(s, i + 1, j - 1, memo);
        } else {
            memo[i][j] = false;
        }

        return memo[i][j];
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We can build the DP table iteratively. Since dp[i][j] relies on dp[i+1][j-1]
     * (the row below and the column to the left), we must iterate 'i' backward
     * from (N-1 down to 0) and 'j' forward from (i up to N-1).
     * * EXACT DEFAULT STATE BEFORE NESTED LOOPS (s = "babad"):
     * 0   1   2   3   4
     * 0 [ F,  F,  F,  F,  F ]
     * 1 [ F,  F,  F,  F,  F ]
     * 2 [ F,  F,  F,  F,  F ]
     * 3 [ F,  F,  F,  F,  F ]
     * 4 [ F,  F,  F,  F,  F ]
     * Note: In Java, boolean arrays default to false.
     * * COMPLEXITY:
     * - Time: O(N^2) -> Strictly nested loops.
     * - Space: O(N^2) -> Heap memory for the DP table. Auxiliary Stack space eliminated.
     */
    public int longestPalindromeTabulation(String s) {
        int n = s.length();
        if (n == 0) return 0;

        boolean[][] dp = new boolean[n][n];
        int maxLength = 1;

        // i must go backward to ensure dp[i+1] is already calculated
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = true; // Every single character is a palindrome

            for (int j = i + 1; j < n; j++) {
                // For length 2 (j - i == 1), we just check if chars match.
                // For length > 2, chars must match AND inner string must be palindrome.
                if (s.charAt(i) == s.charAt(j) && (j - i == 1 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * Notice in the Tabulation approach that calculating the current row `i`
     * ONLY requires data from the immediate next row `i+1`. We don't need the
     * entire N*N grid in memory. We can compress this to a 1D array representing
     * the "previous row" (which conceptually is row i+1).
     * * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N) -> A single 1D boolean array.
     */
    public int longestPalindromeSpaceOptimized(String s) {
        int n = s.length();
        if (n == 0) return 0;

        // dp[j] will represent dp[i+1][j] from the 2D matrix
        boolean[] dp = new boolean[n];
        int maxLength = 1;

        for (int i = n - 1; i >= 0; i--) {
            // We need to traverse j backwards so we don't overwrite dp[j-1]
            // before we need it for the current row calculation.
            for (int j = n - 1; j >= i; j--) {
                if (i == j) {
                    dp[j] = true;
                } else if (s.charAt(i) == s.charAt(j) && (j - i == 1 || dp[j - 1])) {
                    dp[j] = true;
                    maxLength = Math.max(maxLength, j - i + 1);
                } else {
                    dp[j] = false; // Must explicitly reset for this iteration
                }
            }
        }
        return maxLength;
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH - EXPAND AROUND CENTER
     * ========================================================================
     * INTUITION:
     * While DP is a great educational exercise, it is not the most optimal
     * standard solution for this specific problem due to space constraints.
     * Since a palindrome mirrors around its center, we can simply iterate through
     * the string, treat each character (and pair of characters) as a potential
     * center, and expand outwards as long as it remains a palindrome.
     * * COMPLEXITY:
     * - Time: O(N^2) -> Expanding takes O(N), done for 2N-1 centers.
     * - Space: O(1) -> No arrays, just pointers! This is the industry standard.
     */
    public int longestPalindromeExpandCenter(String s) {
        if (s == null || s.length() < 1) return 0;

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            // Palindromes can be odd (center is 1 char) or even (center is 2 chars)
            int len1 = expandAroundCenter(s, i, i);       // Odd length
            int len2 = expandAroundCenter(s, i, i + 1);   // Even length

            int len = Math.max(len1, len2);
            maxLength = Math.max(maxLength, len);
        }

        return maxLength;
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // Length is (right - left - 1) because the while loop breaks AFTER
        // pointers have expanded one step past the valid palindrome boundaries.
        return right - left - 1;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        LongestPalindromicSubstringLength solver = new LongestPalindromicSubstringLength();

        System.out.println("=== Test Case 1: Standard Example ===");
        String s1 = "babad";
        System.out.println("Brute Force : " + solver.longestPalindromeBruteForce(s1));
        System.out.println("Memoization : " + solver.longestPalindromeMemo(s1));
        System.out.println("Tabulation  : " + solver.longestPalindromeTabulation(s1));
        System.out.println("Space Opt   : " + solver.longestPalindromeSpaceOptimized(s1));
        System.out.println("Expand Center: " + solver.longestPalindromeExpandCenter(s1));
        System.out.println("Expected    : 3\n");

        System.out.println("=== Test Case 2: Even Length Output ===");
        String s2 = "cbbd";
        System.out.println("Tabulation  : " + solver.longestPalindromeTabulation(s2));
        System.out.println("Expand Center: " + solver.longestPalindromeExpandCenter(s2));
        System.out.println("Expected    : 2\n");

        System.out.println("=== Test Case 3: Fully Palindromic ===");
        String s3 = "racecar";
        System.out.println("Expand Center: " + solver.longestPalindromeExpandCenter(s3));
        System.out.println("Expected    : 7\n");

        System.out.println("=== Test Case 4: Edge Case (Single Char) ===");
        String s4 = "a";
        System.out.println("Expand Center: " + solver.longestPalindromeExpandCenter(s4));
        System.out.println("Expected    : 1\n");
    }
}
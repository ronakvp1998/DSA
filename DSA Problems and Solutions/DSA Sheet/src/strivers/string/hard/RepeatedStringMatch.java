package com.questions.strivers.string.hard;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 686. Repeated String Match
 * Difficulty: Medium
 * * Given two strings a and b, return the minimum number of times you should
 * repeat string a so that string b is a substring of it. If it is impossible
 * for b to be a substring of a after repeating it, return -1.
 * * Notice: string "abc" repeated 0 times is "", repeated 1 time is "abc" and
 * repeated 2 times is "abcabc".
 * * Example 1:
 * Input: a = "abcd", b = "cdabcdab"
 * Output: 3
 * Explanation: We return 3 because by repeating a three times "abcdabcdabcd",
 * b is a substring of it.
 * * Example 2:
 * Input: a = "a", b = "aa"
 * Output: 2
 * * Constraints:
 * 1 <= a.length, b.length <= 10^4
 * a and b consist of lowercase English letters.
 * ============================================================================
 */
public class RepeatedStringMatch {

    /**
     * ============================================================================
     * PHASE 1: BRUTE FORCE APPROACH (StringBuilder & Built-in Matching)
     * ============================================================================
     * Detailed Intuition:
     * For string `b` to be a substring of repeated `a`, the repeated string must
     * have a length of at least `b.length()`. We can determine the minimum
     * repetitions required to reach or just exceed `b.length()`. Let this be `q`.
     * * If `b` starts exactly at the beginning of the repeated string, `q` repetitions
     * might be enough. However, if `b` starts at an offset (e.g., at the 2nd or 3rd
     * character of `a`), we might need one more repetition to cover the "spillover"
     * at the end. Thus, the answer can ONLY be `q` or `q + 1`. If neither works,
     * it's impossible.
     * * We will use a StringBuilder to build the repeated string and the built-in
     * String.indexOf() method to check for the substring.
     * * Complexity Analysis:
     * - Time Complexity: O((N + M) * M), where N is the length of 'a' and M is
     * the length of 'b'. Generating the string takes O(N + M). Java's indexOf
     * in the worst case takes O(LengthOfSource * LengthOfPattern), which translates
     * to O((N + M) * M).
     * - Space Complexity: O(N + M) heap space to store the StringBuilder and
     * the final string representation. Auxiliary stack space is O(1).
     * ============================================================================
     */
    public static int repeatedStringMatchBruteForce(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        // Append 'a' until the length is at least the length of 'b'
        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        // Check if b is a substring with 'count' repetitions
        if (sb.toString().indexOf(b) != -1) {
            return count;
        }

        // Append one more time to account for a shifted start index
        sb.append(a);
        count++;

        if (sb.toString().indexOf(b) != -1) {
            return count;
        }

        // If it's still not a substring, it's impossible
        return -1;
    }

    /**
     * ============================================================================
     * PHASE 2: ALTERNATIVE APPROACH (KMP Algorithm for guaranteed O(N + M))
     * ============================================================================
     * Detailed Intuition:
     * The Brute Force approach suffers from the potentially slow built-in string
     * matching, which can degrade to O(N * M) in the worst case (e.g., when `a`
     * and `b` consist of highly repetitive characters like "aaaaab").
     * * To guarantee linear time, we use the Knuth-Morris-Pratt (KMP) pattern
     * matching algorithm. KMP preprocesses the pattern `b` to create an LPS
     * (Longest Prefix which is also Suffix) array. This allows the search
     * algorithm to skip redundant comparisons, bounding the search time to
     * strictly O(LengthOfSource + LengthOfPattern).
     * * We apply the same repetition logic as Phase 1 (checking `q` and `q + 1`
     * repetitions), but we replace indexOf with our custom linear-time KMP search.
     * * Complexity Analysis:
     * - Time Complexity: O(N + M). Generating the string takes O(N + M).
     * Building the LPS array takes O(M). The KMP search traverses the source
     * string of length at most N + M + N once, taking O(N + M) time.
     * - Space Complexity: O(N + M) heap space for the repeated string, and
     * O(M) heap space for the LPS array. Auxiliary stack space is O(1).
     * ============================================================================
     */
    public static int repeatedStringMatchKMP(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        // Compute LPS array for string 'b' (the pattern)
        int[] lps = computeLPS(b);

        // Check with 'count' repetitions
        if (kmpSearch(sb.toString(), b, lps)) {
            return count;
        }

        // Check with 'count + 1' repetitions
        sb.append(a);
        if (kmpSearch(sb.toString(), b, lps)) {
            return count + 1;
        }

        return -1;
    }

    // Helper method to compute LPS array for KMP
    private static int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0; // length of the previous longest prefix suffix
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // Helper method to perform KMP Search
    private static boolean kmpSearch(String text, String pattern, int[] lps) {
        int n = text.length();
        int m = pattern.length();
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == m) {
                return true; // Match found
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        // Test Cases Setup
        String[][] testCases = {
                {"abcd", "cdabcdab"},  // Example 1: Standard offset match (expected: 3)
                {"a", "aa"},           // Example 2: Simple repeat (expected: 2)
                {"abc", "wxyz"},       // Edge Case: Impossible match (expected: -1)
                {"abc", "c"},          // Edge Case: b is smaller than a, partial match (expected: 1)
                {"aa", "a"},           // Edge Case: a is larger, full match immediately (expected: 1)
                {"ab", "bababa"}       // Standard Case: Repetition with offset (expected: 4)
        };

        System.out.println("=====================================================");
        System.out.println("Running Tests for 686. Repeated String Match");
        System.out.println("=====================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            String a = testCases[i][0];
            String b = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": a = \"" + a + "\", b = \"" + b + "\"");

            int resultPhase1 = repeatedStringMatchBruteForce(a, b);
            int resultPhase2 = repeatedStringMatchKMP(a, b);

            System.out.println("  Phase 1 (Brute Force): " + resultPhase1);
            System.out.println("  Phase 2 (KMP)        : " + resultPhase2);

            // Verification
            if (resultPhase1 == resultPhase2) {
                System.out.println("  Status: PASSED\n");
            } else {
                System.out.println("  Status: FAILED (Results mismatch)\n");
            }
        }
    }
}
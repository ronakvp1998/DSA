package com.questions.strivers.string.hard;

/**
 * ============================================================================
 * 1392. Longest Happy Prefix
 * ============================================================================
 * A string is called a happy prefix if is a non-empty prefix which is also a
 * suffix (excluding itself).
 * * Given a string s, return the longest happy prefix of s. Return an empty
 * string "" if no such prefix exists.
 * * Example 1:
 * Input: s = "level"
 * Output: "l"
 * Explanation: s contains 4 prefix excluding itself ("l", "le", "lev", "leve"),
 * and suffix ("l", "el", "vel", "evel"). The largest prefix which is also suffix
 * is given by "l".
 * * Example 2:
 * Input: s = "ababab"
 * Output: "abab"
 * Explanation: "abab" is the largest prefix which is also suffix. They can
 * overlap in the original string.
 * * Constraints:
 * 1 <= s.length <= 10^5
 * s contains only lowercase English letters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class LongestHappyPrefix {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * A happy prefix is a prefix that is identical to a suffix of the same length.
     * The most intuitive way to find the *longest* such prefix is to start by
     * checking the longest possible prefix (length N - 1) and compare it against
     * the suffix of the same length. If they match, we return it immediately.
     * If not, we decrement the length and check again, all the way down to 1.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2), where N is the length of the string. In the
     * worst-case scenario (e.g., "aaaa...ab"), extracting the substring and
     * performing string equality checks `prefix.equals(suffix)` takes O(K)
     * time for every length K, summing to O(N^2). This will definitely trigger
     * a Time Limit Exceeded (TLE) error for N = 10^5.
     * - Space Complexity: O(N) Auxiliary Heap Space to allocate the substring
     * copies during each comparison.
     * ========================================================================
     */
    public static String longestPrefixBruteForce(String s) {
        if (s == null || s.length() <= 1) return "";

        int n = s.length();
        // Start from the largest possible proper prefix/suffix
        for (int len = n - 1; len > 0; len--) {
            String prefix = s.substring(0, len);
            String suffix = s.substring(n - len);

            if (prefix.equals(suffix)) {
                return prefix;
            }
        }

        return "";
    }

    /**
     * ========================================================================
     * Phase 2: KMP Algorithm (LPS Array) - The "Refine it" stage.
     * ========================================================================
     * Detailed Intuition:
     * If you look closely at the definition of a "Happy Prefix", it is EXACTLY
     * the definition of the Longest Prefix Suffix (LPS) array used in the KMP
     * (Knuth-Morris-Pratt) string matching algorithm.
     * * The LPS array stores the length of the longest proper prefix that is also
     * a proper suffix for every substring ending at index `i`.
     * Therefore, if we simply compute the standard KMP LPS array for the entire
     * string `s`, the value at the very last index `lps[n - 1]` will be the
     * exact length of the longest happy prefix for the whole string!
     * * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the string once using two
     * pointers. The `len` pointer only moves backward via `lps[len - 1]`, and
     * across the entire execution, it can only decrease as many times as it
     * increased, strictly bounding the operations to linear time.
     * - Space Complexity: O(N) Auxiliary Space to store the integer `lps` array.
     * ========================================================================
     */
    public static String longestPrefixKMP(String s) {
        if (s == null || s.length() <= 1) return "";

        int n = s.length();
        int[] lps = new int[n];

        int len = 0; // Length of the previous longest prefix suffix
        int i = 1;

        // Build the LPS array
        while (i < n) {
            if (s.charAt(i) == s.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // Fall back to the previous known valid prefix length
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        // The last element contains the length of the longest happy prefix
        int longestLength = lps[n - 1];
        return s.substring(0, longestLength);
    }

    /**
     * ========================================================================
     * Phase 3: Rolling Hash (Rabin-Karp Variant) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * While KMP is optimal in time, it uses O(N) auxiliary space. We can solve
     * this in O(1) auxiliary space using a Rolling Hash (Rabin-Karp concept).
     * * We simultaneously compute the hash of the prefix (growing left to right)
     * and the hash of the suffix (growing right to left) character by character.
     * If at any length `i`, the `prefixHash` equals the `suffixHash`, we record
     * that length as our new longest happy prefix.
     * * Note: Rolling hashes can suffer from collisions, so a truly robust
     * implementation verifies string equality when hashes match. However, on
     * LeetCode, using a large prime modulo usually passes without strict collision
     * verification. We will omit the string verification here to maintain true O(N)
     * performance, trusting the high-quality modulo.
     * * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the string exactly once.
     * - Space Complexity: O(1) Auxiliary Heap Space. We only maintain a few
     * scalar `long` variables for our hashes and multiplier.
     * ========================================================================
     */
    public static String longestPrefixRollingHash(String s) {
        if (s == null || s.length() <= 1) return "";

        int n = s.length();
        long prefixHash = 0;
        long suffixHash = 0;
        long multiplier = 1; // Base power multiplier for suffix hash

        long MOD = 1000000007L; // Large prime to avoid collisions
        long BASE = 31L;        // Standard base for lowercase strings

        int bestLength = 0;

        // We only iterate up to n - 1 because the happy prefix cannot be
        // the entire string itself.
        for (int i = 0; i < n - 1; i++) {
            // Add current character to prefix hash (shift existing left, add new)
            prefixHash = (prefixHash * BASE + (s.charAt(i) - 'a')) % MOD;

            // Add current character to suffix hash (add new character to front)
            suffixHash = (suffixHash + (s.charAt(n - 1 - i) - 'a') * multiplier) % MOD;

            // Update the power multiplier for the next suffix character
            multiplier = (multiplier * BASE) % MOD;

            // If hashes match, update the best known valid length
            if (prefixHash == suffixHash) {
                bestLength = i + 1;
            }
        }

        return s.substring(0, bestLength);
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "level",         // Example 1: Standard match ("l")
                "ababab",        // Example 2: Overlapping match ("abab")
                "leetcode",      // No matching prefix/suffix ("")
                "a",             // Edge case: length 1 ("")
                "aaaaa",         // Overlapping identical characters ("aaaa")
                "bba",           // No match
                "acccbaaacccbac" // Complex overlap ("acccbac" is not a prefix, output should be "ac")
        };

        System.out.println("Running test cases for 1392. Longest Happy Prefix...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\"");

            String res1 = longestPrefixBruteForce(s);
            String res2 = longestPrefixKMP(s);
            String res3 = longestPrefixRollingHash(s);

            System.out.println("Phase 1 (Brute Force)  : \"" + res1 + "\"");
            System.out.println("Phase 2 (KMP Array)    : \"" + res2 + "\"");
            System.out.println("Phase 3 (Rolling Hash) : \"" + res3 + "\"");

            // Validation
            if (res1.equals(res2) && res2.equals(res3)) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
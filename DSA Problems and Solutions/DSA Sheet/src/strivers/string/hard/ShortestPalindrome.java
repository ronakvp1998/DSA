package com.questions.strivers.string.hard;
/**
 * ============================================================================
 * 214. Shortest Palindrome
 * ============================================================================
 * You are given a string s. You can convert s to a palindrome by adding
 * characters in front of it.
 * * Return the shortest palindrome you can find by performing this transformation.
 * * Example 1:
 * Input: s = "aacecaaa"
 * Output: "aaacecaaa"
 * * Example 2:
 * Input: s = "abcd"
 * Output: "dcbabcd"
 * * Constraints:
 * 0 <= s.length <= 5 * 10^4
 * s consists of lowercase English letters only.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class ShortestPalindrome {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Prefix Matching) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The core requirement is to add characters to the *front* of the string
     * to make it a palindrome.
     * If we find the *longest palindromic prefix* of the string, the characters
     * that remain after this prefix are the ones causing the asymmetry.
     * Therefore, to form the shortest palindrome, we simply need to take the
     * remaining suffix, reverse it, and append it to the front of the string.
     * * In the brute force method, we reverse the original string `s` to get `rev`.
     * We then iteratively check if the prefix of `s` matches the suffix of `rev`.
     * The first match we find (starting from the longest possible prefix) gives
     * us our longest palindromic prefix.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2), where N is the length of the string. In the
     * worst case (e.g., "aaaaa..."), string comparison `startsWith` takes O(N)
     * time, and we might do this N times in the loop.
     * - Space Complexity: O(N) Auxiliary Heap Space to create the reversed
     * string and the temporary substrings during iteration.
     * ========================================================================
     */
    public static String shortestPalindromeBruteForce(String s) {
        if (s == null || s.length() <= 1) return s;

        int n = s.length();
        String rev = new StringBuilder(s).reverse().toString();

        // Slide through the reversed string to find the matching prefix in s
        for (int i = 0; i < n; i++) {
            // If the prefix of 's' matches the suffix of 'rev'
            // rev.substring(i) removes the first 'i' characters of the reversed string
            if (s.startsWith(rev.substring(i))) {
                // Take the unmatched part of 'rev', and prepend it to 's'
                return rev.substring(0, i) + s;
            }
        }

        return "";
    }

    /**
     * ========================================================================
     * Phase 2: KMP Algorithm (Longest Prefix Suffix) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Finding the longest palindromic prefix sounds remarkably similar to finding
     * the Longest Prefix Suffix (LPS) in the Knuth-Morris-Pratt (KMP) algorithm.
     * * THE TRICK: We create a new combined string: `temp = s + "#" + reverse(s)`.
     * Why the "#"? It acts as a strict boundary so that the prefix and suffix
     * calculations don't bleed into each other across the original string and
     * its reversed copy.
     * * By calculating the standard KMP LPS array for this `temp` string, the very
     * last value in the LPS array will tell us exactly the length of the longest
     * prefix of `s` that perfectly matches a suffix of `reverse(s)`.
     * This length is exactly the longest palindromic prefix of `s`!
     * * Example: s = "aacecaaa"
     * rev = "aaacecaa"
     * temp = "aacecaaa#aaacecaa"
     * The last value of LPS for temp will be 7 (for "aacecaa").
     * Unmatched suffix length = s.length() - 7 = 8 - 7 = 1 (the last 'a').
     * We reverse that unmatched part and prepend it.
     * * Complexity Analysis:
     * - Time Complexity: O(N). String reversal takes O(N). Building the `temp`
     * string takes O(N). Computing the LPS array takes linear time relative to
     * the length of `temp`, which is O(2N) -> O(N).
     * - Space Complexity: O(N) Auxiliary Heap Space. We allocate memory for the
     * reversed string, the combined `temp` string (size 2N + 1), and the integer
     * LPS array (size 2N + 1).
     * ========================================================================
     */
    public static String shortestPalindromeOptimal(String s) {
        if (s == null || s.length() <= 1) return s;

        String rev = new StringBuilder(s).reverse().toString();
        // The '#' prevents the prefix from overlapping with the reversed string
        String temp = s + "#" + rev;

        // Step 1: Build the LPS array for the combined string
        int[] lps = new int[temp.length()];
        int prevLPS = 0; // Length of the previous longest prefix suffix
        int i = 1;

        while (i < temp.length()) {
            if (temp.charAt(i) == temp.charAt(prevLPS)) {
                prevLPS++;
                lps[i] = prevLPS;
                i++;
            } else {
                if (prevLPS != 0) {
                    // Fallback to the previous valid prefix length
                    prevLPS = lps[prevLPS - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        // Step 2: The last value in the LPS array gives the length of the
        // longest palindromic prefix of the original string 's'.
        int longestPalindromicPrefixLen = lps[temp.length() - 1];

        // Step 3: Extract the non-palindromic suffix, reverse it (which is
        // already done in 'rev'), and prepend it to 's'.
        String suffixToPrepend = rev.substring(0, s.length() - longestPalindromicPrefixLen);

        return suffixToPrepend + s;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "aacecaaa",    // Example 1: Palindromic prefix "aacecaa"
                "abcd",        // Example 2: No palindromic prefix > 1
                "a",           // Single character
                "aa",          // All identical
                "",            // Empty string
                "abacabac",    // Overlapping palindromic prefixes
                "racecar"      // Already a full palindrome
        };

        System.out.println("Running test cases for 214. Shortest Palindrome...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\"");

            String res1 = shortestPalindromeBruteForce(s);
            String res2 = shortestPalindromeOptimal(s);

            System.out.println("Phase 1 (Brute Force) : \"" + res1 + "\"");
            System.out.println("Phase 2 (KMP Optimal) : \"" + res2 + "\"");

            // Validation step
            if (res1.equals(res2)) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
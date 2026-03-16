package com.questions.strivers.string.hard;


/**
 * ============================================================================
 * 28. Find the Index of the First Occurrence in a String (using Rabin-Karp)
 * ============================================================================
 * Given two strings needle and haystack, return the index of the first
 * occurrence of needle in haystack, or -1 if needle is not part of haystack.
 * * Note: While the standard LeetCode problem asks for the first occurrence,
 * the Rabin-Karp algorithm is a foundational string matching algorithm often
 * used to find multiple occurrences or solve plagiarism detection. We will
 * implement it here to return the index of the first occurrence to match
 * the standard LeetCode 28 signature.
 * * Example 1:
 * Input: haystack = "sadbutsad", needle = "sad"
 * Output: 0
 * Explanation: "sad" occurs at index 0 and 6.
 * The first occurrence is at index 0, so we return 0.
 * * Example 2:
 * Input: haystack = "leetcode", needle = "leeto"
 * Output: -1
 * Explanation: "leeto" did not occur in "leetcode", so we return -1.
 * * Constraints:
 * 1 <= haystack.length, needle.length <= 10^4
 * haystack and needle consist of only lowercase English characters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class RabinKarpAlgorithm {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Naive String Matching) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most intuitive way to find a substring is to slide a "window" of the
     * needle's length across the haystack. At every single index `i` from 0 up
     * to `N - M`, we check if the characters in the haystack perfectly match
     * the characters in the needle. If we find a mismatch, we break the inner
     * loop and shift the window to the right by one position.
     * * Complexity Analysis:
     * - Time Complexity: O(N * M), where N is the length of the haystack and
     * M is the length of the needle. In the worst-case scenario
     * (e.g., haystack = "aaaaaaaaab", needle = "aaab"), we compare almost all
     * characters of the needle for every starting position in the haystack.
     * - Space Complexity: O(1) Auxiliary Heap Space. We only use a few primitive
     * integer pointers, requiring strictly constant memory.
     * ========================================================================
     */
    public static int strStrBruteForce(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        // Edge case: needle is longer than haystack
        if (m > n) {
            return -1;
        }

        // Slide the window over the haystack
        for (int i = 0; i <= n - m; i++) {
            int j;
            // Check character by character
            for (j = 0; j < m; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break; // Mismatch found, move to next window
                }
            }

            // If the inner loop finished completely, we found a match
            if (j == m) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ========================================================================
     * Phase 2: Rabin-Karp Algorithm (Rolling Hash) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * To optimize the O(N*M) brute force, we want to avoid comparing every single
     * character of the needle against every window.
     * * Rabin-Karp introduces the concept of a "Rolling Hash". We compute a hash
     * value for the needle, and a hash value for the first window of the haystack.
     * If the hashes do NOT match, the strings definitely do not match (O(1) check).
     * If the hashes DO match, we perform a character-by-character check to ensure
     * it's not a hash collision.
     * * The magic is in the "Rolling" part: when we slide the window from index `i`
     * to `i+1`, we don't recalculate the hash from scratch in O(M) time. We take
     * the old hash, mathematically remove the leading character, shift the remaining
     * characters, and add the new trailing character. This updates the hash in
     * strictly O(1) time!
     * * Complexity Analysis:
     * - Time Complexity: O(N + M) Average Case. Calculating the initial hashes
     * takes O(M). Sliding the window and updating the hash takes O(1) per step
     * for N steps. However, in the absolute worst-case (severe hash collisions,
     * e.g., all characters are the same), it degrades to O(N * M) because we
     * must manually verify every collision.
     * - Space Complexity: O(1) Auxiliary Space. The hash variables and prime
     * modulo constants only consume constant primitive space.
     * ========================================================================
     */
    public static int strStrRabinKarp(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        if (m > n) return -1;
        if (m == 0) return 0;

        // Base/Radix for the polynomial rolling hash (256 for extended ASCII)
        int d = 256;
        // A large prime number to prevent integer overflow and minimize collisions
        int q = 101;

        int needleHash = 0;   // Hash value for the needle
        int windowHash = 0;   // Hash value for the current window in haystack
        int h = 1;            // The multiplier for the most significant digit (d^(m-1) % q)

        // Step 1: Precompute the multiplier h = (d^(m-1)) % q
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }

        // Step 2: Calculate the initial hash values for the needle and the first window
        for (int i = 0; i < m; i++) {
            needleHash = (d * needleHash + needle.charAt(i)) % q;
            windowHash = (d * windowHash + haystack.charAt(i)) % q;
        }

        // Step 3: Slide the window across the haystack
        for (int i = 0; i <= n - m; i++) {

            // If the hashes match, we MUST verify character by character
            // to rule out a spurious hit (hash collision)
            if (needleHash == windowHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return i; // Found the exact match
                }
            }

            // Step 4: Calculate the hash for the next window
            // Remove the leading digit, add the trailing digit
            if (i < n - m) {
                windowHash = (d * (windowHash - haystack.charAt(i) * h) + haystack.charAt(i + m)) % q;

                // In Java, modulo on negative numbers returns negative numbers.
                // We must convert it back to positive to ensure hash consistency.
                if (windowHash < 0) {
                    windowHash = windowHash + q;
                }
            }
        }

        return -1;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[][] testCases = {
                {"sadbutsad", "sad"},         // Example 1: Match at start
                {"leetcode", "leeto"},        // Example 2: No match
                {"hello", "ll"},              // Standard middle match
                {"aaaaa", "bba"},             // No match, repeating characters
                {"abc", "c"},                 // Match at end
                {"a", "a"},                   // Single character match
                {"mississippi", "issip"},     // Multiple partial overlaps
                {"aabaaabaaac", "aabaaac"}    // Long complex string
        };

        System.out.println("Running test cases for Rabin-Karp (strStr)...\n");

        for (int i = 0; i < testCases.length; i++) {
            String haystack = testCases[i][0];
            String needle = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": haystack = \"" + haystack + "\", needle = \"" + needle + "\"");

            int res1 = strStrBruteForce(haystack, needle);
            int res2 = strStrRabinKarp(haystack, needle);

            System.out.println("Phase 1 (Brute Force) : " + res1);
            System.out.println("Phase 2 (Rabin-Karp)  : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
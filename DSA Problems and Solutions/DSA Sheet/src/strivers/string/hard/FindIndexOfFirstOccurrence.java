package strivers.string.hard;

/**
 * ============================================================================
 * 28. Find the Index of the First Occurrence in a String
 * ============================================================================
 * Given two strings needle and haystack, return the index of the first
 * occurrence of needle in haystack, or -1 if needle is not part of haystack.
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
public class FindIndexOfFirstOccurrence {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most intuitive way to find a substring is to use a sliding window.
     * We iterate through the `haystack` using an outer loop. For every starting
     * position `i`, we use an inner loop to check if the subsequent characters
     * exactly match the `needle`.
     * If we find a mismatch, we break the inner loop and advance the outer
     * loop by exactly one position, completely restarting the needle comparison.
     * * Complexity Analysis:
     * - Time Complexity: O(N * M), where N is the length of the haystack and
     * M is the length of the needle. In the worst-case scenario
     * (e.g., haystack = "aaaaaaaaab", needle = "aaab"), we compare almost all
     * characters of the needle for every starting position in the haystack.
     * - Space Complexity: O(1) Auxiliary Heap Space. We only use a few primitive
     * integer pointers.
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

            // If the inner loop finished completely without breaking, we found a match
            if (j == m) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ========================================================================
     * Phase 2: Built-in Approach - The "Pragmatic" stage.
     * ========================================================================
     * Detailed Intuition:
     * In a production Java/Spring Boot environment, you would never write your
     * own string matching loop. You would rely on the highly optimized standard
     * library. In an interview, it is critical to state that you know `indexOf`
     * exists, before proceeding to implement the algorithmic mechanics underneath it.
     * * Complexity Analysis:
     * - Time Complexity: O(N * M) worst case, though highly optimized via
     * intrinsics in modern JVMs.
     * - Space Complexity: O(1).
     * ========================================================================
     */
    public static int strStrBuiltIn(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    /**
     * ========================================================================
     * Phase 3: KMP (Knuth-Morris-Pratt) Algorithm - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The fundamental flaw of the Brute Force approach is that when a mismatch
     * occurs, we throw away all the information we just learned about the characters
     * we successfully matched, backing up the haystack pointer and starting over.
     * * KMP solves this by NEVER moving the haystack pointer backwards.
     * It pre-processes the `needle` to create an LPS (Longest Prefix Suffix) array.
     * The LPS array tells us: "If a mismatch happens at needle index J, how many
     * of the preceding characters are a valid prefix, so I don't have to check
     * them again?"
     * * Example LPS for "AAACAAAA":
     * Char: A A A C A A A A
     * LPS:  0 1 2 0 1 2 3 3
     * * When traversing, if `haystack[i] != needle[j]`, we don't reset `i`.
     * Instead, we just fallback `j` to `LPS[j-1]`.
     * * Complexity Analysis:
     * - Time Complexity: O(N + M). Building the LPS array takes O(M). Traversing
     * the haystack takes O(N) because the haystack pointer `i` never decrements.
     * - Space Complexity: O(M) Auxiliary Space to store the LPS array.
     * ========================================================================
     */
    public static int strStrKMP(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        if (m == 0) return 0;
        if (m > n) return -1;

        // Step 1: Build the LPS (Longest Prefix Suffix) array
        int[] lps = new int[m];
        int prevLPS = 0; // Length of the previous longest prefix suffix
        int i = 1;

        while (i < m) {
            if (needle.charAt(i) == needle.charAt(prevLPS)) {
                prevLPS++;
                lps[i] = prevLPS;
                i++;
            } else {
                if (prevLPS != 0) {
                    // Fall back to the previous known prefix length
                    prevLPS = lps[prevLPS - 1];
                } else {
                    // No prefix matches
                    lps[i] = 0;
                    i++;
                }
            }
        }

        // Step 2: Use LPS array to traverse haystack
        int haystackPointer = 0;
        int needlePointer = 0;

        while (haystackPointer < n) {
            if (haystack.charAt(haystackPointer) == needle.charAt(needlePointer)) {
                haystackPointer++;
                needlePointer++;
            }

            if (needlePointer == m) {
                // We reached the end of the needle, match found!
                return haystackPointer - needlePointer;
            } else if (haystackPointer < n && haystack.charAt(haystackPointer) != needle.charAt(needlePointer)) {
                // Mismatch after at least one matching character
                if (needlePointer != 0) {
                    // Fall back the needle pointer using LPS.
                    // CRUCIAL: haystackPointer does NOT decrement.
                    needlePointer = lps[needlePointer - 1];
                } else {
                    haystackPointer++;
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
                {"aabaaabaaac", "aabaaac"}    // Long complex string requiring LPS fallbacks
        };

        System.out.println("Running test cases for 28. Find the Index of the First Occurrence...\n");

        for (int i = 0; i < testCases.length; i++) {
            String haystack = testCases[i][0];
            String needle = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": haystack = \"" + haystack + "\", needle = \"" + needle + "\"");

            int res1 = strStrBruteForce(haystack, needle);
            int res2 = strStrBuiltIn(haystack, needle);
            int res3 = strStrKMP(haystack, needle);

            System.out.println("Phase 1 (Brute Force) : " + res1);
            System.out.println("Phase 2 (Built-In)    : " + res2);
            System.out.println("Phase 3 (KMP Optimal) : " + res3);

            // Validation step
            if (res1 == res2 && res2 == res3) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
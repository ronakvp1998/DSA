package strivers.string.hard;

/**
 * ============================================================================
 * Z Function / Z Algorithm (String Pattern Matching)
 * ============================================================================
 * The Z-function for a string is an array of length n where the i-th element
 * is equal to the greatest number of characters starting from the position i
 * that coincide with the first characters of the string.
 * In simpler terms, Z[i] is the length of the longest common prefix between s
 * and the suffix of s starting at i.
 * * Application (Pattern Matching):
 * To find all occurrences of a "pattern" in a "text", we can concatenate them
 * as: S = pattern + "$" + text (where "$" is a character not present in either).
 * If Z[i] == pattern.length() for any index in the concatenated string, we
 * found an occurrence of the pattern in the text!
 * * Example 1:
 * Input: s = "aaaaa"
 * Output: [0, 4, 3, 2, 1]
 * Explanation:
 * Z[1] for "aaaa" -> matches prefix "aaaa" (length 4)
 * Z[2] for "aaa" -> matches prefix "aaa" (length 3)
 * * Example 2:
 * Input: s = "aaabaab"
 * Output: [0, 2, 1, 0, 2, 1, 0]
 * Explanation:
 * Z[1] for "aabaab" -> "aa" matches prefix "aa" (length 2)
 * Z[4] for "aab" -> "aa" matches prefix "aa" (length 2)
 * * Constraints:
 * 1 <= string length <= 10^5
 * String consists of English letters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZAlgorithm {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most intuitive way to calculate the Z-array is to manually check
     * the prefix match for every single suffix.
     * For every index `i` from 1 to N-1, we compare `s.charAt(j)` with
     * `s.charAt(i + j)`. As long as they are equal, we increment our match
     * counter. Once a mismatch occurs, we record the count in Z[i] and move
     * to the next `i`. (By definition, Z[0] is usually defined as 0).
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). In the worst-case scenario (e.g., "aaaaaaa"),
     * the inner loop runs to the end of the string for every outer iteration,
     * resulting in quadratic time.
     * - Space Complexity: O(N) Auxiliary Heap Space to store the resulting
     * Z-array of size N.
     * ========================================================================
     */
    public static int[] calculateZArrayBruteForce(String s) {
        int n = s.length();
        int[] z = new int[n];

        // Z[0] is generally defined as 0
        for (int i = 1; i < n; i++) {
            int matchLen = 0;
            // Compare suffix starting at i with prefix starting at 0
            while (i + matchLen < n && s.charAt(matchLen) == s.charAt(i + matchLen)) {
                matchLen++;
            }
            z[i] = matchLen;
        }

        return z;
    }

    /**
     * ========================================================================
     * Phase 2: Optimal Z-Algorithm (Z-Box / Sliding Window) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * To achieve linear time, we must avoid redundant comparisons. We maintain a
     * "Z-box" or a window [L, R] representing the rightmost substring we've
     * found so far that perfectly matches a prefix of `s`.
     * * When evaluating a new index `i`:
     * 1. If `i > R`: The current index is outside our known matched window.
     * We have no historical data to help us, so we must calculate Z[i]
     * manually (like Brute Force) and then update our window [L, R].
     * 2. If `i <= R`: The current index is INSIDE our known matched window.
     * This means the substring starting at `i` is identical to the substring
     * starting at index `k = i - L`.
     * - If Z[k] is strictly less than the remaining length of the window
     * (R - i + 1), then Z[i] is exactly Z[k]. No character comparisons needed!
     * - If Z[k] stretches beyond our window bounds, we can only safely say
     * it matches up to R. We then manually compare characters beyond R
     * and update our window [L, R] accordingly.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Although there is a nested `while` loop, the
     * right boundary `R` strictly increases or stays the same. `R` can increase
     * at most N times, making the character comparisons amortized O(N).
     * - Space Complexity: O(N) Auxiliary Heap Space to store the Z-array.
     * ========================================================================
     */
    public static int[] calculateZArrayOptimal(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i > r) {
                // i is outside the window, calculate explicitly
                l = r = i;
                while (r < n && s.charAt(r - l) == s.charAt(r)) {
                    r++;
                }
                z[i] = r - l;
                r--; // r is now the index of the last matching character
            } else {
                // i is inside the window
                int k = i - l;

                // If Z[k] does not stretch beyond the current window's right edge
                if (z[k] < r - i + 1) {
                    z[i] = z[k];
                } else {
                    // It stretches to the edge or beyond, we must explicitly match
                    // from the current right edge onwards
                    l = i;
                    while (r < n && s.charAt(r - l) == s.charAt(r)) {
                        r++;
                    }
                    z[i] = r - l;
                    r--;
                }
            }
        }

        return z;
    }

    /**
     * ========================================================================
     * Utility: Pattern Matching using Z-Algorithm
     * ========================================================================
     * Detailed Intuition:
     * This demonstrates the real-world application of the Z-function.
     * We create a concatenated string: pattern + "$" + text.
     * We run the optimal Z-algorithm on this concatenated string.
     * Any index `i` where Z[i] equals the pattern's length signifies that the
     * text contains the pattern starting at that relative index.
     * ========================================================================
     */
    public static List<Integer> searchPattern(String text, String pattern) {
        String concat = pattern + "$" + text;
        int[] z = calculateZArrayOptimal(concat);
        List<Integer> occurrences = new ArrayList<>();

        int pLen = pattern.length();
        // We only check the Z-array after the "pattern$" portion
        for (int i = pLen + 1; i < concat.length(); i++) {
            if (z[i] == pLen) {
                // Map the index back to the original text
                occurrences.add(i - pLen - 1);
            }
        }

        return occurrences;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running Z-Array calculations...\n");
        String[] strings = {
                "aaaaa",       // Same character repeated
                "aaabaab",     // Example from header
                "abacaba",     // Palindromic structure
                "abcdef"       // All unique characters
        };

        for (String s : strings) {
            int[] bruteForce = calculateZArrayBruteForce(s);
            int[] optimal = calculateZArrayOptimal(s);

            System.out.println("String: \"" + s + "\"");
            System.out.println("Phase 1 (Brute)  : " + Arrays.toString(bruteForce));
            System.out.println("Phase 2 (Optimal): " + Arrays.toString(optimal));
            System.out.println("Match: " + Arrays.equals(bruteForce, optimal) + "\n");
        }

        System.out.println("========================================");
        System.out.println("Testing Pattern Search Application");
        System.out.println("========================================\n");

        String[][] searchCases = {
                {"sadbutsad", "sad"},         // Standard match
                {"leetcode", "leeto"},        // No match
                {"aaaaa", "aa"},              // Overlapping matches
                {"hello", "ll"}               // Match in middle
        };

        for (String[] testCase : searchCases) {
            String text = testCase[0];
            String pattern = testCase[1];
            List<Integer> result = searchPattern(text, pattern);

            System.out.println("Text: \"" + text + "\", Pattern: \"" + pattern + "\"");
            System.out.println("Occurrences found at indices: " + result + "\n");
        }
    }
}
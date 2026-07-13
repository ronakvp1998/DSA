package strivers.arrays.easy;

/**
 * ============================================================================
 * 242. Valid Anagram
 * ============================================================================
 *
 * Formal Problem Statement:
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different
 * word or phrase, typically using all the original letters exactly once.
 *
 * Constraints:
 * - 1 <= s.length, t.length <= 5 * 10^4
 * - s and t consist of lowercase English letters.
 *
 * Examples:
 * Example 1:
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 *
 * Example 2:
 * Input: s = "rat", t = "car"
 * Output: false
 *
 * Follow up:
 * What if the inputs contain Unicode characters? How would you adapt your
 * solution to such a case?
 *
 * ============================================================================
 * Progressive Implementation Roadmap (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Frequency Array counting (Best for fixed alphabet).
 * Phase 2: Brute Force Approach - Sorting strings and comparing.
 * Phase 3: Alternative Approach - HashMap/Stream API (Best for Unicode Follow-up).
 */

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidAnagram {

    /**
     * ========================================================================
     * Phase 1: Optimal Approach (Frequency Array)
     * ========================================================================
     * Detailed Intuition:
     * Since the problem specifies that the strings only contain lowercase English
     * letters, the character set is restricted to 26 characters. We can use a
     * fixed-size integer array of length 26 to count the frequencies of each
     * character in string `s`. Then, we iterate through string `t`, decrementing
     * the counts. If the strings are anagrams, all counts will drop exactly to zero.
     * We can optimize further by immediately returning false if the lengths differ.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of string s (and t). We
     *   traverse the strings a constant number of times.
     * - Space Complexity: O(1) auxiliary space. The integer array is always of
     *   fixed size 26, irrespective of the input string length N.
     */
    public boolean isAnagramOptimal(String s, String t) {
        // If lengths differ, they cannot be anagrams
        if (s.length() != t.length()) {
            return false;
        }

        int[] charCounts = new int[26];

        // Increment for s, decrement for t
        for (int i = 0; i < s.length(); i++) {
            charCounts[s.charAt(i) - 'a']++;
            charCounts[t.charAt(i) - 'a']--;
        }

        // Check if any frequency is non-zero
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * ========================================================================
     * Phase 2: Brute Force Approach (Sorting)
     * ========================================================================
     * Detailed Intuition:
     * By definition, two strings are anagrams if they are made of the exact same
     * characters in the exact same frequencies. If we sort both strings, their
     * character sequences must become identical.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N log N) dominated by the sorting algorithm
     *   (Dual-Pivot Quicksort for primitives in Java).
     * - Space Complexity: O(N) auxiliary space. In Java, Strings are immutable,
     *   so `toCharArray()` creates a new array of size N for each string.
     */
    public boolean isAnagramBruteForce(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        Arrays.sort(sChars);
        Arrays.sort(tChars);

        return Arrays.equals(sChars, tChars);
    }

    /**
     * ========================================================================
     * Phase 3: Alternative Approach (HashMap / Unicode Follow-Up)
     * ========================================================================
     * Detailed Intuition:
     * For the follow-up question regarding Unicode characters, a fixed array of size
     * 26 will cause an IndexOutOfBoundsException. Unicode has thousands of characters.
     * To handle this, we transition to a HashMap. To demonstrate modern Java
     * proficiency and fulfill the Stream API requirement, we use `codePoints()`
     * which safely handles Unicode surrogate pairs, boxing them and collecting
     * into a frequency map.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Collecting elements into a HashMap using Streams
     *   takes linear time. Comparing two HashMaps also takes linear time in the
     *   worst case.
     * - Space Complexity: O(U) auxiliary space, where U is the number of UNIQUE
     *   Unicode characters in the string. In the worst case, U = N, making it O(N)
     *   heap space for the HashMaps.
     */
    public boolean isAnagramUnicode(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        // Using Java 8 Streams to build a frequency map of Unicode code points
        Map<Integer, Long> sFrequency = s.codePoints()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Integer, Long> tFrequency = t.codePoints()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Maps correctly implement .equals() to check key-value pair equivalence
        return sFrequency.equals(tFrequency);
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        ValidAnagram solution = new ValidAnagram();

        // Define Test Cases
        String[][] testCases = {
                {"anagram", "nagaram"},       // Standard True case
                {"rat", "car"},               // Standard False case
                {"a", "a"},                   // Single character True
                {"a", "b"},                   // Single character False
                {"ab", "a"},                  // Length mismatch
                {"", ""},                     // Empty strings (edge case)
                {"hello", "olleh"},           // Standard reversal
                {"💡💡😂", "😂💡💡"}              // Unicode characters True (Emojis)
        };

        System.out.println("Running test suite for Valid Anagram...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String t = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\", t = \"" + t + "\"");

            // Note: isAnagramOptimal and isAnagramBruteForce are only guaranteed
            // to work on lowercase english letters as per constraints.
            // We'll skip the optimal array approach for the emoji test case.
            if (i < testCases.length - 1) {
                System.out.println("  Optimal    : " + solution.isAnagramOptimal(s, t));
                System.out.println("  Brute Force: " + solution.isAnagramBruteForce(s, t));
            } else {
                System.out.println("  Optimal    : SKIPPED (Unicode constraint)");
                System.out.println("  Brute Force: SKIPPED (char[] breaks on some surrogates)");
            }

            // Unicode approach works for all
            System.out.println("  Unicode/Map: " + solution.isAnagramUnicode(s, t));
            System.out.println("-".repeat(50));
        }
    }
}
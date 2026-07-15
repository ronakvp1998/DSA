package strivers.slidingwind2pointer;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 567. Permutation in String (Medium)
 * * Formal Problem Statement:
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1,
 * or false otherwise. In other words, return true if one of s1's permutations
 * is the substring of s2.
 * * Constraints:
 * - 1 <= s1.length, s2.length <= 10^4
 * - s1 and s2 consist of lowercase English letters.
 * * Example 1:
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * * Example 2:
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 * ============================================================================
 * * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * * Phase 1: Optimal Approach - Sliding Window with Constant Time Updates (The best approach).
 * Phase 2: Brute Force Approach - Substring sorting and comparison (The "Think it" stage).
 * Phase 3: Alternative Approach - Standard Sliding Window with Array Equality.
 * ============================================================================
 */
public class PermutationInString {


    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Standard Sliding Window + Array Equality)
     * ========================================================================
     * Detailed Intuition:
     * A middle ground between Brute Force and Optimal. We maintain the sliding
     * window frequency map for s2, but instead of tracking a `matches` variable,
     * we utilize Java's `Arrays.equals()` to compare the two 26-element arrays
     * at every step. This is much easier to write during an interview and
     * practically very fast, though theoretically slightly slower than Phase 1.
     * * Complexity Analysis:
     * - Time Complexity: O(L1 + 26 * (L2 - L1)). Array comparison takes O(26)
     * which simplifies to O(L1 + L2).
     * - Space Complexity: O(1) Auxiliary Heap Space (two size-26 integer arrays).
     * O(1) Auxiliary Stack Space.
     * ========================================================================
     */
    public boolean checkInclusionAlternative(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s2.length() - s1.length(); i++) {
            if (Arrays.equals(s1Count, s2Count)) {
                return true;
            }
            // Slide window: remove outgoing char, add incoming char
            // 1. Remove the outgoing character from the left of the window
            s2Count[s2.charAt(i) - 'a']--;
            // 2. Add the incoming character to the right of the window
            s2Count[s2.charAt(i + s1.length()) - 'a']++;
        }

        // Check the final window
        return Arrays.equals(s1Count, s2Count);
    }

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Optimized Sliding Window)
     * ========================================================================
     * Detailed Intuition:
     * Instead of comparing two arrays of size 26 on every window slide, we can
     * maintain a `matches` variable. This variable tracks how many characters
     * (out of 26) currently have the exact same frequency in our s2 window as
     * they do in s1. When we slide the window, we only update the frequencies
     * of the character entering the window and the character leaving the window.
     * If `matches == 26`, we have found a valid permutation.
     * * Complexity Analysis:
     * - Time Complexity: O(L1 + L2) where L1 is the length of s1 and L2 is the
     * length of s2. We process each character at most twice (once entering,
     * once leaving).
     * - Space Complexity: O(1) Auxiliary Heap Space (two integer arrays of
     * constant size 26). O(1) Auxiliary Stack Space (no recursion).
     * ========================================================================
     */
    public boolean checkInclusionOptimal(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        // Populate initial frequencies for the first window
        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }

        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Count[i] == s2Count[i]) {
                matches++;
            }
        }

        // Slide the window
        int left = 0,right = s1.length(),n=s2.length();
        while (right < n) {
            if (matches == 26) return true;

            // Process character entering the window (right pointer)
            int index = s2.charAt(right) - 'a';
            s2Count[index]++;
            if (s2Count[index] == s1Count[index]) {
                matches++;
            } else if (s2Count[index] == s1Count[index] + 1) {
                matches--; // We just broke a previously matching frequency
            }

            // Process character leaving the window (left pointer)
            index = s2.charAt(left) - 'a';
            s2Count[index]--;
            if (s2Count[index] == s1Count[index]) {
                matches++;
            } else if (s2Count[index] == s1Count[index] - 1) {
                matches--; // We just broke a previously matching frequency
            }
            left++;
            right++;
        }

        return matches == 26;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * Detailed Intuition:
     * A permutation is simply a rearranged version of a string. If we sort
     * a string and its permutation, they will become identical. The brute force
     * strategy evaluates every single substring of length L1 within s2, sorts
     * it, and compares it to the sorted version of s1.
     * * Complexity Analysis:
     * - Time Complexity: O(L2 * L1 log L1). There are (L2 - L1 + 1) substrings.
     * Extracting and sorting each takes O(L1 log L1). This will Time Out
     * for large constraints.
     * - Space Complexity: O(L1) Auxiliary Heap Space to hold the substrings
     * and character arrays for sorting. O(1) Auxiliary Stack Space.
     * ========================================================================
     */
    public boolean checkInclusionBruteForce(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        char[] s1Chars = s1.toCharArray();
        Arrays.sort(s1Chars);
        String sortedS1 = new String(s1Chars);

        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            String sub = s2.substring(i, i + s1.length());
            char[] subChars = sub.toCharArray();
            Arrays.sort(subChars);

            if (sortedS1.equals(new String(subChars))) {
                return true;
            }
        }
        return false;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Leveraging Java 8 Streams to neatly iterate through our test fixtures.
     */
    public static void main(String[] args) {
        PermutationInString solution = new PermutationInString();

        // Object array holding: {s1, s2, Expected Result, Test Name}
        Object[][] testCases = {
                {"ab", "eidbaooo", true, "Example 1 (Standard match)"},
                {"ab", "eidboaoo", false, "Example 2 (No match)"},
                {"adc", "dcda", true, "Anagrams of different ordering"},
                {"a", "ab", true, "Single character s1"},
                {"hello", "ooolleoooleh", false, "Longer s1 variations"},
                {"abc", "ab", false, "Edge Case: s1 longer than s2"},
                {"xyz", "xyz", true, "Edge Case: Exact identical strings"}
        };

        System.out.println("--- Running Tests ---");

        Stream.of(testCases).forEach(test -> {
            String s1 = (String) test[0];
            String s2 = (String) test[1];
            boolean expected = (boolean) test[2];
            String name = (String) test[3];

            boolean resOptimal = solution.checkInclusionOptimal(s1, s2);
            boolean resBrute = solution.checkInclusionBruteForce(s1, s2);
            boolean resAlt = solution.checkInclusionAlternative(s1, s2);

            boolean passed = (resOptimal == expected) &&
                    (resBrute == expected) &&
                    (resAlt == expected);

            System.out.printf("[%s] %s%n", passed ? "PASS" : "FAIL", name);
            if (!passed) {
                System.out.printf("  -> Input: s1=\"%s\", s2=\"%s\"%n", s1, s2);
                System.out.printf("  -> Expected: %b | Optimal: %b | Brute: %b | Alt: %b%n",
                        expected, resOptimal, resBrute, resAlt);
            }
        });
    }
}
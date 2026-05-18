package strivers.slidingwind2pointer;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION: Minimum Window Substring
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * PROBLEM: 76. Minimum Window Substring (Hard)
 *
 * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given two strings `s` and `t` of lengths `m` and `n` respectively, return
 * the minimum window substring of `s` such that every character in `t`
 * (including duplicates) is included in the window. If there is no such
 * substring, return the empty string "".
 *
 * The testcases will be generated such that the answer is unique.
 *
 * Constraints:
 * - m == s.length
 * - n == t.length
 * - 1 <= m, n <= 10^5
 * - s and t consist of uppercase and lowercase English letters.
 *
 * Follow up: Could you find an algorithm that runs in O(m + n) time?
 *
 * Example 1:
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 *
 * Example 2:
 * Input: s = "a", t = "a"
 * Output: "a"
 * Explanation: The entire string s is the minimum window.
 *
 * Example 3:
 * Input: s = "a", t = "aa"
 * Output: ""
 * Explanation: Both 'a's from t must be included in the window.
 * Since the largest window of s only has one 'a', return empty string.
 *
 * --- 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP) ---
 * Phase 1: Optimal Approach - Sliding Window with ASCII Array Frequency Maps
 * Phase 2: Brute Force Approach - Nested Loops generating all substrings
 * Phase 3: Alternative Approach - Filtered Sliding Window (Optimized for sparse targets)
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumWindowSubstring {

    /**
     * ============================================================================
     * Phase 1: Optimal Approach - The "Best" Stage
     * ============================================================================
     * Detailed Intuition:
     * We use a Sliding Window approach with two pointers (`left` and `right`).
     * We expand the window by moving `right` until we have all the required characters
     * from `t` in our window. Once our window is "valid", we try to shrink it by
     * moving `left` forward to find the minimum possible length, while keeping the
     * window valid.
     *
     * To track validity efficiently in O(1) time at each step, we use primitive integer
     * arrays of size 128 (to cover all ASCII characters). We track how many unique
     * characters have met their target frequency. Once `formed == required`, the
     * window is valid.
     *
     * Complexity Analysis:
     * - Time Complexity: O(m + n) where m is the length of s and n is the length of t.
     *   The `right` pointer moves from 0 to m. The `left` pointer moves from 0 to m.
     *   Each character is visited at most twice.
     * - Space Complexity: O(1) auxiliary heap space. The arrays are always size 128,
     *   which is a constant regardless of string size.
     * ============================================================================
     */
    public String minWindowOptimal(String s, String t) {
        if (s == null || t == null || s.length() < t.length() || s.length() == 0) {
            return "";
        }

        int[] targetFreq = new int[128];
        int required = 0; // Number of unique characters in t

        for (char c : t.toCharArray()) {
            if (targetFreq[c] == 0) required++;
            targetFreq[c]++;
        }

        int[] windowFreq = new int[128];
        int left = 0, right = 0;
        int formed = 0; // Number of unique characters that match the target frequency

        int minLen = Integer.MAX_VALUE;
        int minStart = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            windowFreq[c]++;

            // If the current character's frequency matches its target frequency,
            // increment the formed counter
            if (targetFreq[c] > 0 && windowFreq[c] == targetFreq[c]) {
                formed++;
            }

            // Shrink the window from the left as long as the window is valid
            while (left <= right && formed == required) {
                // Update the minimum window
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                // Remove the character at the left pointer
                char leftChar = s.charAt(left);
                windowFreq[leftChar]--;

                // If removing this character breaks the window's validity, decrement formed
                if (targetFreq[leftChar] > 0 && windowFreq[leftChar] < targetFreq[leftChar]) {
                    formed--;
                }
                left++;
            }
            right++;
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach - The "Think It" Stage
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to generate every possible
     * substring of `s` that is at least as long as `t`. For each substring, we
     * check if it contains all the characters of `t` with the required frequencies.
     * We keep track of the shortest valid substring found.
     *
     * Complexity Analysis:
     * - Time Complexity: O(m^2) to generate substrings * O(m) to check validity = O(m^3).
     *   This will definitely result in a Time Limit Exceeded (TLE) on LeetCode.
     * - Space Complexity: O(1) auxiliary space (using a constant size 128 array for checking).
     * ============================================================================
     */
    public String minWindowBruteForce(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";

        int[] targetFreq = new int[128];
        for (char c : t.toCharArray()) {
            targetFreq[c]++;
        }

        int minLen = Integer.MAX_VALUE;
        String minWindow = "";

        // Generate all possible substrings of length >= t.length()
        for (int i = 0; i <= s.length() - t.length(); i++) {
            for (int j = i + t.length(); j <= s.length(); j++) {
                String sub = s.substring(i, j);
                if (isValid(sub, targetFreq) && sub.length() < minLen) {
                    minLen = sub.length();
                    minWindow = sub;
                }
            }
        }
        return minWindow;
    }

    // Helper method for Brute Force
    private boolean isValid(String sub, int[] targetFreq) {
        int[] subFreq = new int[128];
        for (char c : sub.toCharArray()) {
            subFreq[c]++;
        }
        for (int i = 0; i < 128; i++) {
            if (targetFreq[i] > subFreq[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach - Filtered Sliding Window
     * ============================================================================
     * Detailed Intuition:
     * If the string `s` is massive, but the characters we care about (from `t`)
     * are very sparse (e.g., s = "a....................b.......c", t = "abc"),
     * iterating through all the irrelevant characters is inefficient.
     * We can first pass through `s` and extract only the characters present in `t`,
     * storing them along with their original indices. Then, we apply the sliding
     * window algorithm strictly on this heavily filtered list.
     *
     * Complexity Analysis:
     * - Time Complexity: O(m + n). We filter `s` in O(m), then run sliding window
     *   on the filtered list which takes at most O(m).
     * - Space Complexity: O(m) auxiliary heap space to store the filtered list of pairs.
     * ============================================================================
     */
    public String minWindowAlternative(String s, String t) {
        if (s == null || t == null || s.length() < t.length() || s.length() == 0) {
            return "";
        }

        int[] targetFreq = new int[128];
        int required = 0;
        for (char c : t.toCharArray()) {
            if (targetFreq[c] == 0) required++;
            targetFreq[c]++;
        }

        // Filter all the characters from s into a new list
        class Pair {
            int index;
            char c;
            Pair(int index, char c) {
                this.index = index;
                this.c = c;
            }
        }

        List<Pair> filteredS = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (targetFreq[c] > 0) {
                filteredS.add(new Pair(i, c));
            }
        }

        int left = 0, right = 0, formed = 0;
        int[] windowFreq = new int[128];
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;

        while (right < filteredS.size()) {
            Pair rightPair = filteredS.get(right);
            char c = rightPair.c;
            windowFreq[c]++;

            if (windowFreq[c] == targetFreq[c]) {
                formed++;
            }

            while (left <= right && formed == required) {
                Pair leftPair = filteredS.get(left);

                int end = rightPair.index;
                int start = leftPair.index;

                if (end - start + 1 < minLen) {
                    minLen = end - start + 1;
                    minStart = start;
                }

                char leftChar = leftPair.c;
                windowFreq[leftChar]--;
                if (windowFreq[leftChar] < targetFreq[leftChar]) {
                    formed--;
                }
                left++;
            }
            right++;
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thorough testing of all approaches against standard and edge cases.
     * Uses Java 8 Stream API to format output elegantly.
     * ============================================================================
     */
    public static void main(String[] args) {
        MinimumWindowSubstring solution = new MinimumWindowSubstring();

        // Define Test Cases [s, t, expectedOutput]
        String[][] testCases = {
                {"ADOBECODEBANC", "ABC", "BANC"},       // Standard Case
                {"a", "a", "a"},                        // Edge Case: Single Char exact match
                {"a", "aa", ""},                        // Edge Case: Impossible target
                {"aabdec", "abc", "abdec"},             // Duplicates in source
                {"bba", "ab", "ba"},                    // Overlapping chars
                {"c", "b", ""}                          // Completely different
        };

        System.out.println("🤖 Running Test Suite: Minimum Window Substring\n");

        Arrays.stream(testCases).forEach(test -> {
            String s = test[0];
            String t = test[1];
            String expected = test[2];

            String optimalResult = solution.minWindowOptimal(s, t);
            String bruteResult = solution.minWindowBruteForce(s, t);
            String altResult = solution.minWindowAlternative(s, t);

            boolean passed = optimalResult.equals(expected) &&
                    bruteResult.equals(expected) &&
                    altResult.equals(expected);

            System.out.printf("Test Case: s = \"%s\" | t = \"%s\"\n", s, t);
            System.out.printf("   Expected Output:        \"%s\"\n", expected);
            System.out.printf("   Optimal Approach:       \"%s\"\n", optimalResult);
            System.out.printf("   Brute Force Approach:   \"%s\"\n", bruteResult);
            System.out.printf("   Alternative Approach:   \"%s\"\n", altResult);
            System.out.printf("   Status: %s\n", passed ? "✅ PASSED" : "❌ FAILED");
            System.out.println("--------------------------------------------------");
        });
    }
}
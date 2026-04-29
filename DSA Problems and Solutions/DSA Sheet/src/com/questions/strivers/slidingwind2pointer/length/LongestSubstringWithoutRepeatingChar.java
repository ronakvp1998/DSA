package com.questions.strivers.slidingwind2pointer.length;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ==========================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ==========================================================================================
 * Problem: 3. Longest Substring Without Repeating Characters
 * Difficulty: Medium
 *
 * Formal Problem Statement:
 * Given a string s, find the length of the longest substring without duplicate characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Constraints:
 * - 0 <= s.length <= 5 * 10^4
 * - s consists of English letters, digits, symbols and spaces.
 * ==========================================================================================
 * Note: Since this is a string pattern recognition problem rather than Dynamic Programming,
 * the Non-DP Progressive Implementation Roadmap (Section 2.2) is applied below.
 * ==========================================================================================
 */
public class LongestSubstringWithoutRepeatingChar {

    /**
     * ======================================================================================
     * Phase 1: Optimal Approach - The "Perfect It" Stage
     * Approach: Sliding Window with Direct Address Table (Array-based Hashing)
     * ======================================================================================
     * Detailed Intuition:
     * While a HashSet or HashMap works well to track seen characters and their indices,
     * generating Integer objects and dealing with hash collisions introduces unnecessary
     * overhead. Given the constraints state that `s` consists of English letters, digits,
     * symbols, and spaces, we are dealing exclusively with the standard ASCII character set
     * (128 characters).
     *
     * We can use an integer array of size 128 as a direct address table. The array will
     * store the (index + 1) of the last time we saw a character. When we encounter a
     * character that is already in our current window, we instantly jump the 'left' pointer
     * to the right of the previous occurrence. We use Math.max to ensure our 'left' pointer
     * never moves backward (e.g., in the case of "abba").
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the string. Both left and right pointers
     *   traverse the string at most once. Character array lookup is an O(1) operation.
     * - Space Complexity: O(1) (Auxiliary Space). The integer array is always exactly 128
     *   elements regardless of the input size N. Heap space used is minimal and constant.
     *   Auxiliary stack space is O(1) since it's an iterative approach.
     * ======================================================================================
     */
    public int lengthOfLongestSubstringOptimal(String s) {
        if (s == null || s.isEmpty()) return 0;

        int[] charIndexMap = new int[128]; // Direct address table for ASCII
        int maxLength = 0;

        for (int left = 0, right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // If the character was seen, jump the left pointer to the right of its last occurrence.
            // Math.max ensures the left pointer only moves forward (handles "abba" edge case).
            left = Math.max(left, charIndexMap[currentChar]);

            // Calculate current window size and update max
            maxLength = Math.max(maxLength, right - left + 1);

            // Store the index + 1 to denote where the left pointer should jump if seen again
            charIndexMap[currentChar] = right + 1;
        }

        return maxLength;
    }

    /**
     * ======================================================================================
     * Phase 2: Brute Force Approach - The "Think It" Stage
     * Approach: Generate All Substrings and Validate
     * ======================================================================================
     * Detailed Intuition:
     * The most naive way to solve this is to generate every possible substring of `s`,
     * check if that substring has entirely unique characters, and keep track of the maximum
     * length found. It's a fundamental baseline to guarantee correctness before optimizing.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^3). There are N*(N+1)/2 substrings, taking O(N^2) to generate.
     *   For each substring, validating uniqueness takes up to O(N) time.
     * - Space Complexity: O(min(N, M)) where M is the character set size. Auxiliary heap
     *   space is used to create a HashSet for uniqueness validation during each iteration.
     *   Auxiliary stack space is O(1).
     * ======================================================================================
     */
    public int lengthOfLongestSubstringBruteForce(String s) {
        if (s == null || s.isEmpty()) return 0;
        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (hasUniqueCharacters(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    // Helper for Brute Force
    private boolean hasUniqueCharacters(String s, int start, int end) {
        Set<Character> seen = new HashSet<>();
        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            if (seen.contains(c)) return false;
            seen.add(c);
        }
        return true;
    }

    /**
     * ======================================================================================
     * Phase 3: Alternative Approach 1 - The "Refine It" Stage
     * Approach: Sliding Window with HashSet (Expanding and Contracting Window)
     * ======================================================================================
     * Detailed Intuition:
     * Instead of checking every substring from scratch, we use two pointers to represent a
     * sliding window [left, right]. We move 'right' to expand the window. If the character
     * at 'right' is already in our HashSet, it means we have a duplicate. We then increment
     * 'left', removing characters from the set until the duplicate is eliminated.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2N) = O(N). In the worst case (e.g., "abcdefg_a"), each character
     *   will be visited exactly twice by the 'left' and 'right' pointers.
     * - Space Complexity: O(min(N, M)) where M is the character set (e.g., 128 for ASCII).
     *   Heap space is consumed by the HashSet. Stack space is O(1).
     * ======================================================================================
     */
    public int lengthOfLongestSubstringHashSet(String s) {
        if (s == null || s.isEmpty()) return 0;

        Set<Character> windowSet = new HashSet<>();
        int left = 0, right = 0;
        int maxLength = 0;

        while (right < s.length()) {
            if (!windowSet.contains(s.charAt(right))) {
                windowSet.add(s.charAt(right++));
                maxLength = Math.max(maxLength, windowSet.size());
            } else {
                windowSet.remove(s.charAt(left++));
            }
        }
        return maxLength;
    }

    /**
     * ======================================================================================
     * Phase 3: Alternative Approach 2 - The "Map It" Stage
     * Approach: Sliding Window with HashMap (Jump the Left Pointer)
     * ======================================================================================
     * Detailed Intuition:
     * An evolution of the HashSet approach. Instead of sliding the 'left' pointer step-by-step
     * to find the duplicate, what if we just remembered exactly where we last saw it?
     * By using a HashMap to store (Character -> Last Seen Index), we can instantly jump the
     * 'left' pointer right past the previous duplicate, saving iterative steps.
     * Note: This is the logical precursor to the Phase 1 Optimal Array solution.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We visit each character at most once. Hashmap lookups are O(1)
     *   on average.
     * - Space Complexity: O(min(N, M)). Heap space allocated for the HashMap storing up to M
     *   unique characters. Stack space is O(1).
     * ======================================================================================
     */
    public int lengthOfLongestSubstringHashMap(String s) {
        if (s == null || s.isEmpty()) return 0;

        Map<Character, Integer> charMap = new HashMap<>();
        int maxLength = 0;

        for (int left = 0, right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            if (charMap.containsKey(currentChar)) {
                left = Math.max(left, charMap.get(currentChar) + 1);
            }
            charMap.put(currentChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * ======================================================================================
     * 4. TESTING SUITE
     * ======================================================================================
     * Evaluates all approaches against standard LeetCode examples and crucial edge cases
     * (Empty string, single space, full duplicates, pointer jump traps like "abba").
     * Utilizes Java 8 Streams for elegant execution and evaluation formatting.
     * ======================================================================================
     */
    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingChar solver = new LongestSubstringWithoutRepeatingChar();

        // Test Cases encapsulated in an inner class record for clean Stream API processing
        class TestCase {
            String input;
            int expectedOutput;
            String description;

            TestCase(String input, int expectedOutput, String description) {
                this.input = input;
                this.expectedOutput = expectedOutput;
                this.description = description;
            }
        }

        List<TestCase> testCases = Arrays.asList(
                new TestCase("abcabcbb", 3, "Standard Example 1"),
                new TestCase("bbbbb", 1, "Standard Example 2 (All Duplicates)"),
                new TestCase("pwwkew", 3, "Standard Example 3 (Subsequence Trap)"),
                new TestCase("", 0, "Edge Case: Empty String"),
                new TestCase(" ", 1, "Edge Case: Single Space"),
                new TestCase("dvdf", 3, "Edge Case: Left pointer jump requires memory mapping (vdf)"),
                new TestCase("abba", 2, "Edge Case: Left pointer backward trap (ensures Math.max check)")
        );

        System.out.println("======================================================");
        System.out.println("Running Masterclass Test Suite: Longest Substring");
        System.out.println("======================================================\n");

        // Java 8 Stream API execution
        testCases.stream().forEach(tc -> {
            int optimalResult = solver.lengthOfLongestSubstringOptimal(tc.input);
            int bruteResult = solver.lengthOfLongestSubstringBruteForce(tc.input);
            int setResult = solver.lengthOfLongestSubstringHashSet(tc.input);
            int mapResult = solver.lengthOfLongestSubstringHashMap(tc.input);

            boolean passed = (optimalResult == tc.expectedOutput) &&
                    (bruteResult == tc.expectedOutput) &&
                    (setResult == tc.expectedOutput) &&
                    (mapResult == tc.expectedOutput);

            String status = passed ? "✅ PASS" : "❌ FAIL";

            System.out.printf("%s | Desc: %s\n", status, tc.description);
            System.out.printf("   Input: \"%s\"\n", tc.input);
            System.out.printf("   Expected: %d | Optimal: %d | Set: %d | Map: %d | Brute: %d\n",
                    tc.expectedOutput, optimalResult, setResult, mapResult, bruteResult);
            System.out.println("------------------------------------------------------");
        });
    }
}
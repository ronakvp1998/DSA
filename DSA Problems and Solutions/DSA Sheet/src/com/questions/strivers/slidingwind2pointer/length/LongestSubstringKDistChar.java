package com.questions.strivers.slidingwind2pointer.length;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM: Longest Substring With At Most K Distinct Characters (Medium/Hard)
 *
 * --- HEADER & PROBLEM CONTEXT ---
 * Given a string `s` and an integer `k`, return the length of the longest
 * substring of `s` that contains at most `k` distinct characters.
 *
 * Example 1:
 * Input : s = "aababbcaacc", k = 2
 * Output: 6
 * Explanation: The longest substring is "aababb" (length = 6).
 *
 * Example 2:
 * Input : s = "abcddefg", k = 3
 * Output: 4
 * Explanation: The longest substring is "bcdd" (length = 4).
 *
 * Example 3 (Edge Case):
 * Input : s = "a", k = 0
 * Output: 0
 * Explanation: No substring can have at most 0 distinct characters unless it's empty.
 *
 * Constraints (Assumed Standard):
 * 1 <= s.length <= 5 * 10^4
 * 0 <= k <= 50
 * `s` consists of English letters (or standard ASCII).
 * ============================================================================
 * NOTE ON PROVIDED CODE EVALUATION:
 * The provided code includes an O(N^2) Brute Force and an excellent O(N)
 * Sliding Window utilizing a HashMap. While the HashMap approach is optimal
 * in time complexity, it carries a larger constant factor and memory footprint
 * due to Object wrappers (Character, Integer) and hashing overhead.
 *
 * For the TRUE Optimal approach (Phase 1), we will use an integer array as a
 * frequency map (assuming the ASCII character set). The provided Brute Force
 * will be Phase 2, and the provided HashMap approach will be preserved as
 * Phase 3 (Alternative - best for full Unicode support).
 * ============================================================================
 */


public class LongestSubstringKDistChar {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window + ASCII Array)
     * ============================================================================
     * Detailed Intuition:
     * This improves upon the HashMap sliding window. By recognizing that character
     * sets (like ASCII) are bounded (e.g., 256 characters), we can replace the
     * HashMap with a fixed-size integer array.
     *
     * We maintain a `left` and `right` pointer. As `right` expands, we increment
     * the character's frequency in the array. If the frequency transitions from
     * 0 to 1, we increment our `distinctCount`. If `distinctCount` exceeds `k`,
     * we must shrink the window from the `left` until `distinctCount` falls back
     * to `k` or below, decrementing frequencies along the way.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). Both `left` and `right` pointers traverse the string
     * at most once. Array lookups are strictly O(1) with minimal overhead.
     * Space Complexity: O(1) Auxiliary Space. We use a fixed-size array of 256
     * integers on the heap, which requires constant space regardless of input size.
     * O(1) stack space is used for primitives.
     */
    public int longestSubKDistOptimal(String s, int k) {
        if (k == 0 || s == null || s.length() == 0) return 0;

        int[] freq = new int[256]; // Assuming standard extended ASCII
        int left = 0, maxLen = 0, distinctCount = 0;

        for (int right = 0; right < s.length(); right++) {
            char cRight = s.charAt(right);
            if (freq[cRight] == 0) {
                distinctCount++;
            }
            freq[cRight]++;

            // Shrink window if distinct characters exceed k
            while (distinctCount > k) {
                char cLeft = s.charAt(left);
                freq[cLeft]--;
                if (freq[cLeft] == 0) {
                    distinctCount--;
                }
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Provided Code)
     * ============================================================================
     * Detailed Intuition:
     * The "Think it" stage. Generate all possible substrings of `s` by fixing a
     * starting index `i` and expanding an ending index `j`. For each substring,
     * track the count of distinct characters using a HashMap. If the count exceeds
     * `k`, we break out of the inner loop since extending the substring will only
     * maintain or increase the distinct character count.
     *
     * Complexity Analysis:
     * Time Complexity: O(N^2). The outer loop runs N times, and the inner loop
     * runs up to N times. Map operations take O(1) amortized time.
     * Space Complexity: O(K) Auxiliary Space. The HashMap on the heap stores at
     * most K + 1 key-value pairs at any time.
     */
    public int longestSubKDistBruteForce(String s, int k) {
        if (k == 0) return 0;

        int maxLen = 0, n = s.length();
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.clear(); // reset for new starting point
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                map.put(c, map.getOrDefault(c, 0) + 1);

                if (map.size() <= k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                } else {
                    // More than k distinct characters, stop expanding
                    break;
                }
            }
        }
        return maxLen;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Provided Sliding Window + HashMap)
     * ============================================================================
     * Detailed Intuition:
     * This is the textbook Sliding Window implementation. It functions identically
     * to the Optimal array approach but uses a HashMap. This is the preferred
     * approach in an interview if the character set is vast (e.g., full Unicode)
     * or sparsely distributed, where allocating a massive array is inefficient.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). Each character is added to and removed from the
     * HashMap at most once. Hash collisions could technically degrade this to
     * O(N * K) in the worst case, but practically it's O(N).
     * Space Complexity: O(K) Auxiliary Space. The HashMap on the heap stores
     * at most K + 1 entries.
     */
    public int longestSubKDistAlternative(String s, int k) {
        if (k == 0) return 0;

        int maxLen = 0, left = 0, right = 0, n = s.length();
        Map<Character, Integer> map = new HashMap<>();

        while (right < n) {
            // Expand window by adding current character
            char cRight = s.charAt(right);
            map.put(cRight, map.getOrDefault(cRight, 0) + 1);

            // Shrink window if distinct chars exceed k
            while (map.size() > k) {
                char cLeft = s.charAt(left);
                map.put(cLeft, map.get(cLeft) - 1);
                if (map.get(cLeft) == 0) {
                    map.remove(cLeft);
                }
                left++; // move window's left pointer
            }

            // Update max length of valid window
            maxLen = Math.max(maxLen, right - left + 1);
            right++; // expand right pointer
        }
        return maxLen;
    }

    /**
     * ============================================================================
     * SECTION 4: TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        LongestSubstringKDistChar solver = new LongestSubstringKDistChar();

        // Object array format: {String s, int k, Integer expectedResult}
        Object[][] testCases = {
                {"aababbcaacc", 2, 6},         // Standard Example 1 ("aababb")
                {"abcddefg", 3, 4},            // Standard Example 2 ("bcdd")
                {"aaabbccd", 2, 5},            // Provided Main Method Example ("aaabb")
                {"eceba", 2, 3},               // Classic LeetCode Case ("ece")
                {"a", 0, 0},                   // Edge Case: k = 0
                {"abcabcabc", 10, 9},          // Edge Case: k > distinct characters
                {"", 2, 0},                    // Edge Case: Empty String
                {"aaaaa", 1, 5}                // Edge Case: Uniform characters
        };

        System.out.println("🚀 Running Tests for: Longest Substring With At Most K Distinct Characters...\n");

        // Use Java 8 Stream API to process and evaluate test cases systematically
        IntStream.range(0, testCases.length).forEach(i -> {
            String s = (String) testCases[i][0];
            int k = (int) testCases[i][1];
            int expected = (int) testCases[i][2];

            int resOptimal = solver.longestSubKDistOptimal(s, k);
            int resBrute = solver.longestSubKDistBruteForce(s, k);
            int resAlternative = solver.longestSubKDistAlternative(s, k);

            boolean passed = (resOptimal == expected) &&
                    (resBrute == expected) &&
                    (resAlternative == expected);

            System.out.printf("Test %d: s = \"%s\", k = %d\n", i + 1, s, k);
            System.out.printf("   Expected    : %d\n", expected);
            System.out.printf("   Optimal     : %d | Brute: %d | Alternative (HashMap): %d\n", resOptimal, resBrute, resAlternative);
            System.out.printf("   Result      : %s\n", passed ? "✅ PASS" : "❌ FAIL");
            System.out.println("---------------------------------------------------------");
        });
    }
}
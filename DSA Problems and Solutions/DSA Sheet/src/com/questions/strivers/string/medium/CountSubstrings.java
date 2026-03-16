package com.questions.strivers.string.medium;

/**
 * ============================================================================
 * Count Number of Substrings (with exactly K distinct characters)
 * ============================================================================
 * Given a string of lowercase alphabets, count all possible substrings
 * (not necessarily distinct) that have exactly k distinct characters.
 * * Example 1:
 * Input: s = "aba", k = 2
 * Output: 3
 * Explanation: The substrings are: "ab", "ba" and "aba".
 * * Example 2:
 * Input: s = "abaaca", k = 1
 * Output: 7
 * Explanation: The substrings are: "a", "b", "a", "a", "c", "a", "aa".
 * * Constraints:
 * 1 <= s.length <= 10^4
 * 1 <= k <= 26
 * s consists of lowercase English letters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class CountSubstrings {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * To find all substrings with exactly 'k' distinct characters, the most
     * intuitive approach is to generate every possible substring. For every
     * starting index `i`, we expand a window to the right index `j`.
     * We maintain a frequency array to count the distinct characters in the
     * current window. If the distinct count reaches `k`, we increment our
     * answer. If it exceeds `k`, we can safely break the inner loop because
     * adding more characters will never decrease the distinct count.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2), where N is the length of the string. In the
     * worst case, we iterate through the remaining string for every character.
     * - Space Complexity: O(1) Auxiliary Heap Space. We use a frequency array
     * of size 26, which is constant regardless of the string's length.
     * ========================================================================
     */
    public static int countSubstringsBruteForce(String s, int k) {
        int n = s.length();
        int totalCount = 0;

        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];
            int distinctCount = 0;

            for (int j = i; j < n; j++) {
                int charIndex = s.charAt(j) - 'a';

                // If it's a new character, increment distinct count
                if (freq[charIndex] == 0) {
                    distinctCount++;
                }
                freq[charIndex]++;

                // If we hit exactly k distinct, we found a valid substring
                if (distinctCount == k) {
                    totalCount++;
                }
                // If we exceed k, no further extensions from 'i' will be valid
                else if (distinctCount > k) {
                    break;
                }
            }
        }
        return totalCount;
    }

    /**
     * ========================================================================
     * Phase 2: Optimal Sliding Window (At Most K Trick) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Finding substrings with *exactly* K distinct characters directly using a
     * single sliding window is impossible because the window doesn't know when
     * to shrink (e.g., if you have exact K, adding a duplicate character keeps
     * it at K, but dropping a character might also keep it at K).
     * * The Master Trick:
     * Exact(K) = AtMost(K) - AtMost(K - 1)
     * * We can easily write a sliding window to find the number of substrings with
     * *at most* K distinct characters. The number of valid substrings ending at
     * pointer `right` is exactly `(right - left + 1)`.
     * By calculating `atMost(K)` and subtracting `atMost(K - 1)`, we mathematically
     * isolate the exact count of substrings with exactly K distinct characters.
     * * Complexity Analysis:
     * - Time Complexity: O(N). The `atMost` function uses a sliding window where
     * both `left` and `right` pointers move forward at most N times. We call
     * this function twice, resulting in O(2N) -> O(N) time.
     * - Space Complexity: O(1) Auxiliary Space. We only use an integer array of
     * size 26 to track frequencies, regardless of input size.
     * ========================================================================
     */
    public static int countSubstringsOptimal(String s, int k) {
        return countAtMostK(s, k) - countAtMostK(s, k - 1);
    }

    // Helper function for the sliding window logic
    private static int countAtMostK(String s, int k) {
        // Edge case: Negative K is impossible
        if (k < 0) return 0;

        int[] freq = new int[26];
        int left = 0;
        int distinctCount = 0;
        int totalSubstrings = 0;

        for (int right = 0; right < s.length(); right++) {
            int rightCharIndex = s.charAt(right) - 'a';

            // Add right character to the window
            if (freq[rightCharIndex] == 0) {
                distinctCount++;
            }
            freq[rightCharIndex]++;

            // If we exceed K distinct characters, shrink the window from the left
            while (distinctCount > k) {
                int leftCharIndex = s.charAt(left) - 'a';
                freq[leftCharIndex]--;

                // If the frequency drops to 0, we removed a distinct character
                if (freq[leftCharIndex] == 0) {
                    distinctCount--;
                }
                left++;
            }

            // The number of valid substrings ending at 'right' is the length of the window
            totalSubstrings += (right - left + 1);
        }

        return totalSubstrings;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        // Array of test cases: {string, k}
        Object[][] testCases = {
                {"aba", 2},              // Standard case
                {"abaaca", 1},           // Multiple single-character substrings
                {"abc", 3},              // Exactly length of string
                {"abc", 4},              // K is larger than distinct characters in string
                {"aaaa", 1},             // All identical characters
                {"pqpqs", 2},            // Standard overlapping case
                {"a", 1}                 // Minimal edge case
        };

        System.out.println("Running test cases for Count Number of Substrings...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = (String) testCases[i][0];
            int k = (Integer) testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\", k = " + k);

            int res1 = countSubstringsBruteForce(s, k);
            int res2 = countSubstringsOptimal(s, k);

            System.out.println("Phase 1 (Brute Force)      : " + res1);
            System.out.println("Phase 2 (Sliding Window)   : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
package com.questions.strivers.slidingwind2pointer.length;

/**
 * =========================================================================================
 * MASTERCLASS SOLUTION: 424. Longest Repeating Character Replacement
 * =========================================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * -----------------------------------------------------------------------------------------
 * HEADER & PROBLEM CONTEXT
 * -----------------------------------------------------------------------------------------
 * You are given a string s and an integer k. You can choose any character of the string
 * and change it to any other uppercase English character. You can perform this operation
 * at most k times.
 *
 * Return the length of the longest substring containing the same letter you can get after
 * performing the above operations.
 *
 * Example 1:
 * Input: s = "ABAB", k = 2
 * Output: 4
 * Explanation: Replace the two 'A's with two 'B's or vice versa.
 *
 * Example 2:
 * Input: s = "AABABBA", k = 1
 * Output: 4
 * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
 * The substring "BBBB" has the longest repeating letters, which is 4.
 * There may exists other ways to achieve this answer too.
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s consists of only uppercase English letters.
 * 0 <= k <= s.length
 *
 * -----------------------------------------------------------------------------------------
 * CONCEPTUAL VISUALIZATION (Non-DP - Sliding Window Mechanics)
 * -----------------------------------------------------------------------------------------
 * Core Logic:
 * A window [left, right] is VALID if: (Length of Window) - (Max Character Frequency) <= k.
 * This translates to: The number of characters we NEED to change to make the whole window
 * uniform is less than or equal to our allowed replacements (k).
 *
 * Visualization of s = "AABABBA", k = 1:
 * L   R   Window   MaxFreqChar(Freq)   Valid? (Len - Freq <= k)   Action
 * 0   0   "A"      A(1)                1 - 1 <= 1 (Yes)           maxLen = 1
 * 0   1   "AA"     A(2)                2 - 2 <= 1 (Yes)           maxLen = 2
 * 0   2   "AAB"    A(2)                3 - 2 <= 1 (Yes)           maxLen = 3
 * 0   3   "AABA"   A(3)                4 - 3 <= 1 (Yes)           maxLen = 4
 * 0   4   "AABAB"  A(3)                5 - 3 > 1  (No)            Shrink: L++ (L=1)
 * 1   4   "ABAB"   A(2)/B(2)           4 - 2 > 1  (No)            Wait, notice maxLen optimization!
 *
 * *Interview Pro-Tip: In the highly optimized sliding window, we don't even need to shrink
 * the window entirely. We just shift it (L++) to avoid recalculating smaller windows
 * since we only care about finding a strictly LARGER maximum window.
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LongestRepeatingCharacterReplacement {

    /**
     * =====================================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window / Two Pointers)
     * =====================================================================================
     * Detailed Intuition:
     * This is the industry-standard approach. Instead of checking all substrings, we
     * maintain a dynamic window `[left, right]`. As we expand `right`, we track the highest
     * frequency of a single character in the current window (`maxFreq`). If the remaining
     * characters in the window exceed `k` ((window_length) - maxFreq > k), the window is
     * invalid. We resolve this by sliding the `left` pointer forward.
     *
     * Crucially, we do NOT need to strictly decrement `maxFreq` when moving `left`. We
     * are searching for a MAXIMUM length. A historical `maxFreq` ensures the window only
     * grows when we find a substring that beats our previous best.
     *
     * Complexity Analysis:
     * Time Complexity: O(N) where N is the length of the string. Both `left` and `right`
     * pointers traverse the string at most once.
     * Space Complexity: O(1) auxiliary space (an integer array of fixed size 26).
     */
    public int characterReplacementOptimal(String s, int k) {
        int[] charCounts = new int[26];
        int maxFreq = 0;
        int left = 0,right=0;
        int maxLength = 0;

        while (right < s.length()){
            int rightCharIdx = s.charAt(right) - 'A';
            charCounts[rightCharIdx]++;

            // Track the historical maximum frequency in the window
            maxFreq = Math.max(maxFreq, charCounts[rightCharIdx]);

            // Is the current window invalid? (Elements to change > k)
            if ((right - left + 1) - maxFreq > k) {
                int leftCharIdx = s.charAt(left) - 'A';
                charCounts[leftCharIdx]--;
                left++; // Shift the window rightward
            }

            // Window is valid (or at least maintained its max size), update maxLength
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }

        return maxLength;
    }

    /**
     * =====================================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * =====================================================================================
     * Detailed Intuition:
     * The "Think it" stage. To find the longest valid substring, we can simply generate
     * all possible substrings. For each substring, we determine its most frequent
     * character. If the length of the substring minus this frequency is <= k, it's a
     * valid candidate. We track the maximum length across all valid candidates.
     *
     * Complexity Analysis:
     * Time Complexity: O(N^2) where N is the string length. The nested loops generate all
     * substrings, and maintaining the frequency array takes O(1) per step.
     * Space Complexity: O(1) auxiliary space (array of size 26 initialized repeatedly).
     */
    public int characterReplacementBruteForce(String s, int k) {
        int maxLength = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int[] charCounts = new int[26];
            int maxFreq = 0;

            for (int j = i; j < n; j++) {
                int charIdx = s.charAt(j) - 'A';
                charCounts[charIdx]++;
                maxFreq = Math.max(maxFreq, charCounts[charIdx]);

                int charsToReplace = (j - i + 1) - maxFreq;

                if (charsToReplace <= k) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }

        return maxLength;
    }

    /**
     * =====================================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Binary Search on Answer)
     * =====================================================================================
     * Detailed Intuition:
     * The answer (maxLength) falls within a monotonic range: [1, N]. If a valid substring
     * of length L exists, it's highly likely valid substrings of length L-1 exist. If L
     * is invalid across the whole string, L+1 is definitely invalid. We can binary search
     * the length L. For a mid value, we use a fixed-size sliding window of length `mid`
     * to check if any such window is valid.
     *
     * Complexity Analysis:
     * Time Complexity: O(N log N). The binary search takes O(log N) iterations. In each
     * iteration, checking fixed-size windows takes O(N) time.
     * Space Complexity: O(1) auxiliary space.
     */
    public int characterReplacementBinarySearch(String s, int k) {
        int low = 1, high = s.length();
        int maxLength = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (isValidLength(s, k, mid)) {
                maxLength = mid;
                low = mid + 1; // Try to find a longer valid length
            } else {
                high = mid - 1; // Shorter lengths only
            }
        }
        return maxLength;
    }

    // Helper for Alternative Approach
    private boolean isValidLength(String s, int k, int len) {
        int[] charCounts = new int[26];
        int maxFreq = 0;

        for (int i = 0; i < s.length(); i++) {
            charCounts[s.charAt(i) - 'A']++;
            if (i >= len) {
                charCounts[s.charAt(i - len) - 'A']--;
            }

            // Recalculate maxFreq for the exact window (O(26) -> O(1))
            maxFreq = 0;
            for (int count : charCounts) {
                maxFreq = Math.max(maxFreq, count);
            }

            if (i >= len - 1 && len - maxFreq <= k) {
                return true;
            }
        }
        return false;
    }

    /**
     * =====================================================================================
     * 4. TESTING SUITE
     * =====================================================================================
     * Contains standard examples from LeetCode, plus edge cases like empty strings,
     * exact replacement matches, k=0, and k >= string length. Evaluated cleanly using
     * the Java 8 Stream API.
     */
    public static void main(String[] args) {
        LongestRepeatingCharacterReplacement solver = new LongestRepeatingCharacterReplacement();

        // Inner class to represent test cases
        class TestCase {
            String s;
            int k;
            int expected;

            TestCase(String s, int k, int expected) {
                this.s = s;
                this.k = k;
                this.expected = expected;
            }
        }

        List<TestCase> testCases = Arrays.asList(
                new TestCase("ABAB", 2, 4),                // LC Example 1
                new TestCase("AABABBA", 1, 4),             // LC Example 2
                new TestCase("A", 0, 1),                   // Edge: Single character, k=0
                new TestCase("ABCDEF", 0, 1),              // Edge: All unique, k=0
                new TestCase("AAAA", 2, 4),                // Edge: All identical, k > 0
                new TestCase("ABCDE", 5, 5),               // Edge: k >= string length
                new TestCase("BAAA", 0, 3)                 // Edge: k=0, answer at end
        );

        System.out.println("Starting Test Suite...\n");

        IntStream.range(0, testCases.size()).forEach(i -> {
            TestCase tc = testCases.get(i);

            // Testing Optimal Approach
            int optResult = solver.characterReplacementOptimal(tc.s, tc.k);
            boolean optPass = optResult == tc.expected;

            // Testing Brute Force Approach
            int bfResult = solver.characterReplacementBruteForce(tc.s, tc.k);
            boolean bfPass = bfResult == tc.expected;

            // Testing Binary Search Approach
            int bsResult = solver.characterReplacementBinarySearch(tc.s, tc.k);
            boolean bsPass = bsResult == tc.expected;

            System.out.printf("Test Case %d | s=\"%s\", k=%d | Expected: %d\n", i + 1, tc.s, tc.k, tc.expected);
            System.out.printf("  [Optimal]      Result: %d | Status: %s\n", optResult, optPass ? "PASS" : "FAIL");
            System.out.printf("  [Brute Force]  Result: %d | Status: %s\n", bfResult, bfPass ? "PASS" : "FAIL");
            System.out.printf("  [Bin Search]   Result: %d | Status: %s\n", bsResult, bsPass ? "PASS" : "FAIL");
            System.out.println("---------------------------------------------------------");
        });
    }
}


///* L8 424. Longest Repeating Character Replacement
//        You are given a string s and an integer k.
//        You can choose any character of the string and change it to any other uppercase English character.
//        You can perform this operation at most k times.
//        Return the length of the longest substring containing the same letter you can get after performing the above operations.
//        Example 1: Input: s = "ABAB", k = 2 Output: 4
//        Explanation: Replace the two 'A's with two 'B's or vice versa.
//        Example 2: Input: s = "AABABBA", k = 1 Output: 4
//        Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
//        The substring "BBBB" has the longest repeating letters, which is 4.
//        There may exists other ways to achieve this answer too. */
//
//public class LongestRepeatCharReplace {
//
//    public static void main(String[] args) {
//        String s = "AABABBA";
//        int k = 2;
//        System.out.println(longestRepeatCharReplace2(s,k));
//    }
//
//    // approach 2
//    private static int longestRepeatCharReplace2(String s ,int k){
//        int l=0,r=0,maxLen=0,maxFreq=0;
//        int hash[] = new int[26];
//        while (r < s.length()){
//            hash[s.charAt(r) - 'A']++;
//            maxFreq = Math.max(maxFreq,hash[s.charAt(r) - 'A']);
//            while ((r-l+1) - maxFreq > k){
//                hash[s.charAt(l) - 'A']--;
//                maxFreq = 0;
//                for(int i=0;i<26;i++){
//                    maxFreq = Math.max(maxFreq,hash[i]);
//                }
//                l = l + 1;
//            }
//            if((r-l+1) - maxFreq <= k){
//                maxLen = Math.max(maxLen,r-l+1);
//            }
//            r++;
//        }
//        return maxLen;
//    }
//
//
//    // approach 1
//    private static int longestRepeatCharReplace(String s ,int k){
//        int maxLen = 0,n=s.length();
//        for(int i=0;i<n;i++){
//            int hash[] = new int[26];
//            int maxFreq = 0;
//            for(int j=i;j<n;j++){
//                hash[s.charAt(j) - 'A'] += 1;
//                maxFreq = Math.max(maxFreq,hash[s.charAt(j) - 'A']);
//                int changes = (j-i+1) - maxFreq;
//                if (changes <= k){
//                    maxLen = Math.max(maxLen,j-i+1);
//                }else {
//                    break;
//                }
//            }
//        }
//        return maxLen;
//    }
//}

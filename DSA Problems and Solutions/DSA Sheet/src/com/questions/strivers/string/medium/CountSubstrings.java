package com.questions.strivers.string.medium;
/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Count Number of Substrings with Exactly K Distinct Characters
 * ==================================================================================================
 * Given a string s and integer k, find the count of substrings having exactly k
 * unique characters.
 * * Logic:
 * It is easier to find substrings with "at most K" characters.
 * Substrings with Exactly K = (Substrings with At Most K) - (Substrings with At Most K-1).
 * ==================================================================================================
 */
public class CountSubstrings {

    public static void main(String[] args) {
        String s1 = "pqpqs";
        int k1 = 2;
        System.out.println("Exactly " + k1 + " in '" + s1 + "': " + countExactlyK(s1, k1));

        String s2 = "abcbaa";
        int k2 = 3;
        System.out.println("Exactly " + k2 + " in '" + s2 + "': " + countExactlyK(s2, k2));
    }

    /**
     * Main function to compute Exactly K
     */
    public static long countExactlyK(String s, int k) {
        if (k <= 0) return 0;
        return countAtMostK(s, k) - countAtMostK(s, k - 1);
    }

    /**
     * Helper: Sliding Window to find substrings with AT MOST K distinct characters.
     * Time Complexity: O(N)
     */
    private static long countAtMostK(String s, int k) {
        if (k == 0) return 0;

        int n = s.length();
        int left = 0, right = 0, distinctCount = 0;
        long totalSubstrings = 0;
        int[] freq = new int[26]; // Frequency array for 'a'-'z'

        while (right < n) {
            // 1. Expand window: add character at 'right'
            int charIdx = s.charAt(right) - 'a';
            if (freq[charIdx] == 0) {
                distinctCount++;
            }
            freq[charIdx]++;

            // 2. Shrink window: if distinct characters exceed k
            while (distinctCount > k) {
                int leftCharIdx = s.charAt(left) - 'a';
                freq[leftCharIdx]--;
                if (freq[leftCharIdx] == 0) {
                    distinctCount--;
                }
                left++;
            }

            // 3. Add number of substrings ending at 'right'
            // The number of substrings in a window [left, right] is (right - left + 1)
            totalSubstrings += (right - left + 1);
            right++;
        }
        return totalSubstrings;
    }
}
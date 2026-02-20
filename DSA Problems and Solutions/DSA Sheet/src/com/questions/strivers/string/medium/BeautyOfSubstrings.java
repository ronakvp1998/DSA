package com.questions.strivers.string.medium;

/**
 * ==================================================================================================
 * APPROACH: Incremental Frequency Counting (O(N^2))
 * ==================================================================================================
 * 1. Use two nested loops to iterate through all possible substrings.
 * 2. Maintain a frequency array for the current substring starting at 'i'.
 * 3. For every new character added to the substring, update the frequency and
 * calculate beauty: maxFreq - minFreq.
 * 4. Sum up all beauty values.
 * ==================================================================================================
 */
public class BeautyOfSubstrings {

    public static int beautySum(String s) {
        int totalBeauty = 0;
        int n = s.length();

        // Fix the starting point of the substring
        for (int i = 0; i < n; i++) {
            // Frequency array for characters 'a'-'z' in the current substring
            int[] freq = new int[26];

            // Expand the substring by moving the end point 'j'
            for (int j = i; j < n; j++) {
                // Update frequency of the character at s[j]
                freq[s.charAt(j) - 'a']++;

                // Calculate beauty for the substring s[i...j]
                totalBeauty += calculateBeauty(freq);
            }
        }

        return totalBeauty;
    }

    /**
     * Helper to find (max frequency - min frequency) from the frequency array.
     * We only consider characters that have appeared at least once (freq > 0).
     */
    private static int calculateBeauty(int[] freq) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int f : freq) {
            if (f > 0) {
                max = Math.max(max, f);
                min = Math.min(min, f);
            }
        }

        // If all characters appear the same number of times (e.g., "abc"),
        // max - min will be 0.
        return max - min;
    }

    public static void main(String[] args) {
        String s1 = "aabcb";
        System.out.println("Sum of beauty ('aabcb'): " + beautySum(s1)); // Output: 5

        String s2 = "aabcbaa";
        System.out.println("Sum of beauty ('aabcbaa'): " + beautySum(s2)); // Output: 17
    }
}
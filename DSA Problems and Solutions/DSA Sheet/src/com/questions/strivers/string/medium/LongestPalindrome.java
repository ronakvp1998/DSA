package com.questions.strivers.string.medium;

/**
 * ==================================================================================================
 * APPROACH: Expand Around Center
 * ==================================================================================================
 * 1. A palindrome mirrors around its center.
 * 2. We iterate through the string and treat every character (and every gap) as a center.
 * 3. We expand outwards as long as the characters match.
 * 4. We keep track of the maximum length found so far.
 * ==================================================================================================
 */
public class LongestPalindrome {

    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            // Case 1: Odd length palindrome (Center is a character: "aba")
            int len1 = expandFromCenter(s, i, i);

            // Case 2: Even length palindrome (Center is between characters: "abba")
            int len2 = expandFromCenter(s, i, i + 1);

            // Take the maximum of the two
            int len = Math.max(len1, len2);

            // If we found a longer palindrome, update the start and end indices
            if (len > end - start) {
                // Calculation: start is the center minus half the length
                // end is the center plus half the length
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        // substring(start, end + 1) because end index is exclusive in Java
        return s.substring(start, end + 1);
    }

    /**
     * Helper method to expand outwards and return the length of the palindrome found.
     */
    private static int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // The length is (right - 1) - (left + 1) + 1 = right - left - 1
        return right - left - 1;
    }

    public static void main(String[] args) {
        System.out.println("babad -> " + longestPalindrome("babad")); // Output: "bab" or "aba"
        System.out.println("cbbd  -> " + longestPalindrome("cbbd"));  // Output: "bb"
    }
}
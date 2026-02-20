package com.questions.strivers.string.hard;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 647. Palindromic Substrings (Medium)
 * ==================================================================================================
 * Return the total number of palindromic substrings in string s.
 * * APPROACH: Expand Around Center
 * 1. For each character i, expand for ODD length palindromes (center is i).
 * 2. For each character i, expand for EVEN length palindromes (center is between i and i+1).
 * 3. Every successful expansion step represents one palindromic substring.
 * ==================================================================================================
 */
public class PalindromicSubstrings {

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "aaa";

        System.out.println("Input: " + s1 + " | Count: " + countSubstrings(s1)); // Output: 3
        System.out.println("Input: " + s2 + " | Count: " + countSubstrings(s2)); // Output: 6
    }

    public static int countSubstrings(String s) {
        if (s == null || s.length() == 0) return 0;

        int totalCount = 0;

        for (int i = 0; i < s.length(); i++) {
            // Case 1: Odd length palindromes. Center is s.charAt(i)
            // Example: "aba", center is 'b'
            totalCount += expandAndCount(s, i, i);

            // Case 2: Even length palindromes. Center is between s.charAt(i) and s.charAt(i+1)
            // Example: "aa", center is between 'a' and 'a'
            totalCount += expandAndCount(s, i, i + 1);
        }

        return totalCount;
    }

    /**
     * Helper method to expand outwards from a given center and count palindromes.
     */
    private static int expandAndCount(String s, int left, int right) {
        int count = 0;

        // Expand as long as pointers are within bounds and characters match
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++; // Found a palindrome
            left--;  // Move left pointer outwards
            right++; // Move right pointer outwards
        }

        return count;
    }
}
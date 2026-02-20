package com.questions.strivers.string.hard;

/**
 * ==================================================================================================
 * APPROACH: KMP Longest Prefix Suffix (LPS) Array
 * ==================================================================================================
 * 1. We use the preprocessing logic of the KMP algorithm.
 * 2. We maintain two pointers: 'i' (current suffix end) and 'len' (current prefix end).
 * 3. If characters match, we extend the length.
 * 4. If they don't, we "fallback" using the lps array to find a smaller possible prefix.
 * ==================================================================================================
 */
public class LongestHappyPrefix {

    public static String longestPrefix(String s) {
        int n = s.length();
        if (n <= 1) return "";

        // lps[i] will store the length of the longest happy prefix for s[0...i]
        int[] lps = new int[n];

        // len tracks the length of the previous longest happy prefix
        int len = 0;
        int i = 1; // We start from the second character

        while (i < n) {
            if (s.charAt(i) == s.charAt(len)) {
                // Characters match, increment length and move to next character
                len++;
                lps[i] = len;
                i++;
            } else {
                // Mismatch occurred
                if (len != 0) {
                    // This is the "magic" of KMP: instead of restarting from 0,
                    // we jump back to the previous possible happy prefix length.
                    len = lps[len - 1];
                } else {
                    // No happy prefix exists for s[0...i], move to next character
                    lps[i] = 0;
                    i++;
                }
            }
        }

        // The longest happy prefix length for the whole string is the last value in lps
        int longestLen = lps[n - 1];
        return s.substring(0, longestLen);
    }

    public static void main(String[] args) {
        System.out.println("level:  " + longestPrefix("level"));  // Output: "l"
        System.out.println("ababab: " + longestPrefix("ababab")); // Output: "abab"
    }
}
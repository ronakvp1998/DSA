package com.questions.strivers.string.hard;

/**
 * ==================================================================================================
 * APPROACH: Brute Force Sliding Window
 * ==================================================================================================
 * 1. Iterate through haystack from index 0 to (haystackLen - needleLen).
 * 2. For each index 'i', check if haystack.substring(i, i + needleLen) equals needle.
 * 3. Return 'i' if a match is found; otherwise, return -1.
 * ==================================================================================================
 */
public class FindIndex {

    public static int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        // Edge case: If needle is longer than haystack, it can't be a substring
        if (m > n) return -1;

        // Loop through haystack up to the last possible starting point
        for (int i = 0; i <= n - m; i++) {
            int j;
            // Check if characters from this starting point match the needle
            for (j = 0; j < m; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break; // Mismatch found, move to next starting point in haystack
                }
            }

            // If the inner loop finished without breaking, we found the needle
            if (j == m) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: " + strStr("sadbutsad", "sad")); // Output: 0
        System.out.println("Test 2: " + strStr("leetcode", "leeto")); // Output: -1
    }
}
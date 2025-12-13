package com.questions.strivers.slidingwind2pointer.length;

/*
L3. Longest Substring Without Repeating Characters

Problem Statement:
--------------------------------
Given a string s, find the length of the longest substring without duplicate characters.

Examples:
1. Input: s = "abcabcbb"
   Output: 3
   Explanation: "abc" is the longest substring with no repeating chars.

2. Input: s = "bbbbb"
   Output: 1
   Explanation: "b" is the longest substring.

3. Input: s = "pwwkew"
   Output: 3
   Explanation: "wke" is the longest substring.
   (Notice "pwke" is not valid because it's not contiguous).
*/

public class LongestSubstringWithoutRepeatingChar {
    public static void main(String[] args) {
        String s = "cadbzabcd";
//        System.out.println(longestSubstringWithoutRepeatingChar1(s)); // Brute force
        System.out.println(longestSubstringWithoutRepeatingChar2(s));   // Sliding window
    }

    /**
     * Approach 2: Optimized Sliding Window with HashMap (Array of size 256)
     * ---------------------------------------------------------------------
     * Idea:
     * - Use two pointers `l` (left) and `r` (right) to represent a window.
     * - Maintain the last seen index of every character in an array `map`.
     * - Expand `r` step by step, and if a duplicate character is found
     *   inside the window, move `l` to exclude the duplicate.
     * - Update the maximum length at each step.
     *
     * Example: "cadbzabcd"
     * r=0 -> "c" → length=1
     * r=1 -> "ca" → length=2
     * r=2 -> "cad" → length=3
     * r=3 -> "cadb" → length=4
     * r=4 -> "cadbz" → length=5
     * r=5 -> "adbz" → length=4 (because 'a' repeated)
     * r=6 -> "dbza" → length=4
     * r=7 -> "bzac" → length=4
     * r=8 -> "zacd" → length=4
     * Answer = 5
     *
     * Time Complexity: O(n), because each character is processed at most twice.
     * Space Complexity: O(1), since we use a fixed array of size 256 (constant).
     */
    private static int longestSubstringWithoutRepeatingChar2(String s) {
        int[] map = new int[256]; // stores last index of each character
        // Initialize all positions as -1 (not seen yet)
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }

        int l = 0, r = 0, maxLen = 0, n = s.length();

        while (r < n) {
            char ch = s.charAt(r);

            // If char was seen before and lies in the current window
            if (map[ch] != -1 && map[ch] >= l) {
                // Move left pointer after the previous occurrence
                l = map[ch] + 1;
            }

            // Calculate current window length
            int len = r - l + 1;
            maxLen = Math.max(maxLen, len);

            // Update last seen index of current character
            map[ch] = r;

            r++;
        }
        return maxLen;
    }

    /**
     * Approach 1: Brute Force
     * ------------------------
     * Idea:
     * - Generate all substrings starting from index i.
     * - Use a map/array to track if a character has already appeared.
     * - Stop when a duplicate is found.
     * - Track the maximum length of substring without repetition.
     *
     * Time Complexity: O(n^2), because for each i we may scan j till n.
     * Space Complexity: O(1), fixed array of size 256.
     */
    private static int longestSubstringWithoutRepeatingChar1(String s) {
        int n = s.length(), maxLen = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int[] map = new int[256]; // reset for each new start index
            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);

                // If char already exists, stop expanding
                if (map[ch] == 1) {
                    break;
                }

                // Update current substring length
                int len = j - i + 1;
                maxLen = Math.max(maxLen, len);

                // Mark char as seen
                map[ch] = 1;
            }
        }
        return maxLen;
    }
}

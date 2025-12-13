package com.questions.strivers.slidingwind2pointer.length;

import java.util.HashMap;
import java.util.Map;

/*
===========================================================
Problem: Longest Substring With At Most K Distinct Characters
-----------------------------------------------------------
Given a string `s` and an integer `k`, return the length of the
longest substring of `s` that contains at most `k` distinct characters.

Examples:
Input : s = "aababbcaacc", k = 2
Output: 6
Explanation: The longest substring is "aababb" (length = 6).

Input : s = "abcddefg", k = 3
Output: 4
Explanation: The longest substring is "bcdd" (length = 4).
===========================================================
*/

public class LongestSubstringKDistChar {

    public static void main(String[] args) {
        String s = "aaabbccd";
        int k = 2;
        // Using optimized sliding window
        System.out.println("Sliding Window Result: " + longestSubKDist2(s, k));
        // Using brute force
        System.out.println("Brute Force Result: " + longestSubKDist1(s, k));
    }

    /*
     * Approach 1: Brute Force
     * --------------------------------------------------------
     * - Generate all substrings of s.
     * - For each substring, count distinct characters using a map.
     * - If distinct count <= k, update maximum length.
     *
     * Time Complexity: O(n^2)
     * - Outer loop fixes starting index (n iterations).
     * - Inner loop explores ending index (up to n iterations).
     * - Each substring uses map operations → O(1) amortized per char.
     *
     * Space Complexity: O(k)
     * - Map can store at most k+1 distinct characters.
     */
    private static int longestSubKDist1(String s, int k) {
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

    /*
     * Approach 2: Sliding Window + HashMap (Optimized)
     * --------------------------------------------------------
     * - Use two pointers (left, right) to maintain a sliding window.
     * - Expand 'right' by adding characters into the map.
     * - If the map size > k (more than k distinct chars):
     *      - Shrink window from 'left' until map size ≤ k.
     * - Track max length whenever map size ≤ k.
     *
     * Time Complexity: O(n)
     * - Each character is added once and removed once from map.
     *
     * Space Complexity: O(k)
     * - Map holds at most k distinct characters at any point.
     */
    private static int longestSubKDist2(String s, int k) {
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
}

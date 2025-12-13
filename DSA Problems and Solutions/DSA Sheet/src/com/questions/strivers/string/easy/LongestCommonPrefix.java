package com.questions.strivers.string.easy;

import java.util.Arrays;

/*
🔍 Problem: 14. Longest Common Prefix
Link: https://leetcode.com/problems/longest-common-prefix/

📝 Problem Summary:
Given an array of strings `strs`, return the **longest common prefix** among all the strings.
If there is no common prefix, return "".

---

🧠 Examples:
Input:  ["flower", "flow", "flight"] → Output: "fl"
Input:  ["dog", "racecar", "car"]    → Output: ""

---
*/

public class LongestCommonPrefix {

    /**
     * ✅ Approach 1: Horizontal Scanning (Efficient)
     *
     * ➤ Idea:
     * - Start with the first string as the prefix.
     * - Compare it with each string in the array.
     * - Keep reducing the prefix until it matches the beginning of each string.
     *
     * Time Complexity: O(N * M)
     * - N = number of strings
     * - M = length of the shortest string
     * Space Complexity: O(1)
     */
    private static String longestCommonPrefixHorizontal(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            // Keep shortening prefix until it matches
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }

    /**
     * ✅ Approach 2: Vertical Scanning
     *
     * ➤ Idea:
     * - Check character-by-character for all strings.
     * - If any character doesn’t match at a position, return the prefix until that point.
     *
     * Time Complexity: O(N * M)
     * Space Complexity: O(1)
     */
    private static String longestCommonPrefixVertical(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            char ch = strs[0].charAt(i);

            for (int j = 1; j < strs.length; j++) {
                // If i exceeds current word length or characters mismatch
                if (i >= strs[j].length() || strs[j].charAt(i) != ch) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    /**
     * ✅ Approach 3: Sorting-based
     *
     * ➤ Idea:
     * - Sort the array lexicographically.
     * - Only compare the first and last strings (they will have the least common prefix).
     *
     * Time Complexity: O(N * logN + M) ≈ O(N * logN)
     * - Sorting dominates, comparison of two strings is linear in length M.
     * Space Complexity: O(1) extra (ignoring sorting space)
     */
    private static String longestCommonPrefixSorting(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        Arrays.sort(strs); // Sort lexicographically

        String first = strs[0];
        String last = strs[strs.length - 1];

        int i = 0;
        while (i < first.length() && i < last.length() && first.charAt(i) == last.charAt(i)) {
            i++;
        }

        return first.substring(0, i);
    }

    /**
     * ✅ Approach 4: Divide and Conquer
     *
     * ➤ Idea:
     * - Recursively divide array into halves.
     * - Find prefix for each half, then combine.
     *
     * Time Complexity: O(N * M)
     * Space Complexity: O(logN) for recursion stack
     */
    private static String longestCommonPrefixDivideAndConquer(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        return divide(strs, 0, strs.length - 1);
    }

    private static String divide(String[] strs, int left, int right) {
        if (left == right) return strs[left];

        int mid = (left + right) / 2;
        String lcpLeft = divide(strs, left, mid);
        String lcpRight = divide(strs, mid + 1, right);
        return commonPrefix(lcpLeft, lcpRight);
    }

    private static String commonPrefix(String s1, String s2) {
        int len = Math.min(s1.length(), s2.length());
        for (int i = 0; i < len; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return s1.substring(0, i);
        }
        return s1.substring(0, len);
    }

    public static void main(String[] args) {
        String[] strs1 = {"flower", "flow", "flight"};
        String[] strs2 = {"dog", "racecar", "car"};

        System.out.println("Horizontal Scanning: " + longestCommonPrefixHorizontal(strs1)); // "fl"
        System.out.println("Vertical Scanning:   " + longestCommonPrefixVertical(strs1));   // "fl"
        System.out.println("Sorting Approach:    " + longestCommonPrefixSorting(strs1));    // "fl"
        System.out.println("Divide & Conquer:    " + longestCommonPrefixDivideAndConquer(strs1)); // "fl"

        System.out.println("No Common Prefix (Horizontal): " + longestCommonPrefixHorizontal(strs2)); // ""
    }
}

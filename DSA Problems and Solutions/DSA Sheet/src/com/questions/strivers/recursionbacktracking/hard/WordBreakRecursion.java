package com.questions.strivers.recursionbacktracking.hard;

import java.util.*;

/**
 * Problem Statement:
 * ------------------
 * Given a string s and a dictionary of words (dict), determine if s can be segmented
 * into a sequence of one or more words from the dictionary.
 *
 * Example:
 * Input:  s = "leetcode", dict = ["leet","code"]
 * Output: true
 * Explanation: "leetcode" can be segmented as "leet code".
 *
 * ------------------------------------------------------------
 * Code Logic:
 * 1. If the input string becomes empty, return true (successful segmentation).
 * 2. For each index i (1 to s.length()):
 *      - Split the string into prefix = s[0...i-1] and suffix = s[i...end].
 *      - If the prefix exists in the dictionary, recursively check the suffix.
 * 3. If any recursive call returns true, return true.
 * 4. If no valid segmentation exists, return false.
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - For each substring, we try all possible splits.
 * - Worst-case time = O(2^n), where n = length of s (exponential growth).
 * - Reason: Each recursive call may branch into two further calls (include prefix or not).
 *
 * Space Complexity:
 * - O(n) recursion depth in worst case (call stack).
 * - No extra significant space apart from recursion.
 * - Overall = O(n).
 *
 * Note:
 * - This is the pure recursive solution (exponential).
 * - Optimized versions use Memoization (DP) to reduce time complexity to O(n^2).
 */

public class WordBreakRecursion {

    /**
     * Recursive function to check if the word can be segmented into dictionary words
     *
     * @param s     input string
     * @param dict  set of valid dictionary words
     * @return      true if s can be segmented, false otherwise
     */
    public static boolean wordBreak(String s, Set<String> dict) {
        // Base case: empty string means successfully segmented
        if (s.length() == 0) {
            return true;
        }

        // Try every possible prefix
        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);   // take substring [0, i)
            String suffix = s.substring(i);      // take substring [i, end)

            // If prefix is in dict, recursively check the suffix
            if (dict.contains(prefix) && wordBreak(suffix, dict)) {
                return true;
            }
        }

        // No valid segmentation found
        return false;
    }

    public static void main(String[] args) {
        String s = "leetcode"; // input string
        Set<String> dict = new HashSet<>(Arrays.asList("leet", "code")); // dictionary words

        boolean result = wordBreak(s, dict);

        System.out.println("Can the word \"" + s + "\" be segmented? " + result);
    }
}

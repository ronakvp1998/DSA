package com.questions.strivers.recursion.hard;

import java.util.*;

public class WordBreakRecursion {

    // Recursive function to check if word can be broken
    public static boolean wordBreak(String s, Set<String> dict) {
        // Base case: empty string means successfully segmented
        if (s.length() == 0) {
            return true;
        }

        // Try every possible prefix
        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            String suffix = s.substring(i);

            // If prefix is in dict, check suffix recursively
            if (dict.contains(prefix) && wordBreak(suffix, dict)) {
                return true;
            }
        }

        // No valid segmentation found
        return false;
    }

    public static void main(String[] args) {
        String s = "leetcode";
        Set<String> dict = new HashSet<>(Arrays.asList("leet", "code"));

        boolean result = wordBreak(s, dict);

        System.out.println("Can the word \"" + s + "\" be segmented? " + result);
    }
}

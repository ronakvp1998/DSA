package com.questions.strivers.string.easy;

import java.util.HashMap;
import java.util.Map;
/*
205. Isomorphic Strings
Easy
Topics
premium lock icon
Companies
Given two strings s and t, determine if they are isomorphic.

Two strings s and t are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.



Example 1:

Input: s = "egg", t = "add"

Output: true

Explanation:

The strings s and t can be made identical by:

Mapping 'e' to 'a'.
Mapping 'g' to 'd'.
Example 2:

Input: s = "foo", t = "bar"

Output: false

Explanation:

The strings s and t can not be made identical as 'o' needs to be mapped to both 'a' and 'r'.

Example 3:

Input: s = "paper", t = "title"

Output: true



Constraints:

1 <= s.length <= 5 * 104
t.length == s.length
s and t consist of any valid ascii character.

 */
public class IsomorphicString {

    // Approach 1: Using two HashMaps
    public static boolean isIsomorphic(String s, String t) {
        // If lengths are not equal, they can't be isomorphic
        if (s.length() != t.length()) return false;

        Map<Character, Character> sToT = new HashMap<>();
        Map<Character, Character> tToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i); // character from s
            char tc = t.charAt(i); // character from t

            // Check existing mappings
            if (sToT.containsKey(sc)) {
                if (sToT.get(sc) != tc) return false; // inconsistent mapping
            } else {
                sToT.put(sc, tc);
            }

            if (tToS.containsKey(tc)) {
                if (tToS.get(tc) != sc) return false;
            } else {
                tToS.put(tc, sc);
            }
        }

        return true;
    }

    /*
     * Time Complexity: O(n), where n is the length of the strings
     * Space Complexity: O(1) since we store at most 256 mappings (ASCII chars)
     */

    public static boolean isIsomorphic2(String s, String t) {
        int[] sMap = new int[256];  // maps for s
        int[] tMap = new int[256];  // maps for t

        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);

            // Compare last seen indices
            if (sMap[sc] != tMap[tc]) return false;

            // Store index + 1 (since default is 0)
            sMap[sc] = i + 1;
            tMap[tc] = i + 1;
        }

        return true;
    }

    /*
     * Time Complexity: O(n)
     * Space Complexity: O(1) — only 256 bytes per array (constant space for ASCII)
     */

    public static void main(String[] args) {
        System.out.println(isIsomorphic("egg", "add"));     // true
        System.out.println(isIsomorphic2("foo", "bar"));     // false
        System.out.println(isIsomorphic("paper", "title")); // true
    }
}

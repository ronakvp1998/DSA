package com.questions.strivers.string.easy;

/**
 * ==================================================================================================
 * APPROACH: Frequency Array (Hash Table)
 * ==================================================================================================
 * 1. Anagrams must have the same length.
 * 2. We use an array of size 26 to map characters 'a'-'z' to indices 0-25.
 * 3. We iterate through both strings, updating the frequency counts.
 * ==================================================================================================
 */
public class ValidAnagram {

    public static boolean isAnagram(String s, String t) {
        // Step 1: Length check
        if (s.length() != t.length()) {
            return false;
        }

        // Step 2: Create a frequency counter for 26 lowercase letters
        int[] counter = new int[26];

        for (int i = 0; i < s.length(); i++) {
            // Increment for string s
            counter[s.charAt(i) - 'a']++;
            // Decrement for string t
            counter[t.charAt(i) - 'a']--;
        }

        // Step 3: Check if all counts are zero
        for (int count : counter) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String s1 = "anagram", t1 = "nagaram";
        System.out.println("Test Case 1: " + isAnagram(s1, t1)); // true

        String s2 = "rat", t2 = "car";
        System.out.println("Test Case 2: " + isAnagram(s2, t2)); // false
    }
}
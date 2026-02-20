package com.questions.strivers.string.easy;

/**
 * ==================================================================================================
 * APPROACH: String Concatenation Trick
 * ==================================================================================================
 * 1. If 's' and 'goal' have different lengths, 's' can never become 'goal'.
 * 2. If we concatenate 's' with itself (s + s), it contains every possible
 * rotation of 's' as a substring.
 * 3. We simply check if 'goal' exists within 's + s'.
 * ==================================================================================================
 */
public class RotateString {

    public static boolean rotateString(String s, String goal) {
        // Step 1: Length check is crucial.
        // A string cannot be a rotation of another if lengths differ.
        if (s.length() != goal.length()) {
            return false;
        }

        // Step 2: Check if goal is a substring of (s + s)
        String doubledS = s + s;

        // contains() uses a substring search algorithm (like naive or KMP)
        return doubledS.contains(goal);
    }

    public static void main(String[] args) {
        // Test Case 1
        String s1 = "abcde", goal1 = "cdeab";
        System.out.println("Test Case 1: " + rotateString(s1, goal1)); // Output: true

        // Test Case 2
        String s2 = "abcde", goal2 = "abced";
        System.out.println("Test Case 2: " + rotateString(s2, goal2)); // Output: false
    }
}
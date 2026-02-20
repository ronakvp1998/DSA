package com.questions.strivers.string.medium;

/**
 * ==================================================================================================
 * APPROACH: Single Pass Counter (Virtual Stack)
 * ==================================================================================================
 * 1. We maintain 'currentDepth' to track how many parentheses are currently open.
 * 2. We maintain 'maxDepth' to record the highest value 'currentDepth' ever reached.
 * 3. We ignore digits and operators as they don't affect nesting depth.
 * ==================================================================================================
 */
public class MaximumNestingDepth {

    public static int maxDepth(String s) {
        int currentDepth = 0;
        int maxDepth = 0;

        // Iterate through each character in the string
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                // Entering a deeper level
                currentDepth++;
                // Check if this is the deepest we've been so far
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                }
            } else if (c == ')') {
                // Leaving a level
                currentDepth--;
            }
            // Ignore digits and arithmetic operators (+, -, *, /)
        }

        return maxDepth;
    }

    public static void main(String[] args) {
        // Test Case 1: (1+(2*3)+((8)/4))+1
        String s1 = "(1+(2*3)+((8)/4))+1";
        System.out.println("Test Case 1: " + maxDepth(s1)); // Output: 3

        // Test Case 2: (1)+((2))+(((3)))
        String s2 = "(1)+((2))+(((3)))";
        System.out.println("Test Case 2: " + maxDepth(s2)); // Output: 3

        // Test Case 3: "()(())((()()))"
        String s3 = "()(())((()()))";
        System.out.println("Test Case 3: " + maxDepth(s3)); // Output: 3
    }
}
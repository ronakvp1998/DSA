package com.questions.strivers.string.medium;

/**
 * ============================================================================
 * 1614. Maximum Nesting Depth of the Parentheses
 * ============================================================================
 * Given a valid parentheses string s, return the nesting depth of s.
 * The nesting depth is the maximum number of nested parentheses.
 * * Example 1:
 * Input: s = "(1+(2*3)+((8)/4))+1"
 * Output: 3
 * Explanation:
 * Digit 8 is inside of 3 nested parentheses in the string.
 * * Example 2:
 * Input: s = "(1)+((2))+(((3)))"
 * Output: 3
 * Explanation:
 * Digit 3 is inside of 3 nested parentheses in the string.
 * * Example 3:
 * Input: s = "()(())((()()))"
 * Output: 3
 * * Constraints:
 * - 1 <= s.length <= 100
 * - s consists of digits 0-9 and characters '+', '-', '*', '/', '(', and ')'.
 * - It is guaranteed that parentheses expression s is a VPS.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumNestingDepth {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Stack Simulation) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * When dealing with parentheses, the most intuitive data structure is a Stack.
     * Every time we encounter an opening parenthesis '(', we push it onto the stack.
     * This represents descending one level deeper into a nested structure.
     * Every time we encounter a closing parenthesis ')', we pop from the stack,
     * representing stepping back out of that nested structure.
     * The "depth" at any given point is exactly equal to the number of elements
     * currently sitting in the stack. By keeping track of the maximum stack size
     * during our traversal, we find the maximum nesting depth.
     * We ignore all other characters (digits, operators).
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string `s`. We iterate
     * through the string exactly once. Pushing and popping from the Deque are O(1)
     * operations.
     * - Space Complexity: O(N) Auxiliary Stack Space. In the worst-case scenario
     * (e.g., "(((((((((("), we will push all characters onto the stack,
     * consuming memory proportional to the length of the string.
     * ========================================================================
     */
    public static int maxDepthStack(String s) {
        int maxDepth = 0;
        // Using Deque as a Stack is the modern Java best practice over java.util.Stack
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                stack.push(c);
                // The current depth is the size of the stack
                maxDepth = Math.max(maxDepth, stack.size());
            } else if (c == ')') {
                stack.pop();
            }
        }

        return maxDepth;
    }

    /**
     * ========================================================================
     * Phase 2: Optimal Approach (Counter Variable) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * As a Senior Developer optimizing for space, we must ask: "Do we actually
     * need to store the characters in a stack?"
     * The answer is no. We never actually inspect the elements inside the stack;
     * we only care about its *size*.
     * Therefore, we can completely eliminate the Stack data structure and replace
     * it with a simple integer variable `currentDepth`.
     * When we see '(', we increment the counter (simulating a push).
     * When we see ')', we decrement the counter (simulating a pop).
     * We update `maxDepth` whenever `currentDepth` increases.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. We iterate
     * through the character array exactly once.
     * - Space Complexity: O(1) Auxiliary Space. We only allocate two primitive
     * integer variables (`currentDepth` and `maxDepth`). This completely
     * eliminates the O(N) heap/stack space overhead of the Deque object.
     * ========================================================================
     */
    public static int maxDepthOptimal(String s) {
        int maxDepth = 0;
        int currentDepth = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                currentDepth++;
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                }
            } else if (c == ')') {
                currentDepth--;
            }
        }

        return maxDepth;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "(1+(2*3)+((8)/4))+1",  // Example 1: Standard case with math
                "(1)+((2))+(((3)))",    // Example 2: Sequential nesting
                "()(())((()()))",       // Example 3: Pure parentheses
                "",                     // Edge case: Empty string (though constraint says length >= 1)
                "1+2*3-4",              // Edge case: Zero parentheses
                "(((((((((())))))))))"  // Edge case: Deep max nesting
        };

        System.out.println("Running test cases for 1614. Maximum Nesting Depth of the Parentheses...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            int res1 = maxDepthStack(input);
            int res2 = maxDepthOptimal(input);

            System.out.println("Phase 1 (Stack)   : " + res1);
            System.out.println("Phase 2 (Optimal) : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
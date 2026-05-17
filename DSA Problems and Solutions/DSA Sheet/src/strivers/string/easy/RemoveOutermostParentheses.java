package com.questions.strivers.string.easy;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 1021. Remove Outermost Parentheses
 * * A valid parentheses string is either empty "", "(" + A + ")", or A + B,
 * where A and B are valid parentheses strings, and + represents string concatenation.
 * For example, "", "()", "(())()", and "(()(()))" are all valid parentheses strings.
 * * A valid parentheses string s is primitive if it is nonempty, and there does
 * not exist a way to split it into s = A + B, with A and B nonempty valid
 * parentheses strings.
 * * Given a valid parentheses string s, consider its primitive decomposition:
 * s = P1 + P2 + ... + Pk, where Pi are primitive valid parentheses strings.
 * * Return s after removing the outermost parentheses of every primitive string
 * in the primitive decomposition of s.
 * * Constraints:
 * - 1 <= s.length <= 10^5
 * - s[i] is either '(' or ')'.
 * - s is a valid parentheses string.
 * * Example 1:
 * Input: s = "(()())(())"
 * Output: "()()()"
 * Explanation:
 * The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
 * After removing outer parentheses of each part, this is "()()" + "()" = "()()()".
 * * Example 2:
 * Input: s = "(()())(())(()(()))"
 * Output: "()()()()(())"
 * Explanation:
 * The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
 * After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".
 * ============================================================================
 */

import java.util.Stack;

public class RemoveOutermostParentheses {

    /**
     * ============================================================================
     * PHASE 1: BRUTE FORCE APPROACH - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most direct interpretation of the problem is to literally perform the
     * "primitive decomposition" first. We can iterate through the string, counting
     * open and close parentheses. Whenever the counts match, we have found a
     * primitive block. We then slice that block (removing the first and last
     * characters) and append it to our result.
     * * Complexity Analysis:
     * - Time: O(N). We iterate through the string once. Creating substrings also
     * takes time relative to the block size, but the total characters copied
     * across all blocks will not exceed N. Thus, overall time is O(N).
     * - Space: O(N) auxiliary space. The `substring` method creates new String
     * objects in the heap before they are appended to the StringBuilder.
     */
    public String removeOuterParenthesesBruteForce(String s) {
        StringBuilder result = new StringBuilder();
        int openCount = 0;
        int closeCount = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                openCount++;
            } else {
                closeCount++;
            }

            // A primitive string is found when open and close counts balance out
            if (openCount == closeCount) {
                // Append the primitive string WITHOUT its outermost parentheses
                result.append(s.substring(start + 1, i));
                start = i + 1; // Reset start for the next primitive block
            }
        }
        return result.toString();
    }

    /**
     * ============================================================================
     * PHASE 2: ALTERNATIVE APPROACH - Stack (Simulation)
     * ============================================================================
     * Detailed Intuition:
     * Instead of waiting to find the end of a primitive block and slicing it,
     * we can process characters on the fly using a Stack to represent "depth".
     * If we push an '(' to the stack and the stack was already NOT empty, it means
     * this '(' is an inner parenthesis, so we keep it. If we pop a ')' and the
     * stack is STILL NOT empty, it's an inner parenthesis, so we keep it.
     * * Complexity Analysis:
     * - Time: O(N). Single pass through the string.
     * - Space: O(N) heap space. In the worst-case scenario (e.g., "(((((( ))))))"),
     * we will push N/2 characters onto the Stack.
     */
    public String removeOuterParenthesesStack(String s) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                // If stack is not empty BEFORE pushing, it's not the outermost '('
                if (!stack.isEmpty()) {
                    result.append(c);
                }
                stack.push(c);
            } else {
                stack.pop(); // Remove the matching '('
                // If stack is not empty AFTER popping, it's not the outermost ')'
                if (!stack.isEmpty()) {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * ============================================================================
     * PHASE 2: ALTERNATIVE APPROACH - Optimal Counter (Space Optimized)
     * ============================================================================
     * Detailed Intuition:
     * Building on the Stack intuition: we don't actually need to store the
     * characters in a physical Stack. Since the only character we ever push is '(',
     * we only care about the *size* of the stack (the depth of the parentheses).
     * We can replace the O(N) Stack with an O(1) integer counter.
     * * Complexity Analysis:
     * - Time: O(N). Single pass through the string array.
     * - Space: O(1) auxiliary space. We only use a single integer. (The O(N) heap
     * space used by the StringBuilder is required for the output and does not
     * count towards auxiliary algorithm space).
     */
    public String removeOuterParenthesesOptimal(String s) {
        StringBuilder result = new StringBuilder();
        int depth = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Only append if it's an inner parenthesis (depth > 0)
                if (depth > 0) {
                    result.append(c);
                }
                depth++;
            } else {
                depth--;
                // Only append if it's an inner parenthesis (depth > 0 after decrementing)
                if (depth > 0) {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        RemoveOutermostParentheses solver = new RemoveOutermostParentheses();

        // Standard test cases from LeetCode
        String[] testCases = {
                "(()())(())",           // Expected: "()()()"
                "(()())(())(()(()))",   // Expected: "()()()()(())"
                "()()",                 // Expected: ""
                "(((((())))))"          // Deep nesting edge case -> Expected: "((((()))))"
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": " + testCases[i]);
            System.out.println("  Brute Force: " + solver.removeOuterParenthesesBruteForce(testCases[i]));
            System.out.println("  Stack Appr:  " + solver.removeOuterParenthesesStack(testCases[i]));
            System.out.println("  Optimal:     " + solver.removeOuterParenthesesOptimal(testCases[i]));
            System.out.println("-".repeat(50));
        }
    }
}
package com.questions.strivers.greedyalgorithm.easy;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 678. Valid Parenthesis String (Medium)
 * ==================================================================================================
 * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
 * * The following rules define a valid string:
 * 1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * 2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * 3. Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * 4. '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 *
 * Example 1:
 * Input: s = "()"  -> Output: true
 *
 * Example 2:
 * Input: s = "(*)" -> Output: true
 *
 * Example 3:
 * Input: s = "(*))" -> Output: true
 * * ==================================================================================================
 * APPROACH: GREEDY (Min/Max Open Parentheses Tracking)
 * ==================================================================================================
 * Instead of checking every single combination of what '*' could be (which takes exponential time),
 * we can simply track the RANGE of possible open parentheses at any given step.
 * * - `minOpen`: The minimum possible number of unmatched '(' we currently have.
 * - `maxOpen`: The maximum possible number of unmatched '(' we currently have.
 * * When we see '(': Both minOpen and maxOpen increase.
 * When we see ')': Both minOpen and maxOpen decrease.
 * When we see '*':
 * - maxOpen increases (if we treat '*' as '(')
 * - minOpen decreases (if we treat '*' as ')')
 * * If `maxOpen` ever becomes negative, it means even if we treat all '*' as '(', we still have
 * too many ')'. So the string is invalid.
 * If `minOpen` drops below 0, it means we tried to treat a '*' as a ')', but we didn't have
 * an open '(' to match it with. We just reset `minOpen` to 0 because we can choose to treat
 * that '*' as an empty string instead.
 * ==================================================================================================
 */
public class ValidParenthesisString {

    public static void main(String[] args) {
        System.out.println("Test Case 1 '()'   -> " + checkValidString("()"));     // true
        System.out.println("Test Case 2 '(*)'  -> " + checkValidString("(*)"));    // true
        System.out.println("Test Case 3 '(*))' -> " + checkValidString("(*))"));   // true
        System.out.println("Test Case 4 ')*('  -> " + checkValidString(")*("));    // false
    }

    /**
     * Checks if a string containing '(', ')', and '*' is a valid parenthesis string.
     * * @param s The input string
     * @return true if valid, false otherwise
     */
    public static boolean checkValidString(String s) {
        // Minimum possible number of open parentheses
        int minOpen = 0;
        // Maximum possible number of open parentheses
        int maxOpen = 0;

        // Iterate through each character in the string
        for (char c : s.toCharArray()) {

            if (c == '(') {
                // Must be an open bracket, so both minimum and maximum possible counts increase
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                // Must be a close bracket, so both minimum and maximum possible counts decrease
                minOpen--;
                maxOpen--;
            } else {
                // It's a '*'.
                // We could use it as a ')' which DECREASES our minimum open count
                minOpen--;
                // We could use it as a '(' which INCREASES our maximum open count
                maxOpen++;
            }

            // If maxOpen is negative, it means we have more ')' than '(' and '*' combined.
            // There is no possible way to fix this, so it's strictly invalid.
            if (maxOpen < 0) {
                return false;
            }

            // If minOpen is negative, it means we forced a '*' to act as a ')',
            // but we didn't have any unmatched '(' available.
            // We can easily fix this by just letting that '*' be an empty string ("") instead.
            // So we reset minOpen to 0. (We can never have negative open brackets in reality).
            if (minOpen < 0) {
                minOpen = 0;
            }
        }

        // At the end, if minOpen is 0, it means there's a valid path where all '(' are matched.
        return minOpen == 0;
    }
}
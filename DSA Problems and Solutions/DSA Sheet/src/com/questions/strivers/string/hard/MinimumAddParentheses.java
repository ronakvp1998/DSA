package com.questions.strivers.string.hard;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 921. Minimum Add to Make Parentheses Valid (Medium)
 * ==================================================================================================
 * Given a string s of '(' and ')', return the minimum number of insertions needed to
 * make the string valid.
 * * Logic:
 * - We need to track unmatched '(' and unmatched ')'.
 * - A ')' can only match a '(' that appeared BEFORE it.
 * ==================================================================================================
 */
public class MinimumAddParentheses {

    public static void main(String[] args) {
        String s1 = "())";
        String s2 = "(((";
        String s3 = "()))((";

        System.out.println("Input: " + s1 + " | Moves: " + minAddToMakeValid(s1)); // Output: 1
        System.out.println("Input: " + s2 + " | Moves: " + minAddToMakeValid(s2)); // Output: 3
        System.out.println("Input: " + s3 + " | Moves: " + minAddToMakeValid(s3)); // Output: 4
    }

    /**
     * OPTIMAL APPROACH: Greedy Counter
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public static int minAddToMakeValid(String s) {
        // 'balance' tracks '(' that have been opened but not yet closed.
        int balance = 0;
        // 'additions' tracks ')' that appeared without a leading '('.
        int additions = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                // We found an opening bracket, it might be matched later.
                balance++;
            } else {
                // We found a closing bracket ')'.
                if (balance > 0) {
                    // There is an available '(' to match this ')'.
                    balance--;
                } else {
                    // No '(' available, we MUST add one at some point.
                    additions++;
                }
            }
        }

        // Total moves = unmatched ')' (additions) + unmatched '(' (balance).
        return additions + balance;
    }
}
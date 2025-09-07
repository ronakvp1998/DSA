package com.questions.strivers.recursion.subsequences;

import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {

    // Main function to generate all combinations
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    // Recursive backtracking function
    private static void backtrack(List<String> result, StringBuilder current, int open, int close, int n) {
        // Base case: if the string is of length 2*n, it's a valid sequence
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        // Add an opening parenthesis if possible
        if (open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1); // backtrack
        }

        // Add a closing parenthesis if possible (only if close < open)
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1); // backtrack
        }
    }

    public static void main(String[] args) {
        int n = 3;
        List<String> result = generateParenthesis(n);
        System.out.println("All Balanced Parentheses for n = " + n + ":");
        for (String s : result) {
            System.out.println(s);
        }
    }
}

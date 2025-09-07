package com.questions.strivers.recursion.hard;

import java.util.*;

public class LetterCombinations {

    // Mapping of digits to characters
    private static final String[] KEYPAD = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    // Recursive backtracking function
    public static void backtrack(String digits, int index, String current, List<String> result) {
        // Base case: if length matches, add to result
        if (index == digits.length()) {
            result.add(current);
            return;
        }

        // Get letters corresponding to current digit
        String letters = KEYPAD[digits.charAt(index) - '0'];

        // Try each possible letter
        for (char c : letters.toCharArray()) {
            backtrack(digits, index + 1, current + c, result);
        }
    }

    // Main function to generate combinations
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        if (digits == null || digits.length() == 0) {
            return result;
        }

        backtrack(digits, 0, "", result);
        return result;
    }

    public static void main(String[] args) {
        String digits = "23";
        List<String> combinations = letterCombinations(digits);

        System.out.println("Letter Combinations: " + combinations);
    }
}

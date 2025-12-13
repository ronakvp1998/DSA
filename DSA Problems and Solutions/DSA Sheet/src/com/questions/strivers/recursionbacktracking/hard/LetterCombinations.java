package com.questions.strivers.recursionbacktracking.hard;

import java.util.*;

/**
 * Problem Statement:
 * ------------------
 * Given a string containing digits from 2-9 inclusive, return all possible
 * letter combinations that the number could represent. The mapping of digits
 * to letters is the same as on a telephone keypad.
 *
 * Example:
 * Input:  digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * ------------------------------------------------------------
 * Code Logic:
 * 1. Use recursion with backtracking to generate all possible strings.
 * 2. At each index of the digits string:
 *      - Fetch the letters corresponding to the current digit from the KEYPAD mapping.
 *      - Append each possible character and recurse to the next index.
 * 3. When the length of the current combination equals the digits length,
 *    add it to the result list.
 * 4. Backtracking ensures all possible paths are explored.
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - Each digit maps to up to 4 letters (max for digit '7' or '9').
 * - If the input has n digits, total combinations = O(4^n).
 * - Building each string combination takes O(n).
 * - Overall Time Complexity = O(n * 4^n).
 *
 * Space Complexity:
 * - Recursion depth = O(n).
 * - Result storage = O(4^n) combinations, each of length n → O(n * 4^n).
 * - Overall Space Complexity = O(n + n * 4^n).
 */

public class LetterCombinations {

    // Mapping of digits to characters (telephone keypad)
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

    /**
     * Recursive backtracking function to generate combinations
     *
     * @param digits  input string of digits
     * @param index   current index in the digit string
     * @param current current combination being formed
     * @param result  list to store valid combinations
     */
    private static void backtrack(String digits, int index, String current, List<String> result) {
        // Base case: if length matches, add to result
        if (index == digits.length()) {
            result.add(current);
            return;
        }

        // Get letters corresponding to current digit
        String letters = KEYPAD[digits.charAt(index) - '0'];

        // Try each possible letter and recurse
        for (char c : letters.toCharArray()) {
            backtrack(digits, index + 1, current + c, result);
        }
    }

    /**
     * Main function to generate all letter combinations
     */
    private static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        // Edge case: empty input
        if (digits == null || digits.length() == 0) {
            return result;
        }

        backtrack(digits, 0, "", result);
        return result;
    }

    public static void main(String[] args) {
        String digits = "23"; // Example input
        List<String> combinations = letterCombinations(digits);

        System.out.println("Letter Combinations: " + combinations);
    }
}

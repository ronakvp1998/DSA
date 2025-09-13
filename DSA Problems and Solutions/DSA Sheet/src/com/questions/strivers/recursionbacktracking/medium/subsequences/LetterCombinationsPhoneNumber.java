package com.questions.strivers.recursionbacktracking.medium.subsequences;

import java.util.ArrayList;
import java.util.List;

public class LetterCombinationsPhoneNumber {

    /*
     * Problem: Given digits (2-9), return all possible letter combinations
     *          based on phone keypad mapping.
     *
     * Approach: Backtracking
     * ---------------------------------
     * - Use a recursive function to build strings digit by digit.
     * - For each digit, try all possible letters it maps to.
     * - Continue until we build a string of length = digits.length.
     * - Add the string to the result list.
     */

    // Phone digit-to-letters mapping
    private static final String[] KEYPAD = {
            "",     // 0 (not used)
            "",     // 1 (not used)
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        // Edge case: empty input → no combinations
        if (digits == null || digits.length() == 0) {
            return result;
        }

        // Start recursive backtracking
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    /*
     * Recursive function to generate combinations
     * @param result   → final list of combinations
     * @param current  → current string being built
     * @param digits   → input digits string
     * @param index    → current position in digits string
     */
    private void backtrack(List<String> result, StringBuilder current,
                           String digits, int index) {
        // Base case: if we processed all digits, add to result
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // Get the possible letters for the current digit
        String letters = KEYPAD[digits.charAt(index) - '0'];

        // Try each letter
        for (char ch : letters.toCharArray()) {
            current.append(ch); // choose
            backtrack(result, current, digits, index + 1); // explore
            current.deleteCharAt(current.length() - 1); // undo (backtrack)
        }
    }

    // Driver code
    public static void main(String[] args) {
        LetterCombinationsPhoneNumber solution = new LetterCombinationsPhoneNumber();

        System.out.println(solution.letterCombinations("23")); // [ad, ae, af, bd, be, bf, cd, ce, cf]
        System.out.println(solution.letterCombinations(""));   // []
        System.out.println(solution.letterCombinations("2"));  // [a, b, c]
    }
}
/*
Time Complexity:
Each digit maps to at most 4 letters (digit 7 & 9).
For n = digits.length, number of combinations = O(4^n).
Each combination takes O(n) to build.
Total = O(n * 4^n)

Space Complexity:
Recursion depth = O(n).
StringBuilder stores at most n characters.
Result list stores O(4^n) combinations.
Space = O(n + 4^n)
 */
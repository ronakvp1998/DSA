package com.questions.strivers.recursionbacktracking.medium.subsequences;

import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation {

    /*
     * Problem Statement:
     * Given a string s, return a list of all possible strings formed by toggling
     * the case (uppercase/lowercase) of its alphabetical characters.
     * Digits remain unchanged.
     *
     * Example:
     * Input: "a1b2"
     * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
     */

    // Main function to generate all permutations of the string
    private static List<String> letterCasePermutation(String s) {
        List<String> result = new ArrayList<>();
        // Start recursive backtracking from index 0 with an empty current string
        generatePermutations(s, 0, new StringBuilder(), result);
        return result;
    }

    /*
     * Recursive helper function to generate permutations
     * s       -> original input string
     * index   -> current position in the string
     * current -> building string (so far) using StringBuilder
     * result  -> stores all valid permutations
     */
    private static void generatePermutations(String s, int index, StringBuilder current, List<String> result) {
        // Base case: if we have processed the entire string
        if (index == s.length()) {
            result.add(current.toString()); // Add the formed string to result
            return;
        }

        char ch = s.charAt(index); // Current character

        // Case 1: If character is a letter, explore both lowercase and uppercase
        if (Character.isLetter(ch)) {
            // Option 1: lowercase
            current.append(Character.toLowerCase(ch));
            generatePermutations(s, index + 1, current, result); // Recursive call
            current.deleteCharAt(current.length() - 1); // Backtrack

            // Option 2: uppercase
            current.append(Character.toUpperCase(ch));
            generatePermutations(s, index + 1, current, result); // Recursive call
            current.deleteCharAt(current.length() - 1); // Backtrack
        } else {
            // Case 2: If character is a digit, only one option (keep as is)
            current.append(ch);
            generatePermutations(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1); // Backtrack
        }
    }

    // Driver code to test the function
    public static void main(String[] args) {
        String s = "a1b2";
        List<String> result = letterCasePermutation(s);
        System.out.println("All Letter Case Permutations: " + result);
    }
}

/*
----------------------------------------------------
Time Complexity Analysis:
----------------------------------------------------
- Let n = length of the input string.
- Each letter has 2 choices (uppercase or lowercase).
- If there are k letters, total combinations = 2^k.
- Each permutation requires O(n) time to build (since we use StringBuilder).
- Overall Time Complexity: O(2^k * n), where k = number of letters.

----------------------------------------------------
Space Complexity Analysis:
----------------------------------------------------
- Recursion depth = O(n) (because we process one character at a time).
- StringBuilder at most stores O(n) characters.
- Result list stores O(2^k) strings, each of length n → O(2^k * n).
- Overall Space Complexity: O(2^k * n).
*/

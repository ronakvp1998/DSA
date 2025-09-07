package com.questions.strivers.recursion.basics;

import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation {

    // Main function to generate permutations
    public static List<String> letterCasePermutation(String s) {
        List<String> result = new ArrayList<>();
        generatePermutations(s, 0, new StringBuilder(), result);
        return result;
    }

    // Recursive helper function
    private static void generatePermutations(String s, int index, StringBuilder current, List<String> result) {
        // Base case: if we've processed the entire string
        if (index == s.length()) {
            result.add(current.toString());
            return;
        }

        char ch = s.charAt(index);

        if (Character.isLetter(ch)) {
            // Option 1: lowercase
            current.append(Character.toLowerCase(ch));
            generatePermutations(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1); // backtrack

            // Option 2: uppercase
            current.append(Character.toUpperCase(ch));
            generatePermutations(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1); // backtrack
        } else {
            // If digit, just add as is
            current.append(ch);
            generatePermutations(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1); // backtrack
        }
    }

    public static void main(String[] args) {
        String s = "a1b2";
        List<String> result = letterCasePermutation(s);
        System.out.println("All Letter Case Permutations: " + result);
    }
}

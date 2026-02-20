package com.questions.strivers.string.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 151. Reverse Words in a String (Medium)
 * ==================================================================================================
 * Given an input string s, reverse the order of the words.
 * A word is defined as a sequence of non-space characters.
 * * Rules:
 * 1. Words in s are separated by at least one space.
 * 2. s may contain leading or trailing spaces.
 * 3. s may contain multiple spaces between words.
 * 4. Result must have words in reverse order separated by exactly one space.
 * 5. No leading or trailing spaces in result.
 *
 * Example:
 * Input:  "  hello world  "
 * Output: "world hello"
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Built-in Methods)
 * ==================================================================================================
 * 1. Use s.trim() to remove leading/trailing spaces.
 * 2. Use s.split("\\s+") to split the string by one or more whitespace characters.
 * 3. Use Collections.reverse() or a simple reverse loop to swap positions in the array.
 * 4. Join the array back into a string using String.join(" ").
 * ==================================================================================================
 * APPROACH 2: OPTIMAL / HEAP-STYLE DEQUE (Manual Two-Pointer/Deque)
 * ==================================================================================================
 * 1. Use two pointers (left and right) to ignore leading/trailing spaces.
 * 2. Traverse the string, identifying each word.
 * 3. Use a Deque (Double-Ended Queue) to store words. By using deque.addFirst(),
 * the words are effectively reversed as they are added (similar to a Stack).
 * 4. Join the words with a single space.
 * ==================================================================================================
 */

import java.util.*;

public class ReverseWords {

    public static void main(String[] args) {
        String input1 = "the sky is blue";
        String input2 = "  hello world  ";
        String input3 = "a good   example";

        System.out.println("Result 1: '" + reverseWordsOptimal(input1) + "'");
        System.out.println("Result 2: '" + reverseWordsOptimal(input2) + "'");
        System.out.println("Result 3: '" + reverseWordsOptimal(input3) + "'");
    }

    /**
     * OPTIMAL APPROACH: Manual Parsing with Deque
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    public static String reverseWordsOptimal(String s) {
        int left = 0, right = s.length() - 1;

        // 1. Remove leading spaces
        while (left <= right && s.charAt(left) == ' ') {
            left++;
        }

        // 2. Remove trailing spaces
        while (left <= right && s.charAt(right) == ' ') {
            right--;
        }

        Deque<String> d = new ArrayDeque<>();
        StringBuilder word = new StringBuilder();

        // 3. Extract words and add them to the front of the Deque
        while (left <= right) {
            char c = s.charAt(left);

            // If we find a non-space char, build the word
            if (c != ' ') {
                word.append(c);
            }
            // If we hit a space and 'word' isn't empty, push it to front (reversing it)
            else if (word.length() > 0) {
                d.offerFirst(word.toString());
                word.setLength(0); // Reset StringBuilder for the next word
            }
            left++;
        }

        // Push the very last word after the loop ends
        d.offerFirst(word.toString());

        // 4. Join the words in the Deque with a single space
        return String.join(" ", d);
    }

    /**
     * BRUTE FORCE APPROACH: Built-in split and reverse
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    public static String reverseWordsBruteForce(String s) {
        // Trim and split by any number of spaces
        String[] words = s.trim().split("\\s+");

        // Reverse the array
        Collections.reverse(Arrays.asList(words));

        // Join with single space
        return String.join(" ", words);
    }
}
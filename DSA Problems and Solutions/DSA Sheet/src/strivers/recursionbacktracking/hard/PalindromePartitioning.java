package com.questions.strivers.recursionbacktracking.hard;

import java.util.ArrayList;
import java.util.List;

/*
Problem Statement:
You are given a string s, partition it in such a way that every substring is a palindrome.
Return all such palindromic partitions of s.

Note: A palindrome string is a string that reads the same backward as forward.

Examples:

Example 1:
Input: s = "aab"
Output: [ ["a","a","b"], ["aa","b"] ]

Example 2:
Input: s = "aabb"
Output: [ ["a","a","b","b"], ["aa","bb"], ["a","a","bb"], ["aa","b","b"] ]
*/

public class PalindromePartitioning {

    // Main function that initializes recursive partitioning
    private static List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();  // stores final result (all partitions)
        List<String> path = new ArrayList<>();       // stores current partition (path)
        partitionHelper(0, s, path, res);            // start recursion from index 0
        return res;
    }

    // Recursive helper function for generating partitions
    static void partitionHelper(int index, String s, List<String> path, List<List<String>> res) {
        // Base case: if index reaches end of string, we found a valid partition
        if (index == s.length()) {
            res.add(new ArrayList<>(path));  // add a copy of current path
            return;
        }

        // Explore all possible substrings starting at 'index'
        for (int i = index; i < s.length(); ++i) {
            // If substring from index -> i is palindrome
            if (isPalindrome(s, index, i)) {
                // Add substring to current path
                path.add(s.substring(index, i + 1));

                // Recur for remaining string
                partitionHelper(i + 1, s, path, res);

                // Backtrack: remove last added substring to explore new partitions
                path.remove(path.size() - 1);
            }
        }
    }

    // Utility function to check if a substring is palindrome
    static boolean isPalindrome(String s, int start, int end) {
        while (start <= end) {
            if (s.charAt(start++) != s.charAt(end--))  // mismatch → not palindrome
                return false;
        }
        return true;
    }

    public static void main(String args[]) {
        String s = "aabb";
        List<List<String>> ans = partition(s);

        System.out.println("The Palindromic partitions are :-");
        System.out.print(" [ ");
        for (int i = 0; i < ans.size(); i++) {
            System.out.print("[ ");
            for (int j = 0; j < ans.get(i).size(); j++) {
                System.out.print(ans.get(i).get(j) + " ");
            }
            System.out.print("] ");
        }
        System.out.print("]");
    }
}

/*
----------------------------
Time Complexity Analysis:
----------------------------
- At each index, we try all possible substrings (O(n) choices).
- For each substring, we check if it is a palindrome: O(n) in worst case.
- The recursion explores all possible partitions (which can be exponential).
- The total number of partitions for a string of length n is O(2^n).

=> Overall Time Complexity: O(n * 2^n)
   - O(2^n) for generating all partitions
   - O(n) for palindrome check at each step.

----------------------------
Space Complexity Analysis:
----------------------------
- Recursion depth can go up to O(n) (since at most n substrings in a path).
- Each recursive path stores substrings → O(n) space.
- Result storage can take O(2^n) in worst case (since output is exponential).

=> Overall Space Complexity: O(n) (recursion + path storage)
   + O(2^n * n) for storing results.
*/

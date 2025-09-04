package com.questions.strivers.slidingwind2pointer.count;

import java.util.Arrays;

/*
L7. Number of Substrings Containing All Three Characters
---------------------------------------------------------
Problem Statement:
You are given a string s consisting only of characters 'a', 'b', and 'c'.
Return the number of substrings that contain at least one occurrence of all three characters.

Examples:
----------
Input: s = "abcabc"
Output: 10
Explanation: Valid substrings are "abc", "abca", "abcab", "abcabc",
             "bca", "bcab", "bcabc", "cab", "cabc", "abc".

Input: s = "aaacb"
Output: 3
Explanation: Valid substrings are "aaacb", "aacb", "acb".

Input: s = "abc"
Output: 1
Explanation: Only "abc".
*/

public class NumSubstringCont3Char {
    public static void main(String[] args) {
        String s = "bbacba";
        System.out.println(numSubStringCont2(s)); // Using Approach 2
    }

    // ✅ Approach 3 (Optimized using last seen indices)
    // Idea:
    // - Maintain last seen index of 'a', 'b', and 'c'.
    // - At every index i, check if we have seen all three characters before.
    // - The number of valid substrings ending at i is (1 + min(lastSeen[a], lastSeen[b], lastSeen[c])).
    //   Because substrings starting from any index <= min(lastSeen[]) up to i will contain all 'a','b','c'.

//    Approach 3: Optimized using last seen indices
//   - Time: O(n)
//   - Space: O(1)
    public static int numSubStringCont3(String s) {
        int lastSeen[] = new int[3]; // stores last seen indices for 'a','b','c'
        int n = s.length();
        Arrays.fill(lastSeen, -1);
        int count = 0;

        for (int i = 0; i < n; i++) {
            lastSeen[s.charAt(i) - 'a'] = i; // update last seen index of current char
            if (lastSeen[0] != -1 && lastSeen[1] != -1 && lastSeen[2] != -1) {
                // min of lastSeen gives leftmost valid start
                count += (1 + Math.min(Math.min(lastSeen[0], lastSeen[1]), lastSeen[2]));
            }
        }
        return count;
    }

    // ✅ Approach 2 (Improved Brute Force)
    // Idea:
    // - Start at each index i, expand j until all 3 chars are found.
    // - Once found at position j, ALL substrings from s[i..j] to s[i..n-1] are valid.
    //   (that's why we add (n-j) directly and break).
    // Time: O(n^2) (since we scan substrings but break early when all chars are found)
    // Space: O(1)

//    Approach 2: Improved Brute Force (break early when all 3 chars found)
//   - Time: O(n^2) in worst case, but faster in practice.
//            - Space: O(1)
    public static int numSubStringCont2(String s) {
        int count = 0, n = s.length();
        for (int i = 0; i < n; i++) {
            int arr[] = new int[3]; // track if 'a','b','c' are seen
            for (int j = i; j < n; j++) {
                arr[s.charAt(j) - 'a'] = 1;
                if (arr[0] + arr[1] + arr[2] == 3) {
                    count += (n - j); // all substrings from j..end are valid
                    break;
                }
            }
        }
        return count;
    }

    // ✅ Approach 1 (Naive Brute Force)
    // Idea:
    // - Generate all substrings and check if it contains all 3 characters.
    // - Use array[3] to mark presence of a,b,c in substring.
    // Time: O(n^2) (all substrings) * O(1) (check) = O(n^2)
    // Space: O(1)
//    Approach 1: Naive Brute Force
//   - Time: O(n^2)
//   - Space: O(1)
    public static int numSubStringCont1(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            int arr[] = new int[3];
            for (int j = i; j < s.length(); j++) {
                arr[s.charAt(j) - 'a'] = 1;
                if (arr[0] + arr[1] + arr[2] == 3) {
                    count++; // count every valid substring individually
                }
            }
        }
        return count;
    }
}
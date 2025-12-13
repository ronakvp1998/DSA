package com.questions.strivers.string.easy;

/*
🔍 Problem: 1903. Largest Odd Number in String
Link: https://leetcode.com/problems/largest-odd-number-in-string/

📝 Problem Summary:
Given a string `num` representing a large non-negative integer, return the **largest-valued odd integer** (as a string)
that is a **non-empty substring** of `num`. Return `""` if no odd number exists.

A number is odd if its **last digit is 1, 3, 5, 7, or 9**.

---

🧠 Examples:
Input:  "52"     → Output: "5"
Input:  "4206"   → Output: ""
Input:  "35427"  → Output: "35427"
*/

public class LargestOddNumInString {

    /**
     * ✅ Approach 1: Right-to-Left Scan (Optimal)
     *
     * ➤ Idea:
     * - Scan from the end of the string.
     * - The first digit you find that is odd will give you the largest odd-valued substring: `num.substring(0, i+1)`
     *
     * Time Complexity: O(n)
     * - One pass from the end of the string.
     * Space Complexity: O(1)
     * - No extra space apart from the result substring.
     */
    private static String largestOddNumber(String num) {
        for (int i = num.length() - 1; i >= 0; i--) {
            char ch = num.charAt(i);
            if ((ch - '0') % 2 == 1) {
                return num.substring(0, i + 1);
            }
        }
        return "";
    }

    /**
     * ✅ Approach 2: Brute-force (Not Recommended for Large Inputs)
     *
     * ➤ Idea:
     * - Generate all possible substrings and check if it ends with an odd digit.
     * - Keep track of the maximum odd substring.
     *
     * Time Complexity: O(n^2)
     * - Generating and checking all substrings.
     * Space Complexity: O(1)
     * - Only tracking max string (not storing all substrings).
     */
    private static String largestOddNumberBruteForce(String num) {
        String maxOdd = "";

        for (int i = 0; i < num.length(); i++) {
            for (int j = i + 1; j <= num.length(); j++) {
                String sub = num.substring(i, j);
                char lastDigit = sub.charAt(sub.length() - 1);
                if ((lastDigit - '0') % 2 == 1) {
                    // If sub is longer or lexicographically greater
                    if (sub.length() > maxOdd.length() ||
                            (sub.length() == maxOdd.length() && sub.compareTo(maxOdd) > 0)) {
                        maxOdd = sub;
                    }
                }
            }
        }

        return maxOdd;
    }

    /**
     * ✅ Approach 3: Using Regular Expression (Alternative)
     *
     * ➤ Idea:
     * - Traverse from end to start, find last index where digit is odd.
     * - Then return substring up to that index.
     *
     * ➤ Note:
     * - More or less same as Approach 1 internally, just a different syntax or styling.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static String largestOddNumberRegexLike(String num) {
        int i = num.length() - 1;
        while (i >= 0 && (num.charAt(i) - '0') % 2 == 0) {
            i--;
        }
        return i >= 0 ? num.substring(0, i + 1) : "";
    }

    public static void main(String[] args) {
        String input1 = "52";
        String input2 = "4206";
        String input3 = "35427";

        System.out.println("Efficient Approach:");
        System.out.println("Input: 52     → " + largestOddNumber(input1));      // "5"
        System.out.println("Input: 4206   → " + largestOddNumber(input2));      // ""
        System.out.println("Input: 35427  → " + largestOddNumber(input3));      // "35427"

        System.out.println("\nBrute Force Approach:");
        System.out.println("Input: 52     → " + largestOddNumberBruteForce(input1));
        System.out.println("Input: 4206   → " + largestOddNumberBruteForce(input2));
        System.out.println("Input: 35427  → " + largestOddNumberBruteForce(input3));

        System.out.println("\nRegex-like Scan:");
        System.out.println("Input: 35427  → " + largestOddNumberRegexLike(input3));
    }
}

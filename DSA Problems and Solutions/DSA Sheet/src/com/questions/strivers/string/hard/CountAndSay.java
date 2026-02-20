package com.questions.strivers.string.hard;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 38. Count and Say (Medium)
 * ==================================================================================================
 * Generate the nth string in the sequence where each string describes the previous one.
 * * Logic:
 * 1. Start with the base case "1".
 * 2. For each step up to n, perform Run-Length Encoding.
 * 3. Use a StringBuilder for efficiency when concatenating counts and digits.
 * ==================================================================================================
 */
public class CountAndSay {

    public static void main(String[] args) {
        int n = 4;
        System.out.println("n = 1: " + countAndSay(1)); // 1
        System.out.println("n = 2: " + countAndSay(2)); // 11
        System.out.println("n = 3: " + countAndSay(3)); // 21
        System.out.println("n = 4: " + countAndSay(4)); // 1211
        System.out.println("n = 5: " + countAndSay(5)); // 111221
    }

    /**
     * ITERATIVE APPROACH
     * Time Complexity: O(2^n) - Growth is roughly exponential.
     * Space Complexity: O(2^n) - To store the resulting string.
     */
    public static String countAndSay(int n) {
        if (n <= 0) return "";

        // Base case: n=1 is always "1"
        String result = "1";

        // Loop to generate terms from 2 to n
        for (int i = 2; i <= n; i++) {
            StringBuilder nextTerm = new StringBuilder();
            int len = result.length();

            // Traverse the current string to encode it
            for (int j = 0; j < len; j++) {
                int count = 1;

                // Count consecutive occurrences of the same character
                while (j + 1 < len && result.charAt(j) == result.charAt(j + 1)) {
                    count++;
                    j++;
                }

                // Append the count and the digit itself
                nextTerm.append(count);
                nextTerm.append(result.charAt(j));
            }

            // Update the result for the next iteration
            result = nextTerm.toString();
        }

        return result;
    }
}
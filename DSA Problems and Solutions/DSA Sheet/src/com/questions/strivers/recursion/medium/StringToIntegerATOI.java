package com.questions.strivers.recursion.medium;

/*
Problem: Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.

Algorithm Steps:
1. Ignore leading whitespaces.
2. Detect the sign (+ or -).
3. Convert consecutive digit characters to an integer.
4. Stop conversion when a non-digit character is encountered.
5. Handle overflow/underflow and clamp the result to [-2^31, 2^31 - 1].

Constraints:
- Input string length <= 200
- Characters may include spaces, digits, letters, '+', '-', and '.'
*/

public class StringToIntegerATOI {
    public static int myAtoi(String s) {
        // Step 1: Handle null or empty string
        if (s == null || s.length() == 0) {
            return 0;
        }

        int i = 0; // Pointer to traverse the string
        int n = s.length();

        // Step 2: Skip leading whitespaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // If string contains only spaces
        if (i == n) {
            return 0;
        }

        // Step 3: Detect the sign
        int sign = 1; // Default positive
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        // Step 4: Convert digits to integer
        long result = 0; // Use long to handle overflow during conversion
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0'; // Convert char -> int

            result = result * 10 + digit;

            // Step 5: Check overflow and clamp within 32-bit signed int range
            if (sign == 1 && result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE; // Clamp to 2^31 - 1
            }
            if (sign == -1 && -result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE; // Clamp to -2^31
            }

            i++;
        }

        // Step 6: Apply sign and return
        return (int) (sign * result);
    }

    // Example test cases
    public static void main(String[] args) {
        System.out.println(myAtoi("42"));        // Output: 42
        System.out.println(myAtoi("   -042"));   // Output: -42
        System.out.println(myAtoi("1337c0d3"));  // Output: 1337
        System.out.println(myAtoi("0-1"));       // Output: 0
        System.out.println(myAtoi("words 987")); // Output: 0
        System.out.println(myAtoi("91283472332"));// Output: 2147483647 (clamped)
        System.out.println(myAtoi("-91283472332"));// Output: -2147483648 (clamped)
    }
}

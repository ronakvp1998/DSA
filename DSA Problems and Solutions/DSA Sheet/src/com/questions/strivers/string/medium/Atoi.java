package com.questions.strivers.string.medium;

/**
 * ==================================================================================================
 * APPROACH: Single Pass with Overflow Check
 * ==================================================================================================
 * 1. Clean the input: Skip leading spaces.
 * 2. Identify sign: Handle '+' or '-' only at the start.
 * 3. Build number: Process digits and check for Integer.MAX_VALUE / MIN_VALUE.
 * ==================================================================================================
 */
public class Atoi {

    public static int myAtoi(String s) {
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int i = 0;
        int sign = 1;
        int result = 0;

        // Step 1: Skip leading whitespaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // Step 2: Handle sign
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        // Step 3: Convert digits and handle overflow
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';

            // Check for overflow before multiplying by 10
            // Integer.MAX_VALUE is 2147483647
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            i++;
        }

        return result * sign;
    }

    public static void main(String[] args) {
        System.out.println("Input: '42'        -> Output: " + myAtoi("42"));
        System.out.println("Input: '  -042'    -> Output: " + myAtoi("  -042"));
        System.out.println("Input: '1337c0d3'  -> Output: " + myAtoi("1337c0d3"));
        System.out.println("Input: '2147483648'-> Output: " + myAtoi("2147483648")); // Overflow
    }
}
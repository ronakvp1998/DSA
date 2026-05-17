package com.questions.strivers.bitmanipulation.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 29. Divide Two Integers (Medium)
 * ==================================================================================================
 * Divide two integers without using multiplication, division, and the mod operator.
 * Truncate the result toward zero.
 * * Note: 32-bit signed integer range: [−2^31, 2^31 − 1].
 * Handle overflow: if quotient > 2^31 - 1, return 2^31 - 1. If < -2^31, return -2^31.
 *
 * Example 1:
 * Input: dividend = 10, divisor = 3 -> Output: 3
 * * Example 2:
 * Input: dividend = 7, divisor = -3 -> Output: -2
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Repeated Subtraction)
 * ==================================================================================================
 * We can keep subtracting the divisor from the dividend as long as the dividend remains
 * greater than or equal to the divisor. The number of times we subtract is the quotient.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation / Exponential Subtraction)
 * ==================================================================================================
 * Repeated subtraction is too slow (O(Dividend)). Instead, we can use bit shifting
 * (Left Shift <<) to find the largest multiple of the divisor (divisor * 2^k) that fits into
 * the current dividend.
 * This is effectively "binary division" which significantly reduces the number of operations.
 * ==================================================================================================
 */

public class DivideTwoIntegers {

    public static void main(String[] args) {
        // Test Case 1
        System.out.println("Test Case 1 (10 / 3):");
        System.out.println("Brute Force : " + divideBruteForce(10, 3));
        System.out.println("Optimal     : " + divideOptimal(10, 3));
        System.out.println("--------------------------------------------------");

        // Test Case 2 (Overflow Case)
        System.out.println("Test Case 2 (-2147483648 / -1):");
        System.out.println("Optimal     : " + divideOptimal(Integer.MIN_VALUE, -1));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Linear Subtraction)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Determine the sign of the result.
     * 2. Use absolute values for both numbers (Long to handle overflow).
     * 3. Subtract divisor from dividend in a loop, incrementing a counter.
     */
    public static int divideBruteForce(int dividend, int divisor) {
        // Handle overflow edge case
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;

        // Determine sign
        boolean isNegative = (dividend < 0) ^ (divisor < 0);

        // Convert to absolute long to handle Integer.MIN_VALUE safely
        long n = Math.abs((long) dividend);
        long d = Math.abs((long) divisor);

        int quotient = 0;
        while (n >= d) {
            n -= d;
            quotient++;
        }

        return isNegative ? -quotient : quotient;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. We treat the division like building a binary number.
     * 2. For the current dividend, we find the highest power of 2 (k) such that:
     * divisor * 2^k <= dividend.
     * 3. We subtract (divisor << k) from the dividend and add (1 << k) to the result.
     * 4. Repeat until the dividend is smaller than the divisor.
     */
    public static int divideOptimal(int dividend, int divisor) {
        // Handle overflow edge case
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Determine the sign of the result using XOR
        boolean isNegative = (dividend < 0) ^ (divisor < 0);

        // Use long to prevent overflow during intermediate calculations
        long n = Math.abs((long) dividend);
        long d = Math.abs((long) divisor);

        long quotient = 0;

        // Outer loop: continue as long as dividend is larger than divisor
        while (n >= d) {
            int k = 0;
            // Inner loop: find the largest power of 2 shift
            // d << (k + 1) is equivalent to d * 2^(k+1)
            while (n >= (d << (k + 1))) {
                k++;
            }

            // Add the power of 2 to our quotient
            quotient += (1L << k);
            // Remove the chunk we just calculated from the dividend
            n -= (d << k);
        }

        // Apply sign and cast back to int
        return isNegative ? (int) -quotient : (int) quotient;
    }
}
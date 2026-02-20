package com.questions.strivers.bitmanipulation.advancemaths;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 50. Pow(x, n) (Medium)
 * ==================================================================================================
 * Implement pow(x, n), which calculates x raised to the power n (i.e., x^n).
 * * Example 1:
 * Input: x = 2.00000, n = 10 -> Output: 1024.00000
 * * Example 3:
 * Input: x = 2.00000, n = -2 -> Output: 0.25000 (1 / 2^2)
 * * Constraints:
 * -2^31 <= n <= 2^31 - 1 (Note: Integer.MIN_VALUE requires special handling)
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Linear Multiplication)
 * ==================================================================================================
 * Multiply 'x' by itself 'n' times.
 * If 'n' is negative, we calculate x^|n| and then take the reciprocal (1 / ans).
 * Drawback: O(n) time complexity. For n = 2 billion, this is too slow.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Binary Exponentiation / Bit Manipulation)
 * ==================================================================================================
 * We can use the property of exponents:
 * - If n is even: x^n = (x * x)^(n/2)
 * - If n is odd:  x^n = x * (x^n-1)
 * * This is effectively looking at the binary representation of 'n'.
 * For example, x^13 (13 is 1101 in binary) = x^8 * x^4 * x^1.
 * We can iterate through the bits of 'n'. If the bit is 1, multiply the result by the current x.
 * Every step, square x (x = x * x) and shift 'n' to the right.
 * ==================================================================================================
 */

public class PowerXN {

    public static void main(String[] args) {
        // Test Case 1: Positive power
        System.out.println("Test Case 1 (2.0, 10): " + myPowOptimal(2.0, 10));

        // Test Case 2: Negative power
        System.out.println("Test Case 2 (2.0, -2): " + myPowOptimal(2.0, -2));

        // Test Case 3: Large negative power (Edge Case)
        System.out.println("Test Case 3 (1.0, -2147483648): " + myPowOptimal(1.0, -2147483648));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Linear)
     * ----------------------------------------------------------------------
     */
    public static double myPowBruteForce(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        double ans = 1;
        for (long i = 0; i < N; i++) {
            ans = ans * x;
        }
        return ans;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Binary Exponentiation)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Use a 'long' for n to handle the overflow when n = -2147483648.
     * 2. If N is negative, transform x to 1/x and N to positive.
     * 3. Use bitwise logic to check if the current LSB of N is 1.
     * 4. Square the base x at each step and halve N.
     */
    public static double myPowOptimal(double x, int n) {
        // Handle n = Integer.MIN_VALUE by converting to long
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double ans = 1.0;
        double currentProduct = x;

        // Binary Exponentiation Loop
        while (N > 0) {
            // If the current bit is 1 (N is odd), include currentProduct in result
            if ((N & 1) == 1) {
                ans = ans * currentProduct;
            }

            // Square the base
            currentProduct = currentProduct * currentProduct;

            // Move to the next bit (divide N by 2)
            N >>= 1;
        }

        return ans;
    }
}
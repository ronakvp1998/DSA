package com.questions.strivers.recursionbacktracking.medium;

public class PowXN {

    /*
     Problem Statement:
     Implement pow(x, n), which calculates x raised to the power n (x^n).

     Example:
        Input: x = 2.00000, n = 10
        Output: 1024.00000

        Input: x = 2.10000, n = 3
        Output: 9.26100

        Input: x = 2.00000, n = -2
        Output: 0.25000  (Explanation: 2^-2 = 1 / (2^2) = 0.25)

     Constraints:
        -100.0 < x < 100.0
        -2^31 <= n <= 2^31 - 1
        n is an integer.
        Either x != 0 or n > 0.
        -10^4 <= x^n <= 10^4
     */

    // Main function to calculate x^n
    public double myPow(double x, int n) {
        // Convert 'n' to long because |n| can be up to 2^31, which overflows int
        long N = n;

        // If n is negative, compute positive power and take reciprocal
        if (N < 0) {
            x = 1 / x;    // reciprocal of base
            N = -N;       // make exponent positive
        }

        return fastPower(x, N);
    }

    // Helper function: Fast Exponentiation using Binary Exponentiation (Iterative)
    private double fastPower(double x, long n) {
        double result = 1.0;

        // Loop until all powers are processed
        while (n > 0) {
            // If n is odd, multiply result by current x
            if ((n % 2) == 1) {
                result *= x;
            }

            // Square the base for the next step
            x *= x;

            // Divide exponent by 2 (integer division)
            n /= 2;
        }

        return result;
    }

    // Driver code to test the implementation
    public static void main(String[] args) {
        PowXN pow = new PowXN();

        System.out.println(pow.myPow(2.00000, 10));   // Output: 1024.0
        System.out.println(pow.myPow(2.10000, 3));    // Output: 9.261
        System.out.println(pow.myPow(2.00000, -2));   // Output: 0.25
    }
}

/*
Time Complexity
Each step halves n → O(log n)
Much faster than O(n).

💾 Space Complexity
Iterative approach → O(1) (constant space).
If we used recursion, it would take O(log n) recursion stack space.
 */
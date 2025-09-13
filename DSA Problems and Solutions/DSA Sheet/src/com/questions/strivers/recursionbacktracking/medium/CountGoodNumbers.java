package com.questions.strivers.recursionbacktracking.medium;
/*
1922. Count Good Numbers

A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime
(2, 3, 5, or 7).

For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2)
at odd positions are prime. However, "3245" is not good because 3 is at an even index but is not even.
Given an integer n, return the total number of good digit strings of length n. Since the answer may be large,
return it modulo 109 + 7.
A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.

Example 1:

Input: n = 1
Output: 5
Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
Example 2:

Input: n = 4
Output: 400
Example 3:

Input: n = 50
Output: 564908303

Constraints:
1 <= n <= 1015
 */
public class CountGoodNumbers {

    // Define modulo constant (10^9 + 7)
    private static final long MOD = 1000000007;

    /*
     * Function to count the number of good digit strings of length n.
     *
     * Approach:
     * - Even positions -> 5 choices (0,2,4,6,8)
     * - Odd positions  -> 4 choices (2,3,5,7)
     * - Total = (5 ^ countEvenPositions) * (4 ^ countOddPositions) % MOD
     *
     * We use fast exponentiation (binary exponentiation) to handle very large n (up to 10^15).
     */
    public int countGoodNumbers(long n) {
        // Count of even and odd indexed positions
        long countEvenPositions = (n + 1) / 2;
        long countOddPositions = n / 2;

        // Calculate power using modular exponentiation
        long powerOf5 = power(5, countEvenPositions);
        long powerOf4 = power(4, countOddPositions);

        // Multiply and take modulo
        return (int) ((powerOf5 * powerOf4) % MOD);
    }

    /*
     * Helper function: Fast exponentiation (Binary Exponentiation)
     * Computes (base ^ exp) % MOD efficiently in O(log exp).
     */
    private long power(long base, long exp) {
        long result = 1;
        base %= MOD; // Reduce base modulo first

        while (exp > 0) {
            // If exponent is odd, multiply result with current base
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }

            // Square the base and reduce exponent by half
            base = (base * base) % MOD;
            exp >>= 1; // Divide exponent by 2
        }

        return result;
    }

    // Driver code for testing
    public static void main(String[] args) {
        CountGoodNumbers solution = new CountGoodNumbers();

        System.out.println(solution.countGoodNumbers(1));   // Output: 5
        System.out.println(solution.countGoodNumbers(4));   // Output: 400
        System.out.println(solution.countGoodNumbers(50));  // Output: 564908303
    }
}
/*
⏱️ Time Complexity

power(base, exp) runs in O(log exp).

We call it twice → O(log n) overall.

💾 Space Complexity

Iterative fast exponentiation uses O(1) extra space.
 */
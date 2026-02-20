package com.questions.strivers.bitmanipulation.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Check if a number is odd or not
 * ==================================================================================================
 * Given a non-negative integer n, determine whether it is odd.
 * Return true if the number is odd, otherwise return false.
 * A number is odd if it is not divisible by 2 (i.e., n % 2 != 0).
 *
 * Example 1:
 * Input: n = 7
 * Output: true
 * Explanation: 7 is not divisible by 2. Hence, it is odd.
 *
 * Example 2:
 * Input: n = 10
 * Output: false
 * Explanation: 10 is divisible by 2. Hence, it is not odd (it is even).
 * * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Modulo Operator)
 * ==================================================================================================
 * The standard mathematical definition of an odd number is a number that leaves a remainder
 * of 1 when divided by 2. We can use the modulo operator (%) to check this remainder.
 * * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation)
 * ==================================================================================================
 * In binary representation, all odd numbers end with a '1' in their Least Significant Bit (LSB),
 * and all even numbers end with a '0'.
 * By performing a Bitwise AND (&) between the number and 1, we isolate this last bit.
 * If the result is 1, the number is odd. If the result is 0, the number is even.
 * ==================================================================================================
 */

public class CheckOddNumber {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int n1 = 7;  // Binary: 0111
        System.out.println("Test Case 1 (n = 7):");
        System.out.println("Brute Force : " + isOddBruteForce(n1));
        System.out.println("Optimal     : " + isOddOptimal(n1));
        System.out.println("--------------------------------------------------");

        int n2 = 10; // Binary: 1010
        System.out.println("Test Case 2 (n = 10):");
        System.out.println("Brute Force : " + isOddBruteForce(n2));
        System.out.println("Optimal     : " + isOddOptimal(n2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Modulo Operator)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Divide the number 'n' by 2 and check the remainder.
     * 2. If the remainder is not equal to 0, it means it cannot be divided
     * evenly by 2, making it an odd number.
     * * Edge Cases Handled:
     * - Using `!= 0` instead of `== 1` safely handles negative odd numbers
     * in Java, as `-7 % 2` results in `-1`, not `1`. (Though the problem
     * states non-negative, it's a good habit).
     */
    public static boolean isOddBruteForce(int n) {
        // Return true if the remainder of n divided by 2 is not zero
        return n % 2 != 0;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. The binary representation of 1 is just... 1 (with leading zeros).
     * 2. When we do `n & 1`, the Bitwise AND operator compares every bit.
     * 3. Because '1' has zeros everywhere except the 0-th position, everything
     * but the 0-th bit of 'n' gets masked out (turned to 0).
     * 4. Therefore, `n & 1` strictly evaluates to whatever the last bit of 'n' is.
     */
    public static boolean isOddOptimal(int n) {
        // If the least significant bit is 1, the bitwise AND will evaluate to 1.
        return (n & 1) == 1;
    }
}
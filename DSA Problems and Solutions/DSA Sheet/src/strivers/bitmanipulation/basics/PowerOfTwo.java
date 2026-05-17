package com.questions.strivers.bitmanipulation.basics;
/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 231. Power of Two (Easy)
 * ==================================================================================================
 * Given an integer n, return true if it is a power of two. Otherwise, return false.
 * An integer n is a power of two, if there exists an integer x such that n == 2^x.
 *
 * Example 1:
 * Input: n = 1
 * Output: true
 * Explanation: 2^0 = 1
 *
 * Example 2:
 * Input: n = 16
 * Output: true
 * Explanation: 2^4 = 16
 *
 * Example 3:
 * Input: n = 3
 * Output: false
 * * Constraints:
 * -2^31 <= n <= 2^31 - 1
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Repeated Division)
 * ==================================================================================================
 * A power of two can be continuously divided by 2 without ever leaving a remainder,
 * eventually reducing down to exactly 1. If at any point the number becomes odd
 * (before reaching 1), it is not a power of two.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation)
 * ==================================================================================================
 * In binary representation, every power of two has exactly ONE bit set to '1',
 * and all other bits are '0'. (e.g., 2 is 10, 4 is 100, 8 is 1000).
 * If we subtract 1 from a power of two, all the trailing '0's become '1's,
 * and the single '1' becomes a '0' (e.g., 8 is 1000, 7 is 0111).
 * Performing a Bitwise AND (&) between n and (n - 1) will always result in 0
 * IF AND ONLY IF the number is a power of two.
 * ==================================================================================================
 */

public class PowerOfTwo {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int n1 = 1; // 2^0
        System.out.println("Test Case 1 (n = 1):");
        System.out.println("Brute Force : " + isPowerOfTwoBruteForce(n1));
        System.out.println("Optimal     : " + isPowerOfTwoOptimal(n1));
        System.out.println("--------------------------------------------------");

        int n2 = 16; // 2^4
        System.out.println("Test Case 2 (n = 16):");
        System.out.println("Brute Force : " + isPowerOfTwoBruteForce(n2));
        System.out.println("Optimal     : " + isPowerOfTwoOptimal(n2));
        System.out.println("--------------------------------------------------");

        int n3 = 3; // Not a power of two
        System.out.println("Test Case 3 (n = 3):");
        System.out.println("Brute Force : " + isPowerOfTwoBruteForce(n3));
        System.out.println("Optimal     : " + isPowerOfTwoOptimal(n3));
        System.out.println("--------------------------------------------------");

        int n4 = 0; // Edge case
        System.out.println("Test Case 4 (n = 0):");
        System.out.println("Brute Force : " + isPowerOfTwoBruteForce(n4));
        System.out.println("Optimal     : " + isPowerOfTwoOptimal(n4));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Repeated Division)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Check for the base failure cases: powers of two must be strictly greater than 0.
     * 2. Loop continuously as long as the number is perfectly divisible by 2 (n % 2 == 0).
     * 3. Divide the number by 2 in each iteration.
     * 4. Once the loop breaks (because the number is no longer even), check if it equals 1.
     * If it is 1, it was a perfect power of two. If it is anything else (e.g., 3), it wasn't.
     */
    public static boolean isPowerOfTwoBruteForce(int n) {
        // Edge case: 0 and negative numbers cannot be powers of two
        if (n <= 0) {
            return false;
        }

        // Keep dividing by 2 as long as there is no remainder
        while (n % 2 == 0) {
            n = n / 2;
        }

        // If we successfully reduced the number down to exactly 1, it's a power of two.
        return n == 1;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation using n & (n - 1))
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Check if n > 0, because 0 and negative numbers are invalid.
     * 2. Use the famous bit trick: n & (n - 1)
     * 3. If n is a power of two, it has only one '1' bit.
     * n - 1 flips that '1' to '0' and all trailing '0's to '1's.
     * 4. Therefore, n & (n - 1) will leave absolutely no shared '1' bits, resulting in 0.
     */
    public static boolean isPowerOfTwoOptimal(int n) {
        // A power of two must be positive.
        // We then apply the bitwise AND trick to clear the lowest set bit.
        // If the result is 0, the number had exactly one set bit.
        return (n > 0) && ((n & (n - 1)) == 0);
    }
}
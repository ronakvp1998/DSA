package com.questions.strivers.bitmanipulation.basics;
/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Set the rightmost unset bit
 * ==================================================================================================
 * Given a positive integer n, set the rightmost unset (0) bit of its binary representation to 1
 * and return the resulting integer.
 * If all bits are already set (from the most significant bit down to the LSB), return the number as it is.
 *
 * Example 1:
 * Input: n = 10 (binary: 1010)
 * Output: 11 (binary: 1011)
 * Explanation: The rightmost unset bit is the least significant bit (LSB).
 * Setting it to 1 gives 1011 = 11.
 *
 * Example 2:
 * Input: n = 7 (binary: 111)
 * Output: 7 (binary: 111)
 * Explanation: All bits are already set to 1, so the number remains the same.
 * * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Bit-by-Bit Traversal)
 * ==================================================================================================
 * We can iterate through the binary representation of the number starting from the 0-th bit
 * (Least Significant Bit) and moving leftwards. The moment we find a bit that is `0`,
 * we set it to `1` using the bitwise OR (`|`) operator and return the result.
 * If we reach the end of the number's significant bits without finding a `0`, we return it as is.
 * * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation)
 * ==================================================================================================
 * Adding 1 to a binary number naturally flips the rightmost `0` to a `1` and turns all
 * trailing `1`s into `0`s (e.g., 1011 + 1 = 1100).
 * By performing a Bitwise OR (`|`) between `n` and `n + 1`, we preserve all original `1`s
 * from `n` while absorbing the newly flipped `1` from `n + 1`.
 * Edge Case: If the number is entirely made of `1`s (e.g., 7 is 111), `n + 1` will create a new
 * most significant bit (8 is 1000). We must handle this specifically using `n & (n + 1)`.
 * ==================================================================================================
 */

public class SetRightmostUnsetBit {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int n1 = 10; // Binary: 1010
        System.out.println("Test Case 1 (n = 10):");
        System.out.println("Brute Force : " + setBitBruteForce(n1));
        System.out.println("Optimal     : " + setBitOptimal(n1));
        System.out.println("--------------------------------------------------");

        int n2 = 7; // Binary: 111 (All bits are 1)
        System.out.println("Test Case 2 (n = 7):");
        System.out.println("Brute Force : " + setBitBruteForce(n2));
        System.out.println("Optimal     : " + setBitOptimal(n2));
        System.out.println("--------------------------------------------------");

        int n3 = 11; // Binary: 1011 (Rightmost 0 is the 4's place -> 1111 = 15)
        System.out.println("Test Case 3 (n = 11):");
        System.out.println("Brute Force : " + setBitBruteForce(n3));
        System.out.println("Optimal     : " + setBitOptimal(n3));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Bit-by-Bit Traversal)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Create a variable `temp` initialized to `n` to help us count how many
     * significant bits the number has.
     * 2. Use a `bitIndex` to keep track of the current position we are checking.
     * 3. Loop as long as `temp > 0`. In each iteration, check if the bit at `bitIndex`
     * in `n` is 0 using `(n & (1 << bitIndex)) == 0`.
     * 4. If it is 0, we found our target! Set it to 1 using `n | (1 << bitIndex)` and return.
     * 5. If we exit the loop, it means there were no 0s among the significant bits (like 7).
     * We simply return the original `n`.
     */
    public static int setBitBruteForce(int n) {
        int temp = n;
        int bitIndex = 0;

        // Loop through the actual length of the binary number
        while (temp > 0) {

            // If the bit at the current index is 0
            if ((n & (1 << bitIndex)) == 0) {
                // Set the bit to 1 using Bitwise OR and return
                return n | (1 << bitIndex);
            }

            // Shift temp right to process the next bit, and increment our index
            temp >>= 1;
            bitIndex++;
        }

        // If we reach here, all significant bits were 1.
        return n;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. First, check if the number is already composed entirely of 1s (e.g., 3, 7, 15).
     * We know from previous problems that if `n & (n + 1) == 0`, the number has no
     * internal 0s (it is of the form 2^k - 1). If so, the problem dictates we return `n`.
     * 2. If it has internal unset bits, we simply return `n | (n + 1)`.
     * - `n + 1` mathematically flips the rightmost 0 to a 1, and turns all 1s to its right into 0s.
     * - ORing `n` with `n + 1` restores those trailing 1s from the original `n`,
     * leaving us with the exact original number PLUS the newly set rightmost bit!
     */
    public static int setBitOptimal(int n) {
        // Edge Case: If all bits are already 1 (e.g., 7 is 0111, 7+1 is 1000. 7 & 8 == 0)
        if ((n & (n + 1)) == 0) {
            return n;
        }

        // Main Logic: ORing 'n' with 'n + 1' sets the rightmost unset bit.
        return n | (n + 1);
    }
}
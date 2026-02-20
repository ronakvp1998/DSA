package com.questions.strivers.bitmanipulation.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Check if the i-th bit is set or not
 * ==================================================================================================
 * Given two integers n and i, return true if the ith bit in the binary representation of n
 * (counting from the least significant bit, 0-indexed) is set (i.e., equal to 1).
 * Otherwise, return false.
 *
 * Example 1:
 * Input: n = 5, i = 0
 * Output: true
 * Explanation: Binary representation of 5 is 101. The 0-th bit from LSB is set (1).
 *
 * Example 2:
 * Input: n = 10, i = 1
 * Output: true
 * Explanation: Binary representation of 10 is 1010. The 1-st bit from LSB is set (1).
 * * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (String Conversion)
 * ==================================================================================================
 * Convert the integer into its binary string representation. Once we have the string,
 * we can check the character at the specific index corresponding to the i-th bit.
 * * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bit Manipulation using a Mask)
 * ==================================================================================================
 * We can use a bitwise left shift (<<) to create a "mask".
 * Shifting the number '1' to the left by 'i' positions creates a binary number where ONLY
 * the i-th bit is 1, and all other bits are 0.
 * We then perform a Bitwise AND (&) between 'n' and this mask.
 * If the i-th bit in 'n' is 1, the result will be non-zero. If it is 0, the result will be 0.
 * ==================================================================================================
 */

public class CheckIthBit {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int n1 = 5;  // Binary: 101
        int i1 = 0;
        System.out.println("Test Case 1 (n=5, i=0):");
        System.out.println("Brute Force : " + isIthBitSetBruteForce(n1, i1));
        System.out.println("Optimal     : " + isIthBitSetOptimal(n1, i1));
        System.out.println("--------------------------------------------------");

        int n2 = 10; // Binary: 1010
        int i2 = 1;
        System.out.println("Test Case 2 (n=10, i=1):");
        System.out.println("Brute Force : " + isIthBitSetBruteForce(n2, i2));
        System.out.println("Optimal     : " + isIthBitSetOptimal(n2, i2));
        System.out.println("--------------------------------------------------");

        int n3 = 8;  // Binary: 1000
        int i3 = 2;
        System.out.println("Test Case 3 (n=8, i=2):");
        System.out.println("Brute Force : " + isIthBitSetBruteForce(n3, i3));
        System.out.println("Optimal     : " + isIthBitSetOptimal(n3, i3));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (String Conversion)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Convert the number 'n' to a binary string.
     * 2. Calculate the actual string index. Since the 0-th bit is the Least
     * Significant Bit (rightmost), its index in a standard string is
     * (length - 1 - i).
     * 3. Check if the character at that index is '1'.
     * * Edge Cases Handled:
     * - If 'i' is larger than or equal to the length of the binary string,
     * it implies those leading bits are naturally '0' (for positive numbers).
     */
    public static boolean isIthBitSetBruteForce(int n, int i) {
        // Convert integer to binary string (e.g., 5 becomes "101")
        String binaryString = Integer.toBinaryString(n);

        int length = binaryString.length();

        // If the requested bit 'i' is beyond the length of our string,
        // it means that bit is a leading zero. We safely return false.
        if (i >= length) {
            return false;
        }

        // Calculate the string index.
        // Example: For "101" (length=3), the 0-th bit from LSB is at index 2 (3 - 1 - 0).
        int charIndex = length - 1 - i;

        // Return true if the character is '1', false otherwise
        return binaryString.charAt(charIndex) == '1';
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Bit Manipulation)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Create a mask by shifting the integer 1 to the left by 'i' places.
     * (e.g., if i=2, 1 << 2 results in binary 100)
     * 2. Use Bitwise AND (&) between 'n' and the mask.
     * 3. Because the mask only has a 1 at the i-th position, the result of
     * the AND operation will be 0 if the i-th bit in 'n' is 0.
     * If the i-th bit in 'n' is 1, the result will be non-zero (specifically, 2^i).
     */
    public static boolean isIthBitSetOptimal(int n, int i) {
        // Create the mask. Shifting 1 left by i positions places a 1 exactly at the i-th bit.
        int mask = 1 << i;

        // Perform bitwise AND.
        // If the result is NOT 0, it means both 'n' and 'mask' had a 1 at the i-th position.
        return (n & mask) != 0;
    }
}
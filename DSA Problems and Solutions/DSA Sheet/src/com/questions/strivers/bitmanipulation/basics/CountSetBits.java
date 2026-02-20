package com.questions.strivers.bitmanipulation.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Count the number of set bits
 * ==================================================================================================
 * Given an integer n, return the number of set bits (1s) in its binary representation.
 * Can you solve it in O(log n) time complexity?
 *
 * Example 1:
 * Input: n = 5
 * Output: 2
 * Explanation: The binary representation of 5 is 101, which has 2 set bits.
 *
 * Example 2:
 * Input: n = 15
 * Output: 4
 * Explanation: The binary representation of 15 is 1111, which has 4 set bits.
 * * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Bit-by-Bit Shift)
 * ==================================================================================================
 * We can continuously check if the Least Significant Bit (LSB) is a 1.
 * If it is, we increment a counter. We then logically shift the number to the right by 1 position
 * to bring the next bit into the LSB position. We repeat this until the number becomes 0.
 * * ==================================================================================================
 * APPROACH 2: OPTIMAL (Brian Kernighan's Algorithm)
 * ==================================================================================================
 * Instead of checking every single bit (including the 0s), we can jump directly from one '1'
 * to the next '1'.
 * We learned previously that the operation (n & (n - 1)) clears the lowest set bit of n.
 * Therefore, we can simply apply this operation in a loop and count how many times we can
 * clear a bit before the number turns into 0.
 * ==================================================================================================
 */

public class CountSetBits {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int n1 = 5; // Binary: 101 (Two 1s)
        System.out.println("Test Case 1 (n = 5):");
        System.out.println("Brute Force : " + countSetBitsBruteForce(n1));
        System.out.println("Optimal     : " + countSetBitsOptimal(n1));
        System.out.println("--------------------------------------------------");

        int n2 = 15; // Binary: 1111 (Four 1s)
        System.out.println("Test Case 2 (n = 15):");
        System.out.println("Brute Force : " + countSetBitsBruteForce(n2));
        System.out.println("Optimal     : " + countSetBitsOptimal(n2));
        System.out.println("--------------------------------------------------");

        int n3 = -1; // Binary: 11111111111111111111111111111111 (Thirty-two 1s)
        System.out.println("Test Case 3 (n = -1):");
        System.out.println("Brute Force : " + countSetBitsBruteForce(n3));
        System.out.println("Optimal     : " + countSetBitsOptimal(n3));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Bit-by-Bit Shift)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Check the rightmost bit using (n & 1). If it is 1, add it to the count.
     * 2. Shift all bits to the right by 1 using the unsigned right shift operator (>>>).
     * 3. Repeat until 'n' becomes 0.
     * * Edge Cases Handled:
     * - We use the logical right shift (>>>) instead of arithmetic right shift (>>).
     * If n is negative, the highest bit is 1. Arithmetic shift (>>) preserves the sign
     * by filling the gap with 1s, causing an infinite loop. The logical shift (>>>)
     * safely fills the gap with 0s.
     */
    public static int countSetBitsBruteForce(int n) {
        int count = 0;

        while (n != 0) {
            // Add the lowest bit to count (1 if set, 0 if not)
            count += (n & 1);

            // Unsigned right shift to push the next bit into the lowest position
            n >>>= 1;
        }

        return count;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (Brian Kernighan's Algorithm)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. If 'n' is not 0, it has at least one set bit.
     * 2. We perform n = n & (n - 1). This mathematical trick perfectly removes the
     * rightmost '1' bit, turning it into a '0'.
     * 3. We increment our counter because we successfully deleted one '1'.
     * 4. Repeat until all '1' bits have been deleted (n == 0).
     */
    public static int countSetBitsOptimal(int n) {
        int count = 0;

        // Loop runs EXACTLY the number of times there are set bits
        while (n != 0) {
            // Drops the lowest set bit
            n = n & (n - 1);
            count++;
        }

        return count;
    }
}
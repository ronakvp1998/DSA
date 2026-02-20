package com.questions.strivers.bitmanipulation.interviewproblem;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 2220. Minimum Bit Flips to Convert Number (Easy)
 * ==================================================================================================
 * Given two integers start and goal, return the minimum number of bit flips to convert start to goal.
 * A bit flip is changing a 0 to a 1 or a 1 to a 0.
 *
 * Example 1:
 * Input: start = 10 (1010), goal = 7 (0111)
 * Output: 3
 * Explanation: 1010 -> 0111 requires 3 flips (at indices 0, 2, and 3).
 *
 * Example 2:
 * Input: start = 3 (011), goal = 4 (100)
 * Output: 3
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Bit-by-Bit Comparison)
 * ==================================================================================================
 * We can iterate through each bit position (0 to 31 for a standard integer). For each position,
 * we check if the bit in 'start' is different from the bit in 'goal'. If they are different,
 * it means a flip is required at that position.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (XOR + Brian Kernighan's Algorithm)
 * ==================================================================================================
 * 1. The XOR (^) operator is the perfect tool for finding differences.
 * start ^ goal results in a number where bits are '1' ONLY where start and goal differ.
 * 2. The problem then reduces to: "Count the number of set bits (1s) in the XOR result."
 * 3. We use Brian Kernighan's Algorithm (n & (n - 1)) to count the 1s efficiently.
 * ==================================================================================================
 */

public class MinBitFlips {

    public static void main(String[] args) {
        // Test Case 1
        int start1 = 10, goal1 = 7;
        System.out.println("Test Case 1 (10 -> 7):");
        System.out.println("Brute Force : " + minBitFlipsBruteForce(start1, goal1));
        System.out.println("Optimal     : " + minBitFlipsOptimal(start1, goal1));
        System.out.println("--------------------------------------------------");

        // Test Case 2
        int start2 = 3, goal2 = 4;
        System.out.println("Test Case 2 (3 -> 4):");
        System.out.println("Brute Force : " + minBitFlipsBruteForce(start2, goal2));
        System.out.println("Optimal     : " + minBitFlipsOptimal(start2, goal2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE (Bit-by-Bit Comparison)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Loop 32 times (for each bit in a 32-bit integer).
     * 2. Use (start & 1) and (goal & 1) to get the rightmost bit of each.
     * 3. If they are not equal, increment flips.
     * 4. Right shift both numbers to check the next bit.
     */
    public static int minBitFlipsBruteForce(int start, int goal) {
        int flips = 0;
        // Process until both numbers are 0
        while (start > 0 || goal > 0) {
            // Check if the LSB (Least Significant Bit) is different
            if ((start & 1) != (goal & 1)) {
                flips++;
            }
            // Move to the next bit
            start >>= 1;
            goal >>= 1;
        }
        return flips;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (XOR + Kernighan's)
     * ----------------------------------------------------------------------
     * Logic:
     * 1. XOR start and goal. Example: 10 (1010) ^ 7 (0111) = 13 (1101).
     * The '1's in 1101 represent the positions that need to be flipped.
     * 2. Count the 1s in the result.
     */
    public static int minBitFlipsOptimal(int start, int goal) {
        // Step 1: Find the difference map using XOR
        int xorResult = start ^ goal;
        int count = 0;

        // Step 2: Brian Kernighan's Algorithm to count set bits
        // This is faster than checking every bit because it only loops
        // as many times as there are 1s.
        while (xorResult != 0) {
            xorResult = xorResult & (xorResult - 1); // Clears the rightmost set bit
            count++;
        }

        return count;
    }
}
package com.questions.strivers.bitmanipulation.basics;

/**
 * ============================================================================
 * PROBLEM: Set i-th Bit and Clear i-th Bit
 * ============================================================================
 *
 * HEADER & PROBLEM CONTEXT
 * ------------------------
 * Problem Statement:
 * Given a number N and a 0-based index i, perform two operations:
 * 1. Set the i-th bit of N (turn it to 1).
 * 2. Clear the i-th bit of N (turn it to 0).
 * Return the modified numbers. The bits are indexed from right to left,
 * starting at 0 for the Least Significant Bit (LSB).
 *
 * Constraints:
 * - 0 <= N <= 2^31 - 1
 * - 0 <= i <= 30
 *
 * Input/Output Format:
 * - Input: An integer N, and an integer i representing the bit index.
 * - Output: The integer after setting the i-th bit, and the integer after
 * clearing the i-th bit.
 *
 * Examples:
 * Example 1:
 * Input: N = 10 (binary: 1010), i = 2
 * Output for Set: 14 (binary: 1110)
 * Output for Clear: 10 (binary: 1010) - 2nd bit is already 0, remains unchanged.
 *
 * Example 2:
 * Input: N = 13 (binary: 1101), i = 2
 * Output for Set: 13 (binary: 1101) - 2nd bit is already 1, remains unchanged.
 * Output for Clear: 9 (binary: 1001)
 *
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ----------------------------------
 * Phase 1: Brute Force Approach (String Manipulation) - The "Think it" stage.
 * - Approach: Convert the number to a physical 32-bit binary string,
 * manipulate the character at the specific index corresponding to the
 * i-th bit, and parse the string back into a base-10 integer.
 *
 * Phase 2: Optimal Approach (Bit Manipulation) - The "Perfect it" stage.
 * - Approach: Use hardware-level bitwise operators (Shift, OR, AND, NOT)
 * to manipulate the bits directly in memory. This completely bypasses
 * expensive string allocations and conversions.
 * ============================================================================
 */
public class SetClearIthBit {

    // ========================================================================
    // PHASE 1: BRUTE FORCE APPROACH (String Manipulation)
    // ========================================================================

    /**
     * Phase 1.1: Brute Force - Set i-th Bit
     * * Detailed Intuition:
     * When humans think of "changing a bit", we visualize writing out the 1s
     * and 0s on paper, erasing the i-th digit from the right, and writing a '1'.
     * We can simulate this literally by converting the integer to a 32-character
     * string array. Since strings are read left-to-right (0 to 31), but bits
     * are counted right-to-left, the i-th bit is located at index (31 - i).
     *
     * Complexity Analysis:
     * - Time Complexity: O(1). Even though we are doing string operations,
     * the size of the string is strictly bounded to 32 characters regardless
     * of the input size N. However, the constant factor is very high compared
     * to raw bitwise operations.
     * - Space Complexity: O(1) Heap Space. We allocate a fixed-size 32-character
     * string/array. Auxiliary stack space is O(1).
     */
    public static int setIthBitBruteForce(int n, int i) {
        // Convert to 32-bit binary string (padding with leading zeros)
        String binaryStr = String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
        char[] bits = binaryStr.toCharArray();

        // Calculate index from the left
        int targetIndex = 31 - i;

        // Set the bit to '1'
        if (targetIndex >= 0 && targetIndex < 32) {
            bits[targetIndex] = '1';
        }

        // Parse back to integer (using UnsignedInt to handle potential 31st bit overflow)
        return Integer.parseUnsignedInt(new String(bits), 2);
    }

    /**
     * Phase 1.2: Brute Force - Clear i-th Bit
     * * Detailed Intuition:
     * Exactly identical to the set logic, but instead of writing a '1' at the
     * target index (31 - i), we write a '0'.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1). Bounded by 32 operations.
     * - Space Complexity: O(1) Heap Space. Fixed size string allocations.
     */
    public static int clearIthBitBruteForce(int n, int i) {
        String binaryStr = String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
        char[] bits = binaryStr.toCharArray();

        int targetIndex = 31 - i;

        // Clear the bit to '0'
        if (targetIndex >= 0 && targetIndex < 32) {
            bits[targetIndex] = '0';
        }

        return Integer.parseUnsignedInt(new String(bits), 2);
    }

    // ========================================================================
    // PHASE 2: OPTIMAL APPROACH (Bit Manipulation)
    // ========================================================================

    /**
     * Phase 2.1: Optimal - Set i-th Bit
     * * Detailed Intuition:
     * String manipulation is incredibly slow. Instead, we use bitwise OR (`|`).
     * The OR operator returns 1 if *either* bit is 1.
     * We create a "mask" by taking the number 1 (which is just a single 1 at
     * the 0th position) and shifting it left by `i` positions (`1 << i`).
     * This creates a number where ONLY the i-th bit is 1, and all others are 0.
     * When we OR this mask with N:
     * - N's original bits OR'd with 0 remain unchanged (x | 0 = x).
     * - N's i-th bit OR'd with 1 becomes 1 (x | 1 = 1).
     *
     * Complexity Analysis:
     * - Time Complexity: O(1). Bitwise operations execute in a single CPU clock cycle.
     * - Space Complexity: O(1). No auxiliary data structures are used.
     */
    public static int setIthBitOptimal(int n, int i) {
        // Create a mask with a 1 at the i-th position, then OR it with n.
        int mask = 1 << i;
        return n | mask;
    }

    /**
     * Phase 2.2: Optimal - Clear i-th Bit
     * * Detailed Intuition:
     * We use bitwise AND (`&`). The AND operator returns 1 ONLY if *both* bits are 1.
     * We want to keep all original bits exactly the same, EXCEPT the i-th bit,
     * which must become 0.
     * 1. Create a mask: `1 << i` (e.g., 000100).
     * 2. Invert the mask using bitwise NOT `~`: `~(1 << i)` (e.g., 111011).
     * Now we have a mask where ONLY the i-th bit is 0, and all others are 1.
     * 3. AND this mask with N:
     * - N's original bits AND'd with 1 remain unchanged (x & 1 = x).
     * - N's i-th bit AND'd with 0 becomes 0 (x & 0 = 0).
     *
     * Complexity Analysis:
     * - Time Complexity: O(1). Executes directly on the ALU in constant time.
     * - Space Complexity: O(1). No auxiliary memory required.
     */
    public static int clearIthBitOptimal(int n, int i) {
        // Create a mask with a 0 at the i-th position and 1s elsewhere, then AND it.
        int mask = ~(1 << i);
        return n & mask;
    }

    // ========================================================================
    // TESTING SUITE
    // ========================================================================

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  DSA EVALUATION: Set and Clear i-th Bit");
        System.out.println("==================================================\n");

        // Test Cases format: { N, i }
        int[][] testCases = {
                {10, 2},  // Standard case: 1010, toggle 2nd bit
                {13, 2},  // Standard case: 1101, toggle 2nd bit
                {0, 0},   // Edge case: All zeros, toggle LSB
                {1, 0},   // Edge case: 1, toggle LSB
                {0, 30},  // Edge case: High bit manipulation
                {Integer.MAX_VALUE, 15} // Edge case: Large number
        };

        for (int[] test : testCases) {
            int n = test[0];
            int i = test[1];

            System.out.println("Testing N = " + n + " (Binary: " + Integer.toBinaryString(n) + "), i = " + i);

            // Phase 1 Executions
            int bruteSet = setIthBitBruteForce(n, i);
            int bruteClear = clearIthBitBruteForce(n, i);

            // Phase 2 Executions
            int optimalSet = setIthBitOptimal(n, i);
            int optimalClear = clearIthBitOptimal(n, i);

            // Verifications
            System.out.println("  [SET]   Brute: " + bruteSet + " | Optimal: " + optimalSet);
            System.out.println("          Binary Result: " + Integer.toBinaryString(optimalSet));
            assert bruteSet == optimalSet : "Set methods mismatch!";

            System.out.println("  [CLEAR] Brute: " + bruteClear + " | Optimal: " + optimalClear);
            System.out.println("          Binary Result: " + Integer.toBinaryString(optimalClear));
            assert bruteClear == optimalClear : "Clear methods mismpanchatch!";

            System.out.println("--------------------------------------------------");
        }
        System.out.println("All test cases passed successfully!");
    }
}
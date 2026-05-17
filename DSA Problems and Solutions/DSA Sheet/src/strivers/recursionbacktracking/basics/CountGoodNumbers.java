package com.questions.strivers.recursionbacktracking.basics;

/**
 * =====================================================================================
 *  LeetCode 1922 - Count Good Numbers
 * =====================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT ----------------------------------
 *
 * A digit string of length `n` is called **good** if:
 *
 * 1. Digits at EVEN indices (0-based indexing) are EVEN digits:
 *    {0, 2, 4, 6, 8} → 5 choices
 *
 * 2. Digits at ODD indices are PRIME digits:
 *    {2, 3, 5, 7} → 4 choices
 *
 * Given a number `n`, return the total number of **good digit strings**
 * of length `n`.
 *
 * Since the answer can be very large, return it modulo:
 *      MOD = 10^9 + 7
 *
 * -------------------------------- EXAMPLES -------------------------------------------
 *
 * Input: n = 1
 * Output: 5
 * Explanation:
 * - Only index 0 exists (even index)
 * - Choices = {0,2,4,6,8}
 *
 * Input: n = 4
 * Output: 400
 *
 * -------------------------------- CONSTRAINTS ----------------------------------------
 *
 * 1 <= n <= 10^15
 *
 * This very large constraint eliminates any brute-force or recursion-based approach.
 *
 * =====================================================================================
 * APPROACH & OBSERVATION
 * =====================================================================================
 *
 * We do NOT need to generate the actual strings.
 * We only need to COUNT the number of valid possibilities.
 *
 * ------------------------------
 * Step 1: Count Positions
 * ------------------------------
 *
 * Let n be the length of the string.
 *
 * - Number of EVEN indices  = (n + 1) / 2
 * - Number of ODD indices   = n / 2
 *
 * Example:
 * n = 5 → indices = 0 1 2 3 4
 * even = 3 (0,2,4)
 * odd  = 2 (1,3)
 *
 * ------------------------------
 * Step 2: Count Choices
 * ------------------------------
 *
 * - Each EVEN index → 5 choices
 * - Each ODD index  → 4 choices
 *
 * Total number of good strings:
 *
 *   = (5 ^ evenCount) × (4 ^ oddCount)
 *
 * ------------------------------
 * Step 3: Handle Large Powers
 * ------------------------------
 *
 * Since n can be as large as 10^15:
 * - Direct exponentiation is impossible.
 * - We use **Binary Exponentiation** (Fast Power).
 *
 * Time complexity reduces from O(n) → O(log n)
 *
 * =====================================================================================
 * WHY THIS APPROACH WORKS
 * =====================================================================================
 *
 * - Each position is independent.
 * - The rule only depends on index parity.
 * - Counting combinations is sufficient.
 * - Binary exponentiation handles very large powers efficiently.
 *
 * =====================================================================================
 */

public class CountGoodNumbers {

    /**
     * MOD value as required by the problem
     */
    private static final long MOD = 1_000_000_007;

    /**
     * ------------------------------------------------------------------------------
     * MAIN FUNCTION
     * ------------------------------------------------------------------------------
     * Computes the total number of good digit strings of length n.
     */
    public static int countGoodNumbers(long n) {

        /**
         * Count of even indices:
         * Example:
         * n = 5 → (5 + 1) / 2 = 3
         */
        long evenCount = (n + 1) / 2;

        /**
         * Count of odd indices:
         * Example:
         * n = 5 → 5 / 2 = 2
         */
        long oddCount = n / 2;

        /**
         * Calculate:
         * 5 ^ evenCount % MOD
         */
        long evenWays = fastPower(5, evenCount);

        /**
         * Calculate:
         * 4 ^ oddCount % MOD
         */
        long oddWays = fastPower(4, oddCount);

        /**
         * Final answer:
         * Multiply both results and apply modulo
         */
        return (int) ((evenWays * oddWays) % MOD);
    }

    /**
     * =================================================================================
     * FAST POWER (Binary Exponentiation)
     * =================================================================================
     *
     * Computes:
     *      (base ^ exp) % MOD
     *
     * Time Complexity:
     *      O(log exp)
     *
     * Why Binary Exponentiation?
     * - Reduces large exponent calculations efficiently
     * - Required because exp can be up to 10^15
     */
    private static long fastPower(long base, long exp) {

        long result = 1;

        /**
         * Continue until exponent becomes zero
         */
        while (exp > 0) {

            /**
             * If current bit of exponent is set (odd),
             * multiply result with base
             */
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }

            /**
             * Square the base for next iteration
             */
            base = (base * base) % MOD;

            /**
             * Right shift exponent (divide by 2)
             */
            exp >>= 1;
        }

        return result;
    }

    /**
     * =================================================================================
     * MAIN METHOD (LOCAL TESTING)
     * =================================================================================
     */
    public static void main(String[] args) {

        System.out.println(countGoodNumbers(1));   // Expected: 5
        System.out.println(countGoodNumbers(4));   // Expected: 400
        System.out.println(countGoodNumbers(50));  // Expected: 564908303
    }
}

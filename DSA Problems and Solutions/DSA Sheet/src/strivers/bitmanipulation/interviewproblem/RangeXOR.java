package com.questions.strivers.bitmanipulation.interviewproblem;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Find XOR of numbers from L to R
 * ==================================================================================================
 * Given two integers L and R, find the XOR of all elements in the range [L, R].
 *
 * Example 1:
 * Input: L = 3, R = 5
 * Output: 2
 * Explanation: 3 ^ 4 ^ 5 = 2
 *
 * Example 2:
 * Input: L = 1, R = 3
 * Output: 0
 * Explanation: 1 ^ 2 ^ 3 = 0
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Linear Traversal)
 * ==================================================================================================
 * Iterate from L to R and compute the cumulative XOR.
 * This is simple but slow for large ranges (O(N) time).
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Prefix XOR Pattern)
 * ==================================================================================================
 * XOR has a self-inverse property: A ^ A = 0.
 * Therefore, XOR(L, R) = XOR(0, R) ^ XOR(0, L - 1).
 * * To find XOR(0, N) in O(1), we use a repeating pattern observed in XOR sequences:
 * - If N % 4 == 0 -> Result is N
 * - If N % 4 == 1 -> Result is 1
 * - If N % 4 == 2 -> Result is N + 1
 * - If N % 4 == 3 -> Result is 0
 * ==================================================================================================
 */

public class RangeXOR {

    public static void main(String[] args) {
        int L = 3, R = 5;

        System.out.println("Range [" + L + ", " + R + "]");
        System.out.println("Brute Force : " + findXORBruteForce(L, R));
        System.out.println("Optimal     : " + findXOROptimal(L, R));

        System.out.println("--------------------------------------------------");

        L = 1; R = 3;
        System.out.println("Range [" + L + ", " + R + "]");
        System.out.println("Brute Force : " + findXORBruteForce(L, R));
        System.out.println("Optimal     : " + findXOROptimal(L, R));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BRUTE FORCE
     * ----------------------------------------------------------------------
     * Logic: Simply loop from L to R and XOR everything.
     */
    public static int findXORBruteForce(int L, int R) {
        int xorSum = 0;
        for (int i = L; i <= R; i++) {
            xorSum ^= i;
        }
        return xorSum;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: OPTIMAL (O(1))
     * ----------------------------------------------------------------------
     * Logic:
     * 1. Calculate XOR from 0 to R using the mathematical pattern.
     * 2. Calculate XOR from 0 to (L-1) using the mathematical pattern.
     * 3. XOR the two results to get the range [L, R].
     */
    public static int findXOROptimal(int L, int R) {
        return computeXORFromZero(R) ^ computeXORFromZero(L - 1);
    }

    /**
     * Helper function to find XOR from 0 to N in O(1)
     * Pattern:
     * N=0: 0
     * N=1: 0^1 = 1
     * N=2: 1^2 = 3 (N+1)
     * N=3: 3^3 = 0
     * N=4: 0^4 = 4 (N)
     */
    private static int computeXORFromZero(int n) {
        if (n < 0) return 0; // Handle L=0 edge case (L-1 would be -1)

        int remainder = n % 4;
        if (remainder == 0) return n;
        if (remainder == 1) return 1;
        if (remainder == 2) return n + 1;
        return 0; // remainder == 3
    }
}
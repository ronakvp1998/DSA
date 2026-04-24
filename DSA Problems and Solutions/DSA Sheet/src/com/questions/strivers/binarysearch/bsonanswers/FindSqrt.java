package com.questions.strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * 🤖 MASTERCLASS: FINDING SQUARE ROOT OF A NUMBER
 * ============================================================================
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ### 1. Header & Problem Context
 * Problem Statement:
 * You are given a non-negative integer n. Your task is to find and return its
 * square root. If 'n' is not a perfect square, then return the floor value
 * of sqrt(n). You must not use any built-in square root functions.
 * * Constraints:
 * 0 <= n <= 2^31 - 1
 * * Input/Output Formats:
 * Input: An integer 'n'.
 * Output: An integer representing the floor of the square root of 'n'.
 * * Examples:
 * Input: n = 36
 * Output: 6
 * Explanation: Square root of 36 is exactly 6.
 * * Input: n = 28
 * Output: 5
 * Explanation: Square root of 28 is approximately 5.291. The floor value is 5.
 * * Note: Since this is a search/math problem and not a DP problem, the
 * implementation follows the standard algorithmic progression roadmap.
 * ============================================================================
 */

public class FindSqrt {

    /**
     * ========================================================================
     * Phase 1: Best and Recommended Approach - Binary Search (Optimized)
     * ========================================================================
     * Approach Steps:
     * 1. Handle base cases for 0 and 1 immediately.
     * 2. The square root of any number 'n' >= 2 will always lie in the
     * range [1, n/2].
     * 3. Since this range is strictly increasing (monotonic), we apply
     * Binary Search to find the answer efficiently.
     * 4. Calculate mid. If mid * mid <= n, then mid is a potential answer. We
     * store it and move to the right half to find a larger potential answer.
     * 5. If mid * mid > n, the answer must lie in the left half.
     * * Detailed Intuition:
     * Because squares of positive integers grow monotonically, the search space
     * is perfectly sorted. We divide the search space in half at each step.
     * Furthermore, mathematically, for any n >= 2, sqrt(n) <= n/2. By setting
     * our upper bound (high) to n/2 instead of n, we safely eliminate the upper
     * half of the integers right from the start, saving an iteration!
     * * Complexity Analysis:
     * Time Complexity: O(log(N/2)) which simplifies to O(log N).
     * Space Complexity: O(1) - Constant auxiliary space used.
     */
    public int floorSqrtOptimal(int n) {
        // Base cases for 0 and 1 are critical here.
        // If n=1, n/2 becomes 0, which would break the binary search range [1, 0].
        if (n == 0 || n == 1) {
            return n;
        }

        int low = 1;
        int high = n / 2; // Optimized upper bound
        int ans = 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents integer overflow

            // Use long to prevent integer overflow when multiplying mid * mid
            long square = (long) mid * mid;

            if (square == n) {
                return mid; // Exact match found
            }

            if (square < n) {
                ans = mid;         // Potential answer, store it
                low = mid + 1;     // Look for a larger number
            } else {
                high = mid - 1;    // Mid is too large, look in the left half
            }
        }

        return ans;
    }

    /**
     * ========================================================================
     * Phase 2: Brute Force Approach - Linear Search
     * ========================================================================
     * Approach Steps:
     * 1. Start a loop from 1 up to n.
     * 2. For each number i, check if its square is less than or equal to n.
     * 3. If it is, update the answer variable.
     * 4. If i * i exceeds n, we have crossed the boundary. Break the loop.
     * * Detailed Intuition:
     * The most fundamental way to find a square root is to test every integer
     * sequentially. We simply count up, squaring each number until the square
     * becomes strictly greater than our target 'n'. The number right before
     * we crossed the threshold is our floor square root.
     * * Complexity Analysis:
     * Time Complexity: O(sqrt(N)) - The loop runs exactly sqrt(N) times.
     * Space Complexity: O(1) - Constant space used.
     */
    public int floorSqrtBrute(int n) {
        if (n == 0) return 0;

        int ans = 1;

        for (int i = 1; i <= n; i++) {
            // Using long to prevent overflow during i * i
            if ((long) i * i <= n) {
                ans = i;
            } else {
                // Break immediately when the square exceeds n
                break;
            }
        }
        return ans;
    }

    /**
     * ========================================================================
     * Phase 3: Alternative Approach - Newton-Raphson Method
     * ========================================================================
     * Approach Steps:
     * 1. Start with an initial guess (e.g., n).
     * 2. Iteratively refine the guess using the formula: root = 0.5 * (X + (N / X)).
     * 3. Stop when the difference between the current guess and the next guess
     * is sufficiently small (or when root * root <= n for integer math).
     * * Detailed Intuition:
     * The Newton-Raphson method is a calculus-based approach to finding roots of
     * real-valued functions. To find the square root of N, we are finding the
     * roots of the function f(x) = x^2 - N. The method uses tangents to rapidly
     * converge on the exact root. It converges quadratically, meaning the number
     * of correct digits roughly doubles with each step.
     * * Complexity Analysis:
     * Time Complexity: O(log(log N)) - Extremely fast convergence.
     * Space Complexity: O(1) - Constant space used.
     */
    public int floorSqrtNewton(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        long x = n;
        // Keep iterating as long as x^2 is strictly greater than n
        while (x * x > n) {
            // Formula: x_next = (x + n / x) / 2
            x = (x + n / x) / 2;
        }

        return (int) x;
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        FindSqrt solver = new FindSqrt();

        // Standard test cases, edge cases, and large number boundaries
        int[] testCases = {
                36,             // Perfect square
                28,             // Non-perfect square
                8,              // Small non-perfect square
                0,              // Edge case: zero
                1,              // Edge case: one
                4,              // Small boundary logic test
                2147395599      // Large integer near Integer.MAX_VALUE to test overflow
        };

        System.out.println("======================================================");
        System.out.println("🧪 RUNNING TESTING SUITE");
        System.out.println("======================================================");

        for (int n : testCases) {
            System.out.println("Testing N = " + n);

            // Phase 1 execution (Optimized Binary Search)
            long startTime1 = System.nanoTime();
            int res1 = solver.floorSqrtOptimal(n);
            long time1 = System.nanoTime() - startTime1;

            // Phase 2 execution (Brute Force)
            long startTime2 = System.nanoTime();
            // Note: Skipping Brute Force for massive numbers to avoid stalling the test suite
            int res2 = n > 100000000 ? res1 : solver.floorSqrtBrute(n);
            long time2 = System.nanoTime() - startTime2;

            // Phase 3 execution (Newton Method)
            long startTime3 = System.nanoTime();
            int res3 = solver.floorSqrtNewton(n);
            long time3 = System.nanoTime() - startTime3;

            System.out.println("   [Phase 1] Binary Search : " + res1 + " (Time: " + time1 + " ns)");
            if (n <= 100000000) {
                System.out.println("   [Phase 2] Brute Force   : " + res2 + " (Time: " + time2 + " ns)");
            } else {
                System.out.println("   [Phase 2] Brute Force   : SKIPPED (Too slow for large N)");
            }
            System.out.println("   [Phase 3] Newton Method : " + res3 + " (Time: " + time3 + " ns)");

            // Validation
            boolean passed = (res1 == res3) && (n > 100000000 || res1 == res2);
            System.out.println("   Status: " + (passed ? "✅ PASSED" : "❌ FAILED"));
            System.out.println("------------------------------------------------------");
        }
    }
}
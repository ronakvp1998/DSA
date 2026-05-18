package strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * 🤖 MASTERCLASS: NTH ROOT OF A NUMBER
 * ============================================================================
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ### 1. Header & Problem Context
 * Problem Statement:
 * Given two numbers N and M, find the Nth root of M. The Nth root of a number
 * M is defined as a positive integer X such that X raised to the power N
 * equals M. If the Nth root is not an integer, return -1.
 * * Constraints:
 * 1 <= N <= 30
 * 1 <= M <= 10^9
 * * Input/Output Formats:
 * Input: Two integers 'N' and 'M'.
 * Output: An integer representing the exact Nth root of M, or -1 if it doesn't exist.
 * * Examples:
 * Input: N = 3, M = 27
 * Output: 3
 * Explanation: The cube root of 27 is equal to 3 (since 3 * 3 * 3 = 27).
 * * Input: N = 4, M = 69
 * Output: -1
 * Explanation: The 4th root of 69 does not exist as an integer. Return -1.
 * * Input: N = 1, M = 14
 * Output: 14
 * Explanation: The 1st root of 14 is 14.
 * * Note: This is a Search/Math problem. We will follow the non-DP roadmap.
 * ============================================================================
 */

public class FindNRoot {

    /**
     * Helper Method: Prevents Overflow during Exponentiation
     * Instead of calculating mid^n blindly (which can overflow standard integer
     * limits), we multiply step-by-step and break early if the product exceeds M.
     * * Returns:
     * 1 : if mid^N == M (Exact match)
     * 0 : if mid^N < M  (Too small)
     * 2 : if mid^N > M  (Too large)
     */
    private int powerCheck(int mid, int n, int m) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans = ans * mid;
            // Early exit to prevent overflow and save computation
            if (ans > m) {
                return 2;
            }
        }
        if (ans == m) return 1;
        return 0;
    }

    /**
     * ========================================================================
     * Phase 1: Best and Recommended Approach - Binary Search
     * ========================================================================
     * Approach Steps:
     * 1. The Nth root of M will always lie within the range [1, M].
     * 2. Since the function f(x) = x^N is strictly increasing (monotonic) for
     * positive integers, we can use Binary Search.
     * 3. Calculate mid. Use the helper function to safely compute mid^N.
     * 4. If mid^N == M, we found the exact root; return mid.
     * 5. If mid^N < M, the root must be larger; move the search to the right half (low = mid + 1).
     * 6. If mid^N > M, the root must be smaller; move the search to the left half (high = mid - 1).
     * 7. If the loop exhausts without returning, the integer root does not exist; return -1.
     * * Detailed Intuition:
     * Linearly searching for the root is highly inefficient for large values of M.
     * Because exponentiation scales monotonically, our search space is inherently
     * sorted. By applying binary search, we halve the search space at every step.
     * The critical insight here is avoiding integer overflow: calculating
     * (10^5)^30 will easily overflow even a 64-bit long. The `powerCheck` helper
     * safely bounds the calculation dynamically.
     * * Complexity Analysis:
     * Time Complexity: O(N * log(M))
     * - Binary search takes O(log M) iterations.
     * - Inside each iteration, the `powerCheck` loop runs at most N times.
     * Space Complexity: O(1) - Constant auxiliary space.
     */
    public int nthRootOptimal(int n, int m) {
        // If N is 1, the root is always M itself.
        if (n == 1) return m;

        int low = 1;
        // Now it is mathematically safe to divide M by 2!
        int high = m / 2;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevent overflow calculating mid
            int checkResult = powerCheck(mid, n, m);

            if (checkResult == 1) {
                return mid; // Exact root found
            } else if (checkResult == 0) {
                low = mid + 1; // mid^N is less than M, target is larger
            } else {
                high = mid - 1; // mid^N is strictly greater than M, target is smaller
            }
        }

        return -1; // No integer root found
    }

    /**
     * ========================================================================
     * Phase 2: Brute Force Approach - Linear Search
     * ========================================================================
     * Approach Steps:
     * 1. Iterate with a variable 'i' from 1 up to M.
     * 2. For each 'i', safely compute i^N using the helper function.
     * 3. If i^N == M, return 'i'.
     * 4. If i^N > M, break the loop early (as all subsequent i^N will also be > M).
     * 5. If the loop completes without finding a match, return -1.
     * * Detailed Intuition:
     * This is the "Think It" stage. To find the Nth root of M, we check every
     * single integer starting from 1. We raise it to the power of N. If it equals
     * M, we have our answer. The moment our calculated power exceeds M, we know
     * an integer root doesn't exist because the sequence is strictly increasing.
     * * Complexity Analysis:
     * Time Complexity: O(M * N) - In the worst case (e.g., M=10^9, N=1), the loop
     * runs M times, and power computation takes N operations.
     * Space Complexity: O(1) - Constant space used.
     */
    public int nthRootBrute(int n, int m) {
        for (int i = 1; i <= m; i++) {
            int checkResult = powerCheck(i, n, m);

            if (checkResult == 1) {
                return i;
            }
            if (checkResult == 2) {
                break; // Exceeded M, no point in checking larger numbers
            }
        }
        return -1;
    }

    /**
     * ========================================================================
     * Phase 3: Alternative Approach - Math library precision
     * ========================================================================
     * Approach Steps:
     * 1. Calculate the Nth root using the formula: root = M^(1/N).
     * 2. Use Math.pow(m, 1.0 / n) to get a double-precision float.
     * 3. Round the result to the nearest integer.
     * 4. Verify if rounded_val^N exactly equals M to handle precision loss.
     * * Detailed Intuition:
     * Modern math libraries are highly optimized. Taking the Nth root is
     * mathematically equivalent to raising a number to the fractional power of 1/N.
     * However, floating-point arithmetic is prone to precision errors (e.g., a
     * cube root of 27 might evaluate to 2.9999999999). We must round the result
     * and strictly verify it with integer math.
     * * Complexity Analysis:
     * Time Complexity: O(N) or O(1) depending on hardware implementation of Math.pow,
     * plus the O(N) verification step.
     * Space Complexity: O(1) - Constant space used.
     */
    public int nthRootAlternative(int n, int m) {
        // Calculate M^(1/N)
        double rootDouble = Math.pow(m, 1.0 / n);

        // Round to nearest integer to fix floating point precision errors
        int rootInt = (int) Math.round(rootDouble);

        // Strictly verify using the safe power check
        if (powerCheck(rootInt, n, m) == 1) {
            return rootInt;
        }

        return -1;
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        FindNRoot solver = new FindNRoot();

        // [N, M] test pairs
        int[][] testCases = {
                {3, 27},       // Perfect Nth root
                {4, 69},       // No integer Nth root
                {2, 100},      // Standard square root
                {1, 14},       // N = 1 edge case
                {6, 4096},     // Larger perfect root (4^6 = 4096)
                {30, 1073741824} // High N value to test overflow boundaries (2^30)
        };

        System.out.println("======================================================");
        System.out.println("🧪 RUNNING TESTING SUITE: Nth Root of M");
        System.out.println("======================================================");

        for (int[] testCase : testCases) {
            int n = testCase[0];
            int m = testCase[1];

            System.out.println("Testing N = " + n + ", M = " + m);

            // Phase 1 execution (Optimized Binary Search)
            long startTime1 = System.nanoTime();
            int res1 = solver.nthRootOptimal(n, m);
            long time1 = System.nanoTime() - startTime1;

            // Phase 2 execution (Brute Force)
            long startTime2 = System.nanoTime();
            // Skip Brute Force for large M to avoid stalling the test suite
            int res2 = m > 100000 ? res1 : solver.nthRootBrute(n, m);
            long time2 = System.nanoTime() - startTime2;

            // Phase 3 execution (Alternative Math Library)
            long startTime3 = System.nanoTime();
            int res3 = solver.nthRootAlternative(n, m);
            long time3 = System.nanoTime() - startTime3;

            System.out.println("   [Phase 1] Binary Search : " + res1 + " (Time: " + time1 + " ns)");
            if (m <= 100000) {
                System.out.println("   [Phase 2] Brute Force   : " + res2 + " (Time: " + time2 + " ns)");
            } else {
                System.out.println("   [Phase 2] Brute Force   : SKIPPED (Too slow for large M)");
            }
            System.out.println("   [Phase 3] Math.pow()    : " + res3 + " (Time: " + time3 + " ns)");

            // Validation
            boolean passed = (res1 == res3) && (m > 100000 || res1 == res2);
            System.out.println("   Status: " + (passed ? "✅ PASSED" : "❌ FAILED"));
            System.out.println("------------------------------------------------------");
        }
    }
}
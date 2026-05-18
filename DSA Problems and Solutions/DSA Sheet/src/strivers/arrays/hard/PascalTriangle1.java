package strivers.arrays.hard;
/**
 * ============================================================================
 * 🎯 MASTERCLASS: Pascal's Triangle - Variation 1 (Find Element at r, c)
 * ============================================================================
 *
 * ### 1. Header & Problem Context
 * * Problem Statement:
 * Given row number 'r' and column number 'c' (both 1-indexed), return the element
 * at position (r, c) in Pascal's triangle.
 * * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * The 1st row (r=1) is just [1].
 *
 * Constraints:
 * - 1 <= r <= 31 (To keep the answer within standard 32-bit integer limits,
 * though we will use 'long' in optimal approaches to prevent intermediate overflow).
 * - 1 <= c <= r
 *
 * Example 1:
 * Input: r = 5, c = 3
 * Output: 6
 * Explanation:
 * Row 1: 1
 * Row 2: 1 1
 * Row 3: 1 2 1
 * Row 4: 1 3 3 1
 * Row 5: 1 4 6 4 1  <-- 3rd element is 6.
 *
 * Example 2:
 * Input: r = 1, c = 1
 * Output: 1
 * * ----------------------------------------------------------------------------
 * CONCEPTUAL VISUALIZATION:
 * To map 1-indexed (r, c) to 0-indexed combinatorics (n, k), we use:
 * n = r - 1, k = c - 1. So for r=5, c=3, we are finding f(4, 2).
 *
 * Overlapping Subproblems Recursion Tree for f(4, 2):
 * * f(4,2)
 * /        \
 * f(3,1)      f(3,2)
 * /    \      /    \
 * f(2,0) f(2,1) f(2,1) f(2,2)
 * (1)    /  \   /  \    (1)
 * f(1,0) .. ..  ..
 * * Notice how f(2,1) is computed multiple times. This overlapping nature
 * makes it a perfect candidate for Dynamic Programming (Memoization).
 * * Complete Final DP Array (0-indexed, up to n=4, k=2):
 * k=0  k=1  k=2
 * n=0 [ 1,   0,   0 ]
 * n=1 [ 1,   1,   0 ]
 * n=2 [ 1,   2,   1 ]
 * n=3 [ 1,   3,   3 ]
 * n=4 [ 1,   4,   6 ]  <-- f(4,2) = 6
 * ============================================================================
 */
import java.util.Arrays;

public class PascalTriangle1 {

    /**
     * ============================================================================
     * Phase 5: Optimal Math/Combinatorics - The "Master" stage.
     * ============================================================================
     * Intuition:
     * The DP approaches simulate building the triangle. However, mathematically,
     * the element at 0-indexed position (n, k) is simply the binomial coefficient
     * nCr (n choose k).
     * * Formula: nCr = n! / (k! * (n-k)!)
     * To avoid overflow and compute efficiently, we use the multiplicative formula:
     * C(n, k) = C(n, k-1) * (n - k + 1) / k
     * * We calculate this in a single loop. We also use the symmetry property
     * C(n, k) == C(n, n - k) to minimize the number of loop iterations.
     *
     * Complexity Analysis:
     * Time (O): O(min(k, n-k)) -> Effectively O(c). Single loop execution.
     * Space (O): O(1) auxiliary and heap space. Pure mathematics!
     */
    public static int phase5OptimalMath(int r, int c) {
        int n = r - 1;
        int k = c - 1;

        // Symmetry optimization: nCr == nC(n-r)
        if (k > n - k) {
            k = n - k;
        }

        long res = 1; // Use long to prevent intermediate step overflow
        for (int i = 0; i < k; i++) {
            res = res * (n - i);
            res = res / (i + 1);
        }

        return (int) res;
    }

    /**
     * ============================================================================
     * Phase 1: Brute Force Recursion - The "Think it" stage.
     * ============================================================================
     * Intuition:
     * The core property of Pascal's triangle is that any element is the sum of
     * the element directly above it, and the element above and to the left.
     * f(n, k) = f(n-1, k-1) + f(n-1, k). Base cases are the edges of the triangle
     * where k == 0 (first element) or n == k (last element), which are always 1.
     *
     * Complexity Analysis:
     * Time (O): O(2^n) - At each step we branch into two subproblems.
     * Space (O): O(n) auxiliary stack space for the recursion depth. O(1) heap space.
     */
    public static int phase1BruteForce(int r, int c) {
        return solveRecursive(r - 1, c - 1);
    }

    private static int solveRecursive(int n, int k) {
        // Base cases: First column or the diagonal edge
        if (k == 0 || n == k) {
            return 1;
        }
        // Recursive step: Top-Left + Top-Right (conceptually)
        return solveRecursive(n - 1, k - 1) + solveRecursive(n - 1, k);
    }

    /**
     * ============================================================================
     * Phase 2: Top-Down Memoization - The "Refine it" stage.
     * ============================================================================
     * Intuition:
     * As seen in the recursion tree, states like f(2,1) are calculated multiple times.
     * We can cache the results of f(n, k) in a 2D array to avoid redundant work.
     *
     * Complexity Analysis:
     * Time (O): O(n * k) - We compute each state exactly once.
     * Space (O): O(n * k) heap space for the 2D memoization array + O(n) auxiliary
     * stack space for recursion depth.
     */
    public static int phase2Memoization(int r, int c) {
        int n = r - 1;
        int k = c - 1;
        int[][] memo = new int[n + 1][k + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return solveMemoized(n, k, memo);
    }

    private static int solveMemoized(int n, int k, int[][] memo) {
        if (k == 0 || n == k) return 1;
        if (memo[n][k] != -1) return memo[n][k];

        memo[n][k] = solveMemoized(n - 1, k - 1, memo) + solveMemoized(n - 1, k, memo);
        return memo[n][k];
    }

    /**
     * ============================================================================
     * Phase 3: Bottom-Up Tabulation - The "Build it" stage.
     * ============================================================================
     * Intuition:
     * We convert the top-down recursive approach into an iterative approach. We build
     * the DP table from the top of the triangle down to our target row.
     * * DP Array States for r=5, c=3 (n=4, k=2):
     * * EXACT DEFAULT STATE (Immediately after initialization & base cases):
     * k=0  k=1  k=2
     * n=0 [ 1,   0,   0 ]
     * n=1 [ 1,   1,   0 ]
     * n=2 [ 1,   0,   1 ]
     * n=3 [ 1,   0,   0 ]
     * n=4 [ 1,   0,   0 ]
     * * EXACT FINAL STATE (After loops complete):
     * k=0  k=1  k=2
     * n=0 [ 1,   0,   0 ]
     * n=1 [ 1,   1,   0 ]
     * n=2 [ 1,   2,   1 ]
     * n=3 [ 1,   3,   3 ]
     * n=4 [ 1,   4,   6 ] -> Answer is 6.
     *
     * Complexity Analysis:
     * Time (O): O(n * k) - Two nested loops iterating through the matrix.
     * Space (O): O(n * k) heap space for the 2D DP array. O(1) auxiliary stack space.
     */
    public static int phase3Tabulation(int r, int c) {
        int n = r - 1;
        int k = c - 1;

        if (k == 0 || n == k) return 1;

        int[][] dp = new int[n + 1][k + 1];

        // Initialize base cases
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1; // First column is always 1
            if (i <= k) dp[i][i] = 1; // Diagonal is always 1
        }

        // Fill the DP table
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k && j < i; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }
        return dp[n][k];
    }

    /**
     * ============================================================================
     * Phase 4: Space Optimization - The "Perfect it" stage.
     * ============================================================================
     * Intuition:
     * Looking at the relation `dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j]`,
     * we notice that we only ever need the *previous row* to compute the *current row*.
     * We can optimize the O(n * k) 2D array into an O(k) 1D array.
     * By iterating backwards from right to left, we avoid overwriting values
     * we still need for the current row's calculation.
     *
     * Complexity Analysis:
     * Time (O): O(n * k) - Still computing elements row by row.
     * Space (O): O(k) heap space for the 1D array. O(1) auxiliary stack space.
     */
    public static int phase4SpaceOptimization(int r, int c) {
        int n = r - 1;
        int k = c - 1;

        int[] dp = new int[k + 1];
        dp[0] = 1; // Base case

        for (int i = 1; i <= n; i++) {
            // Iterate backwards to prevent overwriting values needed for the same row
            for (int j = Math.min(i, k); j > 0; j--) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[k];
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Running Test Suite for Pascal's Triangle Element...");
        System.out.println("------------------------------------------------------\n");

        // Test Cases format: {row, col, expected_output}
        int[][] testCases = {
                {5, 3, 6},   // Standard case (mid triangle)
                {1, 1, 1},   // Edge case: Top of triangle
                {6, 1, 1},   // Edge case: First element of a row
                {6, 6, 1},   // Edge case: Last element of a row
                {7, 4, 20},  // Larger number mid triangle
                {30, 15, 77558760} // Stress test for Math approach (DP would be slow/memory heavy if unoptimized)
        };

        boolean allPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            int r = testCases[i][0];
            int c = testCases[i][1];
            int expected = testCases[i][2];

            // For the stress test (r=30), Brute Force will take too long (O(2^n)).
            // We only test optimal paths for large 'r'.
            if (r > 20) {
                int resMath = phase5OptimalMath(r, c);
                boolean pass = (resMath == expected);
                System.out.printf("Test %d (Stress Test r=%d, c=%d): %s\n", i + 1, r, c, pass ? "✅ PASS" : "❌ FAIL");
                if (!pass) allPassed = false;
                continue;
            }

            int res1 = phase1BruteForce(r, c);
            int res2 = phase2Memoization(r, c);
            int res3 = phase3Tabulation(r, c);
            int res4 = phase4SpaceOptimization(r, c);
            int res5 = phase5OptimalMath(r, c);

            boolean pass = (res1 == expected && res2 == expected &&
                    res3 == expected && res4 == expected && res5 == expected);

            System.out.printf("Test %d (r=%d, c=%d) -> Expected: %d\n", i + 1, r, c, expected);
            System.out.printf("  Brute Force : %d\n", res1);
            System.out.printf("  Memoization : %d\n", res2);
            System.out.printf("  Tabulation  : %d\n", res3);
            System.out.printf("  Space Opt.  : %d\n", res4);
            System.out.printf("  Optimal Math: %d\n", res5);
            System.out.printf("  Status: %s\n\n", pass ? "✅ PASS" : "❌ FAIL");

            if (!pass) allPassed = false;
        }

        System.out.println("------------------------------------------------------");
        System.out.println(allPassed ? "🎉 ALL TESTS PASSED!" : "⚠️ SOME TESTS FAILED.");
    }
}
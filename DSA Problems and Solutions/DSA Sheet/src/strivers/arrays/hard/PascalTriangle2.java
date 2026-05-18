package strivers.arrays.hard;

import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: PRINT THE N-TH ROW OF PASCAL'S TRIANGLE (1-BASED INDEXING)
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an integer N, return the N-th row of Pascal's Triangle.
 * Assume 1-based indexing (i.e., N=1 represents the 1st row).
 * * * Constraints:
 * - 1 <= N <= 33 (Usually restricted to 33 so values fit within a 32-bit signed integer)
 * * * Input/Output Formats:
 * Input: A single integer N representing the row number.
 * Output: A List of integers representing the complete N-th row.
 * * * Example 1:
 * Input: n = 5
 * Output: [1, 4, 6, 4, 1]
 * Explanation:
 * Row 1:        1
 * Row 2:      1   1
 * Row 3:    1   2   1
 * Row 4:  1   3   3   1
 * Row 5: 1   4   6   4   1  <- This is the 5th row.
 * * * Example 2:
 * Input: n = 1
 * Output: [1]
 * * * Conceptual Visualization (Mathematical Relationship):
 * The sequence of numbers in the N-th row (0-indexed internally as N-1) are:
 * (N-1)C0, (N-1)C1, (N-1)C2, ..., (N-1)C(N-1)
 * Let N = 5 (so row index is 4):
 * 4C0 = 1
 * 4C1 = 4
 * 4C2 = 6
 * 4C3 = 4
 * 4C4 = 1
 * ============================================================================
 */
public class PascalTriangle2 {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Iterative Math)
     * ========================================================================
     * Approach and Steps:
     * 1. Since we know the first element of any row is always 1, we start by
     * adding 1 to our result list.
     * 2. We use a variable `ans` initialized to 1.
     * 3. Loop from i = 1 to N - 1.
     * 4. In each iteration, multiply `ans` by (N - i) and divide by i.
     * 5. Add the updated `ans` to the result list.
     * 6. Return the list.
     * * * Detailed Intuition:
     * To avoid recalculating the combinations from scratch (which is O(r) each time),
     * we leverage the mathematical recurrence relation between adjacent elements
     * in Pascal's triangle: C(n, k) = C(n, k-1) * (n - k + 1) / k.
     * This allows us to derive the next element in O(1) time based solely on the
     * previous element.
     * * * Complexity Analysis:
     * - Time (O): O(N). We iterate N times, doing basic arithmetic in each step.
     * - Space (O): O(1) auxiliary space (excluding the space needed for the output list).
     * ========================================================================
     */
    public static List<Integer> pascalTriangleOptimal(int n) {
        List<Integer> row = new ArrayList<>();
        long ans = 1; // Use long to prevent intermediate overflow before division
        row.add((int) ans);

        for (int i = 1; i < n; i++) {
            ans = ans * (n - i);
            ans = ans / i;
            row.add((int) ans);
        }

        return row;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Create a helper method `nCr(n, r)` to calculate combinations.
     * 2. Loop `c` from 1 to N (representing columns).
     * 3. Calculate nCr(N-1, c-1) and add the result to our list.
     * 4. Return the list.
     * * * Detailed Intuition:
     * This relies purely on the foundational definition that the c-th column of
     * the N-th row is given by the combination formula (N-1)C(c-1). While
     * correct, it wastes computation by calculating the factorial sequence from
     * scratch for every single column in the row.
     * * * Complexity Analysis:
     * - Time (O): O(N^2). We iterate N times, and for each iteration, our nCr
     * helper method takes O(r) time, which approaches O(N) in the worst case.
     * - Space (O): O(1) auxiliary space (excluding the output list).
     * ========================================================================
     */
    public static List<Integer> pascalTriangleBruteForce(int n) {
        List<Integer> row = new ArrayList<>();

        // Loop through each column of the given row
        for (int c = 1; c <= n; c++) {
            row.add(calculateNcR(n - 1, c - 1));
        }

        return row;
    }

    // Helper method for Brute Force
    private static int calculateNcR(int n, int r) {
        long res = 1;
        // Optimization for nCr: nCr == nC(n-r)
        if (r > n - r) {
            r = n - r;
        }
        for (int i = 0; i < r; i++) {
            res = res * (n - i);
            res = res / (i + 1);
        }
        return (int) res;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Iterative Addition/Simulation)
     * ========================================================================
     * Approach and Steps:
     * 1. Allocate an array `row` of size N initialized with 0s.
     * 2. Set the first element to 1.
     * 3. Loop `i` from 1 to N-1 (representing row construction).
     * 4. Loop `j` backwards from `i` down to 1.
     * 5. Update row[j] = row[j] + row[j-1].
     * 6. Convert to a List and return.
     * * * Detailed Intuition:
     * If one forgets the mathematical formulas completely, we can simply simulate
     * the generation of the triangle row by row. By updating a single array
     * *backwards* on each step, we can build the N-th row using the N-1th row's
     * data without overwriting the values we still need.
     * * * Complexity Analysis:
     * - Time (O): O(N^2). We simulate the generation of the entire triangle up to N.
     * - Space (O): O(N) auxiliary space for the tracking array.
     * ========================================================================
     */
    public static List<Integer> pascalTriangleAlternative(int n) {
        int[] row = new int[n];
        row[0] = 1;

        for (int i = 1; i < n; i++) {
            // Update backwards to prevent using newly updated values
            for (int j = i; j > 0; j--) {
                row[j] = row[j] + row[j - 1];
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int val : row) {
            result.add(val);
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== PASCAL'S TRIANGLE N-TH ROW TESTING SUITE ===\n");

        int[] testCases = {1, 2, 5, 6, 10, 30};

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": N = " + n);

            // Test Brute Force
            long start1 = System.nanoTime();
            List<Integer> res1 = pascalTriangleBruteForce(n);
            long end1 = System.nanoTime();

            // Test Alternative
            long start2 = System.nanoTime();
            List<Integer> res2 = pascalTriangleAlternative(n);
            long end2 = System.nanoTime();

            // Test Optimal
            long start3 = System.nanoTime();
            List<Integer> res3 = pascalTriangleOptimal(n);
            long end3 = System.nanoTime();

            // Output Formatting
            System.out.println("Brute Force: " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");
            System.out.println("Alternative: " + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");
            System.out.println("Optimal:     " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = res1.equals(res2) && res2.equals(res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(60) + "\n");
        }
    }
}
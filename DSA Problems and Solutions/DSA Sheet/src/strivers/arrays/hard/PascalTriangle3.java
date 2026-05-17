package com.questions.strivers.arrays.hard;

import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: GENERATE PASCAL'S TRIANGLE (FIRST N ROWS)
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement (LeetCode 118):
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * * * Input/Output Formats:
 * Input: An integer `numRows` representing the number of rows to generate.
 * Output: A List of Lists of integers representing the triangle.
 * * * Constraints:
 * - 1 <= numRows <= 30
 * * * Example 1:
 * Input: numRows = 5
 * Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 * * * Example 2:
 * Input: numRows = 1
 * Output: [[1]]
 * * * Conceptual Visualization (Mathematical Relationship vs Simulation):
 * Mathematical Math (Combinatorics):
 * Row 1 (Index 0): 0C0
 * Row 2 (Index 1): 1C0, 1C1
 * Row 3 (Index 2): 2C0, 2C1, 2C2
 * Row 4 (Index 3): 3C0, 3C1, 3C2, 3C3
 * Row 5 (Index 4): 4C0, 4C1, 4C2, 4C3, 4C4
 * -> Evaluates to: 1, 4, 6, 4, 1
 * * By tracking the previous element in a row, the next element is:
 * Current Element = Previous Element * (RowIndex - ColIndex) / (ColIndex)
 * ============================================================================
 */
public class PascalTriangle3 {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Iterative Math)
     * ========================================================================
     * Approach and Steps:
     * 1. Create a `generateOptimal` method that loops from row = 1 to `numRows`.
     * 2. Inside the loop, call a helper method `generateRow(row)`.
     * 3. In `generateRow(row)`:
     * a. Initialize `ans = 1` and add it to the row list (first element is always 1).
     * b. Loop `col` from 1 to `row - 1`.
     * c. Calculate the next element using the formula: ans = ans * (row - col) / col.
     * d. Add `ans` to the row list.
     * 4. Add the generated row list to the master triangle list.
     * * * Detailed Intuition:
     * Instead of relying on the previous row (simulation) or calculating `nCr`
     * completely from scratch for every element, we utilize the mathematical
     * recurrence relation: C(n, k) = C(n, k-1) * (n - k + 1) / k.
     * This allows us to build any specific row completely independently of the
     * others in strict O(N) time. We wrap this row-generator in a loop to build
     * the full triangle in O(N^2) time.
     * * * Complexity Analysis:
     * - Time (O): O(N^2). We iterate N rows. For each row `i`, we do `i` constant
     * time operations. Sum of 1 + 2 + 3 + ... + N = O(N^2).
     * - Space (O): O(1) auxiliary space (excluding the O(N^2) heap space required
     * to store and return the final 2D List). Stack space is O(1).
     * ========================================================================
     */
    public static List<List<Integer>> generateOptimal(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        // 1-based indexing for row generation logic
        for (int row = 1; row <= numRows; row++) {
            triangle.add(generateRow(row));
        }
        return triangle;
    }

    private static List<Integer> generateRow(int row) {
        long ans = 1; // Use long to prevent integer overflow during multiplication
        List<Integer> ansRow = new ArrayList<>();
        ansRow.add(1); // First element is always 1

        for (int col = 1; col < row; col++) {
            ans = ans * (row - col);
            ans = ans / col;
            ansRow.add((int) ans);
        }
        return ansRow;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Loop `row` from 1 to `numRows`.
     * 2. For each `row`, initialize an empty list.
     * 3. Loop `col` from 1 to `row`.
     * 4. Calculate `nCr(row - 1, col - 1)` using a helper function.
     * 5. Add the computed value to the current row list.
     * 6. Add the row list to the master triangle list.
     * * * Detailed Intuition:
     * This directly translates the combinatorial definition of Pascal's Triangle
     * into code. The element at the i-th row and j-th column is precisely (i-1)C(j-1).
     * While mathematically sound, it recalculates the entire factorial chain for
     * every single cell, leading to heavy redundant operations.
     * * * Complexity Analysis:
     * - Time (O): O(N^3). Generating N rows, each having up to N elements, and
     * the `nCr` calculation takes O(r) -> O(N) time. Overall: O(N * N * N).
     * - Space (O): O(1) auxiliary space (excluding the O(N^2) output list).
     * ========================================================================
     */
    public static List<List<Integer>> generateBruteForce(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            List<Integer> temp = new ArrayList<>();
            for (int col = 1; col <= row; col++) {
                temp.add(calculateNcR(row - 1, col - 1));
            }
            triangle.add(temp);
        }
        return triangle;
    }

    // Helper method for Brute Force
    private static int calculateNcR(int n, int r) {
        long res = 1;
        // Combinatorics optimization: nCr == nC(n-r)
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
     * PHASE 3: ALTERNATIVE APPROACH (Dynamic Programming / Simulation)
     * ========================================================================
     * Approach and Steps:
     * 1. Initialize the triangle list.
     * 2. If `numRows` is 0, return the empty list. Add the first row `[1]`.
     * 3. Loop `i` from 1 to `numRows - 1`.
     * 4. Retrieve the previous row from the triangle list.
     * 5. Create a new row, and start it with a `1`.
     * 6. Loop `j` from 1 to `i - 1`.
     * 7. The element is `prevRow.get(j - 1) + prevRow.get(j)`. Add it.
     * 8. End the row with a `1`. Add it to the triangle.
     * * * Detailed Intuition:
     * This is the standard, highly intuitive LeetCode approach. Instead of using
     * combinatorics, it models the visual construction of the triangle. Every
     * element (except the 1s on the edges) is the direct sum of the two elements
     * directly above it. This naturally forms a dynamic programming state where
     * Row[i] depends entirely on Row[i-1].
     * * * Complexity Analysis:
     * - Time (O): O(N^2). We iterate through a triangular grid of size N*(N+1)/2,
     * doing O(1) addition at each step.
     * - Space (O): O(1) auxiliary space (excluding output list).
     * ========================================================================
     */
    public static List<List<Integer>> generateSimulation(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        if (numRows == 0) return triangle;

        // Base case: First row
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);

        for (int i = 1; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(i - 1);

            // First element of row is always 1
            row.add(1);

            // Each triangle element is equal to the sum of the elements
            // above-and-to-the-left and above-and-to-the-right.
            for (int j = 1; j < i; j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }

            // Last element of row is always 1
            row.add(1);
            triangle.add(row);
        }

        return triangle;
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * sequence sizes and edge cases.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== PASCAL'S TRIANGLE GENERATION TESTING SUITE ===\n");

        int[] testCases = {1, 2, 5, 10, 30};

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": numRows = " + n);

            // Test Brute Force
            long start1 = System.nanoTime();
            List<List<Integer>> res1 = generateBruteForce(n);
            long end1 = System.nanoTime();

            // Test Simulation (DP)
            long start2 = System.nanoTime();
            List<List<Integer>> res2 = generateSimulation(n);
            long end2 = System.nanoTime();

            // Test Optimal (Math)
            long start3 = System.nanoTime();
            List<List<Integer>> res3 = generateOptimal(n);
            long end3 = System.nanoTime();

            // Result Formatting (only print output for small triangles to avoid console flood)
            if (n <= 5) {
                System.out.println("Output: " + res3);
            } else {
                System.out.println("Output: [Large Triangle Generated Successfully]");
            }

            System.out.println("Brute Force (nCr) \tTime: " + (end1 - start1) / 1000 + " us");
            System.out.println("Simulation (DP)   \tTime: " + (end2 - start2) / 1000 + " us");
            System.out.println("Optimal (Math)    \tTime: " + (end3 - start3) / 1000 + " us");

            // Verification
            boolean isMatch = res1.equals(res2) && res2.equals(res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(60) + "\n");
        }
    }
}
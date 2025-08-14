package com.questions.strivers.arrays.medium;

// https://leetcode.com/problems/set-matrix-zeroes/description/

/* https://takeuforward.org/data-structure/set-matrix-zero/
Problem Statement:
Given a matrix, if an element is 0, set its entire row and column to 0 and return the matrix.

Examples:
Example 1:
Input:  [[1,1,1],
         [1,0,1],
         [1,1,1]]

Output: [[1,0,1],
         [0,0,0],
         [1,0,1]]

Explanation: Since matrix[1][1] = 0, the 2nd row and 2nd column are set to 0.

Example 2:
Input:  [[0,1,2,0],
         [3,4,5,2],
         [1,3,1,5]]

Output: [[0,0,0,0],
         [0,4,5,0],
         [0,3,1,0]]

Explanation: Since matrix[0][0] = 0 and matrix[0][3] = 0,
the 1st row, 1st column, and 4th column are set to 0.
*/

import java.util.ArrayList;
import java.util.Arrays;

public class SetMatrixZero {

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
        matrix.add(new ArrayList<>(Arrays.asList(1, 0, 1)));
        matrix.add(new ArrayList<>(Arrays.asList(1, 1, 1)));

        int n = matrix.size();
        int m = matrix.get(0).size();

        ArrayList<ArrayList<Integer>> ans = zeroMatrix(matrix, n, m);

        System.out.println("The Final matrix is: ");
        for (ArrayList<Integer> row : ans) {
            for (Integer ele : row) {
                System.out.print(ele + " ");
            }
            System.out.println();
        }
    }

    /**
     * OPTIMAL APPROACH — In-place marker method
     *
     * Idea:
     * - Use the first row and first column as markers instead of extra arrays.
     * - col0 variable is used to handle the first column separately to avoid overwriting.
     * - First pass: mark rows and columns that need to be zeroed.
     * - Second pass: set zeroes based on markers.
     * - Finally: handle first row and first column separately.
     *
     * Time Complexity: O(n*m)
     * Space Complexity: O(1) — in-place, only extra variable col0.
     */
    static ArrayList<ArrayList<Integer>> zeroMatrix3(ArrayList<ArrayList<Integer>> matrix, int n, int m) {
        int col0 = 1;

        // Step 1: Mark the first row and column based on zero positions
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == 0) {
                    matrix.get(i).set(0, 0); // Mark first cell of row
                    if (j != 0)
                        matrix.get(0).set(j, 0); // Mark first cell of column
                    else
                        col0 = 0; // First column will need to be zeroed
                }
            }
        }

        // Step 2: Use markers to set cells to zero
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix.get(i).get(0) == 0 || matrix.get(0).get(j) == 0) {
                    matrix.get(i).set(j, 0);
                }
            }
        }

        // Step 3: Zero out the first row if needed
        if (matrix.get(0).get(0) == 0) {
            for (int j = 0; j < m; j++) {
                matrix.get(0).set(j, 0);
            }
        }

        // Step 4: Zero out the first column if needed
        if (col0 == 0) {
            for (int i = 0; i < n; i++) {
                matrix.get(i).set(0, 0);
            }
        }

        return matrix;
    }

    /**
     * BETTER APPROACH — Using two extra arrays
     *
     * Idea:
     * - Use two arrays: one for marking rows, one for marking columns.
     * - First pass: mark rows and columns containing zero.
     * - Second pass: set matrix cells to zero if their row or column is marked.
     *
     * Time Complexity: O(n*m)
     * Space Complexity: O(n + m)
     */
    static ArrayList<ArrayList<Integer>> zeroMatrix2(ArrayList<ArrayList<Integer>> matrix, int n, int m) {
        int[] row = new int[n]; // Row marker
        int[] col = new int[m]; // Column marker

        // Step 1: Mark rows and columns containing zero
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == 0) {
                    row[i] = 1;
                    col[j] = 1;
                }
            }
        }

        // Step 2: Set matrix cells to zero if their row or column is marked
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (row[i] == 1 || col[j] == 1) {
                    matrix.get(i).set(j, 0);
                }
            }
        }

        return matrix;
    }

    /**
     * BRUTE FORCE APPROACH — Using -1 markers
     *
     * Idea:
     * - On finding a zero, mark all non-zero cells in its row & column as -1 (temporary marker).
     * - After first pass, replace all -1 with 0.
     * - Ensures we don't prematurely zero out cells before processing all zeroes.
     *
     * Time Complexity: O(n*m*(n+m)) — For each zero, we traverse its row & column.
     * Space Complexity: O(1) — in-place changes, no extra storage except constants.
     */
    static void markRow(ArrayList<ArrayList<Integer>> matrix, int n, int m, int i) {
        for (int j = 0; j < m; j++) {
            if (matrix.get(i).get(j) != 0) {
                matrix.get(i).set(j, -1); // Temporary marker
            }
        }
    }

    static void markCol(ArrayList<ArrayList<Integer>> matrix, int n, int m, int j) {
        for (int i = 0; i < n; i++) {
            if (matrix.get(i).get(j) != 0) {
                matrix.get(i).set(j, -1); // Temporary marker
            }
        }
    }

    static ArrayList<ArrayList<Integer>> zeroMatrix(ArrayList<ArrayList<Integer>> matrix, int n, int m) {
        // Step 1: Mark rows & columns for zero conversion
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == 0) {
                    markRow(matrix, n, m, i);
                    markCol(matrix, n, m, j);
                }
            }
        }

        // Step 2: Convert all markers (-1) into zeroes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == -1) {
                    matrix.get(i).set(j, 0);
                }
            }
        }
        return matrix;
    }
}

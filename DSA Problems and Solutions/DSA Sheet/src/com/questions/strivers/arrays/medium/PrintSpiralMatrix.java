package com.questions.strivers.arrays.medium;

/*
Problem Statement:
Given a Matrix, print the given matrix in spiral order.

Examples:

Example 1:
Input:
Matrix[][] = {
  { 1,  2,  3,  4 },
  { 5,  6,  7,  8 },
  { 9, 10, 11, 12 },
  { 13,14, 15, 16 }
}
Output:
1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10.

Example 2:
Input:
Matrix[][] = {
  { 1, 2, 3 },
  { 4, 5, 6 },
  { 7, 8, 9 }
}
Output:
1, 2, 3, 6, 9, 8, 7, 4, 5.
*/

import java.util.ArrayList;
import java.util.List;

public class PrintSpiralMatrix {
    public static void main(String[] args) {
        int arr[][] = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        // Print the matrix for reference
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        // Call the spiral print function
        List<Integer> res = printSpiral(arr);
        System.out.println(res);
    }

    /**
     * Approach:
     * We maintain four boundaries (rowStart, rowEnd, colStart, colEnd)
     * and traverse in layers from the outside to the inside:
     * 1. Traverse from left to right (top row).
     * 2. Traverse from top to bottom (right column).
     * 3. Traverse from right to left (bottom row) — if rows remain.
     * 4. Traverse from bottom to top (left column) — if columns remain.
     * After each pass, we move the respective boundaries inward.
     *
     * This ensures we cover all elements exactly once in spiral order.
     *
     * Time Complexity: O(m * n) → Each element is visited exactly once.
     * Space Complexity: O(1) extra (excluding output list) → Only a few variables.
     */
    private static List<Integer> printSpiral(int arr[][]) {
        // Initialize boundaries
        int rowStart = 0, rowEnd = arr.length - 1;
        int colStart = 0, colEnd = arr[0].length - 1;

        List<Integer> res = new ArrayList<>();

        // Loop until boundaries overlap
        while (rowStart <= rowEnd && colStart <= colEnd) {

            // 1. Traverse top row from left to right
            for (int i = colStart; i <= colEnd; i++) {
                res.add(arr[rowStart][i]);
            }

            // 2. Traverse right column from top to bottom
            for (int i = rowStart + 1; i <= rowEnd; i++) {
                res.add(arr[i][colEnd]);
            }

            // 3. Traverse bottom row from right to left (if more than one row remains)
            for (int i = colEnd - 1; i >= colStart; i--) {
                if (rowStart == rowEnd) { // Avoid double-counting if single row left
                    break;
                }
                res.add(arr[rowEnd][i]);
            }

            // 4. Traverse left column from bottom to top (if more than one column remains)
            for (int i = rowEnd - 1; i >= rowStart + 1; i--) {
                if (colStart == colEnd) { // Avoid double-counting if single column left
                    break;
                }
                res.add(arr[i][colStart]);
            }

            // Move the boundaries inward
            rowStart++;
            rowEnd--;
            colStart++;
            colEnd--;
        }

        return res;
    }
}

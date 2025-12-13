package com.questions.strivers.arrays.medium;

/* https://takeuforward.org/data-structure/rotate-image-by-90-degree/
Problem Statement:
Given a matrix, rotate the matrix 90 degrees clockwise in-place if possible.

Example 1:
Input:  [[1,2,3],
         [4,5,6],
         [7,8,9]]
Output: [[7,4,1],
         [8,5,2],
         [9,6,3]]

Example 2:
Input:  [[5,1,9,11],
         [2,4,8,10],
         [13,3,6,7],
         [15,14,12,16]]
Output: [[15,13,2,5],
         [14,3,4,1],
         [12,6,8,9],
         [16,7,10,11]]
 */

public class RotateMatrix90 {

    public static void main(String[] args) {
        int arr[][] = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        // Call optimal rotation
        rotateMatrix1(arr);

        // Print rotated matrix
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Optimal Approach: Rotate matrix in-place by first transposing it
     * and then reversing each row.
     *
     * Steps:
     * 1. Transpose the matrix: swap matrix[i][j] with matrix[j][i].
     * 2. Reverse each row to complete the 90° clockwise rotation.
     *
     * Time Complexity: O(N^2) → Every element is visited twice (transpose + reverse).
     * Space Complexity: O(1) → Done in-place, no extra matrix is used.
     */
    private static void rotateMatrix1(int matrix[][]) {
        int n = matrix.length;

        // Step 1: Transpose the matrix (convert rows to columns)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Swap element at (i, j) with element at (j, i)
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                // Swap first and last elements of the row moving inward
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    /**
     * Brute Force Approach: Create a new matrix and fill it with rotated values.
     * Mapping rule:
     * Element at (i, j) in original → (j, N - 1 - i) in rotated matrix.
     *
     * Time Complexity: O(N^2) → Each element is moved once.
     * Space Complexity: O(N^2) → Requires a separate matrix of same size.
     */
    private static void rotateMatrix(int arr[][]) {
        int n = arr.length;
        int ans[][] = new int[n][n];

        // Fill new matrix with rotated values
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[j][n - 1 - i] = arr[i][j];
            }
        }

        // Copy rotated values back to original array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = ans[i][j];
            }
        }
    }
}

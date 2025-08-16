package com.questions.strivers.binarysearch.bson2darray;

/*
Problem: Find a peak element in a 2D grid.
A peak element mat[i][j] is strictly greater than its adjacent neighbors
(top, bottom, left, right).

Constraints:
- No two adjacent cells are equal.
- You can assume the grid is surrounded by -1s outside the boundary.
- Required time complexity: O(m log n) or O(n log m).

Approach:
1. We will apply binary search on columns (you can also do it on rows).
2. At each mid-column:
    a) Find the global maximum element in this column (since a peak must be >= neighbors).
    b) Compare this max element with its left and right neighbors.
    c) If this element is greater than both neighbors → it's a peak, return its coordinates.
    d) Otherwise, move binary search to the half (left/right) where a greater neighbor exists.
3. Continue until peak is found.

Why this works?
- Since the matrix has no two adjacent equal elements, moving in the direction
  of a greater neighbor guarantees that a peak exists in that half.

Time Complexity:
- For each column in binary search (log n steps),
  we scan m rows to find the maximum element → O(m log n).
- If we instead binary search on rows, it would be O(n log m).
- Both satisfy constraints.

Space Complexity:
- O(1), only variables used.
*/
public class FindPeakElement2DMatrix {

    public static int[] findPeakGrid(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int low = 0, high = n - 1; // binary search on columns

        while (low <= high) {
            int mid = (low + high) / 2;

            // Step 1: Find row index of max element in this column
            int maxRow = 0;
            for (int i = 0; i < m; i++) {
                if (mat[i][mid] > mat[maxRow][mid]) {
                    maxRow = i;
                }
            }

            // Step 2: Compare with left and right neighbors
            int left = (mid - 1 >= 0) ? mat[maxRow][mid - 1] : -1;
            int right = (mid + 1 < n) ? mat[maxRow][mid + 1] : -1;

            // Step 3: Check if this is a peak
            if (mat[maxRow][mid] > left && mat[maxRow][mid] > right) {
                return new int[]{maxRow, mid}; // found peak
            }

            // Step 4: Move search space
            if (mat[maxRow][mid] < left) {
                high = mid - 1; // search left half
            } else {
                low = mid + 1;  // search right half
            }
        }

        return new int[]{-1, -1}; // should never reach here
    }

    // Driver Code
    public static void main(String[] args) {
        int[][] mat1 = {{1, 4}, {3, 2}};
        int[][] mat2 = {{10, 20, 15}, {21, 30, 14}, {7, 16, 32}};

        int[] peak1 = findPeakGrid(mat1);
        System.out.println("Peak in mat1: [" + peak1[0] + ", " + peak1[1] + "]");

        int[] peak2 = findPeakGrid(mat2);
        System.out.println("Peak in mat2: [" + peak2[0] + ", " + peak2[1] + "]");
    }

}

package com.questions.strivers.graph.problemsbfsdfs;

/**
 * ================================= FLOOD FILL (DFS APPROACH) =================================
 *
 * Problem:
 * --------
 * You are given a 2D image represented as a matrix, where:
 *  - image[r][c] = color value of pixel at row r and column c
 *
 * You are given:
 *  - starting pixel: (startRow, startCol)
 *  - newColor: color to apply
 *
 * Flood Fill Rule:
 * ----------------
 * Change the color of the starting pixel AND
 * all pixels that are:
 *   1️⃣ Connected 4-directionally (Up, Right, Down, Left)
 *   2️⃣ Have the SAME initial color as the starting pixel
 *
 * Return the modified image.
 *
 * Example:
 * --------
 * Input:
 * image =
 *  [1 1 1
 *   1 1 0
 *   1 0 1]
 *
 * start = (1, 1), newColor = 2
 *
 * Output:
 *  [2 2 2
 *   2 2 0
 *   2 0 1]
 *
 * Explanation:
 * All connected 1's around starting pixel (1,1) are recolored to 2.
 *
 * =============================================================================================
 */

public class FloodFillDfs {

    /**
     * Main Flood Fill function (Driver)
     *
     * @param image      Input 2D matrix representing the image
     * @param startRow   Starting pixel row
     * @param startCol   Starting pixel column
     * @param newColor   Color to apply
     * @return           Final modified image
     */
    private static int[][] floodFill(int[][] image, int startRow, int startCol, int newColor) {

        // Store original color of starting pixel
        int initColor = image[startRow][startCol];

        // If the color is already the same as newColor → No need to process
        // Avoids infinite recursion for already colored region
        if (initColor == newColor) return image;

        // We will modify the image itself
        int[][] ans = image;

        // Arrays representing 4-directional movement:
                        // Up, Right, Down, Left
        int[] delRow =  { -1,   0,     1,    0};
        int[] delCol =  { 0,    1,     0,   -1};

        // Call DFS starting from given pixel
        dfs(startRow, startCol, ans, newColor, delRow, delCol, initColor);

        return ans;
    }

    /**
     * Depth First Search helper to recolor all connected pixels
     *
     * @param row        Current pixel row
     * @param col        Current pixel column
     * @param ans        Final answer matrix
     * @param newColor   Color to apply
     * @param delRow     Row direction array
     * @param delCol     Column direction array
     * @param initColor  Original color to match
     */
    private static void dfs(int row, int col,
                            int[][] ans,
                            int newColor,
                            int[] delRow,
                            int[] delCol,
                            int initColor) {

        // Color the current pixel
        ans[row][col] = newColor;

        int n = ans.length;
        int m = ans[0].length;

        // Check in 4 directions
        for (int i = 0; i < 4; i++) {

            int nRow = row + delRow[i];
            int nCol = col + delCol[i];

            // Boundary check
            // Pixel must:
            // 1️⃣ Be inside grid
            // 2️⃣ Have same color as initial
            // 3️⃣ Not already colored with newColor
            if (nRow >= 0 && nRow < n && nCol >= 0 && nCol < m && ans[nRow][nCol] == initColor) {
                // Recur for neighboring cell
                dfs(nRow, nCol, ans, newColor, delRow, delCol, initColor);
            }
        }
    }

    // =============================== MAIN METHOD (TESTING) ================================
    public static void main(String[] args) {

        int[][] image = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };

        int startRow = 1;
        int startCol = 1;
        int newColor = 2;

        int[][] result = floodFill(image, startRow, startCol, newColor);

        System.out.println("Flood Filled Image:");
        for (int[] row : result) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}

/*
=================================================================================================
APPROACH / INTUITION (DFS)
=================================================================================================

We treat the image as a grid (graph).
Each pixel is a node.
Edges exist between 4-directionally connected pixels.

Steps:
------
1️⃣ Take starting pixel color
2️⃣ Perform DFS
3️⃣ Recolor every connected pixel that:
    - Lies within boundaries
    - Has SAME initial color
4️⃣ Stop when:
    - Boundary is reached
    - Different color encountered
    - Pixel already recolored

Why DFS Works?
--------------
DFS explores all connected neighbors deeply.
This is exactly what Flood Fill needs → paint connected region completely.

When To Use DFS Flood Fill?
---------------------------
✔ When recursion is acceptable
✔ When exploring connected components in grids
✔ For image processing, region filling, island problems

Limitations:
------------
❌ Deep recursion may cause stack overflow for very large grids
❌ BFS is safer when grid is very large


=================================================================================================
TIME & SPACE COMPLEXITY
=================================================================================================

Let N = number of rows
Let M = number of columns

Time Complexity:  O(N * M)
---------------------------------
Worst case:
We may visit every cell exactly once.

Space Complexity: O(N * M)
---------------------------------
Due to recursion stack in worst case (all cells same color).


=================================================================================================
ALTERNATIVE APPROACHES
=================================================================================================

1️⃣ BFS Flood Fill (Queue Based)
--------------------------------
Use queue instead of recursion.
Safer for large grids.
Same Time & Space complexity.

Tradeoffs:
DFS = simpler to write
BFS = avoids stack overflow


2️⃣ Iterative DFS Using Stack
-----------------------------
Use manual stack instead of recursion.
Eliminates recursion depth risk.


=================================================================================================
INTERVIEW TIPS
=================================================================================================
✔ Always explain boundary check logic
✔ Mention stack overflow risk in DFS
✔ Mention BFS alternative
✔ Clarify 4-direction vs 8-direction flood fill
✔ State complexity confidently

=================================================================================================
*/

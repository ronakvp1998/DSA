package com.questions.strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================= FLOOD FILL (BFS APPROACH) ==================================
 *
 * Problem:
 * --------
 * Given a 2D image (matrix) where each cell represents a pixel's color, perform a "Flood Fill".
 *
 * You are given:
 *   - startRow, startCol → starting position of pixel
 *   - newColor → color we want to apply
 *
 * Flood Fill Definition:
 * ----------------------
 * Change the color of the starting pixel AND every pixel connected to it
 * 4-directionally (up, right, down, left)
 * IF AND ONLY IF they share the same INITIAL color.
 *
 * Return the updated image.
 *
 * -------------------------------- Example --------------------------------
 *
 * image =
 *   [1 1 1
 *    1 1 0
 *    1 0 1]
 *
 * start = (1,1), newColor = 2
 *
 * Output =
 *   [2 2 2
 *    2 2 0
 *    2 0 1]
 *
 * ================================================================================================
 */

public class FloodFillBfs {

    /**
     * Performs flood fill using BFS
     */
    private static int[][] floodFill(int[][] image, int startRow, int startCol, int newColor) {

        int initColor = image[startRow][startCol];

        // If the starting pixel already has the new color,
        // no need to process further (prevents infinite loops).
        if (initColor == newColor) return image;

        int n = image.length;
        int m = image[0].length;

        // Queue for BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});

        // Update the starting pixel immediately
        image[startRow][startCol] = newColor;

        // 4-direction movement arrays
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};

        // BFS traversal
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            // Check all 4 neighbors
            for (int i = 0; i < 4; i++) {
                int nRow = row + delRow[i];
                int nCol = col + delCol[i];

                // Valid boundaries & same initial color
                if (nRow >= 0 && nRow < n &&
                        nCol >= 0 && nCol < m &&
                        image[nRow][nCol] == initColor) {

                    // Change color
                    image[nRow][nCol] = newColor;

                    // Push into queue for further BFS exploration
                    queue.add(new int[]{nRow, nCol});
                }
            }
        }

        return image;
    }

    // =================================== MAIN TESTING ===========================================
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

        System.out.println("Flood Filled Image (BFS):");
        for (int[] row : result) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}

/*
=================================================================================================
APPROACH (BFS)
=================================================================================================

We use a queue to perform a level-order traversal (Breadth First Search).

Steps:
------
1️⃣ Take the initial color at starting pixel
2️⃣ Push starting pixel into queue
3️⃣ Change its color to newColor
4️⃣ Pop element from queue
5️⃣ Check all 4 neighbors:
      - Inside boundaries
      - Have the same initial color
6️⃣ Push valid neighbors to queue & recolor them
7️⃣ Continue until queue is empty

This ensures we flood fill the region in all directions.

=================================================================================================
WHY BFS?
=================================================================================================
✔ Avoids recursion & stack overflow
✔ Good for large grids
✔ Natural for level-by-level area filling (like paint buckets)

=================================================================================================
TIME & SPACE COMPLEXITY
=================================================================================================

Let N = number of rows
Let M = number of columns

Time Complexity: **O(N × M)**
- Each pixel is processed at most once.

Space Complexity: **O(N × M)**
- Queue may hold many pixels at worst.

=================================================================================================
ALTERNATIVE APPROACHES
=================================================================================================

1️⃣ **DFS (Recursive)**
   - Easy to write
   - Risk of stack overflow for large grids
   - Same time complexity

2️⃣ **DFS Iterative (Using Stack)**
   - Avoids recursion
   - Similar to BFS but uses stack

3️⃣ **Multi-source Flood Fill**
   - If multiple start points are given

=================================================================================================
INTERVIEW NOTES
=================================================================================================
✔ Mention BFS vs DFS trade-offs
✔ Explain boundary checks clearly
✔ Mention early exit optimization (initColor == newColor)
✔ Time/space correctness is crucial

=================================================================================================
*/

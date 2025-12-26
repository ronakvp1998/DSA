package com.questions.strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ==================================================================================================
 *                                        542. 01 MATRIX
 * ==================================================================================================
 *
 * -------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given an m x n binary matrix mat, return a matrix where each cell contains the
 * distance to the nearest cell having value **0**.
 *
 * The distance between two cells that share a common edge is considered as 1.
 *
 * For every cell mat[i][j], we must compute:
 *      dist[i][j] = minimum number of steps required to reach the nearest cell containing 0
 *
 * Distance Rules:
 *  - Distance is measured via minimum number of moves
 *  - Movement allowed only in 4 directions:
 *        Up    → (row - 1 , col)
 *        Down  → (row + 1 , col)
 *        Left  → (row , col - 1)
 *        Right → (row , col + 1)
 *
 * If a cell itself is 0 → its distance = 0
 *
 * --------------------------------------------------------------------------------------------------
 *                             APPROACH — MULTI-SOURCE BFS (Optimal)
 * --------------------------------------------------------------------------------------------------
 *
 * 🔑 KEY IDEA
 * Instead of starting BFS from each **1** (which would be very slow),
 * we start BFS from **all 0s simultaneously**.
 *
 * Treat every 0 as a source node and push all of them into the queue initially.
 * These 0s expand outward layer-by-layer, and the first time they reach any cell,
 * that distance is guaranteed to be the shortest.
 *
 * ---------------------------------- STEPS ---------------------------------------------------------
 * 1️⃣ Push all cells containing 0 into a queue
 * 2️⃣ Mark them visited and assign distance = 0
 * 3️⃣ Run BFS:
 *        - Each outward level adds +1 distance
 *        - First time a cell is reached → shortest distance found
 *
 * --------------------------------- WHY BFS WORKS? -------------------------------------------------
 * BFS processes nodes level-wise (in increasing order of distance).
 * Since all 0s start first, they expand like multiple waves.
 * The earliest wave reaching a cell guarantees minimum distance to some 0.
 *
 * ---------------------------------- COMPLEXITY ----------------------------------------------------
 * Time Complexity  : O(n * m)
 *      Each cell is pushed and processed at most once.
 *
 * Space Complexity : O(n * m)
 *      Queue + visited matrix + distance matrix
 *
 * -------------------------------- EDGE CASES ------------------------------------------------------
 * ✔ Matrix containing only 0s → output all 0s
 * ✔ Matrix with single row / column
 * ✔ Large matrix (up to 10⁴ cells)
 * ✔ Problem guarantees at least one 0
 *
 * --------------------------------- ALTERNATIVE APPROACHES ----------------------------------------
 *
 * 1️⃣ Dynamic Programming (Two-Pass Method)
 *    - First pass  → Top-Left → Bottom-Right
 *    - Second pass → Bottom-Right → Top-Left
 *    - Update distances based on neighbors
 *
 *    ➕ Lower extra space usage
 *    ➖ Trickier to implement compared to BFS
 *
 * 2️⃣ DFS ❌ (NOT Recommended)
 *    DFS is depth-first and does not naturally give shortest distance.
 *    Would require revisits + extra tracking → inefficient.
 *
 * -------------------------------- INTERVIEW TIP ---------------------------------------------------
 * Whenever asked:
 *      “distance to nearest X”
 * Use **Multi-Source BFS**
 * Push all X initially instead of BFS from each cell separately.
 *
 * --------------------------------------------------------------------------------------------------
 */

public class DistanceCellHaving0 {

    /**
     * Helper class used in BFS
     * Stores:
     * nRow  → row index
     * nCol  → column index
     * steps → distance travelled so far
     */
    static class Node {
        int nRow;
        int nCol;
        int steps;

        public Node(int nRow, int nCol, int steps) {
            this.nRow = nRow;
            this.nCol = nCol;
            this.steps = steps;
        }
    }

    /**
     * Function that returns matrix containing
     * distance of nearest 0 for every cell
     */
    private static int[][] nearest(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // Visited matrix → ensures each cell is processed only once
        int[][] vis = new int[n][m];

        // Output distance matrix
        int[][] dist = new int[n][m];

        // Queue for BFS
        Queue<Node> queue = new LinkedList<>();

        /**
         * STEP 1:
         * Push all cells containing 0 into queue
         * because these are the starting sources
         * Distance for them = 0
         */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (grid[i][j] == 0) {

                    // Put this 0 into BFS queue
                    queue.add(new Node(i, j, 0));

                    // Mark as visited
                    vis[i][j] = 1;

                } else {
                    vis[i][j] = 0; // Not visited initially
                }
            }
        }

        // 4-directional movement arrays
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};

        /**
         * STEP 2:
         * Perform Standard BFS
         */
        while (!queue.isEmpty()) {

            Node temp = queue.remove();

            int row = temp.nRow;
            int col = temp.nCol;
            int step = temp.steps;

            // Store current shortest distance
            dist[row][col] = step;

            // Explore all 4 connected neighbors
            for (int i = 0; i < 4; i++) {

                int nRow = row + delRow[i];
                int nCol = col + delCol[i];

                /**
                 * Conditions to move:
                 * ✔ Within matrix boundary
                 * ✔ Not already visited
                 */
                if (nRow >= 0 && nRow < n &&
                        nCol >= 0 && nCol < m &&
                        vis[nRow][nCol] == 0) {

                    vis[nRow][nCol] = 1; // mark visited
                    queue.add(new Node(nRow, nCol, step + 1)); // push next level
                }
            }
        }

        return dist;
    }


    /**
     * --------------------------------- MAIN METHOD FOR TESTING ----------------------------------
     */
    public static void main(String[] args) {

        int[][] grid = {
                {0, 0, 1},
                {0, 1, 1},
                {1, 1, 0}
        };

        int[][] ans = nearest(grid);

        System.out.println("Nearest Distance to 0 Matrix:");
        for (int[] row : ans) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}

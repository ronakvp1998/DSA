package strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ==================================================================================================
 *                                    🔥 Number of Enclaves 🔥
 * ==================================================================================================
 *
 * ------------------------------------ PROBLEM STATEMENT -------------------------------------------
 * You are given an N x M binary matrix 'grid' where:
 *      0 → represents SEA
 *      1 → represents LAND
 *
 * A "move" means:
 *      You can move from one land cell to another land cell ONLY if they share a side
 *      (i.e., movement allowed in 4 directions: Up, Down, Left, Right).
 *
 * A land cell is considered SAFE if it can eventually walk off the grid boundary.
 *
 * We need to count how many land cells are completely enclosed and
 * can NEVER reach the boundary in any number of moves.
 *
 * Simply,
 *      ✔ Count cells = 1 (land)
 *      ✔ BUT cannot reach boundary
 *
 * Output → Number of such "enclave" land cells.
 *
 *
 * --------------------------------------------------------------------------------------------------
 *                        ✅ APPROACH — BFS From Boundary (Multi-Source BFS)
 * --------------------------------------------------------------------------------------------------
 *
 * 🔑 KEY OBSERVATION
 * Land cells that CAN escape must somehow connect to the **boundary land cells**.
 *
 * So instead of searching from every land cell inward
 * → We start from the **boundary land cells**
 * → Spread inward using BFS
 * → Mark every reachable land cell as SAFE
 *
 * Whatever land remains unvisited after this = ENCLOSED LAND
 *
 *
 * ------------------------------------- ALGORITHM ---------------------------------------------------
 *
 * 1️⃣ Traverse the boundary of the matrix
 *      If a boundary cell is LAND (1)
 *          → Push it into queue
 *          → Mark visited
 *
 * 2️⃣ Perform BFS
 *      From each boundary land cell, spread inward
 *      Mark all reachable land cells as visited (SAFE)
 *
 * 3️⃣ Finally count
 *      Land cells that are NOT visited = Enclave cells
 *
 *
 * -------------------------------- WHY THIS WORKS? --------------------------------------------------
 * BFS ensures we capture **all connected land masses**
 * that can escape through at least one boundary.
 *
 * Any land not touched by this BFS wave
 * must be trapped inside (cannot reach border).
 *
 *
 * ---------------------------------- COMPLEXITY -----------------------------------------------------
 *
 * Time Complexity  :  O(N * M)
 *      - Each cell is processed at most once in BFS
 *
 * Space Complexity : O(N * M)
 *      - Visited matrix
 *      - BFS Queue (worst case stores all land cells)
 *
 *
 * ---------------------------------- EDGE CASES -----------------------------------------------------
 * ✔ Grid full of sea → answer = 0
 * ✔ Grid full of land but touching border → all are safe → 0 enclaves
 * ✔ Island floating inside → counted correctly
 * ✔ Single row / single column handled naturally
 *
 *
 * -------------------------------- ALTERNATIVE APPROACHES ------------------------------------------
 *
 * 1️⃣ DFS Instead of BFS
 *    We can use recursive DFS instead of BFS queue.
 *    But BFS is safer due to recursion depth limitation in large inputs.
 *
 * 2️⃣ Modify Grid Instead of Visited Matrix
 *    Instead of boolean vis[][], we could directly convert reachable land → 0
 *    Saves extra space but modifies input.
 *
 *
 * -------------------------------- INTERVIEW NOTE ---------------------------------------------------
 * Whenever asked:
 *      ✔ "Count enclosed regions"
 *      ✔ "Number of closed islands"
 *      ✔ "Regions not touching boundary"
 *
 * → Think **Reverse BFS/DFS from boundary** instead of checking each island.
 *
 * --------------------------------------------------------------------------------------------------
 */

public class NumOfEnclavesBSF {

    /**
     * Function to compute number of land cells that cannot reach boundary
     */
    private static int numberOfEnclaves(int[][] grid) {

        // Handle invalid / empty grid edge case
        if (grid == null || grid.length == 0 || grid[0].length == 0)
            return 0;

        int n = grid.length;
        int m = grid[0].length;

        // Visited matrix → ensures we don't count same land twice
        boolean[][] vis = new boolean[n][m];

        // Queue for BFS traversal
        Queue<int[]> q = new LinkedList<>();

        /**
         * -------------------------------- STEP 1 --------------------------------
         * Traverse the boundary
         * If a boundary cell is LAND → start BFS from there
         */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Check whether cell sits on boundary
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {

                    // If boundary contains land and not visited
                    if (grid[i][j] == 1 && !vis[i][j]) {

                        vis[i][j] = true;          // mark safe
                        q.add(new int[]{i, j});    // push for BFS
                    }
                }
            }
        }

        // Direction arrays → Up, Right, Down, Left
        int[] delrow = {-1, 0, 1, 0};
        int[] delcol = {0, 1, 0, -1};

        /**
         * -------------------------------- STEP 2 --------------------------------
         * BFS — Mark every land reachable from boundary as SAFE
         */
        while (!q.isEmpty()) {

            int[] cur = q.poll();
            int row = cur[0];
            int col = cur[1];

            // Explore 4 neighbors
            for (int k = 0; k < 4; k++) {

                int nrow = row + delrow[k];
                int ncol = col + delcol[k];

                // Valid bounds + unvisited + land check
                if (nrow >= 0 && nrow < n &&
                        ncol >= 0 && ncol < m &&
                        !vis[nrow][ncol] &&
                        grid[nrow][ncol] == 1) {

                    vis[nrow][ncol] = true;       // mark reachable
                    q.add(new int[]{nrow, ncol}); // push forward
                }
            }
        }

        /**
         * -------------------------------- STEP 3 --------------------------------
         * Count land cells that are NOT visited
         * These are enclaves
         */
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (grid[i][j] == 1 && !vis[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * -------------------------------- MAIN METHOD (TESTING) --------------------------------
     */
    public static void main(String[] args) {

        int[][] grid = {
                {0, 0, 0, 0},
                {1, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        System.out.println("Number of Enclave Land Cells = " + numberOfEnclaves(grid));
        // Expected Output → 3
    }
}

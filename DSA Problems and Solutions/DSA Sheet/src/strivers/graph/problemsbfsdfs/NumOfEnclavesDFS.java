package com.questions.strivers.graph.problemsbfsdfs;

/**
 * ========================= Problem: Number of Enclaves (DFS) =========================
 *
 * You are given a binary matrix "grid" of size N x M where:
 *  -> 1 represents LAND
 *  -> 0 represents WATER
 *
 * A cell is an ENCLAVE if:
 *  -> It is land (value = 1)
 *  -> It CANNOT reach the boundary of the grid by moving only
 *     UP, DOWN, LEFT, or RIGHT through land cells.
 *
 * We must return:
 *  -> Count of such land cells that are completely enclosed by water
 *
 * ---------------------------- Intuition ------------------------------------
 * Any land cell that is connected to the boundary is NOT an enclave,
 * because it has a path to the outside world.
 *
 * Therefore:
 * 1️⃣ Start DFS from ALL boundary land cells.
 * 2️⃣ Mark all land cells connected to the boundary as visited.
 * 3️⃣ Any remaining unvisited land cell is an ENCLAVE → Count it.
 *
 * ------------------------------ Example ------------------------------------
 *
 * grid =
 * 0 0 0 0
 * 1 0 1 0
 * 0 1 1 0
 * 0 0 0 0
 *
 * Boundary land reachable region gets removed.
 * Remaining = 3 enclave land cells → Output = 3
 *
 * ------------------------------ Edge Cases ---------------------------------
 * ✔️ No enclave exists (answer = 0)
 * ✔️ All land (everything touches boundary → answer = 0)
 * ✔️ Single cell grid
 * ✔️ Large grid
 *
 * ----------------------------------------------------------------------------
 */

public class NumOfEnclavesDFS {

    /**
     * Main function to count number of enclave land cells.
     */
    private static int numberOfEnclaves(int[][] grid) {

        int n = grid.length;        // total rows
        int m = grid[0].length;     // total columns

        // Visited matrix to ensure we don't reprocess cells
        boolean[][] vis = new boolean[n][m];

        // Step 1 -> Run DFS from all boundary land cells
        // Boundary means: first row, last row, first col, last col
        for (int i = 0; i < n; i++) {             // iterate rows
            for (int j = 0; j < m; j++) {         // iterate columns

                // Check if cell lies on boundary
                boolean isBoundary =
                        (i == 0 || j == 0 || i == n - 1 || j == m - 1);

                // If boundary cell contains land and not visited -> DFS
                if (isBoundary && grid[i][j] == 1 && !vis[i][j]) {
                    dfs(i, j, grid, vis);
                }
            }
        }

        // Step 2 -> Count cells which are land but NOT visited
        // These are enclave cells
        int count = 0;

        for (int i = 0; i < n; i++) {         // traverse grid again
            for (int j = 0; j < m; j++) {
                // Land but not boundary connected => enclave
                if (grid[i][j] == 1 && !vis[i][j]) {
                    count++;
                }
            }
        }

        return count;   // return total enclave cells
    }

    /**
     * DFS method to mark all connected land cells as visited
     * starting from a given land cell.
     */
    private static void dfs(int row, int col, int[][] grid, boolean[][] vis) {

        vis[row][col] = true;        // mark current cell as visited

        int n = grid.length;
        int m = grid[0].length;

        // Arrays to move in 4 directions (Up, Right, Down, Left)
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};

        // Explore all 4 neighbors
        for (int k = 0; k < 4; k++) {

            int newRow = row + delRow[k];
            int newCol = col + delCol[k];

            // Check boundary, check land, and check not visited
            if (newRow >= 0 && newRow < n &&
                    newCol >= 0 && newCol < m &&
                    grid[newRow][newCol] == 1 &&
                    !vis[newRow][newCol]) {

                // Continue DFS
                dfs(newRow, newCol, grid, vis);
            }
        }
    }

    /**
     * =============================== MAIN METHOD ============================
     * For testing the solution
     */
    public static void main(String[] args) {

        int[][] grid = {
                {0, 0, 0, 0},
                {1, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        // Expected Output: 3
        System.out.println("Number of Enclaves = " + numberOfEnclaves(grid));
    }
}


/**
 * ======================== APPROACH EXPLANATION (Interview Friendly) ======================
 *
 * Approach Used → DFS + Boundary Flood Fill
 *
 * Step-by-step reasoning:
 * 1️⃣ Any land connected to boundary is NOT enclave.
 * 2️⃣ So instead of finding enclaves directly,
 *     we eliminate all NON-enclave land first.
 * 3️⃣ We start DFS from every boundary land cell,
 *     marking all connected land cells as visited.
 * 4️⃣ After DFS completes:
 *     → visited land = boundary connected
 *     → remaining land = enclaves
 *
 * ---------------------------------------------
 * ✔️ Why this approach works?
 * Because enclaves are exactly those land cells that
 * cannot escape to boundary. Removing escapable land reveals them.
 *
 * ---------------------------------------------
 * When to use?
 * ✔️ Grid problems
 * ✔️ Flood fill
 * ✔️ Problems involving “surrounded areas”
 *
 * ---------------------------------------------
 * Drawbacks / Limitations
 * ❌ Uses recursion → risk of stack overflow for extremely large grids
 * ❌ Uses extra O(N*M) visited space
 *
 * ---------------------------------------------
 * Possible Alternatives
 *
 * 1️⃣ BFS Approach
 * - Use Queue instead of recursion
 * - Same logic (start from boundaries)
 * - Better for very deep recursion cases
 *
 * 2️⃣ In-place Marking (Without extra visited)
 * - Modify grid itself instead of using visited[]
 * - Converts visited boundary land to some temp marker
 * - Saves space → O(1)
 *
 * Trade-off:
 * - BFS avoids recursion risk
 * - DFS simpler & cleaner
 *
 * ---------------------------------------------
 * Time & Space Complexity
 *
 * Time Complexity → O(N × M)
 * Because:
 * - Every cell is processed at most once
 *
 * Space Complexity → O(N × M)
 * Because:
 * - Visited matrix
 * - DFS recursion stack worst case O(N * M)
 *
 * Best Case:
 * - All water → very fast → still O(N*M) to scan
 *
 * Worst Case:
 * - All land
 * - DFS visits entire grid
 *
 * ---------------------------------------------------------------------------------------
 */

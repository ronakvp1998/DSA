package com.questions.strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================= NUMBER OF ISLANDS (BFS - 8 Directional) ================================
 *
 * Problem Statement:
 * ------------------
 * You are given a grid of size N x M consisting of:
 *   '1' → Land
 *   '0' → Water
 *
 * An ISLAND is a group of connected land cells. Two land cells are considered
 * connected if they touch in ANY of the following 8 directions:
 *
 *                  ↖  ↑  ↗
 *                  ←  *  →
 *                  ↙  ↓  ↘
 *
 * Your task is to return the TOTAL NUMBER OF DISTINCT ISLANDS.
 *
 *
 * =========================================================================================================
 * APPROACH → BFS (Breadth First Search)
 * =========================================================================================================
 *
 * 🔹 Step-By-Step Logic:
 * ----------------------
 * 1️⃣ Traverse every cell in the grid
 * 2️⃣ When we find a land cell ('1') that is NOT visited:
 *        → This means we discovered a NEW island
 *        → Increase island count
 *        → Start BFS from this cell
 *
 * 3️⃣ BFS explores ALL connected land cells in 8 directions
 *     and marks them visited so the same island is not counted again.
 *
 * 4️⃣ Continue scanning the grid
 *     Any new unvisited '1' found means a completely different island.
 *
 *
 * =========================================================================================================
 * WHY BFS WORKS?
 * =========================================================================================================
 * BFS explores level-by-level (or layer-by-layer).
 * Once BFS starts from a land cell, it covers the WHOLE island connected to it.
 * So when BFS ends, we are guaranteed that:
 *    ✔ Every cell belonging to that island is visited
 *    ✔ The island will never be counted twice
 *
 *
 * =========================================================================================================
 * TIME & SPACE COMPLEXITY
 * =========================================================================================================
 *
 * Let N = number of rows, M = number of columns
 *
 * Time Complexity  →  O(N × M)
 * --------------------------------
 * Every cell is visited at most once
 * BFS processes each cell only once
 *
 * Space Complexity →  O(N × M)
 * --------------------------------
 * ✔ Visited matrix stores N*M states
 * ✔ Queue may store all cells of one large island in worst case
 *
 *
 * =========================================================================================================
 * EDGE CASES
 * =========================================================================================================
 * ✔ Grid completely empty or null → answer = 0
 * ✔ All water grid → answer = 0
 * ✔ All land grid → answer = 1
 * ✔ Land cells only diagonally connected → MUST BE SAME island
 *
 *
 * =========================================================================================================
 * ALTERNATIVE APPROACHES
 * =========================================================================================================
 *
 * 1️⃣ DFS (Depth First Search)
 * ----------------------------
 * - Uses recursion instead of queue
 * - Simpler to write
 * - But may cause stack overflow in huge grids
 *
 * 2️⃣ Disjoint Set Union (Union-Find)
 * ------------------------------------
 * - Treat each land as a node
 * - Union adjacent lands
 * - Count number of unique parents
 * - Useful when:
 *      ✔ Grid updates frequently
 *      ✔ Multiple queries required
 * - Harder to implement
 *
 *
 * =========================================================================================================
 * LIMITATIONS
 * =========================================================================================================
 * BFS needs extra space for queue
 * DFS may cause recursion depth issues
 *
 */
public class NumOfDistinctIslandsBFS {

    /**
     * BFS to explore and mark all connected land cells of ONE island
     */
    private static void bfs(int row, int col, boolean[][] vis, char[][] grid) {

        // Queue to support BFS, storing cell coordinates
        Queue<int[]> q = new LinkedList<>();

        // Push starting cell and mark as visited
        q.add(new int[]{row, col});
        vis[row][col] = true;

        // Row & Column movement arrays for all 8 directions
        int[] drow = {-1,-1,-1, 0, 1, 1, 1, 0};
        int[] dcol = {-1, 0, 1, 1, 1, 0,-1,-1};

        // Start BFS traversal
        while (!q.isEmpty()) {

            int[] cell = q.poll();
            int r = cell[0];
            int c = cell[1];

            // Explore all 8 directions
            for (int i = 0; i < 8; i++) {
                int nr = r + drow[i];   // target row
                int nc = c + dcol[i];   // target column

                // Conditions to allow visiting:
                // 1. Must lie inside grid boundaries
                // 2. Must NOT be visited earlier
                // 3. Must be land ('1')
                if (nr >= 0 && nr < grid.length &&
                        nc >= 0 && nc < grid[0].length &&
                        !vis[nr][nc] && grid[nr][nc] == '1') {

                    // Mark visited and push in queue for BFS
                    vis[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }
    }

    /**
     * Counts number of islands using BFS
     */
    private static int numIslands(char[][] grid) {

        int n = grid.length;        // total rows
        int m = grid[0].length;     // total columns

        // visited matrix to prevent reprocessing
        boolean[][] vis = new boolean[n][m];

        int count = 0; // stores number of islands

        // Traverse whole grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // New island detected → BFS it!
                if (!vis[i][j] && grid[i][j] == '1') {
                    count++;                // new island found
                    bfs(i, j, vis, grid);   // explore complete island
                }
            }
        }

        return count;
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {

        char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };

        // Expected Output → 3
        System.out.println("Number of Islands: " + numIslands(grid));
    }
}

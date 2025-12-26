package com.questions.strivers.graph.problemsbfsdfs;

/**
 * ================================= NUMBER OF ISLANDS (DFS - 8 Directional) ================================
 *
 * Problem Statement:
 * ------------------
 * You are given a grid of size N x M consisting of:
 *     '1' → Land
 *     '0' → Water
 *
 * An ISLAND is a group of connected '1's. Two land cells are connected if they
 * touch in ANY of the 8 possible directions:
 *
 *                  ↖  ↑  ↗
 *                  ←  *  →
 *                  ↙  ↓  ↘
 *
 * Task:
 * ------
 * Return the TOTAL NUMBER of distinct islands.
 *
 * Example:
 * --------
 * Input:
 * 1 1 0 0 0
 * 1 1 0 0 0
 * 0 0 1 0 0
 * 0 0 0 1 1
 *
 * Output: 3
 *
 * =========================================================================================================
 * APPROACH → DFS (Depth First Search)
 * =========================================================================================================
 *
 * 🔹 Key Idea:
 * -----------
 * Traverse the entire grid.
 *
 * Whenever we find an unvisited land cell ('1'):
 *     → This is a NEW ISLAND
 *     → Increase island count
 *     → Start DFS from this cell
 *     → DFS will recursively explore ALL connected land cells (8 directions)
 *       and mark them visited
 *
 * Once DFS finishes, we are sure the entire island is processed.
 *
 *
 * =========================================================================================================
 * WHY DFS WORKS?
 * =========================================================================================================
 * DFS goes deep along one path before backtracking.
 * Starting DFS from a land cell ensures:
 *     ✔ All connected land cells of the island are marked visited
 *     ✔ The island will never be counted again
 *
 *
 * =========================================================================================================
 * TIME & SPACE COMPLEXITY
 * =========================================================================================================
 *
 * Let N = rows, M = columns
 *
 * Time Complexity  →  O(N × M)
 * --------------------------------
 * Every cell is visited at most once
 *
 * Space Complexity →  O(N × M)
 * --------------------------------
 * Recursion stack in worst case (all land)
 * + visited matrix
 *
 *
 * =========================================================================================================
 * EDGE CASES
 * =========================================================================================================
 * ✔ Empty grid → 0
 * ✔ No land → 0
 * ✔ All land → 1
 * ✔ Only diagonal connections count as SAME island (because 8-dir allowed)
 *
 *
 * =========================================================================================================
 * ALTERNATIVE APPROACHES
 * =========================================================================================================
 *
 * 1️⃣ BFS
 * -------
 * - Uses queue instead of recursion
 * - More memory safe for very large grids
 *
 * 2️⃣ Disjoint Set (Union-Find)
 * -----------------------------
 * - Useful when:
 *     ✔ Multiple queries asked
 *     ✔ Frequent updates on grid
 * - Harder to implement but scalable
 *
 *
 * =========================================================================================================
 * LIMITATIONS OF DFS
 * =========================================================================================================
 * ⚠️ In very large grids, DFS may cause StackOverflow (deep recursion)
 * BFS is safer there.
 *
 */
public class NumOfDistinctIslandsDFS {

    /**
     * DFS function to explore full island
     * Marks all connected (8-directional) land cells as visited
     */
    private static void dfs(int row, int col, boolean[][] vis, char[][] grid) {

        // Mark current cell as visited
        vis[row][col] = true;

        // 8 possible direction movements
        int[] drow = {-1,-1,-1, 0, 1, 1, 1, 0};
        int[] dcol = {-1, 0, 1, 1, 1, 0,-1,-1};

        // Explore all 8 neighbors
        for (int i = 0; i < 8; i++) {
            int nr = row + drow[i];   // next row
            int nc = col + dcol[i];   // next column

            // Check:
            // ✔ inside grid
            // ✔ land cell
            // ✔ not visited earlier
            if (nr >= 0 && nr < grid.length &&
                    nc >= 0 && nc < grid[0].length &&
                    !vis[nr][nc] && grid[nr][nc] == '1') {

                dfs(nr, nc, vis, grid);   // recursively visit next cell
            }
        }
    }

    /**
     * Function to count total number of islands
     */
    private static int numIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];   // visited matrix
        int count = 0;                          // island counter

        // Traverse whole grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Found NEW island start point
                if (!vis[i][j] && grid[i][j] == '1') {
                    count++;                    // island found
                    dfs(i, j, vis, grid);       // explore whole island
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
        System.out.println("Number of Islands (DFS): " + numIslands(grid));
    }
}

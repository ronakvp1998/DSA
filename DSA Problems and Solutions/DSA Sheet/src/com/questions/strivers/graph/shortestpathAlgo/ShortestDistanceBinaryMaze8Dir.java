package com.questions.strivers.graph.shortestpathAlgo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * =================================================================================================
 *  LeetCode 1091 — Shortest Path in Binary Matrix (BFS — 8 Direction Movement)
 * =================================================================================================
 *
 * 🔥 PROBLEM STATEMENT (LeetCode Style)
 * You are given an n x n binary matrix grid where:
 *  - grid[i][j] == 0  → cell is OPEN (movement allowed)
 *  - grid[i][j] == 1  → cell is BLOCKED (cannot move)
 *
 * You start at the TOP-LEFT cell (0,0) and want to reach the BOTTOM-RIGHT cell (n-1, n-1).
 *
 * A valid path must:
 *  ✔ Move ONLY through 0-valued cells
 *  ✔ Move 8-directionally (Up, Down, Left, Right + 4 diagonals)
 *  ✔ Count path length as NUMBER OF CELLS VISITED
 *
 * If a clear path exists → return its length.
 * If there is NO valid path → return -1.
 *
 * -----------------------------------------------------------------------------------------------
 * Example:
 *
 * Input:  [[0,1],
 *          [1,0]]
 *
 * Output: 2
 *
 * Explanation:
 * Path → (0,0) → (1,1)
 *
 * -----------------------------------------------------------------------------------------------
 * WHY BFS?
 * --------------------------------
 * BFS is ideal because:
 *  - Grid is UNWEIGHTED
 *  - We want SHORTEST path
 *  - BFS explores level-by-level
 *  - First time we reach destination => Minimum steps guaranteed
 *
 * -----------------------------------------------------------------------------------------------
 * APPROACH / INTUITION
 * --------------------------------
 * 1️⃣ If start or end is blocked → immediately return -1
 * 2️⃣ Use BFS because each move costs equal weight
 * 3️⃣ Maintain a visited matrix to avoid reprocessing cells
 * 4️⃣ From each cell, explore all 8 neighboring directions
 * 5️⃣ When we pop destination from queue → return distance
 * 6️⃣ If BFS finishes without reaching → return -1
 *
 * -----------------------------------------------------------------------------------------------
 * EDGE CASES
 * --------------------------------
 *  - n = 1 and cell is open → answer is 1
 *  - Start or destination blocked → -1
 *  - Fully blocked matrix → -1
 *  - Only one valid diagonal path → must work
 *
 * -----------------------------------------------------------------------------------------------
 * TIME & SPACE COMPLEXITY
 * --------------------------------
 * Let n = grid size
 *
 * Time Complexity:  O(n²)
 *  - Each cell is processed at most once in BFS
 *
 * Space Complexity: O(n²)
 *  - Visited matrix
 *  - BFS queue worst case stores all cells
 *
 * -----------------------------------------------------------------------------------------------
 * WHEN TO USE THIS APPROACH?
 * ✔ Shortest path needed
 * ✔ Grid is unweighted
 * ✔ Movement is 4 or 8 directional
 *
 * LIMITATIONS / DRAWBACKS
 * ❌ Not suitable when:
 *    - Weighted paths exist → use Dijkstra instead
 *    - Heuristic preferred → use A*
 *
 * -----------------------------------------------------------------------------------------------
 * ALTERNATIVE APPROACHES
 * --------------------------------
 * 1️⃣ Dijkstra’s Algorithm
 *    - Useful if each step had different costs
 *    - Overkill here since all moves cost same
 *
 * 2️⃣ A* Search
 *    - Faster in practice using heuristic
 *    - Still returns shortest path
 *    - More complex to implement
 *
 * =================================================================================================
 */
public class ShortestDistanceBinaryMaze8Dir {

    /**
     * Returns the shortest path length from (0,0) to (n-1,n-1)
     * using BFS with 8-direction movement.
     */
    public static int shortestPathBinaryMatrix(int[][] grid) {

        int n = grid.length;

        // Base Case: If starting or ending cell is blocked,
        // there is NO valid path → return -1 immediately
        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1)
            return -1;

        // Special Case: If n == 1 and cell is open → answer is 1
        if (n == 1)
            return 1;

        // Direction arrays representing 8 possible moves
        // (Up, Down, Left, Right + 4 Diagonals)
        int[] dr = {-1,-1,-1, 0,0, 1,1,1};
        int[] dc = {-1, 0, 1,-1,1,-1,0,1};

        // Queue for BFS → each entry holds {row, col, pathLength}
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0, 1});   // Start from cell (0,0) with path length 1

        // Visited matrix to ensure each cell is processed only once
        boolean[][] vis = new boolean[n][n];
        vis[0][0] = true;  // Mark starting cell visited

        // Standard BFS Loop
        while (!q.isEmpty()) {

            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            int dist = cur[2];  // Current path length

            // If we reached bottom-right → shortest path found!
            if (r == n - 1 && c == n - 1)
                return dist;

            // Explore all 8 neighbors
            for (int i = 0; i < 8; i++) {

                int nr = r + dr[i];
                int nc = c + dc[i];

                // Check:
                // 1️⃣ Inside grid boundaries
                // 2️⃣ Cell must be open (0)
                // 3️⃣ Not visited earlier
                if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < n &&
                        grid[nr][nc] == 0 &&
                        !vis[nr][nc]) {

                    vis[nr][nc] = true;             // Mark visited
                    q.offer(new int[]{nr, nc, dist + 1});  // Push next cell with incremented path length
                }
            }
        }

        // BFS completed but destination was never reached → No valid path
        return -1;
    }

    /**
     * ============================== DRIVER CODE (TESTING) ===============================
     */
    public static void main(String[] args) {

        int[][] grid1 = {
                {0,1},
                {1,0}
        };

        int[][] grid2 = {
                {0,0,0},
                {1,1,0},
                {1,1,0}
        };

        int[][] grid3 = {
                {1,0,0},
                {1,1,0},
                {1,1,0}
        };

        System.out.println("Output 1: " + shortestPathBinaryMatrix(grid1)); // Expected: 2
        System.out.println("Output 2: " + shortestPathBinaryMatrix(grid2)); // Expected: 4
        System.out.println("Output 3: " + shortestPathBinaryMatrix(grid3)); // Expected: -1
    }
}

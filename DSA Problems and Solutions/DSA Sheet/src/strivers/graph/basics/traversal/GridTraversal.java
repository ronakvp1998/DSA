package com.questions.strivers.graph.basics.traversal;

import java.util.*;

/**
 * =====================================================================================
 *  Grid Traversal using DFS and BFS (4-Direction Movement)
 * =====================================================================================
 *
 * ----------------------------- LEETCODE-STYLE PROBLEM ---------------------------------
 *
 * You are given a 2D grid of size n x m consisting of 0s and 1s.
 *
 * - 1 represents a valid cell that can be visited
 * - 0 represents a blocked cell
 *
 * Starting from a given source cell (row, col),
 * perform:
 *   1. Depth First Search (DFS)
 *   2. Breadth First Search (BFS)
 *
 * Visit only the cells that contain value 1 and are reachable
 * from the starting cell using 4-directional movement
 * (up, right, down, left).
 *
 * ----------------------------- INPUT --------------------------------------------------
 * grid[][] : 2D integer grid
 * row, col : starting cell
 *
 * ----------------------------- OUTPUT -------------------------------------------------
 * Print the order of visited cells as (row, col)
 *
 * ----------------------------- CONSTRAINTS --------------------------------------------
 * 1 ≤ n, m ≤ 10^3
 *
 * =====================================================================================
 *  REAL-WORLD / LEETCODE APPLICATIONS
 * =====================================================================================
 * - Number of Islands
 * - Flood Fill
 * - Rotting Oranges
 * - Shortest Path in Binary Matrix
 * - Connected Components in Grid
 *
 * =====================================================================================
 */

public class GridTraversal {

    /**
     * Direction arrays for 4-directional movement:
     * Index 0 → Up
     * Index 1 → Right
     * Index 2 → Down
     * Index 3 → Left
     */
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, 1, 0, -1};

    // =============================================================================
    // DEPTH FIRST SEARCH (DFS)
    // =============================================================================
    /**
     * DFS explores the grid by going as deep as possible before backtracking.
     *
     * @param row  Current row index
     * @param col  Current column index
     * @param grid Input grid
     * @param vis  Visited matrix to avoid revisiting cells
     */
    static void dfs(int row, int col, int[][] grid, boolean[][] vis) {

        int n = grid.length;        // Number of rows
        int m = grid[0].length;     // Number of columns

        // Mark the current cell as visited
        vis[row][col] = true;

        // Print the current cell
        System.out.print("(" + row + "," + col + ") ");

        // Explore all 4 possible directions
        for (int i = 0; i < 4; i++) {

            // Calculate new row and column
            int nr = row + dRow[i];
            int nc = col + dCol[i];

            // Check:
            // 1. Within grid boundaries
            // 2. Cell value is 1 (valid cell)
            // 3. Cell has not been visited
            if (nr >= 0 && nr < n &&
                    nc >= 0 && nc < m &&
                    grid[nr][nc] == 1 &&
                    !vis[nr][nc]) {

                // Recursively visit the valid neighboring cell
                dfs(nr, nc, grid, vis);
            }
        }
    }

    // =============================================================================
    // BREADTH FIRST SEARCH (BFS)
    // =============================================================================
    /**
     * BFS explores the grid level by level using a queue.
     *
     * @param row  Starting row index
     * @param col  Starting column index
     * @param grid Input grid
     * @param vis  Visited matrix
     */
    static void bfs(int row, int col, int[][] grid, boolean[][] vis) {

        int n = grid.length;
        int m = grid[0].length;

        // Queue stores the cell coordinates
        Queue<int[]> q = new LinkedList<>();

        // Start BFS from the given source cell
        q.add(new int[]{row, col});
        vis[row][col] = true;

        // Continue until all reachable cells are processed
        while (!q.isEmpty()) {

            // Remove the front cell from queue
            int[] cell = q.poll();

            // Print current cell
            System.out.print("(" + cell[0] + "," + cell[1] + ") ");

            // Explore all 4 directions
            for (int i = 0; i < 4; i++) {

                int nr = cell[0] + dRow[i];
                int nc = cell[1] + dCol[i];

                // Validate neighbor cell
                if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < m &&
                        grid[nr][nc] == 1 &&
                        !vis[nr][nc]) {

                    // Mark as visited before pushing to queue
                    vis[nr][nc] = true;

                    // Add neighbor cell to queue
                    q.add(new int[]{nr, nc});
                }
            }
        }
    }

    // =============================================================================
    // MAIN METHOD (TESTING)
    // =============================================================================
    public static void main(String[] args) {

        int[][] grid = {
                {1, 1, 0},
                {0, 1, 0},
                {1, 1, 1}
        };

        boolean[][] vis = new boolean[grid.length][grid[0].length];

        System.out.println("DFS Traversal:");
        dfs(0, 0, grid, vis);

        // Reset visited array for BFS
        vis = new boolean[grid.length][grid[0].length];

        System.out.println("\nBFS Traversal:");
        bfs(0, 0, grid, vis);
    }
}

/**
 * =====================================================================================
 *  APPROACH EXPLANATION (INTERVIEW FRIENDLY)
 * =====================================================================================
 *
 * We treat the grid as a graph where:
 * - Each cell is a node
 * - Edges exist between adjacent valid cells (value = 1)
 *
 * -----------------------------------------------------------------------------
 * DFS APPROACH
 * -----------------------------------------------------------------------------
 * - Go as deep as possible from a starting cell
 * - Uses recursion
 * - Backtracks when no valid neighbors exist
 *
 * USE CASES:
 * - Counting islands
 * - Detecting connected components
 *
 * -----------------------------------------------------------------------------
 * BFS APPROACH
 * -----------------------------------------------------------------------------
 * - Explore all neighbors first
 * - Uses a queue
 * - Guarantees shortest path in unweighted grids
 *
 * USE CASES:
 * - Shortest path problems
 * - Multi-source BFS
 *
 * =====================================================================================
 *  EDGE CASES HANDLED
 * =====================================================================================
 * - Out-of-bounds access prevented
 * - Blocked cells (0) ignored
 * - Cycles avoided using visited matrix
 *
 * =====================================================================================
 *  TIME & SPACE COMPLEXITY
 * =====================================================================================
 *
 * Let n = number of rows, m = number of columns
 *
 * DFS:
 * ----
 * Time Complexity: O(n × m)
 * Space Complexity: O(n × m)  (visited + recursion stack)
 *
 * BFS:
 * ----
 * Time Complexity: O(n × m)
 * Space Complexity: O(n × m)  (visited + queue)
 *
 * =====================================================================================
 *  ALTERNATIVE / RECOMMENDED APPROACHES
 * =====================================================================================
 *
 * 1️⃣ 8-Direction Traversal
 * - Include diagonals
 * - Used in problems like "Shortest Path in Binary Matrix"
 *
 * 2️⃣ Iterative DFS using Stack
 * - Avoids recursion stack overflow
 *
 * 3️⃣ In-Place Modification
 * - Mark visited cells by changing grid values
 * - Saves extra space
 *
 * =====================================================================================
 *  KEY INTERVIEW TAKEAWAY
 * =====================================================================================
 *
 * - Grid problems = Graph problems in disguise
 * - Always think in terms of BFS/DFS
 * - Direction arrays simplify code and reduce bugs
 *
 * =====================================================================================
 */

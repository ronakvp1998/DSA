package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * =====================================================================================
 * LeetCode 778 – Swim in Rising Water
 * =====================================================================================
 * <p>
 * ------------------------------- PROBLEM STATEMENT -----------------------------------
 * <p>
 * You are given an n x n integer matrix grid where:
 * - grid[i][j] represents the elevation at cell (i, j).
 * - The elevation of the water rises over time.
 * <p>
 * At time t, you can enter any cell whose elevation is <= t.
 * <p>
 * You start at the top-left cell (0, 0) and want to reach
 * the bottom-right cell (n - 1, n - 1).
 * <p>
 * You can move in 4 directions:
 * - Up, Down, Left, Right
 * <p>
 * Return the **minimum time** required so that you can reach the destination.
 * <p>
 * -------------------------------------------------------------------------------------
 * <p>
 * EXAMPLE:
 * <p>
 * Input:
 * grid = [
 * [0, 2],
 * [1, 3]
 * ]
 * <p>
 * Output:
 * 3
 * <p>
 * Explanation:
 * - You must wait until time = 3 to be able to move through all required cells.
 * <p>
 * -------------------------------------------------------------------------------------
 * <p>
 * CONSTRAINTS:
 * - 1 <= n <= 50
 * - 0 <= grid[i][j] < n²
 * - All grid values are unique
 * <p>
 * =====================================================================================
 * <p>
 * ----------------------------------- APPROACH ----------------------------------------
 * <p>
 * This problem is solved using **Dijkstra’s Algorithm** (Min-Heap based).
 * <p>
 * KEY IDEA:
 * - Treat each cell as a node.
 * - The "cost" to reach a cell is the **maximum elevation encountered so far**.
 * - We always want to explore the path with the **minimum possible maximum elevation**.
 * <p>
 * WHY DIJKSTRA WORKS:
 * - The "time" increases monotonically.
 * - Once a cell is visited with minimum possible time, it never needs revisiting.
 * <p>
 * -------------------------------------------------------------------------------------
 * <p>
 * STEP-BY-STEP:
 * 1. Use a min-heap storing {currentTime, row, col}.
 * 2. Start from (0, 0) with time = grid[0][0].
 * 3. At each step:
 * - Pop the cell with the smallest time.
 * - Explore its 4 neighbors.
 * - The new time = max(currentTime, neighborElevation).
 * 4. Stop when destination is reached.
 * <p>
 * -------------------------------------------------------------------------------------
 * <p>
 * EDGE CASES:
 * - n = 1 → answer is grid[0][0]
 * - Grid strictly increasing → must wait till max elevation
 * <p>
 * -------------------------------------------------------------------------------------
 * <p>
 * TIME COMPLEXITY:
 * - O(n² log n²) ≈ O(n² log n)
 * Each cell is pushed into the heap once.
 * <p>
 * SPACE COMPLEXITY:
 * - O(n²) for visited array and heap
 * <p>
 * =====================================================================================
 * <p>
 * -------------------------------- ALTERNATIVE APPROACHES ------------------------------
 * <p>
 * 1️⃣ Binary Search + BFS
 * - Binary search on time.
 * - Check reachability using BFS.
 * - Time: O(n² log n)
 * <p>
 * 2️⃣ Union-Find
 * - Sort cells by elevation.
 * - Gradually union neighbors.
 * - Stop when start and end connect.
 * <p>
 * Dijkstra is the most intuitive and interview-friendly.
 * <p>
 * =====================================================================================
 */
public class SwimInRisingWater {

    // Direction arrays for moving up, down, left, right
    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};

    /**
     * Returns the minimum time required to swim from (0,0) to (n-1,n-1).
     */
    public int swimInWater(int[][] grid) {

        int n = grid.length;

        // Min-heap storing {time, row, col}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> a[0] - b[0]
        );

        // Visited matrix to avoid revisiting cells
        boolean[][] visited = new boolean[n][n];

        // Start from top-left cell
        pq.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0] = true;

        // Standard Dijkstra loop
        while (!pq.isEmpty()) {

            // Get cell with minimum current time
            int[] cur = pq.poll();
            int time = cur[0];
            int x = cur[1];
            int y = cur[2];

            // If destination reached, this is the minimum time
            if (x == n - 1 && y == n - 1) {
                return time;
            }

            // Explore all 4 directions
            for (int d = 0; d < 4; d++) {

                int nx = x + dx[d];
                int ny = y + dy[d];

                // Check boundaries and visited condition
                if (nx >= 0 && ny >= 0 && nx < n && ny < n && !visited[nx][ny]) {

                    visited[nx][ny] = true;

                    // Time to reach neighbor is max of current time and its elevation
                    int nextTime = Math.max(time, grid[nx][ny]);

                    pq.offer(new int[]{nextTime, nx, ny});
                }
            }
        }

        // This line should never be reached for valid input
        return -1;
    }

    /**
     * ------------------------------ MAIN METHOD --------------------------------
     * Used for local testing.
     */
    public static void main(String[] args) {

        SwimInRisingWater solution = new SwimInRisingWater();

        int[][] grid = {
                {0, 2},
                {1, 3}
        };

        int result = solution.swimInWater(grid);
        System.out.println("Minimum time to swim: " + result);
    }
}


package com.questions.strivers.graph.shortestpathAlgo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ==================================================================================================
 *  Shortest Path in a Binary Maze using BFS
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * Given an n x m grid where each cell is either 0 or 1:
 * - 1 represents a valid cell to move into.
 * - 0 represents an obstacle.
 * Find the shortest distance from a source cell to a destination cell.
 * - You can move only in 4 directions: Up, Down, Left, Right.
 * - Return -1 if there is no valid path.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — BFS
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 * - Use BFS for shortest path in an unweighted grid.
 * - Keep a distance matrix to track the minimum steps to reach each cell.
 * - Start from the source cell, traverse all 4 directions.
 * - If a neighbor cell is valid and not yet visited (or a shorter distance found), push it to the queue.
 * - Stop BFS when the destination is reached.
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity: O(n*m)
 *      - Each cell is processed at most once.
 * - Space Complexity: O(n*m)
 *      - Distance matrix and BFS queue.
 *
 * --------------------------------------------------------------------------------------------------
 */
public class ShortestDistanceBinaryMaze {

    /**
     * Computes the shortest distance from source to destination in a binary maze
     * using BFS traversal.
     *
     * @param grid  The n x m binary grid
     * @param source Array {row, col} representing source coordinates
     * @param destination Array {row, col} representing destination coordinates
     * @return Minimum number of steps to reach destination or -1 if unreachable
     */
    private static int shortestPath(int[][] grid, int[] source, int[] destination) {

        // Edge Case: source is same as destination
        if (source[0] == destination[0] && source[1] == destination[1])
            return 0;

        int n = grid.length;
        int m = grid[0].length;

        // Initialize distance matrix with "infinity"
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // Distance to source = 0
        dist[source[0]][source[1]] = 0;

        // BFS queue stores {distance, row, col}
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, source[0], source[1]});

        // Directions: up, right, down, left
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        // BFS traversal
        while (!q.isEmpty()) {
            int[] current = q.poll();
            int dis = current[0];
            int r = current[1];
            int c = current[2];

            // Explore all 4 adjacent cells
            for (int i = 0; i < 4; i++) {
                int newr = r + dr[i];
                int newc = c + dc[i];

                // Check boundaries, cell validity, and distance relaxation
                if (newr >= 0 && newr < n && newc >= 0 && newc < m &&
                        grid[newr][newc] == 1 && dis + 1 < dist[newr][newc]) {

                    dist[newr][newc] = dis + 1;

                    // Destination reached
                    if (newr == destination[0] && newc == destination[1])
                        return dis + 1;

                    // Push neighbor to queue
                    q.offer(new int[]{dis + 1, newr, newc});
                }
            }
        }

        // Destination unreachable
        return -1;
    }

    /**
     * ============================== DRIVER CODE ===============================
     */
    public static void main(String[] args) {

        // Source and destination coordinates
        int[] source = {0, 1};
        int[] destination = {2, 2};

        // Define the grid (1 = valid, 0 = blocked)
        int[][] grid = {
                {1, 1, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 1},
                {1, 1, 0, 0},
                {1, 0, 0, 1}
        };

        // Compute shortest path
        int res = shortestPath(grid, source, destination);

        // Output result
        System.out.println("Shortest distance: " + res);
    }
}

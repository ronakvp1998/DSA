package com.questions.strivers.graph.shortestpathAlgo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * ===============================================================================================
 *  Minimum Effort Path
 * ===============================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT --------------------------------------------
 * Given a 2D grid `heights` of size rows x columns:
 * - heights[row][col] represents the height of the cell (row, col)
 * - Start at the top-left cell (0,0) and aim to reach the bottom-right cell (rows-1, columns-1)
 * - You can move up, down, left, or right
 *
 * A route's effort is defined as the maximum absolute difference in heights between
 * two consecutive cells along the path.
 * Find a route with the minimum effort and return that minimum effort value.
 *
 * ------------------------------------------------------------------------------------------------
 *                                APPROACH — Dijkstra's Algorithm
 * ------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 * - Treat each cell as a node in a graph where edge weight = absolute difference in height.
 * - Use Dijkstra's algorithm to find the path from source to destination that minimizes
 *   the maximum edge weight along the path.
 * - Maintain a distance matrix `dist[i][j]` to track the minimum effort required to reach cell (i,j)
 * - PriorityQueue ensures we always process the cell with the current minimum effort first.
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity: O(N * M * log(N*M)), where N = rows, M = columns
 *      - Each cell may enter the priority queue multiple times
 * - Space Complexity: O(N*M) for distance matrix and priority queue
 *
 * ------------------------------------------------------------------------------------------------
 */
public class PathMinEffort {

    static class Pair{
        int effort;
        int row;
        int col;
        Pair(int effort,int row,int col){
            this.effort = effort;
            this.row = row;
            this.col = col;
        }
    }
    /**
     * Method to compute the minimum effort required to travel from (0,0) to (n-1,m-1)
     * using a modified Dijkstra's algorithm.
     *
     * @param heights 2D array representing the height of each cell
     * @return Minimum effort required to reach the destination
     */
    private static int MinimumEffort(int[][] heights) {

        int n = heights.length;      // Number of rows
        int m = heights[0].length;   // Number of columns

        // Distance matrix to store minimum effort to reach each cell
        int[][] dist = new int[n][m];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        // Priority queue: stores {current effort, row, col}
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.effort));
        pq.add(new Pair(0, 0, 0));  // Start from source cell (0,0)

        // Define possible directions (up, right, down, left)
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        // Start processing cells
        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int diff = current.effort;  // Current effort
            int row = current.row;
            int col = current.col;

            // Destination reached
            if (row == n - 1 && col == m - 1)
                return diff;

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newr = row + dr[i];
                int newc = col + dc[i];

                // Check if neighbor is inside grid bounds
                if (newr >= 0 && newc >= 0 && newr < n && newc < m) {
                    // Effort to move to neighbor = max(current effort, height difference)
                    int newEffort = Math.max(diff, Math.abs(heights[row][col] - heights[newr][newc]));

                    // Update if this path gives a smaller effort
                    if (newEffort < dist[newr][newc]) {
                        dist[newr][newc] = newEffort;
                        pq.add(new Pair(newEffort, newr, newc));
                    }
                }
            }
        }

        // Fallback (should not reach here as destination is reachable)
        return 0;
    }

    /**
     * Driver code
     */
    public static void main(String[] args) {

        int[][] heights = {
                {1, 2, 2},
                {3, 8, 2},
                {5, 3, 5}
        };

        int ans = MinimumEffort(heights);
        System.out.println("Minimum effort required: " + ans);
    }
}

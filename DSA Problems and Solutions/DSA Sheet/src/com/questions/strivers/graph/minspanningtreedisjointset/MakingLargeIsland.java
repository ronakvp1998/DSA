package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * =====================================================================================
 *  LeetCode 827 – Making A Large Island
 * =====================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------
 *
 * You are given an n x n binary matrix `grid` where:
 * - 1 represents land
 * - 0 represents water
 *
 * You are allowed to change AT MOST ONE cell from 0 to 1.
 *
 * An island is defined as a group of 1s connected 4-directionally
 * (up, down, left, right).
 *
 * Your task is to return the size of the largest possible island
 * after applying at most one such change.
 *
 * -------------------------------------------------------------------------------------
 *
 * EXAMPLES:
 *
 * Input:  [[1,0],
 *          [0,1]]
 * Output: 3
 *
 * Input:  [[1,1],
 *          [1,0]]
 * Output: 4
 *
 * Input:  [[1,1],
 *          [1,1]]
 * Output: 4
 *
 * -------------------------------------------------------------------------------------
 *
 * CONSTRAINTS:
 * - 1 <= n <= 500
 * - grid[i][j] ∈ {0, 1}
 *
 * =====================================================================================
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * We solve this problem in TWO MAIN PHASES:
 *
 * 🔹 PHASE 1: Identify and label all existing islands
 * - Traverse the grid using DFS.
 * - Assign a UNIQUE ID (starting from 2) to each island.
 * - Store the area of each island in a HashMap.
 *
 * Why start IDs from 2?
 * - 0 → water
 * - 1 → unvisited land
 * - 2+ → labeled islands
 *
 * 🔹 PHASE 2: Try flipping each 0 to 1
 * - For every 0-cell, check its 4 neighbors.
 * - Collect UNIQUE neighboring island IDs.
 * - Sum their areas + 1 (for the flipped cell).
 *
 * The maximum area obtained is the answer.
 *
 * -------------------------------------------------------------------------------------
 *
 * EDGE CASES HANDLED:
 * - All cells are 0 → answer = 1
 * - All cells are 1 → answer = n * n
 * - Multiple islands around a flipped 0 (avoid double counting)
 *
 * -------------------------------------------------------------------------------------
 *
 * TIME COMPLEXITY:
 * - O(n²)
 *   Each cell is visited a constant number of times.
 *
 * SPACE COMPLEXITY:
 * - O(n²)
 *   For recursion stack + HashMap + grid modification.
 *
 * -------------------------------------------------------------------------------------
 *
 * ⚠️ LIMITATION:
 * - DFS is recursive and may cause StackOverflowError for very large grids.
 * - An iterative DFS or BFS can be used in production systems.
 *
 * =====================================================================================
 */
public class MakingLargeIsland {

    // Direction vectors for 4-directional movement
    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};

    /**
     * Main function that computes the largest island size.
     */
    public int largestIsland(int[][] grid) {

        int n = grid.length;

        // Island IDs will start from 2
        int islandId = 2;

        // Maps islandId -> island area
        Map<Integer, Integer> islandArea = new HashMap<>();

        boolean hasZero = false;
        int maxIsland = 0;

        // =========================
        // PHASE 1: Label all islands
        // =========================
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // If we find unvisited land
                if (grid[i][j] == 1) {

                    // DFS to compute area and label island
                    int area = dfs(i, j, grid, islandId);

                    // Store island area
                    islandArea.put(islandId, area);

                    // Track maximum existing island
                    maxIsland = Math.max(maxIsland, area);

                    islandId++;
                } else {
                    hasZero = true;
                }
            }
        }

        // Case 1: Entire grid is water
        if (islandArea.isEmpty()) {
            return 1;
        }

        // Case 2: Entire grid is land
        if (!hasZero) {
            return maxIsland; // equals n * n
        }

        // =========================
        // PHASE 2: Flip each 0 cell
        // =========================
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 0) {

                    // To avoid counting the same island twice
                    Set<Integer> uniqueIslands = new HashSet<>();

                    // Area after flipping this 0
                    int newArea = 1;

                    // Check all 4 neighbors
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dx[d];
                        int nj = j + dy[d];

                        if (ni >= 0 && nj >= 0 && ni < n && nj < n) {
                            int id = grid[ni][nj];

                            // If neighbor belongs to an island
                            if (id > 1 && uniqueIslands.add(id)) {
                                newArea += islandArea.get(id);
                            }
                        }
                    }

                    maxIsland = Math.max(maxIsland, newArea);
                }
            }
        }

        return maxIsland;
    }

    /**
     * DFS to label an island and calculate its area.
     *
     * @param i        row index
     * @param j        column index
     * @param grid     input grid
     * @param islandId unique id assigned to this island
     * @return area of the island
     */
    private int dfs(int i, int j, int[][] grid, int islandId) {

        int n = grid.length;

        // Boundary and base condition
        if (i < 0 || j < 0 || i >= n || j >= n || grid[i][j] != 1) {
            return 0;
        }

        // Mark cell with island ID
        grid[i][j] = islandId;

        int area = 1;

        // Explore all 4 directions
        for (int d = 0; d < 4; d++) {
            area += dfs(i + dx[d], j + dy[d], grid, islandId);
        }

        return area;
    }

    /**
     * -------------------------------- MAIN METHOD --------------------------------
     * Used for local testing.
     */
    public static void main(String[] args) {

        MakingLargeIsland solution = new MakingLargeIsland();

        int[][] grid = {
                {1, 0},
                {0, 1}
        };

        System.out.println("Largest Island Size: " + solution.largestIsland(grid));
    }
}

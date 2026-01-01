package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * =================================================================================================
 *  🔥 LeetCode problem 694: Number of Distinct Islands (DFS Approach)
 * =================================================================================================
 *
 *  You are given a 2D grid consisting of characters:
 *      '1' → represents LAND
 *      '0' → represents WATER
 *
 *  An island is a group of continuously connected '1's using ONLY 4 directions:
 *      ➤ UP
 *      ➤ DOWN
 *      ➤ LEFT
 *      ➤ RIGHT
 *
 *  Two islands are considered the SAME if their SHAPE is identical.
 *  Their actual LOCATION in the grid does NOT matter.
 *
 *  Example:
 *      Shapes like:
 *         1 1           1 1
 *         1      and     1
 *
 *      are considered SAME because their structure is identical.
 *
 *  We must return:
 *      👉 The COUNT of UNIQUE island shapes present in the grid.
 *
 * -------------------------------------------------------------------------------------------------
 *  Why is this problem tricky?
 * -------------------------------------------------------------------------------------------------
 *  - Simply counting islands is easy.
 *  - But identifying UNIQUE SHAPES requires normalization.
 *  - We need to ensure that the same structured island in different locations
 *    is recognized as one unique island.
 *
 * =================================================================================================
 *  APPROACH (DFS + NORMALIZATION OF SHAPE)
 * =================================================================================================
 *
 *  1️⃣ Traverse every cell in the grid.
 *  2️⃣ Whenever we find an unvisited LAND ('1'):
 *          → perform DFS to explore the entire island
 *  3️⃣ While exploring an island, instead of storing raw coordinates,
 *      we store RELATIVE COORDINATES:
 *
 *              (row - baseRow, col - baseCol)
 *
 *      baseRow, baseCol = starting land cell of the island
 *
 *      This normalizes the island shape to start from (0,0),
 *      so location does not matter anymore.
 *
 *  4️⃣ Store each island shape (list of normalized coordinates) in a SET.
 *      → SET automatically keeps only UNIQUE shapes
 *
 *  5️⃣ Final Answer = size of the SET
 *
 * =================================================================================================
 *  TIME COMPLEXITY
 * =================================================================================================
 *  ✔ Visiting each cell once → O(N * M)
 *  ✔ DFS explores neighbors → still bounded by total land cells
 *
 *  Overall:
 *          🔷 Time = O(N * M)
 *
 *  Explanation:
 *      Every land cell is visited only once and inserted into the shape list.
 *
 * =================================================================================================
 *  SPACE COMPLEXITY
 * =================================================================================================
 *  ✔ Visited matrix = O(N * M)
 *  ✔ Recursion stack worst case = O(N * M)
 *  ✔ Set storing shapes (depends on island sizes but upper bounded) = O(N * M)
 *
 *          🔷 Space = O(N * M)
 *
 * =================================================================================================
 *  WHEN TO USE THIS APPROACH?
 * =================================================================================================
 *  ✔ When island shapes must be compared
 *  ✔ When translation/position should not matter
 *  ✔ Ideal for interview problems involving:
 *        - Grid traversal
 *        - Shape normalization
 *        - DFS / BFS exploration
 *
 * =================================================================================================
 *  LIMITATIONS
 * =================================================================================================
 *  ❌ Does not consider ROTATION or REFLECTION equality
 *     Example:
 *         L-shape rotated is considered DIFFERENT here.
 *
 *  ❌ Recursion may cause stack overflow on extremely large grids.
 *
 * =================================================================================================
 *  POSSIBLE ALTERNATIVE APPROACHES
 * =================================================================================================
 *
 *  ✅ BFS instead of DFS
 *     - Same logic but iterative using queue
 *     - Avoids recursion stack overflow
 *
 *  ✅ Store shape as a STRING instead of ArrayList
 *     - Example: "0_0 0_1 1_0"
 *     - Slightly simpler to hash
 *
 *  ✅ Canonical Form Encoding
 *     - Normalize and sort coordinates before storing
 *     - Useful if you want to support rotations/mirroring in advanced problems
 *
 * =================================================================================================
 */

public class NumOfDistinctIslandsDFS {

    /**
     * ------------------------------------------------------------------------------------------------
     * DFS METHOD
     * ------------------------------------------------------------------------------------------------
     * This DFS explores an island and RECORDS its shape using RELATIVE coordinates.
     *
     * @param row      -> current row index
     * @param col      -> current column index
     * @param baseRow  -> starting row of the island (reference for normalization)
     * @param baseCol  -> starting column of the island
     * @param vis      -> visited matrix to avoid re-processing cells
     * @param grid     -> input grid
     * @param shape    -> list storing the normalized coordinates of current island
     */
    private static void dfs(int row, int col,
                            int baseRow, int baseCol,
                            boolean[][] vis,
                            char[][] grid,
                            ArrayList<String> shape) {

        // Mark current cell as visited
        vis[row][col] = true;

        // Store relative position → this NORMALIZES shape
        // Example: If island starts at (2,3) and this cell is (3,4)
        // We store (1,1) instead of absolute coordinates
        shape.add((row - baseRow) + "_" + (col - baseCol));

        // Arrays to move in 4 directions
        int[] dr = {-1, 0, 1, 0};  // Up, Right, Down, Left row movement
        int[] dc = {0, 1, 0, -1};  // Corresponding column movement

        // Check all 4 neighbors
        for (int i = 0; i < 4; i++) {
            int nr = row + dr[i];
            int nc = col + dc[i];

            // Validate boundaries + land check + not visited
            if (nr >= 0 && nr < grid.length &&
                    nc >= 0 && nc < grid[0].length &&
                    !vis[nr][nc] &&
                    grid[nr][nc] == '1') {

                // Continue DFS to expand island
                dfs(nr, nc, baseRow, baseCol, vis, grid, shape);
            }
        }
    }

    /**
     * ------------------------------------------------------------------------------------------------
     * FUNCTION: Count Distinct Islands
     * ------------------------------------------------------------------------------------------------
     *
     * @param grid -> 2D grid of '1' and '0'
     * @return number of UNIQUE island shapes
     */
    private static int countDistinctIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        // Visited matrix to ensure each land cell is processed once
        boolean[][] vis = new boolean[n][m];

        // Set to store UNIQUE island shapes
        // ArrayList<String> stores relative coordinate sequence
        // HashSet ensures only distinct shapes remain
        Set<ArrayList<String>> shapes = new HashSet<>();

        // Traverse entire grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Found a NEW island starting point
                if (!vis[i][j] && grid[i][j] == '1') {

                    // List to store current island shape
                    ArrayList<String> shape = new ArrayList<>();

                    // Perform DFS and record normalized positions
                    dfs(i, j, i, j, vis, grid, shape);

                    // Add the island shape to set
                    shapes.add(shape);
                }
            }
        }

        // Number of unique shapes
        return shapes.size();
    }

    /**
     * =================================================================================================
     *  DRIVER CODE (for testing)
     * =================================================================================================
     */
    public static void main(String[] args) {

        // Test Grid
        char[][] grid = {
                {'1','1','0','1'},
                {'1','0','0','0'},
                {'0','0','1','1'},
                {'1','1','0','1'}
        };

        System.out.println("Distinct Islands = " + countDistinctIslands(grid));
    }
}

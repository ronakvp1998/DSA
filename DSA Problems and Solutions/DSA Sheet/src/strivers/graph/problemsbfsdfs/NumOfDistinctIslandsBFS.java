package com.questions.strivers.graph.problemsbfsdfs;

import java.util.*;

/**
 * =================================================================================================
 *  🔥 LeetCode problem 694: Number of Distinct Islands (BFS Approach)
 * =================================================================================================
 *
 *  You are given a 2D grid consisting of:
 *      '1' → LAND
 *      '0' → WATER
 *
 *  An island is formed by connecting adjacent lands horizontally or vertically (4-directional).
 *
 *  Two islands are considered the SAME if their SHAPE is identical,
 *  regardless of their position in the grid.
 *
 *  Your task:
 *          👉 Return the count of UNIQUE island shapes.
 *
 * -------------------------------------------------------------------------------------------------
 *  Key Difficulty
 * -------------------------------------------------------------------------------------------------
 *  We must ensure that:
 *      - Location does NOT matter
 *      - Only SHAPE matters
 *
 *  Example:
 *           1 1            1 1
 *           1       and     1
 *
 *  Although placed differently, they represent the SAME island shape.
 *
 * =================================================================================================
 *  APPROACH (BFS + SHAPE NORMALIZATION)
 * =================================================================================================
 *
 *  1️⃣ Iterate through each cell of the grid
 *  2️⃣ Whenever we encounter an unvisited LAND ('1'):
 *          → Start a BFS traversal
 *  3️⃣ While performing BFS, store the RELATIVE POSITION of each cell:
 *
 *          (row - baseRow , col - baseCol)
 *
 *      Where baseRow & baseCol are starting coordinates of the island.
 *
 *      This ensures the island always starts at (0,0)
 *      so identical shapes from different places look the same.
 *
 *  4️⃣ Store each island's normalized coordinate list into a SET
 *      → Set automatically handles uniqueness
 *
 *  5️⃣ Answer = size of the SET
 *
 * =================================================================================================
 *  TIME COMPLEXITY
 * =================================================================================================
 *  ✔ Each cell is visited once → O(N * M)
 *  ✔ BFS explores neighboring land cells → Still O(N * M)
 *
 *      🔷 Overall Time Complexity = O(N * M)
 *
 * =================================================================================================
 *  SPACE COMPLEXITY
 * =================================================================================================
 *  ✔ Visited matrix → O(N * M)
 *  ✔ Queue for BFS worst case → O(N * M)
 *  ✔ Set storing unique shapes → O(N * M)
 *
 *      🔷 Overall Space = O(N * M)
 *
 * =================================================================================================
 *  WHY BFS VERSION?
 * =================================================================================================
 *  ✔ Avoids deep recursion stack overflow issues
 *  ✔ Iterative → safer on large grids
 *
 * =================================================================================================
 *  LIMITATIONS
 * =================================================================================================
 *  ❌ Does not treat rotated / mirrored shapes as same (same as DFS version)
 *
 * =================================================================================================
 *  POSSIBLE ALTERNATIVES
 * =================================================================================================
 *  - DFS Normalization (already shown previously)
 *  - Canonical Encoding with Sorting
 *  - Hash based shape encoding
 *
 * =================================================================================================
 */

public class NumOfDistinctIslandsBFS {
    static class Pair{
        int row;
        int col;
        Pair(int row,int col){
            this.row = row;
            this.col = col;
        }
    }
    /**
     * ------------------------------------------------------------------------------------------------
     * BFS to record SHAPE of an island using RELATIVE COORDINATES
     * ------------------------------------------------------------------------------------------------
     *
     * @param row     -> starting row of island
     * @param col     -> starting col of island
     * @param baseRow -> reference row for normalization
     * @param baseCol -> reference col for normalization
     * @param vis     -> visited matrix
     * @param grid    -> input grid
     * @param shape   -> stores normalized island coordinates
     */
    private static void bfs(int row, int col,
                            int baseRow, int baseCol,
                            boolean[][] vis,
                            char[][] grid,
                            ArrayList<String> shape) {

        // Queue for BFS traversal
        Queue<Pair> q = new LinkedList<>();

        // Mark starting cell visited and push to queue
        vis[row][col] = true;
        q.add(new Pair(row, col));

        // Directions for moving UP, RIGHT, DOWN, LEFT
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        // BFS traversal
        while (!q.isEmpty()) {

            Pair cell = q.poll();
            int r = cell.row;
            int c = cell.col;

            // Store RELATIVE POSITION (Normalization)
            shape.add((r - baseRow) + "_" + (c - baseCol));

            // Check all 4 direction neighbors
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                // Boundary check + unvisited + land check
                if (nr >= 0 && nr < grid.length &&
                        nc >= 0 && nc < grid[0].length &&
                        !vis[nr][nc] &&
                        grid[nr][nc] == '1') {

                    vis[nr][nc] = true;     // mark visited
                    q.add(new Pair(nr, nc)); // push neighbor into queue
                }
            }
        }
    }

    /**
     * ------------------------------------------------------------------------------------------------
     * FUNCTION: Count Distinct Islands using BFS
     * ------------------------------------------------------------------------------------------------
     *
     * @param grid -> 2D character matrix
     * @return number of UNIQUE island shapes
     */
    private static int countDistinctIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];

        // Set to store UNIQUE shapes
        Set<ArrayList<String>> shapes = new HashSet<>();

        // Traverse full grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // If unvisited land found → New island
                if (!vis[i][j] && grid[i][j] == '1') {

                    ArrayList<String> shape = new ArrayList<>();

                    // Perform BFS to record shape
                    bfs(i, j, i, j, vis, grid, shape);

                    // Insert shape in set
                    shapes.add(shape);
                }
            }
        }

        return shapes.size();
    }

    /**
     * =================================================================================================
     *  DRIVER CODE
     * =================================================================================================
     */
    public static void main(String[] args) {

        char[][] grid = {
                {'1','1','0','1'},
                {'1','0','0','0'},
                {'0','0','1','1'},
                {'1','1','0','1'}
        };

        System.out.println("Distinct Islands (BFS) = " + countDistinctIslands(grid));
    }
}

/*
package com.questions.strivers.graph.problemsbfsdfs;

        import java.util.*;

public class NumOfDistinctIslandsBFS {

    // ===================== PAIR CLASS =====================
    static class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // REQUIRED for HashSet comparison
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return row == pair.row && col == pair.col;
        }

        // REQUIRED for HashSet hashing
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    // ===================== BFS TO CAPTURE SHAPE =====================
    private static void bfs(int row, int col,
                            int baseRow, int baseCol,
                            char[][] grid,
                            boolean[][] vis,
                            ArrayList<Pair> shape) {

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(row, col));
        vis[row][col] = true;

        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        while (!queue.isEmpty()) {
            Pair cur = queue.poll();
            int r = cur.row;
            int c = cur.col;

            // Store RELATIVE position
            shape.add(new Pair(r - baseRow, c - baseCol));

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < grid.length &&
                        nc >= 0 && nc < grid[0].length &&
                        !vis[nr][nc] &&
                        grid[nr][nc] == '1') {

                    vis[nr][nc] = true;
                    queue.add(new Pair(nr, nc));
                }
            }
        }
    }

    // ===================== MAIN FUNCTION =====================
    public static int countDistinctIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];
        Set<ArrayList<Pair>> uniqueIslands = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (!vis[i][j] && grid[i][j] == '1') {
                    ArrayList<Pair> shape = new ArrayList<>();
                    bfs(i, j, i, j, grid, vis, shape);
                    uniqueIslands.add(shape);
                }
            }
        }
        return uniqueIslands.size();
    }

    // ===================== DRIVER =====================
    public static void main(String[] args) {

        char[][] grid = {
                {'1','1','0','1'},
                {'1','0','0','0'},
                {'0','0','1','1'},
                {'1','1','0','1'}
        };

        System.out.println("Distinct Islands = " + countDistinctIslands(grid));
    }
}
*/
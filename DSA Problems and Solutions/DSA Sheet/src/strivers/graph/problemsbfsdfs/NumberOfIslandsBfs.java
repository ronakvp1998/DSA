package com.questions.strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * =================================================================================================
 * 🧩 PROBLEM: Number of Islands (BFS – 8 Direction Version)
 * =================================================================================================
 * We are given a 2D grid consisting of:
 * '1' → represents LAND
 * '0' → represents WATER
 * <p>
 * An "Island" is defined as a group of connected lands.
 * <p>
 * ✔️ In THIS implementation:
 * Land cells are considered CONNECTED if they touch:
 * → vertically
 * → horizontally
 * → diagonally
 * <p>
 * Meaning:
 * We explore in ALL 8 DIRECTIONS.
 * <p>
 * Example Grid:
 * 1 1 0
 * 0 1 0
 * 0 0 1
 * <p>
 * Output: 1
 * Because diagonal connectivity is allowed.
 * <p>
 * (⚠️ Note: LeetCode 200 original problem considers ONLY 4 Directions.
 * This solution solves the 8-direction island problem.)
 * <p>
 * =================================================================================================
 * ✅ APPROACH — BFS Traversal on Grid
 * =================================================================================================
 * 1️⃣ Traverse every cell in the grid
 * 2️⃣ Whenever we encounter an unvisited '1':
 * → This means we found a NEW ISLAND
 * → Increase island count
 * → Perform BFS to mark the entire island as visited
 * 3️⃣ BFS will explore all connected neighbours and mark them visited
 * 4️⃣ Continue scanning grid
 * <p>
 * BFS ensures each island is processed exactly once.
 * <p>
 * =================================================================================================
 * ⏱️ TIME & SPACE COMPLEXITY
 * =================================================================================================
 * Let N = number of rows
 * M = number of columns
 * <p>
 * Time Complexity  :  O(N * M)
 * - Every cell is visited at most once
 * <p>
 * Space Complexity :  O(N * M)
 * - Visited matrix
 * - BFS queue worst case (all land)
 * <p>
 * =================================================================================================
 * 🎯 EDGE CASES HANDLED
 * =================================================================================================
 * ✔ Single cell grid
 * ✔ All water grid
 * ✔ All land grid
 * ✔ Multiple separate island blocks
 * ✔ Diagonal land connections count as SAME island
 * <p>
 * =================================================================================================
 * 🧠 WHY BFS WORKS?
 * =================================================================================================
 * BFS explores level-by-level ensuring:
 * - Once we hit an island, we explore FULL connected region
 * - We never double count because visited[] prevents revisits
 * <p>
 * =================================================================================================
 * 🔀 ALTERNATIVE APPROACHES (INTERVIEW TALKING POINT)
 * =================================================================================================
 * <p>
 * 1️⃣ DFS (Depth First Search)
 * - Use recursion instead of queue
 * - Simpler code
 * - Risk of stack overflow for huge grids
 * - Same complexity
 * <p>
 * 2️⃣ Disjoint Set / Union-Find
 * - Treat each land cell as a node
 * - Union adjacent ones
 * - Count number of unique parents
 * - Best for dynamic grid scenarios
 * <p>
 * =================================================================================================
 */

class NumberOfIslandsBfs {

    /**
     * BFS function that marks all connected land cells as visited
     */
    private static void bfs(int row, int col, int[][] vis, char[][] grid) {

        // Mark starting cell as visited
        vis[row][col] = 1;

        // Queue to perform BFS
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(row, col));

        int n = grid.length;
        int m = grid[0].length;

        // Process BFS until all connected cells are visited
        while (!queue.isEmpty()) {

            // Get front cell
            int first = queue.peek().first;
            int second = queue.peek().second;
            queue.remove();

            /**
             * Explore ALL 8 DIRECTIONS
             *
             * delRow, delCol range from -1 → 1
             * This covers:
             *  (-1,-1) (-1,0) (-1,1)
             *  (0,-1)  (0,0)  (0,1)
             *  (1,-1)  (1,0)  (1,1)
             *
             * (0,0) simply refers current cell but since it is already visited,
             * it will automatically be ignored.
             */
            for (int delRow = -1; delRow <= 1; delRow++) {
                for (int delCol = -1; delCol <= 1; delCol++) {

                    int nRow = first + delRow;
                    int nCol = second + delCol;

                    // Boundary check + must be land + must be unvisited
                    if (nRow >= 0 && nRow < n && nCol >= 0 && nCol < m &&
                            grid[nRow][nCol] == '1' && vis[nRow][nCol] == 0) {
                        vis[nRow][nCol] = 1;                 // mark visited
                        queue.add(new Pair(nRow, nCol));     // push to BFS
                    }
                }
            }
        }
    }

    /**
     * Function to count number of islands in the grid
     */
    private static int numIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m]; // visited matrix
        int cnt = 0;                 // island counter

        // Traverse entire grid
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {

                // If this land is unvisited → new island found
                if (vis[row][col] == 0 && grid[row][col] == '1') {
                    cnt++;
                    bfs(row, col, vis, grid); // cover full island
                }
            }
        }

        return cnt;
    }

    /**
     * Helper Pair class to store row-column together
     */
    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    // =========================================================================================
    // MAIN METHOD FOR TESTING
    // =========================================================================================
    public static void main(String[] args) {

        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'0', '1', '0', '0', '1'},
                {'1', '0', '1', '1', '1'},
                {'0', '0', '0', '0', '0'},
                {'1', '1', '0', '1', '1'}
        };

        System.out.println("Number of Islands (8-direction): " + numIslands(grid));
    }
}

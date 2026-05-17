package com.questions.strivers.graph.problemsbfsdfs;

/**
 * ---------------------------- PROBLEM STATEMENT ----------------------------
 *
 * Problem: Number of Islands (DFS Approach)
 *
 * You are given a 2D grid consisting of characters '1' and '0':
 *  - '1' represents LAND
 *  - '0' represents WATER
 *
 * An ISLAND is a group of connected '1's. Connectivity is allowed in
 * ALL 8 DIRECTIONS:
 *  → left, right, up, down
 *  → and diagonals (top-left, top-right, bottom-left, bottom-right)
 *
 * Your task is to count how many separate islands exist in the grid.
 *
 * Example:
 *  Input Grid:
 *      1 1 0 0 0
 *      1 1 0 0 0
 *      0 0 1 0 0
 *      0 0 0 1 1
 *
 *  Output:
 *      3
 *
 * Explanation:
 *  - First island = top-left block of connected 1s
 *  - Second island = single 1 at center
 *  - Third island = two connected 1s at bottom-right
 *
 *
 * ---------------------------- APPROACH (DFS) ----------------------------
 *
 * We treat the grid as a graph:
 *  - Each cell = node
 *  - Edge exists if two land cells ('1') are adjacent (8-directionally)
 *
 * Algorithm:
 * 1️⃣ Traverse every cell in the grid.
 * 2️⃣ When we find a cell with '1' that is NOT visited:
 *      - This means we discovered a NEW ISLAND.
 *      - Increase island count.
 *      - Call DFS to explore all connected land cells.
 * 3️⃣ DFS will recursively visit all neighboring 1's and mark them visited.
 * 4️⃣ Continue scanning grid until all cells are processed.
 *
 * ---------------------------- WHY THIS WORKS ----------------------------
 * DFS ensures that when an island is found, we completely explore it and mark
 * all its cells visited so we never count it again.
 *
 * ---------------------------- EDGE CASES ----------------------------
 * ✔ Grid size may be 1x1
 * ✔ Grid may contain all water
 * ✔ Grid may contain all land
 * ✔ Non-rectangular issues do not exist (LeetCode guarantees valid grid)
 *
 *
 * ---------------------------- TIME COMPLEXITY ----------------------------
 * O(N * M)
 * We visit each cell once. DFS may explore neighbors but total visits remain bounded.
 *
 * ---------------------------- SPACE COMPLEXITY ----------------------------
 * O(N * M) for visited matrix
 * O(N * M) recursion stack in worst case (all land)
 *
 *
 * ---------------------------- ALTERNATIVE APPROACHES ----------------------------
 *
 * ✔ BFS Approach:
 *  - Instead of recursion, use a queue.
 *  - Better when recursion depth risk exists.
 *
 * ✔ Union-Find / Disjoint Set:
 *  - Converts grid to a graph representation.
 *  - Useful in dynamic connectivity problems.
 *  - More complex to implement.
 *
 * ✔ DFS 4-Direction Only:
 *  - If problem states NO diagonal connectivity, then check only 4 directions.
 *
 * ---------------------------- WHEN TO USE DFS ----------------------------
 * ✔ When recursion is fine
 * ✔ When exploring connected components
 * ✔ When stack overflow isn't a concern (grid <= 500x500 generally ok)
 *
 */

class NumberOfIslandsDfs {

    /**
     * Depth First Search function to explore all connected land cells.
     * Marks cells as visited to avoid recounting.
     */
    private static void dfs(int row, int col, int[][] vis, char[][] grid) {

        // Mark current cell as visited
        vis[row][col] = 1;

        int n = grid.length;
        int m = grid[0].length;

        // Traverse all 8 possible directions
        for (int delRow = -1; delRow <= 1; delRow++) {
            for (int delCol = -1; delCol <= 1; delCol++) {

                int nRow = row + delRow;
                int nCol = col + delCol;

                // Check boundaries + land + not visited
                if (nRow >= 0 && nRow < n &&
                        nCol >= 0 && nCol < m &&
                        grid[nRow][nCol] == '1' &&
                        vis[nRow][nCol] == 0) {

                    // Recursively explore the neighbor cell
                    dfs(nRow, nCol, vis, grid);
                }
            }
        }
    }

    /**
     * Main function to count number of islands.
     */
    public static int numIslands(char[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m];   // visited matrix
        int count = 0;                 // stores number of islands found

        // Scan the entire grid
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {

                // If cell is land and not visited → new island!
                if (grid[row][col] == '1' && vis[row][col] == 0) {
                    count++;                // found a new island
                    dfs(row, col, vis, grid); // explore full connected region
                }
            }
        }

        return count;
    }

    /**
     * ---------------------------- TEST MAIN ----------------------------
     */
    public static void main(String[] args) {

        char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };

        System.out.println("Number of Islands: " + numIslands(grid));
    }
}

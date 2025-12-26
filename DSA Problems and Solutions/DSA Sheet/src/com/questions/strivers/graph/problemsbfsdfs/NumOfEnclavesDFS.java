package com.questions.strivers.graph.problemsbfsdfs;

public class NumOfEnclavesDFS {

    private static int numberOfEnclaves(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];

        // Call DFS for all boundary land cells
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // If boundary cell and land
                if ((i == 0 || j == 0 || i == n - 1 || j == m - 1)
                        && grid[i][j] == 1 && !vis[i][j]) {
                    dfs(i, j, grid, vis);
                }
            }
        }

        // Count land cells not visited (enclaves)
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !vis[i][j]) count++;
            }
        }

        return count;
    }

    private static void dfs(int row, int col, int[][] grid, boolean[][] vis) {
        vis[row][col] = true;
        int n = grid.length;
        int m = grid[0].length;

        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};

        for (int k = 0; k < 4; k++) {
            int newRow = row + delRow[k];
            int newCol = col + delCol[k];

            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m
                    && grid[newRow][newCol] == 1 && !vis[newRow][newCol]) {
                dfs(newRow, newCol, grid, vis);
            }
        }
    }

    public static void main(String[] args) {

        int[][] grid = {
                {0, 0, 0, 0},
                {1, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        System.out.println(numberOfEnclaves(grid)); // Expected: 3
    }
}

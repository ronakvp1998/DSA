package com.questions.strivers.graph.basics.dfs;

public class DFSGridMatrix {

    private static void dfs(int row, int col, int[][] grid, boolean[][] vis) {
        int n = grid.length;
        int m = grid[0].length;

        vis[row][col] = true;
        System.out.println("Visited: (" + row + "," + col + ")");

        // Directions: up, right, down, left
        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int nrow = row + drow[i];
            int ncol = col + dcol[i];

            if (nrow >= 0 && nrow < n &&
                    ncol >= 0 && ncol < m &&
                    grid[nrow][ncol] == 1 &&
                    !vis[nrow][ncol]) {

                dfs(nrow, ncol, grid, vis);
            }
        }
    }

    private static void dfsTraversal(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] vis = new boolean[n][m];

        // Start DFS from (0,0)
        if (grid[0][0] == 1) {
            dfs(0, 0, grid, vis);
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 0},
                {0, 1, 1},
                {1, 0, 1}
        };

        System.out.println("DFS (Grid Traversal):");
        dfsTraversal(grid);
    }
}

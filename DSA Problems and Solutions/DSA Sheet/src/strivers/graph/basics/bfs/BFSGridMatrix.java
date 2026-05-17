package com.questions.strivers.graph.basics.bfs;

import java.util.*;

public class BFSGridMatrix {

    private static void bfs(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        boolean[][] vis = new boolean[n][m];
        Queue<int[]> q = new LinkedList<>();

        if (grid[0][0] == 1) {
            q.offer(new int[]{0, 0});
            vis[0][0] = true;
        }

        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};

        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int row = cell[0];
            int col = cell[1];

            System.out.println("Visited: (" + row + "," + col + ")");

            for (int i = 0; i < 4; i++) {
                int nrow = row + drow[i];
                int ncol = col + dcol[i];

                if (nrow >= 0 && nrow < n &&
                        ncol >= 0 && ncol < m &&
                        grid[nrow][ncol] == 1 &&
                        !vis[nrow][ncol]) {

                    vis[nrow][ncol] = true;
                    q.offer(new int[]{nrow, ncol});
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 0},
                {0, 1, 1},
                {1, 0, 1}
        };

        System.out.println("BFS (Grid Traversal):");
        bfs(grid);
    }
}

package com.questions.strivers.graph.problemsbfsdfs;

public class SurroundedRegionDFS {
    // DFS to mark boundary-connected 'O's as visited
    private static void dfs(int r, int c, int[][] vis, char[][] mat, int[] dr, int[] dc) {
        // mark current cell visited
        vis[r][c] = 1;
        // cache dimensions
        int n = mat.length, m = mat[0].length;
        // try 4 directions
        for (int k = 0; k < 4; k++) {
            // compute next cell
            int nr = r + dr[k], nc = c + dc[k];
            // check bounds and unvisited 'O'
            if (nr >= 0 && nr < n && nc >= 0 && nc < m &&
                    vis[nr][nc] == 0 && mat[nr][nc] == 'O') {
                // continue DFS
                dfs(nr, nc, vis, mat, dr, dc);
            }
        }
    }

    // Flip all 'O' not connected to boundary to 'X'
    private static char[][] fill(int n, int m, char[][] mat) {
        // handle empty matrix
        if (n == 0 || m == 0) return mat;
        // direction arrays
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};
        // visited matrix
        int[][] vis = new int[n][m];

        // traverse first and last row
        for (int j = 0; j < m; j++) {
            // DFS from top boundary 'O'
            if (vis[0][j] == 0 && mat[0][j] == 'O') dfs(0, j, vis, mat, dr, dc);
            // DFS from bottom boundary 'O'
            if (vis[n - 1][j] == 0 && mat[n - 1][j] == 'O') dfs(n - 1, j, vis, mat, dr, dc);
        }

        // traverse first and last column
        for (int i = 0; i < n; i++) {
            // DFS from left boundary 'O'
            if (vis[i][0] == 0 && mat[i][0] == 'O') dfs(i, 0, vis, mat, dr, dc);
            // DFS from right boundary 'O'
            if (vis[i][m - 1] == 0 && mat[i][m - 1] == 'O') dfs(i, m - 1, vis, mat, dr, dc);
        }

        // flip all unvisited 'O' to 'X'
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // convert enclosed 'O' to 'X'
                if (vis[i][j] == 0 && mat[i][j] == 'O') mat[i][j] = 'X';
            }
        }

        // return updated board
        return mat;
    }

    public static void main(String[] args) {
        // sample board
        char[][] mat = {
                {'X','X','X','X'},
                {'X','O','X','X'},
                {'X','O','O','X'},
                {'X','O','X','X'},
                {'X','X','O','O'}
        };
        // create solver
        // compute result
        char[][] ans = fill(mat.length, mat[0].length, mat);
        // print board
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                System.out.print(ans[i][j] + " ");
            }
            System.out.println();
        }
    }

}

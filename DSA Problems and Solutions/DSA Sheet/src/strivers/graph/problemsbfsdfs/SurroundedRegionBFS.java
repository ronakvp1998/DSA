package com.questions.strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ========================== PROBLEM STATEMENT ==============================
 * Surrounded Regions | Replace O’s with X’s
 *
 * You are given a 2D matrix of size N x M containing only:
 *      'O' → open space
 *      'X' → blocked
 *
 * Replace all 'O' that are completely surrounded by 'X' with 'X'.
 *
 * An 'O' region is considered SURROUNDED if:
 *  - It is fully enclosed by 'X' on all 4 sides (no diagonal movement allowed)
 *  - None of its connected cells touches the matrix boundary
 *
 * Connected means movement allowed ONLY in 4 directions:
 *      Up, Down, Left, Right
 *
 * ===========================================================================
 *
 * ========================== BFS APPROACH (LEVEL ORDER) =====================
 *
 * Intuition:
 * Instead of finding surrounded regions directly,
 * we protect the ones that CANNOT be surrounded.
 *
 * Observation:
 * Any 'O' connected to a boundary 'O' is SAFE
 * because it has an escape path to outside the grid.
 *
 * So:
 * 1) Traverse ALL boundary cells.
 * 2) Whenever we find 'O', perform a BFS.
 * 3) Mark all connected 'O' as SAFE.
 * 4) After BFS completes:
 *        - SAFE 'O' remain unchanged
 *        - Other 'O' → must be enclosed → convert to 'X'
 *
 * ===========================================================================
 *
 * ========================== WHY BFS? =======================================
 *
 * ✔ BFS avoids recursion stack overflow (safe for huge matrices)
 * ✔ Cleaner iterative approach
 * ✔ Same complexity as DFS but safer in interviews
 *
 * ===========================================================================
 *
 * ========================== TIME & SPACE COMPLEXITY ========================
 *
 * TIME COMPLEXITY:
 * O(N * M)
 * Each cell is visited at most once in BFS.
 *
 * SPACE COMPLEXITY:
 * O(N * M)
 * - Visited matrix
 * - BFS queue worst case
 *
 * ===========================================================================
 *
 * ========================== EDGE CASES ====================================
 * ✔ Grid with NO 'O' → return as-is
 * ✔ Single row / single column
 * ✔ All boundary O's → nothing changes
 * ✔ Random scattered O's
 *
 * ===========================================================================
 *
 * ========================== ALTERNATIVE APPROACHES ========================
 * 1️⃣ DFS Approach (your previous solution)
 *  - Uses recursion
 *  - Simpler code but risk of stack overflow for large grids
 *
 * 2️⃣ In-place marking:
 *  - Mark safe O's as '*'
 *  - Convert remaining O → X
 *  - Convert '*' → O
 *  - Saves visited array space
 *
 * ===========================================================================
 */

public class SurroundedRegionBFS {

    /**
     * Function to replace surrounded 'O' with 'X' using BFS
     *
     * @param n rows
     * @param m cols
     * @param mat board
     * @return updated matrix
     */
    public static char[][] fill(int n, int m, char[][] mat) {

        // Edge case → empty grid
        if (n == 0 || m == 0) return mat;

        // Visited matrix to mark safe 'O'
        int[][] vis = new int[n][m];

        // Queue for BFS
        Queue<int[]> q = new LinkedList<>();

        // =========================
        // STEP 1: Push all BORDER 'O' into queue
        // =========================

        // Traverse first and last row
        for (int j = 0; j < m; j++) {

            // Top row
            if (mat[0][j] == 'O' && vis[0][j] == 0) {
                q.offer(new int[]{0, j});
                vis[0][j] = 1;
            }

            // Bottom row
            if (mat[n - 1][j] == 'O' && vis[n - 1][j] == 0) {
                q.offer(new int[]{n - 1, j});
                vis[n - 1][j] = 1;
            }
        }

        // Traverse first and last column
        for (int i = 0; i < n; i++) {

            // Left column
            if (mat[i][0] == 'O' && vis[i][0] == 0) {
                q.offer(new int[]{i, 0});
                vis[i][0] = 1;
            }

            // Right column
            if (mat[i][m - 1] == 'O' && vis[i][m - 1] == 0) {
                q.offer(new int[]{i, m - 1});
                vis[i][m - 1] = 1;
            }
        }

        // Direction vectors for 4-directional BFS
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        // =========================
        // STEP 2: BFS traversal
        // Mark all boundary-connected O as SAFE
        // =========================
        while (!q.isEmpty()) {

            int[] cell = q.poll();
            int r = cell[0];
            int c = cell[1];

            // Check all 4 directions
            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                // If inside bounds, unvisited, and is 'O'
                if (nr >= 0 && nr < n && nc >= 0 && nc < m &&
                        vis[nr][nc] == 0 && mat[nr][nc] == 'O') {

                    vis[nr][nc] = 1;   // mark safe
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        // =========================
        // STEP 3: Convert unvisited O → X
        // =========================
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // If not visited → surrounded → convert
                if (vis[i][j] == 0 && mat[i][j] == 'O') {
                    mat[i][j] = 'X';
                }
            }
        }

        return mat;
    }

    /**
     * ============================ TESTING MAIN ===============================
     */
    public static void main(String[] args) {

        char[][] mat = {
                {'X','X','X','X'},
                {'X','O','X','X'},
                {'X','O','O','X'},
                {'X','O','X','X'},
                {'X','X','O','O'}
        };

        char[][] ans = fill(mat.length, mat[0].length, mat);

        System.out.println("Final Board Using BFS:");
        for (char[] row : ans) {
            for (char ch : row)
                System.out.print(ch + " ");
            System.out.println();
        }
    }
}

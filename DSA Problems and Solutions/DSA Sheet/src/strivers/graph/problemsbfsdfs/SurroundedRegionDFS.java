package com.questions.strivers.graph.problemsbfsdfs;

/**
 * ========================== PROBLEM STATEMENT ==============================
 * Surrounded Regions | Replace O’s with X’s
 *
 * You are given a 2D grid (matrix) of size N x M consisting only of 'O' and 'X'.
 *
 * Goal:
 * Replace all 'O' that are completely surrounded by 'X' with 'X'.
 *
 * Definition of "Surrounded":
 * An 'O' (or group of connected 'O's) is said to be surrounded if it does NOT
 * have any connection (directly or indirectly through other 'O's) to the boundary
 * of the matrix.
 *
 * A connection is valid **only through 4 directions**:
 *      Up, Down, Left, Right   (No diagonal connection allowed)
 *
 * Example:
 * Input:
 *  X X X X
 *  X O X X
 *  X O O X
 *  X O X X
 *  X X O O
 *
 * Output:
 *  X X X X
 *  X X X X
 *  X X X X
 *  X X X X
 *  X X O O
 *
 * Here:
 * - The bottom-right 'O's remain because they touch boundary -> cannot be captured.
 * - The middle O-region is surrounded -> converted to X.
 *
 * ===========================================================================
 *
 * ========================== MAIN APPROACH (DFS) ============================
 *
 * Key Logic / Intuition:
 * Instead of trying to find surrounded regions, we do the OPPOSITE:
 *
 * 1️⃣ Any 'O' connected to the boundary can NEVER be captured.
 * 2️⃣ So we find all boundary-connected 'O's and mark them SAFE.
 * 3️⃣ After marking safe cells, any remaining 'O' must be surrounded -> convert to 'X'.
 *
 * Steps:
 * --------------------------------------------------------------------------
 * 1) Traverse ALL boundary cells (first row, last row, first col, last col)
 * 2) Whenever a boundary cell = 'O', perform DFS and mark all reachable 'O' as visited.
 * 3) After DFS is done:
 *        - visited 'O' = safe → keep them 'O'
 *        - unvisited 'O' = surrounded → convert to 'X'
 *
 * Why this works?
 * Because only regions touching border survive.
 *
 * When should we use this approach?
 * ✔ Grid problems
 * ✔ When regions must be preserved based on boundary constraints
 * ✔ Common in flood-fill, island, surrounded region problems
 *
 * Drawbacks:
 * ❌ Recursive DFS may cause stack overflow for huge matrices (BFS safer there)
 *
 * ===========================================================================
 *
 * ========================== TIME & SPACE COMPLEXITY ========================
 *
 * TIME COMPLEXITY:
 * Every cell is visited at most once.
 * DFS traversal across matrix → O(N * M)
 *
 * SPACE COMPLEXITY:
 * Visited matrix → O(N * M)
 * DFS recursion stack (worst case) → O(N * M)
 *
 * ===========================================================================
 *
 * ========================== ALTERNATIVE APPROACHES =========================
 *
 * 1️⃣ Using BFS instead of DFS:
 * - Use a queue instead of recursion.
 * - Prevents stack overflow on large grids.
 * - Same complexity → O(N*M)
 *
 * 2️⃣ In-place marking without extra 'vis' array:
 * - Temporarily mark safe 'O' as '*'
 * - Convert remaining 'O' to 'X'
 * - Convert '*' back to 'O'
 * Saves space but reduces readability.
 *
 * ===========================================================================
 */

public class SurroundedRegionDFS {

    /**
     * Depth First Search (DFS)
     * ----------------------------------------------------------------------
     * This DFS marks all 'O' cells connected to a boundary 'O'.
     * These cells are SAFE and should NOT be converted to 'X'.
     *
     * @param r   current row
     * @param c   current column
     * @param vis visited matrix to avoid repeat processing
     * @param mat original board
     * @param dr  row direction array
     * @param dc  column direction array
     */
    private static void dfs(int r, int c, int[][] vis, char[][] mat, int[] dr, int[] dc) {

        // Mark current cell as visited (meaning: safe O)
        vis[r][c] = 1;

        // Matrix dimensions
        int n = mat.length;
        int m = mat[0].length;

        // Explore all 4 directions
        for (int k = 0; k < 4; k++) {

            int nr = r + dr[k];   // next row
            int nc = c + dc[k];   // next column

            // Check: inside bounds + unvisited + still 'O'
            if (nr >= 0 && nr < n && nc >= 0 && nc < m &&
                    vis[nr][nc] == 0 && mat[nr][nc] == 'O') {

                // Continue DFS deeper to mark connected 'O's
                dfs(nr, nc, vis, mat, dr, dc);
            }
        }
    }

    /**
     * Function to replace surrounded 'O' with 'X'
     *
     * @param n   number of rows
     * @param m   number of cols
     * @param mat board
     * @return updated board
     */
    private static char[][] fill(int n, int m, char[][] mat) {

        // Edge case: empty matrix safeguard
        if (n == 0 || m == 0) return mat;

        // Direction vectors for UP, RIGHT, DOWN, LEFT
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        // Visited matrix to mark safe boundary-connected 'O'
        int[][] vis = new int[n][m];

        // ------------------------------------------------------------------
        // STEP 1: TRAVERSE FIRST & LAST ROWS
        // ------------------------------------------------------------------
        for (int j = 0; j < m; j++) {

            // Top row boundary
            if (vis[0][j] == 0 && mat[0][j] == 'O')
                dfs(0, j, vis, mat, dr, dc);

            // Bottom row boundary
            if (vis[n - 1][j] == 0 && mat[n - 1][j] == 'O')
                dfs(n - 1, j, vis, mat, dr, dc);
        }

        // ------------------------------------------------------------------
        // STEP 2: TRAVERSE FIRST & LAST COLUMNS
        // ------------------------------------------------------------------
        for (int i = 0; i < n; i++) {

            // Left boundary
            if (vis[i][0] == 0 && mat[i][0] == 'O')
                dfs(i, 0, vis, mat, dr, dc);

            // Right boundary
            if (vis[i][m - 1] == 0 && mat[i][m - 1] == 'O')
                dfs(i, m - 1, vis, mat, dr, dc);
        }

        // ------------------------------------------------------------------
        // STEP 3: Convert all unvisited 'O' → 'X'
        // These are the surrounded regions
        // ------------------------------------------------------------------
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // If not visited & still 'O' => it is enclosed
                if (vis[i][j] == 0 && mat[i][j] == 'O')
                    mat[i][j] = 'X';
            }
        }

        return mat;
    }

    /**
     * ============================ TESTING MAIN ===============================
     * Demonstrates functionality with a sample matrix.
     */
    public static void main(String[] args) {

        // Sample board input
        char[][] mat = {
                {'X','X','X','X'},
                {'X','O','X','X'},
                {'X','O','O','X'},
                {'X','O','X','X'},
                {'X','X','O','O'}
        };

        // Call function
        char[][] ans = fill(mat.length, mat[0].length, mat);

        // Print output
        System.out.println("Final Board:");
        for (char[] row : ans) {
            for (char ch : row) System.out.print(ch + " ");
            System.out.println();
        }
    }
}

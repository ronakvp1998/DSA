package com.questions.strivers.graph.problemsbfsdfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ========================= Problem: Rotten Oranges (BFS) =========================
 * <p>
 * You are given a grid (matrix) where:
 * 0 → Empty cell
 * 1 → Fresh orange
 * 2 → Rotten orange
 * <p>
 * Every minute, any fresh orange that is 4-directionally adjacent (up, down, left, right)
 * to a rotten orange becomes rotten.
 * <p>
 * We must determine:
 * -> Minimum time required to rot all fresh oranges.
 * -> If it is impossible to rot all oranges, return -1.
 * <p>
 * This is a classical multi-source BFS problem.
 * <p>
 * ===========================================================================
 * APPROACH (Breadth First Search - BFS)
 * ===========================================================================
 * 1️⃣ Treat every initially rotten orange as a starting point (multi-source BFS).
 * - Push all rotten oranges into a queue initially with time = 0
 * <p>
 * 2️⃣ Perform BFS level by level:
 * - Each BFS level = 1 minute passing
 * - Whenever we rot a fresh orange, push it into queue with incremented time.
 * <p>
 * 3️⃣ Track:
 * - Count of total fresh oranges (cntFresh)
 * - Count of oranges that actually become rotten (cnt)
 * - Maximum time taken during BFS
 * <p>
 * 4️⃣ At the end:
 * - If cnt != cntFresh → Some oranges never rotted → return -1
 * - Otherwise return total time
 * <p>
 * ===========================================================================
 * WHY BFS?
 * ===========================================================================
 * ✔ BFS processes nodes in layers → perfectly simulates "time passing"
 * ✔ Suitable where spreading happens step-by-step in equal cost waves
 * <p>
 * ===========================================================================
 * EDGE CASES
 * ===========================================================================
 * - No fresh oranges → answer = 0
 * - Fresh oranges completely isolated → return -1
 * - Single rotten orange in middle should rot gradually outward correctly
 * <p>
 * ===========================================================================
 * TIME & SPACE COMPLEXITY
 * ===========================================================================
 * Time Complexity:  O(N * M)
 * We visit each cell at most once.
 * <p>
 * Space Complexity: O(N * M)
 * Queue + visited matrix stores at most all cells.
 * <p>
 * Best Case:
 * Already all rotten → O(N*M) scan only, time returned = 0
 * <p>
 * Worst Case:
 * Oranges rot level-by-level till end of grid
 * <p>
 * ===========================================================================
 * ALTERNATIVE APPROACHES
 * ===========================================================================
 * DFS ❌ Not suitable
 * DFS does NOT work because DFS goes deep instead of level-by-level,
 * so it cannot simulate "time in waves".
 * <p>
 * Without Vis Matrix (Modify grid directly) ✔
 * Instead of visited[][] we can directly overwrite grid values to avoid extra space.
 * But visited helps clarity, avoids modifying input in interviews.
 * <p>
 * ===========================================================================
 * INTERVIEW TIP
 * ===========================================================================
 * - Recognize pattern: "spreading problem" → think BFS
 * - Multiple starting points → Push all into queue initially
 * - Level-based traversal → time tracking using queue timestamps
 */
public class RottenOrangesBsf {

    /**
     * Helper class to store:
     * row index, column index, and the time at which this orange became rotten
     */
    static class Pair {
        int nRow;
        int nCol;
        int time;

        public Pair(int nRow, int nCol, int time) {
            this.nRow = nRow;
            this.nCol = nCol;
            this.time = time;
        }
    }

    /**
     * Function to return the minimum time required to rot all oranges.
     * Returns -1 if impossible.
     */
    private static int orangeRotting(int[][] grid) {

        int n = grid.length;        // Total rows
        int m = grid[0].length;     // Total columns

        Queue<Pair> queue = new LinkedList<>();

        int[][] vis = new int[n][m];    // Visited matrix to mark rotten oranges
        int cntFresh = 0;               // Count of fresh oranges

        // STEP 1: Initialize the queue with all initial rotten oranges
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (grid[i][j] == 2) {
                    queue.add(new Pair(i, j, 0));   // Push rotten orange with time 0
                    vis[i][j] = 2;                  // Mark visited as rotten
                } else {
                    vis[i][j] = 0;                  // Mark as not visited yet
                }

                if (grid[i][j] == 1) {
                    cntFresh++;                     // Count fresh oranges
                }
            }
        }

        int time = 0;                 // Stores total time required
        int cnt = 0;                  // Count how many oranges actually became rotten

        // Directions array for 4-directional traversal
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};

        // STEP 2: BFS traversal
        while (!queue.isEmpty()) {

            int r = queue.peek().nRow;
            int c = queue.peek().nCol;
            int t = queue.peek().time;
            queue.remove();

            time = Math.max(time, t); // Track max time level reached

            // Explore all 4 neighbours
            for (int i = 0; i < 4; i++) {
                int nRow = r + dRow[i];
                int nCol = c + dCol[i];

                // Check valid index + not visited + fresh orange
                if (nRow >= 0 && nRow < n && nCol >= 0 && nCol < m &&
                        vis[nRow][nCol] == 0 && grid[nRow][nCol] == 1) {
                    queue.add(new Pair(nRow, nCol, t + 1)); // Rot it at next time
                    vis[nRow][nCol] = 2;                    // Mark rotten
                    cnt++;                                  // Count rotted fresh orange
                }
            }
        }

        // STEP 3: If not all fresh oranges rotted → impossible
        if (cnt != cntFresh) return -1;

        return time;
    }

    /**
     * ====================== MAIN METHOD FOR TESTING ========================
     */
    public static void main(String[] args) {

        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };

        int result = orangeRotting(grid);
        System.out.println("Minimum Time to Rot All Oranges = " + result);
        // Expected Output: 4
    }
}

package com.questions.strivers.recursionbacktracking.hard;

import java.util.ArrayList;

/*
Problem Statement:
Rat in a Maze:
A rat is placed at the starting position (0, 0) in a square matrix (N x N).
The goal is to reach the destination at (N-1, N-1).
- Allowed moves: 'U' (up), 'D' (down), 'L' (left), 'R' (right).
- A cell with value 0 is blocked (cannot move through).
- A cell with value 1 is open (can move through).
- No cell can be visited more than once in a path.
- Return all possible paths sorted in lexicographical order.

Example:
Input:
N = 4
m[][] = {{1, 0, 0, 0},
         {1, 1, 0, 1},
         {1, 1, 0, 0},
         {0, 1, 1, 1}}
Output: DDRDRR DRDDRR
*/
public class RatInRace {

    /*
     * Recursive helper function to explore all possible paths.
     * Parameters:
     *   i, j   -> current position of the rat
     *   a[][]  -> input maze matrix
     *   n      -> size of the matrix
     *   ans    -> list to store all valid paths
     *   move   -> current path string (sequence of 'U','D','L','R')
     *   vis[][]-> visited matrix to track visited cells
     */
    private static void solve(int i, int j, int a[][], int n, ArrayList<String> ans, String move,
                              int vis[][]) {
        // Base case: if destination (n-1, n-1) is reached
        if (i == n - 1 && j == n - 1) {
            ans.add(move);  // store the valid path
            return;
        }

        // Move Down
        if (i + 1 < n && vis[i + 1][j] == 0 && a[i + 1][j] == 1) {
            vis[i][j] = 1; // mark current cell visited
            solve(i + 1, j, a, n, ans, move + 'D', vis);
            vis[i][j] = 0; // backtrack (unmark cell)
        }

        // Move Left
        if (j - 1 >= 0 && vis[i][j - 1] == 0 && a[i][j - 1] == 1) {
            vis[i][j] = 1;
            solve(i, j - 1, a, n, ans, move + 'L', vis);
            vis[i][j] = 0;
        }

        // Move Right
        if (j + 1 < n && vis[i][j + 1] == 0 && a[i][j + 1] == 1) {
            vis[i][j] = 1;
            solve(i, j + 1, a, n, ans, move + 'R', vis);
            vis[i][j] = 0;
        }

        // Move Up
        if (i - 1 >= 0 && vis[i - 1][j] == 0 && a[i - 1][j] == 1) {
            vis[i][j] = 1;
            solve(i - 1, j, a, n, ans, move + 'U', vis);
            vis[i][j] = 0;
        }
    }

    /*
     * Function to find all paths from (0,0) to (n-1,n-1).
     * It initializes the visited array and calls the recursive solver.
     */
    public static ArrayList<String> findPath(int[][] m, int n) {
        int vis[][] = new int[n][n];  // visited array to track path
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                vis[i][j] = 0; // initialize all cells as unvisited
            }
        }

        ArrayList<String> ans = new ArrayList<>();
        if (m[0][0] == 1) { // start only if source cell is open
            solve(0, 0, m, n, ans, "", vis);
        }
        return ans;
    }

    // Driver function to test the solution
    public static void main(String[] args) {
        int n = 4;
        int[][] a = {
                {1,0,0,0},
                {1,1,0,1},
                {1,1,0,0},
                {0,1,1,1}
        };

        ArrayList<String> res = findPath(a, n);
        if (res.size() > 0) {
            for (int i = 0; i < res.size(); i++)
                System.out.print(res.get(i) + " ");
            System.out.println();
        } else {
            System.out.println(-1); // if no path exists
        }
    }
}

/*
----------------------
TIME COMPLEXITY:
----------------------
- In the worst case, the rat can move in 4 directions from each cell.
- For an N x N matrix, the recursion explores paths with exponential branching.
- Upper bound: O(4^(N*N)) in the worst case.
- However, due to pruning (visited cells and blocked paths), actual complexity is much less.
- Practically: O(N^2 * 4^(N^2)) in brute force sense.

----------------------
SPACE COMPLEXITY:
----------------------
- Visited matrix: O(N^2)
- Recursion stack (backtracking): O(N^2) in the worst case (path covering all cells).
- Path storage in ans: O(K * L) where K = number of valid paths, L = average path length (≤ N^2).
- Overall: O(N^2 + K*L)
*/

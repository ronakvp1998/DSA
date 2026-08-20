package strivers.recursionbacktracking.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * # Rat in a Maze
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Given a grid of dimensions n x n. A rat is placed at coordinates (0, 0) and wants to reach
 * coordinates (n-1, n-1). Find all possible paths that the rat can take to travel from (0, 0)
 * to (n-1, n-1). The directions in which the rat can move are 'U' (up), 'D' (down), 'L' (left),
 * 'R' (right).
 *
 * The value 0 in the grid denotes that the cell is blocked and the rat cannot use that cell
 * for travelling, whereas value 1 represents that the rat can travel through the cell. If the
 * cell (0, 0) has a 0 value, then the mouse cannot move to any other cell.
 *
 * **Constraints:**
 * - 2 <= n <= 5
 * - 0 <= grid[i][j] <= 1
 *
 * **Examples:**
 * - Input: n = 4, grid = [[1, 0, 0, 0], [1, 1, 0, 1], [1, 1, 0, 0], [0, 1, 1, 1]]
 *   Output: ["DDRDRR", "DRDDRR"]
 *   Explanation: The rat has two different paths to reach (3, 3).
 *
 * - Input: n = 2, grid = [[1, 0], [1, 0]]
 *   Output: []
 *   Explanation: There is no path that the rat can choose to travel from (0,0) to (1,1).
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Backtracking with In-Place Visited Marking & Lexicographical Ordering.
 * * **Phase 2: Brute Force Approach** - Standard Backtracking with an explicit `visited` matrix and String concatenation.
 * * **Phase 3: Alternative Approaches** - Breadth-First Search (BFS) is excellent for finding the *shortest* path,
 *   but it is highly memory-inefficient for finding *all* paths, as it requires storing every active path in a Queue.
 */
public class RatInAMaze {

    /**
     * ## Phase 1: Optimal Approach - In-Place Backtracking
     *
     * **Detailed Intuition:**
     * To find all paths, Depth-First Search (DFS) with Backtracking is required.
     * We can optimize standard backtracking in two ways:
     * 1. **Space Optimization:** Instead of passing a `boolean[][] visited` array, we temporarily
     *    mark the current cell in the grid as `0` (blocked) while diving down the recursion tree,
     *    and restore it to `1` when backtracking.
     * 2. **Lexicographical Output:** Competitive programming platforms usually expect the output
     *    strings to be sorted alphabetically. If we explore directions strictly in the order of
     *    'D' (Down), 'L' (Left), 'R' (Right), 'U' (Up), our result list will naturally be generated
     *    in lexicographical order, bypassing the need for a final `Collections.sort()`.
     * 3. **StringBuilder:** Avoid immutable `String` concatenation to reduce GC pressure.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(4^{n^2})$ in the worst case, as from each cell we can potentially
     *   move in 4 directions, and a path can be up to $n^2$ cells long.
     * - **Space Complexity:** $O(n^2)$ for the auxiliary recursion stack depth. $O(1)$ auxiliary
     *   heap space since we modify the grid in-place (excluding the output list).
     */
    public List<String> findPathsOptimal(int[][] grid, int n) {
        List<String> result = new ArrayList<>();
        // Edge case: Start or End is blocked
        if (grid[0][0] == 0 || grid[n - 1][n - 1] == 0) {
            return result;
        }

        // Direction vectors strictly in 'D', 'L', 'R', 'U' order
        int[] dRow = {1, 0, 0, -1};
        int[] dCol = {0, -1, 1, 0};
        char[] dirChars = {'D', 'L', 'R', 'U'};

        dfsOptimal(0, 0, grid, n, new StringBuilder(), result, dRow, dCol, dirChars);
        return result;
    }

    private void dfsOptimal(int row, int col, int[][] grid, int n, StringBuilder currentPath,
                            List<String> result, int[] dRow, int[] dCol, char[] dirChars) {
        // Base Condition: Reached destination
        if (row == n - 1 && col == n - 1) {
            result.add(currentPath.toString());
            return;
        }

        // Mark as visited by mutating the grid
        grid[row][col] = 0;

        // Explore all 4 valid directions
        for (int i = 0; i < 4; i++) {
            int nextRow = row + dRow[i];
            int nextCol = col + dCol[i];

            // Boundary and Valid Cell Check
            if (nextRow >= 0 && nextRow < n && nextCol >= 0 && nextCol < n && grid[nextRow][nextCol] == 1) {
                currentPath.append(dirChars[i]); // DO
                dfsOptimal(nextRow, nextCol, grid, n, currentPath, result, dRow, dCol, dirChars);
                currentPath.deleteCharAt(currentPath.length() - 1); // UNDO (Backtrack)
            }
        }

        // Restore the grid state (Backtrack)
        grid[row][col] = 1;
    }

    /**
     * ## Phase 2: Brute Force Approach - Standard Backtracking
     *
     * **Detailed Intuition:**
     * This represents the "Think it" phase. It uses an explicit `visited` boolean matrix to track
     * the rat's path, and passes an immutable `String` object down the recursion tree.
     * It relies on 4 manual `if` blocks instead of a unified loop with direction arrays.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(4^{n^2})$. String concatenation adds a hidden cost of $O(L)$ where $L$
     *   is the path length, increasing the constant factor significantly.
     * - **Space Complexity:** $O(n^2)$ for the auxiliary stack space, plus $O(n^2)$ for the `visited`
     *   matrix, and high heap allocation for temporary `String` instances.
     */
    public List<String> findPathsBruteForce(int[][] grid, int n) {
        List<String> result = new ArrayList<>();
        if (grid[0][0] == 0 || grid[n - 1][n - 1] == 0) {
            return result;
        }

        boolean[][] visited = new boolean[n][n];
        dfsBruteForce(0, 0, grid, n, "", visited, result);
        return result;
    }

    private void dfsBruteForce(int row, int col, int[][] grid, int n, String currentPath,
                               boolean[][] visited, List<String> result) {
        if (row == n - 1 && col == n - 1) {
            result.add(currentPath);
            return;
        }

        visited[row][col] = true;

        // Down
        if (row + 1 < n && !visited[row + 1][col] && grid[row + 1][col] == 1) {
            dfsBruteForce(row + 1, col, grid, n, currentPath + "D", visited, result);
        }
        // Left
        if (col - 1 >= 0 && !visited[row][col - 1] && grid[row][col - 1] == 1) {
            dfsBruteForce(row, col - 1, grid, n, currentPath + "L", visited, result);
        }
        // Right
        if (col + 1 < n && !visited[row][col + 1] && grid[row][col + 1] == 1) {
            dfsBruteForce(row, col + 1, grid, n, currentPath + "R", visited, result);
        }
        // Up
        if (row - 1 >= 0 && !visited[row - 1][col] && grid[row - 1][col] == 1) {
            dfsBruteForce(row - 1, col, grid, n, currentPath + "U", visited, result);
        }

        visited[row][col] = false; // Backtrack
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        RatInAMaze solver = new RatInAMaze();

        class TestCase {
            int n;
            int[][] grid;
            TestCase(int n, int[][] grid) {
                this.n = n;
                this.grid = grid;
            }
        }

        TestCase[] tests = {
                new TestCase(4, new int[][]{
                        {1, 0, 0, 0},
                        {1, 1, 0, 1},
                        {1, 1, 0, 0},
                        {0, 1, 1, 1}
                }),
                new TestCase(2, new int[][]{
                        {1, 0},
                        {1, 0}
                }),
                // Edge Case: Blocked at start
                new TestCase(2, new int[][]{
                        {0, 1},
                        {1, 1}
                })
        };

        System.out.println("--- Running Rat in a Maze Tests ---");

        Stream.of(tests).forEach(test -> {
            System.out.println("\nTesting Grid (n = " + test.n + ")");

            // Deep copy grid for brute force to ensure optimal doesn't corrupt it if a bug occurs
            int[][] gridCopy = Arrays.stream(test.grid).map(int[]::clone).toArray(int[][]::new);

            List<String> optimalRes = solver.findPathsOptimal(test.grid, test.n);
            List<String> bruteRes = solver.findPathsBruteForce(gridCopy, test.n);

            System.out.println("Optimal Result:    " + optimalRes);
            System.out.println("BruteForce Result: " + bruteRes);
            System.out.println("Match? " + (optimalRes.equals(bruteRes) ? "YES" : "NO"));
            System.out.println("-----------------------------------");
        });
    }
}


//
//import java.util.ArrayList;
//
///*Rat in a Maze
//
//
//5
//
//Problem Statement: Given a grid of dimensions n x n. A rat is placed at coordinates (0, 0) and wants to reach at coordinates (n-1, n-1). Find all possible paths that rat can take to travel from (0, 0) to (n-1, n-1). The directions in which rat can move are 'U' (up) , 'D' (down) , 'L' (left) , 'R' (right).
//The value 0 in grid denotes that the cell is blocked and rat cannot use that cell for travelling, whereas value 1 represents that rat can travel through the cell. If the cell (0, 0) has 0 value, then mouse cannot move to any other cell.
//
//Examples
//Input: n = 4 , grid = [ [1, 0, 0, 0] , [1, 1, 0, 1], [1, 1, 0, 0], [0, 1, 1, 1] ]
//Output: ["DDRDRR" , "DRDDRR"]
//Explanation: The rat has two different path to reach (3, 3).
//The first path is (0, 0) => (1, 0) => (2, 0) => (2, 1) => (3, 1) => (3, 2) => (3, 3).
//The second path is (0,0) => (1,0) => (1,1) => (2,1) => (3,1) => (3,2) => (3,3).
//
//Input: n = 2 , grid = [[1, 0] , [1, 0]]
//Output: []
//Explanation: There is no path that rat can choose to travel from (0,0) to (1,1)
//*/
//public class RatInRace {
//
//    /*
//     * Recursive helper function to explore all possible paths.
//     * Parameters:
//     *   i, j   -> current position of the rat
//     *   a[][]  -> input maze matrix
//     *   n      -> size of the matrix
//     *   ans    -> list to store all valid paths
//     *   move   -> current path string (sequence of 'U','D','L','R')
//     *   vis[][]-> visited matrix to track visited cells
//     */
//    private static void solve(int i, int j, int a[][], int n, ArrayList<String> ans, String move,
//                              int vis[][]) {
//        // Base case: if destination (n-1, n-1) is reached
//        if (i == n - 1 && j == n - 1) {
//            ans.add(move);  // store the valid path
//            return;
//        }
//
//        // Move Down
//        if (i + 1 < n && vis[i + 1][j] == 0 && a[i + 1][j] == 1) {
//            vis[i][j] = 1; // mark current cell visited
//            solve(i + 1, j, a, n, ans, move + 'D', vis);
//            vis[i][j] = 0; // backtrack (unmark cell)
//        }
//
//        // Move Left
//        if (j - 1 >= 0 && vis[i][j - 1] == 0 && a[i][j - 1] == 1) {
//            vis[i][j] = 1;
//            solve(i, j - 1, a, n, ans, move + 'L', vis);
//            vis[i][j] = 0;
//        }
//
//        // Move Right
//        if (j + 1 < n && vis[i][j + 1] == 0 && a[i][j + 1] == 1) {
//            vis[i][j] = 1;
//            solve(i, j + 1, a, n, ans, move + 'R', vis);
//            vis[i][j] = 0;
//        }
//
//        // Move Up
//        if (i - 1 >= 0 && vis[i - 1][j] == 0 && a[i - 1][j] == 1) {
//            vis[i][j] = 1;
//            solve(i - 1, j, a, n, ans, move + 'U', vis);
//            vis[i][j] = 0;
//        }
//    }
//
//    /*
//     * Function to find all paths from (0,0) to (n-1,n-1).
//     * It initializes the visited array and calls the recursive solver.
//     */
//    private static ArrayList<String> findPath(int[][] m, int n) {
//        int vis[][] = new int[n][n];  // visited array to track path
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                vis[i][j] = 0; // initialize all cells as unvisited
//            }
//        }
//
//        ArrayList<String> ans = new ArrayList<>();
//        if (m[0][0] == 1) { // start only if source cell is open
//            solve(0, 0, m, n, ans, "", vis);
//        }
//        return ans;
//    }
//
//    // Driver function to test the solution
//    public static void main(String[] args) {
//        int n = 4;
//        int[][] a = {
//                {1,0,0,0},
//                {1,1,0,1},
//                {1,1,0,0},
//                {0,1,1,1}
//        };
//
//        ArrayList<String> res = findPath(a, n);
//        if (res.size() > 0) {
//            for (int i = 0; i < res.size(); i++)
//                System.out.print(res.get(i) + " ");
//            System.out.println();
//        } else {
//            System.out.println(-1); // if no path exists
//        }
//    }
//}
//
///*
//----------------------
//TIME COMPLEXITY:
//----------------------
//- In the worst case, the rat can move in 4 directions from each cell.
//- For an N x N matrix, the recursion explores paths with exponential branching.
//- Upper bound: O(4^(N*N)) in the worst case.
//- However, due to pruning (visited cells and blocked paths), actual complexity is much less.
//- Practically: O(N^2 * 4^(N^2)) in brute force sense.
//
//----------------------
//SPACE COMPLEXITY:
//----------------------
//- Visited matrix: O(N^2)
//- Recursion stack (backtracking): O(N^2) in the worst case (path covering all cells).
//- Path storage in ans: O(K * L) where K = number of valid paths, L = average path length (≤ N^2).
//- Overall: O(N^2 + K*L)
//*/

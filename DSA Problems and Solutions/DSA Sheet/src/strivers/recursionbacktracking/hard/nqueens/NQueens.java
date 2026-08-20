package strivers.recursionbacktracking.hard.nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * # 51. N-Queens
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard
 * such that no two queens attack each other.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * You may return the answer in any order.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement,
 * where 'Q' and '.' both indicate a queen and an empty space, respectively.
 *
 * **Constraints:**
 * - 1 <= n <= 9
 *
 * **Examples:**
 * Example 1:
 * Input: n = 4
 * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * Explanation: There exist two distinct solutions to the 4-queens puzzle.
 *
 * Example 2:
 * Input: n = 1
 * Output: [["Q"]]
 *
 * ---
 *
 * ## Conceptual Visualization (Diagonal Hashing)
 * When placing queens column by column, we must ensure the current row and both
 * left-facing diagonals are clear. Instead of traversing the board, we can map them:
 *
 * - **Left Row:** Indexed directly by `row`.
 * - **Lower Diagonal (\):** The sum of `row + col` is constant for any specific diagonal.
 * - **Upper Diagonal (/):** The difference `(n - 1) + (col - row)` is constant.
 *
 * By using simple boolean/integer arrays for these mappings, we turn an $O(N)$
 * validity check into an $O(1)$ lookup.
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Backtracking with Array Hashing for $O(1)$ lookups.
 * * **Phase 2: Brute Force Approach** - Standard Backtracking with $O(N)$ matrix traversal for validation.
 * * **Phase 3: Alternative Approach** - Bitmask Optimization (Using integers instead of arrays).
 */
public class NQueens {

    /**
     * ## Phase 1: Optimal Approach - Backtracking with Array Hashing
     *
     * **Detailed Intuition:**
     * Instead of linearly checking the left row, upper-left diagonal, and lower-left
     * diagonal every time we want to place a queen, we maintain three tracking arrays.
     * When a queen is placed at `(row, col)`, we mark the respective indices in `leftRow`,
     * `lowerDiagonal`, and `upperDiagonal` as used (1). When backtracking, we unmark them (0).
     * This eliminates the inner loops for safety checks.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N!)$. For the first column we have $N$ choices, for the second
     *   at most $N-2$, then $N-4$, etc. Checking safety is $O(1)$.
     * - **Space Complexity:** $O(N^2)$ for the board state string construction, plus $O(N)$
     *   for the tracking arrays and recursive auxiliary stack space.
     */
    public List<List<String>> solveNQueensOptimal(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }

        int[] leftRow = new int[n];
        int[] lowerDiagonal = new int[2 * n - 1];
        int[] upperDiagonal = new int[2 * n - 1];

        solveOptimal(0, board, result, leftRow, lowerDiagonal, upperDiagonal, n);
        return result;
    }

    private void solveOptimal(int col, char[][] board, List<List<String>> result,
                              int[] leftRow, int[] lowerDiagonal, int[] upperDiagonal, int n) {
        if (col == n) {
            result.add(constructBoard(board));
            return;
        }

        for (int row = 0; row < n; row++) {
            if (leftRow[row] == 0 && lowerDiagonal[row + col] == 0 && upperDiagonal[n - 1 + col - row] == 0) {
                // Place Queen
                board[row][col] = 'Q';
                leftRow[row] = 1;
                lowerDiagonal[row + col] = 1;
                upperDiagonal[n - 1 + col - row] = 1;

                // Recurse
                solveOptimal(col + 1, board, result, leftRow, lowerDiagonal, upperDiagonal, n);

                // Backtrack
                board[row][col] = '.';
                leftRow[row] = 0;
                lowerDiagonal[row + col] = 0;
                upperDiagonal[n - 1 + col - row] = 0;
            }
        }
    }

    /**
     * ## Phase 2: Brute Force Approach - Matrix Traversal Validation
     *
     * **Detailed Intuition:**
     * The most intuitive way to solve N-Queens is to manually verify the board.
     * For every column transition, we check the current row, upper-left diagonal,
     * and lower-left diagonal by walking backwards through the grid until we hit an edge.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N! \times N)$. The safety check adds an $O(N)$ operation
     *   at every node in the recursion tree.
     * - **Space Complexity:** $O(N^2)$ for storing the board and results, plus $O(N)$ stack space.
     */
    public List<List<String>> solveNQueensBruteForce(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        solveBruteForce(0, board, result, n);
        return result;
    }

    private void solveBruteForce(int col, char[][] board, List<List<String>> result, int n) {
        if (col == n) {
            result.add(constructBoard(board));
            return;
        }

        for (int row = 0; row < n; row++) {
            if (isSafeBruteForce(row, col, board, n)) {
                board[row][col] = 'Q';
                solveBruteForce(col + 1, board, result, n);
                board[row][col] = '.'; // Backtrack
            }
        }
    }

    private boolean isSafeBruteForce(int row, int col, char[][] board, int n) {
        int dupRow = row;
        int dupCol = col;

        // Check Upper-Left Diagonal
        while (row >= 0 && col >= 0) {
            if (board[row][col] == 'Q') return false;
            row--;
            col--;
        }

        // Check Left Row
        row = dupRow;
        col = dupCol;
        while (col >= 0) {
            if (board[row][col] == 'Q') return false;
            col--;
        }

        // Check Lower-Left Diagonal
        row = dupRow;
        col = dupCol;
        while (col >= 0 && row < n) {
            if (board[row][col] == 'Q') return false;
            col--;
            row++;
        }

        return true;
    }

    /**
     * ## Phase 3: Alternative Approach - Bitmask Optimization
     *
     * **Detailed Intuition:**
     * We can replace the boolean/integer tracking arrays from Phase 1 with integers
     * acting as bitmasks. Since $N \le 9$, an integer (32 bits) is more than enough
     * to store the states of columns and diagonals.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N!)$, but with highly optimized constant factors due to bitwise operations.
     * - **Space Complexity:** $O(N^2)$ for output generation, but auxiliary space is purely $O(N)$ stack.
     */
    public List<List<String>> solveNQueensBitmask(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(board[i], '.');

        solveBitmask(0, 0, 0, 0, board, result, n);
        return result;
    }

    private void solveBitmask(int col, int leftRowMask, int lowerDiagMask, int upperDiagMask,
                              char[][] board, List<List<String>> result, int n) {
        if (col == n) {
            result.add(constructBoard(board));
            return;
        }

        // Available rows represent bits that are 0 in all three masks.
        // We shift the diagonal masks relative to the column progression.
        for (int row = 0; row < n; row++) {
            int rowBit = 1 << row;
            int lowerDiagBit = 1 << (row + col);
            int upperDiagBit = 1 << (n - 1 + col - row);

            if ((leftRowMask & rowBit) == 0 &&
                    (lowerDiagMask & lowerDiagBit) == 0 &&
                    (upperDiagMask & upperDiagBit) == 0) {

                board[row][col] = 'Q';

                solveBitmask(col + 1,
                        leftRowMask | rowBit,
                        lowerDiagMask | lowerDiagBit,
                        upperDiagMask | upperDiagBit,
                        board, result, n);

                board[row][col] = '.'; // Backtrack
            }
        }
    }

    // Helper method to convert the char matrix into a List of Strings
    private List<String> constructBoard(char[][] board) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            res.add(new String(board[i]));
        }
        return res;
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        NQueens solver = new NQueens();

        // Testing edge case N=1 and standard case N=4, N=8
        int[] testCases = {1, 4, 8};

        System.out.println("--- Running N-Queens Tests ---");

        IntStream.of(testCases).forEach(n -> {
            System.out.println("\nTesting N = " + n);

            long startOpt = System.nanoTime();
            List<List<String>> optimalRes = solver.solveNQueensOptimal(n);
            long timeOpt = System.nanoTime() - startOpt;

            long startBF = System.nanoTime();
            List<List<String>> bruteRes = solver.solveNQueensBruteForce(n);
            long timeBF = System.nanoTime() - startBF;

            long startBit = System.nanoTime();
            List<List<String>> bitmaskRes = solver.solveNQueensBitmask(n);
            long timeBit = System.nanoTime() - startBit;

            System.out.println("Total Distinct Solutions: " + optimalRes.size());
            System.out.printf("Optimal Time:    %.3f ms\n", timeOpt / 1_000_000.0);
            System.out.printf("BruteForce Time: %.3f ms\n", timeBF / 1_000_000.0);
            System.out.printf("Bitmask Time:    %.3f ms\n", timeBit / 1_000_000.0);

            // Validating that all approaches yield the exact same number of results
            boolean match = (optimalRes.size() == bruteRes.size()) && (bruteRes.size() == bitmaskRes.size());
            System.out.println("Result Match Validation: " + (match ? "PASS" : "FAIL"));

            // Print the first solution for visualization if N is small
            if (n == 4 && !optimalRes.isEmpty()) {
                System.out.println("Sample Solution for N=4:");
                optimalRes.get(0).forEach(System.out::println);
            }
            System.out.println("------------------------------");
        });
    }
}

//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//public class NQueens {
//
//    /*
//     Problem Statement:
//        Place N queens on an N x N chessboard such that no two queens attack each other.
//        Queens can attack in the same row, column, and diagonals.
//        Return all possible valid board configurations.
//
//     Example:
//        Input: N = 4
//        Output:
//        [
//          [".Q..",
//           "...Q",
//           "Q...",
//           "..Q."],
//
//          ["..Q.",
//           "Q...",
//           "...Q",
//           ".Q.."]
//        ]
//    */
//
//    // Main function to solve the N-Queens problem
//    private static List<List<String>> solveNQueens(int n) {
//        // Create an empty board with '.'
//        char[][] board = new char[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                board[i][j] = '.';
//
//        List<List<String>> res = new ArrayList<>();
//        // Start DFS (backtracking) from column 0
//        dfs(0, board, res);
//        return res;
//    }
//
//    // Recursive DFS function to place queens column by column
//    static void dfs(int col, char[][] board, List<List<String>> res) {
//        // Base case: if we placed queens in all columns, add configuration to result
//        if (col == board.length) {
//            res.add(construct(board));
//            return;
//        }
//
//        // Try placing queen in each row of the current column
//        for (int row = 0; row < board.length; row++) {
//            if (validate(board, row, col)) { // Check if safe
//                board[row][col] = 'Q';       // Place queen
//                dfs(col + 1, board, res);    // Move to next column
//                board[row][col] = '.';       // Backtrack (remove queen)
//            }
//        }
//    }
//
//    // Convert board state (2D char array) into List<String>
//    static List<String> construct(char[][] board) {
//        List<String> res = new LinkedList<>();
//        for (int i = 0; i < board.length; i++) {
//            String s = new String(board[i]); // Convert char[] row -> String
//            res.add(s);
//        }
//        return res;
//    }
//
//    // Validate if a queen can be placed at (row, col)
//    static boolean validate(char[][] board, int row, int col) {
//        int duprow = row;
//        int dupcol = col;
//
//        // Check upper-left diagonal
//        while (row >= 0 && col >= 0) {
//            if (board[row][col] == 'Q') return false;
//            row--;
//            col--;
//        }
//
//        // Reset
//        row = duprow;
//        col = dupcol;
//
//        // Check left side (row-wise)
//        while (col >= 0) {
//            if (board[row][col] == 'Q') return false;
//            col--;
//        }
//
//        // Reset
//        row = duprow;
//        col = dupcol;
//
//        // Check lower-left diagonal
//        while (col >= 0 && row < board.length) {
//            if (board[row][col] == 'Q') return false;
//            col--;
//            row++;
//        }
//        return true; // Safe position
//    }
//
//    // Driver function to test the solution
//    public static void main(String args[]) {
//        int N = 4;
//        List<List<String>> queen = solveNQueens(N);
//        int i = 1;
//        for (List<String> it: queen) {
//            System.out.println("Arrangement " + i);
//            for (String s: it) {
//                System.out.println(s);
//            }
//            System.out.println();
//            i += 1;
//        }
//    }
//}
//
///*
//------------------------------------------------
//⏱ Time Complexity:
//- At each column, we try N rows → O(N)
//- For each placement, validation takes O(N) (checking diagonals and row).
//- In the worst case, we explore N! configurations (placing N queens in N columns).
//=> Overall Time Complexity ≈ O(N! * N)
//
//💾 Space Complexity:
//- Board size: O(N^2)
//- Recursion depth: O(N) (since we place one queen per column)
//- Result storage depends on the number of solutions.
//
//=> Overall Space Complexity ≈ O(N^2)
//------------------------------------------------
//*/

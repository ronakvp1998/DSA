package strivers.recursionbacktracking.hard.nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * # N-Queens Problem
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Place N Queens on an n x n chessboard such that no two Queens attack each other.
 * A Queen can attack vertically, horizontally, and diagonally.
 * The goal is to return all possible valid configurations of the chessboard
 * where n Queens are placed safely. 'Q' represents a queen and '.' represents an empty space.
 *
 * **Constraints:**
 * - 1 <= n <= 9 (Standard limits for returning all full string board configurations)
 *
 * **Examples:**
 * Example 1:
 * Input: n = 4
 * Output:
 * [
 *  [".Q..",  // Solution 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // Solution 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 *
 * Example 2:
 * Input: n = 1
 * Output: [["Q"]]
 *
 * ---
 *
 * ## Conceptual Visualization (Diagonal Mapping)
 * When placing queens column by column (left to right), we only need to check the left side
 * of the current column. Instead of manually traversing the grid to check for attacking queens,
 * we can map the rows and diagonals to 1D arrays:
 *
 * - **Left Row:** Queen at (r, c) occupies `leftRow[r]`.
 * - **Lower Diagonal (\):** Queen at (r, c) occupies `lowerDiagonal[r + c]`.
 *   (The sum of row and col is constant on these diagonals).
 * - **Upper Diagonal (/):** Queen at (r, c) occupies `upperDiagonal[(n - 1) + (c - r)]`.
 *   (The difference is constant. We add (n - 1) to prevent negative array indices).
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Backtracking with Array Hashing for O(1) safety checks.
 * * **Phase 2: Brute Force Approach** - Standard Backtracking with O(N) grid traversal for safety.
 * * **Phase 3: Alternative Approach** - Bitmask Optimization using integers instead of boolean/int arrays.
 */
public class NQueensWithAttach {

    /**
     * ## Phase 1: Optimal Approach - Backtracking with Array Hashing
     *
     * **Detailed Intuition:**
     * By using three arrays (`leftRow`, `lowerDiagonal`, `upperDiagonal`), we can track which rows
     * and diagonals are currently under attack. When we attempt to place a queen at `(row, col)`,
     * we can check its safety in O(1) time. If safe, we mark the arrays, recurse to the next column,
     * and unmark them (backtrack) when returning.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(N!) - We place 1 queen per column. The first column has N choices,
     *   the second has at most N-2, etc. The safety check is O(1).
     * - **Space Complexity:** O(N) auxiliary stack space for recursion + O(N) heap space for the
     *   hash arrays. The result storage takes O(N^2 * Valid_Solutions) heap space.
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

                // Recurse to next column
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
     * The most straightforward way to verify safety is to physically walk the 2D grid backwards
     * from the current cell. We check the left row, upper-left diagonal, and lower-left diagonal
     * step-by-step.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(N! * N). The safety check adds an O(N) traversal inside the loop
     *   for every node in the recursion tree.
     * - **Space Complexity:** O(N) auxiliary stack space for recursion. No extra arrays are used.
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
     * We can replace the arrays from Phase 1 with integers acting as bitmasks. Since n <= 9,
     * a 32-bit integer is more than enough to track diagonals. Bitwise operations are significantly
     * faster than array lookups at the CPU level.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** O(N!) with drastically reduced constant factors due to bitwise operations.
     * - **Space Complexity:** O(N) auxiliary stack space. Zero heap space used for state tracking.
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
        for (char[] row : board) {
            res.add(new String(row));
        }
        return res;
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        NQueensWithAttach solver = new NQueensWithAttach();

        // Edge case (N=1), small case (N=4), standard case (N=8)
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

            boolean match = (optimalRes.size() == bruteRes.size()) && (bruteRes.size() == bitmaskRes.size());
            System.out.println("Result Match Validation: " + (match ? "PASS" : "FAIL"));

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
//import java.util.List;
//
//// Problem Statement:
//// ------------------
//// Place N Queens on an n x n chessboard such that no two Queens attack each other.
//// A Queen can attack vertically, horizontally, and diagonally.
//// The goal is to return all possible valid configurations of the chessboard
//// where n Queens are placed safely.
//
//public class NQueensWithAttach {
//
//    // Main driver method (example execution)
//    public static void main(String[] args) {
//        NQueensWithAttach solver = new NQueensWithAttach();
//        List<List<String>> solutions = solver.solveNQueens(4);
//
//        // Print all solutions
//        for (List<String> board : solutions) {
//            System.out.println("----------- Chess Board -------------");
//            for (String row : board) {
//                System.out.println(row);
//            }
//        }
//    }
//
//    // Function to solve N-Queens and return all valid configurations
//    public List<List<String>> solveNQueens(int n) {
//        List<List<String>> result = new ArrayList<>();
//
//        // Step 1: Create an empty chessboard filled with 'X'
//        char[][] board = new char[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                board[i][j] = '.';
//            }
//        }
//
//        // Step 2: Start recursive backtracking from row 0
//        nQueens(board, 0, result);
//        return result;
//    }
//
//    // Recursive function to place Queens row by row
//    private void nQueens(char[][] board, int row, List<List<String>> result) {
//        // Base case: If all rows are filled, convert board into list<String> and add to result
//        if (row == board.length) {
//            result.add(construct(board));
//            return;
//        }
//
//        // Try placing a Queen in every column of the current row
//        for (int col = 0; col < board.length; col++) {
//            if (isSafe(board, row, col)) {
//                board[row][col] = 'Q';              // Place Queen
//                nQueens(board, row + 1, result);    // Recurse to next row
//                board[row][col] = '.';              // Backtrack (remove Queen)
//            }
//        }
//    }
//
//    // Utility function to check if a Queen can be placed safely
//    private boolean isSafe(char[][] board, int row, int col) {
//        // 1. Check vertically upward in the same column
//        for (int i = row - 1; i >= 0; i--) {
//            if (board[i][col] == 'Q') {
//                return false;
//            }
//        }
//
//        // 2. Check left diagonal upward
//        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
//            if (board[i][j] == 'Q') {
//                return false;
//            }
//        }
//
//        // 3. Check right diagonal upward
//        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
//            if (board[i][j] == 'Q') {
//                return false;
//            }
//        }
//
//        return true; // Safe position
//    }
//
//    // Convert char[][] board into List<String>
//    private List<String> construct(char[][] board) {
//        List<String> configuration = new ArrayList<>();
//        for (int i = 0; i < board.length; i++) {
//            configuration.add(new String(board[i]));
//        }
//        return configuration;
//    }
//}
//
///*
//Time Complexity:
//----------------
//- For each row, we try placing a Queen in n columns.
//- For each placement, isSafe() takes O(n) time (to check column + 2 diagonals).
//- Worst-case recursive calls = O(n^n).
//- So, Time Complexity = O(n^n * n) = O(n^(n+1)).
//- Effective complexity is closer to O(N!) since many branches are pruned early.
//
//Space Complexity:
//-----------------
//1. Chessboard storage = O(n^2).
//2. Recursion depth = O(n).
//3. Result storage = O(NumberOfSolutions * n^2) (to store all valid boards).
//=> Overall Space Complexity = O(n^2 + recursion + output).
//*/

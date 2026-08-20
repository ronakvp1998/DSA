package strivers.recursionbacktracking.hard;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * # 37. Sudoku Solver
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * A sudoku solution must satisfy all of the following rules:
 * 1. Each of the digits 1-9 must occur exactly once in each row.
 * 2. Each of the digits 1-9 must occur exactly once in each column.
 * 3. Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 *
 * The '.' character indicates empty cells.
 *
 * **Constraints:**
 * - board.length == 9
 * - board[i].length == 9
 * - board[i][j] is a digit or '.'.
 * - It is guaranteed that the input board has only one solution.
 *
 * **Examples:**
 * Example 1:
 * Input: board =
 * [["5","3",".",".","7",".",".",".","."],
 *  ["6",".",".","1","9","5",".",".","."],
 *  [".","9","8",".",".",".",".","6","."],
 *  ["8",".",".",".","6",".",".",".","3"],
 *  ["4",".",".","8",".","3",".",".","1"],
 *  ["7",".",".",".","2",".",".",".","6"],
 *  [".","6",".",".",".",".","2","8","."],
 *  [".",".",".","4","1","9",".",".","5"],
 *  [".",".",".",".","8",".",".","7","9"]]
 * Output:
 * [["5","3","4","6","7","8","9","1","2"],
 *  ["6","7","2","1","9","5","3","4","8"],
 *  ["1","9","8","3","4","2","5","6","7"],
 *  ["8","5","9","7","6","1","4","2","3"],
 *  ["4","2","6","8","5","3","7","9","1"],
 *  ["7","1","3","9","2","4","8","5","6"],
 *  ["9","6","1","5","3","7","2","8","4"],
 *  ["2","8","7","4","1","9","6","3","5"],
 *  ["3","4","5","2","8","6","1","7","9"]]
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Backtracking with Early Pruning (Standard approach).
 * * **Phase 2: Brute Force Approach** - Generate all combinations and validate at the end.
 * * **Phase 3: Alternative Approach** - Bitmask Optimization for $O(1)$ validation lookups.
 */
public class SudokuSolver {

    /**
     * ## Phase 1: Optimal Approach - Backtracking with Early Pruning
     *
     * **Detailed Intuition:**
     * Instead of generating all possible boards, we use backtracking with early pruning.
     * We iterate through the board to find an empty cell. Once found, we try placing digits '1'
     * through '9'. Before placing a digit, we check if it is valid in the current row, column,
     * and 3x3 sub-box. If it is valid, we place it and recursively attempt to solve the rest
     * of the board. If a placement leads to a dead end, we backtrack (reset the cell to '.')
     * and try the next digit.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(9^K)$ where $K$ is the number of empty cells. However, due to
     *   aggressive early pruning (validity checks), the average time is drastically lower.
     * - **Space Complexity:** $O(K)$ auxiliary stack space for the recursion tree depth. $O(1)$
     *   heap space since we modify the board in-place.
     */
    public void solveSudokuOptimal(char[][] board) {
        solveOptimal(board);
    }

    private boolean solveOptimal(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; // Place digit

                            if (solveOptimal(board)) {
                                return true; // Found the solution
                            }

                            board[i][j] = '.'; // Backtrack
                        }
                    }
                    return false; // No valid digit found, trigger backtracking
                }
            }
        }
        return true; // All cells filled
    }

    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // Check row
            if (board[row][i] == c) return false;
            // Check column
            if (board[i][col] == c) return false;
            // Check 3x3 block
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false;
        }
        return true;
    }

    /**
     * ## Phase 2: Brute Force Approach - Generate and Validate
     *
     * **Detailed Intuition:**
     * This is the true "Think it" brute force. We find an empty cell, try 1-9 WITHOUT checking
     * if it's currently valid, and recurse. We only validate the board when ALL empty cells
     * are filled.
     * NOTE: This is practically unusable for a standard Sudoku board due to the sheer number
     * of combinations ($9^K$). It will cause a Time Limit Exceeded (TLE). It is included here
     * strictly for theoretical progression.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(9^K \times 81)$ where $K$ is the number of empty cells. We generate
     *   all permutations and validate them at the leaf nodes.
     * - **Space Complexity:** $O(K)$ stack space for recursion.
     */
    public void solveSudokuBruteForce(char[][] board) {
        solveBruteForce(board);
    }

    private boolean solveBruteForce(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        board[i][j] = c;
                        if (solveBruteForce(board)) {
                            return true;
                        }
                        board[i][j] = '.';
                    }
                    return false;
                }
            }
        }
        // Validate entirely ONLY at the end
        return isBoardValid(board);
    }

    private boolean isBoardValid(char[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                board[r][c] = '.';
                if (!isValid(board, r, c, val)) {
                    board[r][c] = val;
                    return false;
                }
                board[r][c] = val;
            }
        }
        return true;
    }

    /**
     * ## Phase 3: Alternative Approach - Bitmask Optimized Backtracking
     *
     * **Detailed Intuition:**
     * In the optimal approach, checking validity takes $O(9)$ time per attempt. We can reduce this
     * to $O(1)$ by using arrays of integers as bitmasks to keep track of which numbers are used
     * in each row, column, and 3x3 box. The $i$-th bit of `rows[r]` is 1 if digit `i` is used in row `r`.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(9^K)$ worst case, but the constant factor is significantly smaller
     *   than Phase 1 because validation is an $O(1)$ bitwise AND operation.
     * - **Space Complexity:** $O(K)$ stack space + $O(1)$ auxiliary state space (3 integer arrays of size 9).
     */
    public void solveSudokuBitmask(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];

        // Initialize bitmasks with existing board state
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') {
                    int val = board[r][c] - '1';
                    int bit = 1 << val;
                    int boxIdx = (r / 3) * 3 + (c / 3);
                    rows[r] |= bit;
                    cols[c] |= bit;
                    boxes[boxIdx] |= bit;
                }
            }
        }
        solveBitmask(board, 0, 0, rows, cols, boxes);
    }

    private boolean solveBitmask(char[][] board, int r, int c, int[] rows, int[] cols, int[] boxes) {
        if (r == 9) return true; // Reached the end of the board
        if (c == 9) return solveBitmask(board, r + 1, 0, rows, cols, boxes); // Move to next row
        if (board[r][c] != '.') return solveBitmask(board, r, c + 1, rows, cols, boxes); // Skip filled

        int boxIdx = (r / 3) * 3 + (c / 3);
        for (int val = 0; val < 9; val++) {
            int bit = 1 << val;
            // Check if the digit is used in the current row, column, or box
            if ((rows[r] & bit) == 0 && (cols[c] & bit) == 0 && (boxes[boxIdx] & bit) == 0) {

                // Place digit and set masks
                board[r][c] = (char) ('1' + val);
                rows[r] |= bit;
                cols[c] |= bit;
                boxes[boxIdx] |= bit;

                if (solveBitmask(board, r, c + 1, rows, cols, boxes)) {
                    return true;
                }

                // Backtrack: Reset digit and unset masks
                board[r][c] = '.';
                rows[r] ^= bit;
                cols[c] ^= bit;
                boxes[boxIdx] ^= bit;
            }
        }
        return false;
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();

        char[][] originalBoard = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };

        System.out.println("--- Running Sudoku Solver Tests ---");

        // Test Phase 1: Optimal
        char[][] boardOptimal = deepCopyBoard(originalBoard);
        long startOpt = System.nanoTime();
        solver.solveSudokuOptimal(boardOptimal);
        long endOpt = System.nanoTime();
        System.out.println("Optimal Approach Solved in: " + (endOpt - startOpt) / 1_000_000.0 + " ms");

        // Test Phase 3: Bitmask
        char[][] boardBitmask = deepCopyBoard(originalBoard);
        long startBit = System.nanoTime();
        solver.solveSudokuBitmask(boardBitmask);
        long endBit = System.nanoTime();
        System.out.println("Bitmask Approach Solved in: " + (endBit - startBit) / 1_000_000.0 + " ms");

        // Validate results match
        boolean match = Arrays.deepEquals(boardOptimal, boardBitmask);
        System.out.println("Results Match (Optimal & Bitmask): " + (match ? "PASS" : "FAIL"));

        // Note: We skip Phase 2 (Brute Force) in the live test suite because generating
        // 9^K states for a standard board will hang the execution indefinitely.
        System.out.println("\n[Note: Phase 2 (Brute Force) execution skipped to prevent Time Limit Exceeded]");

        System.out.println("\nSolved Board Output:");
        printBoard(boardOptimal);
    }

    // Helper method for deep copying a 2D char array
    private static char[][] deepCopyBoard(char[][] matrix) {
        return Stream.of(matrix)
                .map(char[]::clone)
                .toArray(char[][]::new);
    }

    // Helper method to neatly print the board
    private static void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (i > 0 && i % 3 == 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < 9; j++) {
                if (j > 0 && j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}


//
///*
//Problem Statement:
//------------------
//Given a 9x9 incomplete Sudoku board, solve it such that it becomes a valid Sudoku.
//
//A valid Sudoku must satisfy:
//1. Each row contains digits 1–9 exactly once.
//2. Each column contains digits 1–9 exactly once.
//3. Each 3x3 subgrid contains digits 1–9 exactly once.
//
//Input board may contain '.' which represents empty cells.
//The goal is to fill those cells with valid digits to complete the Sudoku.
//*/
//
//public class SodukoSolver {
//
//    // Function to solve Sudoku using backtracking
//    private static boolean solveSudoku1(char[][] board) {
//        // Step 1: Traverse the entire board to find an empty cell ('.')
//        for (int i = 0; i < 9; i++) {             // iterate over rows
//            for (int j = 0; j < 9; j++) {         // iterate over columns
//                if (board[i][j] == '.') {         // found an empty cell
//
//                    // Step 2: Try placing digits '1' to '9'
//                    for (char c = '1'; c <= '9'; c++) {
//                        // Step 3: Check if placing 'c' here is valid
//                        if (isValid(board, i, j, c)) {
//                            board[i][j] = c; // Place the digit
//
//                            // Step 4: Recurse to solve the rest of the board
//                            if (solveSudoku1(board))
//                                return true; // If solved, return true
//
//                                // Step 5: Backtrack (reset cell to empty) if invalid later
//                            else
//                                board[i][j] = '.';
//                        }
//                    }
//
//                    // If no valid number could be placed in this cell → backtrack
//                    return false;
//                }
//            }
//        }
//        // If no empty cell found, Sudoku is solved
//        return true;
//    }
//
//    // Helper function to check if placing 'c' at (row, col) is valid
//    private static boolean isValid(char[][] board, int row, int col, char c) {
//        for (int i = 0; i < 9; i++) {
//            // Check entire column
//            if (board[i][col] == c)
//                return false;
//
//            // Check entire row
//            if (board[row][i] == c)
//                return false;
//
//            // Check 3x3 subgrid
//            // Formula to iterate inside a 3x3 box:
//            // Row start = 3 * (row / 3), Col start = 3 * (col / 3)
//            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c)
//                return false;
//        }
//        return true; // Safe to place
//    }
//
//    // Driver function to test Sudoku Solver
//    public static void main(String[] args) {
//        char[][] board = {
//                {'9', '5', '7', '.', '1', '3', '.', '8', '4'},
//                {'4', '8', '3', '.', '5', '7', '1', '.', '6'},
//                {'.', '1', '2', '.', '4', '9', '5', '3', '7'},
//                {'1', '7', '.', '3', '.', '4', '9', '.', '2'},
//                {'5', '.', '4', '9', '7', '.', '3', '6', '.'},
//                {'3', '.', '9', '5', '.', '8', '7', '.', '1'},
//                {'8', '4', '5', '7', '9', '.', '6', '1', '3'},
//                {'.', '9', '1', '.', '3', '6', '.', '7', '5'},
//                {'7', '.', '6', '1', '8', '5', '4', '.', '9'}
//        };
//
//        // Solve the board
//        solveSudoku1(board);
//
//        // Print final solved board
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++)
//                System.out.print(board[i][j] + " ");
//            System.out.println();
//        }
//    }
//}
//
///*
//-------------------------------
//Code Logic Explanation:
//-------------------------------
//1. We scan the board cell by cell.
//2. When we encounter an empty cell ('.'), we try all possible digits '1' to '9'.
//3. For each digit, we check if placing it there is valid (row, col, subgrid check).
//4. If valid, place it and recursively solve the remaining board.
//5. If a dead end is reached, backtrack by resetting the cell to '.'.
//6. If all cells are filled successfully, the board is solved.
//
//-------------------------------
//Time Complexity:
//-------------------------------
//- In the worst case, each empty cell has 9 possibilities.
//- For 81 cells → O(9^81), which is extremely large (exponential).
//- However, backtracking + constraints pruning drastically reduces possibilities.
//- Practical runtime is fast for standard Sudoku puzzles.
//
//-------------------------------
//Space Complexity:
//-------------------------------
//1. Board storage: O(81) = O(1) (constant size).
//2. Recursion depth: At most 81 (all cells empty).
//3. No extra data structures used apart from recursion.
//=> Total Space Complexity = O(81) ≈ O(1).
//*/

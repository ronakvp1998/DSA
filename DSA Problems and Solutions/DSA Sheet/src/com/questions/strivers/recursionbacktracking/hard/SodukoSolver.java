package com.questions.strivers.recursionbacktracking.hard;

/*
Problem Statement:
------------------
Given a 9x9 incomplete Sudoku board, solve it such that it becomes a valid Sudoku.

A valid Sudoku must satisfy:
1. Each row contains digits 1–9 exactly once.
2. Each column contains digits 1–9 exactly once.
3. Each 3x3 subgrid contains digits 1–9 exactly once.

Input board may contain '.' which represents empty cells.
The goal is to fill those cells with valid digits to complete the Sudoku.
*/

public class SodukoSolver {

    // Function to solve Sudoku using backtracking
    public static boolean solveSudoku1(char[][] board) {
        // Step 1: Traverse the entire board to find an empty cell ('.')
        for (int i = 0; i < 9; i++) {             // iterate over rows
            for (int j = 0; j < 9; j++) {         // iterate over columns
                if (board[i][j] == '.') {         // found an empty cell

                    // Step 2: Try placing digits '1' to '9'
                    for (char c = '1'; c <= '9'; c++) {
                        // Step 3: Check if placing 'c' here is valid
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; // Place the digit

                            // Step 4: Recurse to solve the rest of the board
                            if (solveSudoku1(board))
                                return true; // If solved, return true

                                // Step 5: Backtrack (reset cell to empty) if invalid later
                            else
                                board[i][j] = '.';
                        }
                    }

                    // If no valid number could be placed in this cell → backtrack
                    return false;
                }
            }
        }
        // If no empty cell found, Sudoku is solved
        return true;
    }

    // Helper function to check if placing 'c' at (row, col) is valid
    public static boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // Check entire column
            if (board[i][col] == c)
                return false;

            // Check entire row
            if (board[row][i] == c)
                return false;

            // Check 3x3 subgrid
            // Formula to iterate inside a 3x3 box:
            // Row start = 3 * (row / 3), Col start = 3 * (col / 3)
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c)
                return false;
        }
        return true; // Safe to place
    }

    // Driver function to test Sudoku Solver
    public static void main(String[] args) {
        char[][] board = {
                {'9', '5', '7', '.', '1', '3', '.', '8', '4'},
                {'4', '8', '3', '.', '5', '7', '1', '.', '6'},
                {'.', '1', '2', '.', '4', '9', '5', '3', '7'},
                {'1', '7', '.', '3', '.', '4', '9', '.', '2'},
                {'5', '.', '4', '9', '7', '.', '3', '6', '.'},
                {'3', '.', '9', '5', '.', '8', '7', '.', '1'},
                {'8', '4', '5', '7', '9', '.', '6', '1', '3'},
                {'.', '9', '1', '.', '3', '6', '.', '7', '5'},
                {'7', '.', '6', '1', '8', '5', '4', '.', '9'}
        };

        // Solve the board
        solveSudoku1(board);

        // Print final solved board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }
}

/*
-------------------------------
Code Logic Explanation:
-------------------------------
1. We scan the board cell by cell.
2. When we encounter an empty cell ('.'), we try all possible digits '1' to '9'.
3. For each digit, we check if placing it there is valid (row, col, subgrid check).
4. If valid, place it and recursively solve the remaining board.
5. If a dead end is reached, backtrack by resetting the cell to '.'.
6. If all cells are filled successfully, the board is solved.

-------------------------------
Time Complexity:
-------------------------------
- In the worst case, each empty cell has 9 possibilities.
- For 81 cells → O(9^81), which is extremely large (exponential).
- However, backtracking + constraints pruning drastically reduces possibilities.
- Practical runtime is fast for standard Sudoku puzzles.

-------------------------------
Space Complexity:
-------------------------------
1. Board storage: O(81) = O(1) (constant size).
2. Recursion depth: At most 81 (all cells empty).
3. No extra data structures used apart from recursion.
=> Total Space Complexity = O(81) ≈ O(1).
*/

package com.questions.strivers.recursion.hard.nqueens;

import java.util.ArrayList;
import java.util.List;

// Problem Statement:
// ------------------
// Place N Queens on an n x n chessboard such that no two Queens attack each other.
// A Queen can attack vertically, horizontally, and diagonally.
// The goal is to return all possible valid configurations of the chessboard
// where n Queens are placed safely.

public class NQueensWithAttach {

    // Main driver method (example execution)
    public static void main(String[] args) {
        NQueensWithAttach solver = new NQueensWithAttach();
        List<List<String>> solutions = solver.solveNQueens(4);

        // Print all solutions
        for (List<String> board : solutions) {
            System.out.println("----------- Chess Board -------------");
            for (String row : board) {
                System.out.println(row);
            }
        }
    }

    // Function to solve N-Queens and return all valid configurations
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();

        // Step 1: Create an empty chessboard filled with 'X'
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        // Step 2: Start recursive backtracking from row 0
        nQueens(board, 0, result);
        return result;
    }

    // Recursive function to place Queens row by row
    private void nQueens(char[][] board, int row, List<List<String>> result) {
        // Base case: If all rows are filled, convert board into list<String> and add to result
        if (row == board.length) {
            result.add(construct(board));
            return;
        }

        // Try placing a Queen in every column of the current row
        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';              // Place Queen
                nQueens(board, row + 1, result);    // Recurse to next row
                board[row][col] = '.';              // Backtrack (remove Queen)
            }
        }
    }

    // Utility function to check if a Queen can be placed safely
    private boolean isSafe(char[][] board, int row, int col) {
        // 1. Check vertically upward in the same column
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        // 2. Check left diagonal upward
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        // 3. Check right diagonal upward
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        return true; // Safe position
    }

    // Convert char[][] board into List<String>
    private List<String> construct(char[][] board) {
        List<String> configuration = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            configuration.add(new String(board[i]));
        }
        return configuration;
    }
}

/*
Time Complexity:
----------------
- For each row, we try placing a Queen in n columns.
- For each placement, isSafe() takes O(n) time (to check column + 2 diagonals).
- Worst-case recursive calls = O(n^n).
- So, Time Complexity = O(n^n * n) = O(n^(n+1)).
- Effective complexity is closer to O(N!) since many branches are pruned early.

Space Complexity:
-----------------
1. Chessboard storage = O(n^2).
2. Recursion depth = O(n).
3. Result storage = O(NumberOfSolutions * n^2) (to store all valid boards).
=> Overall Space Complexity = O(n^2 + recursion + output).
*/

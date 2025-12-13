package com.questions.strivers.recursionbacktracking.hard.nqueens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NQueens {

    /*
     Problem Statement:
        Place N queens on an N x N chessboard such that no two queens attack each other.
        Queens can attack in the same row, column, and diagonals.
        Return all possible valid board configurations.

     Example:
        Input: N = 4
        Output:
        [
          [".Q..",
           "...Q",
           "Q...",
           "..Q."],

          ["..Q.",
           "Q...",
           "...Q",
           ".Q.."]
        ]
    */

    // Main function to solve the N-Queens problem
    private static List<List<String>> solveNQueens(int n) {
        // Create an empty board with '.'
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = '.';

        List<List<String>> res = new ArrayList<>();
        // Start DFS (backtracking) from column 0
        dfs(0, board, res);
        return res;
    }

    // Recursive DFS function to place queens column by column
    static void dfs(int col, char[][] board, List<List<String>> res) {
        // Base case: if we placed queens in all columns, add configuration to result
        if (col == board.length) {
            res.add(construct(board));
            return;
        }

        // Try placing queen in each row of the current column
        for (int row = 0; row < board.length; row++) {
            if (validate(board, row, col)) { // Check if safe
                board[row][col] = 'Q';       // Place queen
                dfs(col + 1, board, res);    // Move to next column
                board[row][col] = '.';       // Backtrack (remove queen)
            }
        }
    }

    // Convert board state (2D char array) into List<String>
    static List<String> construct(char[][] board) {
        List<String> res = new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            String s = new String(board[i]); // Convert char[] row -> String
            res.add(s);
        }
        return res;
    }

    // Validate if a queen can be placed at (row, col)
    static boolean validate(char[][] board, int row, int col) {
        int duprow = row;
        int dupcol = col;

        // Check upper-left diagonal
        while (row >= 0 && col >= 0) {
            if (board[row][col] == 'Q') return false;
            row--;
            col--;
        }

        // Reset
        row = duprow;
        col = dupcol;

        // Check left side (row-wise)
        while (col >= 0) {
            if (board[row][col] == 'Q') return false;
            col--;
        }

        // Reset
        row = duprow;
        col = dupcol;

        // Check lower-left diagonal
        while (col >= 0 && row < board.length) {
            if (board[row][col] == 'Q') return false;
            col--;
            row++;
        }
        return true; // Safe position
    }

    // Driver function to test the solution
    public static void main(String args[]) {
        int N = 4;
        List<List<String>> queen = solveNQueens(N);
        int i = 1;
        for (List<String> it: queen) {
            System.out.println("Arrangement " + i);
            for (String s: it) {
                System.out.println(s);
            }
            System.out.println();
            i += 1;
        }
    }
}

/*
------------------------------------------------
⏱ Time Complexity:
- At each column, we try N rows → O(N)
- For each placement, validation takes O(N) (checking diagonals and row).
- In the worst case, we explore N! configurations (placing N queens in N columns).
=> Overall Time Complexity ≈ O(N! * N)

💾 Space Complexity:
- Board size: O(N^2)
- Recursion depth: O(N) (since we place one queen per column)
- Result storage depends on the number of solutions.

=> Overall Space Complexity ≈ O(N^2)
------------------------------------------------
*/

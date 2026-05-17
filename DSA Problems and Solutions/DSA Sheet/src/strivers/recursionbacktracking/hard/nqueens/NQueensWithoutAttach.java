package com.questions.strivers.recursionbacktracking.hard.nqueens;

// Problem Statement:
// Place N Queens on an n x n chessboard such that each row has exactly one Queen,
// but WITHOUT checking the attack conditions (i.e., multiple Queens may attack each other).
// This code simply generates all possible placements of n Queens (one per row).

public class NQueensWithoutAttach {
    public static void main(String[] args) {
        int n = 2; // size of the chessboard (n x n)

        // Step 1: Create an empty chessboard and initialize with 'X' (empty cell)
        char board[][] = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = 'X';
            }
        }

        // Step 2: Start placing queens row by row
        nQueens(board, 0);
    }

    // Recursive function to place queens row by row
    private static void nQueens(char board[][], int row) {
        // Base case: If all rows are filled, print the board configuration
        if (row == board.length) {
            printBoard(board);
            return;
        }

        // Try placing a Queen in every column of the current row
        for (int j = 0; j < board.length; j++) {
            board[row][j] = 'Q';      // Place Queen at position (row, j)
            nQueens(board, row + 1); // Recurse to next row
            board[row][j] = 'X';      // Backtrack (remove Queen for next placement)
        }
    }

    // Utility function to print the chessboard
    private static void printBoard(char board[][]) {
        System.out.println("----------- Chess Board -------------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*
Time Complexity:
---------------
- At each row, we try placing a queen in all n columns.
- There are n rows, and for each row we have n choices → total possibilities = n^n.
- Printing each board takes O(n^2).
- Overall Time Complexity = O(n^n * n^2).

Space Complexity:
-----------------
- Chessboard storage requires O(n^2).
- Recursion depth = O(n) (one recursive call per row).
- Total Space Complexity = O(n^2).
*/

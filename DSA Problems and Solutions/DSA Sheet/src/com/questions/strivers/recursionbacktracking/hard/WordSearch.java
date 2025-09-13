package com.questions.strivers.recursionbacktracking.hard;

public class WordSearch {

    /*
     * Problem: Given an m x n grid of characters (board) and a word,
     *          return true if the word exists in the grid.
     *
     * Approach: Backtracking (DFS)
     * -------------------------------------------------
     * - Iterate over each cell of the board.
     * - If the first character matches, start DFS from there.
     * - DFS tries moving in 4 directions (up, down, left, right).
     * - Mark the current cell as visited (temporarily change value).
     * - If at any point all characters of the word are matched → return true.
     * - Backtrack: restore the original character and continue exploring.
     * - If no path matches, return false.
     */

    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        // Try to start search from every cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Start DFS if first character matches
                if (board[i][j] == word.charAt(0) && dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false; // word not found
    }

    /*
     * DFS function to check if word can be formed starting from board[row][col]
     * @param board -> character grid
     * @param word  -> target word
     * @param row   -> current row index
     * @param col   -> current column index
     * @param index -> current index in the word
     */
    private boolean dfs(char[][] board, String word, int row, int col, int index) {
        // Base case: entire word is matched
        if (index == word.length()) {
            return true;
        }

        // Check boundaries and character match
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length
                || board[row][col] != word.charAt(index)) {
            return false;
        }

        // Mark this cell as visited (using temporary marker)
        char temp = board[row][col];
        board[row][col] = '#'; // special marker to denote visited

        // Explore all 4 directions
        boolean found = dfs(board, word, row + 1, col, index + 1) || // down
                dfs(board, word, row - 1, col, index + 1) || // up
                dfs(board, word, row, col + 1, index + 1) || // right
                dfs(board, word, row, col - 1, index + 1);   // left

        // Backtrack: restore the original character
        board[row][col] = temp;

        return found;
    }

    // Driver code
    public static void main(String[] args) {
        WordSearch solution = new WordSearch();

        char[][] board1 = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };

        System.out.println(solution.exist(board1, "ABCCED")); // true
        System.out.println(solution.exist(board1, "SEE"));    // true
        System.out.println(solution.exist(board1, "ABCB"));   // false
    }
}

/*
Time Complexity:

For each cell (m × n), we may start a DFS.

In worst case, DFS explores 4 directions at each step, up to word length L.

O(m × n × 4^L) worst case.

But since m, n ≤ 6 and L ≤ 15, it’s manageable.

Space Complexity:

DFS recursion depth = L → O(L).

We use in-place marking, so no extra visited array.

O(L) auxiliary space.
 */

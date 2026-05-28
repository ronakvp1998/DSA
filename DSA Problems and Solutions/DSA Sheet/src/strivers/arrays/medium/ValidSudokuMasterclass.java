package strivers.arrays.medium;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * * Problem: 36. Valid Sudoku (Medium)
 * * Formal Problem Statement:
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
 * validated according to the following rules:
 * 1. Each row must contain the digits 1-9 without repetition.
 * 2. Each column must contain the digits 1-9 without repetition.
 * 3. Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9
 * without repetition.
 * * Note:
 * - A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * - Only the filled cells need to be validated according to the mentioned rules.
 * * * Constraints:
 * - board.length == 9
 * - board[i].length == 9
 * - board[i][j] is a digit 1-9 or '.'.
 * * * Examples:
 * Example 1:
 * Input: board =
 * [["5","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: true
 * * Example 2:
 * Input: board = Same as above but top-left is "8".
 * Output: false
 * Explanation: There are two 8's in the top left 3x3 sub-box, it is invalid.
 * * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach (Single-Pass 2D Array Hashing) - Fastest execution.
 * Phase 2: Brute Force Approach (Three Passes) - The "Think it" stage.
 * Phase 3: Alternative Approach (Single-Pass Bitmasking) - Space optimized & elegant.
 */

public class ValidSudokuMasterclass {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Single-Pass 2D Array Hashing)
     * ========================================================================
     * * Detailed Intuition:
     * Instead of iterating the board multiple times, we can traverse every cell
     * exactly once. We maintain three sets of "memory" to track what we've seen:
     * one for rows, one for columns, and one for the 3x3 sub-boxes.
     * Since the values are strictly 1-9, we can use a 2D boolean array where
     * `rows[i][val]` represents "has the value 'val' been seen in row 'i'?".
     * * The trickiest part is mapping a cell (r, c) to its 3x3 sub-box index.
     * We can mathematically map any cell to one of the 9 sub-boxes (0 to 8) using:
     * boxIndex = (r / 3) * 3 + (c / 3)
     * * * Complexity Analysis:
     * - Time Complexity: O(N^2) where N = 9. Since N is fixed, it mathematically
     * reduces to O(1). We visit all 81 cells exactly once.
     * - Space Complexity: O(N^2) Heap Space -> O(1). We allocate three 9x9
     * boolean arrays, which takes a tiny, constant amount of memory. Auxiliary
     * stack space is O(1).
     */
    public boolean isValidSudokuOptimal(char[][] board) {
        // Arrays to keep track of seen numbers in rows, cols, and boxes.
        // Dimension is [9][9]. First index is the row/col/box number (0-8).
        // Second index is the number itself (0-8 representing 1-9).
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') continue; // Skip empty cells

                // Convert char '1'-'9' to integer index 0-8
                int val = board[r][c] - '1';

                // Calculate which of the 9 boxes this cell belongs to
                int boxIndex = (r / 3) * 3 + (c / 3);

                // If the number was already seen in the current row, col, or box, it's invalid
                if (rows[r][val] || cols[c][val] || boxes[boxIndex][val]) {
                    return false;
                }

                // Mark the number as seen
                rows[r][val] = true;
                cols[c][val] = true;
                boxes[boxIndex][val] = true;
            }
        }
        return true;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Three Distinct Passes)
     * ========================================================================
     * * Detailed Intuition:
     * The most straightforward human-like approach. We explicitly perform three
     * separate checks:
     * 1. Check all 9 rows independently.
     * 2. Check all 9 columns independently.
     * 3. Check all 9 sub-boxes independently.
     * For each check, we use a simple boolean array of size 9 to track duplicates.
     * * * Complexity Analysis:
     * - Time Complexity: O(3 * N^2) -> O(1). We iterate over the 81 cells three
     * separate times. Still constant time, but a larger constant factor than Phase 1.
     * - Space Complexity: O(N) Heap Space -> O(1). We only allocate a single
     * boolean array of size 9 for each check, reusing it.
     */
    public boolean isValidSudokuBruteForce(char[][] board) {
        // 1. Check Rows
        for (int r = 0; r < 9; r++) {
            boolean[] seen = new boolean[9];
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') {
                    int val = board[r][c] - '1';
                    if (seen[val]) return false;
                    seen[val] = true;
                }
            }
        }

        // 2. Check Columns
        for (int c = 0; c < 9; c++) {
            boolean[] seen = new boolean[9];
            for (int r = 0; r < 9; r++) {
                if (board[r][c] != '.') {
                    int val = board[r][c] - '1';
                    if (seen[val]) return false;
                    seen[val] = true;
                }
            }
        }

        // 3. Check 3x3 Boxes
        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {
                boolean[] seen = new boolean[9];
                // Traverse the 3x3 grid inside the box
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int actualRow = boxRow + r;
                        int actualCol = boxCol + c;
                        if (board[actualRow][actualCol] != '.') {
                            int val = board[actualRow][actualCol] - '1';
                            if (seen[val]) return false;
                            seen[val] = true;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Single-Pass Bitmasking)
     * ========================================================================
     * * Detailed Intuition:
     * We can highly optimize the space of Phase 1. Instead of using a 2D boolean
     * array (which takes up memory objects), we can use a 1D integer array.
     * Since an integer in Java is 32 bits, and we only need to track 9 digits,
     * we can map the occurrence of a digit to a specific bit in an integer.
     * * Example: If we see a '3', we flip the 3rd bit to 1: (1 << 3).
     * To check if '3' is already seen, we use the bitwise AND operator `&`.
     * If `(memory & (1 << 3))` is greater than 0, it means it's a duplicate.
     * * * Complexity Analysis:
     * - Time Complexity: O(N^2) -> O(1). We traverse 81 cells once. Bitwise
     * operations are incredibly fast at the CPU level.
     * - Space Complexity: O(N) Heap Space -> O(1). We only allocate three
     * integer arrays of size 9. This is the most memory-efficient approach.
     */
    public boolean isValidSudokuBitmask(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') continue;

                int val = board[r][c] - '1';
                int bitMask = 1 << val; // Create a bitmask for the current number
                int boxIndex = (r / 3) * 3 + (c / 3);

                // Bitwise AND checks if the bit is already set (i.e., we saw this number)
                if ((rows[r] & bitMask) != 0 ||
                        (cols[c] & bitMask) != 0 ||
                        (boxes[boxIndex] & bitMask) != 0) {
                    return false;
                }

                // Bitwise OR sets the bit to 1 (marks it as seen)
                rows[r] |= bitMask;
                cols[c] |= bitMask;
                boxes[boxIndex] |= bitMask;
            }
        }
        return true;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        ValidSudokuMasterclass solution = new ValidSudokuMasterclass();

        char[][] board1 = {
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

        char[][] board2 = {
                {'8','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };

        System.out.println("==================================================");
        System.out.println("Test Case 1: Valid Board");
        System.out.println("Phase 1 (Optimal)    : " + solution.isValidSudokuOptimal(board1));
        System.out.println("Phase 2 (Brute Force): " + solution.isValidSudokuBruteForce(board1));
        System.out.println("Phase 3 (Bitmask)    : " + solution.isValidSudokuBitmask(board1));

        System.out.println("\n==================================================");
        System.out.println("Test Case 2: Invalid Board (Duplicate '8' in top-left box)");
        System.out.println("Phase 1 (Optimal)    : " + solution.isValidSudokuOptimal(board2));
        System.out.println("Phase 2 (Brute Force): " + solution.isValidSudokuBruteForce(board2));
        System.out.println("Phase 3 (Bitmask)    : " + solution.isValidSudokuBitmask(board2));
        System.out.println("==================================================");
    }
}
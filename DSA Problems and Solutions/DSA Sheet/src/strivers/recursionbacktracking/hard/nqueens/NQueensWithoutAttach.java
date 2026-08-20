package strivers.recursionbacktracking.hard.nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * # All N-Queens Placements (Unconstrained)
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Place N Queens on an n x n chessboard such that each row has exactly one Queen,
 * but WITHOUT checking the attack conditions (i.e., multiple Queens may attack each other).
 * This code simply generates all possible placements of n Queens (one per row).
 *
 * 'Q' represents a queen and '.' represents an empty space.
 *
 * **Constraints:**
 * - 1 <= n <= 5 (Keeping it small because the number of configurations grows exponentially as $N^N$)
 *
 * **Examples:**
 * Example 1:
 * Input: n = 2
 * Output:
 * [
 *  ["Q.", "Q."],  // Both queens in column 0
 *  ["Q.", ".Q"],  // Row 0 col 0, Row 1 col 1
 *  [".Q", "Q."],  // Row 0 col 1, Row 1 col 0
 *  [".Q", ".Q"]   // Both queens in column 1
 * ]
 * Explanation: 2 rows, 2 choices per row = 2^2 = 4 configurations.
 *
 * Example 2:
 * Input: n = 1
 * Output: [["Q"]]
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Optimal Approach** - Backtracking with shared mutable state (char matrix).
 * * **Phase 2: Brute Force Approach** - Backtracking with immutable string generation per node.
 * * **Phase 3: Alternative Approach** - Iterative Base-N Counting (Combinatorics simulation).
 */
public class NQueensWithoutAttach {

    /**
     * ## Phase 1: Optimal Approach - Backtracking with Shared Mutable State
     *
     * **Detailed Intuition:**
     * Since we do not need to check for valid diagonal/column attacks, the problem simplifies
     * to picking exactly 1 column out of $N$ for every single row. This is a classic Permutation/
     * Combination generation. We use a shared `char[][]` initialized to all `.` to avoid creating
     * garbage objects. We place a 'Q', recurse to the next row, and backtrack by replacing it with a '.'.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \cdot N^N)$. There are exactly $N^N$ leaf nodes. At each leaf,
     *   we spend $O(N^2)$ to build the board strings, but overall time is bounded by the leaves.
     * - **Space Complexity:** $O(N)$ auxiliary stack space for the recursion depth, plus $O(N^2)$
     *   for the temporary `char[][]`. Result storage takes $O(N^2 \cdot N^N)$ heap space.
     */
    public List<List<String>> generateOptimal(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        backtrackOptimal(0, board, result, n);
        return result;
    }

    private void backtrackOptimal(int row, char[][] board, List<List<String>> result, int n) {
        // Base case: All rows have exactly one Queen placed
        if (row == n) {
            result.add(constructBoard(board));
            return;
        }

        // For the current row, we have N independent choices
        for (int col = 0; col < n; col++) {
            board[row][col] = 'Q'; // DO
            backtrackOptimal(row + 1, board, result, n); // RECURSE
            board[row][col] = '.'; // UNDO
        }
    }

    // Helper method to convert char[][] to List<String>
    private List<String> constructBoard(char[][] board) {
        List<String> res = new ArrayList<>();
        for (char[] row : board) {
            res.add(new String(row));
        }
        return res;
    }

    /**
     * ## Phase 2: Brute Force Approach - String Concatenation Backtracking
     *
     * **Detailed Intuition:**
     * This represents the "Think it" phase where a developer might build the strings dynamically
     * at every single recursive step. Instead of modifying a 2D array, we create a full string
     * for the current row, add it to our tracking list, recurse, and then remove it.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \cdot N^N)$. The asymptotic time is the same, but the hidden
     *   constant is much larger due to heavy string creation and GC overhead at every node.
     * - **Space Complexity:** $O(N)$ for recursion stack and $O(N)$ for the temporary list.
     *   High object churn on the heap.
     */
    public List<List<String>> generateBruteForce(int n) {
        List<List<String>> result = new ArrayList<>();
        backtrackBruteForce(0, new ArrayList<>(), result, n);
        return result;
    }

    private void backtrackBruteForce(int row, List<String> currentBoard, List<List<String>> result, int n) {
        if (row == n) {
            result.add(new ArrayList<>(currentBoard));
            return;
        }

        for (int col = 0; col < n; col++) {
            // Build the row string on the fly
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(i == col ? 'Q' : '.');
            }

            currentBoard.add(sb.toString());
            backtrackBruteForce(row + 1, currentBoard, result, n);
            currentBoard.remove(currentBoard.size() - 1);
        }
    }

    /**
     * ## Phase 3: Alternative Approach - Iterative Base-N Counting
     *
     * **Detailed Intuition:**
     * Since we are picking 1 item from $N$ options across $N$ independent categories (rows),
     * this perfectly mirrors counting in Base-N.
     * Total configurations = $N^N$.
     * If $N=3$, we count from $0$ to $26$ in Base-3 (000, 001, 002, 010, 011... 222).
     * The $i$-th digit of the Base-N number dictates which column gets the Queen in the $i$-th row.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \cdot N^N)$. We explicitly iterate $N^N$ times, building an $N$-length
     *   board each time.
     * - **Space Complexity:** $O(1)$ auxiliary memory (no recursion stack).
     */
    public List<List<String>> generateIterative(int n) {
        List<List<String>> result = new ArrayList<>();
        int totalConfigurations = (int) Math.pow(n, n);

        for (int i = 0; i < totalConfigurations; i++) {
            List<String> board = new ArrayList<>();
            int temp = i;

            // Extract the base-N digits to determine column placements
            for (int row = 0; row < n; row++) {
                int col = temp % n; // The column for the queen in the current row
                temp /= n;          // Shift to the next base-N digit

                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < n; c++) {
                    sb.append(c == col ? 'Q' : '.');
                }
                board.add(sb.toString());
            }
            // Reverse is optional depending on if we want top-down mapping,
            // but for generating ALL combinations, order doesn't strictly matter.
            result.add(board);
        }

        return result;
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        NQueensWithoutAttach solver = new NQueensWithoutAttach();

        // Testing N = 1, 2, 3 (Avoiding larger N as N^N grows extremely fast: 4^4 = 256, 5^5 = 3125)
        int[] testCases = {1, 2, 3};

        System.out.println("--- Running Unconstrained N-Queens Tests ---");

        IntStream.of(testCases).forEach(n -> {
            System.out.println("\nTesting N = " + n);

            long startOpt = System.nanoTime();
            List<List<String>> optimalRes = solver.generateOptimal(n);
            long timeOpt = System.nanoTime() - startOpt;

            long startBF = System.nanoTime();
            List<List<String>> bruteRes = solver.generateBruteForce(n);
            long timeBF = System.nanoTime() - startBF;

            long startIter = System.nanoTime();
            List<List<String>> iterRes = solver.generateIterative(n);
            long timeIter = System.nanoTime() - startIter;

            int expectedSize = (int) Math.pow(n, n);

            System.out.println("Total Expected:   " + expectedSize);
            System.out.println("Optimal Count:    " + optimalRes.size() + String.format(" [%.3f ms]", timeOpt / 1_000_000.0));
            System.out.println("BruteForce Count: " + bruteRes.size() + String.format(" [%.3f ms]", timeBF / 1_000_000.0));
            System.out.println("Iterative Count:  " + iterRes.size() + String.format(" [%.3f ms]", timeIter / 1_000_000.0));

            boolean match = (optimalRes.size() == expectedSize && bruteRes.size() == expectedSize && iterRes.size() == expectedSize);
            System.out.println("Result Validation: " + (match ? "PASS" : "FAIL"));

            if (n == 2) {
                System.out.println("Sample Output (N=2):");
                optimalRes.forEach(board -> {
                    System.out.println(String.join(" | ", board));
                });
            }
            System.out.println("----------------------------------------");
        });
    }
}


//
//// Problem Statement:
//// Place N Queens on an n x n chessboard such that each row has exactly one Queen,
//// but WITHOUT checking the attack conditions (i.e., multiple Queens may attack each other).
//// This code simply generates all possible placements of n Queens (one per row).
//
//public class NQueensWithoutAttach {
//    public static void main(String[] args) {
//        int n = 2; // size of the chessboard (n x n)
//
//        // Step 1: Create an empty chessboard and initialize with 'X' (empty cell)
//        char board[][] = new char[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                board[i][j] = 'X';
//            }
//        }
//
//        // Step 2: Start placing queens row by row
//        nQueens(board, 0);
//    }
//
//    // Recursive function to place queens row by row
//    private static void nQueens(char board[][], int row) {
//        // Base case: If all rows are filled, print the board configuration
//        if (row == board.length) {
//            printBoard(board);
//            return;
//        }
//
//        // Try placing a Queen in every column of the current row
//        for (int j = 0; j < board.length; j++) {
//            board[row][j] = 'Q';      // Place Queen at position (row, j)
//            nQueens(board, row + 1); // Recurse to next row
//            board[row][j] = 'X';      // Backtrack (remove Queen for next placement)
//        }
//    }
//
//    // Utility function to print the chessboard
//    private static void printBoard(char board[][]) {
//        System.out.println("----------- Chess Board -------------");
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board.length; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//}
//
///*
//Time Complexity:
//---------------
//- At each row, we try placing a queen in all n columns.
//- There are n rows, and for each row we have n choices → total possibilities = n^n.
//- Printing each board takes O(n^2).
//- Overall Time Complexity = O(n^n * n^2).
//
//Space Complexity:
//-----------------
//- Chessboard storage requires O(n^2).
//- Recursion depth = O(n) (one recursive call per row).
//- Total Space Complexity = O(n^2).
//*/

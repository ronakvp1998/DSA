package strivers.recursionbacktracking.hard;

/**
 * ============================================================================
 * 79. Word Search
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an m x n grid of characters board and a string word, return true if
 * word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells,
 * where adjacent cells are horizontally or vertically neighboring. The same
 * letter cell may not be used more than once.
 *
 * EXAMPLES:
 * Example 1:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: true
 *
 * Example 2:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 *
 * Example 3:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 * Output: false
 *
 * CONSTRAINTS:
 * m == board.length
 * n = board[i].length
 * 1 <= m, n <= 6
 * 1 <= word.length <= 15
 * board and word consists of only lowercase and uppercase English letters.
 *
 * Follow up: Could you use search pruning to make your solution faster with a larger board?
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for Grid DFS)
 * ============================================================================
 * Searching for "ABCC" starting from (0,0) in Example 1:
 * Board:
 * A B C E
 * S F C S
 * A D E E
 *
 * Tree representation mapping (Row, Col, Match Index):
 *
 *                        Start at (0,0) 'A' (Index 0)
 *                                |
 *                       Move Right -> (0,1) 'B' (Index 1)
 *                         /             |             \
 *               Up (Out)        Down -> (1,1) 'F'     Right -> (0,2) 'C' (Index 2)
 *                                  (Mismatch!)              /      |      \
 *                                                Up (Out)  Down(1,2)'C'  Right(0,3)'E'
 *                                                          (Index 3)✅    (Mismatch)
 *                                                         Match Found!
 *
 * Note: Backtracking is performed by marking visited cells with a special
 * character (e.g., '*') and then restoring them when returning up the tree.
 * ============================================================================
 */

import java.util.Arrays;

public class WordSearchSolution {

    /**
     * ============================================================================
     * PHASE 1: Optimal Approach - DFS with Search Pruning (Follow-up Addressed)
     * ============================================================================
     * Detailed Intuition:
     * We use a standard backtracking approach to traverse the grid. However, to
     * make this highly optimized (addressing the follow-up), we apply two major
     * pruning techniques BEFORE we even start the DFS:
     * 1. Frequency Check: We count characters in the board and the word. If the
     *    board lacks the necessary count of ANY character in the word, return false.
     * 2. Path Optimization (Prefix vs Suffix): If the starting character of the
     *    word appears more frequently in the board than the ending character, we
     *    reverse the word. This minimizes the branching factor at the start of our
     *    search, drastically reducing the size of the recursion tree.
     *
     * Note: We use explicit recursion calls (up, down, left, right) here as it
     * avoids the overhead of iterating through a directions array, making it
     * slightly faster in competitive programming contexts.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N * 3^L)
     *   Where M*N is the grid size, and L is word length. At each step, we have at
     *   most 3 directions to explore (since we won't go back to the previous cell).
     *   Pruning makes the average case significantly faster.
     * - Space Complexity: O(L) auxiliary stack space
     *   The depth of recursion goes up to the length of the word (L). We modify
     *   the board in-place, using O(1) heap space.
     * ============================================================================
     */
    public boolean existOptimalWithPruning(char[][] board, String word) {
        int m = board.length, n = board[0].length;

        // Pruning 1: Character frequency check
        int[] boardFreq = new int[128]; // Handles A-Z and a-z
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boardFreq[board[i][j]]++;
            }
        }

        int[] wordFreq = new int[128];
        for (char c : word.toCharArray()) {
            wordFreq[c]++;
            if (boardFreq[c] < wordFreq[c]) {
                return false; // Impossible to form the word
            }
        }

        // Pruning 2: Reverse word if start char is more frequent than end char
        // (This reduces the number of initial branches DFS has to explore)
        if (boardFreq[word.charAt(0)] > boardFreq[word.charAt(word.length() - 1)]) {
            word = new StringBuilder(word).reverse().toString();
        }

        char[] w = word.toCharArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Explore only if the first character matches
                if (board[i][j] == w[0] && dfsOptimal(board, i, j, w, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsOptimal(char[][] board, int i, int j, char[] word, int index) {
        if (index == word.length) return true;

        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word[index]) {
            return false;
        }

        // Mark as visited by altering the character
        char temp = board[i][j];
        board[i][j] = '*';

        // Explore 4 directions explicitly (Normal Recursion approach)
        boolean found = dfsOptimal(board, i + 1, j, word, index + 1) ||
                dfsOptimal(board, i - 1, j, word, index + 1) ||
                dfsOptimal(board, i, j + 1, word, index + 1) ||
                dfsOptimal(board, i, j - 1, word, index + 1);

        // Backtrack
        board[i][j] = temp;
        return found;
    }

    /**
     * ============================================================================
     * PHASE 2: Alternative Approach - DFS with For-Loop (Explore Candidates)
     * ============================================================================
     * Detailed Intuition:
     * In the context of a 2D grid, the equivalent of the "For-Loop Backtracking"
     * pattern used in combinations is iterating over a `directions` array.
     * Instead of explicitly writing out 4 OR conditions, we iterate through the
     * 4 possible directional offsets to find our next valid candidate cell.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N * 3^L)
     * - Space Complexity: O(L) auxiliary stack space
     * ============================================================================
     */
    public boolean existForLoop(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        char[] w = word.toCharArray();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfsForLoop(board, i, j, w, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private boolean dfsForLoop(char[][] board, int i, int j, char[] word, int index) {
        // Base case: All characters matched
        if (index == word.length) return true;

        // Boundary and match check
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word[index]) {
            return false;
        }

        char temp = board[i][j];
        board[i][j] = '*'; // Mark visited

        // For-Loop pattern iterating over valid next steps
        for (int[] dir : DIRECTIONS) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];

            if (dfsForLoop(board, nextI, nextJ, word, index + 1)) {
                return true;
            }
        }

        board[i][j] = temp; // Backtrack
        return false;
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Standard DFS Normal Recursion
     * ============================================================================
     * Detailed Intuition:
     * This is identical to the logic in Phase 1, but without the extreme pruning.
     * It relies entirely on short-circuit evaluation (`||`) to explore the grid.
     * This is the "Pick/Don't Pick" equivalent for grids—we try a path, if it fails,
     * the boolean short-circuit moves us to try the next path.
     *
     * Complexity Analysis:
     * - Time Complexity: O(M * N * 3^L)
     * - Space Complexity: O(L) auxiliary stack space
     * ============================================================================
     */
    public boolean existNormalRecursion(char[][] board, String word) {
        char[] w = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfsNormal(board, i, j, w, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsNormal(char[][] board, int i, int j, char[] word, int index) {
        if (index == word.length) return true;

        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word[index]) {
            return false;
        }

        char temp = board[i][j];
        board[i][j] = '*'; // Visited

        // Normal Recursion (Explicit chaining, no for-loop)
        boolean res = dfsNormal(board, i + 1, j, word, index + 1) ||
                dfsNormal(board, i - 1, j, word, index + 1) ||
                dfsNormal(board, i, j + 1, word, index + 1) ||
                dfsNormal(board, i, j - 1, word, index + 1);

        board[i][j] = temp; // Backtrack
        return res;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        WordSearchSolution solution = new WordSearchSolution();

        char[][] board1 = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };

        char[][] board2 = {
                {'a','a','a','a'},
                {'a','a','a','a'},
                {'a','a','a','a'}
        };

        // Structure: {Board, Word, Expected Result}
        Object[][] testCases = {
                {board1, "ABCCED", true},   // Example 1
                {board1, "SEE", true},      // Example 2
                {board1, "ABCB", false},    // Example 3
                {board2, "aaaaaaaaaaaaa", false} // Extreme case triggering pruning
        };

        System.out.println("====== WORD SEARCH DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            // We clone the boards because our algorithms run in-place.
            // In a real environment, backtracking restores the board perfectly,
            // but it's safe practice for tests.
            char[][] testBoard = cloneBoard((char[][]) testCases[i][0]);
            String word = (String) testCases[i][1];
            boolean expected = (boolean) testCases[i][2];

            System.out.println("Test Case " + (i + 1) + ": Word = \"" + word + "\" (Expected: " + expected + ")");

            // Phase 1: Optimal with Pruning
            boolean resOptimal = solution.existOptimalWithPruning(cloneBoard(testBoard), word);
            System.out.println("  [Optimal + Pruning] : " + resOptimal);

            // Phase 2: For-Loop Recursion
            boolean resForLoop = solution.existForLoop(cloneBoard(testBoard), word);
            System.out.println("  [For-Loop DFS]      : " + resForLoop);

            // Phase 3: Normal Recursion
            boolean resNormal = solution.existNormalRecursion(cloneBoard(testBoard), word);
            System.out.println("  [Normal Recursion]  : " + resNormal);

            System.out.println("--------------------------------------------------");
        }
    }

    // Utility for safely duplicating 2D char arrays in tests
    private static char[][] cloneBoard(char[][] original) {
        char[][] clone = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            clone[i] = original[i].clone();
        }
        return clone;
    }
}
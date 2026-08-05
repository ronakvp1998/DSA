package strivers.recursionbacktracking.medium;

/**
 * ==============================================================================================
 * 🤖 TOWER OF HANOI
 * ==============================================================================================
 *
 * PROBLEM STATEMENT:
 * ------------------
 * The Tower of Hanoi is a mathematical puzzle where we have 3 rods and N disks.
 * The puzzle's goal is to move all the disks from the source rod to the destination rod,
 * following these rules:
 * 1. Only one disk can be moved at a time.
 * 2. A disk can only be moved if it is the top disk on a rod.
 * 3. No disk may be placed on top of a smaller disk.
 *
 * Task:
 * Given N disks placed on the source rod (in increasing order of size from top to bottom),
 * return/print the steps required to move all disks to the destination rod using the helper rod.
 *
 * CONSTRAINTS:
 * ------------
 * - 1 <= N <= 16 (To avoid exponential explosion in standard execution)
 *
 * EXAMPLES:
 * ---------
 * Example 1:
 * Input: N = 2
 * Output:
 * Move disk 1 from A to B
 * Move disk 2 from A to C
 * Move disk 1 from B to C
 *
 * Example 2:
 * Input: N = 3
 * Output:
 * Move disk 1 from A to C
 * Move disk 2 from A to B
 * Move disk 1 from C to B
 * Move disk 3 from A to C
 * Move disk 1 from B to A
 * Move disk 2 from B to C
 * Move disk 1 from A to C
 *
 * CONCEPTUAL VISUALIZATION (Recursion Tree for N=3):
 * --------------------------------------------------
 * Let H(n, src, aux, dest) be the function.
 *
 *                                      H(3, A, B, C)
 *                                    /       |       \
 *                     H(2, A, C, B)      Move 3 A->C      H(2, B, A, C)
 *                     /     |     \                       /     |     \
 *         H(1, A, B, C) Move 2 H(1, C, A, B)  H(1, B, C, A) Move 2 H(1, A, B, C)
 *            |          A->B       |             |          B->C       |
 *        Move 1 A->C           Move 1 C->B   Move 1 B->A           Move 1 A->C
 *
 * ==============================================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class TowerOfHanoi {

    /**
     * ==========================================================================================
     * PHASE 1: OPTIMAL APPROACH (Recursive Divide & Conquer)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * This is the mathematical and most elegant approach (as provided in the initial code).
     * To move N disks from Source to Destination:
     * 1. We must first get the largest disk (N) to the bottom of Destination.
     *    To do this, the N-1 smaller disks must be safely parked on the Helper rod.
     * 2. Move the Nth disk to Destination.
     * 3. Move the N-1 disks from Helper to Destination, using Source as the new helper.
     * This perfectly maps to a recursive subproblem.
     *
     * (Note: Modified from the original code to return a List<String> for automated testing).
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N). The recurrence relation is T(N) = 2*T(N-1) + 1.
     *   Solving this yields 2^N - 1 total operations.
     * - Space Complexity: O(N) (Auxiliary Stack space). The maximum depth of the recursion
     *   tree is N. Heap space takes O(2^N) to store the result strings.
     */
    public static List<String> solveHanoiOptimal(int n, char source, char helper, char destination) {
        List<String> moves = new ArrayList<>();
        solveHanoiRecursive(n, source, helper, destination, moves);
        return moves;
    }

    private static void solveHanoiRecursive(int n, char source, char helper, char destination, List<String> moves) {
        // Base case: only 1 disk left, move it directly
        if (n == 1) {
            moves.add("Move disk 1 from " + source + " to " + destination);
            return;
        }

        // Step 1: Move top n-1 disks from source to helper (using destination as auxiliary space)
        solveHanoiRecursive(n - 1, source, destination, helper, moves);

        // Step 2: Move the nth (largest) disk from source to destination
        moves.add("Move disk " + n + " from " + source + " to " + destination);

        // Step 3: Move the n-1 disks from helper to destination (using source as auxiliary space)
        solveHanoiRecursive(n - 1, helper, source, destination, moves);
    }

    /**
     * ==========================================================================================
     * PHASE 2: ALTERNATIVE APPROACH (Iterative Simulation with Stacks)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * We can solve this without recursion by simulating the rods as Stacks and exploiting a
     * known mathematical pattern in the Tower of Hanoi:
     * 1. The total number of moves is exactly 2^N - 1.
     * 2. If N is even, the logical roles of Destination and Helper rods are swapped.
     * 3. For any move `i` (from 1 to 2^N - 1):
     *    - If `i % 3 == 1`: The only valid move is between Source and Destination.
     *    - If `i % 3 == 2`: The only valid move is between Source and Helper.
     *    - If `i % 3 == 0`: The only valid move is between Helper and Destination.
     *    ("Valid move" means moving the smaller top disk from one rod to the other).
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^N). We iterate exactly 2^N - 1 times.
     * - Space Complexity: O(N). Heap space is used for the 3 stacks containing N elements
     *   total. Auxiliary Stack Space is O(1).
     */
    public static List<String> solveHanoiIterative(int n, char source, char helper, char destination) {
        List<String> moves = new ArrayList<>();

        Stack<Integer> s = new Stack<>();
        Stack<Integer> a = new Stack<>();
        Stack<Integer> d = new Stack<>();

        // Initialize Source stack with disks (largest at bottom, smallest at top)
        // Using Java 8 Streams for clean initialization
        IntStream.rangeClosed(1, n)
                .map(i -> n - i + 1)
                .forEach(s::push);

        char srcPeg = source, auxPeg = helper, destPeg = destination;

        // If N is even, swap the logical destination and helper pegs
        if (n % 2 == 0) {
            char temp = auxPeg;
            auxPeg = destPeg;
            destPeg = temp;
        }

        int totalMoves = (1 << n) - 1; // Equivalent to Math.pow(2, n) - 1

        for (int i = 1; i <= totalMoves; i++) {
            if (i % 3 == 1) {
                moveDiskBetween(s, d, srcPeg, destPeg, moves);
            } else if (i % 3 == 2) {
                moveDiskBetween(s, a, srcPeg, auxPeg, moves);
            } else if (i % 3 == 0) {
                moveDiskBetween(a, d, auxPeg, destPeg, moves);
            }
        }

        return moves;
    }

    // Helper method to make the only valid move between two stacks
    private static void moveDiskBetween(Stack<Integer> peg1, Stack<Integer> peg2,
                                        char name1, char name2, List<String> moves) {
        if (peg1.isEmpty()) {
            moves.add(formatMove(peg2.peek(), name2, name1));
            peg1.push(peg2.pop());
        } else if (peg2.isEmpty()) {
            moves.add(formatMove(peg1.peek(), name1, name2));
            peg2.push(peg1.pop());
        } else if (peg1.peek() < peg2.peek()) {
            moves.add(formatMove(peg1.peek(), name1, name2));
            peg2.push(peg1.pop());
        } else {
            moves.add(formatMove(peg2.peek(), name2, name1));
            peg1.push(peg2.pop());
        }
    }

    private static String formatMove(int disk, char from, char to) {
        return "Move disk " + disk + " from " + from + " to " + to;
    }

    /**
     * ==========================================================================================
     * 4. TESTING SUITE
     * ==========================================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Starting Test Suite for Tower Of Hanoi...\n");

        int[] testCases = {1, 2, 3, 4}; // Number of disks

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            int expectedMoves = (1 << n) - 1;

            System.out.printf("Test Case %d: N = %d (Expected Moves: %d)%n", i + 1, n, expectedMoves);

            // Phase 1: Optimal Recursive
            List<String> optimalRes = solveHanoiOptimal(n, 'A', 'B', 'C');
            boolean optimalCountMatch = optimalRes.size() == expectedMoves;
            System.out.printf("   Optimal Recursive Approach -> %d moves generated [%s]%n",
                    optimalRes.size(), optimalCountMatch ? "PASS" : "FAIL");

            // Phase 2: Iterative Stack
            List<String> iterativeRes = solveHanoiIterative(n, 'A', 'B', 'C');
            boolean iterCountMatch = iterativeRes.size() == expectedMoves;
            System.out.printf("   Iterative Stack Approach   -> %d moves generated [%s]%n",
                    iterativeRes.size(), iterCountMatch ? "PASS" : "FAIL");

            // Validate consistency between approaches
            boolean isConsistent = optimalRes.equals(iterativeRes);
            System.out.printf("   Consistency Check          -> [%s]%n", isConsistent ? "PASS" : "FAIL");

            // Print actual steps for small N to verify visually
            if (n <= 3) {
                System.out.println("   --- Steps for N = " + n + " ---");
                optimalRes.forEach(step -> System.out.println("      " + step));
            }

            System.out.println("---------------------------------------------------------");
        }

        System.out.println("\n✅ All test cases executed.");
    }
}
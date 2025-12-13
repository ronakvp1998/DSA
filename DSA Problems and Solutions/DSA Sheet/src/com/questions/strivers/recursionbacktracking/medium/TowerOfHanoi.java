package com.questions.strivers.recursionbacktracking.medium;

/**
 * Problem Statement:
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
 * print the steps required to move all disks to the destination rod using the helper rod.
 *
 * Example:
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
 * ------------------------------------------------------------
 * Code Logic:
 * - Base Case: If only 1 disk is left, move it directly from source to destination.
 * - Recursive Steps:
 *   1. Move the top (n-1) disks from source → helper using destination as auxiliary.
 *   2. Move the nth (largest) disk from source → destination.
 *   3. Move the (n-1) disks from helper → destination using source as auxiliary.
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - O(2^N), since the recurrence relation is T(n) = 2*T(n-1) + 1.
 * - The total number of moves required is (2^N - 1).
 *
 * Space Complexity:
 * - O(N), due to the recursion stack depth (one function call for each disk).
 */

public class TowerOfHanoi {

    // Recursive function to solve Tower of Hanoi
    private static void solveHanoi(int n, char source, char helper, char destination) {
        // Base case: only 1 disk, move directly
        if (n == 1) {
            System.out.println("Move disk 1 from " + source + " to " + destination);
            return;
        }

        // Step 1: Move top n-1 disks from source → helper
        solveHanoi(n - 1, source, destination, helper);

        // Step 2: Move the nth (largest) disk from source → destination
        System.out.println("Move disk " + n + " from " + source + " to " + destination);

        // Step 3: Move the n-1 disks from helper → destination
        solveHanoi(n - 1, helper, source, destination);
    }

    public static void main(String[] args) {
        int n = 3; // number of disks
        System.out.println("Tower of Hanoi with " + n + " disks:");
        solveHanoi(n, 'A', 'B', 'C');
    }
}

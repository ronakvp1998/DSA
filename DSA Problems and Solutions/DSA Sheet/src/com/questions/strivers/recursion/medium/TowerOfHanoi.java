package com.questions.strivers.recursion.medium;

public class TowerOfHanoi {

    // Recursive function to solve Tower of Hanoi
    public static void solveHanoi(int n, char source, char helper, char destination) {
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

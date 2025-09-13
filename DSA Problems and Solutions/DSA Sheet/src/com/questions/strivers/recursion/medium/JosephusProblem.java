package com.questions.strivers.recursion.medium;

public class JosephusProblem {

    // Recursive function to find the safe position
    public static int josephus(int n, int k) {
        // Base case: when only one person is left, safe position is 0
        if (n == 1) {
            return 0;
        }

        // Recurrence relation:
        // (safe position of n-1 people + k) % n
        return (josephus(n - 1, k) + k) % n;
    }

    public static void main(String[] args) {
        int n = 7;  // Number of people in the circle
        int k = 3;  // Step size (every 3rd person is eliminated)

        // josephus(n, k) gives 0-based index, so +1 for 1-based answer
        int safePosition = josephus(n, k) + 1;

        System.out.println("The safe position is: " + safePosition);
    }
}

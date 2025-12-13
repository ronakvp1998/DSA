package com.questions.strivers.recursionbacktracking.basics;

public class FibonacciSeries {
    public static void main(String[] args) {
        int n = 10;  // We want the first 10 Fibonacci numbers
//        fibonacci(n, 0, 1);  // Start the Fibonacci sequence with 0 and 1

        // Print the first 10 Fibonacci numbers
        for (int i = 0; i < n; i++) {
            System.out.println(fibonacci(i));
        }
    }

    // parametrized approach
    private static void fibonacci(int n, int first, int second) {
        // Base case: when n reaches 0, stop the recursion
        if (n <= 0) {
            return;
        }

        // Print the current Fibonacci number
        System.out.println(first);

        // Call the function for the next Fibonacci number
        fibonacci(n - 1, second, first + second);
    }

    // functional approach
    // Recursive Fibonacci function
    private static int fibonacci(int n) {
        if (n <= 1) {
            return n;  // Base cases: fibonacci(0) = 0, fibonacci(1) = 1
        }
        return fibonacci(n - 1) + fibonacci(n - 2);  // Recursive call for fibonacci(n-1) + fibonacci(n-2)
    }

}

package com.questions.strivers.arrays.hard;

/*
Variation 2: Print the nth row of Pascal’s Triangle (1-based indexing).
------------------------------------
Approach 1 (`pascalTriangle` method):
------------------------------------
Use the mathematical binomial coefficient formula:
    Each element in the row is nCr(n-1, c-1)
    Since Pascal’s Triangle starts from row 0, the nth row corresponds to index (n-1)

For each column in that row (c from 1 to n), calculate the value using the `nCr` function.

Time Complexity: O(n)  -> We compute nCr n times, each in O(r) time but in this specific formula it’s simplified.
Space Complexity: O(1) -> Constant space is used.

------------------------------------
Approach 2 (`pascalTriangle2` method):
------------------------------------
This is a more **optimized and efficient approach**:
- It builds the row iteratively using the recurrence:
    C(n, k+1) = C(n, k) * (n-k)/(k+1)
- Start from 1, and iteratively calculate the next element using previous one.

Time Complexity: O(n)
Space Complexity: O(1)
*/

public class PascalTriangle2 {

    // Method to compute nCr using a multiplicative formula (avoids large factorials)
    private static long nCr(int n, int r) {
        long res = 1;

        // Compute nCr iteratively using:
        // res = res * (n - i) / (i + 1)
        for (int i = 0; i < r; i++) {
            res = res * (n - i);     // Multiply numerator
            res = res / (i + 1);     // Divide denominator
        }

        return res; // Return the result
    }

    // Approach 1: Prints the nth row using the binomial coefficient method
    private static void pascalTriangle(int n) {
        // Iterate over each element in the nth row (1-based)
        for (int c = 1; c <= n; c++) {
            // Print the element at column c: C(n-1, c-1)
            System.out.print(nCr(n - 1, c - 1) + " ");
        }
        System.out.println(); // Move to the next line after printing the row
    }

    // Approach 2: Optimized way to print nth row using iterative formula
    static void pascalTriangle2(int n) {
        long ans = 1; // First element is always 1
        System.out.print(ans + " "); // Print the first element

        // Calculate and print the rest of the elements using:
        // next = prev * (n - i) / i
        for (int i = 1; i < n; i++) {
            ans = ans * (n - i); // Multiply numerator
            ans = ans / i;       // Divide denominator
            System.out.print(ans + " "); // Print current element
        }

        System.out.println(); // Move to the next line after printing the row
    }

    // Main method to test both approaches
    public static void main(String[] args) {
        int n = 5; // Specify the row number (1-based index)

        // Call first approach
        pascalTriangle(n);     // Output: 1 4 6 4 1

        // Call optimized approach
        pascalTriangle2(n);    // Output: 1 4 6 4 1
    }
}

package com.questions.strivers.arrays.hard;

/*
Variation 1: Given row number r and column number c.
Print the element at position (r, c) in Pascal’s triangle.

Approach:
-----------
In Pascal’s Triangle, each element at (r, c) is the value of the binomial coefficient "nCr", where:
    - n = r - 1
    - r = c - 1
This is because Pascal's Triangle starts from 0-index, and the formula to find the value at position (r, c) is:
    Value = C(r-1, c-1) = (r-1)! / ((c-1)! * (r-c)!)

To avoid computing large factorials directly (which can cause overflow), we use a multiplicative formula:
    C(n, r) = C(n, r - 1) * (n - r + 1) / r

This gives an efficient way to compute the binomial coefficient without recursion or factorials.

Time Complexity:
---------------
O(c)  – We loop from 0 to r (which is `c` in our nCr formula), so it's linear in terms of column `c`.

Space Complexity:
----------------
O(1) – Constant extra space used, since we’re just using variables (no array or recursion stack).
*/

public class PascalTriangle1 {

    // Function to compute nCr using an optimized iterative method
    private static long nCr(int n, int r) {
        long res = 1; // Initialize result

        // Iteratively calculate value of nCr using the formula:
        // res = res * (n - i) / (i + 1)
        for (int i = 0; i < r; i++) {
            res = res * (n - i);     // Multiply numerator part (n - i)
            res = res / (i + 1);     // Divide by denominator part (i + 1)
        }

        return res; // Return the computed binomial coefficient
    }

    // Function to return the element at position (r, c) in Pascal's Triangle
    private static int pascalTriangle(int r, int c) {
        // Since Pascal Triangle is 0-indexed, we use (r - 1) and (c - 1)
        int element = (int) nCr(r - 1, c - 1);
        return element; // Return the element at position (r, c)
    }

    // Main method to test the function
    public static void main(String[] args) {
        int r = 5; // Row number
        int c = 3; // Column number

        // Get the element at position (r, c) in Pascal's Triangle
        int element = pascalTriangle(r, c);

        // Print the result
        System.out.println("The element at position (r,c) is: " + element);
    }

}

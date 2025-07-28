package com.questions.strivers.arrays.hard;

import java.util.ArrayList;
import java.util.List;

/*
Variation 3: Given the number of rows n. Print the first n rows of Pascal’s triangle.
Approach:
---------
There are two ways to construct the first `n` rows of Pascal’s Triangle:

🔸 Approach 1 (Function: pascalTriangle):
- For every element at position (row, col), compute C(row-1, col-1) using the `nCr` function.
- Time Complexity: O(n^2) (n rows * up to n elements each, with each nCr taking O(r) but practically optimized)
- Space Complexity: O(1) extra (excluding the result list)

🔸 Approach 2 (Function: pascalTriangle2):
- Use a helper function `generateRow()` to build each row iteratively using previous element:
  C(n, k+1) = C(n, k) * (n-k)/(k+1)
- More efficient and avoids repeated calculations.
- Time Complexity: O(n^2)
- Space Complexity: O(1) extra (excluding result list)
*/

public class PascalTriangle3 {

    // Helper method to calculate nCr (Binomial Coefficient)
    public static int nCr(int n, int r) {
        long res = 1;

        // Iteratively calculate nCr = n! / (r! * (n-r)!)
        for (int i = 0; i < r; i++) {
            res = res * (n - i);     // Multiply numerator
            res = res / (i + 1);     // Divide denominator
        }

        return (int) res; // Return as int (safe since n <= 30 in most cases)
    }

    // Approach 1: Build entire Pascal’s triangle using nCr formula
    public static List<List<Integer>> pascalTriangle(int n) {
        List<List<Integer>> ans = new ArrayList<>(); // Stores final triangle

        // Loop through each row (1-based indexing)
        for (int row = 1; row <= n; row++) {
            List<Integer> tempLst = new ArrayList<>(); // Temporary list for current row

            // For each column in the row, compute value using nCr(row-1, col-1)
            for (int col = 1; col <= row; col++) {
                tempLst.add(nCr(row - 1, col - 1));
            }

            // Add completed row to the final result
            ans.add(tempLst);
        }

        return ans; // Return Pascal’s Triangle
    }

    // Helper function to generate a single row efficiently using iterative approach
    public static List<Integer> generateRow(int row) {
        long ans = 1;
        List<Integer> ansRow = new ArrayList<>();
        ansRow.add(1); // 1st element is always 1

        // Build rest of the row based on previous element
        for (int col = 1; col < row; col++) {
            ans = ans * (row - col); // Multiply numerator
            ans = ans / col;         // Divide denominator
            ansRow.add((int) ans);   // Add to the row
        }

        return ansRow; // Return the full row
    }

    // Approach 2: Generate Pascal’s triangle using iterative row building
    public static List<List<Integer>> pascalTriangle2(int n) {
        List<List<Integer>> ans = new ArrayList<>(); // Stores final triangle

        // For each row number, generate row and add to result
        for (int row = 1; row <= n; row++) {
            ans.add(generateRow(row));
        }

        return ans; // Return the complete Pascal’s Triangle
    }

    // Main method for testing
    public static void main(String[] args) {
        int n = 5;

        // Generate triangle using approach 1
        List<List<Integer>> ans = pascalTriangle(n);

        // Print Pascal’s Triangle
        for (List<Integer> it : ans) {
            for (int ele : it) {
                System.out.print(ele + " ");
            }
            System.out.println(); // Move to next line after each row
        }

        /*
        Output:
        1
        1 1
        1 2 1
        1 3 3 1
        1 4 6 4 1
        */
    }
}

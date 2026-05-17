package com.questions.strivers.binarysearch.bson2darray;

import java.util.ArrayList;
import java.util.Arrays;

/*
Problem Statement: You have been given a 2-D array 'mat' of size 'N x M' where 'N' and 'M'
denote the number of rows and columns, respectively. The elements of each row and each
column are sorted in non-decreasing order.
But, the first element of a row is not necessarily greater than the last element of the
previous row (if it exists).
You are given an integer ‘target’, and your task is to find if it exists in the given 'mat' or not.

Pre-requisite: Search in a 2D sorted matrix

Examples:

Example 1:
Input Format: N = 5, M = 5, target = 14
mat =
1   4   7   11  15
2   5   8   12  19
3   6   9   16  22
10  13  14  17  24
18  21  23  26  30

Result: true
Explanation: Target 14 is present in the cell (3, 2)(0-based indexing).

Example 2:
Input Format: N = 3, M = 3, target = 12
mat =
1   4   7
2   5   8
3   6   9

Result: false
Explanation: As target 12 is not present in the matrix, the answer is false.
 */
public class SearchRowColumnWiseSortedMatrix {

    // ----------------- Approach 1: Brute Force Search -----------------
    /*
       Idea: Traverse through every element in the matrix and compare with the target.
       If found, return true, else return false after complete traversal.

       Time Complexity: O(N * M)  → Checking all elements
       Space Complexity: O(1)     → No extra space used
    */
    private static boolean searchElement(ArrayList<ArrayList<Integer>> matrix, int target) {
        int n = matrix.size(), m = matrix.get(0).size();

        // Traverse every element of the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == target)
                    return true; // Found the target
            }
        }
        return false; // Target not found
    }

    // ----------------- Helper: Binary Search -----------------
    /*
       Standard binary search on a 1D sorted list.

       Time Complexity: O(log M) → Half search space each step
       Space Complexity: O(1)
    */
    private static boolean binarySearch2(ArrayList<Integer> nums, int target) {
        int n = nums.size();
        int low = 0, high = n - 1;

        while (low <= high) {
            int mid = (low + high) / 2; // middle index
            if (nums.get(mid) == target) return true;     // target found
            else if (target > nums.get(mid)) low = mid + 1; // search right half
            else high = mid - 1;                           // search left half
        }
        return false; // target not found
    }

    // ----------------- Approach 2: Row-wise Binary Search -----------------
    /*
       Idea: Each row is sorted. Perform binary search in every row to check for target.

       Steps:
       1. For each row, perform binary search.
       2. If found, return true.
       3. If not found in any row, return false.

       Time Complexity: O(N * log M)  → N rows, each binary search takes log(M)
       Space Complexity: O(1)
    */
    private static boolean searchElement2(ArrayList<ArrayList<Integer>> matrix, int target) {
        int n = matrix.size();
        int m = matrix.get(0).size();

        for (int i = 0; i < n; i++) {
            boolean flag = binarySearch2(matrix.get(i), target); // search in each row
            if (flag) return true;
        }
        return false;
    }

    // ----------------- Approach 3: Optimized Search from Top-Right -----------------
    /*
       Idea: Start from the top-right corner (0, m-1).
       - If current element == target → Found
       - If current element < target → Move down (row++)
       - If current element > target → Move left (col--)

       Why it works?
       - Since rows and columns are sorted, moving down increases numbers and moving left decreases numbers.

       Time Complexity: O(N + M) → At most N downward + M leftward moves
       Space Complexity: O(1)
    */
    private static boolean searchElement3(ArrayList<ArrayList<Integer>> matrix, int target) {
        int n = matrix.size();
        int m = matrix.get(0).size();
        int row = 0, col = m - 1; // Start from top-right corner

        while (row < n && col >= 0) {
            if (matrix.get(row).get(col) == target) return true; // found
            else if (matrix.get(row).get(col) < target) row++;   // move downward
            else col--;                                         // move left
        }
        return false; // not found
    }

    // ----------------- Driver Code -----------------
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(Arrays.asList(1, 4, 7, 11, 15)));
        matrix.add(new ArrayList<>(Arrays.asList(2, 5, 8, 12, 19)));
        matrix.add(new ArrayList<>(Arrays.asList(3, 6, 9, 16, 22)));
        matrix.add(new ArrayList<>(Arrays.asList(10, 13, 14, 17, 24)));
        matrix.add(new ArrayList<>(Arrays.asList(18, 21, 23, 26, 30)));

        boolean result = searchElement3(matrix, 14); // Using Optimized Approach
        System.out.println(result ? "true" : "false"); // Expected: true
    }
}

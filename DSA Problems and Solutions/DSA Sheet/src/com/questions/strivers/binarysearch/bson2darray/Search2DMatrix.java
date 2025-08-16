package com.questions.strivers.binarysearch.bson2darray;

import java.util.ArrayList;
import java.util.Arrays;

/*
Problem Statement:
You are given a 2-D array 'mat' of size 'N x M' where 'N' and 'M' denote the number of rows and columns, respectively.
The elements of each row are sorted in non-decreasing order. Moreover, the first element of a row is greater than the
last element of the previous row (if it exists).

You are given an integer ‘target’, and your task is to find if it exists in the given 'mat' or not.

Examples

Example 1:
Input Format: N = 3, M = 4, target = 8,
mat[] =
1  2  3  4
5  6  7  8
9 10 11 12
Result: true
Explanation: The ‘target’ = 8 exists in the 'mat' at index (1, 3).

Example 2:
Input Format: N = 3, M = 3, target = 78,
mat[] =
1  2  4
6  7  8
9 10 34
Result: false
Explanation: The ‘target’ = 78 does not exist in the 'mat'. Therefore the output is 'false'.
 */
public class Search2DMatrix {

    /*
    Approach 1: Brute Force Traversal
    - Traverse through every element of the matrix.
    - Compare with the target.
    - If found, return true; else return false after traversal.

    Time Complexity: O(N * M)  -> N = number of rows, M = number of columns
    Space Complexity: O(1)     -> No extra space used
     */
    public static boolean searchMatrix(ArrayList<ArrayList<Integer>> matrix, int target) {
        int n = matrix.size(), m = matrix.get(0).size();

        // Traverse each element in the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == target)
                    return true;
            }
        }
        return false;
    }

    /*
    Helper Function: Binary Search on 1D Array
    - Standard binary search implementation on a row.

    Time Complexity: O(log M)
    Space Complexity: O(1)
     */
    public static boolean binarySearch2(ArrayList<Integer> nums, int target) {
        int n = nums.size();
        int low = 0, high = n - 1;

        // Perform binary search
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums.get(mid) == target) return true;
            else if (target > nums.get(mid)) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }

    /*
    Approach 2: Row-wise Search + Binary Search
    - For each row, check if target lies within the range [first element, last element].
    - If yes, apply binary search on that row.
    - If found, return true.

    Time Complexity: O(N * log M) -> N = rows, log M for binary search on each row
    Space Complexity: O(1)
     */
    public static boolean searchMatrix2(ArrayList<ArrayList<Integer>> matrix, int target) {
        int n = matrix.size();
        int m = matrix.get(0).size();

        for (int i = 0; i < n; i++) {
            // Check if target can be inside this row
            if (matrix.get(i).get(0) <= target && target <= matrix.get(i).get(m - 1)) {
                return binarySearch2(matrix.get(i), target);
            }
        }
        return false;
    }

    /*
    Approach 3: Treat Matrix as a Flattened Sorted Array
    - Since rows are sorted and each row’s first element is greater than the previous row’s last,
      the whole matrix can be viewed as a sorted 1D array.
    - Apply binary search on the "virtual 1D array" using index mapping:
      row = mid / m, col = mid % m

    Time Complexity: O(log(N*M)) -> Binary search on total elements
    Space Complexity: O(1)
     */
    public static boolean searchMatrix3(ArrayList<ArrayList<Integer>> matrix, int target) {
        int n = matrix.size();
        int m = matrix.get(0).size();

        int low = 0, high = n * m - 1; // Treat as 1D array of size N*M
        while (low <= high) {
            int mid = (low + high) / 2;

            // Map mid index to 2D indices
            int row = mid / m, col = mid % m;

            if (matrix.get(row).get(col) == target) return true;
            else if (matrix.get(row).get(col) < target) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        matrix.add(new ArrayList<>(Arrays.asList(5, 6, 7, 8)));
        matrix.add(new ArrayList<>(Arrays.asList(9, 10, 11, 12)));

        boolean result = searchMatrix(matrix, 8);
        System.out.println(result ? "true" : "false");
    }

}

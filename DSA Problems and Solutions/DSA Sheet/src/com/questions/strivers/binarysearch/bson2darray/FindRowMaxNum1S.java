package com.questions.strivers.binarysearch.bson2darray;

import java.util.ArrayList;
import java.util.Arrays;

/*
Problem Statement:
You have been given a non-empty grid ‘mat’ with 'n' rows and 'm' columns consisting of only 0s and 1s.
All the rows are sorted in ascending order.
Your task is to find the index of the row with the maximum number of ones.

Note:
- If two rows have the same number of ones, return the one with a smaller index.
- If there's no row with at least 1 one, return -1.

Examples:
Input:
n = 3, m = 3
mat =
1 1 1
0 0 1
0 0 0
Output: 0
Explanation: Row 0 has the most number of ones.

Input:
n = 2, m = 2
mat =
0 0
0 0
Output: -1
Explanation: No row contains any 1.
 */
public class FindRowMaxNum1S {

    // ----------------------------------------------------------
    // Approach 1: Brute Force
    // Count the number of 1's in each row and track the row with the maximum.
    // ----------------------------------------------------------
    private static int rowWithMax1s(ArrayList<ArrayList<Integer>> matrix, int n, int m) {
        int cnt_max = 0; // maximum count of 1s found so far
        int index = -1;  // index of row with maximum 1s

        // Traverse each row
        for (int i = 0; i < n; i++) {
            int cnt_ones = 0;
            // Count 1's in current row
            for (int j = 0; j < m; j++) {
                cnt_ones += matrix.get(i).get(j);
            }
            // Update maximum if current row has more 1s
            if (cnt_ones > cnt_max) {
                cnt_max = cnt_ones;
                index = i;
            }
        }
        return index;
    }
    // Time Complexity: O(n * m) → Checking each element.
    // Space Complexity: O(1) → No extra space used.


    // ----------------------------------------------------------
    // Utility Function: Lower Bound
    // Returns the first index where arr[index] >= x
    // ----------------------------------------------------------
    private static int lowerBound2(ArrayList<Integer> arr, int n, int x) {
        int low = 0, high = n - 1;
        int ans = n; // default = n (if all elements < x)

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) >= x) {
                ans = mid;      // potential answer
                high = mid - 1; // look on left
            } else {
                low = mid + 1;  // move right
            }
        }
        return ans;
    }


    // ----------------------------------------------------------
    // Approach 2: Binary Search on Each Row
    // Since rows are sorted (0s then 1s), we can find the
    // first occurrence of 1 using lowerBound (O(log m)).
    // Number of 1's = m - index_of_first_1
    // ----------------------------------------------------------
    private static int rowWithMax1s2(ArrayList<ArrayList<Integer>> matrix, int n, int m) {
        int cnt_max = 0; // maximum number of 1s
        int index = -1;  // row index with max 1s

        // Traverse each row
        for (int i = 0; i < n; i++) {
            // Find the first index of 1 in the row using lowerBound
            int firstOneIndex = lowerBound2(matrix.get(i), m, 1);
            int cnt_ones = m - firstOneIndex; // number of 1s in row

            // Update maximum if this row has more 1s
            if (cnt_ones > cnt_max) {
                cnt_max = cnt_ones;
                index = i;
            }
        }
        return index;
    }
    // Time Complexity: O(n * log m) → binary search in each row
    // Space Complexity: O(1) → only variables used


    // ----------------------------------------------------------
    // Driver Code
    // ----------------------------------------------------------
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
        matrix.add(new ArrayList<>(Arrays.asList(0, 0, 1)));
        matrix.add(new ArrayList<>(Arrays.asList(0, 0, 0)));

        int n = 3, m = 3;
        System.out.println("The row with the maximum number of 1's is: " +
                rowWithMax1s(matrix, n, m));

        System.out.println("The row with the maximum number of 1's (Binary Search Optimized) is: " +
                rowWithMax1s2(matrix, n, m));
    }
}

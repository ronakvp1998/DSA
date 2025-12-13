package com.questions.strivers.binarysearch.bson2darray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Problem Statement:
------------------
Given a row-wise sorted matrix of size M x N, where M is no. of rows and N is no. of columns,
find the median in the given matrix.

Note: M x N is always odd, so the median will always exist.

Examples:
---------
Example 1:
Input Format: M = 3, N = 3, matrix[][] =
1 4 9
2 5 6
3 8 7
Result: 5
Explanation: Flattened sorted array = 1 2 3 4 5 6 7 8 9. Median = 5

Example 2:
Input Format: M = 3, N = 3, matrix[][] =
1 3 8
2 3 4
1 2 5
Result: 3
Explanation: Flattened sorted array = 1 1 2 2 3 3 4 5 8. Median = 3
*/

public class MedianRowWiseSortedMatrix {

    /*
     * Approach 1: Brute Force
     * -----------------------
     * 1. Traverse through the matrix and store all elements in a list.
     * 2. Sort the list.
     * 3. The median is at index (M*N)/2 in the sorted list.
     *
     * Time Complexity: O(M*N log(M*N)) → inserting all elements + sorting
     * Space Complexity: O(M*N) → extra space to store elements
     */
    private static int median(int matrix[][], int m, int n) {
        List<Integer> lst = new ArrayList<>();

        // Step 1: Copy all elements to list
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                lst.add(matrix[i][j]);
            }
        }

        // Step 2: Sort the list
        Collections.sort(lst);

        // Step 3: Return middle element
        return lst.get((m * n) / 2);
    }


    /*
     * Helper Function: upperBound2
     * ----------------------------
     * Returns the index of the first element greater than 'x' in a sorted row.
     * Essentially counts how many elements are <= x.
     *
     * Time Complexity: O(log N) per row
     * Space Complexity: O(1)
     */
    static int upperBound2(int[] arr, int x, int n) {
        int low = 0, high = n - 1;
        int ans = n;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] > x) {
                ans = mid;      // potential answer
                high = mid - 1; // move left
            } else {
                low = mid + 1;  // move right
            }
        }
        return ans; // count of elements <= x
    }

    /*
     * Helper Function: countSmallEqual2
     * ---------------------------------
     * Counts total number of elements <= x across all rows.
     *
     * Time Complexity: O(M log N)
     * Space Complexity: O(1)
     */
    static int countSmallEqual2(int[][] matrix, int m, int n, int x) {
        int cnt = 0;
        for (int i = 0; i < m; i++) {
            cnt += upperBound2(matrix[i], x, n);
        }
        return cnt;
    }

    /*
     * Approach 2: Optimized Binary Search on Answer
     * ---------------------------------------------
     * 1. The median is the (M*N)/2-th element in the sorted order.
     * 2. Since the matrix rows are sorted, we can binary search over the
     *    value range (from min element to max element).
     * 3. For each mid value, count how many elements are <= mid.
     * 4. Adjust the search range until we find the median.
     *
     * Time Complexity: O( log(max-min) * M * log N )
     *    - log(max-min) for binary search on value range
     *    - M*logN for counting elements <= mid
     * Space Complexity: O(1)
     */
    static int median2(int[][] matrix, int m, int n) {
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;

        // Step 1: Get min element (first column) and max element (last column)
        for (int i = 0; i < m; i++) {
            low = Math.min(low, matrix[i][0]);       // smallest element
            high = Math.max(high, matrix[i][n - 1]); // largest element
        }

        int req = (n * m) / 2; // index of median element

        // Step 2: Binary search on possible values
        while (low <= high) {
            int mid = (low + high) / 2;

            // Step 3: Count elements <= mid
            int smallEqual = countSmallEqual2(matrix, m, n, mid);

            if (smallEqual <= req) {
                low = mid + 1;  // search higher values
            } else {
                high = mid - 1; // search lower values
            }
        }

        return low; // final median
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4, 5},
                {8, 9, 11, 12, 13},
                {21, 23, 25, 27, 29}
        };

        int m = matrix.length;
        int n = matrix[0].length;

        // Brute Force Method
        int ans1 = median(matrix, m, n);
        System.out.println("Brute Force Median: " + ans1);

        // Optimized Method
        int ans2 = median2(matrix, m, n);
        System.out.println("Optimized Binary Search Median: " + ans2);
    }
}

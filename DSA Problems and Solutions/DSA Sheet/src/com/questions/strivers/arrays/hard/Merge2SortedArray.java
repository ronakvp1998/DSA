package com.questions.strivers.arrays.hard;

import java.util.Arrays;
/*Problem statement: Given two sorted arrays arr1[] and arr2[] of sizes n and m in non-decreasing order.
Merge them in sorted order.
Modify arr1 so that it contains the first N elements and modify arr2 so that it contains the last M elements.
        Example 1:

        Input:
        n = 4, arr1[] = [1 4 8 10]
        m = 5, arr2[] = [2 3 9]

        Output:
        arr1[] = [1 2 3 4]
        arr2[] = [8 9 10]

        Explanation:
        After merging the two non-decreasing arrays, we get, 1,2,3,4,8,9,10.

        Example2:

        Input:
        n = 4, arr1[] = [1 3 5 7]
        m = 5, arr2[] = [0 2 6 8 9]

        Output:
        arr1[] = [0 1 2 3]
        arr2[] = [5 6 7 8 9]

        Explanation:
        After merging the two non-decreasing arrays, we get, 0 1 2 3 5 6 7 8 9. */

public class Merge2SortedArray {

    /**
     * ✅ Approach 1: Using Extra Array (Brute Force)
     * Merge both arrays into a third array in sorted order, then copy back.
     * Time Complexity: O(n + m) + O(n + m) = O(n + m)
     * Space Complexity: O(n + m) for the extra array
     */
    private static void merge(long[] arr1, long[] arr2, int n, int m) {
        // Temporary array to store merged result
        long[] arr3 = new long[n + m];
        int left = 0;
        int right = 0;
        int index = 0;

        // Merge elements in sorted order
        while (left < n && right < m) {
            if (arr1[left] <= arr2[right]) {
                arr3[index++] = arr1[left++];
            } else {
                arr3[index++] = arr2[right++];
            }
        }

        // Copy remaining elements of arr1[]
        while (left < n) {
            arr3[index++] = arr1[left++];
        }

        // Copy remaining elements of arr2[]
        while (right < m) {
            arr3[index++] = arr2[right++];
        }

        // Copy merged elements back to arr1 and arr2
        for (int i = 0; i < n + m; i++) {
            if (i < n) {
                arr1[i] = arr3[i];
            } else {
                arr2[i - n] = arr3[i];
            }
        }
    }

    /**
     * ✅ Approach 2: Swap and Sort (Better)
     * Swap largest of arr1 with smallest of arr2, then sort both arrays.
     * Time Complexity: O(min(n, m)) for swap + O(nlogn + mlogm) for sorting
     * Space Complexity: O(1) — in-place
     */
    private static void merge2(long[] arr1, long[] arr2, int n, int m) {
        int left = n - 1;
        int right = 0;

        // Swap elements until arr1's end is <= arr2's start
        while (left >= 0 && right < m) {
            if (arr1[left] > arr2[right]) {
                // Swap arr1[left] and arr2[right]
                long temp = arr1[left];
                arr1[left] = arr2[right];
                arr2[right] = temp;
                left--;
                right++;
            } else {
                break; // Already sorted
            }
        }

        // Sort both arrays individually
        Arrays.sort(arr1);
        Arrays.sort(arr2);
    }

    /**
     * 🔁 Utility Function: Swap if arr1[ind1] > arr2[ind2]
     */
    private static void swapIfGreater(long[] arr1, long[] arr2, int ind1, int ind2) {
        if (arr1[ind1] > arr2[ind2]) {
            long temp = arr1[ind1];
            arr1[ind1] = arr2[ind2];
            arr2[ind2] = temp;
        }
    }

    /**
     * ✅✅ Approach 3: Gap Method (Optimal — No Extra Space, No Sorting)
     * Use shell sort-like gap comparison between arr1 and arr2.
     * Time Complexity: O((n+m) * log(n+m)) — log iterations with linear scan each
     * Space Complexity: O(1) — in-place
     */
    private static void merge3(long[] arr1, long[] arr2, int n, int m) {
        int len = n + m;

        // Initial gap
        int gap = (len / 2) + (len % 2); // ceil(len/2)

        while (gap > 0) {
            int left = 0;
            int right = left + gap;

            // Compare and swap elements across the virtual combined array
            while (right < len) {
                // Case 1: Both pointers in arr1
                if (left < n && right < n) {
                    swapIfGreater(arr1, arr1, left, right);
                }
                // Case 2: left in arr1, right in arr2
                else if (left < n && right >= n) {
                    swapIfGreater(arr1, arr2, left, right - n);
                }
                // Case 3: Both pointers in arr2
                else {
                    swapIfGreater(arr2, arr2, left - n, right - n);
                }
                left++;
                right++;
            }

            // Break if gap becomes 1 and completed
            if (gap == 1) break;

            // Recalculate the next gap
            gap = (gap / 2) + (gap % 2);
        }
    }

    public static void main(String[] args) {
        long[] arr1 = {1, 4, 8, 10};
        long[] arr2 = {2, 3, 9};
        int n = 4, m = 3;

        // You can test each merge method here:
        merge(arr1, arr2, n, m);     // Brute Force
        // merge2(arr1, arr2, n, m); // Swap and Sort
        // merge3(arr1, arr2, n, m); // Optimal Gap Method

        // Print merged arrays
        System.out.println("The merged arrays are:");
        System.out.print("arr1[] = ");
        for (int i = 0; i < n; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.print("\narr2[] = ");
        for (int i = 0; i < m; i++) {
            System.out.print(arr2[i] + " ");
        }
        System.out.println();
    }
}

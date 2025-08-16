package com.questions.strivers.binarysearch.bsonanswers;

import java.util.ArrayList;
import java.util.List;

/*
Problem Statement:
Given two sorted arrays arr1 and arr2 of size m and n respectively,
return the median of the two sorted arrays. The median is defined as
the middle value of a sorted list of numbers. In case the length of
the list is even, the median is the average of the two middle elements.

Examples

Example 1:
Input Format: n1 = 3, arr1[] = {2,4,6}, n2 = 3, arr2[] = {1,3,5}
Result: 3.5
Explanation: The merged array is {1, 2, 3, 4, 5, 6}.
Median = (3+4)/2 = 3.5

Example 2:
Input Format: n1 = 3, arr1[] = {2,4,6}, n2 = 2, arr2[] = {1,3}
Result: 3
Explanation: The merged array is {1, 2, 3, 4, 6}.
Median = 3
 */
public class MedianOfTwoSortedArraysDiffSize {

    // ---------------------- Approach 1: Merge Full Arrays ----------------------
    public static double median(int[] a, int[] b) {
        // Get sizes of arrays
        int n1 = a.length;
        int n2 = b.length;

        List<Integer> arr3 = new ArrayList<>(); // merged sorted list
        int i = 0, j = 0;

        // Merge both arrays into arr3 (like merge step in merge sort)
        while (i < n1 && j < n2) {
            if (a[i] < b[j]) arr3.add(a[i++]);
            else arr3.add(b[j++]);
        }

        // Add remaining elements
        while (i < n1) arr3.add(a[i++]);
        while (j < n2) arr3.add(b[j++]);

        // Find median
        int n = n1 + n2;
        if (n % 2 == 1) {
            // Odd length → middle element
            return (double) arr3.get(n / 2);
        }
        // Even length → average of two middle elements
        double median = ((double) arr3.get(n / 2) + (double) arr3.get((n / 2) - 1)) / 2.0;
        return median;
    }
    /*
    ✅ Approach:
       - Merge both sorted arrays fully
       - Find the middle element(s) for median

    ⏱️ Time Complexity: O(n1 + n2)  (merging step)
    🛑 Space Complexity: O(n1 + n2) (extra merged list)
    */


    // ---------------------- Approach 2: Merge Until Median ----------------------
    public static double median2(int[] a, int[] b) {
        int n1 = a.length, n2 = b.length;
        int n = n1 + n2; // total length

        // Required median indices
        int ind2 = n / 2;
        int ind1 = ind2 - 1;
        int cnt = 0;
        int ind1el = -1, ind2el = -1;

        int i = 0, j = 0;
        // Merge only until we reach median position
        while (i < n1 && j < n2) {
            if (a[i] < b[j]) {
                if (cnt == ind1) ind1el = a[i];
                if (cnt == ind2) ind2el = a[i];
                cnt++;
                i++;
            } else {
                if (cnt == ind1) ind1el = b[j];
                if (cnt == ind2) ind2el = b[j];
                cnt++;
                j++;
            }
        }

        // Add leftover elements (only until required indices)
        while (i < n1) {
            if (cnt == ind1) ind1el = a[i];
            if (cnt == ind2) ind2el = a[i];
            cnt++;
            i++;
        }
        while (j < n2) {
            if (cnt == ind1) ind1el = b[j];
            if (cnt == ind2) ind2el = b[j];
            cnt++;
            j++;
        }

        // Return median
        if (n % 2 == 1) return (double) ind2el;
        return ((double) (ind1el + ind2el)) / 2.0;
    }
    /*
    ✅ Approach:
       - Similar to merge step, but we stop once we find the median index.
       - No need to merge the whole array.

    ⏱️ Time Complexity: O(n1 + n2) in worst case
    🛑 Space Complexity: O(1) (no extra merged array)
    */


    // ---------------------- Approach 3: Binary Search (Optimized) ----------------------
    public static double median3(int[] a, int[] b) {
        int n1 = a.length, n2 = b.length;
        // Ensure 'a' is the smaller array for binary search
        if (n1 > n2) return median3(b, a);

        int n = n1 + n2; // total length
        int left = (n1 + n2 + 1) / 2; // length of left partition

        int low = 0, high = n1;
        while (low <= high) {
            int mid1 = (low + high) / 2; // partition in a
            int mid2 = left - mid1;      // partition in b

            // Left & right values from both arrays
            int l1 = (mid1 > 0) ? a[mid1 - 1] : Integer.MIN_VALUE;
            int l2 = (mid2 > 0) ? b[mid2 - 1] : Integer.MIN_VALUE;
            int r1 = (mid1 < n1) ? a[mid1] : Integer.MAX_VALUE;
            int r2 = (mid2 < n2) ? b[mid2] : Integer.MAX_VALUE;

            // Check valid partition
            if (l1 <= r2 && l2 <= r1) {
                if (n % 2 == 1) return Math.max(l1, l2); // odd → max of left
                else return ((double) (Math.max(l1, l2) + Math.min(r1, r2))) / 2.0; // even → avg
            } else if (l1 > r2) high = mid1 - 1; // move left
            else low = mid1 + 1; // move right
        }
        return 0; // dummy return
    }
    /*
    ✅ Approach:
       - Use binary search on the smaller array to partition both arrays.
       - Ensure left half ≤ right half for valid partition.
       - Then compute median from boundary values.

    ⏱️ Time Complexity: O(log(min(n1, n2)))
    🛑 Space Complexity: O(1)
    */


    public static void main(String[] args) {
        int[] a = {1, 4, 7, 10, 12};
        int[] b = {2, 3, 6, 15};
        System.out.println("Median using merge full array: " + median(a, b));
        System.out.println("Median using merge until median: " + median2(a, b));
        System.out.println("Median using binary search: " + median3(a, b));
    }
}

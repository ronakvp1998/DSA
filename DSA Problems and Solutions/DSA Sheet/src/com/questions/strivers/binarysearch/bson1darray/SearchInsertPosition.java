package com.questions.strivers.binarysearch.bson1darray;

/*
Problem Statement:
You are given a sorted array arr of distinct values and a target value x.
You need to search for the index of the target value in the array.

- If the value is present in the array, return its index.
- Otherwise, return the index where it would be inserted in sorted order.

Pre-requisite:
- Understanding of Lower Bound & Binary Search.

Example 1:
arr[] = {1, 2, 4, 7}, x = 6 → Output: 3
Explanation: 6 would be inserted at index 3 to maintain sorted order.

Example 2:
arr[] = {1, 2, 4, 7}, x = 2 → Output: 1
Explanation: 2 is already present at index 1.

Approach:
------------
We use a **modified Binary Search** to find the "Lower Bound":
1. Initialize `low = 0`, `high = n - 1`.
2. Perform binary search:
   - If `arr[mid] >= k`, record `mid` as a potential answer (since it could be the insertion point)
     and move left (`high = mid - 1`).
   - Else move right (`low = mid + 1`).
3. Return the `ans` which will store the index where `k` exists or should be inserted.

This approach works for both:
- When the element exists (returns its index).
- When the element doesn't exist (returns correct insertion index).

Time Complexity:
----------------
O(log n) → because we divide the search space by 2 each time (binary search).

Space Complexity:
-----------------
O(1) → only uses a few extra variables, no extra data structures.
*/

public class SearchInsertPosition {
    public static void main(String[] args) {
        int arr[] = {0, 5, 5, 5, 9, 12}; // Sorted array
        int k = 6; // Target element
        System.out.println(lowerBound(arr, arr.length, k)); // Expected Output: 4
    }

    /**
     * Finds the lower bound index of the target k in the array.
     * @param arr Sorted array
     * @param n   Length of array
     * @param k   Target element
     * @return Index where k exists or should be inserted
     */
    private static int lowerBound(int arr[], int n, int k) {
        int low = 0, high = n - 1;
        int ans = n; // Default to n (insert at the end if greater than all elements)

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents integer overflow

            if (arr[mid] >= k) {
                ans = mid;        // Potential insertion index found
                high = mid - 1;   // Look for a smaller index on the left
            } else {
                low = mid + 1;    // Move right
            }
        }
        return ans;
    }
}

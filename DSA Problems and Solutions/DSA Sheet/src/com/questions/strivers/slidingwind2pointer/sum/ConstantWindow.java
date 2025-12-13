package com.questions.strivers.slidingwind2pointer.sum;

/*
Problem Statement:
------------------
Given an array of integers `arr[]` and an integer `k`, find the maximum sum
of any subarray of length exactly `k`. The subarray must consist of `k`
consecutive elements.

Example:
---------
Input: arr = {-1, 2, 3, 3, 4, 5, -1}, k = 4
Output: 15
Explanation:
- Possible windows of size 4 and their sums:
  {-1, 2, 3, 3} → 7
  {2, 3, 3, 4} → 12
  {3, 3, 4, 5} → 15  ✅ (maximum sum)
  {3, 4, 5, -1} → 11
So, the maximum sum is 15.
*/

public class ConstantWindow {
    public static void main(String[] args) {
        int arr[] = {-1, 2, 3, 3, 4, 5, -1};
        int k = 4;
        System.out.println(constantWindow(arr, k)); // Expected output: 15
    }

    /*
    Function: constantWindow
    ------------------------
    Uses the Sliding Window technique to calculate the maximum sum of
    k consecutive elements in the array.

    Approach:
    1. Compute the sum of the first `k` elements (initial window).
    2. Slide the window across the array:
       - Subtract the element going out (left side).
       - Add the new element coming in (right side).
    3. Update the maximum sum during each shift.
    4. Return the maximum sum after scanning all windows.

    Time Complexity:
    ----------------
    O(n) → We traverse the array once while sliding the window.

    Space Complexity:
    -----------------
    O(1) → Only a few integer variables are used, no extra space required.
    */
    private static int constantWindow(int arr[], int k) {
        int n = arr.length;
        int maxSum = Integer.MIN_VALUE;
        int l = 0, r = k - 1;
        int sum = 0;

        // Step 1: Calculate sum of first window (first k elements)
        for (int i = 0; i <= r; i++) {
            sum += arr[i];
        }
        maxSum = sum;

        // Step 2: Slide the window until end of array
        while (r < n - 1) {
            // Remove the element going out of the window
            sum -= arr[l];
            l++;

            // Add the new element entering the window
            r++;
            sum += arr[r];

            // Update maximum sum
            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }
}

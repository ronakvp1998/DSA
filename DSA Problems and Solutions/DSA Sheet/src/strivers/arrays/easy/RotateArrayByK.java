package com.questions.strivers.arrays.easy;

/*
Problem: Rotate an array of size n by k steps (either to the left or right).

Example:
--------
Input:  arr = [1, 2, 3, 4, 5, 6, 7], k = 2
Right Rotation Output: [6, 7, 1, 2, 3, 4, 5]
Left Rotation Output:  [3, 4, 5, 6, 7, 1, 2]
*/

public class RotateArrayByK {

    // ----------------------------------------------------------------
    // Approach 1: Extra Space Rotation (Right)
    // ----------------------------------------------------------------
    /*
    Logic:
    - Store the last k elements in a temporary array.
    - Shift the first n-k elements to the right.
    - Copy the stored k elements to the beginning.
    */
    private static void rotateToRightExtraSpace(int[] arr, int n, int k) {
        if (n == 0) return;
        k = k % n; // handle k > n

        int[] temp = new int[k];
        // Step 1: Copy last k elements
        for (int i = n - k; i < n; i++) {
            temp[i - (n - k)] = arr[i];
        }
        // Step 2: Shift the remaining n-k elements to the right
        for (int i = n - k - 1; i >= 0; i--) {
            arr[i + k] = arr[i];
        }
        // Step 3: Copy back temp elements to the start
        for (int i = 0; i < k; i++) {
            arr[i] = temp[i];
        }
    }
    /*
     🕒 Time Complexity: O(n)
     🧠 Space Complexity: O(k)
    */

    // ----------------------------------------------------------------
    // Approach 1: Extra Space Rotation (Left)
    // ----------------------------------------------------------------
    /*
    Logic:
    - Store the first k elements in a temporary array.
    - Shift the remaining n-k elements to the left.
    - Copy the stored k elements to the end.
    */
    private static void rotateToLeftExtraSpace(int[] arr, int n, int k) {
        if (n == 0) return;
        k = k % n;

        int[] temp = new int[k];
        // Step 1: Copy first k elements
        for (int i = 0; i < k; i++) {
            temp[i] = arr[i];
        }
        // Step 2: Shift remaining elements to the left
        for (int i = 0; i < n - k; i++) {
            arr[i] = arr[i + k];
        }
        // Step 3: Copy temp elements to the end
        for (int i = n - k; i < n; i++) {
            arr[i] = temp[i - (n - k)];
        }
    }
    /*
     🕒 Time Complexity: O(n)
     🧠 Space Complexity: O(k)
    */

    // ----------------------------------------------------------------
    // Helper Method: Reverse an array section
    // ----------------------------------------------------------------
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    // ----------------------------------------------------------------
    // Approach 2: Reverse Method (Right Rotation)
    // ----------------------------------------------------------------
    /*
    Logic:
    1. Reverse first n-k elements.
    2. Reverse last k elements.
    3. Reverse the whole array.
    */
    private static void rotateRightReverse(int[] arr, int n, int k) {
        k = k % n;
        reverse(arr, 0, n - k - 1);
        reverse(arr, n - k, n - 1);
        reverse(arr, 0, n - 1);
    }
    /*
     🕒 Time Complexity: O(n)
     🧠 Space Complexity: O(1)
     ✅ Most optimal in-place method
    */

    // ----------------------------------------------------------------
    // Approach 2: Reverse Method (Left Rotation)
    // ----------------------------------------------------------------
    /*
    Logic:
    1. Reverse first k elements.
    2. Reverse last n-k elements.
    3. Reverse the whole array.
    */
    private static void rotateLeftReverse(int[] arr, int n, int k) {
        k = k % n;
        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);
        reverse(arr, 0, n - 1);
    }
    /*
     🕒 Time Complexity: O(n)
     🧠 Space Complexity: O(1)
     ✅ Most optimal in-place method
    */

    public static void main(String[] args) {
        int n = 7;
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7};
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};

        int k = 2;

        // Example: Left rotation using extra space
        rotateToLeftExtraSpace(arr1, n, k);
        System.out.println("After rotating left (extra space):");
        for (int num : arr1) System.out.print(num + " ");
        System.out.println();

        // Example: Right rotation using reverse method
        rotateRightReverse(arr2, n, k);
        System.out.println("After rotating right (reverse method):");
        for (int num : arr2) System.out.print(num + " ");
    }
}

package com.questions.strivers.arrays.easy;
//https://takeuforward.org/data-structure/find-the-largest-element-in-an-array/
//Problem Statement: Given an array, we have to find the largest element in the array
public class LargestElementInArray {

    public static void main(String[] args) {
        int arr[] = {2, 5, 1, 3, 0}; // Input array to find the largest element

        // ✅ Calling iterative function to find the largest element
        System.out.println(largestElement1(arr));

        // ✅ Calling corrected recursive function
        System.out.println(largestElement(arr, 1, arr[0]));
    }

    /**
     * ✅ Iterative approach to find the largest element in the array
     *
     * Time Complexity: O(n)
     *    → We traverse the array once, comparing each element.
     *
     * Space Complexity: O(1)
     *    → We use only a single variable 'max' for storing the largest value.
     */
    public static int largestElement1(int arr[]) {
        int max = arr[0]; // Initialize max with the first element

        // Loop through all elements in the array
        for (int i = 0; i < arr.length; i++) {
            // If current element is greater than max, update max
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // Return the largest element found
        return max;
    }

    /**
     * ✅ Recursive approach to find the largest element in the array
     *
     * Time Complexity: O(n)
     *    → Each recursive call processes one element, total n calls.
     *
     * Space Complexity: O(n)
     *    → Due to the recursion stack (n function calls for n elements).
     *
     * @param arr - Input array
     * @param i - Current index being processed
     * @param longest - Current largest element found so far
     * @return - The largest element in the array
     */
    public static int largestElement(int arr[], int i, int longest) {
        // Base case: If we've reached the end of the array, return the largest found
        if (i == arr.length) {
            return longest;
        }

        // Recursive call: Check the next element and update the longest if needed
        return largestElement(arr, i + 1, Math.max(arr[i], longest));
    }
}

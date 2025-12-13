package com.questions.strivers.arrays.easy;
//https://takeuforward.org/data-structure/find-second-smallest-and-second-largest-element-in-an-array/
//Problem Statement: Given an array, find the second smallest and second largest element in the array.
// Print ‘-1’ in the event that either of them doesn’t exist.

public class SecondLargSmallElement {
    public static void main(String[] args) {
        // Sample input array
        int[] arr = {1, 2, 4, 7, 7, 5};

        // Output the second largest element in the array
        System.out.println(secondLargest(arr));

        // Output the second smallest element in the array
        System.out.println(secondSmallest(arr));
    }

    // ✅ Function to find the second smallest element in an array
    private static int secondSmallest(int arr[]){
        // Initialize smallest and second smallest with maximum possible integer value
        int small = Integer.MAX_VALUE;
        int secSmall = Integer.MAX_VALUE;

        // Traverse the entire array
        for(int i = 0; i < arr.length; i++) {
            // If current element is smaller than smallest found so far
            if(arr[i] < small){
                // Update second smallest to previous smallest
                secSmall = small;
                // Update smallest
                small = arr[i];
            }
            // If current element is greater than smallest but smaller than second smallest
            else if (arr[i] < secSmall && arr[i] != small) {
                secSmall = arr[i];
            }
        }
        // Return the second smallest element
        return secSmall;
    }

    /*
     🕒 Time Complexity: O(n)
     → We traverse the array once to find the second smallest.
     🧠 Space Complexity: O(1)
     → Only constant extra variables are used (small, secSmall).
    */

    // ✅ Function to find the second largest element in an array
    private static int secondLargest(int arr[]){
        // Initialize largest and second largest with minimum possible integer value
        int large = Integer.MIN_VALUE;
        int secLarge = Integer.MIN_VALUE;

        // Traverse the entire array
        for(int i = 0; i < arr.length; i++) {
            // If current element is greater than largest found so far
            if(arr[i] > large){
                // Update second largest to previous largest
                secLarge = large;
                // Update largest
                large = arr[i];
            }
            // If current element is smaller than largest but greater than second largest
            else if (arr[i] > secLarge && arr[i] != large) {
                secLarge = arr[i];
            }
        }
        // Return the second largest element
        return secLarge;
    }

    /*
     🕒 Time Complexity: O(n)
     → We traverse the array once to find the second largest.
     🧠 Space Complexity: O(1)
     → Only constant extra variables are used (large, secLarge).
    */
}

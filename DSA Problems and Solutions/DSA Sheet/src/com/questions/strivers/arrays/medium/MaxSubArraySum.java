package com.questions.strivers.arrays.medium;

/**
 * Problem Statement:
 * Given an integer array `arr`, find the contiguous subarray (containing at least one number)
 * which has the largest sum and return its sum. Also print the subarray.
 *
 * Leetcode: https://leetcode.com/problems/maximum-subarray/
 */

public class MaxSubArraySum {

    public static void main(String[] args) {
        int[] arr = {-2, -3, 4, -1, -2, 1, 5, -2};
        int n = arr.length;

        System.out.println("Brute Force: " + maxSubarraySum1(arr, n));
        System.out.println("Better Approach: " + maxSubarraySum2(arr, n));
        System.out.println("Kadane’s Optimized: " + maxSubarraySum3(arr, n));
        System.out.println("Kadane’s with Subarray Print: " + maxSubarraySum4(arr, n));
        System.out.println("Kadane’s with Negative Handling: " + maxSubarraySum5(arr));
        System.out.println("Kadane’s Full (Print + Negatives): " + maxSubarraySum6(arr));
    }

    // Approach 1: Brute Force - Try all subarrays using 3 loops
    // Time: O(N^3), Space: O(1)
    public static int maxSubarraySum1(int[] arr, int n) {
        int maxi = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = 0;

                for (int k = i; k <= j; k++) {
                    sum += arr[k]; // Calculate sum of subarray arr[i...j]
                }

                maxi = Math.max(maxi, sum); // Update max sum
            }
        }

        return maxi;
    }

    // Approach 2: Better Approach - Remove inner loop by maintaining sum
    // Time: O(N^2), Space: O(1)
    public static int maxSubarraySum2(int[] arr, int n) {
        int maxi = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int sum = 0;

            for (int j = i; j < n; j++) {
                sum += arr[j]; // Add element to sum of arr[i...j]
                maxi = Math.max(maxi, sum); // Track max
            }
        }

        return maxi;
    }

    // Approach 3: Kadane’s Algorithm (Optimized) - No printing
    // Time: O(N), Space: O(1)
    public static long maxSubarraySum3(int[] arr, int n) {
        long maxi = Long.MIN_VALUE;
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += arr[i]; // Add current element
            maxi = Math.max(maxi, sum); // Update max
            if (sum < 0) sum = 0; // Reset if sum goes negative
        }

        return maxi;
    }

    // Approach 4: Kadane’s with Subarray Printing
    // Time: O(N), Space: O(1)
    public static long maxSubarraySum4(int[] arr, int n) {
        long maxi = Long.MIN_VALUE;
        long sum = 0;

        int start = 0;
        int ansStart = -1, ansEnd = -1;

        for (int i = 0; i < n; i++) {
            if (sum == 0) start = i; // New start for subarray

            sum += arr[i];

            if (sum > maxi) {
                maxi = sum;
                ansStart = start;
                ansEnd = i;
            }

            if (sum < 0) sum = 0;
        }

        // Print subarray with max sum
        System.out.print("Max Sum Subarray (Kadane + Print): ");
        for (int i = ansStart; i <= ansEnd; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        return maxi;
    }

    // Approach 5: Kadane’s with Handling All-Negatives (no printing)
    // Time: O(N), Space: O(1)
    public static int maxSubarraySum5(int[] arr) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            if (sum < 0) sum = 0;

            maxSum = Math.max(maxSum, sum);
        }

        // If all numbers are negative, maxSum would be 0. We pick max element.
        if (maxSum == 0) {
            int max = Integer.MIN_VALUE;
            for (int num : arr) {
                max = Math.max(max, num);
            }
            maxSum = max;
        }

        return maxSum;
    }

    // Approach 6: Kadane’s Full - Print + Handle all-negative case
    // Time: O(N), Space: O(1)
    public static int maxSubarraySum6(int[] arr) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        int start = 0, ansStart = -1, ansEnd = -1;

        for (int i = 0; i < arr.length; i++) {
            if (sum == 0) start = i; // Start of new subarray

            sum += arr[i];

            if (sum < 0) {
                sum = 0;
            }

            if (maxSum < sum) {
                maxSum = sum;
                ansStart = start;
                ansEnd = i;
            }
        }

        // Handle negative-only case
        if (maxSum == 0) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                    ansStart = i;
                    ansEnd = i;
                }
            }
            maxSum = max;
        }

        // Print the subarray
        System.out.print("Max Sum Subarray (Kadane + Print + Negative): ");
        for (int i = ansStart; i <= ansEnd; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        return maxSum;
    }
}

//Method	                    Name	        Time Complexity	    Space Complexity	Prints Subarray	Handles All-Negatives
//Brute Force	                maxSubarraySum1	O(N³)	            O(1)	            ❌	            ✅
//Better	                    maxSubarraySum2	O(N²)	            O(1)	            ❌	            ✅
//Kadane	                    maxSubarraySum3	O(N)	            O(1)	            ❌	            ❌
//Kadane + Print	            maxSubarraySum4	O(N)	            O(1)	            ✅	            ❌
//Kadane + Negatives	        maxSubarraySum5	O(N)	            O(1)	            ❌	            ✅
//Kadane + Print + Negatives	maxSubarraySum6	O(N)	            O(1)	            ✅	            ✅
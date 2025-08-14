package com.questions.strivers.binarysearch.bsonanswers;
/*
* Problem Statement: You are given an array of integers 'arr' and an integer i.e. a threshold value 'limit'. Your task is to find the smallest positive integer divisor, such that upon dividing all the elements of the given array by it, the sum of the division's result is less than or equal to the given threshold value.

Examples

Example 1:
Input Format: N = 5, arr[] = {1,2,3,4,5}, limit = 8
Result: 3
Explanation: We can get a sum of 15(1 + 2 + 3 + 4 + 5) if we choose 1 as a divisor.
The sum is 9(1 + 1 + 2 + 2 + 3)  if we choose 2 as a divisor. Upon dividing all the elements of the array by 3, we get 1,1,1,2,2 respectively. Now, their sum is equal to 7 <= 8 i.e. the threshold value. So, 3 is the minimum possible answer.

Example 2:
Input Format: N = 4, arr[] = {8,4,2,3}, limit = 10
Result: 2
Explanation: If we choose 1, we get 17 as the sum. If we choose 2, we get 9(4+2+1+2) <= 10 as the answer. So, 2 is the answer.



Point to remember:


While dividing the array elements with a chosen number, we will always take the ceiling value. And then we will consider their summation. For example, 3 / 2 = 2.

Observation:


Minimum possible divisor: We can easily consider 1 as the minimum divisor as it is the smallest positive integer.
Maximum possible divisor: If we observe, we can conclude the maximum element in the array i.e. max(arr[]) is the maximum possible divisor. Any number > max(arr[]), will give the exact same result as max(arr[]) does. This divisor will generate the minimum possible result i.e. n(1 for each element), where n = size of the array.

With these observations, we can surely say that our answer will lie in the range
[1, max(arr[])].


* */
public class SmallestDivisor {

    // Brute-force method to find the smallest divisor
    public static int smallestDivisor(int[] arr, int limit) {
        int n = arr.length;

        // Step 1: Find the maximum value in the array
        int maxi = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            maxi = Math.max(maxi, arr[i]);
        }

        // Step 2: Try every divisor from 1 to max(arr)
        for (int d = 1; d <= maxi; d++) {
            int sum = 0; // Sum of ceiling values for current divisor
            for (int i = 0; i < n; i++) {
                // Math.ceil returns a double, so we cast back to int
                sum += Math.ceil((double)(arr[i]) / (double)(d));
            }

            // Step 3: If sum is within the limit, return current divisor
            if (sum <= limit)
                return d;
        }

        // If no valid divisor found (shouldn't happen as per constraints)
        return -1;
    }

    /**
     * Time Complexity (Brute-force): O(max(arr) * n)
     * - Try up to max(arr) divisors
     * - Each divisor does O(n) work
     *
     * Space Complexity: O(1)
     * - No extra space used beyond variables
     */


    // Helper function to compute the sum of ceilings for a given divisor
    public static int sumByD2(int[] arr, int div) {
        int n = arr.length;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            // Calculate ceil of division manually without using Math.ceil
            sum += (arr[i] + div - 1) / div;
        }

        return sum;
    }

    // Binary search optimized method to find the smallest divisor
    public static int smallestDivisor2(int[] arr, int limit) {
        int n = arr.length;

        // Edge case: if number of elements is greater than the limit,
        // it's impossible to keep the sum <= limit, since each element will contribute ≥1
        if (n > limit) return -1;

        // Step 1: Find the max value in the array
        int maxi = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            maxi = Math.max(maxi, arr[i]);
        }

        // Step 2: Binary search between 1 and max(arr)
        int low = 1, high = maxi;

        while (low <= high) {
            int mid = (low + high) / 2;

            // Step 3: Check if current divisor is valid
            if (sumByD2(arr, mid) <= limit) {
                // Try to find a smaller valid divisor
                high = mid - 1;
            } else {
                // Current divisor gives sum > limit, try bigger divisor
                low = mid + 1;
            }
        }

        // Step 4: Return the smallest valid divisor
        return low;
    }

    /**
     * Time Complexity (Binary Search): O(n * log(max(arr)))
     * - log(max(arr)) binary search steps
     * - Each step computes sum in O(n)
     *
     * Space Complexity: O(1)
     * - No extra space used
     */

    // Main function to test the solution
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5}; // Sample input array
        int limit = 8; // Threshold for sum

        int ans = smallestDivisor2(arr, limit);
        System.out.println("The minimum divisor is: " + ans); // Expected Output: 3
    }
}

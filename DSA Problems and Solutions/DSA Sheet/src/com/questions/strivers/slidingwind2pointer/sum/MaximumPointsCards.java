package com.questions.strivers.slidingwind2pointer.sum;

/*
Problem Statement: Maximum Points You Can Obtain from Cards

There are several cards arranged in a row, and each card has an associated number of points.
You can take exactly k cards from either the beginning or the end of the array.
Return the maximum score you can obtain.

Examples:
1. Input: cardPoints = [1,2,3,4,5,6,1], k = 3 → Output: 12
   Explanation: Best choice is taking [6,5,1] → sum = 12.

2. Input: cardPoints = [2,2,2], k = 2 → Output: 4
   Explanation: Any two cards = 2 + 2 = 4.

3. Input: cardPoints = [9,7,7,9,7,7,9], k = 7 → Output: 55
   Explanation: Must take all cards = total sum.
*/

public class MaximumPointsCards {

    public static void main(String[] args) {
        int arr[] = {6,2,3,4,7,2,1,7,1};
        int k = 4;

        // Two different implementations that solve the same problem
        System.out.println(maxSum1(arr, k)); // Approach 1
        System.out.println(maxSum2(arr, k)); // Approach 2
    }

    /**
     * Approach 1: Sliding Window Technique
     * -------------------------------------
     * 1. Start by taking the first k elements (all from the left).
     * 2. Then, gradually replace one element from the left side with one from the right side.
     * 3. Update the sum in each iteration and track the maximum.
     *
     * Example:
     * arr = [6,2,3,4,7,2,1,7,1], k=4
     * Step 1: Take [6,2,3,4] → sum = 15
     * Step 2: Replace 4 with last element 1 → sum = 12
     * Step 3: Replace 3 with 7 → sum = 16
     * Step 4: Replace 2 with 1 → sum = 15
     * Step 5: Replace 6 with 7 → sum = 21 (max)
     *
     * Time Complexity: O(k) → We check k possible splits.
     * Space Complexity: O(1) → Only variables used.
     */
    public static int maxSum1(int arr[], int k) {
        int maxSum = Integer.MIN_VALUE, sum = 0, l = 0, r = 0, n = arr.length;

        // Step 1: Take first k elements from the left
        for (int i = 0; i < k; i++) {
            sum = sum + arr[i];
        }
        maxSum = sum;

        // Step 2: Slide the window by replacing left elements with right elements
        l = k - 1;
        r = n - 1;
        while (l >= 0) {
            sum = sum - arr[l];  // Remove one element from left side
            sum = sum + arr[r];  // Add one element from right side
            l--;
            r--;
            maxSum = Math.max(sum, maxSum);
        }
        return maxSum;
    }

    /**
     * Approach 2: Two-Pointer with Prefix-Suffix Sum
     * ----------------------------------------------
     * 1. First, take the sum of the first k elements (all left).
     * 2. Then, step-by-step, remove one element from the left sum and add one element from the right sum.
     * 3. Keep track of the maximum.
     *
     * Example:
     * arr = [6,2,3,4,7,2,1,7,1], k=4
     * LeftSum = 6+2+3+4 = 15
     * Now swap gradually:
     *   Take 3 from left, add 1 from right → sum = 13
     *   Take 2 from left, add 7 from right → sum = 18
     *   Take 6 from left, add 1 from right → sum = 15
     *   Max = 18
     *
     * Time Complexity: O(k)
     * Space Complexity: O(1)
     */
    public static int maxSum2(int arr[], int k) {
        int maxSum = Integer.MIN_VALUE, lsum = 0, rsum = 0, n = arr.length;

        // Step 1: Sum of first k elements (all from left)
        for (int i = 0; i < k; i++) {
            lsum = lsum + arr[i];
        }
        maxSum = lsum;

        // Step 2: Swap left elements with right elements one by one
        int rightIndex = n - 1;
        for (int i = k - 1; i >= 0; i--) {
            lsum = lsum - arr[i];          // Remove from left
            rsum = rsum + arr[rightIndex]; // Add from right
            rightIndex = rightIndex - 1;
            maxSum = Math.max(maxSum, lsum + rsum);
        }
        return maxSum;
    }
}

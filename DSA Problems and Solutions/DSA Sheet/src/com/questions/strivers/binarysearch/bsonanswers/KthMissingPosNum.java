package com.questions.strivers.binarysearch.bsonanswers;

/*
Problem Statement:
You are given a strictly increasing array ‘vec’ and a positive integer 'k'.
Find the 'kth' positive integer missing from 'vec'.

---

Examples:

Example 1:
Input: vec[]={4,7,9,10}, k = 1
Missing numbers: 1,2,3,5,6,8,11,...
Output: 1 (1st missing = 1)

Example 2:
Input: vec[]={4,7,9,10}, k = 4
Missing numbers: 1,2,3,5,6,8,11,...
Output: 5 (4th missing = 5)

---

Approaches:

1. Linear Scan (missingK):
   - Iterate through array elements.
   - If vec[i] <= k → increment k (shifting the missing count forward).
   - Otherwise stop and return k.
   - Time: O(n)
   - Space: O(1)

2. Binary Search (missingK2):
   - Observation:
       - For index mid, number of missing elements before vec[mid] = vec[mid] - (mid + 1).
       - Compare this count with k to decide search direction.
   - If missing < k → move right.
   - Else move left.
   - Finally, answer = k + high + 1.
   - Time: O(log n)
   - Space: O(1)

---
*/

public class KthMissingPosNum {

    /**
     * Linear Approach
     * Iterates over array to find kth missing number.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static int missingK(int[] vec, int n, int k) {
        for (int i = 0; i < n; i++) {
            if (vec[i] <= k) {
                // If current element is <= k, then kth missing shifts ahead
                k++;
            } else {
                // If vec[i] > k, kth missing lies before vec[i]
                break;
            }
        }
        return k;
    }

    /**
     * Optimized Approach - Binary Search
     * Uses missing count logic to narrow search.
     *
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    private static int missingK2(int[] vec, int n, int k) {
        int low = 0, high = n - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            // Missing numbers until index mid:
            // vec[mid] - (mid+1) → because ideal array [1...n]
            int missing = vec[mid] - (mid + 1);

            if (missing < k) {
                // kth missing lies to the right
                low = mid + 1;
            } else {
                // kth missing lies to the left
                high = mid - 1;
            }
        }

        // Answer = k + (high+1)
        // Because "high" represents index of last element
        // before which missing numbers are < k
        return k + high + 1;
    }

    public static void main(String[] args) {
        int[] vec = {4, 7, 9, 10};
        int n = 4, k = 4;

        // Linear method
        int ans1 = missingK(vec, n, k);
        System.out.println("The missing number (Linear) is: " + ans1);

        // Binary Search method
        int ans2 = missingK2(vec, n, k);
        System.out.println("The missing number (Binary Search) is: " + ans2);
    }
}

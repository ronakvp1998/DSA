package com.questions.strivers.recursionbacktracking.medium;

/*
Problem Statement:
------------------
Given an array of integers and a target sum, count the number of subsequences
whose elements sum up exactly to the given target.

Example:
--------
Input: arr = [1, 2, 1], sum = 2
Output: 2
Explanation: The subsequences that sum to 2 are:
    [1, 1], [2]

Approach:
---------
We use recursion to explore all possible subsequences.
- At each index, we have 2 choices:
    1. Include the current element in the subsequence.
    2. Exclude the current element from the subsequence.
- We keep track of the running sum (res).
- Once we reach the end of the array (base case), we check
  if the sum matches the target.

Time Complexity:
----------------
O(2^n)
- At each index, we make two recursive calls (include or exclude).
- For an array of length n, total subsequences = 2^n.

Space Complexity:
-----------------
O(n)
- Recursion stack can go as deep as the length of the array.
*/

public class CountSubsequences {
    public static void main(String[] args) {
        int arr[] = {1, 2, 1};
        int sum = 2;
        // Prints the number of subsequences that sum to target
        System.out.println(countSubSeq(0, arr, arr.length, sum, 0));
    }

    public static int countSubSeq(int index, int arr[], int n, int sum, int res) {
        // Base case: If we have reached the end of the array
        if (index == n) {
            // If current sum matches target, count this subsequence
            if (res == sum) {
                return 1;
            } else {
                return 0;
            }
        }

        // Include current element in sum
        res = res + arr[index];
        int left = countSubSeq(index + 1, arr, n, sum, res);

        // Backtrack: remove current element and move ahead
        res = res - arr[index];
        int right = countSubSeq(index + 1, arr, n, sum, res);

        // Total count = subsequences including current element + excluding it
        return left + right;
    }
}

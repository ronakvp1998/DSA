package com.questions.strivers.recursionbacktracking.hard;

import java.util.*;

/*
Problem Statement:
------------------
Given n and k, return the k-th permutation sequence of numbers [1, 2, 3, ..., n].

Example:
Input: n = 3, k = 3
Output: "213"
Explanation: The ordered permutations are:
             123, 132, 213, 231, 312, 321
             The 3rd is "213".
*/

public class KthPermutationSequence {

    // Function to return the kth permutation
    public static String getPermutation(int n, int k) {
        // Step 1: Prepare the list of numbers [1, 2, 3, ..., n]
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        // Step 2: Pre-compute factorial values for (n-1)!
        int fact = 1;
        for (int i = 1; i < n; i++) {
            fact *= i;
        }

        // Step 3: Convert k into zero-based index
        k = k - 1;

        StringBuilder ans = new StringBuilder();

        // Step 4: Construct permutation by choosing elements one by one
        while (true) {
            // Find the index of the current number
            int index = k / fact;
            ans.append(numbers.get(index));

            // Remove chosen number from the list
            numbers.remove(index);

            if (numbers.isEmpty()) break;

            // Update k for the remaining digits
            k = k % fact;

            // Update factorial for the next round
            fact = fact / numbers.size();
        }

        return ans.toString();
    }

    // Driver code to test
    public static void main(String[] args) {
        int n = 4, k = 9;
        System.out.println("The " + k + "th permutation of 1.." + n + " is: " + getPermutation(n, k));
    }
}

/*
----------------------
TIME COMPLEXITY:
----------------------
- Precomputing factorial: O(n)
- Each iteration removes one element from the list: O(n) (because of list.remove(index))
- Total = O(n^2) in worst case.

----------------------
SPACE COMPLEXITY:
----------------------
- List of numbers: O(n)
- Output string: O(n)
- Overall: O(n)
*/

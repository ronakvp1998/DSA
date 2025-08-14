package com.questions.strivers.arrays.medium;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/*
* Problem Statement: Given an array Arr[] of integers,
* rearrange the numbers of the given array into the lexicographically next greater permutation of numbers.

If such an arrangement is not possible, it must rearrange to the lowest possible order (i.e., sorted in ascending order).

Example 1 :

Input format: Arr[] = {1,3,2}
Output: Arr[] = {2,1,3}
Explanation: All permutations of {1,2,3} are {{1,2,3} , {1,3,2}, {2,13} , {2,3,1} , {3,1,2} , {3,2,1}}.
* So, the next permutation just after {1,3,2} is {2,1,3}.
Example 2:

Input format: Arr[] = {3,2,1}
Output: Arr[] = {1,2,3}
Explanation: As we see all permutations of {1,2,3}, we find {3,2,1} at the last position.
* So, we have to return the topmost permutation.
*  */
public class NextPermutation {

    /**
     * This function modifies the input list to its next lexicographically greater permutation.
     * If no such permutation exists, it rearranges the list to the lowest possible order (ascending).
     *
     * @param A List of integers representing the permutation
     * @return The next permutation of the list
     */
    public static List<Integer> nextGreaterPermutation(List<Integer> A) {
        int n = A.size(); // Get the size of the list

        // Step 1: Find the "break point" where the order decreases from the end.
        int ind = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (A.get(i) < A.get(i + 1)) {
                // Found the first index from the right where A[i] < A[i+1]
                ind = i;
                break;
            }
        }

        // Step 2: If no such break point exists, the array is in descending order
        if (ind == -1) {
            // The current permutation is the largest. Reverse it to get the smallest.
            Collections.reverse(A);
            return A;
        }

        // Step 3: Find the smallest element greater than A[ind] to the right of it
        for (int i = n - 1; i > ind; i--) {
            if (A.get(i) > A.get(ind)) {
                // Swap A[i] and A[ind]
                int tmp = A.get(i);
                A.set(i, A.get(ind));
                A.set(ind, tmp);
                break;
            }
        }

        // Step 4: Reverse the subarray to the right of the break point to get the next permutation
        List<Integer> sublist = A.subList(ind + 1, n);
        Collections.reverse(sublist);

        return A;
    }

    public static void main(String args[]) {
        List<Integer> A = Arrays.asList(new Integer[]{2, 1, 5, 4, 3, 0, 0});
        List<Integer> ans = nextGreaterPermutation(A);

        System.out.print("The next permutation is: [");
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i) + (i == ans.size() - 1 ? "" : ", "));
        }
        System.out.println("]");
    }
}

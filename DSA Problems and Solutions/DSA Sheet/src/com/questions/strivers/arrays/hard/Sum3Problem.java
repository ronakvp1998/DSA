package com.questions.strivers.arrays.hard;

import java.util.*;
/*https://takeuforward.org/data-structure/3-sum-find-triplets-that-add-up-to-a-zero/
//Problem Statement: Given an array of N integers, your task is to find unique triplets that add up to give a sum of zero.
In short, you need to return an array of all the unique triplets [arr[a], arr[b], arr[c]] such that i!=j, j!=k, k!=i,
and their sum is equal to zero.
Example 1:

        Input: nums = [-1,0,1,2,-1,-4]

        Output: [[-1,-1,2],[-1,0,1]]

        Explanation: Out of all possible unique triplets possible, [-1,-1,2] and [-1,0,1]
        satisfy the condition of summing up to zero with i!=j!=k

        Example 2:

        Input: nums=[-1,0,1,0]
        Output: Output: [[-1,0,1],[-1,1,0]]
        Explanation: Out of all possible unique triplets possible, [-1,0,1] and [-1,1,0]
        satisfy the condition of summing up to zero with i!=j!=k */

public class Sum3Problem {

    /*
    🔸 Approach 1: Brute Force (3 Nested Loops)
    --------------------------------------------
    - Try every triplet (i, j, k) using 3 nested loops.
    - Check if arr[i] + arr[j] + arr[k] == 0.
    - Store each valid triplet in a Set to avoid duplicates (after sorting).

    Time Complexity: O(n^3)
    Space Complexity: O(no. of unique triplets) → up to O(n^2)
    */
    private static List<List<Integer>> triplet(int n, int[] arr) {
        Set<List<Integer>> st = new HashSet<>(); // Set to store unique triplets

        // Try all combinations of triplets
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    // Check if the sum is 0
                    if (arr[i] + arr[j] + arr[k] == 0) {
                        List<Integer> temp = Arrays.asList(arr[i], arr[j], arr[k]);
                        temp.sort(null); // Sort to ensure uniqueness in the set
                        st.add(temp);    // Add to set
                    }
                }
            }
        }

        // Convert the set to list before returning
        List<List<Integer>> ans = new ArrayList<>(st);
        return ans;
    }

    /*
    🔸 Approach 2: Better using HashSet (Two loops + Hashing)
    ---------------------------------------------------------
    - Fix the first element (i), and use HashSet to find the 3rd element that makes sum = 0.
    - For each pair (i, j), check if -(arr[i] + arr[j]) exists in the set.

    Time Complexity: O(n^2)
    Space Complexity: O(n) for inner HashSet + O(unique triplets)
    */
    private static List<List<Integer>> triplet2(int n, int[] arr) {
        Set<List<Integer>> st = new HashSet<>();

        // Fix one element and use hashing to find the rest two
        for (int i = 0; i < n; i++) {
            // we will put only i+1 to j-1 elements in this hashset to avoid duplicates
            Set<Integer> hashset = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int third = -(arr[i] + arr[j]); // target for the third number

                // If target exists in set, we found a triplet
                if (hashset.contains(third)) {
                    List<Integer> temp = Arrays.asList(arr[i], arr[j], third);
                    temp.sort(null); // Sort to remove duplicates
                    st.add(temp);
                }

                // Add current element to hashset
                hashset.add(arr[j]);
            }
        }

        // Convert the set to list and return
        return new ArrayList<>(st);
    }

    /*
    🔸 Approach 3: Optimal using Two Pointers + Sorting
    ----------------------------------------------------
    - Sort the array first.
    - For each element, fix arr[i], then use 2 pointers (j, k) to find two elements such that sum = 0 - arr[i].

    Time Complexity: O(n^2)
    Space Complexity: O(1) (excluding result list)
    */
    private static List<List<Integer>> triplet3(int n, int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(arr); // Sort the array to use 2-pointer approach

        // Iterate through the array
        for (int i = 0; i < n; i++) {
            // Skip duplicate elements
            if (i != 0 && arr[i] == arr[i - 1]) continue;

            int j = i + 1;     // Left pointer
            int k = n - 1;     // Right pointer

            // While left < right
            while (j < k) {
                int sum = arr[i] + arr[j] + arr[k];

                if (sum < 0) {
                    j++; // Need a larger number
                } else if (sum > 0) {
                    k--; // Need a smaller number
                } else {
                    // Found a valid triplet
                    List<Integer> temp = Arrays.asList(arr[i], arr[j], arr[k]);
                    ans.add(temp);
                    j++;
                    k--;
                    // Skip duplicates for j and k
                    // Increment the left pointer (j) to skip duplicate elements
                    while (j < k && arr[j] == arr[j - 1]) {
                        j++;
                    }

                    // Decrement the right pointer (k) to skip duplicate elements
                    while (j < k && arr[k] == arr[k + 1]) {
                        k--;
                    }
                }
            }
        }

        return ans; // Return list of triplets
    }

    // Main function to test the above methods
    public static void main(String[] args) {
        int[] arr = { -1, 0, 1, 2, -1, -4 }; // Example input
        int n = arr.length;

        // Test approach 1: brute-force
        List<List<Integer>> ans = triplet(n, arr); // You can try triplet2 or triplet3 here

        // Print all the triplets
        for (List<Integer> it : ans) {
            System.out.print("[");
            for (Integer i : it) {
                System.out.print(i + " ");
            }
            System.out.print("] ");
        }
        System.out.println();
    }
}

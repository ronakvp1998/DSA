package com.questions.strivers.arrays.easy;

/*
Problem Statement: Given two sorted arrays, arr1, and arr2 of size n and m.
Find the union of two sorted arrays.

The union of two arrays can be defined as the common and distinct elements in the two arrays.
NOTE: Elements in the union should be in ascending order.

Examples

Example 1:
Input:
n = 5,m = 5.
arr1[] = {1,2,3,4,5}
arr2[] = {2,3,4,4,5}
Output: {1,2,3,4,5}

Example 2:
Input:
n = 10,m = 7.
arr1[] = {1,2,3,4,5,6,7,8,9,10}
arr2[] = {2,3,4,4,5,11,12}
Output: {1,2,3,4,5,6,7,8,9,10,11,12}
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UnionTwoSortedArray {

    // Approach 1: Using HashMap to store frequencies
    // Time Complexity: O(n + m) → inserting each element into HashMap
    // Space Complexity: O(n + m) → HashMap stores unique elements from both arrays
    static ArrayList<Integer> FindUnion(int arr1[], int arr2[], int n, int m) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        ArrayList<Integer> Union = new ArrayList<>();

        // Store frequency of elements from arr1
        for (int i = 0; i < n; i++)
            freq.put(arr1[i], freq.getOrDefault(arr1[i], 0) + 1);

        // Store frequency of elements from arr2
        for (int i = 0; i < m; i++)
            freq.put(arr2[i], freq.getOrDefault(arr2[i], 0) + 1);

        // Add unique keys to result
        for (int it : freq.keySet())
            Union.add(it);

        return Union;
    }

    // Approach 2: Using HashSet for uniqueness
    // Time Complexity: O(n + m) → each insertion into HashSet is O(1) on average
    // Space Complexity: O(n + m) → HashSet stores unique elements
    static ArrayList<Integer> FindUnion2(int arr1[], int arr2[], int n, int m) {
        HashSet<Integer> s = new HashSet<>();
        ArrayList<Integer> Union = new ArrayList<>();

        // Add all elements from arr1
        for (int i = 0; i < n; i++)
            s.add(arr1[i]);

        // Add all elements from arr2
        for (int i = 0; i < m; i++)
            s.add(arr2[i]);

        // Convert set to list
        for (int it : s)
            Union.add(it);

        return Union;
    }

    // Approach 3: Optimal Two-Pointer Method (since arrays are sorted)
    // Time Complexity: O(n + m) → each element processed once
    // Space Complexity: O(1) extra space (excluding output list)
    static ArrayList<Integer> FindUnion3(int arr1[], int arr2[], int n, int m) {
        int i = 0, j = 0; // pointers
        ArrayList<Integer> Union = new ArrayList<>();

        // Traverse both arrays
        while (i < n && j < m) {
            if (arr1[i] <= arr2[j]) { // Case 1 & 2
                if (Union.size() == 0 || Union.get(Union.size() - 1) != arr1[i])
                    Union.add(arr1[i]);
                i++;
            } else { // Case 3
                if (Union.size() == 0 || Union.get(Union.size() - 1) != arr2[j])
                    Union.add(arr2[j]);
                j++;
            }
        }

        // Add remaining elements from arr1
        while (i < n) {
            if (Union.get(Union.size() - 1) != arr1[i])
                Union.add(arr1[i]);
            i++;
        }

        // Add remaining elements from arr2
        while (j < m) {
            if (Union.get(Union.size() - 1) != arr2[j])
                Union.add(arr2[j]);
            j++;
        }

        return Union;
    }

    public static void main(String args[]) {
        int n = 10, m = 7;
        int arr1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int arr2[] = {2, 3, 4, 4, 5, 11, 12};

        ArrayList<Integer> Union = FindUnion3(arr1, arr2, n, m); // using optimal method

        System.out.println("Union of arr1 and arr2 is ");
        for (int val : Union)
            System.out.print(val + " ");
    }
}

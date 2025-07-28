package com.questions.strivers.arrays.hard;

import java.util.ArrayList;

/*
🔗 Problem Statement:
Given an array of numbers, return the count of *reverse pairs*.
A reverse pair is a pair (i, j) such that:
    - i < j
    - arr[i] > 2 * arr[j]

🧠 Example:
Input: [1,3,2,3,1]
Output: 2
Explanation: (3,1) and (3,1)

---

✅ Approaches:
1. Brute Force: Check each pair (i,j)
   - Time Complexity: O(n^2)
   - Space Complexity: O(1)

2. Optimized: Merge Sort based Divide & Conquer
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)
*/

public class CountReversePairs {

    /**
     * ✅ Brute Force Approach
     * Count all (i,j) such that i<j and arr[i] > 2*arr[j]
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public static int countPairs(int[] a, int n) {
        int cnt = 0;
        // Check each pair
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i] > 2 * a[j])
                    cnt++;
            }
        }
        return cnt;
    }

    // Wrapper for brute force version
    public static int team(int[] skill, int n) {
        return countPairs(skill, n);
    }

    /**
     * 🔁 Helper Method: merge()
     * Merge two sorted halves [low...mid] and [mid+1...high] of array.
     * This step just performs the sorted merge (no counting).
     */
    private static void merge(int[] arr, int low, int mid, int high) {
        ArrayList<Integer> temp = new ArrayList<>();
        int left = low;
        int right = mid + 1;

        // Standard merge of two sorted arrays
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp.add(arr[left++]);
            } else {
                temp.add(arr[right++]);
            }
        }

        while (left <= mid) {
            temp.add(arr[left++]);
        }

        while (right <= high) {
            temp.add(arr[right++]);
        }

        // Copy back to original array
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
        }
    }

    /**
     * 🔁 Helper Method: countPairs during merge
     * Count valid reverse pairs (i < j and arr[i] > 2 * arr[j])
     * between two sorted halves
     */
    public static int countPairs(int[] arr, int low, int mid, int high) {
        int cnt = 0;
        int right = mid + 1;

        // For each element in left half
        for (int i = low; i <= mid; i++) {
            // Move right pointer as long as arr[i] > 2 * arr[right]
            while (right <= high && arr[i] > 2L * arr[right]) right++;
            cnt += (right - (mid + 1)); // count of such right elements
        }

        return cnt;
    }

    /**
     * ✅ Optimized Approach using Merge Sort
     * Time Complexity: O(n log n)
     * Space Complexity: O(n) for temp merge array
     */
    public static int mergeSort(int[] arr, int low, int high) {
        int cnt = 0;

        // Base case: single element
        if (low >= high) return cnt;

        int mid = (low + high) / 2;

        // Recursive calls for left and right halves
        cnt += mergeSort(arr, low, mid);
        cnt += mergeSort(arr, mid + 1, high);

        // Count valid reverse pairs across left and right
        cnt += countPairs(arr, low, mid, high);

        // Merge two sorted halves
        merge(arr, low, mid, high);

        return cnt;
    }

    // Wrapper for optimized method
    public static int team2(int[] skill, int n) {
        return mergeSort(skill, 0, n - 1);
    }

    public static void main(String[] args) {
        int[] a = {4, 1, 2, 3, 1};
        int n = 5;

        // ✅ Brute force method
        int cnt = team(a.clone(), n);
        System.out.println("The number of reverse pairs (Brute Force) is: " + cnt);

        // ✅ Optimized merge sort based method
        cnt = team2(a.clone(), n);
        System.out.println("The number of reverse pairs (Merge Sort) is: " + cnt);
    }
}

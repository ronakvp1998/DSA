package com.questions.strivers.arrays.medium;

//https://leetcode.com/problems/rearrange-array-elements-by-sign/description/
// pg 6 book4
/* Problem Statement:
        There’s an array ‘A’ of size ‘N’ with an equal number of positive and negative elements.
        Without altering the relative order of positive and negative elements,
        you must return an array of alternately positive and negative values.
        Note: Start the array with positive elements.
        Example 1:
        Input:
        arr[] = {1,2,-4,-5}, N = 4
        Output:
        1 -4 2 -5

        Explanation:
        Positive elements = 1,2
        Negative elements = -4,-5
        To maintain relative ordering, 1 must occur before 2, and -4 must occur before -5.

        Example 2:
        Input:
        arr[] = {1,2,-3,-1,-2,-3}, N = 6
        Output:
        1 -3 2 -1 3 -2
        Explanation:
        Positive elements = 1,2,3
        Negative elements = -3,-1,-2
        To maintain relative ordering, 1 must occur before 2, and 2 must occur before 3.
        Also, -3 should come before -1, and -1 should come before -2. */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RearrangeArrayBySign {
    public static void main(String[] args) {
        // Example where positives and negatives are unequal
        Integer arr[] = {1, 2, -4, -5, 3, -6, 7};
        // Convert array to List and pass to function
        System.out.println(RearrangebySign(Arrays.asList(arr), arr.length));
    }

    /**
     * ✅ Handles case when number of positive and negative elements are not equal
     * Time Complexity: O(n) - One full traversal to separate, then rearrange.
     * Space Complexity: O(n) - Uses two extra lists (positive and negative).
     */
    public static List<Integer> RearrangebySign(List<Integer> A, int n) {
        // Separate positives and negatives
        ArrayList<Integer> pos = new ArrayList<>();
        ArrayList<Integer> neg = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (A.get(i) > 0)
                pos.add(A.get(i));
            else
                neg.add(A.get(i));
        }

        // Case 1: more negatives
        if (pos.size() < neg.size()) {
            for (int i = 0; i < pos.size(); i++) {
                A.set(2 * i, pos.get(i));       // even index -> positive
                A.set(2 * i + 1, neg.get(i));   // odd index -> negative
            }
            // Add remaining negatives
            int index = pos.size() * 2;
            for (int i = pos.size(); i < neg.size(); i++) {
                A.set(index, neg.get(i));
                index++;
            }
        }
        // Case 2: more positives or equal
        else {
            for (int i = 0; i < neg.size(); i++) {
                A.set(2 * i, pos.get(i));
                A.set(2 * i + 1, neg.get(i));
            }
            // Add remaining positives
            int index = neg.size() * 2;
            for (int i = neg.size(); i < pos.size(); i++) {
                A.set(index, pos.get(i));
                index++;
            }
        }
        return A;
    }

    /**
     * ✅ Optimized approach (assumes equal number of pos & neg elements)
     * Time Complexity: O(n)
     * Space Complexity: O(n) - uses temp array
     */
    public static int[] rearrangeArray3(int arr[]) {
        int temp[] = new int[arr.length];  // Result array
        int pos = 0, neg = 1;              // Indexes for positive and negative numbers

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                temp[pos] = arr[i];        // Place positive at even index
                pos += 2;
            } else {
                temp[neg] = arr[i];        // Place negative at odd index
                neg += 2;
            }
        }
        return temp;
    }

    /**
     * ❌ Brute-force: Only works when equal pos & neg elements
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[] rearrangeArray2(int arr[]) {
        int pos[] = new int[arr.length / 2];   // Store positives
        int neg[] = new int[arr.length / 2];   // Store negatives

        int a = 0, b = 0, c = 0;
        while (c < arr.length) {
            if (arr[c] < 0) {
                neg[b++] = arr[c++];
            } else {
                pos[a++] = arr[c++];
            }
        }

        // Alternating placement
        for (int i = 0; i < arr.length / 2; i++) {
            arr[2 * i] = pos[i];
            arr[2 * i + 1] = neg[i];
        }
        return arr;
    }

    /**
     * Time Complexity: O(2n)
     * Space Complexity: O(2n)
     */
    public static void rearrangeArray(int arr[]) {
        // Use LinkedList to support removeFirst()
        List<Integer> neg = new LinkedList<>();
        List<Integer> pos = new LinkedList<>();

        // Separate into positive and negative lists
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                neg.add(arr[i]);
            } else if (arr[i] > 0) {
                pos.add(arr[i]);
            }
        }

        // Alternate placing positive and negative values
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                arr[i] = pos.remove(0);  // remove and return first element
            } else {
                arr[i] = neg.remove(0);
            }
        }
    }
}


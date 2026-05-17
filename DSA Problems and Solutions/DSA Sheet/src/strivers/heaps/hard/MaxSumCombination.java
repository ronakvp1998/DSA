package com.questions.strivers.heaps.hard;

import java.util.*;

/**
 * ==================================================================================================
 * APPROACH: Sorting + Max-Heap + Index Tracking
 * ==================================================================================================
 * 1. Sorting allows us to start from the largest possible sum.
 * 2. Max-Heap ensures we always pick the next largest sum from the pool of candidates.
 * 3. Set prevents redundant processing of the same index pairs.
 * ==================================================================================================
 */
public class MaxSumCombination {

    // Helper class to store sum and indices in the PriorityQueue
    static class Node {
        int sum, i, j;
        Node(int sum, int i, int j) {
            this.sum = sum;
            this.i = i;
            this.j = j;
        }
    }

    public static List<Integer> solve(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        // Step 1: Sort both arrays descending (using primitive sort + reverse)
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        // Step 2: Max-Heap to store sums
        PriorityQueue<Node> maxHeap = new PriorityQueue<>((a, b) -> b.sum - a.sum);

        // Set to store visited index pairs (i, j)
        Set<String> visited = new HashSet<>();

        // Start from the end of sorted arrays (largest elements)
        int i = n - 1;
        int j = n - 1;

        maxHeap.add(new Node(nums1[i] + nums2[j], i, j));
        visited.add(i + "," + j);

        List<Integer> result = new ArrayList<>();

        // Step 3: Extract K maximum sums
        while (k > 0 && !maxHeap.isEmpty()) {
            Node top = maxHeap.poll();
            result.add(top.sum);

            int currI = top.i;
            int currJ = top.j;

            // Candidate 1: (currI - 1, currJ)
            if (currI - 1 >= 0 && !visited.contains((currI - 1) + "," + currJ)) {
                maxHeap.add(new Node(nums1[currI - 1] + nums2[currJ], currI - 1, currJ));
                visited.add((currI - 1) + "," + currJ);
            }

            // Candidate 2: (currI, currJ - 1)
            if (currJ - 1 >= 0 && !visited.contains(currI + "," + (currJ - 1))) {
                maxHeap.add(new Node(nums1[currI] + nums2[currJ - 1], currI, currJ - 1));
                visited.add(currI + "," + (currJ - 1));
            }
            k--;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 4, 5};
        int[] nums2 = {2, 6, 3};
        int k = 2;
        System.out.println("Maximum " + k + " sum combinations: " + solve(nums1, nums2, k));
        // Output: [11, 10]
    }
}
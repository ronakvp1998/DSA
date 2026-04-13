package com.questions.strivers.arrays.hard;

import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: COUNT INVERSIONS IN AN ARRAY
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an array of integers of size N, count the number of inversions.
 * An inversion is defined as a pair of indices (i, j) such that:
 * 1. i < j
 * 2. arr[i] > arr[j]
 * * * Constraints:
 * - 1 <= N <= 10^5
 * - 1 <= arr[i] <= 10^9
 * - Time Limit: Usually 1-2 seconds, meaning O(N^2) will result in TLE.
 * * * Input/Output Formats:
 * Input: An array of N integers.
 * Output: A single integer (or long) representing the total inversion count.
 * Note: Maximum inversions can be N*(N-1)/2. For N=10^5, this is ~5*10^9,
 * which exceeds the 32-bit signed integer limit. We MUST use a 64-bit integer (long).
 * * * Example 1:
 * Input: arr = [5, 4, 3, 2, 1]
 * Output: 10
 * Explanation: All possible pairs are inversions: (5,4), (5,3), (5,2), (5,1),
 * (4,3), (4,2), (4,1), (3,2), (3,1), (2,1).
 * * * Example 2:
 * Input: arr = [2, 4, 1, 3, 5]
 * Output: 3
 * Explanation: The inversions are (2,1), (4,1), and (4,3).
 * * * Conceptual Visualization (Merge Sort Split Count):
 * Original: [5, 3, 2, 4, 1]
 * Divide into [5, 3, 2] and [4, 1]
 * Recursively sort and count left: [2, 3, 5] (Inversions: 3)
 * Recursively sort and count right: [1, 4] (Inversions: 1)
 * Now Merge: [2, 3, 5] and [1, 4]
 * - Compare 2 and 1: 2 > 1. Since left array is sorted, 3 and 5 are ALSO > 1.
 * Cross Inversions += (elements remaining in left) -> 3.
 * Total inversions = Left(3) + Right(1) + Cross(5 total during full merge) = 9.
 * ============================================================================
 */
public class CountInversion {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Modified Merge Sort)
     * ========================================================================
     * Approach and Steps:
     * 1. Use the Divide and Conquer paradigm (Merge Sort).
     * 2. The total inversions are the sum of inversions in the left half, the right
     * half, and the "split/cross inversions" discovered during the merge step.
     * 3. During the `merge` process, use two pointers (`i` for left half, `j` for right).
     * 4. If `arr[i] <= arr[j]`, no inversion. Place `arr[i]` in temp array and i++.
     * 5. If `arr[i] > arr[j]`, we found an inversion! Because the left half is
     * sorted, ALL elements from `i` to the end of the left half are also strictly
     * greater than `arr[j]`.
     * 6. Add `(mid - i + 1)` to the inversion count, place `arr[j]` in temp, and j++.
     * * * Detailed Intuition:
     * A sorted array has 0 inversions. The act of sorting an array requires us to
     * fix all out-of-order pairs. By piggybacking on Merge Sort, we systematically
     * count how many elements "jump over" other elements to achieve sorted order.
     * The linear time merge step allows us to count massive blocks of inversions
     * at once rather than checking pairs one by one.
     * * * Complexity Analysis:
     * - Time (O): O(N log N). Standard Merge Sort time complexity. The recurrence
     * relation is T(N) = 2T(N/2) + O(N).
     * - Space (O): O(N) auxiliary heap space for the temporary array used in merging.
     * O(log N) auxiliary stack space for the recursion tree.
     * ========================================================================
     */
    public static long countInversionsOptimal(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int[] temp = new int[arr.length];
        // We pass a clone so the original array remains unmodified for other tests
        return mergeSortAndCount(arr.clone(), temp, 0, arr.length - 1);
    }

    private static long mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        long invCount = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Inversions in the left half
            invCount += mergeSortAndCount(arr, temp, left, mid);
            // Inversions in the right half
            invCount += mergeSortAndCount(arr, temp, mid + 1, right);
            // Split inversions across the two halves
            invCount += mergeAndCount(arr, temp, left, mid, right);
        }
        return invCount;
    }

    private static long mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;      // Starting index for left subarray
        int j = mid + 1;   // Starting index for right subarray
        int k = left;      // Starting index to be sorted
        long invCount = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                // arr[i] > arr[j] implies everything from i to mid is > arr[j]
                temp[k++] = arr[j++];
                invCount += (mid - i + 1);
            }
        }

        // Copy remaining elements of left subarray (if any)
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // Copy remaining elements of right subarray (if any)
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // Copy sorted subarray back to original array
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }

        return invCount;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Initialize an inversion counter `count` to 0.
     * 2. Run two nested loops. The outer loop `i` runs from 0 to N-2.
     * 3. The inner loop `j` runs from i+1 to N-1.
     * 4. For every pair, check if `arr[i] > arr[j]`. If true, increment `count`.
     * 5. Return the counter.
     * * * Detailed Intuition:
     * This directly translates the mathematical definition of an inversion
     * into code. We compare every single possible pair. It is mathematically
     * sound but computationally unviable for large inputs, acting purely as
     * a correctness baseline.
     * * * Complexity Analysis:
     * - Time (O): O(N^2). We check exactly N*(N-1)/2 pairs.
     * - Space (O): O(1) auxiliary space. No additional memory used.
     * ========================================================================
     */
    public static long countInversionsBruteForce(int[] arr) {
        long count = 0;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Coordinate Compression + Fenwick Tree/BIT)
     * ========================================================================
     * Approach and Steps:
     * 1. Since array values can be up to 10^9, we cannot use them directly as
     * indices in a Binary Indexed Tree (BIT). We must perform "Coordinate
     * Compression" to map values to ranks (1 to unique_count).
     * 2. Initialize a BIT of size equal to the number of unique elements.
     * 3. Iterate through the original array from right to left.
     * 4. For each element, query the BIT for the sum of frequencies of all ranks
     * strictly less than the current element's rank. This represents elements
     * already processed (to the right) that are smaller than the current element.
     * 5. Add this queried sum to the inversion count.
     * 6. Update the BIT by incrementing the frequency of the current element's rank.
     * * * Detailed Intuition:
     * A Fenwick tree efficiently tracks running frequencies. If we traverse the
     * array backwards, everything currently in the BIT originally appeared to the
     * right of our current pointer. By asking the BIT, "How many numbers currently
     * stored are smaller than me?", we are directly calculating the inversions
     * for the current element.
     * * * Complexity Analysis:
     * - Time (O): O(N log N). Coordinate compression involves sorting O(N log N).
     * Querying and updating the BIT takes O(log N) per element, so O(N log N) total.
     * - Space (O): O(N) heap space for the BIT array and auxiliary structures used
     * for coordinate compression. O(1) stack space.
     * ========================================================================
     */
    public static long countInversionsBIT(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int n = arr.length;

        // Step 1: Coordinate Compression
        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        Map<Integer, Integer> ranks = new HashMap<>();
        int rank = 1;
        for (int i = 0; i < n; i++) {
            if (!ranks.containsKey(sortedArr[i])) {
                ranks.put(sortedArr[i], rank++);
            }
        }

        // Step 2: Initialize BIT
        int[] bit = new int[rank];
        long invCount = 0;

        // Step 3: Traverse Right to Left
        for (int i = n - 1; i >= 0; i--) {
            int currentRank = ranks.get(arr[i]);
            // Query for elements strictly smaller (rank - 1)
            invCount += queryBIT(bit, currentRank - 1);
            // Add current element to BIT
            updateBIT(bit, rank, currentRank, 1);
        }

        return invCount;
    }

    // BIT Helper: Add value to index
    private static void updateBIT(int[] bit, int maxRank, int index, int val) {
        while (index < maxRank) {
            bit[index] += val;
            index += index & (-index); // Move to next node
        }
    }

    // BIT Helper: Get prefix sum up to index
    private static long queryBIT(int[] bit, int index) {
        long sum = 0;
        while (index > 0) {
            sum += bit[index];
            index -= index & (-index); // Move to parent node
        }
        return sum;
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * Validates all implementations against strictly decreasing arrays (worst-case),
     * strictly increasing arrays (best-case), standard permutations, and duplicates.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== COUNT INVERSIONS MASTERCLASS TESTING SUITE ===\n");

        int[][] testCases = {
                {5, 4, 3, 2, 1},             // Worst case: fully reversed
                {1, 2, 3, 4, 5},             // Best case: already sorted
                {2, 4, 1, 3, 5},             // Mixed standard case
                {5, 3, 2, 4, 1},             // Mixed standard case 2
                {2, 2, 2, 2},                // Duplicates (0 inversions)
                {10, 10, 10, 1, 1},          // Duplicates with inversion blocks
                {1},                         // Single element
                {}                           // Empty array
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Brute Force
            long start1 = System.nanoTime();
            long res1 = countInversionsBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // BIT / Fenwick Tree
            long start2 = System.nanoTime();
            long res2 = countInversionsBIT(tc);
            long end2 = System.nanoTime();
            System.out.println("BIT (Fenwick) Output: " + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Optimal (Merge Sort)
            long start3 = System.nanoTime();
            long res3 = countInversionsOptimal(tc);
            long end3 = System.nanoTime();
            System.out.println("Optimal Merge Sort: " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = (res1 == res2) && (res2 == res3);
            System.out.println("Sanity Check Passed:  " + isMatch + "\n" + "-".repeat(50) + "\n");
        }
    }
}
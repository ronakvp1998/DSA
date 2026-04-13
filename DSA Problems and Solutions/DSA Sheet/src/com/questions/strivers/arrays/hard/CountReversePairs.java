package com.questions.strivers.arrays.hard;

import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: REVERSE PAIRS
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Formal Problem Statement:
 * Given an integer array nums, return the number of reverse pairs in the array.
 * A reverse pair is a pair (i, j) where:
 * 0 <= i < j < nums.length and
 * nums[i] > 2 * nums[j].
 * * Constraints:
 * - 1 <= nums.length <= 5 * 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 * * Example 1:
 * Input: nums = [1,3,2,3,1]
 * Output: 2
 * Explanation: The reverse pairs are:
 * (1, 4) --> nums[1] = 3, nums[4] = 1, 3 > 2 * 1
 * (3, 4) --> nums[3] = 3, nums[4] = 1, 3 > 2 * 1
 * * Example 2:
 * Input: nums = [2,4,3,5,1]
 * Output: 3
 * Explanation: The reverse pairs are:
 * (1, 4) --> nums[1] = 4, nums[4] = 1, 4 > 2 * 1
 * (2, 4) --> nums[2] = 3, nums[4] = 1, 3 > 2 * 1
 * (3, 4) --> nums[3] = 5, nums[4] = 1, 5 > 2 * 1
 * * * Conceptual Visualization (Divide and Conquer):
 * Array: [2, 4, 3, 5, 1]
 * To find pairs efficiently, we can use a modified Merge Sort.
 * If we divide the array into two halves, the total reverse pairs =
 * (Pairs purely in Left Half) + (Pairs purely in Right Half) + (Cross Pairs).
 * * When counting Cross Pairs, both the Left Half and Right Half are SORTED.
 * Let Left = [2, 4] and Right = [1, 3, 5]
 * For each element in Left, we see how many elements in Right satisfy Left[i] > 2 * Right[j].
 * Because Left and Right are sorted, we can use a two-pointer approach, drastically
 * reducing the cross-pair counting time from O(N^2) to O(N).
 * ============================================================================
 */
public class CountReversePairs {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Modified Merge Sort)
     * ========================================================================
     * Approach and Steps:
     * 1. Implement a classic Merge Sort algorithm.
     * 2. In the `mergeSort` recursive function, sort the left half, sort the right
     * half, and sum their reverse pairs.
     * 3. Before the actual merging of the two halves, implement a "count pairs" step:
     * - Iterate through the `left` array with pointer `i`.
     * - Use a pointer `j` for the `right` array.
     * - While `left[i] > 2L * right[j]`, increment `j`.
     * - The number of valid pairs for `left[i]` is exactly `(j - (mid + 1))`.
     * 4. Perform the standard merge operation to keep the array sorted for the
     * higher-level recursive calls.
     * * * Detailed Intuition:
     * We transition from an O(N^2) naive check to O(N log N) by exploiting sorting.
     * If the subarrays are sorted, and we know `left[i] > 2 * right[j]`, then all
     * subsequent elements in the left array (which are greater than or equal to `left[i]`)
     * will ALSO be strictly greater than `2 * right[j]`. This monotonically increasing
     * property allows the `j` pointer to only move forward, completing the cross-count
     * in linear time.
     * NOTE: We MUST cast to `long` (`2L * nums[j]`) because `nums[i]` can be up to
     * 2^31 - 1. Multiplying by 2 will cause a 32-bit integer overflow!
     * * * Complexity Analysis:
     * - Time (O): O(N log N). The array is divided log(N) times, and counting+merging
     * at each level takes O(N) time.
     * - Space (O): O(N) total auxiliary space.
     * - Heap Space: O(N) for the temporary array used during the merge step.
     * - Stack Space: O(log N) for the recursion tree depth.
     * ========================================================================
     */
    public static int reversePairsOptimal(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        return mergeSortAndCount(nums, 0, nums.length - 1);
    }

    private static int mergeSortAndCount(int[] nums, int start, int end) {
        if (start >= end) return 0;

        int mid = start + (end - start) / 2;
        int count = 0;

        count += mergeSortAndCount(nums, start, mid);
        count += mergeSortAndCount(nums, mid + 1, end);
        count += countCrossPairsAndMerge(nums, start, mid, end);

        return count;
    }

    private static int countCrossPairsAndMerge(int[] nums, int start, int mid, int end) {
        int count = 0;
        int j = mid + 1;

        // Count reverse pairs
        for (int i = start; i <= mid; i++) {
            while (j <= end && nums[i] > 2L * nums[j]) {
                j++;
            }
            count += (j - (mid + 1));
        }

        // Standard merge step
        int[] temp = new int[end - start + 1];
        int left = start, right = mid + 1, k = 0;

        while (left <= mid && right <= end) {
            if (nums[left] <= nums[right]) {
                temp[k++] = nums[left++];
            } else {
                temp[k++] = nums[right++];
            }
        }

        while (left <= mid) temp[k++] = nums[left++];
        while (right <= end) temp[k++] = nums[right++];

        for (int i = 0; i < temp.length; i++) {
            nums[start + i] = temp[i];
        }

        return count;
    }


    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Use two nested loops to check every possible pair (i, j) where i < j.
     * 2. If the condition `nums[i] > 2L * nums[j]` is met, increment a counter.
     * 3. Return the counter.
     * * * Detailed Intuition:
     * This is the mathematical translation of the problem statement directly into code.
     * It checks every combination. It is highly inefficient and will yield a
     * Time Limit Exceeded (TLE) error for array sizes approaching 50,000, but it
     * verifies our logic handles negatives and zeroes correctly.
     * * * Complexity Analysis:
     * - Time (O): O(N^2) where N is the length of the array, due to the nested loops.
     * - Space (O): O(1) auxiliary space. Entirely in-place, zero heap usage, O(1) stack.
     * ========================================================================
     */
    public static int reversePairsBruteForce(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > 2L * nums[j]) {
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
     * 1. We want to iterate left-to-right. For each `nums[i]`, we need to know
     * how many already-processed elements are > 2 * nums[i].
     * 2. Since array values are huge (up to 2^31 - 1), we cannot use them as
     * direct indices in an array. We must compress them to ranks (1, 2, 3...).
     * 3. Collect all `nums[i]` and `2L * nums[i]` into a TreeSet to sort and deduplicate.
     * 4. Map each unique value to a rank.
     * 5. Initialize a Binary Indexed Tree (BIT) with the size of unique ranks.
     * 6. Iterate through `nums`. For each number:
     * - The number of valid reverse pairs is the number of elements in the BIT
     * strictly greater than the rank of `2L * nums[i]`.
     * - We calculate this by: total_elements_in_BIT - query(rank of 2 * nums[i]).
     * - Insert the rank of `nums[i]` into the BIT.
     * * * Detailed Intuition:
     * A Fenwick Tree tracks cumulative frequencies efficiently. By iterating left
     * to right, the BIT contains exactly the elements appearing BEFORE the current
     * element `j` (fulfilling i < j). We query how many previously seen elements
     * are strictly greater than `2 * nums[j]`. Coordinate compression resolves the
     * issue of massive element ranges by collapsing values into tight 1-based indices.
     * * * Complexity Analysis:
     * - Time (O): O(N log N). Creating the TreeSet and Mapping takes O(N log N).
     * Processing N elements with BIT updates/queries takes O(N log N).
     * - Space (O): O(N) auxiliary space.
     * - Heap Space: O(N) for the TreeSet, HashMap, and the BIT array.
     * - Stack Space: O(1) auxiliary stack space.
     * ========================================================================
     */
    public static int reversePairsBIT(int[] nums) {
        if (nums == null || nums.length < 2) return 0;

        // Coordinate Compression
        TreeSet<Long> sortedUniqueVals = new TreeSet<>();
        for (int num : nums) {
            sortedUniqueVals.add((long) num);
            sortedUniqueVals.add(2L * num);
        }

        Map<Long, Integer> ranks = new HashMap<>();
        int rank = 1;
        for (long val : sortedUniqueVals) {
            ranks.put(val, rank++);
        }

        int[] bit = new int[ranks.size() + 1];
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            // Find how many numbers previously inserted are > 2 * nums[i]
            int queryRank = ranks.get(2L * nums[i]);
            // Total numbers inserted so far is 'i'. query(queryRank) gives numbers <= 2*nums[i]
            count += (i - queryBIT(bit, queryRank));

            // Insert current nums[i] into BIT
            updateBIT(bit, ranks.get((long) nums[i]), 1);
        }

        return count;
    }

    // Fenwick Tree / BIT Helper Methods
    private static void updateBIT(int[] bit, int index, int val) {
        while (index < bit.length) {
            bit[index] += val;
            index += index & (-index);
        }
    }

    private static int queryBIT(int[] bit, int index) {
        int sum = 0;
        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }
        return sum;
    }


    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all implementations against standard
     * sequences, edge cases with zeroes, negatives, and integer overflow traps.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== REVERSE PAIRS MASTERCLASS TESTING SUITE ===\n");

        int[][] testCases = {
                {1, 3, 2, 3, 1},                      // Standard case 1
                {2, 4, 3, 5, 1},                      // Standard case 2
                {5, 4, 3, 2, 1},                      // Purely descending array
                {1, 2, 3, 4, 5},                      // Purely ascending array (0 pairs)
                {0, 0, 0, 0},                         // Zeroes edge case
                {-5, -5},                             // Negative pairs: -5 > 2 * -5 -> -5 > -10 (True)
                {Integer.MAX_VALUE, Integer.MAX_VALUE / 2}, // Testing strict > and overflow
                {Integer.MAX_VALUE, Integer.MIN_VALUE}      // Extreme limits overflow check
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " +
                    (tc.length < 10 ? Arrays.toString(tc) : "[Large Array]"));

            // Brute Force
            long start1 = System.nanoTime();
            int res1 = reversePairsBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output:  " + res1 + " \t(Time: " + (end1 - start1) / 1000 + " us)");

            // BIT / Fenwick Tree
            long start2 = System.nanoTime();
            int res2 = reversePairsBIT(tc);
            long end2 = System.nanoTime();
            System.out.println("BIT (Fenwick) Output:" + res2 + " \t(Time: " + (end2 - start2) / 1000 + " us)");

            // Optimal Merge Sort
            // (Note: Merge Sort modifies the array in-place, so we must pass a clone)
            long start3 = System.nanoTime();
            int res3 = reversePairsOptimal(tc.clone());
            long end3 = System.nanoTime();
            System.out.println("Optimal Merge Sort:  " + res3 + " \t(Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = (res1 == res2) && (res2 == res3);
            System.out.println("Sanity Check Passed: " + isMatch + "\n" + "-".repeat(60) + "\n");
        }
    }
}
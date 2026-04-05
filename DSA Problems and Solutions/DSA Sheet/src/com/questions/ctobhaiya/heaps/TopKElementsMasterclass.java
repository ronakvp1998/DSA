package com.questions.ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: TOP K LARGEST & SMALLEST ELEMENTS IN AN ARRAY
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an integer array `nums` and an integer `k`, return the `k` largest
 * elements and the `k` smallest elements in the array.
 * (Note: This is an extension of LeetCode #215 which asks for the Kth largest.
 * Here, we are returning the entire collection of the top K elements).
 * * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * * Input/Output Formats:
 * Input: int[] nums, int k
 * Output: int[] (containing the top K elements)
 * * Examples:
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output:
 * Top K Largest: [5, 6] (or [6, 5])
 * Top K Smallest: [1, 2] (or [2, 1])
 * * Example 2:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output:
 * Top K Largest: [2, 3]
 * Top K Smallest: [1, 1]
 * * Conceptual Visualization (Min-Heap for Top K Largest):
 * Goal: Find Top 3 Largest in [3, 2, 1, 5, 6, 4] -> k = 3
 * We maintain a Min-Heap of size strictly K. It acts as a "bouncer", only
 * keeping the 3 largest elements seen so far. The root is the smallest of these.
 * * Stream: 3, 2, 1 -> Heap: [1, 2, 3] (Root is 1)
 * Add 5 -> Heap: [1, 2, 3, 5] -> Size > 3, eject root (1) -> [2, 3, 5]
 * Add 6 -> Heap: [2, 3, 5, 6] -> Size > 3, eject root (2) -> [3, 5, 6]
 * Add 4 -> Heap: [3, 4, 5, 6] -> Size > 3, eject root (3) -> [4, 5, 6]
 * * Final Heap Elements: [4, 5, 6] (These are the top 3 largest elements!)
 * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ----------------------------------------------------------------------------
 * Phase 1: Brute Force Approach (Sorting) - The "Think it" stage.
 * - Sort the entire array. Slice the first K elements for the smallest, and
 * the last K elements for the largest.
 * * Phase 2: Alternative Approach (Heaps) - The "Refine it" stage.
 * - Use a Min-Heap of size K to track the Top K largest elements.
 * - Use a Max-Heap of size K to track the Top K smallest elements.
 * * Phase 3: Alternative Approach (QuickSelect) - The "Perfect it" stage.
 * - Use the QuickSelect partitioning algorithm to place the Kth element in its
 * correct sorted position. All elements to one side will be the Top K elements
 * (though not necessarily perfectly sorted among themselves).
 */

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class TopKElementsMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (FULL SORTING)
     * ========================================================================
     * * Detailed Intuition:
     * By sorting the entire array in ascending order, we establish a total ranking.
     * The `k` smallest elements will naturally occupy indices `0` to `k-1`.
     * The `k` largest elements will occupy indices `n-k` to `n-1`.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Dominated by the Arrays.sort() operation.
     * - Space Complexity: O(1) auxiliary heap space (Java primitive sort is in-place),
     * but O(log N) auxiliary stack space for the Dual-Pivot Quicksort implementation.
     * Returning the result array takes O(K) space.
     */
    public static int[] getTopKLargestBrute(int[] nums, int k) {
        Arrays.sort(nums);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = nums[nums.length - k + i];
        }
        return result;
    }

    public static int[] getTopKSmallestBrute(int[] nums, int k) {
        Arrays.sort(nums);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = nums[i];
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL HEAP APPROACH (MIN/MAX HEAPS)
     * ========================================================================
     * * Detailed Intuition:
     * Sorting the entire array is overkill. We only need to maintain a running tally
     * of the Top K.
     * - For Top K LARGEST: We use a Min-Heap. Why? The heap needs to easily identify
     * and discard the *smallest* element among our current Top K candidates to make
     * room for a larger one.
     * - For Top K SMALLEST: We use a Max-Heap. The heap easily identifies and discards
     * the *largest* element among our current bottom K candidates.
     * * Complexity Analysis:
     * - Time Complexity: O(N log K). We process N elements. Pushing/popping from a
     * heap capped at size K takes O(log K) time.
     * - Space Complexity: O(K) auxiliary heap space for the PriorityQueue. O(1) stack space.
     */
    public static int[] getTopKLargestHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Toss out the smallest item
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        return result;
    }

    public static int[] getTopKSmallestHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Toss out the largest item
            }
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll(); // Populate array
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 3: QUICKSELECT PARTITIONING (THE "PERFECT IT" STAGE)
     * ========================================================================
     * * Detailed Intuition:
     * We can achieve average O(N) time using Hoare's selection algorithm (QuickSelect).
     * By partitioning the array around a random pivot, elements smaller than the pivot
     * move left, and larger move right.
     * - To find Top K Largest, we want to partition such that the element at index `N-K`
     * is in its final sorted position. Everything to its right will be the Top K largest.
     * - To find Top K Smallest, we partition such that the element at index `K-1` is in
     * its sorted position. Everything to its left (and including it) will be the Top K smallest.
     * * Complexity Analysis:
     * - Time Complexity: O(N) average time. O(N^2) worst-case (mitigated by random pivot).
     * - Space Complexity: O(1) auxiliary heap space (modifies array in-place).
     * O(log N) auxiliary stack space for recursion depth. Result array takes O(K).
     */
    public static int[] getTopKLargestQuickSelect(int[] nums, int k) {
        int n = nums.length;
        quickSelect(nums, 0, n - 1, n - k);
        return Arrays.copyOfRange(nums, n - k, n);
    }

    public static int[] getTopKSmallestQuickSelect(int[] nums, int k) {
        quickSelect(nums, 0, nums.length - 1, k - 1);
        return Arrays.copyOfRange(nums, 0, k);
    }

    private static void quickSelect(int[] nums, int left, int right, int targetIndex) {
        if (left >= right) return;

        Random rand = new Random();
        int pivotIndex = left + rand.nextInt(right - left + 1);

        pivotIndex = partition(nums, left, right, pivotIndex);

        if (pivotIndex == targetIndex) {
            return;
        } else if (targetIndex < pivotIndex) {
            quickSelect(nums, left, pivotIndex - 1, targetIndex);
        } else {
            quickSelect(nums, pivotIndex + 1, right, targetIndex);
        }
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right); // Move pivot out of the way

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }

        swap(nums, storeIndex, right); // Restore pivot
        return storeIndex;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("🚀 Executing Top K Elements Masterclass Testing Suite...\n");

        int[][][] testCases = {
                {{3, 2, 1, 5, 6, 4}, {2}},                    // Standard Case
                {{3, 2, 3, 1, 2, 4, 5, 5, 6}, {4}},           // Array with Duplicates
                {{1}, {1}},                                   // Single Element Array
                {{-1, -5, 0, 8, -3, 2}, {3}},                 // Positives/Negatives/Zeros
                {{9, 9, 9, 9, 9}, {2}}                        // Identical Elements
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] originalNums = testCases[i][0];
            int k = testCases[i][1][0];

            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(originalNums) + ", k = " + k);

            // Create copies to prevent in-place modifications from ruining subsequent tests
            int[] arrBrute = Arrays.copyOf(originalNums, originalNums.length);
            int[] arrHeap = Arrays.copyOf(originalNums, originalNums.length);
            int[] arrQS = Arrays.copyOf(originalNums, originalNums.length);

            // Run Top K Largest
            int[] bruteL = getTopKLargestBrute(Arrays.copyOf(arrBrute, arrBrute.length), k);
            int[] heapL = getTopKLargestHeap(Arrays.copyOf(arrHeap, arrHeap.length), k);
            int[] qsL = getTopKLargestQuickSelect(Arrays.copyOf(arrQS, arrQS.length), k);

            // Sort outputs for clean comparison (since Heap/QuickSelect order may vary internally)
            Arrays.sort(bruteL); Arrays.sort(heapL); Arrays.sort(qsL);

            // Run Top K Smallest
            int[] bruteS = getTopKSmallestBrute(Arrays.copyOf(arrBrute, arrBrute.length), k);
            int[] heapS = getTopKSmallestHeap(Arrays.copyOf(arrHeap, arrHeap.length), k);
            int[] qsS = getTopKSmallestQuickSelect(Arrays.copyOf(arrQS, arrQS.length), k);

            Arrays.sort(bruteS); Arrays.sort(heapS); Arrays.sort(qsS);

            System.out.println("  [Top K Largest]");
            System.out.println("    Brute:       " + Arrays.toString(bruteL));
            System.out.println("    Heap:        " + Arrays.toString(heapL) + (Arrays.equals(bruteL, heapL) ? " ✅" : " ❌"));
            System.out.println("    QuickSelect: " + Arrays.toString(qsL) + (Arrays.equals(bruteL, qsL) ? " ✅" : " ❌"));

            System.out.println("  [Top K Smallest]");
            System.out.println("    Brute:       " + Arrays.toString(bruteS));
            System.out.println("    Heap:        " + Arrays.toString(heapS) + (Arrays.equals(bruteS, heapS) ? " ✅" : " ❌"));
            System.out.println("    QuickSelect: " + Arrays.toString(qsS) + (Arrays.equals(bruteS, qsS) ? " ✅" : " ❌"));
            System.out.println("--------------------------------------------------");
        }
    }
}
package com.questions.ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: Kth LARGEST & SMALLEST ELEMENT IN AN ARRAY (LeetCode #215)
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement (LeetCode 215 - Extended):
 * Given an integer array `nums` and an integer `k`, return the kth largest
 * element in the array. Note that it is the kth largest element in the sorted
 * order, not the kth distinct element.
 * (Extension: We will also solve for the Kth smallest element concurrently).
 * * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * * Input/Output Formats:
 * Input: int[] nums, int k
 * Output: int
 * * Examples:
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5 (2nd largest) | 2 (2nd smallest)
 * * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4 (4th largest) | 3 (4th smallest)
 * * Conceptual Visualization (Min-Heap Strategy for Kth LARGEST):
 * Goal: Find 3rd largest in [3, 2, 1, 5, 6, 4] -> k = 3
 * We keep a Min-Heap strictly of size K. It acts as a filter, keeping only the
 * 3 largest elements seen so far. The smallest of those 3 (the root) is our answer.
 * * Stream: 3, 2, 1 -> Heap: [1, 2, 3] (Root is 1)
 * Add 5 -> Heap: [1, 2, 3, 5] -> Size > 3, eject root (1) -> [2, 3, 5]
 * Add 6 -> Heap: [2, 3, 5, 6] -> Size > 3, eject root (2) -> [3, 5, 6]
 * Add 4 -> Heap: [3, 4, 5, 6] -> Size > 3, eject root (3) -> [4, 5, 6]
 * * Final Heap Array: [4, 6, 5]
 * Tree View:
 * 4  <-- Root is the 3rd Largest overall!
 * / \
 * 6   5
 * * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ----------------------------------------------------------------------------
 * Phase 1: Brute Force Approach (Sorting) - The "Think it" stage.
 * - Sort the array entirely. Access the element by calculating the correct index.
 * Phase 2: Priority Queue (Heap) - The "Refine it" stage.
 * - Use a Min-Heap of size K for the Kth largest element.
 * - Use a Max-Heap of size K for the Kth smallest element.
 * Phase 3: QuickSelect Algorithm - The "Perfect it" stage.
 * - Use a divide-and-conquer approach based on QuickSort's partitioning to
 * find the element in average O(N) time without full sorting or extra heap space.
 */

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class KthElementMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (FULL SORTING)
     * ========================================================================
     * Detailed Intuition:
     * The simplest way to find the positional rank of an element is to establish
     * a total order. By sorting the array in ascending order, the Kth smallest
     * element naturally lands at index `k - 1`, and the Kth largest lands at
     * index `nums.length - k`.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Array sorting dominates the execution time.
     * - Space Complexity: O(1) auxiliary heap space. However, it uses O(log N)
     * auxiliary stack space under the hood for Java's Dual-Pivot Quicksort.
     */
    public static int getKthLargestBrute(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public static int getKthSmallestBrute(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[k - 1];
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL HEAP APPROACH (MIN/MAX HEAPS)
     * ========================================================================
     * Detailed Intuition:
     * Full sorting does unnecessary work by sorting the *entire* array when we
     * only care about the top K boundaries.
     * - For Kth LARGEST: We maintain a Min-Heap of size K. It stores the K
     * largest elements seen so far. If a new element is larger than the root
     * (the smallest of the top K), it pushes the root out and takes its place.
     * - For Kth SMALLEST: We maintain a Max-Heap of size K. It stores the K
     * smallest elements seen so far.
     * * Complexity Analysis:
     * - Time Complexity: O(N log K). We process N elements, and each heap insertion/
     * deletion takes O(log K) time since the heap size is capped at K.
     * - Space Complexity: O(K) auxiliary heap space to store the PriorityQueue.
     * O(1) auxiliary stack space.
     */
    public static int getKthLargestHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // Default is Min-Heap
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the smallest among the top K
            }
        }
        return minHeap.peek();
    }

    public static int getKthSmallestHeap(int[] nums, int k) {
        // Max-Heap requires a custom comparator
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Evict the largest among the bottom K
            }
        }
        return maxHeap.peek();
    }

    /**
     * ========================================================================
     * PHASE 3: QUICKSELECT (DIVIDE & CONQUER) - THE "PERFECT IT" STAGE
     * ========================================================================
     * Detailed Intuition:
     * Can we do better than O(N log K)? Yes, using QuickSelect (Hoare's Selection).
     * Similar to QuickSort, we pick a random 'pivot' and partition the array:
     * smaller elements to the left, larger to the right.
     * The beauty: the pivot is now in its absolute final sorted position!
     * If this position equals our target index, we are done. If not, we only
     * recurse into the half where our target index lies, discarding the other half.
     * * Complexity Analysis:
     * - Time Complexity: O(N) Average Time. N + N/2 + N/4... = O(N).
     * Worst Case Time: O(N^2) if the array is already sorted and we pick bad
     * pivots (mitigated heavily by randomized pivot selection).
     * - Space Complexity: O(1) auxiliary heap space. O(log N) auxiliary stack space
     * for the recursion depth (O(N) stack in the worst-case scenario).
     */
    public static int getKthLargestQuickSelect(int[] nums, int k) {
        // Kth largest is at index (length - k) in a sorted array
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public static int getKthSmallestQuickSelect(int[] nums, int k) {
        // Kth smallest is at index (k - 1) in a sorted array
        return quickSelect(nums, 0, nums.length - 1, k - 1);
    }

    private static int quickSelect(int[] nums, int left, int right, int targetIndex) {
        if (left == right) {
            return nums[left];
        }

        // 1. Pick a random pivot to avoid worst-case O(N^2) on sorted arrays
        Random rand = new Random();
        int pivotIndex = left + rand.nextInt(right - left + 1);

        // 2. Partition the array around the pivot
        pivotIndex = partition(nums, left, right, pivotIndex);

        // 3. Determine which partition to search next
        if (pivotIndex == targetIndex) {
            return nums[pivotIndex]; // Found it!
        } else if (targetIndex < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, targetIndex); // Search left
        } else {
            return quickSelect(nums, pivotIndex + 1, right, targetIndex); // Search right
        }
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];

        // Move pivot to the very end to get it out of the way
        swap(nums, pivotIndex, right);

        int storeIndex = left;
        // Move all elements smaller than pivotValue to the left
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }

        // Move pivot back to its final, sorted resting place
        swap(nums, storeIndex, right);
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
        System.out.println("🚀 Executing Kth Element Masterclass Testing Suite...\n");

        // Format: { {Array Elements}, {k}, {Expected Kth Largest}, {Expected Kth Smallest} }
        int[][][] testCases = {
                {{3, 2, 1, 5, 6, 4}, {2}, {5}, {2}},                    // Standard Case
                {{3, 2, 3, 1, 2, 4, 5, 5, 6}, {4}, {4}, {3}},           // Array with Duplicates
                {{1}, {1}, {1}, {1}},                                   // Single Element Array
                {{-1, -5, 0, 8, -3, 2}, {3}, {0}, {-1}},                // Mix of Positives/Negatives/Zeros
                {{9, 9, 9, 9, 9}, {1}, {9}, {9}},                       // All Identical Elements
                {{10, 20, 30, 40, 50}, {5}, {10}, {50}}                 // Target is the boundary (smallest/largest)
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] originalNums = testCases[i][0];
            int k = testCases[i][1][0];
            int expectedLargest = testCases[i][2][0];
            int expectedSmallest = testCases[i][3][0];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Input: nums = " + Arrays.toString(originalNums) + ", k = " + k);

            // Phase 1: Brute Force Tests (Uses copies to prevent mutating source arrays for later tests)
            int bruteL = getKthLargestBrute(Arrays.copyOf(originalNums, originalNums.length), k);
            int bruteS = getKthSmallestBrute(Arrays.copyOf(originalNums, originalNums.length), k);

            // Phase 2: Heap Tests
            int heapL = getKthLargestHeap(originalNums, k); // Read-only operation
            int heapS = getKthSmallestHeap(originalNums, k);

            // Phase 3: QuickSelect Tests
            int quickL = getKthLargestQuickSelect(Arrays.copyOf(originalNums, originalNums.length), k);
            int quickS = getKthSmallestQuickSelect(Arrays.copyOf(originalNums, originalNums.length), k);

            // Output Validation
            System.out.println("[Kth Largest]");
            System.out.printf("  Expected: %d | Brute: %d | Heap: %d | QuickSelect: %d\n", expectedLargest, bruteL, heapL, quickL);
            boolean passL = (bruteL == expectedLargest) && (heapL == expectedLargest) && (quickL == expectedLargest);
            System.out.println("  Largest Result: " + (passL ? "✅ PASS" : "❌ FAIL"));

            System.out.println("[Kth Smallest]");
            System.out.printf("  Expected: %d | Brute: %d | Heap: %d | QuickSelect: %d\n", expectedSmallest, bruteS, heapS, quickS);
            boolean passS = (bruteS == expectedSmallest) && (heapS == expectedSmallest) && (quickS == expectedSmallest);
            System.out.println("  Smallest Result: " + (passS ? "✅ PASS" : "❌ FAIL"));

            System.out.println("--------------------------------------------------");
        }
    }
}
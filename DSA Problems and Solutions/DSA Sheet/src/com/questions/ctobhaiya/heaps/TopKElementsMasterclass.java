package com.questions.ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: Kth LARGEST / SMALLEST ELEMENT IN AN ARRAY (LeetCode #215)
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an integer array `nums` and an integer `k`, return the kth largest
 * element in the array. Note that it is the kth largest element in the sorted
 * order, not the kth distinct element.
 * * (Extension: We will also cover finding the kth smallest element, as the
 * underlying heap mechanics are perfectly mirrored).
 * * * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * * * Input/Output Formats:
 * Input: int[] nums, int k
 * Output: int
 * * * Examples:
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 * * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 * * * Conceptual Visualization (Min-Heap of size K for Kth Largest):
 * To find the 3rd largest element in [3, 2, 1, 5, 6, 4]:
 * We maintain a Min-Heap of size exactly K=3.
 * As we iterate, if heap size > 3, we eject the smallest element at the root.
 * * Iteration through array:
 * Add 3 -> Heap: [3]
 * Add 2 -> Heap: [2, 3]
 * Add 1 -> Heap: [1, 3, 2]
 * Add 5 -> Heap: [1, 3, 2, 5] -> Size > 3, poll root (1) -> Heap: [2, 3, 5]
 * Add 6 -> Heap: [2, 3, 5, 6] -> Size > 3, poll root (2) -> Heap: [3, 6, 5]
 * Add 4 -> Heap: [3, 6, 5, 4] -> Size > 3, poll root (3) -> Heap: [4, 6, 5]
 * * Final Heap (Min at root is the 3rd largest overall!):
 * 4
 * /   \
 * 6     5
 * * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ----------------------------------------------------------------------------
 * * Phase 1: Brute Force approach - The "Think it" stage.
 * - Sort the entire array and pick the element at the required index.
 * * Phase 2: Optimal Approach (Min-Heap / Max-Heap) - The "Build it" stage.
 * - Use a Min-Heap of size K for the Kth Largest.
 * - Use a Max-Heap of size K for the Kth Smallest.
 * * Phase 3: Alternative Approach (QuickSelect) - The "Perfect it" stage.
 * - Use Hoare's or Lomuto's partition scheme to find the element in average
 * linear time without extra space.
 */

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class TopKElementsMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE (SORTING)
     * ========================================================================
     * * Detailed Intuition:
     * The most straightforward way to find the Kth largest element is to sort the
     * array in ascending order. Once sorted, the largest element is at the end,
     * the second largest is second from the end, and so on. The Kth largest is
     * exactly at index `nums.length - k`.
     * * * Complexity Analysis:
     * - Time Complexity: O(N log N). Standard comparison-based sorting takes
     * O(N log N) time, where N is the number of elements in the array.
     * - Space Complexity: O(1) or O(N). Depending on the underlying sorting
     * algorithm used by Arrays.sort() (Dual-Pivot Quicksort for primitives
     * typically uses O(log N) auxiliary stack space, but we generally consider
     * primitive sorting in-place O(1) heap space).
     */
    public static int findKthLargestBruteForce(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * ========================================================================
     * PHASE 2.A: OPTIMAL APPROACH (MIN-HEAP for Kth LARGEST)
     * ========================================================================
     * * Detailed Intuition:
     * We don't need to sort the entire array. We only care about the top K elements.
     * If we maintain a Min-Heap of size K, the smallest element among the top K
     * largest elements will always reside at the root. As we iterate through the
     * array, we add each number to the heap. If the heap grows larger than K,
     * we remove the root. By the end, the heap contains exactly the K largest
     * elements, and the root is the smallest of them—which is exactly the Kth largest!
     * * * Complexity Analysis:
     * - Time Complexity: O(N log K). We iterate through N elements. Each insertion
     * and deletion in a heap of size K takes O(log K) time.
     * - Space Complexity: O(K) auxiliary heap space to store the Priority Queue.
     */
    public static int findKthLargestHeap(int[] nums, int k) {
        // Min-Heap by default in Java
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.add(num);
            // Maintain heap size of k
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the smallest element seen so far
            }
        }

        return minHeap.peek(); // The root is the Kth largest element
    }

    /**
     * ========================================================================
     * PHASE 2.B: OPTIMAL APPROACH (MAX-HEAP for Kth SMALLEST)
     * ========================================================================
     * * Detailed Intuition:
     * Flipping the logic: to find the Kth smallest element, we maintain a
     * Max-Heap of size K. The root will hold the largest of the K smallest elements
     * seen so far. If a new element is smaller than the root, it belongs in our
     * bottom K, so we add it and evict the previous maximum.
     * * * Complexity Analysis:
     * - Time Complexity: O(N log K).
     * - Space Complexity: O(K) auxiliary heap space.
     */
    public static int findKthSmallestHeap(int[] nums, int k) {
        // Max-Heap (Reverse order comparator)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        for (int num : nums) {
            maxHeap.add(num);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Evict the largest element among the current K smallest
            }
        }

        return maxHeap.peek();
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (QUICKSELECT)
     * ========================================================================
     * * Detailed Intuition:
     * QuickSelect uses the partition logic from QuickSort. We pick a pivot and
     * partition the array so that larger elements are on the left and smaller on
     * the right. The pivot ends up in its final sorted position. If this position
     * matches `k - 1`, we found our answer! If `k - 1` is less than the pivot index,
     * we recursively search the left side; otherwise, the right side.
     * * * Complexity Analysis:
     * - Time Complexity: O(N) average case. We cut the search space roughly in
     * half each time: N + N/2 + N/4... = 2N = O(N). Worst case is O(N^2) if the
     * array is already sorted and we pick bad pivots (mitigated by random pivot).
     * - Space Complexity: O(1) auxiliary heap space, O(log N) auxiliary stack
     * space for recursion depth.
     */
    public static int findKthLargestQuickSelect(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private static int quickSelect(int[] nums, int left, int right, int kSmallestIndex) {
        if (left == right) {
            return nums[left];
        }

        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        pivotIndex = partition(nums, left, right, pivotIndex);

        if (kSmallestIndex == pivotIndex) {
            return nums[kSmallestIndex];
        } else if (kSmallestIndex < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, kSmallestIndex);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, kSmallestIndex);
        }
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right); // Move pivot to end
        int storeIndex = left;

        for (int i = left; i <= right - 1; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        swap(nums, storeIndex, right); // Move pivot to its final place
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

        class TestCase {
            int[] nums;
            int k;
            int expectedLargest;
            int expectedSmallest;

            TestCase(int[] nums, int k, int expectedLargest, int expectedSmallest) {
                this.nums = nums;
                this.k = k;
                this.expectedLargest = expectedLargest;
                this.expectedSmallest = expectedSmallest;
            }
        }

        TestCase[] testCases = {
                new TestCase(new int[]{3, 2, 1, 5, 6, 4}, 2, 5, 2),                   // Standard Case
                new TestCase(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, 4, 3),          // Duplicates
                new TestCase(new int[]{1}, 1, 1, 1),                                  // Single Element
                new TestCase(new int[]{-1, -5, -3, 0, 2, -10}, 3, -1, -3),            // Negative Numbers
                new TestCase(new int[]{9, 9, 9, 9, 9}, 1, 9, 9)                       // All Identical
        };

        for (int i = 0; i < testCases.length; i++) {
            TestCase tc = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Array = " + Arrays.toString(tc.nums) + ", k = " + tc.k);

            // Copies to prevent QuickSelect/Sorting from mutating the original for subsequent tests
            int[] arr1 = Arrays.copyOf(tc.nums, tc.nums.length);
            int[] arr2 = Arrays.copyOf(tc.nums, tc.nums.length);
            int[] arr3 = Arrays.copyOf(tc.nums, tc.nums.length);
            int[] arr4 = Arrays.copyOf(tc.nums, tc.nums.length);

            // Execute Methods
            int bruteForceRes = findKthLargestBruteForce(arr1, tc.k);
            int heapLargestRes = findKthLargestHeap(arr2, tc.k);
            int heapSmallestRes = findKthSmallestHeap(arr3, tc.k);
            int quickSelectRes = findKthLargestQuickSelect(arr4, tc.k);

            // Print Results
            System.out.println("  [Kth Largest] Expected: " + tc.expectedLargest);
            System.out.println("  -> Phase 1 (Brute):      " + bruteForceRes + (bruteForceRes == tc.expectedLargest ? " ✅" : " ❌"));
            System.out.println("  -> Phase 2.A (Min-Heap): " + heapLargestRes + (heapLargestRes == tc.expectedLargest ? " ✅" : " ❌"));
            System.out.println("  -> Phase 3 (QuickSel):   " + quickSelectRes + (quickSelectRes == tc.expectedLargest ? " ✅" : " ❌"));

            System.out.println("  [Kth Smallest] Expected: " + tc.expectedSmallest);
            System.out.println("  -> Phase 2.B (Max-Heap): " + heapSmallestRes + (heapSmallestRes == tc.expectedSmallest ? " ✅" : " ❌"));
            System.out.println("--------------------------------------------------");
        }
    }
}
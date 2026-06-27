package strivers.heaps.mediumproblems;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an integer array nums and an integer k, return the kth largest element
 * in the array. Note that it is the kth largest element in the sorted order,
 * not the kth distinct element.
 * * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * * Input/Output Formats:
 * Input: An integer array `nums` and an integer `k`.
 * Output: An integer representing the kth largest element.
 * * Examples:
 * Example 1:
 * Input: nums = [1, 2, 3, 4, 5], k = 2
 * Output: 4
 * * Example 2:
 * Input: nums = [-5, 4, 1, 2, -3], k = 5
 * Output: -5
 * * Example 3 (Duplicates):
 * Input: nums = [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4
 * Output: 4
 * ============================================================================
 */
public class KthLargestElement {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Min-Heap / Priority Queue)
     * ========================================================================
     * Detailed Intuition:
     * We only care about the K largest elements. If we maintain a Min-Heap of
     * strictly size K, the elements inside will represent the K largest values
     * seen so far. Because it's a Min-Heap, the smallest among these K largest
     * values rests exactly at the root. As we iterate, if we add a new element
     * and the heap size exceeds K, we pop the root. By the end of the array,
     * the root of the heap is precisely the Kth largest element.
     * * Complexity Analysis:
     * - Time: O(N log K) - We process N elements, and each insertion/deletion
     * in a heap of size K takes log K time.
     * - Space: O(K) - Heap space is utilized to store the K elements. Auxiliary
     * stack space is O(1).
     * ========================================================================
     */
    public static int findKthLargestOptimal(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            // Maintain heap size of exactly K
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Sorting)
     * ========================================================================
     * Detailed Intuition:
     * The "Think it" stage naturally points to sorting. If the array is sorted
     * in ascending order, the largest element is at the end (index N-1), the
     * second largest is at index N-2, and thus the Kth largest element sits
     * exactly at index `N - K`.
     * * Complexity Analysis:
     * - Time: O(N log N) - Dominated by the standard sorting algorithm.
     * - Space: O(1) heap space (if sorted in-place like Quicksort/Heapsort)
     * but O(log N) auxiliary stack space for the sorting recursion.
     * ========================================================================
     */
    public static int findKthLargestBruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Arrays.sort(nums); // Sorts in ascending order
        return nums[nums.length - k];
    }

    /**
     * ========================================================================
     * PHASE 3A: ALTERNATIVE APPROACH (QuickSelect Algorithm)
     * ========================================================================
     * Detailed Intuition:
     * QuickSelect is the theoretical champion for this problem. It uses QuickSort's
     * partition logic. By picking a pivot, we place it in its correct sorted
     * position. If that position exactly matches our target index (N - K), we
     * found our answer! If the target index is less than the pivot index, we
     * only recursively search the left half; otherwise, the right half. This
     * halves the search space on average.
     * * Complexity Analysis:
     * - Time: O(N) average case. O(N^2) in the absolute worst case (which we
     * mitigate using randomized pivot selection).
     * - Space: O(log N) auxiliary stack space for recursion on average. O(1)
     * heap space.
     * ========================================================================
     */
    public static int findKthLargestQuickSelect(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        int targetIndex = nums.length - k; // Kth largest is (N-K)th smallest
        return quickSelect(nums, 0, nums.length - 1, targetIndex);
    }

    private static int quickSelect(int[] nums, int left, int right, int kSmallest) {
        if (left == right) {
            return nums[left];
        }

        // Use a random pivot to avoid O(N^2) worst case on already sorted arrays
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);

        pivotIndex = partition(nums, left, right, pivotIndex);

        if (kSmallest == pivotIndex) {
            return nums[kSmallest];
        } else if (kSmallest < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, kSmallest);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, kSmallest);
        }
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        // Move pivot to the end
        swap(nums, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        // Move pivot to its final place
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
     * PHASE 3B: ALTERNATIVE APPROACH (Java 8 Streams)
     * ========================================================================
     * Detailed Intuition:
     * Using declarative programming, we box the array primitives, sort them in
     * descending order, skip the first (K - 1) elements, and retrieve the first
     * one available. Highly readable, but comes with boxing overhead.
     * * Complexity Analysis:
     * - Time: O(N log N) - Bounded by Timsort.
     * - Space: O(N) - Heap space required for boxing `int` to `Integer` objects.
     * ========================================================================
     */
    public static int findKthLargestStream(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        return Arrays.stream(nums)
                .boxed()
                .sorted((a, b) -> Integer.compare(b, a)) // Sort descending
                .skip(k - 1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Element not found"));
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- TESTING KTH LARGEST ELEMENT ---");

        // Test Case 1: Standard case
        int[] tc1 = {1, 2, 3, 4, 5};
        int k1 = 2; // Expected: 4

        // Test Case 2: Negative numbers
        int[] tc2 = {-5, 4, 1, 2, -3};
        int k2 = 5; // Expected: -5

        // Test Case 3: Duplicates
        int[] tc3 = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k3 = 4; // Expected: 4

        // Test Case 4: Edge Case - Single Element
        int[] tc4 = {99};
        int k4 = 1; // Expected: 99

        // Test Case 5: Zeroes
        int[] tc5 = {0, 0, 0, 0};
        int k5 = 2; // Expected: 0

        System.out.println("TC1 (Standard):  " + findKthLargestOptimal(tc1, k1));
        System.out.println("TC2 (Negatives): " + findKthLargestBruteForce(tc2.clone(), k2));
        System.out.println("TC3 (Duplicates):" + findKthLargestQuickSelect(tc3.clone(), k3));
        System.out.println("TC4 (Single):    " + findKthLargestStream(tc4, k4));
        System.out.println("TC5 (Zeroes):    " + findKthLargestOptimal(tc5, k5));

        System.out.println("\n--- VERIFYING ALL APPROACHES ON TC3 ---");
        System.out.println("Optimal (Heap):  " + findKthLargestOptimal(tc3, k3));
        System.out.println("Brute (Sort):    " + findKthLargestBruteForce(tc3.clone(), k3));
        System.out.println("QuickSelect:     " + findKthLargestQuickSelect(tc3.clone(), k3));
        System.out.println("Stream API:      " + findKthLargestStream(tc3, k3));
    }
}
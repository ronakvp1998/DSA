package strivers.heaps.mediumproblems;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an integer array nums and an integer k, return the kth smallest element
 * in the array. Note that it is the kth smallest element in sorted order, not
 * the kth distinct element.
 * * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * * Input/Output Formats:
 * Input: An integer array `nums` and an integer `k`.
 * Output: An integer representing the kth smallest element.
 * * Examples:
 * Example 1:
 * Input: nums = [7, 10, 4, 3, 20, 15], k = 3
 * Output: 7
 * Explanation: Sorted array is [3, 4, 7, 10, 15, 20]. The 3rd smallest is 7.
 * * Example 2:
 * Input: nums = [7, 10, 4, 20, 15], k = 4
 * Output: 15
 * * Example 3 (Duplicates):
 * Input: nums = [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4
 * Output: 3
 * ============================================================================
 */
public class KthSmallestElement {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Max-Heap / Priority Queue)
     * ========================================================================
     * Detailed Intuition:
     * To find the Kth SMALLEST element, we intuitively maintain a MAX-HEAP of
     * exactly size K. As we iterate through the array, we push elements into
     * our Max-Heap. If the heap grows larger than K, we pop the root (which is
     * the largest element currently in the heap).
     * Why does this work? By continuously removing the largest elements when
     * our capacity exceeds K, the heap is forced to retain only the K smallest
     * elements seen so far. At the end of the array, the root of this Max-Heap
     * will represent the "largest of the K smallest elements," which is exactly
     * the Kth smallest element overall.
     * * Complexity Analysis:
     * - Time: O(N log K) - We process all N elements. Inserting into and popping
     * from a heap of size K takes O(log K) time.
     * - Space: O(K) - Heap space is utilized to store a maximum of K elements
     * in the PriorityQueue. Auxiliary stack space is O(1) as it is iterative.
     * ========================================================================
     */
    public static int findKthSmallestOptimal(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        // Java's PriorityQueue is a Min-Heap by default.
        // We use Collections.reverseOrder() to make it a Max-Heap.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : nums) {
            maxHeap.offer(num);
            // Maintain a strict heap size of K
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // The root of the max-heap is the kth smallest element
        return maxHeap.peek();
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Sorting)
     * ========================================================================
     * Detailed Intuition:
     * The "Think it" stage naturally leads to the most straightforward definition
     * of the problem. If we sort the array in ascending order, the smallest
     * element is at index 0, the second smallest at index 1, and the Kth
     * smallest element will be exactly at index (K - 1).
     * * Complexity Analysis:
     * - Time: O(N log N) - Dominated by the standard sorting algorithm (Dual-Pivot
     * Quicksort for primitive arrays in Java). Inefficient if K is very small
     * compared to N.
     * - Space: O(1) heap space (since sorting is done in-place) but O(log N)
     * auxiliary stack space for the sorting recursion tree.
     * ========================================================================
     */
    public static int findKthSmallestBruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Arrays.sort(nums); // Sorts in ascending order
        return nums[k - 1]; // 0-indexed, so kth element is at k-1
    }

    /**
     * ========================================================================
     * PHASE 3A: ALTERNATIVE APPROACH (QuickSelect Algorithm)
     * ========================================================================
     * Detailed Intuition:
     * QuickSelect is the theoretical optimal algorithm for the Kth order
     * statistic. It leverages the partition subroutine from QuickSort. By
     * choosing a random pivot and partitioning the array, we place the pivot
     * in its absolute correct sorted position. If this position matches index
     * (K - 1), we have found our answer. If (K - 1) is less than the pivot's
     * index, we discard the right half and recurse exclusively on the left;
     * otherwise, we recurse on the right.
     * * Complexity Analysis:
     * - Time: O(N) average case. We halve the search space on average each time.
     * O(N^2) in the strict mathematical worst case, heavily mitigated by
     * randomized pivot selection.
     * - Space: O(log N) auxiliary stack space for recursion on average. O(1)
     * extra heap space as partitioning is performed in-place.
     * ========================================================================
     */
    public static int findKthSmallestQuickSelect(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        int targetIndex = k - 1;
        return quickSelect(nums, 0, nums.length - 1, targetIndex);
    }

    private static int quickSelect(int[] nums, int left, int right, int kSmallest) {
        if (left == right) {
            return nums[left];
        }

        // Randomized pivot to defend against O(N^2) worst-case on sorted arrays
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
        // Move pivot out of the way to the end
        swap(nums, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        // Move pivot to its final definitive place
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
     * Utilizing modern declarative Java, we can build a pipeline that sorts
     * the array in ascending order, skips the first (K - 1) elements, and
     * immediately grabs the next available element. It is incredibly concise
     * but comes with the performance overhead of object boxing.
     * * Complexity Analysis:
     * - Time: O(N log N) - Constrained by the underlying Timsort implementation.
     * - Space: O(N) - Heap space overhead required for boxing primitive `int`
     * elements to `Integer` objects to use standard stream operations.
     * ========================================================================
     */
    public static int findKthSmallestStream(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        return Arrays.stream(nums)
                .sorted()
                .skip(k - 1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Element not found"));
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Validates all implementations against standard arrays, duplicate values,
     * negative numbers, and boundary sizes.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- TESTING KTH SMALLEST ELEMENT ---");

        // Test Case 1: Standard case
        int[] tc1 = {7, 10, 4, 3, 20, 15};
        int k1 = 3; // Expected: 7 (Sorted: 3, 4, 7, 10, 15, 20)

        // Test Case 2: Standard case variant
        int[] tc2 = {7, 10, 4, 20, 15};
        int k2 = 4; // Expected: 15 (Sorted: 4, 7, 10, 15, 20)

        // Test Case 3: Duplicates
        int[] tc3 = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k3 = 4; // Expected: 3 (Sorted: 1, 2, 2, 3, 3, 4, 5, 5, 6)

        // Test Case 4: Edge Case - Single Element
        int[] tc4 = {99};
        int k4 = 1; // Expected: 99

        // Test Case 5: Zeroes and Negatives
        int[] tc5 = {0, -5, 0, 4, -2};
        int k5 = 2; // Expected: -2 (Sorted: -5, -2, 0, 0, 4)

        System.out.println("TC1 (Standard):  " + findKthSmallestOptimal(tc1.clone(), k1));
        System.out.println("TC2 (Standard):  " + findKthSmallestBruteForce(tc2.clone(), k2));
        System.out.println("TC3 (Duplicates):" + findKthSmallestQuickSelect(tc3.clone(), k3));
        System.out.println("TC4 (Single):    " + findKthSmallestStream(tc4.clone(), k4));
        System.out.println("TC5 (Negatives): " + findKthSmallestOptimal(tc5.clone(), k5));

        System.out.println("\n--- VERIFYING ALL APPROACHES ON TC3 (Duplicates) ---");
        System.out.println("Optimal (Heap):  " + findKthSmallestOptimal(tc3.clone(), k3));
        System.out.println("Brute (Sort):    " + findKthSmallestBruteForce(tc3.clone(), k3));
        System.out.println("QuickSelect:     " + findKthSmallestQuickSelect(tc3.clone(), k3));
        System.out.println("Stream API:      " + findKthSmallestStream(tc3.clone(), k3));
    }
}
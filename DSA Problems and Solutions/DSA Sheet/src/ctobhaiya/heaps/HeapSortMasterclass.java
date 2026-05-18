package ctobhaiya.heaps;

/**
 * ============================================================================
 * 🚀 MASTERCLASS: HEAP SORT (LeetCode #912: Sort an Array)
 * ============================================================================
 * * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 * * ----------------------------------------------------------------------------
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an array of integers `nums`, sort the array in ascending order and return it.
 * You must solve the problem without using any built-in functions in O(n log(n))
 * time complexity and with the smallest space complexity possible.
 * * Constraints:
 * - 1 <= nums.length <= 5 * 10^4
 * - -5 * 10^4 <= nums[i] <= 5 * 10^4
 * * Input/Output Formats:
 * Input: int[] nums
 * Output: int[] sorted_nums
 * * Examples:
 * Example 1:
 * Input: nums = [5,2,3,1]
 * Output: [1,2,3,5]
 * Explanation: After sorting the array, the positions of some numbers are not changed
 * (for example, 2 and 3), while the positions of other numbers are   changed (5 and 1).
 * * Example 2:
 * Input: nums = [5,1,1,2,0,0]
 * Output: [0,0,1,1,2,5]
 * Explanation: Note that the values of nums are not necessarily unique.
 * * Conceptual Visualization (Max-Heap Array Representation for [5,2,3,1]):
 * Initial Array: [5, 2, 3, 1]
 * * Tree View:
 *        5
 *      /   \
 *     2     3
 *    /
 *   1
 * (This happens to already be a valid Max-Heap. In practice, we build it bottom-up).
 * * ----------------------------------------------------------------------------
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP
 * ----------------------------------------------------------------------------
 * Phase 1: Brute Force Approach (Using extra space) - The "Think it" stage.
 * - Approach: Use Java's built-in PriorityQueue (Min-Heap) to store all elements,
 * then extract them one by one to form the sorted array.
 * - Why it's phase 1: It demonstrates an understanding of the heap data structure
 * conceptually, but fails the "smallest space complexity possible" constraint
 * required by optimal standard algorithms.
 * * Phase 2: Optimal Approach (In-Place Heap Sort) - The "Perfect it" stage.
 * - Approach:
 * Step 1: Build a Max-Heap in-place from the input array. We do this by iterating
 * from the last non-leaf node down to the root, sinking nodes down.
 * Step 2: Swap the root (maximum element) with the last element of the heap.
 * Step 3: Reduce the "heap size" boundary by 1.
 * Step 4: "Heapify" (sink down) the new root to restore the Max-Heap property.
 * Step 5: Repeat until the heap size is 1. The array is now sorted in ascending order.
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class HeapSortMasterclass {

    /**
     * ========================================================================
     * PHASE 1: NAIVE HEAP SORT (EXTRA SPACE)
     * ========================================================================
     * * Detailed Intuition:
     * The simplest way to sort using heaps is to toss everything into a Min-Heap.
     * A Min-Heap guarantees that the smallest element is always at the top. By
     * repeatedly polling the top element, we retrieve the elements in ascending order.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Inserting N elements takes O(N log N) time,
     * and extracting N elements takes O(N log N) time.
     * - Space Complexity: O(N) heap space. The PriorityQueue requires separate
     * memory proportional to the number of elements in the array. This violates
     * strict in-place requirements. Auxiliary stack space is O(1).
     */
    public static int[] sortArrayNaive(int[] nums) {
        if (nums == null || nums.length <= 1) return nums;

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.add(num);
        }

        int[] result = new int[nums.length];
        int index = 0;
        while (!minHeap.isEmpty()) {
            result[index++] = minHeap.poll();
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL IN-PLACE HEAP SORT
     * ========================================================================
     * * Detailed Intuition:
     * To achieve O(1) space, we must view the input array itself as a complete binary tree.
     * For any node at index `i`:
     * - Left child is at `2*i + 1`
     * - Right child is at `2*i + 2`
     * * We use a MAX-HEAP instead of a Min-Heap. Why? Because sorting in ascending order
     * means the largest elements should be placed at the end of the array. A Max-Heap
     * gives us O(1) access to the maximum element (at index 0). We can swap index 0
     * with the end of the array, effectively putting the max element in its final sorted
     * position, and then logically "shrink" the heap and re-heapify.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). Building the initial max-heap is O(N).
     * Extracting N elements and calling heapify (which takes O(log N)) each time
     * results in O(N log N) time.
     * - Space Complexity: O(1) auxiliary heap space. O(log N) stack space if heapify
     * is implemented recursively, but here we can keep it strictly O(log N) or O(1)
     * if done iteratively. The recursion depth of heapify is bounded by tree height log(N).
     * (Note: Below implementation uses recursion for clarity, space is O(log N) stack space.
     * Iterative heapify would be strictly O(1)).
     */
    public static int[] sortArrayOptimal(int[] nums) {
        if (nums == null || nums.length <= 1) return nums;

        int n = nums.length;

        // Step 1: Build a Max-Heap.
        // We start from the last non-leaf node, which is at index (n / 2) - 1.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // Step 2: Extract elements from the heap one by one.
        // We start from the last index and move backwards.
        for (int i = n - 1; i > 0; i--) {
            // Swap the current root (max element) with the end element
            swap(nums, 0, i);

            // Step 3 & 4: Call max heapify on the reduced heap.
            // The heap size is now 'i' (effectively hiding the sorted elements at the end).
            heapify(nums, i, 0);
        }

        return nums;
    }

    /**
     * Sinks a node down the tree to maintain the Max-Heap property.
     * * @param nums The array representing the heap.
     * @param n    The current size of the heap boundary.
     * @param i    The index of the node to heapify.
     */
    private static void heapify(int[] nums, int n, int i) {
        int largest = i;          // Initialize largest as root
        int left = 2 * i + 1;     // Left child index
        int right = 2 * i + 2;    // Right child index

        // If left child is larger than root
        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        // If largest is not the root, swap and recursively heapify the affected sub-tree
        if (largest != i) {
            swap(nums, i, largest);
            heapify(nums, n, largest);
        }
    }

    /**
     * Utility method to swap two elements in an array.
     */
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
        System.out.println("🚀 Executing Heap Sort Masterclass Testing Suite...\n");

        int[][] testCases = {
                {5, 2, 3, 1},                      // Standard Case
                {5, 1, 1, 2, 0, 0},                // Duplicates & Zeros
                {1, 2, 3, 4, 5},                   // Already Sorted
                {9, 8, 7, 6, 5, 4, 3, 2, 1},       // Reverse Sorted
                {42},                              // Single Element
                {},                                // Empty Array
                {-10, 5, 0, -3, 8, 2, -100}        // Negative Numbers
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            int[] original = testCases[i];
            System.out.println("Original: " + Arrays.toString(original));

            // Test Phase 1 (Naive)
            int[] naiveInput = Arrays.copyOf(original, original.length);
            int[] naiveResult = sortArrayNaive(naiveInput);

            // Test Phase 2 (Optimal)
            int[] optimalInput = Arrays.copyOf(original, original.length);
            sortArrayOptimal(optimalInput);

            System.out.println("Phase 1 (Naive):   " + Arrays.toString(naiveResult));
            System.out.println("Phase 2 (Optimal): " + Arrays.toString(optimalInput));

            // Validation
            boolean isCorrect = Arrays.equals(naiveResult, optimalInput);
            System.out.println("Result Match:      " + (isCorrect ? "✅ PASS" : "❌ FAIL"));
            System.out.println("--------------------------------------------------");
        }
    }
}
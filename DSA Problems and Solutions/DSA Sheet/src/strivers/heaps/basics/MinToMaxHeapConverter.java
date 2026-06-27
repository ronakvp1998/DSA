package strivers.heaps.basics;

import java.util.Arrays;
import java.util.Collections;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array of integers representing a Min Heap, convert it into a Max Heap
 * in-place. A Max Heap is a complete binary tree where every parent node is
 * greater than or equal to its child nodes (nums[parent] >= nums[child]).
 * * Constraints:
 * - 0 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * Input/Output Formats:
 * Input: An integer array `arr` (representing a valid Min Heap).
 * Output: The array modified in-place to represent a valid Max Heap (or returned
 * as a new array for approaches where in-place mutation isn't feasible).
 * * Examples:
 * Example 1:
 * Input: [3, 4, 8, 9, 10]
 * Output: [10, 9, 8, 4, 3] (One of the valid Max Heap configurations)
 * * Example 2:
 * Input: [1, 2, 3, 4, 5, 6, 7]
 * Output: [7, 5, 6, 4, 2, 1, 3] (One of the valid Max Heap configurations)
 * ============================================================================
 */
public class MinToMaxHeapConverter {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Bottom-Up Heapify / Floyd's Algorithm)
     * ========================================================================
     * Detailed Intuition:
     * Instead of inserting elements one by one, we use Floyd's "Heapify" approach.
     * We start from the last non-leaf node (the last parent in the tree), which
     * is found at index `(n - 2) / 2`, and move upwards to the root. For each node,
     * we perform a "Shift Down" (Max-Heapify) operation. This ensures that the
     * subtree rooted at the current node satisfies the Max Heap property. Because
     * leaf nodes are trivially valid heaps, we don't need to process them. This
     * bottom-up approach is mathematically proven to operate in linear time.
     * * Complexity Analysis:
     * - Time: O(N) - Where N is the number of elements. Though `maxHeapify` takes
     * O(log N) time, the number of nodes at each height decreases exponentially.
     * The sum of the work done across all nodes bounds to O(N).
     * - Space: O(1) - The array is modified entirely in-place. We use an iterative
     * approach for the shift-down operation, meaning zero auxiliary stack space
     * is consumed. Total heap space is O(1) auxiliary.
     * ========================================================================
     */
    public static void convertOptimal(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;
        // Start from the last internal node and move up to the root
        for (int i = (n - 2) / 2; i >= 0; i--) {
            maxHeapifyIterative(arr, n, i);
        }
    }

    /**
     * Iterative Shift-Down to maintain strictly O(1) space.
     */
    private static void maxHeapifyIterative(int[] arr, int n, int i) {
        while (true) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && arr[left] > arr[largest]) {
                largest = left;
            }
            if (right < n && arr[right] > arr[largest]) {
                largest = right;
            }

            // If the largest is not the current node, swap and continue shifting down
            if (largest != i) {
                swap(arr, i, largest);
                i = largest;
            } else {
                break; // Max-Heap property is satisfied for this subtree
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (New Heap Construction)
     * ========================================================================
     * Detailed Intuition:
     * The "Think it" stage naturally leads to building a brand new array. We
     * iterate through the given Min Heap and insert each element into a new
     * array. After each insertion, we perform a standard "Shift Up" operation
     * to ensure the new array maintains the Max Heap property.
     * * Complexity Analysis:
     * - Time: O(N log N) - We process N elements, and each insertion requires
     * a "shift up" operation which takes O(log N) time in the worst case.
     * - Space: O(N) - We allocate a brand new array of size N in heap memory.
     * Auxiliary stack space is O(1) due to iterative shift-up.
     * ========================================================================
     */
    public static int[] convertBruteForce(int[] arr) {
        if (arr == null) return null;
        if (arr.length <= 1) return arr.clone();

        int[] maxHeap = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            maxHeap[i] = arr[i];
            shiftUp(maxHeap, i);
        }

        return maxHeap;
    }

    private static void shiftUp(int[] arr, int index) {
        int parent = (index - 1) / 2;
        // While we are not at the root and the current node is greater than its parent
        while (index > 0 && arr[parent] < arr[index]) {
            swap(arr, index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Sorting via Java 8 Stream API)
     * ========================================================================
     * Detailed Intuition:
     * A strictly descending sorted array inherently satisfies the Max Heap
     * property (every parent is larger than both children because the parent
     * appears earlier in the array). Using Java 8 Streams, we can box the array,
     * sort it in reverse order, and unbox it back. While visually elegant and
     * bug-free, it is strictly suboptimal for this specific algorithmic problem.
     * * Complexity Analysis:
     * - Time: O(N log N) - Bounded by the underlying Timsort algorithm.
     * - Space: O(N) - Boxing to Integer objects and generating a new array consumes
     * O(N) heap space overhead.
     * ========================================================================
     */
    public static int[] convertAlternativeStream(int[] arr) {
        if (arr == null) return null;

        return Arrays.stream(arr)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /**
     * Helper method to swap elements in an array.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        // Test Case 1: Standard Min Heap
        int[] tc1Optimal = {3, 4, 8, 9, 10};
        int[] tc1Brute = tc1Optimal.clone();

        // Test Case 2: Larger Min Heap
        int[] tc2Optimal = {1, 2, 3, 4, 5, 6, 7};

        // Test Case 3: Edge Case - Single Element
        int[] tc3Single = {42};

        // Test Case 4: Edge Case - Duplicate elements & Zeroes
        int[] tc4Dupes = {0, 0, 0, 5, 5, 10, 10};

        System.out.println("--- TESTING BRUTE FORCE APPROACH ---");
        System.out.println("Input: " + Arrays.toString(tc1Brute));
        System.out.println("Output: " + Arrays.toString(convertBruteForce(tc1Brute)));

        System.out.println("\n--- TESTING OPTIMAL APPROACH (In-Place) ---");

        System.out.println("Input TC1: " + Arrays.toString(tc1Optimal));
        convertOptimal(tc1Optimal);
        System.out.println("Output TC1: " + Arrays.toString(tc1Optimal));

        System.out.println("\nInput TC2: " + Arrays.toString(tc2Optimal));
        convertOptimal(tc2Optimal);
        System.out.println("Output TC2: " + Arrays.toString(tc2Optimal));

        System.out.println("\nInput TC3 (Single): " + Arrays.toString(tc3Single));
        convertOptimal(tc3Single);
        System.out.println("Output TC3: " + Arrays.toString(tc3Single));

        System.out.println("\nInput TC4 (Duplicates/Zeroes): " + Arrays.toString(tc4Dupes));
        convertOptimal(tc4Dupes);
        System.out.println("Output TC4: " + Arrays.toString(tc4Dupes));

        System.out.println("\n--- TESTING ALTERNATIVE STREAM APPROACH ---");
        int[] tcStream = {3, 4, 8, 9, 10};
        System.out.println("Input: " + Arrays.toString(tcStream));
        System.out.println("Output: " + Arrays.toString(convertAlternativeStream(tcStream)));
    }
}
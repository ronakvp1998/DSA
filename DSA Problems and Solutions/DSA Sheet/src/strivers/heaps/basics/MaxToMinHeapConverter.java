package strivers.heaps.basics;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array 'arr' of size N representing a valid Max Heap, convert it
 * into a valid Min Heap in-place.
 * * Constraints:
 * - 1 <= N <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * - Must operate in-place for the optimal approach.
 * * Input/Output Formats:
 * Input: An integer array representing a Max Heap.
 * Output: The same array modified to represent a valid Min Heap.
 * * Examples:
 * Example 1:
 * Input:  [9, 4, 7, 1, -2, 6, 5]
 * Output: [-2, 1, 5, 9, 4, 6, 7]
 * * Example 2:
 * Input:  [10, 8, 9, 7, 6, 5, 4]
 * Output: [4, 6, 5, 7, 8, 10, 9] (Note: Multiple valid min heaps exist,
 * this depends on the in-place algorithm)
 * * Conceptual Visualization (Before and After for Example 1):
 * * MAX HEAP (Input)                 MIN HEAP (After Optimal Conversion)
 * 9                                       -2
 * /   \                                    /   \
 * 4       7             ====>              1       5
 * / \     / \                              / \     / \
 * 1  -2   6   5                            9   4   6   7
 * * Array: [9, 4, 7, 1, -2, 6, 5]           Array: [-2, 1, 5, 9, 4, 6, 7]
 * ============================================================================
 */
public class MaxToMinHeapConverter {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Bottom-Up Min-Heapify / Floyd's Algorithm)
     * ========================================================================
     * Detailed Intuition:
     * The most efficient way to build any heap is Floyd's Build-Heap algorithm.
     * Since leaf nodes have no children, they trivially satisfy the min-heap
     * property. We start from the last non-leaf node (index `(n - 2) / 2`)
     * and move upwards to the root. For each node, we perform a "Shift Down"
     * (min-heapify) operation. This ensures that every subtree processed
     * becomes a valid min-heap, culminating in the entire tree being a valid
     * min-heap once the root is processed.
     * * Complexity Analysis:
     * - Time: O(N) - Although `minHeapify` takes O(log N) in the worst case,
     * most nodes are at the bottom of the tree where they do very little work.
     * The mathematical sum of the heights of all nodes bounds strictly to O(N).
     * - Space: O(1) - The array is modified entirely in-place. We use an iterative
     * approach for the shift-down operation, meaning zero auxiliary stack space
     * is consumed. Total auxiliary space is strictly O(1).
     * ========================================================================
     */
    public static void convertOptimal(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;
        // Start from the last internal node and move up to the root
        for (int i = (n - 2) / 2; i >= 0; i--) {
            minHeapifyIterative(arr, n, i);
        }
    }

    /**
     * Iterative Shift-Down to maintain strictly O(1) space.
     */
    private static void minHeapifyIterative(int[] arr, int n, int i) {
        while (true) {
            int smallest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && arr[left] < arr[smallest]) {
                smallest = left;
            }
            if (right < n && arr[right] < arr[smallest]) {
                smallest = right;
            }

            // If the smallest is not the current node, swap and continue shifting down
            if (smallest != i) {
                swap(arr, i, smallest);
                i = smallest;
            } else {
                break; // Min-Heap property is satisfied for this subtree
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Sorting)
     * ========================================================================
     * Detailed Intuition:
     * The "Think it" stage allows us to step back and look at the definition
     * of a Min Heap: every parent node is less than or equal to its children
     * (arr[i] <= arr[2i+1] and arr[i] <= arr[2i+2]). A strictly ascending sorted
     * array naturally perfectly satisfies this condition. So, if we just sort
     * the Max Heap array, it instantly becomes a valid Min Heap.
     * * Complexity Analysis:
     * - Time: O(N log N) - Dominated by the Dual-Pivot Quicksort algorithm
     * used by `Arrays.sort()` for primitive arrays.
     * - Space: O(log N) - Auxiliary stack space used by the sorting recursion
     * tree. O(1) heap space since sorting is done in-place.
     * ========================================================================
     */
    public static void convertBruteForce(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        Arrays.sort(arr);
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (PriorityQueue / Extra Space)
     * ========================================================================
     * Detailed Intuition:
     * If in-place modification is not strictly enforced, we can leverage
     * existing data structures. We insert all elements of the Max Heap into a
     * standard Java PriorityQueue (which is inherently a Min Heap). Then, we
     * simply poll the elements back into the original array.
     * * Complexity Analysis:
     * - Time: O(N log N) - Inserting N elements into a PriorityQueue takes
     * O(N log N). Extracting them takes another O(N log N).
     * - Space: O(N) - We allocate O(N) heap space for the PriorityQueue's
     * internal dynamic array. Auxiliary stack space is O(1).
     * ========================================================================
     */
    public static void convertAlternative(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Add all elements to the Min Heap
        for (int num : arr) {
            minHeap.offer(num);
        }

        // Poll elements back to the array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = minHeap.poll();
        }
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
        // Test Case 1: Standard Max Heap
        int[] tc1Optimal = {9, 4, 7, 1, -2, 6, 5};
        int[] tc1Brute = tc1Optimal.clone();
        int[] tc1Alt = tc1Optimal.clone();

        // Test Case 2: Larger Max Heap
        int[] tc2Optimal = {10, 8, 9, 7, 6, 5, 4};

        // Test Case 3: Edge Case - Single Element
        int[] tc3Single = {42};

        // Test Case 4: Edge Case - Duplicate elements & Zeroes
        int[] tc4Dupes = {10, 10, 5, 5, 0, 0, 0};

        System.out.println("--- TESTING OPTIMAL APPROACH (In-Place Floyd's) ---");
        System.out.println("Input TC1:  " + Arrays.toString(tc1Optimal));
        convertOptimal(tc1Optimal);
        System.out.println("Output TC1: " + Arrays.toString(tc1Optimal));

        System.out.println("\nInput TC2:  " + Arrays.toString(tc2Optimal));
        convertOptimal(tc2Optimal);
        System.out.println("Output TC2: " + Arrays.toString(tc2Optimal));

        System.out.println("\nInput TC3 (Single): " + Arrays.toString(tc3Single));
        convertOptimal(tc3Single);
        System.out.println("Output TC3: " + Arrays.toString(tc3Single));

        System.out.println("\nInput TC4 (Duplicates/Zeroes): " + Arrays.toString(tc4Dupes));
        convertOptimal(tc4Dupes);
        System.out.println("Output TC4: " + Arrays.toString(tc4Dupes));

        System.out.println("\n--- TESTING BRUTE FORCE APPROACH (Sorting) ---");
        System.out.println("Input:  " + Arrays.toString(tc1Brute));
        convertBruteForce(tc1Brute);
        System.out.println("Output: " + Arrays.toString(tc1Brute));

        System.out.println("\n--- TESTING ALTERNATIVE APPROACH (PriorityQueue) ---");
        System.out.println("Input:  " + Arrays.toString(tc1Alt));
        convertAlternative(tc1Alt);
        System.out.println("Output: " + Arrays.toString(tc1Alt));
    }
}
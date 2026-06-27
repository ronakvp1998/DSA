package strivers.heaps.mediumproblems;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement: Given an array arr[] and a number k .
 * The array is sorted in a way that every element is at max k distance away from it sorted position.
 * It means if we completely sort the array,
 * then the index of the element can go from i - k to i + k where i is index in the given array.
 * Our task is to completely sort the array.
 * * Logic: The element that should be at index 0 must be somewhere in the
 * range [0, k].
 * * Constraints:
 * - 1 <= arr.length <= 10^5
 * - 0 <= k < arr.length
 * - -10^9 <= arr[i] <= 10^9
 * * Input/Output Formats:
 * Input: An integer array `arr` and an integer `k`.
 * Output: The `arr` modified in-place to be completely sorted.
 * * Examples:
 * Example 1:
 * Input: arr = [6, 5, 3, 2, 8, 10, 9], k = 3
 * Output: [2, 3, 5, 6, 8, 9, 10]
 * * Example 2:
 * Input: arr = [2, 1, 4, 3, 6, 5], k = 1
 * Output: [1, 2, 3, 4, 5, 6]
 * ============================================================================
 */
public class KSortedArray {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Min-Heap / Priority Queue)
     * ========================================================================
     * Detailed Intuition:
     * Since every element is at most 'k' positions away from its final sorted
     * position, the absolute smallest element in the entire array MUST exist
     * within the first (k + 1) elements. We can maintain a Min-Heap of size
     * (k + 1). By continuously adding the next element from the array and
     * popping the minimum element from the heap, we guarantee that we are
     * placing the correct globally smallest available element at each step.
     * * Complexity Analysis:
     * - Time: O(N log K) - We insert N elements into a heap of maximum size K.
     * Each insertion and extraction takes O(log K) time.
     * - Space: O(K) - Heap space is utilized to store at most K + 1 elements.
     * Auxiliary stack space is O(1) as the process is iterative.
     * ========================================================================
     */
    public static void sortOptimal(int[] arr, int k) {
        if (arr == null || arr.length <= 1 || k == 0) return;

        // Min-Heap to store up to k + 1 elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Add the first k + 1 elements to the Min-Heap
        int n = arr.length;
        int limit = Math.min(n, k + 1);
        for (int i = 0; i < limit; i++) {
            minHeap.offer(arr[i]);
        }

        int index = 0;
        // Process the remaining elements in the array
        for (int i = limit; i < n; i++) {
            arr[index++] = minHeap.poll();
            minHeap.offer(arr[i]);
        }

        // Extract the remaining elements from the heap
        while (!minHeap.isEmpty()) {
            arr[index++] = minHeap.poll();
        }
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Standard Sorting)
     * ========================================================================
     * Detailed Intuition:
     * The "Think it" stage involves ignoring the special "K-sorted" property
     * entirely. If we just treat it as a standard unsorted array, we can use
     * a generic sorting algorithm. This works perfectly but leaves performance
     * on the table since it doesn't capitalize on the pre-existing partial order.
     * * Complexity Analysis:
     * - Time: O(N log N) - Dominated by the Dual-Pivot Quicksort algorithm
     * used by Arrays.sort() for primitive types.
     * - Space: O(log N) - Auxiliary stack space used by the recursive sorting
     * calls. Heap space is O(1) as sorting is in-place.
     * ========================================================================
     */
    public static void sortBruteForce(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        Arrays.sort(arr);
    }

    /**
     * ========================================================================
     * PHASE 3A: ALTERNATIVE APPROACH (Insertion Sort)
     * ========================================================================
     * Detailed Intuition:
     * Insertion Sort is notoriously slow (O(N^2)) for highly unsorted arrays.
     * However, its secret superpower is that it runs incredibly fast on
     * "nearly sorted" arrays. Since no element has to shift more than 'k'
     * places to the left, the inner `while` loop of Insertion Sort is strictly
     * bounded by 'k'. For very small values of 'k' (e.g., k = 1 or 2), this
     * approach can actually outperform the Min-Heap due to lower constant overhead.
     * * Complexity Analysis:
     * - Time: O(N * K) - The outer loop runs N times, and the inner loop runs
     * at most K times. If K is small, this approaches O(N).
     * - Space: O(1) - Entirely in-place with no auxiliary data structures.
     * ========================================================================
     */
    public static void sortInsertion(int[] arr, int k) {
        if (arr == null || arr.length <= 1 || k == 0) return;

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // Shift elements to the right. This loop runs at most 'k' times.
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * ========================================================================
     * PHASE 3B: ALTERNATIVE APPROACH (Java 8 Stream API)
     * ========================================================================
     * Detailed Intuition:
     * For purely functional or declarative codebases, we can achieve the brute
     * force sort using Streams. We box, sort, and collect back to an array.
     * While clean and readable, it creates a new array rather than sorting
     * in-place, making it suboptimal for this specific algorithm.
     * * Complexity Analysis:
     * - Time: O(N log N) - Underlying Timsort algorithm.
     * - Space: O(N) - Heap space required for boxing to Integer objects and
     * creating the new array output.
     * ========================================================================
     */
    public static int[] sortStream(int[] arr) {
        if (arr == null || arr.length <= 1) return arr;
        return Arrays.stream(arr).sorted().toArray();
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- TESTING K-SORTED ARRAY ALGORITHMS ---");

        // Test Case 1: Standard case
        int[] tc1Optimal = {6, 5, 3, 2, 8, 10, 9};
        int k1 = 3;

        // Test Case 2: Very small K (Insertion Sort shines here)
        int[] tc2Insertion = {2, 1, 4, 3, 6, 5};
        int k2 = 1;

        // Test Case 3: K is larger than the array length
        int[] tc3Optimal = {5, 4, 3, 2, 1};
        int k3 = 10;

        // Test Case 4: Already sorted (K = 0)
        int[] tc4Brute = {1, 2, 3, 4, 5};
        int k4 = 0;

        // Test Case 5: Duplicates
        int[] tc5Stream = {3, 2, 3, 1, 4, 5, 5, 4};
        int k5 = 3;

        System.out.println("\n--- Phase 1: Optimal (Min-Heap) ---");
        System.out.println("Input TC1:  " + Arrays.toString(tc1Optimal) + ", k=" + k1);
        sortOptimal(tc1Optimal, k1);
        System.out.println("Output TC1: " + Arrays.toString(tc1Optimal));

        System.out.println("\nInput TC3 (Large K): " + Arrays.toString(tc3Optimal) + ", k=" + k3);
        sortOptimal(tc3Optimal, k3);
        System.out.println("Output TC3: " + Arrays.toString(tc3Optimal));

        System.out.println("\n--- Phase 3A: Alternative (Insertion Sort) ---");
        System.out.println("Input TC2:  " + Arrays.toString(tc2Insertion) + ", k=" + k2);
        sortInsertion(tc2Insertion, k2);
        System.out.println("Output TC2: " + Arrays.toString(tc2Insertion));

        System.out.println("\n--- Phase 2: Brute Force (Arrays.sort) ---");
        System.out.println("Input TC4 (Sorted): " + Arrays.toString(tc4Brute) + ", k=" + k4);
        sortBruteForce(tc4Brute);
        System.out.println("Output TC4: " + Arrays.toString(tc4Brute));

        System.out.println("\n--- Phase 3B: Alternative (Stream API) ---");
        System.out.println("Input TC5 (Dupes): " + Arrays.toString(tc5Stream) + ", k=" + k5);
        int[] streamResult = sortStream(tc5Stream);
        System.out.println("Output TC5: " + Arrays.toString(streamResult));
    }
}
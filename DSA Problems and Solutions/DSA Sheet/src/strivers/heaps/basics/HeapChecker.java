package strivers.heaps.basics;

import java.util.stream.IntStream;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement:
 * Given an array of integers representing a complete binary tree (0-indexed),
 * write functions to determine if the array represents a valid Min-Heap or a
 * valid Max-Heap.
 * * In a 0-indexed array representation of a complete binary tree:
 * - The left child of a node at index 'i' is at index '2*i + 1'.
 * - The right child of a node at index 'i' is at index '2*i + 2'.
 * * - A Max-Heap requires that the value of any internal node is greater than
 * or equal to the values of its children.
 * - A Min-Heap requires that the value of any internal node is less than
 * or equal to the values of its children.
 * * Constraints:
 * - 0 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * Input/Output Formats:
 * Input: An integer array `arr`.
 * Output: Boolean `true` if it satisfies the heap property, `false` otherwise.
 * * Examples:
 * Example 1 (Max-Heap):
 * Input: [90, 15, 10, 7, 12, 2]
 * Output: isMaxHeap = true, isMinHeap = false
 * * Example 2 (Min-Heap):
 * Input: [2, 10, 15, 90, 12, 11]
 * Output: isMaxHeap = false, isMinHeap = true
 * * Example 3 (Neither):
 * Input: [9, 15, 10, 7, 12, 2]
 * Output: isMaxHeap = false, isMinHeap = false
 * ============================================================================
 */
public class HeapChecker {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Iterative)
     * ========================================================================
     * Detailed Intuition:
     * A complete binary tree represented as an array has its internal (non-leaf)
     * nodes ranging from index 0 to (n/2) - 1. Leaf nodes do not have children,
     * so they inherently satisfy the heap property. The most optimal way to
     * verify a heap is to iterate strictly through the internal nodes and compare
     * each to its left and right children. If any node violates the invariant,
     * we return false immediately.
     * * Complexity Analysis:
     * Time: O(N) - Where N is the number of elements in the array. We visit half
     * of the elements exactly once.
     * Space: O(1) - No auxiliary data structures or call stacks are used; strictly
     * constant space.
     * ========================================================================
     */
    public static boolean isMaxHeapOptimal(int[] arr) {
        if (arr == null || arr.length <= 1) return true;
        int n = arr.length;

        // Only iterate up to the last internal node
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            if (leftChild < n && arr[i] < arr[leftChild]) return false;
            if (rightChild < n && arr[i] < arr[rightChild]) return false;
        }
        return true;
    }

    public static boolean isMinHeapOptimal(int[] arr) {
        if (arr == null || arr.length <= 1) return true;
        int n = arr.length;

        for (int i = 0; i <= (n - 2) / 2; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            if (leftChild < n && arr[i] > arr[leftChild]) return false;
            if (rightChild < n && arr[i] > arr[rightChild]) return false;
        }
        return true;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Recursive Depth-First)
     * ========================================================================
     * Detailed Intuition:
     * The structural definition of a heap is recursive: a tree is a Max-Heap if
     * the root is greater than its children AND both the left and right subtrees
     * are also Max-Heaps. This "Think it" stage naturally translates to a DFS
     * traversal. We check the current node's validity, then recursively ask the
     * left and right children to validate themselves.
     * * Complexity Analysis:
     * Time: O(N) - We still visit every internal node to verify the properties.
     * Space: O(log N) - Auxiliary stack space used by the recursion tree. Since
     * a heap is a complete binary tree, the maximum depth of the call
     * stack is strictly bounded by log base 2 of N.
     * ========================================================================
     */
    public static boolean isMaxHeapBruteForce(int[] arr, int index) {
        // Base case: If index is out of bounds or is a leaf node
        if (index > (arr.length - 2) / 2) return true;

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        boolean isCurrentValid = true;
        if (leftChild < arr.length && arr[index] < arr[leftChild]) isCurrentValid = false;
        if (rightChild < arr.length && arr[index] < arr[rightChild]) isCurrentValid = false;

        return isCurrentValid
                && isMaxHeapBruteForce(arr, leftChild)
                && isMaxHeapBruteForce(arr, rightChild);
    }

    public static boolean isMinHeapBruteForce(int[] arr, int index) {
        if (index > (arr.length - 2) / 2) return true;

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        boolean isCurrentValid = true;
        if (leftChild < arr.length && arr[index] > arr[leftChild]) isCurrentValid = false;
        if (rightChild < arr.length && arr[index] > arr[rightChild]) isCurrentValid = false;

        return isCurrentValid
                && isMinHeapBruteForce(arr, leftChild)
                && isMinHeapBruteForce(arr, rightChild);
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Java 8 Stream API)
     * ========================================================================
     * Detailed Intuition:
     * Modern Java codebases favor declarative programming. Instead of managing
     * loop boundaries manually, we can define a stream of integer indices
     * representing the internal nodes. We then apply an `allMatch` predicate
     * to assert that every single internal node obeys the heap property.
     * Under the hood, `allMatch` short-circuits (just like our iterative
     * approach) the moment it finds a violation.
     * * Complexity Analysis:
     * Time: O(N) - Evaluates at most N/2 elements. The short-circuiting behavior
     * maintains the same time complexity as the iterative approach.
     * Space: O(1) - The Stream API adds a tiny amount of overhead for object
     * creation, but algorithmically it remains constant auxiliary space
     * (ignoring heap allocation for the stream pipeline itself).
     * ========================================================================
     */
    public static boolean isMaxHeapStream(int[] arr) {
        if (arr == null || arr.length <= 1) return true;

        return IntStream.rangeClosed(0, (arr.length - 2) / 2)
                .allMatch(i -> {
                    int leftChild = 2 * i + 1;
                    int rightChild = 2 * i + 2;
                    boolean leftValid = (leftChild >= arr.length) || (arr[i] >= arr[leftChild]);
                    boolean rightValid = (rightChild >= arr.length) || (arr[i] >= arr[rightChild]);
                    return leftValid && rightValid;
                });
    }

    public static boolean isMinHeapStream(int[] arr) {
        if (arr == null || arr.length <= 1) return true;

        return IntStream.rangeClosed(0, (arr.length - 2) / 2)
                .allMatch(i -> {
                    int leftChild = 2 * i + 1;
                    int rightChild = 2 * i + 2;
                    boolean leftValid = (leftChild >= arr.length) || (arr[i] <= arr[leftChild]);
                    boolean rightValid = (rightChild >= arr.length) || (arr[i] <= arr[rightChild]);
                    return leftValid && rightValid;
                });
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Thoroughly tests standard cases, edge cases, and zeroes.
     * ========================================================================
     */
    public static void main(String[] args) {
        // Test Case 1: Standard Max-Heap
        int[] maxHeap = {90, 15, 10, 7, 12, 2};

        // Test Case 2: Standard Min-Heap
        int[] minHeap = {2, 10, 15, 90, 12, 11};

        // Test Case 3: Neither Min nor Max
        int[] randomArr = {9, 15, 10, 7, 12, 2};

        // Test Case 4: Edge Case - Single Element
        int[] singleElement = {42};

        // Test Case 5: Edge Case - All Zeroes/Duplicates
        int[] duplicates = {0, 0, 0, 0};

        System.out.println("--- TESTING MAX-HEAP LOGIC ---");
        System.out.println("TC1 (Max-Heap) Optimal: " + isMaxHeapOptimal(maxHeap));         // Expected: true
        System.out.println("TC1 (Max-Heap) Brute:   " + isMaxHeapBruteForce(maxHeap, 0));   // Expected: true
        System.out.println("TC1 (Max-Heap) Stream:  " + isMaxHeapStream(maxHeap));          // Expected: true

        System.out.println("\nTC3 (Random)   Optimal: " + isMaxHeapOptimal(randomArr));     // Expected: false
        System.out.println("TC4 (Single)   Stream:  " + isMaxHeapStream(singleElement));    // Expected: true
        System.out.println("TC5 (Zeroes)   Optimal: " + isMaxHeapOptimal(duplicates));      // Expected: true

        System.out.println("\n--- TESTING MIN-HEAP LOGIC ---");
        System.out.println("TC2 (Min-Heap) Optimal: " + isMinHeapOptimal(minHeap));         // Expected: true
        System.out.println("TC2 (Min-Heap) Brute:   " + isMinHeapBruteForce(minHeap, 0));   // Expected: true
        System.out.println("TC2 (Min-Heap) Stream:  " + isMinHeapStream(minHeap));          // Expected: true

        System.out.println("\nTC3 (Random)   Optimal: " + isMinHeapOptimal(randomArr));     // Expected: false
        System.out.println("TC4 (Single)   Stream:  " + isMinHeapStream(singleElement));    // Expected: true
        System.out.println("TC5 (Zeroes)   Optimal: " + isMinHeapOptimal(duplicates));      // Expected: true
    }
}
package com.questions.strivers.binarysearch.bson1darray;

/**
 * Masterclass Solution: Search Insert Position
 * * ==========================================
 * ### 1. Header & Problem Context
 * ==========================================
 * Problem Statement:
 * You are given a sorted array arr of distinct values and a target value x.
 * You need to search for the index of the target value in the array.
 * * - If the value is present in the array, return its index.
 * - Otherwise, return the index where it would be inserted in sorted order.
 * * Pre-requisite:
 * - Understanding of Lower Bound & Binary Search.
 *
 * * Examples:
 * Example 1:
 * Input: arr[] = {1, 2, 4, 7}, x = 6
 * Output: 3
 * Explanation: 6 would be inserted at index 3 to maintain sorted order.
 *
 * * Example 2:
 * Input: arr[] = {1, 2, 4, 7}, x = 2
 * Output: 1
 * Explanation: 2 is already present at index 1.
 *
 * * Example 3 (Edge Case):
 * Input: arr[] = {1, 3, 5, 6}, x = 7
 * Output: 4
 *
 * Explanation: 7 is larger than all elements, so it gets inserted at the very end (index 4).
 * * Note: As this is a binary search problem and NOT a Dynamic Programming problem,
 * the DP roadmap (2.1), recursion trees, and state visualizations are omitted.
 * We will follow Roadmap 2.2.
 */
public class SearchInsertPosition {

    /**
     * ==========================================
     * Phase 1: Best and Recommended Approach (Iterative Binary Search)
     * ==========================================
     * Detailed Intuition:
     * This problem is a direct application of the "Lower Bound" algorithm. The lower bound
     * finds the index of the first element that is greater than or equal to the target.
     * If the target exists, the lower bound points to it. If the target does not exist,
     * the lower bound points to the exact index where the target should be inserted to
     * maintain the sorted order. By maintaining a search space and continually cutting it
     * in half, we can pinpoint this insertion index efficiently. We initialize our answer
     * as `n` (the length of the array) to handle the edge case where the target is greater
     * than all elements in the array.
     * * Complexity Analysis:
     * - Time Complexity: O(log N), where N is the number of elements in the array. The
     * search space is halved in each step.
     * - Space Complexity: O(1) Auxiliary Space. We only allocate a few primitive pointer
     * variables (low, high, mid, ans), requiring constant heap/stack space.
     */
    public static int searchInsertOptimal(int[] arr, int x) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int ans = n; // Default answer if x is strictly greater than all elements

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents potential integer overflow

            if (arr[mid] >= x) {
                ans = mid;        // Potential insertion point found
                high = mid - 1;   // Search the left half for a smaller, valid index
            } else {
                low = mid + 1;    // x is larger, search the right half
            }
        }
        return ans;
    }

    /**
     * ==========================================
     * Phase 2: Brute Force Approach (Linear Search) - The "Think it" stage.
     * ==========================================
     * Detailed Intuition:
     * Before optimizing with binary search, the most intuitive way to solve this is to
     * simply scan the array from left to right. Since the array is already sorted, the
     * very first element we encounter that is greater than or equal to `x` represents
     * the exact spot where `x` belongs. If we finish the entire loop without finding
     * such an element, it means `x` is the largest element, and we should append it
     * at the end of the array (index `n`).
     * * Complexity Analysis:
     * - Time Complexity: O(N) in the worst case. We might have to traverse the entire
     * array if the target is larger than all elements or positioned at the very end.
     * - Space Complexity: O(1) Auxiliary Space. No extra data structures are used.
     */
    public static int searchInsertBruteForce(int[] arr, int x) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] >= x) {
                return i; // First element >= x is the insertion point
            }
        }
        return n; // If loop finishes, target belongs at the end
    }

    /**
     * ==========================================
     * Phase 3: Alternative Approach (Recursive Binary Search)
     * ==========================================
     * Detailed Intuition:
     * The logical transition from Phase 1 is implementing the exact same divide-and-conquer
     * strategy using recursion instead of an iterative `while` loop. We pass the `ans`
     * candidate down through the recursive calls. While this does not provide a speed
     * benefit over the iterative approach, it validates our understanding of call-stack
     * mechanics and recursive state passing, which interviewers often probe.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). The search space is still halved at each step.
     * - Space Complexity: O(log N) Auxiliary Stack Space. In the worst case, the recursion
     * will add log(N) frames to the call stack. Heap space remains O(1).
     */
    public static int searchInsertRecursive(int[] arr, int x, int low, int high, int ans) {
        if (low > high) {
            return ans;
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] >= x) {
            // Found a potential insertion point, record it and search left
            return searchInsertRecursive(arr, x, low, mid - 1, mid);
        } else {
            // Target is larger, search right
            return searchInsertRecursive(arr, x, mid + 1, high, ans);
        }
    }

    /**
     * ==========================================
     * 4. Testing Suite
     * ==========================================
     * A comprehensive test suite validating core examples, missing elements,
     * and extreme edge cases.
     */
    public static void main(String[] args) {
        // Standard Cases
        int[] arr1 = {1, 2, 4, 7};
        int x1 = 6; // Should return 3

        int[] arr2 = {1, 2, 4, 7};
        int x2 = 2; // Should return 1 (element exists)

        // Edge Cases
        int[] arr3 = {1, 3, 5, 6};
        int x3 = 7; // Target larger than all (returns n)

        int[] arr4 = {1, 3, 5, 6};
        int x4 = 0; // Target smaller than all (returns 0)

        int[] arr5 = {};
        int x5 = 5; // Empty array (returns 0)

        System.out.println("=== Phase 1: Optimal Approach (Iterative Binary Search) ===");
        System.out.println("Test 1 (Target 6): " + searchInsertOptimal(arr1, x1) + " \t| Expected: 3");
        System.out.println("Test 2 (Target 2): " + searchInsertOptimal(arr2, x2) + " \t| Expected: 1");
        System.out.println("Test 3 (Target 7): " + searchInsertOptimal(arr3, x3) + " \t| Expected: 4");
        System.out.println("Test 4 (Target 0): " + searchInsertOptimal(arr4, x4) + " \t| Expected: 0");
        System.out.println("Test 5 (Empty arr): " + searchInsertOptimal(arr5, x5) + " \t| Expected: 0\n");

        System.out.println("=== Phase 2: Brute Force Approach (Linear Search) ===");
        System.out.println("Test 1 (Target 6): " + searchInsertBruteForce(arr1, x1) + " \t| Expected: 3");
        System.out.println("Test 2 (Target 2): " + searchInsertBruteForce(arr2, x2) + " \t| Expected: 1");
        System.out.println("Test 3 (Target 7): " + searchInsertBruteForce(arr3, x3) + " \t| Expected: 4\n");

        System.out.println("=== Phase 3: Alternative Approach (Recursive Binary Search) ===");
        System.out.println("Test 1 (Target 6): " + searchInsertRecursive(arr1, x1, 0, arr1.length - 1, arr1.length) + " \t| Expected: 3");
        System.out.println("Test 2 (Target 2): " + searchInsertRecursive(arr2, x2, 0, arr2.length - 1, arr2.length) + " \t| Expected: 1");
        System.out.println("Test 3 (Target 7): " + searchInsertRecursive(arr3, x3, 0, arr3.length - 1, arr3.length) + " \t| Expected: 4");
    }
}

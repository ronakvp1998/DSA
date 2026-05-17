package com.questions.strivers.binarysearch.bson1darray;

/**
 * Masterclass Solution: Lower Bound in a Sorted Array
 * * Header & Problem Context:
 * Problem Statement:
 * Given a sorted array of N integers and an integer x, find the smallest index (lower bound)
 * such that arr[index] >= x.
 * * Lower Bound Definition:
 * - The "lower bound" of a value x in a sorted array is the index of the first element
 * that is greater than or equal to x.
 * - If all elements are smaller than x, it returns n (size of the array).
 * * Examples:
 * Example 1:
 * Input: arr = {1, 2, 2, 3}, x = 2
 * Output: 1
 * Explanation: arr[1] = 2 is the first element >= 2.
 * * Example 2:
 * Input: arr = {3, 5, 8, 15, 19}, x = 9
 * Output: 3
 * Explanation: arr[3] = 15 is the first element >= 9.
 *
 * Example 3 (Edge Case):
 * Input: arr = {1, 2, 8, 10, 11, 12, 19}, x = 20
 * Output: 7
 * Explanation: All elements are smaller than 20, so return n (size of array).
 * * Note: Since this is NOT a Dynamic Programming problem, the DP roadmap (2.1),
 * recursion trees, and state visualizations are omitted. We will follow Roadmap 2.2.
 */
public class LowerBound {

    /**
     * Phase 1: Best and Recommended Approach (Binary Search)
     * * Detailed Intuition:
     * Since the array is sorted, we can leverage Binary Search to find the target efficiently
     * rather than checking every element. We maintain a search space between 'low' and 'high'.
     * If the middle element is greater than or equal to 'x', it could be our answer, but there
     * might be an even smaller index to the left that is also >= 'x'. Thus, we record this
     * index as a potential answer and eliminate the right half (high = mid - 1). If the middle
     * element is strictly less than 'x', the lower bound must exist in the right half (low = mid + 1).
     * * Complexity Analysis:
     * Time Complexity: O(log N), where N is the number of elements in the array.
     * The search space is halved in each step.
     * Space Complexity: O(1) Auxiliary Space. We only use a few pointer variables (low, high, mid, ans)
     * requiring constant heap space.
     */
    public static int lowerBoundOptimal(int[] arr, int x) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int ans = n; // Default answer if all elements are strictly less than x

        while (low <= high) {
            int mid = low + (high - low) / 2; // Formula prevents integer overflow for large arrays

            if (arr[mid] >= x) {
                ans = mid;        // Potential answer found
                high = mid - 1;   // Look for a smaller index on the left side
            } else {
                low = mid + 1;    // x is larger, we must search the right side
            }
        }
        return ans;
    }

    /**
     * Phase 2: Brute Force Approach (Linear Search) - The "Think it" stage.
     * * Detailed Intuition:
     * The simplest way to find the lower bound is to iterate through the array sequentially
     * from left to right. Because the array is already sorted, the very first element we
     * encounter that is greater than or equal to 'x' is guaranteed to be the smallest valid index.
     * Once found, we immediately return it.
     * * Complexity Analysis:
     * Time Complexity: O(N) in the worst case, where we might have to traverse the entire array
     * (e.g., if x is greater than all elements, or at the very end).
     * Space Complexity: O(1) Auxiliary Space. No extra data structures or call stacks are used.
     */
    public static int lowerBoundBruteForce(int[] arr, int x) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] >= x) {
                return i; // First element >= x
            }
        }
        return n; // If no such element is found, return the array size
    }

    /**
     * Phase 3: Alternative Approach (Recursive Binary Search)
     * * Detailed Intuition:
     * We can implement the exact same logical constraints as Phase 1 using recursion. While this
     * doesn't improve time complexity over the iterative binary search, it demonstrates recursive
     * thinking, which interviewers often appreciate. We pass the 'ans' state through our method calls.
     * * Complexity Analysis:
     * Time Complexity: O(log N). The search space halves at each recursive call.
     * Space Complexity: O(log N) Auxiliary Stack Space due to recursive call frames accumulating
     * in the system stack. Heap space is O(1).
     */
    public static int lowerBoundRecursive(int[] arr, int x, int low, int high, int ans) {
        if (low > high) {
            return ans;
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] >= x) {
            // Found a potential answer, record it and search the left half
            return lowerBoundRecursive(arr, x, low, mid - 1, mid);
        } else {
            // The value is too small, search the right half
            return lowerBoundRecursive(arr, x, mid + 1, high, ans);
        }
    }

    /**
     * Testing Suite
     * * A comprehensive suite testing standard paths, overlapping elements, and zero-value/empty edge cases.
     */
    public static void main(String[] args) {
        // Test Case 1: Standard case (Example 1)
        int[] arr1 = {1, 2, 2, 3};
        int x1 = 2;

        // Test Case 2: Standard case (Example 2)
        int[] arr2 = {3, 5, 8, 15, 19};
        int x2 = 9;

        // Test Case 3: Edge case - target larger than all elements
        int[] arr3 = {1, 4, 7, 8, 10};
        int x3 = 15; // Should return n = 5

        // Test Case 4: Edge case - target smaller than all elements
        int[] arr4 = {5, 6, 7, 8};
        int x4 = 2; // Should return 0

        // Test Case 5: Edge case - empty array
        int[] arr5 = {};
        int x5 = 5; // Should return 0

        System.out.println("=== Phase 1: Lower Bound Optimal (Binary Search) ===");
        System.out.println("Test 1 (Target 2): " + lowerBoundOptimal(arr1, x1) + " \t| Expected: 1");
        System.out.println("Test 2 (Target 9): " + lowerBoundOptimal(arr2, x2) + " \t| Expected: 3");
        System.out.println("Test 3 (Target 15): " + lowerBoundOptimal(arr3, x3) + " \t| Expected: 5");
        System.out.println("Test 4 (Target 2): " + lowerBoundOptimal(arr4, x4) + " \t| Expected: 0");
        System.out.println("Test 5 (Target 5): " + lowerBoundOptimal(arr5, x5) + " \t| Expected: 0\n");

        System.out.println("=== Phase 2: Lower Bound Brute Force (Linear Search) ===");
        System.out.println("Test 1 (Target 2): " + lowerBoundBruteForce(arr1, x1) + " \t| Expected: 1");
        System.out.println("Test 2 (Target 9): " + lowerBoundBruteForce(arr2, x2) + " \t| Expected: 3");
        System.out.println("Test 3 (Target 15): " + lowerBoundBruteForce(arr3, x3) + " \t| Expected: 5\n");

        System.out.println("=== Phase 3: Lower Bound Alternative (Recursive Binary Search) ===");
        System.out.println("Test 1 (Target 2): " + lowerBoundRecursive(arr1, x1, 0, arr1.length - 1, arr1.length) + " \t| Expected: 1");
        System.out.println("Test 2 (Target 9): " + lowerBoundRecursive(arr2, x2, 0, arr2.length - 1, arr2.length) + " \t| Expected: 3");
    }
}

/*
Here is the short, practical answer on when and where to use Lower and Upper Bound concepts.
As a golden rule: Always ensure your array or search space is sorted before using these!

1. Finding the Frequency of an Element
If you need to know exactly how many times a target number appears in a sorted array, you don't need to count them one by one.
Formula: Frequency = Upper_Bound(target) - Lower_Bound(target)
Why: Lower bound gives the index of the first occurrence, and upper bound gives the index exactly one spot after the last occurrence.

2. Counting Elements in a Range [A, B]
When a problem asks "How many elements fall between A and B inclusive?" in a sorted dataset.
Formula: Count = Upper_Bound(B) - Lower_Bound(A)
Example: In an array of timestamps, finding how many events happened between 2:00 PM and 4:00 PM.

3. Finding "Floor" and "Ceiling" Values
If you are looking for the closest numbers to a target:
Ceiling (Smallest element $\ge$ target): This is exactly the Lower Bound.
Floor (Largest element $\le$ target): You can find this by taking the Upper Bound and subtracting 1 from the resulting index.

4. Binary Search on Answer (Optimization Problems)
These are problems where you are asked to "Find the minimum capacity,"
"Find the minimum days," or "Find the smallest weight" to satisfy a certain condition.
How it fits: These problems usually have a monotonic search space (e.g., False, False, False, True, True, True).
Finding the first "True" is the exact same logic as finding a Lower Bound.

5. Finding the Insertion Point
If you are maintaining a sorted list and need to insert a new element without breaking the sorted order:
Lower Bound gives you the exact index where the new element should be inserted.
 */
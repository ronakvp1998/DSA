package com.questions.strivers.binarysearch.bson1darray;

/**
 * Masterclass Solution: Upper Bound in a Sorted Array
 * * ==========================================
 * ### 1. Header & Problem Context
 * ==========================================
 * Problem Statement:
 * Given a sorted array of N integers and an integer x, write a program to find the upper bound of x.
 * * What is Upper Bound?
 * The upper bound algorithm finds the first or the smallest index in a sorted array where the value
 * at that index is strictly greater than the given key i.e., x.
 * The upper bound is the smallest index, ind, where arr[ind] > x.
 * If no such element exists (all elements are <= x), it returns N (the size of the array).
 * * Constraints:
 * 1 <= N <= 10^5
 * 1 <= arr[i], x <= 10^9
 * arr is sorted in non-decreasing order.
 * * Examples:
 * Example 1:
 * Input Format: N = 4, arr[] = {1, 2, 2, 3}, x = 2
 * Result: 3
 * Explanation: Index 3 is the smallest index such that arr[3] (which is 3) > 2.
 * * Example 2:
 * Input Format: N = 6, arr[] = {3, 5, 8, 9, 15, 19}, x = 9
 * Result: 4
 * Explanation: Index 4 is the smallest index such that arr[4] (which is 15) > 9.
 * * Example 3 (Edge Case):
 * Input Format: N = 5, arr[] = {1, 2, 3, 4, 5}, x = 10
 * Result: 5
 * Explanation: No element is greater than 10. Return N.
 * * Note: As this is a binary search array problem and NOT a Dynamic Programming problem,
 * we follow the Progressive Implementation Roadmap 2.2.
 */
public class UpperBound {

    /**
     * ==========================================
     * Phase 1: Best and Recommended Approach (Binary Search)
     * ==========================================
     * * Detailed Intuition:
     * Since the array is monotonic (sorted), applying Binary Search is the most optimal strategy.
     * We divide the search space in half at each step.
     * - If `arr[mid] > x`, this element is a valid candidate for the upper bound. We record `mid`
     * as a potential answer, but we must continue searching the left half (`high = mid - 1`)
     * to see if there is an even smaller index that satisfies the condition.
     * - If `arr[mid] <= x`, the current element is not strictly greater than x. Therefore, the
     * upper bound must lie in the right half of the array (`low = mid + 1`).
     * * Complexity Analysis:
     * - Time Complexity: O(log N). The search space is halved in each iteration.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive variables (`low`, `high`,
     * `mid`, `ans`) which allocate a constant amount of memory on the stack. Heap space is O(1).
     */
    public static int upperBoundOptimal(int[] arr, int x) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int ans = n; // Default to array size if no element is > x

        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents integer overflow for extremely large arrays

            if (arr[mid] > x) {
                ans = mid;        // Found a potential answer
                high = mid - 1;   // Look for a smaller valid index on the left
            } else {
                low = mid + 1;    // Value is <= x, move search to the right
            }
        }
        return ans;
    }

    /**
     * ==========================================
     * Phase 2: Brute Force Approach (Linear Search) - The "Think it" stage
     * ==========================================
     * * Detailed Intuition:
     * The most straightforward way to solve this is to iterate through the array sequentially from
     * index 0 to N-1. Because the array is already sorted, the very first element we encounter
     * that is strictly greater than `x` is guaranteed to be the smallest valid index (the upper bound).
     * If the loop finishes without finding such an element, we return N.
     * * Complexity Analysis:
     * - Time Complexity: O(N) in the worst-case scenario (e.g., when x is larger than all elements,
     * forcing us to traverse the entire array).
     * - Space Complexity: O(1) Auxiliary Space. No extra data structures or recursive calls are used.
     */
    public static int upperBoundBruteForce(int[] arr, int x) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] > x) {
                return i; // First element strictly greater than x
            }
        }
        return n; // If no such element exists
    }

    /**
     * ==========================================
     * Phase 3: Alternative Approach (Recursive Binary Search)
     * ==========================================
     * * Detailed Intuition:
     * This follows the exact same divide-and-conquer logic as Phase 1, but implemented recursively.
     * We pass the search boundaries (`low`, `high`) and the current best answer (`ans`) through
     * the function calls. While functionally identical to the iterative version, this demonstrates
     * an understanding of recursion and call-stack manipulation.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). The array is halved at each recursive step.
     * - Space Complexity: O(log N) Auxiliary Stack Space. In the worst case, the system stack
     * will hold log N recursive frames. Heap space remains O(1).
     */
    public static int upperBoundRecursive(int[] arr, int x, int low, int high, int ans) {
        // Base case: search space is exhausted
        if (low > high) {
            return ans;
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] > x) {
            // Potential answer found, narrow search to the left half
            return upperBoundRecursive(arr, x, low, mid - 1, mid);
        } else {
            // Current value too small/equal, narrow search to the right half
            return upperBoundRecursive(arr, x, mid + 1, high, ans);
        }
    }

    /**
     * ==========================================
     * 4. Testing Suite
     * ==========================================
     */
    public static void main(String[] args) {
        // Example 1
        int[] arr1 = {1, 2, 2, 3};
        int x1 = 2;

        // Example 2
        int[] arr2 = {3, 5, 8, 9, 15, 19};
        int x2 = 9;

        // Edge Case 1: Target larger than all elements
        int[] arr3 = {1, 4, 7, 8, 10};
        int x3 = 15; // Expected: 5 (size of array)

        // Edge Case 2: Target smaller than all elements
        int[] arr4 = {5, 6, 7, 8};
        int x4 = 2; // Expected: 0

        // Edge Case 3: Empty array (if constraints allowed, handled gracefully)
        int[] arr5 = {};
        int x5 = 5; // Expected: 0

        System.out.println("=== Phase 1: Optimal (Iterative Binary Search) ===");
        System.out.println("Test 1 (Target 2):  " + upperBoundOptimal(arr1, x1) + " \t| Expected: 3");
        System.out.println("Test 2 (Target 9):  " + upperBoundOptimal(arr2, x2) + " \t| Expected: 4");
        System.out.println("Test 3 (Target 15): " + upperBoundOptimal(arr3, x3) + " \t| Expected: 5");
        System.out.println("Test 4 (Target 2):  " + upperBoundOptimal(arr4, x4) + " \t| Expected: 0");
        System.out.println("Test 5 (Empty arr): " + upperBoundOptimal(arr5, x5) + " \t| Expected: 0\n");

        System.out.println("=== Phase 2: Brute Force (Linear Search) ===");
        System.out.println("Test 1 (Target 2):  " + upperBoundBruteForce(arr1, x1) + " \t| Expected: 3");
        System.out.println("Test 2 (Target 9):  " + upperBoundBruteForce(arr2, x2) + " \t| Expected: 4");
        System.out.println("Test 3 (Target 15): " + upperBoundBruteForce(arr3, x3) + " \t| Expected: 5\n");

        System.out.println("=== Phase 3: Alternative (Recursive Binary Search) ===");
        System.out.println("Test 1 (Target 2):  " + upperBoundRecursive(arr1, x1, 0, arr1.length - 1, arr1.length) + " \t| Expected: 3");
        System.out.println("Test 2 (Target 9):  " + upperBoundRecursive(arr2, x2, 0, arr2.length - 1, arr2.length) + " \t| Expected: 4");
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
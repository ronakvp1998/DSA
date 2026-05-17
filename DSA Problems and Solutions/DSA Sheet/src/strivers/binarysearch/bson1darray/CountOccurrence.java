package com.questions.strivers.binarysearch.bson1darray;

/**
 * Masterclass Solution: Count Occurrences in Sorted Array
 * * ==========================================
 * ### 1. Header & Problem Context
 * ==========================================
 * Problem Statement:
 * You are given a sorted array containing N integers and a number X.
 * You have to find the occurrences of X in the given array.
 * * * Constraints:
 * 1 <= N <= 10^5
 * 1 <= arr[i], X <= 10^9
 * arr is sorted in non-decreasing order.
 * * * Examples:
 * Example 1:
 * Input: N = 7, X = 3, array[] = {2, 2, 3, 3, 3, 3, 4}
 * Output: 4
 * Explanation: 3 is occurring 4 times in the given array so it is our answer.
 * * Example 2:
 * Input: N = 8, X = 2, array[] = {1, 1, 2, 2, 2, 2, 2, 3}
 * Output: 5
 * Explanation: 2 is occurring 5 times in the given array so it is our answer.
 * * Example 3 (Edge Case - Element not present):
 * Input: N = 4, X = 5, array[] = {1, 2, 3, 4}
 * Output: 0
 * Explanation: 5 is not present in the array.
 * * * Note: As this is an Array/Binary Search problem and NOT a Dynamic Programming problem,
 * the DP roadmap (2.1), recursion trees, and state visualizations are omitted.
 * We will follow Progressive Implementation Roadmap 2.2.
 */
public class CountOccurrence {

    /**
     * ==========================================
     * Phase 1: Best and Recommended Approach (First and Last Position)
     * ==========================================
     * Detailed Intuition:
     * Since the array is sorted, all identical elements are grouped together contiguously.
     * To find the total count of a target `X`, we only need to know its starting index
     * (first occurrence) and its ending index (last occurrence).
     * We can use Binary Search twice:
     * 1. One Binary Search to find the absolute first occurrence.
     * 2. One Binary Search to find the absolute last occurrence.
     * The total count is simply: (Last Index - First Index + 1). If the first occurrence
     * is not found, the element doesn't exist, and we return 0 immediately.
     * * * Complexity Analysis:
     * - Time Complexity: O(log N) + O(log N) = O(log N). The search space is halved in
     * each step of both independent binary searches.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive variables (low, high,
     * mid, ans) allocating a constant amount of memory on the stack. Heap space is O(1).
     */
    public static int countOptimal(int[] arr, int x) {
        int first = findFirstOccurrence(arr, x);

        // If the element is not present at all, return 0
        if (first == -1) {
            return 0;
        }

        int last = findLastOccurrence(arr, x);

        return (last - first) + 1;
    }

    private static int findFirstOccurrence(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int first = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == x) {
                first = mid;      // Potential first occurrence
                high = mid - 1;   // Keep searching left for an earlier occurrence
            } else if (arr[mid] < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return first;
    }

    private static int findLastOccurrence(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int last = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == x) {
                last = mid;       // Potential last occurrence
                low = mid + 1;    // Keep searching right for a later occurrence
            } else if (arr[mid] < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return last;
    }

    /**
     * ==========================================
     * Phase 2: Brute Force Approach (Linear Scan with Early Exit) - The "Think it" stage.
     * ==========================================
     * Detailed Intuition:
     * The most basic approach is to iterate through the array and count every time we see `x`.
     * Because the array is sorted, we can optimize slightly: as soon as we encounter an
     * element strictly greater than `x`, we can immediately terminate the loop, because
     * `x` will never appear again.
     * * * Complexity Analysis:
     * - Time Complexity: O(N) in the worst case (e.g., if the entire array consists of
     * the target `x`, or if `x` is the very last element).
     * - Space Complexity: O(1) Auxiliary Space. No extra data structures or call stacks are used.
     */
    public static int countBruteForce(int[] arr, int x) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                count++;
            } else if (arr[i] > x) {
                break; // Early exit optimization due to sorted property
            }
        }
        return count;
    }

    /**
     * ==========================================
     * Phase 3: Alternative Approach (Lower Bound & Upper Bound Difference)
     * ==========================================
     * Detailed Intuition:
     * In Competitive Programming, a highly elegant way to count occurrences is by leveraging
     * strict algorithmic bounds:
     * - Lower Bound: Finds the index of the first element that is >= x.
     * - Upper Bound: Finds the index of the first element that is strictly > x.
     * The exact mathematical difference between the Upper Bound index and the Lower Bound
     * index gives the exact count of occurrences.
     * * * Complexity Analysis:
     * - Time Complexity: O(log N) + O(log N) = O(log N). Two binary searches.
     * - Space Complexity: O(1) Auxiliary Space. Only primitive variables are stored.
     */
    public static int countAlternative(int[] arr, int x) {
        int lowerBoundIdx = getLowerBound(arr, x);
        int upperBoundIdx = getUpperBound(arr, x);

        return upperBoundIdx - lowerBoundIdx;
    }

    private static int getLowerBound(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int ans = arr.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    private static int getUpperBound(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int ans = arr.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > x) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    /**
     * ==========================================
     * 4. Testing Suite
     * ==========================================
     */
    public static void main(String[] args) {
        // Test Case 1: Standard case (Example 1)
        int[] arr1 = {2, 2, 3, 3, 3, 3, 4};
        int x1 = 3;

        // Test Case 2: Standard case (Example 2)
        int[] arr2 = {1, 1, 2, 2, 2, 2, 2, 3};
        int x2 = 2;

        // Test Case 3: Target not in array
        int[] arr3 = {1, 2, 4, 5};
        int x3 = 3;

        // Test Case 4: Array with all identical elements matching target
        int[] arr4 = {7, 7, 7, 7};
        int x4 = 7;

        // Test Case 5: Empty array (Handled gracefully by returning 0)
        int[] arr5 = {};
        int x5 = 5;

        System.out.println("=== Phase 1: Optimal (First & Last Occurrence Binary Search) ===");
        printResult("Test 1", x1, countOptimal(arr1, x1), 4);
        printResult("Test 2", x2, countOptimal(arr2, x2), 5);
        printResult("Test 3", x3, countOptimal(arr3, x3), 0);
        printResult("Test 4", x4, countOptimal(arr4, x4), 4);
        printResult("Test 5", x5, countOptimal(arr5, x5), 0);

        System.out.println("\n=== Phase 2: Brute Force (Linear Scan) ===");
        printResult("Test 1", x1, countBruteForce(arr1, x1), 4);
        printResult("Test 2", x2, countBruteForce(arr2, x2), 5);
        printResult("Test 3", x3, countBruteForce(arr3, x3), 0);

        System.out.println("\n=== Phase 3: Alternative (Lower/Upper Bound Math) ===");
        printResult("Test 1", x1, countAlternative(arr1, x1), 4);
        printResult("Test 2", x2, countAlternative(arr2, x2), 5);
        printResult("Test 3", x3, countAlternative(arr3, x3), 0);
        printResult("Test 4", x4, countAlternative(arr4, x4), 4);
        printResult("Test 5", x5, countAlternative(arr5, x5), 0);
    }

    // Helper formatter for clear console output
    private static void printResult(String testName, int target, int result, int expected) {
        System.out.printf("%s (Target %d): Count = %d \t| Expected = %d%n",
                testName, target, result, expected);
    }
}

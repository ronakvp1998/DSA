package strivers.binarysearch.bson1darray;

/**
 * Masterclass Solution: Floor and Ceil in a Sorted Array
 * * ==========================================
 * ### 1. Header & Problem Context
 * ==========================================
 * Problem Statement:
 * You're given a sorted array arr of n integers and an integer x. Find the floor and ceiling of x in arr[0..n-1].
 * - The floor of x is the largest element in the array which is smaller than or equal to x.
 * - The ceiling of x is the smallest element in the array greater than or equal to x.
 * * Pre-requisite: Lower Bound & Binary Search
 * * If a floor or ceiling does not exist, we return -1.
 * * Examples:
 *
 * Example 1:
 * Input Format: n = 6, arr[] ={3, 4, 4, 7, 8, 10}, x= 5
 * Result: 4 7
 * Explanation: The floor of 5 in the array is 4, and the ceiling of 5 in the array is 7.
 *
 * * Example 2:
 * Input Format: n = 6, arr[] ={3, 4, 4, 7, 8, 10}, x= 8
 * Result: 8 8
 * Explanation: The floor of 8 in the array is 8, and the ceiling of 8 in the array is also 8.
 *
 * * Example 3 (Edge Case - Out of Bounds):
 * Input Format: n = 4, arr[] ={2, 4, 6, 8}, x= 1
 * Result: -1 2
 * Explanation: No element is <= 1, so floor is -1. Ceiling is 2.
 *
 */
public class FloorAndCeil {

    /**
     * ==========================================
     * Phase 1: Best and Recommended Approach (Iterative Binary Search)
     * ==========================================
     * Detailed Intuition:
     * Since the array is sorted, we can completely avoid checking every element by using
     * Binary Search. We can split the problem into two distinct binary searches:
     * 1. Finding Ceiling: This is the exact definition of Lower Bound. We want the first
     * element >= x. If `arr[mid] >= x`, it's a candidate, but we must search the left
     * half for an even smaller candidate.
     * 2. Finding Floor: We want the largest element <= x. If `arr[mid] <= x`, it's a
     * candidate, but we must search the right half for an even larger valid candidate.
     * * Complexity Analysis:
     * - Time Complexity: O(log n) + O(log n) = O(log n), where n is the number of elements.
     * We perform two independent binary searches.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive pointers (low, high,
     * mid, ans) requiring constant heap/stack space.
     */
    public static int[] getFloorAndCeilOptimal(int[] arr, int x) {
        return new int[]{findFloor(arr, x), findCeil(arr, x)};
    }

    private static int findFloor(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int floor = -1; // Default if no floor exists

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) {
                floor = arr[mid]; // Potential floor found
                low = mid + 1;    // Look for a larger valid number on the right
            } else {
                high = mid - 1;   // Value is too large, search left
            }
        }
        return floor;
    }

    private static int findCeil(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int ceil = -1; // Default if no ceiling exists

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                ceil = arr[mid];  // Potential ceiling found
                high = mid - 1;   // Look for a smaller valid number on the left
            } else {
                low = mid + 1;    // Value is too small, search right
            }
        }
        return ceil;
    }

    /**
     * ==========================================
     * Phase 2: Brute Force Approach (Linear Search) - The "Think it" stage.
     * ==========================================
     * Detailed Intuition:
     * If we ignore the sorted property of the array, the most basic way to find the floor
     * and ceiling is to traverse the array from start to finish.
     * - For Floor: Update our answer with the maximum value seen so far that is <= x.
     * - For Ceiling: Update our answer with the minimum value seen so far that is >= x.
     * Because the array IS sorted, we can optimize slightly (e.g., returning early), but
     * the worst-case scenario still demands checking every element.
     * * Complexity Analysis:
     * - Time Complexity: O(n) in the worst case, as we may need to iterate through the
     * entire array to confirm the boundaries.
     * - Space Complexity: O(1) Auxiliary Space. No extra data structures are used.
     */
    public static int[] getFloorAndCeilBruteForce(int[] arr, int x) {
        int floor = -1;
        int ceil = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= x) {
                floor = arr[i]; // Continuously updates to the largest valid <= x
            }
            // Since it's sorted, the first element >= x we encounter is the ceiling
            if (arr[i] >= x && ceil == -1) {
                ceil = arr[i];
            }
        }

        return new int[]{floor, ceil};
    }

    /**
     * ==========================================
     * Phase 3: Alternative Approach (Recursive Binary Search)
     * ==========================================
     * Detailed Intuition:
     * Transitioning from iterative binary search (Phase 1) to recursion. The logic
     * remains identical: divide and conquer. However, instead of updating local variables
     * in a `while` loop, we pass the current best `ans` state down the call stack. This
     * highlights an understanding of stack memory and recursive problem decomposition.
     * * Complexity Analysis:
     * - Time Complexity: O(log n). The search space halves at each recursive call.
     * - Space Complexity: O(log n) Auxiliary Stack Space due to recursive call frames
     * accumulating in the system stack. Heap space is O(1).
     */
    public static int[] getFloorAndCeilRecursive(int[] arr, int x) {
        int floor = recursiveFloor(arr, x, 0, arr.length - 1, -1);
        int ceil = recursiveCeil(arr, x, 0, arr.length - 1, -1);
        return new int[]{floor, ceil};
    }

    private static int recursiveFloor(int[] arr, int x, int low, int high, int ans) {
        if (low > high) return ans;
        int mid = low + (high - low) / 2;

        if (arr[mid] <= x) {
            return recursiveFloor(arr, x, mid + 1, high, arr[mid]); // Search right
        } else {
            return recursiveFloor(arr, x, low, mid - 1, ans);       // Search left
        }
    }

    private static int recursiveCeil(int[] arr, int x, int low, int high, int ans) {
        if (low > high) return ans;
        int mid = low + (high - low) / 2;

        if (arr[mid] >= x) {
            return recursiveCeil(arr, x, low, mid - 1, arr[mid]);  // Search left
        } else {
            return recursiveCeil(arr, x, mid + 1, high, ans);      // Search right
        }
    }

    /**
     * ==========================================
     * 4. Testing Suite
     * ==========================================
     */
    public static void main(String[] args) {
        // Standard Cases
        int[] arr1 = {3, 4, 4, 7, 8, 10};
        int x1 = 5; // Expected: Floor 4, Ceil 7

        int[] arr2 = {3, 4, 4, 7, 8, 10};
        int x2 = 8; // Expected: Floor 8, Ceil 8

        // Edge Cases
        int[] arr3 = {2, 4, 6, 8};
        int x3 = 1; // Expected: Floor -1, Ceil 2 (Target smaller than all)

        int[] arr4 = {2, 4, 6, 8};
        int x4 = 10; // Expected: Floor 8, Ceil -1 (Target larger than all)

        int[] arr5 = {};
        int x5 = 5; // Expected: Floor -1, Ceil -1 (Empty array)

        System.out.println("=== Phase 1: Optimal (Iterative Binary Search) ===");
        printResult(1, 5, getFloorAndCeilOptimal(arr1, x1), 4, 7);
        printResult(2, 8, getFloorAndCeilOptimal(arr2, x2), 8, 8);
        printResult(3, 1, getFloorAndCeilOptimal(arr3, x3), -1, 2);
        printResult(4, 10, getFloorAndCeilOptimal(arr4, x4), 8, -1);
        printResult(5, 5, getFloorAndCeilOptimal(arr5, x5), -1, -1);

        System.out.println("\n=== Phase 2: Brute Force (Linear Search) ===");
        printResult(1, 5, getFloorAndCeilBruteForce(arr1, x1), 4, 7);
        printResult(2, 8, getFloorAndCeilBruteForce(arr2, x2), 8, 8);
        printResult(3, 1, getFloorAndCeilBruteForce(arr3, x3), -1, 2);

        System.out.println("\n=== Phase 3: Alternative (Recursive Binary Search) ===");
        printResult(1, 5, getFloorAndCeilRecursive(arr1, x1), 4, 7);
        printResult(2, 8, getFloorAndCeilRecursive(arr2, x2), 8, 8);
        printResult(4, 10, getFloorAndCeilRecursive(arr4, x4), 8, -1);
    }

    // Helper method for clean console output
    private static void printResult(int testNum, int target, int[] result, int expFloor, int expCeil) {
        System.out.printf("Test %d (Target %2d): Floor = %2d, Ceil = %2d \t| Expected: Floor = %2d, Ceil = %2d\n",
                testNum, target, result[0], result[1], expFloor, expCeil);
    }
}
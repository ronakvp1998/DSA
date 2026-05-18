package strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: Find out how many times the array has been rotated
 * Level: Easy/Medium
 * * Problem Statement:
 * Given an integer array arr of size N, sorted in ascending order (with distinct values).
 * Now the array is rotated between 1 to N times which is unknown. Find how many times
 * the array has been rotated.
 * * Pre-requisites: Find minimum in Rotated Sorted Array, Search in Rotated Sorted Array II
 * & Binary Search algorithm.
 * * Key Insight:
 * In an array rotated right by 'K' steps, the minimum element shifts exactly 'K' steps
 * from the 0th index. Therefore, the index of the minimum element is exactly equal
 * to the number of times the array has been rotated.
 * * Examples:
 * Example 1:
 * Input : arr = [4,5,6,7,0,1,2,3]
 * Result: 4
 * Explanation: The original array should be [0,1,2,3,4,5,6,7]. The minimum element is 0,
 * which is at index 4. The array has been rotated 4 times.
 * * Example 2:
 * Input : arr = [3,4,5,1,2]
 * Output : 3
 * Explanation: The original array should be [1,2,3,4,5]. The minimum element is 1,
 * which is at index 3. The array has been rotated 3 times.
 * * Constraints:
 * 1 <= arr.length <= 5000
 * -10^4 <= arr[i] <= 10^4
 * All the integers of arr are unique.
 * arr is sorted and rotated between 0 and N times.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 * As this is an Array/Binary Search problem (Not DP), we follow the non-DP roadmap:
 * Phase 1: Best and recommended approach (Optimized Binary Search for Minimum Index)
 * Phase 2: Brute Force approach (Linear Search for the Inflection Point)
 * Phase 3: Alternative Approaches (Recursive Binary Search)
 */

public class TimesArrayRotated {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Binary Search)
     * ========================================================================
     * Approach:
     * We use Binary Search to find the index of the minimum element.
     * Since the array was originally sorted, the index of the minimum element
     * directly corresponds to the number of right rotations performed.
     * * Detailed Intuition:
     * 1. We maintain a search space between 'low' and 'high'.
     * 2. Calculate 'mid'. We compare arr[mid] with arr[high].
     * 3. If arr[mid] > arr[high], it implies the right half is unsorted. This means
     * the rotation boundary (and the minimum element) MUST lie in the right half
     * strictly after 'mid'. So, low = mid + 1.
     * 4. If arr[mid] <= arr[high], the right half is perfectly sorted. The minimum
     * element could be 'mid' itself, or somewhere in the unsorted left half.
     * So, high = mid.
     * 5. The loop breaks when low == high, and 'low' will be the index of our minimum.
     * * Complexity Analysis:
     * - Time Complexity: O(log N) where N is the number of elements in the array.
     * The search space is halved in every iteration.
     * - Space Complexity: O(1) auxiliary space. We use a constant amount of primitive
     * variables (low, high, mid) stored on the stack. No heap allocation.
     */
    public int countRotationsBest(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        int low = 0;
        int high = arr.length - 1;

        while (low < high) {
            // Optimization: If the current search space is already strictly sorted,
            // the minimum is at the 'low' index.
            if (arr[low] <= arr[high]) {
                return low;
            }

            int mid = low + (high - low) / 2;

            if (arr[mid] > arr[high]) {
                // Minimum must be to the right of mid
                low = mid + 1;
            } else {
                // Minimum is at mid or to the left
                high = mid;
            }
        }

        return low; // Index of the minimum element
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Search) - The "Think it" stage
     * ========================================================================
     * Approach:
     * Traverse the array linearly and find the index of the minimum element.
     * Alternatively, just look for the first element that is smaller than its
     * preceding element (the inflection point).
     * * Detailed Intuition:
     * In a sorted array that has been rotated, every element is greater than
     * its previous element EXCEPT for the minimum element (the rotation point).
     * By scanning left to right, the moment we find arr[i] < arr[i-1], 'i' is
     * our number of rotations. If we reach the end without finding this, the
     * array is fully sorted (0 rotations).
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. In the worst
     * case (e.g., rotated at the very end, or not rotated at all), we scan N elements.
     * - Space Complexity: O(1) auxiliary space. Only loop counters are used.
     */
    public int countRotationsBruteForce(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return i;
            }
        }

        // If no inflection point is found, the array wasn't rotated (or rotated N times)
        return 0;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Recursive Binary Search)
     * ========================================================================
     * Approach:
     * Same logarithmic O(log N) logic as Phase 1, but implemented functionally
     * through recursion.
     * * Detailed Intuition:
     * We pass the search boundaries to a recursive helper. If the boundary collapses
     * (low == high), we've found our index. If the subarray is perfectly sorted
     * (arr[low] <= arr[high]), we immediately return 'low'. Otherwise, we split
     * at 'mid' and recursively search the unsorted half.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). We halve the search space at each recursive step.
     * - Space Complexity: O(log N) auxiliary stack space due to recursive call frames.
     * No dynamic heap space is consumed.
     */
    public int countRotationsAlternative(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return findMinIndexRecursive(arr, 0, arr.length - 1);
    }

    private int findMinIndexRecursive(int[] arr, int low, int high) {
        // Base case: only one element
        if (low == high) {
            return low;
        }

        // Subarray is already completely sorted
        if (arr[low] < arr[high]) {
            return low;
        }

        int mid = low + (high - low) / 2;

        // Check if mid is the inflection point
        if (mid < high && arr[mid] > arr[mid + 1]) {
            return mid + 1;
        }
        if (mid > low && arr[mid] < arr[mid - 1]) {
            return mid;
        }

        // Decide which half to recursively search
        if (arr[mid] > arr[high]) {
            return findMinIndexRecursive(arr, mid + 1, high);
        } else {
            return findMinIndexRecursive(arr, low, mid - 1);
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering normal cases, edge cases (fully sorted),
     * and specific rotation boundaries.
     */
    public static void main(String[] args) {
        TimesArrayRotated solution = new TimesArrayRotated();

        // Test Cases Setup
        int[][] testArrays = {
                {4, 5, 6, 7, 0, 1, 2, 3}, // Example 1: Rotated 4 times
                {3, 4, 5, 1, 2},          // Example 2: Rotated 3 times
                {1, 2, 3, 4, 5},          // Edge Case: 0 rotations (already fully sorted)
                {5, 1, 2, 3, 4},          // Edge Case: Rotated 1 time (min near start)
                {2, 3, 4, 5, 1},          // Edge Case: Rotated N-1 times (min at the end)
                {1},                      // Edge Case: Single element
                {2, 1}                    // Edge Case: Two elements, rotated
        };

        int[] expectedAnswers = {4, 3, 0, 1, 4, 0, 1};

        System.out.println("==========================================================");
        System.out.println("Executing Testing Suite for Array Rotation Count");
        System.out.println("==========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] arr = testArrays[i];
            int expected = expectedAnswers[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array:    " + java.util.Arrays.toString(arr));
            System.out.println("Expected  : " + expected + " rotations");

            int res1 = solution.countRotationsBest(arr);
            int res2 = solution.countRotationsBruteForce(arr);
            int res3 = solution.countRotationsAlternative(arr);

            System.out.println("Phase 1 (Optimal) : " + res1);
            System.out.println("Phase 2 (Brute)   : " + res2);
            System.out.println("Phase 3 (Recurse) : " + res3);

            if(res1 == expected && res2 == expected && res3 == expected) {
                System.out.println("Status: PASS ✅");
            } else {
                System.out.println("Status: FAIL ❌ (Mismatch in approach outputs)");
            }
            System.out.println("----------------------------------------------------------");
        }
    }
}
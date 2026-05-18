package strivers.arrays.medium;
/**
 * ============================================================================
 * MASTERCLASS: SORT COLORS
 * ============================================================================
 * * 75. Sort Colors
 * Solved | Medium | Topics | Companies
 * * * Hint:
 * Given an array nums with n objects colored red, white, or blue, sort them
 * in-place so that objects of the same color are adjacent, with the colors in
 * the order red, white, and blue.
 * We will use the integers 0, 1, and 2 to represent the color red, white, and
 * blue, respectively.
 * You must solve this problem without using the library's sort function.
 * * * Example 1:
 * Input: nums = [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * * * Example 2:
 * Input: nums = [2,0,1]
 * Output: [0,1,2]
 * * * Constraints:
 * - n == nums.length
 * - 1 <= n <= 300
 * - nums[i] is either 0, 1, or 2.
 * * * Follow up: Could you come up with a one-pass algorithm using only constant
 * extra space?
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Dutch National Flag Algorithm)
 * ============================================================================
 * Since this is an Array/Two-Pointer problem, a recursion tree isn't applicable.
 * Instead, here is a visualization of the optimal One-Pass (3-Pointer) approach.
 * * We maintain three pointers: low, mid, and high.
 * - [0 to low-1] are 0s (Red)
 * - [low to mid-1] are 1s (White)
 * - [mid to high] are unknown
 * - [high+1 to n-1] are 2s (Blue)
 * * Input: [2, 0, 2, 1, 1, 0]
 * * Step 0: low=0, mid=0, high=5
 * [2, 0, 2, 1, 1, 0] -> nums[mid] is 2. Swap mid and high, high--
 * ^              ^
 * Step 1: low=0, mid=0, high=4
 * [0, 0, 2, 1, 1, 2] -> nums[mid] is 0. Swap mid and low, low++, mid++
 * ^           ^
 * Step 2: low=1, mid=1, high=4
 * [0, 0, 2, 1, 1, 2] -> nums[mid] is 0. Swap mid and low, low++, mid++
 * ^        ^
 * Step 3: low=2, mid=2, high=4
 * [0, 0, 2, 1, 1, 2] -> nums[mid] is 2. Swap mid and high, high--
 * ^     ^
 * Step 4: low=2, mid=2, high=3
 * [0, 0, 1, 1, 2, 2] -> nums[mid] is 1. mid++
 * ^  ^
 * Step 5: low=2, mid=3, high=3
 * [0, 0, 1, 1, 2, 2] -> nums[mid] is 1. mid++
 * ^
 * Terminate: mid > high. Array is sorted.
 * ============================================================================
 */

import java.util.Arrays;

public class SortArray012 {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (Counting Sort / Two-Pass) - "Think it"
     * ========================================================================
     * Approach:
     * Iterate through the array once and count the exact occurrences of 0s, 1s,
     * and 2s. In the second pass, overwrite the original array with the correct
     * number of 0s followed by 1s and then 2s.
     * * Intuition:
     * When dealing with a strictly limited set of discrete values (only three
     * possible values here), standard O(N log N) comparison sorting is overkill.
     * A straightforward way to group them is to simply count them and recreate
     * the sequence. This is effectively a basic implementation of Counting Sort.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We make exactly two passes over the array of size N (one for counting,
     * one for overwriting). O(2N) simplifies to O(N).
     * - Space Complexity: O(1)
     * We only use three integer variables to maintain counts.
     * Heap Space: O(1). Auxiliary Stack Space: O(1).
     */
    public void sortColorsBruteForce(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;

        // Pass 1: Count occurrences
        for (int num : nums) {
            if (num == 0) count0++;
            else if (num == 1) count1++;
            else count2++;
        }

        // Pass 2: Overwrite array
        int i = 0;
        while (count0 > 0) { nums[i++] = 0; count0--; }
        while (count1 > 0) { nums[i++] = 1; count1--; }
        while (count2 > 0) { nums[i++] = 2; count2--; }
    }

    /**
     * ========================================================================
     * PHASE 2: ALTERNATIVE APPROACH (Dutch National Flag / One-Pass) - "Perfect it"
     * ========================================================================
     * Approach:
     * Use three pointers: `low`, `mid`, and `high`.
     * Iterate `mid` from 0 to `high`.
     * - If nums[mid] == 0: swap with `low`, increment both `low` and `mid`.
     * - If nums[mid] == 1: just increment `mid` (it's in the correct middle area).
     * - If nums[mid] == 2: swap with `high`, decrement `high` (do NOT increment
     * `mid` yet, because the newly swapped value at `mid` needs evaluation).
     * * Intuition:
     * To satisfy the follow-up of a "one-pass" algorithm, we need to partition
     * the array dynamically. This is Dijkstra's Dutch National Flag problem.
     * By tossing 0s to the left boundary (`low`) and 2s to the right boundary
     * (`high`), the 1s naturally gather in the middle. The `mid` pointer acts
     * as our explorer, classifying each unknown element as it encounters it.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We make a single pass through the array. The `mid` pointer evaluates each
     * element at most once, and the `high` pointer shrinks the bounds.
     * Total operations scale linearly with array length.
     * - Space Complexity: O(1)
     * Sorting is done entirely in-place. We only use three integer pointers.
     * Heap Space: O(1). Auxiliary Stack Space: O(1).
     */
    public void sortColorsOptimal(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                // Swap nums[low] and nums[mid]
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                // 1 is in the right place, just move forward
                mid++;
            } else { // nums[mid] == 2
                // Swap nums[mid] and nums[high]
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;
                high--;
                // Note: We don't increment 'mid' here because the value swapped
                // from 'high' is unexamined and might be a 0 or 2.
            }
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        SortArray012 solution = new SortArray012();

        // Define Test Cases
        int[][] testCases = {
                {2, 0, 2, 1, 1, 0},   // Example 1: Standard mixed case
                {2, 0, 1},            // Example 2: Small array
                {0},                  // Edge Case: Single element (0)
                {1},                  // Edge Case: Single element (1)
                {2},                  // Edge Case: Single element (2)
                {1, 1, 1, 1},         // Edge Case: All same color
                {0, 0, 0, 0},         // Edge Case: All zeros
                {2, 2, 2, 2},         // Edge Case: All twos
                {2, 1, 0, 2, 1, 0}    // Edge Case: Alternating descending
        };

        System.out.println("=============================================");
        System.out.println("Executing Sort Colors Testing Suite");
        System.out.println("=============================================\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(testCases[i]));

            // Clone arrays to test both methods independently
            int[] testArr1 = testCases[i].clone();
            int[] testArr2 = testCases[i].clone();

            // Test Brute Force (Counting Sort)
            long start1 = System.nanoTime();
            solution.sortColorsBruteForce(testArr1);
            long end1 = System.nanoTime();

            // Test Optimal (Dutch National Flag)
            long start2 = System.nanoTime();
            solution.sortColorsOptimal(testArr2);
            long end2 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + Arrays.toString(testArr1) + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Optimal DNF] Output: " + Arrays.toString(testArr2) + " | Time: " + (end2 - start2) + " ns");

            // Verification
            boolean isValid = Arrays.equals(testArr1, testArr2);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------");
        }
    }
}
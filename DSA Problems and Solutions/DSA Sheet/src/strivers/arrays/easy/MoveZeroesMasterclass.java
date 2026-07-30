package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 283. Move Zeroes
 * Difficulty: Easy
 *
 * Formal Problem Statement:
 * Given an integer array nums, move all 0's to the end of it while maintaining
 * the relative order of the non-zero elements.
 *
 * Note that you must do this in-place without making a copy of the array.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 *
 * Follow up:
 * - Could you minimize the total number of operations done?
 *
 * Example 1:
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [0]
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.Collectors;

public class MoveZeroesMasterclass {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Two Pointers / Swap Method)
     * ============================================================================
     * Detailed Intuition:
     * To achieve the goal strictly in-place and minimize operations, we use a
     * two-pointer approach.
     * - The `insertPos` pointer keeps track of the furthest available index where
     *   a non-zero element should be placed.
     * - The `i` pointer iterates through the array.
     * When we encounter a non-zero element at `i`, we swap it with the element at
     * `insertPos`. By doing this, all non-zero elements are pushed to the front
     * maintaining their relative order, and the zeros are naturally bubbled to
     * the end. This approach handles the follow-up question by avoiding redundant
     * writes (especially if the array contains mostly non-zero elements).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. We make a
     *   single pass through the array.
     * - Space Complexity: O(1) auxiliary space. The swapping is done purely
     *   in-place, strictly satisfying the problem constraints.
     */
    public void moveZeroesOptimal(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        int insertPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // Minor optimization: prevent unnecessary self-swaps if there are no preceding zeroes
                if (i != insertPos) {
                    int temp = nums[insertPos];
                    nums[insertPos] = nums[i];
                    nums[i] = temp;
                }
                insertPos++;
            }
        }
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (Using Extra Array)
     * ============================================================================
     * Detailed Intuition:
     * The easiest way to conceptualize this is to take a brand new array of the
     * same size. We iterate through the original array and copy only the non-zero
     * numbers into the new array sequentially. The rest of the new array defaults
     * to zero. Finally, we copy the elements from the new array back to the
     * original array.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the elements a couple of times.
     * - Space Complexity: O(N) heap space to allocate the temporary array.
     *   NOTE: This completely violates the "in-place without making a copy"
     *   constraint, but serves as the foundational "Think It" step.
     */
    public void moveZeroesBruteForce(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        int n = nums.length;
        int[] temp = new int[n]; // Defaults to 0
        int insertIndex = 0;

        // Collect non-zeros
        for (int num : nums) {
            if (num != 0) {
                temp[insertIndex++] = num;
            }
        }

        // Copy back to original array
        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 1 (Overwrite and Fill)
     * ============================================================================
     * Detailed Intuition:
     * Instead of swapping, we can overwrite. We maintain a pointer for the next
     * available non-zero slot. We iterate through the array, and whenever we see a
     * non-zero element, we place it at the available slot and increment the slot.
     * Once we finish checking all elements, we fill all remaining slots from our
     * pointer to the end of the array with zeroes.
     *
     * While technically O(N) time and O(1) space, this approach writes elements twice
     * (once during the shift, once during the zero-fill) which makes it slightly
     * less optimal regarding the "minimize operations" follow-up compared to swapping.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We do two partial passes that equal one full pass.
     * - Space Complexity: O(1) auxiliary space. In-place modification.
     */
    public void moveZeroesOverwriteAndFill(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        int insertPos = 0;

        // Step 1: Push all non-zero elements to the front
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[insertPos++] = nums[i];
            }
        }

        // Step 2: Fill the remaining positions with zeros
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        MoveZeroesMasterclass solution = new MoveZeroesMasterclass();

        System.out.println("--- Testing LeetCode 283: Move Zeroes ---");

        // Test Case 1: Standard case (Example 1)
        int[] tc1 = {0, 1, 0, 3, 12};
        System.out.println("\nTest 1 (Optimal Two Pointers / Swap):");
        System.out.println("Input: " + printArray(tc1));
        solution.moveZeroesOptimal(tc1);
        System.out.println("Output: " + printArray(tc1)); // Expected: [1, 3, 12, 0, 0]

        // Test Case 2: Standard case (Example 2)
        int[] tc2 = {0};
        System.out.println("\nTest 2 (Optimal Size 1):");
        System.out.println("Input: " + printArray(tc2));
        solution.moveZeroesOptimal(tc2);
        System.out.println("Output: " + printArray(tc2)); // Expected: [0]

        // Test Case 3: All zeros
        int[] tc3 = {0, 0, 0, 0};
        System.out.println("\nTest 3 (All Zeros - Alternative Overwrite):");
        System.out.println("Input: " + printArray(tc3));
        solution.moveZeroesOverwriteAndFill(tc3);
        System.out.println("Output: " + printArray(tc3)); // Expected: [0, 0, 0, 0]

        // Test Case 4: No zeros
        int[] tc4 = {1, 2, 3, 4, 5};
        System.out.println("\nTest 4 (No Zeros - Optimal):");
        System.out.println("Input: " + printArray(tc4));
        solution.moveZeroesOptimal(tc4);
        System.out.println("Output: " + printArray(tc4)); // Expected: [1, 2, 3, 4, 5]

        // Test Case 5: Zeros at the end
        int[] tc5 = {1, 2, 0, 0, 0};
        System.out.println("\nTest 5 (Zeros at end - Brute Force):");
        System.out.println("Input: " + printArray(tc5));
        solution.moveZeroesBruteForce(tc5);
        System.out.println("Output: " + printArray(tc5)); // Expected: [1, 2, 0, 0, 0]

        // Test Case 6: Consecutive zeros in middle
        int[] tc6 = {4, 2, 4, 0, 0, 3, 0, 5, 1, 0};
        System.out.println("\nTest 6 (Mixed dense - Optimal):");
        System.out.println("Input: " + printArray(tc6));
        solution.moveZeroesOptimal(tc6);
        System.out.println("Output: " + printArray(tc6)); // Expected: [4, 2, 4, 3, 5, 1, 0, 0, 0, 0]
    }

    /**
     * Helper using Java 8 Stream API to format array printing cleanly.
     */
    private static String printArray(int[] arr) {
        return Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
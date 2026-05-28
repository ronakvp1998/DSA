package strivers.slidingwind2pointer;

/**
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement: 167. Two Sum II - Input Array Is Sorted
 * * Given a 1-indexed array of integers numbers that is already sorted in
 * non-decreasing order, find two numbers such that they add up to a specific
 * target number. Let these two numbers be numbers[index1] and numbers[index2]
 * where 1 <= index1 < index2 <= numbers.length.
 * * Return the indices of the two numbers index1 and index2, each incremented
 * by one, as an integer array [index1, index2] of length 2.
 * * The tests are generated such that there is exactly one solution. You may not
 * use the same element twice.
 * * Your solution must use only constant extra space.
 * * Example 1:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * * Example 2:
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
 * * Example 3:
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
 * * Constraints:
 * - 2 <= numbers.length <= 3 * 10^4
 * - -1000 <= numbers[i] <= 1000
 * - numbers is sorted in non-decreasing order.
 * - -1000 <= target <= 1000
 * - The tests are generated such that there is exactly one solution.
 * * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (For Non-DP Problems)
 * ============================================================================
 * Phase 1: Optimal Approach - Two Pointers
 * Phase 2: Brute Force Approach - Nested Loops
 * Phase 3: Alternative Approaches - Binary Search
 */

import java.util.Arrays;

public class TwoSumII {

    /**
     * ============================================================================
     * Phase 1: Optimal Approach (Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Since the array is already sorted in non-decreasing order, we can utilize a
     * Two Pointer approach. We place one pointer at the beginning (smallest value)
     * and one at the end (largest value).
     * - If the sum of the elements at the two pointers equals the target, we found our answer.
     * - If the sum is less than the target, we need a larger sum, so we increment the left pointer.
     * - If the sum is greater than the target, we need a smaller sum, so we decrement the right pointer.
     * This safely narrows down the search space without missing the guaranteed valid pair.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. In the worst case,
     * we traverse the array exactly once from opposite ends.
     * - Space Complexity: O(1) Auxiliary Space. We only use two integer variables
     * for our pointers, requiring no extra heap or stack space. This satisfies
     * the strict constant extra space constraint.
     */
    public int[] twoSumOptimal(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int currentSum = numbers[left] + numbers[right];

            if (currentSum == target) {
                // Return 1-indexed positions
                return new int[]{left + 1, right + 1};
            } else if (currentSum < target) {
                left++; // We need a larger sum
            } else {
                right--; // We need a smaller sum
            }
        }

        return new int[]{-1, -1}; // Should never be reached due to problem constraints
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach (Nested Loops)
     * ============================================================================
     * Detailed Intuition:
     * The simplest way to solve this is to check every possible pair in the array
     * to see if they add up to the target. We fix the first element and iterate
     * through the rest of the array. While this ignores the "sorted" property
     * of the array, it acts as our baseline "Think it" stage.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) where N is the length of the array. We are checking
     * nearly N*(N-1)/2 pairs.
     * - Space Complexity: O(1) Auxiliary Space. Only loop counters are stored.
     */
    public int[] twoSumBruteForce(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i + 1, j + 1};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach (Binary Search)
     * ============================================================================
     * Detailed Intuition:
     * We can leverage the sorted nature of the array using Binary Search. For every
     * element `numbers[i]`, the complementary value we need to find is
     * `target - numbers[i]`. We can binary search for this complement in the
     * subarray extending to the right of `i` (from index `i+1` to `N-1`).
     * * Complexity Analysis:
     * - Time Complexity: O(N log N). We iterate through N elements, and for each
     * element, we perform a binary search which takes O(log N) time.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive variables
     * for binary search boundaries.
     */
    public int[] twoSumBinarySearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            int left = i + 1;
            int right = numbers.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (numbers[mid] == complement) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] < complement) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        TwoSumII solution = new TwoSumII();

        // Object array to hold test parameters: {numbers, target, expected_result}
        Object[][] testCases = {
                {new int[]{2, 7, 11, 15}, 9, new int[]{1, 2}},    // Standard Example 1
                {new int[]{2, 3, 4}, 6, new int[]{1, 3}},         // Standard Example 2
                {new int[]{-1, 0}, -1, new int[]{1, 2}},          // Standard Example 3 (Negatives)
                {new int[]{0, 0, 3, 4}, 0, new int[]{1, 2}},      // Edge: Zeroes and duplicates
                {new int[]{-1000, -500, 0, 500}, -500, new int[]{1, 4}} // Edge: Large negative gap
        };

        System.out.println("Running test suite...\n");

        // Utilizing Java 8 Streams to print the array for clear formatting
        Arrays.stream(testCases).forEach(test -> {
            int[] numbers = (int[]) test[0];
            int target = (int) test[1];
            int[] expected = (int[]) test[2];

            int[] optimalRes = solution.twoSumOptimal(numbers, target);
            int[] bruteRes = solution.twoSumBruteForce(numbers, target);
            int[] binaryRes = solution.twoSumBinarySearch(numbers, target);

            boolean passed = Arrays.equals(optimalRes, expected) &&
                    Arrays.equals(bruteRes, expected) &&
                    Arrays.equals(binaryRes, expected);

            System.out.println("Input Array: " + Arrays.toString(numbers));
            System.out.println("Target: " + target);
            System.out.println("Expected : " + Arrays.toString(expected));
            System.out.println("Optimal  : " + Arrays.toString(optimalRes));
            System.out.println("Brute    : " + Arrays.toString(bruteRes));
            System.out.println("Binary   : " + Arrays.toString(binaryRes));
            System.out.println("Status   : " + (passed ? "PASS" : "FAIL"));
            System.out.println("------------------------------------------------");
        });
    }
}
package com.questions.strivers.slidingwind2pointer.length;

import java.util.Arrays;
import java.util.List;

/**
 * ==========================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ==========================================================================================
 * Problem: 1004. Max Consecutive Ones III
 * Difficulty: Medium
 *
 * Formal Problem Statement:
 * Given a binary array nums and an integer k, return the maximum number of consecutive 1's
 * in the array if you can flip at most k 0's.
 *
 * Example 1:
 * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
 * Output: 6
 * Explanation: [1,1,1,0,0,1,1,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
 *
 * Example 2:
 * Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
 * Output: 10
 * Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - nums[i] is either 0 or 1.
 * - 0 <= k <= nums.length
 * ==========================================================================================
 * Note: As this problem involves finding an optimal contiguous subarray and does not exhibit
 * overlapping subproblems requiring memoization, we utilize the Non-DP Progressive
 * Implementation Roadmap (Section 2.2) below.
 * ==========================================================================================
 */
public class MaxConsecutiveOnesIII {

    /**
     * ======================================================================================
     * Phase 1: Optimal Approach - The "Perfect It" Stage
     * Approach: Non-Shrinking Sliding Window
     * ======================================================================================
     * Detailed Intuition:
     * While a standard sliding window expands and contracts, we only care about the MAXIMUM
     * window size. We can design a window that only grows.
     * We iterate through the array with a `right` pointer. If we include a 0, we decrement
     * our allowance `k`. If `k` drops below 0 (meaning the current window is invalid), we
     * MUST move the `left` pointer forward by exactly one step to maintain the window's size.
     * As we move the `left` pointer, if the character leaving the window is a 0, we get
     * our allowance `k` back.
     * Because the window never shrinks, its size at the end of the iteration will inherently
     * represent the maximum valid window size encountered during the traversal.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of nums. Both left and right pointers
     *   move exactly once through the array in a single pass.
     * - Space Complexity: O(1) Auxiliary Space. No auxiliary heap space is allocated.
     *   Variables used consume minimal, constant space on the stack.
     * ======================================================================================
     */
    public int longestOnesOptimal(int[] nums, int k) {
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            // If we encounter a 0, we use up one of our allowed flips
            if (nums[right] == 0) {
                k--;
            }

            // If flips fall below 0, window is invalid.
            // Shift the entire window to the right without shrinking it.
            if (k < 0) {
                if (nums[left] == 0) {
                    k++; // Reclaim the flip as the 0 leaves the window
                }
                left++;
            }

            right++;
        }

        // The maximum window size is precisely the distance between left and right pointers
        return right - left;
    }

    /**
     * ======================================================================================
     * Phase 2: Brute Force Approach - The "Think It" Stage
     * Approach: Check All Possible Subarrays
     * ======================================================================================
     * Detailed Intuition:
     * The simplest way to solve this is to test every possible starting position `i`. From
     * each starting position, we extend a subarray ending at `j`. We count the number of
     * zeros. As long as the zero count is <= k, the subarray is valid, and we update our
     * maximum length. Once we exceed `k` zeros, we break early and move to the next `i`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) where N is the length of nums. We check all subarrays,
     *   leading to a nested loop traversal.
     * - Space Complexity: O(1) Auxiliary Space. No heap allocations; primitive counters
     *   reside on the execution stack.
     * ======================================================================================
     */
    public int longestOnesBruteForce(int[] nums, int k) {
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {
            int zeroCount = 0;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] == 0) {
                    zeroCount++;
                }

                // If we exceed our flip limit, this and further subarrays from 'i' are invalid
                if (zeroCount > k) {
                    break;
                }

                maxLength = Math.max(maxLength, j - i + 1);
            }
        }

        return maxLength;
    }

    /**
     * ======================================================================================
     * Phase 3: Alternative Approach - The "Refine It" Stage
     * Approach: Dynamic Shrinking Sliding Window
     * ======================================================================================
     * Detailed Intuition:
     * This is the logical stepping stone between the Brute Force and Optimal solutions.
     * We use a sliding window `[left, right]`. We expand `right` to include elements. If
     * our zero count exceeds `k`, the window is invalid. We then run an inner `while` loop
     * to advance `left` until the zero count is valid again (<= k). We update the maximum
     * length only when the window is valid.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2N) -> O(N). In the worst case, each element is visited twice:
     *   once by the `right` pointer and once by the `left` pointer.
     * - Space Complexity: O(1) Auxiliary Space. No heap allocation, purely stack primitives.
     * ======================================================================================
     */
    public int longestOnesAlternative(int[] nums, int k) {
        int left = 0;
        int zeroCount = 0;
        int maxLength = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }

            // Shrink the window from the left until it is valid
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * ======================================================================================
     * 4. TESTING SUITE
     * ======================================================================================
     * Evaluates all approaches against standard LeetCode examples and crucial edge cases:
     * - Arrays with only 0s or only 1s
     * - k = 0 (no flips allowed)
     * - k >= nums.length (can flip everything)
     * Utilizes Java 8 Streams for elegant execution and evaluation formatting.
     * ======================================================================================
     */
    public static void main(String[] args) {
        MaxConsecutiveOnesIII solver = new MaxConsecutiveOnesIII();

        // Standard Java 8 compatible inner class for test cases
        class TestCase {
            int[] nums;
            int k;
            int expected;
            String description;

            TestCase(int[] nums, int k, int expected, String description) {
                this.nums = nums;
                this.k = k;
                this.expected = expected;
                this.description = description;
            }
        }

        List<TestCase> testCases = Arrays.asList(
                new TestCase(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2, 6, "Standard Example 1"),
                new TestCase(new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1}, 3, 10, "Standard Example 2"),
                new TestCase(new int[]{1,1,1,1}, 0, 4, "Edge Case: All 1s, k = 0"),
                new TestCase(new int[]{0,0,0,0}, 2, 2, "Edge Case: All 0s, partial k flips"),
                new TestCase(new int[]{0,0,0,0}, 5, 4, "Edge Case: k is greater than array length"),
                new TestCase(new int[]{1,0,1,0,1}, 0, 1, "Edge Case: Alternating, k = 0 (No flips allowed)")
        );

        System.out.println("======================================================");
        System.out.println("Running Masterclass Test Suite: Max Consecutive Ones III");
        System.out.println("======================================================\n");

        testCases.stream().forEach(tc -> {
            int optimalResult = solver.longestOnesOptimal(tc.nums, tc.k);
            int altResult = solver.longestOnesAlternative(tc.nums, tc.k);
            int bruteResult = solver.longestOnesBruteForce(tc.nums, tc.k);

            boolean passed = (optimalResult == tc.expected) &&
                    (altResult == tc.expected) &&
                    (bruteResult == tc.expected);

            String status = passed ? "✅ PASS" : "❌ FAIL";

            System.out.printf("%s | Desc: %s\n", status, tc.description);
            System.out.printf("   Input: nums = %s, k = %d\n", Arrays.toString(tc.nums), tc.k);
            System.out.printf("   Expected: %d | Optimal: %d | Dynamic Window: %d | Brute: %d\n",
                    tc.expected, optimalResult, altResult, bruteResult);
            System.out.println("------------------------------------------------------");
        });
    }
}
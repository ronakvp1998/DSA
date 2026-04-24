package com.questions.strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 1283. Find the Smallest Divisor Given a Threshold
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * Given an array of integers nums and an integer threshold, we will choose a
 * positive integer divisor, divide all the array by it, and sum the division's
 * result. Find the smallest divisor such that the result mentioned above is
 * less than or equal to threshold.
 * * Each result of the division is rounded to the nearest integer greater than
 * or equal to that element. (For example: 7/3 = 3 and 10/2 = 5).
 * * The test cases are generated so that there will be an answer.
 * * * EXAMPLES:
 * Example 1:
 * Input: nums = [1,2,5,9], threshold = 6
 * Output: 5
 * Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1.
 * If the divisor is 4 we can get a sum of 7 (1+1+2+3) and if the divisor is 5
 * the sum will be 5 (1+1+1+2).
 * * Example 2:
 * Input: nums = [44,22,33,11,1], threshold = 5
 * Output: 44
 * * * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - 1 <= nums[i] <= 10^6
 * - nums.length <= threshold <= 10^6
 * ============================================================================
 */
public class SmallestDivisorGivenThreshold {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer)
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Establish the Search Space: The minimum possible divisor is 1. The
     * maximum useful divisor is the maximum element in the `nums` array
     * (because any divisor larger than the max element will just yield a sum
     * of `nums.length`, which is the absolute minimum sum possible).
     * 2. Apply Binary Search: Calculate a `mid` divisor.
     * 3. Compute the sum of the array elements divided by this `mid` divisor
     * (using ceiling division).
     * 4. If the sum <= threshold, the `mid` is a valid candidate. However, we
     * want the *smallest* divisor, so we record the answer and search the left
     * half (`right = mid - 1`).
     * 5. If the sum > threshold, the divisor is too small (making the fractions
     * too large). We must search the right half (`left = mid + 1`).
     * * * DETAILED INTUITION:
     * We transition to this approach by recognizing a monotonic relationship.
     * As the divisor increases, the resulting sum strictly decreases or stays
     * the same. It never increases. Because of this predictable downward trend,
     * we don't need to test every single divisor linearly. We can halve our
     * search space at every step, making it drastically faster. We use integer
     * math `(num + divisor - 1) / divisor` to compute the ceiling division
     * efficiently without floating-point inaccuracies.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log M), where N is `nums.length` and M is the
     * maximum element in `nums`. Finding the max takes O(N). The binary search
     * runs log(M) times, and inside the loop, computing the sum takes O(N).
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative loop, no recursion).
     * - Heap Space: O(1) (No new dynamic objects/arrays created).
     */
    public static int smallestDivisorOptimal(int[] nums, int threshold) {
        int left = 1;
        int right = 0;

        // Find the maximum element to bound our search space
        for (int num : nums) {
            right = Math.max(right, num);
        }

        int optimalDivisor = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (calculateSum(nums, mid) <= threshold) {
                optimalDivisor = mid; // Valid divisor, but search for a smaller one
                right = mid - 1;
            } else {
                left = mid + 1; // Divisor too small, need a larger divisor to lower the sum
            }
        }

        return optimalDivisor;
    }

    // Helper method to compute the total sum using ceiling division
    private static long calculateSum(int[] nums, int divisor) {
        long totalSum = 0;
        for (int num : nums) {
            // Equivalent to Math.ceil((double) num / divisor)
            totalSum += (num + divisor - 1) / divisor;
        }
        return totalSum;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Start guessing the divisor `d` starting from 1.
     * 2. For each `d`, calculate the sum of `ceil(nums[i] / d)`.
     * 3. The first `d` that results in a sum <= threshold is our answer.
     * * * DETAILED INTUITION:
     * This is the literal translation of the problem into code. We want the
     * smallest positive integer, so we count up from 1. Since the sum goes
     * down as the divisor goes up, the very first time we dip below or equal
     * to the threshold, we have guaranteed the smallest valid divisor.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * M), where N is `nums.length` and M is the actual
     * answer (which can be up to the maximum element in `nums`, bounded at 10^6).
     * This will cause a Time Limit Exceeded (TLE) error for large arrays and
     * large values.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int smallestDivisorBruteForce(int[] nums, int threshold) {
        int divisor = 1;
        while (true) {
            long totalSum = 0;
            for (int num : nums) {
                totalSum += (num + divisor - 1) / divisor;
            }
            if (totalSum <= threshold) {
                return divisor;
            }
            divisor++;
        }
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Similar to "Koko Eating Bananas" or "Capacity To Ship Packages Within
     * D Days", this problem falls strictly under the "Binary Search on Answer"
     * paradigm.
     * * Why not Greedy, Two Pointers, or Sliding Window?
     * - We are not looking for a subset, subarray, or sequence (eliminating
     * Sliding Window / Two Pointers).
     * - We are globally applying a single value across the entire array, and
     * checking a condition based on a monotonic function. Binary Search is
     * the mathematically proven optimum for finding a threshold over a
     * monotonic search space.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Validating implementations against examples and critical edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Smallest Divisor Given a Threshold Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] nums1 = {1, 2, 5, 9};
        int threshold1 = 6;
        runTestCase(1, nums1, threshold1, 5);

        // Test Case 2: Standard case (Example 2)
        int[] nums2 = {44, 22, 33, 11, 1};
        int threshold2 = 5;
        runTestCase(2, nums2, threshold2, 44);

        // Test Case 3: Edge Case - Threshold equals nums.length
        // Divisor must be max(nums) to reduce every element to 1.
        int[] nums3 = {21212, 10101, 12121};
        int threshold3 = 3;
        runTestCase(3, nums3, threshold3, 21212);

        // Test Case 4: Edge Case - Large threshold, small array
        // Even with divisor 1, the sum is already well below the threshold.
        int[] nums4 = {1, 1, 1};
        int threshold4 = 100;
        runTestCase(4, nums4, threshold4, 1);

        // Test Case 5: Large values and Constraints Check
        // Prevents integer overflow bugs during summation
        int[] nums5 = {1000000, 1000000, 1000000};
        int threshold5 = 3;
        runTestCase(5, nums5, threshold5, 1000000);
    }

    private static void runTestCase(int testNumber, int[] nums, int threshold, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = smallestDivisorOptimal(nums, threshold);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: nums = " + java.util.Arrays.toString(nums) + ", threshold = " + threshold);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}
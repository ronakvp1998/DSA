package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: PRINT MAXIMUM SUBARRAY
 * ============================================================================
 * * Print subarray with maximum subarray sum (Extended 53. Maximum Subarray)
 * Solved | Medium | Topics | Companies
 * * Hint:
 * Given an integer array nums, find the contiguous subarray (containing at least
 * one number) which has the largest sum, and return/print that exact subarray
 * along with its sum.
 * * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: Subarray: [4, -1, 2, 1], Sum: 6
 * Explanation: The contiguous subarray [4,-1,2,1] yields the maximum sum 6.
 * * Example 2:
 * Input: nums = [1]
 * Output: Subarray: [1], Sum: 1
 * * Example 3:
 * Input: nums = [-3, -5, -2, -9]
 * Output: Subarray: [-2], Sum: -2
 * Explanation: In an all-negative array, the maximum sum is the single largest
 * negative number.
 * * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Kadane's Algorithm with Index Tracking)
 * ============================================================================
 * To print the subarray, we must track three pointers during Kadane's algorithm:
 * 1. `currStart`: The starting index of the current running sum sequence.
 * 2. `bestStart`: The finalized starting index of the global maximum sequence.
 * 3. `bestEnd`:   The finalized ending index of the global maximum sequence.
 * * When we decide to "start fresh" at index `i`, `currStart` becomes `i`.
 * When our running sum beats the `globalMax`, we snapshot `currStart` into
 * `bestStart`, and current index `i` into `bestEnd`.
 * * Visualizing transitions for nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]:
 * * i | nums[i] | currMax  | globalMax | currStart | bestStart | bestEnd
 * --|---------|----------|-----------|-----------|-----------|--------
 * 0 |   -2    |   -2     |    -2     |     0     |     0     |    0
 * 1 |    1    |    1 (*) |     1 (+) |     1 (*) |     1 (+) |    1 (+)
 * 2 |   -3    |   -2     |     1     |     1     |     1     |    1
 * 3 |    4    |    4 (*) |     4 (+) |     3 (*) |     3 (+) |    3 (+)
 * 4 |   -1    |    3     |     4     |     3     |     3     |    3
 * 5 |    2    |    5     |     5 (+) |     3     |     3 (+) |    5 (+)
 * 6 |    1    |    6     |     6 (+) |     3     |     3 (+) |    6 (+)
 * 7 |   -5    |    1     |     6     |     3     |     3     |    6
 * 8 |    4    |    5     |     6     |     3     |     3     |    6
 * * (*) Indicates a "fresh start" (currStart updated).
 * (+) Indicates a new global max (bestStart and bestEnd updated).
 * * Result: Subarray from index 3 to 6 -> [4, -1, 2, 1]
 * ============================================================================
 */

import java.util.Arrays;

public class MaxSubArraySumPrint {

    /**
     * Helper class to hold both the max sum and the resulting subarray bounds
     */
    static class Result {
        int sum;
        int[] subarray;

        Result(int sum, int[] subarray) {
            this.sum = sum;
            this.subarray = subarray;
        }

        @Override
        public String toString() {
            return "Sum: " + sum + " | Subarray: " + Arrays.toString(subarray);
        }
    }

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * Evaluate every possible subarray. Use a nested loop where the outer loop
     * `i` defines the start index and the inner loop `j` defines the end index.
     * Keep a running sum for the inner loop and record the indices if the sum
     * exceeds our maximum.
     * * Detailed Intuition:
     * To find the bounds of the maximum subarray, we literally check the bounds
     * of every single subarray. By updating a running sum in the inner loop, we
     * avoid an O(N^3) complexity and bring it down to O(N^2). We snapshot `i`
     * and `j` whenever a new maximum is found.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2)
     * Two nested loops over the array.
     * - Space Complexity: O(N)
     * Heap space is O(N) to instantiate and return the actual subarray slice.
     * Auxiliary stack space is O(1).
     */
    public Result printMaxSubArrayBruteForce(int[] nums) {
        int globalMax = Integer.MIN_VALUE;
        int bestStart = 0;
        int bestEnd = 0;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];

                if (currentSum > globalMax) {
                    globalMax = currentSum;
                    bestStart = i;
                    bestEnd = j;
                }
            }
        }

        // Extract the sub-array using the captured bounds
        int[] subArray = Arrays.copyOfRange(nums, bestStart, bestEnd + 1);
        return new Result(globalMax, subArray);
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL APPROACH - KADANE'S ALGORITHM (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * Use Kadane's Algorithm but introduce tracking variables: `currStart`,
     * `bestStart`, and `bestEnd`. Whenever `nums[i]` by itself is greater than
     * `currentMax + nums[i]`, we reset `currentMax` to `nums[i]` AND reset
     * `currStart` to `i`. Whenever `currentMax` sets a new `globalMax`, we copy
     * `currStart` to `bestStart`, and set `bestEnd` to `i`.
     * * Detailed Intuition:
     * Standard Kadane's tells us *what* the max sum is, but inherently throws
     * away the history of *where* it came from. By tracking the exact moment a
     * sequence begins (the "fresh start" condition) and locking in that start
     * index ONLY when the sequence breaks a new global record, we can perfectly
     * trace the structural boundaries of the optimal subarray in a single pass.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * A single iteration through the array. Arrays.copyOfRange takes O(K) where
     * K is the length of the subarray, which is bounded by N. Overall Time: O(N).
     * - Space Complexity: O(N)
     * Heap space is O(N) required to construct and return the resultant subarray.
     * Auxiliary stack space is O(1).
     */
    public Result printMaxSubArrayOptimal(int[] nums) {
        // Handle edge case of empty array
        if (nums == null || nums.length == 0) {
            return new Result(0, new int[]{});
        }

        int currentMax = nums[0];
        int globalMax = nums[0];

        int currStart = 0;
        int bestStart = 0;
        int bestEnd = 0;

        for (int i = 1; i < nums.length; i++) {
            // Decision: Do we start a new sequence exactly here, or extend?
            if (nums[i] > currentMax + nums[i]) {
                currentMax = nums[i];
                currStart = i; // Reset the starting pointer of our current streak
            } else {
                currentMax += nums[i];
            }

            // Did this current sequence break the global record?
            if (currentMax > globalMax) {
                globalMax = currentMax;

                // Snapshot the boundaries!
                bestStart = currStart;
                bestEnd = i;
            }
        }

        // Extract the sub-array using the captured bounds
        int[] subArray = Arrays.copyOfRange(nums, bestStart, bestEnd + 1);
        return new Result(globalMax, subArray);
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        MaxSubArraySumPrint solution = new MaxSubArraySumPrint();

        // Define Test Cases
        int[][] testCases = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4}, // Example 1: Standard mix
                {1},                             // Example 2: Single element
                {5, 4, -1, 7, 8},                // Example 3: Mostly positive
                {-8, -3, -6, -2, -5},            // Edge Case: All negative
                {0, 0, 0, 0},                    // Edge Case: All zeroes
                {10, -2, -3, 4, 5, -10, 15}      // Reset case: earlier peak vs later peak
        };

        System.out.println("=========================================================");
        System.out.println("Executing Print Maximum Subarray Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums));

            // 1. Brute Force
            long start1 = System.nanoTime();
            Result res1 = solution.printMaxSubArrayBruteForce(nums);
            long end1 = System.nanoTime();

            // 2. Optimal (Kadane's)
            long start2 = System.nanoTime();
            Result res2 = solution.printMaxSubArrayOptimal(nums);
            long end2 = System.nanoTime();

            System.out.println("  [Brute Force] " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Optimal]     " + res2 + " | Time: " + (end2 - start2) + " ns");

            // Verification
            boolean isValid = res1.sum == res2.sum && Arrays.equals(res1.subarray, res2.subarray);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------------------");
        }
    }
}
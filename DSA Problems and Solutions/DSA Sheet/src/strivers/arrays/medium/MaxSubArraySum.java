package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: MAXIMUM SUBARRAY
 * ============================================================================
 * * 53. Maximum Subarray
 * Solved | Medium | Topics | Companies
 * * Given an integer array nums, find the subarray with the largest sum, and return its sum.
 * * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
 * * Example 2:
 * Input: nums = [1]
 * Output: 1
 * Explanation: The subarray [1] has the largest sum 1.
 * * Example 3:
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 * * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * * Follow up: If you have figured out the O(n) solution, try coding another
 * solution using the divide and conquer approach, which is more subtle.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Dynamic Programming & Recursion Tree)
 * ============================================================================
 * We define our subproblem: solve(i) = the maximum subarray sum *ending exactly* at index i.
 * To find the overall maximum, we simply take the max of solve(i) for all valid i.
 * Transition: solve(i) = max(nums[i], solve(i-1) + nums[i])
 * (We either start a new subarray at nums[i], or extend the previous best subarray).
 * * RECURSION TREE (For finding the max ending at index 2 for nums = [-2, 1, -3])
 * * solve(2)
 * /
 * max(nums[2], solve(1) + nums[2])
 * /
 * max(nums[1], solve(0) + nums[1])
 * /
 * nums[0] (Base Case: index 0)
 * * Without memoization, if we call solve(i) for every index from 0 to n-1 to find
 * the global max, we perform a massive amount of redundant recalculation.
 * * DP ARRAY FILLED (Example 1: nums = [-2,1,-3,4,-1,2,1,-5,4])
 * Index :   0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8
 * nums  :  -2 |  1 | -3 |  4 | -1 |  2 |  1 | -5 |  4
 * dp    :  -2 |  1 | -2 |  4 |  3 |  5 |  6 |  1 |  5
 * * Global Max = max(dp) = 6
 * ============================================================================
 */

import java.util.Arrays;

public class MaxSubArraySum {

    public int maxSubArray1(int[] nums) {
        // Array to store the maximum subarray sum ending at each index
        int[] arr = new int[nums.length];

        // Base case: first element
        arr[0] = nums[0];
        int globalMax = arr[0];

        for (int i = 1; i < nums.length; i++) {
            // THE "RESET" LOGIC:
            // If the sum up to the previous step is negative, it hurts us.
            // We "reset to 0" by not adding it, starting fresh at nums[i].
            if (arr[i - 1] < 0) {
                arr[i] = nums[i];
            }
            // Otherwise, we carry the positive momentum forward
            else {
                arr[i] = arr[i - 1] + nums[i];
            }

            // Check if our newly calculated sum is the best we've seen overall
            globalMax = Math.max(globalMax, arr[i]);
        }

        return globalMax;
    }

    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE; // Start with the smallest possible number
        int currentSum = 0;

        for (int i = 0; i < nums.length; i++) {
            // 1. Add the current number to our running total
            currentSum += nums[i];

            // 2. Update the global max if our current running total is higher
            maxSum = Math.max(maxSum, currentSum);

            // 3. HERE IS THE RESET: If the running total dips below zero,
            // it's useless to us. Ditch it and start fresh at 0.
            if (currentSum < 0) {
                currentSum = 0;
            }
        }

        return maxSum;
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage - Kadane's Algorithm)
     * ========================================================================
     * Approach:
     * We discard the O(N) DP array and replace it with a single variable to track
     * the maximum sum ending at the current position.
     * * Detailed Intuition:
     * If we look at the transition `dp[i] = Math.max(nums[i], dp[i - 1] + nums[i])`,
     * we see that the calculation for `dp[i]` ONLY requires `dp[i-1]`. The rest of
     * the historical DP array (`dp[i-2]`, `dp[i-3]`, etc.) is completely useless.
     * We can optimize away the array and just keep a running "current best" integer.
     * This is formally known as Kadane's Algorithm.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * Single traversal of the array.
     * - Space Complexity: O(1)
     * Only primitive integer variables are used. Heap space O(1), Stack space O(1).
     */
    public int maxSubArraySpaceOptimized(int[] nums) {
        int currentMax = nums[0]; // Represents dp[i-1]
        int globalMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            globalMax = Math.max(globalMax, currentMax);
        }

        return globalMax;
    }


    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * Approach:
     * We define a recursive function `findMaxEndingHere(i)` that returns the max
     * subarray sum ending strictly at index `i`. To find the overall maximum
     * subarray sum, we iterate over every index `i` in the array and take the
     * maximum of all `findMaxEndingHere(i)` calls.
     * * Detailed Intuition:
     * Before optimizing with DP, we break the problem down into its raw recursive
     * subproblems. A contiguous subarray ending at `i` can either be just the
     * element `nums[i]` itself, or the best contiguous subarray ending at `i-1`
     * plus `nums[i]`. We explore both paths for every possible ending point.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2)
     * Calling `findMaxEndingHere(i)` takes O(i) time because it steps back to
     * index 0. We call this inside a loop from 0 to N-1, leading to 1+2+...+N
     * operations.
     * - Space Complexity: O(N)
     * The depth of the recursion tree goes up to N. Auxiliary stack space is O(N).
     * Heap space is O(1).
     */
    public int maxSubArrayBruteForceRec(int[] nums) {
        int maxOverall = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            maxOverall = Math.max(maxOverall, findMaxEndingHere(nums, i));
        }
        return maxOverall;
    }

    private int findMaxEndingHere(int[] nums, int i) {
        // Base case: at index 0, the max subarray ending here is just the element itself
        if (i == 0) return nums[0];

        // Recursive transition
        return Math.max(nums[i], findMaxEndingHere(nums, i - 1) + nums[i]);
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * Approach:
     * We utilize the exact same recursive structure as Phase 1, but we introduce
     * a `memo` array to cache the result of `findMaxEndingHere(i)` once it is
     * computed.
     * * Detailed Intuition:
     * The logical transition here is identifying overlapping subproblems.
     * `solve(5)` needs `solve(4)`, and to find the global max, our main loop
     * also independently asks for `solve(4)`. By memoizing, every index is
     * computed exactly once. We trade space for a dramatic improvement in time.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * Each state from 0 to N-1 is computed exactly once.
     * - Space Complexity: O(N)
     * We use O(N) heap space for the `memo` array and O(N) auxiliary stack
     * space for the recursion depth.
     */
    public int maxSubArrayMemoization(int[] nums) {
        Integer[] memo = new Integer[nums.length];
        int maxOverall = Integer.MIN_VALUE;

        // To avoid an O(N) iterative loop in the main function causing StackOverflow,
        // we can actually compute the state for the last element, which forces the
        // recursion to build the entire memo array naturally.
        findMaxEndingHereMemo(nums, nums.length - 1, memo);

        // Once the memo array is populated, the global max is the max value in memo.
        for (int i = 0; i < nums.length; i++) {
            maxOverall = Math.max(maxOverall, memo[i]);
        }

        return maxOverall;
    }

    private int findMaxEndingHereMemo(int[] nums, int i, Integer[] memo) {
        if (i == 0) {
            memo[0] = nums[0];
            return nums[0];
        }

        if (memo[i] != null) {
            return memo[i];
        }

        int prevBest = findMaxEndingHereMemo(nums, i - 1, memo);
        memo[i] = Math.max(nums[i], prevBest + nums[i]);
        return memo[i];
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * Approach:
     * We eliminate recursion entirely. We create a `dp` array of size N where
     * `dp[i]` represents the max subarray ending at index `i`. We iteratively
     * build the solution from index 1 to N-1.
     * * Detailed Intuition:
     * We recognized that recursion risks StackOverflow for large inputs (like N=10^5).
     * Because state `i` strictly depends on state `i-1`, we can iteratively build
     * our DP table from left to right. This guarantees safety from stack overflow
     * and reduces overhead from function calls.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * A single pass through the array.
     * - Space Complexity: O(N)
     * O(N) heap space is allocated for the `dp` array. Auxiliary stack space is O(1).
     * * ------------------------------------------------------------------------
     * CRITICAL VISUALIZATION:
     * DP Array State for nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
     * * EXACT DEFAULT STATE (Immediately after base case init, before loops):
     * Index :   0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8
     * dp    :  -2 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0
     * * EXACT FINAL STATE (After loops complete):
     * Index :   0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8
     * dp    :  -2 |  1 | -2 |  4 |  3 |  5 |  6 |  1 |  5
     * ------------------------------------------------------------------------
     */
    public int maxSubArrayTabulation(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        // Base case initialization
        dp[0] = nums[0];
        int maxOverall = dp[0];

        // Main nested/iterative logic
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            maxOverall = Math.max(maxOverall, dp[i]);
        }

        return maxOverall;
    }

    /**
     * ========================================================================
     * PHASE 5: ALTERNATIVE APPROACH (Divide and Conquer)
     * ========================================================================
     * Approach:
     * Recursively divide the array into two halves. The maximum subarray must lie:
     * 1. Entirely in the left half.
     * 2. Entirely in the right half.
     * 3. Crossing the midpoint.
     * We calculate the max for all three and return the highest.
     * * Detailed Intuition:
     * This approach doesn't use DP. It's built on a binary tree splitting mechanic
     * similar to Merge Sort. It's often required in interviews just to test tree
     * traversal and segment logic, even though it's less optimal in practice than Kadane's.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N)
     * The array is split in half log(N) times. At each level of the recursion tree,
     * calculating the "crossing sum" takes O(N) time overall. Total = O(N log N).
     * (Note: A more complex O(N) Segment-Tree-like D&C exists, but this is the standard).
     * - Space Complexity: O(log N)
     * Heap space is O(1). Auxiliary stack space for recursion depth is O(log N).
     */
    public int maxSubArrayDivideAndConquer(int[] nums) {
        return findMaxSubArrayRec(nums, 0, nums.length - 1);
    }

    private int findMaxSubArrayRec(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = left + (right - left) / 2;

        int leftMax = findMaxSubArrayRec(nums, left, mid);
        int rightMax = findMaxSubArrayRec(nums, mid + 1, right);
        int crossMax = findMaxCrossingSubArray(nums, left, mid, right);

        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    private int findMaxCrossingSubArray(int[] nums, int left, int mid, int right) {
        int leftSum = Integer.MIN_VALUE;
        int currentSum = 0;
        // Scan leftward from mid
        for (int i = mid; i >= left; i--) {
            currentSum += nums[i];
            leftSum = Math.max(leftSum, currentSum);
        }

        int rightSum = Integer.MIN_VALUE;
        currentSum = 0;
        // Scan rightward from mid+1
        for (int i = mid + 1; i <= right; i++) {
            currentSum += nums[i];
            rightSum = Math.max(rightSum, currentSum);
        }

        return leftSum + rightSum;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        MaxSubArraySum solution = new MaxSubArraySum();

        // Define Test Cases
        int[][] testCases = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4}, // Example 1: Standard mix
                {1},                             // Example 2: Single element
                {5, 4, -1, 7, 8},                // Example 3: Mostly positive
                {-5, -2, -9, -1, -4},            // Edge Case: All negative
                {0, 0, 0, 0},                    // Edge Case: All zeroes
                {2, -1, 2, 3, 4, -5}             // Alternating positives and negatives
        };

        System.out.println("=============================================");
        System.out.println("Executing Maximum Subarray Testing Suite");
        System.out.println("=============================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums));

            // 1. Brute Force
            long start1 = System.nanoTime();
            int res1 = solution.maxSubArrayBruteForceRec(nums);
            long end1 = System.nanoTime();

            // 2. Memoization
            long start2 = System.nanoTime();
            int res2 = solution.maxSubArrayMemoization(nums);
            long end2 = System.nanoTime();

            // 3. Tabulation
            long start3 = System.nanoTime();
            int res3 = solution.maxSubArrayTabulation(nums);
            long end3 = System.nanoTime();

            // 4. Space Optimized (Kadane's)
            long start4 = System.nanoTime();
            int res4 = solution.maxSubArraySpaceOptimized(nums);
            long end4 = System.nanoTime();

            // 5. Divide and Conquer
            long start5 = System.nanoTime();
            int res5 = solution.maxSubArrayDivideAndConquer(nums);
            long end5 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Memoization] Output: " + res2 + " | Time: " + (end2 - start2) + " ns");
            System.out.println("  [Tabulation]  Output: " + res3 + " | Time: " + (end3 - start3) + " ns");
            System.out.println("  [Kadane's]    Output: " + res4 + " | Time: " + (end4 - start4) + " ns");
            System.out.println("  [Div & Conq]  Output: " + res5 + " | Time: " + (end5 - start5) + " ns");

            // Verification
            boolean isValid = (res1 == res2) && (res2 == res3) && (res3 == res4) && (res4 == res5);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------");
        }
    }
}


//
///**
// * Problem Statement:
// * Given an integer array `arr`, find the contiguous subarray (containing at least one number)
// * which has the largest sum and return its sum. Also print the subarray.
// *
// 53. Maximum Subarray
// Given an integer array nums, find the subarray with the largest sum, and return its sum.
//
// Example 1:
// Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
// Output: 6
// Explanation: The subarray [4,-1,2,1] has the largest sum 6.
//
// Example 2:
// Input: nums = [1]
// Output: 1
// Explanation: The subarray [1] has the largest sum 1.
//
// Example 3:
// Input: nums = [5,4,-1,7,8]
// Output: 23
// Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
//
// Constraints:
//
// 1 <= nums.length <= 105
// -104 <= nums[i] <= 104
// */
//
//public class MaxSubArraySum {
//
//    public static void main(String[] args) {
//        int[] arr = {-2, -3, 4, -1, -2, 1, 5, -2};
//        int n = arr.length;
//
//        System.out.println("Brute Force: " + maxSubarraySum1(arr, n));
//        System.out.println("Better Approach: " + maxSubarraySum2(arr, n));
//        System.out.println("Kadane’s Optimized: " + maxSubarraySum3(arr, n));
//        System.out.println("Kadane’s with Subarray Print: " + maxSubarraySum4(arr, n));
//        System.out.println("Kadane’s with Negative Handling: " + maxSubarraySum5(arr));
//        System.out.println("Kadane’s Full (Print + Negatives): " + maxSubarraySum6(arr));
//    }
//
//    // Approach 1: Brute Force - Try all subarrays using 3 loops
//    // Time: O(N^3), Space: O(1)
//    private static int maxSubarraySum1(int[] arr, int n) {
//        int maxi = Integer.MIN_VALUE;
//
//        for (int i = 0; i < n; i++) {
//            for (int j = i; j < n; j++) {
//                int sum = 0;
//
//                for (int k = i; k <= j; k++) {
//                    sum += arr[k]; // Calculate sum of subarray arr[i...j]
//                }
//
//                maxi = Math.max(maxi, sum); // Update max sum
//            }
//        }
//
//        return maxi;
//    }
//
//    // Approach 2: Better Approach - Remove inner loop by maintaining sum
//    // Time: O(N^2), Space: O(1)
//    private static int maxSubarraySum2(int[] arr, int n) {
//        int maxi = Integer.MIN_VALUE;
//
//        for (int i = 0; i < n; i++) {
//            int sum = 0;
//
//            for (int j = i; j < n; j++) {
//                sum += arr[j]; // Add element to sum of arr[i...j]
//                maxi = Math.max(maxi, sum); // Track max
//            }
//        }
//
//        return maxi;
//    }
//
//    // Approach 3: Kadane’s Algorithm (Optimized) - No printing
//    // Time: O(N), Space: O(1)
//    private static long maxSubarraySum3(int[] arr, int n) {
//        long maxi = Long.MIN_VALUE;
//        long sum = 0;
//
//        for (int i = 0; i < n; i++) {
//            sum += arr[i]; // Add current element
//            maxi = Math.max(maxi, sum); // Update max
//            if (sum < 0) sum = 0; // Reset if sum goes negative
//        }
//
//        return maxi;
//    }
//
//    // Approach 4: Kadane’s with Subarray Printing
//    // Time: O(N), Space: O(1)
//    private static long maxSubarraySum4(int[] arr, int n) {
//        long maxi = Long.MIN_VALUE;
//        long sum = 0;
//
//        int start = 0;
//        int ansStart = -1, ansEnd = -1;
//
//        for (int i = 0; i < n; i++) {
//            if (sum == 0) start = i; // New start for subarray
//
//            sum += arr[i];
//
//            if (sum > maxi) {
//                maxi = sum;
//                ansStart = start;
//                ansEnd = i;
//            }
//
//            if (sum < 0) sum = 0;
//        }
//
//        // Print subarray with max sum
//        System.out.print("Max Sum Subarray (Kadane + Print): ");
//        for (int i = ansStart; i <= ansEnd; i++) {
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println();
//
//        return maxi;
//    }
//
//    // Approach 5: Kadane’s with Handling All-Negatives (no printing)
//    // Time: O(N), Space: O(1)
//    private static int maxSubarraySum5(int[] arr) {
//        int maxSum = Integer.MIN_VALUE;
//        int sum = 0;
//
//        for (int i = 0; i < arr.length; i++) {
//            sum += arr[i];
//
//            if (sum < 0) sum = 0;
//
//            maxSum = Math.max(maxSum, sum);
//        }
//
//        // If all numbers are negative, maxSum would be 0. We pick max element.
//        if (maxSum == 0) {
//            int max = Integer.MIN_VALUE;
//            for (int num : arr) {
//                max = Math.max(max, num);
//            }
//            maxSum = max;
//        }
//
//        return maxSum;
//    }
//
//    // Approach 6: Kadane’s Full - Print + Handle all-negative case
//    // Time: O(N), Space: O(1)
//    private static int maxSubarraySum6(int[] arr) {
//        int maxSum = Integer.MIN_VALUE;
//        int sum = 0;
//        int start = 0, ansStart = -1, ansEnd = -1;
//
//        for (int i = 0; i < arr.length; i++) {
//            if (sum == 0) start = i; // Start of new subarray
//
//            sum += arr[i];
//
//            if (sum < 0) {
//                sum = 0;
//            }
//
//            if (maxSum < sum) {
//                maxSum = sum;
//                ansStart = start;
//                ansEnd = i;
//            }
//        }
//
//        // Handle negative-only case
//        if (maxSum == 0) {
//            int max = Integer.MIN_VALUE;
//            for (int i = 0; i < arr.length; i++) {
//                if (arr[i] > max) {
//                    max = arr[i];
//                    ansStart = i;
//                    ansEnd = i;
//                }
//            }
//            maxSum = max;
//        }
//
//        // Print the subarray
//        System.out.print("Max Sum Subarray (Kadane + Print + Negative): ");
//        for (int i = ansStart; i <= ansEnd; i++) {
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println();
//
//        return maxSum;
//    }
//}
//
////Method	                    Name	        Time Complexity	    Space Complexity	Prints Subarray	Handles All-Negatives
////Brute Force	                maxSubarraySum1	O(N³)	            O(1)	            ❌	            ✅
////Better	                    maxSubarraySum2	O(N²)	            O(1)	            ❌	            ✅
////Kadane	                    maxSubarraySum3	O(N)	            O(1)	            ❌	            ❌
////Kadane + Print	            maxSubarraySum4	O(N)	            O(1)	            ✅	            ❌
////Kadane + Negatives	        maxSubarraySum5	O(N)	            O(1)	            ❌	            ✅
////Kadane + Print + Negatives	maxSubarraySum6	O(N)	            O(1)	            ✅	            ✅
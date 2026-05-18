package strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 410. Split Array Largest Sum
 * Solved | Hard
 * * * PROBLEM STATEMENT:
 * Given an integer array nums and an integer k, split nums into k non-empty
 * subarrays such that the largest sum of any subarray is minimized.
 * Return the minimized largest sum of the split.
 * A subarray is a contiguous part of the array.
 * * * EXAMPLES:
 * Example 1:
 * Input: nums = [7,2,5,10,8], k = 2
 * Output: 18
 * Explanation: There are four ways to split nums into two subarrays.
 * The best way is to split it into [7,2,5] and [10,8], where the largest sum
 * among the two subarrays is only 18.
 * * Example 2:
 * Input: nums = [1,2,3,4,5], k = 2
 * Output: 9
 * Explanation: There are four ways to split nums into two subarrays.
 * The best way is to split it into [1,2,3] and [4,5], where the largest sum
 * among the two subarrays is only 9.
 * * * CONSTRAINTS:
 * - 1 <= nums.length <= 1000
 * - 0 <= nums[i] <= 10^6
 * - 1 <= k <= min(50, nums.length)
 * ============================================================================
 */
public class SplitArrayLargestSum {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer Space)
     * ========================================================================
     * * APPROACH & STEPS:
     * In structured revision sheets, this problem serves as the canonical
     * capstone for the "Binary Search on Answer" pattern. It is mathematically
     * and logically identical to the "Allocate Minimum Number of Pages" and
     * "Painter's Partition" problems.
     * * 1. Define the Search Space:
     * - Minimum possible max-sum (`low`): The largest single element in the
     * array (`max(nums)`). If k == nums.length, every element is its own
     * subarray, and the max sum is simply the largest element.
     * - Maximum possible max-sum (`high`): The total sum of the array
     * (`sum(nums)`). If k == 1, the whole array is one subarray.
     * 2. Apply Binary Search: Calculate `mid` as the proposed maximum subarray sum.
     * 3. Greedily Check Feasibility: Use a helper function `countSubarrays` to
     * iterate through `nums`. Keep adding elements to a running sum. If adding
     * the next element exceeds `mid`, we are forced to make a split.
     * 4. Decision Logic:
     * - If `splitsRequired <= k`, this `mid` is a valid upper bound! We record
     * it as a potential answer but aggressively search the left half
     * (`high = mid - 1`) to see if we can push the maximum sum even lower.
     * - If `splitsRequired > k`, the capacity `mid` is too restrictive, forcing
     * us to make too many splits. We must relax the limit by searching the right
     * half (`low = mid + 1`).
     * * * DETAILED INTUITION:
     * We exploit the monotonic nature of the problem: as the allowed maximum
     * subarray sum increases, the number of required splits strictly decreases.
     * Instead of looking at array indices, we binary search the continuous
     * range of possible *answers*.
     * * Note on `splitsRequired < k`: If we can do it in fewer splits than `k`
     * without exceeding `mid`, we can definitely do it in exactly `k` splits
     * (by just arbitrarily splitting an existing valid subarray, which only
     * lowers the sums of those pieces).
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log(Sum - Max)), where N is nums.length.
     * Finding the Max and Sum takes O(N). The search space size is `Sum - Max`.
     * Each of the `log(Sum - Max)` binary search iterations requires an O(N)
     * greedy check.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative loop).
     * - Heap Space: O(1) (No dynamic collections used).
     */
    public static int splitArrayOptimal(int[] nums, int k) {
        int low = 0;
        long high = 0; // Using long for safety against massive sums

        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        long optimalResult = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;

            if (countSubarrays(nums, mid) <= k) {
                optimalResult = mid; // Valid configuration found, but try to minimize
                high = mid - 1;
            } else {
                low = (int) mid + 1; // Limit too strict, we used too many subarrays
            }
        }

        return (int) optimalResult;
    }

    // Helper method to greedily simulate subarray splitting
    private static int countSubarrays(int[] nums, long maxSumLimit) {
        int subarraysRequired = 1;
        long currentSum = 0;

        for (int num : nums) {
            if (currentSum + num > maxSumLimit) {
                // Current subarray exceeds limit, must make a cut
                subarraysRequired++;
                currentSum = num; // Start new subarray with the current element
            } else {
                // Safe to append to current subarray
                currentSum += num;
            }
        }

        return subarraysRequired;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Calculate the minimum possible bound (`low` = max element) and the
     * maximum possible bound (`high` = sum of elements).
     * 2. Run a linear loop starting from `limit = low` up to `high`.
     * 3. For each `limit`, simulate the array splitting using the greedy helper.
     * 4. The very first `limit` that results in `<= k` subarrays is mathematically
     * guaranteed to be the minimum largest sum. Return it immediately.
     * * * DETAILED INTUITION:
     * This mimics brute-forcing the answer space. "Can I make the max sum 10? No.
     * 11? No. 18? Yes." The transition from false to true happens at exactly
     * the optimal answer.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * (Sum - Max)). The loop runs `Sum - Max` times,
     * and for every iteration, we do an O(N) array traversal.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int splitArrayBruteForce(int[] nums, int k) {
        int low = 0;
        long high = 0;

        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        for (long limit = low; limit <= high; limit++) {
            if (countSubarrays(nums, limit) <= k) {
                return (int) limit;
            }
        }

        return (int) low;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Dynamic Programming)
     * ========================================================================
     * * Approach: Top-Down Memoization or Bottom-Up Tabulation
     * - This problem actually has overlapping subproblems and optimal substructure
     * making it solvable via DP (Matrix Chain Multiplication style).
     * - `dp[i][j]` = the minimum largest subarray sum for splitting the first
     * `i` elements into `j` parts.
     * - Transition: To compute `dp[i][j]`, we try placing the last split at
     * every possible index `p` from `j-1` to `i-1`. The cost is the maximum of:
     * 1. The DP value of the left part: `dp[p][j-1]`
     * 2. The sum of the right part: `sum(nums[p]...nums[i-1])`
     * - We take the minimum of these maximums across all possible split points `p`.
     * * * Why Binary Search is vastly superior:
     * - DP Time Complexity: O(K * N^2). With N = 1000 and K = 50, this takes
     * roughly 5 * 10^7 operations. It passes, but barely.
     * - Binary Search Time Complexity: O(N * log(Sum)). With N = 1000 and Sum = 10^9,
     * this takes roughly 1000 * 30 = 30,000 operations.
     * Binary Search is exponentially faster and uses O(1) space compared to the
     * DP's O(N * K) matrix space.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running Split Array Largest Sum Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] nums1 = {7, 2, 5, 10, 8};
        int k1 = 2;
        runTestCase(1, nums1, k1, 18);

        // Test Case 2: Standard case (Example 2)
        int[] nums2 = {1, 2, 3, 4, 5};
        int k2 = 2;
        runTestCase(2, nums2, k2, 9);

        // Test Case 3: Edge Case - k equals 1 (Whole array is one subarray)
        int[] nums3 = {1, 2, 3, 4, 5};
        int k3 = 1;
        runTestCase(3, nums3, k3, 15);

        // Test Case 4: Edge Case - k equals array length (Each element is a subarray)
        int[] nums4 = {10, 20, 30, 40};
        int k4 = 4;
        runTestCase(4, nums4, k4, 40);

        // Test Case 5: Large Values (Testing overflow handling)
        int[] nums5 = {1000000, 1000000, 1000000};
        int k5 = 2;
        runTestCase(5, nums5, k5, 2000000);
    }

    private static void runTestCase(int testNumber, int[] nums, int k, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = splitArrayOptimal(nums, k);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: nums = " + java.util.Arrays.toString(nums) + ", k = " + k);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}
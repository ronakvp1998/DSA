package strivers.slidingwind2pointer.count;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM: 930. Binary Subarrays With Sum (Medium)
 *
 * --- HEADER & PROBLEM CONTEXT ---
 * Given a binary array `nums` and an integer `goal`, return the number of
 * non-empty subarrays with a sum `goal`.
 * A subarray is a contiguous part of the array.
 *
 * Example 1:
 * Input: nums = [1,0,1,0,1], goal = 2
 * Output: 4
 * Explanation: The 4 subarrays are:
 * [1,0,1,0,1] -> indices 0 to 2
 * [1,0,1,0,1] -> indices 0 to 3
 * [1,0,1,0,1] -> indices 1 to 4
 * [1,0,1,0,1] -> indices 2 to 4
 *
 * Example 2:
 * Input: nums = [0,0,0,0,0], goal = 0
 * Output: 15
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * nums[i] is either 0 or 1.
 * 0 <= goal <= nums.length
 * ============================================================================
 * NOTE ON PROGRESSION:
 * Since this is an Array/Sliding Window problem (Non-DP), we will follow
 * the Non-DP Progressive Implementation Roadmap.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class BinarySubarraysWithSum {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window / Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Finding an *exact* sum with a sliding window in an array containing zeroes
     * is tricky. A zero doesn't change the sum, meaning the window could expand or
     * shrink without violating the exact sum condition, breaking the standard
     * sliding window monotonicity.
     *
     * The Master Trick: "At Most K"
     * It is much easier to count subarrays whose sum is "at most X".
     * If we have a function `atMost(goal)`, it gives us all subarrays with sum <= goal.
     * Therefore, subarrays with EXACTLY `goal` = `atMost(goal) - atMost(goal - 1)`.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). Both `right` and `left` pointers only traverse the
     * array forward. `atMost` is called twice, making it O(2N) -> O(N).
     * Space Complexity: O(1) Auxiliary Space. No extra heap space is allocated,
     * and the call stack depth is O(1).
     */
    public int numSubarraysWithSumOptimal(int[] nums, int goal) {
        return atMost(nums, goal) - atMost(nums, goal - 1);
    }

    private int atMost(int[] nums, int goal) {
        if (goal < 0) return 0; // Negative sum is impossible with binary arrays

        int count = 0;
        int currentSum = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];

            // Shrink window from the left until sum is <= goal
            while (currentSum > goal) {
                currentSum -= nums[left];
                left++;
            }

            // The number of valid subarrays ending at 'right' is the size of the window
            count += right - left + 1;
        }

        return count;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ============================================================================
     * Detailed Intuition:
     * The "Think it" stage. To find subarrays summing to `goal`, we can manually
     * generate every possible contiguous subarray, calculate its sum, and check
     * if it equals `goal`.
     *
     * We use a nested loop where 'i' determines the starting point, and 'j'
     * determines the ending point. An inner optimization allows breaking early
     * if the sum exceeds the goal (since all numbers are non-negative).
     *
     * Complexity Analysis:
     * Time Complexity: O(N^2). In the worst case (e.g., goal is very high), the
     * nested loops will evaluate N * (N + 1) / 2 subarrays.
     * Space Complexity: O(1) Auxiliary Space. No extra structures are used.
     */
    public int numSubarraysWithSumBruteForce(int[] nums, int goal) {
        int totalSubarrays = 0;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];

                if (currentSum == goal) {
                    totalSubarrays++;
                } else if (currentSum > goal) {
                    // Early exit optimization: no negative numbers to bring sum back down
                    break;
                }
            }
        }

        return totalSubarrays;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Prefix Sum + Hashing)
     * ============================================================================
     * Detailed Intuition:
     * We can solve this with a Prefix Sum strategy. As we iterate through `nums`,
     * we maintain a running `currentSum`. The mathematical trick is:
     * If `currentSum - goal` has occurred previously, the subarray between that
     * previous state and our current index equals exactly `goal`.
     *
     * We track the frequency of previous prefix sums. Since the maximum possible
     * sum is the length of the array (all 1s), we can use a fixed-size array
     * as a hash map to achieve ultra-fast O(1) lookups instead of java.util.HashMap.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). Single pass through the array.
     * Space Complexity: O(N) Auxiliary Space on the heap for the `prefixCounts` array.
     */
    public int numSubarraysWithSumPrefixSum(int[] nums, int goal) {
        int count = 0;
        int currentSum = 0;

        // Since maximum sum is nums.length (all 1s), we use an array map for speed.
        // Index represents the prefix sum, value represents its frequency.
        int[] prefixCounts = new int[nums.length + 1];

        // Base case: A prefix sum of 0 has occurred 1 time (empty prefix)
        prefixCounts[0] = 1;

        for (int num : nums) {
            currentSum += num;

            // If we have seen (currentSum - goal) before, those segments form a valid subarray
            if (currentSum >= goal) {
                count += prefixCounts[currentSum - goal];
            }

            // Record the current prefix sum frequency
            prefixCounts[currentSum]++;
        }

        return count;
    }

    /**
     * ============================================================================
     * SECTION 4: TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        BinarySubarraysWithSum solver = new BinarySubarraysWithSum();

        // Using Java 8 Stream API syntax to structure our test suite
        Object[][] testCases = {
                {new int[]{1, 0, 1, 0, 1}, 2, 4},       // Standard Case
                {new int[]{0, 0, 0, 0, 0}, 0, 15},      // Zeroes Only Edge Case
                {new int[]{1, 1, 1, 1, 1}, 5, 1},       // All Ones Exact Match
                {new int[]{1, 1, 1}, 5, 0},             // Impossible Goal
                {new int[]{0, 1, 0, 1, 0}, 1, 6}        // Mixed Case
        };

        System.out.println("🚀 Running Tests for LeetCode 930: Binary Subarrays With Sum...\n");

        IntStream.range(0, testCases.length).forEach(i -> {
            int[] nums = (int[]) testCases[i][0];
            int goal = (int) testCases[i][1];
            int expected = (int) testCases[i][2];

            int resOptimal = solver.numSubarraysWithSumOptimal(nums, goal);
            int resBrute = solver.numSubarraysWithSumBruteForce(nums, goal);
            int resPrefix = solver.numSubarraysWithSumPrefixSum(nums, goal);

            boolean passed = (resOptimal == expected) &&
                    (resBrute == expected) &&
                    (resPrefix == expected);

            System.out.printf("Test %d: nums = %s, goal = %d\n", i + 1, Arrays.toString(nums), goal);
            System.out.printf("   Expected : %d\n", expected);
            System.out.printf("   Optimal  : %d | Brute: %d | Prefix: %d\n", resOptimal, resBrute, resPrefix);
            System.out.printf("   Result   : %s\n", passed ? "✅ PASS" : "❌ FAIL");
            System.out.println("---------------------------------------------------------");
        });
    }
}

//
//public class BinarySubarray {
//
//    public static void main(String[] args) {
//        int arr[] = {1,0,0,1,1,0};
//        int goal = 2;
//        System.out.println(binarySub(arr,goal) - binarySub(arr,goal-1));
//    }
//
//    private static int binarySub(int arr[], int k){
//        if(k < 0 ){
//            return 0;
//        }
//        int l=0,r=0,sum=0,count=0;
//        while (r < arr.length){
//            sum += arr[r];
//            while (sum > k){
//                sum = sum - arr[l];
//                l = l + 1;
//            }
//            count = count + (r-l+1);
//            r = r+1;
//        }
//        return count;
//    }
//}

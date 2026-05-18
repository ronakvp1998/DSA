package strivers.slidingwind2pointer.count;
/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM: 1248. Count Number of Nice Subarrays (Medium)
 *
 * --- HEADER & PROBLEM CONTEXT ---
 * Given an array of integers `nums` and an integer `k`. A continuous subarray
 * is called nice if there are `k` odd numbers on it.
 * Return the number of nice sub-arrays.
 *
 * Example 1:
 * Input: nums = [1,1,2,1,1], k = 3
 * Output: 2
 * Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
 *
 * Example 2:
 * Input: nums = [2,4,6], k = 1
 * Output: 0
 * Explanation: There are no odd numbers in the array.
 *
 * Example 3:
 * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * Output: 16
 *
 * Constraints:
 * 1 <= nums.length <= 50000
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= nums.length
 * ============================================================================
 * NOTE ON PROGRESSION:
 * Since this is an Array/Sliding Window problem (Non-DP), we will follow
 * the Non-DP Progressive Implementation Roadmap. Note the mathematical symmetry:
 * By treating odd numbers as '1' and even numbers as '0', this problem becomes
 * mathematically identical to finding a subarray with an exact sum of 'k'.
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class CountNumberOfNiceSubarrays {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Sliding Window / Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Finding a subarray with *exactly* k odds using a single sliding window is
     * difficult because even numbers (0s in our abstraction) do not change the
     * count of odds. Thus, the window could expand or shrink without changing
     * the odd count, which breaks standard sliding window monotonicity.
     *
     * The Master Trick: "Exact K = At Most K - At Most (K - 1)"
     * We create a helper function `atMost(k)` that counts all subarrays containing
     * less than or equal to `k` odd numbers. Subtracting `atMost(k - 1)` from
     * `atMost(k)` leaves us with the count of subarrays with exactly `k` odds.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). The array is processed by the `atMost` function twice.
     * Both the `left` and `right` pointers traverse the array at most once per call.
     * Therefore, Time is O(2N) + O(2N) -> O(N).
     * Space Complexity: O(1) Auxiliary Space. We only use primitive integer
     * variables for pointers and counters; no extra heap memory is allocated.
     */
    public int numberOfSubarraysOptimal(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private int atMost(int[] nums, int k) {
        if (k < 0) return 0; // A negative count of odd numbers is impossible

        int count = 0;
        int oddCount = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            // Treat odd numbers as 1, evens as 0 using modulo arithmetic
            oddCount += nums[right] % 2;

            // Shrink window from the left until odd count is <= k
            while (oddCount > k) {
                oddCount -= nums[left] % 2;
                left++;
            }

            // Number of valid subarrays ending at 'right' is the window size
            count += right - left + 1;
        }

        return count;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ============================================================================
     * Detailed Intuition:
     * The "Think it" stage. The most straightforward way to solve this is to
     * systematically generate every possible contiguous subarray, count the
     * number of odd integers within it, and tally how many equal exactly `k`.
     *
     * We use nested loops: the outer loop `i` marks the start of the subarray,
     * and the inner loop `j` extends it. We can optimize slightly by breaking
     * early if the count of odds exceeds `k`.
     *
     * Complexity Analysis:
     * Time Complexity: O(N^2). In the worst-case scenario, the nested loops will
     * iterate N * (N + 1) / 2 times, generating all subarrays.
     * Space Complexity: O(1) Auxiliary Space. Evaluation happens in place.
     */
    public int numberOfSubarraysBruteForce(int[] nums, int k) {
        int totalSubarrays = 0;

        for (int i = 0; i < nums.length; i++) {
            int oddCount = 0;
            for (int j = i; j < nums.length; j++) {
                // Check if current element is odd
                if (nums[j] % 2 != 0) {
                    oddCount++;
                }

                if (oddCount == k) {
                    totalSubarrays++;
                } else if (oddCount > k) {
                    // Early exit optimization
                    break;
                }
            }
        }

        return totalSubarrays;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Prefix Count + Hashing)
     * ============================================================================
     * Detailed Intuition:
     * We can view this as a Prefix Sum problem. As we iterate, we maintain a
     * running count of odd numbers seen so far (`currentOdds`).
     * If `currentOdds - k` has occurred at some previous index, the subarray
     * between that previous index and our current index contains exactly `k` odds.
     *
     * Because the maximum possible count of odd numbers is the length of `nums`,
     * we can use a fixed-size integer array (`prefixCounts`) instead of a HashMap
     * to track the frequencies. This guarantees ultra-fast O(1) lookups.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). We iterate through the `nums` array exactly once.
     * Space Complexity: O(N) Auxiliary Space on the heap for the `prefixCounts`
     * array to store frequencies of prefix odd counts.
     */
    public int numberOfSubarraysPrefixCount(int[] nums, int k) {
        int count = 0;
        int currentOdds = 0;

        // Array map: Index is prefix odd count, value is frequency of that count.
        int[] prefixCounts = new int[nums.length + 1];

        // Base case: A prefix with 0 odd numbers has occurred exactly 1 time (empty prefix)
        prefixCounts[0] = 1;

        for (int num : nums) {
            // Increment if the number is odd
            currentOdds += num % 2;

            // If we have seen (currentOdds - k) before, those segments are valid nice subarrays
            if (currentOdds >= k) {
                count += prefixCounts[currentOdds - k];
            }

            // Record the current state
            prefixCounts[currentOdds]++;
        }

        return count;
    }

    /**
     * ============================================================================
     * SECTION 4: TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        CountNumberOfNiceSubarrays solver = new CountNumberOfNiceSubarrays();

        // Using Java 8 Stream API syntax to structure our test suite
        Object[][] testCases = {
                {new int[]{1, 1, 2, 1, 1}, 3, 2},                   // Standard Case 1
                {new int[]{2, 4, 6}, 1, 0},                         // Evens Only Edge Case
                {new int[]{2, 2, 2, 1, 2, 2, 1, 2, 2, 2}, 2, 16},   // Standard Case 2
                {new int[]{1, 3, 5, 7}, 2, 3},                      // Odds Only Case
                {new int[]{2, 1, 2, 1, 2}, 3, 0}                    // Impossible Goal
        };

        System.out.println("🚀 Running Tests for LeetCode 1248: Count Number of Nice Subarrays...\n");

        IntStream.range(0, testCases.length).forEach(i -> {
            int[] nums = (int[]) testCases[i][0];
            int k = (int) testCases[i][1];
            int expected = (int) testCases[i][2];

            int resOptimal = solver.numberOfSubarraysOptimal(nums, k);
            int resBrute = solver.numberOfSubarraysBruteForce(nums, k);
            int resPrefix = solver.numberOfSubarraysPrefixCount(nums, k);

            boolean passed = (resOptimal == expected) &&
                    (resBrute == expected) &&
                    (resPrefix == expected);

            System.out.printf("Test %d: nums = %s, k = %d\n", i + 1, Arrays.toString(nums), k);
            System.out.printf("   Expected : %d\n", expected);
            System.out.printf("   Optimal  : %d | Brute: %d | Prefix: %d\n", resOptimal, resBrute, resPrefix);
            System.out.printf("   Result   : %s\n", passed ? "✅ PASS" : "❌ FAIL");
            System.out.println("---------------------------------------------------------");
        });
    }
}

//
//public class NiceSubarray {
//
//    public static void main(String[] args) {
//        int arr[] = {1,1,2,1,1};
//        int k = 3;
//        System.out.println(niceSubarray(arr,k) - niceSubarray(arr,k-1) );
//    }
//
//    private static int niceSubarray(int arr[], int k){
//        if(k < 0) return 0;
//        int l=0,r=0,sum=0,count=0;
//        while (r < arr.length){
//            sum += (arr[r]%2);
//            while (sum > k){
//                sum = sum - (arr[l]%2);
//                l = l+1;
//            }
//            count = count + (r-l+1);
//            r = r+1;
//        }
//        return count;
//    }
//}

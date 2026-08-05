package strivers.recursionbacktracking.medium.combinationsum;

/**
 * ==============================================================================================
 * 🤖 216. COMBINATION SUM III
 * ==============================================================================================
 *
 * PROBLEM STATEMENT:
 * ------------------
 * Find all valid combinations of k numbers that sum up to n such that the following
 * conditions are true:
 * - Only numbers 1 through 9 are used.
 * - Each number is used at most once.
 *
 * Return a list of all possible valid combinations. The list must not contain the same
 * combination twice, and the combinations may be returned in any order.
 *
 * CONSTRAINTS:
 * ------------
 * - 2 <= k <= 9
 * - 1 <= n <= 60
 *
 * EXAMPLES:
 * ---------
 * Example 1:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 * Explanation:
 * 1 + 2 + 4 = 7
 * There are no other valid combinations.
 *
 * Example 2:
 * Input: k = 3, n = 9
 * Output: [[1,2,6],[1,3,5],[2,3,4]]
 * Explanation:
 * 1 + 2 + 6 = 9
 * 1 + 3 + 5 = 9
 * 2 + 3 + 4 = 9
 * There are no other valid combinations.
 *
 * Example 3:
 * Input: k = 4, n = 1
 * Output: []
 * Explanation: There are no valid combinations.
 * Using 4 different numbers in the range [1,9], the smallest sum we can get is
 * 1+2+3+4 = 10 and since 10 > 1, there are no valid combinations.
 *
 * CONCEPTUAL VISUALIZATION (Backtracking Tree for k=3, n=7):
 * ----------------------------------------------------------
 * The algorithm explores combinations, pruning when size > k or sum > n.
 *
 *                               [] (sum=0, size=0)
 *                     /                 |                 \
 *               [1] (s=1,sz=1)      [2] (s=2,sz=1)       ... [7..9 pruned early]
 *               /          \               \
 *         [1,2](s=3,sz=2)  [1,3](s=4,sz=2)  [2,3](s=5,sz=2)
 *           /      \             |                |
 * [1,2,3](s=6) [1,2,4](s=7)✅ [1,3,4](s=8)❌    [2,3,4](s=9)❌
 *               (sz=3)         (pruned)         (pruned)
 *
 * ==============================================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CombinationSumIII {

    /**
     * ==========================================================================================
     * PHASE 1: OPTIMAL APPROACH (Backtracking with Pruning)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * We need to build combinations of size exactly `k` using unique numbers from 1 to 9.
     * Backtracking is the natural fit for combinatorics. To make it optimal, we "prune"
     * the search space early:
     * 1. If the current sum exceeds `n`, stop exploring this path.
     * 2. If the current combination size exceeds `k`, stop exploring.
     * 3. Start the next recursive call from `current_number + 1` to ensure we don't
     *    reuse numbers and inherently avoid duplicate combinations (e.g., generating
     *    both [1,2] and [2,1]).
     *
     * Complexity Analysis:
     * - Time Complexity: O(C(9, k)). Since the maximum pool is 9 numbers, the upper bound
     *   is 2^9 = 512 operations. We only explore combinations of size `k`, making it even smaller.
     *   Functionally, this evaluates to O(1) strict time complexity since k and n are tightly bounded.
     * - Space Complexity: O(k) (Auxiliary Stack space). The recursion tree never goes deeper
     *   than `k`. Heap space is used to store the valid combinations in the result list.
     */
    public static List<List<Integer>> combinationSum3Optimal(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackOptimal(result, new ArrayList<>(), k, n, 1);
        return result;
    }

    private static void backtrackOptimal(List<List<Integer>> result, List<Integer> current,
                                         int k, int target, int start) {
        // Base Condition: Valid Combination found
        if (target == 0 && current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Pruning: Stop if we exceeded target, or we have too many numbers
        if (target < 0 || current.size() > k) {
            return;
        }

        // Explore choices from 'start' to 9
        for (int num = start; num <= 9; num++) {
            current.add(num); // Choose
            backtrackOptimal(result, current, k, target - num, num + 1); // Explore
            current.remove(current.size() - 1); // Un-choose (Backtrack)
        }
    }

    /**
     * ==========================================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Naive Subset Generation) - The "Think it" stage
     * ==========================================================================================
     *
     * Detailed Intuition:
     * Before adding pruning, the most basic way to solve this is to generate ALL possible
     * subsets of the array [1, 2, 3, 4, 5, 6, 7, 8, 9]. Once we reach the end of the array,
     * we check if the generated subset has exactly length `k` and sums to `n`.
     *
     * We use Java 8 Streams here at the base case to calculate the sum, emphasizing clean
     * syntax at the cost of slight constant overhead.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^9 * k). We generate all 512 subsets. For the valid subsets of size k,
     *   we calculate the sum. While O(1) in competitive programming terms, it's
     *   unoptimized compared to Phase 1.
     * - Space Complexity: O(9) for recursion depth, plus space for the result array.
     */
    public static List<List<Integer>> combinationSum3BruteForce(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        generateAllSubsets(1, new ArrayList<>(), k, n, result);
        return result;
    }

    private static void generateAllSubsets(int currentNum, List<Integer> currentSubset,
                                           int k, int target, List<List<Integer>> result) {
        // When we have evaluated all numbers from 1 to 9
        if (currentNum > 9) {
            if (currentSubset.size() == k) {
                // Java 8 Stream to calculate the sum of the subset
                int sum = currentSubset.stream().mapToInt(Integer::intValue).sum();
                if (sum == target) {
                    result.add(new ArrayList<>(currentSubset));
                }
            }
            return;
        }

        // Option 1: Include currentNum in the subset
        currentSubset.add(currentNum);
        generateAllSubsets(currentNum + 1, currentSubset, k, target, result);

        // Option 2: Exclude currentNum from the subset (Backtrack)
        currentSubset.remove(currentSubset.size() - 1);
        generateAllSubsets(currentNum + 1, currentSubset, k, target, result);
    }

    /**
     * ==========================================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Bitmasking)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * Since the pool of available numbers is extremely small and fixed (1 to 9), we can
     * represent any subset of these numbers using a 9-bit integer (Bitmask).
     * - The number 1 is represented by the 0th bit.
     * - The number 9 is represented by the 8th bit.
     *
     * There are 2^9 = 512 possible subsets, represented by integers from 1 to 511.
     * We iterate through these numbers, check if the bit count (number of 1s) is exactly `k`,
     * and if so, check if the corresponding numbers sum to `n`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(2^9 * 9). We loop 512 times, and for each valid mask, we do a 9-step
     *   inner loop to decode the bits. O(1) strict time.
     * - Space Complexity: O(1) auxiliary space. No recursion stack is used!
     */
    public static List<List<Integer>> combinationSum3Bitmask(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();

        // Iterate over all possible states (2^9 = 512)
        int maxMask = 1 << 9;

        // Using Java 8 IntStream to iterate over the bitmasks cleanly
        IntStream.range(1, maxMask)
                // Filter masks that have exactly 'k' set bits
                .filter(mask -> Integer.bitCount(mask) == k)
                .forEach(mask -> {
                    List<Integer> subset = new ArrayList<>();
                    int sum = 0;

                    // Decode the mask
                    for (int i = 0; i < 9; i++) {
                        if ((mask & (1 << i)) != 0) {
                            int num = i + 1;
                            subset.add(num);
                            sum += num;
                        }
                    }

                    if (sum == n) {
                        result.add(subset);
                    }
                });

        return result;
    }

    /**
     * ==========================================================================================
     * 4. TESTING SUITE
     * ==========================================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Starting Test Suite for Combination Sum III...\n");

        int[][] testCases = {
                {3, 7},   // Standard case
                {3, 9},   // Standard case with multiple results
                {4, 1},   // Impossible case (min sum for 4 nums is 10)
                {9, 45},  // Max k, Max n (1+2+...+9 = 45)
                {2, 18},  // Impossible case (max 2 nums sum is 8+9=17)
                {2, 17}   // Edge valid case
        };

        for (int i = 0; i < testCases.length; i++) {
            int k = testCases[i][0];
            int n = testCases[i][1];

            System.out.printf("Test Case %d: k = %d, n = %d%n", i + 1, k, n);

            // Phase 1: Optimal
            List<List<Integer>> optimalRes = combinationSum3Optimal(k, n);
            System.out.printf("   Optimal Approach       -> %s%n", optimalRes);

            // Phase 2: Brute Force
            List<List<Integer>> bruteRes = combinationSum3BruteForce(k, n);
            System.out.printf("   Brute Force Generation -> %s%n", bruteRes);

            // Phase 3: Bitmask
            List<List<Integer>> bitmaskRes = combinationSum3Bitmask(k, n);
            System.out.printf("   Bitmask Approach       -> %s%n", bitmaskRes);

            // Validate consistency
            boolean isConsistent = optimalRes.equals(bruteRes) && optimalRes.equals(bitmaskRes);
            System.out.printf("   Consistency Check      -> [%s]%n", isConsistent ? "PASS" : "FAIL");

            System.out.println("---------------------------------------------------------");
        }

        System.out.println("\n✅ All test cases executed.");
    }
}
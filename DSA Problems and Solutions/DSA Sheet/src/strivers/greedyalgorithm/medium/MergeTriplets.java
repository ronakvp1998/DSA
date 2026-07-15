package strivers.greedyalgorithm.medium;

/**
 * ============================================================================
 * 1899. Merge Triplets to Form Target Triplet
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * A triplet is an array of three integers. You are given a 2D integer array
 * triplets, where triplets[i] = [ai, bi, ci] describes the ith triplet. You are
 * also given an integer array target = [x, y, z] that describes the triplet you
 * want to obtain.
 *
 * To obtain target, you may apply the following operation on triplets any number
 * of times (possibly zero):
 * Choose two indices (0-indexed) i and j (i != j) and update triplets[j] to
 * become [max(ai, aj), max(bi, bj), max(ci, cj)].
 * For example, if triplets[i] = [2, 5, 3] and triplets[j] = [1, 7, 5],
 * triplets[j] will be updated to [max(2, 1), max(5, 7), max(3, 5)] = [2, 7, 5].
 *
 * Return true if it is possible to obtain the target triplet [x, y, z] as an
 * element of triplets, or false otherwise.
 *
 * Example 1:
 * Input: triplets = [[2,5,3],[1,8,4],[1,7,5]], target = [2,7,5]
 * Output: true
 * Explanation: Perform the following operations:
 * - Choose the first and last triplets [[2,5,3],[1,8,4],[1,7,5]]. Update the last triplet to be [max(2,1), max(5,7), max(3,5)] = [2,7,5]. triplets = [[2,5,3],[1,8,4],[2,7,5]]
 * The target triplet [2,7,5] is now an element of triplets.
 *
 * Example 2:
 * Input: triplets = [[3,4,5],[4,5,6]], target = [3,2,5]
 * Output: false
 * Explanation: It is impossible to have [3,2,5] as an element because there is no 2 in any of the triplets.
 *
 * Example 3:
 * Input: triplets = [[2,5,3],[2,3,4],[1,2,5],[5,2,3]], target = [5,5,5]
 * Output: true
 * Explanation: Perform the following operations:
 * - Choose the first and third triplets [[2,5,3],[2,3,4],[1,2,5],[5,2,3]]. Update the third triplet to be [max(2,1), max(5,2), max(3,5)] = [2,5,5]. triplets = [[2,5,3],[2,3,4],[2,5,5],[5,2,3]].
 * - Choose the third and fourth triplets [[2,5,3],[2,3,4],[2,5,5],[5,2,3]]. Update the fourth triplet to be [max(2,5), max(5,2), max(5,3)] = [5,5,5]. triplets = [[2,5,3],[2,3,4],[2,5,5],[5,5,5]].
 * The target triplet [5,5,5] is now an element of triplets.
 *
 * Constraints:
 * 1 <= triplets.length <= 10^5
 * triplets[i].length == target.length == 3
 * 1 <= ai, bi, ci, x, y, z <= 1000
 *
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class MergeTriplets {

    /**
     * ============================================================================
     * PHASE 1: Optimal Approach (Greedy) - The "Best" stage.
     * ============================================================================
     * Detailed Intuition:
     * The operation allows us to merge triplets by taking the maximum value at
     * each index. Because the values NEVER decrease when we merge, we can apply
     * a simple elimination strategy:
     * 1. If ANY value in a triplet is STRICTLY GREATER than the corresponding
     *    value in the `target`, that triplet is "poisonous". If we merge it,
     *    our resulting triplet will overshoot the target, and we can never
     *    reduce it back down.
     * 2. Therefore, we must completely ignore all "poisonous" triplets.
     * 3. For all remaining (valid) triplets, we effectively want to merge them ALL
     *    together. Since none of them exceed the target, merging all of them will
     *    give us the maximum possible values we can safely achieve.
     * 4. While iterating through valid triplets, we just track if we have seen
     *    the exact target value for `x`, `y`, and `z` across ANY of these valid
     *    triplets. If we find all three, we can form the target!
     *
     * Complexity Analysis:
     * Time: O(N) - We iterate through the triplets array exactly once.
     * Space: O(1) - Only using three boolean variables for heap space. O(1) auxiliary stack space.
     */
    public boolean phase1OptimalGreedy(int[][] triplets, int[] target) {
        boolean foundX = false;
        boolean foundY = false;
        boolean foundZ = false;

        for (int[] t : triplets) {
            // Check if the current triplet is valid (none of its elements exceed the target)
            if (t[0] <= target[0] && t[1] <= target[1] && t[2] <= target[2]) {
                // If valid, check if it contains the exact target pieces we need
                if (t[0] == target[0]) foundX = true;
                if (t[1] == target[1]) foundY = true;
                if (t[2] == target[2]) foundZ = true;
            }
        }

        // We can only form the target if all three pieces were found among the valid triplets
        return foundX && foundY && foundZ;
    }

    /**
     * ============================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage.
     * ============================================================================
     * Detailed Intuition:
     * The most naive approach would be to explore all possible combinations (subsets)
     * of merges to see if any subset yields the exact target. For every triplet,
     * we decide whether to include it in our "merged pool" or exclude it.
     *
     * Complexity Analysis:
     * Time: O(2^N) - We are essentially exploring the power set of all triplets.
     *       This will yield a Time Limit Exceeded (TLE) for N = 10^5.
     * Space: O(N) - Auxiliary stack space for recursion depth.
     */
    public boolean phase2BruteForce(int[][] triplets, int[] target) {
        // Start recursion with an empty triplet [0, 0, 0]
        return solveRecursive(triplets, target, 0, new int[]{0, 0, 0});
    }

    private boolean solveRecursive(int[][] triplets, int[] target, int index, int[] current) {
        // If we hit the exact target, return true
        if (current[0] == target[0] && current[1] == target[1] && current[2] == target[2]) {
            return true;
        }
        // Base case: exhausted all triplets
        if (index == triplets.length) {
            return false;
        }

        // Option 1: Exclude the current triplet
        if (solveRecursive(triplets, target, index + 1, current)) {
            return true;
        }

        // Option 2: Include the current triplet (merge it)
        int[] merged = new int[]{
                Math.max(current[0], triplets[index][0]),
                Math.max(current[1], triplets[index][1]),
                Math.max(current[2], triplets[index][2])
        };

        return solveRecursive(triplets, target, index + 1, merged);
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Declarative Stream API
     * ============================================================================
     * Detailed Intuition:
     * This is the exact same logic as Phase 1 (Greedy Elimination), but written
     * using Java 8 Stream API. It showcases declarative programming—filtering
     * out the poisonous triplets and reducing the remaining valid triplets by
     * taking the max at each position.
     *
     * Complexity Analysis:
     * Time: O(N) - Single pass through the data using streams.
     * Space: O(1) - No extra data structures are built (ignoring minor stream overhead).
     */
    public boolean phase3StreamGreedy(int[][] triplets, int[] target) {
        int[] result = Arrays.stream(triplets)
                // Filter out any triplet that exceeds the target in any dimension
                .filter(t -> t[0] <= target[0] && t[1] <= target[1] && t[2] <= target[2])
                // Merge all remaining triplets together
                .reduce(new int[]{0, 0, 0}, (a, b) -> new int[]{
                        Math.max(a[0], b[0]),
                        Math.max(a[1], b[1]),
                        Math.max(a[2], b[2])
                });

        return result[0] == target[0] && result[1] == target[1] && result[2] == target[2];
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     * Executing comprehensive tests across approaches using Java 8 Streams.
     * Note: Brute Force is tested only on small inputs to prevent TLE.
     */
    public static void main(String[] args) {
        MergeTriplets solver = new MergeTriplets();

        // Define Test Cases
        int[][][] tripletsCases = {
                {{2,5,3}, {1,8,4}, {1,7,5}},                      // Example 1 (True)
                {{3,4,5}, {4,5,6}},                               // Example 2 (False)
                {{2,5,3}, {2,3,4}, {1,2,5}, {5,2,3}},             // Example 3 (True)
                {{1,1,1}, {2,2,2}, {3,3,3}},                      // Direct match available (True)
                {{1,3,1}},                                        // Single insufficient array (False)
        };

        int[][] targetCases = {
                {2,7,5},
                {3,2,5},
                {5,5,5},
                {3,3,3},
                {1,5,1}
        };

        String[] approachNames = {
                "Phase 1: Optimal (Greedy)   ",
                "Phase 2: Brute Force (Recur)",
                "Phase 3: Stream API         "
        };

        System.out.println("=================================================");
        System.out.println("     TESTING MERGE TRIPLETS SOLUTIONS            ");
        System.out.println("=================================================\n");

        IntStream.range(0, tripletsCases.length).forEach(i -> {
            int[][] triplets = tripletsCases[i];
            int[] target = targetCases[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Target : " + Arrays.toString(target));

            boolean[] results = {
                    solver.phase1OptimalGreedy(triplets, target),
                    solver.phase2BruteForce(triplets, target),
                    solver.phase3StreamGreedy(triplets, target)
            };

            for (int j = 0; j < results.length; j++) {
                System.out.println(approachNames[j] + " => " + results[j]);
            }
            System.out.println("-------------------------------------------------");
        });

        System.out.println("All implementations executed successfully.");
    }
}
package strivers.arrays.medium;

/**
 * ============================================================================
 * 🎯 MASTERCLASS: GENERATING SUBARRAYS, SUBSEQUENCES, AND SUBSETS
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement:
 * Given an integer array `nums` (which may contain duplicates), generate and
 * print/return:
 *   1. All contiguous Subarrays.
 *   2. All Subsequences (maintaining relative order, 2^N possibilities).
 *   3. All Unique Subsets (mathematical combinations, handling duplicates).
 *
 * Constraints:
 * - 1 <= nums.length <= 15 (Small enough for O(2^N) and O(N^3) operations).
 * - -10 <= nums[i] <= 10
 *
 * Example 1 (Distinct elements):
 * Input: nums = [1, 2, 3]
 * Output:
 * - Subarrays: [1], [1,2], [1,2,3], [2], [2,3], [3]
 * - Subsequences: [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
 * - Subsets: [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
 *
 * Example 2 (With Duplicates):
 * Input: nums = [1, 2, 2]
 * Output:
 * - Subarrays: [1], [1,2], [1,2,2], [2], [2,2], [2]
 * - Subsequences: [], [1], [2], [2], [1,2], [1,2], [2,2], [1,2,2]
 * - Unique Subsets: [], [1], [2], [1,2], [2,2], [1,2,2]
 *
 * Conceptual Visualization (Pick / Don't Pick Recursion for Subsequences):
 *                       []
 *               /                 \
 *           Include 1          Exclude 1
 *             [1]                  []
 *           /     \             /      \
 *       Inc 2    Exc 2      Inc 2     Exc 2
 *       [1,2]     [1]        [2]       []
 *
 * ============================================================================
 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrintSubarraySubsequenceSubsets {

    /**
     * ========================================================================
     * TASK 1: SUBARRAYS (Contiguous Elements)
     * ========================================================================
     * Phase 1: Optimal Approach (Nested Loops)
     *
     * Detailed Intuition:
     * A subarray is strictly contiguous. We can define every possible subarray
     * by simply choosing a starting index `i` and an ending index `j`.
     * We then extract all elements from `i` to `j`.
     *
     * Complexity Analysis:
     * - Time: O(N^3) standard loop extraction, or O(N^2) if just tracking bounds.
     *   Here, copying the sub-segment takes O(N), resulting in O(N^3) overall.
     * - Space: O(1) auxiliary space (O(N^3) to store the result list of lists).
     */
    public static List<List<Integer>> generateSubarrays(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Java 8 Stream API to extract and collect the contiguous segment
                List<Integer> sub = Arrays.stream(nums, i, j + 1)
                        .boxed()
                        .collect(Collectors.toList());
                result.add(sub);
            }
        }
        return result;
    }

    /**
     * ========================================================================
     * TASK 2: SUBSEQUENCES (Non-Contiguous, Order Maintained)
     * ========================================================================
     * Phase 1: Optimal Approach (Backtracking / Recursion)
     *
     * Detailed Intuition:
     * For every element in the array, we have exactly two choices:
     * 1. Pick it (add to our current sequence path).
     * 2. Don't pick it (skip it and move to the next index).
     * This creates a binary decision tree of height N, yielding 2^N leaves.
     *
     * Complexity Analysis:
     * - Time: O(N * 2^N) - 2^N subsequences, and copying each to the result takes O(N).
     * - Space: O(N) auxiliary stack space for recursion depth + O(N) for the path list.
     */
    public static List<List<Integer>> generateSubsequencesRecursive(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        findSubsequences(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void findSubsequences(int[] nums, int index, List<Integer> path, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // Choice 1: Include current element
        path.add(nums[index]);
        findSubsequences(nums, index + 1, path, result);

        // Choice 2: Exclude current element (Backtrack)
        path.remove(path.size() - 1);
        findSubsequences(nums, index + 1, path, result);
    }

    /**
     * Phase 3: Alternative Approach (Bitmasking for Subsequences)
     *
     * Detailed Intuition:
     * Since there are 2^N possible subsequences, we can map them to the binary
     * representation of numbers from 0 to (2^N - 1).
     * If the i-th bit of the number is 1, we include nums[i] in the subsequence.
     *
     * Complexity Analysis:
     * - Time: O(N * 2^N)
     * - Space: O(1) auxiliary space (ignoring the space to store the result).
     */
    public static List<List<Integer>> generateSubsequencesBitmask(int[] nums) {
        int n = nums.length;
        int totalSubsequences = 1 << n; // 2^N

        // Using Java 8 Streams to map numbers to subsequences
        return IntStream.range(0, totalSubsequences)
                .mapToObj(mask -> {
                    List<Integer> sub = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        // Check if the i-th bit is set
                        if ((mask & (1 << i)) != 0) {
                            sub.add(nums[i]);
                        }
                    }
                    return sub;
                })
                .collect(Collectors.toList());
    }

    /**
     * ========================================================================
     * TASK 3: UNIQUE SUBSETS (Combinations, Handling Duplicates)
     * ========================================================================
     * Phase 1: Optimal Approach (Sorting + Backtracking)
     *
     * Detailed Intuition:
     * Subsets are similar to subsequences but represent mathematical sets.
     * If the input contains duplicates, normal backtracking generates duplicate subsets.
     * To prevent this:
     * 1. Sort the array so duplicates are adjacent.
     * 2. In the recursion tree, loop through available elements.
     * 3. If the current element is identical to the previous one at the SAME tree level,
     *    skip it (`if (i > start && nums[i] == nums[i-1]) continue;`).
     *
     * Complexity Analysis:
     * - Time: O(N * 2^N) - Generating subsets and cloning lists. Sorting takes O(N log N).
     * - Space: O(N) auxiliary stack space for recursion depth.
     */
    public static List<List<Integer>> generateUniqueSubsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // Step 1: Sort to bring duplicates together
        Arrays.sort(nums);
        findUniqueSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void findUniqueSubsets(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        // Every node in the recursion tree is a valid subset
        result.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            // Step 3: Skip duplicates at the same recursive level
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            // Pick the element
            path.add(nums[i]);
            // Recurse for the next elements
            findUniqueSubsets(nums, i + 1, path, result);
            // Backtrack
            path.remove(path.size() - 1);
        }
    }


    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Thoroughly testing standard and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("  Array Generators Masterclass Tests     ");
        System.out.println("=========================================\n");

        int[] distinctNums = {1, 2, 3};
        int[] duplicateNums = {1, 2, 2};
        int[] emptyNums = {};

        // ---------------------------------------------------------
        System.out.println("TEST 1: Subarrays");
        System.out.println("Input: " + Arrays.toString(distinctNums));
        System.out.println("Result: " + generateSubarrays(distinctNums));
        System.out.println();

        // ---------------------------------------------------------
        System.out.println("TEST 2: Subsequences (Recursive & Bitmask)");
        System.out.println("Input: " + Arrays.toString(distinctNums));

        List<List<Integer>> seqRec = generateSubsequencesRecursive(distinctNums);
        List<List<Integer>> seqBit = generateSubsequencesBitmask(distinctNums);

        System.out.println("Recursive Result: " + seqRec);
        System.out.println("Bitmask Result:   " + seqBit);
        System.out.println("Matches? " + seqRec.containsAll(seqBit) + "\n");

        // ---------------------------------------------------------
        System.out.println("TEST 3: Subsets (Handling Duplicates)");
        System.out.println("Input: " + Arrays.toString(duplicateNums));

        List<List<Integer>> rawSubsequences = generateSubsequencesRecursive(duplicateNums);
        List<List<Integer>> uniqueSubsets = generateUniqueSubsets(duplicateNums);

        System.out.println("Raw Subsequences (has duplicates): \n" + rawSubsequences);
        System.out.println("Unique Subsets (duplicates pruned): \n" + uniqueSubsets);
        System.out.println();

        // ---------------------------------------------------------
        System.out.println("TEST 4: Edge Case (Empty Array)");
        System.out.println("Input: " + Arrays.toString(emptyNums));
        System.out.println("Unique Subsets: " + generateUniqueSubsets(emptyNums));
        System.out.println("=========================================");
    }
}
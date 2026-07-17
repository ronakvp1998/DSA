package strivers.recursionbacktracking.medium.premutationprobelms;

/**
 * ============================================================================
 * 46. Permutations
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array nums of distinct integers, return all the possible permutations.
 * You can return the answer in any order.
 *
 * EXAMPLES:
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * Example 2:
 * Input: nums = [0,1]
 * Output: [[0,1],[1,0]]
 *
 * Example 3:
 * Input: nums = [1]
 * Output: [[1]]
 *
 * CONSTRAINTS:
 * - 1 <= nums.length <= 6
 * - -10 <= nums[i] <= 10
 * - All the integers of nums are unique.
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for For-Loop / Visited Approach)
 * ============================================================================
 * For nums = [1, 2, 3]
 * State format: [Current Permutation Path]
 *
 *                                []
 *                      /         |         \
 *                  [1]          [2]          [3]
 *                 /   \        /   \        /   \
 *             [1,2]  [1,3]  [2,1]  [2,3]  [3,1]  [3,2]
 *               |      |      |      |      |      |
 *           [1,2,3][1,3,2][2,1,3][2,3,1][3,1,2][3,2,1] ✅
 *
 * Note on "Pick/Don't Pick" vs "For-Loop" in Permutations:
 * Unlike Subsets or Combinations, generating permutations means arranging ALL
 * elements. We cannot simply say "Pick 1 or Don't Pick 1". Instead, we must ask:
 * "Which available element should go in position X?". Therefore, ALL recursive
 * approaches to permutations inherently use a loop to select from the remaining
 * candidates.
 * Below, Phase 1A uses a 'visited' array (the Explore Candidates pattern),
 * while Phase 1B uses In-Place Swapping (the structural recursion pattern).
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PermutationsSolution {

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - For-Loop Backtracking with Visited Array
     * ============================================================================
     * Detailed Intuition:
     * This uses an N-ary decision tree. At each step of building our permutation,
     * we need to pick an element that hasn't been used yet. We iterate through
     * the entire `nums` array. To efficiently check if an element is already in
     * our current path, we use a boolean `visited` array.
     * If not visited, we add it to our path, mark it as visited, recurse to fill
     * the next position, and then backtrack by removing it and unmarking it.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * N!)
     *   There are N! (N factorial) permutations. For each permutation, creating a
     *   new ArrayList takes O(N) time to copy the elements.
     * - Space Complexity: O(N) auxiliary stack space + O(N) auxiliary array space
     *   The maximum recursion depth is N. The boolean `visited` array and the
     *   `current` list also take O(N) space. The output list takes O(N * N!) heap space.
     * ============================================================================
     */
    public List<List<Integer>> permuteForLoop(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        solveForLoop(nums, new ArrayList<>(), visited, result);
        return result;
    }

    private void solveForLoop(int[] nums, List<Integer> current, boolean[] visited, List<List<Integer>> result) {
        // Base case: The permutation is complete when it has N elements
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Loop through all candidate elements
        for (int i = 0; i < nums.length; i++) {
            // Skip elements that are already in the current permutation path
            if (visited[i]) {
                continue;
            }

            // INCLUDE candidate
            visited[i] = true;
            current.add(nums[i]);

            // RECURSE deeper to fill the next slot
            solveForLoop(nums, current, visited, result);

            // BACKTRACK: undo the choice
            current.remove(current.size() - 1);
            visited[i] = false;
        }
    }

    /**
     * ============================================================================
     * PHASE 1B: Optimal Approach - In-Place Swapping (Structural Recursion)
     * ============================================================================
     * Detailed Intuition:
     * Instead of using an external `visited` array and a `current` list, we can
     * use the input array itself to keep track of the permutations.
     * We divide the array into two parts: a "fixed" part (from index 0 to `index-1`)
     * and an "unfixed" part (from `index` to the end). At each step, we swap the
     * element at `index` with every element in the unfixed part, recurse to the
     * next index, and then swap back to restore the original state.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * N!)
     *   Similar to the previous approach, generating the permutations takes N!
     *   calls, and copying the array to a list at the base case takes O(N).
     * - Space Complexity: O(N) auxiliary stack space
     *   This approach completely eliminates the O(N) space needed for the `visited`
     *   array and the `current` path list, operating entirely in-place.
     * ============================================================================
     */
    public List<List<Integer>> permuteSwap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        solveSwap(0, nums, result);
        return result;
    }

    private void solveSwap(int index, int[] nums, List<List<Integer>> result) {
        // Base case: All positions are fixed
        if (index == nums.length) {
            // Convert the current state of the array into a List
            List<Integer> currentPermutation = Arrays.stream(nums)
                    .boxed()
                    .collect(Collectors.toList());
            result.add(currentPermutation);
            return;
        }

        // Try placing every remaining element into the current 'index' position
        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i);                 // DO: place nums[i] at current index
            solveSwap(index + 1, nums, result);   // RECURSE: lock in index, move to index+1
            swap(nums, index, i);                 // UNDO: restore the array
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Iterative (Next Permutation Algorithm)
     * ============================================================================
     * Detailed Intuition:
     * This relies on lexicographical ordering. We start by sorting the array to
     * get the absolutely smallest permutation. Then, we repeatedly find the "next
     * greater permutation" until the array is sorted in descending order.
     *
     * Finding the next permutation:
     * 1. Find the largest index k such that nums[k] < nums[k + 1].
     * 2. Find the largest index l greater than k such that nums[k] < nums[l].
     * 3. Swap nums[k] and nums[l].
     * 4. Reverse the sub-array from k + 1 to the end.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * N!)
     *   Sorting takes O(N log N). Finding the next permutation takes O(N), and we
     *   do it N! times.
     * - Space Complexity: O(1) auxiliary space (excluding output list)
     *   No recursion stack. Modifies array in place.
     * ============================================================================
     */
    public List<List<Integer>> permuteIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        // Start with the lowest lexicographical sequence
        Arrays.sort(nums);

        // Add the initial sorted sequence
        result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));

        while (nextPermutation(nums)) {
            result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        }

        return result;
    }

    private boolean nextPermutation(int[] nums) {
        int i = nums.length - 2;
        // Step 1: Find the first decreasing element from the right
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // If no such element exists, we are at the last permutation
        if (i < 0) {
            return false;
        }

        // Step 2: Find the element just larger than nums[i] from the right
        int j = nums.length - 1;
        while (j >= 0 && nums[j] <= nums[i]) {
            j--;
        }

        // Step 3: Swap them
        swap(nums, i, j);

        // Step 4: Reverse the suffix to get the smallest lexicographical order
        reverse(nums, i + 1, nums.length - 1);
        return true;
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        PermutationsSolution solution = new PermutationsSolution();

        int[][] testCases = {
                {1, 2, 3}, // Standard Example 1
                {0, 1},    // Standard Example 2
                {1},       // Standard Example 3
                {4, 5, 6}  // Additional test case
        };

        System.out.println("====== PERMUTATIONS DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": " +
                    Arrays.toString(testCases[i]));

            // Test For-Loop / Visited approach
            List<List<Integer>> resForLoop = solution.permuteForLoop(testCases[i]);
            System.out.println("  [For-Loop Visited] : " + formatResult(resForLoop));

            // Test In-Place Swap approach
            // Clone array because swap modifies it in-place
            List<List<Integer>> resSwap = solution.permuteSwap(testCases[i].clone());
            System.out.println("  [In-Place Swap]    : " + formatResult(resSwap));

            // Test Iterative Next Permutation approach
            List<List<Integer>> resIterative = solution.permuteIterative(testCases[i].clone());
            System.out.println("  [Iterative Next]   : " + formatResult(resIterative));

            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format List<List<Integer>> cleanly for the console
    private static String formatResult(List<List<Integer>> res) {
        if (res.isEmpty()) return "[]";
        return "[" + res.stream()
                .map(list -> "[" + list.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining(",")) + "]";
    }
}
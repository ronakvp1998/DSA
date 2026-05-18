package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: PERMUTATIONS
 * ============================================================================
 * * 46. Permutations
 * Solved | Medium | Topics | Companies
 * * Hint:
 * Given an array nums of distinct integers, return all the possible permutations.
 * You can return the answer in any order.
 * * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * * Example 2:
 * Input: nums = [0,1]
 * Output: [[0,1],[1,0]]
 * * Example 3:
 * Input: nums = [1]
 * Output: [[1]]
 * * Constraints:
 * - 1 <= nums.length <= 6
 * - -10 <= nums[i] <= 10
 * - All the integers of nums are unique.
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Backtracking Recursion Tree)
 * ============================================================================
 * Generating permutations is a classic Backtracking problem. At each step, we
 * make a choice from the available pool of numbers, recurse to build the rest
 * of the sequence, and then "backtrack" (undo the choice) to explore other paths.
 * * RECURSION TREE FOR nums = [1, 2, 3]
 * * [ ]
 * /             |             \
 * [1]              [2]              [3]
 * /   \            /   \            /   \
 * [1,2]     [1,3]  [2,1]     [2,3]  [3,1]     [3,2]
 * |         |      |         |      |         |
 * [1,2,3]   [1,3,2][2,1,3]   [2,3,1][3,1,2]   [3,2,1]
 * * The leaves of this tree represent our base cases (path length == nums length).
 * When we hit a leaf, we add a copy of the current path to our final results.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationsMasterclass {

    /**
     * ========================================================================
     * PHASE 1: BASIC BACKTRACKING W/ VISITED ARRAY (The "Think it" stage)
     * ========================================================================
     * Approach:
     * We use a boolean array `used` to keep track of which elements have already
     * been added to our current `path`. We iterate through all elements: if an
     * element is not used, we add it to the path, mark it as used, and recurse.
     * Upon returning from recursion, we remove the element and unmark it.
     * * Detailed Intuition:
     * This mimics how a human writes out permutations. You pick an available
     * number, cross it off your list, pick the next available number, and so on.
     * When you finish a sequence, you erase the last number you crossed off and
     * try a different one.
     * * Complexity Analysis:
     * - Time Complexity: O(N! * N)
     * There are N! permutations. For each permutation, we spend O(N) time copying
     * the elements from our temporary `path` into a new list to store in the result.
     * - Space Complexity: O(N)
     * Heap Space: O(N) for the boolean `used` array and O(N) for the `path` list.
     * Auxiliary Stack Space: O(N) for the depth of the recursion tree.
     * Note: The space to store the actual N! output lists is O(N! * N), but we
     * generally exclude output structure size from auxiliary space analysis.
     */
    public List<List<Integer>> permuteBasic(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrackBasic(nums, new ArrayList<>(), used, result);
        return result;
    }

    private void backtrackBasic(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> result) {
        // Base case: if the path has all numbers, we found a permutation
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path)); // Deep copy the path
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue; // Skip if already in the current path

            // Choose
            used[i] = true;
            path.add(nums[i]);

            // Explore
            backtrackBasic(nums, path, used, result);

            // Un-choose (Backtrack)
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL IN-PLACE SWAPPING (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * Instead of maintaining a separate `path` list and a `used` boolean array,
     * we modify the original array in place. We keep an `index` tracker. For a
     * given `index`, we swap it with every element from `index` to the end of
     * the array, recurse on `index + 1`, and then swap back to restore the array.
     * * Detailed Intuition:
     * We can partition the array into a "fixed" prefix and an "unfixed" suffix.
     * By swapping elements from the suffix into the current prefix boundary, we
     * implicitly choose the next element for our permutation. This eliminates
     * the overhead of the `used` array and `path` manipulations, making it
     * highly space-efficient.
     * * Complexity Analysis:
     * - Time Complexity: O(N! * N)
     * We still generate N! permutations and spend O(N) copying the array into
     * a List at the base case. The constant factors are significantly lower than Phase 1.
     * - Space Complexity: O(N)
     * Heap Space: O(1) auxiliary space (we just modify the input array).
     * Auxiliary Stack Space: O(N) for the depth of the recursion tree.
     */
    public List<List<Integer>> permuteOptimal(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSwapping(nums, 0, result);
        return result;
    }

    private void backtrackSwapping(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> currentPermutation = new ArrayList<>(nums.length);
            for (int num : nums) {
                currentPermutation.add(num);
            }
            result.add(currentPermutation);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            // Choose by swapping the element to the current 'start' position
            swap(nums, start, i);

            // Explore the remaining positions
            backtrackSwapping(nums, start + 1, result);

            // Backtrack by swapping them back
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Iterative via Next Permutation)
     * ========================================================================
     * Approach:
     * 1. Sort the array to get the lexicographically smallest permutation.
     * 2. Add it to the results.
     * 3. Continually use the "Next Permutation" logic (find the dip, swap with
     * next greater, reverse suffix) to find the next sequence.
     * 4. Stop when the array is sorted in descending order (no next permutation).
     * * Detailed Intuition:
     * If the interviewer asks "Can you do this without recursion?" or "Can you
     * guarantee the output is generated in strict lexicographical order?", this
     * is the answer. It iteratively mutates the array from its lowest state to
     * its highest state.
     * * Complexity Analysis:
     * - Time Complexity: O(N! * N)
     * Getting the next permutation takes O(N). We do this N! times.
     * - Space Complexity: O(1)
     * Fully iterative and strictly in-place. Auxiliary Heap and Stack space is O(1).
     */
    public List<List<Integer>> permuteIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Start with the smallest permutation

        boolean hasNext = true;
        while (hasNext) {
            // Add current state
            List<Integer> currentPermutation = new ArrayList<>(nums.length);
            for (int num : nums) currentPermutation.add(num);
            result.add(currentPermutation);

            // Generate next permutation
            hasNext = nextPermutation(nums);
        }

        return result;
    }

    private boolean nextPermutation(int[] nums) {
        int i = nums.length - 2;
        // Find the first dip from the right
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // If no dip exists, we are at the last permutation
        if (i < 0) return false;

        int j = nums.length - 1;
        // Find the next greater element in the suffix
        while (j >= 0 && nums[j] <= nums[i]) {
            j--;
        }

        swap(nums, i, j);
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
     * ========================================================================
     * ALTERNATIVE APPROACH: BITMASK BACKTRACKING (The "Hacker's" stage)
     * ========================================================================
     * Approach:
     * We use a single integer `mask` to represent our `used` boolean array.
     * We iterate through the array, and for each index `i`, we check if the
     * i-th bit is set in the `mask`. If it is 0, we add the element to our
     * path, and recursively call the function while passing a new mask where
     * the i-th bit is flipped to 1 using the Bitwise OR operator.
     * * Detailed Intuition:
     * In Competitive Programming and high-level interviews, memory allocation
     * for arrays inside recursive calls is a known bottleneck. By leveraging
     * CPU-level bitwise operations (`&`, `|`, `<<`), we make the "is visited"
     * check almost instantaneously fast and drop the memory overhead of the
     * boolean array entirely.
     * * Complexity Analysis:
     * - Time Complexity: O(N! * N)
     * We still must generate all N! paths and copy them over (taking N time).
     * However, the constant overhead is much smaller due to CPU-optimized bit math.
     * - Space Complexity: O(N)
     * Auxiliary Stack Space is O(N) for recursion depth. Heap Space for the
     * temporary `path` list is O(N). Tracking space is reduced to O(1) integer.
     */
    public List<List<Integer>> permuteBitmask(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        // Pass 0 as the initial mask (in binary: 000000...) meaning nothing is used yet.
        backtrackWithMask(nums, 0, new ArrayList<>(), result);

        return result;
    }

    private void backtrackWithMask(int[] nums, int mask, List<Integer> path, List<List<Integer>> result) {
        // Base case: path size equals array size
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {

            // CHECK BIT: If the i-th bit is 0, it means nums[i] is NOT used
            if ((mask & (1 << i)) == 0) {

                path.add(nums[i]);

                // SET BIT: Pass (mask | (1 << i)) to the next level to mark it as used
                backtrackWithMask(nums, mask | (1 << i), path, result);

                // BACKTRACK: Remove from path.
                // We don't need to "unset" the bit because `mask` is passed by value,
                // so the current stack frame's `mask` is untouched!
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        PermutationsMasterclass solution = new PermutationsMasterclass();

        // Define Test Cases
        int[][] testCases = {
                {1, 2, 3},       // Example 1: Standard case
                {0, 1},          // Example 2: Two elements
                {1},             // Example 3: Single element
                {4, 5, 6, 7}     // Larger array
        };

        System.out.println("=========================================================");
        System.out.println("Executing Permutations Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(nums));

            // Clone arrays to prevent state mutation across test cases
            int[] numsForBasic = nums.clone();
            int[] numsForOptimal = nums.clone();
            int[] numsForIterative = nums.clone();

            // 1. Basic Backtracking
            long start1 = System.nanoTime();
            List<List<Integer>> res1 = solution.permuteBasic(numsForBasic);
            long end1 = System.nanoTime();

            // 2. Optimal Swapping
            long start2 = System.nanoTime();
            List<List<Integer>> res2 = solution.permuteOptimal(numsForOptimal);
            long end2 = System.nanoTime();

            // 3. Iterative Next Permutation
            long start3 = System.nanoTime();
            List<List<Integer>> res3 = solution.permuteIterative(numsForIterative);
            long end3 = System.nanoTime();

            System.out.println("  [Basic Backtracking] Generated: " + res1.size() + " perms | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Optimal Swapping]   Generated: " + res2.size() + " perms | Time: " + (end2 - start2) + " ns");
            System.out.println("  [Iterative NextPerm] Generated: " + res3.size() + " perms | Time: " + (end3 - start3) + " ns");

            // Basic verification (Checking sizes, exact equality is tricky since optimal swapping alters order)
            boolean isValid = (res1.size() == res2.size()) && (res2.size() == res3.size());
            System.out.println("  [Verification] Matching permutation counts: " + (isValid ? "PASS" : "FAIL"));
            if (nums.length <= 2) {
                System.out.println("  [Sample Output] " + res1);
            }
            System.out.println("---------------------------------------------------------");
        }
    }
}
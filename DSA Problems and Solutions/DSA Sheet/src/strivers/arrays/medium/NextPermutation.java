package strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: NEXT PERMUTATION
 * ============================================================================
 * * 31. Next Permutation
 * Solved | Medium | Topics | Companies
 * * A permutation of an array of integers is an arrangement of its members into a
 * sequence or linear order.
 * For example, for arr = [1,2,3], the following are all the permutations of arr:
 * [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1].
 * * The next permutation of an array of integers is the next lexicographically
 * greater permutation of its integer. More formally, if all the permutations of
 * the array are sorted in one container according to their lexicographical order,
 * then the next permutation of that array is the permutation that follows it in
 * the sorted container. If such arrangement is not possible, the array must be
 * rearranged as the lowest possible order (i.e., sorted in ascending order).
 * * For example, the next permutation of arr = [1,2,3] is [1,3,2].
 * Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
 * While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does
 * not have a lexicographical larger rearrangement.
 * * Given an array of integers nums, find the next permutation of nums.
 * The replacement must be in place and use only constant extra memory.
 * * Example 1:
 * Input: nums = [1,2,3]
 * Output: [1,3,2]
 * * Example 2:
 * Input: nums = [3,2,1]
 * Output: [1,2,3]
 * * Example 3:
 * Input: nums = [1,1,5]
 * Output: [1,5,1]
 * * Constraints:
 * - 1 <= nums.length <= 100
 * - 0 <= nums[i] <= 100
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Optimal Array Manipulation)
 * ============================================================================
 * Since this is an Array/Math problem, we don't use a DP Recursion Tree.
 * Instead, here is a visualization of the Optimal In-Place approach.
 * * Consider finding the next dictionary word. We want to change the sequence as
 * far to the right as possible to make it only *slightly* larger.
 * * Input: nums = [2, 1, 5, 4, 3, 0, 0]
 * * Step 1: Find the "Dip" (longest non-increasing suffix)
 * Traverse from right to left to find the first element that is smaller than
 * the element immediately after it: nums[i] < nums[i+1].
 * [2, 1, 5, 4, 3, 0, 0]
 * ^
 * (i=1, nums[i]=1. The suffix [5, 4, 3, 0, 0] is non-increasing)
 * * Step 2: Find the Next Greater Element in the Suffix
 * Traverse the suffix from right to left to find the first element strictly
 * greater than nums[i].
 * [2, 1, 5, 4, 3, 0, 0]
 * ^
 * (j=4, nums[j]=3)
 * * Step 3: Swap them
 * Swap nums[i] and nums[j]. This makes the prefix slightly larger.
 * [2, 3, 5, 4, 1, 0, 0]
 * ^        ^
 * * Step 4: Reverse the Suffix
 * The suffix [5, 4, 1, 0, 0] is currently in descending order. To make the
 * permutation the *smallest possible* slightly larger permutation, we reverse
 * the suffix to ascending order.
 * [2, 3, 0, 0, 1, 4, 5] -> Final Answer!
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextPermutation {


    /**
     * ========================================================================
     * PHASE 2: OPTIMAL ALGORITHM (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * 1. Traverse from right to left to find the first index `i` where nums[i] < nums[i+1].
     * 2. If no such `i` exists, the array is entirely descending (last permutation).
     * Simply reverse the whole array.
     * 3. If `i` exists, traverse from right to left again to find the first
     * element nums[j] that is strictly greater than nums[i].
     * 4. Swap nums[i] and nums[j].
     * 5. Reverse the sub-array from `i+1` to the end.
     * * Detailed Intuition:
     * We want the *next* lexicographical sequence. To minimize the increase, we
     * must modify the rightmost elements. The suffix that is in descending order
     * is already at its maximum possible lexicographical arrangement. The element
     * just before this suffix (the "dip") is the one we must increment. We replace
     * it with the smallest element in the suffix that is greater than it. After
     * swapping, the suffix remains in descending order. To make it the *smallest* * possible arrangement, we just reverse it.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * In the worst case, we make two passes over the array to find `i` and `j`
     * (O(N)), and one pass to reverse the suffix (O(N/2)). Overall Time is strictly O(N).
     * - Space Complexity: O(1)
     * The modification is done entirely in-place. Auxiliary Stack Space is O(1)
     * and Heap Space is O(1).
     */
    public void nextPermutationOptimal(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        int i = nums.length - 2;

        // Step 1: Find the dip
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // If a dip was found
        if (i >= 0) {
            int j = nums.length - 1;
            // Step 2: Find the next greater element in the suffix
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            // Step 3: Swap them
            swap(nums, i, j);
        }

        // Step 4: Reverse the suffix (or the whole array if i < 0)
        reverse(nums, i + 1, nums.length - 1);
    }

    // --- Helper Methods ---

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
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
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * Generate all possible permutations of the array using recursion/backtracking.
     * Store them in a list, sort the list lexicographically, find the current
     * permutation, and return the one immediately following it. If it's the last
     * one, return the first one.
     * * Detailed Intuition:
     * This is the literal translation of the problem definition ("if all the
     * permutations of the array are sorted in one container..."). While logically
     * sound, it is computationally catastrophic for arrays larger than ~10 elements.
     * * Complexity Analysis:
     * - Time Complexity: O(N! * N)
     * Generating all permutations takes O(N!) time. Sorting them takes O(N! * log(N!))
     * which is roughly O(N! * N log N). For N=100 (LeetCode constraint), 100! is
     * a number with 158 digits. The universe will end before this finishes.
     * - Space Complexity: O(N!)
     * Heap Space: We store N! permutations of size N.
     * Stack Space: O(N) for the depth of the recursion tree.
     */
    public void nextPermutationBruteForce(int[] nums) {
        // NOTE: This approach is purely conceptual to demonstrate foundational
        // understanding. Executing this on N > 10 will result in TLE/OOM.
        List<List<Integer>> allPermutations = new ArrayList<>();
        generatePermutations(nums, 0, allPermutations);

        // Sort permutations lexicographically
        allPermutations.sort((a, b) -> {
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).equals(b.get(i))) {
                    return a.get(i) - b.get(i);
                }
            }
            return 0;
        });

        // Convert current array to list for comparison
        List<Integer> current = new ArrayList<>();
        for (int num : nums) current.add(num);

        // Find current permutation and pick the next
        for (int i = 0; i < allPermutations.size(); i++) {
            if (allPermutations.get(i).equals(current)) {
                List<Integer> next = allPermutations.get((i + 1) % allPermutations.size());
                for (int j = 0; j < nums.length; j++) {
                    nums[j] = next.get(j);
                }
                return;
            }
        }
    }

    private void generatePermutations(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> current = new ArrayList<>();
            for (int num : nums) current.add(num);
            result.add(current);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            generatePermutations(nums, start + 1, result);
            swap(nums, start, i); // backtrack
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        NextPermutation solution = new NextPermutation();

        // Define Test Cases
        int[][] testCases = {
                {1, 2, 3},                   // Example 1: Standard ascending
                {3, 2, 1},                   // Example 2: Fully descending (Wrap around)
                {1, 1, 5},                   // Example 3: Contains duplicates
                {2, 1, 5, 4, 3, 0, 0},       // Complex case used in visualization
                {1},                         // Edge case: Single element
                {5, 1, 1}                    // Descending with duplicates
        };

        System.out.println("=========================================================");
        System.out.println("Executing Next Permutation Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": Input = " + Arrays.toString(testCases[i]));

            // Clone arrays to test independently
            int[] testArr1 = testCases[i].clone();
            int[] testArr2 = testCases[i].clone();

            // Note: Brute force is only executed on small arrays to avoid TLE
            boolean runBruteForce = testArr1.length <= 4;

            long start1 = 0, end1 = 0;
            if (runBruteForce) {
                start1 = System.nanoTime();
                solution.nextPermutationBruteForce(testArr1);
                end1 = System.nanoTime();
            }

            // Test Optimal Algorithm
            long start2 = System.nanoTime();
            solution.nextPermutationOptimal(testArr2);
            long end2 = System.nanoTime();

            if (runBruteForce) {
                System.out.println("  [Brute Force] Output: " + Arrays.toString(testArr1) + " | Time: " + (end1 - start1) + " ns");
            } else {
                System.out.println("  [Brute Force] Output: SKIPPED (N too large for O(N!))");
            }

            System.out.println("  [Optimal]     Output: " + Arrays.toString(testArr2) + " | Time: " + (end2 - start2) + " ns");

            if (runBruteForce) {
                boolean isValid = Arrays.equals(testArr1, testArr2);
                System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            }
            System.out.println("---------------------------------------------------------");
        }
    }
}
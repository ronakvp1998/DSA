package strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 153. Find Minimum in Rotated Sorted Array
 * Level: Medium
 * * Problem Statement:
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
 * For example, the array nums = [0,1,2,4,5,6,7] might become:
 * - [4,5,6,7,0,1,2] if it was rotated 4 times.
 * - [0,1,2,4,5,6,7] if it was rotated 7 times.
 * * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the
 * array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * You must write an algorithm that runs in O(log n) time.
 * * Example 1:
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 * * Example 2:
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
 * * Example 3:
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
 * * Constraints:
 * - n == nums.length
 * - 1 <= n <= 5000
 * - -5000 <= nums[i] <= 5000
 * - All the integers of nums are unique.
 * - nums is sorted and rotated between 1 and n times.
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 * As this is an Array/Binary Search problem (Not DP), we follow the non-DP roadmap:
 * Phase 1: Best and recommended approach (Optimized Binary Search)
 * Phase 2: Brute Force approach (Linear Search)
 * Phase 3: Alternative Approaches (Recursive Binary Search)
 */

public class FindMinimumInRotatedArray {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Binary Search)
     * ========================================================================
     * Approach:
     * We use a modified Binary Search. In a rotated sorted array, the minimum element
     * is the only element that is smaller than its previous element. It acts as the
     * "inflection point". We can find this by comparing the middle element with the
     * rightmost element of our current search space.
     * * Detailed Intuition:
     * 1. Calculate 'mid'.
     * 2. Compare nums[mid] with nums[high].
     * 3. If nums[mid] > nums[high], it means the array is unsorted in the right half.
     * Therefore, the inflection point (minimum element) MUST be in the right half,
     * and it cannot be 'mid' itself (since mid is greater than high).
     * So, we set low = mid + 1.
     * 4. If nums[mid] <= nums[high], the right half is perfectly sorted. This means
     * the minimum element is either 'mid' itself, or it exists in the left half.
     * So, we set high = mid.
     * 5. The loop terminates when low == high, at which point 'low' points to the minimum.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). We halve the search space at every step.
     * - Space Complexity: O(1) auxiliary space. We only use three primitive integer
     * variables (low, high, mid) which are stored on the stack. Zero heap space used.
     */
    public int findMinBest(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] > nums[high]) {
                // Minimum is in the right half
                low = mid + 1;
            } else {
                // Minimum is in the left half, or is mid itself
                high = mid;
            }
        }

        return nums[low];
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Search) - The "Think it" stage
     * ========================================================================
     * Approach:
     * Iterate through every element in the array and keep track of the minimum
     * value encountered so far.
     * * Detailed Intuition:
     * This approach completely ignores the fact that the array is sorted and rotated.
     * It treats it as an unsorted array. While this violates the O(log N) constraint
     * requested by the problem, it is foolproof and guarantees the correct answer.
     * It serves as an excellent baseline to test our optimal O(log n) solution against.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. We scan every element.
     * - Space Complexity: O(1) auxiliary space. Only a loop counter and a minimum
     * tracking variable are used.
     */
    public int findMinBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
        }
        return min;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Recursive Binary Search)
     * ========================================================================
     * Approach:
     * The exact same logical premise as Phase 1, but implemented functionally
     * using recursion instead of an iterative while-loop.
     * * Detailed Intuition:
     * Sometimes interviewers ask for a recursive implementation to test your
     * understanding of call stacks and base cases. The logic holds: compare
     * mid to high, and recursively call the function on the appropriate subarray
     * until the base case (low == high) is reached.
     * * Complexity Analysis:
     * - Time Complexity: O(log N). The search space is still halved each time.
     * - Space Complexity: O(log N) auxiliary stack space. Because the tree depth
     * of our recursive calls is bounded by log(N), we consume O(log N) frames
     * on the call stack. No dynamic heap space is allocated.
     */
    public int findMinAlternative(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        return findMinRecursive(nums, 0, nums.length - 1);
    }

    private int findMinRecursive(int[] nums, int low, int high) {
        // Base case: only one element left in the search space
        if (low == high) {
            return nums[low];
        }

        int mid = low + (high - low) / 2;

        if (nums[mid] > nums[high]) {
            // Min is strictly to the right
            return findMinRecursive(nums, mid + 1, high);
        } else {
            // Min is at mid or to the left
            return findMinRecursive(nums, low, mid);
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering normal cases, edge cases (fully sorted),
     * and specific rotation boundaries.
     */
    public static void main(String[] args) {
        FindMinimumInRotatedArray solution = new FindMinimumInRotatedArray();

        // Test Cases Setup
        int[][] testArrays = {
                {3, 4, 5, 1, 2},          // Standard rotation, min in right half
                {4, 5, 6, 7, 0, 1, 2},    // Standard rotation, min near middle
                {11, 13, 15, 17},         // Edge Case: 0 rotations (already fully sorted)
                {2, 1},                   // Edge Case: 2 elements, rotated
                {1, 2},                   // Edge Case: 2 elements, not rotated
                {1},                      // Edge Case: Single element
                {5, 1, 2, 3, 4}           // Rotation where min is immediately after pivot
        };

        int[] expectedAnswers = {1, 0, 11, 1, 1, 1, 1};

        System.out.println("==========================================================");
        System.out.println("Executing Testing Suite for Find Minimum in Rotated Array");
        System.out.println("==========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int expected = expectedAnswers[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array:    " + java.util.Arrays.toString(nums));
            System.out.println("Expected  : " + expected);

            int res1 = solution.findMinBest(nums);
            int res2 = solution.findMinBruteForce(nums);
            int res3 = solution.findMinAlternative(nums);

            System.out.println("Phase 1 (Optimal) : " + res1);
            System.out.println("Phase 2 (Brute)   : " + res2);
            System.out.println("Phase 3 (Recurse) : " + res3);

            if(res1 == expected && res2 == expected && res3 == expected) {
                System.out.println("Status: PASS ✅");
            } else {
                System.out.println("Status: FAIL ❌ (Mismatch in approach outputs)");
            }
            System.out.println("----------------------------------------------------------");
        }
    }
}
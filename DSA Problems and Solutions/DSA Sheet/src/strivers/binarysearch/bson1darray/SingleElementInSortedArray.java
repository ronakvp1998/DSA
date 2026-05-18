package strivers.binarysearch.bson1darray;

/**
 * ============================================================================
 * HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 540. Single Element in a Sorted Array
 * Level: Medium
 * * Problem Statement:
 * You are given a sorted array consisting of only integers where every element
 * appears exactly twice, except for one element which appears exactly once.
 * * Return the single element that appears only once.
 * * Your solution must run in O(log n) time and O(1) space.
 * * Example 1:
 * Input: nums = [1,1,2,3,3,4,4,8,8]
 * Output: 2
 * * Example 2:
 * Input: nums = [3,3,7,7,10,11,11]
 * Output: 10
 * * Constraints:
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^5
 * * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 * As this is an Array/Binary Search problem (Not DP), we follow the non-DP roadmap:
 * Phase 1: Best and recommended approach (Optimized Binary Search)
 * Phase 2: Brute Force approach (Linear Search)
 * Phase 3: Alternative Approaches (Bitwise XOR)
 */

public class SingleElementInSortedArray {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Optimized Binary Search)
     * ========================================================================
     * Approach:
     * We leverage the fact that the array is sorted and every element appears twice
     * except for one. This creates a predictable pattern of pairs based on indices:
     * - BEFORE the single element: The pairs are located at (even, odd) indices.
     * (e.g., index 0 and 1, index 2 and 3).
     * - AFTER the single element: The pattern shifts. Pairs are now located at
     * (odd, even) indices.
     * * We can use Binary Search to find the point where this pattern breaks.
     * * Detailed Intuition:
     * 1. First, we handle edge cases: array of size 1, or the single element
     * being at the very beginning or the very end of the array. This allows us
     * to strictly search from index 1 to n-2 without going out of bounds.
     * 2. We calculate `mid`. If `nums[mid]` is different from both its left and
     * right neighbors, we have found the single element!
     * 3. Otherwise, we check if we are in the "left half" (before the single element).
     * We are in the left half if:
     * - `mid` is odd and `nums[mid] == nums[mid - 1]`
     * - `mid` is even and `nums[mid] == nums[mid + 1]`
     * 4. If we are in the left half, the single element MUST be to our right.
     * We update `low = mid + 1`.
     * 5. If not, we are in the right half, meaning the single element is to our left.
     * We update `high = mid - 1`.
     * * Complexity Analysis:
     * - Time Complexity: O(log N) where N is the length of the array. The search
     * space is halved at each step, meeting the strict problem constraint.
     * - Space Complexity: O(1) auxiliary space. We only use primitive pointers
     * (low, high, mid) allocated on the stack. No heap space is consumed.
     */
    public int singleNonDuplicateBest(int[] nums) {
        int n = nums.length;

        // Edge cases
        if (n == 1) return nums[0];
        if (nums[0] != nums[1]) return nums[0];
        if (nums[n - 1] != nums[n - 2]) return nums[n - 1];

        // Binary search space narrowed down safely
        int low = 1;
        int high = n - 2;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Check if mid is the single element
            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }

            // Determine if we are in the left half
            if ((mid % 2 == 1 && nums[mid] == nums[mid - 1]) ||
                    (mid % 2 == 0 && nums[mid] == nums[mid + 1])) {
                // Move right
                low = mid + 1;
            } else {
                // Move left
                high = mid - 1;
            }
        }

        return -1; // Should not be reached given problem constraints
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Scan) - The "Think it" stage
     * ========================================================================
     * Approach:
     * Iterate through the array checking adjacent elements. Since duplicates
     * sit next to each other, we can jump by 2 steps. If the current element
     * doesn't match the next one, the current element is the single one.
     * * Detailed Intuition:
     * This is the most straightforward way to solve the problem, though it
     * ignores the O(log n) constraint. We check pairs `(nums[i], nums[i+1])`.
     * The moment the pair does not match, `nums[i]` is our anomaly. If we
     * traverse the whole array without a mismatch, the very last element
     * must be the single one.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. In the worst
     * case (single element is at the end), we traverse roughly N/2 steps.
     * - Space Complexity: O(1) auxiliary space. Only a loop counter is used.
     */
    public int singleNonDuplicateBruteForce(int[] nums) {
        int n = nums.length;

        // Jump by 2 to check pairs
        for (int i = 0; i < n - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }

        // If no mismatch found, the last element is the single one
        return nums[n - 1];
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACHES (Bitwise XOR)
     * ========================================================================
     * Approach:
     * Use the XOR bitwise operator across all elements in the array.
     * * Detailed Intuition:
     * The XOR operator has two very useful properties for this problem:
     * 1. A ^ A = 0 (XORing a number by itself results in 0).
     * 2. A ^ 0 = A (XORing a number by 0 results in the number itself).
     * * Because every number except one appears exactly twice, they will all
     * cancel each other out to 0. The remaining number will be XORed with 0,
     * leaving just the single element. This approach doesn't even require the
     * array to be sorted!
     * * Complexity Analysis:
     * - Time Complexity: O(N). We must process every element in the array once.
     * While beautiful and standard for "single number" problems, it fails
     * the strict O(log n) constraint of this specific variation.
     * - Space Complexity: O(1) auxiliary stack space.
     */
    public int singleNonDuplicateAlternative(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Comprehensive tests covering standard cases, edge cases (single element,
     * answer at the start, answer at the end).
     */
    public static void main(String[] args) {
        SingleElementInSortedArray solution = new SingleElementInSortedArray();

        // Test Cases Setup
        int[][] testArrays = {
                {1, 1, 2, 3, 3, 4, 4, 8, 8}, // Standard case, single in middle left
                {3, 3, 7, 7, 10, 11, 11},    // Standard case, single in middle right
                {1},                         // Edge case: Size 1
                {1, 2, 2, 3, 3},             // Edge case: Single element at the start
                {1, 1, 2, 2, 3},             // Edge case: Single element at the end
                {1, 1, 2, 2, 3, 3, 4}        // Edge case: Single element at the end (longer)
        };

        int[] expectedAnswers = {2, 10, 1, 1, 3, 4};

        System.out.println("==========================================================");
        System.out.println("Executing Testing Suite for Single Element in Sorted Array");
        System.out.println("==========================================================\n");

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int expected = expectedAnswers[i];

            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Array:    " + java.util.Arrays.toString(nums));
            System.out.println("Expected  : " + expected);

            int res1 = solution.singleNonDuplicateBest(nums);
            int res2 = solution.singleNonDuplicateBruteForce(nums);
            int res3 = solution.singleNonDuplicateAlternative(nums);

            System.out.println("Phase 1 (Optimal) : " + res1);
            System.out.println("Phase 2 (Brute)   : " + res2);
            System.out.println("Phase 3 (XOR)     : " + res3);

            if(res1 == expected && res2 == expected && res3 == expected) {
                System.out.println("Status: PASS ✅");
            } else {
                System.out.println("Status: FAIL ❌ (Mismatch in outputs)");
            }
            System.out.println("----------------------------------------------------------");
        }
    }
}
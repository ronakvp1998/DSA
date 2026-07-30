package strivers.arrays.easy;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 189. Rotate Array
 * Difficulty: Medium
 *
 * Formal Problem Statement:
 * Given an integer array nums, rotate the array to the right by k steps,
 * where k is non-negative.
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -2^31 <= nums[i] <= 2^31 - 1
 * - 0 <= k <= 10^5
 *
 * Follow up:
 * - Try to come up with as many solutions as you can. There are at least three
 *   different ways to solve this problem.
 * - Could you do it in-place with O(1) extra space?
 *
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 * ============================================================================
 */

import java.util.Arrays;
import java.util.stream.Collectors;

public class RotateArrayByK {

    /**
     * ============================================================================
     * 2.2 PHASE 1: OPTIMAL APPROACH (Array Reversal Method)
     * ============================================================================
     * Detailed Intuition:
     * When we rotate an array right by k steps, the last k elements of the array
     * move to the front, and the first (n-k) elements shift to the right.
     * We can achieve this cleanly in O(1) space using three reversals:
     * 1. Reversing the ENTIRE array brings the last k elements to the front,
     *    but they are in reverse order. The remaining (n-k) elements are at the
     *    back, also in reverse order.
     * 2. Reversing the first k elements restores their original internal order.
     * 3. Reversing the remaining (n-k) elements restores their original order.
     *
     * Example: nums = [1, 2, 3, 4, 5, 6, 7], k = 3
     * Reverse All:    [7, 6, 5, 4, 3, 2, 1]
     * Reverse 0-2:    [5, 6, 7, 4, 3, 2, 1]
     * Reverse 3-6:    [5, 6, 7, 1, 2, 3, 4] -> Done!
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the array. Each element
     *   is touched at most twice (once during full reverse, once during partial).
     * - Space Complexity: O(1) auxiliary stack/heap space. Strictly in-place.
     */
    public void rotateOptimal(int[] nums, int k) {
        if (nums == null || nums.length < 2) return;

        int n = nums.length;
        k = k % n; // Handle k > n cases
        if (k == 0) return; // No rotation needed

        // Step 1: Reverse the whole array
        reverse(nums, 0, n - 1);
        // Step 2: Reverse the first k elements
        reverse(nums, 0, k - 1);
        // Step 3: Reverse the remaining n-k elements
        reverse(nums, k, n - 1);
    }

    /** Helper method to reverse a portion of an array in-place */
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * ============================================================================
     * 2.2 PHASE 2: BRUTE FORCE APPROACH (Rotate 1 Step, K Times)
     * ============================================================================
     * Detailed Intuition:
     * The most intuitive way to rotate is to take the last element, move it to
     * the front, and shift every other element to the right by 1. We simply
     * repeat this process K times.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * K). For each of the K steps, we shift N elements.
     *   This will yield a Time Limit Exceeded (TLE) error for large constraints
     *   on LeetCode (e.g., N = 10^5, K = 10^5 -> 10^10 operations).
     * - Space Complexity: O(1) auxiliary space. In-place modification.
     */
    public void rotateBruteForce(int[] nums, int k) {
        if (nums == null || nums.length < 2) return;

        int n = nums.length;
        k = k % n;

        for (int i = 0; i < k; i++) {
            int previous = nums[n - 1]; // Store the last element
            for (int j = 0; j < n; j++) {
                int temp = nums[j];
                nums[j] = previous;
                previous = temp;
            }
        }
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 1 (Using Extra Array)
     * ============================================================================
     * Detailed Intuition:
     * We know exactly where each element will end up after k rotations.
     * An element at index 'i' will land at index '(i + k) % n'.
     * We can create a new array, place elements in their calculated final
     * positions, and then copy the new array back into the original one.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array once to copy elements
     *   out, and once to copy them back in.
     * - Space Complexity: O(N) heap space to allocate the temporary array.
     *   (Fails the O(1) extra space Follow-up constraint).
     */
    public void rotateExtraSpace(int[] nums, int k) {
        if (nums == null || nums.length < 2) return;

        int n = nums.length;
        int[] rotated = new int[n];

        for (int i = 0; i < n; i++) {
            rotated[(i + k) % n] = nums[i];
        }

        for (int i = 0; i < n; i++) {
            nums[i] = rotated[i];
        }
    }

    /**
     * ============================================================================
     * 2.2 PHASE 3: ALTERNATIVE APPROACH 2 (Cyclic Replacements)
     * ============================================================================
     * Detailed Intuition:
     * We can jump elements directly to their final positions ((i + k) % n) one by
     * one. We store the displaced element and continue the jump sequence.
     * However, if n and k share a greatest common divisor (GCD) > 1, the jumps
     * will form a cycle and return to the starting index before touching every
     * element. When we hit a cycle, we increment our starting index by 1 and start
     * a new cycle. We stop when we have moved exactly N elements.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Every element is moved exactly once.
     * - Space Complexity: O(1) auxiliary space. In-place swapping.
     */
    public void rotateCyclic(int[] nums, int k) {
        if (nums == null || nums.length < 2) return;

        int n = nums.length;
        k = k % n;
        if (k == 0) return;

        int count = 0; // Number of elements successfully placed
        for (int start = 0; count < n; start++) {
            int current = start;
            int prevValue = nums[start];

            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prevValue;
                prevValue = temp;
                current = next;
                count++;
            } while (start != current); // Stop when cycle loops back to start
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        RotateArrayByK solution = new RotateArrayByK();

        System.out.println("--- Testing LeetCode 189: Rotate Array ---");

        // Test Case 1: Standard case (Example 1)
        int[] tc1 = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\nTest 1 (Optimal Reversal):");
        System.out.println("Input: " + printArray(tc1) + " | k = 3");
        solution.rotateOptimal(tc1, 3);
        System.out.println("Output: " + printArray(tc1)); // Expected: [5, 6, 7, 1, 2, 3, 4]

        // Test Case 2: Standard case (Example 2)
        int[] tc2 = {-1, -100, 3, 99};
        System.out.println("\nTest 2 (Cyclic Replacement):");
        System.out.println("Input: " + printArray(tc2) + " | k = 2");
        solution.rotateCyclic(tc2, 2);
        System.out.println("Output: " + printArray(tc2)); // Expected: [3, 99, -1, -100]

        // Test Case 3: K greater than N
        int[] tc3 = {1, 2};
        System.out.println("\nTest 3 (K > N using Extra Space):");
        System.out.println("Input: " + printArray(tc3) + " | k = 3");
        solution.rotateExtraSpace(tc3, 3);
        System.out.println("Output: " + printArray(tc3)); // Expected: [2, 1]

        // Test Case 4: Edge Case - Array size 1
        int[] tc4 = {1};
        System.out.println("\nTest 4 (Size 1 using Brute Force):");
        System.out.println("Input: " + printArray(tc4) + " | k = 0");
        solution.rotateBruteForce(tc4, 0);
        System.out.println("Output: " + printArray(tc4)); // Expected: [1]

        // Test Case 5: Edge Case - K = 0
        int[] tc5 = {1, 2, 3};
        System.out.println("\nTest 5 (K = 0 Optimal):");
        System.out.println("Input: " + printArray(tc5) + " | k = 0");
        solution.rotateOptimal(tc5, 0);
        System.out.println("Output: " + printArray(tc5)); // Expected: [1, 2, 3]
    }

    /** Helper using Java 8 Stream API to format array printing */
    private static String printArray(int[] arr) {
        return Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 503. Next Greater Element II
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given a circular integer array nums (i.e., the next element of
 * nums[nums.length - 1] is nums[0]), return the next greater number for every
 * element in nums.
 * * The next greater number of a number x is the first greater number to its
 * traversing-order next in the array, which means you could search circularly
 * to find its next greater number. If it doesn't exist, return -1 for this number.
 *
 * Example 1:
 * Input: nums = [1,2,1]
 * Output: [2,-1,2]
 * Explanation:
 * - The first 1's next greater number is 2.
 * - The number 2 can't find next greater number.
 * - The second 1's next greater number needs to search circularly, which is also 2.
 *
 * Example 2:
 * Input: nums = [1,2,3,4,3]
 * Output: [2,3,4,-1,4]
 *
 * CONSTRAINTS:
 * 1 <= nums.length <= 10^4
 * -10^9 <= nums[i] <= 10^9
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Monotonic Decreasing Stack (Right-to-Left)
 * Phase 2: Brute Force Approach - Nested Loops with Modulo
 * Phase 3: Alternative Approach - Monotonic Stack storing Indices (Left-to-Right)
 * ============================================================================
 */
public class NextGreaterElementII {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Decreasing Stack)
     * ============================================================================
     * Detailed Intuition:
     * We need to find the Next Greater Element, which strongly hints at using a
     * Monotonic Decreasing Stack. However, the array is "circular".
     * How do we simulate a circular array without actually modifying the data structure?
     * We pretend the array is concatenated to itself! Instead of iterating from
     * 0 to N-1, we iterate from 0 to 2N - 1. We access elements using the modulo
     * operator: `nums[i % n]`.
     * * By traversing from right to left (from 2N - 1 down to 0), the stack gets
     * pre-populated with elements from the "circular" wrap-around during the
     * second half of the traversal (from 2N-1 down to N). When we reach the actual
     * elements (from N-1 down to 0), the stack already contains the necessary future
     * values to provide the correct answer.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the array. The loop runs
     * exactly 2N times. Every element is pushed onto the stack at most twice and
     * popped at most twice. Thus, operations scale linearly.
     * - Space Complexity: O(N) auxiliary heap space for the Deque. In the worst case
     * (e.g., a strictly decreasing array), the stack will hold N elements.
     * ============================================================================
     */
    public int[] nextGreaterElementsOptimal(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        // Pretend the array is double its size, iterate from right to left
        for (int i = 2 * n - 1; i >= 0; i--) {
            int current = nums[i % n];

            // Maintain the decreasing monotonic property
            while (!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }

            // Only record the answer for the actual elements (indices 0 to N-1)
            if (i < n) {
                ans[i] = stack.isEmpty() ? -1 : stack.peek();
            }

            // Push current element for future evaluations
            stack.push(current);
        }

        return ans;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Loops with Modulo) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most literal translation of the problem statement. For each element at
     * index `i`, we scan the next N-1 elements. We use `(i + j) % n` to seamlessly
     * wrap around the end of the array back to the beginning. As soon as we find
     * an element strictly greater than `nums[i]`, we record it and break the loop.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). In the worst-case scenario (e.g., [5, 4, 3, 2, 1]),
     * the inner loop runs N-1 times for every single element, leading to quadratic time.
     * - Space Complexity: O(1) auxiliary space (excluding the output array).
     * ============================================================================
     */
    public int[] nextGreaterElementsBruteForce(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        // Default to -1 in case no greater element is found
        Arrays.fill(ans, -1);

        for (int i = 0; i < n; i++) {
            // Check up to N-1 subsequent elements
            for (int j = 1; j < n; j++) {
                int circularIndex = (i + j) % n;

                if (nums[circularIndex] > nums[i]) {
                    ans[i] = nums[circularIndex];
                    break;
                }
            }
        }

        return ans;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Monotonic Stack storing Indices)
     * ============================================================================
     * Detailed Intuition:
     * Instead of going Right-to-Left and storing values, we can traverse
     * Left-to-Right and store INDICES in the stack.
     * - The stack keeps track of indices whose Next Greater Element is not yet found.
     * - If we encounter a number `nums[i % n]` that is larger than the number at
     * the index stored on the top of the stack, we have found the answer for that
     * suspended index!
     * This is a very common alternative pattern for Monotonic Stacks.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Loop runs 2N times.
     * - Space Complexity: O(N) auxiliary space.
     * ============================================================================
     */
    public int[] nextGreaterElementsLeftToRight(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        // Stack will store indices, not values
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < 2 * n; i++) {
            int circularIndex = i % n;

            // While the current element is greater than the element at the index
            // stored on top of the stack, we resolve the NGE for that stored index.
            while (!stack.isEmpty() && nums[stack.peek()] < nums[circularIndex]) {
                int resolvedIndex = stack.pop();
                ans[resolvedIndex] = nums[circularIndex];
            }

            // Only push indices from the first pass
            if (i < n) {
                stack.push(circularIndex);
            }
        }

        return ans;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        NextGreaterElementII solver = new NextGreaterElementII();

        // Utility to cleanly format array outputs
        java.util.function.Function<int[], String> format =
                arr -> Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Circular Input (Example 1) ===");
        int[] nums1 = {1, 2, 1};
        System.out.println("Input:          " + format.apply(nums1));
        System.out.println("Optimal:        " + format.apply(solver.nextGreaterElementsOptimal(nums1)));
        System.out.println("Brute Force:    " + format.apply(solver.nextGreaterElementsBruteForce(nums1)));
        System.out.println("Left-to-Right:  " + format.apply(solver.nextGreaterElementsLeftToRight(nums1)));

        System.out.println("\n=== Test Case 2: Complex Array (Example 2) ===");
        int[] nums2 = {1, 2, 3, 4, 3};
        System.out.println("Input:          " + format.apply(nums2));
        System.out.println("Optimal:        " + format.apply(solver.nextGreaterElementsOptimal(nums2)));

        System.out.println("\n=== Test Case 3: Strictly Decreasing (Worst Case) ===");
        // NGE for everything except the smallest element wraps around to the largest
        int[] nums3 = {5, 4, 3, 2, 1};
        System.out.println("Input:          " + format.apply(nums3));
        System.out.println("Optimal:        " + format.apply(solver.nextGreaterElementsOptimal(nums3)));

        System.out.println("\n=== Test Case 4: Identical Elements ===");
        // No element is strictly greater
        int[] nums4 = {7, 7, 7};
        System.out.println("Input:          " + format.apply(nums4));
        System.out.println("Optimal:        " + format.apply(solver.nextGreaterElementsOptimal(nums4)));
    }
}
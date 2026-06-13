package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 2104. Sum of Subarray Ranges
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * You are given an integer array nums. The range of a subarray of nums is the
 * difference between the largest and smallest element in the subarray.
 * Return the sum of all subarray ranges of nums.
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: 4
 * Explanation: The 6 subarrays of nums are the following:
 * [1], range = largest - smallest = 1 - 1 = 0
 * [2], range = 2 - 2 = 0
 * [3], range = 3 - 3 = 0
 * [1,2], range = 2 - 1 = 1
 * [2,3], range = 3 - 2 = 1
 * [1,2,3], range = 3 - 1 = 2
 * So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.
 *
 * Example 2:
 * Input: nums = [4,-2,-3,4,1]
 * Output: 59
 * Explanation: The sum of all subarray ranges of nums is 59.
 *
 * CONSTRAINTS:
 * 1 <= nums.length <= 1000
 * -10^9 <= nums[i] <= 10^9
 * * Follow-up: Could you find a solution with O(n) time complexity?
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Contribution Technique (O(N) Time)
 * Phase 2: Brute Force Approach - Nested Loops (O(N^2) Time)
 * Phase 3: Alternative Approach - DP-like Array Formulation (O(N) Time)
 * ============================================================================
 */
public class SumOfSubarrayRanges {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Contribution Technique / Monotonic Stack)
     * ============================================================================
     * Detailed Intuition:
     * We know that:
     * Sum of Subarray Ranges = Sum of Maximums of all subarrays - Sum of Minimums of all subarrays.
     * * Since you have already conquered "Sum of Subarray Minimums" (LC 907), this
     * problem is exactly that concept applied twice!
     * 1. We use a Monotonic Increasing Stack to find how many subarrays each element
     * acts as the MINIMUM. We subtract `(count * nums[i])` from the total sum.
     * 2. We use a Monotonic Decreasing Stack to find how many subarrays each element
     * acts as the MAXIMUM. We add `(count * nums[i])` to the total sum.
     * * * MASTERCLASS OPTIMIZATION: Instead of making multiple passes to find the
     * left boundary and right boundary separately, we can do it in a single pass
     * per stack. When an element is popped from the stack, the current index `right`
     * is its strictly bounded right boundary, and the new top of the stack is its
     * bounded left boundary.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate over the array twice. Each element is
     * pushed and popped from each stack exactly once, making it amortized O(1) per element.
     * - Space Complexity: O(N) auxiliary heap space for the Deques.
     * ============================================================================
     */
    public long subArrayRangesOptimal(int[] nums) {
        int n = nums.length;
        long totalSum = 0;

        Deque<Integer> stack = new ArrayDeque<>();

        // 1. Calculate Sum of Minimums (Subtract from total)
        // We iterate up to 'n' to forcefully flush the remaining elements in the stack.
        for (int right = 0; right <= n; right++) {
            // Use Integer.MIN_VALUE as a dummy tail to pop everything at the end
            int currentVal = (right == n) ? Integer.MIN_VALUE : nums[right];

            // Enforce strictly increasing monotonic stack
            while (!stack.isEmpty() && nums[stack.peek()] > currentVal) {
                int i = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();

                // Number of subarrays where nums[i] is the minimum
                long count = (long) (i - left) * (right - i);
                totalSum -= count * nums[i];
            }
            if (right < n) stack.push(right);
        }

        stack.clear();

        // 2. Calculate Sum of Maximums (Add to total)
        for (int right = 0; right <= n; right++) {
            // Use Integer.MAX_VALUE as a dummy tail to pop everything at the end
            int currentVal = (right == n) ? Integer.MAX_VALUE : nums[right];

            // Enforce strictly decreasing monotonic stack
            while (!stack.isEmpty() && nums[stack.peek()] < currentVal) {
                int i = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();

                // Number of subarrays where nums[i] is the maximum
                long count = (long) (i - left) * (right - i);
                totalSum += count * nums[i];
            }
            if (right < n) stack.push(right);
        }

        return totalSum;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Loops) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The constraints specify N <= 1000. An O(N^2) solution will easily pass within
     * the time limit. This is the most intuitive approach.
     * We define a starting index `i` and expand a window to the right `j`.
     * As we expand, we keep track of the absolute maximum and absolute minimum
     * seen so far in that specific window. At every step, we add the difference
     * `(max - min)` to our total sum.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). We evaluate all N*(N+1)/2 contiguous subarrays.
     * - Space Complexity: O(1) auxiliary space. We only store scalar running variables.
     * ============================================================================
     */
    public long subArrayRangesBruteForce(int[] nums) {
        long totalSum = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int min = nums[i];
            int max = nums[i];

            // Expand the window to the right
            for (int j = i; j < n; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                totalSum += (max - min);
            }
        }

        return totalSum;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (DP-like Array Formulation)
     * ============================================================================
     * Detailed Intuition:
     * This is identical in logic to Phase 1, but instead of calculating the answer
     * "on the fly" as elements pop from the stack, we populate four distinct
     * arrays: `leftMin`, `rightMin`, `leftMax`, `rightMax`.
     * Many candidates find this visual mapping easier to reason about during an
     * interview, even if it requires more lines of code and passes over the array.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Four linear passes to populate boundary arrays,
     * plus one final pass to compute the sum.
     * - Space Complexity: O(N) auxiliary space for four bounds arrays and a stack.
     * ============================================================================
     */
    public long subArrayRangesAlternative(int[] nums) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        Arrays.fill(leftMin, -1);
        Arrays.fill(rightMin, n);
        Arrays.fill(leftMax, -1);
        Arrays.fill(rightMax, n);

        Deque<Integer> stack = new ArrayDeque<>();

        // Find previous smaller element (Strict inequality to prevent duplicate overlap)
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) stack.pop();
            if (!stack.isEmpty()) leftMin[i] = stack.peek();
            stack.push(i);
        }

        stack.clear();
        // Find next smaller element
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) stack.pop();
            if (!stack.isEmpty()) rightMin[i] = stack.peek();
            stack.push(i);
        }

        stack.clear();
        // Find previous greater element
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) stack.pop();
            if (!stack.isEmpty()) leftMax[i] = stack.peek();
            stack.push(i);
        }

        stack.clear();
        // Find next greater element
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) stack.pop();
            if (!stack.isEmpty()) rightMax[i] = stack.peek();
            stack.push(i);
        }

        long totalSum = 0;
        for (int i = 0; i < n; i++) {
            long minContrib = (long)(i - leftMin[i]) * (rightMin[i] - i) * nums[i];
            long maxContrib = (long)(i - leftMax[i]) * (rightMax[i] - i) * nums[i];
            totalSum += (maxContrib - minContrib);
        }

        return totalSum;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        SumOfSubarrayRanges solver = new SumOfSubarrayRanges();

        // Formatter using Java 8 Streams for clean terminal output
        java.util.function.Function<int[], String> format =
                array -> Arrays.stream(array)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Input (Example 1) ===");
        int[] arr1 = {1, 2, 3};
        System.out.println("Input:        " + format.apply(arr1));
        System.out.println("Optimal:      " + solver.subArrayRangesOptimal(arr1) + " (Expected: 4)");
        System.out.println("Brute Force:  " + solver.subArrayRangesBruteForce(arr1));
        System.out.println("Alternative:  " + solver.subArrayRangesAlternative(arr1));

        System.out.println("\n=== Test Case 2: Duplicate Elements (Example 2) ===");
        int[] arr2 = {1, 3, 3};
        System.out.println("Input:        " + format.apply(arr2));
        System.out.println("Optimal:      " + solver.subArrayRangesOptimal(arr2) + " (Expected: 4)");
        System.out.println("Brute Force:  " + solver.subArrayRangesBruteForce(arr2));

        System.out.println("\n=== Test Case 3: Negative Elements (Example 3) ===");
        int[] arr3 = {4, -2, -3, 4, 1};
        System.out.println("Input:        " + format.apply(arr3));
        System.out.println("Optimal:      " + solver.subArrayRangesOptimal(arr3) + " (Expected: 59)");
        System.out.println("Brute Force:  " + solver.subArrayRangesBruteForce(arr3));
        System.out.println("Alternative:  " + solver.subArrayRangesAlternative(arr3));

        System.out.println("\n=== Test Case 4: Strictly Decreasing Array ===");
        int[] arr4 = {5, 4, 3, 2, 1};
        System.out.println("Input:        " + format.apply(arr4));
        System.out.println("Optimal:      " + solver.subArrayRangesOptimal(arr4) + " (Expected: 20)");

        System.out.println("\n=== Test Case 5: All Same Elements (Range = 0) ===");
        int[] arr5 = {7, 7, 7, 7, 7};
        System.out.println("Input:        " + format.apply(arr5));
        System.out.println("Optimal:      " + solver.subArrayRangesOptimal(arr5) + " (Expected: 0)");
    }
}
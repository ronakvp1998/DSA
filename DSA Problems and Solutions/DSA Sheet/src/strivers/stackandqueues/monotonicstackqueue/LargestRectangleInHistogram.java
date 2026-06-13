package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 84. Largest Rectangle in Histogram
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given an array of integers heights representing the histogram's bar height
 * where the width of each bar is 1, return the area of the largest rectangle
 * in the histogram.
 *
 * Example 1:
 * Input: heights = [2,1,5,6,2,3]
 * Output: 10
 * Explanation: The largest rectangle has an area = 10 units (formed by the
 * bars of height 5 and 6, with a width of 2).
 *
 * Example 2:
 * Input: heights = [2,4]
 * Output: 4
 * Explanation: The largest rectangle is formed by the bar of height 4.
 *
 * CONSTRAINTS:
 * 1 <= heights.length <= 10^5
 * 0 <= heights[i] <= 10^4
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Single Pass Monotonic Stack (O(N) Time)
 * Phase 2: Brute Force Approach - Nested Loops (O(N^2) Time)
 * Phase 3: Alternative Approach - Three-Pass Precomputed Boundaries (O(N) Time)
 * ============================================================================
 */
public class LargestRectangleInHistogram {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Single Pass Monotonic Stack)
     * ============================================================================
     * Detailed Intuition:
     * This is a quintessential "Hard" problem for top-tier product company interviews.
     * Mastering this specific monotonic stack pattern unlocks several other hard
     * problems (like Maximal Rectangle).
     * * We want to find the largest rectangle. The height of any rectangle is bottlenecked
     * by the shortest bar within its width. Thus, for every bar `i`, we can ask:
     * "If this bar `heights[i]` is the absolute shortest bar in a rectangle, how far
     * left and right can that rectangle extend?"
     * * We use a Monotonic Increasing Stack that stores INDICES.
     * - As we iterate, if we encounter a bar shorter than the bar at the top of the
     * stack, we know we have found the "right boundary" for the stack's top bar!
     * - We pop the top bar. Its height is `heights[popped_index]`.
     * - Its "left boundary" is the new top of the stack (because the stack is strictly
     * increasing). If the stack is empty, it means this bar extends all the way to
     * index 0.
     * - Width = `current_index - stack.peek() - 1`.
     * - We calculate the area and keep a running maximum.
     * * *Masterclass trick:* We loop up to `i <= n` (one past the end of the array)
     * and treat the height at `n` as 0. This elegantly forces the stack to completely
     * flush out any remaining bars at the end of the iteration without needing a
     * secondary cleanup loop.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array once (plus one extra step).
     * Every index is pushed onto the stack exactly once and popped exactly once.
     * - Space Complexity: O(N) auxiliary heap space for the Deque. O(1) stack space.
     * ============================================================================
     */
    public int largestRectangleAreaOptimal(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        // Loop up to n to forcefully flush the stack at the end with a height of 0
        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            // Enforce increasing monotonic stack
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int heightOfPoppedBar = heights[stack.pop()];

                // If stack is empty, the popped bar was the smallest seen so far,
                // meaning it extends all the way to index 0.
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);

                maxArea = Math.max(maxArea, heightOfPoppedBar * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Loops) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most literal translation of the problem. We evaluate every possible contiguous
     * subarray (which represents the base/width of a rectangle).
     * For a starting index `i`, we expand our right boundary `j`. As we expand, we
     * keep track of the minimum height seen so far in this window. The maximum possible
     * rectangle in this specific `i` to `j` window is `minHeight * (j - i + 1)`.
     * We keep a running maximum of all these calculated areas.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). We evaluate all N*(N+1)/2 possible base widths.
     * This will yield a Time Limit Exceeded (TLE) on constraints of 10^5.
     * - Space Complexity: O(1) auxiliary space.
     * ============================================================================
     */
    public int largestRectangleAreaBruteForce(int[] heights) {
        int n = heights.length;
        int maxArea = 0;

        for (int i = 0; i < n; i++) {
            int minHeight = heights[i];

            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                int width = j - i + 1;
                maxArea = Math.max(maxArea, minHeight * width);
            }
        }

        return maxArea;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Three-Pass Precomputed Boundaries)
     * ============================================================================
     * Detailed Intuition:
     * If the single-pass logic is difficult to trace mentally, this is the perfect
     * bridging approach often expected as a stepping stone in high-level interviews.
     * Instead of resolving left and right boundaries simultaneously, we decouple them:
     * 1. Traverse left-to-right to build a `leftSmaller` array (the index of the first
     * smaller bar to the left).
     * 2. Traverse right-to-left to build a `rightSmaller` array (the index of the first
     * smaller bar to the right).
     * 3. Traverse the array one final time: For each bar `i`, its bounded width is
     * `rightSmaller[i] - leftSmaller[i] - 1`. Calculate area and maximize.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Three separate linear passes over the array.
     * - Space Complexity: O(N) auxiliary heap space for two arrays and a stack.
     * ============================================================================
     */
    public int largestRectangleAreaAlternative(int[] heights) {
        int n = heights.length;
        if (n == 0) return 0;

        int[] leftSmaller = new int[n];
        int[] rightSmaller = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        // 1. Find Next Smaller to the Left
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            leftSmaller[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // 2. Find Next Smaller to the Right
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            rightSmaller[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // 3. Compute Max Area
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int width = rightSmaller[i] - leftSmaller[i] - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }

        return maxArea;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        LargestRectangleInHistogram solver = new LargestRectangleInHistogram();

        // Helper lambda for clean formatting
        java.util.function.Function<int[], String> format =
                arr -> Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Input (Example 1) ===");
        int[] heights1 = {2, 1, 5, 6, 2, 3};
        System.out.println("Input:       " + format.apply(heights1));
        System.out.println("Optimal:     " + solver.largestRectangleAreaOptimal(heights1) + " (Expected: 10)");
        System.out.println("Brute Force: " + solver.largestRectangleAreaBruteForce(heights1));
        System.out.println("Alternative: " + solver.largestRectangleAreaAlternative(heights1));

        System.out.println("\n=== Test Case 2: Simple Input (Example 2) ===");
        int[] heights2 = {2, 4};
        System.out.println("Input:       " + format.apply(heights2));
        System.out.println("Optimal:     " + solver.largestRectangleAreaOptimal(heights2) + " (Expected: 4)");

        System.out.println("\n=== Test Case 3: Strictly Increasing ===");
        int[] heights3 = {1, 2, 3, 4, 5};
        System.out.println("Input:       " + format.apply(heights3));
        System.out.println("Optimal:     " + solver.largestRectangleAreaOptimal(heights3) + " (Expected: 9)");

        System.out.println("\n=== Test Case 4: Strictly Decreasing ===");
        int[] heights4 = {5, 4, 3, 2, 1};
        System.out.println("Input:       " + format.apply(heights4));
        System.out.println("Optimal:     " + solver.largestRectangleAreaOptimal(heights4) + " (Expected: 9)");

        System.out.println("\n=== Test Case 5: All Same Heights ===");
        int[] heights5 = {4, 4, 4, 4};
        System.out.println("Input:       " + format.apply(heights5));
        System.out.println("Optimal:     " + solver.largestRectangleAreaOptimal(heights5) + " (Expected: 16)");

        System.out.println("\n=== Test Case 6: Edge Case (Zeros included) ===");
        int[] heights6 = {2, 0, 2};
        System.out.println("Input:       " + format.apply(heights6));
        System.out.println("Optimal:     " + solver.largestRectangleAreaOptimal(heights6) + " (Expected: 2)");
    }
}
package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 42. Trapping Rain Water
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 *
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The elevation map is represented by array
 * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water are trapped.
 *
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 * CONSTRAINTS:
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Two Pointers (O(N) Time, O(1) Space)
 * Phase 2: Brute Force Approach - Nested Traversal (O(N^2) Time)
 * Phase 3: Alternative Approach 1 - Prefix & Suffix Arrays (O(N) Time & Space)
 * Phase 4: Alternative Approach 2 - Monotonic Decreasing Stack (O(N) Time & Space)
 * ============================================================================
 */
public class TrappingRainWaterMasterclass {


    /**
     * ============================================================================
     * PHASE 4: ALTERNATIVE APPROACH 2 (Monotonic Decreasing Stack)
     * ============================================================================
     * Detailed Intuition:
     * Instead of computing water column-by-column, we can compute it row-by-row
     * bounded horizontally. We use a strictly decreasing monotonic stack to track
     * indices.
     * When we encounter an elevation `height[i]` that is taller than the top of
     * the stack, we know a "puddle" has been formed.
     * - The bottom of the puddle is the popped element.
     * - The left wall is the new top of the stack.
     * - The right wall is `height[i]`.
     * We calculate the bounded water and continue popping until the monotonic
     * property is restored.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Each index is pushed and popped exactly once.
     * - Space Complexity: O(N) auxiliary heap space for the Deque.
     * ============================================================================
     */
    public int trapStack(int[] height) {
        int totalWater = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < height.length; i++) {
            // While a right boundary is found that is taller than the stack's top
            int current = height[i];
            while (!stack.isEmpty() && current > height[stack.peek()]) {
                int puddleBottom = stack.pop();

                if (stack.isEmpty()) {
                    break; // No left boundary exists to trap water
                }

                int leftBoundary = stack.peek();
                int width = i - leftBoundary - 1;
                int boundedHeight = Math.min(height[leftBoundary], current) - height[puddleBottom];

                totalWater += width * boundedHeight;
            }

            stack.push(i);
        }

        return totalWater;
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * To compute the trapped water at any index `i`, we need the maximum height to
     * its left (`left_max`) and the maximum height to its right (`right_max`).
     * The water trapped at `i` is bounded by `min(left_max, right_max) - height[i]`.
     * * Instead of fully calculating both arrays, we can use two pointers (`left`
     * and `right`). If `height[left] <= height[right]`, we are guaranteed that
     * there is a boundary on the right that is at least as tall as the left
     * boundary. Therefore, the limiting factor for trapped water at the `left`
     * pointer is purely `leftMax`. We can safely process the `left` pointer,
     * add trapped water if it's smaller than `leftMax`, or update `leftMax`
     * if it's taller. We apply the mirror logic for the `right` pointer.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the height array. Both
     * pointers converge towards the center, visiting each element exactly once.
     * - Space Complexity: O(1) auxiliary space. Only scalar variables are used.
     * ============================================================================
     */
    public int trapOptimal(int[] height) {
        if (height == null || height.length <= 2) return 0;

        int left = 0;
        int right = height.length - 1;

        int leftMax = 0;
        int rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (height[left] <= height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // Update highest seen on the left
                } else {
                    totalWater += leftMax - height[left]; // Trap water
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // Update highest seen on the right
                } else {
                    totalWater += rightMax - height[right]; // Trap water
                }
                right--;
            }
        }

        return totalWater;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Traversal) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most literal translation of the physics. For every single bar in the
     * array, scan all the way to the left to find its `leftMax`, and scan all
     * the way to the right to find its `rightMax`. The water above this specific
     * bar is exactly `min(leftMax, rightMax) - height[i]`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). For each of the N elements, we traverse the
     * rest of the array to find boundaries.
     * - Space Complexity: O(1) auxiliary space.
     * ============================================================================
     */
    public int trapBruteForce(int[] height) {
        int totalWater = 0;
        int n = height.length;

        for (int i = 0; i < n; i++) {
            int leftMax = 0;
            int rightMax = 0;

            // Find highest bar to the left of i (including i)
            for (int j = i; j >= 0; j--) {
                leftMax = Math.max(leftMax, height[j]);
            }

            // Find highest bar to the right of i (including i)
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            totalWater += Math.min(leftMax, rightMax) - height[i];
        }

        return totalWater;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH 1 (Prefix & Suffix Arrays / DP-style)
     * ============================================================================
     * Detailed Intuition:
     * The Brute Force approach does redundant work by recalculating `leftMax` and
     * `rightMax` for every single index. Instead, we can precompute these values!
     * 1. Traverse left-to-right to populate a `leftMax` array.
     * 2. Traverse right-to-left to populate a `rightMax` array.
     * 3. Iterate one last time (or use a Stream) to calculate the water at each index.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Three separate linear passes over the array.
     * - Space Complexity: O(N) auxiliary heap space for `leftMax` and `rightMax` arrays.
     * ============================================================================
     */
    public int trapPrefixSuffix(int[] height) {
        int n = height.length;
        if (n <= 2) return 0;

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Fill leftMax array
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }

        // Fill rightMax array
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }

        // Calculate total water using Java 8 IntStream for elegance
        return IntStream.range(0, n)
                .map(i -> Math.min(leftMax[i], rightMax[i]) - height[i])
                .sum();
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        TrappingRainWaterMasterclass solver = new TrappingRainWaterMasterclass();

        System.out.println("=== Test Case 1: Standard Elevation Map (Example 1) ===");
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Input:          " + Arrays.toString(height1));
        System.out.println("Optimal:        " + solver.trapOptimal(height1) + " (Expected: 6)");
        System.out.println("Brute Force:    " + solver.trapBruteForce(height1) + " (Expected: 6)");
        System.out.println("Prefix/Suffix:  " + solver.trapPrefixSuffix(height1) + " (Expected: 6)");
        System.out.println("Stack:          " + solver.trapStack(height1) + " (Expected: 6)");

        System.out.println("\n=== Test Case 2: Deep Valley (Example 2) ===");
        int[] height2 = {4, 2, 0, 3, 2, 5};
        System.out.println("Input:          " + Arrays.toString(height2));
        System.out.println("Optimal:        " + solver.trapOptimal(height2) + " (Expected: 9)");

        System.out.println("\n=== Test Case 3: Strictly Increasing (No trapped water) ===");
        int[] height3 = {0, 1, 2, 3, 4, 5};
        System.out.println("Input:          " + Arrays.toString(height3));
        System.out.println("Optimal:        " + solver.trapOptimal(height3) + " (Expected: 0)");

        System.out.println("\n=== Test Case 4: Peak in the middle ===");
        int[] height4 = {1, 3, 5, 2, 1};
        System.out.println("Input:          " + Arrays.toString(height4));
        System.out.println("Optimal:        " + solver.trapOptimal(height4) + " (Expected: 0)");

        System.out.println("\n=== Test Case 5: Single Puddle ===");
        int[] height5 = {5, 0, 5};
        System.out.println("Input:          " + Arrays.toString(height5));
        System.out.println("Optimal:        " + solver.trapOptimal(height5) + " (Expected: 5)");
    }
}
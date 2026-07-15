package strivers.slidingwind2pointer;

import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 42. Trapping Rain Water
 * Difficulty: Hard
 * * * Formal Problem Statement:
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 * * * Constraints:
 * - n == height.length
 * - 1 <= n <= 2 * 10^4
 * - 0 <= height[i] <= 10^5
 * * * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array
 * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section)
 * are being trapped.
 * * * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 * ============================================================================
 */
public class TrappingRainWater {


    /**
     * Phase 3: Alternative Approach - Precomputed Prefix/Suffix Arrays (Dynamic Programming)
     * * * Detailed Intuition:
     * The brute force recalculates the left and right maxes repeatedly. We can optimize
     * this by trading space for time. We iterate through the array once from left to right
     * to populate a `leftMax` array, and once from right to left to populate a `rightMax`
     * array. Then, in a final O(N) pass, we calculate the trapped water using the
     * precomputed arrays. This is often the most intuitive leap from the brute-force method.
     * * * Complexity Analysis:
     * Time Complexity: O(N)
     * We perform three separate O(N) traversals of the array.
     * Space Complexity: O(N)
     * We allocate two auxiliary arrays (`leftMax` and `rightMax`) on the heap, each of size N.
     */
    public int trapPrecomputedArrays(int[] height) {
        int n = height.length;
        if (n == 0) return 0;

        // Calculate left max boundary
        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }

        // Calculate right max boundary
        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }

        // Get the trapped water
        int trappedWater = 0;
        for (int i = 0; i < n; i++) {
            int waterLevel = Math.min(leftMax[i], rightMax[i]);
            trappedWater += waterLevel - height[i];
        }

        return trappedWater;
    }

    /**
     * ========================================================================
     * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (NON-DP FOCUS)
     * ========================================================================
     * * Phase 1: Optimal Approach - Two Pointers
     * * * Detailed Intuition:
     * To find the water trapped above any bar `i`, we need `min(leftMax, rightMax) - height[i]`.
     * Instead of fully calculating both arrays, we can maintain two pointers (`left` and `right`)
     * at the extremes of the array and maintain two variables, `leftMax` and `rightMax`.
     * If `height[left] < height[right]`, we are guaranteed that `leftMax < rightMax` (or at
     * least limited by `leftMax`). Thus, the trapped water at `left` strictly depends on
     * `leftMax`. We process the left side, incrementing the pointer. Conversely, if
     * `height[right] <= height[left]`, we process the right side. This removes the need
     * to store the boundaries in an array, saving space.
     * * * Complexity Analysis:
     * Time Complexity: O(N)
     * We traverse the array exactly once with the two pointers.
     * Space Complexity: O(1)
     * We only use four variables (`left`, `right`, `leftMax`, `rightMax`), requiring
     * strictly constant auxiliary space.
     */
    public int trapOptimal(int[] height) {
        if (height == null || height.length < 3) {
            return 0; // It takes at least 3 bars to form a trap
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int trappedWater = 0;

        while (left < right) {
            if (height[left] <= height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // Update left boundary
                } else {
                    trappedWater += leftMax - height[left]; // Calculate water
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // Update right boundary
                } else {
                    trappedWater += rightMax - height[right]; // Calculate water
                }
                right--;
            }
        }

        return trappedWater;
    }

    /**
     * Phase 2: Brute Force Approach
     * * * Detailed Intuition:
     * The "Think it" stage. For every single index `i` in the array, the amount of
     * water trapped is the minimum of maximum height of bars on both sides minus its
     * own height. We can literally run two internal loops for every element: one
     * scanning to the far left, and one scanning to the far right, to find the
     * respective maximum boundaries.
     * * * Complexity Analysis:
     * Time Complexity: O(N^2)
     * For each of the N elements, we traverse the rest of the array to find the
     * left and right peaks.
     * Space Complexity: O(1)
     * No extra arrays are created.
     */
    public int trapBruteForce(int[] height) {
        int trappedWater = 0;
        int n = height.length;

        for (int i = 0; i < n; i++) {
            int leftMax = 0;
            int rightMax = 0;

            // Find the maximum element on the left
            for (int j = i; j >= 0; j--) {
                leftMax = Math.max(leftMax, height[j]);
            }

            // Find the maximum element on the right
            for (int j = i; j < n; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            // Calculate trapped water for current bar
            trappedWater += Math.min(leftMax, rightMax) - height[i];
        }

        return trappedWater;
    }


    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        TrappingRainWater solution = new TrappingRainWater();

        // Standard and Edge Test Cases
        List<TestCase> testCases = Arrays.asList(
                new TestCase("Standard - LeetCode Example 1", new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, 6),
                new TestCase("Standard - LeetCode Example 2", new int[]{4, 2, 0, 3, 2, 5}, 9),
                new TestCase("Empty Array", new int[]{}, 0),
                new TestCase("No Traps Possible (Length < 3)", new int[]{2, 1}, 0),
                new TestCase("Flat Surface", new int[]{3, 3, 3, 3}, 0),
                new TestCase("Strictly Increasing", new int[]{1, 2, 3, 4, 5}, 0),
                new TestCase("Strictly Decreasing", new int[]{5, 4, 3, 2, 1}, 0),
                new TestCase("Massive Valley", new int[]{10, 0, 0, 0, 10}, 30)
        );

        System.out.println("=================================================");
        System.out.println("Running Trapping Rain Water Test Suite");
        System.out.println("=================================================");

        // Utilizing Java 8 Stream API to process and evaluate test cases
        testCases.stream().forEach(tc -> {
            int optimalResult = solution.trapOptimal(tc.heights);
            int bruteResult = solution.trapBruteForce(tc.heights);
            int dpResult = solution.trapPrecomputedArrays(tc.heights);

            boolean passed = (optimalResult == tc.expected) &&
                    (bruteResult == tc.expected) &&
                    (dpResult == tc.expected);

            System.out.printf("Test: %-35s | Expected: %-3d | Passed: %b%n",
                    tc.description, tc.expected, passed);

            if (!passed) {
                System.out.printf("   [!] FAILED -> Optimal: %d, DP: %d, Brute: %d%n",
                        optimalResult, dpResult, bruteResult);
            }
        });
        System.out.println("=================================================");
    }

    // Helper class for organizing test cases
    static class TestCase {
        String description;
        int[] heights;
        int expected;

        TestCase(String description, int[] heights, int expected) {
            this.description = description;
            this.heights = heights;
            this.expected = expected;
        }
    }
}
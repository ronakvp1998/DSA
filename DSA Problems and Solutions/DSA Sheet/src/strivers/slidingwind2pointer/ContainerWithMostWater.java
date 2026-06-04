package strivers.slidingwind2pointer;

import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem: 11. Container With Most Water
 * Difficulty: Medium
 * * Formal Problem Statement:
 * You are given an integer array height of length n. There are n vertical
 * lines drawn such that the two endpoints of the ith line are (i, 0) and
 * (i, height[i]).
 * * Find two lines that together with the x-axis form a container, such that
 * the container contains the most water.
 * Return the maximum amount of water a container can store.
 * Notice that you may not slant the container.
 * * Constraints:
 * - n == height.length
 * - 2 <= n <= 10^5
 * - 0 <= height[i] <= 10^4
 * * Example 1:
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The max area of water the container can contain is 49
 * (lines at index 1 and 8: min(8, 7) * (8 - 1) = 7 * 7 = 49).
 * * Example 2:
 * Input: height = [1,1]
 * Output: 1
 * Explanation: min(1, 1) * (1 - 0) = 1 * 1 = 1.
 * ============================================================================
 */
public class ContainerWithMostWater {

    /**
     * ========================================================================
     * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (NON-DP)
     * ========================================================================
     * * Phase 1: Optimal Approach - Two Pointers
     * * Detailed Intuition:
     * The area formed between two lines is limited by the shorter line.
     * Furthermore, the farther the lines are, the higher the area obtained.
     * We start with the maximum width possible (left pointer at the start,
     * right pointer at the end). To maximize the area, we must seek to
     * increase the height. Moving the pointer of the taller line inwards
     * cannot possibly increase the area because the area is bottlenecked by
     * the shorter line, and the width is strictly decreasing. Therefore,
     * we always move the pointer pointing to the shorter line inward,
     * hoping to find a taller line to compensate for the lost width.
     * * Complexity Analysis:
     * Time Complexity: O(N)
     * We traverse the array exactly once. Both pointers move towards each
     * other and never overlap or repeat elements.
     * Space Complexity: O(1)
     * No extra auxiliary space is used; only a few variables for pointers
     * and area calculation (heap space strictly limited to the array itself).
     */
    public int maxAreaOptimal(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            // Calculate the current area
            int currentHeight = Math.min(height[left], height[right]);
            int currentWidth = right - left;
            int currentArea = currentHeight * currentWidth;

            // Update the maximum area found so far
            maxArea = Math.max(maxArea, currentArea);

            // Move the pointer of the shorter line inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    /**
     * Phase 2: Brute Force Approach - Nested Loops
     * * Detailed Intuition:
     * The "Think it" stage. We evaluate every single possible pair of lines
     * in the array. For every line `i`, we pair it with every subsequent line
     * `j`, calculate the area they form, and keep track of the absolute
     * maximum. This guarantees we check the optimal container, but it does
     * immense redundant work evaluating containers that logically cannot hold
     * more water than boundaries already checked.
     * * Complexity Analysis:
     * Time Complexity: O(N^2)
     * The outer loop runs N times, and the inner loop runs N-i times,
     * resulting in an arithmetic progression sum: N*(N-1)/2 iterations.
     * (Note: Will cause Time Limit Exceeded on LeetCode for N = 10^5).
     * Space Complexity: O(1)
     * Only constant extra space is used for loop iterators and tracking variables.
     */
    public int maxAreaBruteForce(int[] height) {
        int maxArea = 0;
        int n = height.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int currentHeight = Math.min(height[i], height[j]);
                int currentWidth = j - i;
                int currentArea = currentHeight * currentWidth;
                maxArea = Math.max(maxArea, currentArea);
            }
        }

        return maxArea;
    }

    /**
     * Phase 3: Alternative Approaches
     * * Are there other ways?
     * - Sorting: We cannot sort the array because the specific x-axis
     * coordinates (indices) are strictly required to calculate the width.
     * - Binary Search: The search space is not monotonic regarding heights,
     * so binary searching for an optimal line doesn't work.
     * * Conclusion: The Two-Pointer approach is definitively the most optimal
     * and natural solution for this specific problem.
     */

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();

        // Standard and Edge Test Cases
        List<TestCase> testCases = Arrays.asList(
                new TestCase("Standard - LeetCode Example 1", new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}, 49),
                new TestCase("Minimum Length - LeetCode Example 2", new int[]{1, 1}, 1),
                new TestCase("Zeros at Ends", new int[]{0, 2, 3, 0}, 2), // min(2,3)*1 = 2
                new TestCase("All Zeros", new int[]{0, 0, 0, 0}, 0),
                new TestCase("Decreasing Array", new int[]{5, 4, 3, 2, 1}, 6), // min(5,2)*3=6 or min(4,2)*2=4 etc.
                new TestCase("Increasing Array", new int[]{1, 2, 3, 4, 5}, 6)
        );

        System.out.println("=================================================");
        System.out.println("Running Container With Most Water Test Suite");
        System.out.println("=================================================");

        // Utilizing Java 8 Stream API to process and evaluate test cases
        testCases.stream().forEach(tc -> {
            int optimalResult = solution.maxAreaOptimal(tc.heights);
            int bruteResult = solution.maxAreaBruteForce(tc.heights);

            boolean passed = (optimalResult == tc.expected) && (bruteResult == tc.expected);

            System.out.printf("Test: %-35s | Expected: %-3d | Passed: %b%n",
                    tc.description, tc.expected, passed);

            if (!passed) {
                System.out.printf("   [!] FAILED -> Optimal: %d, Brute: %d%n", optimalResult, bruteResult);
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
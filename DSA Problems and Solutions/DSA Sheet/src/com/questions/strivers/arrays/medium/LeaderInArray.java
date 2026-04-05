package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: LEADERS IN AN ARRAY
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * Problem Statement:
 * Given an integer array `arr`, print/return all the elements which are leaders.
 * A Leader is an element that is strictly greater than or equal to all the
 * elements on its right side in the array.
 * Note: The rightmost element is always a leader.
 * * Example 1:
 * Input: arr = [4, 7, 1, 0]
 * Output: [7, 1, 0]
 * Explanation:
 * - 7 is >= all elements to its right (1, 0).
 * - 1 is >= all elements to its right (0).
 * - 0 is the rightmost element.
 * * Example 2:
 * Input: arr = [10, 22, 12, 3, 0, 6]
 * Output: [22, 12, 6]
 * Explanation:
 * - 22 is >= (12, 3, 0, 6).
 * - 12 is >= (3, 0, 6).
 * - 6 is the rightmost element.
 * * Constraints:
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * * ---
 * * ### Conceptual Visualization (Right-to-Left Scan)
 * * Since this is an Array/Traversal problem (not DP), we visualize the optimal
 * Right-to-Left scanning strategy rather than a recursion tree.
 * * Target Array: [10, 22, 12, 3, 0, 6]
 * * Direction of Scan: <-----------------------
 * * Step | Index | Value | Max So Far | Is Leader? | Current Output (Reversed)
 * -------------------------------------------------------------------------
 * 1   |   5   |   6   | -Infinity  | YES (6 > -inf)| [6]         (Max becomes 6)
 * 2   |   4   |   0   |     6      | NO  (0 < 6)   | [6]
 * 3   |   3   |   3   |     6      | NO  (3 < 6)   | [6]
 * 4   |   2   |  12   |     6      | YES (12 > 6)  | [6, 12]     (Max becomes 12)
 * 5   |   1   |  22   |    12      | YES (22 > 12) | [6, 12, 22] (Max becomes 22)
 * 6   |   0   |  10   |    22      | NO  (10 < 22) | [6, 12, 22]
 * * Final Action: Reverse the collected list to match the original left-to-right
 * relative ordering: [22, 12, 6].
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderInArray {

    /**
     * ### 2.2 Progressive Implementation Roadmap: Phase 1
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * Approach:
     * For every element at index `i`, we iterate through all subsequent elements
     * from `i + 1` to `n - 1`. If we find any element strictly greater than `arr[i]`,
     * then `arr[i]` is not a leader. If the inner loop finishes without finding
     * a greater element, `arr[i]` is a leader.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * This is a direct, literal translation of the problem statement. We pick an
     * element and manually verify the condition against every element to its right.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2)
     * In the worst case (e.g., an array sorted in descending order), the inner
     * loop runs (N-1) + (N-2) + ... + 1 times, resulting in a quadratic time complexity.
     * - Space Complexity: O(1) auxiliary space (excluding output array).
     * Heap Space: O(N) only to store the resulting list of leaders.
     * Auxiliary Stack Space: O(1) as there is no recursion.
     */
    public List<Integer> findLeadersBruteForce(int[] arr) {
        List<Integer> leaders = new ArrayList<>();
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            boolean isLeader = true;
            // Check all elements to the right of the current element
            for (int j = i + 1; j < n; j++) {
                if (arr[j] > arr[i]) {
                    isLeader = false;
                    break;
                }
            }
            // If no greater element was found, it's a leader
            if (isLeader) {
                leaders.add(arr[i]);
            }
        }
        return leaders;
    }

    /**
     * ### 2.2 Progressive Implementation Roadmap: Phase 2
     * Phase 2: Alternative Approach (Optimal Right-to-Left Scan) - The "Perfect it" stage.
     * Approach:
     * We iterate through the array starting from the last element down to the first.
     * We maintain a variable `maxFromRight` to track the highest value seen so far.
     * If the current element is greater than or equal to `maxFromRight`, it is a
     * leader. We add it to our list, update `maxFromRight`, and finally reverse
     * the list to restore original ordering.
     * * ### 3. In-Code Technical Analysis
     * Detailed Intuition:
     * The brute force approach does highly redundant work by repeatedly scanning
     * the right side of the array. By scanning backwards, we only process the
     * "right side" once! We compress all the future knowledge of the right side
     * into a single variable (`maxFromRight`). This transforms a nested loop O(N^2)
     * bottleneck into a blazing fast single linear pass.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * We traverse the array exactly once from right to left taking O(N) time.
     * Reversing the list at the end takes O(L) time where L is the number of
     * leaders (L <= N). Overall Time: O(N).
     * - Space Complexity: O(1) auxiliary space (excluding output array).
     * Heap Space: O(N) for the output list (worst case: descending array).
     * Auxiliary Stack Space: O(1) since it's an iterative approach.
     */
    public List<Integer> findLeadersOptimal(int[] arr) {
        List<Integer> leaders = new ArrayList<>();
        if (arr == null || arr.length == 0) return leaders;

        int n = arr.length;
        int maxFromRight = Integer.MIN_VALUE;

        // Scan from right to left
        for (int i = n - 1; i >= 0; i--) {
            // If current element is >= max seen so far from the right
            if (arr[i] >= maxFromRight) {
                leaders.add(arr[i]);
                maxFromRight = arr[i]; // Update the new maximum
            }
        }

        // The list is constructed in reverse order, so we reverse it back
        Collections.reverse(leaders);
        return leaders;
    }

    /**
     * ### 4. Testing Suite
     * A robust main method to verify both approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        LeaderInArray solution = new LeaderInArray();

        // Test Cases including edge cases (zeroes, negatives, all same, ascending, descending)
        int[][] testCases = {
                {4, 7, 1, 0},                  // Standard Case 1
                {10, 22, 12, 3, 0, 6},         // Standard Case 2
                {5, 4, 3, 2, 1},               // Edge Case: Descending (All are leaders)
                {1, 2, 3, 4, 5},               // Edge Case: Ascending (Only last is leader)
                {7, 7, 7, 7},                  // Edge Case: All duplicates (All are leaders)
                {0, 0, 0},                     // Edge Case: All zeroes
                {-1, -2, -3, -4},              // Edge Case: All negatives (Descending)
                {42}                           // Edge Case: Single element
        };

        System.out.println("=========================================================");
        System.out.println("Executing Leaders in an Array Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];

            // Format input array for printing
            StringBuilder sb = new StringBuilder("[");
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[j]).append(j == arr.length - 1 ? "" : ", ");
            }
            sb.append("]");

            System.out.println("Test Case " + (i + 1) + ": Input = " + sb.toString());

            // Test Brute Force
            long start1 = System.nanoTime();
            List<Integer> res1 = solution.findLeadersBruteForce(arr);
            long end1 = System.nanoTime();

            // Test Optimal Approach
            long start2 = System.nanoTime();
            List<Integer> res2 = solution.findLeadersOptimal(arr);
            long end2 = System.nanoTime();

            System.out.println("  [Brute Force] Output: " + res1 + " | Time: " + (end1 - start1) + " ns");
            System.out.println("  [Optimal]     Output: " + res2 + " | Time: " + (end2 - start2) + " ns");

            // Verification
            boolean isValid = res1.equals(res2);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------------------");
        }
    }
}

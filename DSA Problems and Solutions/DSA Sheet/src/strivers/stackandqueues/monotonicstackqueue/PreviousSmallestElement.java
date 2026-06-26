package strivers.stackandqueues.monotonicstackqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * ============================================================================
 * 1. Header & Problem Context
 * ============================================================================
 * Problem Name: Previous Smallest Element
 * * Formal Problem Statement:
 * Given an array of integers 'arr', find the previous strictly smaller element
 * for every element in the array. The previous smaller element for an element
 * arr[i] is the first element arr[j] such that j < i and arr[j] < arr[i].
 * If no such element exists, return -1 for that position.
 * * Constraints:
 * - 1 <= arr.length <= 10^5
 * - 0 <= arr[i] <= 10^9
 * * Example 1:
 * Input: arr = [4, 5, 2, 10, 8]
 * Output: [-1, 4, -1, 2, 2]
 * Explanation:
 * - 4: No element to the left, so -1.
 * - 5: 4 is to the left and smaller, so 4.
 * - 2: No element to the left is smaller, so -1.
 * - 10: 2 is to the left and smaller, so 2.
 * - 8: 2 is the closest smaller element to the left, so 2.
 * * Example 2:
 * Input: arr = [3, 2, 1]
 * Output: [-1, -1, -1]
 * Explanation: The array is strictly decreasing, so no smaller elements exist to the left.
 * ============================================================================
 */
public class PreviousSmallestElement {

    /**
     * ========================================================================
     * Phase 1: Optimal Approach (Monotonic Stack) - The "Best" Stage
     * ========================================================================
     * Detailed Intuition:
     * We want to find the closest smaller element to the left. As we iterate
     * from left to right, we can maintain a stack of elements. If the current
     * element is smaller than or equal to the element at the top of the stack,
     * the top element is useless for any future elements (because the current
     * element is both smaller and closer to them). Therefore, we can safely
     * pop all elements from the stack that are >= the current element.
     * What remains at the top of the stack is our previous smallest element.
     * This creates a strictly increasing monotonic stack.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the array. Each element
     * is pushed to the stack exactly once and popped at most once.
     * - Space Complexity: O(N) auxiliary space for the Stack. No extra heap
     * space is used beyond the result array.
     * ========================================================================
     */
    public int[] findPreviousSmallestOptimal(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];

        // We use ArrayDeque as it is faster and more efficient than java.util.Stack
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Remove all elements from stack that are greater than or equal to current.
            // They can never be the "previous smallest" for any future element
            // because arr[i] is smaller and will be closer to future elements.
            while (!stack.isEmpty() && stack.peek() >= arr[i]) {
                stack.pop();
            }

            // If stack is empty, there is no smaller element to the left
            if (stack.isEmpty()) {
                result[i] = -1;
            } else {
                // The top of the stack is strictly smaller than arr[i]
                result[i] = stack.peek();
            }

            // Push current element to the stack to be a candidate for future elements
            stack.push(arr[i]);
        }

        return result;
    }

    /**
     * ========================================================================
     * Phase 2: Brute Force Approach - The "Think it" Stage
     * ========================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to simulate the exact
     * definition of the problem. For every element at index 'i', we look back
     * at every element at index 'j' (where j goes from i-1 down to 0). The
     * very first element we find that is strictly smaller than arr[i] is our answer.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) in the worst case (e.g., when the array is
     * strictly decreasing or all elements are identical), as the inner loop
     * will run 'i' times for every element.
     * - Space Complexity: O(1) auxiliary space. We only use primitive variables
     * for loop counters. (Excluding the O(N) space for the result array).
     * ========================================================================
     */
    public int[] findPreviousSmallestBruteForce(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = -1; // Default assumption

            // Scan backwards to find the immediate strictly smaller element
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    result[i] = arr[j];
                    break; // Found the closest one, break early
                }
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        PreviousSmallestElement solver = new PreviousSmallestElement();

        // Test Cases
        int[][] testCases = {
                {4, 5, 2, 10, 8}, // Standard mixed case
                {3, 2, 1},        // Strictly decreasing
                {1, 2, 3, 4},     // Strictly increasing
                {5, 5, 5, 5},     // All identical (zero-value edge case equivalent)
                {10},             // Single element
                {}                // Empty array
        };

        // Execute tests using Java 8 Streams for clean processing
        Arrays.stream(testCases).forEach(testCase -> {
            System.out.println("Input Array: " + Arrays.toString(testCase));

            long startTimeBrute = System.nanoTime();
            int[] bruteResult = solver.findPreviousSmallestBruteForce(testCase);
            long endTimeBrute = System.nanoTime();

            long startTimeOpt = System.nanoTime();
            int[] optimalResult = solver.findPreviousSmallestOptimal(testCase);
            long endTimeOpt = System.nanoTime();

            System.out.println("Brute Force Output : " + Arrays.toString(bruteResult) +
                    " (Time: " + (endTimeBrute - startTimeBrute) + " ns)");
            System.out.println("Optimal Stack Output: " + Arrays.toString(optimalResult) +
                    " (Time: " + (endTimeOpt - startTimeOpt) + " ns)");

            // Verification step
            boolean isMatch = Arrays.equals(bruteResult, optimalResult);
            System.out.println("Outputs Match      : " + (isMatch ? "✅ YES" : "❌ NO"));
            System.out.println("-".repeat(50));
        });
    }
}
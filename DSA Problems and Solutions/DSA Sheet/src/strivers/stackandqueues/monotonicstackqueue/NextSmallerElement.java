package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * Next Smaller Element
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given an array, find the Next Smaller Element (NSE) for every element.
 * The Next Smaller Element for an element x is the first smaller element on
 * the right side of x in the array. Elements for which no smaller element
 * exists, consider the next smaller element as -1.
 *
 * Example 1:
 * Input: arr = [4, 8, 5, 2, 25]
 * Output: [2, 5, 2, -1, -1]
 * Explanation:
 * - 4 -> Next smaller is 2
 * - 8 -> Next smaller is 5
 * - 5 -> Next smaller is 2
 * - 2 -> No smaller element to the right (-1)
 * - 25 -> No smaller element to the right (-1)
 *
 * Example 2:
 * Input: arr = [13, 7, 6, 12]
 * Output: [7, 6, -1, -1]
 * Explanation:
 * - 13 -> Next smaller is 7
 * - 7 -> Next smaller is 6
 * - 6 -> No smaller element to the right (-1)
 * - 12 -> No smaller element to the right (-1)
 *
 * CONSTRAINTS:
 * 1 <= arr.length <= 10^5
 * 0 <= arr[i] <= 10^9
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Monotonic Increasing Stack (Right-to-Left)
 * Phase 2: Brute Force Approach - Nested Loops
 * Phase 3: Alternative Approach - Monotonic Stack storing Indices (Left-to-Right)
 * ============================================================================
 */
public class NextSmallerElement {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Increasing Stack)
     * ============================================================================
     * Detailed Intuition:
     * To find the Next Smaller Element in O(N) time, we use a Monotonic Increasing
     * Stack and iterate from right to left.
     * 1. If we are at the current element and the stack has elements, we check the top.
     * 2. If the stack's top is greater than or equal to our current element, it is
     * "obstructed" by our current element. Anyone looking from the left will see
     * our current (smaller) element before they see the larger one on the stack.
     * Therefore, we pop those larger/equal elements.
     * 3. Once popped, the top of the stack is guaranteed to be the strictly smaller
     * element to the right (our NSE). If the stack is empty, there is no NSE.
     * 4. Push the current element onto the stack to act as a potential NSE for
     * elements further to the left.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the array once. Every element is
     * pushed onto the stack exactly once and popped at most once. Amortized O(1) per element.
     * - Space Complexity: O(N) auxiliary heap space for the ArrayDeque to store up to
     * N elements (in the worst case of a strictly increasing array).
     * ============================================================================
     */
    public int[] nextSmallerElementOptimal(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        // Traverse the array from right to left
        for (int i = n - 1; i >= 0; i--) {
            int current = arr[i];

            // Enforce Increasing Monotonicity:
            // Pop elements that are greater than or equal to the current element
            while (!stack.isEmpty() && stack.peek() >= current) {
                stack.pop();
            }

            // If stack is empty, no smaller element exists to the right
            ans[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current element to be evaluated by numbers to its left
            stack.push(current);
        }

        return ans;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Loops) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * We directly simulate the definition. For every element at index i, we iterate
     * with a nested loop starting from i + 1. We scan linearly to the right and
     * stop at the first element that is strictly smaller than arr[i].
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). In the worst case (a strictly increasing array),
     * the inner loop will traverse the rest of the array without ever breaking,
     * resulting in the sum of the first N integers operations.
     * - Space Complexity: O(1) auxiliary space (excluding the output array).
     * ============================================================================
     */
    public int[] nextSmallerElementBruteForce(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];

        Arrays.fill(ans, -1);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[i]) {
                    ans[i] = arr[j];
                    break;
                }
            }
        }

        return ans;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Monotonic Stack - Left-to-Right)
     * ============================================================================
     * Detailed Intuition:
     * Instead of iterating right-to-left and holding values, we can iterate
     * left-to-right and store INDICES in our stack.
     * - The stack holds the indices of elements for which we haven't found the NSE yet.
     * - As we traverse left-to-right, if the current element is strictly smaller
     * than the element at the index stored on top of the stack, we have successfully
     * found the NSE for that suspended index. We pop it and record the answer.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Each index is pushed and popped at most once.
     * - Space Complexity: O(N) auxiliary heap space for storing indices.
     * ============================================================================
     */
    public int[] nextSmallerElementLeftToRight(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int current = arr[i];

            // Resolve the suspended indices if current is strictly smaller
            while (!stack.isEmpty() && arr[stack.peek()] > current) {
                int resolvedIndex = stack.pop();
                ans[resolvedIndex] = current;
            }

            stack.push(i);
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
        NextSmallerElement solver = new NextSmallerElement();

        // Lambda to cleanly format output using Java 8 Streams
        java.util.function.Function<int[], String> format =
                array -> Arrays.stream(array)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Input (Mixed values) ===");
        int[] arr1 = {4, 8, 5, 2, 25};
        System.out.println("Input:          " + format.apply(arr1));
        System.out.println("Optimal:        " + format.apply(solver.nextSmallerElementOptimal(arr1)));
        System.out.println("Brute Force:    " + format.apply(solver.nextSmallerElementBruteForce(arr1)));
        System.out.println("Left-to-Right:  " + format.apply(solver.nextSmallerElementLeftToRight(arr1)));

        System.out.println("\n=== Test Case 2: Strictly Decreasing Array ===");
        // Every element has a smaller element immediately to its right
        int[] arr2 = {5, 4, 3, 2, 1};
        System.out.println("Input:          " + format.apply(arr2));
        System.out.println("Optimal:        " + format.apply(solver.nextSmallerElementOptimal(arr2)));

        System.out.println("\n=== Test Case 3: Strictly Increasing Array (Worst Case) ===");
        // No element has a smaller element to its right
        int[] arr3 = {1, 2, 3, 4, 5};
        System.out.println("Input:          " + format.apply(arr3));
        System.out.println("Optimal:        " + format.apply(solver.nextSmallerElementOptimal(arr3)));

        System.out.println("\n=== Test Case 4: Identical Elements ===");
        // Next strictly smaller implies identical elements shouldn't pop each other
        int[] arr4 = {3, 3, 3, 3};
        System.out.println("Input:          " + format.apply(arr4));
        System.out.println("Optimal:        " + format.apply(solver.nextSmallerElementOptimal(arr4)));

        System.out.println("\n=== Test Case 5: Single Element ===");
        int[] arr5 = {42};
        System.out.println("Input:          " + format.apply(arr5));
        System.out.println("Optimal:        " + format.apply(solver.nextSmallerElementOptimal(arr5)));
    }
}
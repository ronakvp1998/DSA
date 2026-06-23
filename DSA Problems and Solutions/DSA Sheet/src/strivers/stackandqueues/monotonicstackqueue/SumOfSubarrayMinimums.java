package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 907. Sum of Subarray Minimums
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given an array of integers arr, find the sum of min(b), where b ranges over
 * every (contiguous) subarray of arr. Since the answer may be large, return the
 * answer modulo 10^9 + 7.
 *
 * Example 1:
 * Input: arr = [3,1,2,4]
 * Output: 17
 * Explanation:
 * Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
 * Sum is 17.
 *
 * Example 2:
 * Input: arr = [11,81,94,43,3]
 * Output: 444
 *
 * CONSTRAINTS:
 * 1 <= arr.length <= 3 * 10^4
 * 1 <= arr[i] <= 3 * 10^4
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Contribution Technique w/ Monotonic Stack
 * Phase 2: Brute Force Approach - Nested Loops Tracking Running Minimum
 * Phase 3: Alternative Approach - Dynamic Programming via Monotonic Stack
 * ============================================================================
 */
public class SumOfSubarrayMinimums {

    private static final int MOD = 1000000007;

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Contribution Technique w/ Monotonic Stack)
     * ============================================================================
     * Detailed Intuition:
     * Instead of generating every subarray and finding its minimum, we invert the
     * question: "For a given element `arr[i]`, in how many subarrays is it the
     * absolute minimum?"
     * * To find this, we look for the Previous Smaller Element (PSE) to the left
     * at index `left_idx`, and the Next Smaller Element (NSE) to the right at
     * index `right_idx`.
     * * The number of contiguous subarrays where `arr[i]` is the minimum is exactly:
     * count = (i - left_idx) * (right_idx - i)
     * Its total contribution to the final sum is: arr[i] * count.
     * * CRITICAL EDGE CASE (Duplicate Elements):
     * If the array has duplicates (e.g., [7, 1, 7, 1, 7]), calculating purely
     * strictly smaller elements on both sides will cause us to double-count
     * subarrays containing both 1s. To solve this uniquely, we enforce an asymmetry:
     * - Left side looks for STRICTLY smaller elements: `arr[stack.peek()] < arr[i]`
     * - Right side looks for LESS THAN OR EQUAL elements: `arr[stack.peek()] <= arr[i]`
     * This makes the leftmost minimum the "boss" of that subarray.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We make two separate linear passes to build the PSE
     * and NSE arrays. Every element is pushed and popped from the stack at most once.
     * - Space Complexity: O(N) auxiliary heap space for the Deque and two state arrays.
     * ============================================================================
     */
    public int sumSubarrayMinsOptimal(int[] arr) {
        int n = arr.length;

        int[] pse = new int[n]; // Previous Smaller Element indices
        int[] nse = new int[n]; // Next Smaller (or Equal) Element indices

        Deque<Integer> stack = new ArrayDeque<>();

        // 1. Find PSE (Strictly smaller)
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            pse[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // 2. Find NSE (Smaller OR Equal to handle duplicate subsets uniquely)
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            nse[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // 3. Calculate total contribution
        long totalSum = 0;
        for (int i = 0; i < n; i++) {
            long leftDist = i - pse[i];
            long rightDist = nse[i] - i;
            long subarraysCount = (leftDist * rightDist) % MOD;
            long contribution = (subarraysCount * arr[i]) % MOD;
            totalSum = (totalSum + contribution) % MOD;
        }

        return (int) totalSum;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Running Minimum) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The simplest way to evaluate subarrays is to define a starting point `i` and
     * expand a window to the right `j`. As we expand, the minimum of the subarray
     * from `i` to `j` is just the minimum between the new element `arr[j]` and the
     * running minimum of the subarray from `i` to `j-1`. We add this running
     * minimum to our total sum on every step.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). Two nested loops evaluating all N*(N+1)/2 subarrays.
     * Will result in Time Limit Exceeded (TLE) for N = 30,000.
     * - Space Complexity: O(1) auxiliary space.
     * ============================================================================
     */
    public int sumSubarrayMinsBruteForce(int[] arr) {
        long totalSum = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int currentMin = arr[i];
            for (int j = i; j < n; j++) {
                currentMin = Math.min(currentMin, arr[j]);
                totalSum = (totalSum + currentMin) % MOD;
            }
        }

        return (int) totalSum;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        SumOfSubarrayMinimums solver = new SumOfSubarrayMinimums();

        // Formatter using Java 8 Streams for clean terminal output
        java.util.function.Function<int[], String> format =
                array -> Arrays.stream(array)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("=== Test Case 1: Standard Input (Example 1) ===");
        int[] arr1 = {3, 1, 2, 4};
        System.out.println("Input:        " + format.apply(arr1));
        System.out.println("Optimal:      " + solver.sumSubarrayMinsOptimal(arr1) + " (Expected: 17)");
        System.out.println("Brute Force:  " + solver.sumSubarrayMinsBruteForce(arr1));

        System.out.println("\n=== Test Case 2: Larger Values (Example 2) ===");
        int[] arr2 = {11, 81, 94, 43, 3};
        System.out.println("Input:        " + format.apply(arr2));
        System.out.println("Optimal:      " + solver.sumSubarrayMinsOptimal(arr2) + " (Expected: 444)");

        System.out.println("\n=== Test Case 3: Duplicate Elements (Edge Case) ===");
        int[] arr3 = {7, 1, 7, 1, 7};
        System.out.println("Input:        " + format.apply(arr3));
        System.out.println("Optimal:      " + solver.sumSubarrayMinsOptimal(arr3));
        System.out.println("Brute Force:  " + solver.sumSubarrayMinsBruteForce(arr3));

        System.out.println("\n=== Test Case 4: Strictly Increasing Array ===");
        int[] arr4 = {1, 2, 3, 4, 5};
        System.out.println("Input:        " + format.apply(arr4));
        System.out.println("Optimal:      " + solver.sumSubarrayMinsOptimal(arr4));

        System.out.println("\n=== Test Case 5: Strictly Decreasing Array ===");
        int[] arr5 = {5, 4, 3, 2, 1};
        System.out.println("Input:        " + format.apply(arr5));
        System.out.println("Optimal:      " + solver.sumSubarrayMinsOptimal(arr5));
    }
}
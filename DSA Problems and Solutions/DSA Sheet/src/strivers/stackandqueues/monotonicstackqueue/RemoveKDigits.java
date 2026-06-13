package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 402. Remove K Digits
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given string num representing a non-negative integer num, and an integer k,
 * return the smallest possible integer after removing k digits from num.
 *
 * Example 1:
 * Input: num = "1432219", k = 3
 * Output: "1219"
 * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219
 * which is the smallest.
 *
 * Example 2:
 * Input: num = "10200", k = 1
 * Output: "200"
 * Explanation: Remove the leading 1 and the number is 200. Note that the output
 * must not contain leading zeroes.
 *
 * Example 3:
 * Input: num = "10", k = 2
 * Output: "0"
 * Explanation: Remove all the digits from the number and it is left with nothing
 * which is 0.
 *
 * CONSTRAINTS:
 * 1 <= k <= num.length <= 10^5
 * num consists of only digits.
 * num does not have any leading zeros except for the zero itself.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Monotonic Stack via StringBuilder (O(N) Time)
 * Phase 2: Brute Force Approach - Iterative Peak Removal (O(N * K) Time)
 * Phase 3: Alternative Approach - Standard Deque Monotonic Stack (O(N) Time)
 * ============================================================================
 */
public class RemoveKDigits {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Stack via StringBuilder)
     * ============================================================================
     * Detailed Intuition:
     * To obtain the smallest number, the most significant digits (leftmost) dictate
     * the overall magnitude. A larger digit placed before a smaller digit should be
     * deleted. For example, in "143", the '4' is followed by '3'. Removing '4'
     * leaves "13", which is smaller than "14".
     * * This logic directly maps to a Monotonic Increasing Stack. We push digits
     * onto the stack one by one. If the current digit is strictly smaller than the
     * top of the stack, we pop the stack (essentially deleting the larger preceding
     * digit) and decrement k.
     * * * Optimization: Instead of using `java.util.Stack` or `ArrayDeque`, we use
     * a `StringBuilder` to simulate the stack. This avoids the overhead of autoboxing
     * characters and allows us to instantly strip leading zeros and convert the
     * final result to a String without iterating over a Deque.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string `num`. We iterate
     * through the string once, and each character is appended to and deleted from
     * the StringBuilder at most once.
     * - Space Complexity: O(N) auxiliary space for the StringBuilder stack, which
     * in the worst case stores all N digits.
     * ============================================================================
     */
    public String removeKdigitsOptimal(String num, int k) {
        if (k == num.length()) return "0";

        StringBuilder stack = new StringBuilder();

        for (char c : num.toCharArray()) {
            // Enforce strictly increasing monotonic property
            while (k > 0 && stack.length() > 0 && stack.charAt(stack.length() - 1) > c) {
                stack.deleteCharAt(stack.length() - 1);
                k--;
            }
            stack.append(c);
        }

        // If k is still greater than 0, it means the remaining digits are already
        // monotonically increasing (e.g., "1234"). We must chop off from the end.
        while (k > 0 && stack.length() > 0) {
            stack.deleteCharAt(stack.length() - 1);
            k--;
        }

        // Strip leading zeros
        int start = 0;
        while (start < stack.length() && stack.charAt(start) == '0') {
            start++;
        }

        String result = stack.substring(start);
        return result.isEmpty() ? "0" : result;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Iterative Peak Removal) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If we don't know the monotonic stack pattern, we can simulate the greedy choice
     * directly. We want to remove `k` digits. In each of the `k` steps, we scan from
     * left to right to find the first "peak" — a digit that is strictly greater than
     * the digit immediately following it. We delete that peak. If no peak exists
     * (the digits are in ascending order), we delete the last digit. We repeat this
     * process `k` times.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * K). In the worst case, finding the peak takes O(N)
     * time, and deleting a character from a StringBuilder takes O(N) time because
     * subsequent characters must be shifted. We do this K times.
     * - Space Complexity: O(N) auxiliary space to hold the mutable string.
     * ============================================================================
     */
    public String removeKdigitsBruteForce(String num, int k) {
        if (k == num.length()) return "0";

        StringBuilder sb = new StringBuilder(num);

        while (k > 0) {
            int i = 0;
            // Find the first peak (where a digit is larger than its right neighbor)
            while (i < sb.length() - 1 && sb.charAt(i) <= sb.charAt(i + 1)) {
                i++;
            }
            // Remove the peak (or the last character if no peak was found)
            sb.deleteCharAt(i);
            k--;
        }

        // Strip leading zeros
        while (sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Standard Deque Monotonic Stack)
     * ============================================================================
     * Detailed Intuition:
     * This is the exact same algorithmic logic as the Optimal Phase 1, but explicitly
     * uses the `ArrayDeque` data structure to represent the stack. This demonstrates
     * fundamental computer science knowledge before making specific language-based
     * optimizations.
     * * Note: After processing, reading from the Deque requires careful extraction
     * (`pollLast()`) to ensure the digits are reconstructed in the correct left-to-right
     * order, since standard popping would reverse the string.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) time to traverse the string and process stack operations.
     * - Space Complexity: O(N) auxiliary heap space for the Deque.
     * ============================================================================
     */
    public String removeKdigitsAlternative(String num, int k) {
        if (k == num.length()) return "0";

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : num.toCharArray()) {
            while (k > 0 && !stack.isEmpty() && stack.peek() > c) {
                stack.pop();
                k--;
            }
            stack.push(c);
        }

        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        // Reconstruct string from the bottom of the stack to the top
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }

        int start = 0;
        while (start < sb.length() && sb.charAt(start) == '0') {
            start++;
        }

        String result = sb.substring(start);
        return result.isEmpty() ? "0" : result;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        RemoveKDigits solver = new RemoveKDigits();

        // Object Array to hold test cases: {num, k, expected}
        Object[][] testCases = {
                {"1432219", 3, "1219"},     // Standard removal
                {"10200", 1, "200"},        // Removal causing leading zeros
                {"10", 2, "0"},             // Removing all digits
                {"9", 1, "0"},              // Single digit removal
                {"112", 1, "11"},           // Monotonically increasing, remove from end
                {"12345", 2, "123"},        // Strictly increasing
                {"54321", 2, "321"},        // Strictly decreasing
                {"1002991", 3, "21"}        // Multiple zero strips
        };

        System.out.println("=== Remove K Digits Testing Suite ===\n");

        for (int i = 0; i < testCases.length; i++) {
            String num = (String) testCases[i][0];
            int k = (Integer) testCases[i][1];
            String expected = (String) testCases[i][2];

            String optRes = solver.removeKdigitsOptimal(num, k);
            String bfRes = solver.removeKdigitsBruteForce(num, k);
            String altRes = solver.removeKdigitsAlternative(num, k);

            // Validation using Java 8 Streams to ensure all methods align with expectation
            boolean allMatch = java.util.stream.Stream.of(optRes, bfRes, altRes)
                    .allMatch(res -> res.equals(expected));

            System.out.printf("Test Case %d: num = \"%s\", k = %d\n", i + 1, num, k);
            System.out.printf("  Expected:    %s\n", expected);
            System.out.printf("  Optimal:     %s\n", optRes);
            System.out.printf("  Brute Force: %s\n", bfRes);
            System.out.printf("  Alternative: %s\n", altRes);
            System.out.printf("  Status:      %s\n\n", allMatch ? "PASS \u2714" : "FAIL \u2718");
        }
    }
}
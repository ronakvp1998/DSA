package com.questions.strivers.string.hard;

/**
 * ============================================================================
 * 38. Count and Say
 * ============================================================================
 * The count-and-say sequence is a sequence of digit strings defined by the
 * recursive formula:
 * countAndSay(1) = "1"
 * countAndSay(n) is the run-length encoding of countAndSay(n - 1).
 * * Run-length encoding (RLE) is a string compression method that works by
 * replacing consecutive identical characters (repeated 2 or more times) with
 * the concatenation of the character and the number marking the count of the
 * characters (length of the run).
 * For example, to compress the string "3322251" we replace "33" with "23",
 * replace "222" with "32", replace "5" with "15" and replace "1" with "11".
 * Thus the compressed string becomes "23321511".
 * * Given a positive integer n, return the nth element of the count-and-say
 * sequence.
 * * Example 1:
 * Input: n = 4
 * Output: "1211"
 * Explanation:
 * countAndSay(1) = "1"
 * countAndSay(2) = RLE of "1" = "11"
 * countAndSay(3) = RLE of "11" = "21"
 * countAndSay(4) = RLE of "21" = "1211"
 * * Example 2:
 * Input: n = 1
 * Output: "1"
 * Explanation:
 * This is the base case.
 * * Constraints:
 * 1 <= n <= 30
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class CountAndSay {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Pure Recursion) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The problem is inherently defined using a recursive mathematical formula.
     * The most literal translation of the problem statement is to write a
     * recursive function. To find the answer for `n`, we first ask for the
     * answer for `n - 1`. Once the recursive call returns the previous string,
     * we traverse it, count consecutive identical characters, and build the
     * new Run-Length Encoded (RLE) string.
     * * CRITICAL JAVA KNOWLEDGE:
     * As a Senior Developer, you must never use `String result += ...` in a
     * loop like this. Because Java Strings are immutable, string concatenation
     * in a loop operates in O(K^2) time, where K is the string length. We MUST
     * use `StringBuilder` to achieve O(K) time for building the next sequence.
     * * Complexity Analysis:
     * - Time Complexity: $O(\sum_{i=1}^{N} L_i)$, where $L_i$ is the length of
     * the sequence at step `i`. The length of the string grows exponentially
     * (specifically, by a factor of roughly 1.303, known as Conway's constant).
     * So time complexity is heavily bound by the string length at $N$.
     * - Space Complexity: $O(N + L_N)$ Auxiliary Space. $O(N)$ space is used by
     * the recursive call stack since we go $N$ levels deep. The StringBuilder
     * and returned string consume $O(L_N)$ heap space, where $L_N$ is the
     * length of the final string.
     * ========================================================================
     */
    public static String countAndSayRecursive(int n) {
        // Base case defined by the problem
        if (n == 1) {
            return "1";
        }

        // Ask the recursion fairy for the previous sequence
        String prev = countAndSayRecursive(n - 1);

        // Apply Run-Length Encoding to the previous sequence
        return applyRLE(prev);
    }

    /**
     * ========================================================================
     * Phase 2: Optimal Iterative Approach - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * While the recursive approach perfectly maps to the problem definition,
     * it introduces unnecessary overhead in production systems via the call stack.
     * We can easily invert the recursive logic into a Bottom-Up Iterative
     * approach. We start at our base case ("1") and simply run a loop from
     * 2 up to `n`, applying the RLE algorithm to our `current` string and
     * overwriting it for the next iteration.
     * This demonstrates an understanding of how to protect applications from
     * StackOverflowErrors, even though the constraint here is small (N <= 30).
     * * Complexity Analysis:
     * - Time Complexity: $O(\sum_{i=1}^{N} L_i)$. The time complexity remains
     * the same as the recursive approach, as we still must generate every
     * intermediate string.
     * - Space Complexity: $O(L_N)$ Auxiliary Heap Space. We completely eliminate
     * the $O(N)$ recursion call stack. The memory is strictly bounded by the
     * size of the `StringBuilder` needed to hold the longest sequence.
     * ========================================================================
     */
    public static String countAndSayIterative(int n) {
        String current = "1";

        // Iteratively build the sequence up to n
        for (int i = 2; i <= n; i++) {
            current = applyRLE(current);
        }

        return current;
    }

    /**
     * Helper Method: Encapsulates the core Run-Length Encoding logic.
     * This keeps the main algorithms clean and adheres to the Single
     * Responsibility Principle (SRP).
     */
    private static String applyRLE(String s) {
        StringBuilder sb = new StringBuilder();
        int count = 1;

        // Traverse the string to count consecutive characters
        for (int i = 1; i < s.length(); i++) {
            // If the current character matches the previous one, increment count
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                // Mismatch found: append the count, then the character itself
                sb.append(count).append(s.charAt(i - 1));
                count = 1; // Reset counter for the new character
            }
        }

        // Flush the final character run into the builder
        sb.append(count).append(s.charAt(s.length() - 1));

        return sb.toString();
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        int[] testCases = {
                1,      // Edge Case: Minimum constraint (Base Case)
                2,      // Standard Case: "11"
                3,      // Standard Case: "21"
                4,      // Example 1: "1211"
                5,      // Standard Case: "111221"
                30      // Edge Case: Maximum constraint
        };

        System.out.println("Running test cases for 38. Count and Say...\n");

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];

            // For N=30, the string is quite long, so we truncate output for readability
            System.out.println("Test Case " + (i + 1) + ": n = " + n);

            String res1 = countAndSayRecursive(n);
            String res2 = countAndSayIterative(n);

            // Print logic with truncation for large outputs
            String displayRes1 = res1.length() > 50 ? res1.substring(0, 50) + "... (length: " + res1.length() + ")" : res1;
            String displayRes2 = res2.length() > 50 ? res2.substring(0, 50) + "... (length: " + res2.length() + ")" : res2;

            System.out.println("Phase 1 (Recursive) : " + displayRes1);
            System.out.println("Phase 2 (Iterative) : " + displayRes2);

            // Validation step
            if (res1.equals(res2)) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
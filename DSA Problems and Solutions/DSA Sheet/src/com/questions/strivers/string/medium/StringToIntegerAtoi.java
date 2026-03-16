package com.questions.strivers.string.medium;
/**
 * ============================================================================
 * 8. String to Integer (atoi)
 * ============================================================================
 * Implement the myAtoi(string s) function, which converts a string to a 32-bit
 * signed integer.
 * * The algorithm for myAtoi(string s) is as follows:
 * 1. Whitespace: Ignore any leading whitespace (" ").
 * 2. Signedness: Determine the sign by checking if the next character is '-'
 * or '+', assuming positivity if neither present.
 * 3. Conversion: Read the integer by skipping leading zeros until a non-digit
 * character is encountered or the end of the string is reached. If no digits
 * were read, then the result is 0.
 * 4. Rounding: If the integer is out of the 32-bit signed integer range
 * [-2^31, 2^31 - 1], then round the integer to remain in the range.
 * * Example 1:
 * Input: s = "42"
 * Output: 42
 * * Example 2:
 * Input: s = " -042"
 * Output: -42
 * * Example 3:
 * Input: s = "1337c0d3"
 * Output: 1337
 * * Example 4:
 * Input: s = "0-1"
 * Output: 0
 * * Example 5:
 * Input: s = "words and 987"
 * Output: 0
 * * Constraints:
 * - 0 <= s.length <= 200
 * - s consists of English letters (lower-case and upper-case), digits (0-9),
 * ' ', '+', '-', and '.'.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class StringToIntegerAtoi {

    /**
     * ========================================================================
     * Phase 1: Iterative Simulation - The "Think it" and "Build it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Unlike many string problems, `atoi` does not have a "brute force vs optimal"
     * algorithmic shift (like going from O(N^2) to O(N)). The challenge of `atoi`
     * is entirely about rigorous implementation and edge-case handling.
     * We must systematically implement the 4 rules exactly as stated:
     * 1. Move a pointer to skip spaces.
     * 2. Check for ONE optional sign character.
     * 3. Iterate through digits, building the number using `total = total * 10 + digit`.
     * 4. CRITICAL: Handle overflow *before* it happens. If we multiply `total` by 10
     * and add the digit, it might overflow Java's 32-bit integer limits.
     * We must check if `total` is already greater than `Integer.MAX_VALUE / 10`.
     * If it equals `Integer.MAX_VALUE / 10`, we must check if the incoming digit
     * will push it over the edge (digit > 7 for max, digit > 8 for min).
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. We iterate
     * through the string exactly once from left to right.
     * - Space Complexity: O(1) Auxiliary Heap Space. We only use primitive integer
     * variables for pointers and the accumulated total.
     * ========================================================================
     */
    public static int myAtoi(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int n = s.length();
        int i = 0;
        int sign = 1;
        int total = 0;

        // Rule 1: Skip leading whitespaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // Check if we reached the end just with spaces
        if (i == n) {
            return 0;
        }

        // Rule 2: Determine sign
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        // Rule 3 & 4: Convert and check overflow
        while (i < n) {
            char c = s.charAt(i);

            // Break if a non-digit character is encountered
            if (c < '0' || c > '9') {
                break;
            }

            int digit = c - '0';

            // Overflow/Underflow check
            // We check against Integer.MAX_VALUE / 10 which is 214748364.
            // If total > 214748364, then total * 10 will definitely overflow.
            // If total == 214748364, it will overflow if digit > 7
            // (since MAX_VALUE ends in 7: 2147483647).
            if (total > Integer.MAX_VALUE / 10 ||
                    (total == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                // If it overflows, return the boundary based on the sign
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            // Accumulate the total
            total = total * 10 + digit;
            i++;
        }

        return total * sign;
    }

    /**
     * ========================================================================
     * Phase 2: Alternative Approach (Using Long) - The "Cheat Code" stage.
     * ========================================================================
     * Detailed Intuition:
     * If the interviewer does not strictly forbid using larger data types
     * (some problems say "assume a 32-bit environment"), using a `long` to
     * accumulate the total makes the overflow logic drastically simpler.
     * You accumulate the result in a 64-bit `long`. After every digit addition,
     * you check if the `long` value has exceeded the boundaries of a 32-bit int.
     * If it has, you immediately return the boundary.
     * * Note: A strict interviewer will disallow this, as true 32-bit systems
     * cannot hold a 64-bit `long` in a single register, making the Phase 1
     * math-based check the true "Senior" answer.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * - Space Complexity: O(1)
     * ========================================================================
     */
    public static int myAtoiWithLong(String s) {
        if (s == null || s.length() == 0) return 0;

        int n = s.length(), i = 0, sign = 1;
        long total = 0; // Using long for easier overflow checking

        while (i < n && s.charAt(i) == ' ') i++;
        if (i == n) return 0;

        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }

        while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            total = total * 10 + (s.charAt(i) - '0');

            // Check overflow easily against the long value
            if (sign == 1 && total > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (sign == -1 && -total < Integer.MIN_VALUE) return Integer.MIN_VALUE;

            i++;
        }

        return (int) (total * sign);
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "42",                   // Example 1: Standard positive
                "   -042",              // Example 2: Leading spaces, negative, leading zeros
                "1337c0d3",             // Example 3: Stops at character
                "0-1",                  // Example 4: Stops at internal minus
                "words and 987",        // Example 5: Starts with characters
                "2147483646",           // Close to MAX
                "2147483648",           // Over MAX (Overflow positive)
                "-2147483649",          // Under MIN (Overflow negative)
                "  +  413",             // Space between sign and number (should return 0)
                "",                     // Empty string
                " "                     // Only spaces
        };

        System.out.println("Running test cases for 8. String to Integer (atoi)...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            int res1 = myAtoi(input);
            int res2 = myAtoiWithLong(input);

            System.out.println("Phase 1 (Math Overflow) : " + res1);
            System.out.println("Phase 2 (Long Overflow) : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
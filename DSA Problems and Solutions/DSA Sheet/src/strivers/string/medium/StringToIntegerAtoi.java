package strivers.string.medium;
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

            int res2 = myAtoiWithLong(input);

            System.out.println("Phase 2 (Long Overflow) : " + res2);

        }
    }
}
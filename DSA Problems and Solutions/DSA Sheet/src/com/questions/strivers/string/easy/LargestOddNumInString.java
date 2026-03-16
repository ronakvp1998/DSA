package com.questions.strivers.string.easy;

/**
 * ============================================================================
 * 1903. Largest Odd Number in String
 * ============================================================================
 * You are given a string num, representing a large integer. Return the
 * largest-valued odd integer (as a string) that is a non-empty substring
 * of num, or an empty string "" if no odd integer exists.
 * * A substring is a contiguous sequence of characters within a string.
 * * Example 1:
 * Input: num = "52"
 * Output: "5"
 * Explanation: The only non-empty substrings are "5", "2", and "52". "5" is
 * the only odd number.
 * * Example 2:
 * Input: num = "4206"
 * Output: ""
 * Explanation: There are no odd numbers in "4206".
 * * Example 3:
 * Input: num = "35427"
 * Output: "35427"
 * Explanation: "35427" is already an odd number.
 * * Constraints:
 * - 1 <= num.length <= 10^5
 * - num only consists of digits and does not contain any leading zeros.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class LargestOddNumInString {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most intuitive way to solve this is to generate all possible
     * substrings of the given string `num`. For each substring, we check if it
     * represents an odd number (i.e., its last character is an odd digit).
     * If it is, we compare its numerical value with our current maximum.
     * * Note: Because `num` can be up to 10^5 characters long, converting these
     * substrings to standard integer types (like `Long` or `BigInteger`) will
     * either overflow or be computationally disastrous. We would have to compare
     * strings based on length, then lexicographically.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) to generate substrings, plus O(N) for string
     * creation and comparison, leading to O(N^3) overall. This will hit a
     * Time Limit Exceeded (TLE) error for N = 10^5.
     * - Space Complexity: O(N) auxiliary heap space for storing the maximum
     * substring found so far.
     * ========================================================================
     */
    public static String largestOddNumberBruteForce(String num) {
        String maxOddStr = "";
        int n = num.length();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String sub = num.substring(i, j + 1);
                // Check if the last digit is odd
                char lastChar = sub.charAt(sub.length() - 1);
                if ((lastChar - '0') % 2 != 0) {
                    // Compare values: longer string is larger.
                    // If lengths are equal, use lexicographical comparison.
                    if (sub.length() > maxOddStr.length() ||
                            (sub.length() == maxOddStr.length() && sub.compareTo(maxOddStr) > 0)) {
                        maxOddStr = sub;
                    }
                }
            }
        }
        return maxOddStr;
    }

    /**
     * ========================================================================
     * Phase 2: Greedy Approach (Right-to-Left Traversal) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * We don't need to generate all substrings. A fundamental property of
     * numbers is that their parity (odd/even) is determined entirely by their
     * rightmost digit.
     * * To maximize the numerical value of a string representation of a number,
     * we must maximize its length (more digits = larger magnitude). Furthermore,
     * keeping the most significant digits (the leftmost digits) keeps the number
     * as large as possible.
     * * Therefore, the largest odd number will always be the longest prefix of `num`
     * that ends in an odd digit. We can simply iterate from the end of the string
     * to the beginning. The first odd digit we encounter marks the end of our
     * optimal substring. We then take everything from index 0 up to that digit.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the string. In the worst
     * case (no odd digits), we traverse the string exactly once. The
     * `substring()` operation also takes O(N) time. Overall Time is O(N).
     * - Space Complexity: O(1) Auxiliary Stack Space, but O(N) Heap Space to
     * create and return the resulting substring (Strings are immutable in Java).
     * ========================================================================
     */
    public static String largestOddNumberOptimal(String num) {
        // Traverse the string from right to left
        for (int i = num.length() - 1; i >= 0; i--) {
            char c = num.charAt(i);

            // Check if the character is an odd digit.
            // ASCII trick: '1' is 49, '3' is 51, etc.
            // So (c % 2 != 0) effectively checks for odd digits directly.
            if (c % 2 != 0) {
                // Return the substring from the beginning up to the current odd digit.
                // i + 1 is used because the end index in substring() is exclusive.
                return num.substring(0, i + 1);
            }
        }

        // If we finish the loop without returning, there are no odd digits.
        return "";
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "52",          // Standard case: odd digit at start
                "4206",        // Edge case: no odd digits
                "35427",       // Standard case: odd digit at end
                "11111",       // Edge case: all odd digits
                "234567890",   // Large increasing number
                "8",           // Single even digit
                "9"            // Single odd digit
        };

        System.out.println("Running test cases...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            // Note: Brute force is only run for small strings to avoid TLE in real tests,
            // but it's safe to run on these small sample lengths.
            String res1 = largestOddNumberBruteForce(input);
            String res2 = largestOddNumberOptimal(input);

            System.out.println("Phase 1 (Brute Force): \"" + res1 + "\"");
            System.out.println("Phase 2 (Optimal)    : \"" + res2 + "\"");

            // Validation
            if (res1.equals(res2)) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗\n");
            }
        }
    }
}
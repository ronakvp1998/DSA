package com.questions.strivers.string.easy;
/**
 * ============================================================================
 * 796. Rotate String
 * ============================================================================
 * Given two strings s and goal, return true if and only if s can become goal
 * after some number of shifts on s.
 * * A shift on s consists of moving the leftmost character of s to the rightmost
 * position.
 * * For example, if s = "abcde", then it will be "bcdea" after one shift.
 * * Example 1:
 * Input: s = "abcde", goal = "cdeab"
 * Output: true
 * * Example 2:
 * Input: s = "abcde", goal = "abced"
 * Output: false
 * * Constraints:
 * - 1 <= s.length, goal.length <= 100
 * - s and goal consist of lowercase English letters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class RotateString {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Simulation) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most literal translation of the problem statement is to physically
     * simulate the rotation process. We can loop through the string, removing
     * the first character and appending it to the back, and check if the
     * newly formed string equals the `goal` string.
     * Because there are exactly N possible rotations (where N is the length
     * of the string), we only need to simulate N shifts. If we haven't found a
     * match after N shifts, it's impossible.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2), where N is the length of string `s`. The loop
     * runs N times, and inside the loop, we perform string concatenation and
     * an `equals()` check, both of which take O(N) time.
     * - Space Complexity: O(N) Auxiliary Heap Space. In Java, strings are
     * immutable, so every `substring` and concatenation operation inside the
     * loop creates a new String object on the heap.
     * ========================================================================
     */
    public static boolean rotateStringBruteForce(String s, String goal) {
        // If lengths don't match, they can never be rotations of each other
        if (s.length() != goal.length()) {
            return false;
        }

        // Edge case: they are exactly the same from the start (0 shifts)
        if (s.equals(goal)) {
            return true;
        }

        int n = s.length();
        // Try all possible rotations
        for (int i = 1; i < n; i++) {
            // Split the string at index i and swap the halves
            String rotated = s.substring(i) + s.substring(0, i);

            // Check if this rotation matches the goal
            if (rotated.equals(goal)) {
                return true;
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * Phase 2: String Concatenation Trick - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * As a Senior Developer, we want to look for mathematical or structural
     * invariants. Here is a beautiful observation:
     * If you take a string `s` and append it to itself (`s + s`), the resulting
     * doubled string will naturally contain *every single possible rotation* of `s`
     * as a contiguous substring.
     * * Example: s = "abcde"
     * doubled = "abcde" + "abcde" = "abcdeabcde"
     * * Let's look inside "abcdeabcde":
     * - Shift 1 ("bcdea") is inside: a[bcdea]bcde
     * - Shift 2 ("cdeab") is inside: ab[cdeab]cde
     * - Shift 3 ("deabc") is inside: abc[deabc]de
     * * Therefore, if `goal` is a valid rotation, and `s.length == goal.length`,
     * then `goal` MUST be a substring of `s + s`. This reduces the entire problem
     * to a single `contains()` check!
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of string `s`. Concatenating
     * `s + s` takes O(N). The `contains()` method (which calls `indexOf` under
     * the hood) will take O(N) time in the average/practical case.
     * - Space Complexity: O(N) Auxiliary Heap Space. We allocate memory exactly
     * once to create the new `s + s` string (length 2N), which scales linearly
     * with the input size. O(1) Auxiliary Stack Space.
     * ========================================================================
     */
    public static boolean rotateStringOptimal(String s, String goal) {
        // Step 1: A rotated string must have the exact same length.
        if (s.length() != goal.length()) {
            return false;
        }

        // Step 2: Concatenate 's' with itself.
        String doubledString = s + s;

        // Step 3: Check if the goal exists as a contiguous substring.
        return doubledString.contains(goal);
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[][] testCases = {
                {"abcde", "cdeab"},    // Example 1: Valid rotation
                {"abcde", "abced"},    // Example 2: Invalid rotation (swapped chars)
                {"a", "a"},            // Single character match
                {"a", "b"},            // Single character mismatch
                {"", ""},              // Empty strings match
                {"abc", "abcd"},       // Different lengths
                {"defdef", "efdefd"}   // Repeating characters
        };

        System.out.println("Running test cases for 796. Rotate String...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String goal = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\", goal = \"" + goal + "\"");

            boolean res1 = rotateStringBruteForce(s, goal);
            boolean res2 = rotateStringOptimal(s, goal);

            System.out.println("Phase 1 (Simulation)    : " + res1);
            System.out.println("Phase 2 (Optimal Trick) : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
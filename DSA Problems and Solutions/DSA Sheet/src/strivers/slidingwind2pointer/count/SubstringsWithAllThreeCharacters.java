package strivers.slidingwind2pointer.count;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM: 1358. Number of Substrings Containing All Three Characters (Medium)
 *
 * --- HEADER & PROBLEM CONTEXT ---
 * Given a string s consisting only of characters a, b and c.
 * Return the number of substrings containing at least one occurrence of all
 * these characters a, b and c.
 *
 * Example 1:
 * Input: s = "abcabc"
 * Output: 10
 * Explanation: The substrings containing at least one occurrence of the
 * characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab",
 * "bcabc", "cab", "cabc" and "abc" (again).
 *
 * Example 2:
 * Input: s = "aaacb"
 * Output: 3
 * Explanation: The substrings containing at least one occurrence of the
 * characters a, b and c are "aaacb", "aacb" and "acb".
 *
 * Example 3:
 * Input: s = "abc"
 * Output: 1
 *
 * Constraints:
 * 3 <= s.length <= 5 * 10^4
 * s only consists of a, b or c characters.
 * ============================================================================
 * NOTE ON PROGRESSION:
 * This is a Substring / Sliding Window problem (Non-DP). We will follow
 * the Non-DP Progressive Implementation Roadmap to dissect the optimal logic.
 * ============================================================================
 */

import java.util.stream.IntStream;

public class SubstringsWithAllThreeCharacters {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Pointer Tracking / Last Seen Index)
     * ============================================================================
     * Detailed Intuition:
     * Instead of maintaining a traditional sliding window with a `left` pointer
     * moving step-by-step, we can solve this in a single pass by tracking the
     * *most recent* indices where we saw 'a', 'b', and 'c'.
     *
     * As we iterate through the string with index `i`, we update the last seen
     * position of the current character. If we have seen all three characters at
     * least once (none are -1), a valid window exists.
     * The smallest index among the three `lastSeen` values dictates the "bottleneck".
     * Any substring starting from index 0 up to this bottleneck index, and ending
     * at `i`, will contain at least one 'a', one 'b', and one 'c'.
     * Thus, we add `1 + min(lastSeen[a], lastSeen[b], lastSeen[c])` to our count.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). We iterate through the string exactly once. Finding
     * the minimum of three values takes O(1) time.
     * Space Complexity: O(1) Auxiliary Space. We only use an array of size 3 on
     * the heap and primitive variables on the stack.
     */
    public int numberOfSubstringsOptimal(String s) {
        int count = 0;
        // Tracks the last seen index of 'a', 'b', and 'c'
        int[] lastSeen = {-1, -1, -1};

        for (int i = 0; i < s.length(); i++) {
            // Update the index for the current character
            lastSeen[s.charAt(i) - 'a'] = i;

            // If all characters have been seen at least once
            if (lastSeen[0] != -1 && lastSeen[1] != -1 && lastSeen[2] != -1) {
                // Number of valid substrings ending at 'i' is bounded by the smallest lastSeen index
                count += 1 + Math.min(lastSeen[0], Math.min(lastSeen[1], lastSeen[2]));
            }
        }

        return count;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ============================================================================
     * Detailed Intuition:
     * The "Think it" stage. We generate all possible substrings starting at index `i`.
     * We expand an inner loop `j` to the right. Once our substring `s[i...j]` contains
     * all three characters, we realize a crucial property: every string extending
     * further to the right (`s[i...j+1]`, `s[i...j+2]`, etc.) will ALSO be valid.
     *
     * So, instead of continuing the inner loop to the end of the string, we calculate
     * how many characters are left (`n - j`), add that to our total count, and
     * break early to start the next `i`.
     *
     * Complexity Analysis:
     * Time Complexity: O(N^2). In the worst case (e.g., "aaaaaab"), the inner loop
     * will scan repeatedly until it reaches the end, doing N*(N+1)/2 operations.
     * Space Complexity: O(1) Auxiliary Space. A small frequency tracker array of
     * size 3 is used.
     */
    public int numberOfSubstringsBruteForce(String s) {
        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int[] seen = new int[3];
            for (int j = i; j < n; j++) {
                seen[s.charAt(j) - 'a'] = 1;

                // If we have found a valid window starting at i and ending at j
                if (seen[0] == 1 && seen[1] == 1 && seen[2] == 1) {
                    // All substrings from j to the end of the string are valid
                    count += (n - j);
                    break; // Early exit optimization
                }
            }
        }

        return count;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Standard Sliding Window)
     * ============================================================================
     * Detailed Intuition:
     * This is the standard "Shrinkable Sliding Window" pattern. We maintain a window
     * `[left...right]` and a frequency array. We expand `right` character by character.
     *
     * Once the window becomes valid (frequency of a, b, c are all > 0), we know that
     * appending any characters to the right of this window keeps it valid. So, there
     * are `n - right` valid substrings that start at `left`. We add this to our count,
     * and then try to shrink the window from the `left` while it remains valid,
     * repeating the addition.
     *
     * Complexity Analysis:
     * Time Complexity: O(N). The `right` pointer moves from 0 to N-1. The `left`
     * pointer also moves at most N times across the entire algorithm. O(2N) -> O(N).
     * Space Complexity: O(1) Auxiliary Space. Fixed frequency array of size 3.
     */
    public int numberOfSubstringsAlternative(String s) {
        int count = 0;
        int left = 0;
        int n = s.length();
        int[] freq = new int[3];

        for (int right = 0; right < n; right++) {
            freq[s.charAt(right) - 'a']++;

            // While the current window is valid, count combinations and shrink
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                // If window [left, right] is valid, then [left, right+1] ... [left, n-1] are also valid
                count += (n - right);

                // Shrink from the left
                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return count;
    }

    /**
     * ============================================================================
     * SECTION 4: TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        SubstringsWithAllThreeCharacters solver = new SubstringsWithAllThreeCharacters();

        // Object array format: {String s, Expected Output}
        Object[][] testCases = {
                {"abcabc", 10},         // Standard Example 1
                {"aaacb", 3},           // Standard Example 2
                {"abc", 1},             // Minimal Exact Match
                {"ab", 0},              // Impossible (Missing 'c')
                {"aaaaab", 0},          // Impossible (Missing 'c', long prefix)
                {"cbabc", 8}            // Reverse order variation
        };

        System.out.println("🚀 Running Tests for LeetCode 1358...\n");

        // Java 8 Stream API to process and evaluate test cases
        IntStream.range(0, testCases.length).forEach(i -> {
            String s = (String) testCases[i][0];
            int expected = (int) testCases[i][1];

            int resOptimal = solver.numberOfSubstringsOptimal(s);
            int resBrute = solver.numberOfSubstringsBruteForce(s);
            int resAlternative = solver.numberOfSubstringsAlternative(s);

            boolean passed = (resOptimal == expected) &&
                    (resBrute == expected) &&
                    (resAlternative == expected);

            System.out.printf("Test %d: s = \"%s\"\n", i + 1, s);
            System.out.printf("   Expected    : %d\n", expected);
            System.out.printf("   Optimal     : %d | Brute: %d | Alternative: %d\n", resOptimal, resBrute, resAlternative);
            System.out.printf("   Result      : %s\n", passed ? "✅ PASS" : "❌ FAIL");
            System.out.println("---------------------------------------------------------");
        });
    }
}
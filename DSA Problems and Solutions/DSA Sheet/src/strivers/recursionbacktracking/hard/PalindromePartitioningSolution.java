package strivers.recursionbacktracking.hard;

/**
 * ============================================================================
 * 131. Palindrome Partitioning
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given a string s, partition s such that every substring of the partition is
 * a palindrome. Return all possible palindrome partitioning of s.
 *
 * EXAMPLES:
 * Example 1:
 * Input: s = "aab"
 * Output: [["a","a","b"],["aa","b"]]
 *
 * Example 2:
 * Input: s = "a"
 * Output: [["a"]]
 *
 * CONSTRAINTS:
 * - 1 <= s.length <= 16
 * - s contains only lowercase English letters.
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (Recursion Tree for For-Loop Approach)
 * ============================================================================
 * For s = "aab":
 * State format: (Current Path, Remaining String)
 *
 *                                   ([], "aab")
 *                                 /             \
 *                   cut "a"     /                 \  cut "aa"
 *                   (isPal=T) /                     \ (isPal=T)
 *                           /                         \
 *                     (["a"], "ab")                (["aa"], "b")
 *                     /           \                      |
 *            cut "a" /             \ cut "ab"            | cut "b"
 *          (isPal=T)/               \ (isPal=F)          | (isPal=T)
 *                 /                  X (pruned)          |
 *        (["a","a"], "b")                          (["aa","b"], "") ✅
 *               |
 *       cut "b" | (isPal=T)
 *               |
 *      (["a","a","b"], "") ✅
 *
 * Final Output: [["a","a","b"], ["aa","b"]]
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PalindromePartitioningSolution {

    /**
     * ============================================================================
     * PHASE 1A: Optimal Approach - For-Loop Based Backtracking (Explore Candidates)
     * ============================================================================
     * Detailed Intuition:
     * This uses the standard N-ary tree exploration pattern for string partitioning.
     * At any index `start`, we iterate an `end` pointer from `start` to the end
     * of the string. The substring from `start` to `end` represents our candidate
     * partition. If this substring is a palindrome, we "pick" it by adding it to
     * our current path, and recursively ask the function to partition the REST of
     * the string (starting from `end + 1`). Once the recursion returns, we backtrack
     * and expand our `end` pointer to try a longer prefix.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   In the worst case (a string of identical characters like "aaaa"), there
     *   are 2^(N-1) possible partitions. For each partition, we copy it to the
     *   result, taking O(N) time. The palindrome check also takes O(N) but is
     *   amortized within the recursive tree generation.
     * - Space Complexity: O(N) auxiliary stack space + O(N * 2^N) heap space.
     *   The recursion depth goes up to N. Storing all partitions requires O(N * 2^N).
     * ============================================================================
     */
    public List<List<String>> partitionForLoop(String s) {
        List<List<String>> result = new ArrayList<>();
        solveForLoop(0, s, new ArrayList<>(), result);
        return result;
    }

    private void solveForLoop(int start, String s, List<String> current, List<List<String>> result) {
        // Base case: If we've reached the end of the string, a valid partition is complete
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Loop to try all possible substrings starting from 'start'
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                // INCLUDE candidate
                current.add(s.substring(start, end + 1));

                // RECURSE for the remaining substring
                solveForLoop(end + 1, s, current, result);

                // BACKTRACK
                current.remove(current.size() - 1);
            }
        }
    }

    /**
     * ============================================================================
     * PHASE 1B: Alternative Approach - Normal Recursion (Pick / Don't Pick)
     * ============================================================================
     * Detailed Intuition:
     * This follows the strict binary tree pattern by treating the problem as a
     * sequence of "cut" or "don't cut" decisions at every character boundary.
     *
     * We maintain two pointers: `start` (where the current substring began) and
     * `index` (the current character we are inspecting).
     * At each `index`, we have two choices:
     * 1. Don't cut after `index`: Extend the substring by moving `index + 1`.
     * 2. Cut after `index`: If the substring from `start` to `index` is a palindrome,
     *    we add it to our path and start a new substring at `index + 1`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * 2^N)
     *   We make a binary choice at each character, resulting in a tree of depth N
     *   and 2^N leaves.
     * - Space Complexity: O(N) auxiliary stack space + O(N * 2^N) heap space.
     * ============================================================================
     */
    public List<List<String>> partitionPickDontPick(String s) {
        List<List<String>> result = new ArrayList<>();
        solvePickDontPick(0, 0, s, new ArrayList<>(), result);
        return result;
    }

    private void solvePickDontPick(int index, int start, String s, List<String> current, List<List<String>> result) {
        // Base case: Reached the end of the string
        if (index == s.length()) {
            // We can only consider this a successful path if there are no trailing un-partitioned characters.
            // i.e., our last "cut" was exactly at the end of the string.
            if (start == s.length()) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        // CHOICE 1: DON'T CUT at 'index'
        // Just move the index forward to extend the current substring being considered.
        solvePickDontPick(index + 1, start, s, current, result);

        // CHOICE 2: CUT at 'index'
        // We can only make a cut here if the substring s[start...index] is a palindrome.
        if (isPalindrome(s, start, index)) {
            current.add(s.substring(start, index + 1));

            // RECURSE: Move index forward, and the new substring will start from index + 1
            solvePickDontPick(index + 1, index + 1, s, current, result);

            // BACKTRACK: Undo the cut
            current.remove(current.size() - 1);
        }
    }

    /**
     * ============================================================================
     * PHASE 3: Alternative Approach - Backtracking with DP (Memoization for Palindromes)
     * ============================================================================
     * Detailed Intuition:
     * Generating partitions strictly requires backtracking, but validating the
     * palindromes repeatedly takes O(N) time for every check, which creates overhead.
     * We can precompute all palindromic substrings in an O(N^2) Dynamic Programming
     * table. `dp[i][j]` will be true if `s.substring(i, j+1)` is a palindrome.
     * After this precomputation, our for-loop backtracking can check if a candidate
     * is a palindrome in strictly O(1) time.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2 + N * 2^N) -> O(N * 2^N) overall.
     *   Precomputing the DP table takes O(N^2). Backtracking is still O(N * 2^N) worst case.
     * - Space Complexity: O(N^2) for the DP table + O(N) recursion stack.
     * ============================================================================
     */
    public List<List<String>> partitionWithDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        // DP: Precompute all palindromes
        // A substring s[i..j] is a palindrome if s[i] == s[j] and s[i+1..j-1] is a palindrome.
        for (int length = 1; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    if (length <= 2) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
            }
        }

        List<List<String>> result = new ArrayList<>();
        solveWithDP(0, s, dp, new ArrayList<>(), result);
        return result;
    }

    private void solveWithDP(int start, String s, boolean[][] dp, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            // O(1) lookup instead of O(N) string traversal
            if (dp[start][end]) {
                current.add(s.substring(start, end + 1));
                solveWithDP(end + 1, s, dp, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    // Helper method for Phase 1A and 1B
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * ============================================================================
     * TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        PalindromePartitioningSolution solution = new PalindromePartitioningSolution();

        String[] testCases = {
                "aab",  // Standard Example 1
                "a",    // Standard Example 2
                "cdd",  // General test case
                "aaaa"  // Edge case: All same characters (worst case generation)
        };

        System.out.println("====== PALINDROME PARTITIONING DSA EVALUATION ======\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": String = \"" + testCases[i] + "\"");

            // Test Phase 1A: For-Loop Backtrack
            List<List<String>> resForLoop = solution.partitionForLoop(testCases[i]);
            System.out.println("  [For-Loop Backtrack] : " + formatResult(resForLoop));

            // Test Phase 1B: Pick / Don't Pick Recursion
            List<List<String>> resPickDontPick = solution.partitionPickDontPick(testCases[i]);
            System.out.println("  [Pick/Don't Pick]    : " + formatResult(resPickDontPick));

            // Test Phase 3: DP Optimized Backtrack
            List<List<String>> resDP = solution.partitionWithDP(testCases[i]);
            System.out.println("  [DP Precomputed]     : " + formatResult(resDP));

            System.out.println("--------------------------------------------------");
        }
    }

    // Utility to format List<List<String>> cleanly for the console using Java 8 Streams
    private static String formatResult(List<List<String>> res) {
        if (res.isEmpty()) return "[]";
        return "[" + res.stream()
                .map(list -> "[" + list.stream()
                        .map(str -> "\"" + str + "\"")
                        .collect(Collectors.joining(", ")) + "]")
                .collect(Collectors.joining(", ")) + "]";
    }
}
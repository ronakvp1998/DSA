package strivers.string.easy;
/**
 * ============================================================================
 * 14. Longest Common Prefix
 * ============================================================================
 * Write a function to find the longest common prefix string amongst an array
 * of strings.
 * * If there is no common prefix, return an empty string "".
 * * Example 1:
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 * * Example 2:
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * * Constraints:
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] consists of only lowercase English letters if it is non-empty.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.Arrays;

public class LongestCommonPrefix {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Horizontal Scanning) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most straightforward way to find the longest common prefix is to assume
     * the first string is the common prefix. Then, we iterate through the rest of
     * the strings one by one. For each string, we check if it starts with our
     * current prefix. If it doesn't, we continuously shorten the prefix from the
     * end by one character until it matches. If the prefix ever becomes empty,
     * we know there is no common prefix at all and can return early.
     * * Complexity Analysis:
     * - Time Complexity: O(S), where S is the sum of all characters in all strings.
     * In the worst case, all strings are identical. The `indexOf` and `substring`
     * operations will scan the characters, meaning we visit every character at
     * most twice.
     * - Space Complexity: O(1) Auxiliary Space. We only maintain a few pointers
     * and references, taking constant extra space (excluding the space needed
     * for the returned string, which is allocated on the heap).
     * ========================================================================
     */
    public static String longestCommonPrefixHorizontal(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // Assume the first string is the prefix
        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            // While the current string DOES NOT start with the prefix
            while (strs[i].indexOf(prefix) != 0) {
                // Shorten the prefix by 1 character from the end
                prefix = prefix.substring(0, prefix.length() - 1);

                // If we've shortened it to nothing, there is no common prefix
                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }

    /**
     * ========================================================================
     * Phase 2: Vertical Scanning - The "Refine it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Horizontal scanning works well, but it can be inefficient if there's a very
     * short string at the very end of the array. We'd process long prefixes pointlessly.
     * Vertical scanning solves this by comparing characters column by column.
     * We look at index 0 of all strings, then index 1 of all strings, and so on.
     * As soon as we hit a string that is too short, or a character that doesn't
     * match the character in the first string, we immediately stop and return
     * the substring up to that point.
     * * Complexity Analysis:
     * - Time Complexity: O(S), where S is the sum of all characters. In the worst
     * case (all strings identical), it's the same as horizontal. However, in the
     * best/average case (mismatch found early), this is much faster because it
     * halts immediately without processing the rest of the strings' tails.
     * - Space Complexity: O(1) Auxiliary Space. Only primitive variables are used
     * for loop counters.
     * ========================================================================
     */
    public static String longestCommonPrefixVertical(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // Iterate through the characters of the first string
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);

            // Compare this character with the same index in all other strings
            for (int j = 1; j < strs.length; j++) {
                // If we reach the end of a string, or find a mismatch
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    /**
     * ========================================================================
     * Phase 3: Lexicographical Sorting Trick - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * This is an elegant mathematical observation. If we sort the array of strings
     * alphabetically (lexicographically), the strings that share the most characters
     * will be grouped together. Conversely, the first string and the last string
     * in the sorted array will be the most different from each other.
     * Therefore, the longest common prefix for the *entire* array is simply the
     * longest common prefix between just the *first* and *last* strings!
     * We don't even need to look at the strings in the middle.
     * * Complexity Analysis:
     * - Time Complexity: O(N * M * log N), where N is the number of strings and
     * M is the maximum length of a string. Sorting strings takes M comparisons
     * per swap. Though technically a higher worst-case time bound than O(S),
     * it is highly elegant, uses highly optimized internal Java sorting, and
     * often performs incredibly well in interviews to show domain knowledge.
     * - Space Complexity: O(log N) to O(N) Auxiliary Stack Space depending on
     * the system's sorting algorithm (Timsort/Dual-Pivot Quicksort in Java),
     * plus O(M) Heap Space to return the substring.
     * ========================================================================
     */
    public static String longestCommonPrefixSorting(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // Step 1: Sort the array lexicographically
        Arrays.sort(strs);

        // Step 2: Grab the first and last strings
        String first = strs[0];
        String last = strs[strs.length - 1];

        int idx = 0;
        // Step 3: Compare characters of just the first and last strings
        while (idx < first.length() && idx < last.length()) {
            if (first.charAt(idx) == last.charAt(idx)) {
                idx++;
            } else {
                break;
            }
        }

        // Return the matched portion
        return first.substring(0, idx);
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        // Define standard and edge cases
        String[][] testCases = {
                {"flower", "flow", "flight"},         // Standard case
                {"dog", "racecar", "car"},            // No common prefix
                {"interspecies", "interstellar", "interstate"}, // Long common prefix
                {"throne", "throne"},                 // Identical strings
                {"a"},                                // Single character string
                {"", "b"},                            // Empty string in array
                {"ab", "a"},                          // Shortest string at the end
                {"prefix", "prefix", "prefix"}        // All strings are exactly the same
        };

        System.out.println("Running test cases for 14. Longest Common Prefix...\n");

        for (int i = 0; i < testCases.length; i++) {
            String[] input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(input));

            String res1 = longestCommonPrefixHorizontal(input);
            String res2 = longestCommonPrefixVertical(input);
            String res3 = longestCommonPrefixSorting(input);

            System.out.println("Phase 1 (Horizontal Scanning) : \"" + res1 + "\"");
            System.out.println("Phase 2 (Vertical Scanning)   : \"" + res2 + "\"");
            System.out.println("Phase 3 (Lexicographical)     : \"" + res3 + "\"");

            // Validation step
            if (res1.equals(res2) && res2.equals(res3)) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
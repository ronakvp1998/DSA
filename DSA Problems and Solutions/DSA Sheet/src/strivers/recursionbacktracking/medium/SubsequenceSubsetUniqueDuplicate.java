package strivers.recursionbacktracking.medium;

import java.util.*;
import java.util.stream.Stream;

/**
 * # Subsequence and Subset Combinations (Unique & Duplicates)
 *
 * ## 1. Header & Problem Context
 * **Problem Statement:**
 * Given a string (which may contain unique or duplicate characters), generate:
 * 1. All Subsequences
 * 2. All Subsets
 * 3. Unique Subsequences
 * 4. Unique Subsets
 *
 * **Core Definitions:**
 * - **Subsequence:** Maintains the original relative order of elements. "ac" is a valid
 *   subsequence of "abc", but "ca" is not. Therefore, sorting the input is strictly forbidden
 *   if you must maintain subsequences.
 * - **Subset:** A mathematical collection where order does not matter. {a, c} is the exact
 *   same subset as {c, a}. Therefore, sorting the input is allowed and encouraged to group duplicates.
 *
 * **Examples:**
 * - Input: "aab"
 *   Unique Subsequences: ["", "a", "b", "aa", "ab", "aab"] (6 items)
 *   Unique Subsets: ["", "a", "aa", "aab", "ab", "b"] (6 items, order may vary)
 *
 * ---
 *
 * ## 2.2 Progressive Implementation Roadmap (Non-DP)
 *
 * * **Phase 1: Subsequences (Duplicates Allowed)** - Standard Pick / Don't Pick recursion.
 * * **Phase 2: Subsets (Duplicates Allowed)** - For-Loop recursion pattern.
 * * **Phase 3: Unique Subsequences** - Pick / Don't Pick pattern with a `HashSet` (Cannot sort).
 * * **Phase 4: Unique Subsets (Optimal)** - Sorting + For-Loop pattern with duplicate skipping (`i > start`).
 */
public class SubsequenceSubsetUniqueDuplicate {

    /**
     * ## Phase 1: All Subsequences (Duplicates Allowed)
     *
     * **Detailed Intuition:**
     * This uses the standard "Pick / Don't Pick" pattern. It evaluates each character
     * from left to right, making a binary decision to either include it in the current
     * string or skip it. This inherently preserves the relative order of the original string.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \cdot 2^N)$. There are $2^N$ subsequences. String concatenation
     *   at each step takes $O(N)$ time.
     * - **Space Complexity:** $O(N)$ auxiliary stack space. $O(N \cdot 2^N)$ heap space to
     *   store the results.
     */
    public List<String> allSubsequences(String str) {
        List<String> result = new ArrayList<>();
        generateSubseq(str, 0, "", result);
        return result;
    }

    private void generateSubseq(String str, int index, String current, List<String> result) {
        if (index == str.length()) {
            result.add(current);
            return;
        }

        // Pick the character
        generateSubseq(str, index + 1, current + str.charAt(index), result);

        // Don't pick the character
        generateSubseq(str, index + 1, current, result);
    }

    /**
     * ## Phase 2: All Subsets (Duplicates Allowed)
     *
     * **Detailed Intuition:**
     * While you can use the exact same "Pick / Don't Pick" code above for subsets,
     * subsets are typically written using a For-Loop pattern. This loop represents
     * picking a starting character and then recursively building on it.
     * Both yield the exact same elements (though possibly generated in a different order).
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \cdot 2^N)$.
     * - **Space Complexity:** $O(N)$ auxiliary stack space + $O(N \cdot 2^N)$ heap space.
     */
    public List<String> allSubsets(String str) {
        List<String> result = new ArrayList<>();
        generateSubsets(str, 0, "", result);
        return result;
    }

    private void generateSubsets(String str, int start, String current, List<String> result) {
        // Add the current combination to the result at EVERY node in the tree
        result.add(current);

        for (int i = start; i < str.length(); i++) {
            generateSubsets(str, i + 1, current + str.charAt(i), result);
        }
    }

    /**
     * ## Phase 3: Unique Subsequences
     *
     * **Detailed Intuition:**
     * If the input is "aab" and you want unique subsequences, you CANNOT sort the string
     * because sorting destroys the original sequence order (e.g., "bca" becomes "abc",
     * which ruins the subsequences). Therefore, you must use the standard Pick/Don't Pick
     * logic but store the results in a `HashSet` to silently swallow and filter out duplicates.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \cdot 2^N)$. The `HashSet` adds a small $O(N)$ hashing overhead per insertion.
     * - **Space Complexity:** $O(N)$ auxiliary stack space. $O(N \cdot 2^N)$ heap space for the Set.
     */
    public List<String> uniqueSubsequences(String str) {
        // Use a HashSet to automatically filter identical subsequences
        Set<String> result = new HashSet<>();
        generateUniqueSubseq(str, 0, "", result);
        return new ArrayList<>(result); // Convert Set back to List for standard return type
    }

    private void generateUniqueSubseq(String str, int index, String current, Set<String> result) {
        if (index == str.length()) {
            result.add(current);
            return;
        }

        generateUniqueSubseq(str, index + 1, current + str.charAt(index), result);
        generateUniqueSubseq(str, index + 1, current, result);
    }

    /**
     * ## Phase 4: Unique Subsets
     *
     * **Detailed Intuition:**
     * Because subset order does not matter, we are ALLOWED to sort the string first.
     * Sorting guarantees that duplicate characters sit next to each other.
     * We can then use the `i > start` condition to skip duplicates on the same recursion level.
     * This avoids generating duplicate branches entirely, saving memory and processing power
     * compared to the `HashSet` approach.
     *
     * **Complexity Analysis:**
     * - **Time Complexity:** $O(N \log N)$ for sorting + $O(N \cdot 2^N)$ for generation.
     * - **Space Complexity:** $O(N)$ auxiliary stack space + $O(N \cdot 2^N)$ heap space.
     */
    public List<String> uniqueSubsets(String str) {
        List<String> result = new ArrayList<>();

        // 1. Convert to char array and SORT to group duplicates
        char[] chars = str.toCharArray();
        Arrays.sort(chars);

        generateUniqueSubsets(chars, 0, "", result);
        return result;
    }

    private void generateUniqueSubsets(char[] chars, int start, String current, List<String> result) {
        result.add(current);

        for (int i = start; i < chars.length; i++) {
            // 2. Skip duplicates horizontally on the SAME recursion level
            if (i > start && chars[i] == chars[i - 1]) {
                continue;
            }

            generateUniqueSubsets(chars, i + 1, current + chars[i], result);
        }
    }

    /**
     * ## 4. Testing Suite
     */
    public static void main(String[] args) {
        SubsequenceSubsetUniqueDuplicate solver = new SubsequenceSubsetUniqueDuplicate();

        // Test Cases: "abc" (all unique), "aab" (contains duplicates)
        String[] testCases = {"abc", "aab"};

        System.out.println("--- Running Subsequence & Subset Variations ---");

        Stream.of(testCases).forEach(str -> {
            System.out.println("\nTesting String: \"" + str + "\"");

            List<String> allSubseq = solver.allSubsequences(str);
            List<String> allSubs = solver.allSubsets(str);
            List<String> uniqueSubseq = solver.uniqueSubsequences(str);
            List<String> uniqueSubs = solver.uniqueSubsets(str);

            System.out.println("1. All Subsequences (" + allSubseq.size() + "): " + allSubseq);
            System.out.println("2. All Subsets      (" + allSubs.size() + "): " + allSubs);

            // Notice how sizes differ for "aab" but remain identical for "abc"
            System.out.println("3. Unique Subseqs   (" + uniqueSubseq.size() + "): " + uniqueSubseq);
            System.out.println("4. Unique Subsets   (" + uniqueSubs.size() + "): " + uniqueSubs);

            System.out.println("-------------------------------------------------");
        });
    }
}
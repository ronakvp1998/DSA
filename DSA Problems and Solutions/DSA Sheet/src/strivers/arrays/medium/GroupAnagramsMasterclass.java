package strivers.arrays.medium;

/**
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * * Problem: 49. Group Anagrams (Medium)
 * * Formal Problem Statement:
 * Given an array of strings strs, group the anagrams together. You can return
 * the answer in any order.
 * * An Anagram is a word or phrase formed by rearranging the letters of a
 * different word or phrase, typically using all the original letters exactly once.
 * * Constraints:
 * - 1 <= strs.length <= 10^4
 * - 0 <= strs[i].length <= 100
 * - strs[i] consists of lowercase English letters.
 * * Examples:
 * Example 1:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * Explanation:
 * - There is no string in strs that can be rearranged to form "bat".
 * - The strings "nat" and "tan" are anagrams.
 * - The strings "ate", "eat", and "tea" are anagrams.
 * * Example 2:
 * Input: strs = [""]
 * Output: [[""]]
 * * Example 3:
 * Input: strs = ["a"]
 * Output: [["a"]]
 * * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach (Frequency Count Hashing) - The recommended approach.
 * Phase 2: Brute Force Approach (Nested Loops) - The "Think it" stage.
 * Phase 3: Alternative Approach (Sorting Key + Java 8 Streams) - Clean & concise.
 */

import java.util.*;
import java.util.stream.Collectors;

public class GroupAnagramsMasterclass {

    /**
     * ========================================================================
     * PHASE 1: OPTIMAL APPROACH (Frequency Count Hashing)
     * ========================================================================
     * * Detailed Intuition:
     * Instead of sorting the string to find a common key (which takes K log K time),
     * we can build a unique key by counting the frequency of each character.
     * Since the problem constrains characters to lowercase English letters,
     * we can use a fixed integer array of size 26.
     * We convert this frequency array into a String (like "#1#0#0...") and use it
     * as the key in our HashMap. All anagrams will generate the exact same frequency key.
     * * Complexity Analysis:
     * - Time Complexity: O(N * K), where N is the number of strings and K is the
     * maximum length of a string. We visit each character of each string exactly once.
     * - Space Complexity: O(N * K) heap space to store the grouping in the HashMap
     * and the resulting lists. The auxiliary frequency array takes O(1) space (size 26).
     */
    public List<List<String>> groupAnagramsOptimal(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // Build the frequency profile
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            // Convert frequency array to a unique string key
            // Arrays.toString is a quick way to generate a distinct string from the array
            String key = Arrays.toString(count);

            // Group anagrams
//            Get the list for this key. If the list doesn't exist yet, create a new one, put it in the map, and then add my string s to it."
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH
     * ========================================================================
     * * Detailed Intuition:
     * The most rudimentary way to solve this is to compare every string with
     * every other string to see if they are anagrams. We use a boolean array
     * to keep track of strings we have already grouped to avoid duplicating work.
     * For checking if two strings are anagrams, we use a character frequency count.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 * K), where N is the number of strings and K is the
     * maximum string length. For each string, we potentially scan the rest of the
     * array, and the anagram check takes O(K) time. This will likely cause a
     * Time Limit Exceeded (TLE) on LeetCode.
     * - Space Complexity: O(N) auxiliary space for the boolean visited array,
     * plus O(N * K) heap space for storing the answer lists.
     */
    public List<List<String>> groupAnagramsBruteForce(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        List<List<String>> ans = new ArrayList<>();
        boolean[] visited = new boolean[strs.length];

        for (int i = 0; i < strs.length; i++) {
            if (visited[i]) continue;

            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            visited[i] = true;

            for (int j = i + 1; j < strs.length; j++) {
                if (!visited[j] && isAnagram(strs[i], strs[j])) {
                    group.add(strs[j]);
                    visited[j] = true;
                }
            }
            ans.add(group);
        }
        return ans;
    }

    // Helper for Brute Force
    private boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int[] freq = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            freq[s1.charAt(i) - 'a']++;
            freq[s2.charAt(i) - 'a']--;
        }
        for (int count : freq) {
            if (count != 0) return false;
        }
        return true;
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Sorting Key + Java 8 Streams)
     * ========================================================================
     * * Detailed Intuition:
     * Anagrams identical when sorted (e.g., "eat" and "tea" both become "aet").
     * We can use the sorted version of the string as the grouping key.
     * To demonstrate modern Java proficiency, this phase leverages the Java 8
     * Stream API. `Collectors.groupingBy` effortlessly handles the map creation
     * and list generation in a declarative functional style.
     * * Complexity Analysis:
     * - Time Complexity: O(N * K log K). We iterate through N strings. For each,
     * we convert to a char array and sort it, which takes O(K log K) time.
     * - Space Complexity: O(N * K) for the resultant lists, Map structure,
     * and the intermediate character arrays during sorting.
     */
    public List<List<String>> groupAnagramsSortingStream(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        return new ArrayList<>(
                Arrays.stream(strs).collect(
                        Collectors.groupingBy(
                                // Key Mapper: Sort the string characters
                                s -> {
                                    char[] chars = s.toCharArray();
                                    Arrays.sort(chars);
                                    return new String(chars);
                                }
                        )
                ).values()
        );
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        GroupAnagramsMasterclass solution = new GroupAnagramsMasterclass();

        // Define Test Cases
        String[][] testCases = {
                {"eat", "tea", "tan", "ate", "nat", "bat"}, // Standard case
                {""},                                       // Edge case: single empty string
                {"a"},                                      // Edge case: single character string
                {"", "b", "", "b"},                         // Edge case: multiple empty and dupes
                {"cab", "tin", "pew", "duh", "may", "ill", "buy", "bar", "max", "doc"} // No anagrams
        };

        int testNum = 1;
        for (String[] test : testCases) {
            System.out.println("==================================================");
            System.out.println("Test Case " + testNum++ + ": " + Arrays.toString(test));

            // Test Optimal
            List<List<String>> optimalRes = solution.groupAnagramsOptimal(test);
            System.out.println("Phase 1 (Optimal)       : " + optimalRes);

            // Test Brute Force
            List<List<String>> bruteRes = solution.groupAnagramsBruteForce(test);
            System.out.println("Phase 2 (Brute Force)   : " + bruteRes);

            // Test Streams + Sorting
            List<List<String>> streamRes = solution.groupAnagramsSortingStream(test);
            System.out.println("Phase 3 (Stream/Sorting): " + streamRes);
            System.out.println("==================================================\n");
        }
    }
}
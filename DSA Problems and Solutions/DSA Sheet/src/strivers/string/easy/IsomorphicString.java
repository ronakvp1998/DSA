package com.questions.strivers.string.easy;

/**
 * ============================================================================
 * 205. Isomorphic Strings
 * ============================================================================
 * Given two strings s and t, determine if they are isomorphic.
 * * Two strings s and t are isomorphic if the characters in s can be replaced
 * to get t.
 * * All occurrences of a character must be replaced with another character while
 * preserving the order of characters. No two characters may map to the same
 * character, but a character may map to itself.
 * * Example 1:
 * Input: s = "egg", t = "add"
 * Output: true
 * Explanation:
 * The strings s and t can be made identical by:
 * Mapping 'e' to 'a'.
 * Mapping 'g' to 'd'.
 * * Example 2:
 * Input: s = "f11", t = "b23"
 * Output: false
 * Explanation:
 * The strings s and t can not be made identical as '1' needs to be mapped
 * to both '2' and '3'.
 * * Example 3:
 * Input: s = "paper", t = "title"
 * Output: true
 * * Constraints:
 * - 1 <= s.length <= 5 * 10^4
 * - t.length == s.length
 * - s and t consist of any valid ascii character.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.HashMap;
import java.util.Map;

public class IsomorphicString {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Index Matching) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * If two strings are isomorphic, the structural "shape" of their characters
     * must be identical. This means that for any character at index `i`, its
     * first occurrence in string `s` must be at the exact same position as the
     * first occurrence of the corresponding character in string `t`.
     * By using the built-in `indexOf()` method, we can compare the first-seen
     * indices of the characters at every position. If they ever diverge, the
     * structural mapping is broken.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). We iterate through the string of length N, and
     * for every character, we call `indexOf()`, which itself takes O(N) time
     * to scan the string from the beginning.
     * - Space Complexity: O(1) Auxiliary Space. We only allocate a few primitive
     * variables.
     * ========================================================================
     * i,Char in s,Char in t,s.indexOf(char),t.indexOf(char),Do they match?
     * Example 1: s = "egg", t = "add" (Should return true)
     * i,   Char in s,     Char in t,      s.indexOf(char),                 t.indexOf(char),                Do they match?
     * 0,   e,             a,              0 (first 'e' is at 0),           0 (first 'a' is at 0),          Yes (0 == 0)
     * 1,   g,             d,              1 (first 'g' is at 1),           1 (first 'd' is at 1),          Yes (1 == 1)
     * 2,   g,             d,              1 (first 'g' is STILL at 1),     1 (first 'd' is STILL at 1),    Yes (1 == 1)
     *
     * Example 2: s = "foo", t = "bar" (Should return false)
     * i,   Char in s,  Char in t,  s.indexOf(char),                t.indexOf(char),        Do they match?
     * 0,   f,          b,          0 (first 'f' is at 0),          0 (first 'b' is at 0),  Yes (0 == 0)
     * 1,   o,          a,          1 (first 'o' is at 1),          1 (first 'a' is at 1),  Yes (1 == 1)
     * 2,   o,          r,          1 (first 'o' is STILL at 1),    2 (first 'r' is at 2),  🛑 NO! (1 != 2)
     */
    public static boolean isIsomorphicBruteForce(String s, String t) {
        if (s.length() != t.length()) return false;

        for (int i = 0; i < s.length(); i++) {
            // If the first occurrence index of the character in S does not match
            // the first occurrence index of the character in T, it's not a valid map.
            if (s.indexOf(s.charAt(i)) != t.indexOf(t.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * ========================================================================
     * Phase 2: HashMap Two-Way Mapping - The "Refine it" stage.
     * ========================================================================
     * Detailed Intuition:
     * We need a strict 1-to-1 bijection mapping. A character in S can only map
     * to one character in T, AND a character in T can only be mapped to by one
     * character in S.
     * We can use two HashMaps (or one Map and one Set) to enforce this rule.
     * As we iterate through the strings, we check:
     * 1. Does s[i] already map to something? If yes, is it t[i]?
     * 2. Has t[i] already been mapped to by a different character?
     * * Complexity Analysis:
     * - Time Complexity: O(N). We traverse the string exactly once. HashMap
     * lookups and insertions are O(1) on average.
     * - Space Complexity: O(1) Auxiliary Heap Space. While we use Maps, the
     * maximum number of entries is bounded by the ASCII character set (256).
     * Therefore, the space does not scale with N, capping at O(1) conceptually.
     * ========================================================================
     */
    public static boolean isIsomorphicHashMap(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Character, Character> mapSToT = new HashMap<>();
        Map<Character, Character> mapTToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            // Check S -> T mapping
            if (mapSToT.containsKey(c1) && mapSToT.get(c1) != c2) {
                return false;
            }

            // Check T -> S mapping
            if (mapTToS.containsKey(c2) && mapTToS.get(c2) != c1) {
                return false;
            }

            mapSToT.put(c1, c2);
            mapTToS.put(c2, c1);
        }

        return true;
    }

    /**
     * ========================================================================
     * Phase 3: ASCII Array Hashing - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * As a Senior Developer, we know HashMaps introduce overhead (Object wrappers
     * like `Character`, hashing functions, tree node allocations).
     * Since the problem explicitly states "valid ascii character", we know there
     * are exactly 256 possible characters. We can replace the HashMaps with two
     * simple primitive integer arrays of size 256.
     * * Instead of mapping characters to characters, we map characters to their
     * **latest seen index**. If the latest seen indices for s[i] and t[i] do not
     * match, their structure is broken.
     * * CRITICAL TRICK: We store `i + 1` instead of `i`. In Java, `int[]` defaults
     * to `0`. If we stored `i`, index 0 would be indistinguishable from an
     * unvisited character. Storing `i + 1` ensures 0 safely means "unvisited".
     * * Complexity Analysis:
     * - Time Complexity: O(N). Single pass through the string. Array lookups
     * are lightning-fast O(1).
     * - Space Complexity: O(1) Auxiliary Heap Space. We allocate exactly two
     * arrays of 256 integers, regardless of how large N gets. This is highly
     * cache-friendly.
     * ========================================================================
     */
    public static boolean isIsomorphicOptimal(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] mapS = new int[256];
        int[] mapT = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            // If the last seen positions of these characters don't match,
            // the structural mapping is broken.
            if (mapS[c1] != mapT[c2]) {
                return false;
            }

            // Update the last seen positions. Use i + 1 to avoid conflicts
            // with the default int array value of 0.
            mapS[c1] = i + 1;
            mapT[c2] = i + 1;
        }

        return true;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[][] testCases = {
                {"egg", "add"},             // Standard isomorphic true
                {"foo", "bar"},             // Standard isomorphic false
                {"paper", "title"},         // Longer true case
                {"f11", "b23"},             // Digits false case
                {"badc", "baba"},           // Crucial Edge Case: multiple chars in S mapping to same in T
                {"a", "a"},                 // Single character
                {"\u0000", "\u0001"}        // Low boundary ASCII characters
        };

        System.out.println("Running test cases for 205. Isomorphic Strings...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String t = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\", t = \"" + t + "\"");

            boolean res1 = isIsomorphicBruteForce(s, t);
            boolean res2 = isIsomorphicHashMap(s, t);
            boolean res3 = isIsomorphicOptimal(s, t);

            System.out.println("Phase 1 (Brute Force): " + res1);
            System.out.println("Phase 2 (HashMap)    : " + res2);
            System.out.println("Phase 3 (Optimal Array): " + res3);

            // Validation step
            if (res1 == res2 && res2 == res3) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
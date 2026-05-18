package strivers.string.easy;
/**
 * ============================================================================
 * 242. Valid Anagram
 * ============================================================================
 * Given two strings s and t, return true if t is an anagram of s, and false
 * otherwise.
 * * Example 1:
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * * Example 2:
 * Input: s = "rat", t = "car"
 * Output: false
 * * Constraints:
 * - 1 <= s.length, t.length <= 5 * 10^4
 * - s and t consist of lowercase English letters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.Arrays;

public class ValidAnagram {

    /**
     * ========================================================================
     * Phase 1: Brute Force Approach (Sorting) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * By definition, an anagram contains the exact same characters as the
     * original string, just in a different order. Therefore, if we sort both
     * strings alphabetically, they must become identical.
     * We can convert both strings to character arrays, sort them using Java's
     * built-in Dual-Pivot Quicksort/Timsort, and then check if the arrays
     * are equal. If the string lengths are different, we can immediately
     * return false as an early exit optimization.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N), where N is the length of the string.
     * Sorting the character array takes O(N log N) time. The subsequent
     * equality check takes O(N) time, making the dominant term O(N log N).
     * - Space Complexity: O(N) Auxiliary Heap Space. Strings are immutable
     * in Java, so calling `toCharArray()` allocates a new array of size N
     * for both strings, consuming O(N) heap memory.
     * ========================================================================
     */
    public static boolean isAnagramSorting(String s, String t) {
        // Early exit: Anagrams must have the same length
        if (s.length() != t.length()) {
            return false;
        }

        // Convert to character arrays
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        // Sort both arrays
        Arrays.sort(sChars);
        Arrays.sort(tChars);

        // Compare the sorted arrays
        return Arrays.equals(sChars, tChars);
    }

    /**
     * ========================================================================
     * Phase 2: Optimal Approach (Frequency Array) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * Since the problem constrains the input to "lowercase English letters"
     * only, there are exactly 26 possible characters. We don't need to sort
     * the strings; we just need to count the frequency of each character.
     * * We can use a simple integer array of size 26 as a hash map.
     * We iterate through both strings simultaneously:
     * 1. Increment the count at the index corresponding to the character in `s`.
     * 2. Decrement the count at the index corresponding to the character in `t`.
     * * If the strings are valid anagrams, every increment will be perfectly
     * offset by a decrement, leaving the entire array filled with 0s. If any
     * value in the array is non-zero at the end, they are not anagrams.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. We do a
     * single linear pass through the strings of length N, followed by a
     * constant 26-step iteration over our frequency array.
     * - Space Complexity: O(1) Auxiliary Heap Space. The frequency array is
     * always exactly size 26, regardless of how large N gets. It requires
     * strictly constant memory.
     * ========================================================================
     */
    public static boolean isAnagramOptimal(String s, String t) {
        // Early exit: Anagrams must have the exact same length
        if (s.length() != t.length()) {
            return false;
        }

        // Frequency array for 26 lowercase English letters
        int[] charCounts = new int[26];

        // Populate the frequency array
        for (int i = 0; i < s.length(); i++) {
            // 'a' is ASCII 97. Subtracting 'a' maps 'a' to 0, 'b' to 1, ..., 'z' to 25.
            charCounts[s.charAt(i) - 'a']++;
            charCounts[t.charAt(i) - 'a']--;
        }

        // Check if any character count is non-zero
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
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
                {"anagram", "nagaram"},     // Example 1: Standard valid anagram
                {"rat", "car"},             // Example 2: Standard invalid anagram
                {"a", "a"},                 // Single character valid
                {"a", "b"},                 // Single character invalid
                {"ab", "a"},                // Different lengths
                {"aabbcc", "abcabc"},       // Valid with repeating characters
                {"aabbcc", "aabbcd"}        // Invalid with same length, one mismatched char
        };

        System.out.println("Running test cases for 242. Valid Anagram...\n");

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String t = testCases[i][1];

            System.out.println("Test Case " + (i + 1) + ": s = \"" + s + "\", t = \"" + t + "\"");

            boolean res1 = isAnagramSorting(s, t);
            boolean res2 = isAnagramOptimal(s, t);

            System.out.println("Phase 1 (Sorting)   : " + res1);
            System.out.println("Phase 2 (Optimal)   : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
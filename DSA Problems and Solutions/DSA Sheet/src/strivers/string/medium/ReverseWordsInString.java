package strivers.string.medium;
/**
 * ============================================================================
 * 151. Reverse Words in a String
 * ============================================================================
 * Given an input string s, reverse the order of the words.
 * * A word is defined as a sequence of non-space characters. The words in s will
 * be separated by at least one space.
 * * Return a string of the words in reverse order concatenated by a single space.
 * * Note that s may contain leading or trailing spaces or multiple spaces between
 * two words. The returned string should only have a single space separating the
 * words. Do not include any extra spaces.
 * * Example 1:
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 * * Example 2:
 * Input: s = "  hello world  "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 * * Example 3:
 * Input: s = "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single
 * space in the reversed string.
 * * Constraints:
 * - 1 <= s.length <= 10^4
 * - s contains English letters (upper-case and lower-case), digits, and spaces ' '.
 * - There is at least one word in s.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class ReverseWordsInString {

    /**
     * ========================================================================
     * Phase 1: Brute Force / Built-in Methods - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The simplest way to handle this in Java is to leverage the language's
     * powerful built-in String methods. We can remove leading and trailing
     * spaces using `trim()`, and then split the string into an array of words
     * using a regular expression `\\s+` (which matches one or more spaces).
     * Once we have the array of words, we simply iterate from the end of the
     * array to the beginning, appending each word to a StringBuilder.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. `trim()`,
     * `split()`, and the final iteration all take linear time.
     * - Space Complexity: O(N) Auxiliary Heap Space. The `split()` method
     * creates a new array of strings, and the StringBuilder requires O(N)
     * space to construct the final output.
     * ========================================================================
     */
    public static String reverseWordsBuiltIn(String s) {
        // Step 1: Trim leading/trailing spaces and split by 1 or more spaces
        String[] words = s.trim().split("\\s+");

        // Step 2: Use StringBuilder for efficient string concatenation
        StringBuilder sb = new StringBuilder();

        // Step 3: Iterate backwards through the array
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) {
                sb.append(" "); // Append space between words, but not at the end
            }
        }

        return sb.toString();
    }

    /**
     * ========================================================================
     * Phase 2: Two Pointers - The "Refine it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The built-in approach is clean, but relies heavily on regex compilation
     * and creates an intermediate String array, which adds overhead.
     * We can optimize this by using a Two-Pointer approach. We start from the
     * right side of the string and move leftwards. We use two pointers to mark
     * the start and end of each word. When we find a word, we extract it using
     * `substring()` and append it to our StringBuilder. This avoids the regex
     * overhead and the intermediate array completely.
     * * Complexity Analysis:
     * - Time Complexity: O(N). We iterate through the string exactly once from
     * right to left. `substring()` takes time proportional to the word length,
     * summing up to O(N) overall.
     * - Space Complexity: O(N) Auxiliary Heap Space. In Java, Strings are
     * immutable, so we fundamentally require O(N) space for the StringBuilder
     * to return the result.
     * ========================================================================
     */
    public static String reverseWordsTwoPointers(String s) {
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;

        while (i >= 0) {
            // Skip trailing spaces
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }

            // If we've reached the beginning of the string and it was just spaces
            if (i < 0) {
                break;
            }

            // i is currently at the last character of a word
            int right = i;

            // Move i to the left to find the start of the word
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }

            // i is now at the space before the word (or -1)
            // Append the word we just found
            if (sb.length() > 0) {
                sb.append(" "); // Add a space if this isn't the first word appended
            }
            sb.append(s.substring(i + 1, right + 1));
        }

        return sb.toString();
    }

    /**
     * ========================================================================
     * Phase 3: Character Array In-Place Manipulation - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * In languages like C++, strings are mutable, meaning this problem can be
     * solved in O(1) space. The classic algorithm is:
     * 1. Reverse the entire string.
     * 2. Reverse each individual word.
     * 3. Clean up extra spaces.
     * While Java strings are immutable (requiring an initial O(N) conversion to
     * a char[]), implementing this logic demonstrates deep algorithmic mastery
     * to an interviewer and shows you understand how to achieve O(1) auxiliary
     * space in lower-level languages.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Reversing the whole array, reversing words, and
     * cleaning spaces all take linear passes.
     * - Space Complexity: O(N) Heap Space strictly to hold the `char[]` and
     * return the new String. However, the algorithm itself utilizes O(1)
     * auxiliary space.
     * ========================================================================
     */
    public static String reverseWordsCharArray(String s) {
        // Convert to mutable char array
        char[] a = s.toCharArray();
        int n = a.length;

        // Step 1: Reverse the entire string array
        reverse(a, 0, n - 1);

        // Step 2: Reverse each individual word
        reverseWords(a, n);

        // Step 3: Clean up spaces and return the final string
        return cleanSpaces(a, n);
    }

    // Helper to reverse a portion of the char array
    private static void reverse(char[] a, int i, int j) {
        while (i < j) {
            char t = a[i];
            a[i++] = a[j];
            a[j--] = t;
        }
    }

    // Helper to find word boundaries and reverse them
    private static void reverseWords(char[] a, int n) {
        int i = 0, j = 0;
        while (i < n) {
            while (i < j || i < n && a[i] == ' ') i++; // skip spaces
            while (j < i || j < n && a[j] != ' ') j++; // skip non spaces
            reverse(a, i, j - 1);                      // reverse the word
        }
    }

    // Helper to shift characters to remove extra spaces
    private static String cleanSpaces(char[] a, int n) {
        int i = 0, j = 0;
        while (j < n) {
            while (j < n && a[j] == ' ') j++;             // skip spaces
            while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
            while (j < n && a[j] == ' ') j++;             // skip spaces
            if (j < n) a[i++] = ' ';                      // keep only one space
        }
        return new String(a).substring(0, i);
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "the sky is blue",          // Standard case
                "  hello world  ",          // Leading and trailing spaces
                "a good   example",         // Multiple spaces between words
                "singleword",               // Single word
                "   ",                      // Only spaces (edge case)
                "A"                         // Single character
        };

        System.out.println("Running test cases...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            String res1 = reverseWordsBuiltIn(input);
            String res2 = reverseWordsTwoPointers(input);
            String res3 = reverseWordsCharArray(input);

            System.out.println("Phase 1 (Built-In)  : \"" + res1 + "\"");
            System.out.println("Phase 2 (2 Pointers): \"" + res2 + "\"");
            System.out.println("Phase 3 (Char Array): \"" + res3 + "\"");

            // Validation
            if (res1.equals(res2) && res2.equals(res3)) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗\n");
            }
        }
    }
}
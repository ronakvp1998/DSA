package strivers.slidingwind2pointer;

/**
 * Role: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * ============================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ============================================================================
 * Problem Statement: 125. Valid Palindrome
 * * A phrase is a palindrome if, after converting all uppercase letters into
 * lowercase letters and removing all non-alphanumeric characters, it reads
 * the same forward and backward. Alphanumeric characters include letters and numbers.
 * * Given a string s, return true if it is a palindrome, or false otherwise.
 * * Example 1:
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * * Example 2:
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 * * Example 3:
 * Input: s = " "
 * Output: true
 * Explanation: s is an empty string "" after removing non-alphanumeric characters.
 * Since an empty string reads the same forward and backward, it is a palindrome.
 * * Constraints:
 * - 1 <= s.length <= 2 * 10^5
 * - s consists only of printable ASCII characters.
 * * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ============================================================================
 * Phase 1: Optimal Approach - Two Pointers moving from outside in.
 * Phase 2: Brute Force Approach - Filter string, reverse, and compare.
 * Phase 3: Alternative Approach - Java 8 Streams API for functional purity.
 */

public class ValidPalindrome {

    /**
     * ============================================================================
     * Phase 1: Optimal Approach (Two Pointers)
     * ============================================================================
     * Detailed Intuition:
     * Instead of allocating memory to build a new filtered string, we can evaluate
     * the string in-place. We initialize two pointers: one at the beginning ('left')
     * and one at the end ('right'). We advance 'left' forward and 'right' backward
     * until they point to alphanumeric characters. We then compare these characters
     * (ignoring case). If they mismatch, it's not a palindrome. We repeat until
     * the pointers cross.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the string. In the worst case,
     * we traverse each character exactly once.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive integer
     * variables for our pointers, requiring no extra heap space or recursive stack
     * space regardless of the input size.
     */
    public boolean isPalindromeOptimal(String s) {
        if (s == null || s.isEmpty()) return true;

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Move left pointer to the next alphanumeric character
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // Move right pointer to the previous alphanumeric character
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            // Advance both pointers
            left++;
            right--;
        }

        return true;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach (String Building)
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to literally do what the problem
     * description says. We iterate through the original string, extract only the
     * valid alphanumeric characters, convert them to lowercase, and append them to
     * a new sequence. Finally, we reverse this new sequence and check if it exactly
     * matches the unreversed sequence.
     * * Complexity Analysis:
     * - Time Complexity: O(N) where N is the length of the string. We iterate
     * through the string once to build the new string, and once more to reverse
     * and compare.
     * - Space Complexity: O(N) Heap Space. We allocate a StringBuilder and
     * subsequently String objects that scale linearly with the number of
     * alphanumeric characters in the input. Auxiliary stack space is O(1).
     */
    public boolean isPalindromeBruteForce(String s) {
        if (s == null) return false;

        StringBuilder cleaned = new StringBuilder();

        // Filter and lowercase
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }

        // Compare with reversed version
        String original = cleaned.toString();
        String reversed = cleaned.reverse().toString();

        return original.equals(reversed);
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach (Java 8 Stream API)
     * ============================================================================
     * Detailed Intuition:
     * For a declarative, functional approach, we can utilize Java 8 Streams.
     * We convert the string into an IntStream of characters, filter out the
     * non-alphanumeric ones, and map them to lowercase. After collecting this into
     * a clean string, we use another IntStream to match the first half of the string
     * symmetrically with the second half using `noneMatch`.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Stream processing still evaluates each character.
     * - Space Complexity: O(N) Heap Space. The Stream API creates intermediate
     * buffers, and we collect the result into a new String of up to size N.
     */
    public boolean isPalindromeStreams(String s) {
        if (s == null) return false;

        // Clean the string using Streams
        String cleaned = s.chars()
                .filter(Character::isLetterOrDigit)
                .map(Character::toLowerCase)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // Check palindrome symmetry using IntStream
        int len = cleaned.length();
        return java.util.stream.IntStream.range(0, len / 2)
                .noneMatch(i -> cleaned.charAt(i) != cleaned.charAt(len - i - 1));
    }

    // regex
    private static boolean isPalindrome(String s) {
        // Convert the string to lowercase and remove non-alphanumeric characters.
        String cleanedString = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Check if the cleaned string is a palindrome.
        int left = 0;
        int right = cleanedString.length() - 1;

        while (left < right) {
            if (cleanedString.charAt(left) != cleanedString.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Main method to thoroughly test all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        ValidPalindrome solution = new ValidPalindrome();

        // Test Cases Mapping: [Input String, Expected Result]
        String[][] testCases = {
                {"A man, a plan, a canal: Panama", "true"},   // Standard valid
                {"race a car", "false"},                      // Standard invalid
                {" ", "true"},                                // Edge: Only spaces
                {".,", "true"},                               // Edge: Only punctuation
                {"0P", "false"},                              // Edge: Numbers and letters mixed
                {"a.", "true"},                               // Edge: Single char with punctuation
                {"", "true"}                                  // Edge: Empty string
        };

        System.out.println("Running test suite...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i][0];
            boolean expected = Boolean.parseBoolean(testCases[i][1]);

            boolean optimalRes = solution.isPalindromeOptimal(input);
            boolean bruteRes = solution.isPalindromeBruteForce(input);
            boolean streamRes = solution.isPalindromeStreams(input);

            boolean passed = (optimalRes == expected) &&
                    (bruteRes == expected) &&
                    (streamRes == expected);

            System.out.printf("Test Case %d: \"%s\"\n", i + 1, input);
            System.out.printf("  Expected : %b\n", expected);
            System.out.printf("  Optimal  : %b\n", optimalRes);
            System.out.printf("  Brute    : %b\n", bruteRes);
            System.out.printf("  Streams  : %b\n", streamRes);
            System.out.printf("  Status   : %s\n\n", passed ? "PASS" : "FAIL");
        }
    }
}
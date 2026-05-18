package strivers.string.medium;

/**
 * ============================================================================
 * 1781. Sum of Beauty of All Substrings
 * ============================================================================
 * The beauty of a string is the difference in frequencies between the most
 * frequent and least frequent characters.
 * * For example, the beauty of "abaacc" is 3 - 1 = 2.
 * * Given a string s, return the sum of beauty of all of its substrings.
 * * Example 1:
 * Input: s = "aabcb"
 * Output: 5
 * Explanation: The substrings with non-zero beauty are
 * ["aab","aabc","aabcb","abcb","bcb"], each with beauty equal to 1.
 * * Example 2:
 * Input: s = "aabcbaa"
 * Output: 17
 * * Constraints:
 * 1 <= s.length <= 500
 * s consists of only lowercase English letters.
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
public class SumOfBeautyOfAllSubstrings {
    /**
     * ========================================================================
     * Phase 2: Expanding Window (Optimized Iteration) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * This optimized expanding window pattern is exactly what interviewers at
     * product-based companies are looking for when evaluating your constraint-solving
     * skills.
     * Notice that when we expand a substring from `s[i...j]` to `s[i...j+1]`,
     * we are only adding ONE new character. Re-evaluating the entire substring
     * from scratch is redundant.
     * We can anchor our left pointer `i`. As we move our right pointer `j`
     * one step at a time, we simply update our existing frequency array in O(1)
     * time by incrementing the count of `s[j]`.
     * After that O(1) update, we spend a constant 26 operations to find the
     * new max and min.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2 * 26) -> O(N^2). The nested loops run N^2 times.
     * Inside the inner loop, finding the max/min takes exactly 26 operations.
     * For N=500, 500^2 * 26 is roughly 6.5 million operations, which executes
     * lightning fast in Java (well under the 1-second limit).
     * - Space Complexity: O(1) Auxiliary Heap Space. We allocate one integer
     * array of size 26 for each outer loop iteration, utilizing strictly
     * constant memory footprint.
     * ========================================================================
     */
    public static int beautySumOptimal(String s) {
        int n = s.length();
        int totalBeauty = 0;

        // i is the starting index of the substring
        for (int i = 0; i < n; i++) {
            // Frequency array for substrings starting at i
            int[] freq = new int[26];

            // j is the ending index. We expand the window to the right.
            for (int j = i; j < n; j++) {
                // Update the frequency of the newly added character
                freq[s.charAt(j) - 'a']++;

                // Calculate and add the beauty of the current valid substring
                totalBeauty += calculateBeauty(freq);
            }
        }

        return totalBeauty;
    }

    // Helper function to find the difference between max and min frequencies
    private static int calculateBeauty(int[] freq) {
        int maxFreq = 0;
        int minFreq = Integer.MAX_VALUE;

        for (int f : freq) {
            if (f > 0) {
                maxFreq = Math.max(maxFreq, f);
                minFreq = Math.min(minFreq, f);
            }
        }

        // If the substring has all unique characters or only one type of character,
        // minFreq will equal maxFreq, resulting in a beauty of 0.
        return maxFreq - minFreq;
    }

    /**
     * ========================================================================
     * (Skip this) Phase 1: Brute Force Approach - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most literal translation of the problem is to generate every single
     * valid substring, independently calculate its beauty, and sum it up.
     * For every starting index `i` and ending index `j`, we extract the substring,
     * iterate through its characters to build a frequency map, and then find
     * the maximum and minimum frequencies.
     * * Complexity Analysis:
     * - Time Complexity: O(N^3). Generating the substring bounds takes O(N^2).
     * Iterating through the substring to build the frequency map takes up to O(N).
     * Total time is cubic, which would Time Limit Exceed (TLE) for constraints
     * larger than ~300.
     * - Space Complexity: O(N) Auxiliary Heap Space to allocate the substring
     * string objects during extraction, plus O(1) for the frequency array of
     * size 26.
     * ========================================================================
     */
    public static int beautySumBruteForce(String s) {
        int n = s.length();
        int totalBeauty = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Extract substring
                String sub = s.substring(i, j + 1);

                // Build frequency array for this specific substring
                int[] freq = new int[26];
                for (int k = 0; k < sub.length(); k++) {
                    freq[sub.charAt(k) - 'a']++;
                }

                // Calculate beauty
                totalBeauty += calculateBeauty(freq);
            }
        }

        return totalBeauty;
    }


    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "aabcb",        // Example 1: Standard case with mixed frequencies
                "aabcbaa",      // Example 2: Longer overlapping frequency shifts
                "a",            // Edge Case: Single character (Beauty 0)
                "aaaa",         // Edge Case: All identical characters (Beauty 0)
                "abcd",         // Edge Case: All unique characters (Beauty 0)
                "abacdba"       // Complex distribution
        };

        System.out.println("Running test cases for 1781. Sum of Beauty of All Substrings...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            int res1 = beautySumBruteForce(input);
            int res2 = beautySumOptimal(input);

            System.out.println("Phase 1 (Brute Force)      : " + res1);
            System.out.println("Phase 2 (Expanding Window) : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
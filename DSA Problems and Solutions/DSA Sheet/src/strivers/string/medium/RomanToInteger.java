package com.questions.strivers.string.medium;

/**
 * ============================================================================
 * 13. Roman to Integer
 * ============================================================================
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * * For example, 2 is written as II in Roman numeral, just two ones added together.
 * 12 is written as XII, which is simply X + II. The number 27 is written as XXVII,
 * which is XX + V + II.
 * * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is written
 * as IV. Because the one is before the five we subtract it making four. The same
 * principle applies to the number nine, which is written as IX. There are six
 * instances where subtraction is used:
 * - I can be placed before V (5) and X (10) to make 4 and 9.
 * - X can be placed before L (50) and C (100) to make 40 and 90.
 * - C can be placed before D (500) and M (1000) to make 400 and 900.
 * * Given a roman numeral, convert it to an integer.
 * * Example 1:
 * Input: s = "III"
 * Output: 3
 * Explanation: III = 3.
 * * Example 2:
 * Input: s = "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 * * Example 3:
 * Input: s = "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 * * Constraints:
 * - 1 <= s.length <= 15
 * - s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
 * - It is guaranteed that s is a valid roman numeral in the range [1, 3999].
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP
 * ============================================================================
 */
import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {

    /**
     * ========================================================================
     * Phase 1: Standard Approach (HashMap & Left-to-Right) - The "Think it" stage.
     * ========================================================================
     * Detailed Intuition:
     * The most intuitive way to map characters to their integer values is using
     * a HashMap. As we read the Roman numeral from left to right, the general rule
     * is to simply add the value of the current character to our total.
     * However, there's one exception: if a smaller numeral appears BEFORE a larger
     * numeral (e.g., 'I' before 'V' in "IV"), it means subtraction.
     * Therefore, at each step `i`, we check if the value at `i` is less than
     * the value at `i + 1`. If it is, we subtract its value from the total.
     * Otherwise, we add it.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the string. We iterate
     * through the string exactly once. HashMap lookups take O(1) time. Since
     * the maximum length is 15, this runs essentially in O(1) constant time,
     * but scales linearly with string length mathematically.
     * - Space Complexity: O(1) Auxiliary Heap Space. The HashMap stores exactly
     * 7 key-value pairs regardless of the input size, which uses constant extra
     * memory.
     * ========================================================================
     */
    public static int romanToIntHashMap(String s) {
        // Step 1: Initialize the dictionary of Roman symbols
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int total = 0;
        int n = s.length();

        // Step 2: Iterate left to right
        for (int i = 0; i < n; i++) {
            int currentVal = romanMap.get(s.charAt(i));

            // If there is a next character, and the current value is less than the next,
            // we have encountered a subtraction case (e.g., IV, IX).
            if (i < n - 1 && currentVal < romanMap.get(s.charAt(i + 1))) {
                total -= currentVal;
            } else {
                total += currentVal; // Normal case
            }
        }

        return total;
    }

    /**
     * ========================================================================
     * Phase 2: Optimal Approach (Switch Statement & Right-to-Left) - The "Perfect it" stage.
     * ========================================================================
     * Detailed Intuition:
     * As a Senior Developer, we want to strip away overhead. HashMaps require
     * object wrappers (`Character`, `Integer`) and hashing computation, which is
     * slow compared to primitives. We can replace the HashMap with a fast `switch`
     * statement.
     * Furthermore, iterating from **Right-to-Left** is much cleaner mentally.
     * As we move right-to-left, the numbers should continuously get larger or stay
     * the same. If we suddenly see a smaller number, we instantly know it's a
     * subtraction case. We just need to keep track of the `previous` value we saw.
     * * Complexity Analysis:
     * - Time Complexity: O(N). We traverse the string exactly once. The `switch`
     * statement evaluates in highly optimized O(1) time without object boxing.
     * - Space Complexity: O(1) Auxiliary Stack Space. We only use primitive
     * integer variables (`total`, `current`, `prev`), utilizing absolutely
     * zero extra heap allocations.
     * ========================================================================
     */
    public static int romanToIntOptimal(String s) {
        int total = 0;
        int prev = 0;

        // Traverse right-to-left
        for (int i = s.length() - 1; i >= 0; i--) {
            int current = 0;

            // Highly optimized mapping using primitives
            switch (s.charAt(i)) {
                case 'I': current = 1; break;
                case 'V': current = 5; break;
                case 'X': current = 10; break;
                case 'L': current = 50; break;
                case 'C': current = 100; break;
                case 'D': current = 500; break;
                case 'M': current = 1000; break;
            }

            // If the current numeral is smaller than the previous one we processed
            // (which was to its right), it must be subtracted.
            if (current < prev) {
                total -= current;
            } else {
                total += current;
            }

            // Update prev for the next iteration
            prev = current;
        }

        return total;
    }

    /**
     * ========================================================================
     * Testing Suite
     * ========================================================================
     */
    public static void main(String[] args) {
        String[] testCases = {
                "III",         // Standard addition only (3)
                "LVIII",       // Multiple characters addition (58)
                "MCMXCIV",     // Complex subtraction cases (1994)
                "IV",          // Simple subtraction (4)
                "IX",          // Simple subtraction (9)
                "MMMCMXCIX",   // Large complex number (3999)
                "D"            // Single character (500)
        };

        System.out.println("Running test cases for 13. Roman to Integer...\n");

        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            System.out.println("Test Case " + (i + 1) + ": \"" + input + "\"");

            int res1 = romanToIntHashMap(input);
            int res2 = romanToIntOptimal(input);

            System.out.println("Phase 1 (HashMap LR)  : " + res1);
            System.out.println("Phase 2 (Switch RL)   : " + res2);

            // Validation step
            if (res1 == res2) {
                System.out.println("-> ALL PASS ✓\n");
            } else {
                System.out.println("-> FAIL ✗ (Outputs differ)\n");
            }
        }
    }
}
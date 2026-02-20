package com.questions.strivers.string.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * ==================================================================================================
 * APPROACH: Single Pass with Look-Ahead
 * ==================================================================================================
 * 1. Store Roman character values in a Map for O(1) lookup.
 * 2. Traverse the string once.
 * 3. Use the logic: If current value is less than the next, it's a subtraction case.
 * ==================================================================================================
 */
public class RomanToInteger {

    public static int romanToInt(String s) {
        // Step 1: Initialize the mapping
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

        // Step 2: Iterate through the string
        for (int i = 0; i < n; i++) {
            int currentVal = romanMap.get(s.charAt(i));

            // Step 3: Look-ahead to check for subtraction
            // If there's a next character and current < next, subtract
            if (i + 1 < n && currentVal < romanMap.get(s.charAt(i + 1))) {
                total -= currentVal;
            } else {
                // Otherwise, simply add
                total += currentVal;
            }
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println("MCMXCIV: " + romanToInt("MCMXCIV")); // Output: 1994
        System.out.println("LVIII:   " + romanToInt("LVIII"));   // Output: 58
        System.out.println("IX:      " + romanToInt("IX"));      // Output: 9
    }
}
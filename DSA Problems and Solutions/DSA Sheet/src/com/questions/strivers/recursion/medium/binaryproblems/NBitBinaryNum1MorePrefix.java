package com.questions.strivers.recursion.medium.binaryproblems;

import java.util.*;

/*
Problem Statement:
------------------
Given an integer N, generate all N-bit binary numbers such that:
    - At every prefix of the binary number, the count of '1's is greater than or equal to the count of '0's.
    - In simpler terms, in any prefix of the binary string, the number of 1s must never be less than the number of 0s.

Example:
--------
For N = 4, valid binary numbers are:
    1111
    1110
    1101
    1100
    1011
    1010

Notice that in every prefix, 1s are always >= 0s.

Approach:
---------
We use recursion with backtracking:
1. At every recursive step, we try to add either '1' or '0' to the current binary string.
2. '1' can always be added (since it only increases the count of ones).
3. '0' can only be added if (ones > zeros), ensuring the prefix condition is satisfied.
4. The recursion continues until the length of the current string equals N.
5. When the length reaches N, we add the string to the result list.

Time Complexity:
----------------
- In the worst case, the recursion explores O(2^N) paths (like generating all binary numbers).
- However, due to the constraint (ones >= zeros), many invalid paths are pruned early.
- So the number of valid binary numbers is fewer than 2^N, but upper bound complexity is still O(2^N).
- Each valid number takes O(N) time to construct (string concatenation).
- Overall: O(N * 2^N) in the worst case.

Space Complexity:
-----------------
- Recursion depth = N (stack space) → O(N).
- Result list stores all valid binary numbers. In worst case, this can be O(2^N) numbers, each of length N → O(N * 2^N).
*/

public class NBitBinaryNum1MorePrefix {

    // Recursive function to generate N-bit binary numbers
    public static void generateBinary(int n, int ones, int zeros, String output, List<String> result) {
        // Base case: if output string has reached length n, add it to result
        if (output.length() == n) {
            result.add(output);
            return;
        }

        // Always allowed to add '1' (increases ones count, so prefix condition remains valid)
        generateBinary(n, ones + 1, zeros, output + "1", result);

        // Allowed to add '0' only when ones > zeros
        // This ensures no prefix has more zeros than ones
        if (ones > zeros) {
            generateBinary(n, ones, zeros + 1, output + "0", result);
        }
    }

    public static void main(String[] args) {
        int n = 4;  // Example: generate all valid 4-bit binary numbers
        List<String> result = new ArrayList<>();

        // Start recursion with empty string and 0 ones/zeros
        generateBinary(n, 0, 0, "", result);

        // Print all valid binary numbers
        for (String binary : result) {
            System.out.println(binary);
        }
    }
}

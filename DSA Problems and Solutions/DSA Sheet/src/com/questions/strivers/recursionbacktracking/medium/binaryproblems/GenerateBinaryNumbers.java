package com.questions.strivers.recursionbacktracking.medium.binaryproblems;

import java.util.ArrayList;
import java.util.List;

/*
Problem Statement:
------------------
Given an integer N, generate all possible binary numbers of length N.
A binary number is a sequence consisting only of '0' and '1'.

Example:
--------
Input: N = 3
Output: [000, 001, 010, 011, 100, 101, 110, 111]

Explanation:
There are 2^3 = 8 binary numbers of length 3, listed above.

Approach:
---------
We solve this using recursion (backtracking):
1. Start with an empty string (output = "").
2. At each step, append either '0' or '1' to the current string.
3. Continue recursion until the string length = N.
4. Once the length is N, add the string to the result list.
5. Recursion tree ensures that all 2^N combinations are generated.

Time Complexity:
----------------
- There are 2^N possible binary strings of length N.
- Each string construction takes O(N) time (due to string concatenation).
- Overall time complexity: O(N * 2^N).

Space Complexity:
-----------------
- Recursion depth = N → O(N) stack space.
- Result list stores all binary numbers: O(N * 2^N).
- Hence, total space complexity = O(N * 2^N).
*/

public class GenerateBinaryNumbers {
    public static void main(String[] args) {
        int n = 3;  // Example: Generate all 3-bit binary numbers
        List<String> result = new ArrayList<>();

        // Start recursion with empty string
        generateBinary(n, "", result);

        // Print all generated binary numbers
        System.out.println(result);
    }

    // Recursive function to generate all binary strings of length n
    private static void generateBinary(int n, String output, List<String> result) {
        // Base case: if output length reaches n, add it to result
        if (output.length() == n) {
            result.add(output);
            return;
        }

        // Choice 1: Append '0'
        generateBinary(n, output + "0", result);

        // Choice 2: Append '1'
        generateBinary(n, output + "1", result);
    }
}

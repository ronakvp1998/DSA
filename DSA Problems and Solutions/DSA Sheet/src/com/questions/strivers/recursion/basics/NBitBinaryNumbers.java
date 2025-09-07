package com.questions.strivers.recursion.basics;

import java.util.*;

public class NBitBinaryNumbers {

    // Function to generate N-bit binary numbers
    public static void generateBinary(int n, int ones, int zeros, String output, List<String> result) {
        // Base case: if length of output is equal to n, add to result
        if (output.length() == n) {
            result.add(output);
            return;
        }

        // Always allowed to add '1' (since it increases ones count)
        generateBinary(n, ones + 1, zeros, output + "1", result);

        // We can only add '0' if ones > zeros (ensures no prefix violates the rule)
        if (ones > zeros) {
            generateBinary(n, ones, zeros + 1, output + "0", result);
        }
    }

    public static void main(String[] args) {
        int n = 4;  // Example: generate 4-bit numbers
        List<String> result = new ArrayList<>();

        // Start recursion with empty string
        generateBinary(n, 0, 0, "", result);

        // Print all valid binary numbers
        for (String binary : result) {
            System.out.println(binary);
        }
    }
}

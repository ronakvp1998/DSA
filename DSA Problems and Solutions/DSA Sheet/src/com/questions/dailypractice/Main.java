package com.questions.dailypractice;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> result = new ArrayList<>();
        generateBinary(3,0,0,"",result);
        System.out.println(result);
    }

    public static void generateBinary(int n, int ones, int zeros, String output, List<String> result) {
        // Base case: if output string has reached length n, add it to result
        if (output.length() == n) {
            result.add(output);
            return;
        }

        // Always allowed to add '1' (increases ones count, so prefix condition remains valid)
        generateBinary(n, ones, zeros + 1, output + "0", result);
        generateBinary(n, ones + 1, zeros, output + "1", result);

    }

}

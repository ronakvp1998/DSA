package com.questions.dailypractice;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> result = new ArrayList<>();
        generateBinary(3,"",result);
        System.out.println(result);
    }

    public static void generateBinary(int n,  String output, List<String> result) {
        if (output.length() == n) {
            result.add(output);
            return;
        }

        generateBinary(n, output + "0", result);
        generateBinary(n, output + "1", result);

    }

}

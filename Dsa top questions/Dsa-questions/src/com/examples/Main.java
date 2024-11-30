package com.examples;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(compress("abbbccdddde".toCharArray()));

        // [a, b, 3, c, 2, d, 4, e, d, d, e]
    }

    public static int compress(char[] chars){
        int i = 0;
        int newIndex = 0;
        while (i<chars.length){
            char compressedChar = chars[i];
            int count = 0;
            while (i<chars.length && ){

            }
        }

    }
}

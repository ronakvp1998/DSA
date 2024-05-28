package org.example;

public class ClearLastIBits {

    public static void main(String[] args) {
        int n = 15;
        int i = 2;
        int bitMask = (-1) << i;
        System.out.println(n & bitMask);
    }
}

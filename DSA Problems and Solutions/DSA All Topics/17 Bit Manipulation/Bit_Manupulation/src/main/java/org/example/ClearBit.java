package org.example;

// Q3 Clear bit
public class ClearBit {

    public static void main(String[] args) {
        int n = 10;
        int ith = 1;
        int bitMask = ~(1<<ith);

        System.out.println(n & bitMask);
    }
}

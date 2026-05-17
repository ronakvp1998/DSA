package org.example;

// Q3) Set ith bit
public class SetBit {

    public static void main(String[] args) {
        int n = 10;
        int ith = 2;
        int biMask = 1<<ith;

        System.out.println(n | biMask);
    }
}

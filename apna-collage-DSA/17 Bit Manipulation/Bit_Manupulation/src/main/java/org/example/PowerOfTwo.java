package org.example;

// Code 8 check if number is power of 2 or not
public class PowerOfTwo {

    public static void main(String[] args) {
        int n = 12;

        System.out.println((n & (n-1)) == 0 ? "Power of 2 " : "not power of 2");
    }
}

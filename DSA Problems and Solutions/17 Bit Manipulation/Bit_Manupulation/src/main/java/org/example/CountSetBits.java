package org.example;

public class CountSetBits {

    public static void main(String[] args) {
        int count = 0;
        int n = 10;

        while (n>0){
            // compare LSB
            if((n & 1) != 0){
                count++;
            }
            n = n>>1;
        }

        System.out.println("Count of 1 in n is " + count);
    }
}

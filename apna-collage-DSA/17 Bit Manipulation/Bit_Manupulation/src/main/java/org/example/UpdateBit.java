package org.example;

public class UpdateBit {

    public static void main(String[] args) {
        int n = 10;
        int ith = 1;
        int update = 0;

        if(update == 0){
            // clear ith bit
            int bitMask = ~(1<<ith);
            System.out.println(n & bitMask);
        }else {
            // set ith bit
            int biMask = 1<<ith;
            System.out.println(n | biMask);
        }
    }
}

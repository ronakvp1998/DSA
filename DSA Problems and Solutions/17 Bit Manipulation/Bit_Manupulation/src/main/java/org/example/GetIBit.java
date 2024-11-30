package org.example;

// Q2 get ith bit of a number
public class GetIBit {

    public static void main(String[] args) {
        int number = 10;
        int ith = 3;
        int bitMask = 1<<ith;

        if((number&bitMask) >0 ){
            System.out.println("ith bit: " + 1);
        }else{
            System.out.println("ith bit: " + 0);
        }

    }

}

package org.example;

// Q1  check if num is Even or Odd
public class EvenOdd {
    public static void main(String[] args) {
        int num = 10;
        int bitMask = 1;

        if((num&bitMask) == 1){
            System.out.println("odd");
        }else{
            System.out.println("Even");
        }
    }
}

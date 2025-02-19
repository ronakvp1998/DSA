package com.questions.strivers.recursion.basics;

public class SumN {

    public static void main(String[] args) {
//        sum1(6,0);
        System.out.println(sum2(6));
    }

    //parametrized way
    public static void sum1(int index, int sum){
        if(index < 1){
            System.out.println(sum);
            return;
        }
        sum1(index-1,sum+index);
    }

    // functional way
    public static int sum2(int n){
        if(n == 0){
            return 0;
        }
        return n + sum2(n-1);
    }
}

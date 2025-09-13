package com.questions.strivers.recursionbacktracking.basics;

public class SumN2 {
    public static void main(String[] args) {
        sum2(5,0);
    }

    // parametrized approach
    public static void sum2(int n,int sum){
        if(n == 0){
            sum = sum + 0;
            System.out.println(sum);
            return;
        }
        sum2(n-1,sum + (int)Math.pow(n,3));
    }


    // functional approach
    public static int sum22(int n){
        if(n == 0){
            return 0;
        }
        return (n*n*n) + sum22(n-1);
    }


}

package com.questions.strivers.recursion.basics;

public class FibonacciSeries {
    public static void main(String[] args) {
        int n = 4;
//        System.out.println(fibonacciSer(n));
       fibonacciSer1(n,0);
    }

    // parametrized approach
    public static void fibonacciSer1(int n, int res){
        if(n <= 1 ){
            return;
        }
        fibonacciSer1(n-1,res+(n-1));
        fibonacciSer1(n-2,res+(n-2));
        System.out.println(res);
    }

    // functional approach
    public static int fibonacciSer(int n){
        if(n <= 1) return n;
        int last = fibonacciSer(n-1);
        int secondLast = fibonacciSer(n-2);
        return last + secondLast;
    }
}

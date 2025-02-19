package com.questions.practice.recursion;

public class BasicProblems {

    public static void main(String[] args) {
//        printNameNTimes("Ronak",5);
//        print1ToN(10);
//        printNTo1(10);
//        sumNNumbers1(10,0);
        System.out.println(sumNNumbers2(10));

    }

    public static int sumNNumbers2(int n){
        if(n == 0 || n == 1){
            return n;
        }
        return n + sumNNumbers2(n-1);
    }

    public static void sumNNumbers1(int n,int sum){
        if(n==0){
            System.out.println(sum);
            return;
        }
        sum = sum + n;
        sumNNumbers1(n-1,sum);
    }


    public static void printNTo1(int n){
        if(n == 0){
            return;
        }
        System.out.println(n);
        printNTo1(n-1);
    }

    public static void print1ToN(int n){
        if(n == 0){
            return;
        }
        print1ToN(n-1);
        System.out.println(n);
    }

    public static void printNameNTimes(String s,int n){
        if(n == 0){
            return ;
        }
        printNameNTimes(s,n-1);
        System.out.println(s);

    }
}

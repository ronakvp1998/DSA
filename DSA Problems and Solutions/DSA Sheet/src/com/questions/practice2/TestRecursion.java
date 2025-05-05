package com.questions.practice2;

import java.util.ArrayList;

public class TestRecursion {
    public static void main(String[] args) {
//        int n = 5;
//        printN(n);
//        print1ToN(n);
//        printNto1(n);
//        parametrized(5,0);
//        System.out.println(functional(5));
        int arr[] = {1,2,1};
        int sum = 2;
        System.out.println(countSubseq(0,arr,arr.length,sum,0));
    }

    public static ArrayList<Long> factorialNum(int n){
        ArrayList<Long> list = new ArrayList<>();
        fact(n,list,1,2);
        return list;
    }

    private static void fact(long n, ArrayList<Long> list, long res, long i) {
        if(res > n){
            return;
        }
        list.add(res);
        fact(n,list,(res * i),i+1);
    }


    public static int countSubseq(int index,int arr[],int n,int sum,int res){
        if(index == n){
            if(res == sum){
                return 1;
            }else{
                return 0;
            }
        }
        res = res + arr[index];
        int left = countSubseq(index+1,arr,n,sum,res);

        res = res - arr[index];
        int right = countSubseq(index + 1,arr,n,sum,res);

        return left + right;
    }

    public static int functional(int n){
        if(n == 0){
            return 0;
        }
        return n + functional(n-1);
    }

    public static void parametrized(int i, int sum){
        if(i < 1){
            System.out.println(sum);
            return;
        }
        parametrized(i-1,sum + i);
    }

    public static void printNto1(int n){
        if(n == 0){
            return;
        }
        System.out.println(n);
        printNto1(n-1);
    }

    public static void print1ToN(int n){
        if(n == 0){
            return;
        }
        print1ToN(n-1);
        System.out.println(n);
    }

    public static void printN(int n){
        if( n== 0){
            return;
        }
        System.out.println("Ronak");
        printN(n-1);
    }
}

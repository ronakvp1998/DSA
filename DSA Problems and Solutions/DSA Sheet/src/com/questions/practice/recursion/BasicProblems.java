package com.questions.practice.recursion;

import java.util.Arrays;

public class BasicProblems {

    public static void main(String[] args) {
//        printNameNTimes("Ronak",5);
//        print1ToN(10);
//        printNTo1(10);
//        sumNNumbers1(10,0);
//        System.out.println(sumNNumbers2(10));
//        System.out.println(factorialN(4));
//        factorialN2(4,1);
//        int arr[] = new int[]{1,2,3,4,5};
//        reverseArray(arr,0,arr.length-1);
//        String s = "madam";
//        System.out.println(checkPalindrome(s,0,s.length()-1));
//        int n = 10;
//        fibonacci1(n,0,1);

        int n = 10; // Number of Fibonacci terms to print
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacci2(i) + " ");
        }
    }

    // functional recursion
    public static int fibonacci2(int n){
        if(n <= 1){
            return n;
        }
        return fibonacci2(n-1) + fibonacci2(n-2);
    }

    // parametrized recursion
    public static void fibonacci(int n,int i,int j){
        if(n<=0){
            return;
        }
        System.out.print(i + " ");
        fibonacci(n-1,j,i+j);
    }


    public static boolean checkPalindrome(String s,int i,int j){
        if(i >= j){
            return true;
        }
        boolean res = s.charAt(i) == s.charAt(j);
        if(res == false){
            return false;
        }
        return checkPalindrome(s,i+1,j-1);
    }

    public static void reverseArray(int arr[], int i,int j){
        if(i > j){
            System.out.println(Arrays.toString(arr));
            return;
        }
        swap(arr,i,j);
        reverseArray(arr,i+1,j-1);
    }

    public static void swap(int arr[], int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void factorialN2(int n,int sum){
        if(n == 1){
            System.out.println(sum);
            return;
        }
        sum = sum * n ;
        factorialN2(n-1,sum);
    }

    public static int factorialN(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        return n * factorialN(n-1);
    }

    public static int sumNNumbers2(int n){
        if(n == 0 || n == 1){
            return 1;
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

package com.questions.strivers.recursionbacktracking.basics;

import java.util.Arrays;

public class Print1ToNLinearly {
    public static void main(String[] args) {
//        print1ToN(0,5);
        System.out.println(Arrays.toString(print1ToN2(0,5,new int [5])));
    }

    // functional way
    public static int[] print1ToN2(int i, int n, int[] arr){
        if(i == n){
            return arr;
        }
        arr[i] = i;
        return print1ToN2(i+1,n,arr);

    }

    // parametrised way
    public static void print1ToN(int i, int n){
        if( i== n){
            return;
        }
        System.out.println(i);
        print1ToN(i+1,n);
    }
}

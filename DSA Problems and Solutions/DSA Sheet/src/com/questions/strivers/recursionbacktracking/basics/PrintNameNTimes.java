package com.questions.strivers.recursionbacktracking.basics;

import java.util.Arrays;

public class PrintNameNTimes {
    public static void main(String[] args) {
        String name = "Ronak";
        int n = 4;
//        printName(name,n);
        System.out.println(Arrays.toString(printName1(name,n-1,new String[n])));
    }

    // functional
    public static String[] printName1(String name, int n, String[] res){
        if(n == 0){
            res[n] = name;
            return res;
        }
        res[n] = name;
        return printName1(name,n-1,res);

    }

    // parametized
    public static void printName(String name, int n){
        if(n == 0){
            return;
        }
        System.out.println(name);
        printName(name,n-1);
    }


}

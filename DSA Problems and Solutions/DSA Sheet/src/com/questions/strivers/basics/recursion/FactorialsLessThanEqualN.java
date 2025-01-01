package com.questions.strivers.basics.recursion;

import java.util.ArrayList;
import java.util.List;

public class FactorialsLessThanEqualN {

    public static void main(String[] args) {
        long n = 6;
        System.out.println(factorialNumbers(n));
    }


    // functional approach
    public static ArrayList<Long> factorialNumbers(long n){
        ArrayList<Long> list = new ArrayList<>();
         fact(n,list,1,2);
         return list;
    }

    public static void  fact( long n,List list,long res,long i){
        if(res > n){
            return;
        }
        list.add(res);
        fact(n,list, (res*i),i+1);
    }
}

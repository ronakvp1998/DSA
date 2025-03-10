package com.questions.practice.recursion;

import java.util.ArrayList;

public class Subsequence1 {
    public static void main(String[] args) {
        int arr[] = {3,1,2,4};
        int k = 4;
//        sequence1(arr,new ArrayList<>(),0);
//        sequence2(arr,new ArrayList<>(),0,0,k);
//        subsequence3(arr,new ArrayList<>(),0,0,k);
        System.out.println(subsequence4(arr,new ArrayList<>(),0,0,k,0));
    }

    public static int subsequence4(int arr[], ArrayList<Integer> res,int i,int sum,int k,int count){
        if(i >= arr.length){
            if(sum == k){
                System.out.println(res);
                return 1;
            }else{
                return 0;
            }
        }
        sum = sum + arr[i];
        res.add(arr[i]);
        int l = subsequence4(arr,res,i+1,sum,k,count);
        sum = sum - arr[i];
        res.remove(Integer.valueOf(arr[i]));
        int r = subsequence4(arr,res,i+1,sum,k,count);
        return l+r;
    }

    public static boolean subsequence3(int arr[], ArrayList<Integer> res,int i,int sum,int k){
        if(i >= arr.length){
            if(sum == k){
                System.out.println(res);
                return true;
            }
            return false;
        }
        res.add(arr[i]);
        sum = sum + arr[i];
        if(subsequence3(arr,res,i+1,sum,k) == true){
            return true;
        }
        res.remove(Integer.valueOf(arr[i]));
        sum = sum - arr[i];
        if(subsequence3(arr,res,i+1,sum,k) == true){
            return true;
        }
        return false;
    }

    public static void sequence2(int arr[], ArrayList<Integer> res,int i,int sum,int k){
        if(i >= arr.length){
            if(sum == k){
                System.out.println(res);
            }
            return;
        }
        res.add(arr[i]);
        sum = sum + arr[i];
        sequence2(arr,res,i+1,sum,k);
        res.remove(Integer.valueOf(arr[i]));
        sum = sum - arr[i];
        sequence2(arr,res,i+1,sum,k);
    }

    public static void sequence1(int arr[], ArrayList<Integer> res,int i){
        if(i >= arr.length){
            System.out.println(res);
            return;
        }
        res.add(arr[i]);
        sequence1(arr,res,i+1);;
        res.remove(Integer.valueOf(arr[i]));
        sequence1(arr,res,i+1);
    }

}

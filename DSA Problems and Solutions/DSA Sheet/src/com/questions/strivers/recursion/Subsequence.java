package com.questions.strivers.recursion;

import java.util.ArrayList;

public class Subsequence {
    public static void main(String[] args) {
        int arr[] = {1,2,1};
        int k = 2;
//        subsequences3(0,arr,new ArrayList<>(),0,k);
        System.out.println(subsequences4(0,arr,0,k,0));
    }

    // count all the subsequences with sum is k
    public static int subsequences4(int index,int arr[],int sum,int k,int count){
        if(index == arr.length){
            if(sum == k){
                count++;
            }
            return count;
        }
        int l = subsequences4(index+1,arr,sum + arr[index],k,count);
        int r = subsequences4(index+1,arr,sum,k,count);
        return l + r;
    }

    // print any one subsequence with sum is k
    public static boolean subsequences3(int index,int arr[],
                                        ArrayList<Integer>ds,int sum,int k){
        if(index == arr.length){
            if(sum == k){
                System.out.println(ds);
                return true;
            }else{
                return false;
            }
        }
        sum = sum + arr[index];
        ds.add(arr[index]);
        if(subsequences3(index+1,arr,ds,sum,k)){
            return true;
        }
        sum = sum - arr[index];
        ds.remove(ds.size()-1);
        if(subsequences3(index+1,arr,ds,sum,k)){
            return true;
        }
        return false;
    }

    // print all the subsequences with sum = k
    public static void subsequence2(int index,int arr[],ArrayList<Integer>ds,
                                    ArrayList<ArrayList<Integer>>ans,int sum,int k){
        if(index == arr.length){
            if(sum == k){
                ans.add(new ArrayList<>(ds));
            }
            return;
        }
        sum = sum + arr[index];
        ds.add(arr[index]);
        subsequence2(index+1,arr,ds,ans,sum,k);
        ds.remove(ds.size()-1);
        sum = sum - arr[index];
        subsequence2(index+1,arr,ds,ans,sum,k);
    }

    // print all the subsequences
    public static void subsequence1(int index, int arr[], ArrayList<Integer> ds,
                                    ArrayList<ArrayList<Integer>>ans){

        if(index == arr.length){
            ans.add(new ArrayList<>(ds));
            return;
        }
        ds.add(arr[index]);
        subsequence1(index+1,arr,ds,ans);
        ds.remove(ds.size()-1);
        subsequence1(index+1,arr,ds,ans);

    }
}

package com.questions.strivers.basics.recursion;

public class CountSubsequences {
    public static void main(String[] args) {
        int arr[] = {1,2,1};
        int sum = 2;
        System.out.println(countSubSeq(0,arr,arr.length,sum,0));
    }

    public static int countSubSeq(int index, int arr[],int n,int sum,int res){
        if(index == n){
            if(res == sum){
                return 1;
            }else {
                return 0;
            }
        }
        res = res + arr[index];
        int left = countSubSeq(index+1,arr,n,sum,res);

        res = res - arr[index];
        int right = countSubSeq(index+1,arr,n,sum,res);

        return left + right;
    }
}

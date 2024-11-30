package com.questions.apnacollageold.array;

import java.util.Arrays;

public class MaxProdSubArray {

    public static void main(String[] args) {
        int nums[] = {2,3,-2,4};
        System.out.println(maxProd3(nums));
    }

    public static int maxProd3(int arr[]){
        int prefix=1, sufix=1;
        int ans = Integer.MIN_VALUE;
        int n = arr.length;
        for(int i=0;i<n;i++){
            if(prefix == 0) prefix = 1;
            if(sufix == 0) sufix = 1;

            prefix = prefix*arr[i];
            sufix = sufix*arr[n-1-i];
            ans = Math.max(ans,Math.max(prefix,sufix));
        }
        return ans;
    }

    // approach2
    public static int maxProd2(int arr[]) {
        int maxProd = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            int prod = 1;
            for(int j=i;j<arr.length;j++){
                prod = prod*arr[j];
                if(prod>maxProd){
                    maxProd = prod;
                }
            }
        }
        return maxProd;
    }

    // brute force approach1
    public static int maxProd1(int arr[]){
        int maxProd = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                int start=i,end=j;
                int prod = 1;
                for(int k=start;k<=end;k++){
                    prod *= arr[k];
                }
                if(maxProd < prod){
                    maxProd = prod;
                }
            }
        }
        return maxProd;
    }

    public static int maxProd(int arr[]){
        int prodArr[] = new int[arr.length];
//        int s = Arrays.stream(arr).max().getAsInt();
//        if(s<0){
//            return s;
//        }
        Arrays.fill(prodArr,1);
        int prod = 1;
        int maxProd = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            prod *=arr[i];
//            if(prod < 0){
//                prod = 0;
//            }
            maxProd = Math.max(prod,maxProd);
        }
        return maxProd;
    }
}

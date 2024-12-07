package com.questions.strivers.binarysearch.bson1darray;

public class LowerBound {

    public static void main(String[] args) {
        int arr[] = {1,2,3,3,7,8,9,9,9,11};
        int x = 1;
        System.out.println(lowerBound(arr,arr.length,x));
    }

    public static int lowerBound(int arr[],int n,int x){
        int low = 0, high = n - 1;
        int ans = n;
        while (low <= high){
            int mid = (low + high)/2;
            if(arr[mid] >= x){
                ans = mid;
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return ans;
    }
}

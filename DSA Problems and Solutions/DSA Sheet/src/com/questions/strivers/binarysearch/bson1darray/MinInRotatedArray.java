package com.questions.strivers.binarysearch.bson1darray;
//https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
public class MinInRotatedArray {

    public static void main(String[] args) {
        int arr[] = {6,7,1,2,3,4,5};
        System.out.println(findMin(arr));
    }

    public static int findMin(int arr[]){
        int n = arr.length;
        int low = 0, high = n-1, ans = Integer.MAX_VALUE;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(arr[low] <= arr[mid]){
                ans = Math.min(ans,arr[low]);
                low = mid+1;
            }else{
                high = mid-1;
                ans = Math.min(ans,arr[mid]);
            }
        }
        return ans;
    }


}

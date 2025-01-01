package com.questions.strivers.binarysearch.bson1darray;

public class TimesArrayRotated {

    public static void main(String[] args) {
        int arr[] = {6,7,1,2,3,4,5};
        System.out.println(count(arr));
    }

    public static int count(int arr[]){
        int n = arr.length;
        int low = 0, high = n-1, ans = Integer.MAX_VALUE, index=0;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(arr[low] <= arr[mid]){
                if(arr[low] < ans){
                    index = low;
                    ans = arr[low];
                }
                low = mid+1;
            }else{
                high = mid-1;
                if(arr[mid] < ans){
                    index = mid;
                    ans = arr[mid];
                }
            }
        }
        return index;
    }

}

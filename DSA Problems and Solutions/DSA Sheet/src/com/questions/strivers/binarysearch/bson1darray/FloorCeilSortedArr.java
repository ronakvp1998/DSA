package com.questions.strivers.binarysearch.bson1darray;

public class FloorCeilSortedArr {
    public static void main(String[] args) {
        int arr[] = {10,20,30,40,50};
        int target = 9;
        System.out.println(floor(arr,target));
        System.out.println(ceil(arr,target));
    }

    public static int ceil(int arr[], int target) {
        int low = 0,high = arr.length-1, ans = -1;
        while (low<=high){
            int mid = low + (high-low)/2;
            if(arr[mid] >= target){
                ans = arr[mid];
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return ans;
    }

    public static int floor(int arr[], int target){
        int low=0,high=arr.length-1,ans=-1;
        while (low<=high){
            int mid = low + (high-low)/2;
            if(arr[mid] <= target){
                ans = arr[mid];
                // move right
                low = mid+1;
            }else{
                // move left
                high = mid-1;
            }
        }
        return ans;
    }

}

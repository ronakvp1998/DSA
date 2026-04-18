package com.questions.practice;

public class Test {

    private static int findFloor(int[] arr, int x) {
        int low=0,high=arr.length-1;
        int floor=-1;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(arr[mid] <= x){
                floor = arr[mid];
                low = mid + 1;
            }else{
                high = mid-1;
            }
        }
        return floor;
    }

    private static int findCeil(int []arr,int x){
        int low=0,high=arr.length-1;
        int ceil=-1;
        while (low <= high){
            int mid = low + (high - low)/2;
            if(arr[mid] >= x){
                ceil = arr[mid];
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return ceil;
    }

}
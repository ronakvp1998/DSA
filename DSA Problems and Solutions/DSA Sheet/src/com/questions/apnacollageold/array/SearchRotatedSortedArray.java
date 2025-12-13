package com.questions.apnacollageold.array;

// 6 search in rotated sorted array in Log(n) TC
public class SearchRotatedSortedArray {

    public static void main(String[] args) {
        int arr[] = {4,5,6,7,0,1,2};
        int key = 1;
        System.out.println(sortRotatedArray2(arr,0,arr.length-1, key))   ;
    }

    private static int sortRotatedArray2(int arr[], int start, int end,int key){
        if(start > end){
            return -1;
        }
        int mid = start + (end-start)/2;

        if(arr[mid] == key){
            return mid;
        }

        if(arr[start] <= arr[mid]){
            if(arr[start] <= key && key <= arr[mid]){
                return sortRotatedArray2(arr,start,mid-1,key);
            }else{
                return sortRotatedArray2(arr,mid+1,end,key);
            }
        }else{
            if(arr[mid] <= key && arr[end] >= key ){
                return sortRotatedArray2(arr,mid+1,end,key);
            }else{
                return sortRotatedArray2(arr,start,mid-1,key);
            }
        }

    }








    private static int sortRotatedArray(int arr[], int start,  int end, int key){
        // base case
        if(start > end){
            return -1;
        }
        int mid = start + (end-start)/2;
        if(arr[mid] == key){
            return mid;
        }
        // case1-> middle value lies on line 1
        if(arr[start] <= arr[mid]){
            // case a-> search left in line 1
            if(arr[start] <= key && key <= arr[mid]){
                return sortRotatedArray(arr,start,mid-1,key);
            }
            // case b-> search right in line 1
            else{
                return sortRotatedArray(arr,mid+1,end,key);
            }
        }
        // case2-> middle value lies on line 2
        else {
            // case c-> search right of line 2
            if(arr[mid] <= key && key<=arr[end]){
                return sortRotatedArray(arr,mid+1,end,key);
            }
            // case d-> search left of line 2
            else{
                return sortRotatedArray(arr,start,mid-1,key);
            }
        }
    }
}

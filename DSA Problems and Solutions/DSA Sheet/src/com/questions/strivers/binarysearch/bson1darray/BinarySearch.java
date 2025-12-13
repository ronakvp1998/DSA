package com.questions.strivers.binarysearch.bson1darray;

//https://leetcode.com/problems/binary-search/
//pg 15 book4
// Binary Search:
// Explained https://takeuforward.org/data-structure/binary-search-explained/

public class BinarySearch {

    public static void main(String[] args) {
        int arr[] = {-1,0,3,5,9,12};
        int k = 12;
//        System.out.println(binarySearch(arr,k));
        System.out.println(binarySearch1(arr,0,arr.length-1,k));
    }

    // binary search recursive approach
    private static int binarySearch1(int arr[], int low,int high, int target) {
        if(low>high){
            return -1;
        }
        int mid = low + (high-low)/2;

        if(arr[mid] == target){
            return mid;
        }
        // call right
        else if (target > arr[mid]) {
            return binarySearch1(arr,mid+1,high,target);
        }
        // call left
        else {
            return binarySearch1(arr,low,mid-1,target);
        }

    }

    // binary search iterative approach
    private static int binarySearch(int arr[], int k){
        int n = arr.length;
        int low = 0, high = n-1;
        while (low<=high){
            int mid = (low+high)/2;
            if(arr[mid] == k){
                return mid;
            }else if(arr[mid] > k ){
                low = mid+1;
            }else {
                high = mid-1;
            }
        }
        return -1;
    }

}

package com.questions.strivers.binarysearch.bson1darray;
/* https://takeuforward.org/arrays/floor-and-ceil-in-sorted-array/
Problem Statement: You're given an sorted array arr of n integers and an integer x. Find the floor and ceiling of x in arr[0..n-1].
The floor of x is the largest element in the array which is smaller than or equal to x.
The ceiling of x is the smallest element in the array greater than or equal to x.

Pre-requisite: Lower Bound & Binary Search

Example 1:
Input Format: n = 6, arr[] ={3, 4, 4, 7, 8, 10}, x= 5
Result: 4 7
Explanation: The floor of 5 in the array is 4, and the ceiling of 5 in the array is 7.

Example 2:
Input Format: n = 6, arr[] ={3, 4, 4, 7, 8, 10}, x= 8
Result: 8 8
Explanation: The floor of 8 in the array is 8, and the ceiling of 8 in the array is also 8.
 */
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

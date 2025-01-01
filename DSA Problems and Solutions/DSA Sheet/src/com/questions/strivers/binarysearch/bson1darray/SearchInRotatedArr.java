package com.questions.strivers.binarysearch.bson1darray;

//https://leetcode.com/problems/search-in-rotated-sorted-array/description/
// pg 18 book4
public class SearchInRotatedArr {
    public static void main(String[] args) {
        int arr[]= {3,1};
        int target = 1;
        System.out.println(searchRotatedArray(arr,arr.length,target));
    }

    public static int searchRotatedArray(int arr[], int n,int target){
        int low=0, high= n-1;

        if(low > high){
            return -1;
        }
        if(arr.length == 1 && target == arr[0]){
            return low;
        }

        while (low<=high){
            int mid = ((low+high)/2)+1;
            if(arr[mid] == target){
                return mid;
            }
            if(arr[low] <= arr[mid]){
                if(arr[low] <= target && target <= arr[mid]){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }else{
                if(arr[mid] <= target && target <= arr[high]){
                    low = mid+1;
                }else{
                    high = mid-1;
                }
            }
        }
        return -1;
    }

}

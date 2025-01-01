package com.questions.strivers.binarysearch.bson1darray;

public class SearchInRotatedArrDuplicates {
    public static void main(String[] args) {
        int[] arr = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        int target = 5;
        System.out.println(searchInRotatedArr(arr,arr.length,target));
    }

    public static int searchInRotatedArr(int arr[],int n, int target){
        int low=0, high=n-1;
        while (low<=high){
            int mid = low + (high-low)/2;
            if(arr[mid] == target){
                return mid;
            }
            if(arr[low] == arr[mid] && arr[mid] == arr[high]){
                low++;
                high--;
                continue;
            }
            if(arr[low] <= arr[mid]){
                if(target>= arr[low] && target <= arr[mid]){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }else{
                if(target>=arr[mid] && target<= arr[high]){
                    low = mid+1;
                }else{
                    high = mid-1;
                }
            }
        }
        return -1;
    }

}

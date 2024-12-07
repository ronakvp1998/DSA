package com.questions.strivers.binarysearch.bson1darray;

import java.util.Arrays;

public class FirstAndLastPosition {
    public static void main(String[] args) {
        int arr[] = {2,4,6,8,8,8,11,13};
        int target = 8;
        System.out.println(Arrays.toString(firstLast1(arr,target)));
    }

    // binary search approach without lower and upper bound
    public static int [] firstLast1(int arr[], int target){
        int n = arr.length;
        int first = firstOccurance(arr,n,target);
        if(first == -1){
            return new int[]{-1,-1};
        }
        int last = lastOccurance(arr,n,target);
        return new int[]{first,last};
    }

    public static int firstOccurance(int arr[], int n, int target){
        int low=0,high = n-1;
        int first = -1;
        while (low<=high){
            int mid = (low+high)/2;
            if(arr[mid] == target){
                first = mid;
                high = mid-1; // left
            } else if (arr[mid] < target) {
                low = mid+1; // right
            }else{
                high = mid-1;  // left
            }
        }
        return first;

    }

    public static int lastOccurance(int arr[], int n, int target){
        int low=0,high = n-1;
        int last = -1;
        while (low<=high){
            int mid = (low+high)/2;
            if(arr[mid] == target){
                last = mid;
                low = mid+1; // right
            } else if (arr[mid] < target) {
                low = mid+1; // right
            }else{
                high = mid-1; // left
            }
        }
        return last;
    }


    // using the lower and upper bound approach
    public static int [] firstLast(int arr[], int target){
        int lb = lowerBound(arr,target);
        if(lb == arr.length || arr[lb] != target){
            return new int[]{-1,-1};
        }
        return new int[]{lb,upperBound(arr,target)-1};
    }

    public static int lowerBound(int arr[], int target){
        int n = arr.length;
        int low=0, high = n-1, ans = n;
        while (low<=high){
            int mid = low + (high-low)/2;
            if(arr[mid] >= target){
                high = mid-1;
                ans = mid;
            }else{
                low = mid+1;
            }

        }
        return ans;
    }

    public static int upperBound(int arr[], int target){
        int n = arr.length;
        int low=0, high = n-1, ans = n;
        while (low<=high){
            int mid = low + (high-low)/2;
            if(arr[mid] > target){
                high = mid-1;
                ans = mid;
            }else{
                low = mid+1;
            }

        }
        return ans;
    }
}

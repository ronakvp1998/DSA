package com.questions.practice;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
    }

    private static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0, right = m*n-1;
        while (left <= right){
            int mid = left + (right - left)/2;
            int midValue = matrix[mid/n][mid%n];
            if(midValue == target){
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return false;
    }

    public static int rowWithMax1sOptimal(int[][] mat, int n, int m) {
        if (mat == null || n == 0 || m == 0) {
            return -1;
        }
        int row=0,col=m-1,maxRowIndex = -1;
        while (row < n && col >= 0){
            if(mat[row][col] == 1){
                maxRowIndex = row;
                col--;
            }else{
                row++;
            }
        }
        return maxRowIndex;
    }

    private static int splitArray(int[] nums, int k) {
        int low = 0;
        long high = Arrays.stream(nums).sum();
        long optimalRes = -1;
        Arrays.sort(nums);
        while (low <= high){
            long mid = low + (high - low)/2;
            if(checkSplitArray(nums,mid) <= k){
                optimalRes = mid;
                high = mid - 1;
            }else{
                low = (int)mid + 1;
            }
        }
        return (int)optimalRes;
    }

    private static int checkSplitArray(int arr[],long mid){
        long currentSum = 0;
        int subarrayRequried = 1;
        for(int i : arr){
            if(currentSum + i > mid){
                subarrayRequried++;
                currentSum = i;
            }else{
                currentSum += i;
            }
        }
        return subarrayRequried;
    }

    private static int bookAllocate(int []books,int n,int m){
        if(m> n){
            return -1;
        }
        int low = Arrays.stream(books).max().getAsInt();
        long  high = Arrays.stream(books).sum();
        long optimalResult = -1;
        while (low <= high){
            long mid = low + (high - low)/2;
            if(checkPages(books,mid) <= m){
                optimalResult = mid;
                high = mid - 1;
            }else{
                low = (int)mid + 1;
            }
        }
        return (int)optimalResult;
    }

    private static int checkPages(int []books,long mid){
        int studentRequired = 1;
        long currentPages = 0;
        for(int pages : books){
            if(currentPages + pages > mid){
                studentRequired++;
                currentPages = pages;
            }else{
                currentPages += pages;
            }
        }
        return studentRequired;
    }


    private static int aggressiveCows(int arr[], int k){
        int n = arr.length;
        Arrays.sort(arr);
        int low = 1;
        int high = arr[n-1] - arr[0];
        int maxDist = low;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(checkDist(arr,mid,k)){
                maxDist = mid;
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return maxDist;
    }
    private static boolean checkDist(int arr[],int dist,int k){
        int cowsplaced = 1;
        int lastPlaced = arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i] - lastPlaced >= dist){
                cowsplaced++;
                lastPlaced = arr[i];
            }
        }
        return cowsplaced >= k;
    }
    private static int shipDays(int [] wth,int days){
        int low = Arrays.stream(wth).max().getAsInt();
        int n = wth.length;
        int high = low*n;
        int capacity = high;
        while (low<=high){
            int mid = low + (high - low)/2;
            int d = checkDays(wth,mid,days);
            if(d <= days){
                capacity = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return capacity;
    }

    private static int checkDays(int []wth,int tempCapacity,int days){
        int d = 1;
        int currentCapacity = 0;
        for (int i : wth){
            currentCapacity = currentCapacity + i;
            if(currentCapacity > tempCapacity){
                currentCapacity = i;
                d++;
            }
        }
        return d;
    }

    private static int smallestDiv(int [] arr,int threshold){
        int low = 1;
        int high = Arrays.stream(arr).max().getAsInt();
        int divisor = high;
        while (low <= high){
            int mid = low + (high-low)/2;
            long temp = checkDivisor(arr,mid,threshold);
            if(temp <= threshold){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return divisor;
    }

    private static long checkDivisor(int arr[],int tempDivisor,int threshold){
        long sum=0;
        for(int i : arr){
            sum += (int)Math.ceil((double) i/tempDivisor);
        }
        return sum;
    }

    private static int minDays(int []bloomDay,int m,int k){
        int n = bloomDay.length;
        if((long) m * k > n){
            return -1;
        }
        int low = Arrays.stream(bloomDay).min().getAsInt();
        int high = Arrays.stream(bloomDay).max().getAsInt();
        int days = -1;
        while (low <= high){
            int mid = low + (high - low)/2;
            if(checkBouq(bloomDay,mid,m,k)){
                days = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return days;
    }

    private static boolean checkBouq(int[] bloomDay,int midDays, int m,int k){

        int bountCount = 0;
        int consecutiveFlowers= 0;
        for(int day : bloomDay){
            if(day <= midDays){
                consecutiveFlowers++;
                if(consecutiveFlowers == k){
                    bountCount++;
                    consecutiveFlowers = 0;
                }
            }else{
                consecutiveFlowers = 0;
            }
        }
        return bountCount >= m;
    }

    private static int kokoBanana(int []piles,int h){
        int n = piles.length;
        int low=1;
        int high = Arrays.stream(piles).max().getAsInt();
        int optimalSpeed = high;
        while (low <= high){
            int mid = low + (high-low)/2;
            long hours = checkHours(piles,mid);
            if(hours <= h){
                optimalSpeed = mid;
                high = mid-1;
            }else{
                low = mid + 1;
            }
        }
        return optimalSpeed;
    }
    private static long checkHours(int []piles,int speed){
        long hours = 0;
        for(int pile : piles){
            hours += (long) Math.ceil((double) pile/speed);
        }
        return hours;
    }

}
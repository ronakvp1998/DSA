package ArraysDemos;

import java.util.*;

public class Demos {

    public static void main(String[] args) {
        int num [] = {2,3,4,10,5,6};
//        System.out.println(linearSearch(num,4));
//        System.out.println(largestNumber(num));
//        System.out.println(binarySearch(num,4));
//        System.out.println(Arrays.toString(num));
//        reverse(num);
//        System.out.println(Arrays.toString(num));
//        int numbers[] = {2,4,6,8,10};
//        pairs(numbers);
//        subarrays(numbers);
//        int nums[] = {1,-2,6,-1,3};
//        maxSubArray2(nums);
        int arr[] = {4,2,0,6,3,2,5};
        System.out.println(waterTrapp(arr));
    }

    public static int waterTrapp(int arr[]){
        int n = arr.length;

        int leftMax[] = new int[n];
        leftMax[0] = arr[0];
        for(int i=1;i<n;i++){
            leftMax[i] = Math.max(arr[i],leftMax[i-1]);
        }

        int rightMax[] = new int[n];
        rightMax[n-1] = arr[n-1];
        for(int i=n-2;i>=0;i--){
            rightMax[i] = Math.max(arr[i], rightMax[i+1]);
        }

        int trappedWater = 0;
        for(int i=0;i<n;i++){
            int waterLevel = Math.min(leftMax[i],rightMax[i]);
//            trappedWater +                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  = waterLevel - arr[i];
        }
        return trappedWater;
    }


    public static void maxSubArray(int arr[]){

        int prefix[] = new int[arr.length];
        prefix[0] = arr[0];
        for(int i=1;i<arr.length;i++){
            prefix[i] = prefix[i-1] + arr[i];
        }
        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            int start = i;
            for(int j=0;j<arr.length;j++){
                int end = j;
                currSum = start == 0 ? prefix[end] : prefix[end] - prefix[start-1];
            }
            if(maxSum < currSum){
                maxSum = currSum;
            }
        }

        System.out.println(maxSum);

    }

    public static void maxSubArray2(int arr[]){
        int ms = Integer.MIN_VALUE;
        int cs = 0;
        for(int i=0;i<arr.length;i++){
            cs = cs + arr[i];
            if(cs < 0){
                cs = 0;
            }
            ms = Math.max(cs,ms);
        }
        System.out.println(ms);
    }

    public static void subarrays(int arr[]){
        HashMap<Integer,List<Integer>> map = new HashMap<>();
        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                int start = i, end = j;
                int sum = 0;
                List<Integer> list = new ArrayList<>();
                for(int k=start;k<=end;k++){
                    System.out.print(arr[k] + " ");
                    sum += arr[k];
                    list.add(arr[k]);
                }
                map.put(sum,list);
                System.out.println("");
            }
            System.out.println();
        }

        System.out.println(map);
//        System.out.println(list);
        int maxSum = Integer.MIN_VALUE;
        for(Map.Entry e : map.entrySet()){
            if((int)e.getKey() > maxSum){
                maxSum = (int)e.getKey();
            }
        }

        System.out.println("Max sum subarray " + map.get(maxSum));
        System.out.println("Max sum " + maxSum);
    }

    public static void pairs(int arr[]){
        int totalPairs = 0;
        for(int i=0;i<arr.length-1;i++){
            for(int j=i+1;j<arr.length;j++){
                System.out.print("("+arr[i]+","+arr[j]+") ");
                totalPairs++;
            }
            System.out.println();
        }
        System.out.println("Total Pairs " + totalPairs);
    }

    public static void reverse(int arr[]){
        int start = 0, end = arr.length-1;
        while (start<end){
            swap(arr,start,end);
            start++;
            end--;
        }

    }

    public static void swap(int arr[], int start , int end){
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

    public static int binarySearch(int arr[] ,int key){
        int start = 0, end = arr.length-1;
        while (start <= end){
            int mid = start + (end - start)/2;
            if(key > arr[mid]){
                start = mid+1;
            }if(key == arr[mid]){
                return mid;
            }if(key < arr[mid]){
                end = mid - 1;
            }
        }

        return -1;
    }

    public static int linearSearch(int arr[], int key){
        for(int i=0;i<arr.length;i++){
            if(arr[i] == key){
                return i;
            }
        }
        return -1;
    }
}

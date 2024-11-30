package ArraysDemos;

import javax.management.ObjectName;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demos {
    public static void main(String[] args) {
//        int num [] = {2,3,4,10,5,6,7};
//        System.out.println(linearSearch(num,4));
//        System.out.println(largestNumber(num));

//        List<Object> sentenceList = Arrays.asList(
//                "I", "Love", "Java", Arrays.asList("CONCEPTS", "ARE", "CLEAR", Arrays.asList("ITS", "VERY", "EASY",Arrays.asList(
//                        "I", "Love", "Java")))
//        );

//        flatmapdemo(sentenceList);
//        int num[] = {2,4,6,8,10,12,14};
//        int key = 14;
//        System.out.println(binarySearch(num,key));
//        pairs(new int[] {2,4,6,8,10});
//        subArray2(new int[] {1,-2,6,-1,3});
//        kadanes(new int[]{4,-1,-2,1,5});
//        rainWater(new int[]{4,2,0,6,3,2,5});
        buySell(new int[] {7,1,5,3,6,4});
    }

    public static void buySell(int arr[]){
        int bp = Integer.MAX_VALUE;
        int profit = 0;
        int MaxProfit = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            if(bp < arr[i]){
                profit = arr[i] - bp;
            }
            else {
                bp = arr[i];
            }
            MaxProfit = Math.max(MaxProfit,profit);
        }
        System.out.println(MaxProfit);
    }


    public static void rainWater(int arr[]){

        int leftMax[] = new int[arr.length];
        leftMax[0] = arr[0];
        int lm = arr[0];

        int rightMax[] = new int[arr.length];
        rightMax[arr.length-1] = arr[arr.length-1];
        int rm = arr[arr.length-1];

        for(int i=1;i<arr.length;i++){
            if(arr[i] > lm){
                lm = arr[i];
            }
            leftMax[i] = lm;
        }

        for(int i=arr.length-2;i>=0;i--){
            if(arr[i] > rm){
                rm = arr[i];
            }
            rightMax[i] = rm;
        }

        int trappedWater = 0;
        for(int i=0;i<arr.length;i++){
            trappedWater = trappedWater + (Math.min(leftMax[i],rightMax[i]) - arr[i]);
        }

        System.out.println(trappedWater);
    }

    public static void kadanes(int arr[]){
        int cs = 0;
        int ms = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            cs = cs + arr[i];
            if(cs<0){
                cs = 0;
            }
            ms = Math.max(cs,ms);
        }
        System.out.println(ms);
    }


    public static void subArray2(int num[] ){
        int prefix[] = new int [num.length];
        prefix[0] = num[0];
        for(int i=1;i<num.length;i++){
            prefix[i] = num[i] + prefix[i-1];
        }
        int max = Integer.MIN_VALUE;
        int currSum = 0;
        for(int i=0;i<num.length;i++){
            int start = i;
            for(int j=1;j<num.length;j++){
                int end = j;
                currSum = start == 0 ? prefix[end] : prefix[end] - prefix[start-1];
                if(max < currSum){
                    max = currSum;
                }
            }
        }
        System.out.println(max);
    }

    public static void subArrays(int []num){
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for(int i=0;i<num.length;i++){
            for(int j=i;j<num.length;j++){
                int start=i,end = j;
                sum = 0;
                for(int k=start;k<=end;k++){
                    System.out.print(num[k] + ",");
                    sum += num[k];
                }
                System.out.print(" ");
                if(sum > max){
                    max = sum;
                }
            }
            System.out.println(" ");

        }
        System.out.println("Sum " + sum);
    }

    public static void pairs(int [] num){
        int total = 0;
        for(int i=0;i<num.length-1;i++){
            for(int j=i+1;j<num.length;j++){
                total+=1;
                System.out.print("(" + num[i] + "," + num[j] + ") ");
            }
            System.out.println();
        }
        System.out.println("Total pairs " + total);
    }

    public static int binarySearch(int num[], int key){
        int start = 0, end = num.length-1;
        while (start <= end){
            int mid = start + (end-start)/2;
            if(num[mid] == key){
                return mid;
            }if(num[mid] > key){
                end = end - 1;
            }if(num[mid] < key){
                start = mid+1;
            }
        }
        return -1;
    }

    public static void flatmapdemo(List<?> sentenceList){
        List<Object> flattenList = flatten(sentenceList);
        System.out.println(flattenList);
    }

    public static List<Object> flatten(List<?> list){
        return list.stream().flatMap(element -> {
            if(element instanceof List<?>){
                return flatten((List<?>) element).stream();
            }else {
                return Stream.of(element);
            }
        }).collect(Collectors.toList());
    }



    public static int largestNumber(int num[]){
        int max = Integer.MIN_VALUE;
        for(int i=0;i<num.length;i++ ){
            if(num[i] > max){
                max = num[i];
            }
        }
        return max;
    }

    public static int linearSearch(int num[], int key){
        for(int i=0;i<num.length;i++){
            if(num[i] == key){
                return i;
            }
        }
        return -1;
    }



}

package com.questions.strivers.arrays.easy;

public class MaximumConsecutiveOnes {

    public static void main(String[] args) {
        int arr[] = {1,1,0,1,1,1,0,1,1};
        System.out.println(maxConsecutiveOnes(arr));
    }

    public static int maxConsecutiveOnes(int arr[]){
        int count = 0, maxCount = 0;

        for(int i=0;i<arr.length;i++){
            if(arr[i] == 1){
                count++;
            }else{
                count = 0;
            }
            if(count > maxCount){
                maxCount = count;
            }
        }
        return maxCount;
    }

}

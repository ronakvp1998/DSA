package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class DpTest {
    private static int recursion(int arr[][],int day,int lastTask){
        if(day == 0){
            int max = 0;
            for(int task = 0;task < 3;task++){
                if(task != lastTask){
                    max = Math.max(max,arr[0][task]);
                }
            }
            return max;
        }
        int maxPoints = 0;
        for(int task=0;task<3;task++){
            if(task != lastTask){
                int currentPoints = arr[day][task] +
                        recursion(arr,day-1,task);
                maxPoints = Math.max(maxPoints,currentPoints);
            }
        }
        return maxPoints;
    }


    public static void main(String[] args) {
        int n = 2;
        int dp[] = new int[n+1];
        Arrays.fill(dp,-1);
        int arr[][] = new int[n][n];

    }
}

package com.questions.practice.dynamicprogramming;

public class NinjaTraning {

    public static void main(String[] args) {
        int[][] meritPoints = {
                {10, 40, 70},
                {20, 50, 80},
                {30, 60, 90}
        };

        // Solve using different approaches
        System.out.println("Recursive: " + ninjaTrainingRecursive(meritPoints));
//        System.out.println("Memoization: " + ninjaTrainingMemoization(meritPoints));
//        System.out.println("Tabulation: " + ninjaTrainingTabulation(meritPoints));
//        System.out.println("Space Optimized: " + ninjaTrainingSpaceOptimized(meritPoints));
    }
    public static int ninjaTrainingRecursive(int arr[][]){

        int last = arr[0].length;
        int day = arr.length-1;
        return recursion(day,last,arr);
    }

    public static int recursion(int day, int lastTask,int [][]arr){
        if(day == 0){
            int maxPoint = 0;
            for(int task = 0;task<arr[0].length;task++){
                if(task != lastTask){
                    maxPoint = Math.max(maxPoint,arr[0][task]);
                }
            }
            return maxPoint;
        }
        int maxPoint = Integer.MIN_VALUE;
        for(int i=0;i<arr[0].length;i++){
            if(i != lastTask){
                int taskPoint = arr[day][i] + recursion(day - 1,i,arr);
                maxPoint = Math.max(taskPoint,maxPoint);
            }
        }
        return maxPoint;
    }
}

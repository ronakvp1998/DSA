package com.questions.practice.greedyapporach;

import java.util.Arrays;

public class GreedyTest {

    private static int test(int [][] intervals){
        if(intervals == null || intervals.length <= 1){
            return 0;
        }
        Arrays.sort(intervals,(a,b) -> Integer.compare(a[1],b[1]));
        int removeCount = 0;
        int currentEndTime = intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            int nextStartTime = intervals[i][0];
            if(nextStartTime < currentEndTime){
                removeCount++;
            }else{
                currentEndTime = intervals[i][1];
            }
        }
        return removeCount;
    }
}

package GreedyAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// code 1:- Activity Selection using greedy Algorithm
public class ActivitySelection {

    public static void main(String[] args) {   // O(n)
        int start [] = {1,3,0,5,8,5};
        int end [] = {2,4,6,7,9,9}; // end arr is already sorted

        // sorting if end arr is not sorted
        int activities[][] = new int[start.length][3];
        // 3 columns -> 1 original index, 2 start time, 3 end time
        for(int i=0;i<start.length;i++){
            activities[i][0] = i;
            activities[i][1] = start[i];
            activities[i][2] = end[i];
        }

        // sorting based on end time
        Arrays.sort(activities, Comparator.comparingDouble(o->o[2]));

        int maxAct = 0;
        ArrayList<Integer> ans = new ArrayList<>();

        maxAct = 1;
//        ans.add(0);
        ans.add(activities[0][0]);

//        int lastEnd = end[0];
        int lastEnd = activities[0][2];
        for(int i=1;i< end.length;i++){
//            if(start[i] >= lastEnd){
            if(activities[i][1] >= lastEnd){
                // activity select
                maxAct++;
//                ans.add(i);
                ans.add(activities[i][0]);
//                lastEnd = end[i];
                lastEnd = activities[i][2];

            }
        }

        System.out.println("Max activities = " + maxAct);
        for(int i=0;i<ans.size();i++){
            System.out.print("A"+ans.get(i)+" ");
        }
        System.out.println();
    }
}

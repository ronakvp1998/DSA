package GreedyAlgorithm;

// code 2:- Fractional Knapsack

import java.util.*;

public class FractionalKnapsack {

    public static void main(String[] args) {
        int val[] = {60,100,120};
        int weight[]= {10,20,30};
        int w = 50;

        double ratio[][] = new double[val.length][2];
        // 0th col => original index
        // 1st col => ratio of items

        for(int i=0;i<val.length;i++){
            ratio[i][0] = i;
            ratio[i][1] = val[i]/(double) weight[i];

        }

        // ascending order sorting
        Arrays.sort(ratio, Comparator.comparingDouble(o -> o[1]));

//        ratio is sorted in ascending order so go from backwards
        int capacity = w;
        int finalVal = 0;
        for(int i= ratio.length-1; i>=0;i--){
            int idx = (int)ratio[1][0];
            if(capacity >= weight[idx]){  // include full item
                finalVal += val[idx];
                capacity -= weight[idx];
            }else{  // include fractional items
                finalVal += (ratio[i][1] * capacity);
                capacity = 0;
                break;
            }
        }

        System.out.println("Final Value  = " + finalVal);

    }
}

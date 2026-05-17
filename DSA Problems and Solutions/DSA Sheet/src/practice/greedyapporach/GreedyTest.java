package com.questions.practice.greedyapporach;

import java.util.Arrays;

public class GreedyTest {
    public static void main(String[] args) {
        int[] arr1 = {900, 945, 955, 1100, 1500, 1800};
        int[] dep1 = {920, 1200, 1130, 1150, 1900, 2000};
        Arrays.sort(arr1);
        Arrays.sort(dep1);
        int platform=1,maxPlatform=1;
        int i=1,j=0;
        while (i < arr1.length && j < dep1.length){
            if(arr1[i] <= dep1[j]){
                platform++;
                i++;
            }
            else {
                platform--;
                j++;
            }
        }

    }
}
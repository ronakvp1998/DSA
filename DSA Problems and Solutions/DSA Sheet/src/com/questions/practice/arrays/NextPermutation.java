package com.questions.practice.arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NextPermutation {
    public static void main(String[] args) {
        Integer arr[] = {1,2,3,6,5,4};
        System.out.println(nextPermutation(Arrays.asList(arr)));
    }

    private static List<Integer> nextPermutation(List<Integer> list){
        int pivot = -1, n = list.size();
        // step1 find pivot
        for(int i=n-2;i>=0;i--){
            if(list.get(i) < list.get(i+1)){
                pivot = i;
                break;
            }
        }
        if(pivot == -1){
            Collections.reverse(list);
            return list;
        }
        // step2 swap pivot and RLE
        for(int i=n-1;i>pivot;i++){
            if(list.get(i) > list.get(pivot)){
                int temp = list.get(i);
                list.set(i,list.get(pivot));
                list.set(pivot,temp);
                break;
            }
        }
        System.out.println("step2 " + list);
        // step3 rever pivot +1 to n-1
        Collections.reverse(list.subList(pivot+1,n));
        System.out.println("step3 " + list);



        return list;
    }
}

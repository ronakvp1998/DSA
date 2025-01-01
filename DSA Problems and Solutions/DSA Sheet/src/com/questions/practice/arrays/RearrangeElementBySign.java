package com.questions.practice.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeElementBySign {

    public static void main(String[] args) {
//        int arr[] = {1,2,-4,-5,3,-6,7};
        int arr[] = {1,2,-4,-5,3,-6,7};

        System.out.println(Arrays.toString(rearrange1(arr)));

    }

    public static int[] rearrange1(int arr[]){
        List<Integer> pos = new ArrayList<>();
        List<Integer> neg = new ArrayList<>();

        for(int i=0;i<arr.length;i++){
            if(arr[i] > 0){
                pos.add(arr[i]);
            }else{
                neg.add(arr[i]);
            }
        }

        if(pos.size() < neg.size()){
            for(int i=0;i<pos.size();i++){
                arr[i*2] = pos.get(i);
                arr[(i*2)+1] = neg.get(i);
            }
            int index = pos.size()*2;
            for(int i=pos.size();i<neg.size();i++){
                arr[index] = neg.remove(i) ;
                index++;
            }
        }else{
            for(int i=0;i<neg.size();i++){
                arr[i*2] = pos.get(i);
                arr[(i*2)+1] = neg.get(i);
            }
            int index = neg.size()*2;
            for(int i=neg.size();i<pos.size();i++){
                arr[index] = pos.remove(i);
                index++;
            }


        }
        return arr;
    }

    public static int[] rearrange(int arr[]){
        int temp[] = new int[arr.length];
        int pos=0,neg=1;
        int i = 0;
        while (i< arr.length){
            if(arr[i] >= 0){
                temp[pos] = arr[i];
                pos+=2;
                i++;
            }else{
                temp[neg] = arr[i];
                neg+=2;
                i++;
            }
        }
        return temp;
    }
}

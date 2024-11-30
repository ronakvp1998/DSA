package com.questions.apnacollageold.array;

//1 Maximum and minimum of an array using minimum number of comparisons

import java.util.Arrays;
import java.util.stream.Collectors;

public class MinMaxFinder {

    public static void main(String[] args) {
        // approach 1
        int arr[]= {3,4,2,1,6};
        System.out.println("min " + setMin(arr,arr.length));
        System.out.println("max " + setMax(arr,arr.length));

        // approach 2
        Integer arr1[]= {3,4,2,1,6};
        System.out.println(getMinMax(arr1,arr.length).min);
    }

    static class Pair{
        int min;
        int max;
        @Override
        public String toString() {
            return "Pair{min=" + min + ", max=" + max + "}";
        }
    }

    public static Pair getMinMax(Integer arr[] ,int n){
        Pair pair = new Pair();
        arr= Arrays.stream(arr).sorted().collect(Collectors.toList()).toArray(Integer[] :: new);
        pair.min = arr[0];
        pair.max = arr[arr.length-1];
        return pair;
    }

    public static int setMin(int arr[] ,int n){
        return Arrays.stream(arr).reduce(Integer.MAX_VALUE,(min,element) -> element < min ? element : min);
    }

    public static int setMax(int arr[] ,int n){
        return Arrays.stream(arr).reduce(Integer.MIN_VALUE,(max,element) -> element > max ?element:max);
    }
}

package com.questions.practice.arrays;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListArray {
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};
        List<Integer> integerList = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(integerList);

        int [] intArray = integerList.stream().mapToInt(Integer::intValue).toArray();
    }
}

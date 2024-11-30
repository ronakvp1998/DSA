package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class TestDemo  {

    public static void main(String[] args) {

        // given int arr positive and neg nums
        // shift all positive to one side and negative to other side
        // [1,23,3,-5,66,8,-8]
        // exp:- [-8,23,3,-5,66,8,1]
        // exp:- [-8,23,3,-5,66,8,1]
        // exp:- [-8,-5,3,23,66,8,1]
        // [-8,-5,1,3,8,23,66]

        int[] arr = new int[]{1,23,3,-5,66,8,-8};
        int start = 0;
        int end = arr.length-1;

//        System.out.println(Arrays.toString(arr));
        for(int i=start;i<arr.length-1;i++){
            if(arr[start] < 0 && start != end){
                start++;
                continue;

            }if(arr[end] >= 0 && start != end){
                end--;
                continue;
            }
            if(start == end){
                break;
            }
            swap(arr,start,end);
            System.out.println(Arrays.toString(arr));

        }
//        System.out.println(Arrays.toString(arr));

    }

    public static void swap(int arr[],int a ,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}




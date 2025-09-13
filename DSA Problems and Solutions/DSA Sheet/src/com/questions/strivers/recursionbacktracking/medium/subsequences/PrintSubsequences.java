package com.questions.strivers.recursionbacktracking.medium.subsequences;
import java.util.ArrayList;
import java.util.List;
public class PrintSubsequences {

    public static void main(String[] args) {
        int arr[] = {3,1,2};
//        printSubsequence(0,new ArrayList<>(),arr);
        System.out.println(printSubsequence2(0,new ArrayList<>(),arr));
    }

     public static void printSubsequence(int index, List<Integer> list, int arr[]){
        if(index >= arr.length){
            System.out.println(list);
            return;
        }
         list.remove(Integer.valueOf(arr[index]));
        printSubsequence(index+1,list,arr);
         list.add(arr[index]);;
        printSubsequence(index+1,list,arr);
     }

    public static List<List<Integer>> printSubsequence2(int index, List<Integer> list, int[] arr) {
        // List to store all subsequences
        List<List<Integer>> result = new ArrayList<>();

        if (index >= arr.length) {
            // Base case: add the current subsequence to the result
            result.add(new ArrayList<>(list));
            return result;
        }

        // Exclude the current element
        result.addAll(printSubsequence2(index + 1, list, arr));

        // Include the current element
        list.add(arr[index]);
        result.addAll(printSubsequence2(index + 1, list, arr));

        // Backtrack
        list.remove(list.size() - 1);

        return result;
    }
}

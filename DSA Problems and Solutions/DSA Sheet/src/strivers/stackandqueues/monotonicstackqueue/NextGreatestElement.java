package com.questions.strivers.stackandqueues.monotonicstackqueue;

import java.util.List;
import java.util.Stack;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NextGreatestElement {

    public static void main(String[] args) {
        int arr[] = {4,12,5,3,1,2,5,3,1,2,4,6};
        System.out.println(findNGE(arr));
    }

    private static List<Integer> findNGE(int arr[]){
        int n = arr.length;
        int nge[] = new int[n];
        Stack<Integer> stack = new Stack<>();

        for(int i=n-1;i>=0;i--){
            while (!stack.empty() && stack.peek() <= arr[i]){
                stack.pop();
            }
            if(stack.empty()){
                nge[i] = -1;
            }
            else {
                nge[i] = stack.peek();
            }
            stack.push(arr[i]);
        }
        return Arrays.stream(nge).boxed().collect(Collectors.toList());
    }

}

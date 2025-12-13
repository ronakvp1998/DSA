package com.questions.strivers.stackandqueues.monotonicstackqueue;

import java.util.Stack;

public class PrevSmallerElement {

    public static void main(String[] args) {

    }

    private static int[] prevSmallerElement(int arr[]){

        int nse[] = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        int n = arr.length;
        for(int i=0;i<n;i++){
            while (!stack.empty() && stack.peek() >= arr[i]){
                stack.pop();
            }
            nse[i] = stack.empty() ? -1 : stack.peek();
            stack.push(arr[i]);
        }
        return nse;
    }
}

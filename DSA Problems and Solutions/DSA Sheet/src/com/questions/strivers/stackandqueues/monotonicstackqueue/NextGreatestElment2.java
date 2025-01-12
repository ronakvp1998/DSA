package com.questions.strivers.stackandqueues.monotonicstackqueue;

import java.util.Stack;

public class NextGreatestElment2 {

    public static void main(String[] args) {

    }

    public static int[] findNGE(int arr[]){
        int nge[] = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        int n = arr.length;
        for(int i=(2*n-1); i>=0;i--){
            while (!stack.empty() && stack.peek() <= arr[i%n]){
                stack.pop();
            }
            if(i<n){
                nge[i] = stack.empty() ? -1 : stack.peek();
            }
            stack.push(arr[i%n]);
        }
        return nge;
    }
}

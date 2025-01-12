package com.questions.strivers.stackandqueues.learning;

import java.util.Queue;

public class QueueUsingArray {

    private int arr[];
    private int start, end, currSize, maxSize;

    public QueueUsingArray(){
        arr = new int[16];
        start = -1;
        end = -1;
        currSize = 0;
    }

    public QueueUsingArray(int maxSize){
        this.maxSize = maxSize;
        arr = new int[maxSize];
        start = -1;
        end = -1;
        currSize = 0;
    }

    public void push(int newElement){
        if(currSize == maxSize){
            System.out.println("Queue is full");
            System.exit(1);
        }
        if(end == -1){
            start = 0;
            end = 0;
        }else {
            end = (end+1)%maxSize;
        }
        arr[end] = newElement;
        System.out.println("Element pushed is " + newElement);
        currSize++;
    }

    public int pop(){
        if(start == -1){
            System.out.println("Queue is empty");
            System.exit(1);
        }
        int popped = arr[start];
        if(currSize == 1){
            start = -1;
            end = -1;
        }else{
            start = (start + 1) % maxSize;
        }
        currSize--;
        return popped;
    }

    public int top(){
        if(start == -1){
            System.out.println("Queue is Empty");
            System.exit(1);
        }
        return arr[start];
    }

    public int size(){
        return currSize;
    }
}

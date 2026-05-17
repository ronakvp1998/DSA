package com.questions.strivers.stackandqueues.learning;

public class StackUsingArray {

    int size = 10000;
    int arr[] = new int[size];
    int top = -1;
    void push(int x){
        top++;
        arr[top] = x;
    }
    int pop(){
        if(top < 0){
            return -1;
        }
        int x = arr[top] ;
        top--;
        return x;
    }
    int top(){
        return arr[top];
    }
    int size(){
        return top + 1;
    }

    public static void main(String[] args) {
        StackUsingArray s = new StackUsingArray();
        s.push(6);
        s.push(3);
        s.push(7);
        System.out.println("Top " + s.top());
        System.out.println("size " + s.size());
        System.out.println("Element deleted " + s.pop());
        System.out.println("size " + s.size());
        System.out.println("Top " + s.top());
    }
}

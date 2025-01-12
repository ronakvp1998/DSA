package com.questions.strivers.stackandqueues.learning;

import java.util.Stack;

class MyQueue2{

    Stack<Integer> input = new Stack<>();
    Stack<Integer> output = new Stack<>();
    public MyQueue2(){
    }

    // approach 2 push TC -> O(1)

    public void push(int x){
        System.out.println("pushed element " + x);
        input.push(x);
    }

    public int pop(){
        // shift input to output
        if (output.empty())
            while (input.empty() == false) {
                output.push(input.peek());
                input.pop();
            }


        int x = output.peek();
        output.pop();
        return x;
    }

    public int peek(){
        // shift input to output
        if (output.empty())
            while (input.empty() == false) {
                output.push(input.peek());
                input.pop();
            }
        return output.peek();
    }

    int size(){
        return (output.size() + input.size());
    }
}

public class QueueUsingStack2 {
    public static void main(String[] args) {
        MyQueue2 q = new MyQueue2();
        q.push(3);
        q.push(4);
        System.out.println("The element poped is " + q.pop());
        q.push(5);
        System.out.println("The top element is " + q.peek());
        System.out.println("The size of the queue is " + q.size());

    }
}

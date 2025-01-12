package com.questions.strivers.stackandqueues.learning;


import java.util.LinkedList;
import java.util.Queue;

class MyStack{

    Queue<Integer> queue;
    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
        for(int i=0;i< queue.size()-1;i++){
            queue.add(queue.remove());
        }

    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}

public class StackUsingQueue {

    public static void main(String[] args) {
        MyStack q = new MyStack();
        q.queue.add(10);
    }

}

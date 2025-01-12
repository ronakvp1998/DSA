package com.questions.strivers.stackandqueues.learning;

import java.util.LinkedList;
import java.util.Stack;

class Pair{
    int val;
    int min;
    Pair(int val,int min){
        this.min = min;
        this.val = val;
    }
}

public class MinStack {

    Stack<Pair> stack = new Stack<>();

    public MinStack() {
        stack = new Stack <>();
    }

    public void push(int val){
        int min;
        if(stack.empty())  min = val;
        else min = Math.min(stack.peek().min,val);
        stack.push(new Pair(val,min));
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek().val;
    }

    public int getMin() {
        return stack.peek().min;
    }

}

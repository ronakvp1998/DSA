package StackDemos;

//code 6 :- Reverse a Stack
// upwards: pop the elements
// downwards: push at the bottom of the stack

import java.util.Stack;

public class ReverseAStack {

    public static void stackReverse(Stack<Integer> stack){
        if(stack.isEmpty()){
            return;
        }

        int top = stack.pop();
        stackReverse(stack);
        pushAtBottom(stack,top);

    }

    public static void pushAtBottom(Stack<Integer>s,int data){
        if(s.isEmpty()){
            s.push(data);
            return;
        }

        int top = s.pop();
        pushAtBottom(s,data);
        s.push(top);

    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        // (top)4 3 2 1

        stackReverse(stack);

        while (!stack.isEmpty()){
            System.out.println(stack.pop()); // {top} 1,2,3,4
        }
    }
}

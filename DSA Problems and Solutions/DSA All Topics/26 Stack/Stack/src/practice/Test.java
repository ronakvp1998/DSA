package practice;

import java.util.Arrays;
import java.util.Stack;

public class Test {

    public static void reverse(Stack<Integer> stack){
        if(stack.isEmpty()){
            return;
        }
        int top = stack.pop();
        reverse(stack);
        addBotton(stack,top);
    }

    public static void addBotton(Stack<Integer>stack,int data){
        if(stack.isEmpty()){
            stack.push(data);
            return;
        }

        int top = stack.pop();
        addBotton(stack,data);
        stack.push(top);

    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
//        while (!stack.isEmpty()){
//            System.out.print(stack.pop() + " ");
//        }
        reverse(stack);
//
        while (!stack.isEmpty()){
            System.out.print(stack.pop() + " ");
        }
    }
}

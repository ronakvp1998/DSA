package StackDemos;

import java.util.Stack;

// code 4 -> push at the bottom of the stack
public class PushBottomOfStack {

    public static void pushBottom(Stack<Integer>s,int data){
        if(s.empty()){
            s.push(data);
            return;
        }
        int temp =s.pop();
        pushBottom(s,data);
        s.push(temp);

    }

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);

        pushBottom(s,4);

        while(!s.empty()){
            System.out.println(s.pop());
        }
        System.out.println();

    }
}

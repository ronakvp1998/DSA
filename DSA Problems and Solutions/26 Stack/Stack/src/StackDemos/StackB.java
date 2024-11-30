package StackDemos;

// code 1 stack Implementation using ArrayList

import java.util.ArrayList;

public class StackB {
    static class Stack{
        static ArrayList<Integer> list = new ArrayList<>();

        // 1 check stack is empty
        public static boolean isEmpty(){
            return list.size() == 0;
        }

        // 2 push into stack
        public static void push(int data){
            list.add(data);   // add Last
        }

        // 3 pop from stack
        public static int pop(){
            if(isEmpty()){
                return -1;
            }
            int top = list.get(list.size()-1);
            list.remove(list.size()-1);
            return top;
        }

        // 4 peek from stack
        public static int peek(){
            if(isEmpty()){
                return -1;
            }
            int top = list.get(list.size()-1);
            return top;
        }
    }

    public static void main(String[] args) {

        Stack s = new Stack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        while (!s.isEmpty()){
            System.out.println(s.peek());
            s.pop();
        }
    }

}

package StackDemos;

import java.util.Stack;

// code 5 -> Reverse a String using a Stack
public class ReverseStringStack {

    public static void main(String[] args) {
        String str = "Ronak Panchal";
        Stack<Character> s = new Stack<>();
        int idx = 0;
        while(idx < str.length()){
            s.push(str.charAt(idx));
            idx++;
        }
        StringBuilder result = new StringBuilder("");
        while(!s.isEmpty()){
            char curr = s.pop();
            result.append(curr);
        }
        System.out.println(str);
        str = result.toString();
        System.out.println(str);

//        String s = "Ronak Panchal";
//        char arr[] = s.toCharArray();
//        Stack<Character> stack = new Stack<>();
//        for(char c : arr){
//            stack.push(c);
//        }
//
//        StringBuilder sb = new StringBuilder();
//        while(!stack.isEmpty()){
//            sb.append(stack.pop());
//        }
//        System.out.println(s);
//        System.out.println(sb);
    }
}

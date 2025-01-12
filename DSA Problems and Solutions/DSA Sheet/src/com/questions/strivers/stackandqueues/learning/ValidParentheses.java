package com.questions.strivers.stackandqueues.learning;

import java.util.Stack;

public class ValidParentheses {

    public static void main(String[] args) {
        String str = "()[{}()]";
        System.out.println(checkPar(str));
    }

    public static boolean checkPar(String s){
        Stack<Character> stack = new Stack<>();
        for(char c : s.toCharArray()){
            if(c == '(' || c == '{' || c == '['){
                stack.push(c);
            } else if (c == ')' && !stack.empty() && stack.peek() == '(' )  {
                stack.pop();
            }else if (c == '}' && !stack.empty() && stack.peek() == '{' )  {
                stack.pop();
            }else if (c == ']' && !stack.empty() && stack.peek() == '[' )  {
                stack.pop();
            }else {
                return false;
            }
        }
        return true;
    }
}

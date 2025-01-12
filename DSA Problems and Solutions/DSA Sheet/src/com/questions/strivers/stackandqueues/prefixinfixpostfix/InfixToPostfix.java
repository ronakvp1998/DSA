package com.questions.strivers.stackandqueues.prefixinfixpostfix;

import java.util.Stack;

public class InfixToPostfix {
    public static void main(String[] args) {
        String str = "a+b*(c^d-e)";
        System.out.println(str);
        System.out.println(infixToPostfix(str));
    }

    public static String infixToPostfix(String str){
        int i=0;
        Stack<Character> stack = new Stack<>();
        StringBuilder ans = new StringBuilder();

        while (i < str.length()){
            char c = str.charAt(i);
            // opprands append into ans
            if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')){
                ans.append(c);
            }
            // opening brac push into stack
            else if (c == '(') {
                stack.push(c);
            }
            // closing brack
            // 1 pop till open brac and append to ans
            // 2 pop the opening brac
            else if (c == ')') {
                while (!stack.empty() && stack.peek() != '('){
                    ans.append(stack.pop());
                }
                // pop the opening bracket
                stack.pop();
            }
            // if top of stak has more priority than curr char then append it to and
            // else push curr char to stack
            else {
                while (!stack.empty() && priority(c) <= priority(stack.peek())) {
                    ans.append(stack.pop());
                }
                stack.push(c);
            }
            i++;
        }
        while (!stack.empty()){
            if(stack.peek() == '(') return "Invalid exp";
            ans.append(stack.pop());
        }

        return ans.toString();
    }

    public static int priority(char c){
        if(c == '^') return 3;
        else if (c == '*' || c == '/') return 2;
        else if (c == '+' || c == '-') return 1;
        else return 0;
    }
}

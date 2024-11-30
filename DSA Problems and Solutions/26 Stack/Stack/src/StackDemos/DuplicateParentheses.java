package StackDemos;

import java.util.Stack;

//code 10:- duplicate Parentheses
// str is already valid with parentheses
public class DuplicateParentheses {

    public static boolean isDuplicate(String str){
        Stack<Character> stack = new Stack<>();

        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);

            // closing
            if(ch == ')'){
                int count = 0;
//                while(!stack.isEmpty() && stack.peek() != '('){
//                because str is already valid so stack can never be empty it will have proper pairs of parentheses

                while(stack.pop() != '('){
                    count++;
                }
                if(count<1){
                    return true;    // duplicate
                }
            }else{
                // opening parentheses, operator, operands
                stack.push(ch);
            }

        }
        return false;
    }

    public static void main(String[] args) {
        String str1 = "((a+b))"; // true
        String str2 = "(a-b)";  // false

        System.out.println(isDuplicate(str2));

    }
}

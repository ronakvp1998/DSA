package com.questions.strivers.string.easy;

import java.util.Stack;

/*
https://leetcode.com/problems/remove-outermost-parentheses/description/
A valid parentheses string is either empty "", "(" + A + ")", or A + B, where A and B are valid parentheses strings, and + represents string concatenation.

For example, "", "()", "(())()", and "(()(()))" are all valid parentheses strings.
A valid parentheses string s is primitive if it is nonempty, and there does not exist a way to split it into s = A + B, with A and B nonempty valid parentheses strings.

Given a valid parentheses string s, consider its primitive decomposition: s = P1 + P2 + ... + Pk, where Pi are primitive valid parentheses strings.

Return s after removing the outermost parentheses of every primitive string in the primitive decomposition of s.


 */
public class RemoveOutermostParentheses {

    // counter approach
    private static String removeOuterParentheses(String s){
        StringBuilder result = new StringBuilder();
        int count = 0;
        for(char ch : s.toCharArray()){
            if(ch == '('){
                if(count > 0){
                    result.append(ch);
                }
                count++;
            }else{
                count--;
                if(count > 0){
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    // stack approach
    private static String removeOuterParentheses2(String s){
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        for(char ch : s.toCharArray()){
            if(ch == '('){
                // if stack is not empty, it's not outermost so append
                if(!stack.isEmpty()){
                    result.append(ch);
                }
                stack.push(ch);
            }else{
                stack.pop();
                // if stack is not empty, it's not outermost so append
                if(!stack.isEmpty()){
                    result.append(ch);
                }
            }
        }
        return result.toString();

    }

    public static void main(String[] args) {
        String s = "(()())(())";
        System.out.println(removeOuterParentheses2(s));
    }

}

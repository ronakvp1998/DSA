package com.questions.strivers.recursion.basics.stacksqueuesproblem;

import java.util.Stack;

public class DeleteMiddleOfStack {

    // Function to delete the middle element
    public static void deleteMiddle(Stack<Integer> stack, int current, int size) {
        // Base case: if we reached the middle element
        if (current == size / 2) {
            stack.pop();  // remove the middle element
            return;
        }

        // Pop the top element and recurse deeper
        int top = stack.pop();
        deleteMiddle(stack, current + 1, size);

        // Push the element back after middle is removed
        stack.push(top);
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println("Original Stack: " + stack);

        int size = stack.size();
        deleteMiddle(stack, 0, size);

        System.out.println("Stack after deleting middle: " + stack);
    }
}

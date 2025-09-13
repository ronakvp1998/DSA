package com.questions.strivers.recursion.medium.stacksqueuesproblem;

import java.util.Stack;

public class ReverseStackRecursively {

    /*
     * Problem: Reverse a stack using recursion
     *
     * Approach:
     * - Use recursion to pop elements from the stack until it's empty.
     * - While backtracking, insert the popped element at the bottom of the stack.
     *
     * Why it works?
     * - When recursion unwinds, elements get inserted at the bottom,
     *   reversing the stack order.
     */

    // Function to reverse the stack
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return; // Base case: if stack is empty, nothing to reverse
        }

        // Step 1: Pop the top element
        int top = stack.pop();

        // Step 2: Recursively reverse the remaining stack
        reverse(stack);

        // Step 3: Insert the popped element at the bottom
        insertAtBottom(stack, top);
    }

    // Helper function to insert an element at the bottom of the stack
    private static void insertAtBottom(Stack<Integer> stack, int element) {
        if (stack.isEmpty()) {
            stack.push(element); // Base case: place element at the bottom
            return;
        }

        // Step 1: Pop top element
        int top = stack.pop();

        // Step 2: Recursively insert into the smaller stack
        insertAtBottom(stack, element);

        // Step 3: Push the original top back
        stack.push(top);
    }

    // Driver code to test
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println("Original Stack: " + stack);

        reverse(stack);

        System.out.println("Reversed Stack: " + stack);
    }
}
/*
⏱️ Time Complexity

Each element is popped and inserted at bottom.

insertAtBottom() itself takes O(n) in worst case (stack unwinding).

For n elements, total complexity = O(n²).

💾 Space Complexity

Recursion depth = O(n) (for both reverse and insert calls).

Extra space = O(n) (stack frames).
 */

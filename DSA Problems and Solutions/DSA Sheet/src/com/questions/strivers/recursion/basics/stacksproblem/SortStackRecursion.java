package com.questions.strivers.recursion.basics.stacksproblem;

import java.util.Stack;

public class SortStackRecursion {

        // Function to sort the entire stack
        public static void sortStack(Stack<Integer> stack) {
            // Base case: if stack has 1 or 0 elements, it's already sorted
            if (stack.size() <= 1) {
                return;
            }

            // Step 1: Pop the top element
            int top = stack.pop();

            // Step 2: Recursively sort the remaining stack
            sortStack(stack);

            // Step 3: Insert the popped element back in sorted order
            insertSorted(stack, top);
        }

        // Helper function to insert element into sorted stack
        private static void insertSorted(Stack<Integer> stack, int element) {
            // Base case: If stack is empty OR top is smaller/equal, push element
            if (stack.isEmpty() || stack.peek() <= element) {
                stack.push(element);
                return;
            }

            // Otherwise pop the top element and recurse
            int top = stack.pop();
            insertSorted(stack, element);

            // Push the stored element back
            stack.push(top);
        }

        public static void main(String[] args) {
            Stack<Integer> stack = new Stack<>();
            stack.push(3);
            stack.push(1);
            stack.push(4);
            stack.push(2);
            stack.push(5);

            System.out.println("Original Stack: " + stack);

            sortStack(stack);

            System.out.println("Sorted Stack: " + stack);
        }

}

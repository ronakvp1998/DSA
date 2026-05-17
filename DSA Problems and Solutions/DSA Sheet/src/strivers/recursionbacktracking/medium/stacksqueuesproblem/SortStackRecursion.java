package com.questions.strivers.recursionbacktracking.medium.stacksqueuesproblem;

import java.util.Stack;

/**
 * Problem Statement:
 * ------------------
 * Given a stack of integers, sort the stack in ascending order using recursion.
 * You cannot use any loop or an extra data structure (like another stack or array).
 * Only recursion is allowed.
 *
 * Example:
 * Input:  [3, 1, 4, 2, 5]
 * Output: [1, 2, 3, 4, 5]
 *
 * ------------------------------------------------------------
 * Code Logic:
 * - Use recursion to sort the stack:
 *   1. Pop the top element.
 *   2. Recursively sort the remaining stack.
 *   3. Insert the popped element back in the correct position (sorted order).
 * - The helper function `insertSorted()` ensures the popped element
 *   is inserted at the right position by recursively popping elements
 *   until the correct spot is found.
 * - Once inserted, push the temporarily removed elements back.
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - O(N^2), where N is the size of the stack.
 * - Each element may require traversing the entire stack in the worst case.
 *
 * Space Complexity:
 * - O(N), due to recursion call stack.
 * - No extra data structures are used apart from recursion.
 */

public class SortStackRecursion {

    // Function to sort the entire stack
    private static void sortStack(Stack<Integer> stack) {
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

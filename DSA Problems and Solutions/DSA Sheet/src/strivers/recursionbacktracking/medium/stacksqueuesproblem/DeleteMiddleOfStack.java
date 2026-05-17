package com.questions.strivers.recursionbacktracking.medium.stacksqueuesproblem;

import java.util.Stack;

/**
 * Problem Statement:
 * ------------------
 * Given a stack of integers, delete its middle element using recursion.
 * You cannot use any additional data structure like an auxiliary stack.
 *
 * Example:
 * Input: Stack = [1, 2, 3, 4, 5]
 * Output: [1, 2, 4, 5]
 * Explanation: Middle element 3 is removed.
 *
 * Input: Stack = [1, 2, 3, 4, 5, 6]
 * Output: [1, 2, 4, 5, 6]
 * Explanation: Middle element (3rd, 0-based) is removed.
 *
 * ------------------------------------------------------------
 * Code Logic:
 * - Use recursion to traverse the stack.
 * - Base Case: When the current index == size/2, pop the middle element.
 * - Recursive Steps:
 *   1. Pop the top element.
 *   2. Recurse until the middle is found and deleted.
 *   3. Push back the popped elements to maintain the original order.
 *
 * ------------------------------------------------------------
 * Time Complexity:
 * - O(N), where N is the size of the stack.
 * - Each element is popped and pushed back once.
 *
 * Space Complexity:
 * - O(N), due to recursion stack (function call for each element).
 */

public class DeleteMiddleOfStack {

    // Function to delete the middle element using recursion
    private static void deleteMiddle(Stack<Integer> stack, int current, int size) {
        // Base case: if we reached the middle element
        if (current == size / 2) {
            stack.pop();  // remove the middle element
            return;
        }

        // Step 1: Pop the top element and recurse deeper
        int top = stack.pop();
        deleteMiddle(stack, current + 1, size);

        // Step 2: Push the element back after middle is removed
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

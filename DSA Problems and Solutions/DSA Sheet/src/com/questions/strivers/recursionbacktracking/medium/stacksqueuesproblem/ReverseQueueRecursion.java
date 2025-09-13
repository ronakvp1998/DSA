package com.questions.strivers.recursionbacktracking.medium.stacksqueuesproblem;

import java.util.ArrayList;
import java.util.List;

public class ReverseQueueRecursion {


        /*
         * Problem: Reverse a Queue using recursion
         *
         * Approach:
         * - Use recursion to pop elements from the queue until it's empty.
         * - While backtracking, insert the popped element at the bottom of the queue.
         *
         * Why it works?
         * - When recursion unwinds, elements get inserted at the bottom,
         *   reversing the queue order.
         */

        // Function to reverse the queue
        public static void reverse(List<Integer> queue) {
            if (queue.isEmpty()) {
                return; // Base case: if queue is empty, nothing to reverse
            }

            // Step 1: Pop the top element
            int top = queue.remove(queue.size()-1);

            // Step 2: Recursively reverse the remaining queue
            reverse(queue);

            // Step 3: Insert the popped element at the bottom
            insertAtBottom(queue, top);
        }

        // Helper function to insert an element at the bottom of the queue
        private static void insertAtBottom(List<Integer> queue, int element) {
            if (queue.isEmpty()) {
                queue.add(element); // Base case: place element at the bottom
                return;
            }

            // Step 1: Pop top element
            int top = queue.remove(queue.size()-1);

            // Step 2: Recursively insert into the smaller queue
            insertAtBottom(queue, element);

            // Step 3: Push the original top back
            queue.add(top);
        }

        // Driver code to test
        public static void main(String[] args) {
            List<Integer> queue = new ArrayList<>();
            queue.add(1);
            queue.add(2);
            queue.add(3);
            queue.add(4);
            queue.add(5);

            System.out.println("Original queue: " + queue);

            reverse(queue);

            System.out.println("Reversed queue: " + queue);
        }
    }
/*
⏱️ Time Complexity

Each element is popped and inserted at bottom.

insertAtBottom() itself takes O(n) in worst case (queue unwinding).

For n elements, total complexity = O(n²).

💾 Space Complexity

Recursion depth = O(n) (for both reverse and insert calls).

Extra space = O(n) (queue frames).
 */


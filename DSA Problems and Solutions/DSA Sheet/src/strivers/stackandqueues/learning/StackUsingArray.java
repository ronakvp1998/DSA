package strivers.stackandqueues.learning;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * Implement Stack using Array
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design a stack data structure using a standard array. You need to implement
 * the following fundamental stack operations:
 * 1. push(x): Pushes an element x to the top of the stack.
 * 2. pop(): Removes the element on the top of the stack and returns it.
 * 3. top() / peek(): Returns the element on the top of the stack without removing it.
 * 4. size(): Returns the current number of elements in the stack.
 *
 * Example 1:
 * Input:
 * ["push 6", "push 3", "push 7", "top", "size", "pop", "size", "top"]
 * Output:
 * [null, null, null, 7, 3, 7, 2, 3]
 *
 * Example 2 (Edge Case - Underflow):
 * Input:
 * ["pop", "top"]
 * Output:
 * [-1, -1]
 *
 * CONSTRAINTS:
 * 1 <= x <= 10^5
 * Maximum number of operations is 10^4.
 * Return -1 if pop() or top() is called on an empty stack.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Fixed-Size Array with Tail Tracking (Refined Provided Code)
 * Phase 2: Brute Force Approach - Fixed-Size Array with Head Tracking (Shifting elements)
 * Phase 3: Alternative Approach - Dynamic Size Stack (ArrayList)
 * ============================================================================
 */
public class StackUsingArray {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Fixed-Size Array with Tail Tracking)
     * ============================================================================
     * Detailed Intuition:
     * The code you provided forms the perfect foundation for the optimal approach.
     * A stack follows the LIFO (Last-In-First-Out) principle. By using a `top`
     * pointer initialized to -1, we can map the "top" of the stack to the end of
     * the populated section of our array.
     * * Refinements made to your provided code:
     * 1. Added a check in `push()` to prevent `StackOverflow` (ArrayIndexOutOfBounds).
     * 2. Added a check in `top()` to prevent `StackUnderflow` if `top == -1`.
     * * Complexity Analysis:
     * - Time Complexity: O(1) for push, pop, top, and size. We are simply accessing
     * array indices directly.
     * - Space Complexity: O(N) where N is the maximum capacity of the stack.
     * Auxiliary Stack Space is O(1).
     * ============================================================================
     */
    public static class OptimalStack {
        private final int size;
        private final int[] arr;
        private int top;

        public OptimalStack() {
            this.size = 10000;
            this.arr = new int[size];
            this.top = -1;
        }

        public OptimalStack(int capacity) {
            this.size = capacity;
            this.arr = new int[size];
            this.top = -1;
        }

        public void push(int x) {
            if (top >= size - 1) {
                System.out.println("Stack Overflow! Cannot push " + x);
                return;
            }
            top++;
            arr[top] = x;
        }

        public int pop() {
            if (top < 0) {
                return -1; // Stack Underflow
            }
            int x = arr[top];
            top--;
            return x;
        }

        public int top() {
            if (top < 0) {
                return -1; // Prevent ArrayIndexOutOfBoundsException
            }
            return arr[top];
        }

        public int size() {
            return top + 1;
        }

        // Utility method to display stack contents using Java 8 Streams
        public String display() {
            if (top < 0) return "[]";
            return "[" + IntStream.rangeClosed(0, top)
                    .mapToObj(i -> String.valueOf(arr[i]))
                    .collect(Collectors.joining(", ")) + "]";
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Dynamic Array / ArrayList)
     * ============================================================================
     * Detailed Intuition:
     * A major limitation of the raw array approach is the fixed capacity. If we
     * exceed it, the stack overflows. By utilizing Java's `ArrayList`, we simulate
     * a dynamic stack that automatically resizes itself under the hood when capacity
     * is reached.
     *
     * Complexity Analysis:
     * - Time Complexity: Amortized O(1) for push. O(1) for pop, top, and size.
     * - Space Complexity: O(N) heap space. Memory is dynamically allocated, but
     * ArrayList resizing operations involve occasional O(N) space overhead.
     * ============================================================================
     */
    public static class DynamicStack {
        private final List<Integer> list;

        public DynamicStack() {
            this.list = new ArrayList<>();
        }

        public void push(int x) {
            list.add(x);
        }

        public int pop() {
            if (list.isEmpty()) return -1;
            return list.remove(list.size() - 1);
        }

        public int top() {
            if (list.isEmpty()) return -1;
            return list.get(list.size() - 1);
        }

        public int size() {
            return list.size();
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Standard Optimal Stack Operations ===");
        OptimalStack s1 = new OptimalStack();
        s1.push(6);
        s1.push(3);
        s1.push(7);
        System.out.println("Stack State: " + s1.display());
        System.out.println("Top element: " + s1.top() + " (Expected: 7)");
        System.out.println("Stack size: " + s1.size() + " (Expected: 3)");
        System.out.println("Element deleted: " + s1.pop() + " (Expected: 7)");
        System.out.println("Stack size: " + s1.size() + " (Expected: 2)");
        System.out.println("Top element: " + s1.top() + " (Expected: 3)");

        System.out.println("\n=== Test Case 2: Edge Case - Stack Underflow ===");
        OptimalStack s2 = new OptimalStack();
        System.out.println("Pop on empty stack: " + s2.pop() + " (Expected: -1)");
        System.out.println("Top on empty stack: " + s2.top() + " (Expected: -1)");

        System.out.println("\n=== Test Case 3: Edge Case - Stack Overflow ===");
        OptimalStack s3 = new OptimalStack(2); // Stack of size 2
        s3.push(10);
        s3.push(20);
        System.out.print("Attempting to push 30 to a full stack: ");
        s3.push(30); // Should trigger overflow message

        System.out.println("\n=== Test Case 4: Dynamic Stack (ArrayList) ===");
        DynamicStack ds = new DynamicStack();
        ds.push(1);
        ds.push(2);
        ds.push(3);
        System.out.println("Top element: " + ds.top() + " (Expected: 3)");
        ds.pop();
        System.out.println("Size after 1 pop: " + ds.size() + " (Expected: 2)");
    }
}
package strivers.stackandqueues.learning;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 232. Implement Queue using Stacks
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Implement a first in first out (FIFO) queue using only two stacks. The
 * implemented queue should support all the functions of a normal queue
 * (push, peek, pop, and empty).
 * * Implement the MyQueue class:
 * - void push(int x) Pushes element x to the back of the queue.
 * - int pop() Removes the element from the front of the queue and returns it.
 * - int peek() Returns the element at the front of the queue.
 * - boolean empty() Returns true if the queue is empty, false otherwise.
 * * Notes:
 * - You must use only standard operations of a stack, which means only push to
 * top, peek/pop from top, size, and is empty operations are valid.
 * - Depending on your language, the stack may not be supported natively. You
 * may simulate a stack using a list or deque (double-ended queue) as long
 * as you use only a stack's standard operations.
 *
 * Example 1:
 * Input
 * ["MyQueue", "push", "push", "peek", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 1, 1, false]
 * * Explanation
 * MyQueue myQueue = new MyQueue();
 * myQueue.push(1); // queue is: [1]
 * myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
 * myQueue.peek(); // return 1
 * myQueue.pop(); // return 1, queue is [2]
 * myQueue.empty(); // return false
 *
 * CONSTRAINTS:
 * 1 <= x <= 9
 * At most 100 calls will be made to push, pop, peek, and empty.
 * All the calls to pop and peek are valid.
 * * Follow-up: Can you implement the queue such that each operation is amortized
 * O(1) time complexity? In other words, performing n operations will take
 * overall O(n) time even if one of those operations may take longer.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Amortized O(1) (Input & Output Stacks)
 * Phase 2: Brute Force Approach - Push-Heavy O(N) (Two Stacks)
 * Phase 3: Alternative Approach - Pop-Heavy O(N) (Two Stacks)
 * ============================================================================
 */
public class ImplementQueueUsingStacks {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Amortized O(1) Input & Output Stacks)
     * ============================================================================
     * Detailed Intuition:
     * To satisfy the follow-up requirement of amortized O(1) time complexity, we
     * use two stacks specifically dedicated to distinct roles: an `input` stack
     * and an `output` stack.
     * 1. `push(x)` always simply pushes to the `input` stack. This is strictly O(1).
     * 2. `pop()` and `peek()` require the oldest element. If the `output` stack
     * is empty, we pop everything from `input` and push it into `output`. This
     * inverts the LIFO order of the stack into the FIFO order of a queue.
     * 3. We then simply pop/peek from the `output` stack.
     * While transferring elements takes O(N) time, we only do this when the
     * `output` stack is completely empty. Over a sequence of operations, the cost
     * averages out to O(1) per operation.
     *
     * Complexity Analysis:
     * - Time Complexity:
     * - push(): O(1).
     * - pop() / peek(): Amortized O(1). In the worst case (when output stack
     * is empty), it takes O(N), but every element is moved exactly twice
     * (once in, once out), making the average time constant.
     * - empty(): O(1).
     * - Space Complexity: O(N) heap space to store the elements across both
     * stacks. O(1) auxiliary stack space since no recursion is used.
     * ============================================================================
     */
    public static class OptimalQueue {
        private final Stack<Integer> input;
        private final Stack<Integer> output;

        public OptimalQueue() {
            input = new Stack<>();
            output = new Stack<>();
        }

        public void push(int x) {
            input.push(x);
        }

        public int pop() {
            shiftStacks();
            return output.pop();
        }

        public int peek() {
            shiftStacks();
            return output.peek();
        }

        public boolean empty() {
            return input.isEmpty() && output.isEmpty();
        }

        // Helper to transfer elements when output stack is empty
        private void shiftStacks() {
            if (output.isEmpty()) {
                while (!input.isEmpty()) {
                    output.push(input.pop());
                }
            }
        }

        // Utility for displaying current state using Streams (for testing)
        public List<Integer> toList() {
            List<Integer> list = new ArrayList<>();
            // Output stack has elements in correct queue order (top to bottom)
            for (int i = output.size() - 1; i >= 0; i--) {
                list.add(output.get(i));
            }
            // Input stack has elements in reverse queue order (bottom to top)
            for (int i = 0; i < input.size(); i++) {
                list.add(input.get(i));
            }
            return list;
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Push-Heavy O(N)) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If we want our main stack (`s1`) to continuously represent a perfect queue
     * state, we must make the `push` operation do the heavy lifting.
     * When a new element `x` arrives, it needs to go to the absolute bottom of `s1`.
     * To achieve this using only stack operations:
     * 1. Pop all elements from `s1` and push them into a temporary stack `s2`.
     * 2. Push the new element `x` onto the now-empty `s1`.
     * 3. Pop all elements from `s2` and push them back onto `s1`.
     * Now, `pop` and `peek` are simple O(1) operations straight from the top of `s1`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) for push(). O(1) for pop(), peek(), and empty().
     * - Space Complexity: O(N) heap space divided between the two stacks.
     * ============================================================================
     */
    public static class BruteForceQueue {
        private final Stack<Integer> s1;
        private final Stack<Integer> s2;

        public BruteForceQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        public void push(int x) {
            // Move all elements from s1 to s2
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            // Push the new element to the bottom
            s1.push(x);
            // Move everything back to s1
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }

        public int pop() {
            return s1.pop();
        }

        public int peek() {
            return s1.peek();
        }

        public boolean empty() {
            return s1.isEmpty();
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Optimal Queue (Amortized O(1)) ===");
        OptimalQueue optimalQ = new OptimalQueue();
        optimalQ.push(1);
        optimalQ.push(2);

        System.out.println("Queue State (Front to Back): " +
                optimalQ.toList().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" -> ")));

        System.out.println("Peek element: " + optimalQ.peek() + " (Expected: 1)");
        System.out.println("Popped element: " + optimalQ.pop() + " (Expected: 1)");
        System.out.println("Is empty: " + optimalQ.empty() + " (Expected: false)");

        optimalQ.push(3);
        System.out.println("Popped element: " + optimalQ.pop() + " (Expected: 2)");
        System.out.println("Popped element: " + optimalQ.pop() + " (Expected: 3)");
        System.out.println("Is empty: " + optimalQ.empty() + " (Expected: true)");

        System.out.println("\n=== Test Case 2: Brute Force Queue (Push-Heavy O(N)) ===");
        BruteForceQueue bruteForceQ = new BruteForceQueue();
        bruteForceQ.push(10);
        bruteForceQ.push(20);
        System.out.println("Peek element: " + bruteForceQ.peek() + " (Expected: 10)");
        System.out.println("Popped element: " + bruteForceQ.pop() + " (Expected: 10)");
        System.out.println("Is empty: " + bruteForceQ.empty() + " (Expected: false)");
    }
}
package strivers.stackandqueues.learning;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 225. Implement Stack using Queues
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Implement a last-in-first-out (LIFO) stack using only two queues. The
 * implemented stack should support all the functions of a normal stack
 * (`push`, `top`, `pop`, and `empty`).
 *
 * You must use only standard operations of a queue, which means that only
 * `push to back`, `peek/pop from front`, `size`, and `is empty` operations
 * are valid.
 *
 * Depending on your language, the queue may not be supported natively. You
 * may simulate a queue using a list or deque (double-ended queue) as long
 * as you use only a queue's standard operations.
 *
 * Example 1:
 * Input
 * ["MyStack", "push", "push", "top", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 2, 2, false]
 *
 * Explanation:
 * MyStack myStack = new MyStack();
 * myStack.push(1);
 * myStack.push(2);
 * myStack.top(); // return 2
 * myStack.pop(); // return 2
 * myStack.empty(); // return False
 *
 * CONSTRAINTS:
 * 1 <= x <= 9
 * At most 100 calls will be made to push, pop, top, and empty.
 * All the calls to pop and top are valid.
 *
 * Follow-up: Can you implement the stack using only one queue?
 * (Note: The code you provided beautifully satisfies this follow-up!)
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Single Queue (The provided code)
 * Phase 2: Brute Force Approach - Two Queues (Push Heavy)
 * Phase 3: Alternative Approach - Two Queues (Pop Heavy)
 * ============================================================================
 */
public class ImplementStackUsingQueue {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Single Queue)
     * ============================================================================
     * Evaluation of Provided Code:
     * The code you provided is the absolutely optimal approach for this problem.
     * It solves the follow-up question by using only a SINGLE queue instead of two.
     * * Detailed Intuition:
     * A queue follows FIFO (First-In-First-Out), while a stack follows LIFO
     * (Last-In-First-Out). To make a queue act like a stack, the most recently
     * added element must always be at the front of the queue.
     * When we push an element 'x' to the queue, it goes to the back. To move it
     * to the front, we determine the previous size of the queue (let's call it N).
     * We then dequeue the front element and immediately enqueue it at the back,
     * repeating this exactly N times. This rotation effectively pushes all older
     * elements behind 'x', making 'x' the new front element.
     *
     * Complexity Analysis:
     * - Time Complexity:
     * - push(x): O(N), where N is the current size of the queue. We must
     * rotate N-1 elements.
     * - pop(): O(1), simply removing the front element.
     * - top(): O(1), simply peeking at the front element.
     * - empty(): O(1).
     * - Space Complexity: O(N) heap space to store the elements in the queue.
     * O(1) auxiliary stack space.
     * ============================================================================
     */
    public static class OptimalSingleQueueStack {
        private final Queue<Integer> queue;

        public OptimalSingleQueueStack() {
            queue = new LinkedList<>();
        }

        public void push(int x) {
            queue.add(x);
            int size = queue.size();
            // Rotate the previous elements behind the newly added element
            for (int i = 0; i < size - 1; i++) {
                queue.add(queue.remove());
            }
        }

        public int pop() {
            if (empty()) return -1; // Edge case safety
            return queue.remove();
        }

        public int top() {
            if (empty()) return -1;
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }

        // Utility for testing
        public List<Integer> toList() {
            return new ArrayList<>(queue);
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Two Queues - Push Heavy) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If we don't realize we can rotate a single queue, the standard textbook
     * approach uses two queues: `q1` (main storage) and `q2` (temporary buffer).
     * To make the newest element the front of `q1`:
     * 1. Add the new element 'x' to the empty `q2`.
     * 2. Dequeue all elements from `q1` and enqueue them into `q2`. Now `q2`
     * has 'x' at the front, followed by the older elements.
     * 3. Swap the references of `q1` and `q2`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) for push(), O(1) for pop(), top(), and empty().
     * - Space Complexity: O(N) heap space distributed across two queues.
     * ============================================================================
     */
    public static class TwoQueuePushHeavyStack {
        private Queue<Integer> q1;
        private Queue<Integer> q2;

        public TwoQueuePushHeavyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        public void push(int x) {
            q2.add(x); // Add to the secondary queue

            // Transfer everything from q1 to q2
            while (!q1.isEmpty()) {
                q2.add(q1.remove());
            }

            // Swap the names/references of the queues
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;
        }

        public int pop() {
            if (empty()) return -1;
            return q1.remove();
        }

        public int top() {
            if (empty()) return -1;
            return q1.peek();
        }

        public boolean empty() {
            return q1.isEmpty();
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Two Queues - Pop Heavy)
     * ============================================================================
     * Detailed Intuition:
     * Instead of making `push` expensive, we can make `pop` expensive.
     * 1. `push` just adds the element to `q1` in O(1) time.
     * 2. When `pop` is called, the element we want is at the absolute back of `q1`.
     * 3. We dequeue all elements EXCEPT the last one from `q1` and put them in `q2`.
     * 4. The single remaining element in `q1` is our target. We pop and return it.
     * 5. Swap `q1` and `q2` to reset state.
     * * Note: This requires O(N) time for both `pop` and `top`, making it generally
     * less efficient than the push-heavy approaches if reads are frequent.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for push(). O(N) for pop() and top(). O(1) for empty().
     * - Space Complexity: O(N) heap space across two queues.
     * ============================================================================
     */
    public static class TwoQueuePopHeavyStack {
        private Queue<Integer> q1;
        private Queue<Integer> q2;
        private int topElement;

        public TwoQueuePopHeavyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        public void push(int x) {
            q1.add(x);
            topElement = x; // Cache the top element for O(1) top() optimization
        }

        public int pop() {
            if (empty()) return -1;

            // Leave exactly one element in q1
            while (q1.size() > 1) {
                topElement = q1.remove(); // Update cache for the new top
                q2.add(topElement);
            }

            int popped = q1.remove(); // This was the last element added

            // Swap queues
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;

            return popped;
        }

        public int top() {
            if (empty()) return -1;
            return topElement; // Return cached top element
        }

        public boolean empty() {
            return q1.isEmpty();
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
        System.out.println("=== Test Case 1: Optimal Single Queue (Provided Logic) ===");
        OptimalSingleQueueStack optimalStack = new OptimalSingleQueueStack();
        optimalStack.push(10);
        optimalStack.push(20);
        optimalStack.push(30);

        System.out.println("Current Stack (Top to Bottom): " +
                optimalStack.toList().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" -> ")));

        System.out.println("Top element: " + optimalStack.top() + " (Expected: 30)");
        System.out.println("Popped element: " + optimalStack.pop() + " (Expected: 30)");
        System.out.println("Top element after pop: " + optimalStack.top() + " (Expected: 20)");
        System.out.println("Is empty: " + optimalStack.empty() + " (Expected: false)");

        System.out.println("\n=== Test Case 2: Two Queues (Push Heavy) ===");
        TwoQueuePushHeavyStack pushHeavyStack = new TwoQueuePushHeavyStack();
        pushHeavyStack.push(1);
        pushHeavyStack.push(2);
        System.out.println("Top element: " + pushHeavyStack.top() + " (Expected: 2)");
        System.out.println("Popped element: " + pushHeavyStack.pop() + " (Expected: 2)");
        System.out.println("Popped element: " + pushHeavyStack.pop() + " (Expected: 1)");
        System.out.println("Is empty: " + pushHeavyStack.empty() + " (Expected: true)");

        System.out.println("\n=== Test Case 3: Two Queues (Pop Heavy) ===");
        TwoQueuePopHeavyStack popHeavyStack = new TwoQueuePopHeavyStack();
        popHeavyStack.push(5);
        popHeavyStack.push(15);
        popHeavyStack.push(25);
        System.out.println("Top element: " + popHeavyStack.top() + " (Expected: 25)");
        System.out.println("Popped element: " + popHeavyStack.pop() + " (Expected: 25)");
        System.out.println("Top element after pop: " + popHeavyStack.top() + " (Expected: 15)");

        System.out.println("\n=== Test Case 4: Edge Case - Operations on Empty Stack ===");
        OptimalSingleQueueStack emptyStack = new OptimalSingleQueueStack();
        System.out.println("Pop from empty: " + emptyStack.pop() + " (Expected: -1)");
        System.out.println("Top from empty: " + emptyStack.top() + " (Expected: -1)");
        System.out.println("Is empty: " + emptyStack.empty() + " (Expected: true)");
    }
}
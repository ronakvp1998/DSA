package strivers.stackandqueues.learning;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * Implement Queue using Array
 * ============================================================================
 *
 * Always use circular Queue and don't use linear queue
 * The most famous limitation of a standard array-based queue: False Overflow.
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design a queue data structure using a standard array. You need to implement
 * the following fundamental queue operations:
 * 1. push(x): Pushes an element x to the back (rear) of the queue.
 * 2. pop(): Removes the element at the front of the queue and returns it.
 * 3. top() / peek(): Returns the element at the front without removing it.
 * 4. size(): Returns the current number of elements in the queue.
 *
 * Example 1:
 * Input:
 * ["push 4", "push 14", "push 24", "top", "size", "pop", "size", "top"]
 * Output:
 * [null, null, null, 4, 3, 4, 2, 14]
 *
 * Example 2 (Edge Case - Circular Wrap & Underflow):
 * Input:
 * ["push 1", "pop", "pop"]
 * Output:
 * [null, 1, -1]
 *
 * CONSTRAINTS:
 * 1 <= x <= 10^5
 * Maximum number of operations is 10^4.
 * Return -1 if pop() or top() is called on an empty queue.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Circular Array Queue (Refined Provided Code)
 * Phase 2: Brute Force Approach - Linear Array with Shifting
 * Phase 3: Alternative Approach - Dynamic Queue (LinkedList wrapper)
 * ============================================================================
 */
public class CircularQueueUsingArray {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Circular Array Queue)
     * ============================================================================
     * Detailed Intuition:
     * The code you provided forms an excellent basis for the optimal approach, utilizing
     * a Circular Queue. If we only move pointers forward, a standard array will quickly
     * run out of space at the end, even if there is empty space at the beginning due
     * to previous `pop()` operations. By using the modulo operator `(index + 1) % maxSize`,
     * we logically connect the end of the array back to the beginning, reusing vacated space.
     * * * Refinements made to your provided code:
     * 1. Fixed a critical bug in the default constructor where `maxSize` was left at 0,
     * which would cause an ArithmeticException (divide by zero) during modulo.
     * 2. Replaced `System.exit(1)` with standard error handling (returning -1 or printing)
     * to prevent the entire application from crashing on edge cases.
     * * Complexity Analysis:
     * - Time Complexity: O(1) for push, pop, top, and size operations. No elements
     * are shifted; we only manipulate pointer indices.
     * - Space Complexity: O(N) heap space where N is the maximum capacity of the queue.
     * O(1) auxiliary stack space.
     * ============================================================================
     */
    public static class OptimalCircularQueue {
        private final int[] arr;
        private int start, end, currSize, maxSize;

        public OptimalCircularQueue() {
            this.maxSize = 16; // FIXED: Explicitly set maxSize
            this.arr = new int[maxSize];
            this.start = -1;
            this.end = -1;
            this.currSize = 0;
        }

        public OptimalCircularQueue(int maxSize) {
            this.maxSize = maxSize;
            this.arr = new int[maxSize];
            this.start = -1;
            this.end = -1;
            this.currSize = 0;
        }

        public void push(int newElement) {
            if (currSize == maxSize) {
                System.out.println("Queue is full! Cannot push " + newElement);
                return; // Graceful exit instead of System.exit(1)
            }
            if (end == -1) {
                start = 0;
                end = 0;
            } else {
                end = (end + 1) % maxSize; // Circular increment
            }
            arr[end] = newElement;
            currSize++;
        }

        public int pop() {
            if (start == -1) {
                System.out.println("Queue is empty!");
                return -1;
            }
            int popped = arr[start];
            if (currSize == 1) {
                // Reset pointers if the queue becomes empty
                start = -1;
                end = -1;
            } else {
                start = (start + 1) % maxSize; // Circular increment
            }
            currSize--;
            return popped;
        }

        public int top() {
            if (start == -1) {
                return -1;
            }
            return arr[start];
        }

        public int size() {
            return currSize;
        }

        // Utility to display elements sequentially from front to rear using Streams
        public String display() {
            if (currSize == 0) return "[]";
            return "[" + IntStream.range(0, currSize)
                    .map(i -> arr[(start + i) % maxSize])
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" -> ")) + "]";
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Linear Array with Shifting) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most intuitive way to build a queue without thinking about circular logic
     * is to treat index 0 as the absolute "front" of the line.
     * When someone joins the queue (`push`), they go to the current `tail` index.
     * When someone leaves the queue (`pop`), the person at index 0 is removed, and
     * EVERY remaining element must shift one step to the left to fill the gap.
     * * Complexity Analysis:
     * - Time Complexity: O(1) for push(), but O(N) for pop() because we must copy
     * and shift all remaining elements. This is heavily unoptimized.
     * - Space Complexity: O(N) heap space for the array.
     * ============================================================================
     */
    public static class BruteForceLinearQueue {
        private final int[] arr;
        private int tail;
        private final int maxSize;

        public BruteForceLinearQueue(int capacity) {
            this.maxSize = capacity;
            this.arr = new int[capacity];
            this.tail = 0;
        }

        public void push(int x) {
            if (tail == maxSize) {
                System.out.println("Queue Overflow");
                return;
            }
            arr[tail] = x;
            tail++;
        }

        public int pop() {
            if (tail == 0) return -1;
            int popped = arr[0];

            // Shift all elements one position to the left
            for (int i = 0; i < tail - 1; i++) {
                arr[i] = arr[i + 1];
            }
            tail--; // Decrease size
            return popped;
        }

        public int top() {
            if (tail == 0) return -1;
            return arr[0];
        }

        public int size() {
            return tail;
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Dynamic Size using Java Collections)
     * ============================================================================
     * Detailed Intuition:
     * Arrays have fixed sizes. If we want a production-ready, infinitely expanding
     * queue, we rely on a Linked List architecture. In Java, the `LinkedList` class
     * implements the `Queue` interface directly. This allows dynamic memory allocation
     * without worrying about circular wrapping or capacity limits.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for all operations.
     * - Space Complexity: O(N) heap space, dynamically allocated per node.
     * ============================================================================
     */
    public static class DynamicQueue {
        private final Queue<Integer> queue;

        public DynamicQueue() {
            this.queue = new LinkedList<>();
        }

        public void push(int x) {
            queue.offer(x);
        }

        public int pop() {
            if (queue.isEmpty()) return -1;
            return queue.poll();
        }

        public int top() {
            if (queue.isEmpty()) return -1;
            return queue.peek();
        }

        public int size() {
            return queue.size();
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests the implementation against standard behavior, wrap-around, and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Standard Optimal Queue Operations ===");
        OptimalCircularQueue q1 = new OptimalCircularQueue(5);
        q1.push(4);
        q1.push(14);
        q1.push(24);
        System.out.println("Queue State: " + q1.display());
        System.out.println("Top element: " + q1.top() + " (Expected: 4)");
        System.out.println("Queue size: " + q1.size() + " (Expected: 3)");
        System.out.println("Element deleted: " + q1.pop() + " (Expected: 4)");
        System.out.println("Queue size: " + q1.size() + " (Expected: 2)");
        System.out.println("Top element: " + q1.top() + " (Expected: 14)");

        System.out.println("\n=== Test Case 2: Circular Wrap-Around Behavior ===");
        OptimalCircularQueue q2 = new OptimalCircularQueue(3);
        q2.push(10);
        q2.push(20);
        q2.push(30); // Queue is full: [10, 20, 30]
        System.out.println("Popped: " + q2.pop()); // Removes 10, space opens at index 0
        System.out.println("Popped: " + q2.pop()); // Removes 20, space opens at index 1
        q2.push(40); // Wraps around to index 0
        q2.push(50); // Wraps around to index 1
        System.out.println("Current Queue: " + q2.display() + " (Expected: [30 -> 40 -> 50])");

        System.out.println("\n=== Test Case 3: Edge Case - Underflow & Overflow ===");
        OptimalCircularQueue q3 = new OptimalCircularQueue(2);
        System.out.println("Pop on empty: " + q3.pop() + " (Expected: -1)");
        q3.push(1);
        q3.push(2);
        System.out.print("Push to full queue: ");
        q3.push(3); // Should trigger overflow message, safely ignored

        System.out.println("\n=== Test Case 4: Brute Force Shift Array ===");
        BruteForceLinearQueue bf = new BruteForceLinearQueue(5);
        bf.push(100);
        bf.push(200);
        System.out.println("Popped: " + bf.pop() + " (Expected: 100, 200 shifts to index 0)");
        System.out.println("Top now: " + bf.top() + " (Expected: 200)");

        System.out.println("\n=== Test Case 5: Dynamic LinkedList Queue ===");
        DynamicQueue dq = new DynamicQueue();
        dq.push(99);
        dq.push(88);
        System.out.println("Top: " + dq.top() + " | Size: " + dq.size());
    }
}
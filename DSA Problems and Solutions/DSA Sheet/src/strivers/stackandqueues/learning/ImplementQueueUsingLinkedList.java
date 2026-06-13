package strivers.stackandqueues.learning;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ============================================================================
 * Implement Queue using Linked List
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design and implement a Queue data structure using a Singly Linked List.
 * The queue must support the following standard operations:
 * 1. push(x): Adds an element x to the back (rear) of the queue.
 * 2. pop(): Removes the element from the front of the queue and returns it.
 * 3. peek() / top(): Returns the element at the front of the queue without removing it.
 * 4. isEmpty(): Returns true if the queue is empty, false otherwise.
 * 5. size(): Returns the number of elements in the queue.
 *
 * Example 1:
 * Input:
 * ["Queue", "push", "push", "push", "peek", "pop", "size", "isEmpty"]
 * [[], [10], [20], [30], [], [], [], []]
 * Output:
 * [null, null, null, null, 10, 10, 2, false]
 * Explanation:
 * Queue queue = new Queue();
 * queue.push(10); // Queue: 10
 * queue.push(20); // Queue: 10 -> 20
 * queue.push(30); // Queue: 10 -> 20 -> 30
 * queue.peek();   // Returns 10
 * queue.pop();    // Returns 10, Queue: 20 -> 30
 * queue.size();   // Returns 2
 * queue.isEmpty();// Returns false
 *
 * Example 2 (Edge Case - Underflow):
 * Input:
 * ["Queue", "pop", "peek"]
 * [[], [], []]
 * Output:
 * [null, -1, -1]
 *
 * CONSTRAINTS:
 * 1 <= x <= 10^5
 * Maximum number of operations is 10^5.
 * Return -1 if pop() or peek() is called on an empty queue.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Tail and Head Pointers (O(1) Time)
 * Phase 2: Brute Force Approach - Head Pointer Only (O(N) Push Time)
 * Phase 3: Alternative Approach - Using Built-in java.util.LinkedList
 * ============================================================================
 */
public class ImplementQueueUsingLinkedList {

    /**
     * Definition for singly-linked list node.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Tail and Head Pointers)
     * ============================================================================
     * Detailed Intuition:
     * A queue operates on a First-In-First-Out (FIFO) principle. To achieve strictly
     * O(1) time complexity for both `push` and `pop` operations using a singly
     * linked list, we must maintain TWO pointers: a `head` (front) and a `tail` (rear).
     * - `push(x)`: We attach the new node to `tail.next` and update the `tail` pointer.
     * This entirely avoids traversing the list to find the end.
     * - `pop()`: We read the value at `head`, and update `head` to `head.next`.
     * If the list becomes empty after popping, we must also set `tail` to null to
     * prevent memory leaks and stale references.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for push(), pop(), peek(), isEmpty(), and size().
     * - Space Complexity: O(N) heap space dynamically allocated for N nodes.
     * O(1) auxiliary stack space.
     * ============================================================================
     */
    public static class OptimalQueue {
        private ListNode head;
        private ListNode tail;
        private int size;

        public OptimalQueue() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public void push(int x) {
            ListNode newNode = new ListNode(x);
            if (tail == null) {
                // Queue was empty, so the new node is both head and tail
                head = newNode;
                tail = newNode;
            } else {
                // Attach to the end and move the tail pointer
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }

        public int pop() {
            if (isEmpty()) {
                System.out.println("Queue Underflow!");
                return -1;
            }
            int poppedValue = head.val;
            head = head.next; // Move head to the next node

            // Critical edge case: If the queue becomes empty, tail must be nullified
            if (head == null) {
                tail = null;
            }

            size--;
            return poppedValue;
        }

        public int peek() {
            if (isEmpty()) {
                return -1;
            }
            return head.val;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public int size() {
            return size;
        }

        // Utility method to display queue contents using Java 8 Streams
        public String display() {
            if (isEmpty()) return "[]";
            return "[" + Stream.iterate(head, node -> node != null, node -> node.next)
                    .map(node -> String.valueOf(node.val))
                    .collect(Collectors.joining(" -> ")) + "]";
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Head Pointer Only) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If someone intuitively thinks of a basic linked list, they usually only track
     * the `head` node. To maintain FIFO order without a `tail` pointer:
     * - `pop()` is still O(1) because we remove from the head.
     * - `push(x)` becomes an O(N) operation because we must traverse from the `head`
     * all the way to the end of the list every single time we want to add an element.
     * This severely degrades the performance of enqueue operations.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) for push() due to traversal. O(1) for pop(), peek(), size().
     * - Space Complexity: O(N) heap space for nodes.
     * ============================================================================
     */
    public static class BruteForceQueue {
        private ListNode head;
        private int size;

        public BruteForceQueue() {
            this.head = null;
            this.size = 0;
        }

        public void push(int x) {
            ListNode newNode = new ListNode(x);
            if (head == null) {
                head = newNode;
            } else {
                ListNode curr = head;
                // Traversal to find the tail
                while (curr.next != null) {
                    curr = curr.next;
                }
                curr.next = newNode;
            }
            size++;
        }

        public int pop() {
            if (head == null) return -1;

            int val = head.val;
            head = head.next;
            size--;
            return val;
        }

        public int peek() {
            if (head == null) return -1;
            return head.val;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Using Java Built-in LinkedList)
     * ============================================================================
     * Detailed Intuition:
     * In a production environment, implementing a custom linked list for a queue
     * is unnecessary unless required by constraints. Java's `java.util.LinkedList`
     * implements the `Queue` interface natively. We can cleanly wrap this built-in
     * class to expose strict FIFO queue operations using `offer` and `poll`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for all operations.
     * - Space Complexity: O(N) heap space overhead for a Doubly Linked List node structure.
     * ============================================================================
     */
    public static class BuiltInLinkedListQueue {
        private final Queue<Integer> queue;

        public BuiltInLinkedListQueue() {
            this.queue = new LinkedList<>();
        }

        public void push(int x) {
            queue.offer(x);
        }

        public int pop() {
            if (queue.isEmpty()) return -1;
            return queue.poll();
        }

        public int peek() {
            if (queue.isEmpty()) return -1;
            return queue.peek();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public int size() {
            return queue.size();
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
        System.out.println("=== Test Case 1: Standard Optimal Queue Operations ===");
        OptimalQueue optimalQueue = new OptimalQueue();
        optimalQueue.push(10);
        optimalQueue.push(20);
        optimalQueue.push(30);

        System.out.println("Queue State (Front to Back): " + optimalQueue.display());
        System.out.println("Front element: " + optimalQueue.peek() + " (Expected: 10)");
        System.out.println("Queue size: " + optimalQueue.size() + " (Expected: 3)");
        System.out.println("Element popped: " + optimalQueue.pop() + " (Expected: 10)");
        System.out.println("Queue size: " + optimalQueue.size() + " (Expected: 2)");
        System.out.println("Front element: " + optimalQueue.peek() + " (Expected: 20)");

        System.out.println("\n=== Test Case 2: Edge Case - Queue Underflow ===");
        OptimalQueue emptyQueue = new OptimalQueue();
        System.out.println("Pop on empty queue: " + emptyQueue.pop() + " (Expected: -1)");
        System.out.println("Peek on empty queue: " + emptyQueue.peek() + " (Expected: -1)");
        System.out.println("isEmpty: " + emptyQueue.isEmpty() + " (Expected: true)");

        System.out.println("\n=== Test Case 3: Emptying and Re-populating (Checking Tail Pointer) ===");
        OptimalQueue refreshQueue = new OptimalQueue();
        refreshQueue.push(100);
        System.out.println("Popped: " + refreshQueue.pop() + " (Expected: 100)");
        refreshQueue.push(200); // If tail wasn't updated correctly, this fails
        System.out.println("Front element: " + refreshQueue.peek() + " (Expected: 200)");

        System.out.println("\n=== Test Case 4: Brute Force Queue (O(N) Push) ===");
        BruteForceQueue bfQueue = new BruteForceQueue();
        bfQueue.push(7);
        bfQueue.push(14);
        System.out.println("Front element: " + bfQueue.peek() + " (Expected: 7)");
        System.out.println("Element popped: " + bfQueue.pop() + " (Expected: 7)");
        System.out.println("Front element now: " + bfQueue.peek() + " (Expected: 14)");

        System.out.println("\n=== Test Case 5: Java Built-in LinkedList ===");
        BuiltInLinkedListQueue builtInQueue = new BuiltInLinkedListQueue();
        builtInQueue.push(55);
        builtInQueue.push(66);
        System.out.println("Front element: " + builtInQueue.peek() + " (Expected: 55)");
        builtInQueue.pop();
        System.out.println("isEmpty: " + builtInQueue.isEmpty() + " (Expected: false)");
    }
}
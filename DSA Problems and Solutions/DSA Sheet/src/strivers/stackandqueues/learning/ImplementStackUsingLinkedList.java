package strivers.stackandqueues.learning;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ============================================================================
 * Implement Stack using Linked List
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design and implement a Stack data structure using a Singly Linked List.
 * The stack must support the following standard operations:
 * 1. push(x): Adds an element x to the top of the stack.
 * 2. pop(): Removes the element from the top of the stack and returns it.
 * 3. peek() / top(): Returns the element at the top of the stack without removing it.
 * 4. isEmpty(): Returns true if the stack is empty, false otherwise.
 * 5. size(): Returns the number of elements in the stack.
 *
 * Example 1:
 * Input:
 * ["Stack", "push", "push", "push", "peek", "pop", "size", "isEmpty"]
 * [[], [10], [20], [30], [], [], [], []]
 * Output:
 * [null, null, null, null, 30, 30, 2, false]
 * Explanation:
 * Stack stack = new Stack();
 * stack.push(10); // Stack: 10
 * stack.push(20); // Stack: 20 -> 10
 * stack.push(30); // Stack: 30 -> 20 -> 10
 * stack.peek();   // Returns 30
 * stack.pop();    // Returns 30, Stack: 20 -> 10
 * stack.size();   // Returns 2
 * stack.isEmpty();// Returns false
 *
 * Example 2 (Edge Case - Underflow):
 * Input:
 * ["Stack", "pop", "peek"]
 * [[], [], []]
 * Output:
 * [null, -1, -1]
 *
 * CONSTRAINTS:
 * 1 <= x <= 10^5
 * Maximum number of operations is 10^5.
 * Return -1 if pop() or peek() is called on an empty stack.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Insert/Remove at Head (O(1) Time)
 * Phase 2: Brute Force Approach - Insert/Remove at Tail without Tail Pointer (O(N) Time)
 * Phase 3: Alternative Approach - Using Built-in java.util.LinkedList (Deque)
 * ============================================================================
 */
public class ImplementStackUsingLinkedList {

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
     * PHASE 1: OPTIMAL APPROACH (Insert/Remove at Head)
     * ============================================================================
     * Detailed Intuition:
     * A stack operates on a Last-In-First-Out (LIFO) principle. To achieve strictly
     * O(1) time complexity for both `push` and `pop` operations using a singly
     * linked list, we must map the "top" of the stack to the "head" of the linked list.
     * - When we push, we create a new node, point its `next` to the current head,
     * and update the head reference.
     * - When we pop, we read the value at the head, and update the head reference
     * to `head.next`.
     * This entirely avoids traversing the list, providing optimal performance.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for push(), pop(), peek(), isEmpty(), and size().
     * - Space Complexity: O(N) heap space dynamically allocated for N nodes.
     * O(1) auxiliary stack space.
     * ============================================================================
     */
    public static class OptimalStack {
        private ListNode head;
        private int size;

        public OptimalStack() {
            this.head = null;
            this.size = 0;
        }

        public void push(int x) {
            ListNode newNode = new ListNode(x);
            newNode.next = head; // Point new node to the current top
            head = newNode;      // New node becomes the new top
            size++;
        }

        public int pop() {
            if (isEmpty()) {
                System.out.println("Stack Underflow!");
                return -1;
            }
            int poppedValue = head.val;
            head = head.next;    // Move head to the next node
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

        // Utility method to display stack contents using Java 8 Streams
        public String display() {
            return "[" + Stream.iterate(head, node -> node != null, node -> node.next)
                    .map(node -> String.valueOf(node.val))
                    .collect(Collectors.joining(" -> ")) + "]";
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Insert/Remove at Tail) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If someone intuitively thinks of an array appending to the end, they might
     * try to map the "top" of the stack to the "tail" of the linked list.
     * However, in a standard Singly Linked List without a tail pointer (or even
     * with one, since popping requires finding the second-to-last element),
     * we must traverse the entire list to find the end for every push and pop.
     * This severely degrades performance from O(1) to O(N).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) for push() and pop() due to traversal. O(1) for size().
     * - Space Complexity: O(N) heap space for nodes.
     * ============================================================================
     */
    public static class BruteForceStack {
        private ListNode head;
        private int size;

        public BruteForceStack() {
            this.head = null;
            this.size = 0;
        }

        public void push(int x) {
            ListNode newNode = new ListNode(x);
            if (head == null) {
                head = newNode;
            } else {
                ListNode curr = head;
                while (curr.next != null) {
                    curr = curr.next; // Traversal to find tail
                }
                curr.next = newNode;
            }
            size++;
        }

        public int pop() {
            if (head == null) return -1;

            size--;
            if (head.next == null) {
                int val = head.val;
                head = null;
                return val;
            }

            ListNode curr = head;
            // Traversal to find second-to-last node
            while (curr.next.next != null) {
                curr = curr.next;
            }
            int val = curr.next.val;
            curr.next = null; // Sever the tail
            return val;
        }

        public int peek() {
            if (head == null) return -1;
            ListNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            return curr.val;
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Using Java Built-in LinkedList)
     * ============================================================================
     * Detailed Intuition:
     * In production environments, reinventing the wheel is usually discouraged.
     * Java's `java.util.LinkedList` implements the `Deque` interface, making it
     * a fully-featured doubly-linked list. We can cleanly wrap this built-in class
     * to expose only strict LIFO stack operations using `addFirst` and `removeFirst`.
     * (Note: For modern Java, `ArrayDeque` is heavily preferred over `LinkedList`
     * for stacks due to cache locality, but this strictly fulfills the prompt's
     * LinkedList requirement).
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for all operations.
     * - Space Complexity: O(N) heap space overhead for a Doubly Linked List node structure.
     * ============================================================================
     */
    public static class BuiltInLinkedListStack {
        private final LinkedList<Integer> list;

        public BuiltInLinkedListStack() {
            this.list = new LinkedList<>();
        }

        public void push(int x) {
            list.addFirst(x);
        }

        public int pop() {
            if (list.isEmpty()) return -1;
            return list.removeFirst();
        }

        public int peek() {
            if (list.isEmpty()) return -1;
            return list.getFirst();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public int size() {
            return list.size();
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
        System.out.println("=== Test Case 1: Standard Optimal Stack Operations ===");
        OptimalStack optimalStack = new OptimalStack();
        optimalStack.push(10);
        optimalStack.push(20);
        optimalStack.push(30);

        System.out.println("Stack State (Top to Bottom): " + optimalStack.display());
        System.out.println("Top element: " + optimalStack.peek() + " (Expected: 30)");
        System.out.println("Stack size: " + optimalStack.size() + " (Expected: 3)");
        System.out.println("Element popped: " + optimalStack.pop() + " (Expected: 30)");
        System.out.println("Stack size: " + optimalStack.size() + " (Expected: 2)");
        System.out.println("Top element: " + optimalStack.peek() + " (Expected: 20)");

        System.out.println("\n=== Test Case 2: Edge Case - Stack Underflow ===");
        OptimalStack emptyStack = new OptimalStack();
        System.out.println("Pop on empty stack: " + emptyStack.pop() + " (Expected: -1)");
        System.out.println("Peek on empty stack: " + emptyStack.peek() + " (Expected: -1)");
        System.out.println("isEmpty: " + emptyStack.isEmpty() + " (Expected: true)");

        System.out.println("\n=== Test Case 3: Brute Force Stack (Tail Operations) ===");
        BruteForceStack bfStack = new BruteForceStack();
        bfStack.push(100);
        bfStack.push(200);
        System.out.println("Top element: " + bfStack.peek() + " (Expected: 200)");
        System.out.println("Element popped: " + bfStack.pop() + " (Expected: 200)");
        System.out.println("Top element now: " + bfStack.peek() + " (Expected: 100)");

        System.out.println("\n=== Test Case 4: Java Built-in LinkedList ===");
        BuiltInLinkedListStack builtInStack = new BuiltInLinkedListStack();
        builtInStack.push(55);
        builtInStack.push(66);
        System.out.println("Top element: " + builtInStack.peek() + " (Expected: 66)");
        builtInStack.pop();
        System.out.println("isEmpty: " + builtInStack.isEmpty() + " (Expected: false)");
    }
}